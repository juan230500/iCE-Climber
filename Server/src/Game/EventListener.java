package Game;

import Net.JsonParser;
import Net.Sockets;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class EventListener extends Thread {
    private GameAdmin Admin;

    EventListener(GameAdmin Admin){
        this.Admin= Admin;
    }

    private void login(String s) throws ParseException {
        Object obj = new JSONParser().parse(s);
        JSONObject jo = (JSONObject) obj;
        int port=(int)(long) jo.get("Puerto");
        String ip=(String) jo.get("IP");

        boolean val = Admin.add(port,ip, (int)(long) jo.get("ID"));
        String res=JsonParser.WriteVal(val);
        Sockets.sendSocket(res,port,ip);
    }

    private void eventCase(String s) throws ParseException {
        Object obj = new JSONParser().parse(s);
        JSONObject jo = (JSONObject) obj;
        String event=(String) jo.get("Evento");
        if (event==null){
            System.out.println("[EVENT] No se encontró el evento");
            return;
        }
        switch (event){
            case "move":
                System.out.println("[EVENT MOVE]");
                Admin.sendAll(s);
                break;
            case "top":
                System.out.println("[EVENT TOP]");
                int top=(int)(long) jo.get("Nivel");
                if (Admin.newTop(top))
                    Admin.sendAll(s);
                break;
            case "life":
                System.out.println("[EVENT LIFE]");
                Admin.sendAll(s);
                break;
            case "destroy":
                System.out.println("[EVENT DESTROY]");
                Admin.sendAll(s);
                break;
            case "end":
                System.out.println("[EVENT END]");
                Admin.sendAll(s);
                break;
            default:
                System.out.println("[EVENT] No se identificó el evento");
                break;
        }
    }

    public void run()
    {
        try
        {
            // Displaying the thread that is running
            System.out.println ("Thread " +
                    Thread.currentThread().getId() +
                    " is running");
            while (true){
                String s=Sockets.listenSocket(8080);
                if (!Admin.isGameRunning()){
                    System.out.println("[LOGIN] Intento de login");
                    login(s);
                }
                else{
                    eventCase(s);
                }
            }

        }
        catch (Exception e)
        {
            // Throwing an exception
            System.out.println ("Exception is caught");
        }
    }
}
