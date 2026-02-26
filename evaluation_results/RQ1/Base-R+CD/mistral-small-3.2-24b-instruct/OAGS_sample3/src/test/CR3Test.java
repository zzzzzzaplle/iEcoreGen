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
        
        // Initialize artists
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
        // Set up artworks for Artist A (John Doe)
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

        // Verify expected counts
        assertEquals("Painting count should be 2", Integer.valueOf(2), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }

    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Set up artworks for Artist B (Jane Smith)
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

        // Verify expected counts
        assertEquals("Painting count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 2", Integer.valueOf(2), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.ARCHITECTURE));
    }

    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Artist C (Emily Brown) has no artworks added

        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artistC.countArtworksByCategory();

        // Verify expected counts (all should be 0)
        assertEquals("Painting count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }

    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Set up artworks for Artist D (Michael Johnson) - only sculptures
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

        // Verify expected counts
        assertEquals("Painting count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 3", Integer.valueOf(3), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }

    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Set up artworks for Artist E (Alice White)
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory(ArtworkCategory.PAINTING);
        artistE.addArtwork(artworkE1);

        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory(ArtworkCategory.ARCHITECTURE);
        artistE.addArtwork(artworkE2);

        // Set up artworks for Artist F (David Green)
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
        assertEquals("Artist E - Painting count should be 1", Integer.valueOf(1), resultE.get(ArtworkCategory.PAINTING));
        assertEquals("Artist E - Sculpture count should be 0", Integer.valueOf(0), resultE.get(ArtworkCategory.SCULPTURE));
        assertEquals("Artist E - Architecture count should be 1", Integer.valueOf(1), resultE.get(ArtworkCategory.ARCHITECTURE));

        // Count artworks by category for Artist F
        Map<ArtworkCategory, Integer> resultF = artistF.countArtworksByCategory();
        assertEquals("Artist F - Painting count should be 0", Integer.valueOf(0), resultF.get(ArtworkCategory.PAINTING));
        assertEquals("Artist F - Sculpture count should be 1", Integer.valueOf(1), resultF.get(ArtworkCategory.SCULPTURE));
        assertEquals("Artist F - Architecture count should be 1", Integer.valueOf(1), resultF.get(ArtworkCategory.ARCHITECTURE));
    }
}