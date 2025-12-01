package edu.artgallery.artgallery5.test;

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
        // Initialize the Ecore factory for object creation
        factory = ArtgalleryFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_CalculateTotalArtworkPriceForSingleArtwork() {
        // Test Case 1: Calculate Total Artwork Price for a Single Artwork
        // Create an artist named "Alice" with ID: A001
        Artist artist = factory.createArtist();
        artist.setName("Alice");
        artist.setId("A001");
        
        // Create an artwork titled "Sunset Painting" with description "A beautiful sunset painting" and price: 500 CNY
        Artwork artwork = factory.createArtwork();
        artwork.setTitle("Sunset Painting");
        artwork.setDescription("A beautiful sunset painting");
        artwork.setPrice(500.0);
        artwork.setCategory(ArtworkCategory.PAINTING);
        
        // Add artwork to artist using the addArtwork method
        artist.addArtwork(artwork);
        
        // Calculate total price and verify expected result
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total price should be 500 CNY for single artwork", 500.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalArtworkPriceWithMultipleArtworks() {
        // Test Case 2: Calculate Total Artwork Price with Multiple Artworks
        // Create an artist named "Bob" with ID: A002
        Artist artist = factory.createArtist();
        artist.setName("Bob");
        artist.setId("A002");
        
        // Create first artwork titled "Starry Night" with price: 1200 CNY
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Starry Night");
        artwork1.setPrice(1200.0);
        artwork1.setCategory(ArtworkCategory.PAINTING);
        
        // Create second artwork titled "Ocean View" with price: 800 CNY
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Ocean View");
        artwork2.setPrice(800.0);
        artwork2.setCategory(ArtworkCategory.PAINTING);
        
        // Add both artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Calculate total price and verify expected result
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total price should be 2000 CNY for two artworks", 2000.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalArtworkPriceWithZeroArtworks() {
        // Test Case 3: Calculate Total Artwork Price with Zero Artworks
        // Create an artist named "Charlie" with ID: A003
        Artist artist = factory.createArtist();
        artist.setName("Charlie");
        artist.setId("A003");
        
        // The artist "Charlie" has no artworks listed
        // Calculate total price and verify expected result
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total price should be 0 CNY when artist has no artworks", 0.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalArtworkPriceForArtworksWithDifferentPrices() {
        // Test Case 4: Calculate Total Artwork Price for Artworks with Different Prices
        // Create an artist named "Diana" with ID: A004
        Artist artist = factory.createArtist();
        artist.setName("Diana");
        artist.setId("A004");
        
        // Create first artwork titled "Marble Sculpture" with price: 2500 CNY
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Marble Sculpture");
        artwork1.setPrice(2500.0);
        artwork1.setCategory(ArtworkCategory.SCULPTURE);
        
        // Create second artwork titled "Wooden Carving" with price: 1500 CNY
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setPrice(1500.0);
        artwork2.setCategory(ArtworkCategory.SCULPTURE);
        
        // Create third artwork titled "Abstract Art" with price: 300 CNY
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Abstract Art");
        artwork3.setPrice(300.0);
        artwork3.setCategory(ArtworkCategory.PAINTING);
        
        // Add all three artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        artist.addArtwork(artwork3);
        
        // Calculate total price and verify expected result
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total price should be 4300 CNY for three artworks with different prices", 4300.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalArtworkPriceForArtistWithArtworksOfZeroPrice() {
        // Test Case 5: Calculate Total Artwork Price for an Artist with Artworks of Zero Price
        // Create an artist named "Eve" with ID: A005
        Artist artist = factory.createArtist();
        artist.setName("Eve");
        artist.setId("A005");
        
        // Create first artwork titled "Sketch" with price: 0 CNY
        Artwork artwork1 = factory.createArtwork();
        artwork1.setTitle("Sketch");
        artwork1.setPrice(0.0);
        artwork1.setCategory(ArtworkCategory.PAINTING);
        
        // Create second artwork titled "Portrait" with price: 1500 CNY
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Portrait");
        artwork2.setPrice(1500.0);
        artwork2.setCategory(ArtworkCategory.PAINTING);
        
        // Add both artworks to artist
        artist.addArtwork(artwork1);
        artist.addArtwork(artwork2);
        
        // Calculate total price and verify expected result
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total price should be 1500 CNY when one artwork has zero price", 1500.0, totalPrice, 0.001);
    }
}