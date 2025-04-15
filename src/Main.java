import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        String aktuellerOrt = "";

        System.out.println("Welcome zum Spiel !");
        System.out.println("Du stehst vor einem düsteren Wald.");
        System.out.println("Was tust Du als nächstes ?");
        System.out.println("1 - In den Wald gehen");
        System.out.println("2 - Zurück ins Dorf gehen");

        input = scanner.nextLine();

        if (input.equals("1")) {
            System.out.println("Du betrittst den Wald. es ist dunkel und unheimlich ....");
            aktuellerOrt = "Wald";
        } else if (input.equals("2")) {
            System.out.println("Du kehrst um und gehst ins Dorf zurück");
            aktuellerOrt = "Dorf";
        } else {
            System.out.println("Ungültigr Eingabe. Das Abenteuer beginnt erst wenn du dich entschieden hast !!");
            System.exit(
                    0);
        }

        // Start erweiterte Spielwelt //

        boolean spielen = true;
        boolean hatBeeren = false;
        boolean hatGold = false;
        boolean hatAxt = false;
        boolean baumEntfernt = false;
        boolean koboldPlatt = false;

        while (spielen) {
            System.out.println("\nDu bist gerade in: " + aktuellerOrt);
            System.out.println("Was möchtest du tun ? ('gehe Norden', 'hilfe', 'gehe Süden', 'untersuche', 'beeren sammeln''ende')");

            input = scanner.nextLine();

            switch (aktuellerOrt) {
                case "Dorf":
                    if (input.equals("gehe Norden")) {
                        aktuellerOrt = "Wald";
                    } else if (input.equals("gehe Süden")) {
                        aktuellerOrt = "Marktplatz";
                    } else if (input.equals("untersuche")) {
                        System.out.println("Du siehst einen Schmied. Er verkauft dir eine Axt wenn du ihm 1 Gold bezahlst.");
                        if (hatGold && !hatAxt) {
                            System.out.println("Willst du die Axt kaufen und mit 1 Gold bezahlen ? (ja/nein)");
                            String entscheidung = scanner.nextLine().toLowerCase();
                            if (entscheidung.equals("ja")) {
                                hatGold = false;
                                hatAxt = true;
                                System.out.println("Du hast eine Axt erhalten !");
                            } else {
                                System.out.println("Du möchtest lieber das Gold behalten.");
                            }
                        } else if (!hatGold) {
                            System.out.println("Du hast kein Gold.");
                        } else if (hatAxt) {
                            System.out.println("Du besitzt schon eine Axt.");
                        }
                    }
                    break;

                case "Wald":
                    if (input.equals("gehe Süden")) {
                        aktuellerOrt = "Dorf";
                    } else if (input.equals("gehe Norden")) {
                        aktuellerOrt = "Wald";
                        System.out.println("Geh in den Wald und sammle Beeren !");
                    } else if (input.equals("beeren sammeln")) {
                        hatBeeren = true;
                        System.out.println("Du hast 10 Beeren gesammelt.");
                        /*aktuellerOrt = "Schule";
                        System.out.println("Du bist nun in der Schule. ");*/
                    } else if (input.equals("untersuche")) {
                        aktuellerOrt = "Wald";
                        System.out.println("Du untersuchst den Wald und entdeckst eine alte Tür in einem seltsamen Baum.");
                        System.out.println("Hineingehen ? (ja/nein)");
                        String aktion = scanner.nextLine().toLowerCase();

                        if (aktion.equals("ja")) {
                            System.out.println("Ein großer Baumstamm versperrt dir den Weg zur Höhle. Du brauchst eine Axt vom Schmied (Dorf).\nWenn du aber kein Gold hast, dann musst du deine Beeren dem Beerenmann verkaufen (Marktplatz)\");");
                            System.out.println("Was willst du tun ?('beeren sammeln' / 'axt kaufen' /'axt benutzen')");
                            String aktion2 = scanner.nextLine().toLowerCase();

                            if (aktion2.equals("axt kaufen")) {
                                System.out.println("Geh zum Marktplatz und verkaufe deine Beeren für Gold.");
                            } else if (aktion2.equals("axt benutzen")) {
                                if (hatAxt) {
                                    baumEntfernt = true;
                                    System.out.println("Du haust den Baum, der den Eingang zur Höhle versperrt, mit der Axt weg und gehst hinein");
                                    System.out.println("Plötzlich erscheint ein Kobold der die Tür bewacht. Du kannst die Höhle nicht untersuchen.");
                                    System.out.println("Was willst du tun ? ('gegen den Kobold kämpfen' / 'davon laufen' / 'untersuchen')");
                                    String aktion3 = scanner.nextLine().toLowerCase();

                                    if (aktion3.equals("gegen den Kobold kämpfen")) {
                                    aktuellerOrt = "Höhle";
                                    koboldPlatt = true;
                                    System.out.println("Du kämpfst gegen den Kobold und machst ihn platt !!\nAus dem Hut des platten Kobolds fliegt ein Schlüssel raus !");
                                    } else if (aktion3.equals("davon laufen")) {
                                        System.out.println("GAME OVER.....GAME OVER.....GAME OVER");
                                        spielen = false;
                                    } else if (aktion3.equals("untersuche")) {
                                        if (koboldPlatt) {
                                            aktuellerOrt = "Höhle";
                                            System.out.println("Du schließt die Tür auf. Du gehst in die Höhle und siehst etwas blitzen.\nDu hast einen Schatz gefunden !\nGEWONNEN !! Du erhältst einen Preis !!\")");
                                            spielen = false;
                                        } else {
                                            System.out.println("Der Kobold versperrt dir den Weg! Du musst ihn erst besiegen.");
                                        }
                                    } else {
                                        System.out.println("Ungültige Aktion.");
                                    }
                                } else {
                                    System.out.println("Du hast noch keine Axt. Du kommst hier nicht rein !");
                                }
                            }
                        } else {
                            System.out.println("Du gehst nicht durch die geheimnisvolle Tür, sondern bleibst lieber im Wald.");
                        }
                    }
                    break;

            case "Marktplatz":
                if (input.equals("gehe Norden")) {
                    aktuellerOrt = "Dorf";
                    System.out.println("Du bist im Dorf.");
                    } else if (input.equals("untersuche")) {
                        if (hatBeeren) {
                            System.out.println("Der Beerenmann hat dir deine Beeren abgekauft. Du bekommst dafür 10 Gold.");
                            hatBeeren = false;
                            hatGold = true;
                        } else {
                            System.out.println("Du hast keine Beeren dabei. Ich kann dir kein Gold geben.");
                        }
                    } /*else {
                        System.out.println("Hier kannst du nur nach Norden gehen.");
                    }*/
                    break;
            }
        }
    }
}








                       /* } else {
                            System.out.println("Du gehst nicht durch die geheimnisvolle Tür, sondern bleibst lieber im Wald.");
                        }
                    } else {
                        if (aktion3.equals("untersuche")) {
                        }
                        aktuellerOrt = "Höhle";
                        koboldPlatt =true;
                        System.out.println("Du schließt die Tür auf. Du gehst in die Höhle und siehst etwas blitzen. \nDu hast einen Schatz gefunden !\nGEWONNEN !! Du erhältst einen Preis !!");
                        }


                    break;*/



                /*case "Schule":
                    if(input.equals("gehe Süden")) {
                        aktuellerOrt = "Wald";
                        System.out.println("Du bist nun wieder im Wald.");
                    }
                    break;*/
                    /*default:
                    System.out.println("Du bist an einem unbekannten Ort.");
                    break;
            }
            if (input.equals("ende")) {
                spielen = false;
                System.out.println("Das hat Spaß gemacht ! Danke fürs Spielen !");
            }
            if (input.equals("hilfe")) {
                System.out.println("Befehle: gehe Norden / Süden, hilfe, ende, untersuche");
            }
                    if (input.equals("untersuche")) {
                    switch (aktuellerOrt) {
                        */
                        /*case "Wald":
                            System.out.println("Du untersuchst den Wald und entdeckst eine alte Tür in einem seltsamen Baum.");
                            System.out.println("Hineingehen ? (ja/nein)");
                            String entscheidung = scanner.nextLine().toLowerCase();*/

                            /*if (entscheidung.equals("ja")) {
                                System.out.println("Du gehst durch die Tür und befindest dich nun in einer dunklen Höhle. Du hast einen geheimen Ort entdeckt !!");
                                aktuellerOrt = "Höhle";*/
                        /*if (entscheidung.equals("nein"));
                        System.out.println("Du gehst nicht durch die geheimnisvolle Tür, sondern bleibst lieber im Wald.");
                    }
                    break;*/

                    /*case "Marktplatz":
                        System.out.println("Der Marktplatz ist leer. Ein Zettel flattert im Wind an einem Brunnen. Darauf steht: 'Geh nach Norden in den Wald und suche den Schatz !'");
                        break;*/

                    /*case "Höhle":
                        System.out.println("In der Höhle findest du eine Truhe mit einem Schatz. Du hast das Spiel gewonnen !! Toll !!");
                        System.out.println("Spiel wird beendet.");
                        spielen = false;
                        break;*/

                    /*default:
                        System.out.println("Hier gibt es nichts zu entdecken.");
                        break;*/


                // scanner.close(); //













