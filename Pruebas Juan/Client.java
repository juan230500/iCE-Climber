import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) throws IOException {
		try{ 
			for (int i=0;i<10;i++){    
				Socket s=new Socket("localhost",8081);  
				PrintWriter dout=new PrintWriter(s.getOutputStream());
				dout.print(i+"This is a string from java client");
				dout.flush();
				dout.close();
				s.close();
			}  
			}catch(Exception e){System.out.println(e);}  
			}  
	}
