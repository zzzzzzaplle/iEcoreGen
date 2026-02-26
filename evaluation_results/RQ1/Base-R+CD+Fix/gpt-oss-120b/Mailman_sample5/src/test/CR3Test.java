import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

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
        Inhabitant linda = new Inhabitant("L001", "Linda");
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Inhabitant Linda should be added successfully", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: "Remove Inhabitant with Assigned Mail"
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        // 2. Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman("K001", "Ken");
        lakeside.addMailman(ken);
        
        // 3. Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant("P001", "Paul");
        lakeside.addInhabitant(paul);
        
        // 4. Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter(paul);
        lakeside.addRegisteredMail(letter6);
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup
        assertNotNull("Letter6 should have carrier assigned", letter6.getCarrier());
        assertEquals("Letter6 carrier should be Ken", ken, letter6.getCarrier());
        assertEquals("Letter6 addressee should be Paul", paul, letter6.getAddressee());
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Paul should be removed successfully", result);
        assertFalse("Lakeside should not contain Paul anymore", lakeside.getInhabitants().contains(paul));
        
        // Verify that Letter6 was deleted (since it had a carrier assigned)
        assertFalse("Letter6 should be deleted from allMails", lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: "Add Multiple Inhabitants to Area"
        // SetUp: Create GeographicalArea "Downtown"
        
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant("L002", "Linda");
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("First inhabitant Linda should be added successfully", result1);
        
        // - Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant("B001", "Becy");
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Second inhabitant Becy should be added successfully", result2);
        
        // - Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Inhabitant Linda should be removed successfully", result3);
        
        // Verify final state
        assertFalse("Downtown should not contain Linda after removal", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
        assertEquals("Downtown should have 1 inhabitant remaining", 1, downtown.getInhabitants().size());
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: "Remove Non-existent Inhabitant"
        // SetUp: Create GeographicalArea "Hillside"
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant("V001", "Victor");
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: "Remove Inhabitant with Multiple Mail Items"
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        // 2. Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman("A001", "Amy");
        beachfront.addMailman(amy);
        
        // 3. Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant("R001", "Rachel");
        beachfront.addInhabitant(rachel);
        
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        Letter letter7 = new Letter(rachel);
        beachfront.addRegisteredMail(letter7);
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        
        //    - Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel(rachel);
        beachfront.addRegisteredMail(parcel4);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup
        assertNotNull("Letter7 should have carrier assigned", letter7.getCarrier());
        assertNotNull("Parcel4 should have carrier assigned", parcel4.getCarrier());
        assertEquals("Letter7 carrier should be Amy", amy, letter7.getCarrier());
        assertEquals("Parcel4 carrier should be Amy", amy, parcel4.getCarrier());
        assertEquals("Letter7 addressee should be Rachel", rachel, letter7.getAddressee());
        assertEquals("Parcel4 addressee should be Rachel", rachel, parcel4.getAddressee());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Rachel should be removed successfully", result);
        assertFalse("Beachfront should not contain Rachel anymore", beachfront.getInhabitants().contains(rachel));
        
        // Verify that both mail items were deleted (since they had carriers assigned)
        assertFalse("Letter7 should be deleted from allMails", beachfront.getAllMails().contains(letter7));
        assertFalse("Parcel4 should be deleted from allMails", beachfront.getAllMails().contains(parcel4));
    }
}