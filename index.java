
/*
    Author : Ilan Hascal 
    Description:
    Program is going to read input from stdin and print correspoinding relevant output from given JSON file (SystemViewController.json)
*/
import java.io.*;
import java.util.*;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import java.simple.*;
import org.json.JSONArray;

import jdk.nashorn.internal.parser.JSONParser;

import org.json.JSON;

public class index {

    public static final Map<String, String> content = new HashMap<String, String>();
    // Declaring a static hashMap that will hold the contents of the JSON

    public static void main(String[] args) {
        // Object obj = new JSONParser().parse(new
        // FileReader("SystemViewController.json"));
        Scanner console = new Scanner(System.in);
        String a = "";
        boolean terminate = false;
        while (!a.equals("q") && !terminate) {
            System.out.println("Please enter a valid selector you would like to use (Enter q to quit): ");
            a = console.nextLine();
            if (!validToken(a)) {
                terminate = true;
            } else {
                traverseJSON(a);

                // Start traversing the Json for the given view since we know we have a valid
                // token
            }

        }

    }

    public static void readJson() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("SystemControllerView.json")) {
            Object obj = parser.parse(reader); // new FileReader("SystemControllerView.json")
            JSONObject jsonObject = (JSONObject) obj;
            // go from here
            // need to parse the whole json
            // put it into a json array
            // iterate form there
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param a
     * @return - return whether the given string is valid input
     */
    public static boolean validToken(String a) {
        boolean valid = true;
        String[] b = a.split(" ");
        if (b.length > 1) { // for now this means our user input was greater than 1
                            // meaning that they entered a compound query selector
            valid = false;
        } else {
            String c = b[0];
            char d = b[0].charAt(0);
            if (Character.isUpperCase(d)) {
                valid = true;
            } else if (c.charAt(0) == '.' || c.charAt(0) == '#') {
                valid = true;

            }

        }
        return valid;
    }

    public static void traverseJSON(String a) {
        if (!content.containsKey(a)) {
            // a.put()
        }
    }

}

// JSONObject obj = new JSONObject(s);
// JSONArray arr = obj.getJSONArray("NewStock");
