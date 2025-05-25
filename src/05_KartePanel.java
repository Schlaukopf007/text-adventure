import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
// for Commit

public class KartePanel extends JPanel {
    private String aktuellerOrt = "Dorf";
    private int spielerX = 300, spielerY = 300;
    private final Map<String, Point> ortPositionen = new HashMap<>();
    private Timer bewegungsTimer;
    private int zielX, zielY;
    private int schrittX, schrittY;
    private int animFrame = 0;
    private Timer animTimer;
    private int wolkenX = 0;
    private int sonnenY = 30;
    private boolean sonneAufwaerts = true;
    private int flussOffset = 0;
    private boolean spielGewonnen = false;


    public KartePanel() {
        setBackground(new Color(150, 200, 150));

        ortPositionen.put("Dorf", new Point(300, 300));
        ortPositionen.put("Wald", new Point(300, 200));
        ortPositionen.put("Marktplatz", new Point(300, 100));
        ortPositionen.put("Höhle", new Point(400, 200));
        ortPositionen.put("Schloss", new Point(300, 20));

        bewegungsTimer = new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                spielerX += schrittX;
                spielerY += schrittY;
                animFrame++;
                if ((schrittX > 0 && spielerX >= zielX) || (schrittX < 0 && spielerX <= zielX)) {
                    spielerX = zielX;
                }
                if ((schrittY > 0 && spielerY >= zielY) || (schrittY < 0 && spielerY <= zielY)) {
                    spielerY = zielY;
                }
                if (spielerX == zielX && spielerY == zielY) {
                    bewegungsTimer.stop();
                }
                repaint();
            }
        });

        animTimer = new Timer(100, e -> {
            animFrame++;
            wolkenX = (wolkenX + 1) % getWidth();

            flussOffset = (flussOffset + 2) % 40; // Fluss animieren

            if (sonneAufwaerts) {
                sonnenY--;
                if (sonnenY <= 20) sonneAufwaerts = false;
            } else {
                sonnenY++;
                if (sonnenY >= 40) sonneAufwaerts = true;
            }
            repaint();
        });
        animTimer.start();
    }

    public void setOrt(String ort) {
        aktuellerOrt = ort;
        Point ziel = ortPositionen.getOrDefault(ort, new Point(300, 300));
        zielX = ziel.x;
        zielY = ziel.y;
        int distanzX = zielX - spielerX;
        int distanzY = zielY - spielerY;
        int schritte = 20;
        schrittX = distanzX / schritte;
        schrittY = distanzY / schritte;
        bewegungsTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Himmel, Sonne, Wolken
        g2.setColor(new Color(135, 206, 235));
        g2.fillRect(0, 0, getWidth(), getHeight() / 2);
        g2.setColor(Color.YELLOW);
        g2.fillOval(50, sonnenY, 60, 60);
        g2.setColor(Color.WHITE);
        g2.fillOval(120 + wolkenX % getWidth(), 40, 50, 30);
        g2.fillOval(160 + wolkenX % getWidth(), 50, 50, 30);

        // Hintergrund/Boden
        g2.setColor(new Color(150, 200, 150));
        g2.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 2);

        // Fluss (animiert)
        g2.setColor(new Color(70, 130, 180));
        for (int i = 0; i < getHeight(); i += 20) {
            g2.fillRect(100 + (int)(5 * Math.sin((i + flussOffset) * 0.2)), i, 40, 10);
        }

        // Bäume
        for (int i = 0; i < 10; i++) {
            int bx = 50 + i * 50;
            int by = 220 + (int)(5 * Math.sin((animFrame + i * 10) * 0.1));
            g2.setColor(new Color(101, 67, 33));
            g2.fillRect(bx + 10, by + 30, 10, 20);
            g2.setColor(new Color(34, 139, 34));
            g2.fillOval(bx, by, 30, 30);
        }

        // Bauernhäuser (Dorf)
        g2.setColor(new Color(200, 150, 100));
        g2.fillRect(260, 310, 30, 30);
        g2.fillRect(310, 310, 30, 30);
        g2.setColor(Color.RED);
        g2.fillPolygon(new int[]{260, 275, 290}, new int[]{310, 295, 310}, 3);
        g2.fillPolygon(new int[]{310, 325, 340}, new int[]{310, 295, 310}, 3);

        // Marktplatzstand
        g2.setColor(new Color(150, 75, 0));
        g2.fillRect(280, 90, 40, 20);
        g2.setColor(new Color(255, 100, 100));
        g2.fillRect(280, 80, 40, 10);

        // Höhle
        g2.setColor(new Color(70, 70, 70));
        g2.fillOval(400, 200, 40, 30);

        // Orte beschriften
        for (Map.Entry<String, Point> eintrag : ortPositionen.entrySet()) {
            Point p = eintrag.getValue();
            g2.setColor(Color.BLACK);
            g2.drawString(eintrag.getKey(), p.x - 20, p.y - 10);
        }

        // Männchen Schatten
        g2.setColor(new Color(0, 0, 0, 60));
        g2.fillOval(spielerX - 5, spielerY + 15, 20, 5);

        // Männchen
        g2.setColor(Color.RED);
        g2.fillOval(spielerX, spielerY, 15, 15);

        // Hut mit Feder
        g2.setColor(new Color(60, 30, 10));
        g2.fillRect(spielerX, spielerY - 5, 15, 5);
        g2.setColor(Color.BLUE);
        g2.drawLine(spielerX + 12, spielerY - 5, spielerX + 20, spielerY - 15);

        // Kobold vor der Höhle
        int koboldX = 410;
        int koboldY = 190;
        g2.setColor(new Color(0, 100, 0)); // Kobold-Farbe
        g2.fillOval(koboldX, koboldY, 15, 15); // Kopf
        g2.setColor(new Color(34, 139, 34)); // Körper
        g2.fillRect(koboldX + 2, koboldY + 15, 10, 15); // Körper

        // Feuerwerk
        if (spielGewonnen) {
            for (int i = 0; i < 10; i++) {
                int x = 100 + (int)(Math.random() * 400);
                int y = 100 + (int)(Math.random() * 200);
                g2.setColor(new Color((int)(Math.random() * 255),
                        (int)(Math.random() * 255),
                        (int)(Math.random() * 255)));
                g2.fillOval(x, y, 10, 10);
            }
        }
    }
}
