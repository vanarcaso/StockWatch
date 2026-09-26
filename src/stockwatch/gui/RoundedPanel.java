package stockwatch.gui;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class RoundedPanel extends JPanel {

    private final int radius;
    private final Color backgroundColor;

    public RoundedPanel(int radius, Color backgroundColor) {
        this.radius = radius;
        this.backgroundColor = backgroundColor;

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D =
                (Graphics2D) graphics.create();

        graphics2D.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        graphics2D.setColor(backgroundColor);

        graphics2D.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                radius,
                radius
        );

        graphics2D.dispose();

        super.paintComponent(graphics);
    }
}