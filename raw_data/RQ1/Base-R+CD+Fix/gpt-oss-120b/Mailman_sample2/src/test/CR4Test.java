import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private GeographicalArea area;
    
    @Before
    public void setUp() {
        area = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp
        // 1. Create GeographicalArea "CentralDistrict" - already done in setUp()
        // 2. Add Mailmen "Alice", "Bob", "Charlie"
        Mailman alice = new Mailman("Alice");
        Mailman bob = new Mailman("Bob");
        Mailman charlie = new Mailman("Charlie");
        area.addMailman(alice);
        area.addMailman(bob);
        area.addMailman(charlie);
        
        // 3. Add Inhabitants "David", "Eve", "Frank"
        Inhabitant david = new Inhabitant("David");
        Inhabitant eve = new Inhabitant("Eve");
        Inhabitant frank = new Inhabitant("Frank");
        area.addInhabitant(david);
        area.addInhabitant(eve);
        area.addInhabitant(frank);
        
        // 4. Create and assign mail items
        Letter letter1 = new Letter(david);
        Parcel parcel1 = new Parcel(eve);
        Letter letter2 = new Letter(frank);
        Parcel parcel2 = new Parcel(eve);
        Letter letter3 = new Letter(david);
        
        // Assign mail to mailmen
        area.assignRegisteredMailDeliver(alice, david, letter1);
        area.assignRegisteredMailDeliver(bob, eve, parcel1);
        area.assignRegisteredMailDeliver(charlie, frank, letter2);
        area.assignRegisteredMailDeliver(alice, eve, parcel2);
        area.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = area.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Alice should be successfully removed", result);
        
        // Verifications
        // - Alice removed
        assertFalse("Alice should be removed from mailmen list", area.getMailmen().contains(alice));
        
        // - Letter1 and Parcel2 reassigned to Bob (now has 4 items), Bob's original deliveries unchanged
        List<RegisteredMail> bobDeliveries = area.listRegisteredMail(bob);
        assertNotNull("Bob should have deliveries", bobDeliveries);
        assertEquals("Bob should have 4 deliveries", 4, bobDeliveries.size());
        
        // - Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieDeliveries = area.listRegisteredMail(charlie);
        assertNotNull("Charlie should have deliveries", charlieDeliveries);
        assertEquals("Charlie should have 1 delivery", 1, charlieDeliveries.size());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        // 1. Create GeographicalArea "NorthQuarter" - already done in setUp()
        // 2. Add Mailmen ["Xavier", "Yvonne", "Zack"]
        Mailman xavier = new Mailman("Xavier");
        Mailman yvonne = new Mailman("Yvonne");
        Mailman zack = new Mailman("Zack");
        area.addMailman(xavier);
        area.addMailman(yvonne);
        area.addMailman(zack);
        
        // 3. Add Inhabitant "Walter"
        Inhabitant walter = new Inhabitant("Walter");
        area.addInhabitant(walter);
        
        // 4. Create and assign mail items
        // - 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter(walter);
            area.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        // - 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel(walter);
            area.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        // - 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter(walter);
            area.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement), then remove Xavier (specify Zack as replacement)
        boolean result1 = area.removeMailman(yvonne, xavier);
        boolean result2 = area.removeMailman(xavier, zack);
        
        // Expected Output: true, true
        assertTrue("Yvonne should be successfully removed", result1);
        assertTrue("Xavier should be successfully removed", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zackDeliveries = area.listRegisteredMail(zack);
        assertNotNull("Zack should have deliveries", zackDeliveries);
        assertEquals("Zack should have all 10 deliveries", 10, zackDeliveries.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        // 1. Create GeographicalArea "SouthEnd" - already done in setUp()
        // 2. Add Mailmen ["Paul", "Quinn"]
        Mailman paul = new Mailman("Paul");
        Mailman quinn = new Mailman("Quinn");
        area.addMailman(paul);
        area.addMailman(quinn);
        
        // 3. Add Inhabitant "Rachel"
        Inhabitant rachel = new Inhabitant("Rachel");
        area.addInhabitant(rachel);
        
        // 4. Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter(rachel);
        area.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = area.removeMailman(paul, quinn);
        assertTrue("Paul should be successfully removed", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = area.removeMailman(quinn, paul); // paul is already removed
        assertFalse("Quinn should not be removed as last mailman", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        // 1. Create GeographicalArea "EastHaven" - already done in setUp()
        // 2. Add Mailmen ["Mario", "Luigi"]
        Mailman mario = new Mailman("Mario");
        Mailman luigi = new Mailman("Luigi");
        area.addMailman(mario);
        area.addMailman(luigi);
        
        // 3. Add Inhabitants ["Peach", "Bowser"]
        Inhabitant peach = new Inhabitant("Peach");
        Inhabitant bowser = new Inhabitant("Bowser");
        area.addInhabitant(peach);
        area.addInhabitant(bowser);
        
        // 4. Create mail items
        // - 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter(peach);
            area.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // - 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel(bowser);
            area.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean addDuplicateResult = area.addMailman(mario);
        assertFalse("Duplicate Mario should not be added", addDuplicateResult);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMarioResult = area.removeMailman(mario, luigi);
        assertTrue("Mario should be successfully removed", removeMarioResult);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiDeliveries = area.listRegisteredMail(luigi);
        assertNotNull("Luigi should have deliveries", luigiDeliveries);
        assertEquals("Luigi should have 15 deliveries", 15, luigiDeliveries.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigiResult = area.removeMailman(luigi, mario); // mario is already removed
        assertFalse("Luigi should not be removed as last mailman", removeLuigiResult);
        
        // 5. Add "Toad" → true
        Mailman toad = new Mailman("Toad");
        boolean addToadResult = area.addMailman(toad);
        assertTrue("Toad should be successfully added", addToadResult);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeLuigiWithToadResult = area.removeMailman(luigi, toad);
        assertTrue("Luigi should be successfully removed with Toad as replacement", removeLuigiWithToadResult);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        // 1. Create GeographicalArea "WestRidge" - already done in setUp()
        // 2. Add Mailmen ["Alpha", "Beta", "Gamma"]
        Mailman alpha = new Mailman("Alpha");
        Mailman beta = new Mailman("Beta");
        Mailman gamma = new Mailman("Gamma");
        area.addMailman(alpha);
        area.addMailman(beta);
        area.addMailman(gamma);
        
        // 3. Add 10 Inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant("Inhabitant" + i);
            area.addInhabitant(inhabitants[i]);
        }
        
        // 4. Create 30 mail items (10 each mailman)
        // Alpha's 10 items
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter(inhabitants[i]);
            area.assignRegisteredMailDeliver(alpha, inhabitants[i], letter);
        }
        
        // Beta's 10 items
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel(inhabitants[i]);
            area.assignRegisteredMailDeliver(beta, inhabitants[i], parcel);
        }
        
        // Gamma's 10 items
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter(inhabitants[i]);
            area.assignRegisteredMailDeliver(gamma, inhabitants[i], letter);
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean removeAlphaResult = area.removeMailman(alpha, beta);
        assertTrue("Alpha should be successfully removed", removeAlphaResult);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean removeBetaResult = area.removeMailman(beta, gamma);
        assertTrue("Beta should be successfully removed", removeBetaResult);
        
        // Verifications:
        // - Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaDeliveries = area.listRegisteredMail(gamma);
        assertNotNull("Gamma should have deliveries", gammaDeliveries);
        assertEquals("Gamma should have all 30 deliveries", 30, gammaDeliveries.size());
    }
}