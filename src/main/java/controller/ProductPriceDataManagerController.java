package controller;

import java.io.IOException;
import java.util.List;

import controller.*;
import dto.ProductDTO;
import dto.YearDTO;

public class ProductPriceDataManagerController implements IController {
	
	@Override
	public int initializeFromIni(String iniPath, String delimiter) throws IOException{
		return 0;
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
}