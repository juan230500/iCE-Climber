package Game;

import Net.JsonParser;
import Net.Sockets;

import java.util.ArrayList;
import java.util.List;

public class GameAdmin {
    private Player[] Players;
    private int observers;

    GameAdmin(){
        this.observers=0;
        this.Players=new Player[4];
    }

    void clean(){
        this.observers=0;
        this.Players=new Player[4];
    }

    public boolean add(int port,String ip,int ID){
        if(ID==2 && this.observers<2){
            ID=ID+this.observers++;
            Players[ID]=new Player(port,ip,ID);
            return true;
        }
        else if (Players[ID]==null){
            Players[ID]=new Player(port,ip,ID);
            return true;
        }
        return false;
    }

    public void showPlayers(){
        for (int i=0;i<4;i++) {
            System.out.printf("%d) ",i);
            if (Players[i]==null){
                System.out.println("null");
                continue;
            }
            String name;
            if (Players[i].getID()==0) name="Popo";
            else if (Players[i].getID()==1) name="Nana";
            else name="Observer";

            System.out.printf("Player: %s (%s:%d)\n",name,Players[i].getIp(),Players[i].getPort(),Players[i].getID());
        }
    }

    public void startPlayers(){
        for (int i=0;i<4;i++) {
            if (Players[i]!=null){
                Sockets.sendSocket(
                        JsonParser.WriteStart(),
                        Players[i].getPort(),
                        Players[i].getIp());
                System.out.printf("[START] Player %s started\n",Players[i].getID());
            }
        }
    }
}
