package view;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.Game;
import model.properties.AProperty;
import model.properties.CommonProperty;
import model.properties.DamageBonus;

/**
 * Created by Marcus Baetz on 11.08.2016.
 *
 * @author Marcus Bätz
 */
public class FormattedTextFieldTableCell<S, T>
        implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    @Override
    public TableCell<S, T> call(final TableColumn<S, T> param) {
        return new TableCell<S, T>() {

            final TextField editText = new TextField();
            {
                editText.setOnAction(a->commitEdit(null));
                editText.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (!newPropertyValue)  commitEdit(null); });
            }
            @Override
            public void updateItem(Object item, boolean empty) {
                if (item == getItem()) {
                    return;
                }
                super.updateItem((T) item, empty);
                if (item == null) {
                    super.setText(null);
                    super.setGraphic(null);
                } else {
                    super.setText(item.toString());
                    super.setGraphic(null);
                }
            }

           @Override
            public void commitEdit(final T newValue) {
               Game game = Game.getInstance();
               TableRow<T> row = (TableRow< T>) getParent();
              Object item = row.getTableView().getItems().get(getIndex());
                if(item instanceof AProperty){
                    if(item instanceof DamageBonus){
                        game.setDamageBonus(editText.getText());
                    }else {
                        try {
                            game.setPropertieValue(((AProperty) item), editText.getText());
                        }catch (NumberFormatException e){
                            editText.setText("Nur ganze Zahlen");
                        }
                    }
                }else if(item instanceof CommonProperty){
                    game.setCommonProperty((CommonProperty)item,editText.getText());
                }
                setText(editText.getText());
                setGraphic(null);
               row.getTableView().getSelectionModel().clearSelection();

            }

            @Override
            public void startEdit() {
                editText.setText(getText());
                setGraphic(editText);
                editText.requestFocus();
                editText.selectAll();
                setText("");
            }

        };
    }

}
