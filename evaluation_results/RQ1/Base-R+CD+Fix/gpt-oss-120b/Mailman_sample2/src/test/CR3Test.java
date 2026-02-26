import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda after addition", 
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
        
        Letter letter6 = new Letter(paul);
        letter6.setCarrier(ken);
        lakeside.getAllMails().add(letter6);
        
        // Verify setup - mail should be assigned to Paul
        assertEquals("Letter6 should be assigned to Paul", paul, letter6.getAddressee());
        assertEquals("Letter6 should be carried by Ken", ken, letter6.getCarrier());
        assertTrue("Lakeside should contain letter6", lakeside.getAllMails().contains(letter6));
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", 
                    lakeside.getInhabitants().contains(paul));
        assertFalse("Letter6 should be deleted after removing Paul", 
                    lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: "Add Multiple Inhabitants to Area"
        // SetUp: Create GeographicalArea "Downtown"
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        // - Add new Inhabitant "Becy" to Downtown, true
        // - Remove Inhabitant "Linda" to Downtown, true
        
        Inhabitant linda = new Inhabitant("Linda");
        Inhabitant becy = new Inhabitant("Becy");
        
        // Add Linda - should return true
        boolean addLindaResult = downtown.addInhabitant(linda);
        assertTrue("Adding Linda to Downtown should return true", addLindaResult);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // Add Becy - should return true
        boolean addBecyResult = downtown.addInhabitant(becy);
        assertTrue("Adding Becy to Downtown should return true", addBecyResult);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // Remove Linda - should return true
        boolean removeLindaResult = downtown.removeInhabitant(linda);
        assertTrue("Removing Linda from Downtown should return true", removeLindaResult);
        assertFalse("Downtown should not contain Linda after removal", 
                    downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy after removing Linda", 
                   downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: "Remove Non-existent Inhabitant"
        // SetUp: Create GeographicalArea "Hillside"
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant("Victor");
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor from Hillside should return false", result);
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
        
        Letter letter7 = new Letter(rachel);
        letter7.setCarrier(amy);
        beachfront.getAllMails().add(letter7);
        
        Parcel parcel4 = new Parcel(rachel);
        parcel4.setCarrier(amy);
        beachfront.getAllMails().add(parcel4);
        
        // Verify setup - both mail items should be assigned to Rachel
        assertEquals("Letter7 should be assigned to Rachel", rachel, letter7.getAddressee());
        assertEquals("Letter7 should be carried by Amy", amy, letter7.getCarrier());
        assertEquals("Parcel4 should be assigned to Rachel", rachel, parcel4.getAddressee());
        assertEquals("Parcel4 should be carried by Amy", amy, parcel4.getCarrier());
        assertTrue("Beachfront should contain letter7", beachfront.getAllMails().contains(letter7));
        assertTrue("Beachfront should contain parcel4", beachfront.getAllMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", 
                    beachfront.getInhabitants().contains(rachel));
        assertFalse("Letter7 should be deleted after removing Rachel", 
                    beachfront.getAllMails().contains(letter7));
        assertFalse("Parcel4 should be deleted after removing Rachel", 
                    beachfront.getAllMails().contains(parcel4));
    }
}