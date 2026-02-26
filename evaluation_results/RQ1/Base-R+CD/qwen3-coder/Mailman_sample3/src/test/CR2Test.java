import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    private GeographicalArea mainStreet;
    private GeographicalArea marketSquare;
    private GeographicalArea oldTown;
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
        // Initialize mailmen
        aaron = new Mailman();
        mia = new Mailman();
        peter = new Mailman();
        maria = new Mailman();
        noah = new Mailman(); // Non-existent mailman for test case 3
        
        // Initialize inhabitants
        zoe = new Inhabitant();
        peterInhabitant = new Inhabitant();
        ella = new Inhabitant();
        jucy = new Inhabitant();
        
        // Initialize mail items
        letter10 = new Letter();
        parcel6 = new Parcel();
        parcel7 = new Parcel();
        letter11 = new Letter();
        letter12 = new Letter();
        parcel12 = new Parcel();
        parcel14 = new Parcel();
        parcel15 = new Parcel();
    }

    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        mainStreet = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        mainStreet.addMailman(aaron);
        
        // Add Inhabitant "Zoe", "Peter" to MainStreet
        mainStreet.addInhabitant(zoe);
        mainStreet.addInhabitant(peterInhabitant);
        
        // Create and assign mail items
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, letter10);
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        mainStreet.assignRegisteredMailDeliver(aaron, peterInhabitant, parcel7);
        
        // Add mail items to allMails list
        mainStreet.getAllMails().add(letter10);
        mainStreet.getAllMails().add(parcel6);
        mainStreet.getAllMails().add(parcel7);
        
        // Action: List all mail for Aaron
        List<RegisteredMail> result = mainStreet.listRegisteredMail(aaron);
        
        // Expected Output: List containing Letter10, Parcel6, and Parcel7
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter10));
        assertTrue(result.contains(parcel6));
        assertTrue(result.contains(parcel7));
    }

    @Test
    public void testCase2_ListMailForMailmanWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        marketSquare = new GeographicalArea();
        
        // Add Mailman "Mia" to MarketSquare
        marketSquare.addMailman(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }

    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        oldTown = new GeographicalArea();
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = oldTown.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull(result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        oldTown = new GeographicalArea();
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        oldTown.addInhabitant(ella);
        oldTown.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        oldTown.assignRegisteredMailDeliver(peter, ella, letter11);
        
        // Create and assign other mail items
        oldTown.assignRegisteredMailDeliver(peter, jucy, letter12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel14);
        oldTown.assignRegisteredMailDeliver(maria, ella, parcel15);
        
        // Add all mail items to allMails list
        oldTown.getAllMails().add(letter11);
        oldTown.getAllMails().add(letter12);
        oldTown.getAllMails().add(parcel12);
        oldTown.getAllMails().add(parcel14);
        oldTown.getAllMails().add(parcel15);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter11, Letter12, Parcel12, and Parcel14
        assertNotNull(result);
        assertEquals(4, result.size());
        assertTrue(result.contains(letter11));
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel12));
        assertTrue(result.contains(parcel14));
        assertFalse(result.contains(parcel15)); // Should not contain Maria's mail
    }

    @Test
    public void testCase5_ListMailForMailmanWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "OldTown"
        oldTown = new GeographicalArea();
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        oldTown.addInhabitant(ella);
        oldTown.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // Create and assign mail items
        oldTown.assignRegisteredMailDeliver(peter, jucy, letter12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel14);
        oldTown.assignRegisteredMailDeliver(maria, jucy, parcel15);
        
        // Add all mail items to allMails list
        oldTown.getAllMails().add(letter12);
        oldTown.getAllMails().add(parcel12);
        oldTown.getAllMails().add(parcel14);
        oldTown.getAllMails().add(parcel15);
        
        // Remove Ella and Maria from OldTown
        oldTown.removeInhabitant(ella);
        oldTown.removeMailman(maria, peter); // Assign Maria's mails to Peter
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel15));
        assertFalse(result.contains(parcel12)); // Should be removed with Ella
        assertFalse(result.contains(parcel14)); // Should be removed with Ella
    }
}