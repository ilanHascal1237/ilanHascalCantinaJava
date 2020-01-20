
/**
 * @author ILAN HASCAL Ilanhascal1237@gmail.com
 * 
 *         Program fetches SystemViewController.json , parses and then allows
 *         for stdin commands from the terminal
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
    /*
    
    */
    public static void main(String[] args) {
        String curl = "https://raw.githubusercontent.com/jdolan/quetoo/master/src/cgame/default/ui/settings/SystemViewController.json";
        String filename = "systemView.json";
        writeJsonFile(curl, filename);
        repl(parseJson(filename));

    }

    /**
     * @param sys - JSON object
     */
    public static void repl(JSONObject sys) {
        Scanner consolereader = new Scanner(System.in);
        System.out.print("Enter selector or [exit]: ");
        String consoleinput = consolereader.nextLine();
        while (!consoleinput.toLowerCase().equals("exit")) {
            select(consoleinput, sys);
            System.out.print("Enter selector or [exit]: ");
            consoleinput = consolereader.nextLine();
        }
        consolereader.close();
    }

    /**
     * 
     * Modular methos works for each selctor either
     * class,classNames,identifierclass. Aftwe
     * 
     * @param line - console input
     * @param sys  - parsed josn object that is returned from parseJson
     */
    public static void select(String line, JSONObject sys) {
        if (line.contains(".")) {
            line = line.replace(".", "");
            selectClasses(line, sys, "classNames");
        } else if (line.contains("#")) {
            line = line.replace("#", "");
            selectClasses(line, sys, "identifier");
        } else {
            selectClasses(line, sys, "class");
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
     * @param sys     - overall json object
     * @param pattern - is the selector we are using
     * @return - number of iterations
     * 
     *         For each key in the JSON object, if its values is an array, we know
     *         that we need to examine each element in the array. We then cast all
     *         generic java objects into JSON objects.Then given a JSON object, we
     *         call checkJSONObject. If this method returns true, we increment the
     *         number of views we have seen.
     * 
     * 
     */
    public static int recurseClasses(String line, JSONObject sys, String pattern) {
        int viewsfound = 0;
        for (Object o : sys.keySet()) {
            if (sys.get(o) instanceof JSONArray) { // loop case, for an array
                JSONArray ja = (JSONArray) sys.get(o);
                for (Object n : ja.toArray()) {
                    if (n instanceof JSONObject) {
                        if (checkJSONObject(line, (JSONObject) n, pattern)) // call checkJSON for contents
                            viewsfound++; // if ture,incerement views
                        viewsfound += recurseClasses(line, (JSONObject) n, pattern);// keep searching and increment for
                                                                                    // nested JSON
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
     * @param line    - console inout
     * @param jo      - json object
     * @param pattern - selector we are using
     * @return - wether we are successfuly able to find the view
     * 
     *         After examining the JSON file, classNames is most of the time
     *         associated with an array so we call checkJSONArray() on ln 144. If it
     *         is either of the other 2 selectors, we compare the value assoicated
     *         with the selector is equal to the console input. We then print and
     *         return true.
     * 
     */
    public static boolean checkJSONObject(String line, JSONObject jo, String pattern) {
        if (jo.containsKey(pattern)) {
            if (pattern != "classNames") {
                if (jo.get(pattern).equals(line)) {
                    System.out.println("VIEW: " + jo.get("class") + "\n" + "JSON: ");
                    for (Object key : jo.keySet()) {
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
     * Method checks if console input your searching for matches the JSON array,
     * returns true if so.
     * 
     * @param jarr     - is the json array within the object
     * @param viewname - is the type of selector we are woking with
     * @param line     - Console input
     * @return - wether or not we have found the right view
     * 
     */
    public static boolean checkJSONArray(JSONArray jarr, Object viewname, String line) {
        if (jarr.contains(line)) {
            System.out.println("VIEW: " + viewname + "\n" + "JSON: " + "\n" + "	" + jarr + "\n");
            return true;
        }
        return false;
    }

    /**
     * @param filename - name of file
     * @return - return the parse JSON
     */
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

    /***
     * @param curl     - url
     * @param filename - name of file
     * @return - return the parse JSON
     * 
     */
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