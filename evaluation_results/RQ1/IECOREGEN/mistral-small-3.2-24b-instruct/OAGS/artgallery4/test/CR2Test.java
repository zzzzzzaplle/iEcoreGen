package edu.artgallery.artgallery4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.artgallery.ArtgalleryFactory;
import edu.artgallery.Artist;
import edu.artgallery.Membership;
import edu.artgallery.Gender;
import edu.artgallery.MembershipType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private ArtgalleryFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = ArtgalleryFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() throws Exception {
        // Test Case 1: "Valid Individual Membership Reward Points Calculation"
        
        // Create artist using factory pattern
        Artist artist = factory.createArtist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        // Create membership using factory pattern
        Membership membership = factory.createMembership();
        membership.setID("M001");
        membership.setStartDate(dateFormat.parse("2022-01-01"));
        membership.setEndDate(dateFormat.parse("2024-01-01"));
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        
        // Set up containment relationship
        artist.setMembership(membership);
        
        // Test input dates
        Date startDate = dateFormat.parse("2023-01-01");
        Date endDate = dateFormat.parse("2023-12-31");
        
        // Execute the operation
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output
        assertEquals("Reward points should be 100 for valid membership period", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() throws Exception {
        // Test Case 2: "Invalid Membership Period Calculation"
        
        // Create artist using factory pattern
        Artist artist = factory.createArtist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        // Create membership using factory pattern
        Membership membership = factory.createMembership();
        membership.setID("M002");
        membership.setStartDate(dateFormat.parse("2022-03-01"));
        membership.setEndDate(dateFormat.parse("2022-09-01"));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        
        // Set up containment relationship
        artist.setMembership(membership);
        
        // Test input dates (period after membership ended)
        Date startDate = dateFormat.parse("2023-06-01");
        Date endDate = dateFormat.parse("2023-07-01");
        
        // Execute the operation
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output
        assertEquals("Reward points should be 0 for invalid membership period", 0, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws Exception {
        // Test Case 3: "Agency Membership Reward Points Calculation"
        
        // Create artist using factory pattern
        Artist artist = factory.createArtist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // Create membership using factory pattern
        Membership membership = factory.createMembership();
        membership.setID("M003");
        membership.setStartDate(dateFormat.parse("2023-01-01"));
        membership.setEndDate(dateFormat.parse("2024-01-01"));
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        
        // Set up containment relationship
        artist.setMembership(membership);
        
        // Test input dates
        Date startDate = dateFormat.parse("2023-05-01");
        Date endDate = dateFormat.parse("2023-10-01");
        
        // Execute the operation
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output
        assertEquals("Reward points should be 300 for valid agency membership period", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws Exception {
        // Test Case 4: "Multiple Memberships Consideration"
        // Note: Artist can only have one membership at a time, so we test with the first membership
        
        // Create artist using factory pattern
        Artist artist = factory.createArtist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        // Create first membership using factory pattern
        Membership membership = factory.createMembership();
        membership.setID("M004");
        membership.setStartDate(dateFormat.parse("2023-01-05"));
        membership.setEndDate(dateFormat.parse("2023-06-01"));
        membership.setRewardPoint(150);
        membership.setType(MembershipType.INDIVIDUAL);
        
        // Set up containment relationship with first membership
        artist.setMembership(membership);
        
        // Test input dates that fall within first membership period
        Date startDate = dateFormat.parse("2023-01-06");
        Date endDate = dateFormat.parse("2023-01-21");
        
        // Execute the operation
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output - should use the first membership's reward points
        assertEquals("Reward points should be 150 for the first valid membership", 150, result);
    }
    
    @Test
    public void testCase5_BoundaryConditionMembershipExpiration() throws Exception {
        // Test Case 5: "Boundary Condition - Membership Expiration"
        
        // Create artist using factory pattern
        Artist artist = factory.createArtist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        // Create membership using factory pattern
        Membership membership = factory.createMembership();
        membership.setID("M006");
        membership.setStartDate(dateFormat.parse("2022-01-01"));
        membership.setEndDate(dateFormat.parse("2023-01-01"));
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        
        // Set up containment relationship
        artist.setMembership(membership);
        
        // Test input date - exactly on the day after membership expiration
        Date startDate = dateFormat.parse("2023-01-02");
        Date endDate = dateFormat.parse("2023-01-02");
        
        // Execute the operation
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output - membership expired on end date, so next day should return 0
        assertEquals("Reward points should be 0 for period after membership expiration", 0, result);
    }
}