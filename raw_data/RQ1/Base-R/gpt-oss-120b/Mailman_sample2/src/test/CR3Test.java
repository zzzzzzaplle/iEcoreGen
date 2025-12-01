import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private MailDeliverySystem system;
    
    @Before
    public void setUp() {
        system = new MailDeliverySystem();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        GeographicalArea riverside = new GeographicalArea("A1", "Riverside");
        system.addArea(riverside);
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant("I1", "Linda");
        boolean result = system.addInhabitant(riverside, linda);
        
        // Expected Output: true (successful addition)
        assertTrue(result);
        assertTrue(riverside.getInhabitants().contains(linda));
        assertEquals(riverside, linda.getArea());
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        GeographicalArea lakeside = new GeographicalArea("A2", "Lakeside");
        system.addArea(lakeside);
        
        // 2. Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman("M1", "Ken");
        system.addMailman(lakeside, ken);
        
        // 3. Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant("I2", "Paul");
        system.addInhabitant(lakeside, paul);
        
        // 4. Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter("L6", paul, "Test Subject", "Test Body");
        lakeside.internalAddRegisteredMail(letter6);
        system.assignMailToMailman(letter6, ken);
        
        // Action: Remove Paul from Lakeside
        boolean result = system.removeInhabitant(lakeside, paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue(result);
        assertFalse(lakeside.getInhabitants().contains(paul));
        assertFalse(lakeside.getRegisteredMails().contains(letter6));
        assertNull(paul.getArea());
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        GeographicalArea downtown = new GeographicalArea("A3", "Downtown");
        system.addArea(downtown);
        
        // Action & Expected Output:
        // - Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant("I3", "Linda");
        boolean result1 = system.addInhabitant(downtown, linda);
        assertTrue(result1);
        assertTrue(downtown.getInhabitants().contains(linda));
        
        // - Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant("I4", "Becy");
        boolean result2 = system.addInhabitant(downtown, becy);
        assertTrue(result2);
        assertTrue(downtown.getInhabitants().contains(becy));
        
        // - Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = system.removeInhabitant(downtown, linda);
        assertTrue(result3);
        assertFalse(downtown.getInhabitants().contains(linda));
        assertTrue(downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        GeographicalArea hillside = new GeographicalArea("A4", "Hillside");
        system.addArea(hillside);
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant("I5", "Victor");
        boolean result = system.removeInhabitant(hillside, victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse(result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        GeographicalArea beachfront = new GeographicalArea("A5", "Beachfront");
        system.addArea(beachfront);
        
        // 2. Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman("M2", "Amy");
        system.addMailman(beachfront, amy);
        
        // 3. Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant("I6", "Rachel");
        system.addInhabitant(beachfront, rachel);
        
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        Letter letter7 = new Letter("L7", rachel, "Important", "Urgent");
        beachfront.internalAddRegisteredMail(letter7);
        system.assignMailToMailman(letter7, amy);
        
        //    - Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel("P4", rachel, 2.5, "Books");
        beachfront.internalAddRegisteredMail(parcel4);
        system.assignMailToMailman(parcel4, amy);
        
        // Action: Remove Rachel from Beachfront
        boolean result = system.removeInhabitant(beachfront, rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue(result);
        assertFalse(beachfront.getInhabitants().contains(rachel));
        assertFalse(beachfront.getRegisteredMails().contains(letter7));
        assertFalse(beachfront.getRegisteredMails().contains(parcel4));
        assertNull(rachel.getArea());
    }
}