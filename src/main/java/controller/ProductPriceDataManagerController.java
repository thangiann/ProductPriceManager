package controller;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import loader.DataLoader;
import models.Measurement;
import models.Product;
import models.Top10;
import dto.*;

public class ProductPriceDataManagerController implements IController {

	private DataLoader dataLoader;
	private List<Product> products;

	public ProductPriceDataManagerController() {
		this.dataLoader = null;
		this.products = new ArrayList<>();
	}

	@Override
	public int initializeFromIni(String iniPath, String delimiter) throws IOException {
		String dataPath;
		String metadataPath;

		try (Scanner in = new Scanner(new File(iniPath))) {
			String rawDataPath = in.nextLine().trim();
			String[] splitPath = rawDataPath.split("=");
			dataPath = splitPath[1].trim();

			String rawMetadataPath = in.nextLine().trim();
			String[] splitMetaData = rawMetadataPath.split("=");
			metadataPath = splitMetaData[1].trim();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		// Create and store DataLoader
		this.dataLoader = new DataLoader();

		// Load metadata first, then data
		dataLoader.loadMetadata(metadataPath, delimiter);
		int lines = dataLoader.loadData(dataPath, delimiter);

		// Initialize products from data headers
		initializeProducts();

		return lines - 1; // Subtract header line
	}

	@Override
	public void loadFile(String path, String delimiter) throws IOException {
		this.dataLoader = new DataLoader();
		dataLoader.loadData(path, delimiter);
		initializeProducts();
	}

	@Override
	public List<YearDTO> listYears() {
		if (dataLoader == null || dataLoader.getData().isEmpty()) {
			return new ArrayList<>();
		}

		List<YearDTO> years = new ArrayList<>();
		// Skip header row (index 0), start from index 1
		for (int i = 1; i < dataLoader.getData().size(); i++) {
			String[] row = dataLoader.getData().get(i);
			int year = Integer.parseInt(row[0]);
			years.add(getYearMeasurements(year));
		}
		return years;
	}

	@Override
	public List<ProductDTO> listProducts() {
		if (dataLoader == null || dataLoader.getData().isEmpty()) {
			return new ArrayList<>();
		}

		List<ProductDTO> productDTOs = new ArrayList<>();
		for (Product p : products) {
			productDTOs.add(getProductMeasurements(p.getName()));
		}
		return productDTOs;
	}

	@Override
	public YearDTO getYearMeasurements(int year) {
		if (dataLoader == null)
			return null;

		// Create measurements for all products in this year
		List<MeasurementDTO> measurements = new ArrayList<>();
		for (Product p : products) {
			Measurement m = new Measurement(year, p, dataLoader);
			measurements.add(m.createMeasurmentDTO());
		}

		// Get Top10 data
		Top10 top10 = new Top10(year, dataLoader);
		List<String> top10Aliases = top10.top10Aliases();
		List<String> top10Headlines = top10.top10Headlines();

		return new YearDTO(year, measurements, top10Aliases, top10Headlines);
	}

	@Override
	public ProductDTO getProductMeasurements(String productName) {
		if (dataLoader == null || dataLoader.getData().isEmpty()) {
			return null;
		}

		// Find the product
		Product product = findProductByName(productName);
		if (product == null)
			return null;

		List<MeasurementDTO> measurements = new ArrayList<>();
		// Skip header row, iterate through all years
		for (int i = 1; i < dataLoader.getData().size(); i++) {
			String[] row = dataLoader.getData().get(i);
			int year = Integer.parseInt(row[0]);
			Measurement m = new Measurement(year, product, dataLoader);
			measurements.add(m.createMeasurmentDTO());
		}

		return new ProductDTO(productName, measurements);
	}

	@Override
	public ProductDTO filterProductMeasurements(String productName, int minYear, int maxYear) {
		ProductDTO fullData = getProductMeasurements(productName);
		if (fullData == null)
			return null;

		List<MeasurementDTO> filtered = new ArrayList<>();
		for (MeasurementDTO m : fullData.getMeasurements()) {
			if (m.getYear() >= minYear && m.getYear() <= maxYear) {
				filtered.add(m);
			}
		}

		return new ProductDTO(productName, filtered);
	}

	@Override
	public List<ProductHighlightDTO> reportProductHighlights(String productAlias) {
		if (dataLoader == null || dataLoader.getData().isEmpty()) {
			return new ArrayList<>();
		}

		List<ProductHighlightDTO> highlights = new ArrayList<>();

		// Check each year for this product in top10
		for (int i = 1; i < dataLoader.getData().size(); i++) {
			String[] row = dataLoader.getData().get(i);
			int year = Integer.parseInt(row[0]);

			Top10 top10 = new Top10(year, dataLoader);
			List<String> aliases = top10.top10Aliases();

			if (aliases.contains(productAlias)) {
				List<String> headlines = top10.top10Headlines();
				// Find matching headline (same position as alias)
				int index = aliases.indexOf(productAlias);
				String headline = (index < headlines.size()) ? headlines.get(index) : "";
				highlights.add(new ProductHighlightDTO(year, headline));
			}
		}

		return highlights;
	}

	@Override
	public List<CategoryHighlightDTO> reportCategoryHighlights(String category) {
		if (dataLoader == null || dataLoader.getData().isEmpty() || dataLoader.getMetadata().isEmpty()) {
			return new ArrayList<>();
		}

		List<CategoryHighlightDTO> highlights = new ArrayList<>();

		// Check each year
		for (int i = 1; i < dataLoader.getData().size(); i++) {
			String[] row = dataLoader.getData().get(i);
			int year = Integer.parseInt(row[0]);

			Top10 top10 = new Top10(year, dataLoader);
			List<String> aliases = top10.top10Aliases();
			List<String> headlines = top10.top10Headlines();

			// Check each product in top10
			for (int j = 0; j < aliases.size(); j++) {
				String productAlias = aliases.get(j);
				String productCategory = getCategoryForProduct(productAlias);

				if (category.equals(productCategory)) {
					String headline = (j < headlines.size()) ? headlines.get(j) : "";
					highlights.add(new CategoryHighlightDTO(year, productAlias, headline));
				}
			}
		}

		return highlights;
	}

	@Override
	public List<ProductStatsDTO> computeProductStats() {
		if (dataLoader == null || products.isEmpty()) {
			return new ArrayList<>();
		}

		List<ProductStatsDTO> stats = new ArrayList<>();

		for (Product p : products) {
			double min = Double.MAX_VALUE;
			double max = Double.MIN_VALUE;
			double sum = 0;
			double lastValue = 0;
			int count = 0;

			// Calculate stats across all years
			for (int i = 1; i < dataLoader.getData().size(); i++) {
				String[] row = dataLoader.getData().get(i);
				int year = Integer.parseInt(row[0]);

				Measurement m = new Measurement(year, p, dataLoader);
				MeasurementDTO dto = m.createMeasurmentDTO();
				double value = dto.getValue();

				if (value < min)
					min = value;
				if (value > max)
					max = value;
				sum += value;
				count++;
				lastValue = value; // Last iteration will be the last year
			}

			double average = (count > 0) ? sum / count : 0;
			stats.add(new ProductStatsDTO(p.getName(), min, average, max, lastValue));
		}

		return stats;
	}

	@Override
	public List<Top10AppearanceDTO> computeTop10ProductAppearances() {
		if (dataLoader == null || dataLoader.getData().isEmpty()) {
			return new ArrayList<>();
		}

		Map<String, Integer> counts = new HashMap<>();

		// Count appearances across all years
		for (int i = 1; i < dataLoader.getData().size(); i++) {
			String[] row = dataLoader.getData().get(i);
			int year = Integer.parseInt(row[0]);

			Top10 top10 = new Top10(year, dataLoader);
			List<String> aliases = top10.top10Aliases();

			for (String alias : aliases) {
				counts.put(alias, counts.getOrDefault(alias, 0) + 1);
			}
		}

		List<Top10AppearanceDTO> result = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : counts.entrySet()) {
			result.add(new Top10AppearanceDTO(entry.getKey(), entry.getValue()));
		}

		return result;
	}

