package oopact.vendingMachine;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            TicketVendingMachine machine = new TicketVendingMachine();
            machine.setVisible(true);
        });
    }
}