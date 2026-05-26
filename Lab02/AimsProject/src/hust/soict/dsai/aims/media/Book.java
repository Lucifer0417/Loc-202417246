package hust.soict.dsai.aims.media;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Book extends Media {
    private List<String> authors = new ArrayList<String>();

    public Book() {
        super();
    }

    public Book(String title, String category, float cost) {
        super(title, category, cost);
    }

    public List<String> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    // Them tac gia (kiem tra xem da ton tai chua)
    public void addAuthor(String authorName) {
        if (!authors.contains(authorName)) {
            authors.add(authorName);
            System.out.println("Author " + authorName + " has been added.");
        } else {
            System.out.println("Author " + authorName + " already exists!");
        }
    }

    // Xoa tac gia
    public void removeAuthor(String authorName) {
        if (authors.contains(authorName)) {
            authors.remove(authorName);
            System.out.println("Author " + authorName + " has been removed.");
        } else {
            System.out.println("Author " + authorName + " not found!");
        }
    }

    @Override
    public String toString() {
        return "Book - [" + getTitle() + "] - [" + getCategory() + "] - " + authors + ": ["
                + getCost() + "] $";
    }
}
