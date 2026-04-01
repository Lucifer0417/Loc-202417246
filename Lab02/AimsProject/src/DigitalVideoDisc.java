public class DigitalVideoDisc {
    // 1. Khai bao cac thuoc tinh (Attributes)
    private String title;
    private String category;
    private String director;
    private int length;
    private float cost;

    // 2. Tao cac Constructor (Ham khoi tao)
    // Constructor 1: Tao DVD chi voi tieu de
    public DigitalVideoDisc(String title) {
        super();
        this.title = title;
    }

    // Constructor 2: Tao DVD voi the loai, tieu de va gia tien
    public DigitalVideoDisc(String title, String category, float cost) {
        super();
        this.title = title;
        this.category = category;
        this.cost = cost;
    }

    // Constructor 3: Tao DVD voi tieu de, the loai, dao dien, gia tien
    public DigitalVideoDisc(String title, String category, String director, float cost) {
        super();
        this.title = title;
        this.category = category;
        this.director = director;
        this.cost = cost;
    }

    // Constructor 4: Tao DVD voi day du tat ca thong tin
    public DigitalVideoDisc(String title, String category, String director, int length, float cost) {
        super();
        this.title = title;
        this.category = category;
        this.director = director;
        this.length = length;
        this.cost = cost;
    }

    // 3. Tao cac ham Getters de lay thong tin
    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDirector() {
        return director;
    }

    public int getLength() {
        return length;
    }

    public float getCost() {
        return cost;
    }
}