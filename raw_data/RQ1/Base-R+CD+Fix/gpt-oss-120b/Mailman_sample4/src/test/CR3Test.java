import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private GeographicalArea area;
    private Mailman mailman;
    private Inhabitant inhabitant;
    private Letter letter;
    private Parcel parcel;
    
    @Before
    public void setUp() {
        // Common setup for test cases
        area = new GeographicalArea();
        mailman = new Mailman();
        inhabitant = new Inhabitant();
        letter = new Letter();
        parcel = new Parcel();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        GeographicalArea riverside = new GeographicalArea();
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        linda.setId("L001");
        
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding new inhabitant Linda should return true", result);
        assertTrue("Riverside should contain Linda after addition", 
                   riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        GeographicalArea lakeside = new GeographicalArea();
        
        // Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        ken.setName("Ken");
        ken.setId("K001");
        lakeside.addMailman(ken);
        
        // Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        paul.setId("P001");
        lakeside.addInhabitant(paul);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setAddressee(paul);
        letter6.setCarrier(ken);
        lakeside.addMail(letter6);
        
        // Verify setup: Paul exists and letter is assigned
        assertTrue("Lakeside should contain Paul", lakeside.getInhabitants().contains(paul));
        assertTrue("Lakeside should contain letter6", lakeside.getAllMails().contains(letter6));
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", 
                    lakeside.getInhabitants().contains(paul));
        assertFalse("Lakeside should not contain letter6 after Paul's removal", 
                    lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        GeographicalArea downtown = new GeographicalArea();
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        linda.setId("L002");
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Adding Linda should return true", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        becy.setId("B001");
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Adding Becy should return true", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Removing Linda should return true", result3);
        assertFalse("Downtown should not contain Linda after removal", 
                    downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy after Linda's removal", 
                   downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        GeographicalArea hillside = new GeographicalArea();
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        victor.setId("V001");
        
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor should return false", result);
        assertTrue("Hillside inhabitants list should remain empty", 
                   hillside.getInhabitants().isEmpty());
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        GeographicalArea beachfront = new GeographicalArea();
        
        // Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        amy.setName("Amy");
        amy.setId("A001");
        beachfront.addMailman(amy);
        
        // Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        rachel.setId("R001");
        beachfront.addInhabitant(rachel);
        
        // Create and assign: Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setAddressee(rachel);
        letter7.setCarrier(amy);
        beachfront.addMail(letter7);
        
        // Create and assign: Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setAddressee(rachel);
        parcel4.setCarrier(amy);
        beachfront.addMail(parcel4);
        
        // Verify setup: Rachel exists and both mail items are assigned
        assertTrue("Beachfront should contain Rachel", beachfront.getInhabitants().contains(rachel));
        assertTrue("Beachfront should contain letter7", beachfront.getAllMails().contains(letter7));
        assertTrue("Beachfront should contain parcel4", beachfront.getAllMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", 
                    beachfront.getInhabitants().contains(rachel));
        assertFalse("Beachfront should not contain letter7 after Rachel's removal", 
                    beachfront.getAllMails().contains(letter7));
        assertFalse("Beachfront should not contain parcel4 after Rachel's removal", 
                    beachfront.getAllMails().contains(parcel4));
    }
}