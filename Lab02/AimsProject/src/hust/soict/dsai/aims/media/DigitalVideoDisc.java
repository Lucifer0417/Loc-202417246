package hust.soict.dsai.aims.media;

public class DigitalVideoDisc extends  Disc implements Playable {

    // Khong can khai bao cac bien id, title, category, cost, director, length nua
    // Vi chung ta da ke thua (extends) tat ca tu lop Disc va Media roi!

    public DigitalVideoDisc(String title) {
        super(title);
    }

    public DigitalVideoDisc(String title, String category, float cost) {
        super(title, category, cost);
    }

    public DigitalVideoDisc(String title, String category, String director, float cost) {
        super(title, category, director, cost);
    }

    public DigitalVideoDisc(String title, String category, String director, int length, float cost) {
        super(title, category, director, length, cost);
    }

    // Ghi de lai ham toString de in ra thong tin
    // Chu y: Phai dung cac ham getTitle(), getCategory()... vi cac bien nay la private o lop cha
    @Override
    public String toString() {
        return "DVD - [" + getTitle() + "] - [" + getCategory() + "] - [" + getDirector() + "] - [" + getLength() + "]: [" + getCost() + "] $";
    }
    
    public boolean isMatch(String title) {
        return getTitle().toLowerCase().contains(title.toLowerCase());
    }
    @Override
    public void play() {
        System.out.println("Playing DVD: " + this.getTitle());
        System.out.println("DVD length: " + this.getLength());
    }
}