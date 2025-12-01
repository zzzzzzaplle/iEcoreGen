import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CR3Test {
    
    private Artist artistA;
    private Artist artistB;
    private Artist artistC;
    private Artist artistD;
    private Artist artistE;
    private Artist artistF;
    
    @Before
    public void setUp() {
        // Initialize artists for test cases
        artistA = new Artist();
        artistB = new Artist();
        artistC = new Artist();
        artistD = new Artist();
        artistE = new Artist();
        artistF = new Artist();
    }
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Test Case 1: Count Artworks by Category for a Single Artist
        // SetUp: Create Artist A (John Doe) with 3 artworks
        artistA.setName("John Doe");
        artistA.setId("A001");
        artistA.setPhoneNumber("1234567890");
        artistA.setEmail("john.doe@example.com");
        artistA.setAddress("123 Art St");
        artistA.setGender("Male");
        
        // Add 3 artworks to John Doe
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
        
        artistA.addArtwork(artwork1);
        artistA.addArtwork(artwork2);
        artistA.addArtwork(artwork3);
        
        // Expected Output: There are 2 painting artworks, 1 sculpture artwork, and 0 architecture artwork
        Map<String, Integer> result = artistA.countArtworksByCategory();
        
        assertEquals("Painting count should be 2", Integer.valueOf(2), result.get("painting"));
        assertEquals("Sculpture count should be 1", Integer.valueOf(1), result.get("sculpture"));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get("architecture"));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Test Case 2: Count Artworks by Category with Multiple Categories
        // SetUp: Create Artist B (Jane Smith) with 4 artworks
        artistB.setName("Jane Smith");
        artistB.setId("A002");
        artistB.setPhoneNumber("9876543210");
        artistB.setEmail("jane.smith@example.com");
        artistB.setAddress("456 Art Ave");
        artistB.setGender("Female");
        
        // Add 1 painting, 2 sculptures, and 1 architecture to Jane Smith
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
        
        artistB.addArtwork(artwork1);
        artistB.addArtwork(artwork2);
        artistB.addArtwork(artwork3);
        artistB.addArtwork(artwork4);
        
        // Expected Output: There are 1 painting artwork, 2 sculpture artworks, and 1 architecture artwork
        Map<String, Integer> result = artistB.countArtworksByCategory();
        
        assertEquals("Painting count should be 1", Integer.valueOf(1), result.get("painting"));
        assertEquals("Sculpture count should be 2", Integer.valueOf(2), result.get("sculpture"));
        assertEquals("Architecture count should be 1", Integer.valueOf(1), result.get("architecture"));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Test Case 3: No Artworks for an Artist
        // SetUp: Create Artist C (Emily Brown) with no artworks
        artistC.setName("Emily Brown");
        artistC.setId("A003");
        artistC.setPhoneNumber("1112223333");
        artistC.setEmail("emily.brown@example.com");
        artistC.setAddress("789 Art Blvd");
        artistC.setGender("Female");
        
        // Do not add any artworks to Emily Brown
        
        // Expected Output: There are 0 painting artwork, 0 sculpture artwork, and 0 architecture artwork
        Map<String, Integer> result = artistC.countArtworksByCategory();
        
        assertEquals("Painting count should be 0", Integer.valueOf(0), result.get("painting"));
        assertEquals("Sculpture count should be 0", Integer.valueOf(0), result.get("sculpture"));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get("architecture"));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Test Case 4: Count Artworks with Only One Category
        // SetUp: Create Artist D (Michael Johnson) with 3 sculptures
        artistD.setName("Michael Johnson");
        artistD.setId("A004");
        artistD.setPhoneNumber("4445556666");
        artistD.setEmail("michael.johnson@example.com");
        artistD.setAddress("123 Art Lane");
        artistD.setGender("Male");
        
        // Add 3 sculptures and no other category
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
        
        artistD.addArtwork(artwork1);
        artistD.addArtwork(artwork2);
        artistD.addArtwork(artwork3);
        
        // Expected Output: There are 0 painting artwork, 3 sculpture artworks, and 0 architecture artwork
        Map<String, Integer> result = artistD.countArtworksByCategory();
        
        assertEquals("Painting count should be 0", Integer.valueOf(0), result.get("painting"));
        assertEquals("Sculpture count should be 3", Integer.valueOf(3), result.get("sculpture"));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get("architecture"));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Test Case 5: Count Artworks with Multiple Artists
        // SetUp: Create Artist E (Alice White) with 2 artworks
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory("painting");
        
        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory("architecture");
        
        artistE.addArtwork(artworkE1);
        artistE.addArtwork(artworkE2);
        
        // SetUp: Create Artist F (David Green) with 2 artworks
        artistF.setName("David Green");
        artistF.setId("A006");
        
        Artwork artworkF1 = new Artwork();
        artworkF1.setTitle("Marble Sculpture");
        artworkF1.setCategory("sculpture");
        
        Artwork artworkF2 = new Artwork();
        artworkF2.setTitle("City Skyline");
        artworkF2.setCategory("architecture");
        
        artistF.addArtwork(artworkF1);
        artistF.addArtwork(artworkF2);
        
        // Expected Output for Alice White: There are 1 painting artwork, 0 sculpture artwork, and 1 architecture artwork
        Map<String, Integer> resultE = artistE.countArtworksByCategory();
        assertEquals("Alice White - Painting count should be 1", Integer.valueOf(1), resultE.get("painting"));
        assertEquals("Alice White - Sculpture count should be 0", Integer.valueOf(0), resultE.get("sculpture"));
        assertEquals("Alice White - Architecture count should be 1", Integer.valueOf(1), resultE.get("architecture"));
        
        // Expected Output for David Green: There are 0 painting artworks, 1 sculpture artwork, and 1 architecture artwork
        Map<String, Integer> resultF = artistF.countArtworksByCategory();
        assertEquals("David Green - Painting count should be 0", Integer.valueOf(0), resultF.get("painting"));
        assertEquals("David Green - Sculpture count should be 1", Integer.valueOf(1), resultF.get("sculpture"));
        assertEquals("David Green - Architecture count should be 1", Integer.valueOf(1), resultF.get("architecture"));
    }
}