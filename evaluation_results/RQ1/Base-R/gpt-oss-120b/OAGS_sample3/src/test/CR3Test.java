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
        // Initialize artists for reuse across test cases
        artistA = new Artist();
        artistA.setName("John Doe");
        artistA.setArtistId("A001");
        artistA.setPhoneNumber("1234567890");
        artistA.setEmail("john.doe@example.com");
        artistA.setAddress("123 Art St");
        artistA.setGender("Male");
        
        artistB = new Artist();
        artistB.setName("Jane Smith");
        artistB.setArtistId("A002");
        artistB.setPhoneNumber("9876543210");
        artistB.setEmail("jane.smith@example.com");
        artistB.setAddress("456 Art Ave");
        artistB.setGender("Female");
        
        artistC = new Artist();
        artistC.setName("Emily Brown");
        artistC.setArtistId("A003");
        artistC.setPhoneNumber("1112223333");
        artistC.setEmail("emily.brown@example.com");
        artistC.setAddress("789 Art Blvd");
        artistC.setGender("Female");
        
        artistD = new Artist();
        artistD.setName("Michael Johnson");
        artistD.setArtistId("A004");
        artistD.setPhoneNumber("4445556666");
        artistD.setEmail("michael.johnson@example.com");
        artistD.setAddress("123 Art Lane");
        artistD.setGender("Male");
        
        artistE = new Artist();
        artistE.setName("Alice White");
        artistE.setArtistId("A005");
        
        artistF = new Artist();
        artistF.setName("David Green");
        artistF.setArtistId("A006");
    }
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Set up artworks for Artist A (John Doe)
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(Category.PAINTING);
        artwork1.setPrice(200.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(Category.PAINTING);
        artwork2.setPrice(150.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(500.0);
        
        artistA.getArtworks().add(artwork1);
        artistA.getArtworks().add(artwork2);
        artistA.getArtworks().add(artwork3);
        
        // Count artworks by category
        Map<Category, Integer> result = artistA.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 2 painting artworks", Integer.valueOf(2), result.get(Category.PAINTING));
        assertEquals("Should have 1 sculpture artwork", Integer.valueOf(1), result.get(Category.SCULPTURE));
        assertNull("Should not have architecture artworks", result.get(Category.ARCHITECTURE));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Set up artworks for Artist B (Jane Smith)
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(Category.PAINTING);
        artwork1.setPrice(300.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(Category.SCULPTURE);
        artwork2.setPrice(700.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(600.0);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(Category.ARCHITECTURE);
        artwork4.setPrice(900.0);
        
        artistB.getArtworks().add(artwork1);
        artistB.getArtworks().add(artwork2);
        artistB.getArtworks().add(artwork3);
        artistB.getArtworks().add(artwork4);
        
        // Count artworks by category
        Map<Category, Integer> result = artistB.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 1 painting artwork", Integer.valueOf(1), result.get(Category.PAINTING));
        assertEquals("Should have 2 sculpture artworks", Integer.valueOf(2), result.get(Category.SCULPTURE));
        assertEquals("Should have 1 architecture artwork", Integer.valueOf(1), result.get(Category.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Artist C (Emily Brown) has no artworks - already set up in @Before
        
        // Count artworks by category
        Map<Category, Integer> result = artistC.countArtworksByCategory();
        
        // Verify empty map for artist with no artworks
        assertTrue("Should return empty map when no artworks", result.isEmpty());
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Set up artworks for Artist D (Michael Johnson) - only sculptures
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(Category.SCULPTURE);
        artwork1.setPrice(800.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(Category.SCULPTURE);
        artwork2.setPrice(1200.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(1000.0);
        
        artistD.getArtworks().add(artwork1);
        artistD.getArtworks().add(artwork2);
        artistD.getArtworks().add(artwork3);
        
        // Count artworks by category
        Map<Category, Integer> result = artistD.countArtworksByCategory();
        
        // Verify expected counts - only sculpture category present
        assertNull("Should not have painting artworks", result.get(Category.PAINTING));
        assertEquals("Should have 3 sculpture artworks", Integer.valueOf(3), result.get(Category.SCULPTURE));
        assertNull("Should not have architecture artworks", result.get(Category.ARCHITECTURE));
        assertEquals("Should have only one category in map", 1, result.size());
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Set up artworks for Artist E (Alice White)
        Artwork artwork1E = new Artwork();
        artwork1E.setTitle("Landscapes");
        artwork1E.setCategory(Category.PAINTING);
        
        Artwork artwork2E = new Artwork();
        artwork2E.setTitle("Steel Bridge");
        artwork2E.setCategory(Category.ARCHITECTURE);
        
        artistE.getArtworks().add(artwork1E);
        artistE.getArtworks().add(artwork2E);
        
        // Set up artworks for Artist F (David Green)
        Artwork artwork1F = new Artwork();
        artwork1F.setTitle("Marble Sculpture");
        artwork1F.setCategory(Category.SCULPTURE);
        
        Artwork artwork2F = new Artwork();
        artwork2F.setTitle("City Skyline");
        artwork2F.setCategory(Category.ARCHITECTURE);
        
        artistF.getArtworks().add(artwork1F);
        artistF.getArtworks().add(artwork2F);
        
        // Count artworks by category for Artist E
        Map<Category, Integer> resultE = artistE.countArtworksByCategory();
        
        // Verify expected counts for Artist E
        assertEquals("Artist E should have 1 painting artwork", Integer.valueOf(1), resultE.get(Category.PAINTING));
        assertNull("Artist E should not have sculpture artworks", resultE.get(Category.SCULPTURE));
        assertEquals("Artist E should have 1 architecture artwork", Integer.valueOf(1), resultE.get(Category.ARCHITECTURE));
        
        // Count artworks by category for Artist F
        Map<Category, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify expected counts for Artist F
        assertNull("Artist F should not have painting artworks", resultF.get(Category.PAINTING));
        assertEquals("Artist F should have 1 sculpture artwork", Integer.valueOf(1), resultF.get(Category.SCULPTURE));
        assertEquals("Artist F should have 1 architecture artwork", Integer.valueOf(1), resultF.get(Category.ARCHITECTURE));
    }
}