package hust.soict.dsai.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NumberGrid extends JFrame {
    private final JButton[] btnNumbers = new JButton[10];
    private final JButton btnDelete = new JButton("DEL");
    private final JButton btnReset = new JButton("C");
    private final JTextField tfDisplay = new JTextField();

    public NumberGrid() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        tfDisplay.setEditable(false);
        cp.add(tfDisplay, BorderLayout.NORTH);

        JPanel panelButtons = new JPanel(new GridLayout(4, 3));
        addButtons(panelButtons);
        cp.add(panelButtons, BorderLayout.CENTER);

        setTitle("Number Grid");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addButtons(JPanel panelButtons) {
        ButtonListener listener = new ButtonListener();

        for (int i = 1; i <= 9; i++) {
            btnNumbers[i] = new JButton(Integer.toString(i));
            btnNumbers[i].addActionListener(listener);
            panelButtons.add(btnNumbers[i]);
        }

        btnDelete.addActionListener(listener);
        panelButtons.add(btnDelete);

        btnNumbers[0] = new JButton("0");
        btnNumbers[0].addActionListener(listener);
        panelButtons.add(btnNumbers[0]);

        btnReset.addActionListener(listener);
        panelButtons.add(btnReset);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            for (JButton btnNumber : btnNumbers) {
                if (source == btnNumber) {
                    tfDisplay.setText(tfDisplay.getText() + btnNumber.getText());
                    return;
                }
            }

            if (source == btnDelete) {
                String current = tfDisplay.getText();
                if (!current.isEmpty()) {
                    tfDisplay.setText(current.substring(0, current.length() - 1));
                }
                return;
            }

            if (source == btnReset) {
                tfDisplay.setText("");
            }
        }
    }

    public static void main(String[] args) {
        new NumberGrid();
    }
}
