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
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
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
		
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}
}
