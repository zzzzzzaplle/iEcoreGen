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
        // Initialize all test objects
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
        
        // Initialize mailmen
        alice = new Mailman(); alice.setId("A1"); alice.setName("Alice");
        bob = new Mailman(); bob.setId("B1"); bob.setName("Bob");
        charlie = new Mailman(); charlie.setId("C1"); charlie.setName("Charlie");
        xavier = new Mailman(); xavier.setId("X1"); xavier.setName("Xavier");
        yvonne = new Mailman(); yvonne.setId("Y1"); yvonne.setName("Yvonne");
        zack = new Mailman(); zack.setId("Z1"); zack.setName("Zack");
        paul = new Mailman(); paul.setId("P1"); paul.setName("Paul");
        quinn = new Mailman(); quinn.setId("Q1"); quinn.setName("Quinn");
        mario = new Mailman(); mario.setId("M1"); mario.setName("Mario");
        luigi = new Mailman(); luigi.setId("L1"); luigi.setName("Luigi");
        toad = new Mailman(); toad.setId("T1"); toad.setName("Toad");
        alpha = new Mailman(); alpha.setId("AL1"); alpha.setName("Alpha");
        beta = new Mailman(); beta.setId("BE1"); beta.setName("Beta");
        gamma = new Mailman(); gamma.setId("GA1"); gamma.setName("Gamma");
        
        // Initialize inhabitants
        david = new Inhabitant(); david.setId("D1"); david.setName("David");
        eve = new Inhabitant(); eve.setId("E1"); eve.setName("Eve");
        frank = new Inhabitant(); frank.setId("F1"); frank.setName("Frank");
        walter = new Inhabitant(); walter.setId("W1"); walter.setName("Walter");
        rachel = new Inhabitant(); rachel.setId("R1"); rachel.setName("Rachel");
        peach = new Inhabitant(); peach.setId("PCH1"); peach.setName("Peach");
        bowser = new Inhabitant(); bowser.setId("BOW1"); bowser.setName("Bowser");
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
        
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Removal should succeed", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen list", 
                   centralDistrict.listRegisteredMail(alice) != null);
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobMail = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have mail assignments", bobMail);
        assertEquals("Bob should have 4 mail items", 4, bobMail.size());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob's mail should contain parcel1", bobMail.contains(parcel1));
        assertTrue("Bob's mail should contain letter3", bobMail.contains(letter3));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieMail = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have mail assignments", charlieMail);
        assertEquals("Charlie should have 1 mail item", 1, charlieMail.size());
        assertTrue("Charlie's mail should contain letter2", charlieMail.contains(letter2));
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
        
        // Action: Remove Yvonne (specify Xavier as replacement), then remove Xavier (specify Zack as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true, true
        assertTrue("First removal should succeed", result1);
        assertTrue("Second removal should succeed", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zackMail = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have mail assignments", zackMail);
        assertEquals("Zack should have all 10 deliveries", 10, zackMail.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        southEnd.addInhabitant(rachel);
        
        Letter letter1 = new Letter();
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("First removal should succeed", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer available, but shouldn't matter
        assertFalse("Removal of last mailman should fail", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        Letter[] marioLetters = new Letter[10];
        for (int i = 0; i < 10; i++) {
            marioLetters[i] = new Letter();
            eastHaven.assignRegisteredMailDeliver(mario, peach, marioLetters[i]);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        Parcel[] luigiParcels = new Parcel[5];
        for (int i = 0; i < 5; i++) {
            luigiParcels[i] = new Parcel();
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, luigiParcels[i]);
        }
        
        // Procedure
        // 1. Add duplicate "Mario" → false
        boolean addResult = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", addResult);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeResult1 = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removal of Mario should succeed", removeResult1);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMail = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have mail assignments", luigiMail);
        assertEquals("Luigi should have 15 deliveries", 15, luigiMail.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeResult2 = eastHaven.removeMailman(luigi, mario); // mario is no longer available
        assertFalse("Removal of last mailman should fail", removeResult2);
        
        // 5. Add "Toad" → true
        boolean addToadResult = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should succeed", addToadResult);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeResult3 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removal of Luigi should succeed", removeResult3);
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
            inhabitants[i].setId("INH" + i);
            inhabitants[i].setName("Inhabitant" + i);
            westRidge.addInhabitant(inhabitants[i]);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCount = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants[i], letter);
            mailCount++;
        }
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            westRidge.assignRegisteredMailDeliver(beta, inhabitants[i], parcel);
            mailCount++;
        }
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants[i], letter);
            mailCount++;
        }
        
        assertEquals("Should have created 30 mail items", 30, mailCount);
        
        // Action
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal should succeed", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal should succeed", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaMail = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have mail assignments", gammaMail);
        assertEquals("Gamma should have all 30 deliveries", 30, gammaMail.size());
    }
}