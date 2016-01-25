
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * <p>
 * ����� ����� Pacman. ���� ����� �������� ��� ������ �������� �����.
 * <p>
 * �������� ���������������� � ������� ���� �������: move() � demoMove().
 * � ������ ������ �������� pacmana ����� �������������� � �����������,
 * ��������� � ���������� ������� �� ����������. �� ������ ������ ��������
 * �������������� �������������. ����� ����������� �������������� ��������� �������
 * ���� ������ ������ changeDirection().
 */

public class Pacman {
	public static final int DIAMETER = 25;
	int x = 25 ;
	int y = 25;
	int dx = 0;
	int dy = 0;
	int desiredDX = 0;
	int desiredDY = 0;
	int Direction = 1;
	int lives = 3;
	int increment = 25;
	boolean demoMode = false;
	boolean replayMode = false;
	Image pac = new ImageIcon("images/Pacman.png").getImage();
	String fileName;
	ArrayList<String> dest;
    int counter;
	
	private Map map;
	
	boolean cantMove = false;
	/**
	 * <p>
	 * ����������� ������.
	 * @param  map  ������� ������ Map, ������������ ��� �������� ������� Pacman
	 */
	public Pacman(Map map) {								//�����������
		this.map= map;
		if(Map.level == 0)
		{
			NotationWrite.create(Game.fileName);
		}
		counter = 0;
	}
	
