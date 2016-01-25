import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.ImageIcon;


/**
 * ����� Ghost. ���� ����� �������� ��� ������ �������� ����������. 
 * �������� �������, ����������� �������� �������� move(). � �� ����������� ��������
 * ������������ ������������� ���� ������ ������ changeDirection(). 
 */
public class Ghost {
	int Direction = 1;
	int x = 325;
	int y = 350;
	int dx = 0;
	int dy = 0;
	int directDX = 0;
	int directDY = 0;
	int increment = 25;
	private Map map;
	boolean cantMove = false;
	int counter = 0;
	private static final int DIAMETER = 25;
	
	ArrayList<String> dest;
	Pacman pacman;
	
	/**
	 * ����������� ������
	 * 
	 * @param  map  ������� ������ Map, ������������ ��� �������� ������� Ghost
	 */
	public Ghost(Map map) {
		this.map= map;
		if(map.level == 0)
		{
			NotationWrite.create(Game.ghostFileName);
		}
		pacman = new Pacman(map);
	}
	/**
	 * ����� ��������� �����������
	 * ��������� Direction ���������������� ��������� ���������
	 * � ���������� [0 ; 4) ���� ������������� Math.random().
	 */
	public void changeDirection() {
		dx = 0;
		dy = 0;
		Direction = (int)(Math.random()*4);
	}
	/**
	 * ����� ��������������� �������� ����������.
	 * ������������ �������� �� ���� ��������� ���������� ���� ����������
	 * ��� ��������� �������� ����� ���� � ����������� �� �����������.
	 * ���������� ��������� �������� � ���������, �������� �� ��������� 
	 * � ���� ���������� ��� ���. ���� ������, �� ������������ ����� ��������� �����������
	 * @throws FileNotFoundException 
	 */
	public void Move() throws FileNotFoundException{
		int _x = (int)(x/map.BLOCK_SIZE);					//�������� � �������
	    int _y = (int)(y/map.BLOCK_SIZE);
	    switch(Direction) {
	        case 0: 
	        	if(canMove(_x-1, _y)) {						//���� ����� ��������� �����
	        			dx = -25; 
	        			dy = 0;
	        		}
	        	else {   
	        			cantMove = true;
	        			directDX = -25;
	        			directDY = 0;
	        			changeDirection();					//������ �����������
	        		}
	        	break;
	        case 1: 
	        	if(canMove(_x,_y-1)) {						//���� ����� ��������� �����
	        		dy = -25;
	        		dx = 0;
	        	}
	        	else {										//�����
	        		cantMove = true;					    //���� ������������� ��������
	        		directDX = 0;
        			directDY = -25;
        			changeDirection();						//������ �����������
        		}
	        	break;
	        case 2: 
	        	if(canMove(_x+1, _y)) {						//���� ����� ��������� ������
	        		dx = 25;
	        		dy = 0;
	        	}
	        	else {										//�����
	        		cantMove = true;						//���� ������������� ��������
	        		directDX = 25;
        			directDY = 0;
        			changeDirection();						//������ �����������
        		}
	        	break;
	        case 3: 
	        	if(canMove(_x,_y+1)) {						//���� ����� ��������� ����
	        		dy = 25; 
	        		dx = 0;
	        	}
	        	else {										//�����
	        		cantMove = true;						//���� ������������� ��������
	        		directDX = 0;
        			directDY = 25;
        			changeDirection();						//������ �����������
        		}
	        	break;
	        	default: break;
	        }
	    if(cantMove && canMove(_x + (directDX/25), 			//���� ��������� ����������� ��� ��������
	    		_y + directDY/25)) {
	    	dx = directDX;
	        dy = directDY;
	        cantMove = false;
	    }
	    else if (canMove(_x + (dx/increment), _y + dy/increment)) {	
		    x = x + dx;
		    y = y + dy;
	    }
	    if(map.level == 0){
	    	NotationWrite.update(Game.ghostFileName, x+" "+y);
	    }
	}
	
	/**
	 * <p>
	 * �����, ������������ �������� �������� �� ���������� � ���� �����������.
	 * ��������� �� ����� ���������� x � y � ���������� �� ��� ���������� 
	 */
	public void notationMove(){
	    if(counter == 0) {
	    	try {
				dest = NotationWrite.readFile(Game.ghostFileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			x = Integer.parseInt(dest.get(0));
			y = Integer.parseInt(dest.get(1));
			counter+=2;
	    }
	    if(counter <= dest.size()) {
		    nextDirection();
	    }
	}
	
	/**
	 * <p>
	 * ����� ��� �������� � ��������� ����������� � �����.
	 * <p>
	 * ������� ��������� ���������� x, � ����� y.
	 * � ����������� �� ����������� �������� �������������� ����������.
	 */
	void nextDirection()
	{
		if(counter < dest.size())
		{
			x = Integer.parseInt(dest.get(counter));
			y = Integer.parseInt(dest.get(counter+1));
		}
		counter+=2;
	}
	
	/**
	 * �����, �������� ����������. 
	 * ��������� ������ ������ Graphics2D � ����� ����������.
	 * 
	 * @param g ������ ������ Graphics2D ��� ��������� � ������
	 * @param number ����� ����������, ������������ ��� ������ �����������  
	 */
	public void paint(Graphics2D g, int number) {
		
		if(number == 0 || number > 4)
			number = 1;
		Image ghost = new ImageIcon("images/ghost"+number+".png").getImage();
		g.drawImage(ghost, x, y, map);
	}
	/**
	 * �����, ����������� ����������� ��������.
	 * � �������� ���������� ��������� ���������� ������ � �������.
	 * <p>
	 * ���� �������� ������ ����� 0, 2 ��� 3, �� ����� ���������� true, �.�.
	 * ���������� ����� ������������� �� ��� ����������. � ��������� ������ 
	 * ������������ �������� false, �.�. ������ �� ��������.
	 * 
	 * @param x ����� ������ �������
	 * @param y ����� ������� �������
	 * @return true, ���� �������� ������� �� ������ ������, false � ���� �����
	 */
	public boolean canMove(int x, int y) {
		if((x >= 0 && x < 25 )&& (y >= 0 && y < 26) ) {
			if((Map.grid[y][x] == 0)||(Map.grid[y][x] ==2) ||(Map.grid[y][x] ==3))
				return true;
			else 
				return false;
		}
		else return false;
	}
}