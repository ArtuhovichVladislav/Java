import java.io.FileNotFoundException;

/**
 * ����� MoveThread
 * ��������� ����� Thread
 * ����� ��� �������� ������ ������, ����������� �� ���������� ������������� �� ������
 */
public class MoveThread extends Thread{
	Map map;
	
	MoveThread(Map map)
	{
		this.map = map;
	}
	public void setPause(boolean pause)
	{
		map.pause = pause;
	}
	
	public void setInGame(boolean inGame)
	{
		map.inGame = inGame;
	}
	
	public void setDemo(boolean demo)
	{
		map.demo = demo;
	}
	
	public void setReplay(boolean replay)
	{
		map.replay = replay;
	}
	public void refreshMap()
	{
		map.refresh();
		map.repaint();
	}
	@Override 
	public void run()
	{
		while(true)
		{
			try {
				map.move();
				sleep(120);
			} catch (FileNotFoundException | InterruptedException e) {
			
				e.printStackTrace();
			}
		}
	}
}
