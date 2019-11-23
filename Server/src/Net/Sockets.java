package Net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Sockets {
    /**
     * Metodo para enviar una string a traves de un canal existente
     * @param str string a enviar
     * @param out canal
     */
    public static void writeSocket(String str,PrintWriter out){
        out.println(str);
        System.out.println("[SERVER] "+str);
        out.flush();
    }

    /**
     * Metodo para leer una string entrante a un canal existente
     * @param in canal
     * @return string leida
     */
    public static String readSocket(BufferedReader in){
        String str= null;
        try {
            str = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[CLIENT] "+str);
        return str;
    }



}
