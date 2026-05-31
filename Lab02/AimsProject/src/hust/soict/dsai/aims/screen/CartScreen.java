package hust.soict.dsai.aims.screen;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.store.Store;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CartScreen extends JFrame {
    private final Store store;
    private final Cart cart;
    private final JFXPanel jfxPanel = new JFXPanel();

    public CartScreen(Store store, Cart cart) {
        this.store = store;
        this.cart = cart;

        setTitle("AIMS Cart");
        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);

        setSize(1080, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        Platform.setImplicitExit(false);
        Platform.runLater(this::loadCartScene);
    }

    private void loadCartScene() {
        try {
            URL fxmlUrl = findCartFxml();
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == CartScreenController.class) {
                    return new CartScreenController(this, store, cart);
                }
                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (ReflectiveOperationException e) {
                    throw new IllegalStateException(e);
                }
            });

            Parent root = loader.load();
            jfxPanel.setScene(new Scene(root));
        } catch (IOException | RuntimeException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                    this,
                    "Cannot load cart.fxml: " + e.getMessage(),
                    "Cart screen error",
                    JOptionPane.ERROR_MESSAGE));
        }
    }

    private URL findCartFxml() throws MalformedURLException {
        URL resource = CartScreen.class.getResource("cart.fxml");
        if (resource != null) {
            return resource;
        }

        Path eclipsePath = Paths.get("src", "hust", "soict", "dsai", "aims", "screen", "cart.fxml");
        if (Files.exists(eclipsePath)) {
            return eclipsePath.toUri().toURL();
        }

        Path repoPath = Paths.get("Lab02", "AimsProject", "src", "hust", "soict", "dsai", "aims", "screen",
                "cart.fxml");
        if (Files.exists(repoPath)) {
            return repoPath.toUri().toURL();
        }

        throw new IllegalStateException("cart.fxml was not found on the classpath or in the source tree.");
    }
}
