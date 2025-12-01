import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        // Initialize all mailmen and inhabitants for reuse across tests
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
        
        david = new Inhabitant();
        eve = new Inhabitant();
        frank = new Inhabitant();
        walter = new Inhabitant();
        rachel = new Inhabitant();
        peach = new Inhabitant();
        bowser = new Inhabitant();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        centralDistrict = new GeographicalArea();
        
        // Add Mailmen ["Alice", "Bob", "Charlie"]
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add Inhabitants ["David", "Eve", "Frank"]
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
        assertTrue("Alice removal should be successful", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen list", centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsDeliveries = getDeliveriesForMailman(centralDistrict, bob);
        assertEquals("Bob should have 4 deliveries after reassignment", 4, bobsDeliveries.size());
        assertTrue("Letter1 should be reassigned to Bob", bobsDeliveries.contains(letter1));
        assertTrue("Parcel2 should be reassigned to Bob", bobsDeliveries.contains(parcel2));
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Parcel1 should remain with Bob", bobsDeliveries.contains(parcel1));
        assertTrue("Letter3 should remain with Bob", bobsDeliveries.contains(letter3));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesDeliveries = getDeliveriesForMailman(centralDistrict, charlie);
        assertEquals("Charlie should have 1 delivery", 1, charliesDeliveries.size());
        assertTrue("Letter2 should remain with Charlie", charliesDeliveries.contains(letter2));
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
            Letter letter = new Letter();
            xavierLetters.add(letter);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        // 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            yvonneParcels.add(parcel);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        // 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            zackLetters.add(letter);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean firstRemoval = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal (Yvonne) should be successful", firstRemoval);
        
        // Verifications after first removal
        // 3 parcels moved to Xavier (now has 8 items)
        List<RegisteredMail> xavierDeliveriesAfterFirst = getDeliveriesForMailman(northQuarter, xavier);
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xavierDeliveriesAfterFirst.size());
        for (Parcel parcel : yvonneParcels) {
            assertTrue("All Yvonne's parcels should be reassigned to Xavier", xavierDeliveriesAfterFirst.contains(parcel));
        }
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean secondRemoval = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal (Xavier) should be successful", secondRemoval);
        
        // Verifications after second removal
        // 8 letters moved to Zack (now has 10 items)
        List<RegisteredMail> zackDeliveries = getDeliveriesForMailman(northQuarter, zack);
        assertEquals("Zack should have 10 deliveries after second removal", 10, zackDeliveries.size());
        
        // Final state: Only Zack remains with all 10 deliveries
        assertEquals("Only one mailman should remain", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", northQuarter.getMailmen().contains(zack));
        assertEquals("Zack should have all 10 deliveries", 10, zackDeliveries.size());
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
        Letter letter1 = new Letter();
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean firstRemoval = southEnd.removeMailman(paul, quinn);
        assertTrue("Removing Paul should be successful", firstRemoval);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean secondRemoval = southEnd.removeMailman(quinn, paul); // paul is no longer in the list
        assertFalse("Removing last mailman should fail", secondRemoval);
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
            Letter letter = new Letter();
            marioLetters.add(letter);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            luigiParcels.add(parcel);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean duplicateAdd = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate Mario should fail", duplicateAdd);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMario = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removing Mario should be successful", removeMario);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiDeliveries = getDeliveriesForMailman(eastHaven, luigi);
        assertEquals("Luigi should have 15 deliveries after taking over Mario's", 15, luigiDeliveries.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigiFirstAttempt = eastHaven.removeMailman(luigi, mario); // mario is no longer in the list
        assertFalse("Removing last mailman should fail", removeLuigiFirstAttempt);
        
        // 5. Add "Toad" → true
        boolean addToad = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should be successful", addToad);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeLuigiSecondAttempt = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removing Luigi with replacement should be successful", removeLuigiSecondAttempt);
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
            Inhabitant inhabitant = new Inhabitant();
            inhabitants.add(inhabitant);
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        List<RegisteredMail> alphaMails = new ArrayList<>();
        List<RegisteredMail> betaMails = new ArrayList<>();
        List<RegisteredMail> gammaMails = new ArrayList<>();
        
        int inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            alphaMails.add(letter);
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(inhabitantIndex % 10), letter);
            inhabitantIndex++;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            betaMails.add(parcel);
            westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(inhabitantIndex % 10), parcel);
            inhabitantIndex++;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            gammaMails.add(letter);
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(inhabitantIndex % 10), letter);
            inhabitantIndex++;
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean firstRemoval = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal (Alpha) should be successful", firstRemoval);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean secondRemoval = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal (Beta) should be successful", secondRemoval);
        
        // Verifications:
        // Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaDeliveries = getDeliveriesForMailman(westRidge, gamma);
        assertEquals("Gamma should have all 30 deliveries", 30, gammaDeliveries.size());
    }
    
    // Helper method to get all deliveries for a specific mailman
    private List<RegisteredMail> getDeliveriesForMailman(GeographicalArea area, Mailman mailman) {
        List<RegisteredMail> deliveries = new ArrayList<>();
        for (RegisteredMail mail : area.getAllDeliveries()) {
            if (mail.getCarrier() != null && mail.getCarrier().equals(mailman)) {
                deliveries.add(mail);
            }
        }
        return deliveries;
    }
}