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
    
    private Mailman alice, bob, charlie;
    private Mailman xavier, yvonne, zack;
    private Mailman paul, quinn;
    private Mailman mario, luigi, toad;
    private Mailman alpha, beta, gamma;
    
    private Inhabitant david, eve, frank;
    private Inhabitant walter;
    private Inhabitant rachel;
    private Inhabitant peach, bowser;
    
    @Before
    public void setUp() {
        // Initialize all geographical areas
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
        List<RegisteredMail> aliceInitial = centralDistrict.listRegisteredMail(alice);
        List<RegisteredMail> bobInitial = centralDistrict.listRegisteredMail(bob);
        List<RegisteredMail> charlieInitial = centralDistrict.listRegisteredMail(charlie);
        
        assertEquals(2, aliceInitial.size());
        assertEquals(2, bobInitial.size());
        assertEquals(1, charlieInitial.size());
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications:
        // - Alice removed
        assertFalse(centralDistrict.getMailmen().contains(alice));
        assertEquals(2, centralDistrict.getMailmen().size());
        
        // - Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobAfterRemoval = centralDistrict.listRegisteredMail(bob);
        assertNotNull(bobAfterRemoval);
        assertEquals(4, bobAfterRemoval.size());
        
        // - Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieAfterRemoval = centralDistrict.listRegisteredMail(charlie);
        assertNotNull(charlieAfterRemoval);
        assertEquals(1, charlieAfterRemoval.size());
        
        // Verify specific mail reassignments
        assertTrue(letter1.getCarrier().equals(bob));
        assertTrue(parcel2.getCarrier().equals(bob));
        assertTrue(parcel1.getCarrier().equals(bob));
        assertTrue(letter3.getCarrier().equals(bob));
        assertTrue(letter2.getCarrier().equals(charlie));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Set up NorthQuarter
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        northQuarter.addInhabitant(walter);
        
        // Create and assign: 5 Letters (Xavier→Walter), 3 Parcels (Yvonne→Walter), 2 Letters (Zack→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
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
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        assertTrue(result1);
        
        // Verify after first removal
        List<RegisteredMail> xavierAfterFirst = northQuarter.listRegisteredMail(xavier);
        assertEquals(8, xavierAfterFirst.size()); // 5 original + 3 from Yvonne
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        assertTrue(result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zackFinal = northQuarter.listRegisteredMail(zack);
        assertNotNull(zackFinal);
        assertEquals(10, zackFinal.size());
        
        // Verify only Zack remains in mailmen list
        assertEquals(1, northQuarter.getMailmen().size());
        assertTrue(northQuarter.getMailmen().contains(zack));
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
        
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue(result1);
        
        // Verify Paul is removed and Quinn has the delivery
        assertFalse(southEnd.getMailmen().contains(paul));
        List<RegisteredMail> quinnDeliveries = southEnd.listRegisteredMail(quinn);
        assertNotNull(quinnDeliveries);
        assertEquals(1, quinnDeliveries.size());
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in the system, but operation should fail anyway
        assertFalse(result2);
        
        // Verify Quinn is still in the system
        assertTrue(southEnd.getMailmen().contains(quinn));
        assertEquals(1, southEnd.getMailmen().size());
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Set up EastHaven
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create: 10 Letters (Mario→Peach), 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // 1. Add duplicate "Mario" → false
        boolean addDuplicate = eastHaven.addMailman(mario);
        assertFalse(addDuplicate);
        
        // Verify initial state
        List<RegisteredMail> marioInitial = eastHaven.listRegisteredMail(mario);
        List<RegisteredMail> luigiInitial = eastHaven.listRegisteredMail(luigi);
        assertEquals(10, marioInitial.size());
        assertEquals(5, luigiInitial.size());
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMario = eastHaven.removeMailman(mario, luigi);
        assertTrue(removeMario);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiAfterFirstRemoval = eastHaven.listRegisteredMail(luigi);
        assertNotNull(luigiAfterFirstRemoval);
        assertEquals(15, luigiAfterFirstRemoval.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigiFailed = eastHaven.removeMailman(luigi, mario); // mario is no longer in system
        assertFalse(removeLuigiFailed);
        
        // 5. Add "Toad" → true
        boolean addToad = eastHaven.addMailman(toad);
        assertTrue(addToad);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeLuigiSuccess = eastHaven.removeMailman(luigi, toad);
        assertTrue(removeLuigiSuccess);
        
        // Verify final state: only Toad remains with all 15 deliveries
        assertEquals(1, eastHaven.getMailmen().size());
        assertTrue(eastHaven.getMailmen().contains(toad));
        
        List<RegisteredMail> toadFinal = eastHaven.listRegisteredMail(toad);
        assertNotNull(toadFinal);
        assertEquals(15, toadFinal.size());
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
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants[inhabitantIndex % 10], letter);
            inhabitantIndex++;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            westRidge.assignRegisteredMailDeliver(beta, inhabitants[inhabitantIndex % 10], parcel);
            inhabitantIndex++;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants[inhabitantIndex % 10], letter);
            inhabitantIndex++;
        }
        
        // Verify initial assignments
        List<RegisteredMail> alphaInitial = westRidge.listRegisteredMail(alpha);
        List<RegisteredMail> betaInitial = westRidge.listRegisteredMail(beta);
        List<RegisteredMail> gammaInitial = westRidge.listRegisteredMail(gamma);
        assertEquals(10, alphaInitial.size());
        assertEquals(10, betaInitial.size());
        assertEquals(10, gammaInitial.size());
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean removeAlpha = westRidge.removeMailman(alpha, beta);
        assertTrue(removeAlpha);
        
        // Verify after first removal
        List<RegisteredMail> betaAfterFirst = westRidge.listRegisteredMail(beta);
        assertEquals(20, betaAfterFirst.size()); // 10 original + 10 from Alpha
        
        // 2. Remove Beta (specify Gamma) → true
        boolean removeBeta = westRidge.removeMailman(beta, gamma);
        assertTrue(removeBeta);
        
        // Verifications:
        // - Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaFinal = westRidge.listRegisteredMail(gamma);
        assertNotNull(gammaFinal);
        assertEquals(30, gammaFinal.size());
        
        // Verify only Gamma remains
        assertEquals(1, westRidge.getMailmen().size());
        assertTrue(westRidge.getMailmen().contains(gamma));
    }
}