package hust.soict.dsai.aims.screen;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class SimpleDocumentListener implements DocumentListener {
    private final Runnable handler;

    SimpleDocumentListener(Runnable handler) {
        this.handler = handler;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        handler.run();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        handler.run();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        handler.run();
    }
}
