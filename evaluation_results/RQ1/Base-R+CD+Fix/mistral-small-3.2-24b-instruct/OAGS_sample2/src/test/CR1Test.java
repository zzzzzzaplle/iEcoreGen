import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_CalculateTotalArtworkPriceForSingleArtwork() {
        // SetUp: Create artist with one artwork
        Artist artist = new Artist();
        artist.setName("Alice");
        artist.setId("A001");
        
        Artwork artwork = new Artwork();
        artwork.setTitle("Sunset Painting");
        artwork.setDescription("A beautiful sunset painting");
        artwork.setPrice(500.0);
        
        List<Artwork> artworks = new ArrayList<>();
        artworks.add(artwork);
        artist.setArtworks(artworks);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output
        assertEquals(500.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalArtworkPriceWithMultipleArtworks() {
        // SetUp: Create artist with multiple artworks
        Artist artist = new Artist();
        artist.setName("Bob");
        artist.setId("A002");
        
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Starry Night");
        artwork1.setPrice(1200.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Ocean View");
        artwork2.setPrice(800.0);
        
        List<Artwork> artworks = new ArrayList<>();
        artworks.add(artwork1);
        artworks.add(artwork2);
        artist.setArtworks(artworks);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output
        assertEquals(2000.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalArtworkPriceWithZeroArtworks() {
        // SetUp: Create artist with no artworks
        Artist artist = new Artist();
        artist.setName("Charlie");
        artist.setId("A003");
        
        // Artist has no artworks (null or empty list)
        artist.setArtworks(null);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output
        assertEquals(0.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalArtworkPriceWithDifferentPrices() {
        // SetUp: Create artist with various priced artworks
        Artist artist = new Artist();
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
        
        List<Artwork> artworks = new ArrayList<>();
        artworks.add(artwork1);
        artworks.add(artwork2);
        artworks.add(artwork3);
        artist.setArtworks(artworks);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output
        assertEquals(4300.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalArtworkPriceWithZeroPriceArtwork() {
        // SetUp: Create artist with artworks including one with zero price
        Artist artist = new Artist();
        artist.setName("Eve");
        artist.setId("A005");
        
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sketch");
        artwork1.setPrice(0.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Portrait");
        artwork2.setPrice(1500.0);
        
        List<Artwork> artworks = new ArrayList<>();
        artworks.add(artwork1);
        artworks.add(artwork2);
        artist.setArtworks(artworks);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Verify expected output
        assertEquals(1500.0, totalPrice, 0.001);
    }
}