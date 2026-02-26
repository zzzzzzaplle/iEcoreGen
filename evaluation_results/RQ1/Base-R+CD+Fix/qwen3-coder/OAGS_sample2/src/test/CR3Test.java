import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;

public class CR3Test {
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // SetUp
        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setId("A001");
        artist.setPhoneNumber("1234567890");
        artist.setEmail("john.doe@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.MALE);
        
        // Add 3 artworks to John Doe
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(200.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setPrice(150.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(500.0);
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Execute
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify
        assertEquals("There should be 2 painting artworks", Integer.valueOf(2), result.get(ArtworkCategory.PAINTING));
        assertEquals("There should be 1 sculpture artwork", Integer.valueOf(1), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("There should be 0 architecture artwork", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // SetUp
        Artist artist = new Artist();
        artist.setName("Jane Smith");
        artist.setId("A002");
        artist.setPhoneNumber("9876543210");
        artist.setEmail("jane.smith@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.FEMALE);
        
        // Add 1 painting, 2 sculptures, and 1 architecture to Jane Smith
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(300.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(700.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(600.0);
        
        Artwork artwork4 = new Artwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setPrice(900.0);
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        artist.addArtwork(artwork4);
        
        // Execute
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify
        assertEquals("There should be 1 painting artwork", Integer.valueOf(1), result.get(ArtworkCategory.PAINTING));
        assertEquals("There should be 2 sculpture artworks", Integer.valueOf(2), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("There should be 1 architecture artwork", Integer.valueOf(1), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // SetUp
        Artist artist = new Artist();
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        // Do not add any artworks to Emily Brown
        
        // Execute
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify
        assertEquals("There should be 0 painting artwork", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("There should be 0 sculpture artwork", Integer.valueOf(0), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("There should be 0 architecture artwork", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // SetUp
        Artist artist = new Artist();
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender(Gender.MALE);
        
        // Add 3 sculptures and no other category
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        artwork1.setPrice(800.0);
        
        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(1200.0);
        
        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(1000.0);
        
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Execute
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify
        assertEquals("There should be 0 painting artwork", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("There should be 3 sculpture artworks", Integer.valueOf(3), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("There should be 0 architecture artwork", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // SetUp Artist E (Alice White)
        Artist artistE = new Artist();
        artistE.setName("Alice White");
        artistE.setId("A005");
        
        Artwork artworkE1 = new Artwork();
        artworkE1.setTitle("Landscapes");
        artworkE1.setCategory(ArtworkCategory.PAINTING);
        
        Artwork artworkE2 = new Artwork();
        artworkE2.setTitle("Steel Bridge");
        artworkE2.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistE.addArtwork(artworkE1);
        artistE.addArtwork(artworkE2);
        
        // SetUp Artist F (David Green)
        Artist artistF = new Artist();
        artistF.setName("David Green");
        artistF.setId("A006");
        
        Artwork artworkF1 = new Artwork();
        artworkF1.setTitle("Marble Sculpture");
        artworkF1.setCategory(ArtworkCategory.SCULPTURE);
        
        Artwork artworkF2 = new Artwork();
        artworkF2.setTitle("City Skyline");
        artworkF2.setCategory(ArtworkCategory.ARCHITECTURE);
        
        artistF.addArtwork(artworkF1);
        artistF.addArtwork(artworkF2);
        
        // Execute for both artists
        Map<ArtworkCategory, Integer> resultE = artistE.countArtworksByCategory();
        Map<ArtworkCategory, Integer> resultF = artistF.countArtworksByCategory();
        
        // Verify Artist E (Alice White)
        assertEquals("For Alice White: There should be 1 painting artwork", Integer.valueOf(1), resultE.get(ArtworkCategory.PAINTING));
        assertEquals("For Alice White: There should be 0 sculpture artwork", Integer.valueOf(0), resultE.get(ArtworkCategory.SCULPTURE));
        assertEquals("For Alice White: There should be 1 architecture artwork", Integer.valueOf(1), resultE.get(ArtworkCategory.ARCHITECTURE));
        
        // Verify Artist F (David Green)
        assertEquals("For David Green: There should be 0 painting artwork", Integer.valueOf(0), resultF.get(ArtworkCategory.PAINTING));
        assertEquals("For David Green: There should be 1 sculpture artwork", Integer.valueOf(1), resultF.get(ArtworkCategory.SCULPTURE));
        assertEquals("For David Green: There should be 1 architecture artwork", Integer.valueOf(1), resultF.get(ArtworkCategory.ARCHITECTURE));
    }
}