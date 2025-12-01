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
    
    @Before
    public void setUp() {
        // Initialize all geographical areas for tests
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp
        Mailman alice = new Mailman("Alice");
        Mailman bob = new Mailman("Bob");
        Mailman charlie = new Mailman("Charlie");
        
        Inhabitant david = new Inhabitant("David");
        Inhabitant eve = new Inhabitant("Eve");
        Inhabitant frank = new Inhabitant("Frank");
        
        // Add mailmen to CentralDistrict
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add inhabitants to CentralDistrict
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create mail items
        Letter letter1 = new Letter(david);
        Parcel parcel1 = new Parcel(eve);
        Letter letter2 = new Letter(frank);
        Parcel parcel2 = new Parcel(eve);
        Letter letter3 = new Letter(david);
        
        // Assign mail items
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
        assertFalse("Alice should be removed", centralDistrict.getMailmen().contains(alice));
        
        // Bob's assignments verification
        List<RegisteredMail> bobsDeliveries = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have deliveries", bobsDeliveries);
        assertEquals("Bob should have 4 deliveries", 4, bobsDeliveries.size());
        
        // Charlie's assignments verification
        List<RegisteredMail> charliesDeliveries = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have deliveries", charliesDeliveries);
        assertEquals("Charlie should have 1 delivery", 1, charliesDeliveries.size());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        Mailman xavier = new Mailman("Xavier");
        Mailman yvonne = new Mailman("Yvonne");
        Mailman zack = new Mailman("Zack");
        
        Inhabitant walter = new Inhabitant("Walter");
        
        // Add mailmen to NorthQuarter
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add inhabitant to NorthQuarter
        northQuarter.addInhabitant(walter);
        
        // Create and assign 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter(walter);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel(walter);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter(walter);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean firstResult = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal should succeed", firstResult);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean secondResult = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal should succeed", secondResult);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zacksDeliveries = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have deliveries", zacksDeliveries);
        assertEquals("Zack should have all 10 deliveries", 10, zacksDeliveries.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        Mailman paul = new Mailman("Paul");
        Mailman quinn = new Mailman("Quinn");
        
        Inhabitant rachel = new Inhabitant("Rachel");
        
        // Add mailmen to SouthEnd
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add inhabitant to SouthEnd
        southEnd.addInhabitant(rachel);
        
        // Create and assign Letter1 (Paul→Rachel)
        Letter letter1 = new Letter(rachel);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean firstResult = southEnd.removeMailman(paul, quinn);
        assertTrue("First removal should succeed", firstResult);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean secondResult = southEnd.removeMailman(quinn, paul); // paul no longer exists, but method should fail before checking
        assertFalse("Removal of last mailman should fail", secondResult);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        Mailman mario = new Mailman("Mario");
        Mailman luigi = new Mailman("Luigi");
        Mailman toad = new Mailman("Toad");
        
        Inhabitant peach = new Inhabitant("Peach");
        Inhabitant bowser = new Inhabitant("Bowser");
        
        // Add mailmen to EastHaven
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add inhabitants to EastHaven
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create and assign 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter(peach);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // Create and assign 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel(bowser);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean duplicateResult = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", duplicateResult);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMarioResult = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removing Mario should succeed", removeMarioResult);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisDeliveries = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have deliveries", luigisDeliveries);
        assertEquals("Luigi should have 15 deliveries", 15, luigisDeliveries.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigiResult = eastHaven.removeMailman(luigi, mario); // mario no longer exists
        assertFalse("Removing last mailman should fail", removeLuigiResult);
        
        // 5. Add "Toad" → true
        boolean addToadResult = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should succeed", addToadResult);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean finalRemoveResult = eastHaven.removeMailman(luigi, toad);
        assertTrue("Final removal should succeed", finalRemoveResult);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        Mailman alpha = new Mailman("Alpha");
        Mailman beta = new Mailman("Beta");
        Mailman gamma = new Mailman("Gamma");
        
        // Add mailmen to WestRidge
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant("Inhabitant" + i);
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        List<Inhabitant> inhabitants = westRidge.getInhabitants();
        int inhabitantIndex = 0;
        
        // Create 10 items for Alpha
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter(inhabitants.get(inhabitantIndex));
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(inhabitantIndex), letter);
            } else {
                Parcel parcel = new Parcel(inhabitants.get(inhabitantIndex));
                westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(inhabitantIndex), parcel);
            }
            inhabitantIndex = (inhabitantIndex + 1) % inhabitants.size();
        }
        
        // Create 10 items for Beta
        for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) {
                Letter letter = new Letter(inhabitants.get(inhabitantIndex));
            westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(inhabitantIndex), letter);
            } else {
                Parcel parcel = new Parcel(inhabitants.get(inhabitantIndex));
                westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(inhabitantIndex), parcel);
            }
            inhabitantIndex = (inhabitantIndex + 1) % inhabitants.size();
        }
        
        // Create 10 items for Gamma
        for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) {
                Letter letter = new Letter(inhabitants.get(inhabitantIndex));
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(inhabitantIndex), letter);
            } else {
                Parcel parcel = new Parcel(inhabitants.get(inhabitantIndex));
                westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(inhabitantIndex), parcel);
            }
            inhabitantIndex = (inhabitantIndex + 1) % inhabitants.size();
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean firstResult = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal should succeed", firstResult);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean secondResult = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal should succeed", secondResult);
        
        // Verifications:
        // Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasDeliveries = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have deliveries", gammasDeliveries);
        assertEquals("Gamma should have all 30 deliveries", 30, gammasDeliveries.size());
    }
}