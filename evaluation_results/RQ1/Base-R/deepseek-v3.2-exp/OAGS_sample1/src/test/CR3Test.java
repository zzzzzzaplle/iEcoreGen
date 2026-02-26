import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        // Set up common test data for all test cases
        initializeArtists();
    }
    
    private void initializeArtists() {
        // Initialize Artist A (John Doe) with artworks
        artistA = new Artist();
        artistA.setName("John Doe");
        artistA.setId("A001");
        artistA.setPhoneNumber("1234567890");
        artistA.setEmail("john.doe@example.com");
        artistA.setAddress("123 Art St");
        artistA.setGender("Male");
        
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
        
        // Initialize Artist B (Jane Smith) with artworks
        artistB = new Artist();
        artistB.setName("Jane Smith");
        artistB.setId("A002");
        artistB.setPhoneNumber("9876543210");
        artistB.setEmail("jane.smith@example.com");
        artistB.setAddress("456 Art Ave");
        artistB.setGender("Female");
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Abstract Colors");
        artwork4.setDescription("An abstract painting");
        artwork4.setCategory("painting");
        artwork4.setPrice(300.0);
        
        Artwork artwork5 = new Artwork();
        artwork5.setTitle("David");
        artwork5.setDescription("A sculpture of David");
        artwork5.setCategory("sculpture");
        artwork5.setPrice(700.0);
        
        Artwork artwork6 = new Artwork();
        artwork6.setTitle("Classic Statue");
        artwork6.setDescription("A classic statue");
        artwork6.setCategory("sculpture");
        artwork6.setPrice(600.0);
        
        Artwork artwork7 = new Artwork();
        artwork7.setTitle("Skyscraper");
        artwork7.setDescription("Modern architecture");
        artwork7.setCategory("architecture");
        artwork7.setPrice(900.0);
        
        artistB.addArtwork(artwork4);
        artistB.addArtwork(artwork5);
        artistB.addArtwork(artwork6);
        artistB.addArtwork(artwork7);
        
        // Initialize Artist C (Emily Brown) with no artworks
        artistC = new Artist();
        artistC.setName("Emily Brown");
        artistC.setId("A003");
        artistC.setPhoneNumber("1112223333");
        artistC.setEmail("emily.brown@example.com");
        artistC.setAddress("789 Art Blvd");
        artistC.setGender("Female");
        
        // Initialize Artist D (Michael Johnson) with only sculptures
        artistD = new Artist();
        artistD.setName("Michael Johnson");
        artistD.setId("A004");
        artistD.setPhoneNumber("4445556666");
        artistD.setEmail("michael.johnson@example.com");
        artistD.setAddress("123 Art Lane");
        artistD.setGender("Male");
        
        Artwork artwork8 = new Artwork();
        artwork8.setTitle("Modern Art");
        artwork8.setDescription("A modern sculpture");
        artwork8.setCategory("sculpture");
        artwork8.setPrice(800.0);
        
        Artwork artwork9 = new Artwork();
        artwork9.setTitle("Wooden Carving");
        artwork9.setDescription("A wooden sculpture");
        artwork9.setCategory("sculpture");
        artwork9.setPrice(1200.0);
        
        Artwork artwork10 = new Artwork();
        artwork10.setTitle("Stone Figure");
        artwork10.setDescription("A stone sculpture");
        artwork10.setCategory("sculpture");
        artwork10.setPrice(1000.0);
        
        artistD.addArtwork(artwork8);
        artistD.addArtwork(artwork9);
        artistD.addArtwork(artwork10);
        
        // Initialize Artist E (Alice White) with mixed categories
        artistE = new Artist();
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        Artwork artwork11 = new Artwork();
        artwork11.setTitle("Landscapes");
        artwork11.setCategory("painting");
        
        Artwork artwork12 = new Artwork();
        artwork12.setTitle("Steel Bridge");
        artwork12.setCategory("architecture");
        
        artistE.addArtwork(artwork11);
        artistE.addArtwork(artwork12);
        
        // Initialize Artist F (David Green) with mixed categories
        artistF = new Artist();
        artistF.setName("David Green");
        artistF.setId("A006");
        
        Artwork artwork13 = new Artwork();
        artwork13.setTitle("Marble Sculpture");
        artwork13.setCategory("sculpture");
        
        Artwork artwork14 = new Artwork();
        artwork14.setTitle("City Skyline");
        artwork14.setCategory("architecture");
        
        artistF.addArtwork(artwork13);
        artistF.addArtwork(artwork14);
    }
    
    @Test
    public void testCase1_countArtworksByCategoryForSingleArtist() {
        // Test Case 1: Count Artworks by Category for a Single Artist
        
        // Call the method to count artworks by category
        Map<String, Integer> categoryCount = ArtistService.countArtworksByCategory(artistA);
        
        // Verify the counts for each category
        assertEquals("Should have 2 painting artworks", Integer.valueOf(2), categoryCount.get("painting"));
        assertEquals("Should have 1 sculpture artwork", Integer.valueOf(1), categoryCount.get("sculpture"));
        
        // Check that architecture is either 0 or not present in the map
        assertTrue("Should have 0 architecture artworks", 
                   categoryCount.get("architecture") == null || categoryCount.get("architecture") == 0);
    }
    
    @Test
    public void testCase2_countArtworksByCategoryWithMultipleCategories() {
        // Test Case 2: Count Artworks by Category with Multiple Categories
        
        // Call the method to count artworks by category
        Map<String, Integer> categoryCount = ArtistService.countArtworksByCategory(artistB);
        
        // Verify the counts for each category
        assertEquals("Should have 1 painting artwork", Integer.valueOf(1), categoryCount.get("painting"));
        assertEquals("Should have 2 sculpture artworks", Integer.valueOf(2), categoryCount.get("sculpture"));
        assertEquals("Should have 1 architecture artwork", Integer.valueOf(1), categoryCount.get("architecture"));
    }
    
    @Test
    public void testCase3_noArtworksForAnArtist() {
        // Test Case 3: No Artworks for an Artist
        
        // Call the method to count artworks by category
        Map<String, Integer> categoryCount = ArtistService.countArtworksByCategory(artistC);
        
        // Verify that all category counts are 0 or not present
        assertTrue("Should have empty map or zero counts for all categories", 
                   categoryCount.isEmpty() || 
                   (categoryCount.get("painting") == null || categoryCount.get("painting") == 0) &&
                   (categoryCount.get("sculpture") == null || categoryCount.get("sculpture") == 0) &&
                   (categoryCount.get("architecture") == null || categoryCount.get("architecture") == 0));
    }
    
    @Test
    public void testCase4_countArtworksWithOnlyOneCategory() {
        // Test Case 4: Count Artworks with Only One Category
        
        // Call the method to count artworks by category
        Map<String, Integer> categoryCount = ArtistService.countArtworksByCategory(artistD);
        
        // Verify the counts for each category
        assertTrue("Should have 0 painting artworks", 
                   categoryCount.get("painting") == null || categoryCount.get("painting") == 0);
        assertEquals("Should have 3 sculpture artworks", Integer.valueOf(3), categoryCount.get("sculpture"));
        assertTrue("Should have 0 architecture artworks", 
                   categoryCount.get("architecture") == null || categoryCount.get("architecture") == 0);
    }
    
    @Test
    public void testCase5_countArtworksWithMultipleArtists() {
        // Test Case 5: Count Artworks with Multiple Artists
        
        // Test Artist E (Alice White)
        Map<String, Integer> categoryCountE = ArtistService.countArtworksByCategory(artistE);
        assertEquals("Artist E should have 1 painting artwork", Integer.valueOf(1), categoryCountE.get("painting"));
        assertTrue("Artist E should have 0 sculpture artworks", 
                   categoryCountE.get("sculpture") == null || categoryCountE.get("sculpture") == 0);
        assertEquals("Artist E should have 1 architecture artwork", Integer.valueOf(1), categoryCountE.get("architecture"));
        
        // Test Artist F (David Green)
        Map<String, Integer> categoryCountF = ArtistService.countArtworksByCategory(artistF);
        assertTrue("Artist F should have 0 painting artworks", 
                   categoryCountF.get("painting") == null || categoryCountF.get("painting") == 0);
        assertEquals("Artist F should have 1 sculpture artwork", Integer.valueOf(1), categoryCountF.get("sculpture"));
        assertEquals("Artist F should have 1 architecture artwork", Integer.valueOf(1), categoryCountF.get("architecture"));
    }
}