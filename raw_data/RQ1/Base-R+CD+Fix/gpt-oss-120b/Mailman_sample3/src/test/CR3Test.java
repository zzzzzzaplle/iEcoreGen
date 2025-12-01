import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR3Test {
    
    private GeographicalArea riverside;
    private GeographicalArea lakeside;
    private GeographicalArea downtown;
    private GeographicalArea hillside;
    private GeographicalArea beachfront;
    
    @Before
    public void setUp() {
        // Initialize geographical areas for each test
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: "Add New Inhabitant to Area"
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant("Linda");
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding new inhabitant Linda should return true", result);
        assertTrue("Riverside should contain inhabitant Linda", 
                   riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: "Remove Inhabitant with Assigned Mail"
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        // 2. Add Mailman "Ken" to Lakeside
        // 3. Add Inhabitant "Paul" to Lakeside
        // 4. Create and assign Letter6 for Paul (Ken)
        Mailman ken = new Mailman("Ken");
        lakeside.addMailman(ken);
        
        Inhabitant paul = new Inhabitant("Paul");
        lakeside.addInhabitant(paul);
        
        Letter letter6 = new Letter(paul, "Letter6 content");
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup - letter should be assigned to Ken and addressed to Paul
        assertEquals("Letter6 carrier should be Ken", ken, letter6.getCarrier());
        assertEquals("Letter6 addressee should be Paul", paul, letter6.getAddressee());
        assertTrue("Lakeside should contain letter6", lakeside.getAllMails().contains(letter6));
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", 
                    lakeside.getInhabitants().contains(paul));
        assertFalse("Lakeside should not contain letter6 after Paul removal", 
                    lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: "Add Multiple Inhabitants to Area"
        // SetUp: Create GeographicalArea "Downtown"
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true.
        // - Add new Inhabitant "Becy" to Downtown, true.
        // - Remove Inhabitant "Linda" to Downtown, true.
        
        Inhabitant linda = new Inhabitant("Linda");
        Inhabitant becy = new Inhabitant("Becy");
        
        // Add Linda - expected true
        boolean addLindaResult = downtown.addInhabitant(linda);
        assertTrue("Adding Linda should return true", addLindaResult);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // Add Becy - expected true
        boolean addBecyResult = downtown.addInhabitant(becy);
        assertTrue("Adding Becy should return true", addBecyResult);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // Remove Linda - expected true
        boolean removeLindaResult = downtown.removeInhabitant(linda);
        assertTrue("Removing Linda should return true", removeLindaResult);
        assertFalse("Downtown should not contain Linda after removal", 
                    downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: "Remove Non-existent Inhabitant"
        // SetUp: Create GeographicalArea "Hillside"
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant("Victor");
        
        // Verify Victor is not in Hillside initially
        assertFalse("Hillside should not contain Victor initially", 
                    hillside.getInhabitants().contains(victor));
        
        // Attempt to remove non-existent Victor
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: "Remove Inhabitant with Multiple Mail Items"
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        // 2. Add Mailman "Amy" to Beachfront
        // 3. Add Inhabitant "Rachel" to Beachfront
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        //    - Parcel4 for Rachel (Amy)
        Mailman amy = new Mailman("Amy");
        beachfront.addMailman(amy);
        
        Inhabitant rachel = new Inhabitant("Rachel");
        beachfront.addInhabitant(rachel);
        
        Letter letter7 = new Letter(rachel, "Letter7 content");
        Parcel parcel4 = new Parcel(rachel, 4.5);
        
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup - both mail items should be assigned and present
        assertEquals("Letter7 carrier should be Amy", amy, letter7.getCarrier());
        assertEquals("Letter7 addressee should be Rachel", rachel, letter7.getAddressee());
        assertEquals("Parcel4 carrier should be Amy", amy, parcel4.getCarrier());
        assertEquals("Parcel4 addressee should be Rachel", rachel, parcel4.getAddressee());
        assertTrue("Beachfront should contain letter7", beachfront.getAllMails().contains(letter7));
        assertTrue("Beachfront should contain parcel4", beachfront.getAllMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", 
                    beachfront.getInhabitants().contains(rachel));
        assertFalse("Beachfront should not contain letter7 after Rachel removal", 
                    beachfront.getAllMails().contains(letter7));
        assertFalse("Beachfront should not contain parcel4 after Rachel removal", 
                    beachfront.getAllMails().contains(parcel4));
    }
}