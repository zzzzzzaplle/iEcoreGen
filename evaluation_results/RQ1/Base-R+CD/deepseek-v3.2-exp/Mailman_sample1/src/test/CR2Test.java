import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

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
    private Letter letter10;
    private Parcel parcel6;
    private Parcel parcel7;
    private Letter letter11;
    private Letter letter12;
    private Parcel parcel12;
    private Parcel parcel14;
    private Parcel parcel15;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        aaron = new Mailman("A1", "Aaron");
        mia = new Mailman("M1", "Mia");
        peter = new Mailman("P1", "Peter");
        maria = new Mailman("M2", "Maria");
        noah = new Mailman("N1", "Noah"); // Non-existent mailman for Test Case 3
        
        zoe = new Inhabitant("Z1", "Zoe");
        peterInhabitant = new Inhabitant("P2", "Peter");
        ella = new Inhabitant("E1", "Ella");
        jucy = new Inhabitant("J1", "Jucy");
        
        letter10 = new Letter("Letter10");
        parcel6 = new Parcel("Parcel6");
        parcel7 = new Parcel("Parcel7");
        letter11 = new Letter("Letter11");
        letter12 = new Letter("Letter12");
        parcel12 = new Parcel("Parcel12");
        parcel14 = new Parcel("Parcel14");
        parcel15 = new Parcel("Parcel15");
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
        area.addRegisteredMail(letter10);
        area.addRegisteredMail(parcel6);
        area.addRegisteredMail(parcel7);
        
        // Assign mail to Aaron
        area.assignRegisteredMailDeliver(aaron, zoe, letter10);
        area.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        area.assignRegisteredMailDeliver(aaron, peterInhabitant, parcel7);
        
        // Action: List all mail for Aaron
        List<RegisteredMail> result = area.listRegisteredMail(aaron);
        
        // Expected Output: List containing Letter10, Parcel6, and Parcel7
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Result list should contain 3 items", 3, result.size());
        assertTrue("Result should contain Letter10", result.contains(letter10));
        assertTrue("Result should contain Parcel6", result.contains(parcel6));
        assertTrue("Result should contain Parcel7", result.contains(parcel7));
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
        assertNull("Result should be null when no mail items exist for mailman", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // SetUp: Create empty GeographicalArea
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
        
        // Create and assign mail items
        area.addRegisteredMail(letter11);
        area.addRegisteredMail(letter12);
        area.addRegisteredMail(parcel12);
        area.addRegisteredMail(parcel14);
        area.addRegisteredMail(parcel15);
        
        // Assign mail to Peter and Maria
        area.assignRegisteredMailDeliver(peter, jucy, letter11);
        area.assignRegisteredMailDeliver(peter, jucy, letter12);
        area.assignRegisteredMailDeliver(peter, ella, parcel12);
        area.assignRegisteredMailDeliver(peter, ella, parcel14);
        area.assignRegisteredMailDeliver(maria, ella, parcel15);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = area.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter11, Letter12, Parcel12, and Parcel14
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Result list should contain 4 items", 4, result.size());
        assertTrue("Result should contain Letter11", result.contains(letter11));
        assertTrue("Result should contain Letter12", result.contains(letter12));
        assertTrue("Result should contain Parcel12", result.contains(parcel12));
        assertTrue("Result should contain Parcel14", result.contains(parcel14));
    }
    
    @Test
    public void testCase5_ListMailForMailmanWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "OldTown"
        area = new GeographicalArea();
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        area.addMailman(peter);
        area.addMailman(maria);
        
        // Create and assign mail items
        area.addRegisteredMail(letter12);
        area.addRegisteredMail(parcel12);
        area.addRegisteredMail(parcel14);
        area.addRegisteredMail(parcel15);
        
        // Assign mail to Peter and Maria
        area.assignRegisteredMailDeliver(peter, jucy, letter12);
        area.assignRegisteredMailDeliver(peter, ella, parcel12);
        area.assignRegisteredMailDeliver(peter, ella, parcel14);
        area.assignRegisteredMailDeliver(maria, jucy, parcel15);
        
        // Remove Ella and Maria from OldTown (this will reassign Maria's mail to Peter)
        area.removeInhabitant(ella);
        area.removeMailman(maria, peter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = area.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Result list should contain 2 items", 2, result.size());
        assertTrue("Result should contain Letter12", result.contains(letter12));
        assertTrue("Result should contain Parcel15", result.contains(parcel15));
    }
}