// For Commit
public class Spiel {
    private void zeigeKarte() {

        gui.zeigeText("""
        
        
         
         [ Schloss ]
             ↑
         [ Höhle ]
             ↑
         [ Wald ]
     ↑           ↑
[Dorf] ←→ [Marktplatz]  


        
    Du befindest dich gerade in: """ + aktuellerOrt);


    }

    private SpielGUI gui;
    private String aktuellerOrt;
    private boolean spielen = true;

    private boolean hatBeeren = false;
    private boolean hatGold = false;
    private boolean hatAxt = false;
    private boolean baumEntfernt = false;
    private boolean koboldPlatt = false;
    private boolean warteAufStartAntwort = true;
    private boolean warteAufAxtAntwort = false;
    private boolean warteAufBaumAntwort = false;
    private boolean warteAufKampfAntwort = false;
    private boolean spielGewonnen = false;


    /*public void setSpielGewonnen(boolean gewonnen) {
        spielGewonnen = gewonnen;
        repaint();
    }*/

    // Konstruktor, der die GUI übergibt
    public Spiel(SpielGUI gui) {
        this.gui = gui;
    }

    public void start() {
        aktuellerOrt = "Dorf";
        gui.zeigeText("Willkommen im Spiel!");
        gui.zeigeText("Du stehst vor einem düsteren Wald.");
        gui.zeigeText("Was tust du als nächstes?");
        gui.zeigeText("1 - In den Wald gehen");
        gui.zeigeText("2 - Zurück ins Dorf gehen");
        gui.zeigeText("\nMögliche Befehle: '1', '2'");
        gui.aktualisiereKarte(aktuellerOrt);
    }


    // Methode, um Eingaben zu verarbeiten
    public void verarbeiteEingabe(String eingabe) {
        if (eingabe.equalsIgnoreCase("karte")) {
            zeigeKarte();
            return;
        }
        if (warteAufStartAntwort) {
            if (eingabe.equals("1")) {
                aktuellerOrt = "Wald";
                gui.aktualisiereKarte(aktuellerOrt); // <-- Karte aktualisieren
                gui.zeigeText("Du betrittst den Wald. Es ist dunkel und unheimlich...");
            } else if (eingabe.equals("2")) {
                aktuellerOrt = "Dorf";
                gui.aktualisiereKarte(aktuellerOrt); // <-- Karte aktualisieren
                gui.zeigeText("Du kehrst um und gehst ins Dorf zurück.");
            } else {
                gui.zeigeText("Bitte gib '1' oder '2' ein.");
                return;
            }
            gui.zeigeText("Du kannst dich jetzt frei bewegen. Nutze z.B. 'gehe norden' oder 'untersuche'.");
            warteAufStartAntwort = false;
            zeigeMoeglicheBefehle();
            return;
        }

        if (warteAufAxtAntwort) {
            if (eingabe.equals("ja")) {
                if (hatGold && !hatAxt) {
                    hatGold = false;
                    hatAxt = true;
                    gui.zeigeText("Du hast eine Axt erhalten!");
                } else {
                    gui.zeigeText("Du hast kein Gold oder besitzt schon eine Axt.");
                }
            } else {
                gui.zeigeText("Du kaufst keine Axt.");
            }
            warteAufAxtAntwort = false;
            zeigeMoeglicheBefehle();
            return;
        }

        if (warteAufBaumAntwort) {
            if (eingabe.equals("ja")) {
                if (hatAxt) {
                    baumEntfernt = true;
                    gui.zeigeText("Du entfernst den Baumstamm und betrittst die Höhle.");
                    aktuellerOrt = "Höhle";
                } else {
                    gui.zeigeText("Du hast keine Axt. Sammle Beeren und verkaufe sie auf dem Marktplatz.");
                }
            } else {
                gui.zeigeText("Du gehst nicht durch die Tür.");
            }
            warteAufBaumAntwort = false;zeigeMoeglicheBefehle();
            gui.aktualisiereKarte(aktuellerOrt);
            return;
        }

        if (warteAufKampfAntwort) {
            if (eingabe.equals("kämpfen")) {
                koboldPlatt = true;
                gui.zeigeText("Du besiegst den Kobold! Ein Schlüssel fällt aus seinem Hut.");
                gui.zeigeText("Du findest einen Schatz. 🎉 GEWONNEN!");
                spielen = false;
            } else {
                gui.zeigeText("Du fliehst aus der Höhle. GAME OVER.");
                spielen = false;
            }
            warteAufKampfAntwort = false;
            return;
        }

        switch (aktuellerOrt) {
            case "Dorf":
                ortDorf(eingabe);
                break;
            case "Wald":
                ortWald(eingabe);
                break;
            case "Marktplatz":
                ortMarktplatz(eingabe);
                break;
            case "Höhle":
                ortHoehle();
                break;
            default:
                gui.zeigeText("Ungültiger Ort.");
        }

        zeigeMoeglicheBefehle();

        if (eingabe.equals("ende")) {
            spielen = false;
            gui.zeigeText("Du hast das Spiel beendet.");
        }
    }

