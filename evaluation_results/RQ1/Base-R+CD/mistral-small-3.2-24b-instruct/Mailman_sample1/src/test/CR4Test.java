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
        // Initialize all mailmen
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
        
        // Initialize all inhabitants
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
        
        // Initialize geographical areas
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        // Add Mailmen "Alice", "Bob", "Charlie"
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add Inhabitants "David", "Eve", "Frank"
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
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Removal of Alice should be successful", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen list", centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobMails = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have mail assignments", bobMails);
        assertEquals("Bob should have 4 mail items", 4, bobMails.size());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob should still have Parcel1", bobMails.contains(parcel1));
        assertTrue("Bob should still have Letter3", bobMails.contains(letter3));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieMails = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have mail assignments", charlieMails);
        assertEquals("Charlie should have 1 mail item", 1, charlieMails.size());
        assertTrue("Charlie should still have Letter2", charlieMails.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add Inhabitant "Walter"
        northQuarter.addInhabitant(walter);
        
        // Create and assign mail items
        // 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        // 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        // 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal (Yvonne) should be successful", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal (Xavier) should be successful", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zackMails = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have mail assignments", zackMails);
        assertEquals("Zack should have all 10 deliveries", 10, zackMails.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        // Add Mailmen ["Paul", "Quinn"]
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add Inhabitant "Rachel"
        southEnd.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("Removal of Paul should be successful", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in the system, but shouldn't matter
        assertFalse("Removal of last mailman should fail", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        // Add Mailmen ["Mario", "Luigi"]
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add Inhabitants ["Peach", "Bowser"]
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create mail items
        // 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean addResult = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", addResult);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeResult1 = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removal of Mario should be successful", removeResult1);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMails = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have mail assignments", luigiMails);
        assertEquals("Luigi should have 15 mail items", 15, luigiMails.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeResult2 = eastHaven.removeMailman(luigi, mario); // mario is no longer in the system
        assertFalse("Removal of last mailman should fail", removeResult2);
        
        // 5. Add "Toad" → true
        boolean addToadResult = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should be successful", addToadResult);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeResult3 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removal of Luigi should be successful", removeResult3);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant();
            inhabitants[i].setId("I" + (100 + i));
            inhabitants[i].setName("Inhabitant" + i);
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
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal (Alpha) should be successful", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal (Beta) should be successful", result2);
        
        // Verifications:
        // Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaMails = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have mail assignments", gammaMails);
        assertEquals("Gamma should have all 30 deliveries", 30, gammaMails.size());
    }
}