
/**
 * @author ILAN HASCAL Ilanhascal1237@gmail.com
 * 
 *         Program fetches SystemViewController.json , parses and then allows
 *         for stdin commands form the terminal
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class readsysview {

    public static void main(String[] args) {
        Scanner consolereader = new Scanner(System.in);
        System.out.print("Enter selector or [exit]: ");
        while (!consoleinput.toLowerCase().equals("exit")) {
            select(consoleinput, sys);
            System.out.print("Enter selector or [exit]: ");
            consoleinput = consolereader.nextLine();
        }
        consolereader.close();
        // url from gitHub
        String curl = "https://raw.githubusercontent.com/jdolan/quetoo/master/src/cgame/default/ui/settings/SystemViewController.json";
        String filename = "systemView.json";
        writeJsonFile(curl, filename);

    }

    /**
     * @param line - is reading from the scanner
     * @param sys  -
     */
    public static void select(String line, JSONObject sys) {
        if (line.contains(".")) {
            // checking for classNames
            // then removing the identifier
            line = line.replace(".", "");
            selectClasses(line, sys, "classNames");
        } else if (line.contains("#")) {
            // checking for an identifier
            line = line.replace("#", "");
            selectClasses(line, sys, "identifier");
        } else {
            // otherwise we know it is a class
            selectClasses(line, sys, "class");
        }

    }

    /**
     * @param curl - is the gitHub link tot he public repo
     * @return - a BufferReader that can traverse the JSON
     */
    public static BufferedReader readFromGit(String curl) {
        try {
            URL url = new URL(curl);
            URLConnection uc;
            uc = url.openConnection();// fethcing
            return new BufferedReader(new InputStreamReader(uc.getInputStream()));
        } catch (IOException e) {
            System.out.println("No Github resource found.");
            return null;
        }

    }

    /**
     * @param line    - line the scanner is reading
     * @param sys
     * @param pattern - is the selector we are using
     */
    public static void selectClasses(String line, JSONObject sys, String pattern) {
        System.out.println("MATCHING VIEWS: " + recurseClasses(line, sys, pattern));
    }

    /**
     * @param line    - line the scanner is reading
     * @param sys     -
     * @param pattern - is the selector we are using
     * @return - number of iterations
     */
    public static int recurseClasses(String line, JSONObject sys, String pattern) {
        int viewsfound = 0;// keep track of how many we hace seen
        for (Object o : sys.keySet()) { // iterate through the keys
            if (sys.get(o) instanceof JSONArray) { // loop case, for an array
                JSONArray ja = (JSONArray) sys.get(o); // get the object
                for (Object n : ja.toArray()) {// iterate through the array
                    if (n instanceof JSONObject) {// checking if it is an object
                        if (checkJSONObject(line, (JSONObject) n, pattern)) // call checkJSON for contents
                            viewsfound++; // if ture,incerement views
                        viewsfound += recurseClasses(line, (JSONObject) n, pattern);// keep searching
                    }
                }
            } else if (sys.get(o) instanceof JSONObject) {// single case
                if (checkJSONObject(line, (JSONObject) sys.get(o), pattern))
                    viewsfound++;
                viewsfound += recurseClasses(line, (JSONObject) sys.get(o), pattern);
            }
        }
        return viewsfound;
    }

    /**
     * @param line    - line we are reading form the scanner
     * @param jo      - json object
     * @param pattern - is the selector we are using
     * @return - wether we are successfuly able to find the view
     */
    public static boolean checkJSONObject(String line, JSONObject jo, String pattern) {
        if (jo.containsKey(pattern)) {// if we see a familiar patter, error check
            if (pattern != "classNames") {
                if (jo.get(pattern).equals(line)) {
                    System.out.println("VIEW: " + jo.get("class") + "\n" + "JSON: ");
                    for (Object key : jo.keySet()) {// iterate through the keys of the object we just found based off of
                                                    // pattern
                        if (!key.equals(pattern)) {
                            System.out.println("	" + jo.get(key));
                        }
                    }
                    System.out.println();
                    return true;
                }
            } else {
                return checkJSONArray((JSONArray) jo.get(pattern), jo.get("class"), line);
            }

        }
        return false;
    }

    /**
     * @param jarr     - is the json array within the object
     * @param viewname - is the type of selector we are woking with
     * @param line     - is reading form the scanner
     * @return - wether or not we have found the right view
     */
    public static boolean checkJSONArray(JSONArray jarr, Object viewname, String line) {
        if (jarr.contains(line)) { // seeing if they correpespons (pattern and the line we pass)
            System.out.println("VIEW: " + viewname + "\n" + "JSON: " + "\n" + "	" + jarr + "\n");
            return true;
        }
        return false;
    }

    public static JSONObject parseJson(String filename) {
        try {
            FileReader reader = new FileReader(new File(filename));
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonobject = (JSONObject) jsonParser.parse(reader);
            return jsonobject;
        } catch (Exception e) {
            System.out.println("Could not parse json.");
            return null;
        }

    }

    public static void writeJsonFile(String curl, String filename) {
        BufferedReader reader = readFromGit(curl);
        if (reader != null) {
            try {
                String line = null;
                BufferedWriter filewriter = new BufferedWriter(new FileWriter(filename));
                while ((line = reader.readLine()) != null) {
                    filewriter.append(line + "\n");
                }
                filewriter.close();
            } catch (IOException e) {
                System.out.println("No JSON file found.");
            }

        }

    }

}