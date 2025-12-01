import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private GeographicalArea geographicalArea;
    private Mailman mailmanAaron;
    private Mailman mailmanMia;
    private Mailman mailmanPeter;
    private Mailman mailmanMaria;
    private Mailman mailmanNoah;
    private Inhabitant inhabitantZoe;
    private Inhabitant inhabitantPeter;
    private Inhabitant inhabitantElla;
    private Inhabitant inhabitantJucy;
    private Letter letter10;
    private Parcel parcel6;
    private Parcel parcel7;
    private Letter letter11;
    private Letter letter12;
    private Parcel parcel12;
    private Parcel parcel14;
    private Parcel parcel15;

    @Before
    public void setUp() {
        // Initialize common test objects
        geographicalArea = new GeographicalArea();
        mailmanAaron = new Mailman();
        mailmanMia = new Mailman();
        mailmanPeter = new Mailman();
        mailmanMaria = new Mailman();
        mailmanNoah = new Mailman();
        inhabitantZoe = new Inhabitant();
        inhabitantPeter = new Inhabitant();
        inhabitantElla = new Inhabitant();
        inhabitantJucy = new Inhabitant();
        
        // Set names and IDs
        mailmanAaron.setName("Aaron");
        mailmanAaron.setId("MA001");
        mailmanMia.setName("Mia");
        mailmanMia.setId("MA002");
        mailmanPeter.setName("Peter");
        mailmanPeter.setId("MA003");
        mailmanMaria.setName("Maria");
        mailmanMaria.setId("MA004");
        mailmanNoah.setName("Noah");
        mailmanNoah.setId("MA005");
        
        inhabitantZoe.setName("Zoe");
        inhabitantZoe.setId("IH001");
        inhabitantPeter.setName("Peter");
        inhabitantPeter.setId("IH002");
        inhabitantElla.setName("Ella");
        inhabitantElla.setId("IH003");
        inhabitantJucy.setName("Jucy");
        inhabitantJucy.setId("IH004");
        
        // Initialize mail items
        letter10 = new Letter();
        letter10.setTrackingNumber("LETTER10");
        parcel6 = new Parcel();
        parcel6.setTrackingNumber("PARCEL6");
        parcel7 = new Parcel();
        parcel7.setTrackingNumber("PARCEL7");
        letter11 = new Letter();
        letter11.setTrackingNumber("LETTER11");
        letter12 = new Letter();
        letter12.setTrackingNumber("LETTER12");
        parcel12 = new Parcel();
        parcel12.setTrackingNumber("PARCEL12");
        parcel14 = new Parcel();
        parcel14.setTrackingNumber("PARCEL14");
        parcel15 = new Parcel();
        parcel15.setTrackingNumber("PARCEL15");
    }

    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // SetUp: Create GeographicalArea "MainStreet"
        GeographicalArea mainStreet = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        mainStreet.addMailman(mailmanAaron);
        
        // Add Inhabitants "Zoe", "Peter" to MainStreet
        mainStreet.addInhabitant(inhabitantZoe);
        mainStreet.addInhabitant(inhabitantPeter);
        
        // Create and assign mail items
        // Letter10 for Zoe (Aaron)
        letter10.setCarrier(mailmanAaron);
        letter10.setAddressee(inhabitantZoe);
        mainStreet.getAllMails().add(letter10);
        
        // Parcel6 for Zoe (Aaron)
        parcel6.setCarrier(mailmanAaron);
        parcel6.setAddressee(inhabitantZoe);
        mainStreet.getAllMails().add(parcel6);
        
        // Parcel7 for Peter (Aaron)
        parcel7.setCarrier(mailmanAaron);
        parcel7.setAddressee(inhabitantPeter);
        mainStreet.getAllMails().add(parcel7);
        
        // Action: List all mail for Aaron
        List<RegisteredMail> result = mainStreet.listRegisteredMail(mailmanAaron);
        
        // Expected Output: List containing Letter10, Parcel6, and Parcel7
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
        assertTrue("Should contain Parcel7", result.contains(parcel7));
    }

    @Test
    public void testCase2_ListMailForMailmanWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        GeographicalArea marketSquare = new GeographicalArea();
        
        // Add Mailman "Mia" to MarketSquare
        marketSquare.addMailman(mailmanMia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMail(mailmanMia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }

    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // SetUp: Create GeographicalArea (no setup needed beyond default)
        GeographicalArea area = new GeographicalArea();
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMail(mailmanNoah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent mailman", result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // Add Inhabitants "Ella", "Jucy" to OldTown
        oldTown.addInhabitant(inhabitantElla);
        oldTown.addInhabitant(inhabitantJucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(mailmanPeter);
        oldTown.addMailman(mailmanMaria);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        letter11.setCarrier(mailmanPeter);
        letter11.setAddressee(inhabitantElla);
        oldTown.getAllMails().add(letter11);
        
        // Create and assign other mail items
        // Letter12 for Jucy (Peter)
        letter12.setCarrier(mailmanPeter);
        letter12.setAddressee(inhabitantJucy);
        oldTown.getAllMails().add(letter12);
        
        // Parcel12 for Ella (Peter)
        parcel12.setCarrier(mailmanPeter);
        parcel12.setAddressee(inhabitantElla);
        oldTown.getAllMails().add(parcel12);
        
        // Parcel14 for Ella (Peter)
        parcel14.setCarrier(mailmanPeter);
        parcel14.setAddressee(inhabitantElla);
        oldTown.getAllMails().add(parcel14);
        
        // Parcel15 for Ella (Maria)
        parcel15.setCarrier(mailmanMaria);
        parcel15.setAddressee(inhabitantElla);
        oldTown.getAllMails().add(parcel15);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(mailmanPeter);
        
        // Expected Output: List containing Letter11, Letter12, Parcel12, and Parcel14
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 4 mail items", 4, result.size());
        assertTrue("Should contain Letter11", result.contains(letter11));
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel12", result.contains(parcel12));
        assertTrue("Should contain Parcel14", result.contains(parcel14));
        assertFalse("Should not contain Parcel15 (assigned to Maria)", result.contains(parcel15));
    }

    @Test
    public void testCase5_ListMailForMailmanWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // Add Inhabitants "Ella", "Jucy" to OldTown
        oldTown.addInhabitant(inhabitantElla);
        oldTown.addInhabitant(inhabitantJucy);
        
        // Add Mailman "Peter", "Maria" to OldTown
        oldTown.addMailman(mailmanPeter);
        oldTown.addMailman(mailmanMaria);
        
        // Create and assign mail items
        // Letter12 for Jucy (Peter)
        letter12.setCarrier(mailmanPeter);
        letter12.setAddressee(inhabitantJucy);
        oldTown.getAllMails().add(letter12);
        
        // Parcel12 for Ella (Peter)
        parcel12.setCarrier(mailmanPeter);
        parcel12.setAddressee(inhabitantElla);
        oldTown.getAllMails().add(parcel12);
        
        // Parcel14 for Ella (Peter)
        parcel14.setCarrier(mailmanPeter);
        parcel14.setAddressee(inhabitantElla);
        oldTown.getAllMails().add(parcel14);
        
        // Parcel15 for Jucy (Maria)
        parcel15.setCarrier(mailmanMaria);
        parcel15.setAddressee(inhabitantJucy);
        oldTown.getAllMails().add(parcel15);
        
        // Remove Ella, Maria from OldTown. Assign Maria's mails to Peter.
        oldTown.removeInhabitant(inhabitantElla);
        oldTown.removeMailman(mailmanMaria, mailmanPeter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(mailmanPeter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel15", result.contains(parcel15));
        assertFalse("Should not contain Parcel12 (Ella removed)", result.contains(parcel12));
        assertFalse("Should not contain Parcel14 (Ella removed)", result.contains(parcel14));
    }
}