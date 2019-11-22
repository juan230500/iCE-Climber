package Game;

import Net.Sockets;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class SocketListener extends Thread{
    PrintWriter out;
    BufferedReader in;
    int ID;
    Game Admin;

    SocketListener(PrintWriter out, BufferedReader in,Game Admin){
        this.Admin=Admin;
        this.in=in;
        this.out=out;
        this.ID=-1;
    }
    void logIn(String str){
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
    void generalEvent(String str){
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
                Admin.sendAll(str);
                break;
        }
    }
    public void run()
    {
        try
        {
            System.out.println ("Thread " +
                    Thread.currentThread().getId() +
                    " is running");
            String str="";
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
