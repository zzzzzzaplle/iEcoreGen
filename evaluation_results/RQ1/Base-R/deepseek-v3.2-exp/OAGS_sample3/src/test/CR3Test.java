import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class CR3Test {
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // SetUp: Create artist John Doe with artworks
        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setId("A001");
        artist.setPhoneNumber("1234567890");
        artist.setEmail("john.doe@example.com");
        artist.setAddress("123 Art St");
        artist.setGender("Male");
        
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
        
        // Execute: Count artworks by category
        Map<String, Integer> result = artist.countArtworksByCategory();
        
        // Verify: Check counts for each category
        assertEquals("Should have 2 painting artworks", Integer.valueOf(2), result.get("painting"));
        assertEquals("Should have 1 sculpture artwork", Integer.valueOf(1), result.get("sculpture"));
        assertNull("Should not have architecture artworks", result.get("architecture"));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // SetUp: Create artist Jane Smith with artworks
        Artist artist = new Artist();
        artist.setName("Jane Smith");
        artist.setId("A002");
        artist.setPhoneNumber("9876543210");
        artist.setEmail("jane.smith@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender("Female");
        
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
        
        // Execute: Count artworks by category
        Map<String, Integer> result = artist.countArtworksByCategory();
        
        // Verify: Check counts for each category
        assertEquals("Should have 1 painting artwork", Integer.valueOf(1), result.get("painting"));
        assertEquals("Should have 2 sculpture artworks", Integer.valueOf(2), result.get("sculpture"));
        assertEquals("Should have 1 architecture artwork", Integer.valueOf(1), result.get("architecture"));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // SetUp: Create artist Emily Brown with no artworks
        Artist artist = new Artist();
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        // Artworks list is empty by default
        
        // Execute: Count artworks by category
        Map<String, Integer> result = artist.countArtworksByCategory();
        
        // Verify: Check that no categories are present
        assertTrue("Should have empty result map", result.isEmpty());
        assertNull("Should not have painting artworks", result.get("painting"));
        assertNull("Should not have sculpture artworks", result.get("sculpture"));
        assertNull("Should not have architecture artworks", result.get("architecture"));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // SetUp: Create artist Michael Johnson with only sculpture artworks
        Artist artist = new Artist();
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender("Male");
        
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
        
        // Execute: Count artworks by category
        Map<String, Integer> result = artist.countArtworksByCategory();
        
        // Verify: Check counts for each category
        assertNull("Should not have painting artworks", result.get("painting"));
        assertEquals("Should have 3 sculpture artworks", Integer.valueOf(3), result.get("sculpture"));
        assertNull("Should not have architecture artworks", result.get("architecture"));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // SetUp: Create artist Alice White with artworks
        Artist artist1 = new Artist();
        artist1.setName("Alice White");
        artist1.setId("A005");
        
        List<Artwork> artworks1 = new ArrayList<>();
        
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Landscapes");
        artwork1.setCategory("painting");
        artworks1.add(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Steel Bridge");
        artwork2.setCategory("architecture");
        artworks1.add(artwork2);
        
        artist1.setArtworks(artworks1);
        
        // SetUp: Create artist David Green with artworks
        Artist artist2 = new Artist();
        artist2.setName("David Green");
        artist2.setId("A006");
        
        List<Artwork> artworks2 = new ArrayList<>();
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Marble Sculpture");
        artwork3.setCategory("sculpture");
        artworks2.add(artwork3);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("City Skyline");
        artwork4.setCategory("architecture");
        artworks2.add(artwork4);
        
        artist2.setArtworks(artworks2);
        
        // Execute: Count artworks by category for both artists
        Map<String, Integer> result1 = artist1.countArtworksByCategory();
        Map<String, Integer> result2 = artist2.countArtworksByCategory();
        
        // Verify: Check counts for Alice White
        assertEquals("Alice White should have 1 painting artwork", Integer.valueOf(1), result1.get("painting"));
        assertNull("Alice White should not have sculpture artworks", result1.get("sculpture"));
        assertEquals("Alice White should have 1 architecture artwork", Integer.valueOf(1), result1.get("architecture"));
        
        // Verify: Check counts for David Green
        assertNull("David Green should not have painting artworks", result2.get("painting"));
        assertEquals("David Green should have 1 sculpture artwork", Integer.valueOf(1), result2.get("sculpture"));
        assertEquals("David Green should have 1 architecture artwork", Integer.valueOf(1), result2.get("architecture"));
    }
}