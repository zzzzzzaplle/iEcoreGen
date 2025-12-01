package edu.artgallery.artgallery4.test;

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
        // Initialize the Ecore factory for object creation
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
        
        // Create membership for artist
        Membership membership = factory.createMembership();
        membership.setID("M001");
        membership.setStartDate(new Date());
        membership.setEndDate(new Date(System.currentTimeMillis() + 86400000L)); // Tomorrow
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Create artwork 1: painting
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Sunset");
        artwork1.setDescription("A beautiful sunset painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(200.0);
        artist.addArtwork(artwork1);
        
        // Create artwork 2: painting
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Still Life");
        artwork2.setDescription("A still life composition");
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setPrice(150.0);
        artist.addArtwork(artwork2);
        
        // Create artwork 3: sculpture
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Bronze Statue");
        artwork3.setDescription("A bronze statue of a horse");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(500.0);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> categoryCount = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 2 painting artworks", Integer.valueOf(2), categoryCount.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 1 sculpture artwork", Integer.valueOf(1), categoryCount.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artworks", Integer.valueOf(0), categoryCount.get(ArtworkCategory.ARCHITECTURE));
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
        
        // Create membership for artist
        Membership membership = factory.createMembership();
        membership.setID("M002");
        membership.setStartDate(new Date());
        membership.setEndDate(new Date(System.currentTimeMillis() + 86400000L));
        membership.setRewardPoint(150);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Create artwork 1: painting
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Abstract Colors");
        artwork1.setDescription("An abstract painting");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(300.0);
        artist.addArtwork(artwork1);
        
        // Create artwork 2: sculpture
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("David");
        artwork2.setDescription("A sculpture of David");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(700.0);
        artist.addArtwork(artwork2);
        
        // Create artwork 3: sculpture
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Classic Statue");
        artwork3.setDescription("A classic statue");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(600.0);
        artist.addArtwork(artwork3);
        
        // Create artwork 4: architecture
        Artwork artwork4 = factory.createArtwork();
        artwork4.setTitle("Skyscraper");
        artwork4.setDescription("Modern architecture");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setPrice(900.0);
        artist.addArtwork(artwork4);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> categoryCount = artist.countArtworksByCategory();
        
        // Verify expected counts
        assertEquals("Should have 1 painting artwork", Integer.valueOf(1), categoryCount.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 2 sculpture artworks", Integer.valueOf(2), categoryCount.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 1 architecture artwork", Integer.valueOf(1), categoryCount.get(ArtworkCategory.ARCHITECTURE));
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
        
        // Create membership for artist
        Membership membership = factory.createMembership();
        membership.setID("M003");
        membership.setStartDate(new Date());
        membership.setEndDate(new Date(System.currentTimeMillis() + 86400000L));
        membership.setRewardPoint(50);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // No artworks added
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> categoryCount = artist.countArtworksByCategory();
        
        // Verify all categories have 0 artworks
        assertEquals("Should have 0 painting artworks", Integer.valueOf(0), categoryCount.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 0 sculpture artworks", Integer.valueOf(0), categoryCount.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artworks", Integer.valueOf(0), categoryCount.get(ArtworkCategory.ARCHITECTURE));
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
        
        // Create membership for artist
        Membership membership = factory.createMembership();
        membership.setID("M004");
        membership.setStartDate(new Date());
        membership.setEndDate(new Date(System.currentTimeMillis() + 86400000L));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY_AFFILIATE);
        artist.setMembership(membership);
        
        // Create artwork 1: sculpture
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Modern Art");
        artwork1.setDescription("A modern sculpture");
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        artwork1.setPrice(800.0);
        artist.addArtwork(artwork1);
        
        // Create artwork 2: sculpture
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setDescription("A wooden sculpture");
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setPrice(1200.0);
        artist.addArtwork(artwork2);
        
        // Create artwork 3: sculpture
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Stone Figure");
        artwork3.setDescription("A stone sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(1000.0);
        artist.addArtwork(artwork3);
        
        // Count artworks by category
        Map<ArtworkCategory, Integer> categoryCount = artist.countArtworksByCategory();
        
        // Verify expected counts - only sculpture category should be present
        assertEquals("Should have 0 painting artworks", Integer.valueOf(0), categoryCount.get(ArtworkCategory.PAINTING));
        assertEquals("Should have 3 sculpture artworks", Integer.valueOf(3), categoryCount.get(ArtworkCategory.SCULPTURE));
        assertEquals("Should have 0 architecture artworks", Integer.valueOf(0), categoryCount.get(ArtworkCategory.ARCHITECTURE));
    }
    
    @Test
    public void testCase5_CountArtworksWithMultipleArtists() {
        // Create artist Alice White
        Artist artist1 = factory.createArtist();
        artist1.setName("Alice White");
        artist1.setId("A005");
        
        // Create membership for artist 1
        Membership membership1 = factory.createMembership();
        membership1.setID("M005");
        membership1.setStartDate(new Date());
        membership1.setEndDate(new Date(System.currentTimeMillis() + 86400000L));
        membership1.setRewardPoint(120);
        membership1.setType(MembershipType.INDIVIDUAL);
        artist1.setMembership(membership1);
        
        // Create artwork 1 for Alice: painting
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Landscapes");
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setPrice(400.0);
        artist1.addArtwork(artwork1);
        
        // Create artwork 2 for Alice: architecture
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Steel Bridge");
        artwork2.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork2.setPrice(1500.0);
        artist1.addArtwork(artwork2);
        
        // Create artist David Green
        Artist artist2 = factory.createArtist();
        artist2.setName("David Green");
        artist2.setId("A006");
        
        // Create membership for artist 2
        Membership membership2 = factory.createMembership();
        membership2.setID("M006");
        membership2.setStartDate(new Date());
        membership2.setEndDate(new Date(System.currentTimeMillis() + 86400000L));
        membership2.setRewardPoint(180);
        membership2.setType(MembershipType.AGENCY);
        artist2.setMembership(membership2);
        
        // Create artwork 1 for David: sculpture
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Marble Sculpture");
        artwork3.setCategory(ArtworkCategory.SCULPTURE);
        artwork3.setPrice(2000.0);
        artist2.addArtwork(artwork3);
        
        // Create artwork 2 for David: architecture
        Artwork artwork4 = factory.createArtwork();
        artwork4.setTitle("City Skyline");
        artwork4.setCategory(ArtworkCategory.ARCHITECTURE);
        artwork4.setPrice(1800.0);
        artist2.addArtwork(artwork4);
        
        // Count artworks by category for Alice White
        Map<ArtworkCategory, Integer> categoryCount1 = artist1.countArtworksByCategory();
        
        // Verify expected counts for Alice White
        assertEquals("Alice should have 1 painting artwork", Integer.valueOf(1), categoryCount1.get(ArtworkCategory.PAINTING));
        assertEquals("Alice should have 0 sculpture artworks", Integer.valueOf(0), categoryCount1.get(ArtworkCategory.SCULPTURE));
        assertEquals("Alice should have 1 architecture artwork", Integer.valueOf(1), categoryCount1.get(ArtworkCategory.ARCHITECTURE));
        
        // Count artworks by category for David Green
        Map<ArtworkCategory, Integer> categoryCount2 = artist2.countArtworksByCategory();
        
        // Verify expected counts for David Green
        assertEquals("David should have 0 painting artworks", Integer.valueOf(0), categoryCount2.get(ArtworkCategory.PAINTING));
        assertEquals("David should have 1 sculpture artwork", Integer.valueOf(1), categoryCount2.get(ArtworkCategory.SCULPTURE));
        assertEquals("David should have 1 architecture artwork", Integer.valueOf(1), categoryCount2.get(ArtworkCategory.ARCHITECTURE));
    }
}