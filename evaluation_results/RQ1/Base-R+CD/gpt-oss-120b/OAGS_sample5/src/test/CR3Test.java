import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.EnumMap;

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
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Test Case 1: Count Artworks by Category for a Single Artist
        // SetUp: Add 3 artworks to John Doe (2 paintings, 1 sculpture)
        
        // Create and add artwork 1: painting
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(200.0);
        artistA.addArtwork(artwork1);
        
        // Create and add artwork 2: painting
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setPrice(150.0);
        artistA.addArtwork(artwork2);
        
        // Create and add artwork 3: sculpture
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(500.0);
        artistA.addArtwork(artwork3);
        
        // Execute the method under test
        Map<ArtworkCategory, Integer> result = artistA.countArtworksByCategory();
        
        // Verify expected output: 2 paintings, 1 sculpture, 0 architecture
        assertEquals("Painting count should be 2", Integer.valueOf(2), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Test Case 2: Count Artworks by Category with Multiple Categories
        // SetUp: Add 1 painting, 2 sculptures, and 1 architecture to Jane Smith
        
        // Create and add artwork 1: painting
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(300.0);
        artistB.addArtwork(artwork1);
        
        // Create and add artwork 2: sculpture
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(700.0);
        artistB.addArtwork(artwork2);
        
        // Create and add artwork 3: sculpture
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(600.0);
        artistB.addArtwork(artwork3);
        
        // Create and add artwork 4: architecture
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setPrice(900.0);
        artistB.addArtwork(artwork4);
        
        // Execute the method under test
        Map<ArtworkCategory, Integer> result = artistB.countArtworksByCategory();
        
        // Verify expected output: 1 painting, 2 sculptures, 1 architecture
        assertEquals("Painting count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 2", Integer.valueOf(2), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Test Case 3: No Artworks for an Artist
        // SetUp: Emily Brown has no artworks
        
        // Execute the method under test
        Map<ArtworkCategory, Integer> result = artistC.countArtworksByCategory();
        
        // Verify expected output: 0 for all categories
        assertEquals("Painting count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Test Case 4: Count Artworks with Only One Category
        // SetUp: Add 3 sculptures to Michael Johnson
        
        // Create and add artwork 1: sculpture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        artwork1.setPrice(800.0);
        artistD.addArtwork(artwork1);
        
        // Create and add artwork 2: sculpture
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(1200.0);
        artistD.addArtwork(artwork2);
        
        // Create and add artwork 3: sculpture
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(1000.0);
        artistD.addArtwork(artwork3);
        
        // Execute the method under test
        Map<ArtworkCategory, Integer> result = artistD.countArtworksByCategory();
        
        // Verify expected output: 0 paintings, 3 sculptures, 0 architecture
        assertEquals("Painting count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 3", Integer.valueOf(3), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Test Case 5: Count Artworks with Multiple Artists
        // SetUp: Add artworks to Alice White and David Green
        
        // Add artworks to Alice White
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Landscapes");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artistE.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Steel Bridge");
        artwork2.setCategory(ArtworkCategory.ARCHITECTURE);
        artistE.addArtwork(artwork2);
        
        // Add artworks to David Green
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Marble Sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artistF.addArtwork(artwork3);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("City Skyline");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artistF.addArtwork(artwork4);
        
        // Execute the method under test for Alice White
        Map<ArtworkCategory, Integer> resultE = artistE.countArtworksByCategory();
        
        // Verify expected output for Alice White: 1 painting, 0 sculpture, 1 architecture
        assertEquals("Alice White - Painting count should be 1", Integer.valueOf(1), resultE.get(ArtworkCategory.PAINTING));
        assertEquals("Alice White - Sculpture count should be 0", Integer.valueOf(0), resultE.get(ArtworkCategory.SCULPTURE));
        assertEquals("Alice White - Architecture count should be 1", Integer.valueOf(1), resultE.get(ArtworkCategory.ARCHITECTURE));
        
        // Execute the method under test for David Green
        Map<ArtworkCategory, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify expected output for David Green: 0 painting, 1 sculpture, 1 architecture
        assertEquals("David Green - Painting count should be 0", Integer.valueOf(0), resultF.get(ArtworkCategory.PAINTING));
        assertEquals("David Green - Sculpture count should be 1", Integer.valueOf(1), resultF.get(ArtworkCategory.SCULPTURE));
        assertEquals("David Green - Architecture count should be 1", Integer.valueOf(1), resultF.get(ArtworkCategory.ARCHITECTURE));
    }
}