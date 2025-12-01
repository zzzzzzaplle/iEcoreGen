import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private GeographicalArea area;
    private Mailman aaron;
    private Mailman mia;
    private Mailman peter;
    private Mailman maria;
    private Mailman noah;
    private Inhabitant zoe;
    private Inhabitant peterInhabitant;
    private Inhabitant ella;
    private Inhabitant jucy;
    
    @Before
    public void setUp() {
        // Create common test objects that can be reused across tests
        aaron = new Mailman("A1", "Aaron");
        mia = new Mailman("M1", "Mia");
        peter = new Mailman("P1", "Peter");
        maria = new Mailman("M2", "Maria");
        noah = new Mailman("N1", "Noah"); // Non-existent mailman for Test Case 3
        
        zoe = new Inhabitant("Z1", "Zoe");
        peterInhabitant = new Inhabitant("P2", "Peter");
        ella = new Inhabitant("E1", "Ella");
        jucy = new Inhabitant("J1", "Jucy");
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        area = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        area.addMailman(aaron);
        
        // Add Inhabitant "Zoe", "Peter" to MainStreet
        area.addInhabitant(zoe);
        area.addInhabitant(peterInhabitant);
        
        // Create and assign mail items
        Letter letter10 = new Letter(aaron, zoe);
        Parcel parcel6 = new Parcel(aaron, zoe);
        Parcel parcel7 = new Parcel(aaron, peterInhabitant);
        
        area.assignRegisteredMailDeliver(aaron, zoe, letter10);
        area.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        area.assignRegisteredMailDeliver(aaron, peterInhabitant, parcel7);
        
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
        area = new GeographicalArea();
        
        // Add Mailman "Mia" to MarketSquare
        area.addMailman(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // SetUp: Create GeographicalArea (no setup needed beyond area creation)
        area = new GeographicalArea();
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        area = new GeographicalArea();
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        area.addMailman(peter);
        area.addMailman(maria);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        Letter letter11 = new Letter(peter, ella);
        area.assignRegisteredMailDeliver(peter, ella, letter11);
        
        // Create and assign additional mail items
        Letter letter12 = new Letter(peter, jucy);
        Parcel parcel12 = new Parcel(peter, ella);
        Parcel parcel14 = new Parcel(peter, ella);
        Parcel parcel15 = new Parcel(maria, ella);
        
        area.assignRegisteredMailDeliver(peter, jucy, letter12);
        area.assignRegisteredMailDeliver(peter, ella, parcel12);
        area.assignRegisteredMailDeliver(peter, ella, parcel14);
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
    public void testCase5_ListMailForMailmanWithMixedMailTypesAfterReassignment() {
        // SetUp: Create GeographicalArea "OldTown"
        area = new GeographicalArea();
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        area.addMailman(peter);
        area.addMailman(maria);
        
        // Create and assign mail items
        Letter letter12 = new Letter(peter, jucy);
        Parcel parcel12 = new Parcel(peter, ella);
        Parcel parcel14 = new Parcel(peter, ella);
        Parcel parcel15 = new Parcel(maria, jucy);
        
        area.assignRegisteredMailDeliver(peter, jucy, letter12);
        area.assignRegisteredMailDeliver(peter, ella, parcel12);
        area.assignRegisteredMailDeliver(peter, ella, parcel14);
        area.assignRegisteredMailDeliver(maria, jucy, parcel15);
        
        // Remove Ella, Maria from OldTown. Assign Maria's mails to Peter.
        area.removeInhabitant(ella);
        area.removeMailman(maria, peter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = area.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel15 (reassigned from Maria)", result.contains(parcel15));
        
        // Verify that Ella's mail items were removed
        for (RegisteredMail mail : result) {
            assertNotEquals("Mail should not be addressed to Ella", ella, mail.getAddressee());
        }
    }
}