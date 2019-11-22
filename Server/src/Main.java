import Net.SocketAdmin;

import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class Main extends Thread {
    public static void main(String[] args)   {
        String str="";
        try{
            Socket s=new Socket("127.0.0.1",8080);
            BufferedReader din=new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter dout=new PrintWriter(s.getOutputStream());
            for (int i = 0; i <5 ; i++) {
                dout.println("Repeate pls");
                dout.flush();
                str=din.readLine();
                System.out.println("Server: "+str);
                sleep(500);
            }
            din.close();
            dout.close();
            s.close();

        }catch(Exception e){System.out.println(e);}


    }
}
