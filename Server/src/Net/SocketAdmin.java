package Net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SocketAdmin {

    public void addClient(){
        ServerSocket ss;
        try {
            ss = new ServerSocket(8080);
            Socket s=ss.accept();//establishes connection
            BufferedReader din=new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter dout=new PrintWriter(s.getOutputStream());
            SocketListener SL=new SocketListener(dout,din);
            SL.start();
            ss.close();
            //ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        SocketAdmin SA=new SocketAdmin();
        while(true){
            SA.addClient();
        }
    }
}
