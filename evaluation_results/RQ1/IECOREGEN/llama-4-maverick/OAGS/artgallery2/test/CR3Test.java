package edu.artgallery.artgallery2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.artgallery.ArtgalleryFactory;
import edu.artgallery.Artist;
import edu.artgallery.Artwork;
import edu.artgallery.ArtworkCategory;
import edu.artgallery.Gender;
import java.util.Map;

public class CR3Test {
    
    private ArtgalleryFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = ArtgalleryFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_CountArtworksByCategoryForSingleArtist() {
        // Create artist John Doe
        Artist artist = factory.createArtist();
        artist.setName("John Doe");
        artist.setId("A001");
        artist.setPhoneNumber("1234567890");
        artist.setEmail("john.doe@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.MALE);
        
        // Create and add 3 artworks: 2 paintings, 1 sculpture
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(200.0);
        artwork1.setAuthor(artist);
        artist.addArtwork(artwork1);
        
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setPrice(150.0);
        artwork2.setAuthor(artist);
        artist.addArtwork(artwork2);
        
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(500.0);
        artwork3.setAuthor(artist);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> categoryCounts = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 2 painting artworks", Integer.valueOf(2), categoryCounts.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 1 sculpture artwork", Integer.valueOf(1), categoryCounts.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artwork", Integer.valueOf(0), categoryCounts.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase2_CountArtworksByCategoryWithMultipleCategories() {
        // Create artist Jane Smith
        Artist artist = factory.createArtist();
        artist.setName("Jane Smith");
        artist.setId("A002");
        artist.setPhoneNumber("9876543210");
        artist.setEmail("jane.smith@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.FEMALE);
        
        // Create and add 4 artworks: 1 painting, 2 sculptures, 1 architecture
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(300.0);
        artwork1.setAuthor(artist);
        artist.addArtwork(artwork1);
        
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(700.0);
        artwork2.setAuthor(artist);
        artist.addArtwork(artwork2);
        
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(600.0);
        artwork3.setAuthor(artist);
        artist.addArtwork(artwork3);
        
        Artwork artwork4 = factory.createArtwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setPrice(900.0);
        artwork4.setAuthor(artist);
        artist.addArtwork(artwork4);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> categoryCounts = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 1 painting artwork", Integer.valueOf(1), categoryCounts.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 2 sculpture artworks", Integer.valueOf(2), categoryCounts.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 1 architecture artwork", Integer.valueOf(1), categoryCounts.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Create artist Emily Brown with no artworks
        Artist artist = factory.createArtist();
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> categoryCounts = artist.countArtworksByCategory();
        
        // Verify all counts are zero
        assertEquals("Should have 0 painting artworks", Integer.valueOf(0), categoryCounts.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 0 sculpture artworks", Integer.valueOf(0), categoryCounts.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artworks", Integer.valueOf(0), categoryCounts.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase4_CountArtworksWithOnlyOneCategory() {
        // Create artist Michael Johnson
        Artist artist = factory.createArtist();
        artist.setName("Michael Johnson");
        artist.setId("A004");
        artist.setPhoneNumber("4445556666");
        artist.setEmail("michael.johnson@example.com");
        artist.setAddress("123 Art Lane");
        artist.setGender(Gender.MALE);
        
        // Create and add 3 sculptures only
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        artwork1.setPrice(800.0);
        artwork1.setAuthor(artist);
        artist.addArtwork(artwork1);
        
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(1200.0);
        artwork2.setAuthor(artist);
        artist.addArtwork(artwork2);
        
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(1000.0);
        artwork3.setAuthor(artist);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> categoryCounts = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 0 painting artworks", Integer.valueOf(0), categoryCounts.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 3 sculpture artworks", Integer.valueOf(3), categoryCounts.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artworks", Integer.valueOf(0), categoryCounts.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Create artist Alice White
        Artist artist1 = factory.createArtist();
        artist1.setName("Alice White");
        artist1.setId("A005");
        
        // Add artworks to Alice White
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Landscapes");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setAuthor(artist1);
        artist1.addArtwork(artwork1);
        
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Steel Bridge");
        artwork2.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork2.setAuthor(artist1);
        artist1.addArtwork(artwork2);
        
        // Create artist David Green
        Artist artist2 = factory.createArtist();
        artist2.setName("David Green");
        artist2.setId("A006");
        
        // Add artworks to David Green
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Marble Sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setAuthor(artist2);
        artist2.addArtwork(artwork3);
        
        Artwork artwork4 = factory.createArtwork();
        artwork4.setTitle("City Skyline");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setAuthor(artist2);
        artist2.addArtwork(artwork4);
        
        // Count artworks by category for Alice White
        Map<ArtworkCategory, Integer> categoryCounts1 = artist1.countArtworksByCategory();
        assertEquals("Alice White should have 1 painting artwork", Integer.valueOf(1), categoryCounts1.get(ArtworkCategory.PAINTING));
        assertEquals("Alice White should have 0 sculpture artworks", Integer.valueOf(0), categoryCounts1.get(ArtworkCategory.SCULPTURE));
        assertEquals("Alice White should have 1 architecture artwork", Integer.valueOf(1), categoryCounts1.get(ArtworkCategory.ARCHITECTURE));
        
        // Count artworks by category for David Green
        Map<ArtworkCategory, Integer> categoryCounts2 = artist2.countArtworksByCategory();
        assertEquals("David Green should have 0 painting artworks", Integer.valueOf(0), categoryCounts2.get(ArtworkCategory.PAINTING));
        assertEquals("David Green should have 1 sculpture artwork", Integer.valueOf(1), categoryCounts2.get(ArtworkCategory.SCULPTURE));
        assertEquals("David Green should have 1 architecture artwork", Integer.valueOf(1), categoryCounts2.get(ArtworkCategory.ARCHITECTURE));
    }
}