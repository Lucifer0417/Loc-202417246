package hust.soict.dsai.aims.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.Playable;

public class MediaStore extends JPanel {
    private final Media media;
    private final Cart cart;
    private final Runnable onCartChanged;

    public MediaStore(Media media, Cart cart) {
        this(media, cart, null);
    }

    public MediaStore(Media media, Cart cart, Runnable onCartChanged) {
        this.media = media;
        this.cart = cart;
        this.onCartChanged = onCartChanged;

        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(260, 190));
        AimsUi.surface(this);

        JLabel type = new JLabel(getDisplayType(media), SwingConstants.LEFT);
        type.setForeground(AimsUi.MUTED);
        type.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel title = new JLabel("<html><b>" + media.getTitle() + "</b></html>", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(AimsUi.TEXT);

        JLabel category = new JLabel(media.getCategory() == null ? "No category" : media.getCategory());
        category.setForeground(AimsUi.MUTED);

        JLabel cost = new JLabel(String.format("%.2f $", media.getCost()), SwingConstants.LEFT);
        cost.setForeground(AimsUi.SUCCESS);
        cost.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel info = new JPanel(new GridLayout(4, 1, 0, 4));
        info.setOpaque(false);
        info.add(type);
        info.add(title);
        info.add(category);
        info.add(cost);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttons.setOpaque(false);

        JButton addToCart = AimsUi.button("Add to cart", AimsUi.PRIMARY);
        addToCart.addActionListener(e -> addMediaToCart());
        buttons.add(addToCart);

        if (media instanceof Playable) {
            JButton play = AimsUi.button("Play", new Color(102, 16, 242));
            play.addActionListener(e -> AimsScreenNavigator.playMedia(
                    (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), media));
            buttons.add(play);
        }

        add(info, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void addMediaToCart() {
        cart.addMedia(media);
        if (onCartChanged != null) {
            onCartChanged.run();
        }
        JOptionPane.showMessageDialog(this, "Added " + media.getTitle() + " to cart.");
    }

    private String getDisplayType(Media media) {
        if (media instanceof hust.soict.dsai.aims.media.Book) {
            return "Book";
        }
        if (media instanceof hust.soict.dsai.aims.media.CompactDisc) {
            return "CD";
        }
        if (media instanceof hust.soict.dsai.aims.media.DigitalVideoDisc) {
            return "DVD";
        }
        return "Media";
    }
}
