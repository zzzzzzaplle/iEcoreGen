import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    private GeographicalArea centralDistrict;
    private GeographicalArea northQuarter;
    private GeographicalArea southEnd;
    private GeographicalArea eastHaven;
    private GeographicalArea westRidge;
    
    private Mailman alice;
    private Mailman bob;
    private Mailman charlie;
    private Mailman xavier;
    private Mailman yvonne;
    private Mailman zack;
    private Mailman paul;
    private Mailman quinn;
    private Mailman mario;
    private Mailman luigi;
    private Mailman toad;
    private Mailman alpha;
    private Mailman beta;
    private Mailman gamma;
    
    private Inhabitant david;
    private Inhabitant eve;
    private Inhabitant frank;
    private Inhabitant walter;
    private Inhabitant rachel;
    private Inhabitant peach;
    private Inhabitant bowser;
    
    @Before
    public void setUp() {
        // Initialize all mailmen
        alice = new Mailman();
        bob = new Mailman();
        charlie = new Mailman();
        xavier = new Mailman();
        yvonne = new Mailman();
        zack = new Mailman();
        paul = new Mailman();
        quinn = new Mailman();
        mario = new Mailman();
        luigi = new Mailman();
        toad = new Mailman();
        alpha = new Mailman();
        beta = new Mailman();
        gamma = new Mailman();
        
        // Initialize all inhabitants
        david = new Inhabitant();
        eve = new Inhabitant();
        frank = new Inhabitant();
        walter = new Inhabitant();
        rachel = new Inhabitant();
        peach = new Inhabitant();
        bowser = new Inhabitant();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // Set up CentralDistrict geographical area
        centralDistrict = new GeographicalArea();
        
        // Add mailmen
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add inhabitants
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create mail items
        Letter letter1 = new Letter();
        Parcel parcel1 = new Parcel();
        Letter letter2 = new Letter();
        Parcel parcel2 = new Parcel();
        Letter letter3 = new Letter();
        
        // Add mail items to the geographical area
        centralDistrict.getAllMails().add(letter1);
        centralDistrict.getAllMails().add(parcel1);
        centralDistrict.getAllMails().add(letter2);
        centralDistrict.getAllMails().add(parcel2);
        centralDistrict.getAllMails().add(letter3);
        
        // Assign mail items
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Verify initial assignments
        List<RegisteredMail> initialAliceMails = centralDistrict.listRegisteredMail(alice);
        List<RegisteredMail> initialBobMails = centralDistrict.listRegisteredMail(bob);
        List<RegisteredMail> initialCharlieMails = centralDistrict.listRegisteredMail(charlie);
        
        assertEquals(2, initialAliceMails.size());
        assertEquals(2, initialBobMails.size());
        assertEquals(1, initialCharlieMails.size());
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications: Alice removed
        assertFalse(centralDistrict.getMailmen().contains(alice));
        
        // Verifications: Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> finalBobMails = centralDistrict.listRegisteredMail(bob);
        assertNotNull(finalBobMails);
        assertEquals(4, finalBobMails.size());
        
        // Verifications: Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue(finalBobMails.contains(parcel1));
        assertTrue(finalBobMails.contains(letter3));
        
        // Verifications: Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> finalCharlieMails = centralDistrict.listRegisteredMail(charlie);
        assertNotNull(finalCharlieMails);
        assertEquals(1, finalCharlieMails.size());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Set up NorthQuarter geographical area
        northQuarter = new GeographicalArea();
        
        // Add mailmen
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add inhabitant
        northQuarter.addInhabitant(walter);
        
        // Create and assign 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            northQuarter.getAllMails().add(letter);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            northQuarter.getAllMails().add(parcel);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            northQuarter.getAllMails().add(letter);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Verify initial counts
        assertEquals(3, northQuarter.getMailmen().size());
        assertEquals(10, northQuarter.getAllMails().size());
        
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
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Set up SouthEnd geographical area
        southEnd = new GeographicalArea();
        
        // Add mailmen
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add inhabitant
        southEnd.addInhabitant(rachel);
        
        // Create and assign Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        southEnd.getAllMails().add(letter1);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Action 1: Attempt remove Paul specifying Quinn
        boolean result1 = southEnd.removeMailman(paul, quinn);
        
        // Expected Output: true (normal case)
        assertTrue(result1);
        
        // Action 2: Attempt remove Quinn (last mailman)
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is already removed, but this should fail anyway
        
        // Expected Output: false
        assertFalse(result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Set up EastHaven geographical area
        eastHaven = new GeographicalArea();
        
        // Add mailmen
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add inhabitants
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            eastHaven.getAllMails().add(letter);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            eastHaven.getAllMails().add(parcel);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
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
        
        // Procedure 4: Attempt remove Luigi (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // mario is already removed
        
        // Expected Output: false
        assertFalse(result3);
        
        // Procedure 5: Add "Toad"
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
        // Set up WestRidge geographical area
        westRidge = new GeographicalArea();
        
        // Add mailmen
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitants.add(inhabitant);
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.getAllMails().add(letter);
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(i % 10), letter);
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            westRidge.getAllMails().add(parcel);
            westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(i % 10), parcel);
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.getAllMails().add(letter);
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(i % 10), letter);
        }
        
        // Verify initial counts
        assertEquals(3, westRidge.getMailmen().size());
        assertEquals(30, westRidge.getAllMails().size());
        
        // Action 1: Remove Alpha (specify Beta)
        boolean result1 = westRidge.removeMailman(alpha, beta);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Action 2: Remove Beta (specify Gamma)
        boolean result2 = westRidge.removeMailman(beta, gamma);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaMails = westRidge.listRegisteredMail(gamma);
        assertNotNull(gammaMails);
        assertEquals(30, gammaMails.size());
    }
}