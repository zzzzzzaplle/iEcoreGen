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
        // Initialize artists for each test
        artistA = new Artist();
        artistB = new Artist();
        artistC = new Artist();
        artistD = new Artist();
        artistE = new Artist();
        artistF = new Artist();
    }
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Set up Artist A (John Doe)
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
        
        // Count artworks by category
        Map<String, Integer> categoryCount = artistA.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 2 painting artworks", Integer.valueOf(2), categoryCount.get("painting"));
        assertEquals("Should have 1 sculpture artwork", Integer.valueOf(1), categoryCount.get("sculpture"));
        assertNull("Should not have architecture artworks", categoryCount.get("architecture"));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Set up Artist B (Jane Smith)
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
        
        // Count artworks by category
        Map<String, Integer> categoryCount = artistB.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 1 painting artwork", Integer.valueOf(1), categoryCount.get("painting"));
        assertEquals("Should have 2 sculpture artworks", Integer.valueOf(2), categoryCount.get("sculpture"));
        assertEquals("Should have 1 architecture artwork", Integer.valueOf(1), categoryCount.get("architecture"));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Set up Artist C (Emily Brown)
        artistC.setName("Emily Brown");
        artistC.setId("A003");
        artistC.setPhoneNumber("1112223333");
        artistC.setEmail("emily.brown@example.com");
        artistC.setAddress("789 Art Blvd");
        artistC.setGender("Female");
        
        // Do not add any artworks to Emily Brown
        // Count artworks by category
        Map<String, Integer> categoryCount = artistC.countArtworksByCategory();
        
        // Verify expected counts - empty map
        assertTrue("Should have empty category count map", categoryCount.isEmpty());
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Set up Artist D (Michael Johnson)
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
        
        // Count artworks by category
        Map<String, Integer> categoryCount = artistD.countArtworksByCategory();
        
        // Verify expected counts
        assertNull("Should not have painting artworks", categoryCount.get("painting"));
        assertEquals("Should have 3 sculpture artworks", Integer.valueOf(3), categoryCount.get("sculpture"));
        assertNull("Should not have architecture artworks", categoryCount.get("architecture"));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Set up Artist E (Alice White)
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        // Add artworks to Alice White
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory("painting");
        
        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory("architecture");
        
        artistE.addArtwork(artworkE1);
        artistE.addArtwork(artworkE2);
        
        // Set up Artist F (David Green)
        artistF.setName("David Green");
        artistF.setId("A006");
        
        // Add artworks to David Green
        Artwork artworkF1 = new Artwork();
        artworkF1.setTitle("Marble Sculpture");
        artworkF1.setCategory("sculpture");
        
        Artwork artworkF2 = new Artwork();
        artworkF2.setTitle("City Skyline");
        artworkF2.setCategory("architecture");
        
        artistF.addArtwork(artworkF1);
        artistF.addArtwork(artworkF2);
        
        // Count artworks by category for Alice White
        Map<String, Integer> categoryCountE = artistE.countArtworksByCategory();
        
        // Verify expected counts for Alice White
        assertEquals("Alice White should have 1 painting artwork", Integer.valueOf(1), categoryCountE.get("painting"));
        assertNull("Alice White should not have sculpture artworks", categoryCountE.get("sculpture"));
        assertEquals("Alice White should have 1 architecture artwork", Integer.valueOf(1), categoryCountE.get("architecture"));
        
        // Count artworks by category for David Green
        Map<String, Integer> categoryCountF = artistF.countArtworksByCategory();
        
        // Verify expected counts for David Green
        assertNull("David Green should not have painting artworks", categoryCountF.get("painting"));
        assertEquals("David Green should have 1 sculpture artwork", Integer.valueOf(1), categoryCountF.get("sculpture"));
        assertEquals("David Green should have 1 architecture artwork", Integer.valueOf(1), categoryCountF.get("architecture"));
    }
}