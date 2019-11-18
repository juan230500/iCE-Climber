package Net;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Sockets {
    /**
     * Autor: Juan
     * Método genérico para enviar una string desde un socket
     */
    public static void sendSocket(String hello, int PORT, String IP) {
        try{
            Socket s=new Socket(IP,PORT);
            PrintWriter dout=new PrintWriter(s.getOutputStream());
            dout.print(hello);
            System.out.println("[JAVA CLIENT] "+hello);
            dout.flush();
            dout.close();
            s.close();
        }catch(Exception e){System.out.println(e);}
    }

    /**
     * Autor: Juan
     * Método genérico para esperar una string en un socket
     */
    public static String listenSocket(int PORT){
        ServerSocket ss;
        String str="";
        try {
            ss = new ServerSocket(PORT);
            Socket s=ss.accept();//establishes connection
            DataInputStream dis=new DataInputStream(s.getInputStream());
            str=dis.readLine();
            System.out.println("[JAVA SERVER] "+str);
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void main(String[] args){
        /*for (int i=0;i<10;i++) {
            sendSocket("Mensaje "+i, 8090, "127.0.0.1");
        }*/
        for (int i=0;i<10;i++) {
            listenSocket( 8090);
        }
    }
}
