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
        // Initialize geographical areas
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
        
        // Initialize mailmen
        alice = new Mailman();
        alice.setName("Alice");
        bob = new Mailman();
        bob.setName("Bob");
        charlie = new Mailman();
        charlie.setName("Charlie");
        xavier = new Mailman();
        xavier.setName("Xavier");
        yvonne = new Mailman();
        yvonne.setName("Yvonne");
        zack = new Mailman();
        zack.setName("Zack");
        paul = new Mailman();
        paul.setName("Paul");
        quinn = new Mailman();
        quinn.setName("Quinn");
        mario = new Mailman();
        mario.setName("Mario");
        luigi = new Mailman();
        luigi.setName("Luigi");
        toad = new Mailman();
        toad.setName("Toad");
        alpha = new Mailman();
        alpha.setName("Alpha");
        beta = new Mailman();
        beta.setName("Beta");
        gamma = new Mailman();
        gamma.setName("Gamma");
        
        // Initialize inhabitants
        david = new Inhabitant();
        david.setName("David");
        eve = new Inhabitant();
        eve.setName("Eve");
        frank = new Inhabitant();
        frank.setName("Frank");
        walter = new Inhabitant();
        walter.setName("Walter");
        rachel = new Inhabitant();
        rachel.setName("Rachel");
        peach = new Inhabitant();
        peach.setName("Peach");
        bowser = new Inhabitant();
        bowser.setName("Bowser");
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
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
        assertFalse("Alice should be removed from mailmen list", 
                   centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobMails = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have mail assignments", bobMails);
        assertEquals("Bob should have 4 mail items after reassignment", 4, bobMails.size());
        
        // Verify Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob should still have Parcel1", bobMails.contains(parcel1));
        assertTrue("Bob should still have Letter3", bobMails.contains(letter3));
        
        // Verify Alice's mail reassigned to Bob
        assertTrue("Letter1 should be reassigned to Bob", bobMails.contains(letter1));
        assertTrue("Parcel2 should be reassigned to Bob", bobMails.contains(parcel2));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieMails = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have mail assignments", charlieMails);
        assertEquals("Charlie should have 1 mail item", 1, charlieMails.size());
        assertTrue("Charlie should still have Letter2", charlieMails.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        northQuarter.addInhabitant(walter);
        
        // Create 5 Letters (Xavier→Walter)
        Letter[] xavierLetters = new Letter[5];
        for (int i = 0; i < 5; i++) {
            xavierLetters[i] = new Letter();
            northQuarter.assignRegisteredMailDeliver(xavier, walter, xavierLetters[i]);
        }
        
        // Create 3 Parcels (Yvonne→Walter)
        Parcel[] yvonneParcels = new Parcel[3];
        for (int i = 0; i < 3; i++) {
            yvonneParcels[i] = new Parcel();
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, yvonneParcels[i]);
        }
        
        // Create 2 Letters (Zack→Walter)
        Letter[] zackLetters = new Letter[2];
        for (int i = 0; i < 2; i++) {
            zackLetters[i] = new Letter();
            northQuarter.assignRegisteredMailDeliver(zack, walter, zackLetters[i]);
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
        assertEquals("Zack should have all 10 deliveries", 10, zackMails.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        southEnd.addInhabitant(rachel);
        
        Letter letter1 = new Letter();
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("Paul removal should be successful", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in the list
        assertFalse("Quinn removal should fail (last mailman)", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
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
        assertEquals("Luigi should have 15 deliveries", 15, luigiMails.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // mario is no longer in the list
        assertFalse("Luigi removal should fail (last mailman)", result3);
        
        // 5. Add "Toad" → true
        boolean result4 = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should be successful", result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Luigi removal should be successful after adding Toad", result5);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant();
            inhabitants[i].setName("Inhabitant" + (i + 1));
            westRidge.addInhabitant(inhabitants[i]);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCounter = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants[i], letter);
            mailCounter++;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            westRidge.assignRegisteredMailDeliver(beta, inhabitants[i], parcel);
            mailCounter++;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants[i], letter);
            mailCounter++;
        }
        
        assertEquals("Should have created 30 mail items", 30, mailCounter);
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("Alpha removal should be successful", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Beta removal should be successful", result2);
        
        // Verifications:
        // Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaMails = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have mail assignments", gammaMails);
        assertEquals("Gamma should have all 30 deliveries", 30, gammaMails.size());
    }
}