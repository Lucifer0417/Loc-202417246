package hust.soict.dsai.aims.media;

import java.util.Comparator; // Them thu vien nay de dung Comparator
import java.util.Objects;

public abstract class Media {
    private static int nbMedia = 0;

    private int id = ++nbMedia;
    private String title;
    private String category;
    private float cost;
    
    // Them 2 bo so sanh vao day de phuc vu cho viec sap xep Gio hang
    public static final Comparator<Media> COMPARE_BY_TITLE_COST = new MediaComparatorByTitleCost();
    public static final Comparator<Media> COMPARE_BY_COST_TITLE = new MediaComparatorByCostTitle();

    // Constructors
    public Media() {
    }

    public Media(String title) {
        this.title = title;
    }

    public Media(String title, String category, float cost) {
        this.title = title;
        this.category = category;
        this.cost = cost;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
    
    // Ghi de ham equals de so sanh Media theo tieu de
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true; // Cung mot vi tri bo nho
        }
        if (!(obj instanceof Media)) {
            return false; // Khong phai la Media hoac la null
        }
        Media media = (Media) obj; // Ep kieu Object obj ve Media
        return Objects.equals(this.title, media.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
