import java.net.*;
import java.io.*;

public class Server  {
    public static void main(String[] args) throws IOException {
        try{  
            ServerSocket ss=new ServerSocket(8081);
            String  str="";
            while (str!="close"){
                Socket s=ss.accept();//establishes connection   
                DataInputStream dis=new DataInputStream(s.getInputStream());
                str=(String)dis.readLine(); 
                System.out.println("[JAVA SERVER] "+str);  
            }
            ss.close();  
            }catch(Exception e){System.out.println(e);}  
            }  
        
    }