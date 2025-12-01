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
        // Initialize mailmen
        alice = new Mailman("M1", "Alice");
        bob = new Mailman("M2", "Bob");
        charlie = new Mailman("M3", "Charlie");
        xavier = new Mailman("M4", "Xavier");
        yvonne = new Mailman("M5", "Yvonne");
        zack = new Mailman("M6", "Zack");
        paul = new Mailman("M7", "Paul");
        quinn = new Mailman("M8", "Quinn");
        mario = new Mailman("M9", "Mario");
        luigi = new Mailman("M10", "Luigi");
        toad = new Mailman("M11", "Toad");
        alpha = new Mailman("M12", "Alpha");
        beta = new Mailman("M13", "Beta");
        gamma = new Mailman("M14", "Gamma");
        
        // Initialize inhabitants
        david = new Inhabitant("I1", "David");
        eve = new Inhabitant("I2", "Eve");
        frank = new Inhabitant("I3", "Frank");
        walter = new Inhabitant("I4", "Walter");
        rachel = new Inhabitant("I5", "Rachel");
        peach = new Inhabitant("I6", "Peach");
        bowser = new Inhabitant("I7", "Bowser");
        
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
        Letter letter1 = new Letter("L1", david);
        Parcel parcel1 = new Parcel("P1", eve);
        Letter letter2 = new Letter("L2", frank);
        Parcel parcel2 = new Parcel("P2", eve);
        Letter letter3 = new Letter("L3", david);
        
        centralDistrict.addRegisteredMail(letter1);
        centralDistrict.addRegisteredMail(parcel1);
        centralDistrict.addRegisteredMail(letter2);
        centralDistrict.addRegisteredMail(parcel2);
        centralDistrict.addRegisteredMail(letter3);
        
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications
        assertFalse("Alice should be removed", centralDistrict.getMailmen().contains(alice));
        
        // Bob should have 4 items: original Parcel1 and Letter3 + reassigned Letter1 and Parcel2
        List<RegisteredMail> bobDeliveries = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have deliveries", bobDeliveries);
        assertEquals("Bob should have 4 deliveries", 4, bobDeliveries.size());
        
        // Charlie's assignments unchanged (should have 1 item)
        List<RegisteredMail> charlieDeliveries = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have deliveries", charlieDeliveries);
        assertEquals("Charlie should have 1 delivery", 1, charlieDeliveries.size());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Set up NorthQuarter
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        northQuarter.addInhabitant(walter);
        
        // Create and assign mail items
        for (int i = 1; i <= 5; i++) {
            Letter letter = new Letter("LX" + i, walter);
            northQuarter.addRegisteredMail(letter);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        for (int i = 1; i <= 3; i++) {
            Parcel parcel = new Parcel("PY" + i, walter);
            northQuarter.addRegisteredMail(parcel);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        for (int i = 1; i <= 2; i++) {
            Letter letter = new Letter("LZ" + i, walter);
            northQuarter.addRegisteredMail(letter);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal should succeed", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal should succeed", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zackDeliveries = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have deliveries", zackDeliveries);
        assertEquals("Zack should have all 10 deliveries", 10, zackDeliveries.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Set up SouthEnd
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        southEnd.addInhabitant(rachel);
        
        Letter letter1 = new Letter("L1", rachel);
        southEnd.addRegisteredMail(letter1);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("First removal should succeed", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in the list, but method doesn't check that
        assertFalse("Removal of last mailman should fail", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Set up EastHaven
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter("LM" + i, peach);
            eastHaven.addRegisteredMail(letter);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 1; i <= 5; i++) {
            Parcel parcel = new Letter("PL" + i, bowser);
            eastHaven.addRegisteredMail(parcel);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // 1. Add duplicate "Mario" → false
        boolean addDuplicate = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", addDuplicate);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMario = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removing Mario should succeed", removeMario);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiDeliveries = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have deliveries", luigiDeliveries);
        assertEquals("Luigi should have 15 deliveries", 15, luigiDeliveries.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigi = eastHaven.removeMailman(luigi, mario); // mario is no longer in the list
        assertFalse("Removing last mailman should fail", removeLuigi);
        
        // 5. Add "Toad" → true
        boolean addToad = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should succeed", addToad);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeLuigiAgain = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removing Luigi with Toad as replacement should succeed", removeLuigiAgain);
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
            inhabitants[i] = new Inhabitant("IW" + (i + 1), "Inhabitant" + (i + 1));
            westRidge.addInhabitant(inhabitants[i]);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCounter = 1;
        
        // 10 items for Alpha
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter("LA" + mailCounter, inhabitants[i % 10]);
            westRidge.addRegisteredMail(letter);
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants[i % 10], letter);
            mailCounter++;
        }
        
        // 10 items for Beta
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel("PB" + mailCounter, inhabitants[i % 10]);
            westRidge.addRegisteredMail(parcel);
            westRidge.assignRegisteredMailDeliver(beta, inhabitants[i % 10], parcel);
            mailCounter++;
        }
        
        // 10 items for Gamma
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter("LG" + mailCounter, inhabitants[i % 10]);
            westRidge.addRegisteredMail(letter);
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants[i % 10], letter);
            mailCounter++;
        }
        
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal should succeed", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal should succeed", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaDeliveries = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have deliveries", gammaDeliveries);
        assertEquals("Gamma should have all 30 deliveries", 30, gammaDeliveries.size());
    }
}