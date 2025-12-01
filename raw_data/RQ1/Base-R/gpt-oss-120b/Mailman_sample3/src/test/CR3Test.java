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
    
    private Inhabitant linda;
    private Inhabitant paul;
    private Inhabitant becy;
    private Inhabitant victor;
    private Inhabitant rachel;
    
    private Mailman ken;
    private Mailman amy;
    
    @Before
    public void setUp() {
        // Initialize geographical areas
        riverside = new GeographicalArea();
        riverside.setName("Riverside");
        
        lakeside = new GeographicalArea();
        lakeside.setName("Lakeside");
        
        downtown = new GeographicalArea();
        downtown.setName("Downtown");
        
        hillside = new GeographicalArea();
        hillside.setName("Hillside");
        
        beachfront = new GeographicalArea();
        beachfront.setName("Beachfront");
        
        // Initialize inhabitants
        linda = new Inhabitant();
        linda.setId("L001");
        linda.setName("Linda");
        
        paul = new Inhabitant();
        paul.setId("P001");
        paul.setName("Paul");
        
        becy = new Inhabitant();
        becy.setId("B001");
        becy.setName("Becy");
        
        victor = new Inhabitant();
        victor.setId("V001");
        victor.setName("Victor");
        
        rachel = new Inhabitant();
        rachel.setId("R001");
        rachel.setName("Rachel");
        
        // Initialize mailmen
        ken = new Mailman();
        ken.setId("K001");
        ken.setName("Ken");
        
        amy = new Mailman();
        amy.setId("A001");
        amy.setName("Amy");
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Linda should be successfully added to Riverside", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
        assertEquals("Linda's area should be set to Riverside", riverside, linda.getArea());
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        // 2. Add Mailman "Ken" to Lakeside
        lakeside.addMailman(ken);
        // 3. Add Inhabitant "Paul" to Lakeside
        lakeside.addInhabitant(paul);
        // 4. Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setTrackingNumber("LTR6");
        letter6.setAddressee(paul);
        lakeside.addRegisteredMail(letter6);
        lakeside.assignMail(letter6, ken);
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Paul should be successfully removed from Lakeside", result);
        assertFalse("Lakeside should not contain Paul", lakeside.getInhabitants().contains(paul));
        assertNull("Paul's area should be set to null", paul.getArea());
        assertFalse("Letter6 should be deleted from registered mails", lakeside.getRegisteredMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true.
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Linda should be successfully added to Downtown", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // - Add new Inhabitant "Becy" to Downtown, true.
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Becy should be successfully added to Downtown", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // - Remove Inhabitant "Linda" to Downtown, true.
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Linda should be successfully removed from Downtown", result3);
        assertFalse("Downtown should not contain Linda", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor should return false", result);
        assertEquals("Hillside should have no inhabitants", 0, hillside.getInhabitants().size());
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        // 2. Add Mailman "Amy" to Beachfront
        beachfront.addMailman(amy);
        // 3. Add Inhabitant "Rachel" to Beachfront
        beachfront.addInhabitant(rachel);
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setTrackingNumber("LTR7");
        letter7.setAddressee(rachel);
        beachfront.addRegisteredMail(letter7);
        beachfront.assignMail(letter7, amy);
        
        //    - Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setTrackingNumber("PCL4");
        parcel4.setAddressee(rachel);
        beachfront.addRegisteredMail(parcel4);
        beachfront.assignMail(parcel4, amy);
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Rachel should be successfully removed from Beachfront", result);
        assertFalse("Beachfront should not contain Rachel", beachfront.getInhabitants().contains(rachel));
        assertNull("Rachel's area should be set to null", rachel.getArea());
        assertFalse("Letter7 should be deleted from registered mails", beachfront.getRegisteredMails().contains(letter7));
        assertFalse("Parcel4 should be deleted from registered mails", beachfront.getRegisteredMails().contains(parcel4));
        assertEquals("Beachfront should have no registered mails after removal", 0, beachfront.getRegisteredMails().size());
    }
}