import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private MailDeliverySystem system;
    private GeographicalArea mainStreet;
    private GeographicalArea marketSquare;
    private GeographicalArea oldTown;
    private GeographicalArea newDistrict;
    private Inhabitant zoe;
    private Inhabitant mia;
    private Inhabitant ella;
    private Inhabitant luke;
    private Mailman aaron;
    private Mailman peter;
    private Mailman ruby;
    private Letter letter10;
    private Parcel parcel6;
    private Letter letter11;
    private Letter letter12;
    private Parcel parcel7;
    private Letter letter13;

    @Before
    public void setUp() {
        system = new MailDeliverySystem();
        
        // Create geographical areas
        mainStreet = new GeographicalArea("MS001", "MainStreet");
        marketSquare = new GeographicalArea("MS002", "MarketSquare");
        oldTown = new GeographicalArea("OT001", "OldTown");
        newDistrict = new GeographicalArea("ND001", "NewDistrict");
        
        // Add areas to system
        system.addArea(mainStreet);
        system.addArea(marketSquare);
        system.addArea(oldTown);
        system.addArea(newDistrict);
        
        // Create inhabitants
        zoe = new Inhabitant("I001", "Zoe");
        mia = new Inhabitant("I002", "Mia");
        ella = new Inhabitant("I003", "Ella");
        luke = new Inhabitant("I004", "Luke");
        
        // Create mailmen
        aaron = new Mailman("M001", "Aaron");
        peter = new Mailman("M002", "Peter");
        ruby = new Mailman("M003", "Ruby");
        
        // Create mail items
        letter10 = new Letter("L010", zoe, "Important Document", "Please review this document.");
        parcel6 = new Parcel("P006", zoe, 2.5, "Electronics");
        letter11 = new Letter("L011", ella, "Meeting Notes", "Summary of our last meeting.");
        letter12 = new Letter("L012", ella, "Invoice", "Payment due by end of month.");
        parcel7 = new Parcel("P007", luke, 1.8, "Books");
        letter13 = new Letter("L013", luke, "Invitation", "You're invited to our event.");
    }

    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        // Add Mailman "Aaron" to MainStreet
        system.addMailman(mainStreet, aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        system.addInhabitant(mainStreet, zoe);
        
        // Create and assign: Letter10 for Zoe (Aaron), Parcel6 for Zoe (Aaron)
        mainStreet.internalAddRegisteredMail(letter10);
        mainStreet.internalAddRegisteredMail(parcel6);
        system.assignMailToMailman(letter10, aaron);
        system.assignMailToMailman(parcel6, aaron);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = system.listMailForInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }

    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        // Add Inhabitant "Mia" to MarketSquare
        system.addInhabitant(marketSquare, mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = system.listMailForInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }

    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        Inhabitant noah = new Inhabitant("I999", "Noah");
        List<RegisteredMail> result = system.listMailForInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        // Add Inhabitant "Ella" to OldTown
        system.addInhabitant(oldTown, ella);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        system.addMailman(oldTown, peter);
        oldTown.internalAddRegisteredMail(letter11);
        system.assignMailToMailman(letter11, peter);
        
        // Create and assign: Letter12 for Ella
        oldTown.internalAddRegisteredMail(letter12);
        system.assignMailToMailman(letter12, peter);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = system.listMailForInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter11", result.contains(letter11));
        assertTrue("Should contain letter12", result.contains(letter12));
    }

    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        // Add Mailman "Ruby" to NewDistrict
        system.addMailman(newDistrict, ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        system.addInhabitant(newDistrict, luke);
        
        // Create and assign: Letter12 for Luke (Ruby), Parcel7 for Luke (Ruby), Letter13 for Luke (Ruby)
        newDistrict.internalAddRegisteredMail(letter12);
        newDistrict.internalAddRegisteredMail(parcel7);
        newDistrict.internalAddRegisteredMail(letter13);
        system.assignMailToMailman(letter12, ruby);
        system.assignMailToMailman(parcel7, ruby);
        system.assignMailToMailman(letter13, ruby);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = system.listMailForInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}