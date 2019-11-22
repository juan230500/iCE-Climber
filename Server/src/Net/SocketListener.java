package Net;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.PrintWriter;

public class SocketListener extends Thread{
    PrintWriter out;
    BufferedReader in;
    SocketListener(PrintWriter out, BufferedReader in){
        this.in=in;
        this.out=out;
    }
    public void run()
    {
        try
        {
            System.out.println ("Thread " +
                    Thread.currentThread().getId() +
                    " is running");
            String str="";
            while(str!=null && !str.equals("close")){
                str=in.readLine();
                System.out.println("[CLIENT] "+str);
            }
            out.close();
            in.close();
        }
        catch (Exception e)
        {
            System.out.println ("Exception is caught");
        }
    }
}
