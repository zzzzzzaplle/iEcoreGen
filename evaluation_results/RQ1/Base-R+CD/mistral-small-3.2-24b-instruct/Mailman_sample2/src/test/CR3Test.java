import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private GeographicalArea geographicalArea;
    
    @Before
    public void setUp() {
        // Reset geographical area before each test
        geographicalArea = new GeographicalArea();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        GeographicalArea riverside = new GeographicalArea();
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding new inhabitant should return true", result);
        assertTrue("Inhabitant should be in the geographical area", 
                  riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        GeographicalArea lakeside = new GeographicalArea();
        
        // Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        lakeside.addMailman(ken);
        
        // Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        lakeside.addInhabitant(paul);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setAddressee(paul);
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup is correct
        assertTrue("Mail should be assigned to Paul", 
                  lakeside.getAllMails().contains(letter6));
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing inhabitant should return true", result);
        assertFalse("Inhabitant should be removed from area", 
                   lakeside.getInhabitants().contains(paul));
        assertFalse("Assigned mail should be deleted", 
                   lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        GeographicalArea downtown = new GeographicalArea();
        
        // Action & Expected Output: 
        // Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Adding first inhabitant should return true", result1);
        assertTrue("First inhabitant should be in area", 
                  downtown.getInhabitants().contains(linda));
        
        // Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant();
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Adding second inhabitant should return true", result2);
        assertTrue("Second inhabitant should be in area", 
                  downtown.getInhabitants().contains(becy));
        assertEquals("Area should have 2 inhabitants", 2, 
                    downtown.getInhabitants().size());
        
        // Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Removing inhabitant should return true", result3);
        assertFalse("First inhabitant should be removed", 
                   downtown.getInhabitants().contains(linda));
        assertTrue("Second inhabitant should remain", 
                  downtown.getInhabitants().contains(becy));
        assertEquals("Area should have 1 inhabitant", 1, 
                    downtown.getInhabitants().size());
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        GeographicalArea hillside = new GeographicalArea();
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant(); // Not added to hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
        assertTrue("Inhabitants list should remain empty", 
                  hillside.getInhabitants().isEmpty());
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        GeographicalArea beachfront = new GeographicalArea();
        
        // Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        beachfront.addMailman(amy);
        
        // Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        beachfront.addInhabitant(rachel);
        
        // Create and assign:
        // - Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setAddressee(rachel);
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        
        // - Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setAddressee(rachel);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup is correct
        assertEquals("Area should have 2 mail items", 2, 
                    beachfront.getAllMails().size());
        assertTrue("Letter should be assigned to Rachel", 
                  beachfront.getAllMails().contains(letter7));
        assertTrue("Parcel should be assigned to Rachel", 
                  beachfront.getAllMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing inhabitant should return true", result);
        assertFalse("Inhabitant should be removed from area", 
                   beachfront.getInhabitants().contains(rachel));
        assertTrue("All mail items should be deleted", 
                  beachfront.getAllMails().isEmpty());
    }
}