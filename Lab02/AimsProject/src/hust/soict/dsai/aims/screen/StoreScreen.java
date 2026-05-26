package hust.soict.dsai.aims.screen;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
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

    private JPanel mediaGrid;
    private JLabel resultLabel;
    private JLabel cartSummaryLabel;
    private JTextField searchField;
    private JComboBox<String> typeFilter;
    private JComboBox<String> sortBox;

    public StoreScreen(Store store, Cart cart) {
        this.store = store;
        this.cart = cart;

        setTitle("AIMS Store");
        setJMenuBar(AimsScreenNavigator.createMenuBar(this, store, cart));
        setLayout(new BorderLayout());
        getContentPane().setBackground(AimsUi.BACKGROUND);
        add(createHeader(), BorderLayout.NORTH);
        add(createCenter(), BorderLayout.CENTER);

        setSize(1080, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        refreshStore();
        setVisible(true);
    }

    private Component createHeader() {
        JPanel header = new JPanel(new BorderLayout(16, 8));
        header.setBackground(AimsUi.BACKGROUND);
        header.setBorder(AimsUi.paddedBorder(18, 24, 10, 24));

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        titlePanel.add(AimsUi.screenTitle("AIMS Store"));
        resultLabel = AimsUi.muted("");
        titlePanel.add(resultLabel);

        cartSummaryLabel = AimsUi.sectionTitle("");
        cartSummaryLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        header.add(titlePanel, BorderLayout.CENTER);
        header.add(cartSummaryLabel, BorderLayout.EAST);
        return header;
    }

    private Component createCenter() {
        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setBackground(AimsUi.BACKGROUND);
        content.setBorder(AimsUi.paddedBorder(0, 24, 24, 24));
        content.add(createToolbar(), BorderLayout.NORTH);

        mediaGrid = new JPanel(new GridLayout(0, 3, 12, 12));
        mediaGrid.setBackground(AimsUi.BACKGROUND);

        JScrollPane scrollPane = new JScrollPane(mediaGrid);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(AimsUi.BACKGROUND);
        content.add(scrollPane, BorderLayout.CENTER);
        return content;
    }

    private Component createToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        toolbar.setBackground(AimsUi.SURFACE);
        toolbar.setBorder(AimsUi.cardBorder());

        searchField = new JTextField(24);
        searchField.getDocument().addDocumentListener(new SimpleDocumentListener(this::refreshStore));

        typeFilter = new JComboBox<String>(new String[] {"All types", "Book", "CD", "DVD"});
        typeFilter.addActionListener(e -> refreshStore());

        sortBox = new JComboBox<String>(new String[] {"Title A-Z", "Price low-high", "Price high-low"});
        sortBox.addActionListener(e -> refreshStore());

        toolbar.add(new JLabel("Search"));
        toolbar.add(searchField);
        toolbar.add(new JLabel("Type"));
        toolbar.add(typeFilter);
        toolbar.add(new JLabel("Sort"));
        toolbar.add(sortBox);
        return toolbar;
    }

    private void refreshStore() {
        if (mediaGrid == null) {
            return;
        }

        List<Media> visibleMedia = filterAndSortMedia();
        mediaGrid.removeAll();

        if (visibleMedia.isEmpty()) {
            JLabel empty = AimsUi.muted("No media matches the current filter.");
            empty.setHorizontalAlignment(SwingConstants.CENTER);
            mediaGrid.add(empty);
        } else {
            for (Media media : visibleMedia) {
                mediaGrid.add(new MediaStore(media, cart, this::refreshCartSummary));
            }
        }

        resultLabel.setText(visibleMedia.size() + " item(s) shown from " + store.getItemsInStore().size());
        refreshCartSummary();
        mediaGrid.revalidate();
        mediaGrid.repaint();
    }

    private List<Media> filterAndSortMedia() {
        String query = searchField == null ? "" : searchField.getText().trim().toLowerCase();
        String selectedType = typeFilter == null ? "All types" : (String) typeFilter.getSelectedItem();
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

        if ("Price low-high".equals(selectedSort)) {
            result.sort(Comparator.comparing(Media::getCost).thenComparing(Media::getTitle,
                    Comparator.nullsLast(String::compareToIgnoreCase)));
        } else if ("Price high-low".equals(selectedSort)) {
            result.sort(Comparator.comparing(Media::getCost).reversed().thenComparing(Media::getTitle,
                    Comparator.nullsLast(String::compareToIgnoreCase)));
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

    private void refreshCartSummary() {
        if (cartSummaryLabel != null) {
            cartSummaryLabel.setText(String.format("Cart: %d item(s) | %.2f $",
                    cart.getItemsOrdered().size(), cart.totalCost()));
        }
    }
}
