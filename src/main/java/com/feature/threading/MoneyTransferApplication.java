package com.feature.threading;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

class Account {

    String name;
    BigDecimal balance;

    public Account(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}

class AccountProcessor {

    public void deposit(Account account, BigDecimal balance) {
        account.setBalance(account.getBalance().add(balance));
    }

    public synchronized void transfer(Account from, Account to, BigDecimal balance) {
        deposit(from, balance.negate());
        deposit(to, balance);
    }
}

public class MoneyTransferApplication {

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account("A", BigDecimal.TEN.multiply(BigDecimal.valueOf(1000d)));
        Account b = new Account("B", BigDecimal.TEN.multiply(BigDecimal.valueOf(1000d)));
        Account c = new Account("C", BigDecimal.TEN.multiply(BigDecimal.valueOf(1000d)));
        Account d = new Account("D", BigDecimal.TEN.multiply(BigDecimal.valueOf(1000d)));

        AccountProcessor processor = new AccountProcessor();

        System.out.println("Initial Balance");
        printBalance(a, b, c, d);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(() -> processor.transfer(a, b, BigDecimal.valueOf(1000d)));
        executorService.execute(() -> processor.transfer(b, c, BigDecimal.valueOf(1000d)));
        executorService.execute(() -> processor.transfer(b, d, BigDecimal.valueOf(1000d)));
        executorService.execute(() -> processor.transfer(a, d, BigDecimal.valueOf(1000d)));

        List<Callable<Boolean>> callables = new ArrayList<>();
        callables.add(()->{
            processor.transfer(a, b, BigDecimal.valueOf(1000d));
            return null;
        });
        callables.add(()->{
            processor.transfer(b, c, BigDecimal.valueOf(1000d));
            return null;
        });
        callables.add(()->{
            processor.transfer(b, d, BigDecimal.valueOf(1000d));
            return null;
        });
        callables.add(()->{
            processor.transfer(a, b, BigDecimal.valueOf(1000d));
            return null;
        });

        executorService.invokeAll(callables);


        executorService.shutdown();
        try {
            TimeUnit.SECONDS.sleep(5);
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdownNow();

        System.out.println("\nAfter transfer");
        printBalance(a, b, c, d);


    }

    private static void printBalance(Account a, Account b, Account c, Account d) {
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
    }
}
