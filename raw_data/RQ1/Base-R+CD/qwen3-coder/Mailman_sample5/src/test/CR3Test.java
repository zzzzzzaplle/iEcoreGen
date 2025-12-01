import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        // Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Verify successful addition
        assertTrue("Linda should be successfully added to Riverside", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
    }

    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Set up Lakeside area with mailman and inhabitant
        lakeside.addMailman(ken);
        lakeside.addInhabitant(paul);
        
        // Create and assign mail for Paul
        letter6.setAddressee(paul);
        lakeside.getAllMails().add(letter6);
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify mail is assigned before removal
        assertNotNull("Letter6 should have a carrier assigned", letter6.getCarrier());
        
        // Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Verify successful removal and mail deletion
        assertTrue("Paul should be successfully removed from Lakeside", result);
        assertFalse("Lakeside should not contain Paul", lakeside.getInhabitants().contains(paul));
        assertFalse("Letter6 should be deleted from all mails", lakeside.getAllMails().contains(letter6));
    }

    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Add Linda to Downtown
        boolean addLindaResult = downtown.addInhabitant(linda);
        assertTrue("Linda should be successfully added to Downtown", addLindaResult);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // Add Becy to Downtown
        boolean addBecyResult = downtown.addInhabitant(becy);
        assertTrue("Becy should be successfully added to Downtown", addBecyResult);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // Remove Linda from Downtown
        boolean removeLindaResult = downtown.removeInhabitant(linda);
        assertTrue("Linda should be successfully removed from Downtown", removeLindaResult);
        assertFalse("Downtown should not contain Linda", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }

    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Attempt to remove non-existent Victor from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Verify unsuccessful removal
        assertFalse("Removing non-existent Victor should return false", result);
        assertFalse("Hillside should not contain Victor", hillside.getInhabitants().contains(victor));
    }

    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Set up Beachfront area with mailman and inhabitant
        beachfront.addMailman(amy);
        beachfront.addInhabitant(rachel);
        
        // Create and assign multiple mail items for Rachel
        letter7.setAddressee(rachel);
        parcel4.setAddressee(rachel);
        
        beachfront.getAllMails().add(letter7);
        beachfront.getAllMails().add(parcel4);
        
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify mail items are assigned before removal
        assertNotNull("Letter7 should have a carrier assigned", letter7.getCarrier());
        assertNotNull("Parcel4 should have a carrier assigned", parcel4.getCarrier());
        
        // Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Verify successful removal and mail deletion
        assertTrue("Rachel should be successfully removed from Beachfront", result);
        assertFalse("Beachfront should not contain Rachel", beachfront.getInhabitants().contains(rachel));
        assertFalse("Letter7 should be deleted from all mails", beachfront.getAllMails().contains(letter7));
        assertFalse("Parcel4 should be deleted from all mails", beachfront.getAllMails().contains(parcel4));
    }
}