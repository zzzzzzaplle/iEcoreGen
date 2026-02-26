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
        // Initialize geographical areas
        mainStreet = new GeographicalArea();
        marketSquare = new GeographicalArea();
        oldTown = new GeographicalArea();
        
        // Initialize mailmen
        aaron = new Mailman("Aaron");
        mia = new Mailman("Mia");
        peter = new Mailman("Peter");
        maria = new Mailman("Maria");
        noah = new Mailman("Noah");
        
        // Initialize inhabitants
        zoe = new Inhabitant("Zoe");
        peterInhabitant = new Inhabitant("Peter");
        ella = new Inhabitant("Ella");
        jucy = new Inhabitant("Jucy");
        
        // Initialize mail items
        letter10 = new Letter(zoe);
        parcel6 = new Parcel(zoe);
        parcel7 = new Parcel(peterInhabitant);
        letter11 = new Letter(ella);
        letter12 = new Letter(jucy);
        parcel12 = new Parcel(ella);
        parcel14 = new Parcel(ella);
        parcel15 = new Parcel(jucy);
    }

    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp:
        // 1. Create GeographicalArea "MainStreet".
        // 2. Add Mailman "Aaron" to MainStreet.
        mainStreet.addMailman(aaron);
        
        // 3. Add Inhabitant "Zoe", "Peter" to MainStreet.
        mainStreet.addInhabitant(zoe);
        mainStreet.addInhabitant(peterInhabitant);
        
        // 4. Create and assign:
        //    - Letter10 for Zoe (Aaron)
        //    - Parcel6 for Zoe (Aaron)
        //    - Parcel7 for Peter (Aaron)
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, letter10);
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        mainStreet.assignRegisteredMailDeliver(aaron, peterInhabitant, parcel7);
        
        // Action: List all mail for Aaron.
        List<RegisteredMail> result = mainStreet.listRegisteredMail(aaron);
        
        // Expected Output: List containing Letter10, Parcel6, and Parcel7.
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter10));
        assertTrue(result.contains(parcel6));
        assertTrue(result.contains(parcel7));
    }

    @Test
    public void testCase2_ListMailForMailmanWithNoMail() {
        // SetUp:
        // 1. Create GeographicalArea "MarketSquare".
        // 2. Add Mailman "Mia" to MarketSquare.
        marketSquare.addMailman(mia);
        
        // Action: List all mail for Mia.
        List<RegisteredMail> result = marketSquare.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }

    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // Action: List all mail for non-existent "Noah".
        List<RegisteredMail> result = mainStreet.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull(result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp:
        // 1. Create GeographicalArea "OldTown".
        // 2. Add Inhabitant "Ella", "Jucy" to OldTown.
        oldTown.addInhabitant(ella);
        oldTown.addInhabitant(jucy);
        
        // 3. Add Mailman "Peter", "Maria" to OldTown.
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // 3. Create Letter11 assigned to mailman "Peter" in "OldTown".
        oldTown.assignRegisteredMailDeliver(peter, ella, letter11);
        
        // 4. Create and assign:
        //    - Letter12 for Jucy (Peter)
        //    - Parcel12 for Ella (Peter)
        //    - Parcel14 for Ella (Peter)
        //    - Parcel15 for Ella (Maria)
        oldTown.assignRegisteredMailDeliver(peter, jucy, letter12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel14);
        oldTown.assignRegisteredMailDeliver(maria, ella, parcel15);
        
        // Action: List all mail for Peter.
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12, Parcel12, and Parcel14.
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel12));
        assertTrue(result.contains(parcel14));
        assertFalse(result.contains(letter11)); // Letter11 should not be in result
        assertFalse(result.contains(parcel15)); // Parcel15 should not be in result
    }

    @Test
    public void testCase5_ListMailForMailmanWithMixedMailTypes() {
        // SetUp:
        // 1. Create GeographicalArea "OldTown".
        // 2. Add Inhabitant "Ella", "Jucy" to OldTown.
        oldTown.addInhabitant(ella);
        oldTown.addInhabitant(jucy);
        
        // 3. Add Mailman "Peter", "Maria" to OldTown.
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // 4. Create and assign:
        //    - Letter12 for Jucy (Peter)
        //    - Parcel12 for Ella (Peter)
        //    - Parcel14 for Ella (Peter)
        //    - Parcel15 for Jucy (Maria)
        oldTown.assignRegisteredMailDeliver(peter, jucy, letter12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel12);
        oldTown.assignRegisteredMailDeliver(peter, ella, parcel14);
        oldTown.assignRegisteredMailDeliver(maria, jucy, parcel15);
        
        // 5. Remove Ella, Maria from OldTown. Assign Maria's mails to Peter.
        oldTown.removeInhabitant(ella);
        oldTown.removeMailman(maria, peter);
        
        // Action: List all mail for Peter.
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15.
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel15));
    }
}