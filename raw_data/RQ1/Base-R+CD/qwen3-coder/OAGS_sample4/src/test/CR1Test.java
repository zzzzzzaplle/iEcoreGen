import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR1Test {
    
    private Artist artist;
    private Artwork artwork1;
    private Artwork artwork2;
    private Artwork artwork3;
    
    @Before
    public void setUp() {
        artist = new Artist();
    }
    
    @Test
    public void testCase1_CalculateTotalArtworkPriceForSingleArtwork() {
        // SetUp: Create an artist named "Alice" with ID: A001
        artist.setName("Alice");
        artist.setId("A001");
        
        // SetUp: Create an artwork titled "Sunset Painting" with description "A beautiful sunset painting" and price: 500 CNY
        artwork1 = new Artwork();
        artwork1.setTitle("Sunset Painting");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setPrice(500.0);
        
        // SetUp: The artist "Alice" has only this artwork
        artist.addArtwork(artwork1);
        
        // Calculate total price and verify expected output
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total artwork price should be 500 CNY", 500.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalArtworkPriceWithMultipleArtworks() {
        // SetUp: Create an artist named "Bob" with ID: A002
        artist.setName("Bob");
        artist.setId("A002");
        
        // SetUp: Create an artwork titled "Starry Night" with price: 1200 CNY
        artwork1 = new Artwork();
        artwork1.setTitle("Starry Night");
        artwork1.setPrice(1200.0);
        
        // SetUp: Create an artwork titled "Ocean View" with price: 800 CNY
        artwork2 = new Artwork();
        artwork2.setTitle("Ocean View");
        artwork2.setPrice(800.0);
        
        // SetUp: The artist "Bob" has both of these artworks
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Calculate total price and verify expected output
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total artwork price should be 2000 CNY", 2000.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalArtworkPriceWithZeroArtworks() {
        // SetUp: Create an artist named "Charlie" with ID: A003
        artist.setName("Charlie");
        artist.setId("A003");
        
        // SetUp: The artist "Charlie" has no artworks listed
        // No artworks added to artist
        
        // Calculate total price and verify expected output
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total artwork price should be 0 CNY", 0.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalArtworkPriceForArtworksWithDifferentPrices() {
        // SetUp: Create an artist named "Diana" with ID: A004
        artist.setName("Diana");
        artist.setId("A004");
        
        // SetUp: Create an artwork titled "Marble Sculpture" with price: 2500 CNY
        artwork1 = new Artwork();
        artwork1.setTitle("Marble Sculpture");
        artwork1.setPrice(2500.0);
        
        // SetUp: Create an artwork titled "Wooden Carving" with price: 1500 CNY
        artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setPrice(1500.0);
        
        // SetUp: Create an artwork titled "Abstract Art" with price: 300 CNY
        artwork3 = new Artwork();
        artwork3.setTitle("Abstract Art");
        artwork3.setPrice(300.0);
        
        // SetUp: The artist "Diana" has all three artworks
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Calculate total price and verify expected output
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total artwork price should be 4300 CNY", 4300.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalArtworkPriceForArtistWithArtworksOfZeroPrice() {
        // SetUp: Create an artist named "Eve" with ID: A005
        artist.setName("Eve");
        artist.setId("A005");
        
        // SetUp: Create an artwork titled "Sketch" with price: 0 CNY
        artwork1 = new Artwork();
        artwork1.setTitle("Sketch");
        artwork1.setPrice(0.0);
        
        // SetUp: Create an artwork titled "Portrait" with price: 1500 CNY
        artwork2 = new Artwork();
        artwork2.setTitle("Portrait");
        artwork2.setPrice(1500.0);
        
        // SetUp: The artist "Eve" has both artworks
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Calculate total price and verify expected output
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total artwork price should be 1500 CNY", 1500.0, totalPrice, 0.001);
    }
}