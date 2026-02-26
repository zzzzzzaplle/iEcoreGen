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
        // Initialize test objects
        area = new GeographicalArea();
        aaron = new Mailman();
        mia = new Mailman();
        peter = new Mailman();
        maria = new Mailman();
        noah = new Mailman();
        zoe = new Inhabitant();
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
        
        // Set names
        aaron.setName("Aaron");
        mia.setName("Mia");
        peter.setName("Peter");
        maria.setName("Maria");
        noah.setName("Noah");
        zoe.setName("Zoe");
        ella.setName("Ella");
        jucy.setName("Jucy");
    }

    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        area.setMailmen(new java.util.ArrayList<>());
        area.setInhabitants(new java.util.ArrayList<>());
        area.setAllMails(new java.util.ArrayList<>());
        
        // Add Mailman "Aaron" to MainStreet
        area.addMailman(aaron);
        
        // Add Inhabitant "Zoe", "Peter" to MainStreet
        Inhabitant peterInhabitant = new Inhabitant();
        peterInhabitant.setName("Peter");
        area.addInhabitant(zoe);
        area.addInhabitant(peterInhabitant);
        
        // Create and assign mail items
        letter10.setCarrier(aaron);
        letter10.setAddressee(zoe);
        area.getAllMails().add(letter10);
        
        parcel6.setCarrier(aaron);
        parcel6.setAddressee(zoe);
        area.getAllMails().add(parcel6);
        
        parcel7.setCarrier(aaron);
        parcel7.setAddressee(peterInhabitant);
        area.getAllMails().add(parcel7);
        
        // Action: List all mail for Aaron
        List<RegisteredMail> result = area.listRegisteredMail(aaron);
        
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
        area.setMailmen(new java.util.ArrayList<>());
        area.setInhabitants(new java.util.ArrayList<>());
        area.setAllMails(new java.util.ArrayList<>());
        
        // Add Mailman "Mia" to MarketSquare
        area.addMailman(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }

    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // SetUp: Create empty GeographicalArea
        area.setMailmen(new java.util.ArrayList<>());
        area.setInhabitants(new java.util.ArrayList<>());
        area.setAllMails(new java.util.ArrayList<>());
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        area.setMailmen(new java.util.ArrayList<>());
        area.setInhabitants(new java.util.ArrayList<>());
        area.setAllMails(new java.util.ArrayList<>());
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        area.addMailman(peter);
        area.addMailman(maria);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        letter11.setCarrier(peter);
        letter11.setAddressee(jucy);
        area.getAllMails().add(letter11);
        
        // Create and assign other mail items
        letter12.setCarrier(peter);
        letter12.setAddressee(jucy);
        area.getAllMails().add(letter12);
        
        parcel12.setCarrier(peter);
        parcel12.setAddressee(ella);
        area.getAllMails().add(parcel12);
        
        parcel14.setCarrier(peter);
        parcel14.setAddressee(ella);
        area.getAllMails().add(parcel14);
        
        parcel15.setCarrier(maria);
        parcel15.setAddressee(ella);
        area.getAllMails().add(parcel15);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = area.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter11, Letter12, Parcel12, and Parcel14
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 4 mail items", 4, result.size());
        assertTrue("Should contain letter11", result.contains(letter11));
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel12", result.contains(parcel12));
        assertTrue("Should contain parcel14", result.contains(parcel14));
        assertFalse("Should not contain parcel15 assigned to Maria", result.contains(parcel15));
    }

    @Test
    public void testCase5_ListMailForMailmanWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "OldTown"
        area.setMailmen(new java.util.ArrayList<>());
        area.setInhabitants(new java.util.ArrayList<>());
        area.setAllMails(new java.util.ArrayList<>());
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        area.addMailman(peter);
        area.addMailman(maria);
        
        // Create and assign initial mail items
        letter12.setCarrier(peter);
        letter12.setAddressee(jucy);
        area.getAllMails().add(letter12);
        
        parcel12.setCarrier(peter);
        parcel12.setAddressee(ella);
        area.getAllMails().add(parcel12);
        
        parcel14.setCarrier(peter);
        parcel14.setAddressee(ella);
        area.getAllMails().add(parcel14);
        
        parcel15.setCarrier(maria);
        parcel15.setAddressee(jucy);
        area.getAllMails().add(parcel15);
        
        // Remove Ella and Maria from OldTown (this should reassign Maria's mail to Peter)
        area.removeInhabitant(ella);
        area.removeMailman(maria, peter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = area.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel15 (reassigned from Maria)", result.contains(parcel15));
        assertFalse("Should not contain parcel12 (removed with Ella)", result.contains(parcel12));
        assertFalse("Should not contain parcel14 (removed with Ella)", result.contains(parcel14));
    }
}