import Game.Game;
import Game.SocketAdmin;
import Game.ConsoleLogic;
import java.util.Scanner;


public class Main extends Thread {
    public static void main(String[] args){
        Game G1=new Game();
        SocketAdmin SA=new SocketAdmin(G1);
        SA.start();
        ConsoleLogic Console1=new ConsoleLogic(G1);
        while (true){
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            if (input.equals("exit")) return;
            System.out.printf("[CONSOLE] %s\n",Console1.mainCase(input));
        }

    }
}
