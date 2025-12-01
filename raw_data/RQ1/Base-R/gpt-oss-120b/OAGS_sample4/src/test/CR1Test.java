import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Artist artist;
    
    @Test
    public void testCase1_CalculateTotalArtworkPriceForSingleArtwork() {
        // SetUp: Create an artist named "Alice" with ID: A001
        artist = new Artist("Alice", null, "A001", null, null, null, null);
        
        // SetUp: Create an artwork titled "Sunset Painting" with description "A beautiful sunset painting" and price: 500 CNY
        Artwork artwork = new Artwork("Sunset Painting", "A beautiful sunset painting", Category.PAINTING, 500.0);
        
        // SetUp: The artist "Alice" has only this artwork
        artist.addArtwork(artwork);
        
        // Expected Output: Total artwork price = 500 CNY
        double expectedTotal = 500.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price for single artwork should be 500", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalArtworkPriceWithMultipleArtworks() {
        // SetUp: Create an artist named "Bob" with ID: A002
        artist = new Artist("Bob", null, "A002", null, null, null, null);
        
        // SetUp: Create an artwork titled "Starry Night" with price: 1200 CNY
        Artwork artwork1 = new Artwork("Starry Night", null, Category.PAINTING, 1200.0);
        
        // SetUp: Create an artwork titled "Ocean View" with price: 800 CNY
        Artwork artwork2 = new Artwork("Ocean View", null, Category.PAINTING, 800.0);
        
        // SetUp: The artist "Bob" has both of these artworks
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Expected Output: Total artwork price = 1200 + 800 = 2000 CNY
        double expectedTotal = 2000.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price for multiple artworks should be 2000", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalArtworkPriceWithZeroArtworks() {
        // SetUp: Create an artist named "Charlie" with ID: A003
        artist = new Artist("Charlie", null, "A003", null, null, null, null);
        
        // SetUp: The artist "Charlie" has no artworks listed
        
        // Expected Output: Total artwork price = 0 CNY
        double expectedTotal = 0.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price for zero artworks should be 0", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalArtworkPriceForArtworksWithDifferentPrices() {
        // SetUp: Create an artist named "Diana" with ID: A004
        artist = new Artist("Diana", null, "A004", null, null, null, null);
        
        // SetUp: Create an artwork titled "Marble Sculpture" with price: 2500 CNY
        Artwork artwork1 = new Artwork("Marble Sculpture", null, Category.SCULPTURE, 2500.0);
        
        // SetUp: Create an artwork titled "Wooden Carving" with price: 1500 CNY
        Artwork artwork2 = new Artwork("Wooden Carving", null, Category.SCULPTURE, 1500.0);
        
        // SetUp: Create an artwork titled "Abstract Art" with price: 300 CNY
        Artwork artwork3 = new Artwork("Abstract Art", null, Category.PAINTING, 300.0);
        
        // SetUp: The artist "Diana" has all three artworks
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Expected Output: Total artwork price = 2500 + 1500 + 300 = 4300 CNY
        double expectedTotal = 4300.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price for artworks with different prices should be 4300", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalArtworkPriceForArtistWithArtworksOfZeroPrice() {
        // SetUp: Create an artist named "Eve" with ID: A005
        artist = new Artist("Eve", null, "A005", null, null, null, null);
        
        // SetUp: Create an artwork titled "Sketch" with price: 0 CNY
        Artwork artwork1 = new Artwork("Sketch", null, Category.PAINTING, 0.0);
        
        // SetUp: Create an artwork titled "Portrait" with price: 1500 CNY
        Artwork artwork2 = new Artwork("Portrait", null, Category.PAINTING, 1500.0);
        
        // SetUp: The artist "Eve" has both artworks
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Expected Output: Total artwork price = 0 + 1500 = 1500 CNY
        double expectedTotal = 1500.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price with zero-priced artwork should be 1500", expectedTotal, actualTotal, 0.001);
    }
}