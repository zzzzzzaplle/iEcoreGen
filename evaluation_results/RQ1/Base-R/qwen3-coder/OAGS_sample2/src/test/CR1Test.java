import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    private Artist artist;
    private Artwork artwork;
    
    @Before
    public void setUp() {
        artist = new Artist();
    }
    
    @Test
    public void testCase1_CalculateTotalArtworkPriceForSingleArtwork() {
        // SetUp: Create an artist named "Alice" with ID: A001
        artist.setName("Alice");
        artist.setId("A001");
        
        // Create an artwork titled "Sunset Painting" with description "A beautiful sunset painting" and price: 500 CNY
        artwork = new Artwork();
        artwork.setTitle("Sunset Painting");
        artwork.setDescription("A beautiful sunset painting");
        artwork.setPrice(500.0);
        
        // The artist "Alice" has only this artwork
        List<Artwork> artworks = new ArrayList<>();
        artworks.add(artwork);
        artist.setArtworks(artworks);
        
        // Expected Output: Total artwork price = 500 CNY
        double expectedTotal = 500.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price for single artwork should be 500", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalArtworkPriceWithMultipleArtworks() {
        // SetUp: Create an artist named "Bob" with ID: A002
        artist.setName("Bob");
        artist.setId("A002");
        
        // Create artworks with their prices
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Starry Night");
        artwork1.setPrice(1200.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Ocean View");
        artwork2.setPrice(800.0);
        
        // The artist "Bob" has both of these artworks
        List<Artwork> artworks = new ArrayList<>();
        artworks.add(artwork1);
        artworks.add(artwork2);
        artist.setArtworks(artworks);
        
        // Expected Output: Total artwork price = 1200 + 800 = 2000 CNY
        double expectedTotal = 2000.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price for multiple artworks should be 2000", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalArtworkPriceWithZeroArtworks() {
        // SetUp: Create an artist named "Charlie" with ID: A003
        artist.setName("Charlie");
        artist.setId("A003");
        
        // The artist "Charlie" has no artworks listed
        artist.setArtworks(new ArrayList<Artwork>());
        
        // Expected Output: Total artwork price = 0 CNY
        double expectedTotal = 0.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price for zero artworks should be 0", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalArtworkPriceForArtworksWithDifferentPrices() {
        // SetUp: Create an artist named "Diana" with ID: A004
        artist.setName("Diana");
        artist.setId("A004");
        
        // Create artworks with different prices
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Marble Sculpture");
        artwork1.setPrice(2500.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setPrice(1500.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Abstract Art");
        artwork3.setPrice(300.0);
        
        // The artist "Diana" has all three artworks
        List<Artwork> artworks = new ArrayList<>();
        artworks.add(artwork1);
        artworks.add(artwork2);
        artworks.add(artwork3);
        artist.setArtworks(artworks);
        
        // Expected Output: Total artwork price = 2500 + 1500 + 300 = 4300 CNY
        double expectedTotal = 4300.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price for artworks with different prices should be 4300", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalArtworkPriceForArtistWithArtworksOfZeroPrice() {
        // SetUp: Create an artist named "Eve" with ID: A005
        artist.setName("Eve");
        artist.setId("A005");
        
        // Create artworks including one with zero price
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sketch");
        artwork1.setPrice(0.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Portrait");
        artwork2.setPrice(1500.0);
        
        // The artist "Eve" has both artworks
        List<Artwork> artworks = new ArrayList<>();
        artworks.add(artwork1);
        artworks.add(artwork2);
        artist.setArtworks(artworks);
        
        // Expected Output: Total artwork price = 0 + 1500 = 1500 CNY
        double expectedTotal = 1500.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        
        assertEquals("Total price including zero-priced artwork should be 1500", expectedTotal, actualTotal, 0.001);
    }
}