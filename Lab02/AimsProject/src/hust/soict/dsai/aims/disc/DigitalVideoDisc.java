package hust.soict.dsai.aims.disc;
public class DigitalVideoDisc {
    // Thuoc tinh cua lop (Class member) - dung chung cho tat ca cac dia de dem so luong
    private static int nbDigitalVideoDiscs = 0;
    
    // Thuoc tinh cua doi tuong (Instance members)
    private int id; // ID tu dong tang
    private String title;
    private String category;
    private String director;
    private int length;
    private float cost;

    // --- CAC HAM KHOI TAO (CONSTRUCTORS) ---
    // Moi khi 1 DVD duoc tao ra, bien static se tang them 1, va gan vao id cua DVD do
    
    public DigitalVideoDisc(String title) {
        super();
        this.title = title;
        nbDigitalVideoDiscs++;
        this.id = nbDigitalVideoDiscs;
    }

    public DigitalVideoDisc(String title, String category, float cost) {
        super();
        this.title = title;
        this.category = category;
        this.cost = cost;
        nbDigitalVideoDiscs++;
        this.id = nbDigitalVideoDiscs;
    }

    public DigitalVideoDisc(String title, String category, String director, float cost) {
        super();
        this.title = title;
        this.category = category;
        this.director = director;
        this.cost = cost;
        nbDigitalVideoDiscs++;
        this.id = nbDigitalVideoDiscs;
    }

    public DigitalVideoDisc(String title, String category, String director, int length, float cost) {
        super();
        this.title = title;
        this.category = category;
        this.director = director;
        this.length = length;
        this.cost = cost;
        nbDigitalVideoDiscs++;
        this.id = nbDigitalVideoDiscs;
    }

    // --- GETTERS ---
    public int getId() {
        return id;
    }

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
    
    // --- HAM HO TRO CHO LAB 03 ---
    
    // Ham toString() de in thong tin dia ra man hinh cho dep
    @Override
    public String toString() {
        return "DVD - [" + title + "] - [" + category + "] - [" + director + "] - [" + length + "]: [" + cost + "] $";
    }
    
    // Ham isMatch() de kiem tra xem tieu de dia co chua tu khoa tim kiem khong (khong phan biet hoa thuong)
    public boolean isMatch(String title) {
        return this.title.toLowerCase().contains(title.toLowerCase());
    }
}