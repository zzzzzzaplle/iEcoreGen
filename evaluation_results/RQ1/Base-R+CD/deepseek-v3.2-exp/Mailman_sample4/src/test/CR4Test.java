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
    
    private Mailman alice, bob, charlie, xavier, yvonne, zack, paul, quinn, mario, luigi, toad, alpha, beta, gamma;
    private Inhabitant david, eve, frank, walter, rachel, peach, bowser;
    
    @Before
    public void setUp() {
        // Initialize all objects needed for tests
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
        
        // Create mailmen
        alice = new Mailman(); alice.setId("M001"); alice.setName("Alice");
        bob = new Mailman(); bob.setId("M002"); bob.setName("Bob");
        charlie = new Mailman(); charlie.setId("M003"); charlie.setName("Charlie");
        xavier = new Mailman(); xavier.setId("M004"); xavier.setName("Xavier");
        yvonne = new Mailman(); yvonne.setId("M005"); yvonne.setName("Yvonne");
        zack = new Mailman(); zack.setId("M006"); zack.setName("Zack");
        paul = new Mailman(); paul.setId("M007"); paul.setName("Paul");
        quinn = new Mailman(); quinn.setId("M008"); quinn.setName("Quinn");
        mario = new Mailman(); mario.setId("M009"); mario.setName("Mario");
        luigi = new Mailman(); luigi.setId("M010"); luigi.setName("Luigi");
        toad = new Mailman(); toad.setId("M011"); toad.setName("Toad");
        alpha = new Mailman(); alpha.setId("M012"); alpha.setName("Alpha");
        beta = new Mailman(); beta.setId("M013"); beta.setName("Beta");
        gamma = new Mailman(); gamma.setId("M014"); gamma.setName("Gamma");
        
        // Create inhabitants
        david = new Inhabitant(); david.setId("I001"); david.setName("David");
        eve = new Inhabitant(); eve.setId("I002"); eve.setName("Eve");
        frank = new Inhabitant(); frank.setId("I003"); frank.setName("Frank");
        walter = new Inhabitant(); walter.setId("I004"); walter.setName("Walter");
        rachel = new Inhabitant(); rachel.setId("I005"); rachel.setName("Rachel");
        peach = new Inhabitant(); peach.setId("I006"); peach.setName("Peach");
        bowser = new Inhabitant(); bowser.setId("I007"); bowser.setName("Bowser");
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // Setup CentralDistrict
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create mail items
        Letter letter1 = new Letter(); letter1.setId("L001"); centralDistrict.getAllMails().add(letter1);
        Parcel parcel1 = new Parcel(); parcel1.setId("P001"); centralDistrict.getAllMails().add(parcel1);
        Letter letter2 = new Letter(); letter2.setId("L002"); centralDistrict.getAllMails().add(letter2);
        Parcel parcel2 = new Parcel(); parcel2.setId("P002"); centralDistrict.getAllMails().add(parcel2);
        Letter letter3 = new Letter(); letter3.setId("L003"); centralDistrict.getAllMails().add(letter3);
        
        // Assign mail items
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Removal should be successful", result);
        
        // Verifications
        assertFalse("Alice should be removed", centralDistrict.getMailmen().contains(alice));
        
        // Check Bob's assignments (should have 4 items: original 2 + Alice's 2)
        List<RegisteredMail> bobsMail = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have mail assignments", bobsMail);
        assertEquals("Bob should have 4 mail items", 4, bobsMail.size());
        
        // Check Charlie's assignments (unchanged, 1 item)
        List<RegisteredMail> charliesMail = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have mail assignments", charliesMail);
        assertEquals("Charlie should have 1 mail item", 1, charliesMail.size());
        
        // Verify specific reassignments
        assertTrue("Letter1 should be reassigned to Bob", bobsMail.contains(letter1));
        assertTrue("Parcel2 should be reassigned to Bob", bobsMail.contains(parcel2));
        assertTrue("Parcel1 should remain with Bob", bobsMail.contains(parcel1));
        assertTrue("Letter3 should remain with Bob", bobsMail.contains(letter3));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Setup NorthQuarter
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        northQuarter.addInhabitant(walter);
        
        // Create and assign 10 mail items
        for (int i = 1; i <= 10; i++) {
            if (i <= 5) {
                Letter letter = new Letter(); letter.setId("L" + i);
                northQuarter.getAllMails().add(letter);
                northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
            } else if (i <= 8) {
                Parcel parcel = new Parcel(); parcel.setId("P" + (i-5));
                northQuarter.getAllMails().add(parcel);
                northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
            } else {
                Letter letter = new Letter(); letter.setId("L" + i);
                northQuarter.getAllMails().add(letter);
                northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
            }
        }
        
        // Action 1: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        assertTrue("First removal should be successful", result1);
        
        // Action 2: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        assertTrue("Second removal should be successful", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zacksMail = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have mail assignments", zacksMail);
        assertEquals("Zack should have all 10 deliveries", 10, zacksMail.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Setup SouthEnd
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        southEnd.addInhabitant(rachel);
        
        Letter letter1 = new Letter(); letter1.setId("L001");
        southEnd.getAllMails().add(letter1);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Action 1: Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("First removal should be successful", result1);
        
        // Action 2: Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in list, but method checks existence
        assertFalse("Removal of last mailman should fail", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Setup EastHaven
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter(); letter.setId("L" + i);
            eastHaven.getAllMails().add(letter);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 1; i <= 5; i++) {
            Parcel parcel = new Parcel(); parcel.setId("P" + i);
            eastHaven.getAllMails().add(parcel);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // Procedure 1: Add duplicate "Mario" → false
        boolean addResult1 = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", addResult1);
        
        // Procedure 2: Remove Mario (specify Luigi) → true
        boolean removeResult1 = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removal of Mario should be successful", removeResult1);
        
        // Procedure 3: Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisMail = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have mail assignments", luigisMail);
        assertEquals("Luigi should have 15 deliveries", 15, luigisMail.size());
        
        // Procedure 4: Attempt remove Luigi → false (last mailman)
        boolean removeResult2 = eastHaven.removeMailman(luigi, mario); // mario is no longer in list
        assertFalse("Removal of last mailman should fail", removeResult2);
        
        // Procedure 5: Add "Toad" → true
        boolean addResult2 = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should be successful", addResult2);
        
        // Procedure 6: Remove Luigi (specify Toad) → true
        boolean removeResult3 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removal of Luigi should be successful", removeResult3);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Setup WestRidge
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setId("I" + i);
            inhabitant.setName("Inhabitant" + i);
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCount = 1;
        for (int i = 0; i < 10; i++) {
            Letter letter1 = new Letter(); letter1.setId("L" + mailCount++);
            westRidge.getAllMails().add(letter1);
            westRidge.assignRegisteredMailDeliver(alpha, westRidge.getInhabitants().get(i), letter1);
            
            Parcel parcel1 = new Parcel(); parcel1.setId("P" + mailCount++);
            westRidge.getAllMails().add(parcel1);
            westRidge.assignRegisteredMailDeliver(beta, westRidge.getInhabitants().get(i), parcel1);
            
            Letter letter2 = new Letter(); letter2.setId("L" + mailCount++);
            westRidge.getAllMails().add(letter2);
            westRidge.assignRegisteredMailDeliver(gamma, westRidge.getInhabitants().get(i), letter2);
        }
        
        // Action 1: Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal should be successful", result1);
        
        // Action 2: Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal should be successful", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasMail = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have mail assignments", gammasMail);
        assertEquals("Gamma should have all 30 deliveries", 30, gammasMail.size());
    }
}