	/**
	 * <p>
	 * �����, ������������ �������� �����. ��������� ��������� Pacmana 
	 * � ������� � ���������, �������� �� �������� � �������� �����������.
	 * ���� ��, �� ������������ �������� �� ���� ��������� ���� ����������
	 * ��� ��������� �������� ����� ���� � ����������� �� �����������.
	 * <p>
	 * ������������ �������� �� ������������ � �����������. ���� ����, ��
	 * ��������� ���������� ���������� ������.
	 * <p>
	 * ���������, ������� �� ��� �����. ���� ��, �� �������� ���� � ���������� � ������. 
	 * @throws FileNotFoundException 
	 */
	void move() throws FileNotFoundException{											
		int _x = (int)(x/map.BLOCK_SIZE);					//��������� �������� ���������		
	    int _y = (int)(y/map.BLOCK_SIZE);					//� �������(�����)
	    
	    if(cantMove && canMove(_x + (desiredDX/increment),	//���� ��������� ����������� ��� ��������
	    		_y + desiredDY/increment)) {
	    	dx = desiredDX;									//������������� �����������
	        dy = desiredDY;
	        cantMove = false;
	        if(dx == -25)									//�������������� ������� 
	        {
	        	drawPacmanLeft();							//� ����������� �� �����������
	        }
	        if(dx == 25)
	        {
	        	drawPacmanRight();
	        }
	        if(dy == -25)
	        {
	        	drawPacmanU();
	        }
	        if(dy == 25)
	        {
	        	drawPacmanD();
	        }
	    }
	    else if (canMove(_x + (dx/increment), _y + dy/increment)) {	//���� ��� �������
    		x += dx;												//���������� �� dx
	        y += dy;												//��� �� dy
	        
	        if(Map.grid[y/increment][x/increment] == 0) {			//������� �����
	        	Map.grid[y/increment][x/increment] = 2;
	        	map.score++;										//���������� ���������� �����
	        }
	    }
	    if(Map.level == 0) {
	    	NotationWrite.update(Game.fileName, x+" "+y);
	    }
		if (collision()) {											//���� ����������� � �����������
			lives--;												//��������� ���-�� ������
			map.continueLevel();									//���������� ����
		}
		if(map.numOfPellets == 0) {									//���� ��� ����� �������
			map.pacmanWin();										//���� � �������
			//map.finish = true;
		}
	}
	/**
	 * <p>
	 * �����, ������������ �������� ����� �� ���������� � ���� �����������.
	 * ��������� �� ����� ���������� x � y � ���������� �� ��� Pacamana
	 * <p>
	 * ������������ �������� �� ������������ � �����������. ���� ����, ��
	 * ��������� ���������� ���������� ������.
	 * <p>
	 * ���������, ������� �� ��� �����. ���� ��, �� �������� ���� � ���������� � ������. 
	 * @throws FileNotFoundException 
	 */
	public void notationMove(){
		int x1 = 0, y1 = 0;
	    if(counter == 0)
	    {
	    	try {
				dest = NotationWrite.readFile(Game.fileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			x1 = Integer.parseInt(dest.get(0));
			y1 = Integer.parseInt(dest.get(1));
			counter+=2;
	    }
	    if(counter <= dest.size())
	    {
	    	 	if(Map.grid[y/increment][x/increment] == 0) {		//������� �����
		        	Map.grid[y/increment][x/increment] = 2;
		        	map.score++;
		        }
	    	 	if(collision())
				{
					lives--;
					map.continueLevel();
				}
			    if(map.numOfPellets == 0) {							//������
					map.pacmanWin();
				}
			    
		    nextDirection();
	    }
	}
	/**
	 * <p>
	 * ����� ��� �������� � ��������� ����������� � �����.
	 * <p>
	 * ������� ��������� ���������� x, � ����� y.
	 * � ����������� �� ����������� �������� �������������� �������.
	 */
	void nextDirection()
	{
		int x1 = 0, y1 = 0;
		if(counter < dest.size())
		{
			x1 = Integer.parseInt(dest.get(counter));
			y1 = Integer.parseInt(dest.get(counter+1));
		}
		if((x1 - x) == 25)
			drawPacmanRight();
		if((x1 - x) == -25)
			drawPacmanLeft();
		if((y1 - y) == 25)
			drawPacmanD();
		if((y1 - y) == -25)
			drawPacmanU();
		x = x1;
		y = y1;
		counter+=2;
	}
	
	/**
	 * <p>
	 * �����, ����������� ����������� ������� � ������������.
	 * ���� ���������� ����� �������� ������ ������ ������� �������
	 * ����� ���������� �������� true, ����� false.
	 */
	boolean collision() {
		for(int i = 0; i < map.ghostNum; i++)
			if((Math.abs(map.ghost[i].x - x) <= 25 && map.ghost[i].y == y) || 
					(Math.abs(map.ghost[i].y - y) <= 25 && map.ghost[i].x == x))
				return true; 
		return false;
	}
	
	/**
	 * <p>
	 * �����, �������� �������, ��������� Graphics2D.
	 * 
	 * @param g ������ ������ Graphics2D ��� ��������� � ������
	 */
	public void paint(Graphics2D g) {
		g.drawImage(pac,x,y, map);
	}
	
	/**
	 * <p>
	 * ����� ��� ��������� Pacmana ��� ��� �������� ������.
	 * ������ � �������� �������� ������� pac ����� �������� � ��������� Pacman��.
	 */
	private void drawPacmanRight()
	{
		pac = new ImageIcon("images/Pacman.png").getImage();
	}

	/**
	 * <p>
	 * ����� ��� ��������� Pacmana ��� ��� �������� �����.
	 * ������ � �������� �������� ������� pac ����� �������� � ��������� Pacman��.
	 */
	private void drawPacmanLeft()
	{
		pac = new ImageIcon("images/L.png").getImage();
	}
	
	/**
	 * <p>=
	 * ����� ��� ��������� Pacmana ��� ��� �������� �����.
	 * ������ � �������� �������� ������� pac ����� �������� � ��������� Pacman��.
	 */
	private void drawPacmanU()
	{
		pac = new ImageIcon("images/U.png").getImage();
	}
	
	/**
	 * <p>
	 * ����� ��� ��������� Pacmana ��� ��� �������� ����.
	 * ������ � �������� �������� ������� pac ����� �������� � ��������� Pacman��.
	 */
	private void drawPacmanD()
	{
		pac = new ImageIcon("images/D.png").getImage();
	}
	
	/**
	 * �����, ����������� ��������� ������. ��������� ��� ������� �������
	 * �  ��������� Pacmana � ������� � ���������, �������� �� �������� � �������� �����������.
	 * ���� ��������, �� ���������� �������� ��� ������� ����������� ��������. ���� ���, �� ���� 
	 * ���������� ��������, �� ������������� ���� ������������� �������� � �������� �����������.
	 * 
	 * @param e ������ ������ KeyEvent. ������������ ��� ��������� ���� ������� �������.
	 * @throws FileNotFoundException 
	 */
	public void keyPressed(KeyEvent e) throws FileNotFoundException {
		int _x = (int)(x/map.BLOCK_SIZE);
	    int _y = (int)(y/map.BLOCK_SIZE);
	    if(!demoMode && !replayMode) {
		    if (e.getKeyCode() == KeyEvent.VK_LEFT 
		    		&& canMove(_x-1, _y)) {					//���� ����� ��������� ������
		    	drawPacmanLeft();
				dx = -increment;
				dy = 0;
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT 
					&& !canMove(_x-1, _y)) { 				//���� �����, �� �� ����� ��������� ������
				cantMove = true; 
				desiredDX = -increment;
				desiredDY = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT 	
					&& canMove(_x + 1, _y)) {				//���� ����� ��������� �������
				drawPacmanRight();
				dx = increment;
				dy = 0;
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT 	//���� �����, �� �� ����� �������� �������
					&& !canMove(_x+1, _y)) {
				cantMove = true;
				desiredDX = increment;
				desiredDY = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP 			//���� ����� ��������� �����
					&& canMove(_x, _y-1)) {
				drawPacmanU();
				dx = 0;
				dy = -increment;
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP		//���� �����, �� �� ����� �������� �����
					&& !canMove(_x, _y-1)) {
				cantMove = true;
				desiredDX = 0;
				desiredDY = -increment;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN			//���� ����� ��������� ����
					&& canMove(_x, _y + 1)) {
				drawPacmanD();
				dx = 0;
				dy = increment;
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN 	//���� �����, �� �� ����� �������� ����
					&& !canMove(_x, _y+1)) {
				cantMove = true;
				desiredDX = 0;
				desiredDY = increment;
			}
	    }
	}
	
	/**
	 * �����, ����������� ����������� ��������.
	 * � �������� ���������� ��������� ���������� ������ � �������.
	 * <p>
	 * ���� �������� ������ ����� 0 ��� 2, �� ����� ���������� true, �.�.
	 * ���������� ����� ������������� �� ��� ����������. � ��������� ������ 
	 * ������������ �������� false, �.�. ������ �� ��������.
	 * 
	 * @param x ����� ������ �������
	 * @param y ����� ������� �������
	 */
	public boolean canMove(int x, int y) {
		if((x >= 0 && x < 25 )&& (y >= 0 && y < 26) ) {
			if((Map.grid[y][x] == 0)||(Map.grid[y][x] ==2))
				return true;
			else 
				return false;
		}
		else return false;
	}
	/**
	 * ����� ��������� �����������
	 * ��������� Direction ���������������� ��������� ���������
	 * � ���������� [0 ; 4) ���� ������������� Math.random().
	 */
	public void changeDirection() {
		dx = 0;
		dy = 0;
		Direction = (int)(Math.random()*4);							//��������� �������� [0,3]
		//System.out.println( Direction );
	}

	/**
	 * ����� ��������������� �������� Pacmana. ������������ � �������������� ������
	 * <p>
	 * ������������ �������� �� ���� ��������� ���������� ���� ����������
	 * ��� ��������� �������� ����� ���� � ����������� �� �����������.
	 * ���������� ��������� �������� � ���������, �������� �� ��������� 
	 * � ���� ���������� ��� ���. ���� ������, �� ������������ ����� ��������� �����������.
	 * * <p>
	 * ������������ �������� �� ������������ � �����������. ���� ����, ��
	 * ��������� ���������� ���������� ������.
	 * <p>
	 * ���������, ������� �� ��� �����. ���� ��, �� �������� ���� � ���������� � ������.
	 */
	public void demoMove(){
		int _x = (int)(x/map.BLOCK_SIZE);			//�������� � �������
	    int _y = (int)(y/map.BLOCK_SIZE);
	    switch(Direction) {
	        case 0: 
	        	if(canMove(_x-1, _y)) {				//���� ����� ��������� �����
	        			dx = -25; 
	        			dy = 0;
	        			drawPacmanLeft();			//����������� �����
	        		}
	        	else {   
	        			cantMove = true;			//���� ������������� ��������
	        			desiredDX = -25;
	        			desiredDY = 0;
	        			changeDirection();			//������ �����������
	        		}
	        	break;
	        case 1: 
	        	if(canMove(_x,_y-1)) {              //���� ����� ��������� �����
	        		dy = -25;
	        		dx = 0;
	        		drawPacmanU();					//����������� �����
	        	}
	        	else {								//�����
	        		cantMove = true;				//���� ������������� ��������
	        		desiredDX = 0;
        			desiredDY = -25;
        			changeDirection();				//������ �����������
        		}
	        	break;
	        case 2: 
	        	if(canMove(_x+1, _y)) {				//���� ����� ��������� ������
	        		dx = 25;
	        		dy = 0;
	        		drawPacmanRight();
	        	}
	        	else  {								//�����
	        		cantMove = true;				//���� ������������� ��������
	        		desiredDX = 25;
        			desiredDY = 0;
        			changeDirection();				//������ �����������
        		}
	        	break;
	        case 3: 					
	        	if(canMove(_x,_y+1)) {				//���� ����� ��������� ����
	        		dy = 25; 
	        		dx = 0;
	        		drawPacmanD();
	        	}
	        	else  {								//�����
	        		cantMove = true;				//���� ������������� ��������
	        		desiredDX = 0;
        			desiredDY = 25;
        			changeDirection();				//������ �����������
        		}
	        	break;
	        	default: break;
	        }
	    if(cantMove && canMove(_x + (desiredDX/25),
	    		_y + desiredDY/25)) {				//���� ��������� ����������� ��� ��������
	    	dx = desiredDX;
	        dy = desiredDY;
	        cantMove = false;
	        if(dx == -25)									//�������������� ������� 
	        	drawPacmanLeft();							//� ����������� �� �����������
	        if(dx == 25)
	        	drawPacmanRight();
	        if(dy == -25)
	        	drawPacmanU();
	        if(dy == 25)
	        	drawPacmanD();
	    }
	    x += dx;
	    y += dy;
	    if(Map.grid[y/increment][x/increment] == 0) {		//������� �����
        	Map.grid[y/increment][x/increment] = 2;
        	map.score++;
        }
	    if (collision()) {									//��������� ������������ � �����������
			lives--;
			map.continueLevel();
		}
	    if(map.numOfPellets == 0) {							//������
			map.pacmanWin();
		}
	    if(map.level == 0){
	    	try {
				NotationWrite.update(Game.fileName, x+" "+y);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	/**
	  * ����� ��������� ������ ����. ���� ����� ���� ��������������, �� � ���� ������ ��������� ���������,
	  * ����� - ������������.
	  * 
	  * @param mode ��������� �������� true, ���� ������������ �������������� ����� ����, 
	  * false, ���� ����������������.
	 */
	void setDemo(boolean mode) {							//��������� ��������������� ������
		demoMode = mode;
	}
	
	/**
	  * ����� ��������� ������ ����. ���� ����� ���� - ������, �� � ���� ������ ��������� ���������,
	  * ������� ������������� ��������, ���������� � ����,
	  * ����� - ������������.
	  * 
	  * @param mode ��������� �������� true, ���� ������������ ����� ������� ����, 
	  * false, ���� ����������������.
	 */
	void setReplay(boolean mode){
		replayMode = mode;
	}
}
