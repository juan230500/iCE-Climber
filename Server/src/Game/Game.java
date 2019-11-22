package Game;

import Net.Sockets;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private int Njugadores;
    private List<PrintWriter> Observers;
    private boolean inGame;
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
        this.Njugadores=0;
        this.inGame=false;
    }

    int add(int ID, PrintWriter out){
        if (ID<2){
            Ready=true;
            if (Njugadores>1)
                return -1;
            else
                return Njugadores++;
        }
        else{
            if (Observers.size()>1)
                return -1;
            else
                Observers.add(out);
                return Observers.size()+1;

        }
    }

    void sendAll(String str){
        for (PrintWriter PW:
             Observers) {
            Sockets.writeSocket(str,PW);
        }
    }

    int getIDe(){
        return this.LastEnemy++;
    }


}
