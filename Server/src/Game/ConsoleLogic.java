package Game;

import org.json.simple.JSONObject;

public class ConsoleLogic {

    private Game Admin;

    public ConsoleLogic(Game Admin){
        this.Admin=Admin;
    }

    /**
     * Verifica y ejecuta un comando create
     * @param words lista de parámetros introducidos
     * @return repuesta de error o éxito
     */
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

    /**
     * Identifica los casos de comandos
     * @param input string del usuario
     * @return respuesta del comando
     */
    public String mainCase(String input){
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

    /**
     *
     * @param args
     */

}
