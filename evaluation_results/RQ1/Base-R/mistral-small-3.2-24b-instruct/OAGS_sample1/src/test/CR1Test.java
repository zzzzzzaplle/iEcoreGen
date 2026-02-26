import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Artist artist;
    
    @Test
    public void testCase1_calculateTotalArtworkPriceForSingleArtwork() {
        // Test Case 1: Calculate Total Artwork Price for a Single Artwork
        // SetUp: Create an artist named "Alice" with ID: A001
        artist = new Artist("Alice", "123-456-7890", "A001", "alice@example.com", "123 Main St", "Female");
        
        // Create an artwork titled "Sunset Painting" with description "A beautiful sunset painting" and price: 500 CNY
        Artwork artwork = new Artwork("Sunset Painting", "A beautiful sunset painting", "Painting", 500.0);
        
        // The artist "Alice" has only this artwork
        artist.addArtwork(artwork);
        
        // Expected Output: Total artwork price = 500 CNY
        double expectedTotal = 500.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price should be 500 CNY for a single artwork", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase2_calculateTotalArtworkPriceWithMultipleArtworks() {
        // Test Case 2: Calculate Total Artwork Price with Multiple Artworks
        // SetUp: Create an artist named "Bob" with ID: A002
        artist = new Artist("Bob", "234-567-8901", "A002", "bob@example.com", "456 Oak St", "Male");
        
        // Create an artwork titled "Starry Night" with price: 1200 CNY
        Artwork artwork1 = new Artwork("Starry Night", "A starry night painting", "Painting", 1200.0);
        
        // Create an artwork titled "Ocean View" with price: 800 CNY
        Artwork artwork2 = new Artwork("Ocean View", "An ocean view painting", "Painting", 800.0);
        
        // The artist "Bob" has both of these artworks
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Expected Output: Total artwork price = 1200 + 800 = 2000 CNY
        double expectedTotal = 2000.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price should be 2000 CNY for two artworks", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase3_calculateTotalArtworkPriceWithZeroArtworks() {
        // Test Case 3: Calculate Total Artwork Price with Zero Artworks
        // SetUp: Create an artist named "Charlie" with ID: A003
        artist = new Artist("Charlie", "345-678-9012", "A003", "charlie@example.com", "789 Pine St", "Male");
        
        // The artist "Charlie" has no artworks listed (artworks list is empty by default)
        
        // Expected Output: Total artwork price = 0 CNY
        double expectedTotal = 0.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price should be 0 CNY when no artworks exist", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase4_calculateTotalArtworkPriceWithDifferentPrices() {
        // Test Case 4: Calculate Total Artwork Price for Artworks with Different Prices
        // SetUp: Create an artist named "Diana" with ID: A004
        artist = new Artist("Diana", "456-789-0123", "A004", "diana@example.com", "321 Elm St", "Female");
        
        // Create an artwork titled "Marble Sculpture" with price: 2500 CNY
        Artwork artwork1 = new Artwork("Marble Sculpture", "A marble sculpture", "Sculpture", 2500.0);
        
        // Create an artwork titled "Wooden Carving" with price: 1500 CNY
        Artwork artwork2 = new Artwork("Wooden Carving", "A wooden carving", "Sculpture", 1500.0);
        
        // Create an artwork titled "Abstract Art" with price: 300 CNY
        Artwork artwork3 = new Artwork("Abstract Art", "An abstract artwork", "Painting", 300.0);
        
        // The artist "Diana" has all three artworks
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Expected Output: Total artwork price = 2500 + 1500 + 300 = 4300 CNY
        double expectedTotal = 4300.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price should be 4300 CNY for three artworks with different prices", 
                     expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase5_calculateTotalArtworkPriceWithZeroPriceArtwork() {
        // Test Case 5: Calculate Total Artwork Price for an Artist with Artworks of Zero Price
        // SetUp: Create an artist named "Eve" with ID: A005
        artist = new Artist("Eve", "567-890-1234", "A005", "eve@example.com", "654 Maple St", "Female");
        
        // Create an artwork titled "Sketch" with price: 0 CNY
        Artwork artwork1 = new Artwork("Sketch", "A pencil sketch", "Drawing", 0.0);
        
        // Create an artwork titled "Portrait" with price: 1500 CNY
        Artwork artwork2 = new Artwork("Portrait", "A portrait painting", "Painting", 1500.0);
        
        // The artist "Eve" has both artworks
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Expected Output: Total artwork price = 0 + 1500 = 1500 CNY
        double expectedTotal = 1500.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price should be 1500 CNY when one artwork has zero price", 
                     expectedTotal, actualTotal, 0.001);
    }
}