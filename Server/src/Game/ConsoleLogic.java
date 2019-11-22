package Game;

import Net.JsonParser;
import org.json.simple.JSONObject;

import java.util.Scanner;

public class ConsoleLogic {

    private Game Admin;

    ConsoleLogic(Game Admin){
        this.Admin=Admin;
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

        JSONObject jo = new JSONObject();
        jo.put("Evento", "enemy");
        jo.put("Nombre", words[1]);
        jo.put("Nivel", level);
        jo.put("PosX", location);
        jo.put("IDe", Admin.getIDe());
        Admin.sendAll(jo.toString());
        return "Generación exitosa";
    }

    private String mainCase(String input){
        String[] words= input.split(" ");
        switch (words[0]) {
            case "create":
                if (this.Admin.isInGame())
                    return createCase(words);
                else
                    return "No hay ningún juego donde crear";
            default:
                return "No se reconoce la instrucción";
        }

    }

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
