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
        geographicalArea = new GeographicalArea();
        mailmanAaron = new Mailman("A001", "Aaron");
        mailmanMia = new Mailman("M001", "Mia");
        mailmanPeter = new Mailman("P001", "Peter");
        mailmanMaria = new Mailman("M002", "Maria");
        mailmanNoah = new Mailman("N001", "Noah");
        inhabitantZoe = new Inhabitant("Z001", "Zoe");
        inhabitantPeter = new Inhabitant("P002", "Peter");
        inhabitantElla = new Inhabitant("E001", "Ella");
        inhabitantJucy = new Inhabitant("J001", "Jucy");
        letter10 = new Letter("Letter10", inhabitantZoe);
        parcel6 = new Parcel("Parcel6", inhabitantZoe);
        parcel7 = new Parcel("Parcel7", inhabitantPeter);
        letter11 = new Letter("Letter11", inhabitantJucy);
        letter12 = new Letter("Letter12", inhabitantJucy);
        parcel12 = new Parcel("Parcel12", inhabitantElla);
        parcel14 = new Parcel("Parcel14", inhabitantElla);
        parcel15 = new Parcel("Parcel15", inhabitantJucy);
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        GeographicalArea mainStreet = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        mainStreet.addMailman(mailmanAaron);
        
        // Add Inhabitants "Zoe", "Peter" to MainStreet
        mainStreet.addInhabitant(inhabitantZoe);
        mainStreet.addInhabitant(inhabitantPeter);
        
        // Create and assign mail items
        mainStreet.addRegisteredMail(letter10);
        mainStreet.addRegisteredMail(parcel6);
        mainStreet.addRegisteredMail(parcel7);
        
        // Assign mail items to Aaron
        mainStreet.assignRegisteredMailDeliver(mailmanAaron, inhabitantZoe, letter10);
        mainStreet.assignRegisteredMailDeliver(mailmanAaron, inhabitantZoe, parcel6);
        mainStreet.assignRegisteredMailDeliver(mailmanAaron, inhabitantPeter, parcel7);
        
        // Action: List all mail for Aaron
        List<RegisteredMail> result = mainStreet.listRegisteredMail(mailmanAaron);
        
        // Expected Output: List containing Letter10, Parcel6, and Parcel7
        assertNotNull("Result should not be null", result);
        assertEquals("List should contain 3 items", 3, result.size());
        assertTrue("List should contain Letter10", result.contains(letter10));
        assertTrue("List should contain Parcel6", result.contains(parcel6));
        assertTrue("List should contain Parcel7", result.contains(parcel7));
    }
    
    @Test
    public void testCase2_ListMailForMailmanWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        GeographicalArea marketSquare = new GeographicalArea();
        
        // Add Mailman "Mia" to MarketSquare
        marketSquare.addMailman(mailmanMia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMail(mailmanMia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // SetUp: Create GeographicalArea (no setup needed beyond initial creation)
        GeographicalArea area = new GeographicalArea();
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMail(mailmanNoah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // Add Inhabitants "Ella", "Jucy" to OldTown
        oldTown.addInhabitant(inhabitantElla);
        oldTown.addInhabitant(inhabitantJucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(mailmanPeter);
        oldTown.addMailman(mailmanMaria);
        
        // Create and assign mail items
        oldTown.addRegisteredMail(letter11);
        oldTown.addRegisteredMail(letter12);
        oldTown.addRegisteredMail(parcel12);
        oldTown.addRegisteredMail(parcel14);
        oldTown.addRegisteredMail(parcel15);
        
        // Assign mail items to mailmen
        oldTown.assignRegisteredMailDeliver(mailmanPeter, inhabitantJucy, letter11);
        oldTown.assignRegisteredMailDeliver(mailmanPeter, inhabitantJucy, letter12);
        oldTown.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, parcel12);
        oldTown.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, parcel14);
        oldTown.assignRegisteredMailDeliver(mailmanMaria, inhabitantJucy, parcel15);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(mailmanPeter);
        
        // Expected Output: List containing Letter11, Letter12, Parcel12, and Parcel14
        assertNotNull("Result should not be null", result);
        assertEquals("List should contain 4 items", 4, result.size());
        assertTrue("List should contain Letter11", result.contains(letter11));
        assertTrue("List should contain Letter12", result.contains(letter12));
        assertTrue("List should contain Parcel12", result.contains(parcel12));
        assertTrue("List should contain Parcel14", result.contains(parcel14));
    }
    
    @Test
    public void testCase5_ListMailForMailmanWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // Add Inhabitants "Ella", "Jucy" to OldTown
        oldTown.addInhabitant(inhabitantElla);
        oldTown.addInhabitant(inhabitantJucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(mailmanPeter);
        oldTown.addMailman(mailmanMaria);
        
        // Create and assign mail items
        oldTown.addRegisteredMail(letter12);
        oldTown.addRegisteredMail(parcel12);
        oldTown.addRegisteredMail(parcel14);
        oldTown.addRegisteredMail(parcel15);
        
        // Assign mail items to mailmen
        oldTown.assignRegisteredMailDeliver(mailmanPeter, inhabitantJucy, letter12);
        oldTown.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, parcel12);
        oldTown.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, parcel14);
        oldTown.assignRegisteredMailDeliver(mailmanMaria, inhabitantJucy, parcel15);
        
        // Remove Ella and Maria from OldTown
        oldTown.removeInhabitant(inhabitantElla);
        oldTown.removeMailman(mailmanMaria, mailmanPeter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(mailmanPeter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null", result);
        assertEquals("List should contain 2 items", 2, result.size());
        assertTrue("List should contain Letter12", result.contains(letter12));
        assertTrue("List should contain Parcel15", result.contains(parcel15));
    }
}