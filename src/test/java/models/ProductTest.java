package models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProductTest {

    @Before
    public void setUp() throws Exception {
        // Nothing to set up
    }

    // ==================== HAPPY DAY TESTS ====================

    @Test
    public void testProductConstructor() {
        Product product = new Product("CrudeOil", 1);
        assertNotNull(product);
    }

    @Test
    public void testGetName() {
        Product product = new Product("CrudeOil", 1);
        assertEquals("CrudeOil", product.getName());
    }

    @Test
    public void testGetId() {
        Product product = new Product("CrudeOil", 5);
        assertEquals(5, product.getId());
    }

    @Test
    public void testMultipleProducts() {
        Product product1 = new Product("Oil", 1);
        Product product2 = new Product("Gold", 2);
        Product product3 = new Product("Coffee", 3);

        assertEquals("Oil", product1.getName());
        assertEquals("Gold", product2.getName());
        assertEquals("Coffee", product3.getName());

        assertEquals(1, product1.getId());
        assertEquals(2, product2.getId());
        assertEquals(3, product3.getId());
    }

    // ==================== RAINY DAY TESTS ====================

    @Test
    public void testProductWithEmptyName() {
        Product product = new Product("", 1);
        assertEquals("", product.getName());
    }

    @Test
    public void testProductWithZeroId() {
        Product product = new Product("TestProduct", 0);
        assertEquals(0, product.getId());
    }

    @Test
    public void testProductWithNegativeId() {
        Product product = new Product("TestProduct", -1);
        assertEquals(-1, product.getId());
    }
}
