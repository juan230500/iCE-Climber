package Game;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketAdmin extends Thread {
    private Game Admin;

    public SocketAdmin(Game admin) {
        Admin = admin;
    }

    /**
     * Acepta cualquier socket y pasa el canal de comunicación
     * a un hiloque se encargará de comunicarse con ese jugador
     * @param Admin administrador de juego actual
     */
    private void addClient(Game Admin){
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

    /**
     * Hilo para escuchar registros de nuevos usuarios
     */
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
