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
        aaron.setId("A001");
        aaron.setName("Aaron");
        
        mia = new Mailman();
        mia.setId("M001");
        mia.setName("Mia");
        
        peter = new Mailman();
        peter.setId("P001");
        peter.setName("Peter");
        
        maria = new Mailman();
        maria.setId("M002");
        maria.setName("Maria");
        
        noah = new Mailman();
        noah.setId("N001");
        noah.setName("Noah");
        
        zoe = new Inhabitant();
        zoe.setId("Z001");
        zoe.setName("Zoe");
        zoe.setAddress("123 Main St");
        
        peterInhabitant = new Inhabitant();
        peterInhabitant.setId("P002");
        peterInhabitant.setName("Peter");
        peterInhabitant.setAddress("456 Main St");
        
        ella = new Inhabitant();
        ella.setId("E001");
        ella.setName("Ella");
        ella.setAddress("789 OldTown Rd");
        
        jucy = new Inhabitant();
        jucy.setId("J001");
        jucy.setName("Jucy");
        jucy.setAddress("101 OldTown Rd");
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        area = new GeographicalArea();
        area.setAreaCode("MS001");
        area.setAreaName("MainStreet");
        
        // Add Mailman "Aaron" to MainStreet
        area.addMailman(aaron);
        
        // Add Inhabitants "Zoe", "Peter" to MainStreet
        area.addInhabitant(zoe);
        area.addInhabitant(peterInhabitant);
        
        // Create and assign mail items
        Letter letter10 = new Letter();
        letter10.setTrackingNumber("L10");
        letter10.setWeight(0.1);
        letter10.setUrgent(true);
        
        Parcel parcel6 = new Parcel();
        parcel6.setTrackingNumber("P6");
        parcel6.setWeight(2.5);
        parcel6.setDimensions(10.0);
        parcel6.setRequiresSignature(true);
        
        Parcel parcel7 = new Parcel();
        parcel7.setTrackingNumber("P7");
        parcel7.setWeight(5.0);
        parcel7.setDimensions(15.0);
        parcel7.setRequiresSignature(false);
        
        // Assign mail items to Aaron
        area.assignRegisteredMailDeliver(aaron, zoe, letter10);
        area.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        area.assignRegisteredMailDeliver(aaron, peterInhabitant, parcel7);
        
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
        area.setAreaCode("MS002");
        area.setAreaName("MarketSquare");
        
        // Add Mailman "Mia" to MarketSquare
        area.addMailman(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMail(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // SetUp: Create empty geographical area
        area = new GeographicalArea();
        area.setAreaCode("OT001");
        area.setAreaName("OldTown");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMail(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        area = new GeographicalArea();
        area.setAreaCode("OT001");
        area.setAreaName("OldTown");
        
        // Add Inhabitants "Ella", "Jucy" to OldTown
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        
        // Add Mailmen "Peter", "Maria" to OldTown
        area.addMailman(peter);
        area.addMailman(maria);
        
        // Create Letter11 assigned to mailman "Peter"
        Letter letter11 = new Letter();
        letter11.setTrackingNumber("L11");
        letter11.setWeight(0.2);
        letter11.setUrgent(false);
        area.assignRegisteredMailDeliver(peter, jucy, letter11);
        
        // Create and assign other mail items
        Letter letter12 = new Letter();
        letter12.setTrackingNumber("L12");
        letter12.setWeight(0.15);
        letter12.setUrgent(true);
        area.assignRegisteredMailDeliver(peter, jucy, letter12);
        
        Parcel parcel12 = new Parcel();
        parcel12.setTrackingNumber("P12");
        parcel12.setWeight(3.0);
        parcel12.setDimensions(12.0);
        parcel12.setRequiresSignature(true);
        area.assignRegisteredMailDeliver(peter, ella, parcel12);
        
        Parcel parcel14 = new Parcel();
        parcel14.setTrackingNumber("P14");
        parcel14.setWeight(4.5);
        parcel14.setDimensions(18.0);
        parcel14.setRequiresSignature(false);
        area.assignRegisteredMailDeliver(peter, ella, parcel14);
        
        Parcel parcel15 = new Parcel();
        parcel15.setTrackingNumber("P15");
        parcel15.setWeight(2.0);
        parcel15.setDimensions(8.0);
        parcel15.setRequiresSignature(true);
        area.assignRegisteredMailDeliver(maria, ella, parcel15);
        
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
        area = new GeographicalArea();
        area.setAreaCode("OT001");
        area.setAreaName("OldTown");
        
        // Add Inhabitants "Ella", "Jucy" to OldTown
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        
        // Add Mailmen "Peter", "Maria" to OldTown
        area.addMailman(peter);
        area.addMailman(maria);
        
        // Create and assign mail items
        Letter letter12 = new Letter();
        letter12.setTrackingNumber("L12");
        letter12.setWeight(0.15);
        letter12.setUrgent(true);
        area.assignRegisteredMailDeliver(peter, jucy, letter12);
        
        Parcel parcel12 = new Parcel();
        parcel12.setTrackingNumber("P12");
        parcel12.setWeight(3.0);
        parcel12.setDimensions(12.0);
        parcel12.setRequiresSignature(true);
        area.assignRegisteredMailDeliver(peter, ella, parcel12);
        
        Parcel parcel14 = new Parcel();
        parcel14.setTrackingNumber("P14");
        parcel14.setWeight(4.5);
        parcel14.setDimensions(18.0);
        parcel14.setRequiresSignature(false);
        area.assignRegisteredMailDeliver(peter, ella, parcel14);
        
        Parcel parcel15 = new Parcel();
        parcel15.setTrackingNumber("P15");
        parcel15.setWeight(2.0);
        parcel15.setDimensions(8.0);
        parcel15.setRequiresSignature(true);
        area.assignRegisteredMailDeliver(maria, jucy, parcel15);
        
        // Remove Ella, Maria from OldTown and assign Maria's mails to Peter
        area.removeInhabitant(ella);
        area.removeMailman(maria, peter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = area.listRegisteredMail(peter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel15 (reassigned from Maria)", result.contains(parcel15));
        // Parcel12 and Parcel14 were removed when Ella was removed
        assertFalse("Should not contain parcel12 (removed with Ella)", result.contains(parcel12));
        assertFalse("Should not contain parcel14 (removed with Ella)", result.contains(parcel14));
    }
}