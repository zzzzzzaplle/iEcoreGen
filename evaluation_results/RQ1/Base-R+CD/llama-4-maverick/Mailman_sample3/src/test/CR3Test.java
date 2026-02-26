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
    
    @Before
    public void setUp() {
        // Create geographical areas
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
        
        // Create mailmen
        ken = new Mailman();
        amy = new Mailman();
        
        // Create inhabitants
        linda = new Inhabitant();
        paul = new Inhabitant();
        becy = new Inhabitant();
        victor = new Inhabitant();
        rachel = new Inhabitant();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        assertTrue("Linda should be added successfully to Riverside", result);
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp for Test Case 2
        lakeside.addMailman(ken);
        lakeside.addInhabitant(paul);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup: Paul should have one registered mail
        List<RegisteredMail> paulMailBefore = lakeside.listRegisteredMail(paul);
        assertNotNull("Paul should have registered mail before removal", paulMailBefore);
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Paul should be removed successfully", result);
        
        // Verify that Paul's mail was deleted
        List<RegisteredMail> paulMailAfter = lakeside.listRegisteredMail(paul);
        assertNull("Paul's mail should be deleted after removal", paulMailAfter);
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: Add multiple inhabitants to Downtown
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Linda should be added successfully to Downtown", result1);
        
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Becy should be added successfully to Downtown", result2);
        
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Linda should be removed successfully from Downtown", result3);
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        assertFalse("Removing non-existent Victor should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp for Test Case 5
        beachfront.addMailman(amy);
        beachfront.addInhabitant(rachel);
        
        // Create and assign multiple mail items for Rachel
        Letter letter7 = new Letter();
        Parcel parcel4 = new Parcel();
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup: Rachel should have two registered mail items
        List<RegisteredMail> rachelMailBefore = beachfront.listRegisteredMail(rachel);
        assertNotNull("Rachel should have registered mail before removal", rachelMailBefore);
        assertEquals("Rachel should have exactly 2 mail items", 2, rachelMailBefore.size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Rachel should be removed successfully", result);
        
        // Verify that Rachel's mail was deleted
        List<RegisteredMail> rachelMailAfter = beachfront.listRegisteredMail(rachel);
        assertNull("Rachel's mail should be deleted after removal", rachelMailAfter);
    }
}