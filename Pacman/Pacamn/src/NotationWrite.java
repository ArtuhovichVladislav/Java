import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class NotationWrite {

	public static void create(String fileName) {
	    //���������� ����
	    File file = new File(fileName);
	 
	    try {
	        //���������, ���� ���� �� ���������� �� ������� ���
	        if(!file.exists()){
	            file.createNewFile();
	        }
	    } catch(IOException e) {
	        throw new RuntimeException(e);
	    }
	}
	public static void write(String fileName, String text) {
	    //���������� ����
	    File file = new File(fileName);
	 
	    try {
	        //���������, ���� ���� �� ���������� �� ������� ���
	        if(!file.exists()){
	            file.createNewFile();
	        }
	 
	        //PrintWriter ��������� ����������� ������ � ����
	        PrintWriter out = new PrintWriter(file.getAbsoluteFile());
	 
	        try {
	            //���������� ����� � ����
	            out.print(text);
	        } finally {
	            //����� ���� �� ������ ������� ����
	            //����� ���� �� ���������
	            out.close();
	        }
	    } catch(IOException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public static void update(String nameFile, String newText) throws FileNotFoundException {
	    exists(nameFile);
	    StringBuilder sb = new StringBuilder();
	    String oldFile = read(nameFile);
	    sb.append(oldFile);
	    sb.append(newText);
	    write(nameFile, sb.toString());
	}
	
	public static ArrayList<String> readFile(String fileName) throws FileNotFoundException {
	 
	  //  exists(fileName);
	    
	    ArrayList<String> list = new ArrayList<String>();
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNext()) 
            list.add(scanner.next());
        scanner.close();
    
	    //���������� ���������� ����� � �����
	    return list;
	}
	
	
	public static String read(String fileName) throws FileNotFoundException {
	    //���� ����. ������ ��� ���������� ������
	    StringBuilder sb = new StringBuilder();
	 
	    exists(fileName);
	 
	    try {
	        //������ ��� ������ ����� � �����
	        BufferedReader in = new BufferedReader(new FileReader(fileName));
	        try {
	            //� ����� ��������� ��������� ����
	            String s;
	            while ((s = in.readLine()) != null) {
	                sb.append(s);
	                sb.append("\n");
	            }
	        } finally {
	            //����� �� �������� ������� ����
	            in.close();
	        }
	    } catch(IOException e) {
	        throw new RuntimeException(e);
	    }
	 
	    //���������� ���������� ����� � �����
	    return sb.toString();
	}
	
	private static void exists(String fileName) throws FileNotFoundException {
	    File file = new File(fileName);
	   /* if (!file.exists()){
	        throw new FileNotFoundException(file.getName());
	    }*/
		 
	    
	        //���������, ��� ���� ���� �� ���������� �� ������� ���
	        if(!file.exists()){
	            try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	}
}
