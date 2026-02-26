import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Create artist John Doe
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
        artwork1.setCategory(Category.PAINTING);
        artwork1.setPrice(200.0);
        artist.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(Category.PAINTING);
        artwork2.setPrice(150.0);
        artist.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(500.0);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        String result = artist.countArtworksByCategory();
        String expected = "Painting: 2, Sculpture: 1, Architecture: 0";
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Create artist Jane Smith
        Artist artist = new Artist();
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
        artwork1.setCategory(Category.PAINTING);
        artwork1.setPrice(300.0);
        artist.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(Category.SCULPTURE);
        artwork2.setPrice(700.0);
        artist.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(600.0);
        artist.addArtwork(artwork3);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(Category.ARCHITECTURE);
        artwork4.setPrice(900.0);
        artist.addArtwork(artwork4);
        
        // Count artworks by category
        String result = artist.countArtworksByCategory();
        String expected = "Painting: 1, Sculpture: 2, Architecture: 1";
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Create artist Emily Brown with no artworks
        Artist artist = new Artist();
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // Count artworks by category (should be all zeros)
        String result = artist.countArtworksByCategory();
        String expected = "Painting: 0, Sculpture: 0, Architecture: 0";
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Create artist Michael Johnson
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
        artwork1.setCategory(Category.SCULPTURE);
        artwork1.setPrice(800.0);
        artist.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(Category.SCULPTURE);
        artwork2.setPrice(1200.0);
        artist.addArtwork(artwork2);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(1000.0);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        String result = artist.countArtworksByCategory();
        String expected = "Painting: 0, Sculpture: 3, Architecture: 0";
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Create artist Alice White
        Artist artist1 = new Artist();
        artist1.setName("Alice White");
        artist1.setId("A005");
        
        // Add artworks for Alice White: 1 painting, 1 architecture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Landscapes");
        artwork1.setCategory(Category.PAINTING);
        artist1.addArtwork(artwork1);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Steel Bridge");
        artwork2.setCategory(Category.ARCHITECTURE);
        artist1.addArtwork(artwork2);
        
        // Create artist David Green
        Artist artist2 = new Artist();
        artist2.setName("David Green");
        artist2.setId("A006");
        
        // Add artworks for David Green: 1 sculpture, 1 architecture
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Marble Sculpture");
        artwork3.setCategory(Category.SCULPTURE);
        artist2.addArtwork(artwork3);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("City Skyline");
        artwork4.setCategory(Category.ARCHITECTURE);
        artist2.addArtwork(artwork4);
        
        // Count artworks by category for both artists
        String result1 = artist1.countArtworksByCategory();
        String result2 = artist2.countArtworksByCategory();
        
        String expected1 = "Painting: 1, Sculpture: 0, Architecture: 1";
        String expected2 = "Painting: 0, Sculpture: 1, Architecture: 1";
        
        assertEquals(expected1, result1);
        assertEquals(expected2, result2);
    }
}