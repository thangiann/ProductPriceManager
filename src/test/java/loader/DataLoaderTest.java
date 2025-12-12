package loader;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


public class DataLoaderTest {
    private DataLoader dataLoader;

    @Before
    public void setUp() throws Exception {
        dataLoader = new DataLoader();
    }

    @Test
    public void testDataLoaderConstructor() {
        assertNotNull(dataLoader);
        assertNotNull(dataLoader.getData());
        assertTrue(dataLoader.getData().isEmpty());
    }

    @Test
    public void testLoadDataValidFile() throws IOException {
        int linesLoaded = dataLoader.loadData("src/test/resources/Input/data.tsv", "\t");
        assertTrue(linesLoaded > 0);
    }

    @Test
    public void testGetDataAfterLoad() throws IOException {
        dataLoader.loadData("src/test/resources/Input/data.tsv", "\t");
        ArrayList<String[]> data = dataLoader.getData();
        assertNotNull(data);
        assertFalse(data.isEmpty());
    }

    @Test
    public void testDataContainsHeader() throws IOException {
        dataLoader.loadData("src/test/resources/Input/data.tsv", "\t");
        String[] header = dataLoader.getData().get(0);
        assertNotNull(header);
        assertEquals("year", header[0]);
    }

    @Test
    public void testDataContainsValues() throws IOException {
        dataLoader.loadData("src/test/resources/Input/data.tsv", "\t");
        String[] firstRow = dataLoader.getData().get(1);
        assertEquals("1960", firstRow[0]); // year
        assertEquals("1.63", firstRow[1]); // crude oil price
    }

    @Test
    public void testLoadMetadataValidFile() throws IOException {
        dataLoader.loadMetadata("src/test/resources/Input/metadata.tsv", "\t");
        Map<String, String> aliases = dataLoader.getAliases();
        assertNotNull(aliases);
        assertFalse(aliases.isEmpty());
    }

    @Test
    public void testAliasesContent() throws IOException {
        dataLoader.loadMetadata("src/test/resources/Input/metadata.tsv", "\t");
        Map<String, String> aliases = dataLoader.getAliases();
        // metadata.tsv: Crude oil (average) Oil Energy
        assertEquals("Crude oil (average)", aliases.get("Oil"));
    }

    @Test
    public void testCategoriesContent() throws IOException {
        dataLoader.loadMetadata("src/test/resources/Input/metadata.tsv", "\t");
        Map<String, ArrayList<String>> categories = dataLoader.getCategories();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());

        assertTrue(categories.containsKey("Energy"));
        ArrayList<String> energyProducts = categories.get("Energy");
        assertTrue(energyProducts.contains("Oil"));
        assertTrue(energyProducts.contains("Natural Gas"));
    }

    @Test
    public void testMultipleCategories() throws IOException {
        dataLoader.loadMetadata("src/test/resources/Input/metadata.tsv", "\t");
        Map<String, ArrayList<String>> categories = dataLoader.getCategories();

        assertTrue(categories.containsKey("Energy"));
        assertTrue(categories.containsKey("AgriculturalProducts"));
        assertTrue(categories.containsKey("PreciousMetals"));
    }

    @Test
    public void testLoadBothDataAndMetadata() throws IOException {
        dataLoader.loadData("src/test/resources/Input/data.tsv", "\t");
        dataLoader.loadMetadata("src/test/resources/Input/metadata.tsv", "\t");

        assertFalse(dataLoader.getData().isEmpty());
        assertFalse(dataLoader.getAliases().isEmpty());
        assertFalse(dataLoader.getCategories().isEmpty());
    }


    @Test
    public void testLoadDataNonExistentFile() throws IOException {
        int result = dataLoader.loadData("nonexistent_file.tsv", "\t");
        assertEquals(0, result); // Should return 0
    }

    @Test
    public void testLoadMetadataNonExistentFile() throws IOException {
        dataLoader.loadMetadata("nonexistent_metadata.tsv", "\t");
        assertTrue(dataLoader.getAliases().isEmpty());
        assertTrue(dataLoader.getCategories().isEmpty());
    }

    @Test
    public void testGetDataBeforeLoad() {
        ArrayList<String[]> data = dataLoader.getData();
        assertNotNull(data);
        assertTrue(data.isEmpty());
    }

    @Test
    public void testGetAliasesBeforeLoad() {
        Map<String, String> aliases = dataLoader.getAliases();
        assertNotNull(aliases);
        assertTrue(aliases.isEmpty());
    }

    @Test
    public void testGetCategoriesBeforeLoad() {
        Map<String, ArrayList<String>> categories = dataLoader.getCategories();
        assertNotNull(categories);
        assertTrue(categories.isEmpty());
    }
}
