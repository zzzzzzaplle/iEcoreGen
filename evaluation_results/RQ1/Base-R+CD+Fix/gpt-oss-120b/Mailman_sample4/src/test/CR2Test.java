import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private GeographicalArea area;
    private Mailman mailman;
    private Inhabitant inhabitant1;
    private Inhabitant inhabitant2;
    private Letter letter;
    private Parcel parcel;
    
    @Before
    public void setUp() {
        area = new GeographicalArea();
        mailman = new Mailman();
        inhabitant1 = new Inhabitant();
        inhabitant2 = new Inhabitant();
        letter = new Letter();
        parcel = new Parcel();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        GeographicalArea mainStreet = new GeographicalArea();
        
        // SetUp: Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        aaron.setId("Aaron");
        mainStreet.addMailman(aaron);
        
        // SetUp: Add Inhabitant "Zoe", "Peter" to MainStreet
        Inhabitant zoe = new Inhabitant();
        zoe.setId("Zoe");
        mainStreet.addInhabitant(zoe);
        
        Inhabitant peter = new Inhabitant();
        peter.setId("Peter");
        mainStreet.addInhabitant(peter);
        
        // SetUp: Create and assign mail items
        Letter letter10 = new Letter();
        letter10.setAddressee(zoe);
        letter10.setCarrier(aaron);
        mainStreet.addMail(letter10);
        
        Parcel parcel6 = new Parcel();
        parcel6.setAddressee(zoe);
        parcel6.setCarrier(aaron);
        mainStreet.addMail(parcel6);
        
        Parcel parcel7 = new Parcel();
        parcel7.setAddressee(peter);
        parcel7.setCarrier(aaron);
        mainStreet.addMail(parcel7);
        
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
        
        // SetUp: Add Mailman "Mia" to MarketSquare
        Mailman mia = new Mailman();
        mia.setId("Mia");
        marketSquare.addMailman(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // SetUp: Create GeographicalArea
        GeographicalArea area = new GeographicalArea();
        
        // Create a mailman not added to the area
        Mailman noah = new Mailman();
        noah.setId("Noah");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // SetUp: Add Inhabitant "Ella", "Jucy" to OldTown
        Inhabitant ella = new Inhabitant();
        ella.setId("Ella");
        oldTown.addInhabitant(ella);
        
        Inhabitant jucy = new Inhabitant();
        jucy.setId("Jucy");
        oldTown.addInhabitant(jucy);
        
        // SetUp: Add Mailman "Peter", "Maria" to OldTown
        Mailman peter = new Mailman();
        peter.setId("Peter");
        oldTown.addMailman(peter);
        
        Mailman maria = new Mailman();
        maria.setId("Maria");
        oldTown.addMailman(maria);
        
        // SetUp: Create Letter11 assigned to mailman "Peter" in "OldTown"
        Letter letter11 = new Letter();
        letter11.setAddressee(ella);
        letter11.setCarrier(peter);
        oldTown.addMail(letter11);
        
        // SetUp: Create and assign mail items
        Letter letter12 = new Letter();
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        oldTown.addMail(letter12);
        
        Parcel parcel12 = new Parcel();
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        oldTown.addMail(parcel12);
        
        Parcel parcel14 = new Parcel();
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        oldTown.addMail(parcel14);
        
        Parcel parcel15 = new Parcel();
        parcel15.setAddressee(ella);
        parcel15.setCarrier(maria);
        oldTown.addMail(parcel15);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12, Parcel12, and Parcel14
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel12", result.contains(parcel12));
        assertTrue("Should contain parcel14", result.contains(parcel14));
    }
    
    @Test
    public void testCase5_ListMailForMailmanWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // SetUp: Add Inhabitant "Ella", "Jucy" to OldTown
        Inhabitant ella = new Inhabitant();
        ella.setId("Ella");
        oldTown.addInhabitant(ella);
        
        Inhabitant jucy = new Inhabitant();
        jucy.setId("Jucy");
        oldTown.addInhabitant(jucy);
        
        // SetUp: Add Mailman "Peter", "Maria" to OldTown
        Mailman peter = new Mailman();
        peter.setId("Peter");
        oldTown.addMailman(peter);
        
        Mailman maria = new Mailman();
        maria.setId("Maria");
        oldTown.addMailman(maria);
        
        // SetUp: Create and assign mail items
        Letter letter12 = new Letter();
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        oldTown.addMail(letter12);
        
        Parcel parcel12 = new Parcel();
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        oldTown.addMail(parcel12);
        
        Parcel parcel14 = new Parcel();
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        oldTown.addMail(parcel14);
        
        Parcel parcel15 = new Parcel();
        parcel15.setAddressee(jucy);
        parcel15.setCarrier(maria);
        oldTown.addMail(parcel15);
        
        // SetUp: Remove Ella, Maria from OldTown. Assign Maria's mails to Peter.
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