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
        // Initialize common test objects
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
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        GeographicalArea mainStreet = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        assertTrue(mainStreet.addMailman(aaron));
        
        // Add Inhabitant "Zoe", "Peter" to MainStreet
        assertTrue(mainStreet.addInhabitant(zoe));
        assertTrue(mainStreet.addInhabitant(peterInhabitant));
        
        // Create and assign mail items
        Letter letter10 = new Letter();
        letter10.setAddressee(zoe);
        
        Parcel parcel6 = new Parcel();
        parcel6.setAddressee(zoe);
        
        Parcel parcel7 = new Parcel();
        parcel7.setAddressee(peterInhabitant);
        
        // Assign all mail to Aaron
        assertTrue(mainStreet.assignRegisteredMailDeliver(aaron, zoe, letter10));
        assertTrue(mainStreet.assignRegisteredMailDeliver(aaron, zoe, parcel6));
        assertTrue(mainStreet.assignRegisteredMailDeliver(aaron, peterInhabitant, parcel7));
        
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
        GeographicalArea marketSquare = new GeographicalArea();
        
        // Add Mailman "Mia" to MarketSquare
        assertTrue(marketSquare.addMailman(mia));
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        assertTrue(oldTown.addInhabitant(ella));
        assertTrue(oldTown.addInhabitant(jucy));
        
        // Add Mailman "Peter", "Maria" to OldTown
        assertTrue(oldTown.addMailman(peter));
        assertTrue(oldTown.addMailman(maria));
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        Letter letter11 = new Letter();
        letter11.setAddressee(ella);
        assertTrue(oldTown.assignRegisteredMailDeliver(peter, ella, letter11));
        
        // Create and assign other mail items
        Letter letter12 = new Letter();
        letter12.setAddressee(jucy);
        assertTrue(oldTown.assignRegisteredMailDeliver(peter, jucy, letter12));
        
        Parcel parcel12 = new Parcel();
        parcel12.setAddressee(ella);
        assertTrue(oldTown.assignRegisteredMailDeliver(peter, ella, parcel12));
        
        Parcel parcel14 = new Parcel();
        parcel14.setAddressee(ella);
        assertTrue(oldTown.assignRegisteredMailDeliver(peter, ella, parcel14));
        
        Parcel parcel15 = new Parcel();
        parcel15.setAddressee(ella);
        assertTrue(oldTown.assignRegisteredMailDeliver(maria, ella, parcel15));
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter11, Letter12, Parcel12, and Parcel14
        assertNotNull(result);
        assertEquals(4, result.size());
        assertTrue(result.contains(letter11));
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel12));
        assertTrue(result.contains(parcel14));
    }
    
    @Test
    public void testCase5_ListMailForMailmanWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        assertTrue(oldTown.addInhabitant(ella));
        assertTrue(oldTown.addInhabitant(jucy));
        
        // Add Mailman "Peter", "Maria" to OldTown
        assertTrue(oldTown.addMailman(peter));
        assertTrue(oldTown.addMailman(maria));
        
        // Create and assign mail items
        Letter letter12 = new Letter();
        letter12.setAddressee(jucy);
        assertTrue(oldTown.assignRegisteredMailDeliver(peter, jucy, letter12));
        
        Parcel parcel12 = new Parcel();
        parcel12.setAddressee(ella);
        assertTrue(oldTown.assignRegisteredMailDeliver(peter, ella, parcel12));
        
        Parcel parcel14 = new Parcel();
        parcel14.setAddressee(ella);
        assertTrue(oldTown.assignRegisteredMailDeliver(peter, ella, parcel14));
        
        Parcel parcel15 = new Parcel();
        parcel15.setAddressee(jucy);
        assertTrue(oldTown.assignRegisteredMailDeliver(maria, jucy, parcel15));
        
        // Remove Ella and Maria from OldTown
        assertTrue(oldTown.removeInhabitant(ella));
        assertTrue(oldTown.removeMailman(maria, peter)); // Assign Maria's mails to Peter
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel15));
    }
}