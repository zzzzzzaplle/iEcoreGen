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
    public void testCase1_singleArtwork() {
        // Create artist Alice with ID A001
        artist.setName("Alice");
        artist.setId("A001");
        
        // Create artwork "Sunset Painting" with price 500 CNY
        Artwork artwork = new Artwork();
        artwork.setTitle("Sunset Painting");
        artwork.setDescription("A beautiful sunset painting");
        artwork.setPrice(500.0);
        
        // Add artwork to artist's collection
        artist.addArtwork(artwork);
        
        // Calculate total price and verify result
        double result = artist.calculateTotalPrice();
        assertEquals(500.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleArtworks() {
        // Create artist Bob with ID A002
        artist.setName("Bob");
        artist.setId("A002");
        
        // Create artwork "Starry Night" with price 1200 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Starry Night");
        artwork1.setPrice(1200.0);
        artist.addArtwork(artwork1);
        
        // Create artwork "Ocean View" with price 800 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Ocean View");
        artwork2.setPrice(800.0);
        artist.addArtwork(artwork2);
        
        // Calculate total price and verify result
        double result = artist.calculateTotalPrice();
        assertEquals(2000.0, result, 0.001);
    }
    
    @Test
    public void testCase3_zeroArtworks() {
        // Create artist Charlie with ID A003 and no artworks
        artist.setName("Charlie");
        artist.setId("A003");
        
        // Calculate total price and verify result is 0
        double result = artist.calculateTotalPrice();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_artworksWithDifferentPrices() {
        // Create artist Diana with ID A004
        artist.setName("Diana");
        artist.setId("A004");
        
        // Create artwork "Marble Sculpture" with price 2500 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Marble Sculpture");
        artwork1.setPrice(2500.0);
        artist.addArtwork(artwork1);
        
        // Create artwork "Wooden Carving" with price 1500 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setPrice(1500.0);
        artist.addArtwork(artwork2);
        
        // Create artwork "Abstract Art" with price 300 CNY
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Abstract Art");
        artwork3.setPrice(300.0);
        artist.addArtwork(artwork3);
        
        // Calculate total price and verify result
        double result = artist.calculateTotalPrice();
        assertEquals(4300.0, result, 0.001);
    }
    
    @Test
    public void testCase5_artworksWithZeroPrice() {
        // Create artist Eve with ID A005
        artist.setName("Eve");
        artist.setId("A005");
        
        // Create artwork "Sketch" with price 0 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sketch");
        artwork1.setPrice(0.0);
        artist.addArtwork(artwork1);
        
        // Create artwork "Portrait" with price 1500 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Portrait");
        artwork2.setPrice(1500.0);
        artist.addArtwork(artwork2);
        
        // Calculate total price and verify result
        double result = artist.calculateTotalPrice();
        assertEquals(1500.0, result, 0.001);
    }
}