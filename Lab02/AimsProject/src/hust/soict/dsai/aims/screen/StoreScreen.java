package hust.soict.dsai.aims.screen;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.Book;
import hust.soict.dsai.aims.media.CompactDisc;
import hust.soict.dsai.aims.media.DigitalVideoDisc;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.store.Store;

public class StoreScreen extends JFrame {
    private final Store store;
    private final Cart cart;

    private JPanel mediaPanel;
    private JTextField searchField;
    private JComboBox<String> typeFilter;
    private JComboBox<String> sortBox;
    private JLabel cartSummary;

    public StoreScreen(Store store, Cart cart) {
        this.store = store;
        this.cart = cart;

        setTitle("AIMS Store");
        setJMenuBar(AimsScreenNavigator.createMenuBar(this, store, cart));
        setLayout(new BorderLayout(0, 12));
        getContentPane().setBackground(AimsUi.BACKGROUND);

        add(createNorth(), BorderLayout.NORTH);
        add(createCenter(), BorderLayout.CENTER);

        refreshStore();
        updateCartSummary();

        setSize(1080, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createNorth() {
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.setBackground(AimsUi.BACKGROUND);
        north.setBorder(AimsUi.paddedBorder(18, 24, 0, 24));
        north.add(createHeader());
        north.add(createToolbar());
        return north;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = AimsUi.screenTitle("AIMS Store");
        header.add(title, BorderLayout.WEST);

        cartSummary = AimsUi.muted("");
        cartSummary.setHorizontalAlignment(SwingConstants.RIGHT);
        header.add(cartSummary, BorderLayout.EAST);
        return header;
    }

    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        toolbar.setOpaque(false);

        searchField = new JTextField(28);
        searchField.getDocument().addDocumentListener(new SimpleDocumentListener(this::refreshStore));

        typeFilter = new JComboBox<String>(new String[] { "All", "Book", "CD", "DVD" });
        typeFilter.addActionListener(e -> refreshStore());

        sortBox = new JComboBox<String>(new String[] { "Title A-Z", "Cost low to high", "Cost high to low" });
        sortBox.addActionListener(e -> refreshStore());

        toolbar.add(new JLabel("Filter"));
        toolbar.add(searchField);
        toolbar.add(new JLabel("Type"));
        toolbar.add(typeFilter);
        toolbar.add(new JLabel("Sort"));
        toolbar.add(sortBox);
        return toolbar;
    }

    private JScrollPane createCenter() {
        mediaPanel = new JPanel(new GridLayout(0, 3, 12, 12));
        mediaPanel.setBackground(AimsUi.BACKGROUND);
        mediaPanel.setBorder(AimsUi.paddedBorder(0, 24, 24, 24));

        JScrollPane scrollPane = new JScrollPane(mediaPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(AimsUi.BACKGROUND);
        return scrollPane;
    }

    private void refreshStore() {
        if (mediaPanel == null) {
            return;
        }

        mediaPanel.removeAll();
        List<Media> visibleMedia = filterAndSortMedia();

        if (visibleMedia.isEmpty()) {
            JLabel empty = AimsUi.muted("No media matches the current filter.");
            empty.setHorizontalAlignment(SwingConstants.CENTER);
            mediaPanel.add(empty);
        } else {
            for (Media media : visibleMedia) {
                mediaPanel.add(new MediaStore(media, cart, this::updateCartSummary));
            }
        }

        mediaPanel.revalidate();
        mediaPanel.repaint();
    }

    private List<Media> filterAndSortMedia() {
        String query = searchField == null ? "" : searchField.getText().trim().toLowerCase();
        String selectedType = typeFilter == null ? "All" : (String) typeFilter.getSelectedItem();
        String selectedSort = sortBox == null ? "Title A-Z" : (String) sortBox.getSelectedItem();

        List<Media> result = new ArrayList<Media>();
        for (Media media : store.getItemsInStore()) {
            if (!matchesType(media, selectedType)) {
                continue;
            }
            if (!matchesQuery(media, query)) {
                continue;
            }
            result.add(media);
        }

        if ("Cost low to high".equals(selectedSort)) {
            result.sort(Comparator.comparing(Media::getCost)
                    .thenComparing(Media::getTitle, Comparator.nullsLast(String::compareToIgnoreCase)));
        } else if ("Cost high to low".equals(selectedSort)) {
            result.sort(Comparator.comparing(Media::getCost).reversed()
                    .thenComparing(Media::getTitle, Comparator.nullsLast(String::compareToIgnoreCase)));
        } else {
            result.sort(Comparator.comparing(Media::getTitle, Comparator.nullsLast(String::compareToIgnoreCase)));
        }
        return result;
    }

    private boolean matchesType(Media media, String selectedType) {
        if ("Book".equals(selectedType)) {
            return media instanceof Book;
        }
        if ("CD".equals(selectedType)) {
            return media instanceof CompactDisc;
        }
        if ("DVD".equals(selectedType)) {
            return media instanceof DigitalVideoDisc;
        }
        return true;
    }

    private boolean matchesQuery(Media media, String query) {
        if (query.isEmpty()) {
            return true;
        }

        String title = media.getTitle() == null ? "" : media.getTitle().toLowerCase();
        String category = media.getCategory() == null ? "" : media.getCategory().toLowerCase();
        String type = media.getClass().getSimpleName().toLowerCase();
        return title.contains(query) || category.contains(query) || type.contains(query);
    }

    private void updateCartSummary() {
        if (cartSummary == null) {
            return;
        }
        cartSummary.setText(String.format("Cart: %d item(s) | %.2f $", cart.getItemsOrdered().size(), cart.totalCost()));
    }
}
