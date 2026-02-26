import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class CR3Test {
    private Artist artistA;
    private Artist artistB;
    private Artist artistC;
    private Artist artistD;
    private Artist artistE;
    private Artist artistF;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Set up Artist A (John Doe)
        artistA = new Artist();
        artistA.setName("John Doe");
        artistA.setId("A001");
        artistA.setPhoneNumber("1234567890");
        artistA.setEmail("john.doe@example.com");
        artistA.setAddress("123 Art St");
        artistA.setGender(Gender.MALE);
        
        Artwork artwork1A = new Artwork();
        artwork1A.setTitle("Sunset");
        artwork1A.setDescription("A beautiful sunset painting");
        artwork1A.setCategory(ArtworkCategory.PAINTING);
        artwork1A.setPrice(200.0);
        
        Artwork artwork2A = new Artwork();
        artwork2A.setTitle("Still Life");
        artwork2A.setDescription("A still life composition");
        artwork2A.setCategory(ArtworkCategory.PAINTING);
        artwork2A.setPrice(150.0);
        
        Artwork artwork3A = new Artwork();
        artwork3A.setTitle("Bronze Statue");
        artwork3A.setDescription("A bronze statue of a horse");
        artwork3A.setCategory(ArtworkCategory.SCULPTURE);
        artwork3A.setPrice(500.0);
        
        artistA.addArtwork(artwork1A);
        artistA.addArtwork(artwork2A);
        artistA.addArtwork(artwork3A);
        
        // Set up Artist B (Jane Smith)
        artistB = new Artist();
        artistB.setName("Jane Smith");
        artistB.setId("A002");
        artistB.setPhoneNumber("9876543210");
        artistB.setEmail("jane.smith@example.com");
        artistB.setAddress("456 Art Ave");
        artistB.setGender(Gender.FEMALE);
        
        Artwork artwork1B = new Artwork();
        artwork1B.setTitle("Abstract Colors");
        artwork1B.setDescription("An abstract painting");
        artwork1B.setCategory(ArtworkCategory.PAINTING);
        artwork1B.setPrice(300.0);
        
        Artwork artwork2B = new Artwork();
        artwork2B.setTitle("David");
        artwork2B.setDescription("A sculpture of David");
        artwork2B.setCategory(ArtworkCategory.SCULPTURE);
        artwork2B.setPrice(700.0);
        
        Artwork artwork3B = new Artwork();
        artwork3B.setTitle("Classic Statue");
        artwork3B.setDescription("A classic statue");
        artwork3B.setCategory(ArtworkCategory.SCULPTURE);
        artwork3B.setPrice(600.0);
        
        Artwork artwork4B = new Artwork();
        artwork4B.setTitle("Skyscraper");
        artwork4B.setDescription("Modern architecture");
        artwork4B.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4B.setPrice(900.0);
        
        artistB.addArtwork(artwork1B);
        artistB.addArtwork(artwork2B);
        artistB.addArtwork(artwork3B);
        artistB.addArtwork(artwork4B);
        
        // Set up Artist C (Emily Brown) - no artworks
        artistC = new Artist();
        artistC.setName("Emily Brown");
        artistC.setId("A003");
        artistC.setPhoneNumber("1112223333");
        artistC.setEmail("emily.brown@example.com");
        artistC.setAddress("789 Art Blvd");
        artistC.setGender(Gender.FEMALE);
        
        // Set up Artist D (Michael Johnson) - only sculptures
        artistD = new Artist();
        artistD.setName("Michael Johnson");
        artistD.setId("A004");
        artistD.setPhoneNumber("4445556666");
        artistD.setEmail("michael.johnson@example.com");
        artistD.setAddress("123 Art Lane");
        artistD.setGender(Gender.MALE);
        
        Artwork artwork1D = new Artwork();
        artwork1D.setTitle("Modern Art");
        artwork1D.setDescription("A modern sculpture");
        artwork1D.setCategory(ArtworkCategory.SCULPTURE);
        artwork1D.setPrice(800.0);
        
        Artwork artwork2D = new Artwork();
        artwork2D.setTitle("Wooden Carving");
        artwork2D.setDescription("A wooden sculpture");
        artwork2D.setCategory(ArtworkCategory.SCULPTURE);
        artwork2D.setPrice(1200.0);
        
        Artwork artwork3D = new Artwork();
        artwork3D.setTitle("Stone Figure");
        artwork3D.setDescription("A stone sculpture");
        artwork3D.setCategory(ArtworkCategory.SCULPTURE);
        artwork3D.setPrice(1000.0);
        
        artistD.addArtwork(artwork1D);
        artistD.addArtwork(artwork2D);
        artistD.addArtwork(artwork3D);
        
        // Set up Artist E (Alice White)
        artistE = new Artist();
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        Artwork artwork1E = new Artwork();
        artwork1E.setTitle("Landscapes");
        artwork1E.setCategory(ArtworkCategory.PAINTING);
        
        Artwork artwork2E = new Artwork();
        artwork2E.setTitle("Steel Bridge");
        artwork2E.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistE.addArtwork(artwork1E);
        artistE.addArtwork(artwork2E);
        
        // Set up Artist F (David Green)
        artistF = new Artist();
        artistF.setName("David Green");
        artistF.setId("A006");
        
        Artwork artwork1F = new Artwork();
        artwork1F.setTitle("Marble Sculpture");
        artwork1F.setCategory(ArtworkCategory.SCULPTURE);
        
        Artwork artwork2F = new Artwork();
        artwork2F.setTitle("City Skyline");
        artwork2F.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistF.addArtwork(artwork1F);
        artistF.addArtwork(artwork2F);
    }
    
    @Test
    public void testCase1_countArtworksByCategoryForSingleArtist() {
        // Count artworks for Artist A (John Doe)
        Map<ArtworkCategory, Integer> result = artistA.countArtworksByCategory();
        
        // Verify expected output: 2 painting artworks, 1 sculpture artwork, and 0 architecture artwork
        assertEquals(Integer.valueOf(2), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(1), result.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase2_countArtworksByCategoryWithMultipleCategories() {
        // Count artworks for Artist B (Jane Smith)
        Map<ArtworkCategory, Integer> result = artistB.countArtworksByCategory();
        
        // Verify expected output: 1 painting artwork, 2 sculpture artworks, and 1 architecture artwork
        assertEquals(Integer.valueOf(1), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(2), result.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(1), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_noArtworksForAnArtist() {
        // Count artworks for Artist C (Emily Brown) - no artworks
        Map<ArtworkCategory, Integer> result = artistC.countArtworksByCategory();
        
        // Verify expected output: 0 painting artwork, 0 sculpture artwork, and 0 architecture artwork
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase4_countArtworksWithOnlyOneCategory() {
        // Count artworks for Artist D (Michael Johnson) - only sculptures
        Map<ArtworkCategory, Integer> result = artistD.countArtworksByCategory();
        
        // Verify expected output: 0 painting artwork, 3 sculpture artworks, and 0 architecture artwork
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(3), result.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_countArtworksWithMultipleArtists() {
        // Count artworks for Artist E (Alice White)
        Map<ArtworkCategory, Integer> resultE = artistE.countArtworksByCategory();
        
        // Verify expected output for Alice White: 1 painting artwork, 0 sculpture artwork, and 1 architecture artwork
        assertEquals(Integer.valueOf(1), resultE.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(0), resultE.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(1), resultE.get(ArtworkCategory.ARCHITECTURE));
        
        // Count artworks for Artist F (David Green)
        Map<ArtworkCategory, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify expected output for David Green: 0 painting artworks, 1 sculpture artwork, and 1 architecture artwork
        assertEquals(Integer.valueOf(0), resultF.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(1), resultF.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(1), resultF.get(ArtworkCategory.ARCHITECTURE));
    }
}