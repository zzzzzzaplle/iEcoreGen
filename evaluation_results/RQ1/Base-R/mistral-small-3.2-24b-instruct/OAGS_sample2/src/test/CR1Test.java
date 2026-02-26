import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    @Test
    public void testCase1_CalculateTotalArtworkPriceForSingleArtwork() {
        // Test Case 1: Calculate Total Artwork Price for a Single Artwork
        // SetUp: Create artist "Alice" with ID A001
        Artist artist = new Artist();
        artist.setName("Alice");
        artist.setId("A001");
        
        // SetUp: Create artwork "Sunset Painting" with price 500 CNY
        Artwork artwork = new Artwork();
        artwork.setTitle("Sunset Painting");
        artwork.setDescription("A beautiful sunset painting");
        artwork.setPrice(500.0);
        
        // SetUp: Add artwork to artist's collection
        List<Artwork> artworks = new ArrayList<>();
        artworks.add(artwork);
        artist.setArtworks(artworks);
        
        // Calculate total price and verify expected output
        double totalPrice = artist.calculateTotalArtworkPrice();
        assertEquals(500.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalArtworkPriceWithMultipleArtworks() {
        // Test Case 2: Calculate Total Artwork Price with Multiple Artworks
        // SetUp: Create artist "Bob" with ID A002
        Artist artist = new Artist();
        artist.setName("Bob");
        artist.setId("A002");
        
        // SetUp: Create first artwork "Starry Night" with price 1200 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Starry Night");
        artwork1.setPrice(1200.0);
        
        // SetUp: Create second artwork "Ocean View" with price 800 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Ocean View");
        artwork2.setPrice(800.0);
        
        // SetUp: Add both artworks to artist's collection
        List<Artwork> artworks = new ArrayList<>();
        artworks.add(artwork1);
        artworks.add(artwork2);
        artist.setArtworks(artworks);
        
        // Calculate total price and verify expected output (1200 + 800 = 2000)
        double totalPrice = artist.calculateTotalArtworkPrice();
        assertEquals(2000.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalArtworkPriceWithZeroArtworks() {
        // Test Case 3: Calculate Total Artwork Price with Zero Artworks
        // SetUp: Create artist "Charlie" with ID A003 and no artworks
        Artist artist = new Artist();
        artist.setName("Charlie");
        artist.setId("A003");
        
        // Artist has no artworks by default, so no setup needed
        
        // Calculate total price and verify expected output is 0
        double totalPrice = artist.calculateTotalArtworkPrice();
        assertEquals(0.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalArtworkPriceForArtworksWithDifferentPrices() {
        // Test Case 4: Calculate Total Artwork Price for Artworks with Different Prices
        // SetUp: Create artist "Diana" with ID A004
        Artist artist = new Artist();
        artist.setName("Diana");
        artist.setId("A004");
        
        // SetUp: Create first artwork "Marble Sculpture" with price 2500 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Marble Sculpture");
        artwork1.setPrice(2500.0);
        
        // SetUp: Create second artwork "Wooden Carving" with price 1500 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setPrice(1500.0);
        
        // SetUp: Create third artwork "Abstract Art" with price 300 CNY
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Abstract Art");
        artwork3.setPrice(300.0);
        
        // SetUp: Add all three artworks to artist's collection
        List<Artwork> artworks = new ArrayList<>();
        artworks.add(artwork1);
        artworks.add(artwork2);
        artworks.add(artwork3);
        artist.setArtworks(artworks);
        
        // Calculate total price and verify expected output (2500 + 1500 + 300 = 4300)
        double totalPrice = artist.calculateTotalArtworkPrice();
        assertEquals(4300.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalArtworkPriceForArtistWithArtworksOfZeroPrice() {
        // Test Case 5: Calculate Total Artwork Price for an Artist with Artworks of Zero Price
        // SetUp: Create artist "Eve" with ID A005
        Artist artist = new Artist();
        artist.setName("Eve");
        artist.setId("A005");
        
        // SetUp: Create first artwork "Sketch" with price 0 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sketch");
        artwork1.setPrice(0.0);
        
        // SetUp: Create second artwork "Portrait" with price 1500 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Portrait");
        artwork2.setPrice(1500.0);
        
        // SetUp: Add both artworks to artist's collection
        List<Artwork> artworks = new ArrayList<>();
        artworks.add(artwork1);
        artworks.add(artwork2);
        artist.setArtworks(artworks);
        
        // Calculate total price and verify expected output (0 + 1500 = 1500)
        double totalPrice = artist.calculateTotalArtworkPrice();
        assertEquals(1500.0, totalPrice, 0.001);
    }
}