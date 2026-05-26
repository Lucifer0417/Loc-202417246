package hust.soict.dsai.aims.screen;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.store.Store;

public abstract class AddItemToStoreScreen extends JFrame {
    protected final Store store;
    protected final Cart cart;
    protected final JPanel formPanel = new JPanel(new GridBagLayout());

    protected final JTextField tfTitle;
    protected final JTextField tfCategory;
    protected final JTextField tfCost;

    private int row = 0;

    protected AddItemToStoreScreen(Store store, Cart cart, String itemName) {
        this.store = store;
        this.cart = cart;

        setTitle("Thêm " + itemName);
        setJMenuBar(AimsScreenNavigator.createMenuBar(this, store, cart));
        setLayout(new BorderLayout());

        formPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        tfTitle = addField("Tiêu đề");
        tfCategory = addField("Thể loại");
        tfCost = addField("Giá");
        add(formPanel, BorderLayout.CENTER);

        JButton submit = new JButton("Thêm " + itemName);
        submit.addActionListener(e -> submit());
        add(submit, BorderLayout.SOUTH);

        setSize(520, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    protected JTextField addField(String label) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.insets = new Insets(6, 6, 6, 6);
        labelConstraints.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel(label + ":"), labelConstraints);

        JTextField field = new JTextField(28);
        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = row;
        fieldConstraints.insets = new Insets(6, 6, 6, 6);
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.weightx = 1.0;
        formPanel.add(field, fieldConstraints);

        row++;
        return field;
    }

    protected String getTitleInput() {
        return tfTitle.getText().trim();
    }

    protected String getCategoryInput() {
        return tfCategory.getText().trim();
    }

    protected float getCostInput() {
        return Float.parseFloat(tfCost.getText().trim());
    }

    protected void showScreen() {
        setVisible(true);
    }

    private void submit() {
        try {
            Media media = createMedia();
            store.addMedia(media);
            JOptionPane.showMessageDialog(this, "Đã thêm " + media.getTitle() + " vào cửa hàng.");
            AimsScreenNavigator.openStore(this, store, cart);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng các giá trị số.", "Dữ liệu không hợp lệ",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected abstract Media createMedia();
}
