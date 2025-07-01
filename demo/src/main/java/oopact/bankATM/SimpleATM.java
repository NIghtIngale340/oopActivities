package oopact.bankATM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleATM {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ATMGUI();
        });
    }
}

class Account {
    private String accountNumber;
    private String pin;
    private double balance;

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public boolean validatePin(String inputPin) {
        return pin.equals(inputPin);
    }
    
    public double getBalance() {
        return balance;
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
    
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }
}

class BankSystem {
    private Account[] accounts;
    
    public BankSystem() {
        // Initialize with some predefined accounts
        accounts = new Account[] {
            new Account("123456", "1234", 1000.0),
            new Account("654321", "1234", 2500.0),
            new Account("808080", "1234", 500.0)
        };
    }
    
    public boolean authenticateUser(String accountNumber, String pin) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            return account.validatePin(pin);
        }
        return false;
    }
    
    public double checkBalance(String accountNumber) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            return account.getBalance();
        }
        return -1;
    }
    
    public boolean userWithdraw(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            return account.withdraw(amount);
        }
        return false;
    }
    
    public boolean userDeposit(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            return account.deposit(amount);
        }
        return false;
    }
    
    private Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}