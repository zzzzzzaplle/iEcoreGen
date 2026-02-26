import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    private Artist artist;
    
    @Before
    public void setUp() {
        artist = new Artist();
    }
    
    @Test
    public void testCase1_calculateTotalPriceForSingleArtwork() {
        // SetUp: Create an artist named "Alice" with ID: A001
        artist.setName("Alice");
        artist.setId("A001");
        
        // SetUp: Create an artwork titled "Sunset Painting" with description "A beautiful sunset painting" and price: 500 CNY
        Artwork artwork = new Artwork();
        artwork.setTitle("Sunset Painting");
        artwork.setDescription("A beautiful sunset painting");
        artwork.setPrice(500.0);
        
        // SetUp: The artist "Alice" has only this artwork
        artist.addArtwork(artwork);
        
        // Calculate total price and verify expected output
        double totalPrice = artist.calculateTotalPrice();
        assertEquals(500.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_calculateTotalPriceWithMultipleArtworks() {
        // SetUp: Create an artist named "Bob" with ID: A002
        artist.setName("Bob");
        artist.setId("A002");
        
        // SetUp: Create an artwork titled "Starry Night" with price: 1200 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Starry Night");
        artwork1.setPrice(1200.0);
        
        // SetUp: Create an artwork titled "Ocean View" with price: 800 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Ocean View");
        artwork2.setPrice(800.0);
        
        // SetUp: The artist "Bob" has both of these artworks
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Calculate total price and verify expected output
        double totalPrice = artist.calculateTotalPrice();
        assertEquals(2000.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_calculateTotalPriceWithZeroArtworks() {
        // SetUp: Create an artist named "Charlie" with ID: A003
        artist.setName("Charlie");
        artist.setId("A003");
        
        // SetUp: The artist "Charlie" has no artworks listed
        // (artworks list is already empty by default)
        
        // Calculate total price and verify expected output
        double totalPrice = artist.calculateTotalPrice();
        assertEquals(0.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_calculateTotalPriceForArtworksWithDifferentPrices() {
        // SetUp: Create an artist named "Diana" with ID: A004
        artist.setName("Diana");
        artist.setId("A004");
        
        // SetUp: Create an artwork titled "Marble Sculpture" with price: 2500 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Marble Sculpture");
        artwork1.setPrice(2500.0);
        
        // SetUp: Create an artwork titled "Wooden Carving" with price: 1500 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setPrice(1500.0);
        
        // SetUp: Create an artwork titled "Abstract Art" with price: 300 CNY
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Abstract Art");
        artwork3.setPrice(300.0);
        
        // SetUp: The artist "Diana" has all three artworks
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Calculate total price and verify expected output
        double totalPrice = artist.calculateTotalPrice();
        assertEquals(4300.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_calculateTotalPriceForArtworksWithZeroPrice() {
        // SetUp: Create an artist named "Eve" with ID: A005
        artist.setName("Eve");
        artist.setId("A005");
        
        // SetUp: Create an artwork titled "Sketch" with price: 0 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sketch");
        artwork1.setPrice(0.0);
        
        // SetUp: Create an artwork titled "Portrait" with price: 1500 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Portrait");
        artwork2.setPrice(1500.0);
        
        // SetUp: The artist "Eve" has both artworks
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Calculate total price and verify expected output
        double totalPrice = artist.calculateTotalPrice();
        assertEquals(1500.0, totalPrice, 0.001);
    }
}