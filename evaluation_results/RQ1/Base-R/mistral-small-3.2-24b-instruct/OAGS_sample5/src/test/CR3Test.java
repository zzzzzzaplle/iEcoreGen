import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.HashMap;

public class CR3Test {
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Test Case 1: Count Artworks by Category for a Single Artist
        // SetUp: Create artist John Doe with 3 artworks (2 paintings, 1 sculpture)
        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setId("A001");
        artist.setPhoneNumber("1234567890");
        artist.setEmail("john.doe@example.com");
        artist.setAddress("123 Art St");
        artist.setGender("Male");
        
        // Create and add artworks
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory("painting");
        artwork1.setPrice(200);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory("painting");
        artwork2.setPrice(150);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory("sculpture");
        artwork3.setPrice(500);
        
        artist.getArtworks().add(artwork1);
        artist.getArtworks().add(artwork2);
        artist.getArtworks().add(artwork3);
        
        // Execute: Count artworks by category
        Map<String, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected output: 2 painting, 1 sculpture, 0 architecture
        assertEquals("Should have 2 painting artworks", Integer.valueOf(2), result.get("painting"));
        assertEquals("Should have 1 sculpture artwork", Integer.valueOf(1), result.get("sculpture"));
        assertNull("Should not have architecture category", result.get("architecture"));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Test Case 2: Count Artworks by Category with Multiple Categories
        // SetUp: Create artist Jane Smith with 4 artworks (1 painting, 2 sculptures, 1 architecture)
        Artist artist = new Artist();
        artist.setName("Jane Smith");
        artist.setId("A002");
        artist.setPhoneNumber("9876543210");
        artist.setEmail("jane.smith@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender("Female");
        
        // Create and add artworks
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory("painting");
        artwork1.setPrice(300);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory("sculpture");
        artwork2.setPrice(700);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory("sculpture");
        artwork3.setPrice(600);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory("architecture");
        artwork4.setPrice(900);
        
        artist.getArtworks().add(artwork1);
        artist.getArtworks().add(artwork2);
        artist.getArtworks().add(artwork3);
        artist.getArtworks().add(artwork4);
        
        // Execute: Count artworks by category
        Map<String, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected output: 1 painting, 2 sculptures, 1 architecture
        assertEquals("Should have 1 painting artwork", Integer.valueOf(1), result.get("painting"));
        assertEquals("Should have 2 sculpture artworks", Integer.valueOf(2), result.get("sculpture"));
        assertEquals("Should have 1 architecture artwork", Integer.valueOf(1), result.get("architecture"));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Test Case 3: No Artworks for an Artist
        // SetUp: Create artist Emily Brown with no artworks
        Artist artist = new Artist();
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        // Execute: Count artworks by category (no artworks added)
        Map<String, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected output: 0 painting, 0 sculpture, 0 architecture
        assertTrue("Should return empty map when no artworks", result.isEmpty());
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Test Case 4: Count Artworks with Only One Category
        // SetUp: Create artist Michael Johnson with 3 sculptures only
        Artist artist = new Artist();
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender("Male");
        
        // Create and add 3 sculpture artworks
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory("sculpture");
        artwork1.setPrice(800);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory("sculpture");
        artwork2.setPrice(1200);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory("sculpture");
        artwork3.setPrice(1000);
        
        artist.getArtworks().add(artwork1);
        artist.getArtworks().add(artwork2);
        artist.getArtworks().add(artwork3);
        
        // Execute: Count artworks by category
        Map<String, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected output: 0 painting, 3 sculptures, 0 architecture
        assertNull("Should not have painting category", result.get("painting"));
        assertEquals("Should have 3 sculpture artworks", Integer.valueOf(3), result.get("sculpture"));
        assertNull("Should not have architecture category", result.get("architecture"));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Test Case 5: Count Artworks with Multiple Artists
        // SetUp: Create two artists with their respective artworks
        
        // Artist E: Alice White
        Artist artistE = new Artist();
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory("painting");
        
        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory("architecture");
        
        artistE.getArtworks().add(artworkE1);
        artistE.getArtworks().add(artworkE2);
        
        // Artist F: David Green
        Artist artistF = new Artist();
        artistF.setName("David Green");
        artistF.setId("A006");
        
        Artwork artworkF1 = new Artwork();
        artworkF1.setTitle("Marble Sculpture");
        artworkF1.setCategory("sculpture");
        
        Artwork artworkF2 = new Artwork();
        artworkF2.setTitle("City Skyline");
        artworkF2.setCategory("architecture");
        
        artistF.getArtworks().add(artworkF1);
        artistF.getArtworks().add(artworkF2);
        
        // Execute: Count artworks by category for both artists
        Map<String, Integer> resultE = artistE.countArtworksByCategory();
        Map<String, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify expected output for Alice White: 1 painting, 0 sculpture, 1 architecture
        assertEquals("Alice White should have 1 painting artwork", Integer.valueOf(1), resultE.get("painting"));
        assertNull("Alice White should not have sculpture category", resultE.get("sculpture"));
        assertEquals("Alice White should have 1 architecture artwork", Integer.valueOf(1), resultE.get("architecture"));
        
        // Verify expected output for David Green: 0 painting, 1 sculpture, 1 architecture
        assertNull("David Green should not have painting category", resultF.get("painting"));
        assertEquals("David Green should have 1 sculpture artwork", Integer.valueOf(1), resultF.get("sculpture"));
        assertEquals("David Green should have 1 architecture artwork", Integer.valueOf(1), resultF.get("architecture"));
    }
}