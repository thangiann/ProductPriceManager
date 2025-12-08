package loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class DataLoader {

    private ArrayList<String[]> data;
    private Map<String, String> aliases;
    private Map<String, ArrayList<String>> categories;


    public DataLoader(){
        this.data = new ArrayList<>();
        this.aliases = new HashMap<>();
        this.categories = new HashMap<>();
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
                aliases.put(parsedData[1], parsedData[0]);

                if (categories.containsKey(parsedData[2])){
                    categories.get(parsedData[2]).add(parsedData[0]);
                }
                else {
                    categories.put(parsedData[3], new ArrayList<>());
                    categories.get(parsedData[3]).add(parsedData[0]);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found ");
            e.printStackTrace();
        } catch (IOException e){
            System.err.println("Error reading file: " + datapath);
            e.printStackTrace();
        }
    }


    public ArrayList<String[]> getData(){
        return this.data;
    }


    public Map<String, ArrayList<String>> getCategories(){
        return this.categories;
    }

    public Map<String, String> getAliases(){
        return this.aliases;
    }

}
