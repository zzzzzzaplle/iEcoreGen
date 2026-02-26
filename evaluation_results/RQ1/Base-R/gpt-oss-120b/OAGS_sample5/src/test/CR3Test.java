import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class CR3Test {
    
    private Artist artistA;
    private Artist artistB;
    private Artist artistC;
    private Artist artistD;
    private Artist artistE;
    private Artist artistF;
    
    @Before
    public void setUp() {
        // Initialize all artists once for reuse across tests
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
        // SetUp: Add 3 artworks to John Doe (Artist A)
        List<Artwork> artworksA = new ArrayList<>();
        
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(Category.PAINTING);
        artwork1.setPrice(200.0);
        artworksA.add(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(Category.PAINTING);
        artwork2.setPrice(150.0);
        artworksA.add(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(500.0);
        artworksA.add(artwork3);
        
        artistA.setArtworks(artworksA);
        
        // Execute: Count artworks by category
        Map<Category, Long> result = artistA.countArtworksByCategory();
        
        // Verify: There are 2 painting artworks, 1 sculpture artwork, and 0 architecture artwork
        assertEquals("Should have 2 paintings", Long.valueOf(2), result.get(Category.PAINTING));
        assertEquals("Should have 1 sculpture", Long.valueOf(1), result.get(Category.SCULPTURE));
        assertNull("Should not have architecture in the map", result.get(Category.ARCHITECTURE));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // SetUp: Add 1 painting, 2 sculptures, and 1 architecture to Jane Smith (Artist B)
        List<Artwork> artworksB = new ArrayList<>();
        
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(Category.PAINTING);
        artwork1.setPrice(300.0);
        artworksB.add(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(Category.SCULPTURE);
        artwork2.setPrice(700.0);
        artworksB.add(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(600.0);
        artworksB.add(artwork3);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(Category.ARCHITECTURE);
        artwork4.setPrice(900.0);
        artworksB.add(artwork4);
        
        artistB.setArtworks(artworksB);
        
        // Execute: Count artworks by category
        Map<Category, Long> result = artistB.countArtworksByCategory();
        
        // Verify: There are 1 painting artwork, 2 sculpture artworks, and 1 architecture artwork
        assertEquals("Should have 1 painting", Long.valueOf(1), result.get(Category.PAINTING));
        assertEquals("Should have 2 sculptures", Long.valueOf(2), result.get(Category.SCULPTURE));
        assertEquals("Should have 1 architecture", Long.valueOf(1), result.get(Category.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // SetUp: No artworks added to Emily Brown (Artist C)
        List<Artwork> artworksC = new ArrayList<>();
        artistC.setArtworks(artworksC);
        
        // Execute: Count artworks by category
        Map<Category, Long> result = artistC.countArtworksByCategory();
        
        // Verify: There are 0 painting artwork, 0 sculpture artwork, and 0 architecture artwork
        assertTrue("Result map should be empty when no artworks", result.isEmpty());
        assertNull("Should not have painting in the map", result.get(Category.PAINTING));
        assertNull("Should not have sculpture in the map", result.get(Category.SCULPTURE));
        assertNull("Should not have architecture in the map", result.get(Category.ARCHITECTURE));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // SetUp: Add 3 sculptures and no other category to Michael Johnson (Artist D)
        List<Artwork> artworksD = new ArrayList<>();
        
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(Category.SCULPTURE);
        artwork1.setPrice(800.0);
        artworksD.add(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(Category.SCULPTURE);
        artwork2.setPrice(1200.0);
        artworksD.add(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(1000.0);
        artworksD.add(artwork3);
        
        artistD.setArtworks(artworksD);
        
        // Execute: Count artworks by category
        Map<Category, Long> result = artistD.countArtworksByCategory();
        
        // Verify: There are 0 painting artwork, 3 sculpture artworks, and 0 architecture artwork
        assertNull("Should not have painting in the map", result.get(Category.PAINTING));
        assertEquals("Should have 3 sculptures", Long.valueOf(3), result.get(Category.SCULPTURE));
        assertNull("Should not have architecture in the map", result.get(Category.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // SetUp: Add artworks to Alice White (Artist E)
        List<Artwork> artworksE = new ArrayList<>();
        
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory(Category.PAINTING);
        artworksE.add(artworkE1);
        
        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory(Category.ARCHITECTURE);
        artworksE.add(artworkE2);
        
        artistE.setArtworks(artworksE);
        
        // SetUp: Add artworks to David Green (Artist F)
        List<Artwork> artworksF = new ArrayList<>();
        
        Artwork artworkF1 = new Artwork();
        artworkF1.setTitle("Marble Sculpture");
        artworkF1.setCategory(Category.SCULPTURE);
        artworksF.add(artworkF1);
        
        Artwork artworkF2 = new Artwork();
        artworkF2.setTitle("City Skyline");
        artworkF2.setCategory(Category.ARCHITECTURE);
        artworksF.add(artworkF2);
        
        artistF.setArtworks(artworksF);
        
        // Execute: Count artworks by category for both artists
        Map<Category, Long> resultE = artistE.countArtworksByCategory();
        Map<Category, Long> resultF = artistF.countArtworksByCategory();
        
        // Verify: For Alice White - 1 painting, 0 sculpture, 1 architecture
        assertEquals("Alice should have 1 painting", Long.valueOf(1), resultE.get(Category.PAINTING));
        assertNull("Alice should not have sculpture", resultE.get(Category.SCULPTURE));
        assertEquals("Alice should have 1 architecture", Long.valueOf(1), resultE.get(Category.ARCHITECTURE));
        
        // Verify: For David Green - 0 painting, 1 sculpture, 1 architecture
        assertNull("David should not have painting", resultF.get(Category.PAINTING));
        assertEquals("David should have 1 sculpture", Long.valueOf(1), resultF.get(Category.SCULPTURE));
        assertEquals("David should have 1 architecture", Long.valueOf(1), resultF.get(Category.ARCHITECTURE));
    }
}