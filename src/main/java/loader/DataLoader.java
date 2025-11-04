package loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class DataLoader {
    
    private ArrayList<String[]> data;
    private ArrayList<String[]> metadata;

    public DataLoader(){
        this.data = new ArrayList<>();
        this.metadata = new ArrayList<>();
    }

    public int loadData(String datapath, String delimiter) throws IOException{

        try (BufferedReader br = new BufferedReader(new FileReader(datapath))){
            String line;

            while ((line = br.readLine()) != null){
                String[] parsedData = line.split(delimiter);
                this.data.add(parsedData);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not dound ");
            e.printStackTrace();
        }

        return data.size();
    }

        public void loadMetadata(String datapath, String delimiter) throws IOException{

        try (BufferedReader br = new BufferedReader(new FileReader(datapath))){
            String line;

            while ((line = br.readLine()) != null){
                String[] parsedData = line.split(delimiter);
                this.metadata.add(parsedData);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not dound ");
            e.printStackTrace();
        } catch (IOException e){
            System.err.println("Error reading file: " + datapath);
            e.printStackTrace();
        }
    }

        public ArrayList<String[]> getData(){
            return this.data;
        }

        public ArrayList<String[]> getMetadata(){
            return this.metadata;
        }
    

    public static void main(String[] args) {
        String dataFile = "src/test/resources/Input/data.tsv";
        String metadataFile = "src/test/resources/Input/metadata.tsv";
        
        DataLoader dl = new DataLoader();

        try {
            dl.loadMetadata(dataFile, "/");
        } catch (IOException e) {
            System.err.println("Could not load metadata: " + e.getMessage());
        }          

        ArrayList<String[]> data = dl.getData();

        String[] line = data.get(0);
        for (String s:line){
            System.out.println(s);
        }
    }

}
