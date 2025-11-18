package models;

import java.util.ArrayList;

import dto.YearDTO;

public class Year {

    private int year;
    private Top10 top10;

    public Year(int year){
        this.year = year;
        this.top10 = new Top10(year);
    }

    public YearDTO createYearDTO(){

        ArrayList<String> headlines = getTop10Headlines();
        ArrayList<String> aliases = getTop10Aliases();

        return new YearDTO(year, null, aliases, headlines);
    }


    //helper methods
    private ArrayList<String> getTop10Headlines(){
        return top10.top10Headlines();
    }

    private ArrayList<String> getTop10Aliases(){
        return top10.top10Aliases();
    }
}
