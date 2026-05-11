package com.acoustic.encoder.shared.view.swing.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

public class SwingComboBox<T> extends JComboBox<T> {

    private final static String ILLEGAL_ITEM_TYPE_FOR_SORTING_MSG =
            "ClassCastException: unsupported type of items in ComboBox";
    private final static int ITEM_NOT_FOUND_INDEX = -1;
    private final static String ITEM_NOT_IN_COMBOBOX_MSG = "Current item not found in ComboBox";
    private final static String ITEMS_LIST_IS_NULL_OR_EMPTY_MSG = "ComboBox items list is null or empty";
    private final static String NO_ITEM_IS_CURRENTLY_SELECTED_IN_COMBOBOX_MSG =
            "No item is currently selected in ComboBox";

    private static final int BOX_PADDING = 40;

    private List<T> items;

    private T lastValidItem;

    private T initialItem;

    public SwingComboBox(List<T> items, Font font, float fontSize, Border border, int initialIndex, boolean editable) {

        if (items != null) {
            this.items = items;

            for (T item : items)
                this.addItem(item);

            if (initialIndex >= 0 && initialIndex < items.size()) {
                this.initialItem = items.get(initialIndex);
                this.setSelectedItem(this.initialItem);
                lastValidItem = this.initialItem;
            }
            else
                this.setSelectedIndex(0);
        }

        if (font != null) {
            this.setFont(font);

            if (fontSize > 0)
                this.setFont(font.deriveFont((fontSize)));
        }

        if (border != null) this.setBorder(border);

        if (editable) this.setEditable(true);


//        this.cropBox();

    }

    public void sortItemsAscending() {
        List<T> items = this.items;

        try {
            items.sort(null);

            this.removeAllItems();

            for (T item : items)
                this.addItem(item);

            if (this.initialItem != null)
                this.setSelectedItem(this.initialItem);
        }
        catch (ClassCastException e) {
            e.printStackTrace();
            System.out.println(ILLEGAL_ITEM_TYPE_FOR_SORTING_MSG);
        }
    }

    public void sortItemsDescending() {
        List<T> items = this.items;

        try {
            items.sort(null);

            this.removeAllItems();

            for (int i = items.size() - 1; i >= 0; i--)
                this.addItem(items.get(i));

            if (this.initialItem != null)
                this.setSelectedItem(this.initialItem);
        }
        catch (ClassCastException e) {
            e.printStackTrace();
            System.out.println(ILLEGAL_ITEM_TYPE_FOR_SORTING_MSG);
        }
    }

    public void resetItems() {
        this.removeAllItems();

        for (T item : this.items)
            this.addItem(item);

        if (this.initialItem != null)
            this.setSelectedItem(this.initialItem);
    }

    @SuppressWarnings("unchecked")
    public int getSelectedOriginalIndex() {

        if (this.items == null || this.items.isEmpty()) {
            System.out.println(ITEMS_LIST_IS_NULL_OR_EMPTY_MSG);
            return ITEM_NOT_FOUND_INDEX;
        }

        T currentItem = (T) this.getSelectedItem();

        if (items.contains(currentItem))
            return this.items.indexOf(currentItem);
        else {
            System.out.println(ITEM_NOT_IN_COMBOBOX_MSG);
            return ITEM_NOT_FOUND_INDEX;
        }
    }

    public void setSelectedOriginalIndex(int index) {
        if (index >= 0 && index < this.items.size()) {
            T item = this.items.get(index);
            this.setSelectedItem(item);
        }
    }

    @SuppressWarnings("unchecked")
    public void setInitialItem(Object item) {
        if (item != null && items.contains((T) item)) {
            this.initialItem = (T) item;
            this.setSelectedItem(item);
        }
    }

    public boolean validateEditorInput(SwingFrame frame) {
        JTextField editor = (JTextField) this.getEditor().getEditorComponent();
        String text = editor.getText();

        for (T item : items) {
            if (item.toString().equals(text)) {
                this.setSelectedItem(item);
                this.lastValidItem = item;
                return true;
            }
        }

        this.setSelectedItem(this.lastValidItem);
        editor.setText(this.lastValidItem.toString());
        return false;
    }

//    private void cropBox() {
//        int maxWidth = 0;
//        FontMetrics fm = this.getFontMetrics(this.getFont());
//        for (int i = 0; i < this.getItemCount(); i++) {
//            Object item = this.getItemAt(i);
//            int width = fm.stringWidth(item.toString());
//            if (width > maxWidth) maxWidth = width;
//        }
//        this.setPreferredSize(new Dimension(maxWidth + BOX_PADDING, this.getPreferredSize().height));
//    }



}