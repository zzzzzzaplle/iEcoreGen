import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

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
        // Initialize geographical areas
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
        
        // Initialize mailmen
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
        
        // Initialize inhabitants
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
        // Set up CentralDistrict with mailmen and inhabitants
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create mail items
        Letter letter1 = new Letter();
        Parcel parcel1 = new Parcel();
        Letter letter2 = new Letter();
        Parcel parcel2 = new Parcel();
        Letter letter3 = new Letter();
        
        // Add mail items to geographical area
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
        List<RegisteredMail> aliceMails = centralDistrict.listRegisteredMail(alice);
        List<RegisteredMail> bobMails = centralDistrict.listRegisteredMail(bob);
        List<RegisteredMail> charlieMails = centralDistrict.listRegisteredMail(charlie);
        
        assertEquals(2, aliceMails.size());
        assertEquals(2, bobMails.size());
        assertEquals(1, charlieMails.size());
        
        // Remove Alice with Bob as replacement
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Verify operation result
        assertTrue(result);
        
        // Verify Alice is removed
        assertFalse(centralDistrict.getMailmen().contains(alice));
        
        // Verify Bob now has all of Alice's mail plus his original mail
        List<RegisteredMail> updatedBobMails = centralDistrict.listRegisteredMail(bob);
        assertEquals(4, updatedBobMails.size());
        
        // Verify Charlie's assignments unchanged
        List<RegisteredMail> updatedCharlieMails = centralDistrict.listRegisteredMail(charlie);
        assertEquals(1, updatedCharlieMails.size());
        
        // Verify specific mail reassignments
        assertTrue(letter1.getCarrier().equals(bob));
        assertTrue(parcel2.getCarrier().equals(bob));
        assertTrue(parcel1.getCarrier().equals(bob));
        assertTrue(letter3.getCarrier().equals(bob));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Set up NorthQuarter with mailmen and inhabitants
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        northQuarter.addInhabitant(walter);
        
        // Create mail items
        List<RegisteredMail> xavierMails = new ArrayList<>();
        List<RegisteredMail> yvonneMails = new ArrayList<>();
        List<RegisteredMail> zackMails = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            xavierMails.add(letter);
            northQuarter.getAllMails().add(letter);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            yvonneMails.add(parcel);
            northQuarter.getAllMails().add(parcel);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            zackMails.add(letter);
            northQuarter.getAllMails().add(letter);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Verify initial assignments
        assertEquals(5, northQuarter.listRegisteredMail(xavier).size());
        assertEquals(3, northQuarter.listRegisteredMail(yvonne).size());
        assertEquals(2, northQuarter.listRegisteredMail(zack).size());
        
        // Remove Yvonne with Xavier as replacement
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        assertTrue(result1);
        
        // Verify Yvonne is removed
        assertFalse(northQuarter.getMailmen().contains(yvonne));
        
        // Verify Xavier now has his original mail plus Yvonne's mail
        assertEquals(8, northQuarter.listRegisteredMail(xavier).size());
        
        // Remove Xavier with Zack as replacement
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        assertTrue(result2);
        
        // Verify Xavier is removed
        assertFalse(northQuarter.getMailmen().contains(xavier));
        
        // Verify Zack has all 10 deliveries
        List<RegisteredMail> finalZackMails = northQuarter.listRegisteredMail(zack);
        assertEquals(10, finalZackMails.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Set up SouthEnd with mailmen and inhabitants
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        southEnd.addInhabitant(rachel);
        
        // Create and assign mail
        Letter letter1 = new Letter();
        southEnd.getAllMails().add(letter1);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Test 1: Normal removal - remove Paul specifying Quinn
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue(result1);
        assertFalse(southEnd.getMailmen().contains(paul));
        assertTrue(letter1.getCarrier().equals(quinn));
        
        // Test 2: Attempt to remove last mailman (should fail)
        boolean result2 = southEnd.removeMailman(quinn, paul);
        assertFalse(result2);
        assertTrue(southEnd.getMailmen().contains(quinn));
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Set up EastHaven with mailmen and inhabitants
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create mail items
        List<RegisteredMail> marioLetters = new ArrayList<>();
        List<RegisteredMail> luigiParcels = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            marioLetters.add(letter);
            eastHaven.getAllMails().add(letter);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            luigiParcels.add(parcel);
            eastHaven.getAllMails().add(parcel);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // Step 1: Add duplicate Mario (should fail)
        boolean addDuplicateResult = eastHaven.addMailman(mario);
        assertFalse(addDuplicateResult);
        
        // Step 2: Remove Mario with Luigi as replacement (should succeed)
        boolean removeMarioResult = eastHaven.removeMailman(mario, luigi);
        assertTrue(removeMarioResult);
        assertFalse(eastHaven.getMailmen().contains(mario));
        
        // Step 3: Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMails = eastHaven.listRegisteredMail(luigi);
        assertEquals(15, luigiMails.size());
        
        // Step 4: Attempt to remove Luigi (last mailman, should fail)
        boolean removeLuigiAttempt = eastHaven.removeMailman(luigi, mario);
        assertFalse(removeLuigiAttempt);
        assertTrue(eastHaven.getMailmen().contains(luigi));
        
        // Step 5: Add Toad (should succeed)
        boolean addToadResult = eastHaven.addMailman(toad);
        assertTrue(addToadResult);
        assertTrue(eastHaven.getMailmen().contains(toad));
        
        // Step 6: Remove Luigi with Toad as replacement (should succeed)
        boolean removeLuigiResult = eastHaven.removeMailman(luigi, toad);
        assertTrue(removeLuigiResult);
        assertFalse(eastHaven.getMailmen().contains(luigi));
        
        // Verify Toad has all 15 deliveries
        List<RegisteredMail> toadMails = eastHaven.listRegisteredMail(toad);
        assertEquals(15, toadMails.size());
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Set up WestRidge with mailmen
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
        List<RegisteredMail> alphaMails = new ArrayList<>();
        List<RegisteredMail> betaMails = new ArrayList<>();
        List<RegisteredMail> gammaMails = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            alphaMails.add(letter);
            westRidge.getAllMails().add(letter);
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(i % 10), letter);
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            betaMails.add(parcel);
            westRidge.getAllMails().add(parcel);
            westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(i % 10), parcel);
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            gammaMails.add(letter);
            westRidge.getAllMails().add(letter);
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(i % 10), letter);
        }
        
        // Verify initial assignments
        assertEquals(10, westRidge.listRegisteredMail(alpha).size());
        assertEquals(10, westRidge.listRegisteredMail(beta).size());
        assertEquals(10, westRidge.listRegisteredMail(gamma).size());
        
        // Step 1: Remove Alpha with Beta as replacement
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue(result1);
        assertFalse(westRidge.getMailmen().contains(alpha));
        
        // Verify Beta now has 20 deliveries
        assertEquals(20, westRidge.listRegisteredMail(beta).size());
        
        // Step 2: Remove Beta with Gamma as replacement
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue(result2);
        assertFalse(westRidge.getMailmen().contains(beta));
        
        // Verify Gamma ends with all 30 deliveries
        List<RegisteredMail> finalGammaMails = westRidge.listRegisteredMail(gamma);
        assertEquals(30, finalGammaMails.size());
    }
}