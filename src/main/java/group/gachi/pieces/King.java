package group.gachi.pieces;

import group.gachi.ChessBoard;

import java.awt.*;

public class King extends ChessPiece {

    private boolean hasMoved = false;
    private static King whiteKing;
    private static King blackKing;
    private int x, y;

    public King(ChessBoard board, boolean isWhite, String personalName) {
        super(board, isWhite, personalName);
        this.id = ChessBoard.KING;
        super.maxHealth = 80;
        super.health = maxHealth;
        this.x = 4;
        this.y = isWhite ? 0 : 7;
        if (isWhite) {
            whiteKing = this;
        } else blackKing = this;
    }

    public static King getKing(boolean isWhite) {
        return isWhite ? whiteKing : blackKing;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPos(Point point) {
        setPos(point.x, point.y);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean movePiece(int toX, int toY, int fromX, int fromY) {
        if (super.movePiece(toX, toY, fromX, fromY)) {
            if (Math.abs(fromX - toX) == 2) {
                int rookX = toX > fromX ? 7 : 0;
                Rook rook = (Rook) board.getPiece(rookX, fromY);
                board.setPiece(rookX, fromY, null);
                board.setPiece((fromX + toX) / 2, fromY, rook);
            }
            setPos(toX, toY);
            return hasMoved = true;
        }
        return false;
    }

    @Override
    public boolean isMoveValid(int toX, int toY, int fromX, int fromY) {
        // Make sure that the king is in positions from
        if (board.getPiece(fromX, fromY).getId() != id) return false;

        // Find the change in x and y
        int dx = toX - fromX;
        int dy = toY - fromY;


        if (dx == 0 && dy == 0) return false;
        // Make sure that the king is moving only one square
        if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
            return true;
        }

        return (Math.abs(dx) == 2 && dy == 0 && checkIfCastlingPossible(fromX, dx < 0));
    }

    public boolean checkIfCastlingPossible(int fromX, boolean isLeftCastling) {
        int rank = this.isWhite ? 0 : 7;
        int file = isLeftCastling ? 0 : 7;
        int dir = isLeftCastling ? 1 : -1;

        //Gets the piece in the relevant corner
        ChessPiece corn = board.getPiece(file, rank);

        if (corn == null || corn.getId() != ChessBoard.ROOK) return false;

        for (int i = isLeftCastling ? 1 : 6; i != fromX; i += dir) {
            if (board.getPiece(i, rank) != null) return false;
        }

        return !(((Rook) corn).hasMoved || this.hasMoved);
    }
}
