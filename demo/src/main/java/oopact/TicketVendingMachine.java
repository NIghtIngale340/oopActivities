package oopact;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TicketVendingMachine extends JFrame {
    private final String[] rides = {
        "Roller Coaster - ₱150.00",
        "Ferris Wheel - ₱100.00",
        "Carousel - ₱75.00",
        "Bumper Cars - ₱70.00"
    };
    
    private final String[] ticketTypes = {
        "Adult",
        "Child (50% off)",
        "Senior (30% off)"
    };
    
    private final double[] prices = {150.00, 100.00, 75.00, 70.00};
    private JComboBox<String> rideSelector;
    private JComboBox<String> ticketTypeSelector;
    private JLabel priceLabel;
    private JTextField paymentField;
    private double currentPrice;
    
    public TicketVendingMachine() {
        setTitle("Amusement Park Ride Pass Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Amusement Park Ride Pass Vending Machine");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Ride selection
        JPanel ridePanel = new JPanel();
        ridePanel.add(new JLabel("Select Ride: "));
        rideSelector = new JComboBox<>(rides);
        ridePanel.add(rideSelector);
        mainPanel.add(ridePanel);
        
        // Ticket type selection
        JPanel ticketPanel = new JPanel();
        ticketPanel.add(new JLabel("Ticket Type: "));
        ticketTypeSelector = new JComboBox<>(ticketTypes);
        ticketPanel.add(ticketTypeSelector);
        mainPanel.add(ticketPanel);
        
        // Price display
        priceLabel = new JLabel("Total Price: ₱0.00");
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(priceLabel);
        
        // Payment input
        JPanel paymentPanel = new JPanel();
        paymentPanel.add(new JLabel("Enter Payment: ₱"));
        paymentField = new JTextField(10);
        paymentPanel.add(paymentField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(paymentPanel);
        
        // Purchase button
        JButton purchaseButton = new JButton("Purchase Ticket");
        purchaseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(purchaseButton);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Add event listeners
        rideSelector.addActionListener(e -> updatePrice());
        ticketTypeSelector.addActionListener(e -> updatePrice());
        
        purchaseButton.addActionListener(e -> processPurchase());
        
        // Initial price update
        updatePrice();
        
        // Set window properties
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void updatePrice() {
        int rideIndex = rideSelector.getSelectedIndex();
        int ticketType = ticketTypeSelector.getSelectedIndex();
        
        currentPrice = prices[rideIndex];
        
        // Apply discounts based on ticket type
        if (ticketType == 1) { // Child
            currentPrice *= 0.5; // 50% off
        } else if (ticketType == 2) { // Senior
            currentPrice *= 0.7; // 30% off
        }
        
        DecimalFormat df = new DecimalFormat("0.00");
        priceLabel.setText("Total Price: ₱" + df.format(currentPrice));
    }
    
    private void processPurchase() {
        try {
            double payment = Double.parseDouble(paymentField.getText());
            if (payment < currentPrice) {
                JOptionPane.showMessageDialog(this,
                    "Insufficient payment. Please enter at least ₱" + 
                    new DecimalFormat("0.00").format(currentPrice),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double change = payment - currentPrice;
            DecimalFormat df = new DecimalFormat("0.00");
            
            // Log transaction
            System.out.println("\n=== Transaction Log ===");
            System.out.println("Ride: " + rides[rideSelector.getSelectedIndex()]);
            System.out.println("Ticket Type: " + ticketTypes[ticketTypeSelector.getSelectedIndex()]);
            System.out.println("Price: ₱" + df.format(currentPrice));
            System.out.println("Payment: ₱" + df.format(payment));
            System.out.println("Change: ₱" + df.format(change));
            System.out.println("===================\n");
            
            // Show ticket
            String ticket = "=== RIDE TICKET ===\n" +
                          "Ride: " + rides[rideSelector.getSelectedIndex()].split(" -")[0] + "\n" +
                          "Type: " + ticketTypes[ticketTypeSelector.getSelectedIndex()] + "\n" +
                          "Price Paid: ₱" + df.format(currentPrice) + "\n" +
                          "Change: ₱" + df.format(change) + "\n" +
                          "================\n" +
                          "Thank you for your purchase!";
            
            JOptionPane.showMessageDialog(this, ticket, "Your Ticket", JOptionPane.INFORMATION_MESSAGE);
            
            // Reset for next customer
            resetMachine();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid payment amount",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void resetMachine() {
        rideSelector.setSelectedIndex(0);
        ticketTypeSelector.setSelectedIndex(0);
        paymentField.setText("");
        updatePrice();
    }
}
