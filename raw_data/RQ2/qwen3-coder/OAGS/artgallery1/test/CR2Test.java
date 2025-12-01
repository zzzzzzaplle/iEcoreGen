package edu.artgallery.artgallery1.test;

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
        // Initialize the Ecore factory for object creation
        factory = ArtgalleryFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidIndividualMembershipRewardPointsCalculation() throws Exception {
        // Create artist with ID: A001
        Artist artist = factory.createArtist();
        artist.setId("A001");
        artist.setName("Alice");
        artist.setPhoneNumber("123456789");
        artist.setEmail("alice@example.com");
        artist.setAddress("123 Art St");
        artist.setGender(Gender.FEMALE);
        
        // Create individual membership for artist
        Membership membership = factory.createMembership();
        membership.setID("M001");
        membership.setStartDate(dateFormat.parse("2022-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2024-01-01 00:00:00"));
        membership.setRewardPoint(100);
        membership.setType(MembershipType.INDIVIDUAL);
        
        // Set membership for artist
        artist.setMembership(membership);
        
        // Test period: 2023-01-01 to 2023-12-31
        Date startDate = dateFormat.parse("2023-01-01 00:00:00");
        Date endDate = dateFormat.parse("2023-12-31 23:59:59");
        
        // Calculate reward points
        int rewardPoints = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output
        assertEquals("Reward points should be 100 for valid individual membership", 100, rewardPoints);
    }
    
    @Test
    public void testCase2_InvalidMembershipPeriodCalculation() throws Exception {
        // Create artist with ID: A002
        Artist artist = factory.createArtist();
        artist.setId("A002");
        artist.setName("Bob");
        artist.setPhoneNumber("987654321");
        artist.setEmail("bob@example.com");
        artist.setAddress("456 Art Ave");
        artist.setGender(Gender.MALE);
        
        // Create agency membership for artist (expired before test period)
        Membership membership = factory.createMembership();
        membership.setID("M002");
        membership.setStartDate(dateFormat.parse("2022-03-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2022-09-01 00:00:00"));
        membership.setRewardPoint(200);
        membership.setType(MembershipType.AGENCY);
        
        // Set membership for artist
        artist.setMembership(membership);
        
        // Test period: 2023-06-01 to 2023-07-01 (after membership expired)
        Date startDate = dateFormat.parse("2023-06-01 00:00:00");
        Date endDate = dateFormat.parse("2023-07-01 23:59:59");
        
        // Calculate reward points
        int rewardPoints = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output
        assertEquals("Reward points should be 0 for invalid membership period", 0, rewardPoints);
    }
    
    @Test
    public void testCase3_AgencyMembershipRewardPointsCalculation() throws Exception {
        // Create artist with ID: A003
        Artist artist = factory.createArtist();
        artist.setId("A003");
        artist.setName("Catherine");
        artist.setPhoneNumber("135792468");
        artist.setEmail("catherine@example.com");
        artist.setAddress("789 Art Blvd");
        artist.setGender(Gender.FEMALE);
        
        // Create agency membership for artist
        Membership membership = factory.createMembership();
        membership.setID("M003");
        membership.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2024-01-01 00:00:00"));
        membership.setRewardPoint(300);
        membership.setType(MembershipType.AGENCY);
        
        // Set membership for artist
        artist.setMembership(membership);
        
        // Test period: 2023-05-01 to 2023-10-01 (within membership period)
        Date startDate = dateFormat.parse("2023-05-01 00:00:00");
        Date endDate = dateFormat.parse("2023-10-01 23:59:59");
        
        // Calculate reward points
        int rewardPoints = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output
        assertEquals("Reward points should be 300 for valid agency membership", 300, rewardPoints);
    }
    
    @Test
    public void testCase4_MultipleMembershipsConsideration() throws Exception {
        // Create artist with ID: A004
        Artist artist = factory.createArtist();
        artist.setId("A004");
        artist.setName("David");
        artist.setPhoneNumber("246813579");
        artist.setEmail("david@example.com");
        artist.setAddress("321 Art Rd");
        artist.setGender(Gender.MALE);
        
        // Create individual membership for artist (valid during test period)
        Membership membership = factory.createMembership();
        membership.setID("M004");
        membership.setStartDate(dateFormat.parse("2023-01-05 00:00:00"));
        membership.setEndDate(dateFormat.parse("2023-06-01 00:00:00"));
        membership.setRewardPoint(150);
        membership.setType(MembershipType.INDIVIDUAL);
        
        // Set membership for artist
        artist.setMembership(membership);
        
        // Test period: 2023-01-06 to 2023-01-21 (within first membership period)
        Date startDate = dateFormat.parse("2023-01-06 00:00:00");
        Date endDate = dateFormat.parse("2023-01-21 23:59:59");
        
        // Calculate reward points (should use the first membership)
        int rewardPoints = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output
        assertEquals("Reward points should be 150 from the first valid membership", 150, rewardPoints);
    }
    
    @Test
    public void testCase5_BoundaryCondition_MembershipExpiration() throws Exception {
        // Create artist with ID: A005
        Artist artist = factory.createArtist();
        artist.setId("A005");
        artist.setName("Eva");
        artist.setPhoneNumber("864209753");
        artist.setEmail("eva@example.com");
        artist.setAddress("654 Art Pl");
        artist.setGender(Gender.FEMALE);
        
        // Create agency membership for artist (expired on 2023-01-01)
        Membership membership = factory.createMembership();
        membership.setID("M006");
        membership.setStartDate(dateFormat.parse("2022-01-01 00:00:00"));
        membership.setEndDate(dateFormat.parse("2023-01-01 00:00:00"));
        membership.setRewardPoint(250);
        membership.setType(MembershipType.AGENCY);
        
        // Set membership for artist
        artist.setMembership(membership);
        
        // Test period: single day 2023-01-02 (after membership expiration)
        Date startDate = dateFormat.parse("2023-01-02 00:00:00");
        Date endDate = dateFormat.parse("2023-01-02 23:59:59");
        
        // Calculate reward points
        int rewardPoints = artist.calculateRewardPoints(startDate, endDate);
        
        // Verify expected output
        assertEquals("Reward points should be 0 for expired membership", 0, rewardPoints);
    }
}