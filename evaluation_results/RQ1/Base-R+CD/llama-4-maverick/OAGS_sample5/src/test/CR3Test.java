import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.HashMap;

public class CR3Test {
    private Artist artistA;
    private Artist artistB;
    private Artist artistC;
    private Artist artistD;
    private Artist artistE;
    private Artist artistF;
    
    @Before
    public void setUp() {
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
        // Set up for Artist A (John Doe)
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(200);
        artistA.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setPrice(150);
        artistA.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(500);
        artistA.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artistA.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals(2, (int) result.getOrDefault(ArtworkCategory.PAINTING, 0));
        assertEquals(1, (int) result.getOrDefault(ArtworkCategory.SCULPTURE, 0));
        assertEquals(0, (int) result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Set up for Artist B (Jane Smith)
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(300);
        artistB.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(700);
        artistB.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(600);
        artistB.addArtwork(artwork3);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setPrice(900);
        artistB.addArtwork(artwork4);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artistB.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals(1, (int) result.getOrDefault(ArtworkCategory.PAINTING, 0));
        assertEquals(2, (int) result.getOrDefault(ArtworkCategory.SCULPTURE, 0));
        assertEquals(1, (int) result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Artist C (Emily Brown) has no artworks set up
        Map<ArtworkCategory, Integer> result = artistC.countArtworksByCategory();
        
        // Verify all categories have 0 count
        assertEquals(0, (int) result.getOrDefault(ArtworkCategory.PAINTING, 0));
        assertEquals(0, (int) result.getOrDefault(ArtworkCategory.SCULPTURE, 0));
        assertEquals(0, (int) result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Set up for Artist D (Michael Johnson) - only sculptures
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        artwork1.setPrice(800);
        artistD.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(1200);
        artistD.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(1000);
        artistD.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artistD.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals(0, (int) result.getOrDefault(ArtworkCategory.PAINTING, 0));
        assertEquals(3, (int) result.getOrDefault(ArtworkCategory.SCULPTURE, 0));
        assertEquals(0, (int) result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Set up for Artist E (Alice White)
        Artwork artwork1E = new Artwork();
        artwork1E.setTitle("Landscapes");
        artwork1E.setCategory(ArtworkCategory.PAINTING);
        artistE.addArtwork(artwork1E);
        
        Artwork artwork2E = new Artwork();
        artwork2E.setTitle("Steel Bridge");
        artwork2E.setCategory(ArtworkCategory.ARCHITECTURE);
        artistE.addArtwork(artwork2E);
        
        // Set up for Artist F (David Green)
        Artwork artwork1F = new Artwork();
        artwork1F.setTitle("Marble Sculpture");
        artwork1F.setCategory(ArtworkCategory.SCULPTURE);
        artistF.addArtwork(artwork1F);
        
        Artwork artwork2F = new Artwork();
        artwork2F.setTitle("City Skyline");
        artwork2F.setCategory(ArtworkCategory.ARCHITECTURE);
        artistF.addArtwork(artwork2F);
        
        // Count artworks by category for Artist E
        Map<ArtworkCategory, Integer> resultE = artistE.countArtworksByCategory();
        assertEquals(1, (int) resultE.getOrDefault(ArtworkCategory.PAINTING, 0));
        assertEquals(0, (int) resultE.getOrDefault(ArtworkCategory.SCULPTURE, 0));
        assertEquals(1, (int) resultE.getOrDefault(ArtworkCategory.ARCHITECTURE, 0));
        
        // Count artworks by category for Artist F
        Map<ArtworkCategory, Integer> resultF = artistF.countArtworksByCategory();
        assertEquals(0, (int) resultF.getOrDefault(ArtworkCategory.PAINTING, 0));
        assertEquals(1, (int) resultF.getOrDefault(ArtworkCategory.SCULPTURE, 0));
        assertEquals(1, (int) resultF.getOrDefault(ArtworkCategory.ARCHITECTURE, 0));
    }
}