import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    private GeographicalArea geographicalArea;
    private Mailman mailman1;
    private Mailman mailman2;
    private Mailman mailman3;
    private Inhabitant inhabitant1;
    private Inhabitant inhabitant2;
    private Inhabitant inhabitant3;
    private Letter letter1;
    private Letter letter2;
    private Letter letter3;
    private Parcel parcel1;
    private Parcel parcel2;

    @Before
    public void setUp() {
        geographicalArea = new GeographicalArea();
        mailman1 = new Mailman();
        mailman2 = new Mailman();
        mailman3 = new Mailman();
        inhabitant1 = new Inhabitant();
        inhabitant2 = new Inhabitant();
        inhabitant3 = new Inhabitant();
        letter1 = new Letter();
        letter2 = new Letter();
        letter3 = new Letter();
        parcel1 = new Parcel();
        parcel2 = new Parcel();
    }

    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        GeographicalArea centralDistrict = new GeographicalArea();
        
        // Add Mailmen "Alice", "Bob", "Charlie"
        Mailman alice = new Mailman();
        Mailman bob = new Mailman();
        Mailman charlie = new Mailman();
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add Inhabitants "David", "Eve", "Frank"
        Inhabitant david = new Inhabitant();
        Inhabitant eve = new Inhabitant();
        Inhabitant frank = new Inhabitant();
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create and assign mail items
        Letter letter1 = new Letter();
        letter1.setAddressee(david);
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(eve);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        
        Letter letter2 = new Letter();
        letter2.setAddressee(frank);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(eve);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        
        Letter letter3 = new Letter();
        letter3.setAddressee(david);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications:
        // Alice removed
        assertFalse(centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobMails = centralDistrict.listRegisteredMail(bob);
        assertNotNull(bobMails);
        assertEquals(4, bobMails.size());
        assertTrue(bobMails.contains(letter1));
        assertTrue(bobMails.contains(parcel2));
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue(bobMails.contains(parcel1));
        assertTrue(bobMails.contains(letter3));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieMails = centralDistrict.listRegisteredMail(charlie);
        assertNotNull(charlieMails);
        assertEquals(1, charlieMails.size());
        assertTrue(charlieMails.contains(letter2));
    }

    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        GeographicalArea northQuarter = new GeographicalArea();
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        Mailman xavier = new Mailman();
        Mailman yvonne = new Mailman();
        Mailman zack = new Mailman();
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add Inhabitant "Walter"
        Inhabitant walter = new Inhabitant();
        northQuarter.addInhabitant(walter);
        
        // Create and assign mail items
        Letter[] xavierLetters = new Letter[5];
        for (int i = 0; i < 5; i++) {
            xavierLetters[i] = new Letter();
            xavierLetters[i].setAddressee(walter);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, xavierLetters[i]);
        }
        
        Parcel[] yvonneParcels = new Parcel[3];
        for (int i = 0; i < 3; i++) {
            yvonneParcels[i] = new Parcel();
            yvonneParcels[i].setAddressee(walter);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, yvonneParcels[i]);
        }
        
        Letter[] zackLetters = new Letter[2];
        for (int i = 0; i < 2; i++) {
            zackLetters[i] = new Letter();
            zackLetters[i].setAddressee(walter);
            northQuarter.assignRegisteredMailDeliver(zack, walter, zackLetters[i]);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zackMails = northQuarter.listRegisteredMail(zack);
        assertNotNull(zackMails);
        assertEquals(10, zackMails.size());
        
        // Verify all mail items are assigned to Zack
        for (Letter letter : xavierLetters) {
            assertTrue(zackMails.contains(letter));
        }
        for (Parcel parcel : yvonneParcels) {
            assertTrue(zackMails.contains(parcel));
        }
        for (Letter letter : zackLetters) {
            assertTrue(zackMails.contains(letter));
        }
    }

    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        GeographicalArea southEnd = new GeographicalArea();
        
        // Add Mailmen ["Paul", "Quinn"]
        Mailman paul = new Mailman();
        Mailman quinn = new Mailman();
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add Inhabitant "Rachel"
        Inhabitant rachel = new Inhabitant();
        southEnd.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Action 1: Attempt remove Paul specifying Quinn
        boolean result1 = southEnd.removeMailman(paul, quinn);
        
        // Expected Output: true (normal case)
        assertTrue(result1);
        
        // Action 2: Attempt remove Quinn (last mailman)
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is already removed, but method should fail due to size constraint
        
        // Expected Output: false
        assertFalse(result2);
    }

    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        GeographicalArea eastHaven = new GeographicalArea();
        
        // Add Mailmen ["Mario", "Luigi"]
        Mailman mario = new Mailman();
        Mailman luigi = new Mailman();
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add Inhabitants ["Peach", "Bowser"]
        Inhabitant peach = new Inhabitant();
        Inhabitant bowser = new Inhabitant();
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        Letter[] marioLetters = new Letter[10];
        for (int i = 0; i < 10; i++) {
            marioLetters[i] = new Letter();
            marioLetters[i].setAddressee(peach);
            eastHaven.assignRegisteredMailDeliver(mario, peach, marioLetters[i]);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        Parcel[] luigiParcels = new Parcel[5];
        for (int i = 0; i < 5; i++) {
            luigiParcels[i] = new Parcel();
            luigiParcels[i].setAddressee(bowser);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, luigiParcels[i]);
        }
        
        // Procedure 1: Add duplicate "Mario"
        boolean result1 = eastHaven.addMailman(mario);
        
        // Expected Output: false
        assertFalse(result1);
        
        // Procedure 2: Remove Mario (specify Luigi)
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Procedure 3: Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMails = eastHaven.listRegisteredMail(luigi);
        assertNotNull(luigiMails);
        assertEquals(15, luigiMails.size());
        
        // Procedure 4: Attempt remove Luigi → false (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // mario is already removed, but method should fail due to size constraint
        
        // Expected Output: false
        assertFalse(result3);
        
        // Procedure 5: Add "Toad"
        Mailman toad = new Mailman();
        boolean result4 = eastHaven.addMailman(toad);
        
        // Expected Output: true
        assertTrue(result4);
        
        // Procedure 6: Remove Luigi (specify Toad)
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        
        // Expected Output: true
        assertTrue(result5);
    }

    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        GeographicalArea westRidge = new GeographicalArea();
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        Mailman alpha = new Mailman();
        Mailman beta = new Mailman();
        Mailman gamma = new Mailman();
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant();
            westRidge.addInhabitant(inhabitants[i]);
        }
        
        // Create 30 mail items (10 each mailman)
        RegisteredMail[] alphaMails = new RegisteredMail[10];
        RegisteredMail[] betaMails = new RegisteredMail[10];
        RegisteredMail[] gammaMails = new RegisteredMail[10];
        
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                alphaMails[i] = new Letter();
                betaMails[i] = new Letter();
                gammaMails[i] = new Letter();
            } else {
                alphaMails[i] = new Parcel();
                betaMails[i] = new Parcel();
                gammaMails[i] = new Parcel();
            }
            
            alphaMails[i].setAddressee(inhabitants[i]);
            betaMails[i].setAddressee(inhabitants[i]);
            gammaMails[i].setAddressee(inhabitants[i]);
            
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants[i], alphaMails[i]);
            westRidge.assignRegisteredMailDeliver(beta, inhabitants[i], betaMails[i]);
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants[i], gammaMails[i]);
        }
        
        // Action 1: Remove Alpha (specify Beta)
        boolean result1 = westRidge.removeMailman(alpha, beta);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Action 2: Remove Beta (specify Gamma)
        boolean result2 = westRidge.removeMailman(beta, gamma);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaMailsList = westRidge.listRegisteredMail(gamma);
        assertNotNull(gammaMailsList);
        assertEquals(30, gammaMailsList.size());
        
        // Verify all mail items are assigned to Gamma
        for (int i = 0; i < 10; i++) {
            assertTrue(gammaMailsList.contains(alphaMails[i]));
            assertTrue(gammaMailsList.contains(betaMails[i]));
            assertTrue(gammaMailsList.contains(gammaMails[i]));
        }
    }
}