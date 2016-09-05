package view;

import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import model.properties.APoint;
import model.properties.Attribute;

/**
 * Created by Marcus Baetz on 18.08.2016.
 *
 * @author Marcus Bätz
 */
public class APropertyListCell<T> implements Callback<ListView<T>, ListCell<T>> {


    @Override
    public ListCell<T> call(final ListView<T> param) {
        return new ListCell<T>(){
            @Override public void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if(item instanceof APoint){
                    APoint p = (APoint)item;
                    setText("");
                    Label name = new Label(p.getName());
                    Label normal = new Label(p.getBonus()+"");
                    normal.setFont(new Font(16));
                    HBox hBox1 = new HBox(name);
                    hBox1.setAlignment(Pos.CENTER_RIGHT);
                    hBox1.setSpacing(5);
                    HBox hBox = new HBox(normal,hBox1);
                    HBox.setHgrow(hBox1, Priority.ALWAYS);
                    hBox.setAlignment(Pos.CENTER);
                    hBox.setSpacing(5);
                    setGraphic(hBox);
                } else if(item instanceof Attribute){
                    Attribute p = (Attribute)item;
                    setText("");
                    Label name = new Label(p.getName());
                    Label normal = new Label(p.getNormal()+"");
                    normal.setFont(new Font(16));
                    Label difficult = new Label(p.getDifficult()+"");
                    Label extreme = new Label(p.getExtreme()+"");
                    VBox vBox = new VBox(difficult,extreme);
                    HBox hBox1 = new HBox(normal,vBox);
                    HBox hBox2 = new HBox(name);
                    hBox2.setAlignment(Pos.CENTER_RIGHT);
                    hBox1.setSpacing(5);
                    HBox hBox = new HBox(hBox1,hBox2);
                    HBox.setHgrow(hBox1, Priority.ALWAYS);
                    hBox.setAlignment(Pos.CENTER);
                    vBox.setSpacing(5);
                    hBox.setSpacing(5);
                    setGraphic(hBox);
                    this.setContentDisplay(ContentDisplay.RIGHT);
                }else{
                    setText("");
                }
            }
        };
    }
}
