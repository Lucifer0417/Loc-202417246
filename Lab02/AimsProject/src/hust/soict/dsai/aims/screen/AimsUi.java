package hust.soict.dsai.aims.screen;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;

final class AimsUi {
    static final Color BACKGROUND = new Color(245, 247, 250);
    static final Color SURFACE = Color.WHITE;
    static final Color BORDER = new Color(222, 226, 230);
    static final Color TEXT = new Color(33, 37, 41);
    static final Color MUTED = new Color(108, 117, 125);
    static final Color PRIMARY = new Color(0, 123, 255);
    static final Color SUCCESS = new Color(25, 135, 84);
    static final Color DANGER = new Color(220, 53, 69);
    static final Color WARNING = new Color(255, 193, 7);

    private AimsUi() {
    }

    static Border paddedBorder(int top, int left, int bottom, int right) {
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
    }

    static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(14, 14, 14, 14));
    }

    static JLabel screenTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 36));
        label.setForeground(TEXT);
        return label;
    }

    static JLabel sectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(TEXT);
        return label;
    }

    static JLabel muted(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(MUTED);
        return label;
    }

    static JButton button(String text, Color background) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        return button;
    }

    static JButton secondaryButton(String text) {
        JButton button = button(text, new Color(73, 80, 87));
        return button;
    }

    static void surface(JComponent component) {
        component.setBackground(SURFACE);
        component.setBorder(cardBorder());
    }
}
