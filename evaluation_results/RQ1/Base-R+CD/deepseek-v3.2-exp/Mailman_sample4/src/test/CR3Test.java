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
        // Create GeographicalArea "Riverside"
        Inhabitant linda = new Inhabitant();
        linda.setId("1");
        linda.setName("Linda");
        
        // Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue(result);
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Create GeographicalArea "Lakeside"
        // Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        ken.setId("M1");
        ken.setName("Ken");
        lakeside.addMailman(ken);
        
        // Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setId("I1");
        paul.setName("Paul");
        lakeside.addInhabitant(paul);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setId("L6");
        letter6.setAddressee(paul);
        letter6.setCarrier(ken);
        lakeside.getAllMails().add(letter6);
        
        // Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue(result);
        assertFalse(lakeside.getInhabitants().contains(paul));
        assertFalse(lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Create GeographicalArea "Downtown"
        Inhabitant linda = new Inhabitant();
        linda.setId("1");
        linda.setName("Linda");
        
        Inhabitant becy = new Inhabitant();
        becy.setId("2");
        becy.setName("Becy");
        
        // Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue(result1);
        
        // Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue(result2);
        
        // Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue(result3);
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Create GeographicalArea "Hillside"
        Inhabitant victor = new Inhabitant();
        victor.setId("1");
        victor.setName("Victor");
        
        // Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse(result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Create GeographicalArea "Beachfront"
        // Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        amy.setId("M1");
        amy.setName("Amy");
        beachfront.addMailman(amy);
        
        // Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setId("I1");
        rachel.setName("Rachel");
        beachfront.addInhabitant(rachel);
        
        // Create and assign Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setId("L7");
        letter7.setAddressee(rachel);
        letter7.setCarrier(amy);
        beachfront.getAllMails().add(letter7);
        
        // Create and assign Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setId("P4");
        parcel4.setAddressee(rachel);
        parcel4.setCarrier(amy);
        beachfront.getAllMails().add(parcel4);
        
        // Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue(result);
        assertFalse(beachfront.getInhabitants().contains(rachel));
        assertFalse(beachfront.getAllMails().contains(letter7));
        assertFalse(beachfront.getAllMails().contains(parcel4));
    }
}