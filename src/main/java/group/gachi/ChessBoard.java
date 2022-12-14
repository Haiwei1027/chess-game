package group.gachi;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;

import group.gachi.pieces.*;

public class ChessBoard { // pawn, knight, rook, bishop, king, queen

    public final static int PAWN = 0, KNIGHT = 1, ROOK = 2, BISHOP = 3, KING = 4, QUEEN = 5;

    private String[] personalNames = new String[]{"MESLET", "PODREY", "BINMAN", "NARSI", "KEVIN", "AMLEIA", "SORSON",
            "JAMES", "RORY", "CARWYN", "MAISEI", "BANNED", "VANSYR", "RERTY", "NEYSHA", "BORIS", "JEOFF", "CASSOR",
            "STEVE", "TOVER", "CAER", "GARRY", "HAIWEI", "CAMI", "ISAAC", "SHAH", "VIVIAN", "BETHAN", "BOI", "MICU",
            "ROMAN", "XANDER", "ARIAN", "DERK", "POEL", "GOLEM", "DREW", "CLAUDE", "CLEO"};
    private final int size = 8;

    private ChessScreen screen;
    public BufferedImage image;
    private Point selected;
    private boolean isDragging = false;

    private Point defenderSpot, attackerSpot;

    private Point pawnToBePromoted = null;
    private int sideInCheck = -1;
    private boolean nextSideToMove = true;

    private boolean moveIsEnPassant = true;
    private boolean enPassantAttackerIsWhite;
    private Point enPassantAttackTo;

    //HashMap to keep track and quickly access every non-empty point
    //Key - co-ordinate, value - id
    public HashMap<Point, ChessPiece> nonEmptySpaces = new HashMap<>();
    public HashMap<Point, ChessPiece> board = new HashMap<>();

    private final Color highlightColor = new Color(255, 255, 0, 128);
    private final Color dangerColor = new Color(255, 0, 0, 128);
    private final Color whiteBackgroundHPColor = new Color(193, 102, 107);
    private final Color blackBackgroundHPColor = new Color(177, 182, 149);
    private final Color whiteHPColor = new Color(103, 148, 54);
    private final Color blackHPColor = new Color(241, 115, 0);

    public ChessBoard(ChessScreen screen) {
        this.screen = screen;
        resetBoard();
        paint();
    }

    public int getSize() {
        return size;
    }

    public void mouseUp(Point location) {
        if (selected == null) return;
        if (location.equals(selected) && !isDragging) {
            if (getPiece(location.x, location.y) != null) {
                selectPiece(location);
            } else {
                selected = null;
            }
            return;
        }
        dropPiece(location);
    }

    public void mouseDown(Point location) {
        if (selected == null) {
            selectPiece(location);
        } else {
            if (!dropPiece(location)) {
                selectPiece(location);
            }
        }
    }

    private void selectPiece(Point location) {
        if (getPiece(location) == null) return;
        if (pawnToBePromoted != null) return;
        if (getPiece(location).isWhite() != nextSideToMove) return; //cannot select the opponent pieces
        selected = location;

        paint();
    }

    public void setDragging(boolean dragging) {
        boolean changed = isDragging != dragging;
        isDragging = dragging;
        if (changed) paint();
    }

    //If not draw defenderPiece is winner, attackerPiece is loser
    public void battleFinished(ChessPiece defenderPiece, ChessPiece attackerPiece) {
        if (moveIsEnPassant && defenderPiece.isWhite() == enPassantAttackerIsWhite && attackerPiece == null) {
            setPiece(new Point(defenderSpot.x, defenderPiece.isWhite() ? defenderSpot.y + 1 : defenderSpot.y - 1), defenderPiece);
        } else if (moveIsEnPassant) {
            setPiece(defenderSpot, defenderPiece);
            setPiece(enPassantAttackTo, null);
        } else {
            setPiece(defenderSpot, defenderPiece);
        }
        setPiece(attackerSpot, attackerPiece);
        defenderSpot = null;
        attackerSpot = null;
        paint();
    }

