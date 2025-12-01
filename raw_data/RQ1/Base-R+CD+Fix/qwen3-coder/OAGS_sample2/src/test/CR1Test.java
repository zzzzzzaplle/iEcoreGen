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
    public void testCase1_CalculateTotalPriceForSingleArtwork() {
        // SetUp: Create artist Alice with ID A001
        artist.setName("Alice");
        artist.setId("A001");
        
        // Create artwork "Sunset Painting" with price 500 CNY
        Artwork artwork = new Artwork();
        artwork.setTitle("Sunset Painting");
        artwork.setDescription("A beautiful sunset painting");
        artwork.setPrice(500.0);
        
        // Add artwork to artist
        artist.addArtwork(artwork);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output: Total artwork price = 500 CNY
        assertEquals(500.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalPriceWithMultipleArtworks() {
        // SetUp: Create artist Bob with ID A002
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
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output: Total artwork price = 1200 + 800 = 2000 CNY
        assertEquals(2000.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalPriceWithZeroArtworks() {
        // SetUp: Create artist Charlie with ID A003 and no artworks
        artist.setName("Charlie");
        artist.setId("A003");
        
        // Artist has no artworks by default
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output: Total artwork price = 0 CNY
        assertEquals(0.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalPriceWithDifferentPrices() {
        // SetUp: Create artist Diana with ID A004
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
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output: Total artwork price = 2500 + 1500 + 300 = 4300 CNY
        assertEquals(4300.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalPriceWithZeroPriceArtwork() {
        // SetUp: Create artist Eve with ID A005
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
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output: Total artwork price = 0 + 1500 = 1500 CNY
        assertEquals(1500.0, totalPrice, 0.001);
    }
}