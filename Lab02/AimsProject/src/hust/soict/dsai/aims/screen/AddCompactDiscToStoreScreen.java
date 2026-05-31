package hust.soict.dsai.aims.screen;

import javax.swing.JTextField;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.CompactDisc;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.Track;
import hust.soict.dsai.aims.store.Store;

public class AddCompactDiscToStoreScreen extends AddItemToStoreScreen {
    private final JTextField tfDirector;
    private final JTextField tfLength;
    private final JTextField tfArtist;
    private final JTextField tfTracks;

    public AddCompactDiscToStoreScreen(Store store, Cart cart) {
        super(store, cart, "CD");
        tfDirector = addField("Director");
        tfLength = addField("Default length");
        tfArtist = addField("Artist");
        tfTracks = addField("Tracks (title:length; title:length)");
        showScreen();
    }

    @Override
    protected Media createMedia() {
        int defaultLength = Integer.parseInt(tfLength.getText().trim());
        CompactDisc cd = new CompactDisc(
                getTitleInput(),
                getCategoryInput(),
                tfDirector.getText().trim(),
                defaultLength,
                getCostInput(),
                tfArtist.getText().trim());

        String tracks = tfTracks.getText().trim();
        if (tracks.isEmpty()) {
            cd.addTrack(new Track(getTitleInput(), defaultLength));
        } else {
            for (String trackSpec : tracks.split(";")) {
                addTrack(cd, trackSpec.trim(), defaultLength);
            }
        }
        return cd;
    }

    private void addTrack(CompactDisc cd, String trackSpec, int defaultLength) {
        if (trackSpec.isEmpty()) {
            return;
        }

        int separator = trackSpec.lastIndexOf(':');
        if (separator < 0) {
            cd.addTrack(new Track(trackSpec, defaultLength));
            return;
        }

        String title = trackSpec.substring(0, separator).trim();
        int length = Integer.parseInt(trackSpec.substring(separator + 1).trim());
        cd.addTrack(new Track(title, length));
    }
}
