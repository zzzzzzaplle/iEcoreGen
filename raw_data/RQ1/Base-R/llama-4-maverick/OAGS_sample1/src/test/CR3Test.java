import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // SetUp: Create artist John Doe with artworks
        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setId("A001");
        artist.setPhoneNumber("1234567890");
        artist.setEmail("john.doe@example.com");
        artist.setAddress("123 Art St");
        artist.setGender("Male");
        
        // Add 3 artworks: 2 paintings, 1 sculpture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory("painting");
        artwork1.setPrice(200);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory("painting");
        artwork2.setPrice(150);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory("sculpture");
        artwork3.setPrice(500);
        
        artist.getArtworks().add(artwork1);
        artist.getArtworks().add(artwork2);
        artist.getArtworks().add(artwork3);
        
        // Execute countArtworksByCategory method
        String result = artist.countArtworksByCategory();
        
        // Verify expected output
        assertEquals("Painting: 2, Sculpture: 1, Architecture: 0", result);
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // SetUp: Create artist Jane Smith with artworks
        Artist artist = new Artist();
        artist.setName("Jane Smith");
        artist.setId("A002");
        artist.setPhoneNumber("9876543210");
        artist.setEmail("jane.smith@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender("Female");
        
        // Add 1 painting, 2 sculptures, 1 architecture
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory("painting");
        artwork1.setPrice(300);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory("sculpture");
        artwork2.setPrice(700);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory("sculpture");
        artwork3.setPrice(600);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory("architecture");
        artwork4.setPrice(900);
        
        artist.getArtworks().add(artwork1);
        artist.getArtworks().add(artwork2);
        artist.getArtworks().add(artwork3);
        artist.getArtworks().add(artwork4);
        
        // Execute countArtworksByCategory method
        String result = artist.countArtworksByCategory();
        
        // Verify expected output
        assertEquals("Painting: 1, Sculpture: 2, Architecture: 1", result);
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // SetUp: Create artist Emily Brown without any artworks
        Artist artist = new Artist();
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        // Execute countArtworksByCategory method
        String result = artist.countArtworksByCategory();
        
        // Verify expected output
        assertEquals("Painting: 0, Sculpture: 0, Architecture: 0", result);
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // SetUp: Create artist Michael Johnson with only sculptures
        Artist artist = new Artist();
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender("Male");
        
        // Add 3 sculptures only
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory("sculpture");
        artwork1.setPrice(800);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory("sculpture");
        artwork2.setPrice(1200);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory("sculpture");
        artwork3.setPrice(1000);
        
        artist.getArtworks().add(artwork1);
        artist.getArtworks().add(artwork2);
        artist.getArtworks().add(artwork3);
        
        // Execute countArtworksByCategory method
        String result = artist.countArtworksByCategory();
        
        // Verify expected output
        assertEquals("Painting: 0, Sculpture: 3, Architecture: 0", result);
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // SetUp: Create artist Alice White with artworks
        Artist artist1 = new Artist();
        artist1.setName("Alice White");
        artist1.setId("A005");
        
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Landscapes");
        artwork1.setCategory("painting");
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Steel Bridge");
        artwork2.setCategory("architecture");
        
        artist1.getArtworks().add(artwork1);
        artist1.getArtworks().add(artwork2);
        
        // SetUp: Create artist David Green with artworks
        Artist artist2 = new Artist();
        artist2.setName("David Green");
        artist2.setId("A006");
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Marble Sculpture");
        artwork3.setCategory("sculpture");
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("City Skyline");
        artwork4.setCategory("architecture");
        
        artist2.getArtworks().add(artwork3);
        artist2.getArtworks().add(artwork4);
        
        // Execute countArtworksByCategory method for both artists
        String result1 = artist1.countArtworksByCategory();
        String result2 = artist2.countArtworksByCategory();
        
        // Verify expected outputs
        assertEquals("Painting: 1, Sculpture: 0, Architecture: 1", result1);
        assertEquals("Painting: 0, Sculpture: 1, Architecture: 1", result2);
    }
}