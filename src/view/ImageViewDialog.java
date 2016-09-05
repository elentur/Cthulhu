package view;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Marcus Baetz on 08.08.2016.
 *
 * @author Marcus Bätz
 */
public class ImageViewDialog extends Stage {
    public ImageViewDialog(Image img){
        super();

        // this.setDialogPane(scrollPane);
        ScrollPane pane = new ScrollPane(new ImageView(img));
        pane.setPannable(true);
        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(a->{
            if(a.getCode()== KeyCode.ESCAPE)this.close();
        });
           this.setScene(scene);
        // this.setResizable(true);
         this.initModality(Modality.WINDOW_MODAL);
    }
}
