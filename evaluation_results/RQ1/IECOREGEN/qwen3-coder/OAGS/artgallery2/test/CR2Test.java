package edu.artgallery.artgallery2.test;

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
        
        // Set membership for artist
        artist.setMembership(membership);
        
        // Test dates
        Date startDate = dateFormat.parse("2023-01-01");
        Date endDate = dateFormat.parse("2023-12-31");
        
        // Calculate reward points
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
        
        // Set membership for artist
        artist.setMembership(membership);
        
        // Test dates (membership has already expired)
        Date startDate = dateFormat.parse("2023-06-01");
        Date endDate = dateFormat.parse("2023-07-01");
        
        // Calculate reward points
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
        
        // Set membership for artist
        artist.setMembership(membership);
        
        // Test dates (within membership period)
        Date startDate = dateFormat.parse("2023-05-01");
        Date endDate = dateFormat.parse("2023-10-01");
        
        // Calculate reward points
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
        Membership membership1 = factory.createMembership();
        membership1.setID("M004");
        membership1.setStartDate(dateFormat.parse("2023-01-05"));
        membership1.setEndDate(dateFormat.parse("2023-06-01"));
        membership1.setRewardPoint(150);
        membership1.setType(MembershipType.INDIVIDUAL);
        
        // Set first membership for artist
        artist.setMembership(membership1);
        
        // Test dates (within first membership period)
        Date startDate = dateFormat.parse("2023-01-06");
        Date endDate = dateFormat.parse("2023-01-21");
        
        // Calculate reward points with first membership
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output with first membership
        assertEquals("Reward points should be 150 for first membership period", 150, result);
        
        // Create second membership using factory pattern
        Membership membership2 = factory.createMembership();
        membership2.setID("M005");
        membership2.setStartDate(dateFormat.parse("2023-02-01"));
        membership2.setEndDate(dateFormat.parse("2024-02-01"));
        membership2.setRewardPoint(100);
        membership2.setType(MembershipType.AGENCY_AFFILIATE);
        
        // Update to second membership
        artist.setMembership(membership2);
        
        // Re-test with same dates (now with second membership, which doesn't cover the period)
        int result2 = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output with second membership
        assertEquals("Reward points should be 0 for second membership (period not covered)", 0, result2);
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() throws Exception {
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
        membership.setEndDate(dateFormat.parse("2023-01-01")); // Expires on this date
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        
        // Set membership for artist
        artist.setMembership(membership);
        
        // Test dates - single day after expiration
        Date startDate = dateFormat.parse("2023-01-02");
        Date endDate = dateFormat.parse("2023-01-02");
        
        // Calculate reward points
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output
        assertEquals("Reward points should be 0 for expired membership", 0, result);
    }
}