import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        ella = new Inhabitant();
        jucy = new Inhabitant();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        GeographicalArea mainStreet = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        mainStreet.addMailman(aaron);
        
        // Add Inhabitant "Zoe", "Peter" to MainStreet
        mainStreet.addInhabitant(zoe);
        Inhabitant peterInhabitant = new Inhabitant();
        mainStreet.addInhabitant(peterInhabitant);
        
        // Create and assign: Letter10 for Zoe (Aaron), Parcel6 for Zoe (Aaron), Parcel7 for Peter (Aaron)
        Letter letter10 = new Letter();
        letter10.setAddressee(zoe);
        letter10.setCarrier(aaron);
        mainStreet.addRegisteredMail(letter10);
        
        Parcel parcel6 = new Parcel();
        parcel6.setAddressee(zoe);
        parcel6.setCarrier(aaron);
        mainStreet.addRegisteredMail(parcel6);
        
        Parcel parcel7 = new Parcel();
        parcel7.setAddressee(peterInhabitant);
        parcel7.setCarrier(aaron);
        mainStreet.addRegisteredMail(parcel7);
        
        // Action: List all mail for Aaron
        List<RegisteredMail> result = mainStreet.listRegisteredMail(aaron);
        
        // Expected Output: List containing Letter10, Parcel6, and Parcel7
        assertNotNull("Mail list should not be null", result);
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
        marketSquare.addMailman(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Should return null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull("Should return null for non-existent mailman", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        oldTown.addInhabitant(ella);
        oldTown.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        Letter letter11 = new Letter();
        letter11.setCarrier(peter);
        letter11.setAddressee(ella); // Assuming some addressee
        oldTown.addRegisteredMail(letter11);
        
        // Create and assign: Letter12 for Jucy (Peter), Parcel12 for Ella (Peter), Parcel14 for Ella (Peter)
        Letter letter12 = new Letter();
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        oldTown.addRegisteredMail(letter12);
        
        Parcel parcel12 = new Parcel();
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        oldTown.addRegisteredMail(parcel12);
        
        Parcel parcel14 = new Parcel();
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        oldTown.addRegisteredMail(parcel14);
        
        // Create Parcel15 for Ella (Maria) - should NOT be included in Peter's list
        Parcel parcel15 = new Parcel();
        parcel15.setAddressee(ella);
        parcel15.setCarrier(maria);
        oldTown.addRegisteredMail(parcel15);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter11, Letter12, Parcel12, and Parcel14
        assertNotNull("Mail list should not be null", result);
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
        
        // Add Inhabitant "Ella", "Jucy" to OldTown
        oldTown.addInhabitant(ella);
        oldTown.addInhabitant(jucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(peter);
        oldTown.addMailman(maria);
        
        // Create and assign: Letter12 for Jucy (Peter), Parcel12 for Ella (Peter), Parcel14 for Ella (Peter)
        Letter letter12 = new Letter();
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        oldTown.addRegisteredMail(letter12);
        
        Parcel parcel12 = new Parcel();
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        oldTown.addRegisteredMail(parcel12);
        
        Parcel parcel14 = new Parcel();
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        oldTown.addRegisteredMail(parcel14);
        
        // Create Parcel15 for Jucy (Maria)
        Parcel parcel15 = new Parcel();
        parcel15.setAddressee(jucy);
        parcel15.setCarrier(maria);
        oldTown.addRegisteredMail(parcel15);
        
        // Remove Ella, Maria from OldTown. Assign Maria's mails to Peter.
        oldTown.removeInhabitant(ella);
        oldTown.removeMailman(maria, peter); // Assign Maria's mails to Peter
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Mail list should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel15 (reassigned from Maria)", result.contains(parcel15));
        
        // Note: Parcel12 and Parcel14 should not be included since Ella was removed
    }
}