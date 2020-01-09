
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
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        Scanner console = new Scanner(System.in);
        String a = "";
        while (!a.equals("q")) {
            System.out.println("Please enter a query selector (Enter q to quit): ");
            a = console.nextLine();
            // readJson();

        }

    }

    public static void readJson() {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(); // new FileReader("SystemControllerView.json")
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
}

// JSONObject obj = new JSONObject(s);
// JSONArray arr = obj.getJSONArray("NewStock");
