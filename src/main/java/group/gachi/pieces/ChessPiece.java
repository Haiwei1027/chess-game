package group.gachi.pieces;

import group.gachi.ChessBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class ChessPiece {

    protected int id;
    protected boolean isWhite;

    protected ChessBoard board;
    //combat related variables
    protected String personalName;
    int health;

    public ChessPiece(ChessBoard board, boolean isWhite, String personalName ) {
        this.board = board;
        this.isWhite = isWhite;
        this.personalName = personalName;
    }

    // Get id
    public int getId() {
        return id;
    }

    public abstract boolean isMoveValid(int toX, int toY, int fromX, int fromY);

    public boolean movePiece(int toX, int toY, int fromX, int fromY) {
        // Check if move is valid
        ChessPiece toSpot = board.getPiece(toX, toY);

        // Make sure that the knight is not moving to a position with a piece of the same color
        if (toSpot != null) {
            if (toSpot.isWhite() == this.isWhite()) return false;
        }

        // Check the move fits the piece move pattern
        if (!(isMoveValid(toX, toY, fromX, fromY)) || (toX == fromX && toY == fromY)) return false;

        // Do move two cases taking piece and not
        board.setPiece(toX, toY, this);
        board.setPiece(fromX, fromY, null);

        if (checkCheck() != null) {
            board.setPiece(toX, toY, toSpot);
            board.setPiece(fromX, fromY, this);
            return false;
        }

        //checks for checkmate
        if (!checkStaleMate() && checkCheckMate() && this.isWhite())
            System.out.println("White wins");
        else if (!checkStaleMate() && checkCheckMate() && !this.isWhite())
            System.out.println("Black wins");
        else if (checkStaleMate())
            System.out.println("Stalemate");


        return true;
    }

    public boolean isWhite(){
        return isWhite;
    }

    public ArrayList<Point> getValidMoves(int x, int y) {
        ArrayList<Point> validMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece pieceAtPosition  = board.getPiece(i, j);
                if (isMoveValid(i, j, x, y) && (pieceAtPosition == null || pieceAtPosition.isWhite() != this.isWhite())) {

                    //check if move puts king in check
                    board.setPiece(i, j, this);
                    board.setPiece(x, y, null);
                    if (checkCheck() == null) {
                        validMoves.add(new Point(i, j));
                    }
                    board.setPiece(i, j, pieceAtPosition);
                    board.setPiece(x, y, this);
                }
            }
        }
        return validMoves;
    }

    public Point checkCheck() {
        //Needed to prevent ConcurrentModificationException
        ArrayList<Point> currentNonEmpty = new ArrayList<>(board.nonEmptySpaces.keySet());

        for (Point location : currentNonEmpty) {
            ChessPiece pieceOne = board.getPiece(location.x, location.y);
            if (pieceOne != null && pieceOne.isWhite() != this.isWhite) {
                for (Point locationTwo : currentNonEmpty) {
                    ChessPiece pieceTwo = board.getPiece(locationTwo.x, locationTwo.y);
                    if (pieceTwo != null && pieceOne.isMoveValid(locationTwo.x, locationTwo.y, location.x, location.y) && pieceTwo.getId() == ChessBoard.KING && pieceTwo.isWhite() == this.isWhite) {
                        return new Point(locationTwo.x, board.getSize() - locationTwo.y - 1);
                    }
                }
            }
        }
        return null;
    }

    public boolean checkCheckMate(){
        ArrayList<Point> currentNonEmpty = new ArrayList<>(board.nonEmptySpaces.keySet());

        for (Point location : currentNonEmpty) {
            ChessPiece piece = board.getPiece(location.x, location.y);
            if (piece.isWhite != this.isWhite && piece.getValidMoves(location.x, location.y).size() > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean checkStaleMate(){
        ArrayList<Point> currentNonEmpty = new ArrayList<>(board.nonEmptySpaces.keySet());

        for (Point location : currentNonEmpty) {
            ChessPiece piece = board.getPiece(location.x, location.y);
            if (piece.isWhite != this.isWhite && piece.getValidMoves(location.x, location.y).size() > 0) {
                return false;
            }
        }
        return true;
    }

    public String getName() {return personalName;}
    public int getHealth() {return health;}
    public void damage(int damageValue)
    {
        health -= damageValue;
        if (health > 0) health = 0;

    }
}
