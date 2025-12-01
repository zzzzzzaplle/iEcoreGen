import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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
        artistA.setGender(Gender.MALE);
        
        // Add 3 artworks: 2 paintings, 1 sculpture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(200.0);
        artistA.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setPrice(150.0);
        artistA.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(500.0);
        artistA.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artistA.countArtworksByCategory();
        
        // Verify expected counts: 2 paintings, 1 sculpture, 0 architecture
        assertEquals(Integer.valueOf(2), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(1), result.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Set up Artist B (Jane Smith)
        artistB.setName("Jane Smith");
        artistB.setId("A002");
        artistB.setPhoneNumber("9876543210");
        artistB.setEmail("jane.smith@example.com");
        artistB.setAddress("456 Art Ave");
        artistB.setGender(Gender.FEMALE);
        
        // Add 4 artworks: 1 painting, 2 sculptures, 1 architecture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(300.0);
        artistB.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(700.0);
        artistB.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(600.0);
        artistB.addArtwork(artwork3);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setPrice(900.0);
        artistB.addArtwork(artwork4);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artistB.countArtworksByCategory();
        
        // Verify expected counts: 1 painting, 2 sculptures, 1 architecture
        assertEquals(Integer.valueOf(1), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(2), result.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(1), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Set up Artist C (Emily Brown)
        artistC.setName("Emily Brown");
        artistC.setId("A003");
        artistC.setPhoneNumber("1112223333");
        artistC.setEmail("emily.brown@example.com");
        artistC.setAddress("789 Art Blvd");
        artistC.setGender(Gender.FEMALE);
        
        // No artworks added to Emily Brown
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artistC.countArtworksByCategory();
        
        // Verify expected counts: 0 for all categories
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Set up Artist D (Michael Johnson)
        artistD.setName("Michael Johnson");
        artistD.setId("A004");
        artistD.setPhoneNumber("4445556666");
        artistD.setEmail("michael.johnson@example.com");
        artistD.setAddress("123 Art Lane");
        artistD.setGender(Gender.MALE);
        
        // Add 3 sculptures only
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        artwork1.setPrice(800.0);
        artistD.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(1200.0);
        artistD.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(1000.0);
        artistD.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artistD.countArtworksByCategory();
        
        // Verify expected counts: 0 paintings, 3 sculptures, 0 architecture
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(3), result.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Set up Artist E (Alice White)
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        // Add artworks to Artist E: 1 painting, 1 architecture
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory(ArtworkCategory.PAINTING);
        artistE.addArtwork(artworkE1);
        
        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory(ArtworkCategory.ARCHITECTURE);
        artistE.addArtwork(artworkE2);
        
        // Set up Artist F (David Green)
        artistF.setName("David Green");
        artistF.setId("A006");
        
        // Add artworks to Artist F: 1 sculpture, 1 architecture
        Artwork artworkF1 = new Artwork();
        artworkF1.setTitle("Marble Sculpture");
        artworkF1.setCategory(ArtworkCategory.SCULPTURE);
        artistF.addArtwork(artworkF1);
        
        Artwork artworkF2 = new Artwork();
        artworkF2.setTitle("City Skyline");
        artworkF2.setCategory(ArtworkCategory.ARCHITECTURE);
        artistF.addArtwork(artworkF2);
        
        // Count artworks by category for Artist E
        Map<ArtworkCategory, Integer> resultE = artistE.countArtworksByCategory();
        
        // Verify expected counts for Artist E: 1 painting, 0 sculpture, 1 architecture
        assertEquals(Integer.valueOf(1), resultE.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(0), resultE.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(1), resultE.get(ArtworkCategory.ARCHITECTURE));
        
        // Count artworks by category for Artist F
        Map<ArtworkCategory, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify expected counts for Artist F: 0 painting, 1 sculpture, 1 architecture
        assertEquals(Integer.valueOf(0), resultF.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(1), resultF.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(1), resultF.get(ArtworkCategory.ARCHITECTURE));
    }
}