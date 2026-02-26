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
    public void testCase1_CalculateTotalPriceForSingleArtwork() {
        // Set up artist with one artwork priced at 500 CNY
        artist.setName("Alice");
        artist.setId("A001");
        
        Artwork artwork = new Artwork();
        artwork.setTitle("Sunset Painting");
        artwork.setDescription("A beautiful sunset painting");
        artwork.setPrice(500.0);
        
        artist.addArtwork(artwork);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify the total price is 500 CNY
        assertEquals(500.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalPriceForMultipleArtworks() {
        // Set up artist with two artworks priced at 1200 CNY and 800 CNY
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
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify the total price is 2000 CNY (1200 + 800)
        assertEquals(2000.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalPriceForZeroArtworks() {
        // Set up artist with no artworks
        artist.setName("Charlie");
        artist.setId("A003");
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify the total price is 0 CNY (no artworks)
        assertEquals(0.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalPriceForVariousPricedArtworks() {
        // Set up artist with three artworks priced at 2500, 1500, and 300 CNY
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
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify the total price is 4300 CNY (2500 + 1500 + 300)
        assertEquals(4300.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalPriceWithZeroPricedArtwork() {
        // Set up artist with two artworks: one priced at 0 CNY and one at 1500 CNY
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
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify the total price is 1500 CNY (0 + 1500)
        assertEquals(1500.0, totalPrice, 0.001);
    }
}