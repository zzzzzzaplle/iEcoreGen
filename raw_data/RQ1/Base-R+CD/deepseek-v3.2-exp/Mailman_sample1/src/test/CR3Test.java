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
        // Initialize geographical areas for tests
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant("1", "Linda");
        
        boolean result = riverside.addInhabitant(linda);
        
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: Remove Inhabitant with Assigned Mail
        Mailman ken = new Mailman("M1", "Ken");
        Inhabitant paul = new Inhabitant("I1", "Paul");
        Letter letter6 = new Letter("L6");
        
        lakeside.addMailman(ken);
        lakeside.addInhabitant(paul);
        lakeside.addRegisteredMail(letter6);
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        boolean result = lakeside.removeInhabitant(paul);
        
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul", lakeside.getInhabitants().contains(paul));
        assertFalse("Letter6 should be deleted", lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: Add Multiple Inhabitants to Area
        Inhabitant linda = new Inhabitant("I1", "Linda");
        Inhabitant becy = new Inhabitant("I2", "Becy");
        
        boolean result1 = downtown.addInhabitant(linda);
        boolean result2 = downtown.addInhabitant(becy);
        boolean result3 = downtown.removeInhabitant(linda);
        
        assertTrue("Adding Linda to Downtown should return true", result1);
        assertTrue("Adding Becy to Downtown should return true", result2);
        assertTrue("Removing Linda from Downtown should return true", result3);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        assertFalse("Downtown should not contain Linda", downtown.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: Remove Non-existent Inhabitant
        Inhabitant victor = new Inhabitant("I1", "Victor");
        
        boolean result = hillside.removeInhabitant(victor);
        
        assertFalse("Removing non-existent Victor from Hillside should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: Remove Inhabitant with Multiple Mail Items
        Mailman amy = new Mailman("M1", "Amy");
        Inhabitant rachel = new Inhabitant("I1", "Rachel");
        Letter letter7 = new Letter("L7");
        Parcel parcel4 = new Parcel("P4");
        
        beachfront.addMailman(amy);
        beachfront.addInhabitant(rachel);
        beachfront.addRegisteredMail(letter7);
        beachfront.addRegisteredMail(parcel4);
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        boolean result = beachfront.removeInhabitant(rachel);
        
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel", beachfront.getInhabitants().contains(rachel));
        assertFalse("Letter7 should be deleted", beachfront.getAllMails().contains(letter7));
        assertFalse("Parcel4 should be deleted", beachfront.getAllMails().contains(parcel4));
    }
}