import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CR3Test {
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Create artist John Doe
        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setId("A001");
        artist.setPhoneNumber("1234567890");
        artist.setEmail("john.doe@example.com");
        artist.setAddress("123 Art St");
        artist.setGender("Male");
        
        // Add 3 artworks: 2 paintings and 1 sculpture
        List<Artwork> artworks = new ArrayList<>();
        
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory("painting");
        artwork1.setPrice(200.0);
        artworks.add(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory("painting");
        artwork2.setPrice(150.0);
        artworks.add(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory("sculpture");
        artwork3.setPrice(500.0);
        artworks.add(artwork3);
        
        artist.setArtworks(artworks);
        
        // Count artworks by category
        Map<String, Integer> categoryCounts = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals(2, (int) categoryCounts.getOrDefault("painting", 0));
        assertEquals(1, (int) categoryCounts.getOrDefault("sculpture", 0));
        assertEquals(0, (int) categoryCounts.getOrDefault("architecture", 0));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Create artist Jane Smith
        Artist artist = new Artist();
        artist.setName("Jane Smith");
        artist.setId("A002");
        artist.setPhoneNumber("9876543210");
        artist.setEmail("jane.smith@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender("Female");
        
        // Add 4 artworks: 1 painting, 2 sculptures, and 1 architecture
        List<Artwork> artworks = new ArrayList<>();
        
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory("painting");
        artwork1.setPrice(300.0);
        artworks.add(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory("sculpture");
        artwork2.setPrice(700.0);
        artworks.add(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory("sculpture");
        artwork3.setPrice(600.0);
        artworks.add(artwork3);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory("architecture");
        artwork4.setPrice(900.0);
        artworks.add(artwork4);
        
        artist.setArtworks(artworks);
        
        // Count artworks by category
        Map<String, Integer> categoryCounts = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals(1, (int) categoryCounts.getOrDefault("painting", 0));
        assertEquals(2, (int) categoryCounts.getOrDefault("sculpture", 0));
        assertEquals(1, (int) categoryCounts.getOrDefault("architecture", 0));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Create artist Emily Brown
        Artist artist = new Artist();
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        // No artworks added (empty list by default)
        
        // Count artworks by category
        Map<String, Integer> categoryCounts = artist.countArtworksByCategory();
        
        // Verify expected counts (all zeros)
        assertEquals(0, (int) categoryCounts.getOrDefault("painting", 0));
        assertEquals(0, (int) categoryCounts.getOrDefault("sculpture", 0));
        assertEquals(0, (int) categoryCounts.getOrDefault("architecture", 0));
        assertTrue(categoryCounts.isEmpty());
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Create artist Michael Johnson
        Artist artist = new Artist();
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender("Male");
        
        // Add 3 sculptures only
        List<Artwork> artworks = new ArrayList<>();
        
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory("sculpture");
        artwork1.setPrice(800.0);
        artworks.add(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory("sculpture");
        artwork2.setPrice(1200.0);
        artworks.add(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory("sculpture");
        artwork3.setPrice(1000.0);
        artworks.add(artwork3);
        
        artist.setArtworks(artworks);
        
        // Count artworks by category
        Map<String, Integer> categoryCounts = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals(0, (int) categoryCounts.getOrDefault("painting", 0));
        assertEquals(3, (int) categoryCounts.getOrDefault("sculpture", 0));
        assertEquals(0, (int) categoryCounts.getOrDefault("architecture", 0));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Create artist Alice White
        Artist artistE = new Artist();
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        List<Artwork> artworksE = new ArrayList<>();
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory("painting");
        artworksE.add(artworkE1);
        
        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory("architecture");
        artworksE.add(artworkE2);
        
        artistE.setArtworks(artworksE);
        
        // Create artist David Green
        Artist artistF = new Artist();
        artistF.setName("David Green");
        artistF.setId("A006");
        
        List<Artwork> artworksF = new ArrayList<>();
        Artwork artworkF1 = new Artwork();
        artworkF1.setTitle("Marble Sculpture");
        artworkF1.setCategory("sculpture");
        artworksF.add(artworkF1);
        
        Artwork artworkF2 = new Artwork();
        artworkF2.setTitle("City Skyline");
        artworkF2.setCategory("architecture");
        artworksF.add(artworkF2);
        
        artistF.setArtworks(artworksF);
        
        // Count artworks by category for Alice White
        Map<String, Integer> categoryCountsE = artistE.countArtworksByCategory();
        assertEquals(1, (int) categoryCountsE.getOrDefault("painting", 0));
        assertEquals(0, (int) categoryCountsE.getOrDefault("sculpture", 0));
        assertEquals(1, (int) categoryCountsE.getOrDefault("architecture", 0));
        
        // Count artworks by category for David Green
        Map<String, Integer> categoryCountsF = artistF.countArtworksByCategory();
        assertEquals(0, (int) categoryCountsF.getOrDefault("painting", 0));
        assertEquals(1, (int) categoryCountsF.getOrDefault("sculpture", 0));
        assertEquals(1, (int) categoryCountsF.getOrDefault("architecture", 0));
    }
}