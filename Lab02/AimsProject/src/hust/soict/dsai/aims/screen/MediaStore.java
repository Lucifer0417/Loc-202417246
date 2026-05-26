package hust.soict.dsai.aims.screen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
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

    public MediaStore(Media media, Cart cart) {
        this.media = media;
        this.cart = cart;

        setLayout(new BorderLayout(8, 8));
        setPreferredSize(new Dimension(240, 170));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel title = new JLabel(media.getTitle(), SwingConstants.CENTER);
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 16));
        JLabel cost = new JLabel(String.format("%.2f $", media.getCost()), SwingConstants.CENTER);

        JPanel info = new JPanel(new BorderLayout());
        info.add(title, BorderLayout.CENTER);
        info.add(cost, BorderLayout.SOUTH);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addToCart = new JButton("Add to cart");
        addToCart.addActionListener(e -> addMediaToCart());
        buttons.add(addToCart);

        if (media instanceof Playable) {
            JButton play = new JButton("Play");
            play.addActionListener(e -> AimsScreenNavigator.playMedia((JFrame) javax.swing.SwingUtilities
                    .getWindowAncestor(this), media));
            buttons.add(play);
        }

        add(info, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void addMediaToCart() {
        cart.addMedia(media);
        JOptionPane.showMessageDialog(this, media.getTitle() + " has been added to the cart.");
    }
}
