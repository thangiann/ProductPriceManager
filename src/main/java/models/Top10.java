package models;

import java.util.ArrayList;
import java.util.Arrays;

import loader.*;


public class Top10 {
    
    private String delimitierHeadlines = "|";
    private String delimiterAliases = ",";
    private int headlinesPosition = 33;
    private int alliasesPosition = 32;
    private DataLoader dl;
    private int year;

    public Top10(int year, DataLoader data){
        this.dl = data;
        this.year = year;
    }

    public ArrayList<String> top10Headlines(){
        String[] yearLine = dl.getData().get(year - 1959);
        String[] top10Array = yearLine[headlinesPosition].split(delimitierHeadlines);
        ArrayList<String> top10 = new ArrayList<>(Arrays.asList(top10Array));
        return top10;
    }


    public ArrayList<String> top10Aliases(){
        String[] yearLine = dl.getData().get(year - 1959);
        String[] top10Array = yearLine[alliasesPosition].replace("\"", "").split(delimiterAliases);
        ArrayList<String> top10 = new ArrayList<>(Arrays.asList(top10Array));
        return top10;
    }
}
