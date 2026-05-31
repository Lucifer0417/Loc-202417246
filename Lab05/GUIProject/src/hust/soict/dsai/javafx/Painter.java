package hust.soict.dsai.javafx;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Painter extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(findPainterFxml());
        primaryStage.setTitle("Painter");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private URL findPainterFxml() throws MalformedURLException {
        URL resource = Painter.class.getResource("Painter.fxml");
        if (resource != null) {
            return resource;
        }

        Path eclipsePath = Paths.get("src", "hust", "soict", "dsai", "javafx", "Painter.fxml");
        if (Files.exists(eclipsePath)) {
            return eclipsePath.toUri().toURL();
        }

        Path repoPath = Paths.get("Lab05", "GUIProject", "src", "hust", "soict", "dsai", "javafx",
                "Painter.fxml");
        if (Files.exists(repoPath)) {
            return repoPath.toUri().toURL();
        }

        throw new IllegalStateException("Painter.fxml was not found on the classpath or in the source tree.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
