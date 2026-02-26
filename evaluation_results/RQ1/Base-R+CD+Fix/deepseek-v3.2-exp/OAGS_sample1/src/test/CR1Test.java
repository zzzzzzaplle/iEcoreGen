import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Artist artist;
    
    @Before
    public void setUp() {
        // Reset artist before each test
        artist = new Artist();
    }
    
    @Test
    public void testCase1_calculateTotalArtworkPriceForSingleArtwork() {
        // Set up artist
        artist.setName("Alice");
        artist.setId("A001");
        
        // Create artwork
        Artwork artwork = new Artwork();
        artwork.setTitle("Sunset Painting");
        artwork.setDescription("A beautiful sunset painting");
        artwork.setPrice(500.0);
        
        // Add artwork to artist
        artist.addArtwork(artwork);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify result
        assertEquals("Total price should be 500 CNY for single artwork", 500.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_calculateTotalArtworkPriceWithMultipleArtworks() {
        // Set up artist
        artist.setName("Bob");
        artist.setId("A002");
        
        // Create first artwork
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Starry Night");
        artwork1.setPrice(1200.0);
        
        // Create second artwork
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Ocean View");
        artwork2.setPrice(800.0);
        
        // Add artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify result
        assertEquals("Total price should be 2000 CNY for two artworks", 2000.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_calculateTotalArtworkPriceWithZeroArtworks() {
        // Set up artist with no artworks
        artist.setName("Charlie");
        artist.setId("A003");
        
        // Calculate total price (should be 0 since no artworks)
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify result
        assertEquals("Total price should be 0 CNY when artist has no artworks", 0.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_calculateTotalArtworkPriceWithDifferentPrices() {
        // Set up artist
        artist.setName("Diana");
        artist.setId("A004");
        
        // Create first artwork
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Marble Sculpture");
        artwork1.setPrice(2500.0);
        
        // Create second artwork
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setPrice(1500.0);
        
        // Create third artwork
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Abstract Art");
        artwork3.setPrice(300.0);
        
        // Add all artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify result
        assertEquals("Total price should be 4300 CNY for three different priced artworks", 4300.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_calculateTotalArtworkPriceWithZeroPriceArtworks() {
        // Set up artist
        artist.setName("Eve");
        artist.setId("A005");
        
        // Create first artwork with zero price
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sketch");
        artwork1.setPrice(0.0);
        
        // Create second artwork with regular price
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Portrait");
        artwork2.setPrice(1500.0);
        
        // Add artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify result
        assertEquals("Total price should be 1500 CNY when one artwork has zero price", 1500.0, totalPrice, 0.001);
    }
}