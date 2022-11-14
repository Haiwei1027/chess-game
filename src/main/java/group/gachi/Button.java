package group.gachi;

import java.awt.*;
import java.awt.image.BufferedImage;

class Button {
    private BufferedImage label;
    private Rectangle rect;
    private BufferedImage background;
    private BufferedImage foreground;
    private BattleScreen.ButtonAction action;
    private boolean clicked;
    private boolean hovered;

    public Button(String label, Rectangle rect, BattleScreen.ButtonAction action) {
        this(label, rect, action, null);
    }

    public Button(String label, Rectangle rect, BattleScreen.ButtonAction action, BufferedImage background) {
        this.label = ResourceLoader.instance.picoString(label);
        this.rect = rect;
        this.action = action;
        this.background = background;
    }

    public void drawButton(int startX, int startY, Graphics g) {
        int x = startX + rect.x;
        int y = startY + rect.y;

        unClick();

        if (background != null) {
            g.drawImage(background, x, y, rect.width, rect.height, null);
        }

        if (clicked) {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, rect.width, rect.height);
        } else if (hovered) {
            g.setColor(Color.ORANGE);
            g.fillRect(x, y, rect.width, rect.height);
        }
        if (foreground != null) g.drawImage(foreground, x, y, rect.width, rect.height, null);
        if (label != null) g.drawImage(label, x, y, rect.width, rect.height, null);
    }

    public void setForeground(BufferedImage foreground) {
        this.foreground = foreground;
    }

    public boolean hovered() {
        return hovered;
    }

    private boolean isInside(Point point) {
        return rect.contains(point);
    }

    public void checkHover(Point mouse) {
        hovered = isInside(mouse);
    }

    public void tryClick(Point mouse) {
        if (isInside(mouse) && !clicked) {
            clicked = true;
            action.run();
        }
    }

    public void unClick() {
        clicked = false;
    }
}