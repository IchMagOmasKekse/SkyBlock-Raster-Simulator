package me.xxfreakdevxx.de.game;

import me.xxfreakdevxx.de.game.objects.GameObject;

public class Camera {
	private float x=0,y=0;
	private float move_speed = 5f;
	private float offset = 0f;
	public Camera(float x, float y) {
		this.x=x;
		this.y=y;
	}
	public Camera(Location location) {
		this.x=(float)location.getX();
		this.y=(float)location.getY();
	}
	
	public void tick(GameObject object) {
		this.x += ((MouseInput.getInstance().x_uncon - this.x) - Game.windowWidth/2) * 0.05f;
		this.y += ((MouseInput.getInstance().y_uncon - this.y) - Game.windowHeight/2) * 0.05f;
		this.x += ((object.x - this.x) - Game.windowWidth/2);
		this.y += ((object.y - this.y) - Game.windowHeight/2);
	
		if(this.x <= 0) this.x = 0; 
		if(this.x >= (Game.windowWidth + 32)) this.x = (Game.windowWidth+32);
		if(this.y <= 0) this.y = 0; 
		if(this.x >= (Game.windowHeight + 16)) this.x = (Game.windowHeight+16);
	}

	public float getX() {
		return x;
	}
	public int getX(boolean fixToRaster) {	
		if(fixToRaster) return (int) ((x/Game.getCamera().getOffset())*Game.getCamera().getOffset());
		else return (int)x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}
	public int getY(boolean fixToRaster) {
		if(fixToRaster) return (int) ((y/Game.getCamera().getOffset())*Game.getCamera().getOffset());
		else return (int)y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void addX(int direction) {
		x+=move_speed * direction;
//		x+=SquareCraft.blocksize * direction;
	}
	public void addY(int direction) {
		y+=move_speed * direction;
//		y+=SquareCraft.blocksize * direction;
	}
	
	public float getOffset() {
		return offset;
	}
	
	public void biggerOffset() {
		if(offset < 200f) offset+=0.1f;
		else return;
	}
	public void smallerOffset() {
		if(offset > 0f) offset-=0.1f;
		else return;
	}
	
}
