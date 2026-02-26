import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class CR3Test {
    
    private Artist artist;
    
    @Before
    public void setUp() {
        artist = new Artist();
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
        
        // Add 3 artworks: 2 paintings and 1 sculpture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(200.0);
        artist.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setPrice(150.0);
        artist.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(500.0);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected counts: 2 paintings, 1 sculpture, 0 architecture
        assertEquals(2, result.getOrDefault(ArtworkCategory.PAINTING, 0).intValue());
        assertEquals(1, result.getOrDefault(ArtworkCategory.SCULPTURE, 0).intValue());
        assertEquals(0, result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0).intValue());
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
        
        // Add 4 artworks: 1 painting, 2 sculptures, 1 architecture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(300.0);
        artist.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(700.0);
        artist.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(600.0);
        artist.addArtwork(artwork3);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setPrice(900.0);
        artist.addArtwork(artwork4);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected counts: 1 painting, 2 sculptures, 1 architecture
        assertEquals(1, result.getOrDefault(ArtworkCategory.PAINTING, 0).intValue());
        assertEquals(2, result.getOrDefault(ArtworkCategory.SCULPTURE, 0).intValue());
        assertEquals(1, result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0).intValue());
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Set up artist Emily Brown with no artworks
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify all categories have 0 artworks
        assertEquals(0, result.getOrDefault(ArtworkCategory.PAINTING, 0).intValue());
        assertEquals(0, result.getOrDefault(ArtworkCategory.SCULPTURE, 0).intValue());
        assertEquals(0, result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0).intValue());
        
        // Verify the map is empty since no artworks exist
        assertTrue(result.isEmpty());
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
        
        // Add 3 sculptures only
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        artwork1.setPrice(800.0);
        artist.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(1200.0);
        artist.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(1000.0);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected counts: 0 painting, 3 sculptures, 0 architecture
        assertEquals(0, result.getOrDefault(ArtworkCategory.PAINTING, 0).intValue());
        assertEquals(3, result.getOrDefault(ArtworkCategory.SCULPTURE, 0).intValue());
        assertEquals(0, result.getOrDefault(ArtworkCategory.ARCHITECTURE, 0).intValue());
        
        // Verify only sculpture category exists in the map
        assertEquals(1, result.size());
        assertTrue(result.containsKey(ArtworkCategory.SCULPTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Create first artist Alice White
        Artist artist1 = new Artist();
        artist1.setName("Alice White");
        artist1.setId("A005");
        
        // Add artworks to Alice White: 1 painting, 1 architecture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Landscapes");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artist1.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Steel Bridge");
        artwork2.setCategory(ArtworkCategory.ARCHITECTURE);
        artist1.addArtwork(artwork2);
        
        // Count artworks for Alice White
        Map<ArtworkCategory, Integer> result1 = artist1.countArtworksByCategory();
        
        // Verify Alice White's counts: 1 painting, 0 sculpture, 1 architecture
        assertEquals(1, result1.getOrDefault(ArtworkCategory.PAINTING, 0).intValue());
        assertEquals(0, result1.getOrDefault(ArtworkCategory.SCULPTURE, 0).intValue());
        assertEquals(1, result1.getOrDefault(ArtworkCategory.ARCHITECTURE, 0).intValue());
        
        // Create second artist David Green
        Artist artist2 = new Artist();
        artist2.setName("David Green");
        artist2.setId("A006");
        
        // Add artworks to David Green: 1 sculpture, 1 architecture
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Marble Sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artist2.addArtwork(artwork3);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("City Skyline");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artist2.addArtwork(artwork4);
        
        // Count artworks for David Green
        Map<ArtworkCategory, Integer> result2 = artist2.countArtworksByCategory();
        
        // Verify David Green's counts: 0 painting, 1 sculpture, 1 architecture
        assertEquals(0, result2.getOrDefault(ArtworkCategory.PAINTING, 0).intValue());
        assertEquals(1, result2.getOrDefault(ArtworkCategory.SCULPTURE, 0).intValue());
        assertEquals(1, result2.getOrDefault(ArtworkCategory.ARCHITECTURE, 0).intValue());
    }
}