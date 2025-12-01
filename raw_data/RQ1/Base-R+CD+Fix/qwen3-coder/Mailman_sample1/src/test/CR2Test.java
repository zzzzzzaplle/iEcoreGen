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
    
    @Before
    public void setUp() {
        // Create common test objects that can be reused across tests
        aaron = new Mailman();
        mia = new Mailman();
        peter = new Mailman();
        maria = new Mailman();
        noah = new Mailman();
        
        zoe = new Inhabitant();
        peterInhabitant = new Inhabitant();
        ella = new Inhabitant();
        jucy = new Inhabitant();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        area = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        area.addMailman(aaron);
        
        // Add Inhabitant "Zoe", "Peter" to MainStreet
        area.addInhabitant(zoe);
        area.addInhabitant(peterInhabitant);
        
        // Create and assign mail items
        Letter letter10 = new Letter();
        letter10.setAddressee(zoe);
        letter10.setCarrier(aaron);
        area.getAllMails().add(letter10);
        
        Parcel parcel6 = new Parcel();
        parcel6.setAddressee(zoe);
        parcel6.setCarrier(aaron);
        area.getAllMails().add(parcel6);
        
        Parcel parcel7 = new Parcel();
        parcel7.setAddressee(peterInhabitant);
        parcel7.setCarrier(aaron);
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
        area = new GeographicalArea();
        
        // Add Mailman "Mia" to MarketSquare
        area.addMailman(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // SetUp: Create GeographicalArea
        area = new GeographicalArea();
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        area = new GeographicalArea();
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        area.addMailman(peter);
        area.addMailman(maria);
        
        // Create Letter11 assigned to mailman "Peter"
        Letter letter11 = new Letter();
        letter11.setCarrier(peter);
        area.getAllMails().add(letter11);
        
        // Create and assign other mail items
        Letter letter12 = new Letter();
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        area.getAllMails().add(letter12);
        
        Parcel parcel12 = new Parcel();
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        area.getAllMails().add(parcel12);
        
        Parcel parcel14 = new Parcel();
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        area.getAllMails().add(parcel14);
        
        Parcel parcel15 = new Parcel();
        parcel15.setAddressee(ella);
        parcel15.setCarrier(maria);
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
        assertFalse("Should not contain parcel15 (assigned to Maria)", result.contains(parcel15));
    }
    
    @Test
    public void testCase5_ListMailForMailmanWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "OldTown"
        area = new GeographicalArea();
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        area.addMailman(peter);
        area.addMailman(maria);
        
        // Create and assign mail items
        Letter letter12 = new Letter();
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        area.getAllMails().add(letter12);
        
        Parcel parcel12 = new Parcel();
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        area.getAllMails().add(parcel12);
        
        Parcel parcel14 = new Parcel();
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        area.getAllMails().add(parcel14);
        
        Parcel parcel15 = new Parcel();
        parcel15.setAddressee(jucy);
        parcel15.setCarrier(maria);
        area.getAllMails().add(parcel15);
        
        // Remove Ella, Maria from OldTown. Assign Maria's mails to Peter.
        area.removeInhabitant(ella);
        parcel15.setCarrier(peter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = area.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel15", result.contains(parcel15));
    }
}