package me.xxfreakdevxx.de.game.skyblock;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Generator {
	
	private static int issize = 50;
	private static ConcurrentLinkedQueue<Island> islands = new ConcurrentLinkedQueue<Island>();
	public static boolean pause = false;
	
	public static void renderIslands(Graphics g) {
		for(Island i : islands) {
			if(pause == false && islands.isEmpty() == false) {
				i.render(g);
			}
		}
	}
	
	public static int amountOfIslands = 19;
	
	static int curx = 500;
	static int cury = 300;
	public static void generate() {
		pause = false;
		islands.clear();
		curx = 500;
		cury = 300;
		int left = 1;
		int round = 0;
		int addx1 = 1; //←
		int addy1 = 1; //↓
		int addx2 = 2; //→
		int addy2 = 0; //↑
		while(left < (amountOfIslands+1) || pause == true) {
			
			if(round==0) {
				addIsland(curx, cury, left);
				left++;
			}
			
			if(left < (amountOfIslands+1)) { //↑
				for(int a = 0; a != addy2; a++) {
					if(left < (amountOfIslands+1)) {						
						cury-=issize;
						addIsland(curx, cury, left);
						left++;
					}else break;
				}
			}
			
			if(left < (amountOfIslands+1)) { //←
				for(int a = 0; a != addx1; a++) {
					if(left < (amountOfIslands+1)) {						
						curx-=issize;
						addIsland(curx, cury, left);
						left++;
					}else break;
				}			
			}
			
			if(left < (amountOfIslands+1)) { //↓
				for(int a = 0; a != addy1; a++) {
					if(left < (amountOfIslands+1)) {						
						cury+=issize;
						addIsland(curx, cury, left);
						left++;
					}else break;
				}	
			}
			
			if(left < (amountOfIslands+1)) { //→
				for(int a = 0; a != addx2; a++) {
					if(left < (amountOfIslands+1)) {
						curx+=issize;
						addIsland(curx, cury, left);
						left++;
					}else break;
				}
			}
			if(round == 0) {
				addy2+=2; //↑
				addx1+=2; //←
				addy1+=2; //↓
				addx2+=2; //→
			}else {
				addy2+=2; //↑
				addx1+=2; //←
				addy1+=2; //↓
				addx2+=2; //→
			}
			round++;
		}
	}
	
	public static void addIsland(int x, int y, int id) {
		Island island = new Island(x,y, id);
		
		Generator.islands.add(island);
	}
	
	public static class Island {
		
		private int x,y,id;
		Random r = new Random();
		Color c = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
		
		public Island(int x, int y, int id) {
			this.x = x;
			this.y = y;
			this.id = id;
			
			if(id >= 1 && id <= 5) c = Color.LIGHT_GRAY;
			else if(id >= 6 && id <= 17) c = Color.CYAN;
			else if(id >= 18 && id <= 37) c = Color.pink;
			else if(id >= 38 && id <= 65) c = Color.BLUE;
			else if(id >= 66 && id <= 101) c = Color.GREEN;
		}
		
		public void render(Graphics g) {
			g.setColor(c);
			g.fillRect(x, y, issize/2, issize/2);
			g.setColor(Color.BLACK);
			g.drawString(id+"", x+10, y+10);
			g.setColor(Color.WHITE);
			g.drawString(id+"", x+12, y+11);
		}
		
	}
	
}
