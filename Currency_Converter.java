import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Currency_Converter extends JFrame {
    private JTextField amountField;
    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JLabel resultLabel;

    private static final Map<String, Double> exchangeRates = new HashMap<>();

    static {
        // Sample exchange rates
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.85);
        exchangeRates.put("GBP", 0.75);
        exchangeRates.put("INR", 75.0);
    }

    public Currency_Converter() {
        setTitle("Currency Converter");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI components
        amountField = new JTextField(10);
        fromCurrency = new JComboBox<>(exchangeRates.keySet().toArray(new String[0]));
        toCurrency = new JComboBox<>(exchangeRates.keySet().toArray(new String[0]));
        resultLabel = new JLabel("Converted Amount: ");

        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ConvertActionListener());

        // Layout setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("From:"));
        panel.add(fromCurrency);
        panel.add(new JLabel("To:"));
        panel.add(toCurrency);
        panel.add(convertButton);
        panel.add(new JLabel(""));
        panel.add(resultLabel);

        add(panel);
    }

    private class ConvertActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String from = (String) fromCurrency.getSelectedItem();
                String to = (String) toCurrency.getSelectedItem();

                double convertedAmount = convertCurrency(amount, from, to);
                resultLabel.setText("Converted Amount: " + String.format("%.2f", convertedAmount));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Currency_Converter.this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private double convertCurrency(double amount, String from, String to) {
        double fromRate = exchangeRates.get(from);
        double toRate = exchangeRates.get(to);
        return amount * (toRate / fromRate);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Currency_Converter converter = new Currency_Converter();
            converter.setVisible(true);
        });
    }
}
