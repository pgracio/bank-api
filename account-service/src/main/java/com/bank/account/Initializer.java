package com.bank.account;

import com.bank.account.repository.AccountRepository;
import com.bank.account.model.Account;
import com.bank.account.model.Customer;
import com.bank.account.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {
    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;

    public Initializer(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) {

        Customer customer = customerRepository.save(Customer.builder()
                .name("Paulo")
                .surname("Grácio")
                .build());

        accountRepository.save(Account.builder()
                .costumer(customer)
                .balance(100L)
                .build());
    }
}
