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
    
    private Mailman alice, bob, charlie, xavier, yvonne, zack, paul, quinn, mario, luigi, toad, alpha, beta, gamma;
    private Inhabitant david, eve, frank, walter, rachel, peach, bowser;
    
    @Before
    public void setUp() {
        // Initialize mailmen
        alice = new Mailman("Alice");
        bob = new Mailman("Bob");
        charlie = new Mailman("Charlie");
        xavier = new Mailman("Xavier");
        yvonne = new Mailman("Yvonne");
        zack = new Mailman("Zack");
        paul = new Mailman("Paul");
        quinn = new Mailman("Quinn");
        mario = new Mailman("Mario");
        luigi = new Mailman("Luigi");
        toad = new Mailman("Toad");
        alpha = new Mailman("Alpha");
        beta = new Mailman("Beta");
        gamma = new Mailman("Gamma");
        
        // Initialize inhabitants
        david = new Inhabitant("David");
        eve = new Inhabitant("Eve");
        frank = new Inhabitant("Frank");
        walter = new Inhabitant("Walter");
        rachel = new Inhabitant("Rachel");
        peach = new Inhabitant("Peach");
        bowser = new Inhabitant("Bowser");
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        centralDistrict = new GeographicalArea();
        
        // Add Mailmen "Alice", "Bob", "Charlie"
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add Inhabitants "David", "Eve", "Frank"
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create and assign mail items
        Letter letter1 = new Letter(david, "Content1");
        Parcel parcel1 = new Parcel(eve, 1.5);
        Letter letter2 = new Letter(frank, "Content2");
        Parcel parcel2 = new Parcel(eve, 2.0);
        Letter letter3 = new Letter(david, "Content3");
        
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Removal should succeed", result);
        
        // Verifications:
        // Alice removed
        assertFalse("Alice should be removed", centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsDeliveries = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have deliveries", bobsDeliveries);
        assertEquals("Bob should have 4 deliveries", 4, bobsDeliveries.size());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob should still have Parcel1", bobsDeliveries.contains(parcel1));
        assertTrue("Bob should still have Letter3", bobsDeliveries.contains(letter3));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesDeliveries = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have deliveries", charliesDeliveries);
        assertEquals("Charlie should have 1 delivery", 1, charliesDeliveries.size());
        assertTrue("Charlie should still have Letter2", charliesDeliveries.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        northQuarter = new GeographicalArea();
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add Inhabitant "Walter"
        northQuarter.addInhabitant(walter);
        
        // Create and assign mail items
        List<Letter> xavierLetters = new ArrayList<>();
        List<Parcel> yvonneParcels = new ArrayList<>();
        List<Letter> zackLetters = new ArrayList<>();
        
        // 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter(walter, "XavierLetter" + i);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
            xavierLetters.add(letter);
        }
        
        // 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel(walter, i + 1.0);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
            yvonneParcels.add(parcel);
        }
        
        // 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter(walter, "ZackLetter" + i);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
            zackLetters.add(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal should succeed", result1);
        
        // Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal should succeed", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zacksDeliveries = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have deliveries", zacksDeliveries);
        assertEquals("Zack should have all 10 deliveries", 10, zacksDeliveries.size());
        
        // Verify Zack has all original mail items
        for (Letter letter : xavierLetters) {
            assertTrue("Zack should have Xavier's letters", zacksDeliveries.contains(letter));
        }
        for (Parcel parcel : yvonneParcels) {
            assertTrue("Zack should have Yvonne's parcels", zacksDeliveries.contains(parcel));
        }
        for (Letter letter : zackLetters) {
            assertTrue("Zack should have his original letters", zacksDeliveries.contains(letter));
        }
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        southEnd = new GeographicalArea();
        
        // Add Mailmen ["Paul", "Quinn"]
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add Inhabitant "Rachel"
        southEnd.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter(rachel, "Content");
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("First removal should succeed", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, quinn); // Self-assignment shouldn't matter since removal fails
        assertFalse("Removing last mailman should fail", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        eastHaven = new GeographicalArea();
        
        // Add Mailmen ["Mario", "Luigi"]
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add Inhabitants ["Peach", "Bowser"]
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create mail items
        List<Letter> marioLetters = new ArrayList<>();
        List<Parcel> luigiParcels = new ArrayList<>();
        
        // 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter(peach, "MarioLetter" + i);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
            marioLetters.add(letter);
        }
        
        // 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel(bowser, i + 1.0);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
            luigiParcels.add(parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean addDuplicateResult = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", addDuplicateResult);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMarioResult = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removing Mario should succeed", removeMarioResult);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisDeliveries = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have deliveries", luigisDeliveries);
        assertEquals("Luigi should have 15 deliveries", 15, luigisDeliveries.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigiResult1 = eastHaven.removeMailman(luigi, luigi);
        assertFalse("Removing last mailman should fail", removeLuigiResult1);
        
        // 5. Add "Toad" → true
        boolean addToadResult = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should succeed", addToadResult);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeLuigiResult2 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removing Luigi with replacement should succeed", removeLuigiResult2);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        westRidge = new GeographicalArea();
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Inhabitant inhabitant = new Inhabitant("Inhabitant" + i);
            westRidge.addInhabitant(inhabitant);
            inhabitants.add(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        List<RegisteredMail> allMail = new ArrayList<>();
        int inhabitantIndex = 0;
        
        // Alpha's 10 mail items
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter(inhabitants.get(inhabitantIndex), "AlphaLetter" + i);
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(inhabitantIndex), letter);
            allMail.add(letter);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Beta's 10 mail items
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel(inhabitants.get(inhabitantIndex), i + 1.0);
            westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(inhabitantIndex), parcel);
            allMail.add(parcel);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Gamma's 10 mail items
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter(inhabitants.get(inhabitantIndex), "GammaLetter" + i);
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(inhabitantIndex), letter);
            allMail.add(letter);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal should succeed", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal should succeed", result2);
        
        // Verifications:
        // Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasDeliveries = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have deliveries", gammasDeliveries);
        assertEquals("Gamma should have all 30 deliveries", 30, gammasDeliveries.size());
        
        // Verify Gamma has all original mail items
        for (RegisteredMail mail : allMail) {
            assertTrue("Gamma should have all mail items", gammasDeliveries.contains(mail));
        }
    }
}