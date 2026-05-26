package hust.soict.dsai.aims.media;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hust.soict.dsai.aims.exception.PlayerException;

public class CompactDisc extends Disc implements Playable {
    private String artist;
    private ArrayList<Track> tracks = new ArrayList<Track>();

    public CompactDisc(String title, String category, String director, int length, float cost, String artist) {
        super(title, category, director, length, cost);
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public List<Track> getTracks() {
        return Collections.unmodifiableList(tracks);
    }

    public void addTrack(Track track) {
        if (!tracks.contains(track)) {
            tracks.add(track);
            System.out.println("Track " + track.getTitle() + " added.");
        } else {
            System.out.println("Track already exists!");
        }
    }

    public void removeTrack(Track track) {
        if (tracks.contains(track)) {
            tracks.remove(track);
            System.out.println("Track " + track.getTitle() + " removed.");
        } else {
            System.out.println("Track not found!");
        }
    }

    // Tinh tong do dai cua CD bang cach cong do dai cac Track lai
    @Override
    public int getLength() {
        int totalLength = 0;
        for (Track track : tracks) {
            totalLength += track.getLength();
        }
        return totalLength;
    }

    // Phat nhac CD se phat lan luot tung Track
    @Override
    public void play() throws PlayerException {
        if (this.getLength() <= 0) {
            throw new PlayerException("ERROR: CD length is non-positive!");
        }
        System.out.println("Playing CD: " + this.getTitle());
        System.out.println("CD length: " + this.getLength());
        for (Track track : tracks) {
            try {
                track.play();
            } catch (PlayerException e) {
                throw new PlayerException("ERROR: CD contains an invalid track: " + track.getTitle(), e);
            }
        }
    }

    @Override
    public String toString() {
        return "CD - [" + getTitle() + "] - [" + getCategory() + "] - [" + getDirector() + "] - ["
                + getLength() + "] - [" + artist + "]: [" + getCost() + "] $";
    }
}
