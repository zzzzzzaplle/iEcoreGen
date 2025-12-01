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
    private List<Inhabitant> westRidgeInhabitants;
    
    @Before
    public void setUp() {
        // Initialize mailmen
        alice = new Mailman();
        alice.setId("M001");
        alice.setName("Alice");
        
        bob = new Mailman();
        bob.setId("M002");
        bob.setName("Bob");
        
        charlie = new Mailman();
        charlie.setId("M003");
        charlie.setName("Charlie");
        
        xavier = new Mailman();
        xavier.setId("M004");
        xavier.setName("Xavier");
        
        yvonne = new Mailman();
        yvonne.setId("M005");
        yvonne.setName("Yvonne");
        
        zack = new Mailman();
        zack.setId("M006");
        zack.setName("Zack");
        
        paul = new Mailman();
        paul.setId("M007");
        paul.setName("Paul");
        
        quinn = new Mailman();
        quinn.setId("M008");
        quinn.setName("Quinn");
        
        mario = new Mailman();
        mario.setId("M009");
        mario.setName("Mario");
        
        luigi = new Mailman();
        luigi.setId("M010");
        luigi.setName("Luigi");
        
        toad = new Mailman();
        toad.setId("M011");
        toad.setName("Toad");
        
        alpha = new Mailman();
        alpha.setId("M012");
        alpha.setName("Alpha");
        
        beta = new Mailman();
        beta.setId("M013");
        beta.setName("Beta");
        
        gamma = new Mailman();
        gamma.setId("M014");
        gamma.setName("Gamma");
        
        // Initialize inhabitants
        david = new Inhabitant();
        david.setId("I001");
        david.setName("David");
        
        eve = new Inhabitant();
        eve.setId("I002");
        eve.setName("Eve");
        
        frank = new Inhabitant();
        frank.setId("I003");
        frank.setName("Frank");
        
        walter = new Inhabitant();
        walter.setId("I004");
        walter.setName("Walter");
        
        rachel = new Inhabitant();
        rachel.setId("I005");
        rachel.setName("Rachel");
        
        peach = new Inhabitant();
        peach.setId("I006");
        peach.setName("Peach");
        
        bowser = new Inhabitant();
        bowser.setId("I007");
        bowser.setName("Bowser");
        
        westRidgeInhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setId("I" + (100 + i));
            inhabitant.setName("Inhabitant" + i);
            westRidgeInhabitants.add(inhabitant);
        }
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp
        centralDistrict = new GeographicalArea();
        
        // Add Mailmen
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add Inhabitants
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create and assign mail items
        Letter letter1 = new Letter();
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        
        Parcel parcel1 = new Parcel();
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        
        Letter letter2 = new Letter();
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        
        Parcel parcel2 = new Parcel();
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        
        Letter letter3 = new Letter();
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Removal should be successful", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen", centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobMails = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have mail assignments", bobMails);
        assertEquals("Bob should have 4 mail items", 4, bobMails.size());
        
        // Verify Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob should still have Parcel1", bobMails.contains(parcel1));
        assertTrue("Bob should still have Letter3", bobMails.contains(letter3));
        
        // Verify reassigned items
        assertTrue("Bob should have reassigned Letter1", bobMails.contains(letter1));
        assertTrue("Bob should have reassigned Parcel2", bobMails.contains(parcel2));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieMails = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have mail assignments", charlieMails);
        assertEquals("Charlie should have 1 mail item", 1, charlieMails.size());
        assertTrue("Charlie should still have Letter2", charlieMails.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        northQuarter = new GeographicalArea();
        
        // Add Mailmen
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add Inhabitant
        northQuarter.addInhabitant(walter);
        
        // Create and assign mail items
        List<Letter> xavierLetters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
            xavierLetters.add(letter);
        }
        
        List<Parcel> yvonneParcels = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
            yvonneParcels.add(parcel);
        }
        
        List<Letter> zackLetters = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
            zackLetters.add(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        assertTrue("First removal should be successful", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        assertTrue("Second removal should be successful", result2);
        
        // Expected Output: true, true
        assertTrue("Both removals should return true", result1 && result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zackMails = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have mail assignments", zackMails);
        assertEquals("Zack should have all 10 mail items", 10, zackMails.size());
        
        // Verify all mail items are assigned to Zack
        for (Letter letter : xavierLetters) {
            assertTrue("Xavier's letters should be reassigned to Zack", zackMails.contains(letter));
        }
        for (Parcel parcel : yvonneParcels) {
            assertTrue("Yvonne's parcels should be reassigned to Zack", zackMails.contains(parcel));
        }
        for (Letter letter : zackLetters) {
            assertTrue("Zack's original letters should remain", zackMails.contains(letter));
        }
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        southEnd = new GeographicalArea();
        
        // Add Mailmen
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add Inhabitant
        southEnd.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("First removal should be successful", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is already removed, but replacement doesn't matter since it's last mailman
        assertFalse("Removal of last mailman should fail", result2);
        
        // Verify Quinn is still in the system
        assertTrue("Quinn should still be in mailmen list", southEnd.getMailmen().contains(quinn));
        assertEquals("Should have exactly 1 mailman", 1, southEnd.getMailmen().size());
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        eastHaven = new GeographicalArea();
        
        // Add Mailmen
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add Inhabitants
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create mail items
        List<Letter> marioLetters = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
            marioLetters.add(letter);
        }
        
        List<Parcel> luigiParcels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
            luigiParcels.add(parcel);
        }
        
        // Procedure
        // 1. Add duplicate "Mario" → false
        boolean addDuplicateResult = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", addDuplicateResult);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMarioResult = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removing Mario should be successful", removeMarioResult);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMails = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have mail assignments", luigiMails);
        assertEquals("Luigi should have 15 mail items", 15, luigiMails.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigiFailResult = eastHaven.removeMailman(luigi, mario); // mario is already removed, but replacement doesn't matter
        assertFalse("Removing last mailman should fail", removeLuigiFailResult);
        
        // 5. Add "Toad" → true
        boolean addToadResult = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should be successful", addToadResult);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeLuigiSuccessResult = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removing Luigi with replacement should be successful", removeLuigiSuccessResult);
        
        // Final verification: Toad should have all 15 deliveries
        List<RegisteredMail> toadMails = eastHaven.listRegisteredMail(toad);
        assertNotNull("Toad should have mail assignments", toadMails);
        assertEquals("Toad should have all 15 mail items", 15, toadMails.size());
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        westRidge = new GeographicalArea();
        
        // Add Mailmen
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        for (Inhabitant inhabitant : westRidgeInhabitants) {
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        List<RegisteredMail> alphaMails = new ArrayList<>();
        List<RegisteredMail> betaMails = new ArrayList<>();
        List<RegisteredMail> gammaMails = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(alpha, westRidgeInhabitants.get(i % 10), letter);
            alphaMails.add(letter);
            
            Parcel parcel = new Parcel();
            westRidge.assignRegisteredMailDeliver(beta, westRidgeInhabitants.get(i % 10), parcel);
            betaMails.add(parcel);
            
            Letter letter2 = new Letter();
            westRidge.assignRegisteredMailDeliver(gamma, westRidgeInhabitants.get(i % 10), letter2);
            gammaMails.add(letter2);
        }
        
        // Action
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal should be successful", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal should be successful", result2);
        
        // Verifications
        // Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaFinalMails = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have mail assignments", gammaFinalMails);
        assertEquals("Gamma should have all 30 mail items", 30, gammaFinalMails.size());
        
        // Verify all original mail items are now assigned to Gamma
        for (RegisteredMail mail : alphaMails) {
            assertTrue("Alpha's mail should be reassigned to Gamma", gammaFinalMails.contains(mail));
        }
        for (RegisteredMail mail : betaMails) {
            assertTrue("Beta's mail should be reassigned to Gamma", gammaFinalMails.contains(mail));
        }
        for (RegisteredMail mail : gammaMails) {
            assertTrue("Gamma's original mail should remain", gammaFinalMails.contains(mail));
        }
    }
}