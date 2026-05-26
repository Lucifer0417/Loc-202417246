package hust.soict.dsai.aims.screen;

import java.awt.BorderLayout;
import java.util.Comparator;

import javax.swing.JFrame;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.Playable;
import hust.soict.dsai.aims.store.Store;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CartScreen extends JFrame {
    private static final String STYLE_BACKGROUND = "-fx-background-color: #f5f7fa;";
    private static final String STYLE_CARD = "-fx-background-color: white; -fx-border-color: #dee2e6;"
            + " -fx-border-radius: 8; -fx-background-radius: 8;";
    private static final String STYLE_PRIMARY_BUTTON = "-fx-background-color: #0d6efd; -fx-text-fill: white;"
            + " -fx-font-weight: bold; -fx-background-radius: 6;";
    private static final String STYLE_DANGER_BUTTON = "-fx-background-color: #dc3545; -fx-text-fill: white;"
            + " -fx-font-weight: bold; -fx-background-radius: 6;";
    private static final String STYLE_SUCCESS_BUTTON = "-fx-background-color: #198754; -fx-text-fill: white;"
            + " -fx-font-weight: bold; -fx-background-radius: 6;";

    private final Store store;
    private final Cart cart;
    private final JFXPanel jfxPanel = new JFXPanel();

    private ObservableList<Media> cartItems;
    private FilteredList<Media> filteredItems;
    private TableView<Media> tableView;
    private TextField tfFilter;
    private ComboBox<String> filterMode;
    private Label totalLabel;
    private Button playButton;
    private Button removeButton;

    public CartScreen(Store store, Cart cart) {
        this.store = store;
        this.cart = cart;

        setTitle("Giỏ hàng AIMS");
        setJMenuBar(AimsScreenNavigator.createMenuBar(this, store, cart));
        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);

        setSize(1080, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        Platform.runLater(() -> {
            Platform.setImplicitExit(false);
            cartItems = FXCollections.observableArrayList(cart.getItemsOrdered());
            filteredItems = new FilteredList<Media>(cartItems, media -> true);
            jfxPanel.setScene(new Scene(createRoot(), 1080, 720));
        });
    }

    private BorderPane createRoot() {
        BorderPane root = new BorderPane();
        root.setStyle(STYLE_BACKGROUND);
        root.setPadding(new Insets(18, 24, 24, 24));

        root.setTop(createHeader());
        root.setCenter(createCenter());
        root.setRight(createSummaryPanel());
        updateSummary();
        updateButtonBar();
        return root;
    }

    private VBox createHeader() {
        VBox header = new VBox(4);
        header.setPadding(new Insets(0, 0, 14, 0));

        Label title = new Label("Giỏ hàng");
        title.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 34; -fx-font-weight: bold;"
                + " -fx-text-fill: #212529;");

        Label subtitle = new Label("Tìm kiếm, sắp xếp, phát, xóa sản phẩm và theo dõi tổng tiền.");
        subtitle.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 13;");

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    private VBox createCenter() {
        VBox center = new VBox(12);
        center.setPadding(new Insets(0, 12, 0, 0));

        center.getChildren().add(createToolbar());
        center.getChildren().add(createTable());
        center.getChildren().add(createActionBar());
        VBox.setVgrow(tableView, Priority.ALWAYS);
        return center;
    }

    private HBox createToolbar() {
        HBox toolbar = new HBox(10);
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setPadding(new Insets(12));
        toolbar.setStyle(STYLE_CARD);

        tfFilter = new TextField();
        tfFilter.setPromptText("Tìm trong giỏ hàng...");
        tfFilter.setPrefColumnCount(24);
        tfFilter.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());

        filterMode = new ComboBox<String>();
        filterMode.getItems().addAll("Tất cả", "Mã", "Tiêu đề", "Thể loại");
        filterMode.getSelectionModel().select("Tất cả");
        filterMode.valueProperty().addListener((observable, oldValue, newValue) -> applyFilter());

        Button sortTitle = secondaryButton("Sắp xếp tên");
        sortTitle.setOnAction(event -> sortByTitle());

        Button sortCostLow = secondaryButton("Giá tăng dần");
        sortCostLow.setOnAction(event -> sortByCost(true));

        Button sortCostHigh = secondaryButton("Giá giảm dần");
        sortCostHigh.setOnAction(event -> sortByCost(false));

        toolbar.getChildren().addAll(new Label("Tìm kiếm"), tfFilter, new Label("Theo"), filterMode,
                sortTitle, sortCostLow, sortCostHigh);
        return toolbar;
    }

    private TableView<Media> createTable() {
        tableView = new TableView<Media>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tableView.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6;");

        TableColumn<Media, Integer> idColumn = new TableColumn<Media, Integer>("Mã");
        idColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<Integer>(data.getValue().getId()));
        idColumn.setMaxWidth(80);

        TableColumn<Media, String> titleColumn = new TableColumn<Media, String>("Tiêu đề");
        titleColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<String>(data.getValue().getTitle()));

        TableColumn<Media, String> categoryColumn = new TableColumn<Media, String>("Thể loại");
        categoryColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<String>(data.getValue().getCategory()));

        TableColumn<Media, String> typeColumn = new TableColumn<Media, String>("Loại");
        typeColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<String>(
                getDisplayType(data.getValue())));

        TableColumn<Media, Float> costColumn = new TableColumn<Media, Float>("Giá");
        costColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<Float>(data.getValue().getCost()));
        costColumn.setComparator(Comparator.naturalOrder());

        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(titleColumn);
        tableView.getColumns().add(categoryColumn);
        tableView.getColumns().add(typeColumn);
        tableView.getColumns().add(costColumn);

        SortedList<Media> sortedItems = new SortedList<Media>(filteredItems);
        sortedItems.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedItems);
        tableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> updateButtonBar());
        return tableView;
    }

    private HBox createActionBar() {
        HBox actionBar = new HBox(10);
        actionBar.setAlignment(Pos.CENTER_RIGHT);

        playButton = primaryButton("Phát");
        playButton.setOnAction(event -> playSelectedMedia());

        removeButton = dangerButton("Xóa");
        removeButton.setOnAction(event -> removeSelectedMedia());

        actionBar.getChildren().addAll(playButton, removeButton);
        return actionBar;
    }

    private VBox createSummaryPanel() {
        VBox summary = new VBox(12);
        summary.setPrefWidth(260);
        summary.setPadding(new Insets(16));
        summary.setStyle(STYLE_CARD);

        Label title = new Label("Tổng đơn hàng");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #212529;");

        totalLabel = new Label();
        totalLabel.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #198754;");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button placeOrderButton = successButton("Đặt hàng");
        placeOrderButton.setMaxWidth(Double.MAX_VALUE);
        placeOrderButton.setOnAction(event -> placeOrder());

        summary.getChildren().addAll(title, totalLabel, spacer, placeOrderButton);
        return summary;
    }

    private void applyFilter() {
        if (filteredItems == null) {
            return;
        }

        String filter = tfFilter.getText() == null ? "" : tfFilter.getText().trim().toLowerCase();
        if (filter.isEmpty()) {
            filteredItems.setPredicate(media -> true);
            return;
        }

        filteredItems.setPredicate(media -> {
            String mode = filterMode.getSelectionModel().getSelectedItem();
            String id = Integer.toString(media.getId());
            String title = media.getTitle() == null ? "" : media.getTitle().toLowerCase();
            String category = media.getCategory() == null ? "" : media.getCategory().toLowerCase();
            String type = media.getClass().getSimpleName().toLowerCase();

            if ("Mã".equals(mode)) {
                return id.contains(filter);
            }
            if ("Tiêu đề".equals(mode)) {
                return title.contains(filter);
            }
            if ("Thể loại".equals(mode)) {
                return category.contains(filter);
            }
            return id.contains(filter) || title.contains(filter) || category.contains(filter) || type.contains(filter);
        });
    }

    private void sortByTitle() {
        cartItems.sort(Comparator.comparing(Media::getTitle, Comparator.nullsLast(String::compareToIgnoreCase)));
        tableView.getSortOrder().clear();
    }

    private void sortByCost(boolean ascending) {
        Comparator<Media> comparator = Comparator.comparing(Media::getCost);
        if (!ascending) {
            comparator = comparator.reversed();
        }
        cartItems.sort(comparator.thenComparing(Media::getTitle, Comparator.nullsLast(String::compareToIgnoreCase)));
        tableView.getSortOrder().clear();
    }

    private Media getSelectedMedia() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void updateButtonBar() {
        if (playButton == null || removeButton == null || tableView == null) {
            return;
        }

        Media selected = getSelectedMedia();
        boolean hasSelection = selected != null;
        removeButton.setDisable(!hasSelection);
        playButton.setDisable(!hasSelection || !(selected instanceof Playable));
    }

    private void playSelectedMedia() {
        Media selected = getSelectedMedia();
        if (selected != null) {
            AimsScreenNavigator.playMedia(this, selected);
        }
    }

    private void removeSelectedMedia() {
        Media selected = getSelectedMedia();
        if (selected == null) {
            return;
        }

        cart.removeMedia(selected);
        cartItems.setAll(cart.getItemsOrdered());
        applyFilter();
        updateSummary();
        updateButtonBar();
    }

    private void placeOrder() {
        if (cart.getItemsOrdered().isEmpty()) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.INFORMATION,
                    "Giỏ hàng đang trống.");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        cart.empty();
        cartItems.clear();
        updateSummary();
        updateButtonBar();

        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION,
                "Đã tạo đơn hàng thành công.");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void updateSummary() {
        if (totalLabel == null) {
            return;
        }
        totalLabel.setText(String.format("%.2f $", cart.totalCost()));
    }

    private String getDisplayType(Media media) {
        if (media instanceof hust.soict.dsai.aims.media.Book) {
            return "Sách";
        }
        if (media instanceof hust.soict.dsai.aims.media.CompactDisc) {
            return "CD";
        }
        if (media instanceof hust.soict.dsai.aims.media.DigitalVideoDisc) {
            return "DVD";
        }
        return "Media";
    }

    private Button primaryButton(String text) {
        Button button = new Button(text);
        button.setStyle(STYLE_PRIMARY_BUTTON);
        button.setPadding(new Insets(8, 16, 8, 16));
        return button;
    }

    private Button secondaryButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #495057; -fx-text-fill: white; -fx-background-radius: 6;");
        button.setPadding(new Insets(8, 14, 8, 14));
        return button;
    }

    private Button dangerButton(String text) {
        Button button = new Button(text);
        button.setStyle(STYLE_DANGER_BUTTON);
        button.setPadding(new Insets(8, 16, 8, 16));
        return button;
    }

    private Button successButton(String text) {
        Button button = new Button(text);
        button.setStyle(STYLE_SUCCESS_BUTTON);
        button.setPadding(new Insets(10, 16, 10, 16));
        return button;
    }
}
