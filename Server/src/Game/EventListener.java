package Game;

import Net.JsonParser;
import Net.Sockets;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.file.AccessDeniedException;

public class EventListener extends Thread {
    GameAdmin Admin;

    EventListener(GameAdmin Admin){
        this.Admin= Admin;
    }

    void login(String s) throws ParseException {
        Object obj = new JSONParser().parse(s);
        JSONObject jo = (JSONObject) obj;
        int port=(int)(long) jo.get("Puerto");
        String ip=(String) jo.get("IP");

        boolean val = Admin.add(port,ip, (int)(long) jo.get("ID"));
        String res=JsonParser.WriteVal(val);
        Sockets.sendSocket(res,port,ip);
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
            }

        }
        catch (Exception e)
        {
            // Throwing an exception
            System.out.println ("Exception is caught");
        }
    }
}
