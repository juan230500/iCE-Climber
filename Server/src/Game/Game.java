package Game;

import Net.Sockets;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<PrintWriter> Observers;
    private List<PrintWriter> Players;
    private volatile boolean inGame;
    private int LastEnemy;

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public Game(){
        this.LastEnemy=0;
        this.Observers=new ArrayList<>();
        this.Players=new ArrayList<>();
        this.inGame=false;
    }

    /**
     * Metodo para agregar un jugador y su respectivo canal de comunicación
     * @param ID puede ser 0(Popo),1(Nana),2(Observer)
     * @param out canal de comunicación
     * @return el ID asignado por el servidor, si no hay espacio se devuelve un -1
     */
    int add(int ID, PrintWriter out){
        if (ID<2){
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

    /**
     * Envía una string a todos los observer
     * @param str
     */
    void sendObservers(String str){
        for (PrintWriter PW:
             Observers) {
            Sockets.writeSocket(str,PW);
        }
    }

    /**
     * Envía una string a todos
     * @param str
     */
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

    /**
     * Obtiene un identificador para el siguiente enemigo
     * @return entero del IDe
     */
    int getIDe(){
        return this.LastEnemy++;
    }


}
