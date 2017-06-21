package zadanie4_1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matus Olejnik <matus.olejnik@gmail.com>
 */
public class Program {

    ArrayList<Rule> rulesArr = new ArrayList<>();
    Fact fact = new Fact();
    Rule newRule;
    boolean isReplaced = false;
    boolean goodFact = true;
    ArrayList<Rule> newFacts = new ArrayList<>();
    boolean factAdded = true;

    public Program() {
        fact.loadData("facts.txt");
        loadRules("rules.txt");
    }

    //depth search
    public void search(Rule rule) {
        if (rule.hasVariable()) {
            // System.out.println("hasVariable");
            //chceck first rule, second ...
            for (int j = 0; j < rule.getRules().size(); j++) {
                //compare with all facts
                for (int k = 0; k < fact.getFacts().size(); k++) {
                    // System.out.println("RULE " + rule.getRules().get(j));
                    if (rule.equalsFact(j, fact.getFactWords(k))) {
                        newRule = new Rule(rule);
                        // System.out.println("equalsfacts " + j);
                        for (int m = 0; m < rule.getRulesWords(j).size(); m++) {
                            //   System.out.println(rule.getRulesWords(j).get(m));
                            //ak sme sa dostali k premennej nahradime ju prisluchajucim udajom a spustime rekurzivne hladanie
                            //v temto novo vytvorenom pravidle
                            if (rule.getRulesWords(j).get(m).indexOf("?") > -1) {
                                newRule.replaceVariables(newRule.getRulesWords(j).get(m), fact.getFactWords(k).get(m));
                                isReplaced = true;
                                // System.out.println("isReplaced");
                            }
                        }
                        if (isReplaced) {
                            //  System.out.println("newRule");
                            isReplaced = false;
                            //rekurzivne prehladavanie
                            search(newRule);
                        }
                    }
                    else {
                        //   System.out.println("rule not equals fact");
                    }

                }
            }
        }
        //ak uz mame pravidlo ktore nema ziadne premenne ideme skontrolovat
        //ci su vsetky podpravidla pravdive
        else {
            //System.out.println("HAS NOT VARIABLE");
            boolean tmp = false;
            boolean tmp2 = false;
            for (int i = 0; i < rule.getRules().size(); i++) {
                //ak pravidlo obsahuje nerovnost <>
                if (rule.getHasNotChar()) {
                    //System.out.println("HAS <>");
                    if (!tmp2) {
                        //prehladame podmienky az kym nenajdeme <>
                        for (int j = 0; j < rule.getRules().size(); j++) {
                            if (rule.getRulesWords(j).get(0).equals("<>")) {
                                //System.out.println("IF <>");
                                //ak sa premenne ktore sa nemaju rovnat rovnaju tak podmienka nie je spravna ideme dalej
                                if (rule.getRulesWords(j).get(1).equals(rule.getRulesWords(j).get(2))) {
                                    goodFact = false;
                                    // System.out.println("IF <> ROVNA SA");
                                    tmp = true;
                                    break;
                                }
                                else {
                                    tmp2 = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (tmp) {
                        break;
                    }

                }
                //ak pravidlo obsahovalo <> a dane premenne sa nerovnali
                //skontrolujeme ostatne podmienky v pravidle
                if (tmp2) {
                    goodFact = false;
                    for (int k = 0; k < fact.getFacts().size(); k++) {
                        //System.out.println(rule.getRules().get(i) + " ?= " + fact.getFacts().get(k));
                        //ak sme na podmienke ktoru uz sme testovali preskocime ju
                        if (rule.getRules().get(i).indexOf("<") > -1) {
                            goodFact = true;
                        }
                        //kontrola ci sa podmienky zhoduju s pravidlami
                        else if (rule.getRules().get(i).equals(fact.getFacts().get(k))) {
                            goodFact = true;
                            break;
                        }
                    }
                }
                //ak pravidlo nema nerovnost <> tak skontrolujeme vsetku jeho podmienky s faktami
                if (!rule.getHasNotChar()) {
                    // System.out.println("NEMA <>");
                    goodFact = false;
                    for (int k = 0; k < fact.getFacts().size(); k++) {
                        if (rule.getRules().get(i).equals(fact.getFacts().get(k))) {
                            goodFact = true;
                            break;
                        }
                    }
                }
                if (!goodFact) {
                    break;
                }
            }
            //ak sme nasli pravidlo ktore je cele pravidve mozme vykonat akciu
            if (goodFact) {
                // System.out.println("GOOD FACT");
                factAdded = true;
                //prejdeme vsetky akcie pravidla
                for (int p = 0; p < rule.getActions().size(); p++) {
                    //ak je akcia pridaj
                    if (rule.getActions().get(p).contains("pridaj")) {
                        // System.out.println("obsahuje pridaj");
                        //zistime co sa ma pridat
                        String addWhat = rule.getActions().get(p).replaceFirst("pridaj ", "");
                        //prejdu sa vsetky fakty ci uz tam taky nie je
                        for (int s = 0; s < fact.getFacts().size(); s++) {
                            if (addWhat.equals(fact.getFacts().get(s))) {
                                //  System.out.println("nepridany fakt = " + addWhat);
                                factAdded = false;
                                break;
                            }
                        }
                        //ak taky fakt este nie je prida sa
                        if (factAdded) {
                            System.out.println("pridany fakt = " + addWhat);

                            fact.addFact(addWhat);
                            factAdded = true;
                        }
                    }
                    //ak je akcia sprava tak sa len vypise nasledujuca sprava za klucovym slovom
                    if (factAdded && (rule.getActions().get(p).contains("sprava"))) {
                        String what = rule.getActions().get(p).replaceFirst("sprava ", "");
                        System.out.println("SPRAVA " + what);
                    }
                    //ak je akcia vymaz
                    if (rule.getActions().get(p).contains("vymaz")) {
                        //zisti sa co sa ma vymazat
                        String delWhat = rule.getActions().get(p).replaceFirst("vymaz ", "");
                        //prejdu sa vsetky fakty a ak v nich je taky co sa ma vymazat vymaze sa
                        for (int s = 0; s < fact.getFacts().size(); s++) {
                            if (delWhat.equals(fact.getFacts().get(s))) {
                                //  System.out.println("nepridany fakt = " + addWhat);
                                System.out.println("VYMAZ " + fact.getFacts().get(s));

                                fact.getFacts().remove(s);
                                factAdded = false;
                                break;
                            }
                            else {
                                factAdded = false;
                            }
                        }
                    }
                }

            }
        }
    }

    public void runProgram() {
        //chceck all rules
        //kym sa pridal nejaky novy fakt skontrolujeme vsetky pravidla
        while (factAdded) {
            // System.out.println("runprogram cycle --------------------");
            for (int i = 0; i < rulesArr.size(); i++) {
                isReplaced = false;
                goodFact = true;
                factAdded = true;
                //   System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
                search(rulesArr.get(i));
            }
        }
    }

    public void loadRules(String path) {
        ArrayList<String> result = new ArrayList<>();
        result = null;
        FileReader fr = null;
        try {
            fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                //System.out.println("aaa");
                Rule r = new Rule();
                r.setName(line); //DruhyRodic1:
                line = br.readLine();
                r.addRule(line.substring(4, line.length() - 1)); //AK ((?X je rodic ?Y)(manzelia ?X ?Z)) --> (?X je rodic ?Y)(manzelia ?X ?Z) 
                line = br.readLine();
                r.addAction(line.substring(7, line.length() - 1)); //POTOM ((pridaj ?Y je stryko ?X)(sprava ?X ma stryka)) --> (pridaj ?Y je stryko ?X)(sprava ?X ma stryka)
                rulesArr.add(r);
                line = br.readLine();
                line = br.readLine(); //for while cycle
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
