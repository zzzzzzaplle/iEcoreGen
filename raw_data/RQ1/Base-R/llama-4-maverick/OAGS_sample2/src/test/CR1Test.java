import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    @Test
    public void testCase1_CalculateTotalPriceForSingleArtwork() {
        // Test Case 1: Calculate Total Artwork Price for a Single Artwork
        // SetUp: Create artist "Alice" with one artwork priced at 500 CNY
        Artist artist = new Artist();
        artist.setName("Alice");
        artist.setId("A001");
        
        Artwork artwork = new Artwork();
        artwork.setTitle("Sunset Painting");
        artwork.setDescription("A beautiful sunset painting");
        artwork.setPrice(500.0);
        
        artist.addArtwork(artwork);
        
        // Expected Output: Total artwork price = 500 CNY
        assertEquals(500.0, artist.calculateTotalPrice(), 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalPriceWithMultipleArtworks() {
        // Test Case 2: Calculate Total Artwork Price with Multiple Artworks
        // SetUp: Create artist "Bob" with two artworks priced at 1200 and 800 CNY
        Artist artist = new Artist();
        artist.setName("Bob");
        artist.setId("A002");
        
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Starry Night");
        artwork1.setPrice(1200.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Ocean View");
        artwork2.setPrice(800.0);
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Expected Output: Total artwork price = 1200 + 800 = 2000 CNY
        assertEquals(2000.0, artist.calculateTotalPrice(), 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalPriceWithZeroArtworks() {
        // Test Case 3: Calculate Total Artwork Price with Zero Artworks
        // SetUp: Create artist "Charlie" with no artworks
        Artist artist = new Artist();
        artist.setName("Charlie");
        artist.setId("A003");
        
        // Expected Output: Total artwork price = 0 CNY
        assertEquals(0.0, artist.calculateTotalPrice(), 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalPriceWithDifferentPrices() {
        // Test Case 4: Calculate Total Artwork Price for Artworks with Different Prices
        // SetUp: Create artist "Diana" with three artworks priced at 2500, 1500, and 300 CNY
        Artist artist = new Artist();
        artist.setName("Diana");
        artist.setId("A004");
        
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Marble Sculpture");
        artwork1.setPrice(2500.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setPrice(1500.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Abstract Art");
        artwork3.setPrice(300.0);
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Expected Output: Total artwork price = 2500 + 1500 + 300 = 4300 CNY
        assertEquals(4300.0, artist.calculateTotalPrice(), 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalPriceWithZeroPriceArtwork() {
        // Test Case 5: Calculate Total Artwork Price for an Artist with Artworks of Zero Price
        // SetUp: Create artist "Eve" with two artworks priced at 0 and 1500 CNY
        Artist artist = new Artist();
        artist.setName("Eve");
        artist.setId("A005");
        
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sketch");
        artwork1.setPrice(0.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Portrait");
        artwork2.setPrice(1500.0);
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Expected Output: Total artwork price = 0 + 1500 = 1500 CNY
        assertEquals(1500.0, artist.calculateTotalPrice(), 0.001);
    }
}