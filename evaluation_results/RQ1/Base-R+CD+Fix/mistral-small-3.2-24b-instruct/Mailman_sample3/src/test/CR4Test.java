import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        // Initialize all test objects
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
        
        // Initialize mailmen
        alice = new Mailman("1", "Alice");
        bob = new Mailman("2", "Bob");
        charlie = new Mailman("3", "Charlie");
        
        xavier = new Mailman("4", "Xavier");
        yvonne = new Mailman("5", "Yvonne");
        zack = new Mailman("6", "Zack");
        
        paul = new Mailman("7", "Paul");
        quinn = new Mailman("8", "Quinn");
        
        mario = new Mailman("9", "Mario");
        luigi = new Mailman("10", "Luigi");
        toad = new Mailman("11", "Toad");
        
        alpha = new Mailman("12", "Alpha");
        beta = new Mailman("13", "Beta");
        gamma = new Mailman("14", "Gamma");
        
        // Initialize inhabitants
        david = new Inhabitant("1", "David");
        eve = new Inhabitant("2", "Eve");
        frank = new Inhabitant("3", "Frank");
        
        walter = new Inhabitant("4", "Walter");
        
        rachel = new Inhabitant("5", "Rachel");
        
        peach = new Inhabitant("6", "Peach");
        bowser = new Inhabitant("7", "Bowser");
        
        westRidgeInhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            westRidgeInhabitants.add(new Inhabitant("w" + i, "Inhabitant" + i));
        }
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
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items), Bob's original deliveries (Parcel1, Letter3) unchanged
        List<RegisteredMail> bobMail = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have mail assignments", bobMail);
        assertEquals("Bob should have 4 mail items", 4, bobMail.size());
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieMail = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have mail assignments", charlieMail);
        assertEquals("Charlie should have 1 mail item", 1, charlieMail.size());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
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
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        assertTrue("Yvonne removal should be successful", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        assertTrue("Xavier removal should be successful", result2);
        
        // Expected Output: true, true
        assertTrue("First removal should return true", result1);
        assertTrue("Second removal should return true", result2);
        
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
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("Paul removal should be successful", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul);
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
        Mailman marioDuplicate = new Mailman("9", "Mario");
        boolean addResult1 = eastHaven.addMailman(marioDuplicate);
        assertFalse("Adding duplicate Mario should fail", addResult1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeResult1 = eastHaven.removeMailman(mario, luigi);
        assertTrue("Mario removal should be successful", removeResult1);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMail = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have mail assignments", luigiMail);
        assertEquals("Luigi should have 15 deliveries", 15, luigiMail.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeResult2 = eastHaven.removeMailman(luigi, mario);
        assertFalse("Luigi removal should fail (last mailman)", removeResult2);
        
        // 5. Add "Toad" → true
        boolean addResult2 = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should be successful", addResult2);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeResult3 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Luigi removal should be successful", removeResult3);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        for (Inhabitant inhabitant : westRidgeInhabitants) {
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(alpha, westRidgeInhabitants.get(inhabitantIndex), letter);
            inhabitantIndex = (inhabitantIndex + 1) % westRidgeInhabitants.size();
        }
        
        inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            westRidge.assignRegisteredMailDeliver(beta, westRidgeInhabitants.get(inhabitantIndex), parcel);
            inhabitantIndex = (inhabitantIndex + 1) % westRidgeInhabitants.size();
        }
        
        inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(gamma, westRidgeInhabitants.get(inhabitantIndex), letter);
            inhabitantIndex = (inhabitantIndex + 1) % westRidgeInhabitants.size();
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("Alpha removal should be successful", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Beta removal should be successful", result2);
        
        // Verifications:
        // Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaMail = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have mail assignments", gammaMail);
        assertEquals("Gamma should have all 30 deliveries", 30, gammaMail.size());
    }
}