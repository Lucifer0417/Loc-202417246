package hust.soict.dsai.aims.screen;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.exception.PlayerException;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.Playable;
import hust.soict.dsai.aims.store.Store;

final class AimsScreenNavigator {
    private AimsScreenNavigator() {
    }

    static JMenuBar createMenuBar(JFrame owner, Store store, Cart cart) {
        JMenuBar menuBar = new JMenuBar();

        JMenu options = new JMenu("Tùy chọn");
        JMenuItem viewStore = new JMenuItem("Xem cửa hàng");
        viewStore.addActionListener(e -> openStore(owner, store, cart));

        JMenuItem viewCart = new JMenuItem("Xem giỏ hàng");
        viewCart.addActionListener(e -> openCart(owner, store, cart));

        JMenu updateStore = new JMenu("Cập nhật cửa hàng");
        JMenuItem addBook = new JMenuItem("Thêm sách");
        addBook.addActionListener(e -> openAddBook(owner, store, cart));
        JMenuItem addCd = new JMenuItem("Thêm CD");
        addCd.addActionListener(e -> openAddCd(owner, store, cart));
        JMenuItem addDvd = new JMenuItem("Thêm DVD");
        addDvd.addActionListener(e -> openAddDvd(owner, store, cart));

        updateStore.add(addBook);
        updateStore.add(addCd);
        updateStore.add(addDvd);

        options.add(viewStore);
        options.add(viewCart);
        options.add(updateStore);
        menuBar.add(options);
        return menuBar;
    }

    static void openStore(JFrame owner, Store store, Cart cart) {
        switchTo(owner, () -> new StoreScreen(store, cart));
    }

    static void openCart(JFrame owner, Store store, Cart cart) {
        switchTo(owner, () -> new CartScreen(store, cart));
    }

    private static void openAddBook(JFrame owner, Store store, Cart cart) {
        switchTo(owner, () -> new AddBookToStoreScreen(store, cart));
    }

    private static void openAddCd(JFrame owner, Store store, Cart cart) {
        switchTo(owner, () -> new AddCompactDiscToStoreScreen(store, cart));
    }

    private static void openAddDvd(JFrame owner, Store store, Cart cart) {
        switchTo(owner, () -> new AddDigitalVideoDiscToStoreScreen(store, cart));
    }

    private static void switchTo(JFrame owner, Runnable nextScreen) {
        SwingUtilities.invokeLater(() -> {
            if (owner != null) {
                owner.dispose();
            }
            nextScreen.run();
        });
    }

    static void playMedia(JFrame owner, Media media) {
        SwingUtilities.invokeLater(() -> {
            if (!(media instanceof Playable)) {
                JOptionPane.showMessageDialog(owner, "Sản phẩm này không thể phát.");
                return;
            }

            try {
                ((Playable) media).play();
                JOptionPane.showMessageDialog(owner, "Đang phát: " + media.getTitle());
            } catch (PlayerException e) {
                JOptionPane.showMessageDialog(owner, e.getMessage(), "Lỗi phát media", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
