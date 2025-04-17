import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// For Commit

public class SpielGUI {
    private KartePanel kartePanel;
    private JFrame frame;
    private JTextArea textArea;
    private JTextField eingabeFeld;
    private Spiel spiel;


    public SpielGUI() {
        kartePanel = new KartePanel();
        // Erstelle das Fenster (JFrame)
        frame = new JFrame("Abenteuer-Spiel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Erstelle den Textbereich (für die Spieltexte)
        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);

        // Größeres Eingabefeld
        eingabeFeld = new JTextField();
        eingabeFeld.setFont(new Font("SansSerif", Font.PLAIN, 18));
        eingabeFeld.setPreferredSize(new Dimension(400, 40));

        eingabeFeld.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                String eingabe = eingabeFeld.getText();
                eingabeFeld.setText("");
                zeigeText("> " + eingabe);
                spiel.verarbeiteEingabe(eingabe); // Verarbeite die Eingabe des Spielers
            }
        });

        kartePanel.setPreferredSize(new Dimension(400, 400));




        // Erstelle ein Panel, um alles zusammenzusetzen
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scroll, BorderLayout.NORTH);  // Textbereich oben
        panel.add(eingabeFeld, BorderLayout.SOUTH);  // Eingabefeld unten
        panel.add(kartePanel, BorderLayout.CENTER);  // Karte in der Mitte

        frame.getContentPane().add(panel);
        frame.setVisible(true);

        spiel = new Spiel(this);
    }



    // Diese Methode wird verwendet, um den Text im Textbereich anzuzeigen
    public void zeigeText(String text) {
        textArea.append(text + "\n");
    }

    // Diese Methode wird verwendet, um die Karte zu aktualisieren, wenn sich der Ort ändert
    public void aktualisiereKarte(String ort) {
        kartePanel.setOrt(ort);  // Setze den aktuellen Ort in der Karte
    }
}

