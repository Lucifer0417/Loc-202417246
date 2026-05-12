package hust.soict.dsai.aims.media;

import java.util.Comparator;

public class MediaComparatorByTitleCost implements Comparator<Media> {
    @Override
    public int compare(Media m1, Media m2) {
        // So sanh tieu de theo bang chu cai (Ascending)
        int titleCompare = m1.getTitle().compareTo(m2.getTitle());
        if (titleCompare != 0) {
            return titleCompare;
        }
        // Neu tieu de giong nhau, so sanh gia tien tu cao xuong thap (Descending)
        return Float.compare(m2.getCost(), m1.getCost());
    }
}