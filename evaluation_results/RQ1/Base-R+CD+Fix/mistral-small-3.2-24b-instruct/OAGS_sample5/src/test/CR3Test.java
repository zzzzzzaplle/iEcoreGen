import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Map;

public class CR3Test {
    
    private Artist artist;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        artist = new Artist();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Set up artist John Doe
        artist.setName("John Doe");
        artist.setId("A001");
        artist.setPhoneNumber("1234567890");
        artist.setEmail("john.doe@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.MALE);
        
        // Add artwork 1: painting
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(200);
        artist.addArtwork(artwork1);
        
        // Add artwork 2: painting
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setPrice(150);
        artist.addArtwork(artwork2);
        
        // Add artwork 3: sculpture
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(500);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify the counts
        assertEquals("Painting count should be 2", Integer.valueOf(2), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Set up artist Jane Smith
        artist.setName("Jane Smith");
        artist.setId("A002");
        artist.setPhoneNumber("9876543210");
        artist.setEmail("jane.smith@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.FEMALE);
        
        // Add artwork 1: painting
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(300);
        artist.addArtwork(artwork1);
        
        // Add artwork 2: sculpture
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(700);
        artist.addArtwork(artwork2);
        
        // Add artwork 3: sculpture
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(600);
        artist.addArtwork(artwork3);
        
        // Add artwork 4: architecture
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setPrice(900);
        artist.addArtwork(artwork4);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify the counts
        assertEquals("Painting count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 2", Integer.valueOf(2), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 1", Integer.valueOf(1), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Set up artist Emily Brown
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // No artworks added to Emily Brown
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify the counts (all should be 0)
        assertEquals("Painting count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Set up artist Michael Johnson
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender(Gender.MALE);
        
        // Add artwork 1: sculpture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        artwork1.setPrice(800);
        artist.addArtwork(artwork1);
        
        // Add artwork 2: sculpture
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(1200);
        artist.addArtwork(artwork2);
        
        // Add artwork 3: sculpture
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(1000);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify the counts
        assertEquals("Painting count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count should be 3", Integer.valueOf(3), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count should be 0", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Create artist E (Alice White)
        Artist artistE = new Artist();
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        // Add artwork 1: painting
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory(ArtworkCategory.PAINTING);
        artistE.addArtwork(artworkE1);
        
        // Add artwork 2: architecture
        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory(ArtworkCategory.ARCHITECTURE);
        artistE.addArtwork(artworkE2);
        
        // Count artworks by category for artist E
        Map<ArtworkCategory, Integer> resultE = artistE.countArtworksByCategory();
        
        // Verify the counts for artist E
        assertEquals("Painting count for Alice White should be 1", Integer.valueOf(1), resultE.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count for Alice White should be 0", Integer.valueOf(0), resultE.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count for Alice White should be 1", Integer.valueOf(1), resultE.get(ArtworkCategory.ARCHITECTURE));
        
        // Create artist F (David Green)
        Artist artistF = new Artist();
        artistF.setName("David Green");
        artistF.setId("A006");
        
        // Add artwork 1: sculpture
        Artwork artworkF1 = new Artwork();
        artworkF1.setTitle("Marble Sculpture");
        artworkF1.setCategory(ArtworkCategory.SCULPTURE);
        artistF.addArtwork(artworkF1);
        
        // Add artwork 2: architecture
        Artwork artworkF2 = new Artwork();
        artworkF2.setTitle("City Skyline");
        artworkF2.setCategory(ArtworkCategory.ARCHITECTURE);
        artistF.addArtwork(artworkF2);
        
        // Count artworks by category for artist F
        Map<ArtworkCategory, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify the counts for artist F
        assertEquals("Painting count for David Green should be 0", Integer.valueOf(0), resultF.get(ArtworkCategory.PAINTING));
        assertEquals("Sculpture count for David Green should be 1", Integer.valueOf(1), resultF.get(ArtworkCategory.SCULPTURE));
        assertEquals("Architecture count for David Green should be 1", Integer.valueOf(1), resultF.get(ArtworkCategory.ARCHITECTURE));
    }
}