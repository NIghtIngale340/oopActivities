package oopact.bankATM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATMGUI extends JFrame {
    private BankSystem bankSystem;
    private String currentAccount = null;
    
    private JPanel loginPanel;
    private JPanel operationPanel;
    
    private JTextField accountField;
    private JPasswordField pinField;
    private JLabel statusLabel;
    private JTextField amountField;
    private JLabel balanceLabel;
    
    public ATMGUI() {
        bankSystem = new BankSystem();
        
        // Set up the frame
        setTitle("Simple ATM");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create panels
        createLoginPanel();
        createOperationPanel();
        
        // Initially show login panel
        getContentPane().add(loginPanel);
        
        setVisible(true);
    }
    
    private void createLoginPanel() {
        loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel accountLabel = new JLabel("Account Number:");
        accountField = new JTextField(15);
        JLabel pinLabel = new JLabel("PIN:");
        pinField = new JPasswordField(4);
        JButton loginButton = new JButton("Login");
        statusLabel = new JLabel("");
        statusLabel.setForeground(Color.RED);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(accountLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(accountField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(pinLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(pinField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(statusLabel, gbc);
        
        loginButton.addActionListener(e -> {
            String accountNumber = accountField.getText();
            String pin = new String(pinField.getPassword());
            
            if (bankSystem.authenticateUser(accountNumber, pin)) {
                currentAccount = accountNumber;
                updateBalance();
                switchToOperationPanel();
            } else {
                statusLabel.setText("Invalid account number or PIN");
            }
        });
    }
    
    private void createOperationPanel() {
        operationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        balanceLabel = new JLabel("Current Balance: $0.00");
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton logoutButton = new JButton("Logout");
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        operationPanel.add(balanceLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        operationPanel.add(amountLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        operationPanel.add(amountField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        operationPanel.add(withdrawButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        operationPanel.add(depositButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        operationPanel.add(checkBalanceButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        operationPanel.add(logoutButton, gbc);
        
        withdrawButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (bankSystem.userWithdraw(currentAccount, amount)) {
                    JOptionPane.showMessageDialog(this, 
                        String.format("Successfully withdrew $%.2f", amount));
                    updateBalance();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Withdrawal failed. Insufficient funds or invalid amount.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid amount", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        depositButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (bankSystem.userDeposit(currentAccount, amount)) {
                    JOptionPane.showMessageDialog(this, 
                        String.format("Successfully deposited $%.2f", amount));
                    updateBalance();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Deposit failed. Please enter a valid amount.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid amount", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        checkBalanceButton.addActionListener(e -> {
            updateBalance();
            JOptionPane.showMessageDialog(this, 
                String.format("Your current balance is $%.2f", 
                bankSystem.checkBalance(currentAccount)));
        });
        
        logoutButton.addActionListener(e -> {
            currentAccount = null;
            accountField.setText("");
            pinField.setText("");
            amountField.setText("");
            statusLabel.setText("");
            switchToLoginPanel();
        });
    }
    
    private void updateBalance() {
        double balance = bankSystem.checkBalance(currentAccount);
        balanceLabel.setText(String.format("Current Balance: $%.2f", balance));
    }
    
    private void switchToOperationPanel() {
        getContentPane().removeAll();
        getContentPane().add(operationPanel);
        revalidate();
        repaint();
    }
    
    private void switchToLoginPanel() {
        getContentPane().removeAll();
        getContentPane().add(loginPanel);
        revalidate();
        repaint();
    }
}