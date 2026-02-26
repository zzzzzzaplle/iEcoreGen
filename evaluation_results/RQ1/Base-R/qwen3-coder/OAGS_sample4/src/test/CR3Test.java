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
        
        artistA.addArtwork(artwork1);
        artistA.addArtwork(artwork2);
        artistA.addArtwork(artwork3);
        
        // Count artworks by category
        Map<String, Integer> result = artistA.countArtworksByCategory();
        
        // Verify expected output: 2 painting, 1 sculpture, 0 architecture
        assertEquals("Painting count should be 2", Integer.valueOf(2), result.get("painting"));
        assertEquals("Sculpture count should be 1", Integer.valueOf(1), result.get("sculpture"));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get("architecture"));
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
        
        artistB.addArtwork(artwork1);
        artistB.addArtwork(artwork2);
        artistB.addArtwork(artwork3);
        artistB.addArtwork(artwork4);
        
        // Count artworks by category
        Map<String, Integer> result = artistB.countArtworksByCategory();
        
        // Verify expected output: 1 painting, 2 sculpture, 1 architecture
        assertEquals("Painting count should be 1", Integer.valueOf(1), result.get("painting"));
        assertEquals("Sculpture count should be 2", Integer.valueOf(2), result.get("sculpture"));
        assertEquals("Architecture count should be 1", Integer.valueOf(1), result.get("architecture"));
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
        
        // No artworks added to Emily Brown
        
        // Count artworks by category
        Map<String, Integer> result = artistC.countArtworksByCategory();
        
        // Verify expected output: 0 painting, 0 sculpture, 0 architecture
        assertEquals("Painting count should be 0", Integer.valueOf(0), result.get("painting"));
        assertEquals("Sculpture count should be 0", Integer.valueOf(0), result.get("sculpture"));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get("architecture"));
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
        
        artistD.addArtwork(artwork1);
        artistD.addArtwork(artwork2);
        artistD.addArtwork(artwork3);
        
        // Count artworks by category
        Map<String, Integer> result = artistD.countArtworksByCategory();
        
        // Verify expected output: 0 painting, 3 sculpture, 0 architecture
        assertEquals("Painting count should be 0", Integer.valueOf(0), result.get("painting"));
        assertEquals("Sculpture count should be 3", Integer.valueOf(3), result.get("sculpture"));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get("architecture"));
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
        Map<String, Integer> resultE = artistE.countArtworksByCategory();
        
        // Verify expected output for Alice White: 1 painting, 0 sculpture, 1 architecture
        assertEquals("Alice White - Painting count should be 1", Integer.valueOf(1), resultE.get("painting"));
        assertEquals("Alice White - Sculpture count should be 0", Integer.valueOf(0), resultE.get("sculpture"));
        assertEquals("Alice White - Architecture count should be 1", Integer.valueOf(1), resultE.get("architecture"));
        
        // Count artworks by category for David Green
        Map<String, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify expected output for David Green: 0 painting, 1 sculpture, 1 architecture
        assertEquals("David Green - Painting count should be 0", Integer.valueOf(0), resultF.get("painting"));
        assertEquals("David Green - Sculpture count should be 1", Integer.valueOf(1), resultF.get("sculpture"));
        assertEquals("David Green - Architecture count should be 1", Integer.valueOf(1), resultF.get("architecture"));
    }
}