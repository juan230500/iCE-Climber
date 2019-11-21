package Net;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonParser {
    public static String WriteStart(){
        JSONObject jo = new JSONObject();
        jo.put("Evento", "start");
        return jo.toString();
    }
    public static String WriteVal(boolean val){
        JSONObject jo = new JSONObject();
        jo.put("Evento", "login");
        jo.put("Respuesta", val);
        return jo.toString();
    }
    public static String WriteEnemy(String name,int level,int location,int IDe){
        JSONObject jo = new JSONObject();
        jo.put("Evento", "enemy");
        jo.put("Nombre", name);
        jo.put("Nivel", level);
        jo.put("Localizacion", location);
        jo.put("IDe", IDe);
        return jo.toString();
    }

    public static void WriteJsonTest()
    {
        // creating JSONObject
        JSONObject jo = new JSONObject();

        // putting data to JSONObject
        jo.put("firstName", "John");
        jo.put("lastName", "Smith");
        jo.put("age", 25);

        // for address data, first create LinkedHashMap
        Map m = new LinkedHashMap(4);
        m.put("streetAddress", "21 2nd Street");
        m.put("city", "New York");
        m.put("state", "NY");
        m.put("postalCode", 10021);

        // putting address to JSONObject
        jo.put("address", m);

        // for phone numbers, first create JSONArray
        JSONArray ja = new JSONArray();

        m = new LinkedHashMap(2);
        m.put("type", "home");
        m.put("number", "212 555-1234");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(2);
        m.put("type", "fax");
        m.put("number", "212 555-1234");

        // adding map to list
        ja.add(m);

        // putting phoneNumbers to JSONObject
        jo.put("phoneNumbers", ja);

        System.out.println(jo.toJSONString());

    }

    public static void ParseJsonTest() throws Exception{
        // parsing file "JSONExample.json"
        Object obj = new JSONParser().parse("{\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":{\"streetAddress\":\"21 2nd Street\",\"city\":\"New York\",\"state\":\"NY\",\"postalCode\":10021},\"age\":25,\"phoneNumbers\":[{\"type\":\"home\",\"number\":\"212 555-1234\"},{\"type\":\"fax\",\"number\":\"212 555-1234\"}]}");

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // getting firstName and lastName
        String firstName = (String) jo.get("firstName");
        String lastName = (String) jo.get("lastName");

        System.out.println(firstName);
        System.out.println(lastName);

        // getting age
        long age = (long) jo.get("age");
        System.out.println(age);

        // getting address
        Map address = ((Map)jo.get("address"));

        // iterating address Map
        Iterator<Map.Entry> itr1 = address.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }

        // getting phoneNumbers
        JSONArray ja = (JSONArray) jo.get("phoneNumbers");

        // iterating phoneNumbers
        Iterator itr2 = ja.iterator();

        while (itr2.hasNext())
        {
            itr1 = ((Map) itr2.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Object obj = new JSONParser().parse("{\"ID\":1, \"IP\":\"127.0.0.1\", \"Puerto\":8080}");
        /*WriteJsonTest();
        ParseJsonTest();*/
    }
}
