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
    private Mailman ken;
    private Mailman amy;
    private Inhabitant linda;
    private Inhabitant paul;
    private Inhabitant becy;
    private Inhabitant victor;
    private Inhabitant rachel;
    private Letter letter6;
    private Letter letter7;
    private Parcel parcel4;

    @Before
    public void setUp() {
        // Initialize all geographical areas
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
        
        // Initialize mailmen
        ken = new Mailman();
        amy = new Mailman();
        
        // Initialize inhabitants
        linda = new Inhabitant();
        paul = new Inhabitant();
        becy = new Inhabitant();
        victor = new Inhabitant();
        rachel = new Inhabitant();
        
        // Initialize mail items
        letter6 = new Letter();
        letter7 = new Letter();
        parcel4 = new Parcel();
    }

    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: "Add New Inhabitant to Area"
        // Action: Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Linda should be added successfully to Riverside", result);
    }

    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: "Remove Inhabitant with Assigned Mail"
        // SetUp:
        // 1. Add Mailman "Ken" to Lakeside
        lakeside.addMailman(ken);
        // 2. Add Inhabitant "Paul" to Lakeside
        lakeside.addInhabitant(paul);
        // 3. Create and assign Letter6 for Paul (Ken)
        letter6.setAddressee(paul);
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup: Paul should have one registered mail assigned to Ken
        List<RegisteredMail> paulsMailBefore = lakeside.listRegisteredMail(paul);
        assertNotNull("Paul should have registered mail before removal", paulsMailBefore);
        assertEquals("Paul should have exactly 1 registered mail", 1, paulsMailBefore.size());
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Paul should be removed successfully from Lakeside", result);
        
        // Verify that Paul's mail was deleted
        List<RegisteredMail> paulsMailAfter = lakeside.listRegisteredMail(paul);
        assertNull("Paul's registered mail should be deleted after removal", paulsMailAfter);
    }

    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: "Add Multiple Inhabitants to Area"
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Linda should be added successfully to Downtown", result1);
        
        // - Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Becy should be added successfully to Downtown", result2);
        
        // - Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Linda should be removed successfully from Downtown", result3);
    }

    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: "Remove Non-existent Inhabitant"
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor should return false", result);
    }

    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: "Remove Inhabitant with Multiple Mail Items"
        // SetUp:
        // 1. Add Mailman "Amy" to Beachfront
        beachfront.addMailman(amy);
        // 2. Add Inhabitant "Rachel" to Beachfront
        beachfront.addInhabitant(rachel);
        // 3. Create and assign:
        //    - Letter7 for Rachel (Amy)
        letter7.setAddressee(rachel);
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        //    - Parcel4 for Rachel (Amy)
        parcel4.setAddressee(rachel);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup: Rachel should have two registered mails assigned to Amy
        List<RegisteredMail> rachelsMailBefore = beachfront.listRegisteredMail(rachel);
        assertNotNull("Rachel should have registered mail before removal", rachelsMailBefore);
        assertEquals("Rachel should have exactly 2 registered mails", 2, rachelsMailBefore.size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Rachel should be removed successfully from Beachfront", result);
        
        // Verify that Rachel's mail was deleted
        List<RegisteredMail> rachelsMailAfter = beachfront.listRegisteredMail(rachel);
        assertNull("Rachel's registered mail should be deleted after removal", rachelsMailAfter);
    }
}