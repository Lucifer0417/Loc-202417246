package hust.soict.dsai.aims.media;

import java.util.Objects;

import hust.soict.dsai.aims.exception.PlayerException;

public class Track implements Playable {
    private String title;
    private int length;

    public Track(String title, int length) {
        this.title = title;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    @Override
    public void play() throws PlayerException {
        if (this.getLength() <= 0) {
            throw new PlayerException("ERROR: Track length is non-positive!");
        }
        System.out.println("Playing track: " + this.getTitle());
        System.out.println("Track length: " + this.getLength());
    }
 // Ghi de ham equals de so sanh Track theo tieu de va do dai
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Track)) {
            return false;
        }
        Track track = (Track) obj; // Ep kieu Object obj ve Track
        return this.title != null && this.title.equals(track.getTitle()) && this.length == track.getLength();
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, length);
    }
}
