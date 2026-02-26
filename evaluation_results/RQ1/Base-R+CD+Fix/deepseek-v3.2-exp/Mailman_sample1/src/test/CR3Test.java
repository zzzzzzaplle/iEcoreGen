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
        // Initialize geographical areas
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
        
        // Initialize mailmen
        ken = new Mailman();
        ken.setName("Ken");
        
        amy = new Mailman();
        amy.setName("Amy");
        
        // Initialize inhabitants
        linda = new Inhabitant();
        linda.setName("Linda");
        
        paul = new Inhabitant();
        paul.setName("Paul");
        
        becy = new Inhabitant();
        becy.setName("Becy");
        
        victor = new Inhabitant();
        victor.setName("Victor");
        
        rachel = new Inhabitant();
        rachel.setName("Rachel");
        
        // Initialize mail items
        letter6 = new Letter();
        letter7 = new Letter();
        parcel4 = new Parcel();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda after addition", 
                   riverside.getInhabitants().contains(linda));
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
        letter6.setAddressee(paul);
        letter6.setCarrier(ken);
        lakeside.getAllMails().add(letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", 
                    lakeside.getInhabitants().contains(paul));
        assertFalse("Letter6 should be deleted after Paul's removal", 
                    lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Adding Linda to Downtown should return true", result1);
        assertTrue("Downtown should contain Linda after addition", 
                   downtown.getInhabitants().contains(linda));
        
        // - Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Adding Becy to Downtown should return true", result2);
        assertTrue("Downtown should contain Becy after addition", 
                   downtown.getInhabitants().contains(becy));
        
        // - Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Removing Linda from Downtown should return true", result3);
        assertFalse("Downtown should not contain Linda after removal", 
                    downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy after Linda's removal", 
                   downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor from Hillside should return false", result);
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
        letter7.setAddressee(rachel);
        letter7.setCarrier(amy);
        beachfront.getAllMails().add(letter7);
        
        //    - Parcel4 for Rachel (Amy)
        parcel4.setAddressee(rachel);
        parcel4.setCarrier(amy);
        beachfront.getAllMails().add(parcel4);
        
        // Verify initial state
        assertEquals("Beachfront should have 2 mail items initially", 2, beachfront.getAllMails().size());
        assertTrue("Beachfront should contain Letter7 initially", beachfront.getAllMails().contains(letter7));
        assertTrue("Beachfront should contain Parcel4 initially", beachfront.getAllMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", 
                    beachfront.getInhabitants().contains(rachel));
        assertFalse("Letter7 should be deleted after Rachel's removal", 
                    beachfront.getAllMails().contains(letter7));
        assertFalse("Parcel4 should be deleted after Rachel's removal", 
                    beachfront.getAllMails().contains(parcel4));
        assertEquals("Beachfront should have 0 mail items after removal", 0, beachfront.getAllMails().size());
    }
}