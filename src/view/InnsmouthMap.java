package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Marcus Baetz on 29.08.2016.
 *
 * @author Marcus Bätz
 */
public class InnsmouthMap extends Stage{
    public InnsmouthMap()throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/fxml/innsmouthMapView.fxml"));

        ScrollPane pane = (ScrollPane)root.lookup("#scrollPane");

        Scene scene = new Scene(root);
        setMaximized(true);
        pane.maxWidthProperty().bind(widthProperty());
        pane.maxHeightProperty().bind(heightProperty());
        this.setScene(scene);
        this.initModality(Modality.WINDOW_MODAL);
    }

}
