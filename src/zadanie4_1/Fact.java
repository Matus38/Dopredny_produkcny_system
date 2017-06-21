package zadanie4_1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matus Olejnik <matus.olejnik@gmail.com>
 */
public class Fact {
     private ArrayList<ArrayList<String>> factWords = new ArrayList<>();
     ArrayList<String> facts = new ArrayList<>();

     public void addFact(String fact) {
         facts.add(fact);
         factWords.add(new ArrayList<>(Arrays.asList(fact.split("( )"))));
    }
     
     public ArrayList<String> getFacts(){
         return facts;
     }
     
     public ArrayList<String> getFactWords(int index){
         return factWords.get(index);
     }
     
    public void loadData(String path) {
        FileReader fr = null;
        int i = 0;
        try {
            fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            while (s != null) {
                String ss = s.substring(1, s.length() - 1); 
                facts.add(ss);//(fact) --> fact
                factWords.add(new ArrayList<>(Arrays.asList(ss.split("( )"))));
                s = br.readLine();
            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Fact.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(Fact.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                fr.close();
            }
            catch (IOException ex) {
                Logger.getLogger(Fact.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
