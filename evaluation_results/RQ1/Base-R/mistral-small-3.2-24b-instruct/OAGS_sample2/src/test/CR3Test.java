import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
        artistA.setGender("Male");
        
        artistB = new Artist();
        artistB.setName("Jane Smith");
        artistB.setId("A002");
        artistB.setPhoneNumber("9876543210");
        artistB.setEmail("jane.smith@example.com");
        artistB.setAddress("456 Art Ave");
        artistB.setGender("Female");
        
        artistC = new Artist();
        artistC.setName("Emily Brown");
        artistC.setId("A003");
        artistC.setPhoneNumber("1112223333");
        artistC.setEmail("emily.brown@example.com");
        artistC.setAddress("789 Art Blvd");
        artistC.setGender("Female");
        
        artistD = new Artist();
        artistD.setName("Michael Johnson");
        artistD.setId("A004");
        artistD.setPhoneNumber("4445556666");
        artistD.setEmail("michael.johnson@example.com");
        artistD.setAddress("123 Art Lane");
        artistD.setGender("Male");
        
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
        
        artistA.getArtworks().add(artwork1);
        artistA.getArtworks().add(artwork2);
        artistA.getArtworks().add(artwork3);
        
        // Execute: Count artworks by category
        Map<ArtworkCategory, Integer> result = artistA.countArtworksByCategory();
        
        // Verify: Expected output - 2 paintings, 1 sculpture, 0 architecture
        assertEquals("Should have 2 painting artworks", Integer.valueOf(2), result.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 1 sculpture artwork", Integer.valueOf(1), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artworks", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Test Case 2: Count Artworks by Category with Multiple Categories
        // SetUp: Add 1 painting, 2 sculptures, and 1 architecture to Jane Smith
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
        
        artistB.getArtworks().add(artwork1);
        artistB.getArtworks().add(artwork2);
        artistB.getArtworks().add(artwork3);
        artistB.getArtworks().add(artwork4);
        
        // Execute: Count artworks by category
        Map<ArtworkCategory, Integer> result = artistB.countArtworksByCategory();
        
        // Verify: Expected output - 1 painting, 2 sculptures, 1 architecture
        assertEquals("Should have 1 painting artwork", Integer.valueOf(1), result.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 2 sculpture artworks", Integer.valueOf(2), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 1 architecture artwork", Integer.valueOf(1), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Test Case 3: No Artworks for an Artist
        // SetUp: Emily Brown has no artworks (already set in setUp)
        
        // Execute: Count artworks by category
        Map<ArtworkCategory, Integer> result = artistC.countArtworksByCategory();
        
        // Verify: Expected output - 0 painting, 0 sculpture, 0 architecture
        assertEquals("Should have 0 painting artworks", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 0 sculpture artworks", Integer.valueOf(0), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artworks", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Test Case 4: Count Artworks with Only One Category
        // SetUp: Add 3 sculptures and no other category to Michael Johnson
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
        
        artistD.getArtworks().add(artwork1);
        artistD.getArtworks().add(artwork2);
        artistD.getArtworks().add(artwork3);
        
        // Execute: Count artworks by category
        Map<ArtworkCategory, Integer> result = artistD.countArtworksByCategory();
        
        // Verify: Expected output - 0 painting, 3 sculptures, 0 architecture
        assertEquals("Should have 0 painting artworks", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 3 sculpture artworks", Integer.valueOf(3), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artworks", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Test Case 5: Count Artworks with Multiple Artists
        // SetUp: Add artworks to Alice White (Artist E)
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Landscapes");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Steel Bridge");
        artwork2.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistE.getArtworks().add(artwork1);
        artistE.getArtworks().add(artwork2);
        
        // SetUp: Add artworks to David Green (Artist F)
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Marble Sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("City Skyline");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistF.getArtworks().add(artwork3);
        artistF.getArtworks().add(artwork4);
        
        // Execute: Count artworks by category for Alice White
        Map<ArtworkCategory, Integer> resultE = artistE.countArtworksByCategory();
        
        // Verify: Expected output for Alice White - 1 painting, 0 sculpture, 1 architecture
        assertEquals("Alice White should have 1 painting artwork", Integer.valueOf(1), resultE.get(ArtworkCategory.PAINTING));
        assertEquals("Alice White should have 0 sculpture artworks", Integer.valueOf(0), resultE.get(ArtworkCategory.SCULPTURE));
        assertEquals("Alice White should have 1 architecture artwork", Integer.valueOf(1), resultE.get(ArtworkCategory.ARCHITECTURE));
        
        // Execute: Count artworks by category for David Green
        Map<ArtworkCategory, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify: Expected output for David Green - 0 painting, 1 sculpture, 1 architecture
        assertEquals("David Green should have 0 painting artworks", Integer.valueOf(0), resultF.get(ArtworkCategory.PAINTING));
        assertEquals("David Green should have 1 sculpture artwork", Integer.valueOf(1), resultF.get(ArtworkCategory.SCULPTURE));
        assertEquals("David Green should have 1 architecture artwork", Integer.valueOf(1), resultF.get(ArtworkCategory.ARCHITECTURE));
    }
}