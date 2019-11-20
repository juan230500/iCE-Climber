package Game;

public class Player {
    private int port;
    private String ip;
    private int ID;

    public int getID() {
        return ID;
    }


    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }



    Player(int port,String ip, int ID){
        this.port=port;
        this.ip=ip;
        this.ID=ID;
    }
}
