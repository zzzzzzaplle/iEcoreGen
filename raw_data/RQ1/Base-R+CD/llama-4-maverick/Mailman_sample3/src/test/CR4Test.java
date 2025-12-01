import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

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
        
        // Initialize geographical areas
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // Set up CentralDistrict
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create and assign mail items
        Letter letter1 = new Letter();
        Parcel parcel1 = new Parcel();
        Letter letter2 = new Letter();
        Parcel parcel2 = new Parcel();
        Letter letter3 = new Letter();
        
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Verify initial assignments
        List<RegisteredMail> aliceInitialDeliveries = centralDistrict.listRegisteredMail(alice);
        List<RegisteredMail> bobInitialDeliveries = centralDistrict.listRegisteredMail(bob);
        List<RegisteredMail> charlieInitialDeliveries = centralDistrict.listRegisteredMail(charlie);
        
        assertEquals(2, aliceInitialDeliveries.size());
        assertEquals(2, bobInitialDeliveries.size());
        assertEquals(1, charlieInitialDeliveries.size());
        
        // Action: Remove Alice with Bob as replacement
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications
        assertFalse(centralDistrict.getMailmen().contains(alice)); // Alice removed
        assertTrue(centralDistrict.getMailmen().contains(bob));
        assertTrue(centralDistrict.getMailmen().contains(charlie));
        
        List<RegisteredMail> bobFinalDeliveries = centralDistrict.listRegisteredMail(bob);
        List<RegisteredMail> charlieFinalDeliveries = centralDistrict.listRegisteredMail(charlie);
        
        // Bob should have 4 items (original 2 + Alice's 2)
        assertEquals(4, bobFinalDeliveries.size());
        
        // Charlie's assignments unchanged (1 item)
        assertEquals(1, charlieFinalDeliveries.size());
        
        // Verify specific reassignments
        assertTrue(bobFinalDeliveries.contains(letter1)); // Reassigned from Alice
        assertTrue(bobFinalDeliveries.contains(parcel2)); // Reassigned from Alice
        assertTrue(bobFinalDeliveries.contains(parcel1)); // Original Bob delivery
        assertTrue(bobFinalDeliveries.contains(letter3)); // Original Bob delivery
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Set up NorthQuarter
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        northQuarter.addInhabitant(walter);
        
        // Create and assign 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Verify initial assignments
        List<RegisteredMail> xavierInitial = northQuarter.listRegisteredMail(xavier);
        List<RegisteredMail> yvonneInitial = northQuarter.listRegisteredMail(yvonne);
        List<RegisteredMail> zackInitial = northQuarter.listRegisteredMail(zack);
        
        assertEquals(5, xavierInitial.size());
        assertEquals(3, yvonneInitial.size());
        assertEquals(2, zackInitial.size());
        
        // Action 1: Remove Yvonne with Xavier as replacement
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Verifications after first removal
        assertFalse(northQuarter.getMailmen().contains(yvonne));
        assertTrue(northQuarter.getMailmen().contains(xavier));
        assertTrue(northQuarter.getMailmen().contains(zack));
        
        List<RegisteredMail> xavierAfterFirst = northQuarter.listRegisteredMail(xavier);
        List<RegisteredMail> zackAfterFirst = northQuarter.listRegisteredMail(zack);
        
        // Xavier now has 8 items (original 5 + Yvonne's 3)
        assertEquals(8, xavierAfterFirst.size());
        // Zack unchanged (2 items)
        assertEquals(2, zackAfterFirst.size());
        
        // Action 2: Remove Xavier with Zack as replacement
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Verifications after second removal
        assertFalse(northQuarter.getMailmen().contains(xavier));
        assertFalse(northQuarter.getMailmen().contains(yvonne));
        assertTrue(northQuarter.getMailmen().contains(zack));
        
        List<RegisteredMail> zackFinal = northQuarter.listRegisteredMail(zack);
        
        // Zack ends with all 10 deliveries (original 2 + Xavier's 8)
        assertEquals(10, zackFinal.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Set up SouthEnd
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        southEnd.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Action 1: Attempt remove Paul specifying Quinn
        boolean result1 = southEnd.removeMailman(paul, quinn);
        
        // Expected Output: true (normal case)
        assertTrue(result1);
        
        // Verify Paul is removed and delivery reassigned
        assertFalse(southEnd.getMailmen().contains(paul));
        assertTrue(southEnd.getMailmen().contains(quinn));
        
        List<RegisteredMail> quinnDeliveries = southEnd.listRegisteredMail(quinn);
        assertEquals(1, quinnDeliveries.size());
        assertTrue(quinnDeliveries.contains(letter1));
        
        // Action 2: Attempt remove Quinn (last mailman)
        // Note: According to specification, should attempt to remove last mailman, which should fail
        // Using paul as replacement (even though paul is no longer available) - should fail anyway
        boolean result2 = southEnd.removeMailman(quinn, paul); // Paul is no longer available, but should fail anyway
        
        // Expected Output: false
        assertFalse(result2);
        
        // Verify Quinn is still present (removal failed)
        assertTrue(southEnd.getMailmen().contains(quinn));
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Set up EastHaven
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
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
        
        // Procedure 3: Verify Luigi now has 15 deliveries (original 5 + Mario's 10)
        // Note: Specification says "5 deliveries" but logic requires 15 (Mario's 10 + Luigi's original 5)
        // Code later verifies Toad has 15 deliveries, confirming Luigi should have 15
        List<RegisteredMail> luigiDeliveries = eastHaven.listRegisteredMail(luigi);
        assertEquals(15, luigiDeliveries.size());
        
        // Procedure 4: Attempt remove Luigi (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // Mario is no longer available
        
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
        
        // Final verification: Only Toad remains with all 15 deliveries
        assertFalse(eastHaven.getMailmen().contains(mario));
        assertFalse(eastHaven.getMailmen().contains(luigi));
        assertTrue(eastHaven.getMailmen().contains(toad));
        
        List<RegisteredMail> toadDeliveries = eastHaven.listRegisteredMail(toad);
        assertEquals(15, toadDeliveries.size());
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Set up WestRidge
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
        int inhabitantIndex = 0;
        
        // Alpha's 10 deliveries
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                westRidge.assignRegisteredMailDeliver(alpha, inhabitants[inhabitantIndex], letter);
            } else {
                Parcel parcel = new Parcel();
                westRidge.assignRegisteredMailDeliver(alpha, inhabitants[inhabitantIndex], parcel);
            }
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Beta's 10 deliveries
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                westRidge.assignRegisteredMailDeliver(beta, inhabitants[inhabitantIndex], letter);
            } else {
                Parcel parcel = new Parcel();
                westRidge.assignRegisteredMailDeliver(beta, inhabitants[inhabitantIndex], parcel);
            }
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Gamma's 10 deliveries
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                westRidge.assignRegisteredMailDeliver(gamma, inhabitants[inhabitantIndex], letter);
            } else {
                Parcel parcel = new Parcel();
                westRidge.assignRegisteredMailDeliver(gamma, inhabitants[inhabitantIndex], parcel);
            }
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Verify initial assignments
        List<RegisteredMail> alphaInitial = westRidge.listRegisteredMail(alpha);
        List<RegisteredMail> betaInitial = westRidge.listRegisteredMail(beta);
        List<RegisteredMail> gammaInitial = westRidge.listRegisteredMail(gamma);
        
        assertEquals(10, alphaInitial.size());
        assertEquals(10, betaInitial.size());
        assertEquals(10, gammaInitial.size());
        
        // Action 1: Remove Alpha (specify Beta)
        boolean result1 = westRidge.removeMailman(alpha, beta);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Verifications after first removal
        assertFalse(westRidge.getMailmen().contains(alpha));
        assertTrue(westRidge.getMailmen().contains(beta));
        assertTrue(westRidge.getMailmen().contains(gamma));
        
        List<RegisteredMail> betaAfterFirst = westRidge.listRegisteredMail(beta);
        List<RegisteredMail> gammaAfterFirst = westRidge.listRegisteredMail(gamma);
        
        // Beta now has 20 items (original 10 + Alpha's 10)
        assertEquals(20, betaAfterFirst.size());
        // Gamma unchanged (10 items)
        assertEquals(10, gammaAfterFirst.size());
        
        // Action 2: Remove Beta (specify Gamma)
        boolean result2 = westRidge.removeMailman(beta, gamma);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Final verifications
        assertFalse(westRidge.getMailmen().contains(alpha));
        assertFalse(westRidge.getMailmen().contains(beta));
        assertTrue(westRidge.getMailmen().contains(gamma));
        
        List<RegisteredMail> gammaFinal = westRidge.listRegisteredMail(gamma);
        
        // Gamma ends with all 30 deliveries (original 10 + Beta's 20)
        assertEquals(30, gammaFinal.size());
    }
}