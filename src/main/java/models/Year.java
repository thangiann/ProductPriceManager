package models;

import java.util.ArrayList;
import java.util.List;

import dto.MeasurementDTO;
import dto.YearDTO;
import loader.DataLoader;

public class Year {

    private int year;
    private Top10 top10;
    private List<MeasurementDTO> measurements;
    private DataLoader dl;

    public Year(int year, DataLoader data){
        this.year = year;
        this.top10 = new Top10(year, data);
        this.dl = data;
        this.measurements = new ArrayList<>();
    }

    public YearDTO createYearDTO(){

        ArrayList<String> headlines = getTop10Headlines();
        ArrayList<String> aliases = getTop10Aliases();
        createMeasurementsDTOS();

        return new YearDTO(year, this.measurements, aliases, headlines);
    }


    //helper methods
    private ArrayList<String> getTop10Headlines(){
        return top10.top10Headlines();
    }

    private ArrayList<String> getTop10Aliases(){
        return top10.top10Aliases();
    }

    private void createMeasurementsDTOS(){

        ArrayList<String[]> data = dl.getData();

        for (int productId = 1; productId < 32; productId++){
            

            String name = data.get(0)[productId];
            
            Product product = new Product(name, productId);

            Measurement measm = new Measurement(year, product, dl);
            MeasurementDTO measmDTO = measm.createMeasurmentDTO();

            this.measurements.add(measmDTO);
        }
    }
}
