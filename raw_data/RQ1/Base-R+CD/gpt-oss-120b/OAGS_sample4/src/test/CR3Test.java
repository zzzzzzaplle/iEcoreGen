import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
import java.text.SimpleDateFormat;

public class CR3Test {
    
    private Artist artistA;
    private Artist artistB;
    private Artist artistC;
    private Artist artistD;
    private Artist artistE;
    private Artist artistF;
    
    @Before
    public void setUp() throws Exception {
        // Initialize all artists for test cases
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
        // SetUp: Add 3 artworks to Artist A (John Doe)
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
        
        // Verify: There are 2 painting artworks, 1 sculpture artwork, and 0 architecture artwork
        assertEquals(2, result.getOrDefault(ArtworkCategory.PAINTING, 0).intValue());
        assertEquals(1, result.getOrDefault(ArtworkCategory.SCULPTURE, 0).intValue());
        assertEquals(0, result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0).intValue());
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // SetUp: Add 1 painting, 2 sculptures, and 1 architecture to Artist B (Jane Smith)
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
        
        // Verify: There are 1 painting artwork, 2 sculpture artworks, and 1 architecture artwork
        assertEquals(1, result.getOrDefault(ArtworkCategory.PAINTING, 0).intValue());
        assertEquals(2, result.getOrDefault(ArtworkCategory.SCULPTURE, 0).intValue());
        assertEquals(1, result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0).intValue());
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // SetUp: Artist C (Emily Brown) has no artworks (already set in @Before)
        
        // Execute: Count artworks by category
        Map<ArtworkCategory, Integer> result = artistC.countArtworksByCategory();
        
        // Verify: There are 0 painting artwork, 0 sculpture artwork, and 0 architecture artwork
        assertEquals(0, result.getOrDefault(ArtworkCategory.PAINTING, 0).intValue());
        assertEquals(0, result.getOrDefault(ArtworkCategory.SCULPTURE, 0).intValue());
        assertEquals(0, result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0).intValue());
        assertTrue("Result map should be empty when no artworks exist", result.isEmpty());
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // SetUp: Add 3 sculptures and no other category to Artist D (Michael Johnson)
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
        
        // Verify: There are 0 painting artwork, 3 sculpture artworks, and 0 architecture artwork
        assertEquals(0, result.getOrDefault(ArtworkCategory.PAINTING, 0).intValue());
        assertEquals(3, result.getOrDefault(ArtworkCategory.SCULPTURE, 0).intValue());
        assertEquals(0, result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0).intValue());
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // SetUp: Add artworks to Artist E (Alice White)
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory(ArtworkCategory.PAINTING);
        
        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistE.addArtwork(artworkE1);
        artistE.addArtwork(artworkE2);
        
        // SetUp: Add artworks to Artist F (David Green)
        Artwork artworkF1 = new Artwork();
        artworkF1.setTitle("Marble Sculpture");
        artworkF1.setCategory(ArtworkCategory.SCULPTURE);
        
        Artwork artworkF2 = new Artwork();
        artworkF2.setTitle("City Skyline");
        artworkF2.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistF.addArtwork(artworkF1);
        artistF.addArtwork(artworkF2);
        
        // Execute: Count artworks by category for both artists
        Map<ArtworkCategory, Integer> resultE = artistE.countArtworksByCategory();
        Map<ArtworkCategory, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify: For Alice White - 1 painting, 0 sculpture, 1 architecture
        assertEquals(1, resultE.getOrDefault(ArtworkCategory.PAINTING, 0).intValue());
        assertEquals(0, resultE.getOrDefault(ArtworkCategory.SCULPTURE, 0).intValue());
        assertEquals(1, resultE.getOrDefault(ArtworkCategory.ARCHITECTURE, 0).intValue());
        
        // Verify: For David Green - 0 painting, 1 sculpture, 1 architecture
        assertEquals(0, resultF.getOrDefault(ArtworkCategory.PAINTING, 0).intValue());
        assertEquals(1, resultF.getOrDefault(ArtworkCategory.SCULPTURE, 0).intValue());
        assertEquals(1, resultF.getOrDefault(ArtworkCategory.ARCHITECTURE, 0).intValue());
    }
}