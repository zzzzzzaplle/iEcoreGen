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
        // Initialize geographical areas before each test
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
    }

    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        linda.setId("L001");
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
    }

    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        // 2. Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        ken.setName("Ken");
        ken.setId("K001");
        lakeside.addMailman(ken);
        
        // 3. Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        paul.setId("P001");
        lakeside.addInhabitant(paul);
        
        // 4. Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup
        assertTrue("Lakeside should contain Paul's mail", lakeside.getAllMails().contains(letter6));
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", lakeside.getInhabitants().contains(paul));
        assertFalse("Lakeside should not contain Letter6 after Paul removal", lakeside.getAllMails().contains(letter6));
    }

    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        
        // Action & Expected Output: 
        // Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        linda.setId("L002");
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Adding Linda to Downtown should return true", result1);
        
        // Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        becy.setId("B001");
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Adding Becy to Downtown should return true", result2);
        
        // Verify both inhabitants are present
        assertEquals("Downtown should have 2 inhabitants", 2, downtown.getInhabitants().size());
        
        // Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Removing Linda from Downtown should return true", result3);
        
        // Verify Linda is removed and Becy remains
        assertFalse("Downtown should not contain Linda after removal", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
        assertEquals("Downtown should have 1 inhabitant after removal", 1, downtown.getInhabitants().size());
    }

    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        victor.setId("V001");
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor from Hillside should return false", result);
    }

    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        // 2. Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        amy.setName("Amy");
        amy.setId("A001");
        beachfront.addMailman(amy);
        
        // 3. Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        rachel.setId("R001");
        beachfront.addInhabitant(rachel);
        
        // 4. Create and assign:
        // - Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        
        // - Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup
        assertEquals("Beachfront should have 2 mail items", 2, beachfront.getAllMails().size());
        assertTrue("Beachfront should contain Letter7", beachfront.getAllMails().contains(letter7));
        assertTrue("Beachfront should contain Parcel4", beachfront.getAllMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", beachfront.getInhabitants().contains(rachel));
        assertTrue("Beachfront mail list should be empty", beachfront.getAllMails().isEmpty());
    }
}