    private void ortDorf(String input) {
        switch (input) {
            case "gehe norden":
                aktuellerOrt = "Wald";
                gui.aktualisiereKarte(aktuellerOrt);
                gui.zeigeText("Du gehst in den Wald.");
                break;

            case "gehe süden":
                aktuellerOrt = "Marktplatz";
                gui.aktualisiereKarte(aktuellerOrt);
                gui.zeigeText("Du bist im Marktplatz angekommen.");
                break;

            case "untersuche":
                gui.zeigeText("Du siehst einen Schmied. Er verkauft dir eine Axt für 1 Gold.");
                if (hatGold && !hatAxt) {
                    gui.zeigeText("Willst du die Axt kaufen? (ja/nein)");
                    warteAufAxtAntwort = true;
                    return;
                } else if (hatAxt) {
                    gui.zeigeText("Du besitzt bereits eine Axt.");
                } else {
                    gui.zeigeText("Du hast kein Gold.");
                }
                break;

            default:
                gui.zeigeText("Unbekannte Aktion.");
                gui.zeigeText("Mögliche Befehle: 'gehe norden', 'gehe süden', 'untersuche', 'beeren sammeln', 'karte', 'ende'");
        }
    }

    private void ortWald(String input) {
        switch (input) {
            case "gehe süden":
                aktuellerOrt = "Dorf";
                gui.aktualisiereKarte(aktuellerOrt);
                gui.zeigeText("Du gehst zurück ins Dorf.");
                break;
            case "gehe norden":
                aktuellerOrt = "Marktplatz";
                gui.aktualisiereKarte(aktuellerOrt);
                gui.zeigeText("Du verlässt den Wald und kommst zum Marktplatz.");
                break;
            case "beeren sammeln":
                hatBeeren = true;
                gui.aktualisiereKarte(aktuellerOrt);
                gui.zeigeText("Du hast 10 Beeren gesammelt.");
                break;
            case "untersuche":
                untersucheWald();
                break;
            default:
                gui.zeigeText("Unbekannte Aktion im Wald.");
                gui.zeigeText("Mögliche Befehle: 'gehe norden', 'gehe süden', 'untersuche', 'beeren sammeln',  'karte', 'ende'");
        }
    }

    private void untersucheWald() {
        gui.zeigeText("Du entdeckst eine alte Tür in einem Baum.");
        if (!baumEntfernt) {
            gui.zeigeText("Ein Baumstamm versperrt den Weg. Axt benutzen? (ja/nein)");
            warteAufBaumAntwort = true;
        } else {
            gui.zeigeText("Die Tür ist frei. Du gehst in die Höhle.");
            aktuellerOrt = "Höhle";
            gui.aktualisiereKarte(aktuellerOrt); // <-- Karte aktualisieren
        }
    }

    private void ortMarktplatz(String input) {
        switch (input) {
            case "gehe süden":
                aktuellerOrt = "Wald";
                gui.aktualisiereKarte(aktuellerOrt); // <-- Karte aktualisieren
                gui.zeigeText("Du gehst zurück in den Wald.");
                break;
            case "gehe norden":
                aktuellerOrt = "Dorf";
                gui.aktualisiereKarte(aktuellerOrt); // <-- Karte aktualisieren
                break;
            case "untersuche":
                if (hatBeeren) {
                    hatBeeren = false;
                    hatGold = true;
                    gui.zeigeText("Der Beerenmann kauft deine Beeren. Du erhältst 10 Gold.");
                } else {
                    gui.zeigeText("Du hast keine Beeren zum Verkaufen.");
                }
                break;
            default:
                gui.zeigeText("Hier gibt es nicht viel zu tun.");
                gui.zeigeText("Mögliche Befehle: 'gehe norden', 'gehe süden', 'untersuche', 'beeren sammeln', 'karte', 'ende'");
        }
    }

    private void ortHoehle() {
        if (!koboldPlatt) {
            gui.zeigeText("Ein Kobold erscheint! Kämpfen oder fliehen?");
            warteAufKampfAntwort = true;
            // Hier kann man Eingaben für "kämpfen" oder "fliehen" abfragen
        } else {
            gui.zeigeText("Der Kobold ist besiegt. Du findest einen Schatz. 🎉 GEWONNEN!");
            spielen = false;
        }
    }

        private void zeigeMoeglicheBefehle() {
        gui.zeigeText(""); // Leerzeile für bessere Lesbarkeit
        switch (aktuellerOrt) {
            case "Dorf" -> gui.zeigeText("Mögliche Befehle: 'gehe norden', 'untersuche', 'karte', 'ende'");
            case "Wald" -> gui.zeigeText("Mögliche Befehle: 'gehe süden', 'beeren sammeln', 'gehe norden', 'untersuche', 'karte', 'ende'");
            case "Marktplatz" -> gui.zeigeText("Mögliche Befehle: 'gehe norden', 'untersuche', 'gehe süden', 'karte', 'ende'");
            case "Höhle" -> {
                if (!koboldPlatt) {
                    gui.zeigeText("Mögliche Befehle: 'kämpfen', 'fliehen'");
                }
            }
        }
    }
}
