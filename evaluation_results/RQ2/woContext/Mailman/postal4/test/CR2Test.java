package edu.postal.postal4.test;

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

public class CR2Test {
    
    private PostalFactory factory;
    private GeographicalArea area;
    
    @Before
    public void setUp() {
        // Initialize the postal factory and package
        factory = PostalFactory.eINSTANCE;
        PostalPackage.eINSTANCE.eClass();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp
        area = factory.createGeographicalArea();
        
        Mailman aaron = factory.createMailman();
        area.addMailman(aaron);
        
        Inhabitant zoe = factory.createInhabitant();
        Inhabitant peter = factory.createInhabitant();
        area.addInhabitant(zoe);
        area.addInhabitant(peter);
        
        // Create and assign mail items
        Letter letter10 = factory.createLetter();
        letter10.setAddressee(zoe);
        letter10.setCarrier(aaron);
        area.getAllMails().add(letter10);
        
        Parcel parcel6 = factory.createParcel();
        parcel6.setAddressee(zoe);
        parcel6.setCarrier(aaron);
        area.getAllMails().add(parcel6);
        
        Parcel parcel7 = factory.createParcel();
        parcel7.setAddressee(peter);
        parcel7.setCarrier(aaron);
        area.getAllMails().add(parcel7);
        
        // Action: List all mail for Aaron
        EList<RegisteredMail> result = area.listRegisteredMail(aaron);
        
        // Expected Output: List containing Letter10, Parcel6, and Parcel7
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
        assertTrue("Should contain parcel7", result.contains(parcel7));
    }
    
    @Test
    public void testCase2_ListMailForMailmanWithNoMail() {
        // SetUp
        area = factory.createGeographicalArea();
        
        Mailman mia = factory.createMailman();
        area.addMailman(mia);
        
        // Action: List all mail for Mia
        EList<RegisteredMail> result = area.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // SetUp
        area = factory.createGeographicalArea();
        
        // Create a mailman but don't add to area
        Mailman noah = factory.createMailman();
        
        // Action: List all mail for non-existent Noah
        EList<RegisteredMail> result = area.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp
        area = factory.createGeographicalArea();
        
        Inhabitant ella = factory.createInhabitant();
        Inhabitant jucy = factory.createInhabitant();
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        
        Mailman peter = factory.createMailman();
        Mailman maria = factory.createMailman();
        area.addMailman(peter);
        area.addMailman(maria);
        
        // Create Letter11 assigned to Peter but without valid addressee in area
        Letter letter11 = factory.createLetter();
        // Don't set addressee or set to non-existent inhabitant
        letter11.setCarrier(peter);
        area.getAllMails().add(letter11);
        
        // Create and assign other mail items
        Letter letter12 = factory.createLetter();
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        area.getAllMails().add(letter12);
        
        Parcel parcel12 = factory.createParcel();
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        area.getAllMails().add(parcel12);
        
        Parcel parcel14 = factory.createParcel();
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        area.getAllMails().add(parcel14);
        
        Parcel parcel15 = factory.createParcel();
        parcel15.setAddressee(ella);
        parcel15.setCarrier(maria);
        area.getAllMails().add(parcel15);
        
        // Action: List all mail for Peter
        EList<RegisteredMail> result = area.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12, Parcel12, and Parcel14
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 4, result.size());
        assertTrue("Should contain letter11", result.contains(letter11));
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel12", result.contains(parcel12));
        assertTrue("Should contain parcel14", result.contains(parcel14));
    }
    
    @Test
    public void testCase5_ListMailForMailmanWithMixedMailTypes() {
        // SetUp
        area = factory.createGeographicalArea();
        
        Inhabitant ella = factory.createInhabitant();
        Inhabitant jucy = factory.createInhabitant();
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        
        Mailman peter = factory.createMailman();
        Mailman maria = factory.createMailman();
        area.addMailman(peter);
        area.addMailman(maria);
        
        // Create and assign initial mail items
        Letter letter12 = factory.createLetter();
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        area.getAllMails().add(letter12);
        
        Parcel parcel12 = factory.createParcel();
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        area.getAllMails().add(parcel12);
        
        Parcel parcel14 = factory.createParcel();
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        area.getAllMails().add(parcel14);
        
        Parcel parcel15 = factory.createParcel();
        parcel15.setAddressee(jucy);
        parcel15.setCarrier(maria);
        area.getAllMails().add(parcel15);
        
        // Remove Ella and Maria, reassign Maria's mail to Peter
        area.removeInhabitant(ella);
        area.removeMailman(maria, peter);
        
        // Action: List all mail for Peter
        EList<RegisteredMail> result = area.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel15 (reassigned from Maria)", result.contains(parcel15));
        assertFalse("Should not contain parcel12 (removed with Ella)", result.contains(parcel12));
        assertFalse("Should not contain parcel14 (removed with Ella)", result.contains(parcel14));
    }
}