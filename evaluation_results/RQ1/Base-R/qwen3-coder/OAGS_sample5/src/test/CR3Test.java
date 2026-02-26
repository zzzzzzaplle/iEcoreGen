import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // SetUp: Create an artist with name: "John Doe", ID: "A001", phone: "1234567890", email: "john.doe@example.com", address: "123 Art St", gender: "Male"
        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setId("A001");
        artist.setPhoneNumber("1234567890");
        artist.setEmail("john.doe@example.com");
        artist.setAddress("123 Art St");
        artist.setGender("Male");
        
        // Add 3 artworks to John Doe
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
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        int[] result = artist.countArtworksByCategory();
        
        // Expected Output: There are 2 painting artworks, 1 sculpture artwork, and 0 architecture artwork.
        assertEquals(2, result[0]); // paintings
        assertEquals(1, result[1]); // sculptures
        assertEquals(0, result[2]); // architectures
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // SetUp: Create an artist with name: "Jane Smith", ID: "A002", phone: "9876543210", email: "jane.smith@example.com", address: "456 Art Ave", gender: "Female"
        Artist artist = new Artist();
        artist.setName("Jane Smith");
        artist.setId("A002");
        artist.setPhoneNumber("9876543210");
        artist.setEmail("jane.smith@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender("Female");
        
        // Add 1 painting, 2 sculptures, and 1 architecture to Jane Smith
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
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        artist.addArtwork(artwork4);
        
        // Count artworks by category
        int[] result = artist.countArtworksByCategory();
        
        // Expected Output: There are 1 painting artwork, 2 sculpture artworks, and 1 architecture artwork.
        assertEquals(1, result[0]); // paintings
        assertEquals(2, result[1]); // sculptures
        assertEquals(1, result[2]); // architectures
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // SetUp: Create an artist with name: "Emily Brown", ID: "A003", phone: "1112223333", email: "emily.brown@example.com", address: "789 Art Blvd", gender: "Female"
        Artist artist = new Artist();
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        // Do not add any artworks to Emily Brown
        
        // Count artworks by category
        int[] result = artist.countArtworksByCategory();
        
        // Expected Output: There are 0 painting artwork, 0 sculpture artwork, and 0 architecture artwork.
        assertEquals(0, result[0]); // paintings
        assertEquals(0, result[1]); // sculptures
        assertEquals(0, result[2]); // architectures
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // SetUp: Create an artist with name: "Michael Johnson", ID: "A004", phone: "4445556666", email: "michael.johnson@example.com", address: "123 Art Lane", gender: "Male"
        Artist artist = new Artist();
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender("Male");
        
        // Add 3 sculptures and no other category
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
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        int[] result = artist.countArtworksByCategory();
        
        // Expected Output: There are 0 painting artwork, 3 sculpture artworks, and 0 architecture artwork.
        assertEquals(0, result[0]); // paintings
        assertEquals(3, result[1]); // sculptures
        assertEquals(0, result[2]); // architectures
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // SetUp: Create artist E (Alice White) with ID: "A005"
        Artist artistE = new Artist();
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        // Add artworks to Alice White
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory("painting");
        
        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory("architecture");
        
        artistE.addArtwork(artworkE1);
        artistE.addArtwork(artworkE2);
        
        // SetUp: Create artist F (David Green) with ID: "A006"
        Artist artistF = new Artist();
        artistF.setName("David Green");
        artistF.setId("A006");
        
        // Add artworks to David Green
        Artwork artworkF1 = new Artwork();
        artworkF1.setTitle("Marble Sculpture");
        artworkF1.setCategory("sculpture");
        
        Artwork artworkF2 = new Artwork();
        artworkF2.setTitle("City Skyline");
        artworkF2.setCategory("architecture");
        
        artistF.addArtwork(artworkF1);
        artistF.addArtwork(artworkF2);
        
        // Count artworks by category for Alice White
        int[] resultE = artistE.countArtworksByCategory();
        
        // Expected Output for Alice White: There are 1 painting artwork, 0 sculpture artwork, and 1 architecture artwork.
        assertEquals(1, resultE[0]); // paintings
        assertEquals(0, resultE[1]); // sculptures
        assertEquals(1, resultE[2]); // architectures
        
        // Count artworks by category for David Green
        int[] resultF = artistF.countArtworksByCategory();
        
        // Expected Output for David Green: There are 0 painting artworks, 1 sculpture artwork, and 1 architecture artwork.
        assertEquals(0, resultF[0]); // paintings
        assertEquals(1, resultF[1]); // sculptures
        assertEquals(1, resultF[2]); // architectures
    }
}