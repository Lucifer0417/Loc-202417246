package hust.soict.dsai.aims.screen;

import javax.swing.JTextField;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.DigitalVideoDisc;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.store.Store;

public class AddDigitalVideoDiscToStoreScreen extends AddItemToStoreScreen {
    private final JTextField tfDirector;
    private final JTextField tfLength;

    public AddDigitalVideoDiscToStoreScreen(Store store, Cart cart) {
        super(store, cart, "DVD");
        tfDirector = addField("Đạo diễn");
        tfLength = addField("Thời lượng");
        showScreen();
    }

    @Override
    protected Media createMedia() {
        return new DigitalVideoDisc(
                getTitleInput(),
                getCategoryInput(),
                tfDirector.getText().trim(),
                Integer.parseInt(tfLength.getText().trim()),
                getCostInput());
    }
}
