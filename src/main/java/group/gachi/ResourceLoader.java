package group.gachi;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

public class ResourceLoader {

    public static ResourceLoader instance;
    BufferedImage whitePieces, blackPieces, board, whitePiecesRaw, blackPiecesRaw, weapons;
    BufferedImage battleBackground, healthBarImg, blackBarIcon, whiteBarIcon, emptyHealthBar;
    BufferedImage picoFont, borderedPicoFont, buttonImage;

    public ResourceLoader(){
        instance = this;

        try{
            try {
                whitePieces = ImageIO.read(getClass().getResource("/WhitePiecesWithWeapons.png"));
                blackPieces = ImageIO.read(getClass().getResource("/BlackPiecesWithWeapons.png"));
                board = ImageIO.read(getClass().getResource("/board_plain_04.png"));

                whitePiecesRaw = ImageIO.read(getClass().getResource("/WhitePieces.png"));
                blackPiecesRaw = ImageIO.read(getClass().getResource("/BlackPieces.png"));
                weapons = ImageIO.read(getClass().getResource("/Weapons.png"));

                battleBackground = ImageIO.read(getClass().getResource("/BattleBackground.png"));
                healthBarImg = ImageIO.read(getClass().getResource("/HealthBar.png"));
                whiteBarIcon = ImageIO.read(getClass().getResource("/WhiteBarIcon.png"));
                blackBarIcon = ImageIO.read(getClass().getResource("/BlackBarIcon.png"));
                emptyHealthBar = ImageIO.read(getClass().getResource("/emptyHealthBar.png"));

                picoFont = ImageIO.read(getClass().getResource("/picoFont.png"));
                borderedPicoFont = ImageIO.read(getClass().getResource("/BorderedPicoFont.png"));
                buttonImage = ImageIO.read(getClass().getResource("/Button.png"));

            } catch (IOException e) {
                throw new RuntimeException("Error loading images");
            }
        }
        catch (IllegalArgumentException e){
            throw new RuntimeException("Error with error Error loading images");
        }
    }

    public BufferedImage getPiece(int id, boolean side) {
        if (id < 0) {
            return null;
        }

        if (side) {
            return whitePieces.getSubimage(id * 16,0,16,16);
        }
        return blackPieces.getSubimage(id * 16,0,16,16);
    }

    public BufferedImage getPieceRaw(int id, boolean side) {
        if (id < 0) {
            return null;
        }
        if (side) {
            return whitePiecesRaw.getSubimage(id * 16,0,16,16);
        }
        return blackPiecesRaw.getSubimage(id * 16,0,16,16);
    }
    public BufferedImage getWeaponImage(int id)
    {
        return weapons.getSubimage(id * 16,0,16,16);
    }
    public BufferedImage getPieceIcon(int id, boolean side) {
        if (id < 0) {
            return null;
        }
        if (side) {
            return whiteBarIcon.getSubimage(id * 16,0,16,16);
        }
        return blackBarIcon.getSubimage(id * 16,0,16,16);
    }
    public BufferedImage getLetter(char letter) {
        if ((int)letter < 65 || (int)letter > 90) {return null;}
        return picoFont.getSubimage(((int)letter - 65) * 3, 0, 3, 5);
    }

    public BufferedImage picoString(String string) {
        if (string.length() == 0) return null;
        int x = string.length() * 4;
        BufferedImage picoString = new BufferedImage(x, 5, BufferedImage.TYPE_INT_ARGB);
        Graphics g = picoString.getGraphics();

        for(int i = 0; i < string.length(); i++) {
            g.drawImage(getLetter(string.charAt(i)), (i) * 4, 0, null);
        }

        g.dispose();

        return picoString;
    }

    public BufferedImage getBorderedLetter(char letter) {
        if ((int)letter < 65 || (int)letter > 90) {return null;}
        return borderedPicoFont.getSubimage((((int)letter - 65) * 3) + 1, 0, 4, 7);
    }

    public BufferedImage borderedPicoString(String string) {
        int pixelWidth = 1 + 4*string.length();
        BufferedImage borderedPicoString = new BufferedImage(pixelWidth, 7, BufferedImage.TYPE_INT_ARGB);
        Graphics g = borderedPicoString.getGraphics();

        for (int i = 0; i < string.length(); i++) {
            g.drawImage(borderedPicoFont.getSubimage(0,0, 1,7), i*4, 0, null);
            g.drawImage(getBorderedLetter(string.charAt(i)), (i*4) + 1, 0, null);
        }

        g.drawImage(borderedPicoFont.getSubimage(0,0, 1,7), pixelWidth - 1, 0, null);
        return borderedPicoString;
    }
}
