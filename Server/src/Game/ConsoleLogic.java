package Game;

import Net.Sockets;

import java.util.Scanner;

public class ConsoleLogic {

    private GameAdmin Admin;

    ConsoleLogic(GameAdmin Admin){
        this.Admin=Admin;
    }

    private String startCase(){
        this.Admin.setGameRunning(true);
        Admin.startPlayers();
        return "Inicio exitoso";
    }
    private String endCase(){
        this.Admin.setGameRunning(false);
        return "Cierre exitoso";
    }
    private String createCase(){
        return "Generación exitosa";
    }

    private String mainCase(String input){
        String[] words= input.split(" ");
        switch (words[0]) {
            case "see":
                this.Admin.showPlayers();
                return "";
            case "start":
                if (this.Admin.isGameRunning())
                    return "Ya hay otro juego corriendo";
                else
                    return startCase();
            case "end":
                if (this.Admin.isGameRunning())
                    return endCase();
                else
                    return "No hay ningún juego que cerrar";
            case "create":
                if (this.Admin.isGameRunning())
                    return createCase();
                else
                    return "No hay ningún juego donde crear";
            default:
                return "No se reconoce la instrucción";
        }

    }

    public static void main(String[] args){
        GameAdmin Game1=new GameAdmin();
        ConsoleLogic Console1=new ConsoleLogic(Game1);
        EventListener EL1=new EventListener(Game1);
        EL1.start();
        Game1.add(8081,"127.0.0.1",1);
        Game1.add(8081,"127.0.0.1",2);
        Game1.add(8081,"127.0.0.1",2);
        while (true){
            Scanner scan = new Scanner(System.in);
            String input = scan.next();
            if (input.equals("exit")) return;
            System.out.println(Console1.mainCase(input));
        }

    }

}
