package Game;

import Net.Sockets;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<PrintWriter> Observers;
    private List<PrintWriter> Players;
    private volatile boolean inGame;
    private boolean Ready;
    private int LastEnemy;

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
    public boolean isReady() {
        return Ready;
    }

    Game(){
        this.LastEnemy=0;
        this.Ready=false;
        this.Observers=new ArrayList<>();
        this.Players=new ArrayList<>();
        this.inGame=false;
    }

    int add(int ID, PrintWriter out){
        if (ID<2){
            Ready=true;
            if (Players.size()>1)
                return -1;
            else
                Players.add(out);
                return Players.size()-1;
        }
        else{
            if (Observers.size()>1)
                return -1;
            else
                Observers.add(out);
                return Observers.size()+1;

        }
    }

    void sendObservers(String str){
        for (PrintWriter PW:
             Observers) {
            Sockets.writeSocket(str,PW);
        }
    }

    void sendAll(String str){
        for (PrintWriter PW:
                Observers) {
            Sockets.writeSocket(str,PW);
        }
        for (PrintWriter PW:
                Players) {
            Sockets.writeSocket(str,PW);
        }
    }

    int getIDe(){
        return this.LastEnemy++;
    }


}
