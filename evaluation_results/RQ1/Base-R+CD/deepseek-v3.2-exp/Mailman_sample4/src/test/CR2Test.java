import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private GeographicalArea geographicalArea;
    private Mailman mailmanAaron;
    private Mailman mailmanMia;
    private Mailman mailmanPeter;
    private Mailman mailmanMaria;
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
        // Initialize common objects for tests
        geographicalArea = new GeographicalArea();
        
        mailmanAaron = new Mailman();
        mailmanAaron.setId("A1");
        mailmanAaron.setName("Aaron");
        
        mailmanMia = new Mailman();
        mailmanMia.setId("M1");
        mailmanMia.setName("Mia");
        
        mailmanPeter = new Mailman();
        mailmanPeter.setId("P1");
        mailmanPeter.setName("Peter");
        
        mailmanMaria = new Mailman();
        mailmanMaria.setId("M2");
        mailmanMaria.setName("Maria");
        
        inhabitantZoe = new Inhabitant();
        inhabitantZoe.setId("Z1");
        inhabitantZoe.setName("Zoe");
        
        inhabitantPeter = new Inhabitant();
        inhabitantPeter.setId("P2");
        inhabitantPeter.setName("Peter");
        
        inhabitantElla = new Inhabitant();
        inhabitantElla.setId("E1");
        inhabitantElla.setName("Ella");
        
        inhabitantJucy = new Inhabitant();
        inhabitantJucy.setId("J1");
        inhabitantJucy.setName("Jucy");
        
        letter10 = new Letter();
        letter10.setId("Letter10");
        
        parcel6 = new Parcel();
        parcel6.setId("Parcel6");
        
        parcel7 = new Parcel();
        parcel7.setId("Parcel7");
        
        letter11 = new Letter();
        letter11.setId("Letter11");
        
        letter12 = new Letter();
        letter12.setId("Letter12");
        
        parcel12 = new Parcel();
        parcel12.setId("Parcel12");
        
        parcel14 = new Parcel();
        parcel14.setId("Parcel14");
        
        parcel15 = new Parcel();
        parcel15.setId("Parcel15");
    }

    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // Test Case 1: List Multiple Mail Items for Mailman
        // Setup
        GeographicalArea mainStreet = new GeographicalArea();
        mainStreet.addMailman(mailmanAaron);
        mainStreet.addInhabitant(inhabitantZoe);
        mainStreet.addInhabitant(inhabitantPeter);
        
        letter10.setCarrier(mailmanAaron);
        letter10.setAddressee(inhabitantZoe);
        mainStreet.getAllMails().add(letter10);
        
        parcel6.setCarrier(mailmanAaron);
        parcel6.setAddressee(inhabitantZoe);
        mainStreet.getAllMails().add(parcel6);
        
        parcel7.setCarrier(mailmanAaron);
        parcel7.setAddressee(inhabitantPeter);
        mainStreet.getAllMails().add(parcel7);
        
        // Action: List all mail for Aaron
        List<RegisteredMail> result = mainStreet.listRegisteredMail(mailmanAaron);
        
        // Expected Output: List containing Letter10, Parcel6, and Parcel7
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter10));
        assertTrue(result.contains(parcel6));
        assertTrue(result.contains(parcel7));
    }

    @Test
    public void testCase2_ListMailForMailmanWithNoMail() {
        // Test Case 2: List Mail for Mailman with No Mail
        // Setup
        GeographicalArea marketSquare = new GeographicalArea();
        marketSquare.addMailman(mailmanMia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMail(mailmanMia);
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }

    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // Test Case 3: List Mail for Non-existent Mailman
        // Setup
        GeographicalArea area = new GeographicalArea();
        Mailman nonExistentMailman = new Mailman();
        nonExistentMailman.setId("N1");
        nonExistentMailman.setName("Noah");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMail(nonExistentMailman);
        
        // Expected Output: null
        assertNull(result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // Test Case 4: List Only Assigned Mail for Mailman
        // Setup
        GeographicalArea oldTown = new GeographicalArea();
        oldTown.addInhabitant(inhabitantElla);
        oldTown.addInhabitant(inhabitantJucy);
        oldTown.addMailman(mailmanPeter);
        oldTown.addMailman(mailmanMaria);
        
        letter11.setCarrier(mailmanPeter);
        letter11.setAddressee(inhabitantJucy);
        oldTown.getAllMails().add(letter11);
        
        letter12.setCarrier(mailmanPeter);
        letter12.setAddressee(inhabitantJucy);
        oldTown.getAllMails().add(letter12);
        
        parcel12.setCarrier(mailmanPeter);
        parcel12.setAddressee(inhabitantElla);
        oldTown.getAllMails().add(parcel12);
        
        parcel14.setCarrier(mailmanPeter);
        parcel14.setAddressee(inhabitantElla);
        oldTown.getAllMails().add(parcel14);
        
        parcel15.setCarrier(mailmanMaria);
        parcel15.setAddressee(inhabitantElla);
        oldTown.getAllMails().add(parcel15);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(mailmanPeter);
        
        // Expected Output: List containing Letter11, Letter12, Parcel12, and Parcel14
        assertNotNull(result);
        assertEquals(4, result.size());
        assertTrue(result.contains(letter11));
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel12));
        assertTrue(result.contains(parcel14));
        assertFalse(result.contains(parcel15)); // Should not contain Maria's mail
    }

    @Test
    public void testCase5_ListMailForMailmanWithMixedMailTypes() {
        // Test Case 5: List Mail for Mailman with Mixed Mail Types
        // Setup
        GeographicalArea oldTown = new GeographicalArea();
        oldTown.addInhabitant(inhabitantElla);
        oldTown.addInhabitant(inhabitantJucy);
        oldTown.addMailman(mailmanPeter);
        oldTown.addMailman(mailmanMaria);
        
        letter12.setCarrier(mailmanPeter);
        letter12.setAddressee(inhabitantJucy);
        oldTown.getAllMails().add(letter12);
        
        parcel12.setCarrier(mailmanPeter);
        parcel12.setAddressee(inhabitantElla);
        oldTown.getAllMails().add(parcel12);
        
        parcel14.setCarrier(mailmanPeter);
        parcel14.setAddressee(inhabitantElla);
        oldTown.getAllMails().add(parcel14);
        
        parcel15.setCarrier(mailmanMaria);
        parcel15.setAddressee(inhabitantJucy);
        oldTown.getAllMails().add(parcel15);
        
        // Remove Ella and Maria from OldTown, assign Maria's mails to Peter
        oldTown.removeInhabitant(inhabitantElla);
        oldTown.removeMailman(mailmanMaria, mailmanPeter);
        
        // Action: List all mail for Peter
        List<RegisteredMail> result = oldTown.listRegisteredMail(mailmanPeter);
        
        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel15)); // Maria's mail should now be assigned to Peter
    }
}