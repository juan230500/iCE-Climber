package Game;

import Net.JsonParser;

import java.util.Scanner;

public class ConsoleLogic {

    private GameAdmin Admin;

    ConsoleLogic(GameAdmin Admin){
        this.Admin=Admin;
    }

    private String startCase(){
        if (!this.Admin.isReady())
            return "No hay suficientes jugadores";
        this.Admin.setGameRunning(true);
        Admin.sendAll(JsonParser.WriteStart());
        return "Inicio exitoso";
    }
    private String endCase(){
        this.Admin.setGameRunning(false);
        return "Cierre exitoso";
    }
    private String createCase(String[] words){
        if (words.length!=4)
            return "Cantidad de parámetros incorrecta";
        if (
                !words[1].equals("foca") &&
                !words[1].equals("ave") &&
                !words[1].equals("hielo") &&
                !words[1].equals("naranja") &&
                !words[1].equals("banano") &&
                !words[1].equals("berenjena") &&
                !words[1].equals("lechuga")
        )
            return "Obstáculo no válido";
        int level=Integer.parseInt(words[2]);
        if (level<1 || level>10)
            return "Nivel fuera de rango";
        int location=Integer.parseInt(words[3]);
        if (location<0 || location >100)
            return "Localización fuera de rango";
        Admin.sendAll(JsonParser.WriteEnemy(words[1],level,location,Admin.getIDe()));
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
                    return createCase(words);
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
        /*Game1.add(8081,"127.0.0.1",1);
        Game1.add(8081,"127.0.0.1",2);
        Game1.add(8081,"127.0.0.1",2);*/
        while (true){
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            if (input.equals("exit")) return;
            System.out.printf("[CONSOLE] %s\n",Console1.mainCase(input));
        }

    }

}
