import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR5Test {
    private MailDeliverySystem system;
    private Area area1;
    private Area area2;
    private Area area3;
    private Area area4;
    private Area area5;
    private Mailman mailman1;
    private Mailman mailman2;
    private Mailman mailman3;
    private Inhabitant inhabitant1;
    private Inhabitant inhabitant2;
    private Inhabitant inhabitant3;
    private Inhabitant inhabitant4;
    private Inhabitant inhabitant5;
    private Inhabitant nonExistentInhabitant;

    @Before
    public void setUp() {
        system = new MailDeliverySystem();
        
        // Set up areas
        area1 = new Area();
        area1.setName("MainStreet");
        
        area2 = new Area();
        area2.setName("MarketSquare");
        
        area3 = new Area();
        area3.setName("OldTown");
        
        area4 = new Area();
        area4.setName("NewDistrict");
        
        area5 = new Area();
        area5.setName("TestArea");
        
        // Set up mailmen
        mailman1 = new Mailman();
        mailman1.setName("Aaron");
        
        mailman2 = new Mailman();
        mailman2.setName("Peter");
        
        mailman3 = new Mailman();
        mailman3.setName("Ruby");
        
        // Set up inhabitants
        inhabitant1 = new Inhabitant();
        inhabitant1.setName("Zoe");
        inhabitant1.setArea(area1);
        
        inhabitant2 = new Inhabitant();
        inhabitant2.setName("Mia");
        inhabitant2.setArea(area2);
        
        inhabitant3 = new Inhabitant();
        inhabitant3.setName("Ella");
        inhabitant3.setArea(area3);
        
        inhabitant4 = new Inhabitant();
        inhabitant4.setName("Luke");
        inhabitant4.setArea(area4);
        
        inhabitant5 = new Inhabitant();
        inhabitant5.setName("Noah");
        inhabitant5.setArea(area5);
        
        nonExistentInhabitant = new Inhabitant();
        nonExistentInhabitant.setName("NonExistent");
        
        // Add areas to system
        Set<Area> systemAreas = new HashSet<>();
        systemAreas.add(area1);
        systemAreas.add(area2);
        systemAreas.add(area3);
        systemAreas.add(area4);
        systemAreas.add(area5);
        system.setAreas(systemAreas);
    }

    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        area1.setName("MainStreet");
        
        // SetUp: Add Mailman "Aaron" to MainStreet
        area1.addMailman(mailman1);
        
        // SetUp: Add Inhabitant "Zoe" to MainStreet
        area1.addInhabitant(inhabitant1);
        
        // SetUp: Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        letter10.setContent("Letter10 content");
        letter10.setAddressee(inhabitant1);
        letter10.setMailman(mailman1);
        area1.getMails().add(letter10);
        
        // SetUp: Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        parcel6.setWeight(6.0);
        parcel6.setAddressee(inhabitant1);
        parcel6.setMailman(mailman1);
        area1.getMails().add(parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = system.getMailForInhabitant(inhabitant1);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 2 mail items", 2, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
    }

    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        area2.setName("MarketSquare");
        
        // SetUp: Add Inhabitant "Mia" to MarketSquare
        area2.addInhabitant(inhabitant2);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = system.getMailForInhabitant(inhabitant2);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }

    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        // Note: Noah exists in area5 but we'll use a completely non-existent inhabitant
        List<RegisteredMail> result = system.getMailForInhabitant(nonExistentInhabitant);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        area3.setName("OldTown");
        
        // SetUp: Add Inhabitant "Ella" to OldTown
        area3.addInhabitant(inhabitant3);
        
        // SetUp: Create Letter11 assigned to mailman "Peter" in "OldTown"
        area3.addMailman(mailman2);
        Letter letter11 = new Letter();
        letter11.setContent("Letter11 content");
        letter11.setAddressee(inhabitant3);
        letter11.setMailman(mailman2);
        area3.getMails().add(letter11);
        
        // SetUp: Create and assign Letter12 for Ella
        Letter letter12 = new Letter();
        letter12.setContent("Letter12 content");
        letter12.setAddressee(inhabitant3);
        letter12.setMailman(mailman2);
        area3.getMails().add(letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = system.getMailForInhabitant(inhabitant3);
        
        // Expected Output: Should contain both Letter11 and Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 2 mail items", 2, result.size());
        assertTrue("Should contain Letter11", result.contains(letter11));
        assertTrue("Should contain Letter12", result.contains(letter12));
    }

    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        area4.setName("NewDistrict");
        
        // SetUp: Add Mailman "Ruby" to NewDistrict
        area4.addMailman(mailman3);
        
        // SetUp: Add Inhabitant "Luke" to NewDistrict
        area4.addInhabitant(inhabitant4);
        
        // SetUp: Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        letter12.setContent("Letter12 content");
        letter12.setAddressee(inhabitant4);
        letter12.setMailman(mailman3);
        area4.getMails().add(letter12);
        
        // SetUp: Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        parcel7.setWeight(7.0);
        parcel7.setAddressee(inhabitant4);
        parcel7.setMailman(mailman3);
        area4.getMails().add(parcel7);
        
        // SetUp: Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        letter13.setContent("Letter13 content");
        letter13.setAddressee(inhabitant4);
        letter13.setMailman(mailman3);
        area4.getMails().add(letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = system.getMailForInhabitant(inhabitant4);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 3 mail items", 3, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel7", result.contains(parcel7));
        assertTrue("Should contain Letter13", result.contains(letter13));
    }
}