	@Override
	public List<Top10AppearanceDTO> computeTop10CategoryAppearances() {
		if (dataLoader == null || dataLoader.getData().isEmpty() || dataLoader.getMetadata().isEmpty()) {
			return new ArrayList<>();
		}

		Map<String, Integer> counts = new HashMap<>();

		// Count category appearances across all years
		for (int i = 1; i < dataLoader.getData().size(); i++) {
			String[] row = dataLoader.getData().get(i);
			int year = Integer.parseInt(row[0]);

			Top10 top10 = new Top10(year, dataLoader);
			List<String> aliases = top10.top10Aliases();

			for (String alias : aliases) {
				String category = getCategoryForProduct(alias);
				if (category != null && !category.isEmpty()) {
					counts.put(category, counts.getOrDefault(category, 0) + 1);
				}
			}
		}

		List<Top10AppearanceDTO> result = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : counts.entrySet()) {
			result.add(new Top10AppearanceDTO(entry.getKey(), entry.getValue()));
		}

		return result;
	}

	@Override
	public List<YearDTO> reportAllYearsAllProductPrices() {
		return listYears();
	}

	// Helper methods

	private void initializeProducts() {
		products.clear();
		if (dataLoader == null || dataLoader.getData().isEmpty()) {
			return;
		}

		String[] headers = dataLoader.getData().get(0);
		// Start from index 1 to skip "year" column
		for (int i = 1; i < headers.length; i++) {
			// Skip top10 columns (typically the last 2 columns)
			if (headers[i].equalsIgnoreCase("CommodityTop10") ||
					headers[i].equalsIgnoreCase("News Headline")) {
				break;
			}
			products.add(new Product(headers[i], i));
		}
	}

	private Product findProductByName(String name) {
		for (Product p : products) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	private String getCategoryForProduct(String productAlias) {
		if (dataLoader.getMetadata().isEmpty()) {
			return "Unknown";
		}

		// Metadata format: [ProductName, Alias, Category]
		for (String[] metaRow : dataLoader.getMetadata()) {
			if (metaRow.length >= 3 && metaRow[1].equals(productAlias)) {
				return metaRow[2];
			}
		}
		return "Unknown";
	}
}