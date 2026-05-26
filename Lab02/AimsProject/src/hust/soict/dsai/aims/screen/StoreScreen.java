package hust.soict.dsai.aims.screen;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.Book;
import hust.soict.dsai.aims.media.CompactDisc;
import hust.soict.dsai.aims.media.DigitalVideoDisc;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.Playable;
import hust.soict.dsai.aims.store.Store;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class StoreScreen extends JFrame {
    private static final String STYLE_BACKGROUND = "-fx-background-color: #f5f7fa;";
    private static final String STYLE_CARD = "-fx-background-color: white; -fx-border-color: #dee2e6;"
            + " -fx-border-radius: 8; -fx-background-radius: 8;";
    private static final String STYLE_PRIMARY_BUTTON = "-fx-background-color: #0d6efd; -fx-text-fill: white;"
            + " -fx-font-weight: bold; -fx-background-radius: 6;";
    private static final String STYLE_SECONDARY_BUTTON = "-fx-background-color: #495057; -fx-text-fill: white;"
            + " -fx-font-weight: bold; -fx-background-radius: 6;";
    private static final String STYLE_PURPLE_BUTTON = "-fx-background-color: #6610f2; -fx-text-fill: white;"
            + " -fx-font-weight: bold; -fx-background-radius: 6;";

    private final Store store;
    private final Cart cart;
    private final JFXPanel jfxPanel = new JFXPanel();

    private TilePane mediaGrid;
    private Label resultLabel;
    private Label cartSummaryLabel;
    private TextField searchField;
    private ComboBox<String> typeFilter;
    private ComboBox<String> sortBox;

    public StoreScreen(Store store, Cart cart) {
        this.store = store;
        this.cart = cart;

        setTitle("AIMS Store");
        setJMenuBar(AimsScreenNavigator.createMenuBar(this, store, cart));
        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);

        setSize(1080, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Platform.runLater(() -> {
            Platform.setImplicitExit(false);
            jfxPanel.setScene(new Scene(createRoot(), 1080, 720));
        });
    }

    private BorderPane createRoot() {
        BorderPane root = new BorderPane();
        root.setStyle(STYLE_BACKGROUND);
        root.setPadding(new Insets(18, 24, 24, 24));
        root.setTop(createHeader());
        root.setCenter(createCenter());
        refreshStore();
        return root;
    }

    private VBox createHeader() {
        VBox header = new VBox(4);
        header.setPadding(new Insets(0, 0, 14, 0));

        HBox topRow = new HBox(12);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("AIMS Store");
        title.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 34; -fx-font-weight: bold;"
                + " -fx-text-fill: #212529;");

        cartSummaryLabel = new Label();
        cartSummaryLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #198754;");

        Button cartButton = primaryButton("View cart");
        cartButton.setOnAction(event -> AimsScreenNavigator.openCart(this, store, cart));

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topRow.getChildren().addAll(title, spacer, cartSummaryLabel, cartButton);

        resultLabel = new Label();
        resultLabel.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 13;");

        header.getChildren().addAll(topRow, resultLabel);
        return header;
    }

    private VBox createCenter() {
        VBox center = new VBox(12);
        center.getChildren().add(createToolbar());

        mediaGrid = new TilePane();
        mediaGrid.setHgap(12);
        mediaGrid.setVgap(12);
        mediaGrid.setPrefColumns(3);
        mediaGrid.setPadding(new Insets(2));
        mediaGrid.setStyle(STYLE_BACKGROUND);

        ScrollPane scrollPane = new ScrollPane(mediaGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        center.getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        return center;
    }

    private HBox createToolbar() {
        HBox toolbar = new HBox(10);
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setPadding(new Insets(12));
        toolbar.setStyle(STYLE_CARD);

        searchField = new TextField();
        searchField.setPromptText("Search title, category, or type...");
        searchField.setPrefColumnCount(28);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> refreshStore());

        typeFilter = new ComboBox<String>();
        typeFilter.getItems().addAll("All types", "Book", "CD", "DVD");
        typeFilter.getSelectionModel().select("All types");
        typeFilter.valueProperty().addListener((observable, oldValue, newValue) -> refreshStore());

        sortBox = new ComboBox<String>();
        sortBox.getItems().addAll("Title A-Z", "Price low-high", "Price high-low");
        sortBox.getSelectionModel().select("Title A-Z");
        sortBox.valueProperty().addListener((observable, oldValue, newValue) -> refreshStore());

        toolbar.getChildren().addAll(new Label("Search"), searchField, new Label("Type"), typeFilter,
                new Label("Sort"), sortBox);
        return toolbar;
    }

    private void refreshStore() {
        if (mediaGrid == null) {
            return;
        }

        List<Media> visibleMedia = filterAndSortMedia();
        mediaGrid.getChildren().clear();

        if (visibleMedia.isEmpty()) {
            Label empty = new Label("No media matches the current filter.");
            empty.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 14;");
            mediaGrid.getChildren().add(empty);
        } else {
            for (Media media : visibleMedia) {
                mediaGrid.getChildren().add(createMediaCard(media));
            }
        }

        resultLabel.setText(visibleMedia.size() + " item(s) shown from " + store.getItemsInStore().size());
        refreshCartSummary();
    }

    private VBox createMediaCard(Media media) {
        VBox card = new VBox(10);
        card.setPrefSize(310, 190);
        card.setPadding(new Insets(16));
        card.setStyle(STYLE_CARD);

        Label type = new Label(media.getClass().getSimpleName());
        type.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 12;");

        Label title = new Label(media.getTitle());
        title.setWrapText(true);
        title.setMaxWidth(270);
        title.setStyle("-fx-text-fill: #212529; -fx-font-size: 17; -fx-font-weight: bold;");

        Label category = new Label(media.getCategory() == null ? "No category" : media.getCategory());
        category.setStyle("-fx-text-fill: #6c757d;");

        Label cost = new Label(String.format("%.2f $", media.getCost()));
        cost.setStyle("-fx-text-fill: #198754; -fx-font-size: 20; -fx-font-weight: bold;");

        HBox actions = new HBox(8);
        actions.setAlignment(Pos.CENTER_LEFT);

        Button addToCart = primaryButton("Add to cart");
        addToCart.setOnAction(event -> addMediaToCart(media));
        actions.getChildren().add(addToCart);

        if (media instanceof Playable) {
            Button play = purpleButton("Play");
            play.setOnAction(event -> AimsScreenNavigator.playMedia(this, media));
            actions.getChildren().add(play);
        }

        VBox spacer = new VBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(type, title, category, cost, spacer, actions);
        return card;
    }

    private void addMediaToCart(Media media) {
        cart.addMedia(media);
        refreshCartSummary();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, media.getTitle() + " has been added to the cart.");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private List<Media> filterAndSortMedia() {
        String query = searchField == null ? "" : searchField.getText().trim().toLowerCase();
        String selectedType = typeFilter == null ? "All types" : typeFilter.getSelectionModel().getSelectedItem();
        String selectedSort = sortBox == null ? "Title A-Z" : sortBox.getSelectionModel().getSelectedItem();

        List<Media> result = new ArrayList<Media>();
        for (Media media : store.getItemsInStore()) {
            if (!matchesType(media, selectedType)) {
                continue;
            }
            if (!matchesQuery(media, query)) {
                continue;
            }
            result.add(media);
        }

        if ("Price low-high".equals(selectedSort)) {
            result.sort(Comparator.comparing(Media::getCost).thenComparing(Media::getTitle,
                    Comparator.nullsLast(String::compareToIgnoreCase)));
        } else if ("Price high-low".equals(selectedSort)) {
            result.sort(Comparator.comparing(Media::getCost).reversed().thenComparing(Media::getTitle,
                    Comparator.nullsLast(String::compareToIgnoreCase)));
        } else {
            result.sort(Comparator.comparing(Media::getTitle, Comparator.nullsLast(String::compareToIgnoreCase)));
        }
        return result;
    }

    private boolean matchesType(Media media, String selectedType) {
        if ("Book".equals(selectedType)) {
            return media instanceof Book;
        }
        if ("CD".equals(selectedType)) {
            return media instanceof CompactDisc;
        }
        if ("DVD".equals(selectedType)) {
            return media instanceof DigitalVideoDisc;
        }
        return true;
    }

    private boolean matchesQuery(Media media, String query) {
        if (query.isEmpty()) {
            return true;
        }
        String title = media.getTitle() == null ? "" : media.getTitle().toLowerCase();
        String category = media.getCategory() == null ? "" : media.getCategory().toLowerCase();
        String type = media.getClass().getSimpleName().toLowerCase();
        return title.contains(query) || category.contains(query) || type.contains(query);
    }

    private void refreshCartSummary() {
        if (cartSummaryLabel != null) {
            cartSummaryLabel.setText(String.format("Cart: %d item(s) | %.2f $",
                    cart.getItemsOrdered().size(), cart.totalCost()));
        }
    }

    private Button primaryButton(String text) {
        Button button = new Button(text);
        button.setStyle(STYLE_PRIMARY_BUTTON);
        button.setPadding(new Insets(8, 16, 8, 16));
        return button;
    }

    private Button purpleButton(String text) {
        Button button = new Button(text);
        button.setStyle(STYLE_PURPLE_BUTTON);
        button.setPadding(new Insets(8, 16, 8, 16));
        return button;
    }

    @SuppressWarnings("unused")
    private Button secondaryButton(String text) {
        Button button = new Button(text);
        button.setStyle(STYLE_SECONDARY_BUTTON);
        button.setPadding(new Insets(8, 14, 8, 14));
        return button;
    }
}
