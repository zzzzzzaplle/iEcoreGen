import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.Date;

public class CR3Test {
    
    private Artist artistA;
    private Artist artistB;
    private Artist artistC;
    private Artist artistD;
    private Artist artistE;
    private Artist artistF;
    
    @Before
    public void setUp() {
        // Initialize all artists for the test cases
        artistA = new Artist();
        artistA.setName("John Doe");
        artistA.setId("A001");
        artistA.setPhoneNumber("1234567890");
        artistA.setEmail("john.doe@example.com");
        artistA.setAddress("123 Art St");
        artistA.setGender(Gender.MALE);
        
        artistB = new Artist();
        artistB.setName("Jane Smith");
        artistB.setId("A002");
        artistB.setPhoneNumber("9876543210");
        artistB.setEmail("jane.smith@example.com");
        artistB.setAddress("456 Art Ave");
        artistB.setGender(Gender.FEMALE);
        
        artistC = new Artist();
        artistC.setName("Emily Brown");
        artistC.setId("A003");
        artistC.setPhoneNumber("1112223333");
        artistC.setEmail("emily.brown@example.com");
        artistC.setAddress("789 Art Blvd");
        artistC.setGender(Gender.FEMALE);
        
        artistD = new Artist();
        artistD.setName("Michael Johnson");
        artistD.setId("A004");
        artistD.setPhoneNumber("4445556666");
        artistD.setEmail("michael.johnson@example.com");
        artistD.setAddress("123 Art Lane");
        artistD.setGender(Gender.MALE);
        
        artistE = new Artist();
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        artistF = new Artist();
        artistF.setName("David Green");
        artistF.setId("A006");
    }
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Setup: Add 3 artworks to John Doe (Artist A)
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(200.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setPrice(150.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(500.0);
        
        artistA.addArtwork(artwork1);
        artistA.addArtwork(artwork2);
        artistA.addArtwork(artwork3);
        
        // Execute: Count artworks by category
        Map<ArtworkCategory, Integer> result = artistA.countArtworksByCategory();
        
        // Verify: There should be 2 painting artworks, 1 sculpture artwork
        assertEquals(2, result.size()); // Only categories with artworks should be in the map
        assertEquals(Integer.valueOf(2), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(1), result.get(ArtworkCategory.SCULPTURE));
        assertNull(result.get(ArtworkCategory.ARCHITECTURE)); // Architecture should not be in the map
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Setup: Add 1 painting, 2 sculptures, and 1 architecture to Jane Smith (Artist B)
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(300.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(700.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(600.0);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setPrice(900.0);
        
        artistB.addArtwork(artwork1);
        artistB.addArtwork(artwork2);
        artistB.addArtwork(artwork3);
        artistB.addArtwork(artwork4);
        
        // Execute: Count artworks by category
        Map<ArtworkCategory, Integer> result = artistB.countArtworksByCategory();
        
        // Verify: There should be 1 painting artwork, 2 sculpture artworks, and 1 architecture artwork
        assertEquals(3, result.size()); // All three categories should be in the map
        assertEquals(Integer.valueOf(1), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(2), result.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(1), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Setup: No artworks added to Emily Brown (Artist C)
        
        // Execute: Count artworks by category
        Map<ArtworkCategory, Integer> result = artistC.countArtworksByCategory();
        
        // Verify: There should be 0 artworks in all categories
        assertEquals(0, result.size()); // Map should be empty since no artworks
        assertNull(result.get(ArtworkCategory.PAINTING));
        assertNull(result.get(ArtworkCategory.SCULPTURE));
        assertNull(result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Setup: Add 3 sculptures and no other category to Michael Johnson (Artist D)
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        artwork1.setPrice(800.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(1200.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(1000.0);
        
        artistD.addArtwork(artwork1);
        artistD.addArtwork(artwork2);
        artistD.addArtwork(artwork3);
        
        // Execute: Count artworks by category
        Map<ArtworkCategory, Integer> result = artistD.countArtworksByCategory();
        
        // Verify: There should be 0 painting artwork, 3 sculpture artworks, and 0 architecture artwork
        assertEquals(1, result.size()); // Only sculpture category should be in the map
        assertNull(result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(3), result.get(ArtworkCategory.SCULPTURE));
        assertNull(result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Setup: Add artworks to Alice White (Artist E)
        Artwork artwork1E = new Artwork();
        artwork1E.setTitle("Landscapes");
        artwork1E.setCategory(ArtworkCategory.PAINTING);
        
        Artwork artwork2E = new Artwork();
        artwork2E.setTitle("Steel Bridge");
        artwork2E.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistE.addArtwork(artwork1E);
        artistE.addArtwork(artwork2E);
        
        // Setup: Add artworks to David Green (Artist F)
        Artwork artwork1F = new Artwork();
        artwork1F.setTitle("Marble Sculpture");
        artwork1F.setCategory(ArtworkCategory.SCULPTURE);
        
        Artwork artwork2F = new Artwork();
        artwork2F.setTitle("City Skyline");
        artwork2F.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistF.addArtwork(artwork1F);
        artistF.addArtwork(artwork2F);
        
        // Execute: Count artworks by category for both artists
        Map<ArtworkCategory, Integer> resultE = artistE.countArtworksByCategory();
        Map<ArtworkCategory, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify: For Alice White - 1 painting, 0 sculpture, 1 architecture
        assertEquals(2, resultE.size());
        assertEquals(Integer.valueOf(1), resultE.get(ArtworkCategory.PAINTING));
        assertNull(resultE.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(1), resultE.get(ArtworkCategory.ARCHITECTURE));
        
        // Verify: For David Green - 0 painting, 1 sculpture, 1 architecture
        assertEquals(2, resultF.size());
        assertNull(resultF.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(1), resultF.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(1), resultF.get(ArtworkCategory.ARCHITECTURE));
    }
}