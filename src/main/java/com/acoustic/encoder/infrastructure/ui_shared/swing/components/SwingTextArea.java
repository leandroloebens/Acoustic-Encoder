package com.acoustic.encoder.infrastructure.ui_shared.swing.components;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SwingTextArea extends JTextArea {

    private static final int UNDO_LIMIT = 50;

    private static final String UNDO_ACTION = "Undo";
    private static final KeyStroke UNDO_KEY_EVENT = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);

    private static final String REDO_ACTION = "Redo";
    private static final KeyStroke REDO_KEY_EVENT = KeyStroke.getKeyStroke(
            KeyEvent.VK_Z,
            KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK
    );

    private UndoManager undoManager;

    public SwingTextArea() {
        this.setLineWrap(true);

        this.setWrapStyleWord(true);

    }

    public SwingTextArea(Font font, int fontSize, Border border) {
        this.setLineWrap(true);

        this.setWrapStyleWord(true);

        if (font != null) this.setFont(font);

        if (fontSize > 0) this.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), fontSize));

        if (border != null) this.setBorder(border);

    }

    public String getText() { return super.getText(); }

    public void enableUndoRedo() {
        if (undoManager == null) {
            createUndoManager();
            enableUndo();
            enableRedo();
        }
    }

    public void disableUndoRedo() {
        if (undoManager != null) {
            disableUndo();
            disableRedo();
            undoManager = null;
        }
    }

    private void createUndoManager() {
        undoManager = new UndoManager();
        undoManager.setLimit(UNDO_LIMIT);
        this.getDocument().addUndoableEditListener(undoManager);
    }

    private void enableUndo() {
        Action undoAction = new AbstractAction(UNDO_ACTION) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo())
                    undoManager.undo();
            }
        };

        getInputMap().put(UNDO_KEY_EVENT, UNDO_ACTION);
        getActionMap().put(UNDO_ACTION, undoAction);
    }

    private void disableUndo() {
        getInputMap().remove(UNDO_KEY_EVENT);
        getActionMap().remove(UNDO_ACTION);
    }

    private void enableRedo() {
        Action redoAction = new AbstractAction(REDO_ACTION) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo())
                    undoManager.redo();
            }
        };

        getInputMap().put(REDO_KEY_EVENT, REDO_ACTION);
        getActionMap().put(REDO_ACTION, redoAction);
    }

    private void disableRedo() {
        getInputMap().remove(REDO_KEY_EVENT);
        getActionMap().remove(REDO_ACTION);
    }

}
