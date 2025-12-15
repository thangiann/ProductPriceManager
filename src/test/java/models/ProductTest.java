package models;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {

    @Before
    public void setUp() throws Exception {
        // Nothing to set up
    }

    @Test
    public void testProductConstructor() {
        Product product = new Product("Crude oil (average)", "Oil", 1);
        assertNotNull(product);
    }

    @Test
    public void testGetName() {
        Product product = new Product("Crude oil (average)", "Oil", 1);
        assertEquals("Crude oil (average)", product.getName());
    }

    @Test
    public void testGetAlias() {
        Product product = new Product("Crude oil (average)", "Oil", 1);
        assertEquals("Oil", product.getAlias());
    }

    @Test
    public void testGetId() {
        Product product = new Product("Crude oil (average)", "Oil", 5);
        assertEquals(5, product.getId());
    }

    @Test
    public void testMultipleProducts() {
        Product product1 = new Product("Crude oil (average)", "Oil", 1);
        Product product2 = new Product("Gold", "Gold", 2);
        Product product3 = new Product("Coffee Arabica", "Coffee", 3);

        assertEquals("Crude oil (average)", product1.getName());
        assertEquals("Gold", product2.getName());
        assertEquals("Coffee Arabica", product3.getName());

        assertEquals("Oil", product1.getAlias());
        assertEquals("Gold", product2.getAlias());
        assertEquals("Coffee", product3.getAlias());

        assertEquals(1, product1.getId());
        assertEquals(2, product2.getId());
        assertEquals(3, product3.getId());
    }

    @Test
    public void testProductWithEmptyName() {
        Product product = new Product("", "EmptyName", 1);
        assertEquals("", product.getName());
    }

    @Test
    public void testProductWithEmptyAlias() {
        Product product = new Product("TestProduct", "", 1);
        assertEquals("", product.getAlias());
    }

    @Test
    public void testProductWithZeroId() {
        Product product = new Product("TestProduct", "Test", 0);
        assertEquals(0, product.getId());
    }

    @Test
    public void testProductWithNegativeId() {
        Product product = new Product("TestProduct", "Test", -1);
        assertEquals(-1, product.getId());
    }
}
