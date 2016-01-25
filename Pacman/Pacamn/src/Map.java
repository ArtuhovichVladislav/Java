import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * ����� Map ����������� �� ������ JPanel.
 * <p>
 * ���������� ������ ��������� ������, ������ �����, �������� ����������
 * � ������� ����.
 */
public class Map extends JPanel{
	int ghostNum;
	private static final long serialVersionUID = 1L;
	public final int BLOCK_SIZE = 25;
	static int level = 1;
	String levelStr;
	public static final int WIDTH = 655;
	public static final int HEIGHT = 700;
	int x = 0;
	int y = 0;
	int score = 0;
	int numOfPellets = 1;
	boolean finish = false;
	
	Image pac = new ImageIcon("images/Pacman.png").getImage();
	Image box = new ImageIcon("images/Box.png").getImage();

	Ghost[] ghost;
	Pacman pacman;
	public boolean canMove;
	int count = 0;
	public boolean pause = true;
	public boolean inGame = false;
	public boolean demo = false;
	public boolean replay = false;
	
	
	/**
	 * ����������� ������.
	 * <p>
	 * C������� ������ ������������� ���������� � ��������� ������� ������.
	 * ������� ������� ������� ��������� � ������ ������ Pacman ��� ����������� ���������.
	 */
	public Map() {

		initVariables();									//������������� ����������
		
		addKeyListener(new KeyListener() {					//����� ������� ������
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(!pause && inGame)
					try {
						pacman.keyPressed(e);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}					//�������� ������� ������� pacman
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)	//���� ����� ESCAPE
				{
					count++;									
					if(count % 2 == 0)
						pause = false;
					else
						pause = true;						//�������������/��������� ����
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER)	//���� Enter
				{
						inGame = true;						//��������� ����
						pause = false;
				}
			}
		});
		setFocusable(true);
	}
	
	/**
	 * ����� ������������� ����������. 
	 * <p>
	 * � ����������� �� ������ ��������������� ���������� ���������� � �������� ������.
	 * ��������� ������� ������� Pacman � Ghost.
	 */
	private void initVariables() {							//��������� ���������� ���������� � ������������
		switch(level){										//�� ������
		case 0:
		case 4:
			ghostNum = 1;
			levelStr = new String("REPLAY");
			break;
		case 1:												
			ghostNum = 2;
			levelStr = new String("EASY");
			break;
		case 2:
			ghostNum = 5;
			levelStr = new String("NORMAL");
			break;
		case 3:
			ghostNum  = 10;
			levelStr = new String("HARD");
			break;
		}
		ghost = new Ghost[ghostNum];						//�������� ������� �������� ����������
		for(int i = 0; i <ghostNum; i++)
			ghost[i] = new Ghost(this);
		pacman = new Pacman(this);							//�������� ������� pacman
	}
	
	
	/**
	 * <p>
	 * �����, �������� �� ���������� ����, ��������� Graphics2D.
	 * 
	 * @param g ������ ������ Graphics ��� ��������� � ������
	 */
	@Override						
	public void paint(Graphics g){			
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		drawScore(g);										//��������� ������ � ������ � �������
		drawMap(g);											//��������� ����� �����
		drawPellets(g);										//��������� �����
		for (int i = 0; i<ghostNum; i++)
			ghost[i].paint(g2d, i);							//��������� ����������
		pacman.paint(g2d);									//� �������
		if(!inGame) {										//���� ���� �� ��������
			showIntroScreen(g2d);							//��������� �����
			refresh();										//���������� �����
		}
		
	}

	/**
	 * <p>
	 * �����, �������� ��������� �����, ��������� Graphics2D.
	 * 
	 * @param g2d ������ ������ Graphics2D ��� ��������� � ������
	 */
	 private void showIntroScreen(Graphics2D g2d) {

	        g2d.setColor(new Color(255, 188, 0));
	        g2d.fillRect(175, 275, 300, 50);					//������ ������������� �������
	        g2d.setColor(Color.white);
	        g2d.drawRect(175, 275, 300, 50);

	        String s = "Press ENTER to start.";
	        Font small = new Font("Emulogic", Font.BOLD, 10);

	        g2d.setColor(Color.white);
	        g2d.setFont(small);
	        g2d.drawString(s, 215, 305);						//��������� ������
	    }
	 
	 /**
		 * <p>
		 * �����, �������� �����, ��������� Graphics.
		 * 
		 * @param g ������ ������ Graphics ��� ��������� � ������
		 */
	public void drawMap(Graphics g) {
		int x = 0, y = 0;
		//g.setColor(Color.BLUE);
		for(int i = 0;  i < 25; i++, y+=BLOCK_SIZE) {
			for(int j = 0; j < 26; j++, x+=BLOCK_SIZE) {
					if(grid[i][j] == 1) {
						//g.fillRect(x, y , BLOCK_SIZE, BLOCK_SIZE);
						g.drawImage(box, x, y, this);			//������ �����
					}
			}
			x=0;
		}
	}
	/**
	 * <p>
	 * �����, �������� ����� �� �����, ��������� Graphics.
	 * 
	 * @param g ������ ������ Graphics ��� ��������� � ������
	 */
	public void drawPellets(Graphics g) {
		int x = 0, y = 0;
		numOfPellets = 0;
		g.setColor(Color.YELLOW);
		for(int i = 0;  i < 25; i++, y+=BLOCK_SIZE) {
			for(int j = 0; j < 26; j++, x+=BLOCK_SIZE) {
					if(grid[i][j] == 0) {
						g.fillOval(x+10, y+10 , 6, 6);			//������ �����
						numOfPellets++;
					}
			}
			x=0;
		}
	}
	
	/**
	 * <p>
	 * �����, �������� ����, ����� � �������� ������, ��������� Graphics2D.
	 * 
	 * @param g ������ ������ Graphics ��� ��������� � ������
	 */
	private void drawScore(Graphics g) {

        int i;
        int x = 0;
        String s, lives;

        g.setFont(new Font("Emulogic", Font.BOLD, 14));
        g.setColor(new Color(96, 128, 255));
        s = "SCORE: " + score*10;
        g.drawString(s, WIDTH-250, HEIGHT-45);				//�������� ������ � ������

        lives = "LIVES: ";
        g.drawString(lives, WIDTH-625, HEIGHT-45);			//�������� ������ � �������
        g.setColor(Color.YELLOW);
        for (i = 0; i < pacman.lives; i++, x+=30) {
        	g.drawImage(pac ,WIDTH-530 + x, HEIGHT-65, this);	//���� ����� � ���� ��������
           // g.fillOval(WIDTH-530 + x, HEIGHT-65, pacman.DIAMETER, pacman.DIAMETER);
        }
        
        g.drawString(levelStr, 275, HEIGHT-45);				//�������� ������ � �������
    }	
	
	/**
	 * ����� ���������� (��������������) �����.
	 * <p>
	 * ������� ���������������� ���������� ����������, ����� ��������������� 
	 * � ���� �������� ����������.
	 */	
	public void refresh() {	
        for (int i = 0; i < 25; i++)
        	for(int j = 0; j < 26; j++){
            grid[i][j] = grid2[i][j];		//��������������� �����
        }
        //finish = false;
        pacman.lives = 3;					//����� ��������� ��������
		score = 0;							//������, �����, ���������
		
		pacman.x = 25;
		pacman.y = 25;
		pacman.dx = 0;
		pacman.dy = 0;
		
		for(int i = 0; i <ghostNum; i++) {
			ghost[i].x = 325;
			ghost[i].y = 350;
			ghost[i].dx = 0;
			ghost[i].dy = 0;
		}
	}
	
	/**
	 * ����� ����������� ������ ����� ������������ ������� � �����������.
	 * <p>
	 * ����� ��������������� � ���� �������� ����������.
	 * <p>
	 * ���� ���������� ������ ������� ����� 0, �� ���������� ����� ���� � ���������.
	 */
	public void continueLevel()
	{	
		if(pacman.lives == 0) {
			if(replay)
			{
				score+=1;
			}
			repaint();
			finish = true;
			gameOver();							//���� "����� ����"
			
		}
												//��������������� ��������� ���������
		pacman.x = 25;							//������� � ����������
		pacman.y = 25;
		pacman.dx = 0;
		pacman.dy = 0;
		for(int i = 0; i <ghostNum; i++) {
			ghost[i].x = 325;
			ghost[i].y = 350;
			ghost[i].dx = 0;
			ghost[i].dy = 0;
		}
	}

	/**
	 * �����, ��������� ���������� ���� � ���������� � ������ � ����
	 */
	public void pacmanWin() {
		if(level == 0)
			try {
				NotationWrite.update(Game.mainFileName, score+" ");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		inGame = false;
		pause = true;
		replay = false;
		finish = true;
		setLevel(1);
		refresh();
	}
	/**
	 * �����, ��������� ���������� ���� � ���������� � ��������� � ����
	 */
	public void gameOver() {
		if(level == 0)
			try {
				NotationWrite.update(Game.mainFileName, score+" ");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		JOptionPane.showMessageDialog(this, "Game Over\nYour score: " +score*10, "Game Over", JOptionPane.YES_NO_OPTION);
		inGame = false;
		pause = true;
		replay = false;
		finish = true;
		setLevel(1);
		refresh();
	}
	
	/**
	 * �����, ����������� �������� ������ ����.
	 * <p>
	 * ��� �������� ���������� ������������ ����� Move(), � ���
	 * �������� ������� move() ��� demoMove, � ����������� �� ������ ����,
	 * �.�. �������� ���������� demo.
	 * @throws FileNotFoundException 
	 * @throws InterruptedException 
	 */
	public void move() throws FileNotFoundException, InterruptedException{	
		if(!demo && !replay) {								//������� ����� ����
			if(!pause) {	
				pacman.setDemo(false);
				pacman.setReplay(false);
				for (int i = 0; i<ghostNum; i++)
					ghost[i].Move();
				pacman.move();	
				repaint();
			}
		}
		else if(demo){									//�������������� �����
			pacman.setDemo(true);
			for (int i = 0; i<ghostNum; i++)
				ghost[i].Move();
			pacman.demoMove();
			repaint();
		}
		else if(replay){								//������ ����
			pacman.setReplay(true);
			for (int i = 0; i<ghostNum; i++)
				ghost[i].notationMove();
				//ghost[i].Move();
				pacman.notationMove();
				if(pacman.collision())
				{
					pacman.lives--;
					continueLevel();
				}
				repaint();
		}
	}
	
	/**
	 * ����� ��������� ������ ����.
	 * 
	 * @param newLevel ����� ������ ���� (1-3). 1 - easy, 2 - normal, 3 - hard.
	 */
	public void setLevel(int newLevel) {
		level = newLevel;
	}
	public static int[][] grid = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,1,0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,1},
			{1,1,1,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,1,1,1},
			{2,2,1,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,1,2,2},
			{1,1,1,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,1,1,1},
			{1,0,0,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,0,0,1},
			{1,1,1,0,1,0,0,0,0,0,0,1,2,2,1,0,0,0,0,0,0,1,0,1,1,1},
			{2,2,1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1,2,2},
			{2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2},
			{2,2,1,0,1,1,1,1,0,1,1,2,2,2,2,1,1,0,1,1,1,1,0,1,2,2},
			{2,2,1,0,1,1,1,1,0,1,2,2,2,2,2,2,1,0,1,1,1,1,0,1,2,2},
			{2,2,1,0,1,1,0,0,0,0,2,2,2,2,2,2,0,0,0,0,1,1,0,1,2,2},
			{2,2,1,0,1,1,0,1,0,0,2,2,2,2,2,2,0,0,1,0,1,1,0,1,2,2},
			{1,1,1,0,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,0,1,1,1},
			{1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
			{1,1,1,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,0,1,1,1},
			{2,2,1,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,0,1,2,2},
			{2,2,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,2,2},
			{2,2,1,0,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,2,2},
			{2,2,1,0,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,2,2},
			{2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2},
			{2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2}};
	
	private static int[][] grid2 = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
		{1,0,1,1,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,0,1},
		{1,0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1,1,1,1,0,1},
		{1,0,0,0,0,0,0,0,0,1,0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,1},
		{1,1,1,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,1,1,1},
		{2,2,1,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,1,2,2},
		{1,1,1,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,1,1,1},
		{1,0,0,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,0,0,1},
		{1,1,1,0,1,0,0,0,0,0,0,1,2,2,1,0,0,0,0,0,0,1,0,1,1,1},
		{2,2,1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1,2,2},
		{2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2},
		{2,2,1,0,1,1,1,1,0,1,1,2,2,2,2,1,1,0,1,1,1,1,0,1,2,2},
		{2,2,1,0,1,1,1,1,0,1,2,2,2,2,2,2,1,0,1,1,1,1,0,1,2,2},
		{2,2,1,0,1,1,0,0,0,0,2,2,2,2,2,2,0,0,0,0,1,1,0,1,2,2},
		{2,2,1,0,1,1,0,1,0,0,2,2,2,2,2,2,0,0,1,0,1,1,0,1,2,2},
		{1,1,1,0,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,0,1,1,1},
		{1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
		{1,1,1,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,0,1,1,1},
		{2,2,1,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,0,1,2,2},
		{2,2,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,2,2},
		{2,2,1,0,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,2,2},
		{2,2,1,0,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,2,2},
		{2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2},
		{2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2}};
}
	
