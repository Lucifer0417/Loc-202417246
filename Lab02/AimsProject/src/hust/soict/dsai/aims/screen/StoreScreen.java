package hust.soict.dsai.aims.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.store.Store;

public class StoreScreen extends JFrame {
    private final Store store;
    private final Cart cart;

    public StoreScreen(Store store, Cart cart) {
        this.store = store;
        this.cart = cart;

        setTitle("AIMS Store");
        setJMenuBar(AimsScreenNavigator.createMenuBar(this, store, cart));
        setLayout(new BorderLayout());
        add(createHeader(), BorderLayout.NORTH);
        add(createCenter(), BorderLayout.CENTER);

        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private Component createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new JLabel("STORE", SwingConstants.LEFT);
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 50));
        title.setForeground(Color.CYAN);
        title.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 10));

        JButton cartButton = new JButton("View cart");
        cartButton.addActionListener(e -> AimsScreenNavigator.openCart(this, store, cart));

        header.add(title, BorderLayout.CENTER);
        header.add(cartButton, BorderLayout.EAST);
        return header;
    }

    private Component createCenter() {
        JPanel center = new JPanel(new GridLayout(0, 3, 8, 8));
        center.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));

        if (store.getItemsInStore().isEmpty()) {
            center.add(new JLabel("No media in store", SwingConstants.CENTER));
        } else {
            for (Media media : store.getItemsInStore()) {
                center.add(new MediaStore(media, cart));
            }
        }

        return new JScrollPane(center);
    }
}
