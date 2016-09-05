package view;

import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by Marcus Baetz on 16.08.2016.
 *
 * @author Marcus Bätz
 */
public class HorizontalTitledPane extends BorderPane {
    private static final String TEXT = "text";
    private static final String ICONS_14712986123_TRIANGLE_PNG = "/icons/14712986123_triangle.png";
    private final ImageView image;
    private VBox content = new VBox();
    private final BooleanProperty closed = new SimpleBooleanProperty(false);
    private double startWidth;

    public boolean getClosed() {
        return closed.get();
    }

    public BooleanProperty closedProperty() {
        return closed;
    }

    public void setClosed(final boolean closed) {
        this.closed.set(closed);
    }



    public final StringProperty textProperty() {
        if (text == null) {
            text = new SimpleStringProperty(this, TEXT, "");
        }
        return text;
    }

    private StringProperty text;

    public final void setText(String value) {
        textProperty().setValue(value);
    }

    public final String getText() {
        return text == null ? "" : text.getValue();
    }
    final Rectangle rec = new Rectangle();
    public HorizontalTitledPane() {
        super();
        Label label = new Label();
        label.textProperty().bind(textProperty());
        label.setRotate(270);
        label.translateYProperty().bind(heightProperty().multiply(-0.42));
        label.translateXProperty().bind(heightProperty().multiply(-0.49));
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setContentDisplay(ContentDisplay.RIGHT);
        label.minWidthProperty().bind(heightProperty());
        label.setWrapText(true);
        image = new ImageView(new Image(ICONS_14712986123_TRIANGLE_PNG, 9.0, 9.0, true, true));
        label.setGraphic(image);
        Button slideButton = new Button();
        slideButton.setAlignment(Pos.BOTTOM_LEFT);
        slideButton.setGraphic(label);
        slideButton.prefHeightProperty().bind(heightProperty());
        slideButton.setMinWidth(25);
        slideButton.setMaxWidth(25);
        this.setLeft(slideButton);
        slideButton.setOnAction(a -> closed.setValue(!closed.getValue()));
        centerProperty().addListener((a, b, c) -> {
            if (getCenter() != null && getCenter() != content) {
                content = new VBox(new VBox(getCenter()));
                content.setPadding(new Insets(10));
                setCenter(content);
                content.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
                content.setAlignment(Pos.CENTER);
                rec.widthProperty().bind(content.widthProperty());
                rec.heightProperty().bind(content.heightProperty());
                content.setClip(rec);
            }
        });
        closed.addListener(a -> {
                animateSlide();
        });
    }

    private void animateSlide() {
        if (getClosed()) {
            startWidth = content.getWidth();
            RotateTransition closeRotate = new RotateTransition(Duration.millis(200), image);
            closeRotate.setFromAngle(0.0);
            closeRotate.setToAngle(90.0);
            closeRotate.setInterpolator(Interpolator.EASE_OUT);
            ParallelTransition trans = new ParallelTransition( closeRotate);
            trans.currentTimeProperty().addListener((a,b,c)->{
                rec.widthProperty().unbind();
                rec.heightProperty().unbind();
                rec.setWidth( startWidth* (1.0 -(c.toMillis()/200.0)));
                content.setMaxWidth( startWidth* (1.0 -(c.toMillis()/200.0)));
                content.setMinWidth( startWidth* (1.0 -(c.toMillis()/200.0)));
                content.getChildren().get(0).setTranslateX(-startWidth* (c.toMillis()/200.0));
            });
            trans.playFromStart();
        } else {
            if(startWidth <10) startWidth =200;
            RotateTransition closeRotate = new RotateTransition(Duration.millis(200), image);
            closeRotate.setFromAngle(90.0);
            closeRotate.setToAngle(0.0);
            closeRotate.setInterpolator(Interpolator.EASE_OUT);
            ParallelTransition trans = new ParallelTransition( closeRotate);
            trans.currentTimeProperty().addListener((a,b,c)->{
                rec.setWidth( startWidth* c.toMillis()/200.0);
                content.setMinWidth( startWidth* (c.toMillis()/200.0));
                content.setMaxWidth( startWidth* (c.toMillis()/200.0));
                content.getChildren().get(0).setTranslateX(-startWidth* (1.0 -(c.toMillis()/200.0)));
            });
            trans.setOnFinished(a->{
                content.setMinWidth(-1);
                content.setMaxWidth(-1);
                rec.widthProperty().bind(content.widthProperty());
                rec.heightProperty().bind(content.heightProperty());
            });
            trans.playFromStart();
        }
    }


}
