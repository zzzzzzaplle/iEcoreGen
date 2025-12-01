import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    private GeographicalArea area;
    private Mailman alice, bob, charlie, xavier, yvonne, zack, paul, quinn, mario, luigi, toad, alpha, beta, gamma;
    private Inhabitant david, eve, frank, walter, rachel, peach, bowser;
    private List<Inhabitant> inhabitants;

    @Before
    public void setUp() {
        area = new GeographicalArea();
        
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
        
        inhabitants = new ArrayList<>();
    }

    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp
        area.setMailmen(new ArrayList<>());
        area.setInhabitants(new ArrayList<>());
        area.setAllMails(new ArrayList<>());
        
        // Add mailmen
        area.addMailman(alice);
        area.addMailman(bob);
        area.addMailman(charlie);
        
        // Add inhabitants
        area.addInhabitant(david);
        area.addInhabitant(eve);
        area.addInhabitant(frank);
        
        // Create and assign mail items
        Letter letter1 = new Letter();
        Parcel parcel1 = new Parcel();
        Letter letter2 = new Letter();
        Parcel parcel2 = new Parcel();
        Letter letter3 = new Letter();
        
        area.addRegisteredMail(letter1);
        area.addRegisteredMail(parcel1);
        area.addRegisteredMail(letter2);
        area.addRegisteredMail(parcel2);
        area.addRegisteredMail(letter3);
        
        // Assign mail to mailmen and inhabitants
        area.assignRegisteredMailDeliver(alice, david, letter1);
        area.assignRegisteredMailDeliver(bob, eve, parcel1);
        area.assignRegisteredMailDeliver(charlie, frank, letter2);
        area.assignRegisteredMailDeliver(alice, eve, parcel2);
        area.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = area.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Alice removal should succeed", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen", area.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsMail = area.listRegisteredMail(bob);
        assertNotNull("Bob should have mail assignments", bobsMail);
        assertEquals("Bob should have 4 mail items", 4, bobsMail.size());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob should still have parcel1", bobsMail.contains(parcel1));
        assertTrue("Bob should still have letter3", bobsMail.contains(letter3));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesMail = area.listRegisteredMail(charlie);
        assertNotNull("Charlie should have mail assignments", charliesMail);
        assertEquals("Charlie should have 1 mail item", 1, charliesMail.size());
        assertTrue("Charlie should still have letter2", charliesMail.contains(letter2));
    }

    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        area.setMailmen(new ArrayList<>());
        area.setInhabitants(new ArrayList<>());
        area.setAllMails(new ArrayList<>());
        
        // Add mailmen
        area.addMailman(xavier);
        area.addMailman(yvonne);
        area.addMailman(zack);
        
        // Add inhabitant
        area.addInhabitant(walter);
        
        // Create and assign mail items
        List<RegisteredMail> xaviersLetters = new ArrayList<>();
        List<RegisteredMail> yvonnesParcels = new ArrayList<>();
        List<RegisteredMail> zacksLetters = new ArrayList<>();
        
        // Create 5 Letters for Xavier
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            area.addRegisteredMail(letter);
            area.assignRegisteredMailDeliver(xavier, walter, letter);
            xaviersLetters.add(letter);
        }
        
        // Create 3 Parcels for Yvonne
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            area.addRegisteredMail(parcel);
            area.assignRegisteredMailDeliver(yvonne, walter, parcel);
            yvonnesParcels.add(parcel);
        }
        
        // Create 2 Letters for Zack
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            area.addRegisteredMail(letter);
            area.assignRegisteredMailDeliver(zack, walter, letter);
            zacksLetters.add(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = area.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("Yvonne removal should succeed", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = area.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Xavier removal should succeed", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zacksFinalMail = area.listRegisteredMail(zack);
        assertNotNull("Zack should have mail assignments", zacksFinalMail);
        assertEquals("Zack should have all 10 mail items", 10, zacksFinalMail.size());
        
        // Verify all mail items are assigned to Zack
        for (RegisteredMail mail : area.getAllMails()) {
            assertEquals("All mail should be assigned to Zack", zack, mail.getCarrier());
        }
    }

    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        area.setMailmen(new ArrayList<>());
        area.setInhabitants(new ArrayList<>());
        area.setAllMails(new ArrayList<>());
        
        // Add mailmen
        area.addMailman(paul);
        area.addMailman(quinn);
        
        // Add inhabitant
        area.addInhabitant(rachel);
        
        // Create and assign mail item
        Letter letter1 = new Letter();
        area.addRegisteredMail(letter1);
        area.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = area.removeMailman(paul, quinn);
        assertTrue("Paul removal should succeed", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = area.removeMailman(quinn, paul); // paul is already removed, but method should fail anyway
        assertFalse("Quinn removal should fail (last mailman)", result2);
    }

    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        area.setMailmen(new ArrayList<>());
        area.setInhabitants(new ArrayList<>());
        area.setAllMails(new ArrayList<>());
        
        // Add mailmen
        area.addMailman(mario);
        area.addMailman(luigi);
        
        // Add inhabitants
        area.addInhabitant(peach);
        area.addInhabitant(bowser);
        
        // Create mail items
        List<RegisteredMail> mariosLetters = new ArrayList<>();
        List<RegisteredMail> luigisParcels = new ArrayList<>();
        
        // Create 10 Letters for Mario
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            area.addRegisteredMail(letter);
            area.assignRegisteredMailDeliver(mario, peach, letter);
            mariosLetters.add(letter);
        }
        
        // Create 5 Parcels for Luigi
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            area.addRegisteredMail(parcel);
            area.assignRegisteredMailDeliver(luigi, bowser, parcel);
            luigisParcels.add(parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean result1 = area.addMailman(mario);
        assertFalse("Adding duplicate Mario should fail", result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = area.removeMailman(mario, luigi);
        assertTrue("Mario removal should succeed", result2);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisMail = area.listRegisteredMail(luigi);
        assertNotNull("Luigi should have mail assignments", luigisMail);
        assertEquals("Luigi should have 15 mail items", 15, luigisMail.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = area.removeMailman(luigi, mario); // mario is already removed, but method should fail anyway
        assertFalse("Luigi removal should fail (last mailman)", result3);
        
        // 5. Add "Toad" → true
        boolean result4 = area.addMailman(toad);
        assertTrue("Adding Toad should succeed", result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = area.removeMailman(luigi, toad);
        assertTrue("Luigi removal should succeed", result5);
        
        // Verify Toad has all 15 deliveries
        List<RegisteredMail> toadsMail = area.listRegisteredMail(toad);
        assertNotNull("Toad should have mail assignments", toadsMail);
        assertEquals("Toad should have 15 mail items", 15, toadsMail.size());
    }

    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        area.setMailmen(new ArrayList<>());
        area.setInhabitants(new ArrayList<>());
        area.setAllMails(new ArrayList<>());
        
        // Add mailmen
        area.addMailman(alpha);
        area.addMailman(beta);
        area.addMailman(gamma);
        
        // Add 10 inhabitants
        List<Inhabitant> tenInhabitants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            area.addInhabitant(inhabitant);
            tenInhabitants.add(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        List<RegisteredMail> allMail = new ArrayList<>();
        
        // Alpha's 10 mail items
        for (int i = 0; i < 10; i++) {
            RegisteredMail mail = (i % 2 == 0) ? new Letter() : new Parcel();
            area.addRegisteredMail(mail);
            area.assignRegisteredMailDeliver(alpha, tenInhabitants.get(i % 10), mail);
            allMail.add(mail);
        }
        
        // Beta's 10 mail items
        for (int i = 0; i < 10; i++) {
            RegisteredMail mail = (i % 2 == 0) ? new Letter() : new Parcel();
            area.addRegisteredMail(mail);
            area.assignRegisteredMailDeliver(beta, tenInhabitants.get(i % 10), mail);
            allMail.add(mail);
        }
        
        // Gamma's 10 mail items
        for (int i = 0; i < 10; i++) {
            RegisteredMail mail = (i % 2 == 0) ? new Letter() : new Parcel();
            area.addRegisteredMail(mail);
            area.assignRegisteredMailDeliver(gamma, tenInhabitants.get(i % 10), mail);
            allMail.add(mail);
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = area.removeMailman(alpha, beta);
        assertTrue("Alpha removal should succeed", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = area.removeMailman(beta, gamma);
        assertTrue("Beta removal should succeed", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasMail = area.listRegisteredMail(gamma);
        assertNotNull("Gamma should have mail assignments", gammasMail);
        assertEquals("Gamma should have all 30 mail items", 30, gammasMail.size());
        
        // Verify all mail items are assigned to Gamma
        for (RegisteredMail mail : area.getAllMails()) {
            assertEquals("All mail should be assigned to Gamma", gamma, mail.getCarrier());
        }
    }
}