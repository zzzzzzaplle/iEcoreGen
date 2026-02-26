import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR3Test {
    
    private Artist artist;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Create artist John Doe
        artist = new Artist();
        artist.setName("John Doe");
        artist.setId("A001");
        artist.setPhoneNumber("1234567890");
        artist.setEmail("john.doe@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.MALE);
        artist.setArtworks(new ArrayList<>());
        
        // Add 3 artworks: 2 paintings and 1 sculpture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(200);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setPrice(150);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(500);
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 2 painting artworks", 2, (int)result.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 1 sculpture artwork", 1, (int)result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artwork", 0, (int)result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Create artist Jane Smith
        artist = new Artist();
        artist.setName("Jane Smith");
        artist.setId("A002");
        artist.setPhoneNumber("9876543210");
        artist.setEmail("jane.smith@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.FEMALE);
        artist.setArtworks(new ArrayList<>());
        
        // Add 4 artworks: 1 painting, 2 sculptures, and 1 architecture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(300);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(700);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(600);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setPrice(900);
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        artist.addArtwork(artwork4);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 1 painting artwork", 1, (int)result.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 2 sculpture artworks", 2, (int)result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 1 architecture artwork", 1, (int)result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Create artist Emily Brown
        artist = new Artist();
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        artist.setArtworks(new ArrayList<>());
        
        // No artworks added
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify all categories have 0 artworks
        assertEquals("Should have 0 painting artwork", 0, (int)result.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 0 sculpture artwork", 0, (int)result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artwork", 0, (int)result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Create artist Michael Johnson
        artist = new Artist();
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender(Gender.MALE);
        artist.setArtworks(new ArrayList<>());
        
        // Add 3 sculptures only
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        artwork1.setPrice(800);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(1200);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(1000);
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 0 painting artwork", 0, (int)result.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 3 sculpture artworks", 3, (int)result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artwork", 0, (int)result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Create artist Alice White
        Artist artistE = new Artist();
        artistE.setName("Alice White");
        artistE.setId("A005");
        artistE.setArtworks(new ArrayList<>());
        
        // Add artworks for Alice White
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory(ArtworkCategory.PAINTING);
        
        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistE.addArtwork(artworkE1);
        artistE.addArtwork(artworkE2);
        
        // Count artworks by category for Alice White
        Map<ArtworkCategory, Integer> resultE = artistE.countArtworksByCategory();
        
        // Verify counts for Alice White
        assertEquals("Alice White should have 1 painting artwork", 1, (int)resultE.get(ArtworkCategory.PAINTING));
        assertEquals("Alice White should have 0 sculpture artwork", 0, (int)resultE.get(ArtworkCategory.SCULPTURE));
        assertEquals("Alice White should have 1 architecture artwork", 1, (int)resultE.get(ArtworkCategory.ARCHITECTURE));
        
        // Create artist David Green
        Artist artistF = new Artist();
        artistF.setName("David Green");
        artistF.setId("A006");
        artistF.setArtworks(new ArrayList<>());
        
        // Add artworks for David Green
        Artwork artworkF1 = new Artwork();
        artworkF1.setTitle("Marble Sculpture");
        artworkF1.setCategory(ArtworkCategory.SCULPTURE);
        
        Artwork artworkF2 = new Artwork();
        artworkF2.setTitle("City Skyline");
        artworkF2.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistF.addArtwork(artworkF1);
        artistF.addArtwork(artworkF2);
        
        // Count artworks by category for David Green
        Map<ArtworkCategory, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify counts for David Green
        assertEquals("David Green should have 0 painting artwork", 0, (int)resultF.get(ArtworkCategory.PAINTING));
        assertEquals("David Green should have 1 sculpture artwork", 1, (int)resultF.get(ArtworkCategory.SCULPTURE));
        assertEquals("David Green should have 1 architecture artwork", 1, (int)resultF.get(ArtworkCategory.ARCHITECTURE));
    }
}