import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private GeographicalArea area;
    
    @Before
    public void setUp() {
        area = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        // This is handled by @Before setup
        
        // Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman("Aaron");
        area.addMailman(aaron);
        
        // Add Inhabitants "Zoe", "Peter" to MainStreet
        Inhabitant zoe = new Inhabitant("Zoe");
        Inhabitant peter = new Inhabitant("Peter");
        area.addInhabitant(zoe);
        area.addInhabitant(peter);
        
        // Create and assign mail items
        Letter letter10 = new Letter();
        Parcel parcel6 = new Parcel();
        Parcel parcel7 = new Parcel();
        
        area.assignRegisteredMailDeliver(aaron, zoe, letter10);
        area.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        area.assignRegisteredMailDeliver(aaron, peter, parcel7);
        
        // Action: List all mail for Aaron
        List<RegisteredMail> result = area.listRegisteredMail(aaron);
        
        // Expected Output: List containing Letter10, Parcel6, and Parcel7
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
        assertTrue("Should contain parcel7", result.contains(parcel7));
    }
    
    @Test
    public void testCase2_ListMailForMailmanWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        // This is handled by @Before setup
        
        // Add Mailman "Mia" to MarketSquare
        Mailman mia = new Mailman("Mia");
        area.addMailman(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail items exist", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // Action: List all mail for non-existent "Noah"
        Mailman noah = new Mailman("Noah");
        List<RegisteredMail> result = area.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        // This is handled by @Before setup
        
        // Add Inhabitants "Ella", "Jucy" to OldTown
        Inhabitant ella = new Inhabitant("Ella");
        Inhabitant jucy = new Inhabitant("Jucy");
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        Mailman peter = new Mailman("Peter");
        Mailman maria = new Mailman("Maria");
        area.addMailman(peter);
        area.addMailman(maria);
        
        // Create and assign mail items
        Letter letter11 = new Letter();
        Letter letter12 = new Letter();
        Parcel parcel12 = new Parcel();
        Parcel parcel14 = new Parcel();
        Parcel parcel15 = new Parcel();
        
        // Assign mail to Peter
        area.assignRegisteredMailDeliver(peter, ella, letter11);
        area.assignRegisteredMailDeliver(peter, jucy, letter12);
        area.assignRegisteredMailDeliver(peter, ella, parcel12);
        area.assignRegisteredMailDeliver(peter, ella, parcel14);
        
        // Assign one mail to Maria
        area.assignRegisteredMailDeliver(maria, ella, parcel15);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = area.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter11, Letter12, Parcel12, and Parcel14
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 4 mail items", 4, result.size());
        assertTrue("Should contain letter11", result.contains(letter11));
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel12", result.contains(parcel12));
        assertTrue("Should contain parcel14", result.contains(parcel14));
        assertFalse("Should not contain parcel15 (assigned to Maria)", result.contains(parcel15));
    }
    
    @Test
    public void testCase5_ListMailForMailmanWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "OldTown"
        // This is handled by @Before setup
        
        // Add Inhabitants "Ella", "Jucy" to OldTown
        Inhabitant ella = new Inhabitant("Ella");
        Inhabitant jucy = new Inhabitant("Jucy");
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        Mailman peter = new Mailman("Peter");
        Mailman maria = new Mailman("Maria");
        area.addMailman(peter);
        area.addMailman(maria);
        
        // Create and assign mail items
        Letter letter12 = new Letter();
        Parcel parcel12 = new Parcel();
        Parcel parcel14 = new Parcel();
        Parcel parcel15 = new Parcel();
        
        // Assign mail to Peter
        area.assignRegisteredMailDeliver(peter, jucy, letter12);
        area.assignRegisteredMailDeliver(peter, ella, parcel12);
        area.assignRegisteredMailDeliver(peter, ella, parcel14);
        
        // Assign mail to Maria
        area.assignRegisteredMailDeliver(maria, jucy, parcel15);
        
        // Remove Ella and Maria from OldTown (this should reassign Maria's mails to Peter)
        area.removeInhabitant(ella);
        area.removeMailman(maria, peter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = area.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel15 (reassigned from Maria)", result.contains(parcel15));
        
        // Verify that Ella's mails (parcel12 and parcel14) are no longer assigned to Peter
        assertFalse("Should not contain parcel12 (Ella removed)", result.contains(parcel12));
        assertFalse("Should not contain parcel14 (Ella removed)", result.contains(parcel14));
    }
}