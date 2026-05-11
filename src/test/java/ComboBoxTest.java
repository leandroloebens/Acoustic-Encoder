import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class ComboBoxTest {

    static List<String> itensOriginais = List.of(
            "Java",
            "JavaScript",
            "Python",
            "C",
            "C++",
            "C#",
            "Rust",
            "Ruby"
    );

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        JComboBox<String> combo = new JComboBox<>();
        combo.setEditable(true);

        for (String s : itensOriginais) {
            combo.addItem(s);
        }

        JTextField editor =
                (JTextField) combo.getEditor().getEditorComponent();

        editor.getDocument().addDocumentListener(new DocumentListener() {

            void filtrar() {

                String texto = editor.getText();

                combo.removeAllItems();

                for (String item : itensOriginais) {
                    if (item.toLowerCase().contains(texto.toLowerCase())) {
                        combo.addItem(item);
                    }
                }

                editor.setText(texto);

                combo.showPopup();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrar();
            }
        });

        frame.add(combo);

        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

