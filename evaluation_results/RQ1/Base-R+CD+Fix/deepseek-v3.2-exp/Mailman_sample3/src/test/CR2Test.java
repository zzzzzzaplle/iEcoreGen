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
        // Initialize common objects that might be used across multiple tests
        area = new GeographicalArea();
        aaron = new Mailman();
        mia = new Mailman();
        peter = new Mailman();
        maria = new Mailman();
        noah = new Mailman();
        zoe = new Inhabitant();
        peterInhabitant = new Inhabitant();
        ella = new Inhabitant();
        jucy = new Inhabitant();
        letter10 = new Letter();
        parcel6 = new Parcel();
        parcel7 = new Parcel();
        letter11 = new Letter();
        letter12 = new Letter();
        parcel12 = new Parcel();
        parcel14 = new Parcel();
        parcel15 = new Parcel();
        
        // Set names for clarity
        aaron.setName("Aaron");
        mia.setName("Mia");
        peter.setName("Peter");
        maria.setName("Maria");
        noah.setName("Noah");
        zoe.setName("Zoe");
        peterInhabitant.setName("Peter");
        ella.setName("Ella");
        jucy.setName("Jucy");
    }

    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        GeographicalArea mainStreet = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        aaron.setName("Aaron");
        mainStreet.addMailman(aaron);
        
        // Add Inhabitants "Zoe", "Peter" to MainStreet
        zoe.setName("Zoe");
        peterInhabitant.setName("Peter");
        mainStreet.addInhabitant(zoe);
        mainStreet.addInhabitant(peterInhabitant);
        
        // Create and assign mail items
        Letter letter10 = new Letter();
        Parcel parcel6 = new Parcel();
        Parcel parcel7 = new Parcel();
        
        // Assign mail to Aaron
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, letter10);
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        mainStreet.assignRegisteredMailDeliver(aaron, peterInhabitant, parcel7);
        
        // Action: List all mail for Aaron
        List<RegisteredMail> result = mainStreet.listRegisteredMail(aaron);
        
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
        GeographicalArea marketSquare = new GeographicalArea();
        
        // Add Mailman "Mia" to MarketSquare
        mia.setName("Mia");
        marketSquare.addMailman(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }

    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // Add Inhabitants "Ella", "Jucy" to OldTown
        ella.setName("Ella");
        jucy.setName("Jucy");
        oldTown.addInhabitant(ella);
        oldTown.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        peter.setName("Peter");
        maria.setName("Maria");
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        Letter letter11 = new Letter();
        oldTown.assignRegisteredMailDeliver(peter, jucy, letter11);
        
        // Create and assign other mail items
        Letter letter12 = new Letter();
        Parcel parcel12 = new Parcel();
        Parcel parcel14 = new Parcel();
        Parcel parcel15 = new Parcel();
        
        oldTown.assignRegisteredMailDeliver(peter, jucy, letter12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel14);
        oldTown.assignRegisteredMailDeliver(maria, ella, parcel15);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
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
        GeographicalArea oldTown = new GeographicalArea();
        
        // Add Inhabitants "Ella", "Jucy" to OldTown
        ella.setName("Ella");
        jucy.setName("Jucy");
        oldTown.addInhabitant(ella);
        oldTown.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        peter.setName("Peter");
        maria.setName("Maria");
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // Create and assign mail items
        Letter letter12 = new Letter();
        Parcel parcel12 = new Parcel();
        Parcel parcel14 = new Parcel();
        Parcel parcel15 = new Parcel();
        
        oldTown.assignRegisteredMailDeliver(peter, jucy, letter12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel14);
        oldTown.assignRegisteredMailDeliver(maria, jucy, parcel15);
        
        // Remove Ella and Maria from OldTown (which should reassign Maria's mail to Peter)
        oldTown.removeInhabitant(ella);
        oldTown.removeMailman(maria, peter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel15", result.contains(parcel15));
    }
}