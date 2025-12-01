package edu.artgallery.artgallery5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.artgallery.ArtgalleryFactory;
import edu.artgallery.Artist;
import edu.artgallery.Artwork;
import edu.artgallery.ArtworkCategory;
import edu.artgallery.Gender;
import edu.artgallery.Membership;
import edu.artgallery.MembershipType;

import java.util.Date;
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
        
        // Create membership for the artist
        Membership membership = factory.createMembership();
        membership.setID("M001");
        membership.setStartDate(new Date());
        membership.setEndDate(new Date(System.currentTimeMillis() + 86400000L)); // Tomorrow
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Create and add 3 artworks: 2 paintings, 1 sculpture
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(200.0);
        artist.addArtwork(artwork1);
        
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setPrice(150.0);
        artist.addArtwork(artwork2);
        
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(500.0);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 2 painting artworks", Integer.valueOf(2), result.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 1 sculpture artwork", Integer.valueOf(1), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artwork", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
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
        
        // Create membership for the artist
        Membership membership = factory.createMembership();
        membership.setID("M002");
        membership.setStartDate(new Date());
        membership.setEndDate(new Date(System.currentTimeMillis() + 86400000L));
        membership.setRewardPoint(150);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Create and add 4 artworks: 1 painting, 2 sculptures, 1 architecture
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(300.0);
        artist.addArtwork(artwork1);
        
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(700.0);
        artist.addArtwork(artwork2);
        
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(600.0);
        artist.addArtwork(artwork3);
        
        Artwork artwork4 = factory.createArtwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setPrice(900.0);
        artist.addArtwork(artwork4);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 1 painting artwork", Integer.valueOf(1), result.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 2 sculpture artworks", Integer.valueOf(2), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 1 architecture artwork", Integer.valueOf(1), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase3_NoArtworksForAnArtist() {
        // Create artist Emily Brown
        Artist artist = factory.createArtist();
        artist.setName("Emily Brown");
        artist.setId("A003");
        artist.setPhoneNumber("1112223333");
        artist.setEmail("emily.brown@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // Create membership for the artist
        Membership membership = factory.createMembership();
        membership.setID("M003");
        membership.setStartDate(new Date());
        membership.setEndDate(new Date(System.currentTimeMillis() + 86400000L));
        membership.setRewardPoint(50);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Do not add any artworks
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify all categories have 0 artworks
        assertEquals("Should have 0 painting artwork", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 0 sculpture artwork", Integer.valueOf(0), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artwork", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
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
        
        // Create membership for the artist
        Membership membership = factory.createMembership();
        membership.setID("M004");
        membership.setStartDate(new Date());
        membership.setEndDate(new Date(System.currentTimeMillis() + 86400000L));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY_AFFILIATE);
        artist.setMembership(membership);
        
        // Create and add 3 sculptures only
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        artwork1.setPrice(800.0);
        artist.addArtwork(artwork1);
        
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(1200.0);
        artist.addArtwork(artwork2);
        
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(1000.0);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> result = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 0 painting artwork", Integer.valueOf(0), result.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 3 sculpture artworks", Integer.valueOf(3), result.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artwork", Integer.valueOf(0), result.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Create artist Alice White
        Artist artist1 = factory.createArtist();
        artist1.setName("Alice White");
        artist1.setId("A005");
        
        // Create membership for Alice White
        Membership membership1 = factory.createMembership();
        membership1.setID("M005");
        membership1.setStartDate(new Date());
        membership1.setEndDate(new Date(System.currentTimeMillis() + 86400000L));
        membership1.setRewardPoint(120);
        membership1.setType(MembershipType.INDIVIDUAL);
        artist1.setMembership(membership1);
        
        // Add artworks for Alice White
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Landscapes");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artist1.addArtwork(artwork1);
        
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Steel Bridge");
        artwork2.setCategory(ArtworkCategory.ARCHITECTURE);
        artist1.addArtwork(artwork2);
        
        // Create artist David Green
        Artist artist2 = factory.createArtist();
        artist2.setName("David Green");
        artist2.setId("A006");
        
        // Create membership for David Green
        Membership membership2 = factory.createMembership();
        membership2.setID("M006");
        membership2.setStartDate(new Date());
        membership2.setEndDate(new Date(System.currentTimeMillis() + 86400000L));
        membership2.setRewardPoint(180);
        membership2.setType(MembershipType.AGENCY);
        artist2.setMembership(membership2);
        
        // Add artworks for David Green
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Marble Sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artist2.addArtwork(artwork3);
        
        Artwork artwork4 = factory.createArtwork();
        artwork4.setTitle("City Skyline");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artist2.addArtwork(artwork4);
        
        // Count artworks by category for Alice White
        Map<ArtworkCategory, Integer> result1 = artist1.countArtworksByCategory();
        
        // Verify counts for Alice White
        assertEquals("Alice White should have 1 painting artwork", Integer.valueOf(1), result1.get(ArtworkCategory.PAINTING));
        assertEquals("Alice White should have 0 sculpture artwork", Integer.valueOf(0), result1.get(ArtworkCategory.SCULPTURE));
        assertEquals("Alice White should have 1 architecture artwork", Integer.valueOf(1), result1.get(ArtworkCategory.ARCHITECTURE));
        
        // Count artworks by category for David Green
        Map<ArtworkCategory, Integer> result2 = artist2.countArtworksByCategory();
        
        // Verify counts for David Green
        assertEquals("David Green should have 0 painting artwork", Integer.valueOf(0), result2.get(ArtworkCategory.PAINTING));
        assertEquals("David Green should have 1 sculpture artwork", Integer.valueOf(1), result2.get(ArtworkCategory.SCULPTURE));
        assertEquals("David Green should have 1 architecture artwork", Integer.valueOf(1), result2.get(ArtworkCategory.ARCHITECTURE));
    }
}