package hust.soict.dsai.aims.screen;

import javax.swing.JFrame;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.Playable;
import hust.soict.dsai.aims.store.Store;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CartScreenController {
    private final JFrame owner;
    private final Store store;
    private final Cart cart;

    private FilteredList<Media> filteredMedia;

    @FXML
    private TextField tfFilter;

    @FXML
    private RadioButton radioBtnFilterId;

    @FXML
    private RadioButton radioBtnFilterTitle;

    @FXML
    private TableView<Media> tblMedia;

    @FXML
    private TableColumn<Media, String> colMediaTitle;

    @FXML
    private TableColumn<Media, String> colMediaCategory;

    @FXML
    private TableColumn<Media, Float> colMediaCost;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnRemove;

    @FXML
    private Label totalLabel;

    public CartScreenController(JFrame owner, Store store, Cart cart) {
        this.owner = owner;
        this.store = store;
        this.cart = cart;
    }

    @SuppressWarnings("deprecation")
    @FXML
    private void initialize() {
        tblMedia.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colMediaTitle.setCellValueFactory(data -> new ReadOnlyObjectWrapper<String>(data.getValue().getTitle()));
        colMediaCategory.setCellValueFactory(
                data -> new ReadOnlyObjectWrapper<String>(data.getValue().getCategory()));
        colMediaCost.setCellValueFactory(data -> new ReadOnlyObjectWrapper<Float>(data.getValue().getCost()));

        filteredMedia = new FilteredList<Media>(cart.getItemsOrdered(), media -> true);
        SortedList<Media> sortedMedia = new SortedList<Media>(filteredMedia);
        sortedMedia.comparatorProperty().bind(tblMedia.comparatorProperty());
        tblMedia.setItems(sortedMedia);

        setButtonVisible(btnPlay, false);
        setButtonVisible(btnRemove, false);
        tblMedia.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> updateButtonBar(newValue));

        tfFilter.textProperty().addListener((observable, oldValue, newValue) -> showFilteredMedia());
        radioBtnFilterId.selectedProperty().addListener((observable, oldValue, newValue) -> showFilteredMedia());
        radioBtnFilterTitle.selectedProperty().addListener((observable, oldValue, newValue) -> showFilteredMedia());

        cart.getItemsOrdered().addListener((ListChangeListener<Media>) change -> updateTotalCost());
        updateTotalCost();
    }

    @FXML
    private void btnPlayPressed() {
        Media selected = tblMedia.getSelectionModel().getSelectedItem();
        if (selected != null) {
            AimsScreenNavigator.playMedia(owner, selected);
        }
    }

    @FXML
    private void btnRemovePressed() {
        Media selected = tblMedia.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        cart.removeMedia(selected);
        tblMedia.getSelectionModel().clearSelection();
    }

    @FXML
    private void btnPlaceOrderPressed() {
        if (cart.getItemsOrdered().isEmpty()) {
            showInfo("The cart is empty.");
            return;
        }

        cart.empty();
        tblMedia.getSelectionModel().clearSelection();
        showInfo("Order placed successfully.");
    }

    @FXML
    private void onViewStore() {
        AimsScreenNavigator.openStore(owner, store, cart);
    }

    @FXML
    private void onViewCart() {
        AimsScreenNavigator.openCart(owner, store, cart);
    }

    @FXML
    private void onAddBook() {
        AimsScreenNavigator.openAddBook(owner, store, cart);
    }

    @FXML
    private void onAddCd() {
        AimsScreenNavigator.openAddCd(owner, store, cart);
    }

    @FXML
    private void onAddDvd() {
        AimsScreenNavigator.openAddDvd(owner, store, cart);
    }

    private void showFilteredMedia() {
        if (filteredMedia == null) {
            return;
        }

        String filter = tfFilter.getText() == null ? "" : tfFilter.getText().trim().toLowerCase();
        if (filter.isEmpty()) {
            filteredMedia.setPredicate(media -> true);
            return;
        }

        filteredMedia.setPredicate(media -> {
            if (radioBtnFilterId.isSelected()) {
                return Integer.toString(media.getId()).contains(filter);
            }

            String title = media.getTitle() == null ? "" : media.getTitle().toLowerCase();
            return title.contains(filter);
        });
    }

    private void updateButtonBar(Media selected) {
        boolean hasSelection = selected != null;
        setButtonVisible(btnRemove, hasSelection);
        setButtonVisible(btnPlay, hasSelection && selected instanceof Playable);
    }

    private void setButtonVisible(Button button, boolean visible) {
        button.setVisible(visible);
        button.setManaged(visible);
    }

    private void updateTotalCost() {
        totalLabel.setText(String.format("%.2f $", cart.totalCost()));
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
