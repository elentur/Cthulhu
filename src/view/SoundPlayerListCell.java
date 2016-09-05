package view;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Created by Marcus Baetz on 18.08.2016.
 *
 * @author Marcus Bätz
 */
public class SoundPlayerListCell<T> implements Callback<ListView<T>, ListCell<T>> {

    private static final String CSS_ROOT_STYLE_CSS = "/css/rootStyle.css";
    @Override
    public ListCell<T> call(final ListView<T> param) {
        return new ListCell<T>() {
            @Override
            public void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (item instanceof SoundPlayer) {
                    setGraphic((SoundPlayer)item);
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        };
    }
}
