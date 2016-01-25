import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;


public class StatisticWind extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<String> dest = new ArrayList<String>();
	int right = 0, left = 0, up = 0, down = 0;
	int pcRight = 0, pcLeft = 0, pcUp = 0, pcDown = 0;
	
	ArrayList<Integer> ghStat = new ArrayList<Integer>();
	ArrayList<Integer> pcStat = new ArrayList<Integer>();
	
	private final JPanel contentPanel = new JPanel();
	private final int numOfGames = 5;

	/**
	 * Create the dialog.
	 */
	public StatisticWind() {
		getStatistics();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 379);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label_1 = new JLabel("������: " + right);
		label_1.setBounds(44, 36, 154, 14);
		contentPanel.add(label_1);
		
		JLabel label_2 = new JLabel("�����: " + left);
		label_2.setBounds(44, 54, 154, 14);
		contentPanel.add(label_2);
		
		JLabel label_3 = new JLabel("�����: " + up);
		label_3.setBounds(263, 36, 146, 14);
		contentPanel.add(label_3);
		
		JLabel label_4 = new JLabel("����: " + down);
		label_4.setBounds(263, 54, 146, 14);
		contentPanel.add(label_4);
		
		JLabel lblNewLabel = new JLabel("������� ���������� ����� �� ���� ���� � �����������: ");
		lblNewLabel.setBounds(44, 79, 339, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("������: " + right/numOfGames);
		lblNewLabel_1.setBounds(44, 104, 71, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("�����: " + left/numOfGames);
		lblNewLabel_2.setBounds(125, 104, 73, 14);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("�����: " + up/numOfGames);
		lblNewLabel_3.setBounds(217, 104, 67, 14);
		contentPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("����: " + down/numOfGames);
		lblNewLabel_4.setBounds(307, 104, 71, 14);
		contentPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("���������� ����������");
		lblNewLabel_5.setBounds(154, 11, 162, 14);
		contentPanel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("���������� �������");
		lblNewLabel_6.setBounds(154, 154, 158, 14);
		contentPanel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("������: " + pcRight);
		lblNewLabel_7.setBounds(44, 178, 154, 14);
		contentPanel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("�����: " + pcLeft);
		lblNewLabel_8.setBounds(44, 203, 154, 14);
		contentPanel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("�����: " + pcUp);
		lblNewLabel_9.setBounds(263, 179, 146, 14);
		contentPanel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("����: " + pcDown);
		lblNewLabel_10.setBounds(263, 203, 146, 14);
		contentPanel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("������� ���������� ����� �� ���� ���� � �����������: ");
		lblNewLabel_11.setBounds(44, 228, 339, 14);
		contentPanel.add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("������: " + pcRight/numOfGames);
		lblNewLabel_12.setBounds(44, 253, 154, 14);
		contentPanel.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("�����: " + pcUp/numOfGames);
		lblNewLabel_13.setBounds(217, 253, 146, 14);
		contentPanel.add(lblNewLabel_13);
		
		JLabel lblNewLabel_14 = new JLabel("�����: " + pcLeft/numOfGames);
		lblNewLabel_14.setBounds(125, 253, 154, 14);
		contentPanel.add(lblNewLabel_14);
		
		JLabel lblNewLabel_15 = new JLabel("����: " + pcDown/numOfGames);
		lblNewLabel_15.setBounds(307, 253, 146, 14);
		contentPanel.add(lblNewLabel_15);
		
		JLabel lblNewLabel_16 = new JLabel("����� ���������� ����������� ��������: " + getGhostPopDir());
		lblNewLabel_16.setBounds(44, 133, 365, 14);
		contentPanel.add(lblNewLabel_16);
		
		JLabel lblNewLabel_17 = new JLabel("����� ���������� ����������� ��������: " + getPacmanPopDir());
		lblNewLabel_17.setBounds(44, 282, 319, 14);
		contentPanel.add(lblNewLabel_17);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {	//��������� �������
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();					
						Game.map.pause = false;
					}
		        });
				
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	public void getStatistics(){

		File directory = new File("H://������//4 �������//���//Pacamn//test");
		for(File file:directory.listFiles())
		{
			if(!file.isFile())
				break;
			if(file.getAbsolutePath().contains("ghost"))
			{
				try {
					dest = NotationWrite.readFile(file.getAbsolutePath());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				ghStat = Statistics.getStatistics(dest);
				right+=ghStat.get(0);
				left+=ghStat.get(1);
				up+=ghStat.get(2);
				down+=ghStat.get(3);
			}
			else 
			{
				try {
					dest = NotationWrite.readFile(file.getAbsolutePath());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				pcStat = Statistics.getStatistics(dest);
				pcRight += pcStat.get(0);
				pcLeft += pcStat.get(1);
				pcUp += pcStat.get(2);
				pcDown += pcStat.get(3);
			}
		}
	}
	
	public String getGhostPopDir()
	{
		String dir;
		int temp;
		ArrayList<Integer> directs = new ArrayList<Integer>();
		directs.add(right);
		directs.add(left);
		directs.add(up);
		directs.add(down);
		
		Collections.sort(directs, new Comparator<Integer>() {
			public int compare(Integer s1, Integer s2) {
				return s1 < s2 ? 1 : -1;
			}
		});
		
		Iterator<Integer> i = directs.iterator();
		temp = i.next();
		if(temp == right)
			dir = "������";
		else if(temp == left)
			dir = "�����";
		else if(temp == up)
			dir = "�����";
		else if(temp == down)
			dir = "����";
		else dir = "�� �������";

		return dir;
	}
	
	public String getPacmanPopDir()
	{
		String dir;
		int temp;
		ArrayList<Integer> directs = new ArrayList<Integer>();
		directs.add(pcRight);
		directs.add(pcLeft);
		directs.add(pcUp);
		directs.add(pcDown);
		
		Collections.sort(directs, new Comparator<Integer>() {
			public int compare(Integer s1, Integer s2) {
				return s1 < s2 ? 1 : -1;
			}
		});
		
		Iterator<Integer> i = directs.iterator();
		temp = i.next();
		if(temp == pcRight)
			dir = "������";
		else if(temp == pcLeft)
			dir = "�����";
		else if(temp == pcUp)
			dir = "�����";
		else if(temp == pcDown)
			dir = "����";
		else dir = "�� �������";

		return dir;
	}
}
