package edu.artgallery.artgallery2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.artgallery.ArtgalleryFactory;
import edu.artgallery.Artist;
import edu.artgallery.Artwork;
import edu.artgallery.ArtworkCategory;

public class CR1Test {
    
    private ArtgalleryFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = ArtgalleryFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_CalculateTotalArtworkPriceForSingleArtwork() {
        // Test Case 1: Calculate Total Artwork Price for a Single Artwork
        // SetUp: Create artist "Alice" with one artwork priced at 500 CNY
        Artist artist = factory.createArtist();
        artist.setName("Alice");
        artist.setId("A001");
        
        Artwork artwork = factory.createArtwork();
        artwork.setTitle("Sunset Painting");
        artwork.setDescription("A beautiful sunset painting");
        artwork.setPrice(500.0);
        artwork.setCategory(ArtworkCategory.PAINTING);
        artwork.setAuthor(artist);
        
        // Add artwork to artist using the addArtwork method
        artist.addArtwork(artwork);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Expected Output: Total artwork price = 500 CNY
        assertEquals(500.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalArtworkPriceWithMultipleArtworks() {
        // Test Case 2: Calculate Total Artwork Price with Multiple Artworks
        // SetUp: Create artist "Bob" with two artworks priced at 1200 and 800 CNY
        Artist artist = factory.createArtist();
        artist.setName("Bob");
        artist.setId("A002");
        
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Starry Night");
        artwork1.setPrice(1200.0);
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setAuthor(artist);
        
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Ocean View");
        artwork2.setPrice(800.0);
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setAuthor(artist);
        
        // Add both artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Expected Output: Total artwork price = 1200 + 800 = 2000 CNY
        assertEquals(2000.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalArtworkPriceWithZeroArtworks() {
        // Test Case 3: Calculate Total Artwork Price with Zero Artworks
        // SetUp: Create artist "Charlie" with no artworks
        Artist artist = factory.createArtist();
        artist.setName("Charlie");
        artist.setId("A003");
        
        // No artworks added to artist
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Expected Output: Total artwork price = 0 CNY
        assertEquals(0.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalArtworkPriceForArtworksWithDifferentPrices() {
        // Test Case 4: Calculate Total Artwork Price for Artworks with Different Prices
        // SetUp: Create artist "Diana" with three artworks priced at 2500, 1500, and 300 CNY
        Artist artist = factory.createArtist();
        artist.setName("Diana");
        artist.setId("A004");
        
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Marble Sculpture");
        artwork1.setPrice(2500.0);
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        artwork1.setAuthor(artist);
        
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setPrice(1500.0);
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        artwork2.setAuthor(artist);
        
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Abstract Art");
        artwork3.setPrice(300.0);
        artwork3.setCategory(ArtworkCategory.PAINTING);
        artwork3.setAuthor(artist);
        
        // Add all three artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Expected Output: Total artwork price = 2500 + 1500 + 300 = 4300 CNY
        assertEquals(4300.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalArtworkPriceForArtistWithArtworksOfZeroPrice() {
        // Test Case 5: Calculate Total Artwork Price for an Artist with Artworks of Zero Price
        // SetUp: Create artist "Eve" with two artworks: one free (0 CNY) and one priced at 1500 CNY
        Artist artist = factory.createArtist();
        artist.setName("Eve");
        artist.setId("A005");
        
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Sketch");
        artwork1.setPrice(0.0);
        artwork1.setCategory(ArtworkCategory.PAINTING);
        artwork1.setAuthor(artist);
        
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Portrait");
        artwork2.setPrice(1500.0);
        artwork2.setCategory(ArtworkCategory.PAINTING);
        artwork2.setAuthor(artist);
        
        // Add both artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Calculate total price
        double totalPrice = artist.calculateTotalPrice();
        
        // Expected Output: Total artwork price = 0 + 1500 = 1500 CNY
        assertEquals(1500.0, totalPrice, 0.001);
    }
}