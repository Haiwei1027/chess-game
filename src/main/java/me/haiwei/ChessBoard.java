package me.haiwei;

import java.awt.*;
import java.awt.image.BufferedImage;

import me.haiwei.pieces.*;

public class ChessBoard { // pawn, knight, rook, bishop, king, queen //white, black
	ChessPiece[] pieceTypes;
	int[][] board;
	int row, col;

	private Main main;
	
	private Point selected;

	public BufferedImage image;

	public ChessBoard(Main main, int row, int col) {
		this.main = main;
		this.row = row;
		this.col = col;
		board = new int[row][col];
		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				board[x][y] = -1;
			}
		}

		pieceTypes = new ChessPiece[] { new Pawn(this), new Knight(this), new Rook(this), new Bishop(this),
				new King(this), new Queen(this) };

		for (int i = 0; i < 6; i++) {
			board[i][3] = i;
			// pieces[i][1] = i+6;
		}
		move(0, 0, 0, 1);
		paint();
	}

	public boolean onBoard(int x, int y) {
		return x >= 0 && x < row && y >= 0 && y < col;
	}

	public void setPiece(int x, int y, int id) {
		if (!onBoard(x, y)) {
			return;
		}
		board[x][y] = id;
		paint();
	}

	public int getPiece(int x, int y) {
		if (!onBoard(x, y)) {
			return -1;
		}
		return board[x][y];
	}

	public int getPiece(Point point) {
		transformPoint(point, true);
		return getPiece(point.x, point.y);
	}

	public Point transformPoint(Point point) {
		int x, y;
		x = ((int) (point.x * (142f / 1024f)) - 7) / 16;
		y = ((int) (point.y * (142f / 1024f)) - 7) / 16;
		return new Point(x, y);
	}

	public void transformPoint(Point point, boolean byRef) {
		if (!byRef)
			return;
		point.x = ((int) (point.x * (142f / 1024f)) - 7) / 16;
		point.y = ((int) (point.y * (142f / 1024f)) - 7) / 16;
	}

	public int move(int x1, int y1, int x2, int y2) {
		if (!onBoard(x1, y1) || !onBoard(x2, y2) || (x1 == x2 && y1 == y2)) {
			return -2;
		}
		int p1 = getPiece(x1, y1);
		if (p1 == -1) {
			return -2;
		}

		if (!pieceTypes[p1 % 6].isMoveValid(x1, y1, x2 - x1, y2 - y1, p1 / 6))
			return -1;

		int p2 = getPiece(x2, y2);
		setPiece(x2, y2, p1);
		setPiece(x1, y1, -1);
		return p2;
	}

	public void selectPiece(Point boardPosition) {
		if (getPiece(boardPosition.x, boardPosition.y) < 0) return;
		if (selected == null) {
			selected = boardPosition;
		}
		paint();
		main.panel.enterDrag(getSelectedSprite());
	}

	public void movePiece(Point boardPosition) {
		if (selected == null) return;
		move(selected.x, selected.y, boardPosition.x, boardPosition.y);
		selected = null;
		paint();
		main.panel.exitDrag();
	}
	
	public BufferedImage getSelectedSprite() {
		int piece = board[selected.x][selected.y];
		BufferedImage sprite;
		sprite = ResourceLoader.instance.getPiece(piece % 6, piece / 6);
    	return sprite;
    }

	public void paint() {
		if (image == null) {
			image = new BufferedImage(142, 142, BufferedImage.TYPE_INT_RGB);
		} else {
			image.flush();
		}
		Graphics g = image.getGraphics();
		g.drawImage(ResourceLoader.instance.board, 0, 0, null);
		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				
				int piece = board[x][y];
				BufferedImage sprite;
				sprite = ResourceLoader.instance.getPiece(piece % 6, piece / 6);
				if (selected != null) {
					if (x == selected.x && y == selected.y) {
						g.drawImage(sprite, (int)(InputHandler.getMousePoint().x / (142f / 1024f)), 
								(int)(InputHandler.getMousePoint().y/(142f / 1024f)), null);
						continue;
					}
				}
				g.drawImage(sprite, x * 16 + 7, y * 16 + 7, null);
			}
		}
	}
}
