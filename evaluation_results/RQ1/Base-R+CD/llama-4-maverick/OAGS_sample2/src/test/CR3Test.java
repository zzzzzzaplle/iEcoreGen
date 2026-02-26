import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
import java.text.SimpleDateFormat;

public class CR3Test {
    
    private Artist artist;
    private Artwork artwork;
    
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
        
        // Add artwork 1: painting
        artwork = new Artwork();
        artwork.setTitle("Sunset");
        artwork.setDescription("A beautiful sunset painting");
        artwork.setCategory(ArtworkCategory.PAINTING);
        artwork.setPrice(200.0);
        artist.addArtwork(artwork);
        
        // Add artwork 2: painting
        artwork = new Artwork();
        artwork.setTitle("Still Life");
        artwork.setDescription("A still life composition");
        artwork.setCategory(ArtworkCategory.PAINTING);
        artwork.setPrice(150.0);
        artist.addArtwork(artwork);
        
        // Add artwork 3: sculpture
        artwork = new Artwork();
        artwork.setTitle("Bronze Statue");
        artwork.setDescription("A bronze statue of a horse");
        artwork.setCategory(ArtworkCategory.SCULPTURE);
        artwork.setPrice(500.0);
        artist.addArtwork(artwork);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals(Integer.valueOf(2), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(1), result.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
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
        
        // Add artwork 1: painting
        artwork = new Artwork();
        artwork.setTitle("Abstract Colors");
        artwork.setDescription("An abstract painting");
        artwork.setCategory(ArtworkCategory.PAINTING);
        artwork.setPrice(300.0);
        artist.addArtwork(artwork);
        
        // Add artwork 2: sculpture
        artwork = new Artwork();
        artwork.setTitle("David");
        artwork.setDescription("A sculpture of David");
        artwork.setCategory(ArtworkCategory.SCULPTURE);
        artwork.setPrice(700.0);
        artist.addArtwork(artwork);
        
        // Add artwork 3: sculpture
        artwork = new Artwork();
        artwork.setTitle("Classic Statue");
        artwork.setDescription("A classic statue");
        artwork.setCategory(ArtworkCategory.SCULPTURE);
        artwork.setPrice(600.0);
        artist.addArtwork(artwork);
        
        // Add artwork 4: architecture
        artwork = new Artwork();
        artwork.setTitle("Skyscraper");
        artwork.setDescription("Modern architecture");
        artwork.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork.setPrice(900.0);
        artist.addArtwork(artwork);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals(Integer.valueOf(1), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(2), result.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(1), result.get(ArtworkCategory.ARCHITECTURE));
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
        
        // Verify expected counts - all should be 0
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
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
        
        // Add artwork 1: sculpture
        artwork = new Artwork();
        artwork.setTitle("Modern Art");
        artwork.setDescription("A modern sculpture");
        artwork.setCategory(ArtworkCategory.SCULPTURE);
        artwork.setPrice(800.0);
        artist.addArtwork(artwork);
        
        // Add artwork 2: sculpture
        artwork = new Artwork();
        artwork.setTitle("Wooden Carving");
        artwork.setDescription("A wooden sculpture");
        artwork.setCategory(ArtworkCategory.SCULPTURE);
        artwork.setPrice(1200.0);
        artist.addArtwork(artwork);
        
        // Add artwork 3: sculpture
        artwork = new Artwork();
        artwork.setTitle("Stone Figure");
        artwork.setDescription("A stone sculpture");
        artwork.setCategory(ArtworkCategory.SCULPTURE);
        artwork.setPrice(1000.0);
        artist.addArtwork(artwork);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(3), result.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Create artist Alice White
        Artist artist1 = new Artist();
        artist1.setName("Alice White");
        artist1.setId("A005");
        
        // Add artwork 1: painting
        artwork = new Artwork();
        artwork.setTitle("Landscapes");
        artwork.setCategory(ArtworkCategory.PAINTING);
        artist1.addArtwork(artwork);
        
        // Add artwork 2: architecture
        artwork = new Artwork();
        artwork.setTitle("Steel Bridge");
        artwork.setCategory(ArtworkCategory.ARCHITECTURE);
        artist1.addArtwork(artwork);
        
        // Count artworks by category for Alice White
        Map<ArtworkCategory, Integer> result1 = artist1.countArtworksByCategory();
        
        // Verify expected counts for Alice White
        assertEquals(Integer.valueOf(1), result1.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(0), result1.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(1), result1.get(ArtworkCategory.ARCHITECTURE));
        
        // Create artist David Green
        Artist artist2 = new Artist();
        artist2.setName("David Green");
        artist2.setId("A006");
        
        // Add artwork 1: sculpture
        artwork = new Artwork();
        artwork.setTitle("Marble Sculpture");
        artwork.setCategory(ArtworkCategory.SCULPTURE);
        artist2.addArtwork(artwork);
        
        // Add artwork 2: architecture
        artwork = new Artwork();
        artwork.setTitle("City Skyline");
        artwork.setCategory(ArtworkCategory.ARCHITECTURE);
        artist2.addArtwork(artwork);
        
        // Count artworks by category for David Green
        Map<ArtworkCategory, Integer> result2 = artist2.countArtworksByCategory();
        
        // Verify expected counts for David Green
        assertEquals(Integer.valueOf(0), result2.get(ArtworkCategory.PAINTING));
        assertEquals(Integer.valueOf(1), result2.get(ArtworkCategory.SCULPTURE));
        assertEquals(Integer.valueOf(1), result2.get(ArtworkCategory.ARCHITECTURE));
    }
}