package me.haiwei;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class InputHandler implements MouseListener, MouseMotionListener, KeyListener{

	static InputHandler instance;

	Point mousePosition;

	private Main main;

	public InputHandler(Main main) {
		super();
		instance = this;
		mousePosition = new Point();
		this.main = main;
	}

	public static Point getMousePoint() {
		return instance.mousePosition;
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Point boardPosition = main.boardRenderer.transformPoint(e.getPoint());
		main.board.selectPiece(boardPosition);

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Point boardPosition = main.boardRenderer.transformPoint(e.getPoint());
		main.board.movePiece(boardPosition);

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		mousePosition.x = e.getPoint().x;
		mousePosition.y = e.getPoint().y;
	}

	public void mouseMoved(MouseEvent e) {
		mousePosition.x = e.getPoint().x;
		mousePosition.y = e.getPoint().y;

	}

	public void mouseClicked(MouseEvent e) {
		

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
