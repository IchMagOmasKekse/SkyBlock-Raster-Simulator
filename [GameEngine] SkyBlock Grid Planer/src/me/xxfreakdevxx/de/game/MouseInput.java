package me.xxfreakdevxx.de.game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseInput extends MouseAdapter {
	
	public int x_con = 0;
	public int y_con = 0;
	public int x_uncon = 0;
	public int y_uncon = 0;
	
	private static MouseInput instance = null;
	public static MouseInput getInstance() {
		return instance;
	}
	
	public MouseInput() {
		instance = this;
	}
	
	public void mousePressed(MouseEvent e) {
		Camera camera = Game.getCamera();
		x_con = e.getX();
		y_con = e.getY();
		int mx = (int) (e.getX() + camera.getX());
		int my = (int) (e.getY() + camera.getY());		
		x_con = mx;
		y_con = my;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0) {
			Game.getCamera().addY(1);
        } else {
        	Game.getCamera().addY(-1);
        }
	}
}
