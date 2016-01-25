import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JFrame;
import javax.swing.JRadioButtonMenuItem;


/**
 * �������� ����� Game
 * ����������� �� ������ JFrame
 * ��������� ActionListener
 * ������ ������� ���� � ���������� ��� ������ ���������
 */
public class Game extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L; 
	static Map map;
	Pacman pacman;
	public final static int WIDTH = 655;
	public final static int HEIGHT = 730;
	Ghost[] ghost;
	JMenuBar menuBar;
	JMenu menu, styleMenu, levelMenu, newGame, mode;
	JMenuItem style, level, exit, pause, resume, playerMode, demoMode, replay, generateGames, Readfiles,
				SortFiles, SortFiles2, Statistics, pseudoNot;
	JRadioButtonMenuItem darkStyle, lightStyle;
	JMenuItem easyLevel, normalLevel, hardLevel, forReplayLevel;
	static String mainFileName;
	static String fileName;
	static String ghostFileName;
	NotationWrite notation;
	ArrayList<String> fileNames;
	MoveThread thread;
	ArrayList<String> dest;
	ArrayList<Integer> scoresVal;
	HashMap<Integer, String> valuesMap = new HashMap<Integer, String>();
	//ActorObj actor;
	/**
	 * ����������� ������
	 * @throws FileNotFoundException 
	 */
	public Game() throws FileNotFoundException{
		scoresVal = new ArrayList<Integer>();
		if(Map.level == 0)
		{
			fileName = "H://������//4 �������//���//Pacamn//MyGames//Move//Move_"+getTimeAndDate()+".txt";
			ghostFileName = "H://������//4 �������//���//Pacamn//MyGames//Move//ghostMove_"+getTimeAndDate()+".txt";
			mainFileName = "H://������//4 �������//���//Pacamn//MyGames//Main_"+getTimeAndDate()+".txt";
			NotationWrite.write(mainFileName, fileName);
			NotationWrite.update(mainFileName, ghostFileName);
		}
		initUI();
		thread = new MoveThread(map);
        thread.start();
	}
	
	String getTimeAndDate(){
		long curTime = System.currentTimeMillis(); 
		String curStringDate = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss").format(curTime); 
		return curStringDate;
	}
	
	void setFileNames() throws FileNotFoundException
	{
		fileNames = NotationWrite.readFile(mainFileName);
		fileName = fileNames.get(0)+' ' +fileNames.get(1);
		ghostFileName = fileNames.get(2)+' '+fileNames.get(3);

		System.out.println(fileName);
	}
	/**
	 * ����� ������������� ���������� � �������� �������� ����.
	 * <p>
	 * ������������� �������� ����, ��������� �� ���� ������ ���� � ���� ��������.
	 */
	private void initUI(){
			map = new Map();									//������ ������ Map
			map.setBackground(Color.BLACK);						//���� ���� 
			map.setVisible(true);								//������ �������
			//map.setLevel(1);
	        add(map);											//��������� � ����
	        setTitle("Pacman 1.0");								//��������� ����
	        setDefaultCloseOperation(EXIT_ON_CLOSE);			//�������� ��������
	        setSize(WIDTH, HEIGHT);								//������ ����
	        setResizable(false);								//���������������
	        setLocationRelativeTo(null);						//������������ �� ������
	        setVisible(true);									//������ �������
	        menuBar = new JMenuBar();							//�������� ����-����
	        menu = new JMenu("Game");							//�������� ����
	        menuBar.add(menu);									//���������� � ����-���
	        
	        easyLevel = new JMenuItem("Easy");					//����� ����
	        easyLevel.addActionListener(new ActionListener() {	//��������� �������
				//@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();									//��������� ������� ����
					map.setLevel(1);							//1 �������
					Game game;
					try {
						game = new Game();
						game.setVisible(true);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						//����� ����
			       
				}
	        });				
	        normalLevel = new JMenuItem("Normal");				//����� ����
	        normalLevel.addActionListener(new ActionListener() {//��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {

					dispose();									//��������� ������� ����
					map.setLevel(2);							//2 �������
					Game game;
					try {
						game = new Game();
						game.setVisible(true);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						//����� ����
			      
				}
	        });
	        hardLevel = new JMenuItem("Hard");					//����� ����
	        hardLevel.addActionListener(new ActionListener() {  //��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();									//��������� ������� ����
					map.setLevel(3);							//3 �������
					Game game;
					try {
						game = new Game();
						game.setVisible(true);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						//����� ����
				}
	        });
	        
	        forReplayLevel = new JMenuItem("For replay");					//����� ����
	        forReplayLevel.addActionListener(new ActionListener() {  //��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();									//��������� ������� ����
					map.setLevel(0);							
					Game game;
					try {
						game = new Game();
						game.setVisible(true);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						//����� ����
				}
	        });
	        
	        newGame = new JMenu("New Game");					//�������
	        newGame.add(easyLevel);								//���������� � �������
	        newGame.add(normalLevel);
	        newGame.add(hardLevel);
	        newGame.add(forReplayLevel);
	        mode = new JMenu("Mode");							//�������
	        playerMode = new JMenuItem("Player");				//����� ����
	        playerMode.addActionListener(new ActionListener() {	//��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					thread.setPause(true);
					thread.setInGame(false);
					thread.setDemo(false);
					thread.refreshMap();							//���������� �����
				}
	        });
	        demoMode = new JMenuItem("Demo");					//����� ����
	        demoMode.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) { //��������� �������
					//long start = System.currentTimeMillis();
					ActorObj.setPause(false);
					ActorObj.setInGame(true);
					ActorObj.setDemo(true);
					//System.out.println(System.currentTimeMillis() - start);
					/*long start = System.currentTimeMillis();
					thread.setPause(false);
					thread.setInGame(true);
					thread.setDemo(true);
					System.out.println(System.currentTimeMillis() - start);*/
					thread.refreshMap();							//���������� �����
				}
	        });
	        replay = new JMenuItem("Replay");					//����� ����
	        replay.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) { //��������� �������
					
					JFileChooser fileopen = new JFileChooser("H://������//4 �������//���//Pacamn//MyGames/");             
					fileopen.showOpenDialog(fileopen);
			        fileopen.setVisible(true);
			        if(fileopen.getSelectedFile().isFile())
			        {
			        	ReplayGame(fileopen.getSelectedFile().getName());
			        }
				}
	        });
	        mode.add(playerMode);
	        mode.add(demoMode);
	        mode.add(replay);


	        pause = new JMenuItem("Pause");						//����� ����
	        pause.addActionListener(new ActionListener() {		//��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//thread.setPause(true);							//����� ����
					ActorObj.setPause(true);
				}
	        });
	        resume = new JMenuItem("Resume");					//����� ����
	        resume.addActionListener(new ActionListener() {		//��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//thread.setPause(false);							//���������� ����
					ActorObj.setPause(false);
				}
	        });
	        
	        generateGames = new JMenuItem("Generate");						//����� ����
	        generateGames.addActionListener(new ActionListener() {		//��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int i = 0;
					map.setLevel(0);
					thread.setPause(false);
					thread.setInGame(true);
					thread.setDemo(true);
				
					do{
					if(map.finish)
					{
						try {
							thread.sleep(5);
							Thread.sleep(5);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(Map.level == 0)
						{
							fileName = "H://������//4 �������//���//Pacamn//MyGames//Move//Move_"+getTimeAndDate()+".txt";
							ghostFileName = "H://������//4 �������//���//Pacamn//MyGames//Move//ghostMove_"+getTimeAndDate()+".txt";
							mainFileName = "H://������//4 �������//���//Pacamn//MyGames//Main_"+getTimeAndDate()+".txt";
							NotationWrite.write(mainFileName, fileName);
							try {
								NotationWrite.update(mainFileName, ghostFileName);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						map.finish = false;
						map.refresh();
						map.pause = false;
						map.inGame = true;
						map.demo = true;
						i++;
						}
					}
					}while(i!=5);
					dispose();
				}
	        });
	        
	        exit = new JMenuItem("Exit");						//����� ����
	        exit.addActionListener(new ActionListener() {		//��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();									//�������� ����
				}
	        });
	        menu.add(newGame);									//���������� ������� � ����
	        menu.add(mode);
	        menu.addSeparator();
	        menu.add(pause);
	        menu.add(resume);
	        menu.addSeparator();
	        
	        Readfiles = new JMenuItem("ReadFiles");						//����� ����
	        Readfiles.addActionListener(new ActionListener() {		//��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ReadFiles();
				}
	        });
	        
	        SortFiles = new JMenuItem("Sort");						//����� ����
	        SortFiles.addActionListener(new ActionListener() {		//��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ReadFiles();
					String filename = new String();
					try {
						filename = sortJava();
						ReplayGame(filename);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
	        });
	        
	        
	        SortFiles2 = new JMenuItem("Sort Scala");						//����� ����
	        SortFiles2.addActionListener(new ActionListener() {		//��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ReadFiles();
					String filename = new String();
					try {
						filename = sortScala();
						ReplayGame(filename);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
	        });
	        
	        Statistics = new JMenuItem("Statistics");						//����� ����
	        Statistics.addActionListener(new ActionListener() {		//��������� �������
				@SuppressWarnings("deprecation")
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//map.pause = true;
					ActorObj.setPause(true);
					StatisticWind wind = new StatisticWind();
					wind.setLocationRelativeTo(null);
					wind.setVisible(true);
				}
	        });
	        
	        styleMenu = new JMenu("Style");						//�������
	        darkStyle = new JRadioButtonMenuItem("Dark");		//������
	        darkStyle.setSelected(true);
	        darkStyle.addActionListener(this);
	        lightStyle = new JRadioButtonMenuItem("Light");		//������
	        darkStyle.addActionListener(new ActionListener() {	//��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					map.box = new ImageIcon("images/Box.png").getImage(); //��������� ������ �����������
					darkStyle.setSelected(true);
					lightStyle.setSelected(false);
					map.repaint();								//�����������
				}
	        });
	        lightStyle.addActionListener(new ActionListener() { //��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					map.box = new ImageIcon("images/Box2.png").getImage();//����� �����������
					darkStyle.setSelected(false);
					lightStyle.setSelected(true);
					map.repaint();								//�����������
				}
	        });
	        styleMenu.add(darkStyle);							//���������� � ����
	        styleMenu.add(lightStyle);
	        menu.add(styleMenu);
	        
	        pseudoNot = new JMenuItem("Pseudo Notation");						//����� ����
	        pseudoNot.addActionListener(new ActionListener() {		//��������� �������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//map.pause = true;
					ActorObj.setPause(true);
					try {
						PseudoNotation not = new PseudoNotation();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        });
	        
	        menu.add(Readfiles);
	        menu.add(generateGames);
	        menu.add(SortFiles);
	        menu.add(SortFiles2);
	        menu.addSeparator();
	        menu.add(Statistics);
	        menu.addSeparator();
	        menu.add(pseudoNot);
	        menu.addSeparator();
	        menu.add(exit);
	        setJMenuBar(menuBar);								//��������� ���� � ����
	    }

	private void ReplayGame(String filename)
	{
		mainFileName = "H://������//4 �������//���//Pacamn//MyGames/"+filename/*fileopen.getSelectedFile().getName()*/;
        try {
			setFileNames();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        map.setLevel(4);
        dispose();									//��������� ������� ����							
		Game game;
		try {
			game = new Game();
			game.setVisible(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}						//����� ����

		/*map.pause = false;
		map.inGame = true;
		map.replay = true;*/
		ActorObj.setPause(false);
		ActorObj.setInGame(true);
		ActorObj.setReplay(true);
		map.refresh();
	}
	private void ReadFiles(){
		int x;
		File directory = new File("H://������//4 �������//���//Pacamn//MyGames/");
		for(File file:directory.listFiles())
		{
			if(!file.isFile())
				break;
			try {
				dest = NotationWrite.readFile(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			x = Integer.parseInt(dest.get(4));
			scoresVal.add(x);
			valuesMap.put(x, file.getName());
			System.out.println(x);
			System.out.println(file.getName());
		}
	}
	public String sortScala() throws FileNotFoundException, InterruptedException {
		
		String filename = new String();
		int temp;
		Sorter obj = null;
		long start;
		long end;
		start = System.currentTimeMillis();
		ArrayList<Integer> sortedList = obj.sortScala(scoresVal);

		end = System.currentTimeMillis();
		System.out.println("time = " + (end-start));
		
		Iterator<Integer> i = sortedList.iterator();
		temp = i.next();
		
		System.out.println(temp);
		filename = valuesMap.get(temp);
		System.out.println(filename);
		return filename;
	}
	/**
	 * @throws FileNotFoundException
	 * @throws InterruptedException 
	 * 
	 */
	public String sortJava() throws FileNotFoundException, InterruptedException {
		String filename = new String();
		int temp;
		long start;
		long end;
		start = System.currentTimeMillis();
		Collections.sort(scoresVal, new Comparator<Integer>() {
			public int compare(Integer s1, Integer s2) {
				return s1 < s2 ? 1 : -1;
			}
		});
		end = System.currentTimeMillis();
		System.out.println("time = " + (end-start));
		Iterator<Integer> i = scoresVal.iterator();
		temp = i.next();
		//System.out.println(temp);
		filename = valuesMap.get(temp);
		return filename;
	}
	/**
	 * �����, ���������� �������� ������ �� ����� � ���������
	 * <p>
	 * ���������� ����� ������ move() ������ Map.
	 * @throws FileNotFoundException 
	 */
	static void move() throws InterruptedException, FileNotFoundException
	{
		  while(true)
	         {
	         	map.move();										//��������
	        	Thread.sleep(120);								//��������
	         }
	}
	
	/**
	 * ������� �����
	 * 
	 * @param args �������� ��������� ������
	 * @throws InterruptedException ��������� ����� ���������� ��� ������������� ������
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		 Game game = new Game();									//�������� ����
         game.setVisible(true);
        // move();												//����� ������ ��������
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
