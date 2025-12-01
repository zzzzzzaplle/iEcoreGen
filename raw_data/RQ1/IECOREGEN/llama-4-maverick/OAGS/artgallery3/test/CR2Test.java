package edu.artgallery.artgallery3.test;

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
        factory = ArtgalleryFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() throws Exception {
        // Create artist
        Artist artist = factory.createArtist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        // Create and assign membership
        Membership membership = factory.createMembership();
        membership.setID("M001");
        membership.setStartDate(dateFormat.parse("2022-01-01"));
        membership.setEndDate(dateFormat.parse("2024-01-01"));
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Test reward points calculation
        Date startDate = dateFormat.parse("2023-01-01");
        Date endDate = dateFormat.parse("2023-12-31");
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        assertEquals("Reward points should be 100 for valid membership period", 100, result);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() throws Exception {
        // Create artist
        Artist artist = factory.createArtist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        // Create and assign membership
        Membership membership = factory.createMembership();
        membership.setID("M002");
        membership.setStartDate(dateFormat.parse("2022-03-01"));
        membership.setEndDate(dateFormat.parse("2022-09-01"));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test reward points calculation
        Date startDate = dateFormat.parse("2023-06-01");
        Date endDate = dateFormat.parse("2023-07-01");
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        assertEquals("Reward points should be 0 for invalid membership period", 0, result);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws Exception {
        // Create artist
        Artist artist = factory.createArtist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // Create and assign membership
        Membership membership = factory.createMembership();
        membership.setID("M003");
        membership.setStartDate(dateFormat.parse("2023-01-01"));
        membership.setEndDate(dateFormat.parse("2024-01-01"));
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test reward points calculation
        Date startDate = dateFormat.parse("2023-05-01");
        Date endDate = dateFormat.parse("2023-10-01");
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        assertEquals("Reward points should be 300 for valid agency membership period", 300, result);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws Exception {
        // Create artist
        Artist artist = factory.createArtist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        // Create and assign first membership
        Membership membership = factory.createMembership();
        membership.setID("M004");
        membership.setStartDate(dateFormat.parse("2023-01-05"));
        membership.setEndDate(dateFormat.parse("2023-06-01"));
        membership.setRewardPoint(150);
        membership.setType(MembershipType.INDIVIDUAL);
        artist.setMembership(membership);
        
        // Test reward points calculation with first membership
        Date startDate = dateFormat.parse("2023-01-06");
        Date endDate = dateFormat.parse("2023-01-21");
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        assertEquals("Reward points should be 150 for the first valid membership", 150, result);
        
        // Update to second membership
        Membership secondMembership = factory.createMembership();
        secondMembership.setID("M005");
        secondMembership.setStartDate(dateFormat.parse("2023-02-01"));
        secondMembership.setEndDate(dateFormat.parse("2024-02-01"));
        secondMembership.setRewardPoint(100);
        secondMembership.setType(MembershipType.AGENCY_AFFILIATE);
        artist.setMembership(secondMembership);
        
        // Test with same period - should return 0 since second membership doesn't cover this period
        int resultAfterUpdate = artist.calculateRewardPoints(startDate, endDate);
        
        assertEquals("Reward points should be 0 after membership update for the same period", 0, resultAfterUpdate);
    }
    
    @Test
    public void testCase5_BoundaryConditionMembershipExpiration() throws Exception {
        // Create artist
        Artist artist = factory.createArtist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        // Create and assign membership
        Membership membership = factory.createMembership();
        membership.setID("M006");
        membership.setStartDate(dateFormat.parse("2022-01-01"));
        membership.setEndDate(dateFormat.parse("2023-01-01"));
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        artist.setMembership(membership);
        
        // Test reward points calculation on the day after expiration
        Date startDate = dateFormat.parse("2023-01-02");
        Date endDate = dateFormat.parse("2023-01-02");
        int result = artist.calculateRewardPoints(startDate, endDate);
        
        assertEquals("Reward points should be 0 for expired membership", 0, result);
    }
}