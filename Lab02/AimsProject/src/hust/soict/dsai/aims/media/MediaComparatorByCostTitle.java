package hust.soict.dsai.aims.media;

import java.util.Comparator;

public class MediaComparatorByCostTitle implements Comparator<Media> {
    @Override
    public int compare(Media m1, Media m2) {
        // So sanh gia tien tu cao xuong thap (Descending)
        int costCompare = Float.compare(m2.getCost(), m1.getCost());
        if (costCompare != 0) {
            return costCompare;
        }
        // Neu gia bang nhau, so sanh tieu de theo bang chu cai (Ascending)
        return m1.getTitle().compareTo(m2.getTitle());
    }
}