package com.acoustic.encoder.shared.view.swing.components;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwingComboBox<T> extends JComboBox<T> {

    private static final String ILLEGAL_ITEM_TYPE_FOR_SORTING_MSG =
            "ClassCastException: unsupported type of items in ComboBox";

    private static final int ITEM_NOT_FOUND_INDEX = -1;

    private static final String ITEM_NOT_IN_COMBOBOX_MSG =
            "Current item not found in ComboBox";

    private static final String ITEMS_LIST_IS_NULL_OR_EMPTY_MSG =
            "ComboBox items list is null or empty";

    public enum SortOrder {
        NONE,
        ASCENDING,
        DESCENDING
    }

    private final List<T> originalItems;

    private boolean updatingModel;

    private SortOrder currentSortOrder = SortOrder.NONE;

    private T lastValidItem;

    private T initialItem;

    public SwingComboBox(
            List<T> items,
            Font font,
            int fontSize,
            Border border,
            int initialIndex,
            boolean isEditable,
            Dimension preferredSize,
            Dimension maxSize
    ) {

        this.originalItems = new ArrayList<>();

        if (items != null) {
            this.originalItems.addAll(items);

            for (T item : items)
                this.addItem(item);

            if (initialIndex >= 0 && initialIndex < items.size()) {
                this.initialItem = items.get(initialIndex);
                this.setSelectedItem(this.initialItem);
                this.lastValidItem = this.initialItem;
            }
            else if (!items.isEmpty()) {
                this.setSelectedIndex(0);
                this.lastValidItem = items.getFirst();
            }
        }

        if (font != null) this.setFont(font);

        if (fontSize > 0) this.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), fontSize));

        if (border != null) this.setBorder(border);

        this.setEditable(isEditable);

        if (preferredSize != null) this.setPreferredSize(preferredSize);
        if (maxSize != null) this.setMaximumSize(maxSize);
    }

    public void enableFiltering() {

        if (!this.isEditable()) this.setEditable(true);

        JTextField editor = (JTextField) this.getEditor().getEditorComponent();

        editor.getDocument().addDocumentListener(new DocumentListener() {

            private void filter() {
                if (updatingModel) return;

                SwingUtilities.invokeLater(() -> applyFilter(editor.getText()));
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filter();
            }
        });
    }

    public void sortItemsAscending() {
        this.currentSortOrder = SortOrder.ASCENDING;
        applyFilter(getEditorText());
    }

    public void sortItemsDescending() {
        this.currentSortOrder = SortOrder.DESCENDING;
        applyFilter(getEditorText());
    }

    public void resetItems() {
        this.currentSortOrder = SortOrder.NONE;
        applyFilter(initialItem.toString());
    }

    private String getEditorText() {

        if (!this.isEditable())
            return "";

        JTextField editor = (JTextField) this.getEditor().getEditorComponent();

        return editor.getText().trim();
    }

    public int getSelectedOriginalIndex() {
        if (originalItems.isEmpty()) {
            System.out.println(ITEMS_LIST_IS_NULL_OR_EMPTY_MSG);
            return ITEM_NOT_FOUND_INDEX;
        }

        Object selected = this.getSelectedItem();

        if (selected == null) return ITEM_NOT_FOUND_INDEX;

        for (int i = 0; i < originalItems.size(); i++) {
            T item = originalItems.get(i);

            if (item.equals(selected))
                return i;
        }

        System.out.println(ITEM_NOT_IN_COMBOBOX_MSG);

        return ITEM_NOT_FOUND_INDEX;
    }

    public void setSelectedOriginalIndex(int index) {

        if (index >= 0 && index < this.originalItems.size()) {
            T item = this.originalItems.get(index);
            this.setSelectedItem(item);
        }
    }

    public void setInitialItem(T item) {

        if (item != null && originalItems.contains(item)) {
            this.initialItem = item;
            this.setSelectedItem(item);
        }
    }

    public boolean isEditorInputValid() {

        String text = getEditorText();

        for (T item : originalItems) {
            if (item.toString().equals(text))
                return true;
        }

        return false;
    }

    public void commitEditorInput() {

        String text = getEditorText();

        for (T item : originalItems) {
            if (item.toString().equals(text)) {
                this.setSelectedItem(item);
                this.lastValidItem = item;

                return;
            }
        }
    }

    public void restoreLastValidInput() {
        updatingModel = true;

        JTextField editor = (JTextField) this.getEditor().getEditorComponent();

        this.setSelectedItem(this.lastValidItem);

        if (this.lastValidItem != null) editor.setText(this.lastValidItem.toString());

        updatingModel = false;
    }

    public boolean finishEditing() {

        if (isEditorInputValid()) {
            commitEditorInput();

            return true;
        }

        restoreLastValidInput();

        return false;
    }

    private void applyFilter(String text) {
        List<T> filteredItems = new ArrayList<>();

        for (T item : originalItems) {
            if (item.toString().toLowerCase().startsWith(text.toLowerCase()))
                filteredItems.add(item);
        }

        filteredItems = sortItems(filteredItems);

        JTextField editor = (JTextField) this.getEditor().getEditorComponent();

        int caret = editor.getCaretPosition();

        rebuildModel(filteredItems, text, caret);
    }

    private List<T> sortItems(List<T> source) {

        List<T> sortedItems = new ArrayList<>(source);

        try {
            switch (currentSortOrder) {
                case ASCENDING -> sortedItems.sort(null);

                case DESCENDING -> {
                    sortedItems.sort(null);
                    Collections.reverse(sortedItems);
                }
            }
        }
        catch (ClassCastException e) {
            e.printStackTrace();
            System.out.println(ILLEGAL_ITEM_TYPE_FOR_SORTING_MSG);
        }

        return sortedItems;
    }

    private void rebuildModel(List<T> items, String editorText, int caret) {
        updatingModel = true;

        this.hidePopup();

        this.removeAllItems();

        for (T item : items)
            this.addItem(item);

        JTextField editor = (JTextField) this.getEditor().getEditorComponent();

        this.setSelectedIndex(ITEM_NOT_FOUND_INDEX);

        editor.setText(editorText);

        if (caret <= editorText.length()) editor.setCaretPosition(caret);

        if (this.getItemCount() > 0 && this.isDisplayable() && this.isShowing() && editor.hasFocus())
            SwingUtilities.invokeLater(() -> {
                if (!this.isPopupVisible()) this.showPopup();
            });

        updatingModel = false;
    }
}
