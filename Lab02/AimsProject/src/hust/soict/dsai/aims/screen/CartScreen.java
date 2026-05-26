package hust.soict.dsai.aims.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.Playable;
import hust.soict.dsai.aims.store.Store;

public class CartScreen extends JFrame {
    private final Store store;
    private final Cart cart;
    private final CartTableModel tableModel;
    private final TableRowSorter<CartTableModel> sorter;

    private JTable tblMedia;
    private JButton btnPlay;
    private JButton btnRemove;
    private JLabel totalLabel;
    private JTextField tfFilter;
    private JRadioButton radioBtnFilterId;
    private JRadioButton radioBtnFilterTitle;

    public CartScreen(Store store, Cart cart) {
        this.store = store;
        this.cart = cart;
        this.tableModel = new CartTableModel(cart);
        this.sorter = new TableRowSorter<CartTableModel>(tableModel);

        setTitle("AIMS Cart");
        setJMenuBar(AimsScreenNavigator.createMenuBar(this, store, cart));
        setLayout(new BorderLayout());
        add(createHeader(), BorderLayout.NORTH);
        add(createCenter(), BorderLayout.CENTER);
        add(createRight(), BorderLayout.EAST);

        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        updateButtonBar();
        updateTotal();
    }

    private Component createHeader() {
        JLabel title = new JLabel("CART", SwingConstants.LEFT);
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 50));
        title.setForeground(Color.CYAN);
        title.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        return title;
    }

    private Component createCenter() {
        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Filter:"));
        tfFilter = new JTextField(24);
        filterPanel.add(tfFilter);

        radioBtnFilterId = new JRadioButton("By ID", true);
        radioBtnFilterTitle = new JRadioButton("By Title");
        ButtonGroup filterGroup = new ButtonGroup();
        filterGroup.add(radioBtnFilterId);
        filterGroup.add(radioBtnFilterTitle);
        filterPanel.add(radioBtnFilterId);
        filterPanel.add(radioBtnFilterTitle);

        tfFilter.getDocument().addDocumentListener(new SimpleDocumentListener(this::applyFilter));
        radioBtnFilterId.addActionListener(e -> applyFilter());
        radioBtnFilterTitle.addActionListener(e -> applyFilter());

        tblMedia = new JTable(tableModel);
        tblMedia.setRowSorter(sorter);
        tblMedia.setFillsViewportHeight(true);
        tblMedia.getSelectionModel().addListSelectionListener(this::selectionChanged);

        JPanel buttonBar = new JPanel();
        btnPlay = new JButton("Play");
        btnPlay.addActionListener(e -> playSelectedMedia());
        btnRemove = new JButton("Remove");
        btnRemove.addActionListener(e -> removeSelectedMedia());
        buttonBar.add(btnPlay);
        buttonBar.add(btnRemove);

        center.add(filterPanel, BorderLayout.NORTH);
        center.add(new JScrollPane(tblMedia), BorderLayout.CENTER);
        center.add(buttonBar, BorderLayout.SOUTH);
        return center;
    }

    private Component createRight() {
        JPanel right = new JPanel(new GridLayout(2, 1, 8, 8));
        right.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 20));

        totalLabel = new JLabel("", SwingConstants.CENTER);
        totalLabel.setFont(new Font(totalLabel.getFont().getName(), Font.PLAIN, 24));
        totalLabel.setForeground(Color.CYAN);

        JButton placeOrder = new JButton("Place Order");
        placeOrder.setFont(new Font(placeOrder.getFont().getName(), Font.PLAIN, 20));
        placeOrder.addActionListener(e -> placeOrder());

        right.add(totalLabel);
        right.add(placeOrder);
        return right;
    }

    private void selectionChanged(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) {
            updateButtonBar();
        }
    }

    private void updateButtonBar() {
        Media selected = getSelectedMedia();
        boolean hasSelection = selected != null;
        btnRemove.setVisible(hasSelection);
        btnPlay.setVisible(hasSelection && selected instanceof Playable);
        btnRemove.getParent().revalidate();
        btnRemove.getParent().repaint();
    }

    private void applyFilter() {
        String filter = tfFilter.getText().trim().toLowerCase();
        if (filter.isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }

        sorter.setRowFilter(new RowFilter<CartTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends CartTableModel, ? extends Integer> entry) {
                Media media = tableModel.getMediaAt(entry.getIdentifier());
                if (radioBtnFilterId.isSelected()) {
                    return Integer.toString(media.getId()).contains(filter);
                }
                return media.getTitle() != null && media.getTitle().toLowerCase().contains(filter);
            }
        });
    }

    private Media getSelectedMedia() {
        int selectedRow = tblMedia.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        int modelRow = tblMedia.convertRowIndexToModel(selectedRow);
        return tableModel.getMediaAt(modelRow);
    }

    private void playSelectedMedia() {
        Media selected = getSelectedMedia();
        if (selected != null) {
            AimsScreenNavigator.playMedia(this, selected);
        }
    }

    private void removeSelectedMedia() {
        Media selected = getSelectedMedia();
        if (selected != null) {
            cart.removeMedia(selected);
            refreshCart();
        }
    }

    private void placeOrder() {
        if (cart.getItemsOrdered().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Your cart is empty.");
            return;
        }
        cart.empty();
        refreshCart();
        javax.swing.JOptionPane.showMessageDialog(this, "An order has been created successfully.");
    }

    private void refreshCart() {
        tableModel.fireTableDataChanged();
        applyFilter();
        updateTotal();
        updateButtonBar();
    }

    private void updateTotal() {
        totalLabel.setText(String.format("Total: %.2f $", cart.totalCost()));
    }

    private static class CartTableModel extends AbstractTableModel {
        private static final String[] COLUMNS = {"Title", "Category", "Cost"};
        private final Cart cart;

        CartTableModel(Cart cart) {
            this.cart = cart;
        }

        @Override
        public int getRowCount() {
            return cart.getItemsOrdered().size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 2) {
                return Float.class;
            }
            return String.class;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Media media = getMediaAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return media.getTitle();
                case 1:
                    return media.getCategory();
                case 2:
                    return media.getCost();
                default:
                    return "";
            }
        }

        Media getMediaAt(int rowIndex) {
            return cart.getItemsOrdered().get(rowIndex);
        }
    }
}
