package edu.postal.postal2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.postal.PostalFactory;
import edu.postal.PostalPackage;
import edu.postal.GeographicalArea;
import edu.postal.Mailman;
import edu.postal.Inhabitant;
import edu.postal.Letter;
import edu.postal.Parcel;
import edu.postal.RegisteredMail;
import org.eclipse.emf.common.util.EList;

public class CR5Test {
    
    private PostalFactory factory;
    private GeographicalArea area;
    
    @Before
    public void setUp() {
        // Initialize the factory and package
        factory = PostalFactory.eINSTANCE;
        PostalPackage.eINSTANCE.eClass();
        
        // Create a new geographical area for each test
        area = factory.createGeographicalArea();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        GeographicalArea mainStreet = factory.createGeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        Mailman aaron = factory.createMailman();
        mainStreet.getMailmen().add(aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = factory.createInhabitant();
        mainStreet.getInhabitants().add(zoe);
        
        // Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = factory.createLetter();
        letter10.setAddressee(zoe);
        letter10.setCarrier(aaron);
        mainStreet.getAllMails().add(letter10);
        
        // Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = factory.createParcel();
        parcel6.setAddressee(zoe);
        parcel6.setCarrier(aaron);
        mainStreet.getAllMails().add(parcel6);
        
        // Action: List all mail for Zoe
        EList<RegisteredMail> result = mainStreet.listRegisteredMailWithInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        GeographicalArea marketSquare = factory.createGeographicalArea();
        
        // Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = factory.createInhabitant();
        marketSquare.getInhabitants().add(mia);
        
        // Action: List all mail for Mia
        EList<RegisteredMail> result = marketSquare.listRegisteredMailWithInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Create a non-existent inhabitant
        Inhabitant noah = factory.createInhabitant();
        
        // Action: List all mail for non-existent "Noah"
        EList<RegisteredMail> result = area.listRegisteredMailWithInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = factory.createGeographicalArea();
        
        // Add Inhabitant "Ella" to OldTown
        Inhabitant ella = factory.createInhabitant();
        oldTown.getInhabitants().add(ella);
        
        // Add Mailman "Peter" to OldTown
        Mailman peter = factory.createMailman();
        oldTown.getMailmen().add(peter);
        
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Inhabitant joseph = factory.createInhabitant();
        oldTown.getInhabitants().add(joseph);
        Letter letter11 = factory.createLetter();
        letter11.setAddressee(joseph);
        letter11.setCarrier(peter);
        oldTown.getAllMails().add(letter11);
        
        // Create and assign Letter12 for Ella (Peter)
        Letter letter12 = factory.createLetter();
        letter12.setAddressee(ella);
        letter12.setCarrier(peter);
        oldTown.getAllMails().add(letter12);
        
        // Action: List all mail for Ella
        EList<RegisteredMail> result = oldTown.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 1 mail item", 1, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertFalse("Should not contain Letter11", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        GeographicalArea newDistrict = factory.createGeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        Mailman ruby = factory.createMailman();
        newDistrict.getMailmen().add(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = factory.createInhabitant();
        newDistrict.getInhabitants().add(luke);
        
        // Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = factory.createLetter();
        letter12.setAddressee(luke);
        letter12.setCarrier(ruby);
        newDistrict.getAllMails().add(letter12);
        
        // Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = factory.createParcel();
        parcel7.setAddressee(luke);
        parcel7.setCarrier(ruby);
        newDistrict.getAllMails().add(parcel7);
        
        // Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = factory.createLetter();
        letter13.setAddressee(luke);
        letter13.setCarrier(ruby);
        newDistrict.getAllMails().add(letter13);
        
        // Action: List all mail for Luke
        EList<RegisteredMail> result = newDistrict.listRegisteredMailWithInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel7", result.contains(parcel7));
        assertTrue("Should contain Letter13", result.contains(letter13));
    }
}