import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        
        // Initialize geographical areas
        mainStreet = new GeographicalArea();
        marketSquare = new GeographicalArea();
        oldTown = new GeographicalArea();
    }

    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        // Add Mailman "Aaron" to MainStreet
        mainStreet.addMailman(aaron);
        
        // Add Inhabitant "Zoe", "Peter" to MainStreet
        mainStreet.addInhabitant(zoe);
        mainStreet.addInhabitant(peterInhabitant);
        
        // Create and assign mail items
        letter10.setAddressee(zoe);
        letter10.setCarrier(aaron);
        
        parcel6.setAddressee(zoe);
        parcel6.setCarrier(aaron);
        
        parcel7.setAddressee(peterInhabitant);
        parcel7.setCarrier(aaron);
        
        // Add mail items to geographical area
        mainStreet.getAllMails().add(letter10);
        mainStreet.getAllMails().add(parcel6);
        mainStreet.getAllMails().add(parcel7);
        
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
        // Add Mailman "Mia" to MarketSquare
        marketSquare.addMailman(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }

    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // Action: List all mail for non-existent "Noah"
        // Note: Noah is not added to any geographical area
        List<RegisteredMail> result = mainStreet.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        // Add Inhabitant "Ella", "Jucy" to OldTown
        oldTown.addInhabitant(ella);
        oldTown.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        letter11.setCarrier(peter);
        letter11.setAddressee(jucy); // Addressee needs to be set
        
        // Create and assign other mail items
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        
        parcel15.setAddressee(ella);
        parcel15.setCarrier(maria);
        
        // Add all mail items to geographical area
        oldTown.getAllMails().add(letter11);
        oldTown.getAllMails().add(letter12);
        oldTown.getAllMails().add(parcel12);
        oldTown.getAllMails().add(parcel14);
        oldTown.getAllMails().add(parcel15);
        
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
        // Add Inhabitant "Ella", "Jucy" to OldTown
        oldTown.addInhabitant(ella);
        oldTown.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // Create and assign mail items
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        
        parcel15.setAddressee(jucy);
        parcel15.setCarrier(maria);
        
        // Add all mail items to geographical area
        oldTown.getAllMails().add(letter12);
        oldTown.getAllMails().add(parcel12);
        oldTown.getAllMails().add(parcel14);
        oldTown.getAllMails().add(parcel15);
        
        // Remove Ella, Maria from OldTown. Assign Maria's mails to Peter
        oldTown.removeInhabitant(ella);
        oldTown.removeMailman(maria, peter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel15", result.contains(parcel15));
        assertFalse("Should not contain parcel12 (removed with Ella)", result.contains(parcel12));
        assertFalse("Should not contain parcel14 (removed with Ella)", result.contains(parcel14));
    }
}