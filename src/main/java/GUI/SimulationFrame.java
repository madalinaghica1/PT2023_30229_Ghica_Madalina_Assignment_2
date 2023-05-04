package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class SimulationFrame extends JFrame  {

    private JPanel panel;
    private JLabel label;
    private JTextArea textArea;

    public SimulationFrame() {
        super("Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        initUI();

        setVisible(true);
    }

    private void initUI() {
        panel = new JPanel(new BorderLayout());

        label = new JLabel("Simulation Output");
        panel.add(label, BorderLayout.NORTH);

        textArea = new JTextArea(20, 40);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        panel.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(panel);
    }


    public void refreshFrame(String display) {
        textArea.append(display + "\n");
    }


    public List<Integer> getInitValues() {
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        inputPanel.add(new JLabel("Number of clients:"));
        JTextField numClientsField = new JTextField();
        inputPanel.add(numClientsField);
        inputPanel.add(new JLabel("Number of queues:"));
        JTextField numServersField = new JTextField();
        inputPanel.add(numServersField);
        inputPanel.add(new JLabel("Maximum time:"));
        JTextField timeLimitField = new JTextField();
        inputPanel.add(timeLimitField);
        inputPanel.add(new JLabel("Minimum arrival time:"));
        JTextField minArrivalField = new JTextField();
        inputPanel.add(minArrivalField);
        inputPanel.add(new JLabel("Maximum arrival time:"));
        JTextField maxArrivalField = new JTextField();
        inputPanel.add(maxArrivalField);
        inputPanel.add(new JLabel("Minimum processing time:"));
        JTextField minProcessingField = new JTextField();
        inputPanel.add(minProcessingField);
        inputPanel.add(new JLabel("Maximum processing time:"));
        JTextField maxProcessingField = new JTextField();
        inputPanel.add(maxProcessingField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Introduceti valorilor",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int numClients = Integer.parseInt(numClientsField.getText());
                int numServers = Integer.parseInt(numServersField.getText());
                int timeLimit = Integer.parseInt(timeLimitField.getText());
                int minArrival = Integer.parseInt(minArrivalField.getText());
                int maxArrival = Integer.parseInt(maxArrivalField.getText());
                int minProcessing = Integer.parseInt(minProcessingField.getText());
                int maxProcessing = Integer.parseInt(maxProcessingField.getText());
                return Arrays.asList(numClients, numServers, timeLimit, minArrival, maxArrival, minProcessing, maxProcessing);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter integers only.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return null;
    }}
