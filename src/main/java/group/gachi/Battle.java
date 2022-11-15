package group.gachi;
import group.gachi.pieces.ChessPiece;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Battle
{
    private ChessPiece piece1, piece2;
    private int damageMean = 10, damageSigma = 3;
    private boolean lastTurn;
    public BufferedImage image;

    public Battle(ChessPiece piece1, ChessPiece piece2)
    {
        this.piece1 = piece1;
        this.piece2 = piece2;
    }
    public Battle(ChessPiece piece1, ChessPiece piece2, boolean lastTurn)
    {
        this.piece1 = piece1;
        this.piece2 = piece2;
        this.lastTurn = lastTurn;
    }

    public void Attack()
    {
        int damage;
        Random roll = new Random();
        damage = (int)(roll.nextGaussian()*damageSigma + damageMean);
        System.out.println(damage);
        piece2.damage(damage);
    }
    public void Wound()
    {
        piece2.wound();
    }
    public ChessPiece getPiece1() {return piece1;}
    public ChessPiece getPiece2() {return piece2;}

    public void paint()
    { //buffers the background, the bars, and the names.
        if (image == null)
        {
            image = new BufferedImage(142, 142, BufferedImage.TYPE_INT_ARGB);
        } else {image.flush();}
        Graphics g = image.getGraphics();
        g.drawImage(ResourceLoader.instance.battleBackground, 0, 0, null);
        g.drawImage(ResourceLoader.instance.healthBarImg, 7, 7, null);
        g.drawImage(ResourceLoader.instance.healthBarImg, 87, 85, null);
        g.drawImage(ResourceLoader.instance.emptyHealthBar, 12, 26, null);
        g.drawImage(ResourceLoader.instance.emptyHealthBar, 92, 104, null);

        g.setColor(Color.BLACK);
        g.fillRect(12, 26, (int)(40*(piece2.getHealthPercentage())), 2);
        g.fillRect(92, 104, (int)(40*(piece1.getHealthPercentage())), 2);


        g.drawImage(ResourceLoader.instance.picoString(piece2.getName()), 29, 20, null);
        g.drawImage(ResourceLoader.instance.picoString(piece1.getName()), 109, 98, null);

        g.drawImage(ResourceLoader.instance.getPieceIcon(piece2.getId(),piece2.isWhite()), 12, 10, null);
        g.drawImage(ResourceLoader.instance.getPieceIcon(piece1.getId(),piece1.isWhite()), 92, 88, null);


        g.dispose();
    }

    public boolean isLastTurn() { return lastTurn;}
}
