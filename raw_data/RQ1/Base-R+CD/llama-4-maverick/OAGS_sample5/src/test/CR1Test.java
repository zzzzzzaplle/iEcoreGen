import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR1Test {
    
    private Artist artist;
    
    @Before
    public void setUp() {
        artist = new Artist();
    }
    
    @Test
    public void testCase1_CalculateTotalArtworkPriceForSingleArtwork() {
        // Test Case 1: Calculate Total Artwork Price for a Single Artwork
        // SetUp: Create artist "Alice" with ID: A001
        artist.setName("Alice");
        artist.setId("A001");
        
        // Create artwork "Sunset Painting" with price 500 CNY
        Artwork artwork = new Artwork();
        artwork.setTitle("Sunset Painting");
        artwork.setDescription("A beautiful sunset painting");
        artwork.setPrice(500.0);
        
        // Add artwork to artist
        artist.addArtwork(artwork);
        
        // Expected Output: Total artwork price = 500 CNY
        double expectedPrice = 500.0;
        double actualPrice = artist.calculateTotalPrice();
        
        assertEquals("Total price should be 500 CNY for single artwork", 
                    expectedPrice, actualPrice, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalArtworkPriceWithMultipleArtworks() {
        // Test Case 2: Calculate Total Artwork Price with Multiple Artworks
        // SetUp: Create artist "Bob" with ID: A002
        artist.setName("Bob");
        artist.setId("A002");
        
        // Create artwork "Starry Night" with price 1200 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Starry Night");
        artwork1.setPrice(1200.0);
        
        // Create artwork "Ocean View" with price 800 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Ocean View");
        artwork2.setPrice(800.0);
        
        // Add both artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Expected Output: Total artwork price = 1200 + 800 = 2000 CNY
        double expectedPrice = 2000.0;
        double actualPrice = artist.calculateTotalPrice();
        
        assertEquals("Total price should be 2000 CNY for multiple artworks", 
                    expectedPrice, actualPrice, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalArtworkPriceWithZeroArtworks() {
        // Test Case 3: Calculate Total Artwork Price with Zero Artworks
        // SetUp: Create artist "Charlie" with ID: A003
        artist.setName("Charlie");
        artist.setId("A003");
        
        // Artist has no artworks (default empty list)
        
        // Expected Output: Total artwork price = 0 CNY
        double expectedPrice = 0.0;
        double actualPrice = artist.calculateTotalPrice();
        
        assertEquals("Total price should be 0 CNY when artist has no artworks", 
                    expectedPrice, actualPrice, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalArtworkPriceWithDifferentPrices() {
        // Test Case 4: Calculate Total Artwork Price for Artworks with Different Prices
        // SetUp: Create artist "Diana" with ID: A004
        artist.setName("Diana");
        artist.setId("A004");
        
        // Create artwork "Marble Sculpture" with price 2500 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Marble Sculpture");
        artwork1.setPrice(2500.0);
        
        // Create artwork "Wooden Carving" with price 1500 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setPrice(1500.0);
        
        // Create artwork "Abstract Art" with price 300 CNY
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Abstract Art");
        artwork3.setPrice(300.0);
        
        // Add all three artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Expected Output: Total artwork price = 2500 + 1500 + 300 = 4300 CNY
        double expectedPrice = 4300.0;
        double actualPrice = artist.calculateTotalPrice();
        
        assertEquals("Total price should be 4300 CNY for artworks with different prices", 
                    expectedPrice, actualPrice, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalArtworkPriceWithZeroPriceArtwork() {
        // Test Case 5: Calculate Total Artwork Price for an Artist with Artworks of Zero Price
        // SetUp: Create artist "Eve" with ID: A005
        artist.setName("Eve");
        artist.setId("A005");
        
        // Create artwork "Sketch" with price 0 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sketch");
        artwork1.setPrice(0.0);
        
        // Create artwork "Portrait" with price 1500 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Portrait");
        artwork2.setPrice(1500.0);
        
        // Add both artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Expected Output: Total artwork price = 0 + 1500 = 1500 CNY
        double expectedPrice = 1500.0;
        double actualPrice = artist.calculateTotalPrice();
        
        assertEquals("Total price should be 1500 CNY when including zero-priced artwork", 
                    expectedPrice, actualPrice, 0.001);
    }
}