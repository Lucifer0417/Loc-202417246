package hust.soict.dsai.aims.screen;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.Book;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.store.Store;

public class AddBookToStoreScreen extends AddItemToStoreScreen {
    private final javax.swing.JTextField tfAuthors;

    public AddBookToStoreScreen(Store store, Cart cart) {
        super(store, cart, "sách");
        tfAuthors = addField("Tác giả (cách nhau bằng dấu phẩy)");
        showScreen();
    }

    @Override
    protected Media createMedia() {
        Book book = new Book(getTitleInput(), getCategoryInput(), getCostInput());
        String authors = tfAuthors.getText().trim();
        if (!authors.isEmpty()) {
            for (String author : authors.split(",")) {
                String normalized = author.trim();
                if (!normalized.isEmpty()) {
                    book.addAuthor(normalized);
                }
            }
        }
        return book;
    }
}
