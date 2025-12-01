import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private GeographicalArea area;
    private Mailman mailmanAaron;
    private Mailman mailmanMia;
    private Mailman mailmanPeter;
    private Mailman mailmanMaria;
    private Inhabitant inhabitantZoe;
    private Inhabitant inhabitantPeter;
    private Inhabitant inhabitantElla;
    private Inhabitant inhabitantJucy;
    
    @Before
    public void setUp() {
        // Create common test objects that can be reused across tests
        mailmanAaron = new Mailman("Aaron");
        mailmanMia = new Mailman("Mia");
        mailmanPeter = new Mailman("Peter");
        mailmanMaria = new Mailman("Maria");
        
        inhabitantZoe = new Inhabitant("Zoe");
        inhabitantPeter = new Inhabitant("Peter");
        inhabitantElla = new Inhabitant("Ella");
        inhabitantJucy = new Inhabitant("Jucy");
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        area = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        area.addMailman(mailmanAaron);
        
        // Add Inhabitant "Zoe", "Peter" to MainStreet
        area.addInhabitant(inhabitantZoe);
        area.addInhabitant(inhabitantPeter);
        
        // Create and assign mail items
        Letter letter10 = new Letter(inhabitantZoe);
        Parcel parcel6 = new Parcel(inhabitantZoe);
        Parcel parcel7 = new Parcel(inhabitantPeter);
        
        // Assign all mail to Aaron
        area.assignRegisteredMailDeliver(mailmanAaron, inhabitantZoe, letter10);
        area.assignRegisteredMailDeliver(mailmanAaron, inhabitantZoe, parcel6);
        area.assignRegisteredMailDeliver(mailmanAaron, inhabitantPeter, parcel7);
        
        // Action: List all mail for Aaron
        List<RegisteredMail> result = area.listRegisteredMail(mailmanAaron);
        
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
        area.addMailman(mailmanMia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMail(mailmanMia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // SetUp: Create GeographicalArea (no setup needed for mailman)
        area = new GeographicalArea();
        
        // Action: List all mail for non-existent "Noah"
        Mailman nonExistentMailman = new Mailman("Noah");
        List<RegisteredMail> result = area.listRegisteredMail(nonExistentMailman);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        area = new GeographicalArea();
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        area.addInhabitant(inhabitantElla);
        area.addInhabitant(inhabitantJucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        area.addMailman(mailmanPeter);
        area.addMailman(mailmanMaria);
        
        // Create Letter11 assigned to mailman "Peter"
        Letter letter11 = new Letter(inhabitantElla);
        area.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, letter11);
        
        // Create and assign additional mail items
        Letter letter12 = new Letter(inhabitantJucy);
        Parcel parcel12 = new Parcel(inhabitantElla);
        Parcel parcel14 = new Parcel(inhabitantElla);
        Parcel parcel15 = new Parcel(inhabitantElla);
        
        area.assignRegisteredMailDeliver(mailmanPeter, inhabitantJucy, letter12);
        area.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, parcel12);
        area.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, parcel14);
        area.assignRegisteredMailDeliver(mailmanMaria, inhabitantElla, parcel15);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = area.listRegisteredMail(mailmanPeter);
        
        // Expected Output: List containing Letter12, Parcel12, and Parcel14
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
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
        area.addInhabitant(inhabitantElla);
        area.addInhabitant(inhabitantJucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        area.addMailman(mailmanPeter);
        area.addMailman(mailmanMaria);
        
        // Create and assign mail items
        Letter letter12 = new Letter(inhabitantJucy);
        Parcel parcel12 = new Parcel(inhabitantElla);
        Parcel parcel14 = new Parcel(inhabitantElla);
        Parcel parcel15 = new Parcel(inhabitantJucy);
        
        area.assignRegisteredMailDeliver(mailmanPeter, inhabitantJucy, letter12);
        area.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, parcel12);
        area.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, parcel14);
        area.assignRegisteredMailDeliver(mailmanMaria, inhabitantJucy, parcel15);
        
        // Remove Ella and Maria from OldTown, reassign Maria's mail to Peter
        area.removeInhabitant(inhabitantElla);
        area.removeMailman(mailmanMaria, mailmanPeter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = area.listRegisteredMail(mailmanPeter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel15 (reassigned from Maria)", result.contains(parcel15));
    }
}