package zadanie4_1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Matus Olejnik <matus.olejnik@gmail.com>
 */
public class Rule {

    private String name = "";
    private ArrayList<String> rules = new ArrayList<>();
    private ArrayList<String> actions = new ArrayList<>();
    //jednotlive podmienky rozdelene po slovach do arraylistu
    private ArrayList<ArrayList<String>> rulesWords = new ArrayList<>();
    private boolean hasVariable = false;
    boolean hasNotChar = false;

    //konstruktor ktory prekopiruje udaje z ineho pravidla bez referencii
    public Rule(Rule r) {
        this.name = r.getName();

        for (int i = 0; i < r.getRules().size(); i++) {
            this.rules.add("");
            this.rules.set(i, r.getRules().get(i));
        }
        for (int i = 0; i < r.getActions().size(); i++) {
            this.actions.add("");
            this.actions.set(i, r.getActions().get(i));
        }
        // this.rules = r.getRules();
        // this.actions = r.getActions();
        // this.hasVariable = r.getHasvariable();
        chechVariables();
        this.hasNotChar = r.getHasNotChar();
        //this.rulesWords = r.getRulesWords();
        splitRulesToWords();
    }

    public Rule() {

    }

    public boolean getHasNotChar() {
        return hasNotChar;
    }

    public boolean getHasvariable() {
        return hasVariable;
    }

    //vrati pozadovanu podmienky rozdelenu do arraylistu po slovach
    public ArrayList<String> getRulesWords(int index) {
        return rulesWords.get(index);
    }

    //vrati vsetky podmienky roztriedene do arraylistu po slovach
    public ArrayList<ArrayList<String>> getRulesWords() {
        return rulesWords;
    }

    public boolean hasVariable() {
        return hasVariable;
    }

    //nahradi vsetky pozadovane premenne pozadovanym udajom aj v podmienkach aj akciach
    public void replaceVariables(String variable, String fact) {
        for (int i = 0; i < rules.size(); i++) {
            rules.set(i, rules.get(i).replaceAll("\\" + variable, fact));
        }
        for (int i = 0; i < actions.size(); i++) {
            actions.set(i, actions.get(i).replaceAll("\\" + variable, fact));
        }
        splitRulesToWords();
        chechVariables();
    }

    public void splitRulesToWords() {
        rulesWords.clear();
        for (int i = 0; i < rules.size(); i++) {
            String s = rules.get(i);
            String[] ss = s.split("( )");
            rulesWords.add(new ArrayList<>(Arrays.asList(ss)));
        }
    }

    public void chechVariables() {
        hasVariable = false;
        for (int i = 0; i < rules.size(); i++) {
            if (rules.get(i).indexOf("?") > -1) {
                hasVariable = true;

                break;
            }
        }
    }

    public void checkHasNotChar() {
        for (int i = 0; i < rules.size(); i++) {
            if (rules.get(i).indexOf("<>") > -1) {
                hasNotChar = true;

                break;
            }
        }
    }

    public boolean equalsFact(int ruleIndex, ArrayList<String> fact) {
        //ak sa dlzka pravidla nerovna dlzke faktu tak sa urcite nebude zhodovat
        if (rulesWords.get(ruleIndex).size() != fact.size()) {
            return false;
        }
        //kontrola ci sa shoduju slova v podmienke s faktami (premenne sa preskakuju)
        for (int i = 0; i < fact.size(); i++) {
            if (rulesWords.get(ruleIndex).get(i).charAt(0) == '?') {
                continue;
            }
            else if (!rulesWords.get(ruleIndex).get(i).equals(fact.get(i))) {
                return false;
            }
        }
        return true;
    }

    public void addRule(String s) {
        String[] arr;
        arr = s.split("\\)\\(");
        StringBuilder sb = new StringBuilder(arr[arr.length - 1]);
        sb.deleteCharAt(sb.length() - 1);
        arr[arr.length - 1] = sb.toString();
        sb = new StringBuilder(arr[0]);
        sb.deleteCharAt(0);
        arr[0] = sb.toString();

        for (int i = 0; i < arr.length; i++) {
            if (!hasVariable || !hasNotChar) {
                if (arr[i].indexOf("?") > -1) {
                    hasVariable = true;
                }
                if (arr[i].indexOf("<") > -1) {
                    hasNotChar = true;
                }
            }
            rules.add(arr[i]);
            //System.out.println(rules.get(i));
        }
        splitRulesToWords();
        // rules.addAll(Arrays.asList(arr));
    }

    public void addAction(String s) {
        String[] arr;
        arr = s.split("\\)\\(");
        StringBuilder sb = new StringBuilder(arr[arr.length - 1]);
        sb.deleteCharAt(sb.length() - 1);
        arr[arr.length - 1] = sb.toString();
        sb = new StringBuilder(arr[0]);
        sb.deleteCharAt(0);
        arr[0] = sb.toString();

        actions.addAll(Arrays.asList(arr));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getRules() {
        return rules;
    }

    public ArrayList<String> getActions() {
        return actions;
    }

}
