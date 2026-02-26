import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Test Case 1: Count Artworks by Category for a Single Artist
        // SetUp: Create an artist with name: "John Doe", ID: "A001", phone: "1234567890", email: "john.doe@example.com", address: "123 Art St", gender: "Male"
        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setId("A001");
        artist.setPhoneNumber("1234567890");
        artist.setEmail("john.doe@example.com");
        artist.setAddress("123 Art St");
        artist.setGender("Male");
        
        // Add 3 paintings to John Doe:
        // Artwork 1: Title: "Sunset", Description: "A beautiful sunset painting", Category: "painting", Price: 200 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(Category.PAINTING);
        artwork1.setPrice(200.0);
        artist.addArtwork(artwork1);
        
        // Artwork 2: Title: "Still Life", Description: "A still life composition", Category: "painting", Price: 150 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(Category.PAINTING);
        artwork2.setPrice(150.0);
        artist.addArtwork(artwork2);
        
        // Artwork 3: Title: "Bronze Statue", Description: "A bronze statue of a horse", Category: "sculpture", Price: 500 CNY
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(500.0);
        artist.addArtwork(artwork3);
        
        // Expected Output: There are 2 painting artworks, 1 sculpture artwork, and 0 architecture artwork.
        String result = artist.countArtworksByCategory();
        assertEquals("Painting: 2, Sculpture: 1, Architecture: 0", result);
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Test Case 2: Count Artworks by Category with Multiple Categories
        // SetUp: Create an artist with name: "Jane Smith", ID: "A002", phone: "9876543210", email: "jane.smith@example.com", address: "456 Art Ave", gender: "Female"
        Artist artist = new Artist();
        artist.setName("Jane Smith");
        artist.setId("A002");
        artist.setPhoneNumber("9876543210");
        artist.setEmail("jane.smith@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender("Female");
        
        // Add 1 painting, 2 sculptures, and 1 architecture to Jane Smith:
        // Artwork 1: Title: "Abstract Colors", Description: "An abstract painting", Category: "painting", Price: 300 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(Category.PAINTING);
        artwork1.setPrice(300.0);
        artist.addArtwork(artwork1);
        
        // Artwork 2: Title: "David", Description: "A sculpture of David", Category: "sculpture", Price: 700 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(Category.SCULPTURE);
        artwork2.setPrice(700.0);
        artist.addArtwork(artwork2);
        
        // Artwork 3: Title: "Classic Statue", Description: "A classic statue", Category: "sculpture", Price: 600 CNY
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(600.0);
        artist.addArtwork(artwork3);
        
        // Artwork 4: Title: "Skyscraper", Description: "Modern architecture", Category: "architecture", Price: 900 CNY
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(Category.ARCHITECTURE);
        artwork4.setPrice(900.0);
        artist.addArtwork(artwork4);
        
        // Expected Output: There are 1 painting artwork, 2 sculpture artworks, and 1 architecture artwork.
        String result = artist.countArtworksByCategory();
        assertEquals("Painting: 1, Sculpture: 2, Architecture: 1", result);
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Test Case 3: No Artworks for an Artist
        // SetUp: Create an artist with name: "Emily Brown", ID: "A003", phone: "1112223333", email: "emily.brown@example.com", address: "789 Art Blvd", gender: "Female"
        Artist artist = new Artist();
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender("Female");
        
        // Do not add any artworks to Emily Brown.
        // Expected Output: There are 0 painting artwork, 0 sculpture artwork, and 0 architecture artwork.
        String result = artist.countArtworksByCategory();
        assertEquals("Painting: 0, Sculpture: 0, Architecture: 0", result);
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Test Case 4: Count Artworks with Only One Category
        // SetUp: Create an artist with name: "Michael Johnson", ID: "A004", phone: "4445556666", email: "michael.johnson@example.com", address: "123 Art Lane", gender: "Male"
        Artist artist = new Artist();
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender("Male");
        
        // Add 3 sculptures and no other category:
        // Artwork 1: Title: "Modern Art", Description: "A modern sculpture", Category: "sculpture", Price: 800 CNY
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(Category.SCULPTURE);
        artwork1.setPrice(800.0);
        artist.addArtwork(artwork1);
        
        // Artwork 2: Title: "Wooden Carving", Description: "A wooden sculpture", Category: "sculpture", Price: 1200 CNY
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(Category.SCULPTURE);
        artwork2.setPrice(1200.0);
        artist.addArtwork(artwork2);
        
        // Artwork 3: Title: "Stone Figure", Description: "A stone sculpture", Category: "sculpture", Price: 1000 CNY
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(Category.SCULPTURE);
        artwork3.setPrice(1000.0);
        artist.addArtwork(artwork3);
        
        // Expected Output: There are 0 painting artwork, 3 sculpture artworks, and 0 architecture artwork.
        String result = artist.countArtworksByCategory();
        assertEquals("Painting: 0, Sculpture: 3, Architecture: 0", result);
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Test Case 5: Count Artworks with Multiple Artists
        // SetUp: Create artist E (Alice White) with ID: "A005", and add artworks:
        Artist artistE = new Artist();
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        // Artwork 1: Title: "Landscapes", Category: "painting"
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory(Category.PAINTING);
        artistE.addArtwork(artworkE1);
        
        // Artwork 2: Title: "Steel Bridge", Category: "architecture"
        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory(Category.ARCHITECTURE);
        artistE.addArtwork(artworkE2);
        
        // SetUp: Create artist F (David Green) with ID: "A006", and add artworks:
        Artist artistF = new Artist();
        artistF.setName("David Green");
        artistF.setId("A006");
        
        // Artwork 1: Title: "Marble Sculpture", Category: "sculpture"
        Artwork artworkF1 = new Artwork();
        artworkF1.setTitle("Marble Sculpture");
        artworkF1.setCategory(Category.SCULPTURE);
        artistF.addArtwork(artworkF1);
        
        // Artwork 2: Title: "City Skyline", Category: "architecture"
        Artwork artworkF2 = new Artwork();
        artworkF2.setTitle("City Skyline");
        artworkF2.setCategory(Category.ARCHITECTURE);
        artistF.addArtwork(artworkF2);
        
        // Expected Output:
        // For Alice White: There are 1 painting artwork, 0 sculpture artwork, and 1 architecture artwork.
        String resultE = artistE.countArtworksByCategory();
        assertEquals("Painting: 1, Sculpture: 0, Architecture: 1", resultE);
        
        // For David Green: There are 0 painting artworks, 1 sculpture artwork, and 1 architecture artwork.
        String resultF = artistF.countArtworksByCategory();
        assertEquals("Painting: 0, Sculpture: 1, Architecture: 1", resultF);
    }
}