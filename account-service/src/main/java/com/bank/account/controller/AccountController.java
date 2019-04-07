package com.bank.account.controller;

import com.bank.account.event.AccountCreated;
import com.bank.account.model.Customer;
import com.bank.account.repository.AccountRepository;
import com.bank.account.repository.CustomerRepository;
import com.bank.account.model.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/v1/"})
public class AccountController {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    private KafkaTemplate<String, AccountCreated> kafkaTemplate;

    public AccountController(AccountRepository accountRepository, CustomerRepository customerRepository, KafkaTemplate<String, AccountCreated> kafkaTemplate) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/accounts")
    public List findAll() {
        return accountRepository.findAll();
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> create(@RequestBody @Valid AccountRequest request) {

        Optional<Customer> customer = customerRepository.findById(request.getCustomerId());

        if (customer.isPresent()) {
            Account account = Account.builder()
                    .costumer(customer.get())
                    .balance(request.getInitialBalance())
                    .build();
            accountRepository.save(account);

            kafkaTemplate.send("account-events", AccountCreated.builder()
                    .accountId(account.getId())
                    .amount(account.getBalance())
                    .build());

            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
