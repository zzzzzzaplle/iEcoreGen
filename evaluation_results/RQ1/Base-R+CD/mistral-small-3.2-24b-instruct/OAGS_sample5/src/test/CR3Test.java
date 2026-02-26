import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Create artist with specified details
        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setId("A001");
        artist.setPhoneNumber("1234567890");
        artist.setEmail("john.doe@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.MALE);
        
        // Add 3 artworks: 2 paintings and 1 sculpture
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
        
        // Verify expected counts: 2 paintings, 1 sculpture, 0 architecture
        assertEquals("Painting count should be 2", Integer.valueOf(2), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Create artist with specified details
        Artist artist = new Artist();
        artist.setName("Jane Smith");
        artist.setId("A002");
        artist.setPhoneNumber("9876543210");
        artist.setEmail("jane.smith@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.FEMALE);
        
        // Add 4 artworks: 1 painting, 2 sculptures, and 1 architecture
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
        
        // Verify expected counts: 1 painting, 2 sculptures, 1 architecture
        assertEquals("Painting count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 2", Integer.valueOf(2), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Create artist with specified details
        Artist artist = new Artist();
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // No artworks added to the artist
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected counts: 0 for all categories
        assertEquals("Painting count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Create artist with specified details
        Artist artist = new Artist();
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender(Gender.MALE);
        
        // Add 3 sculptures only
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
        
        // Verify expected counts: 0 paintings, 3 sculptures, 0 architecture
        assertEquals("Painting count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 3", Integer.valueOf(3), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Create first artist (Alice White)
        Artist artist1 = new Artist();
        artist1.setName("Alice White");
        artist1.setId("A005");
        
        // Add artworks to first artist: 1 painting and 1 architecture
        Artwork artwork1a = new Artwork();
        artwork1a.setTitle("Landscapes");
        artwork1a.setCategory(ArtworkCategory.PAINTING);
        
        Artwork artwork1b = new Artwork();
        artwork1b.setTitle("Steel Bridge");
        artwork1b.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artist1.addArtwork(artwork1a);
        artist1.addArtwork(artwork1b);
        
        // Create second artist (David Green)
        Artist artist2 = new Artist();
        artist2.setName("David Green");
        artist2.setId("A006");
        
        // Add artworks to second artist: 1 sculpture and 1 architecture
        Artwork artwork2a = new Artwork();
        artwork2a.setTitle("Marble Sculpture");
        artwork2a.setCategory(ArtworkCategory.SCULPTURE);
        
        Artwork artwork2b = new Artwork();
        artwork2b.setTitle("City Skyline");
        artwork2b.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artist2.addArtwork(artwork2a);
        artist2.addArtwork(artwork2b);
        
        // Count artworks by category for first artist
        Map<ArtworkCategory, Integer> result1 = artist1.countArtworksByCategory();
        
        // Verify expected counts for Alice White: 1 painting, 0 sculpture, 1 architecture
        assertEquals("Alice White - Painting count should be 1", Integer.valueOf(1), result1.get(ArtworkCategory.PAINTING));
        assertEquals("Alice White - Sculpture count should be 0", Integer.valueOf(0), result1.get(ArtworkCategory.SCULPTURE));
        assertEquals("Alice White - Architecture count should be 1", Integer.valueOf(1), result1.get(ArtworkCategory.ARCHITECTURE));
        
        // Count artworks by category for second artist
        Map<ArtworkCategory, Integer> result2 = artist2.countArtworksByCategory();
        
        // Verify expected counts for David Green: 0 painting, 1 sculpture, 1 architecture
        assertEquals("David Green - Painting count should be 0", Integer.valueOf(0), result2.get(ArtworkCategory.PAINTING));
        assertEquals("David Green - Sculpture count should be 1", Integer.valueOf(1), result2.get(ArtworkCategory.SCULPTURE));
        assertEquals("David Green - Architecture count should be 1", Integer.valueOf(1), result2.get(ArtworkCategory.ARCHITECTURE));
    }
}