package Game;

import Net.Sockets;



public class GameAdmin {
    private Player[] Players;
    private int observers;
    private boolean GameRunning;
    private int topLevel;
    private int LastEnemy;

    public boolean isGameRunning() {
        return GameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        GameRunning = gameRunning;
    }

    GameAdmin(){
        this.LastEnemy=0;
        this.topLevel=1;
        this.GameRunning=false;
        this.observers=0;
        this.Players=new Player[4];
    }

    void clean(){
        this.topLevel=1;
        this.GameRunning=false;
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

    public void sendAll(String s){
        for (int i=0;i<4;i++) {
            if (Players[i]!=null){
                Sockets.sendSocket(
                        s,
                        Players[i].getPort(),
                        Players[i].getIp());
                System.out.printf("[SEND ALL] %s\n",s);
            }
        }
    }

    public boolean newTop(int top){
        if (top>this.topLevel){
            this.topLevel=top;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isReady(){
        if (Players[0]==null && Players[1]==null){
            return false;
        }
        return true;
    }

    public int getIDe(){
        return LastEnemy++;
    }

}
