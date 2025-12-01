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
        // Initialize all objects needed for tests
        mainStreet = new GeographicalArea();
        marketSquare = new GeographicalArea();
        oldTown = new GeographicalArea();
        
        aaron = new Mailman();
        aaron.setName("Aaron");
        
        mia = new Mailman();
        mia.setName("Mia");
        
        peter = new Mailman();
        peter.setName("Peter");
        
        maria = new Mailman();
        maria.setName("Maria");
        
        noah = new Mailman();
        noah.setName("Noah");
        
        zoe = new Inhabitant();
        zoe.setName("Zoe");
        
        peterInhabitant = new Inhabitant();
        peterInhabitant.setName("Peter");
        
        ella = new Inhabitant();
        ella.setName("Ella");
        
        jucy = new Inhabitant();
        jucy.setName("Jucy");
        
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
        // SetUp
        // 1. Create GeographicalArea "MainStreet"
        // 2. Add Mailman "Aaron" to MainStreet
        mainStreet.addMailman(aaron);
        
        // 3. Add Inhabitant "Zoe", "Peter" to MainStreet
        zoe.setArea(mainStreet);
        mainStreet.addInhabitant(zoe);
        peterInhabitant.setArea(mainStreet);
        mainStreet.addInhabitant(peterInhabitant);
        
        // 4. Create and assign:
        //    - Letter10 for Zoe (Aaron)
        letter10.setAddressee(zoe);
        letter10.setCarrier(aaron);
        mainStreet.getAllMails().add(letter10);
        //    - Parcel6 for Zoe (Aaron)
        parcel6.setAddressee(zoe);
        parcel6.setCarrier(aaron);
        mainStreet.getAllMails().add(parcel6);
        //    - Parcel7 for Peter (Aaron)
        parcel7.setAddressee(peterInhabitant);
        parcel7.setCarrier(aaron);
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
        // SetUp
        // 1. Create GeographicalArea "MarketSquare"
        // 2. Add Mailman "Mia" to MarketSquare
        marketSquare.addMailman(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }

    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = mainStreet.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull(result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp
        // 1. Create GeographicalArea "OldTown"
        // 2. Add Inhabitant "Ella", "Jucy" to OldTown
        ella.setArea(oldTown);
        oldTown.addInhabitant(ella);
        jucy.setArea(oldTown);
        oldTown.addInhabitant(jucy);
        
        // 3. Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // 3. Create Letter11 assigned to mailman "Peter" in "OldTown"
        letter11.setAddressee(ella);
        letter11.setCarrier(peter);
        oldTown.getAllMails().add(letter11);
        
        // 4. Create and assign:
        //    - Letter12 for Jucy (Peter)
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        oldTown.getAllMails().add(letter12);
        //    - Parcel12 for Ella (Peter)
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        oldTown.getAllMails().add(parcel12);
        //    - Parcel14 for Ella (Peter)
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        oldTown.getAllMails().add(parcel14);
        //    - Parcel15 for Ella (Maria)
        parcel15.setAddressee(ella);
        parcel15.setCarrier(maria);
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
        // SetUp
        // 1. Create GeographicalArea "OldTown"
        // 2. Add Inhabitant "Ella", "Jucy" to OldTown
        ella.setArea(oldTown);
        oldTown.addInhabitant(ella);
        jucy.setArea(oldTown);
        oldTown.addInhabitant(jucy);
        
        // 3. Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // 4. Create and assign:
        //    - Letter12 for Jucy (Peter)
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        oldTown.getAllMails().add(letter12);
        //    - Parcel12 for Ella (Peter)
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        oldTown.getAllMails().add(parcel12);
        //    - Parcel14 for Ella (Peter)
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        oldTown.getAllMails().add(parcel14);
        //    - Parcel15 for Jucy (Maria)
        parcel15.setAddressee(jucy);
        parcel15.setCarrier(maria);
        oldTown.getAllMails().add(parcel15);
        
        // 5. Remove Ella, Maria from OldTown. Assign Maria's mails to Peter
        oldTown.removeInhabitant(ella);
        // Note: Test specification says "Remove Ella, Maria from OldTown" but Maria is a mailman, not an inhabitant
        // Assuming it means remove mailman Maria and reassign her mail
        oldTown.removeMailman(maria, peter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel15));
        assertFalse(result.contains(parcel12)); // Removed when Ella was removed
        assertFalse(result.contains(parcel14)); // Removed when Ella was removed
    }
}