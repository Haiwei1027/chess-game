package group.gachi;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceLoader {

    public static ResourceLoader instance;
    BufferedImage whitePieces,blackPieces,board;

    public ResourceLoader(){
        instance = this;

        try{
            try {
                whitePieces = ImageIO.read(getClass().getResource("/WhitePiecesWithWeapons.png"));
                blackPieces = ImageIO.read(getClass().getResource("/BlackPiecesWithWeapons.png"));
                board = ImageIO.read(getClass().getResource("/board_plain_04.png"));
            } catch (IOException e) {
                throw new RuntimeException("oops");
            }
        }
        catch (IllegalArgumentException e){
            throw new RuntimeException("no");
        }
    }

    public BufferedImage getPiece(int id, boolean side) {
        if (id < 0){
            return null;
        }
        if (side){
            return whitePieces.getSubimage(id * 16,0,16,16);
        }
        return blackPieces.getSubimage(id * 16,0,16,16);
    }
}
