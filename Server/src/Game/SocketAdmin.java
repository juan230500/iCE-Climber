package Game;

import Net.Sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class SocketAdmin extends Thread {
    Game Admin;

    public SocketAdmin(Game admin) {
        Admin = admin;
    }

    public void addClient(Game Admin){
        ServerSocket ss;
        try {
            ss = new ServerSocket(8080);
            Socket s=ss.accept();//establishes connection
            BufferedReader din=new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter dout=new PrintWriter(s.getOutputStream());
            SocketListener SL=new SocketListener(dout,din,Admin,this);
            SL.start();
            ss.close();
            //ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
            while(true || !Admin.isInGame()){
                addClient(Admin);
            }
        }
        catch (Exception e)
        {
            System.out.println ("Exception is caught");
        }
    }

}
