package models;

import java.util.ArrayList;
import java.util.Arrays;

import loader.*;

public class Top10 {
    
    private String delimiter = "|";
    private int headlinesPosition = 33;
    private int alliasesPosition = 32;
    private DataLoader dl;
    private int year;

    public Top10(int year){
        this.dl = new DataLoader();
        this.year = year;
    }

    public ArrayList<String> top10Headlines(){
        
        String[] yearLine = dl.getData().get(year - 1959);
        String[] top10Array = yearLine[headlinesPosition].split(delimiter);
        ArrayList<String> top10 = new ArrayList<>(Arrays.asList(top10Array));
        
        return top10;
    }

    public ArrayList<String> top10Aliases(){
        
        String[] yearLine = dl.getData().get(year - 1959);
        String[] top10Array = yearLine[alliasesPosition].split(delimiter);
        ArrayList<String> top10 = new ArrayList<>(Arrays.asList(top10Array));
        
        return top10;
    }
}
