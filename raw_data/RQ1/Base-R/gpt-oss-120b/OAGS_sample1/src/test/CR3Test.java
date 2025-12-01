import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
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
        artistA.setId("A001");
        artistA.setName("John Doe");
        artistA.setPhoneNumber("1234567890");
        artistA.setEmail("john.doe@example.com");
        artistA.setAddress("123 Art St");
        artistA.setGender(Gender.MALE);
        
        // Add 3 artworks to John Doe
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(Category.PAINTING);
        artwork1.setPrice(200.0);
        artistA.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(Category.PAINTING);
        artwork2.setPrice(150.0);
        artistA.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(500.0);
        artistA.addArtwork(artwork3);
        
        // Count artworks by category
        Map<Category, Long> result = artistA.countArtworksByCategory();
        
        // Verify expected output: 2 painting artworks, 1 sculpture artwork, and 0 architecture artwork
        assertEquals(2, result.getOrDefault(Category.PAINTING, 0L).longValue());
        assertEquals(1, result.getOrDefault(Category.SCULPTURE, 0L).longValue());
        assertEquals(0, result.getOrDefault(Category.ARCHITECTURE, 0L).longValue());
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Set up Artist B (Jane Smith)
        artistB.setId("A002");
        artistB.setName("Jane Smith");
        artistB.setPhoneNumber("9876543210");
        artistB.setEmail("jane.smith@example.com");
        artistB.setAddress("456 Art Ave");
        artistB.setGender(Gender.FEMALE);
        
        // Add 1 painting, 2 sculptures, and 1 architecture to Jane Smith
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(Category.PAINTING);
        artwork1.setPrice(300.0);
        artistB.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(Category.SCULPTURE);
        artwork2.setPrice(700.0);
        artistB.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(600.0);
        artistB.addArtwork(artwork3);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(Category.ARCHITECTURE);
        artwork4.setPrice(900.0);
        artistB.addArtwork(artwork4);
        
        // Count artworks by category
        Map<Category, Long> result = artistB.countArtworksByCategory();
        
        // Verify expected output: 1 painting artwork, 2 sculpture artworks, and 1 architecture artwork
        assertEquals(1, result.getOrDefault(Category.PAINTING, 0L).longValue());
        assertEquals(2, result.getOrDefault(Category.SCULPTURE, 0L).longValue());
        assertEquals(1, result.getOrDefault(Category.ARCHITECTURE, 0L).longValue());
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Set up Artist C (Emily Brown)
        artistC.setId("A003");
        artistC.setName("Emily Brown");
        artistC.setPhoneNumber("1112223333");
        artistC.setEmail("emily.brown@example.com");
        artistC.setAddress("789 Art Blvd");
        artistC.setGender(Gender.FEMALE);
        
        // Do not add any artworks to Emily Brown
        // Count artworks by category
        Map<Category, Long> result = artistC.countArtworksByCategory();
        
        // Verify expected output: 0 painting artwork, 0 sculpture artwork, and 0 architecture artwork
        assertEquals(0, result.getOrDefault(Category.PAINTING, 0L).longValue());
        assertEquals(0, result.getOrDefault(Category.SCULPTURE, 0L).longValue());
        assertEquals(0, result.getOrDefault(Category.ARCHITECTURE, 0L).longValue());
        assertTrue(result.isEmpty()); // Map should be empty when no artworks
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Set up Artist D (Michael Johnson)
        artistD.setId("A004");
        artistD.setName("Michael Johnson");
        artistD.setPhoneNumber("4445556666");
        artistD.setEmail("michael.johnson@example.com");
        artistD.setAddress("123 Art Lane");
        artistD.setGender(Gender.MALE);
        
        // Add 3 sculptures and no other category
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(Category.SCULPTURE);
        artwork1.setPrice(800.0);
        artistD.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(Category.SCULPTURE);
        artwork2.setPrice(1200.0);
        artistD.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(1000.0);
        artistD.addArtwork(artwork3);
        
        // Count artworks by category
        Map<Category, Long> result = artistD.countArtworksByCategory();
        
        // Verify expected output: 0 painting artwork, 3 sculpture artworks, and 0 architecture artwork
        assertEquals(0, result.getOrDefault(Category.PAINTING, 0L).longValue());
        assertEquals(3, result.getOrDefault(Category.SCULPTURE, 0L).longValue());
        assertEquals(0, result.getOrDefault(Category.ARCHITECTURE, 0L).longValue());
        assertEquals(1, result.size()); // Only one category should be present
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Set up Artist E (Alice White)
        artistE.setId("A005");
        artistE.setName("Alice White");
        
        // Add artworks to Alice White
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Landscapes");
        artwork1.setCategory(Category.PAINTING);
        artistE.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Steel Bridge");
        artwork2.setCategory(Category.ARCHITECTURE);
        artistE.addArtwork(artwork2);
        
        // Set up Artist F (David Green)
        artistF.setId("A006");
        artistF.setName("David Green");
        
        // Add artworks to David Green
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Marble Sculpture");
        artwork3.setCategory(Category.SCULPTURE);
        artistF.addArtwork(artwork3);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("City Skyline");
        artwork4.setCategory(Category.ARCHITECTURE);
        artistF.addArtwork(artwork4);
        
        // Count artworks by category for Alice White
        Map<Category, Long> resultE = artistE.countArtworksByCategory();
        
        // Verify expected output for Alice White: 1 painting artwork, 0 sculpture artwork, and 1 architecture artwork
        assertEquals(1, resultE.getOrDefault(Category.PAINTING, 0L).longValue());
        assertEquals(0, resultE.getOrDefault(Category.SCULPTURE, 0L).longValue());
        assertEquals(1, resultE.getOrDefault(Category.ARCHITECTURE, 0L).longValue());
        
        // Count artworks by category for David Green
        Map<Category, Long> resultF = artistF.countArtworksByCategory();
        
        // Verify expected output for David Green: 0 painting artworks, 1 sculpture artwork, and 1 architecture artwork
        assertEquals(0, resultF.getOrDefault(Category.PAINTING, 0L).longValue());
        assertEquals(1, resultF.getOrDefault(Category.SCULPTURE, 0L).longValue());
        assertEquals(1, resultF.getOrDefault(Category.ARCHITECTURE, 0L).longValue());
    }
}