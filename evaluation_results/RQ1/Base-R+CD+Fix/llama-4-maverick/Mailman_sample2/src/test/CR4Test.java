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
    
    private Mailman alice, bob, charlie;
    private Mailman xavier, yvonne, zack;
    private Mailman paul, quinn;
    private Mailman mario, luigi, toad;
    private Mailman alpha, beta, gamma;
    
    private Inhabitant david, eve, frank;
    private Inhabitant walter;
    private Inhabitant rachel;
    private Inhabitant peach, bowser;
    
    @Before
    public void setUp() {
        // Initialize mailmen
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
        
        // Initialize inhabitants
        david = new Inhabitant();
        eve = new Inhabitant();
        frank = new Inhabitant();
        walter = new Inhabitant();
        rachel = new Inhabitant();
        peach = new Inhabitant();
        bowser = new Inhabitant();
        
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
        assertTrue("Alice should be successfully removed", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen", centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsDeliveries = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have deliveries", bobsDeliveries);
        assertEquals("Bob should have 4 deliveries", 4, bobsDeliveries.size());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob's deliveries should contain parcel1", bobsDeliveries.contains(parcel1));
        assertTrue("Bob's deliveries should contain letter3", bobsDeliveries.contains(letter3));
        assertTrue("Bob's deliveries should contain letter1", bobsDeliveries.contains(letter1));
        assertTrue("Bob's deliveries should contain parcel2", bobsDeliveries.contains(parcel2));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesDeliveries = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have deliveries", charliesDeliveries);
        assertEquals("Charlie should have 1 delivery", 1, charliesDeliveries.size());
        assertTrue("Charlie's deliveries should contain letter2", charliesDeliveries.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Set up NorthQuarter
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
        assertTrue("Yvonne should be successfully removed", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        assertTrue("Xavier should be successfully removed", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zacksDeliveries = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have deliveries", zacksDeliveries);
        assertEquals("Zack should have all 10 deliveries", 10, zacksDeliveries.size());
        
        // Verify mailmen list
        assertEquals("Should have only 1 mailman remaining", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the remaining mailman", northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Set up SouthEnd
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        southEnd.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("Paul should be successfully removed", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in the system
        assertFalse("Should not be able to remove last mailman", result2);
        
        // Verify Quinn is still there
        assertTrue("Quinn should still be in mailmen", southEnd.getMailmen().contains(quinn));
        assertEquals("Should still have 1 mailman", 1, southEnd.getMailmen().size());
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Set up EastHaven
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
        
        // 1. Add duplicate "Mario" → false
        boolean result1 = eastHaven.addMailman(mario);
        assertFalse("Should not be able to add duplicate mailman", result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        assertTrue("Mario should be successfully removed", result2);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisDeliveries = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have deliveries", luigisDeliveries);
        assertEquals("Luigi should have 15 deliveries", 15, luigisDeliveries.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // mario is no longer in the system
        assertFalse("Should not be able to remove last mailman", result3);
        
        // 5. Add "Toad" → true
        boolean result4 = eastHaven.addMailman(toad);
        assertTrue("Toad should be successfully added", result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Luigi should be successfully removed", result5);
        
        // Final verification
        assertEquals("Should have only Toad remaining", 1, eastHaven.getMailmen().size());
        assertTrue("Toad should be the remaining mailman", eastHaven.getMailmen().contains(toad));
        
        List<RegisteredMail> toadsDeliveries = eastHaven.listRegisteredMail(toad);
        assertNotNull("Toad should have deliveries", toadsDeliveries);
        assertEquals("Toad should have all 15 deliveries", 15, toadsDeliveries.size());
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
            inhabitants[i] = new Inhabitant();
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
        
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("Alpha should be successfully removed", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Beta should be successfully removed", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasDeliveries = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have deliveries", gammasDeliveries);
        assertEquals("Gamma should have all 30 deliveries", 30, gammasDeliveries.size());
        
        // Verify mailmen list
        assertEquals("Should have only 1 mailman remaining", 1, westRidge.getMailmen().size());
        assertTrue("Gamma should be the remaining mailman", westRidge.getMailmen().contains(gamma));
    }
}