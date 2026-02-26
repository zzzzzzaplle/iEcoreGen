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
    private List<Inhabitant> westRidgeInhabitants;

    @Before
    public void setUp() {
        // Initialize mailmen
        alice = new Mailman();
        alice.setName("Alice");
        alice.setId("M001");
        
        bob = new Mailman();
        bob.setName("Bob");
        bob.setId("M002");
        
        charlie = new Mailman();
        charlie.setName("Charlie");
        charlie.setId("M003");
        
        xavier = new Mailman();
        xavier.setName("Xavier");
        xavier.setId("M004");
        
        yvonne = new Mailman();
        yvonne.setName("Yvonne");
        yvonne.setId("M005");
        
        zack = new Mailman();
        zack.setName("Zack");
        zack.setId("M006");
        
        paul = new Mailman();
        paul.setName("Paul");
        paul.setId("M007");
        
        quinn = new Mailman();
        quinn.setName("Quinn");
        quinn.setId("M008");
        
        mario = new Mailman();
        mario.setName("Mario");
        mario.setId("M009");
        
        luigi = new Mailman();
        luigi.setName("Luigi");
        luigi.setId("M010");
        
        toad = new Mailman();
        toad.setName("Toad");
        toad.setId("M011");
        
        alpha = new Mailman();
        alpha.setName("Alpha");
        alpha.setId("M012");
        
        beta = new Mailman();
        beta.setName("Beta");
        beta.setId("M013");
        
        gamma = new Mailman();
        gamma.setName("Gamma");
        gamma.setId("M014");
        
        // Initialize inhabitants
        david = new Inhabitant();
        david.setName("David");
        david.setId("I001");
        
        eve = new Inhabitant();
        eve.setName("Eve");
        eve.setId("I002");
        
        frank = new Inhabitant();
        frank.setName("Frank");
        frank.setId("I003");
        
        walter = new Inhabitant();
        walter.setName("Walter");
        walter.setId("I004");
        
        rachel = new Inhabitant();
        rachel.setName("Rachel");
        rachel.setId("I005");
        
        peach = new Inhabitant();
        peach.setName("Peach");
        peach.setId("I006");
        
        bowser = new Inhabitant();
        bowser.setName("Bowser");
        bowser.setId("I007");
    }

    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp
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
        
        // Assign mail items
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Alice removal should be successful", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen", centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobMails = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have mail assignments", bobMails);
        assertEquals("Bob should have 4 mail items", 4, bobMails.size());
        
        // Verify Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob should still have parcel1", bobMails.contains(parcel1));
        assertTrue("Bob should still have letter3", bobMails.contains(letter3));
        
        // Verify reassigned items
        assertTrue("Bob should have letter1 after reassignment", bobMails.contains(letter1));
        assertTrue("Bob should have parcel2 after reassignment", bobMails.contains(parcel2));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieMails = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have mail assignments", charlieMails);
        assertEquals("Charlie should have 1 mail item", 1, charlieMails.size());
        assertTrue("Charlie should have letter2", charlieMails.contains(letter2));
    }

    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        northQuarter = new GeographicalArea();
        
        // Add mailmen
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add inhabitant
        northQuarter.addInhabitant(walter);
        
        // Create and assign mail items
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
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("Yvonne removal should be successful", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Xavier removal should be successful", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zackMails = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have mail assignments", zackMails);
        assertEquals("Zack should have all 10 mail items", 10, zackMails.size());
        
        // Verify mailmen list
        assertEquals("Should have only one mailman left", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", northQuarter.getMailmen().contains(zack));
    }

    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        southEnd = new GeographicalArea();
        
        // Add mailmen
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add inhabitant
        southEnd.addInhabitant(rachel);
        
        // Create and assign mail item
        Letter letter1 = new Letter();
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs
        
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("Paul removal should be successful", result1);
        
        // Verify reassignment
        List<RegisteredMail> quinnMails = southEnd.listRegisteredMail(quinn);
        assertNotNull("Quinn should have mail assignments", quinnMails);
        assertTrue("Quinn should have letter1 after reassignment", quinnMails.contains(letter1));
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in the list
        assertFalse("Quinn removal should fail (last mailman)", result2);
        
        // Verify Quinn is still there
        assertTrue("Quinn should still be in mailmen list", southEnd.getMailmen().contains(quinn));
        assertEquals("Should have exactly one mailman", 1, southEnd.getMailmen().size());
    }

    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        eastHaven = new GeographicalArea();
        
        // Add mailmen
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add inhabitants
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create and assign mail items
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // Procedure:
        
        // 1. Add duplicate "Mario" → false
        boolean result1 = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate Mario should fail", result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        assertTrue("Mario removal should be successful", result2);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMails = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have mail assignments", luigiMails);
        assertEquals("Luigi should have 15 mail items", 15, luigiMails.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // mario is no longer in the list
        assertFalse("Luigi removal should fail (last mailman)", result3);
        
        // 5. Add "Toad" → true
        boolean result4 = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should be successful", result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Luigi removal should be successful", result5);
        
        // Verify final state
        List<RegisteredMail> toadMails = eastHaven.listRegisteredMail(toad);
        assertNotNull("Toad should have mail assignments", toadMails);
        assertEquals("Toad should have all 15 mail items", 15, toadMails.size());
        assertEquals("Should have exactly one mailman", 1, eastHaven.getMailmen().size());
        assertTrue("Toad should be the only remaining mailman", eastHaven.getMailmen().contains(toad));
    }

    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        westRidge = new GeographicalArea();
        
        // Add mailmen
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 inhabitants
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setName("Inhabitant" + i);
            inhabitant.setId("I" + (100 + i));
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        List<Inhabitant> inhabitants = westRidge.getInhabitants();
        int inhabitantIndex = 0;
        
        // Alpha's mail
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(inhabitantIndex), letter);
            } else {
                Parcel parcel = new Parcel();
                westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(inhabitantIndex), parcel);
            }
            inhabitantIndex = (inhabitantIndex + 1) % inhabitants.size();
        }
        
        // Beta's mail
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(inhabitantIndex), letter);
            } else {
                Parcel parcel = new Parcel();
                westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(inhabitantIndex), parcel);
            }
            inhabitantIndex = (inhabitantIndex + 1) % inhabitants.size();
        }
        
        // Gamma's mail
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(inhabitantIndex), letter);
            } else {
                Parcel parcel = new Parcel();
                westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(inhabitantIndex), parcel);
            }
            inhabitantIndex = (inhabitantIndex + 1) % inhabitants.size();
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("Alpha removal should be successful", result1);
        
        // Verify Beta now has Alpha's mail
        List<RegisteredMail> betaMailsAfterAlphaRemoval = westRidge.listRegisteredMail(beta);
        assertNotNull("Beta should have mail assignments", betaMailsAfterAlphaRemoval);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Beta removal should be successful", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaMails = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have mail assignments", gammaMails);
        assertEquals("Gamma should have all 30 mail items", 30, gammaMails.size());
        
        // Verify mailmen list
        assertEquals("Should have only one mailman left", 1, westRidge.getMailmen().size());
        assertTrue("Gamma should be the only remaining mailman", westRidge.getMailmen().contains(gamma));
    }
}