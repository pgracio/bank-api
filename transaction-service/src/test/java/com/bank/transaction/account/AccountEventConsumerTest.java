package com.bank.transaction.account;

import com.bank.transaction.event.AccountCreated;
import com.bank.transaction.model.Transaction;
import com.bank.transaction.repository.TransactionRepository;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka
@DirtiesContext
public class AccountEventConsumerTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Autowired
    private TransactionRepository transactionRepository;


    @Test
    public void listen() throws InterruptedException {
        Map<String, Object> senderProps = KafkaTestUtils.senderProps(embeddedKafka.getBrokersAsString());
        senderProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        senderProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        ProducerFactory<String, AccountCreated> producerFactory = new DefaultKafkaProducerFactory<>(senderProps);

        KafkaTemplate<String, AccountCreated> template = new KafkaTemplate<>(producerFactory);

        AccountCreated accountCreated = new AccountCreated();
        accountCreated.setAccountId(1L);
        accountCreated.setAmount(200L);
        accountCreated.setEventType("account-created");

        template.send("account-events", accountCreated);

        Thread.sleep(1000);

        List<Transaction> transactions = transactionRepository.findByAccount(1L);
        assertThat(transactions.size()).isEqualTo(1);
    }
}