    private boolean dropPiece(Point location) {
        ChessPiece attacker = getPiece(selected);
        Point enemyLocation = location;
        moveIsEnPassant = false;

        if (attacker.getId() == ChessBoard.PAWN) {
            if (((Pawn) attacker).enPassant && Math.abs(selected.x - location.x) == 1) {
                enemyLocation = new Point(location.x, attacker.isWhite() ? location.y - 1 : location.y + 1);
                moveIsEnPassant = true;
                enPassantAttackerIsWhite = attacker.isWhite();
                enPassantAttackTo = location;
            }
        }
        ChessPiece defender = getPiece(enemyLocation);

        boolean moved = getPiece(selected).movePiece(location.x, location.y, selected.x, selected.y);

        if (moved) {
            if (!isPromoting()) {
                nextSideToMove = !nextSideToMove;
            }
            if (defender != null) {
                if (defender.isEnemy(attacker)) {
                    Main.enterBattle(new Battle(attacker, defender));
                    defenderSpot = enemyLocation;
                    attackerSpot = selected;
                }
            }
        }

        selected = null;
        setDragging(false);

        paint();

        return moved;
    }

    private void shuffleNames() {
        // Shuffles the names in arrays to randomize the names of the pieces
        for (int i = 0; i < personalNames.length; i++) {
            int randomIndex = (int) (Math.random() * personalNames.length);
            String temp = personalNames[i];
            personalNames[i] = personalNames[randomIndex];
            personalNames[randomIndex] = temp;
        }
    }

    public void resetBoard() {
        shuffleNames(); // Randomising the names about to be used
        int j = 0;
        for (int i = 0; i < 8; i++) {
            board.put(new Point(i, 1), new Pawn(this, true, personalNames[j++]));
            board.put(new Point(i, 6), new Pawn(this, false, personalNames[j++]));
        }
        for (int i = 0; i < 2; i++) {
            board.put(new Point(0, i * 7), new Rook(this, i == 0, personalNames[j++]));
            board.put(new Point(1, i * 7), new Knight(this, i == 0, personalNames[j++]));
            board.put(new Point(2, i * 7), new Bishop(this, i == 0, personalNames[j++]));
            board.put(new Point(3, i * 7), new Queen(this, i == 0, personalNames[j++]));
            board.put(new Point(4, i * 7), new King(this, i == 0, personalNames[j++]));
            board.put(new Point(5, i * 7), new Bishop(this, i == 0, personalNames[j++]));
            board.put(new Point(6, i * 7), new Knight(this, i == 0, personalNames[j++]));
            board.put(new Point(7, i * 7), new Rook(this, i == 0, personalNames[j++]));
        }

        for (int i = 0; i < 8; i++) {
            for (j = 2; j < 6; j++) {
                board.put(new Point(i, j), null);
            }
        }

        setInitialNonEmptySpots();
        paint();
    }

    public void setCheck(int sideChecked) {
        sideInCheck = sideChecked;
    }

    public boolean onBoard(int x, int y) {
        return x >= 0 && x < getSize() && y >= 0 && y < getSize();
    }

    public void awaitPromotion(int x, int y) {
        pawnToBePromoted = new Point(x, y);
        screen.updatePromotionButtons();
    }

    public void promotionDecided(int choice) {
        if (((Pawn) getPiece(pawnToBePromoted)).promote(pawnToBePromoted.x, pawnToBePromoted.y, choice)) {
            pawnToBePromoted = null;
            nextSideToMove = !nextSideToMove;
        }
        paint();
    }

    public void setPiece(Point point, ChessPiece piece) {
        setPiece(point.x, point.y, piece);
    }

