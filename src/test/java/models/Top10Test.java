package models;


import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import loader.DataLoader;


public class Top10Test {
    private DataLoader dataLoader;

    @Before
    public void setUp() throws Exception {
        dataLoader = new DataLoader();
        dataLoader.loadData("src/test/resources/Input/data.tsv", "\t");
        dataLoader.loadMetadata("src/test/resources/Input/metadata.tsv", "\t");
    }


    @Test
    public void testTop10Constructor() {
        Top10 top10 = new Top10(1960, dataLoader);
        assertNotNull(top10);
    }

    @Test
    public void testTop10Aliases() {
        Top10 top10 = new Top10(1960, dataLoader);
        ArrayList<String> aliases = top10.top10Aliases();

        assertNotNull(aliases);
        assertFalse(aliases.isEmpty());
    }

    @Test
    public void testTop10AliasesContent() {
        Top10 top10 = new Top10(1960, dataLoader);
        ArrayList<String> aliases = top10.top10Aliases();

        assertTrue(aliases.size() > 0);
    }

    @Test
    public void testTop10Headlines() {
        Top10 top10 = new Top10(1960, dataLoader);
        ArrayList<String> headlines = top10.top10Headlines();

        assertNotNull(headlines);
        assertFalse(headlines.isEmpty());
    }

    @Test
    public void testTop10HeadlinesContent() {
        Top10 top10 = new Top10(1960, dataLoader);
        ArrayList<String> headlines = top10.top10Headlines();

        String firstHeadline = headlines.get(0);
        assertNotNull(firstHeadline);
    }

    @Test
    public void testTop10DifferentYears() {
        Top10 top10_1960 = new Top10(1960, dataLoader);
        Top10 top10_1961 = new Top10(1961, dataLoader);
        ArrayList<String> aliases1960 = top10_1960.top10Aliases();
        ArrayList<String> aliases1961 = top10_1961.top10Aliases();

        assertNotNull(aliases1960);
        assertNotNull(aliases1961);
    }

    @Test
    public void testAliasesAndHeadlinesSameSize() {
        Top10 top10 = new Top10(1960, dataLoader);
        ArrayList<String> aliases = top10.top10Aliases();
        ArrayList<String> headlines = top10.top10Headlines();

        assertEquals(aliases.size(), headlines.size());
    }


    @Test
    public void testTop10WithNullDataLoader() {
        // Edge case: This will cause NullPointerException when methods are called
        Top10 top10 = new Top10(1960, null);
        assertNotNull(top10);
    }
}
