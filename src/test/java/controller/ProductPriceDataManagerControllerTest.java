package controller;


import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dto.*;


public class ProductPriceDataManagerControllerTest {
    private ProductPriceDataManagerController controller;

    @Before
    public void setUp() throws Exception {
        controller = new ProductPriceDataManagerController();
    }

    @Test
    public void testInitializeFromIni() throws IOException {
        int yearsLoaded = controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        assertTrue(yearsLoaded >= 64);
    }

    @Test
    public void testInitializeFromIni_InvalidPath() throws IOException {
        int result = controller.initializeFromIni("nonexistent.ini", "\t"); // Should return -1 on error
        assertEquals(-1, result);
    }


    @Test
    public void testLoadFile() throws IOException {
        controller.loadFile("src/test/resources/Input/data.tsv", "\t");
        List<ProductDTO> products = controller.listProducts();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    public void testLoadFile_NonExistentFile() throws IOException {
        controller.loadFile("nonexistent_file.tsv", "\t");
        List<ProductDTO> products = controller.listProducts();
        assertTrue(products.isEmpty());
    }


    @Test
    public void testListYears() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<YearDTO> years = controller.listYears();
        assertNotNull(years);
        assertFalse(years.isEmpty());
    }

    @Test
    public void testListYears_CorrectYears() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<YearDTO> years = controller.listYears();
        YearDTO firstYear = years.get(0);
        assertEquals(1960, firstYear.getYear());
    }

    @Test
    public void testListYears_NoDataLoaded() {
        List<YearDTO> years = controller.listYears();
        assertNotNull(years);
        assertTrue(years.isEmpty());
    }


    @Test
    public void testListProducts() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<ProductDTO> products = controller.listProducts();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    public void testListProducts_CorrectCount() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<ProductDTO> products = controller.listProducts();
        assertEquals(31, products.size()); // data.tsv has 31 products
    }

    @Test
    public void testListProducts_NoDataLoaded() {
        List<ProductDTO> products = controller.listProducts();
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }


    @Test
    public void testGetYearMeasurements() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        YearDTO yearDTO = controller.getYearMeasurements(1960);
        assertNotNull(yearDTO);
        assertEquals(1960, yearDTO.getYear());
    }

    @Test
    public void testGetYearMeasurements_HasMeasurements() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        YearDTO yearDTO = controller.getYearMeasurements(1960);
        assertNotNull(yearDTO.getMeasurements());
        assertFalse(yearDTO.getMeasurements().isEmpty());
    }

    @Test
    public void testGetYearMeasurements_HasTop10() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        YearDTO yearDTO = controller.getYearMeasurements(1960);
        assertNotNull(yearDTO.getTop10Aliases());
        assertNotNull(yearDTO.getTop10Headlines());
    }


    @Test
    public void testGetYearMeasurements_NoDataLoaded() {
        YearDTO yearDTO = controller.getYearMeasurements(1960);
        assertNull(yearDTO);
    }


    @Test
    public void testGetProductMeasurements() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        ProductDTO productDTO = controller.getProductMeasurements("Oil");
        assertNotNull(productDTO);
        assertEquals("Oil", productDTO.getName());
    }

    @Test
    public void testGetProductMeasurements_HasMeasurements() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        ProductDTO productDTO = controller.getProductMeasurements("Oil");
        assertNotNull(productDTO.getMeasurements());
    }

    @Test
    public void testGetProductMeasurements_NoDataLoaded() {
        ProductDTO productDTO = controller.getProductMeasurements("Oil");
        assertNull(productDTO);
    }


    @Test
    public void testFilterProductMeasurements() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        ProductDTO productDTO = controller.filterProductMeasurements("Oil", 1960, 1965);
        assertNotNull(productDTO);
    }

    @Test
    public void testFilterProductMeasurements_CorrectRange() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        ProductDTO productDTO = controller.filterProductMeasurements("Oil", 1960, 1965);

        for (MeasurementDTO m : productDTO.getMeasurements()) {
            assertTrue(m.getYear() >= 1960 && m.getYear() <= 1965);
        }
    }


    @Test
    public void testReportProductHighlights() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<ProductHighlightDTO> highlights = controller.reportProductHighlights("Oil");
        assertNotNull(highlights);
    }

    @Test
    public void testReportProductHighlights_NoDataLoaded() {
        List<ProductHighlightDTO> highlights = controller.reportProductHighlights("Oil");
        assertNotNull(highlights);
        assertTrue(highlights.isEmpty());
    }

    @Test
    public void testReportProductHighlights_ProductNotInTop10() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<ProductHighlightDTO> highlights = controller.reportProductHighlights("NonExistentAlias");
        assertNotNull(highlights);
        assertTrue(highlights.isEmpty());
    }

    @Test
    public void testReportCategoryHighlights() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<CategoryHighlightDTO> highlights = controller.reportCategoryHighlights("Energy");
        assertNotNull(highlights);
    }

    @Test
    public void testReportCategoryHighlights_NoDataLoaded() {
        List<CategoryHighlightDTO> highlights = controller.reportCategoryHighlights("Energy");
        assertNotNull(highlights);
        assertTrue(highlights.isEmpty());
    }

    @Test
    public void testReportCategoryHighlights_InvalidCategory() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        try {
            List<CategoryHighlightDTO> highlights = controller.reportCategoryHighlights("NonExistentCategory");
            assertNotNull(highlights);
        } catch (NullPointerException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testComputeProductStats() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<ProductStatsDTO> stats = controller.computeProductStats();
        assertNotNull(stats);
        assertFalse(stats.isEmpty());
    }

    @Test
    public void testComputeProductStats_CorrectStats() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<ProductStatsDTO> stats = controller.computeProductStats();
        assertEquals(31, stats.size());
        for (ProductStatsDTO stat : stats) {
            assertNotNull(stat.getProduct());
            assertTrue(stat.getMin() <= stat.getMax());
            assertTrue(stat.getAverage() >= stat.getMin());
            assertTrue(stat.getAverage() <= stat.getMax());
        }
    }

    @Test
    public void testComputeProductStats_NoDataLoaded() {
        List<ProductStatsDTO> stats = controller.computeProductStats();
        assertNotNull(stats);
        assertTrue(stats.isEmpty());
    }


    @Test
    public void testComputeTop10ProductAppearances() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<Top10AppearanceDTO> appearances = controller.computeTop10ProductAppearances();
        assertNotNull(appearances);
        assertFalse(appearances.isEmpty());
    }

    @Test
    public void testComputeTop10ProductAppearances_ValidCounts() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<Top10AppearanceDTO> appearances = controller.computeTop10ProductAppearances();
        for (Top10AppearanceDTO appearance : appearances) {
            assertNotNull(appearance.getName());
            assertTrue(appearance.getCount() > 0);
        }
    }

    @Test
    public void testComputeTop10ProductAppearances_NoDataLoaded() {
        List<Top10AppearanceDTO> appearances = controller.computeTop10ProductAppearances();
        assertNotNull(appearances);
        assertTrue(appearances.isEmpty());
    }


    @Test
    public void testComputeTop10CategoryAppearances() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<Top10AppearanceDTO> appearances = controller.computeTop10CategoryAppearances();
        assertNotNull(appearances);
    }

    @Test
    public void testComputeTop10CategoryAppearances_NoDataLoaded() {
        List<Top10AppearanceDTO> appearances = controller.computeTop10CategoryAppearances();
        assertNotNull(appearances);
        assertTrue(appearances.isEmpty());
    }

    @Test
    public void testComputeTop10CategoryAppearances_NoCategories() throws IOException {
        controller.loadFile("src/test/resources/Input/data.tsv", "\t"); //Load data.tsv that has no categories
        List<Top10AppearanceDTO> appearances = controller.computeTop10CategoryAppearances();
        assertNotNull(appearances);
        assertTrue(appearances.isEmpty());
    }


    @Test
    public void testReportAllYearsAllProductPrices() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<YearDTO> allData = controller.reportAllYearsAllProductPrices();
        assertNotNull(allData);
        assertFalse(allData.isEmpty());
    }

    @Test
    public void testReportAllYearsAllProductPrices_AllYearsPresent() throws IOException {
        controller.initializeFromIni("src/test/resources/test_config.ini", "\t");
        List<YearDTO> allData = controller.reportAllYearsAllProductPrices();
        assertTrue(allData.size() >= 64);

        for (YearDTO yearDTO : allData) {
            assertNotNull(yearDTO.getMeasurements());
            assertFalse(yearDTO.getMeasurements().isEmpty());
        }
    }

    @Test
    public void testReportAllYearsAllProductPrices_NoDataLoaded() {
        List<YearDTO> allData = controller.reportAllYearsAllProductPrices();
        assertNotNull(allData);
        assertTrue(allData.isEmpty());
    }
}

