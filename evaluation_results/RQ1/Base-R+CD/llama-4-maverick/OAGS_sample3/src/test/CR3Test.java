import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.Date;

public class CR3Test {
    
    private Artist artist;
    
    @Before
    public void setUp() {
        artist = new Artist();
    }
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Create artist John Doe
        artist.setName("John Doe");
        artist.setId("A001");
        artist.setPhoneNumber("1234567890");
        artist.setEmail("john.doe@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.MALE);
        
        // Add artworks
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
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify results
        assertEquals(2, (int)result.get(ArtworkCategory.PAINTING));
        assertEquals(1, (int)result.get(ArtworkCategory.SCULPTURE));
        assertEquals(0, (int)result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Create artist Jane Smith
        artist.setName("Jane Smith");
        artist.setId("A002");
        artist.setPhoneNumber("9876543210");
        artist.setEmail("jane.smith@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.FEMALE);
        
        // Add artworks
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
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        artist.addArtwork(artwork4);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify results
        assertEquals(1, (int)result.get(ArtworkCategory.PAINTING));
        assertEquals(2, (int)result.get(ArtworkCategory.SCULPTURE));
        assertEquals(1, (int)result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Create artist Emily Brown
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // No artworks added
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify results - all categories should have 0 artworks
        assertEquals(0, (int)result.getOrDefault(ArtworkCategory.PAINTING, 0));
        assertEquals(0, (int)result.getOrDefault(ArtworkCategory.SCULPTURE, 0));
        assertEquals(0, (int)result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Create artist Michael Johnson
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender(Gender.MALE);
        
        // Add only sculpture artworks
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
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify results
        assertEquals(0, (int)result.getOrDefault(ArtworkCategory.PAINTING, 0));
        assertEquals(3, (int)result.get(ArtworkCategory.SCULPTURE));
        assertEquals(0, (int)result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Create first artist Alice White
        Artist artistE = new Artist();
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        // Add artworks for Alice White
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Landscapes");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Steel Bridge");
        artwork2.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistE.addArtwork(artwork1);
        artistE.addArtwork(artwork2);
        
        // Count artworks for Alice White
        Map<ArtworkCategory, Integer> resultE = artistE.countArtworksByCategory();
        
        // Verify results for Alice White
        assertEquals(1, (int)resultE.get(ArtworkCategory.PAINTING));
        assertEquals(0, (int)resultE.getOrDefault(ArtworkCategory.SCULPTURE, 0));
        assertEquals(1, (int)resultE.get(ArtworkCategory.ARCHITECTURE));
        
        // Create second artist David Green
        Artist artistF = new Artist();
        artistF.setName("David Green");
        artistF.setId("A006");
        
        // Add artworks for David Green
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Marble Sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("City Skyline");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistF.addArtwork(artwork3);
        artistF.addArtwork(artwork4);
        
        // Count artworks for David Green
        Map<ArtworkCategory, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify results for David Green
        assertEquals(0, (int)resultF.getOrDefault(ArtworkCategory.PAINTING, 0));
        assertEquals(1, (int)resultF.get(ArtworkCategory.SCULPTURE));
        assertEquals(1, (int)resultF.get(ArtworkCategory.ARCHITECTURE));
    }
}