package Game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class SocketAdmin {

    public void addClient(Game Admin){
        ServerSocket ss;
        try {
            ss = new ServerSocket(8080);
            Socket s=ss.accept();//establishes connection
            BufferedReader din=new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter dout=new PrintWriter(s.getOutputStream());
            SocketListener SL=new SocketListener(dout,din,Admin);
            SL.start();
            ss.close();
            //ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Game Admin=new Game();
        SocketAdmin SA=new SocketAdmin();
        while(true){
            SA.addClient(Admin);
        }
    }
}
