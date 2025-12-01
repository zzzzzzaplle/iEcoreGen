package edu.artgallery.artgallery3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.artgallery.ArtgalleryFactory;
import edu.artgallery.Artist;
import edu.artgallery.Artwork;
import edu.artgallery.Membership;
import java.util.Date;

public class CR1Test {
    
    private ArtgalleryFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory for creating model objects
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
        
        // Add artwork to artist's collection
        artist.getArtwork().add(artwork);
        artwork.setAuthor(artist);
        
        // Calculate total price and verify
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total price for single artwork should be 500 CNY", 500.0, totalPrice, 0.001);
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
        artist.getArtwork().add(artwork1);
        artwork1.setAuthor(artist);
        
        // Create second artwork titled "Ocean View" with price: 800 CNY
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Ocean View");
        artwork2.setPrice(800.0);
        artist.getArtwork().add(artwork2);
        artwork2.setAuthor(artist);
        
        // Calculate total price and verify
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total price for multiple artworks should be 2000 CNY", 2000.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalArtworkPriceWithZeroArtworks() {
        // Test Case 3: Calculate Total Artwork Price with Zero Artworks
        // Create an artist named "Charlie" with ID: A003
        Artist artist = factory.createArtist();
        artist.setName("Charlie");
        artist.setId("A003");
        
        // Artist has no artworks listed
        // Calculate total price and verify
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total price for artist with no artworks should be 0 CNY", 0.0, totalPrice, 0.001);
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
        artist.getArtwork().add(artwork1);
        artwork1.setAuthor(artist);
        
        // Create second artwork titled "Wooden Carving" with price: 1500 CNY
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Wooden Carving");
        artwork2.setPrice(1500.0);
        artist.getArtwork().add(artwork2);
        artwork2.setAuthor(artist);
        
        // Create third artwork titled "Abstract Art" with price: 300 CNY
        Artwork artwork3 = factory.createArtwork();
        artwork3.setTitle("Abstract Art");
        artwork3.setPrice(300.0);
        artist.getArtwork().add(artwork3);
        artwork3.setAuthor(artist);
        
        // Calculate total price and verify
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total price for artworks with different prices should be 4300 CNY", 4300.0, totalPrice, 0.001);
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
        artist.getArtwork().add(artwork1);
        artwork1.setAuthor(artist);
        
        // Create second artwork titled "Portrait" with price: 1500 CNY
        Artwork artwork2 = factory.createArtwork();
        artwork2.setTitle("Portrait");
        artwork2.setPrice(1500.0);
        artist.getArtwork().add(artwork2);
        artwork2.setAuthor(artist);
        
        // Calculate total price and verify
        double totalPrice = artist.calculateTotalPrice();
        assertEquals("Total price including zero-priced artwork should be 1500 CNY", 1500.0, totalPrice, 0.001);
    }
}