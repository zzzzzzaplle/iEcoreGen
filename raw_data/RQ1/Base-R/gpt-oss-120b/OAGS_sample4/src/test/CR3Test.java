import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

public class CR3Test {
    
    private Artist artist;
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // SetUp: Create an artist with name: "John Doe", ID: "A001", phone: "1234567890", 
        // email: "john.doe@example.com", address: "123 Art St", gender: "Male"
        artist = new Artist("John Doe", "1234567890", "A001", "john.doe@example.com", 
                           "123 Art St", "Male", null);
        
        // Add 3 paintings to John Doe
        Artwork artwork1 = new Artwork("Sunset", "A beautiful sunset painting", Category.PAINTING, 200.0);
        Artwork artwork2 = new Artwork("Still Life", "A still life composition", Category.PAINTING, 150.0);
        Artwork artwork3 = new Artwork("Bronze Statue", "A bronze statue of a horse", Category.SCULPTURE, 500.0);
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<Category, Integer> result = artist.countArtworksByCategory();
        
        // Expected Output: There are 2 painting artworks, 1 sculpture artwork, and 0 architecture artwork.
        assertEquals(Integer.valueOf(2), result.get(Category.PAINTING));
        assertEquals(Integer.valueOf(1), result.get(Category.SCULPTURE));
        assertEquals(Integer.valueOf(0), result.get(Category.ARCHITECTURE));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // SetUp: Create an artist with name: "Jane Smith", ID: "A002", phone: "9876543210", 
        // email: "jane.smith@example.com", address: "456 Art Ave", gender: "Female"
        artist = new Artist("Jane Smith", "9876543210", "A002", "jane.smith@example.com", 
                           "456 Art Ave", "Female", null);
        
        // Add 1 painting, 2 sculptures, and 1 architecture to Jane Smith
        Artwork artwork1 = new Artwork("Abstract Colors", "An abstract painting", Category.PAINTING, 300.0);
        Artwork artwork2 = new Artwork("David", "A sculpture of David", Category.SCULPTURE, 700.0);
        Artwork artwork3 = new Artwork("Classic Statue", "A classic statue", Category.SCULPTURE, 600.0);
        Artwork artwork4 = new Artwork("Skyscraper", "Modern architecture", Category.ARCHITECTURE, 900.0);
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        artist.addArtwork(artwork4);
        
        // Count artworks by category
        Map<Category, Integer> result = artist.countArtworksByCategory();
        
        // Expected Output: There are 1 painting artwork, 2 sculpture artworks, and 1 architecture artwork.
        assertEquals(Integer.valueOf(1), result.get(Category.PAINTING));
        assertEquals(Integer.valueOf(2), result.get(Category.SCULPTURE));
        assertEquals(Integer.valueOf(1), result.get(Category.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // SetUp: Create an artist with name: "Emily Brown", ID: "A003", phone: "1112223333", 
        // email: "emily.brown@example.com", address: "789 Art Blvd", gender: "Female"
        artist = new Artist("Emily Brown", "1112223333", "A003", "emily.brown@example.com", 
                           "789 Art Blvd", "Female", null);
        
        // Do not add any artworks to Emily Brown
        // Count artworks by category
        Map<Category, Integer> result = artist.countArtworksByCategory();
        
        // Expected Output: There are 0 painting artwork, 0 sculpture artwork, and 0 architecture artwork.
        assertEquals(Integer.valueOf(0), result.get(Category.PAINTING));
        assertEquals(Integer.valueOf(0), result.get(Category.SCULPTURE));
        assertEquals(Integer.valueOf(0), result.get(Category.ARCHITECTURE));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // SetUp: Create an artist with name: "Michael Johnson", ID: "A004", phone: "4445556666", 
        // email: "michael.johnson@example.com", address: "123 Art Lane", gender: "Male"
        artist = new Artist("Michael Johnson", "4445556666", "A004", "michael.johnson@example.com", 
                           "123 Art Lane", "Male", null);
        
        // Add 3 sculptures and no other category
        Artwork artwork1 = new Artwork("Modern Art", "A modern sculpture", Category.SCULPTURE, 800.0);
        Artwork artwork2 = new Artwork("Wooden Carving", "A wooden sculpture", Category.SCULPTURE, 1200.0);
        Artwork artwork3 = new Artwork("Stone Figure", "A stone sculpture", Category.SCULPTURE, 1000.0);
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<Category, Integer> result = artist.countArtworksByCategory();
        
        // Expected Output: There are 0 painting artwork, 3 sculpture artworks, and 0 architecture artwork.
        assertEquals(Integer.valueOf(0), result.get(Category.PAINTING));
        assertEquals(Integer.valueOf(3), result.get(Category.SCULPTURE));
        assertEquals(Integer.valueOf(0), result.get(Category.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // SetUp: Create artist E (Alice White) with ID: "A005"
        Artist artistE = new Artist("Alice White", null, "A005", null, null, null, null);
        
        // Add artworks to Alice White
        Artwork artworkE1 = new Artwork("Landscapes", null, Category.PAINTING, 0.0);
        Artwork artworkE2 = new Artwork("Steel Bridge", null, Category.ARCHITECTURE, 0.0);
        
        artistE.addArtwork(artworkE1);
        artistE.addArtwork(artworkE2);
        
        // SetUp: Create artist F (David Green) with ID: "A006"
        Artist artistF = new Artist("David Green", null, "A006", null, null, null, null);
        
        // Add artworks to David Green
        Artwork artworkF1 = new Artwork("Marble Sculpture", null, Category.SCULPTURE, 0.0);
        Artwork artworkF2 = new Artwork("City Skyline", null, Category.ARCHITECTURE, 0.0);
        
        artistF.addArtwork(artworkF1);
        artistF.addArtwork(artworkF2);
        
        // Count artworks by category for Alice White
        Map<Category, Integer> resultE = artistE.countArtworksByCategory();
        
        // Expected Output: For Alice White: There are 1 painting artwork, 0 sculpture artwork, and 1 architecture artwork.
        assertEquals(Integer.valueOf(1), resultE.get(Category.PAINTING));
        assertEquals(Integer.valueOf(0), resultE.get(Category.SCULPTURE));
        assertEquals(Integer.valueOf(1), resultE.get(Category.ARCHITECTURE));
        
        // Count artworks by category for David Green
        Map<Category, Integer> resultF = artistF.countArtworksByCategory();
        
        // Expected Output: For David Green: There are 0 painting artworks, 1 sculpture artwork, and 1 architecture artwork.
        assertEquals(Integer.valueOf(0), resultF.get(Category.PAINTING));
        assertEquals(Integer.valueOf(1), resultF.get(Category.SCULPTURE));
        assertEquals(Integer.valueOf(1), resultF.get(Category.ARCHITECTURE));
    }
}