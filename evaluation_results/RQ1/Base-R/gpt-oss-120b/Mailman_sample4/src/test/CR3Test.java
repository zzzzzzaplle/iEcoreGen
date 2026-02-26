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
    
    private Letter letter6;
    private Letter letter7;
    private Parcel parcel4;
    
    @Before
    public void setUp() {
        // Initialize geographical areas
        riverside = new GeographicalArea();
        riverside.setAreaId("Riverside");
        
        lakeside = new GeographicalArea();
        lakeside.setAreaId("Lakeside");
        
        downtown = new GeographicalArea();
        downtown.setAreaId("Downtown");
        
        hillside = new GeographicalArea();
        hillside.setAreaId("Hillside");
        
        beachfront = new GeographicalArea();
        beachfront.setAreaId("Beachfront");
        
        // Initialize inhabitants
        linda = new Inhabitant();
        linda.setInhabitantId("L001");
        linda.setName("Linda");
        
        paul = new Inhabitant();
        paul.setInhabitantId("P001");
        paul.setName("Paul");
        
        becy = new Inhabitant();
        becy.setInhabitantId("B001");
        becy.setName("Becy");
        
        victor = new Inhabitant();
        victor.setInhabitantId("V001");
        victor.setName("Victor");
        
        rachel = new Inhabitant();
        rachel.setInhabitantId("R001");
        rachel.setName("Rachel");
        
        // Initialize mailmen
        ken = new Mailman();
        ken.setMailmanId("K001");
        ken.setName("Ken");
        
        amy = new Mailman();
        amy.setMailmanId("A001");
        amy.setName("Amy");
        
        // Initialize mail items
        letter6 = new Letter();
        letter6.setMailId("LTR6");
        
        letter7 = new Letter();
        letter7.setMailId("LTR7");
        
        parcel4 = new Parcel();
        parcel4.setMailId("PCL4");
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
        letter6.setAddressee(paul);
        lakeside.getRegisteredMails().add(letter6);
        lakeside.assignMailToMailman(letter6, ken);
        
        // Verify setup is correct
        assertNotNull("Letter6 should be assigned to Ken", letter6.getAssignedMailman());
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Paul should be successfully removed from Lakeside", result);
        assertFalse("Lakeside should not contain Paul", lakeside.getInhabitants().contains(paul));
        assertNull("Paul's area should be set to null", paul.getArea());
        assertFalse("Letter6 should be deleted from registered mails", 
                   lakeside.getRegisteredMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Linda should be successfully added to Downtown", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // - Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Becy should be successfully added to Downtown", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // - Remove Inhabitant "Linda" to Downtown, true
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
        assertEquals("Hillside should have 0 inhabitants", 0, hillside.getInhabitants().size());
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
        beachfront.getRegisteredMails().add(letter7);
        beachfront.assignMailToMailman(letter7, amy);
        
        //    - Parcel4 for Rachel (Amy)
        parcel4.setAddressee(rachel);
        beachfront.getRegisteredMails().add(parcel4);
        beachfront.assignMailToMailman(parcel4, amy);
        
        // Verify setup is correct
        assertEquals("Beachfront should have 2 registered mails", 2, beachfront.getRegisteredMails().size());
        assertNotNull("Letter7 should be assigned to Amy", letter7.getAssignedMailman());
        assertNotNull("Parcel4 should be assigned to Amy", parcel4.getAssignedMailman());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Rachel should be successfully removed from Beachfront", result);
        assertFalse("Beachfront should not contain Rachel", beachfront.getInhabitants().contains(rachel));
        assertNull("Rachel's area should be set to null", rachel.getArea());
        assertEquals("Both assigned mail items should be deleted", 0, beachfront.getRegisteredMails().size());
    }
}