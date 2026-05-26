package hust.soict.dsai.aims;

import javax.swing.SwingUtilities;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.Book;
import hust.soict.dsai.aims.media.CompactDisc;
import hust.soict.dsai.aims.media.DigitalVideoDisc;
import hust.soict.dsai.aims.media.Track;
import hust.soict.dsai.aims.screen.StoreScreen;
import hust.soict.dsai.aims.store.Store;

public class Aims {
    private static final Store store = new Store();
    private static final Cart cart = new Cart();

    public static void main(String[] args) {
        loadSampleData();
        SwingUtilities.invokeLater(() -> new StoreScreen(store, cart));
    }

    private static void loadSampleData() {
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("The Lion King", "Animation", "Roger Allers", 87, 19.95f);
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Star Wars", "Science Fiction", "George Lucas", 87, 24.95f);
        DigitalVideoDisc dvd3 = new DigitalVideoDisc("Aladin", "Animation", "Ron Clements", 90, 18.99f);

        Book book = new Book("Effective Java", "Programming", 45.50f);
        book.addAuthor("Joshua Bloch");

        CompactDisc cd = new CompactDisc("Greatest Hits", "Music", "Various", 0, 14.99f, "Various Artists");
        cd.addTrack(new Track("Opening", 4));
        cd.addTrack(new Track("Main Theme", 5));
        cd.addTrack(new Track("Finale", 3));

        store.addMedia(dvd1);
        store.addMedia(dvd2);
        store.addMedia(dvd3);
        store.addMedia(book);
        store.addMedia(cd);

        cart.addMedia(dvd1);
        cart.addMedia(book);
    }
}
