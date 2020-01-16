
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
    /*
     * Main method is prompting user with Scanner Class.
     * 
     * 
     * 
     */
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

    /*
     * This method is going to identify what type of identifier the user inputs
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

    /*
     * 
     * Method uses the public gutHub link to fetch its contents
     *
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

}