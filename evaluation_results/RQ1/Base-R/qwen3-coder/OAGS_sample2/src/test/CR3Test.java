import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;

public class CR3Test {
    
    private Artist artist;
    
    @Before
    public void setUp() {
        artist = new Artist();
    }
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // SetUp: Create artist John Doe
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
        
        // Verify: Expected - 2 paintings, 1 sculpture, 0 architecture
        assertEquals(Integer.valueOf(2), result.get("painting"));
        assertEquals(Integer.valueOf(1), result.get("sculpture"));
        assertEquals(Integer.valueOf(0), result.get("architecture"));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // SetUp: Create artist Jane Smith
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
        
        // Verify: Expected - 1 painting, 2 sculptures, 1 architecture
        assertEquals(Integer.valueOf(1), result.get("painting"));
        assertEquals(Integer.valueOf(2), result.get("sculpture"));
        assertEquals(Integer.valueOf(1), result.get("architecture"));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // SetUp: Create artist Emily Brown with no artworks
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        // Artworks list remains empty
        
        // Execute: Count artworks by category
        Map<String, Integer> result = artist.countArtworksByCategory();
        
        // Verify: Expected - 0 for all categories
        assertEquals(Integer.valueOf(0), result.get("painting"));
        assertEquals(Integer.valueOf(0), result.get("sculpture"));
        assertEquals(Integer.valueOf(0), result.get("architecture"));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // SetUp: Create artist Michael Johnson
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender("Male");
        
        // Add 3 sculptures only
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
        
        // Verify: Expected - 0 paintings, 3 sculptures, 0 architecture
        assertEquals(Integer.valueOf(0), result.get("painting"));
        assertEquals(Integer.valueOf(3), result.get("sculpture"));
        assertEquals(Integer.valueOf(0), result.get("architecture"));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // SetUp: Create two artists - Alice White and David Green
        
        // Artist E: Alice White
        Artist artistE = new Artist();
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        // Add artworks for Alice White: 1 painting, 1 architecture
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
        
        // Add artworks for David Green: 1 sculpture, 1 architecture
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
        
        // Verify: Alice White - 1 painting, 0 sculpture, 1 architecture
        assertEquals(Integer.valueOf(1), resultE.get("painting"));
        assertEquals(Integer.valueOf(0), resultE.get("sculpture"));
        assertEquals(Integer.valueOf(1), resultE.get("architecture"));
        
        // Verify: David Green - 0 painting, 1 sculpture, 1 architecture
        assertEquals(Integer.valueOf(0), resultF.get("painting"));
        assertEquals(Integer.valueOf(1), resultF.get("sculpture"));
        assertEquals(Integer.valueOf(1), resultF.get("architecture"));
    }
}