    public void setPiece(int x, int y, ChessPiece piece) {
        if (!onBoard(x, y)) {
            return;
        }

        board.put(new Point(x, y), piece);

        //Updates nonEmptySpaces hash map
        if (piece != null) insertIntoNonEmpty(x, y);
        else removeFromNonEmpty(x, y);
    }

    public ChessPiece getPiece(Point point) {
        return board.get(point);
    }

    public ChessPiece getPiece(int x, int y) {
        if (!onBoard(x, y)) {
            return null;
        }

        return board.get(new Point(x, y));
    }

    public boolean isDragging() {
        return selected != null && isDragging;
    }

    public boolean isPromoting() {
        return pawnToBePromoted != null;
    }

    public Point getPromotion() {
        return pawnToBePromoted;
    }

    public BufferedImage getSelectedSprite() {
        ChessPiece piece = board.get(selected);
        if (piece == null) return null;
        BufferedImage sprite;
        sprite = ResourceLoader.instance.getPiece(piece.getId(), piece.isWhite());
        return sprite;
    }

    public Point getSelected() {
        return selected;
    }

    public boolean isWhiteNextToMove() {
        return nextSideToMove;
    }

    public void paint() {
        if (image == null) {
            image = new BufferedImage(142, 142, BufferedImage.TYPE_INT_RGB);
        } else {
            image.flush();
        }

        Graphics g = image.getGraphics();
        g.drawImage(ResourceLoader.instance.board, 0, 0, null);

        g.setColor(new Color(150,162,179));
        g.fillRect(7,(nextSideToMove ? 141 : 1),8*16,1);

        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                ChessPiece piece = board.get(new Point(x, y));
                BufferedImage sprite;
                sprite = piece != null ? ResourceLoader.instance.getPiece(piece.getId(), piece.isWhite()) : null;



                if (selected != null && selected.equals(new Point(x, y))) {
                    g.setColor(highlightColor);
                    g.fillRect(x * 16 + 7, (7 - y) * 16 + 7, 16, 16);

                    if (isDragging) continue;
                }


                g.drawImage(sprite, x * 16 + 7, (7 - y) * 16 + 7, null);

            }
        }
        g.setColor(dangerColor);

        if (sideInCheck != -1) {
            g.fillRect(King.getKing(sideInCheck == 1).getX() * 16 + 7, (7 - King.getKing(sideInCheck == 1).getY()) * 16 + 7, 16, 16);
        }

        if (selected == null) return;

        ChessPiece selectedPiece = board.get(selected);
        if (selectedPiece != null) {
            int x = selected.x, y = selected.y;
            g.setColor(selectedPiece.isWhite() ? whiteBackgroundHPColor : blackBackgroundHPColor);
            g.fillRect(x * 16 + 8, (7 - y) * 16 + 7, 14, 1);

            g.setColor(selectedPiece.isWhite() ? whiteHPColor : blackHPColor);
            g.fillRect(x * 16 + 8, (7 - y) * 16 + 7, (int) (14 * selectedPiece.getHealth() / selectedPiece.getMaxHealth()), 1);

            for (Point p : selectedPiece.getValidMoves(selected.x, selected.y)) {
                if (p == null) continue;
                g.setColor(highlightColor);
                g.fillRect(p.x * 16 + 11, (7 - p.y) * 16 + 11, 8, 8);
            }
        }
        g.dispose();
    }

    public void setInitialNonEmptySpots() {
        for (Point point : nonEmptySpaces.keySet()) {
            nonEmptySpaces.remove(point);
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 2; j++) {
                insertIntoNonEmpty(i, j);
            }
            for (int j = 7; j > 5; j--) {
                insertIntoNonEmpty(i, j);
            }
        }
    }

    public void insertIntoNonEmpty(int x, int y) {
        nonEmptySpaces.put(new Point(x, y), getPiece(x, y));
    }

    public void removeFromNonEmpty(int x, int y) {
        nonEmptySpaces.remove(new Point(x, y));
    }
}
