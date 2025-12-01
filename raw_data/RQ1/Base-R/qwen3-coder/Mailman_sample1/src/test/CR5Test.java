import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private MailDeliverySystem mailDeliverySystem;
    private Area area1;
    private Area area2;
    private Area area3;
    private Area area4;
    private Inhabitant zoe;
    private Inhabitant mia;
    private Inhabitant ella;
    private Inhabitant luke;
    private Inhabitant noah;
    private Mailman aaron;
    private Mailman peter;
    private Mailman ruby;
    
    @Before
    public void setUp() {
        mailDeliverySystem = new MailDeliverySystem();
        
        // Create areas
        area1 = new Area();
        area1.setName("MainStreet");
        
        area2 = new Area();
        area2.setName("MarketSquare");
        
        area3 = new Area();
        area3.setName("OldTown");
        
        area4 = new Area();
        area4.setName("NewDistrict");
        
        // Create inhabitants
        zoe = new Inhabitant();
        zoe.setName("Zoe");
        zoe.setArea(area1);
        
        mia = new Inhabitant();
        mia.setName("Mia");
        mia.setArea(area2);
        
        ella = new Inhabitant();
        ella.setName("Ella");
        ella.setArea(area3);
        
        luke = new Inhabitant();
        luke.setName("Luke");
        luke.setArea(area4);
        
        noah = new Inhabitant();
        noah.setName("Noah");
        noah.setArea(null); // Non-existent in any area
        
        // Create mailmen
        aaron = new Mailman();
        aaron.setName("Aaron");
        
        peter = new Mailman();
        peter.setName("Peter");
        
        ruby = new Mailman();
        ruby.setName("Ruby");
        
        // Add mailmen to areas
        area1.addMailman(aaron);
        area3.addMailman(peter);
        area4.addMailman(ruby);
        
        // Add inhabitants to areas
        area1.addInhabitant(zoe);
        area2.addInhabitant(mia);
        area3.addInhabitant(ella);
        area4.addInhabitant(luke);
        
        // Add areas to mail delivery system
        mailDeliverySystem.getAreas().add(area1);
        mailDeliverySystem.getAreas().add(area2);
        mailDeliverySystem.getAreas().add(area3);
        mailDeliverySystem.getAreas().add(area4);
        
        // Assign mailmen to areas for area checking
        aaron.addAssignedArea(area1);
        peter.addAssignedArea(area3);
        ruby.addAssignedArea(area4);
    }
    
    @Test
    public void testCase1_listMultipleMailItemsForInhabitant() {
        // Create and assign Letter10 for Zoe
        Letter letter10 = new Letter();
        letter10.setContent("Letter10 content");
        letter10.setAddressee(zoe);
        letter10.setArea(area1);
        letter10.setAssignedMailman(aaron);
        
        // Create and assign Parcel6 for Zoe
        Parcel parcel6 = new Parcel();
        parcel6.setWeight(6.0);
        parcel6.setAddressee(zoe);
        parcel6.setArea(area1);
        parcel6.setAssignedMailman(aaron);
        
        // Add mail to area
        area1.getRegisteredMails().add(letter10);
        area1.getRegisteredMails().add(parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = mailDeliverySystem.getMailForInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_listMailForInhabitantWithNoMail() {
        // Action: List all mail for Mia (who has no mail)
        List<RegisteredMail> result = mailDeliverySystem.getMailForInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_listMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = mailDeliverySystem.getMailForInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_listOnlyAssignedMailForInhabitant() {
        // Create Letter11 assigned to mailman "Peter" in "OldTown" (not for Ella)
        Letter letter11 = new Letter();
        letter11.setContent("Letter11 content");
        Inhabitant otherInhabitant = new Inhabitant();
        otherInhabitant.setName("Other");
        otherInhabitant.setArea(area3);
        letter11.setAddressee(otherInhabitant);
        letter11.setArea(area3);
        letter11.setAssignedMailman(peter);
        
        // Create Letter12 for Ella
        Letter letter12 = new Letter();
        letter12.setContent("Letter12 content");
        letter12.setAddressee(ella);
        letter12.setArea(area3);
        
        // Add mail to area
        area3.getRegisteredMails().add(letter11);
        area3.getRegisteredMails().add(letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = mailDeliverySystem.getMailForInhabitant(ella);
        
        // Expected Output: Letter12 only
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 mail item", 1, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertFalse("Should not contain letter11", result.contains(letter11));
    }
    
    @Test
    public void testCase5_listMailForInhabitantWithMixedMailTypes() {
        // Create and assign Letter12 for Luke
        Letter letter12 = new Letter();
        letter12.setContent("Letter12 content");
        letter12.setAddressee(luke);
        letter12.setArea(area4);
        letter12.setAssignedMailman(ruby);
        
        // Create and assign Parcel7 for Luke
        Parcel parcel7 = new Parcel();
        parcel7.setWeight(7.0);
        parcel7.setAddressee(luke);
        parcel7.setArea(area4);
        parcel7.setAssignedMailman(ruby);
        
        // Create and assign Letter13 for Luke
        Letter letter13 = new Letter();
        letter13.setContent("Letter13 content");
        letter13.setAddressee(luke);
        letter13.setArea(area4);
        letter13.setAssignedMailman(ruby);
        
        // Add mail to area
        area4.getRegisteredMails().add(letter12);
        area4.getRegisteredMails().add(parcel7);
        area4.getRegisteredMails().add(letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = mailDeliverySystem.getMailForInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}