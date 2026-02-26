import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private GeographicalArea geographicalArea;
    private Mailman mailmanAaron;
    private Mailman mailmanMia;
    private Mailman mailmanPeter;
    private Mailman mailmanMaria;
    private Mailman mailmanNoah;
    private Inhabitant inhabitantZoe;
    private Inhabitant inhabitantPeter;
    private Inhabitant inhabitantElla;
    private Inhabitant inhabitantJucy;
    
    @Before
    public void setUp() {
        geographicalArea = new GeographicalArea();
        mailmanAaron = new Mailman();
        mailmanMia = new Mailman();
        mailmanPeter = new Mailman();
        mailmanMaria = new Mailman();
        mailmanNoah = new Mailman();
        inhabitantZoe = new Inhabitant();
        inhabitantPeter = new Inhabitant();
        inhabitantElla = new Inhabitant();
        inhabitantJucy = new Inhabitant();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        // Add Mailman "Aaron" to MainStreet
        geographicalArea.addMailman(mailmanAaron);
        
        // Add Inhabitants "Zoe", "Peter" to MainStreet
        geographicalArea.addInhabitant(inhabitantZoe);
        geographicalArea.addInhabitant(inhabitantPeter);
        
        // Create and assign mail items
        Letter letter10 = new Letter();
        Parcel parcel6 = new Parcel();
        Parcel parcel7 = new Parcel();
        
        geographicalArea.assignRegisteredMailDeliver(mailmanAaron, inhabitantZoe, letter10);
        geographicalArea.assignRegisteredMailDeliver(mailmanAaron, inhabitantZoe, parcel6);
        geographicalArea.assignRegisteredMailDeliver(mailmanAaron, inhabitantPeter, parcel7);
        
        // Action: List all mail for Aaron
        List<RegisteredMail> result = geographicalArea.listRegisteredMail(mailmanAaron);
        
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
        // Add Mailman "Mia" to MarketSquare
        geographicalArea.addMailman(mailmanMia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = geographicalArea.listRegisteredMail(mailmanMia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = geographicalArea.listRegisteredMail(mailmanNoah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        // Add Inhabitants "Ella", "Jucy" to OldTown
        geographicalArea.addInhabitant(inhabitantElla);
        geographicalArea.addInhabitant(inhabitantJucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        geographicalArea.addMailman(mailmanPeter);
        geographicalArea.addMailman(mailmanMaria);
        
        // Create mail items assigned to mailman Peter
        Letter letter11 = new Letter();
        Letter letter12 = new Letter();
        Parcel parcel12 = new Parcel();
        Parcel parcel14 = new Parcel();
        Parcel parcel15 = new Parcel();
        
        geographicalArea.assignRegisteredMailDeliver(mailmanPeter, inhabitantJucy, letter11);
        geographicalArea.assignRegisteredMailDeliver(mailmanPeter, inhabitantJucy, letter12);
        geographicalArea.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, parcel12);
        geographicalArea.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, parcel14);
        geographicalArea.assignRegisteredMailDeliver(mailmanMaria, inhabitantElla, parcel15);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = geographicalArea.listRegisteredMail(mailmanPeter);
        
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
        // Add Inhabitants "Ella", "Jucy" to OldTown
        geographicalArea.addInhabitant(inhabitantElla);
        geographicalArea.addInhabitant(inhabitantJucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        geographicalArea.addMailman(mailmanPeter);
        geographicalArea.addMailman(mailmanMaria);
        
        // Create and assign mail items
        Letter letter12 = new Letter();
        Parcel parcel12 = new Parcel();
        Parcel parcel14 = new Parcel();
        Parcel parcel15 = new Parcel();
        
        geographicalArea.assignRegisteredMailDeliver(mailmanPeter, inhabitantJucy, letter12);
        geographicalArea.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, parcel12);
        geographicalArea.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, parcel14);
        geographicalArea.assignRegisteredMailDeliver(mailmanMaria, inhabitantJucy, parcel15);
        
        // Remove Ella, Maria from OldTown. Assign Maria's mails to Peter.
        geographicalArea.removeInhabitant(inhabitantElla);
        geographicalArea.removeMailman(mailmanMaria, mailmanPeter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = geographicalArea.listRegisteredMail(mailmanPeter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel15 (reassigned from Maria)", result.contains(parcel15));
        assertFalse("Should not contain parcel12 (removed with Ella)", result.contains(parcel12));
        assertFalse("Should not contain parcel14 (removed with Ella)", result.contains(parcel14));
    }
}