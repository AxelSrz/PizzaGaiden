/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzagaiden;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Evan
 */
public class Database {
    private static final String FILE_EXT = "txt";
    
    /**
     *
     * @param file
     * @param object
     * @return
     * @throws IOException
     */
    public static boolean addObject(File file, Object object) throws IOException {
        if(file.getName().endsWith(FILE_EXT)) {
            try (FileWriter pw = new FileWriter(file, true)) {
                System.out.println(object.toString());
                pw.write(object.toString() + "\n");
                pw.close();
            }
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     *
     * @param file
     * @param object
     * @return
     * @throws java.io.IOException
     */
    public static ArrayList<String> getContents(File file) throws IOException {
        ArrayList<String> contents = new ArrayList<>(); 
        String strTmp;
        if(file.getName().endsWith(FILE_EXT)) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while(br.ready()) {
                strTmp = br.readLine();
                contents.add(strTmp);
            }
            br.close();
        }
        return contents;
    }
    
//    public static boolean removeObject(File file, Object object) {
//        if(file.getName().endsWith(FILE_EXT)) {
//        }
//    }
}
