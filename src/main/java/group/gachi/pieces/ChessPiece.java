package group.gachi.pieces;

import group.gachi.Battle;
import group.gachi.ChessBoard;
import group.gachi.Main;

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

        //check if it's the same colour
        if (toSpot != null && toSpot.isWhite() == isWhite()) return false;

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

        for (Point point : board.board.keySet()) {
            ChessPiece pieceAtPosition = board.getPiece(point);
            if (isMoveValid(point.x, point.y, x, y) && (pieceAtPosition == null || pieceAtPosition.isWhite() != this.isWhite())) {
                board.setPiece(point, this);
                board.setPiece(x, y, null);
                if (checkCheck() == null) {
                    validMoves.add(point);
                }
                board.setPiece(point, pieceAtPosition);
                board.setPiece(x, y, this);
            }
        }
        return validMoves;
    }

    public Point checkCheck() {
        //Needed to prevent ConcurrentModificationException
        ArrayList<Point> currentNonEmpty = new ArrayList<>(board.nonEmptySpaces.keySet());

        for (Point location : currentNonEmpty) {
            ChessPiece pieceOne = board.nonEmptySpaces.get(location);
            if (pieceOne.isWhite() != this.isWhite) {
                for (Point locationTwo : currentNonEmpty) {
                    ChessPiece pieceTwo = board.nonEmptySpaces.get(locationTwo);
                    if (pieceOne.isMoveValid(locationTwo.x, locationTwo.y, location.x, location.y) && pieceTwo.getId() == ChessBoard.KING && pieceTwo.isWhite() == this.isWhite) {
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
            ChessPiece piece = board.nonEmptySpaces.get(location);
            if (piece.isWhite != this.isWhite && piece.getValidMoves(location.x, location.y).size() > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean checkStaleMate(){
        ArrayList<Point> currentNonEmpty = new ArrayList<>(board.nonEmptySpaces.keySet());

        for (Point location : currentNonEmpty) {
            ChessPiece piece = board.getPiece(location);
            if (piece.isWhite != this.isWhite && piece.getValidMoves(location.x, location.y).size() > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isEnemy(ChessPiece piece){
        return isWhite != piece.isWhite;
    }

    public String getName() {return personalName;}
    public int getHealth() {return health;}
    public void damage(int damageValue) {
        health -= damageValue;
        if (health > 0) health = 0;

    }
}
