import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    private GeographicalArea area;
    private Mailman aaron;
    private Mailman mia;
    private Mailman peter;
    private Mailman maria;
    private Inhabitant zoe;
    private Inhabitant peterInhabitant;
    private Inhabitant ella;
    private Inhabitant jucy;
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
        area = new GeographicalArea();
        aaron = new Mailman();
        mia = new Mailman();
        peter = new Mailman();
        maria = new Mailman();
        zoe = new Inhabitant();
        peterInhabitant = new Inhabitant();
        ella = new Inhabitant();
        jucy = new Inhabitant();
        letter10 = new Letter();
        parcel6 = new Parcel();
        parcel7 = new Parcel();
        letter11 = new Letter();
        letter12 = new Letter();
        parcel12 = new Parcel();
        parcel14 = new Parcel();
        parcel15 = new Parcel();
    }

    @Test
    public void testCase1_ListMultipleMailItemsForMailman() {
        // Set up MainStreet area
        area.addMailman(aaron);
        area.addInhabitant(zoe);
        area.addInhabitant(peterInhabitant);

        // Create and assign mail items
        letter10.setAddressee(zoe);
        letter10.setCarrier(aaron);
        parcel6.setAddressee(zoe);
        parcel6.setCarrier(aaron);
        parcel7.setAddressee(peterInhabitant);
        parcel7.setCarrier(aaron);

        // Add mail items to area
        List<RegisteredMail> mails = new ArrayList<>();
        mails.add(letter10);
        mails.add(parcel6);
        mails.add(parcel7);
        area.setAllMails(mails);

        // Action: List all mail for Aaron
        List<RegisteredMail> result = area.listRegisteredMail(aaron);

        // Expected Output: List containing Letter10, Parcel6, and Parcel7
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter10));
        assertTrue(result.contains(parcel6));
        assertTrue(result.contains(parcel7));
    }

    @Test
    public void testCase2_ListMailForMailmanWithNoMail() {
        // Set up MarketSquare area
        area.addMailman(mia);

        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMail(mia);

        // Expected Output: null (no mail items)
        assertNull(result);
    }

    @Test
    public void testCase3_ListMailForNonExistentMailman() {
        // Action: List all mail for non-existent "Noah"
        Mailman noah = new Mailman();
        List<RegisteredMail> result = area.listRegisteredMail(noah);

        // Expected Output: null
        assertNull(result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForMailman() {
        // Set up OldTown area
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        area.addMailman(peter);
        area.addMailman(maria);

        // Create and assign mail items
        letter11.setAddressee(ella); // Assuming Ella as addressee per specification
        letter11.setCarrier(peter);
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        parcel15.setAddressee(ella);
        parcel15.setCarrier(maria);

        // Add mail items to area
        List<RegisteredMail> mails = new ArrayList<>();
        mails.add(letter11);
        mails.add(letter12);
        mails.add(parcel12);
        mails.add(parcel14);
        mails.add(parcel15);
        area.setAllMails(mails);

        // Action: List all mail for Peter
        List<RegisteredMail> result = area.listRegisteredMail(peter);

        // Expected Output: List containing Letter11, Letter12, Parcel12, and Parcel14
        assertNotNull(result);
        assertEquals(4, result.size());
        assertTrue(result.contains(letter11));
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel12));
        assertTrue(result.contains(parcel14));
    }

    @Test
    public void testCase5_ListMailForMailmanWithMixedMailTypes() {
        // Set up OldTown area
        area.addInhabitant(ella);
        area.addInhabitant(jucy);
        area.addMailman(peter);
        area.addMailman(maria);

        // Create and assign mail items
        letter12.setAddressee(jucy);
        letter12.setCarrier(peter);
        parcel12.setAddressee(ella);
        parcel12.setCarrier(peter);
        parcel14.setAddressee(ella);
        parcel14.setCarrier(peter);
        parcel15.setAddressee(jucy);
        parcel15.setCarrier(maria);

        // Add mail items to area
        List<RegisteredMail> mails = new ArrayList<>();
        mails.add(letter12);
        mails.add(parcel12);
        mails.add(parcel14);
        mails.add(parcel15);
        area.setAllMails(mails);

        // Remove Ella and Maria from OldTown and reassign Maria's mail to Peter
        area.removeInhabitant(ella);
        area.removeMailman(maria, peter);

        // Action: List all mail for Peter
        List<RegisteredMail> result = area.listRegisteredMail(peter);

        // Expected Output: List containing Letter12 and Parcel15
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel15));
    }
}