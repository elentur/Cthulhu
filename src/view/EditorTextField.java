package view;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;

/**
 * Created by Marcus Baetz on 24.08.2016.
 *
 * @author Marcus Bätz
 */
public class EditorTextField extends HTMLEditor {

    private ToolBar upperToolBar;
    private ToolBar lowerToolBar;

    public EditorTextField() {
        super();
        modifieToolbars();

    }

    public void showHTMLEditorToolbars(final EditorTextField editor) {
        editor.setVisible(false);
        Platform.runLater(() -> {
            Node[] nodes = editor.lookupAll(".tool-bar").toArray(new Node[0]);
            for (Node node : nodes) {
                node.setVisible(true);
                node.setManaged(true);
            }
            editor.setVisible(true);
        });
    }


    private void modifieToolbars() {
        setVisible(false);
        Platform.runLater(() -> {
            Node[] nodes = lookupAll(".tool-bar").toArray(new Node[0]);
            upperToolBar = (ToolBar) nodes[0];
            lowerToolBar = (ToolBar) nodes[1];


            lowerToolBar.getItems().remove(0);
            ((ComboBox) lowerToolBar.getItems().get(0)).setPromptText("");
            ((ComboBox) lowerToolBar.getItems().get(1)).setPromptText("");
            lowerToolBar.getItems().add(upperToolBar.getItems().remove(upperToolBar.getItems().size() - 1));
            lowerToolBar.getItems().add(upperToolBar.getItems().remove(upperToolBar.getItems().size() - 1));
            upperToolBar.getItems().remove(upperToolBar.getItems().size() - 1);
            for (Node item : lowerToolBar.getItems()) {
                item.setOnMouseClicked(a -> this.fireEvent(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.ENTER, true, true, true, true)));
                if(item instanceof ColorPicker){
                    ((ColorPicker)item).valueProperty().addListener(a ->
                                this.fireEvent(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.ENTER, true, true, true, true)));
                }
            }
            for (Node item : upperToolBar.getItems()) {
                item.setOnMouseClicked(a -> this.fireEvent(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.ENTER, true, true, true, true)));

            }
            setVisible(true);
        });
    }

    public static void hideHTMLEditorToolbars(final HTMLEditor editor) {
        editor.setVisible(false);
        Platform.runLater(() -> {
            Node[] nodes = editor.lookupAll(".tool-bar").toArray(new Node[0]);
            for (Node node : nodes) {
                node.setVisible(false);
                node.setManaged(false);
            }
            editor.setVisible(true);
        });
    }
}
