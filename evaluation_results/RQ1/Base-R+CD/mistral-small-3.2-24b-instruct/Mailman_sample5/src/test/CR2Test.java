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

    @Before
    public void setUp() {
        // Initialize test objects
        mainStreet = new GeographicalArea();
        marketSquare = new GeographicalArea();
        oldTown = new GeographicalArea();
        
        aaron = new Mailman("A1", "Aaron");
        mia = new Mailman("M1", "Mia");
        peter = new Mailman("P1", "Peter");
        maria = new Mailman("M2", "Maria");
        noah = new Mailman("N1", "Noah");
        
        zoe = new Inhabitant("Z1", "Zoe");
        peterInhabitant = new Inhabitant("P2", "Peter");
        ella = new Inhabitant("E1", "Ella");
        jucy = new Inhabitant("J1", "Jucy");
    }

    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp
        // 1. Create GeographicalArea "MainStreet"
        // 2. Add Mailman "Aaron" to MainStreet
        mainStreet.addMailman(aaron);
        
        // 3. Add Inhabitant "Zoe", "Peter" to MainStreet
        mainStreet.addInhabitant(zoe);
        mainStreet.addInhabitant(peterInhabitant);
        
        // 4. Create and assign:
        //    - Letter10 for Zoe (Aaron)
        //    - Parcel6 for Zoe (Aaron)
        //    - Parcel7 for Peter (Aaron)
        Letter letter10 = new Letter();
        Parcel parcel6 = new Parcel();
        Parcel parcel7 = new Parcel();
        
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
        // SetUp
        // 1. Create GeographicalArea "MarketSquare"
        // 2. Add Mailman "Mia" to MarketSquare
        marketSquare.addMailman(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }

    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = mainStreet.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp
        // 1. Create GeographicalArea "OldTown"
        // 2. Add Inhabitant "Ella", "Jucy" to OldTown
        oldTown.addInhabitant(ella);
        oldTown.addInhabitant(jucy);
        
        // 3. Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // 3. Create Letter11 assigned to mailman "Peter" in "OldTown"
        Letter letter11 = new Letter();
        oldTown.assignRegisteredMailDeliver(peter, ella, letter11);
        
        // 4. Create and assign:
        //    - Letter12 for Jucy (Peter)
        //    - Parcel12 for Ella (Peter)
        //    - Parcel14 for Ella (Peter)
        //    - Parcel15 for Ella (Maria)
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
        // SetUp
        // 1. Create GeographicalArea "OldTown"
        // 2. Add Inhabitant "Ella", "Jucy" to OldTown
        oldTown.addInhabitant(ella);
        oldTown.addInhabitant(jucy);
        
        // 3. Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // 4. Create and assign:
        //    - Letter12 for Jucy (Peter)
        //    - Parcel12 for Ella (Peter)
        //    - Parcel14 for Ella (Peter)
        //    - Parcel15 for Jucy (Maria)
        Letter letter12 = new Letter();
        Parcel parcel12 = new Parcel();
        Parcel parcel14 = new Parcel();
        Parcel parcel15 = new Parcel();
        
        oldTown.assignRegisteredMailDeliver(peter, jucy, letter12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel14);
        oldTown.assignRegisteredMailDeliver(maria, jucy, parcel15);
        
        // 5. Remove Ella, Maria from OldTown. Assign Maria's mails to Peter
        oldTown.removeInhabitant(ella);
        oldTown.removeMailman(maria, peter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel15 (reassigned from Maria)", result.contains(parcel15));
        assertFalse("Should not contain parcel12 (removed with Ella)", result.contains(parcel12));
        assertFalse("Should not contain parcel14 (removed with Ella)", result.contains(parcel14));
    }
}