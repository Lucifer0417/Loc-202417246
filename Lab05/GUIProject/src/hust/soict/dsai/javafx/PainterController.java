package hust.soict.dsai.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PainterController {
    private static final double PEN_RADIUS = 4.0;
    private static final double ERASER_RADIUS = 10.0;

    @FXML
    private Pane drawingAreaPane;

    @FXML
    private RadioButton penRadioButton;

    @FXML
    private RadioButton eraserRadioButton;

    @FXML
    private void drawingAreaMouseDragged(MouseEvent event) {
        boolean eraserSelected = eraserRadioButton != null && eraserRadioButton.isSelected();
        double radius = eraserSelected ? ERASER_RADIUS : PEN_RADIUS;
        Color color = eraserSelected ? Color.WHITE : Color.BLACK;

        Circle dot = new Circle(event.getX(), event.getY(), radius, color);
        drawingAreaPane.getChildren().add(dot);
    }

    @FXML
    private void clearButtonPressed() {
        drawingAreaPane.getChildren().clear();
        if (penRadioButton != null) {
            penRadioButton.setSelected(true);
        }
    }
}
