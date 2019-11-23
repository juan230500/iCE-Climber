package Game;

import Net.Sockets;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class SocketListener extends Thread{
    private PrintWriter out;
    private BufferedReader in;
    private int ID;
    private Game Admin;

    SocketListener(PrintWriter out, BufferedReader in,Game Admin){
        this.Admin=Admin;
        this.in=in;
        this.out=out;
        this.ID=-1;
    }

    /**
     * Procesa una string para logIn de un nuevo usuario y le responde a la solicitud
     * @param str input de los jugadores
     */
    private void logIn(String str){
        Object obj = null;
        try {
            obj = new JSONParser().parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jo = (JSONObject) obj;
        int val = Admin.add((int)(long) jo.get("ID"),out);
        this.ID=val;
        JSONObject jop = new JSONObject();
        jop.put("Respuesta", val);
        String res=jop.toString();
        Sockets.writeSocket(res,out);
    }

    /**
     * Clasifica los eventos que envían los jugadores a lo largo de partida
     * En muchos casos solo notifica a los observadores
     * @param str input de los jugadores
     */
    private void generalEvent(String str){
        Object obj = null;
        try {
            obj = new JSONParser().parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jo = (JSONObject) obj;
        String event = (String) jo.get("Evento"),out;

        switch (event){
            case "start":
                System.out.println("[START]");
                Admin.setInGame(true);
                break;
            case "move":
                System.out.println("[MOVE]");
                break;
            case "top":
                System.out.println("[TOP]");
                break;
            case "block":
                System.out.println("[BLOCK]");
                break;
            case "life":
                System.out.println("[LIFE]");
                break;
            case "destroy":
                System.out.println("[DESTROY]");
                break;
            case "end":
                System.out.println("[END]");
                break;
        }
        Admin.sendObservers(str);
    }

    /**
     * Hilo para escuchar a un jugador en específico
     */
    public void run()
    {
        try
        {
            System.out.println ("Thread " +
                    Thread.currentThread().getId() +
                    " is running");
            String str;
            while(true){
                str=Sockets.readSocket(in);
                if (str==null || str.equals("close")){
                    break;
                }
                if (ID==-1)
                    logIn(str);
                else
                    generalEvent(str);
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
