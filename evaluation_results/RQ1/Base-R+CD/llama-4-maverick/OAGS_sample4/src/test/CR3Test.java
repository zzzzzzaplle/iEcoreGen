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
    public void testCase1_countArtworksByCategoryForSingleArtist() {
        // Test Case 1: Count Artworks by Category for a Single Artist
        // SetUp: Add 3 artworks to John Doe (2 paintings, 1 sculpture)
        
        // Create and add artwork 1: Sunset (painting)
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(200.0);
        artistA.addArtwork(artwork1);
        
        // Create and add artwork 2: Still Life (painting)
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setPrice(150.0);
        artistA.addArtwork(artwork2);
        
        // Create and add artwork 3: Bronze Statue (sculpture)
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(500.0);
        artistA.addArtwork(artwork3);
        
        // Execute: Count artworks by category
        Map<ArtworkCategory, Integer> result = artistA.countArtworksByCategory();
        
        // Verify expected output: 2 paintings, 1 sculpture, 0 architecture
        assertEquals("Painting count should be 2", Integer.valueOf(2), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.SCULPTURE));
        
        // Check architecture count (should be null if no artworks in this category)
        assertNull("Architecture count should be null (no artworks)", result.get(ArtworkCategory.ARCHITECTURE));
        
        // Verify total number of categories in result
        assertEquals("Result map should contain 2 categories", 2, result.size());
    }
    
    @Test
    public void testCase2_countArtworksByCategoryWithMultipleCategories() {
        // Test Case 2: Count Artworks by Category with Multiple Categories
        // SetUp: Add 1 painting, 2 sculptures, and 1 architecture to Jane Smith
        
        // Create and add artwork 1: Abstract Colors (painting)
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(300.0);
        artistB.addArtwork(artwork1);
        
        // Create and add artwork 2: David (sculpture)
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(700.0);
        artistB.addArtwork(artwork2);
        
        // Create and add artwork 3: Classic Statue (sculpture)
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(600.0);
        artistB.addArtwork(artwork3);
        
        // Create and add artwork 4: Skyscraper (architecture)
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setPrice(900.0);
        artistB.addArtwork(artwork4);
        
        // Execute: Count artworks by category
        Map<ArtworkCategory, Integer> result = artistB.countArtworksByCategory();
        
        // Verify expected output: 1 painting, 2 sculptures, 1 architecture
        assertEquals("Painting count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 2", Integer.valueOf(2), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.ARCHITECTURE));
        
        // Verify total number of categories in result
        assertEquals("Result map should contain 3 categories", 3, result.size());
    }
    
    @Test
    public void testCase3_noArtworksForAnArtist() {
        // Test Case 3: No Artworks for an Artist
        // SetUp: Emily Brown has no artworks
        
        // Execute: Count artworks by category for artist with no artworks
        Map<ArtworkCategory, Integer> result = artistC.countArtworksByCategory();
        
        // Verify expected output: Empty map (0 paintings, 0 sculptures, 0 architecture)
        assertTrue("Result map should be empty for artist with no artworks", result.isEmpty());
        
        // Verify individual categories are not present in the result
        assertNull("Painting count should be null (no artworks)", result.get(ArtworkCategory.PAINTING));
        assertNull("Sculpture count should be null (no artworks)", result.get(ArtworkCategory.SCULPTURE));
        assertNull("Architecture count should be null (no artworks)", result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase4_countArtworksWithOnlyOneCategory() {
        // Test Case 4: Count Artworks with Only One Category
        // SetUp: Add 3 sculptures to Michael Johnson (no other categories)
        
        // Create and add artwork 1: Modern Art (sculpture)
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        artwork1.setPrice(800.0);
        artistD.addArtwork(artwork1);
        
        // Create and add artwork 2: Wooden Carving (sculpture)
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(1200.0);
        artistD.addArtwork(artwork2);
        
        // Create and add artwork 3: Stone Figure (sculpture)
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(1000.0);
        artistD.addArtwork(artwork3);
        
        // Execute: Count artworks by category
        Map<ArtworkCategory, Integer> result = artistD.countArtworksByCategory();
        
        // Verify expected output: 0 paintings, 3 sculptures, 0 architecture
        assertNull("Painting count should be null (no paintings)", result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 3", Integer.valueOf(3), result.get(ArtworkCategory.SCULPTURE));
        assertNull("Architecture count should be null (no architecture)", result.get(ArtworkCategory.ARCHITECTURE));
        
        // Verify total number of categories in result
        assertEquals("Result map should contain 1 category", 1, result.size());
    }
    
    @Test
    public void testCase5_countArtworksWithMultipleArtists() {
        // Test Case 5: Count Artworks with Multiple Artists
        // SetUp: Create two artists with different artwork distributions
        
        // Set up Artist E (Alice White)
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory(ArtworkCategory.PAINTING);
        artistE.addArtwork(artworkE1);
        
        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory(ArtworkCategory.ARCHITECTURE);
        artistE.addArtwork(artworkE2);
        
        // Set up Artist F (David Green)
        Artwork artworkF1 = new Artwork();
        artworkF1.setTitle("Marble Sculpture");
        artworkF1.setCategory(ArtworkCategory.SCULPTURE);
        artistF.addArtwork(artworkF1);
        
        Artwork artworkF2 = new Artwork();
        artworkF2.setTitle("City Skyline");
        artworkF2.setCategory(ArtworkCategory.ARCHITECTURE);
        artistF.addArtwork(artworkF2);
        
        // Execute: Count artworks by category for both artists
        Map<ArtworkCategory, Integer> resultE = artistE.countArtworksByCategory();
        Map<ArtworkCategory, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify expected output for Alice White: 1 painting, 0 sculpture, 1 architecture
        assertEquals("Alice White painting count should be 1", Integer.valueOf(1), resultE.get(ArtworkCategory.PAINTING));
        assertNull("Alice White sculpture count should be null (no sculptures)", resultE.get(ArtworkCategory.SCULPTURE));
        assertEquals("Alice White architecture count should be 1", Integer.valueOf(1), resultE.get(ArtworkCategory.ARCHITECTURE));
        
        // Verify expected output for David Green: 0 paintings, 1 sculpture, 1 architecture
        assertNull("David Green painting count should be null (no paintings)", resultF.get(ArtworkCategory.PAINTING));
        assertEquals("David Green sculpture count should be 1", Integer.valueOf(1), resultF.get(ArtworkCategory.SCULPTURE));
        assertEquals("David Green architecture count should be 1", Integer.valueOf(1), resultF.get(ArtworkCategory.ARCHITECTURE));
        
        // Verify total number of categories in each result
        assertEquals("Alice White result map should contain 2 categories", 2, resultE.size());
        assertEquals("David Green result map should contain 2 categories", 2, resultF.size());
    }
}