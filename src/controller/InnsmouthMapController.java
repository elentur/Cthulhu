package controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import view.ImageViewDialog;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.08.2016.
 *
 * @author Marcus Bätz
 */
public class InnsmouthMapController implements Initializable {
    public ScrollPane scrollPane;
    public StackPane stackPane;
    public Group group;
    public ComboBox<String> cmbPlaces;
    public Group group2;
    public Group root;

    @FXML
    private ImageView imgMap;

    private DoubleProperty scale = new SimpleDoubleProperty(1.0);
    private Polyline line;
    private Group lineNodes;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        cmbPlaces.setItems(FXCollections.observableArrayList());
        group2.getChildren().stream().filter(node -> node instanceof Label).forEach(node -> {
            cmbPlaces.getItems().add(((Label) node).getTooltip().getText());
        });
       Collections.sort(cmbPlaces.getItems());
         line = new Polyline();
        line.setStrokeWidth(5);
         lineNodes = new Group();
        group2.getChildren().addAll(line,lineNodes);
        stackPane.setOnMouseReleased(a->drawRoute(a));
        root.setOnKeyReleased(a->{
            if(a.getCode()== KeyCode.BACK_SPACE){
                if(!line.getPoints().isEmpty()) {
                    line.getPoints().remove(line.getPoints().size() - 1);
                    line.getPoints().remove(line.getPoints().size() - 1);
                    lineNodes.getChildren().remove(lineNodes.getChildren().size() - 1);
                }
            }
        });
        final double hvalue = scrollPane.getHvalue();
        final double vvalue = scrollPane.getVvalue();
        scale.setValue(0.3);
        animateZoom(hvalue,vvalue);
    }

    private void drawRoute(final MouseEvent a) {
        if(a.getButton() == MouseButton.SECONDARY){
            line.getPoints().add(a.getX());
            line.getPoints().add(a.getY());
            lineNodes.getChildren().add(new Circle(a.getX(),a.getY(),10, Color.RED));
        }
    }

    public void onScroll(Event event) {
        ScrollEvent e = (ScrollEvent) event;
        final double hvalue = scrollPane.getHvalue();
        final double vvalue = scrollPane.getVvalue();
        if (e.getDeltaY() > 0.0) scale.setValue(scale.getValue() - 0.05);
        else if (e.getDeltaY() < 0.0) scale.setValue(scale.getValue() + 0.05);
        if (scale.getValue() < 0.3) {
            scale.setValue(0.3);
            return;
        }
        if (scale.getValue() > 1.0) {
            scale.setValue(1.0);
            return;
        }
       animateZoom(hvalue,vvalue);
    }
    private void animateZoom(double hvalue, double vvalue){
        ChangeListener hChange = (observable, oldValue, newValue) -> scrollPane.setHvalue(hvalue);
        ChangeListener vChange = (observable, oldValue, newValue) -> scrollPane.setVvalue(vvalue);
        scrollPane.hvalueProperty().addListener(hChange);
        scrollPane.vvalueProperty().addListener(vChange);
        final Timeline timeline = new Timeline();
        final KeyValue kxv = new KeyValue(stackPane.scaleXProperty(), scale.getValue());
        final KeyValue kyv = new KeyValue(stackPane.scaleYProperty(), scale.getValue());
        final KeyFrame kf = new KeyFrame(Duration.millis(100), kxv, kyv);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(a -> {
            scrollPane.hvalueProperty().removeListener(hChange);
            scrollPane.vvalueProperty().removeListener(vChange);
        });
        timeline.playFromStart();
    }
    public void loadImage(Event event) {
        try {
            Label s = (Label) event.getSource();
            ImageViewDialog dialog = new ImageViewDialog(new Image("/innsmouth/in_" + s.getText() + ".jpg"));
            dialog.setMaxHeight(400);
            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotToPlace() {
        String place = cmbPlaces.getSelectionModel().getSelectedItem();
        Label lblPlace = null;
        for (Node node : group2.getChildren()) {
            if (node instanceof Label && ((Label) node).getTooltip().getText().equals(place)) lblPlace = (Label) node;
        }
        if (lblPlace != null) {
            double x = lblPlace.getTranslateX();
            double y = lblPlace.getTranslateY();

            final Timeline timeline = new Timeline();
            final KeyValue kxv = new KeyValue(scrollPane.hvalueProperty(), x/stackPane.getWidth());
            final KeyValue kyv = new KeyValue(scrollPane.vvalueProperty(), y/stackPane.getHeight());
            final KeyFrame kf = new KeyFrame(Duration.millis(500), kxv, kyv);
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().add(kf);
            timeline.playFromStart();
        }
    }
}
