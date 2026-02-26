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
        // Test Case 1: Artist A (John Doe)
        artistA = new Artist("John Doe", "1234567890", "A001", "john.doe@example.com", "123 Art St", "Male");
        artistA.addArtwork(new Artwork("Sunset", "A beautiful sunset painting", "painting", 200.0));
        artistA.addArtwork(new Artwork("Still Life", "A still life composition", "painting", 150.0));
        artistA.addArtwork(new Artwork("Bronze Statue", "A bronze statue of a horse", "sculpture", 500.0));
        
        // Test Case 2: Artist B (Jane Smith)
        artistB = new Artist("Jane Smith", "9876543210", "A002", "jane.smith@example.com", "456 Art Ave", "Female");
        artistB.addArtwork(new Artwork("Abstract Colors", "An abstract painting", "painting", 300.0));
        artistB.addArtwork(new Artwork("David", "A sculpture of David", "sculpture", 700.0));
        artistB.addArtwork(new Artwork("Classic Statue", "A classic statue", "sculpture", 600.0));
        artistB.addArtwork(new Artwork("Skyscraper", "Modern architecture", "architecture", 900.0));
        
        // Test Case 3: Artist C (Emily Brown)
        artistC = new Artist("Emily Brown", "1112223333", "A003", "emily.brown@example.com", "789 Art Blvd", "Female");
        // No artworks added
        
        // Test Case 4: Artist D (Michael Johnson)
        artistD = new Artist("Michael Johnson", "4445556666", "A004", "michael.johnson@example.com", "123 Art Lane", "Male");
        artistD.addArtwork(new Artwork("Modern Art", "A modern sculpture", "sculpture", 800.0));
        artistD.addArtwork(new Artwork("Wooden Carving", "A wooden sculpture", "sculpture", 1200.0));
        artistD.addArtwork(new Artwork("Stone Figure", "A stone sculpture", "sculpture", 1000.0));
        
        // Test Case 5: Artist E (Alice White)
        artistE = new Artist("Alice White", "5556667777", "A005", "alice.white@example.com", "456 Art Way", "Female");
        artistE.addArtwork(new Artwork("Landscapes", "A landscape painting", "painting", 400.0));
        artistE.addArtwork(new Artwork("Steel Bridge", "A modern bridge design", "architecture", 1100.0));
        
        // Test Case 5: Artist F (David Green)
        artistF = new Artist("David Green", "6667778888", "A006", "david.green@example.com", "789 Art Road", "Male");
        artistF.addArtwork(new Artwork("Marble Sculpture", "A marble sculpture", "sculpture", 1500.0));
        artistF.addArtwork(new Artwork("City Skyline", "Urban architecture", "architecture", 1300.0));
    }
    
    @Test
    public void testCase1_countArtworksByCategoryForSingleArtist() {
        // Count artworks for Artist A (John Doe)
        Map<String, Integer> result = artistA.countArtworksByCategory();
        
        // Verify expected counts: 2 painting, 1 sculpture, 0 architecture
        assertEquals(2, (int) result.getOrDefault("painting", 0));
        assertEquals(1, (int) result.getOrDefault("sculpture", 0));
        assertEquals(0, (int) result.getOrDefault("architecture", 0));
    }
    
    @Test
    public void testCase2_countArtworksByCategoryWithMultipleCategories() {
        // Count artworks for Artist B (Jane Smith)
        Map<String, Integer> result = artistB.countArtworksByCategory();
        
        // Verify expected counts: 1 painting, 2 sculpture, 1 architecture
        assertEquals(1, (int) result.getOrDefault("painting", 0));
        assertEquals(2, (int) result.getOrDefault("sculpture", 0));
        assertEquals(1, (int) result.getOrDefault("architecture", 0));
    }
    
    @Test
    public void testCase3_noArtworksForAnArtist() {
        // Count artworks for Artist C (Emily Brown)
        Map<String, Integer> result = artistC.countArtworksByCategory();
        
        // Verify expected counts: 0 painting, 0 sculpture, 0 architecture
        assertEquals(0, (int) result.getOrDefault("painting", 0));
        assertEquals(0, (int) result.getOrDefault("sculpture", 0));
        assertEquals(0, (int) result.getOrDefault("architecture", 0));
    }
    
    @Test
    public void testCase4_countArtworksWithOnlyOneCategory() {
        // Count artworks for Artist D (Michael Johnson)
        Map<String, Integer> result = artistD.countArtworksByCategory();
        
        // Verify expected counts: 0 painting, 3 sculpture, 0 architecture
        assertEquals(0, (int) result.getOrDefault("painting", 0));
        assertEquals(3, (int) result.getOrDefault("sculpture", 0));
        assertEquals(0, (int) result.getOrDefault("architecture", 0));
    }
    
    @Test
    public void testCase5_countArtworksWithMultipleArtists() {
        // Count artworks for Artist E (Alice White)
        Map<String, Integer> resultE = artistE.countArtworksByCategory();
        
        // Verify expected counts for Alice White: 1 painting, 0 sculpture, 1 architecture
        assertEquals(1, (int) resultE.getOrDefault("painting", 0));
        assertEquals(0, (int) resultE.getOrDefault("sculpture", 0));
        assertEquals(1, (int) resultE.getOrDefault("architecture", 0));
        
        // Count artworks for Artist F (David Green)
        Map<String, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify expected counts for David Green: 0 painting, 1 sculpture, 1 architecture
        assertEquals(0, (int) resultF.getOrDefault("painting", 0));
        assertEquals(1, (int) resultF.getOrDefault("sculpture", 0));
        assertEquals(1, (int) resultF.getOrDefault("architecture", 0));
    }
}