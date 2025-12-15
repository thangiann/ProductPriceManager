package models;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import dto.MeasurementDTO;
import loader.DataLoader;

public class MeasurementTest {
    private DataLoader dataLoader;

    @Before
    public void setUp() throws Exception {
        dataLoader = new DataLoader();
        dataLoader.loadData("src/test/resources/Input/data.tsv", "\t");
        dataLoader.loadMetadata("src/test/resources/Input/metadata.tsv", "\t");
    }

    @Test
    public void testMeasurementConstructor() {
        Product product = new Product("Crude oil (average)", "Oil", 1);
        Measurement measurement = new Measurement(1960, product, dataLoader);
        assertNotNull(measurement);
    }

    @Test
    public void testGetYear() {
        Product product = new Product("Crude oil (average)", "Oil", 1);
        Measurement measurement = new Measurement(1960, product, dataLoader);
        assertEquals(1960, measurement.getYear());
    }

    @Test
    public void testGetProduct() {
        Product product = new Product("Crude oil (average)", "Oil", 1);
        Measurement measurement = new Measurement(1960, product, dataLoader);
        assertEquals(product, measurement.getProduct());
    }

    @Test
    public void testCreateMeasurementDTO() {
        Product product = new Product("Crude oil (average)", "Oil", 1);
        Measurement measurement = new Measurement(1960, product, dataLoader);
        MeasurementDTO dto = measurement.createMeasurmentDTO();

        assertNotNull(dto);
        assertEquals(1960, dto.getYear());
        assertEquals("Oil", dto.getProductName());
        assertEquals(1.63, dto.getValue(), 0.001);
    }

    @Test
    public void testMeasurementDifferentYears() {
        Product product = new Product("Crude oil (average)", "Oil", 1);
        Measurement m1960 = new Measurement(1960, product, dataLoader);
        Measurement m1961 = new Measurement(1961, product, dataLoader);
        MeasurementDTO dto1960 = m1960.createMeasurmentDTO();
        MeasurementDTO dto1961 = m1961.createMeasurmentDTO();

        assertEquals(1.63, dto1960.getValue(), 0.001);
        assertEquals(1.57, dto1961.getValue(), 0.001);
    }

    @Test
    public void testMeasurementDifferentProducts() {
        Product oil = new Product("Crude oil (average)", "Oil", 1);
        Product gas = new Product("Natural gas avg", "Natural Gas", 2);
        Measurement oilMeasure = new Measurement(1960, oil, dataLoader);
        Measurement gasMeasure = new Measurement(1960, gas, dataLoader);
        MeasurementDTO oilDTO = oilMeasure.createMeasurmentDTO();
        MeasurementDTO gasDTO = gasMeasure.createMeasurmentDTO();

        assertEquals(1.63, oilDTO.getValue(), 0.001);
        assertEquals(0.27, gasDTO.getValue(), 0.001);
    }

    @Test
    public void testMeasurementDTOUsesAlias() {
        Product product = new Product("Crude oil (average)", "Oil", 1);
        Measurement measurement = new Measurement(1960, product, dataLoader);
        MeasurementDTO dto = measurement.createMeasurmentDTO();

        assertEquals("Oil", dto.getProductName());
        assertNotEquals("Crude oil (average)", dto.getProductName());
    }

    @Test
    public void testToString() {
        Product product = new Product("Crude oil (average)", "Oil", 1);
        Measurement measurement = new Measurement(1960, product, dataLoader);
        measurement.createMeasurmentDTO(); // This sets the price

        String result = measurement.toString();
        assertNotNull(result);
        assertTrue(result.contains("1960"));
        assertTrue(result.contains("1.63"));
    }

    @Test
    public void testMeasurementWithNullProduct() {
        /*
         * Edge case: creating measurement with null product
         * This will fail when createMeasurmentDTO is called
         * but constructor should work
         */
        Measurement measurement = new Measurement(1960, null, dataLoader);
        assertEquals(1960, measurement.getYear());
        assertNull(measurement.getProduct());
    }
}
