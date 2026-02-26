import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Artist artist;
    
    @Before
    public void setUp() {
        artist = new Artist();
    }
    
    @Test
    public void testCase1_CalculateTotalArtworkPriceForSingleArtwork() {
        // Create an artist named "Alice" with ID: A001
        artist.setName("Alice");
        artist.setArtistId("A001");
        
        // Create an artwork titled "Sunset Painting" with description "A beautiful sunset painting" and price: 500 CNY
        Artwork artwork = new Artwork();
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
        assertEquals(expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalArtworkPriceWithMultipleArtworks() {
        // Create an artist named "Bob" with ID: A002
        artist.setName("Bob");
        artist.setArtistId("A002");
        
        // Create an artwork titled "Starry Night" with price: 1200 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Starry Night");
        artwork1.setPrice(1200.0);
        
        // Create an artwork titled "Ocean View" with price: 800 CNY
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
        assertEquals(expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalArtworkPriceWithZeroArtworks() {
        // Create an artist named "Charlie" with ID: A003
        artist.setName("Charlie");
        artist.setArtistId("A003");
        
        // The artist "Charlie" has no artworks listed
        artist.setArtworks(new ArrayList<Artwork>());
        
        // Expected Output: Total artwork price = 0 CNY
        double expectedTotal = 0.0;
        double actualTotal = artist.calculateTotalArtworkPrice();
        assertEquals(expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalArtworkPriceForArtworksWithDifferentPrices() {
        // Create an artist named "Diana" with ID: A004
        artist.setName("Diana");
        artist.setArtistId("A004");
        
        // Create an artwork titled "Marble Sculpture" with price: 2500 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Marble Sculpture");
        artwork1.setPrice(2500.0);
        
        // Create an artwork titled "Wooden Carving" with price: 1500 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setPrice(1500.0);
        
        // Create an artwork titled "Abstract Art" with price: 300 CNY
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
        assertEquals(expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalArtworkPriceForArtistWithArtworksOfZeroPrice() {
        // Create an artist named "Eve" with ID: A005
        artist.setName("Eve");
        artist.setArtistId("A005");
        
        // Create an artwork titled "Sketch" with price: 0 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sketch");
        artwork1.setPrice(0.0);
        
        // Create an artwork titled "Portrait" with price: 1500 CNY
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
        assertEquals(expectedTotal, actualTotal, 0.001);
    }
}