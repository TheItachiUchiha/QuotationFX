package com.kc.util;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import com.kc.model.ComponentsVO;

public class EditingCell extends TableCell<ComponentsVO, Integer> {
	 
    private TextField textField;
    private Validation validate;
   
    public EditingCell() {
    	validate = new Validation();
    }
   
    @Override
    public void startEdit() {
        super.startEdit();
       
        if (textField == null) {
            createTextField();
        }
       
        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        //textField.selectAll();
        Platform.runLater(new Runnable() {
	            @Override
	            public void run() {
	                textField.requestFocus();
	                textField.selectAll();
	            }
	        });
    }
   
    @Override
    public void cancelEdit() {
        super.cancelEdit();
       
        setText(String.valueOf(getItem()));
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
       
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(getString());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    private void createTextField() {
        textField = new TextField();
        validate.allowDigit(textField);
        //textField.setText(getString());
        //textField.setText("0");
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
        
       /* textField.focusedProperty().addListener(new ChangeListener<Boolean>() {

      	    @Override
      	    public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
      	        if (!arg2) {
      	            commitEdit(Integer.parseInt(textField.getText()));
      	        }
      	    }
      	});*/
        
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent t) {
	                if (t.getCode() == KeyCode.ENTER) {
	                	//For not allowing to commit when Enter is pressed with empty Textfield
	                	if (!validate.isEmpty(textField)){
		                	if (!validate.isWord(textField.getText())){
	                    commitEdit(Integer.parseInt(textField.getText()));
		                	}}
	                } else if (t.getCode() == KeyCode.ESCAPE) {
	                    cancelEdit();
	                } else if (t.getCode() == KeyCode.TAB) {
	                	//For not allowing to commit when TAB is pressed with empty Textfield
	                	if (!validate.isEmpty(textField)){
		                	if (!validate.isWord(textField.getText())){
	                    commitEdit(Integer.parseInt(textField.getText()));
		                	}}
	                    TableColumn nextColumn = getNextColumn(!t.isShiftDown());
	                    if (nextColumn != null) {
	                        getTableView().edit(getTableRow().getIndex(), nextColumn);
	                    }
	                }
	            }
	        });
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	            @Override
	            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	                if (!newValue && textField != null) {
	                	//For not allowing to commit when mouse is pressed with empty Textfield
	                	if (!validate.isEmpty(textField)){
	                	if (!validate.isWord(textField.getText())){
	                		  commitEdit(Integer.parseInt(textField.getText()));
	                	}
	                	}
	                	
	                			                  
	                }
	            }
	        });
    }
   
    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
    
    private TableColumn<ComponentsVO, ?> getNextColumn(boolean forward) {
	        List<TableColumn<ComponentsVO, ?>> columns = new ArrayList<>();
	        for (TableColumn<ComponentsVO, ?> column : getTableView().getColumns()) {
	            columns.addAll(getLeaves(column));
	        }
	        //There is no other column that supports editing.
	        if (columns.size() < 2) {
	            return null;
	        }
	        int currentIndex = columns.indexOf(getTableColumn());
	        int nextIndex = currentIndex;
	        if (forward) {
	            nextIndex++;
	            if (nextIndex > columns.size() - 1) {
	                nextIndex = 0;
	            }
	        } else {
	            nextIndex--;
	            if (nextIndex < 0) {
	                nextIndex = columns.size() - 1;
	            }
	        }
	        return columns.get(nextIndex);
	    }
    
    	private List<TableColumn<ComponentsVO, ?>> getLeaves(TableColumn<ComponentsVO, ?> root) {
	        List<TableColumn<ComponentsVO, ?>> columns = new ArrayList<>();
	        if (root.getColumns().isEmpty()) {
	            //We only want the leaves that are editable.
	            if (root.isEditable()) {
	                columns.add(root);
	            }
	            return columns;
	        } else {
	            for (TableColumn<ComponentsVO, ?> column : root.getColumns()) {
	                columns.addAll(getLeaves(column));
	            }
	            return columns;
	        }
    	}
}

