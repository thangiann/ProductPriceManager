package controller;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import loader.*;
import controller.*;
import dto.ProductDTO;
import dto.YearDTO;



public class ProductPriceDataManagerController implements IController {
	

	@Override
	public int initializeFromIni(String iniPath, String delimiter) throws IOException{
		
		String dataPath;
		String metadataPath;

		try (Scanner in = new Scanner( new File(iniPath))) {
			
			String rawDataPath = in.nextLine().trim();
			String[] splitPath = rawDataPath.split("=");
			dataPath = splitPath[1];

			String rawMetadataPath = in.nextLine().trim();
			String[] splitMetaData = rawMetadataPath.split("=");
			metadataPath = splitMetaData[1];

		} catch (Exception e) {
			e.printStackTrace();
        	return -1;
		}

		DataLoader dl = new DataLoader();
		
		int lines = dl.loadData(metadataPath, delimiter);
		dl.loadMetadata(metadataPath, delimiter); 

		return lines;
	}
	
	@Override
	public void loadFile(String path, String delimiter) throws IOException{

		DataLoader dl = new DataLoader();

		dl.loadData(path, delimiter);
	}
	
	@Override
	public List<YearDTO> listYears(){

		ArrayList years = new ArrayList<>();
	}
	
	@Override
	public List<ProductDTO> listProducts(){
		
	}
	
	@Override
	public YearDTO getYearMeasurements(int year) {
		
	}

	@Override
	public ProductDTO getProductMeasurements(String productName){

	}
	
}