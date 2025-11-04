package controller;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import controller.*;
import dto.ProductDTO;
import dto.YearDTO;



public class ProductPriceDataManagerController implements IController {
	

	@Override
	public int initializeFromIni(String iniPath, String delimiter) throws IOException{
		
		try (Scanner in = new Scanner(iniPath)) {
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void loadFile(String path, String delimiter) throws IOException{

	}
	
	@Override
	public List<YearDTO> listYears(){
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