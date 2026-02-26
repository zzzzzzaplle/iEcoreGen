import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public void testCase1_CalculateTotalPriceForSingleArtwork() {
        // Set up artist
        artist.setName("Alice");
        artist.setId("A001");
        
        // Create and set up artwork
        artwork1 = new Artwork();
        artwork1.setTitle("Sunset Painting");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setPrice(500.0);
        
        // Add artwork to artist
        artist.addArtwork(artwork1);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output
        assertEquals(500.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalPriceWithMultipleArtworks() {
        // Set up artist
        artist.setName("Bob");
        artist.setId("A002");
        
        // Create and set up first artwork
        artwork1 = new Artwork();
        artwork1.setTitle("Starry Night");
        artwork1.setPrice(1200.0);
        
        // Create and set up second artwork
        artwork2 = new Artwork();
        artwork2.setTitle("Ocean View");
        artwork2.setPrice(800.0);
        
        // Add artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output
        assertEquals(2000.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalPriceWithZeroArtworks() {
        // Set up artist with no artworks
        artist.setName("Charlie");
        artist.setId("A003");
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output
        assertEquals(0.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalPriceWithDifferentPrices() {
        // Set up artist
        artist.setName("Diana");
        artist.setId("A004");
        
        // Create and set up first artwork
        artwork1 = new Artwork();
        artwork1.setTitle("Marble Sculpture");
        artwork1.setPrice(2500.0);
        
        // Create and set up second artwork
        artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setPrice(1500.0);
        
        // Create and set up third artwork
        artwork3 = new Artwork();
        artwork3.setTitle("Abstract Art");
        artwork3.setPrice(300.0);
        
        // Add artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output
        assertEquals(4300.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalPriceWithZeroPriceArtworks() {
        // Set up artist
        artist.setName("Eve");
        artist.setId("A005");
        
        // Create and set up first artwork with zero price
        artwork1 = new Artwork();
        artwork1.setTitle("Sketch");
        artwork1.setPrice(0.0);
        
        // Create and set up second artwork
        artwork2 = new Artwork();
        artwork2.setTitle("Portrait");
        artwork2.setPrice(1500.0);
        
        // Add artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output
        assertEquals(1500.0, totalPrice, 0.001);
    }
}