import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;

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
        
        // Add 3 artworks: 2 paintings, 1 sculpture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory("painting");
        artwork1.setPrice(200.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory("painting");
        artwork2.setPrice(150.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory("sculpture");
        artwork3.setPrice(500.0);
        
        artist.getArtworks().add(artwork1);
        artist.getArtworks().add(artwork2);
        artist.getArtworks().add(artwork3);
        
        // Execute: Count artworks by category
        Map<String, Integer> result = artist.countArtworksByCategory();
        
        // Verify: There are 2 painting artworks, 1 sculpture artwork, and 0 architecture artwork
        assertEquals(Integer.valueOf(2), result.get("painting"));
        assertEquals(Integer.valueOf(1), result.get("sculpture"));
        assertNull(result.get("architecture"));
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
        
        // Add 4 artworks: 1 painting, 2 sculptures, 1 architecture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory("painting");
        artwork1.setPrice(300.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory("sculpture");
        artwork2.setPrice(700.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory("sculpture");
        artwork3.setPrice(600.0);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory("architecture");
        artwork4.setPrice(900.0);
        
        artist.getArtworks().add(artwork1);
        artist.getArtworks().add(artwork2);
        artist.getArtworks().add(artwork3);
        artist.getArtworks().add(artwork4);
        
        // Execute: Count artworks by category
        Map<String, Integer> result = artist.countArtworksByCategory();
        
        // Verify: There are 1 painting artwork, 2 sculpture artworks, and 1 architecture artwork
        assertEquals(Integer.valueOf(1), result.get("painting"));
        assertEquals(Integer.valueOf(2), result.get("sculpture"));
        assertEquals(Integer.valueOf(1), result.get("architecture"));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // SetUp: Create artist Emily Brown without any artworks
        Artist artist = new Artist();
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        // Execute: Count artworks by category
        Map<String, Integer> result = artist.countArtworksByCategory();
        
        // Verify: Empty map since no artworks exist
        assertTrue(result.isEmpty());
        
        // Verify specific categories are not present
        assertNull(result.get("painting"));
        assertNull(result.get("sculpture"));
        assertNull(result.get("architecture"));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // SetUp: Create artist Michael Johnson with only sculptures
        Artist artist = new Artist();
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender("Male");
        
        // Add 3 sculptures
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory("sculpture");
        artwork1.setPrice(800.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory("sculpture");
        artwork2.setPrice(1200.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory("sculpture");
        artwork3.setPrice(1000.0);
        
        artist.getArtworks().add(artwork1);
        artist.getArtworks().add(artwork2);
        artist.getArtworks().add(artwork3);
        
        // Execute: Count artworks by category
        Map<String, Integer> result = artist.countArtworksByCategory();
        
        // Verify: There are 0 painting artwork, 3 sculpture artworks, and 0 architecture artwork
        assertNull(result.get("painting"));
        assertEquals(Integer.valueOf(3), result.get("sculpture"));
        assertNull(result.get("architecture"));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // SetUp: Create artist Alice White with artworks
        Artist artist1 = new Artist();
        artist1.setName("Alice White");
        artist1.setId("A005");
        
        Artwork artwork1_1 = new Artwork();
        artwork1_1.setTitle("Landscapes");
        artwork1_1.setCategory("painting");
        
        Artwork artwork1_2 = new Artwork();
        artwork1_2.setTitle("Steel Bridge");
        artwork1_2.setCategory("architecture");
        
        artist1.getArtworks().add(artwork1_1);
        artist1.getArtworks().add(artwork1_2);
        
        // SetUp: Create artist David Green with artworks
        Artist artist2 = new Artist();
        artist2.setName("David Green");
        artist2.setId("A006");
        
        Artwork artwork2_1 = new Artwork();
        artwork2_1.setTitle("Marble Sculpture");
        artwork2_1.setCategory("sculpture");
        
        Artwork artwork2_2 = new Artwork();
        artwork2_2.setTitle("City Skyline");
        artwork2_2.setCategory("architecture");
        
        artist2.getArtworks().add(artwork2_1);
        artist2.getArtworks().add(artwork2_2);
        
        // Execute: Count artworks by category for both artists
        Map<String, Integer> result1 = artist1.countArtworksByCategory();
        Map<String, Integer> result2 = artist2.countArtworksByCategory();
        
        // Verify: For Alice White - 1 painting, 0 sculpture, 1 architecture
        assertEquals(Integer.valueOf(1), result1.get("painting"));
        assertNull(result1.get("sculpture"));
        assertEquals(Integer.valueOf(1), result1.get("architecture"));
        
        // Verify: For David Green - 0 painting, 1 sculpture, 1 architecture
        assertNull(result2.get("painting"));
        assertEquals(Integer.valueOf(1), result2.get("sculpture"));
        assertEquals(Integer.valueOf(1), result2.get("architecture"));
    }
}