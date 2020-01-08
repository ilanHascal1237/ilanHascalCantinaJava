
/*
    Author : Ilan Hascal 
    Description:
    Program is going to read input from stdin and print correspoinding relevant output from given JSON file (SystemViewController)
*/
import java.io.*;

import java.util.*;
import org.json.JSONArray;
import org.json.JSON;

public class ilanCantina {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String a = "";
        while (!a.equals("q")) {
            System.out.println("Please enter the selecor you would like to use (Enter q to quit): ");
            a = console.nextLine();

        }

    }

    JSONObject obj = new JSONObject(s);
    JSONArray arr = obj.getJSONArray("NewStock");// get the array called NewStock

}