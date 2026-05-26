package hust.soict.dsai.swing;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SwingAccumulator extends JFrame {
    private final JTextField tfInput;
    private final JTextField tfOutput;
    private int sum = 0;

    public SwingAccumulator() {
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(2, 2));

        cp.add(new JLabel("Enter an integer: "));
        tfInput = new JTextField(10);
        cp.add(tfInput);
        tfInput.addActionListener(new TFInputListener());

        cp.add(new JLabel("The accumulated sum is: "));
        tfOutput = new JTextField(10);
        tfOutput.setEditable(false);
        cp.add(tfOutput);

        setTitle("Swing Accumulator");
        setSize(350, 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class TFInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                int numberIn = Integer.parseInt(tfInput.getText().trim());
                sum += numberIn;
                tfInput.setText("");
                tfOutput.setText(Integer.toString(sum));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        SwingAccumulator.this,
                        "Please enter a valid integer.",
                        "Invalid input",
                        JOptionPane.ERROR_MESSAGE);
                tfInput.setText("");
            }
        }
    }

    public static void main(String[] args) {
        new SwingAccumulator();
    }
}
