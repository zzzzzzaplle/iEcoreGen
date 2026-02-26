import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private MailDeliveryManager manager;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp
        GeographicalArea centralDistrict = new GeographicalArea("CentralDistrict");
        
        Mailman alice = new Mailman("M1", "Alice");
        Mailman bob = new Mailman("M2", "Bob");
        Mailman charlie = new Mailman("M3", "Charlie");
        
        Inhabitant david = new Inhabitant("I1", "David");
        Inhabitant eve = new Inhabitant("I2", "Eve");
        Inhabitant frank = new Inhabitant("I3", "Frank");
        
        // Add mailmen to area
        manager.addMailman(alice, centralDistrict);
        manager.addMailman(bob, centralDistrict);
        manager.addMailman(charlie, centralDistrict);
        
        // Add inhabitants to area
        manager.addInhabitant(david, centralDistrict);
        manager.addInhabitant(eve, centralDistrict);
        manager.addInhabitant(frank, centralDistrict);
        
        // Create and assign mail items
        Letter letter1 = new Letter("L1", david);
        Parcel parcel1 = new Parcel("P1", eve);
        Letter letter2 = new Letter("L2", frank);
        Parcel parcel2 = new Parcel("P2", eve);
        Letter letter3 = new Letter("L3", david);
        
        manager.assignMailman(letter1, alice);
        manager.assignMailman(parcel1, bob);
        manager.assignMailman(letter2, charlie);
        manager.assignMailman(parcel2, alice);
        manager.assignMailman(letter3, bob);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = manager.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Removal should be successful", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from area", centralDistrict.getMailmen().contains(alice));
        assertNull("Alice's area should be null", alice.getArea());
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        assertEquals("Bob should be assigned to letter1", bob, letter1.getAssignedMailman());
        assertEquals("Bob should be assigned to parcel2", bob, parcel2.getAssignedMailman());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertEquals("Bob should still be assigned to parcel1", bob, parcel1.getAssignedMailman());
        assertEquals("Bob should still be assigned to letter3", bob, letter3.getAssignedMailman());
        
        // Count Bob's deliveries
        List<RegisteredMail> bobDeliveries = manager.getDeliveriesForArea(centralDistrict);
        int bobCount = 0;
        for (RegisteredMail mail : bobDeliveries) {
            if (mail.getAssignedMailman().equals(bob)) {
                bobCount++;
            }
        }
        assertEquals("Bob should have 4 deliveries", 4, bobCount);
        
        // Charlie's assignments unchanged (now has 1 item)
        assertEquals("Charlie should still be assigned to letter2", charlie, letter2.getAssignedMailman());
        
        List<RegisteredMail> charlieDeliveries = manager.getDeliveriesForArea(centralDistrict);
        int charlieCount = 0;
        for (RegisteredMail mail : charlieDeliveries) {
            if (mail.getAssignedMailman().equals(charlie)) {
                charlieCount++;
            }
        }
        assertEquals("Charlie should have 1 delivery", 1, charlieCount);
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        GeographicalArea northQuarter = new GeographicalArea("NorthQuarter");
        
        Mailman xavier = new Mailman("M1", "Xavier");
        Mailman yvonne = new Mailman("M2", "Yvonne");
        Mailman zack = new Mailman("M3", "Zack");
        
        Inhabitant walter = new Inhabitant("I1", "Walter");
        
        // Add mailmen to area
        manager.addMailman(xavier, northQuarter);
        manager.addMailman(yvonne, northQuarter);
        manager.addMailman(zack, northQuarter);
        
        // Add inhabitant to area
        manager.addInhabitant(walter, northQuarter);
        
        // Create and assign 5 Letters (Xavier→Walter)
        for (int i = 1; i <= 5; i++) {
            Letter letter = new Letter("LX" + i, walter);
            manager.assignMailman(letter, xavier);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        for (int i = 1; i <= 3; i++) {
            Parcel parcel = new Parcel("PY" + i, walter);
            manager.assignMailman(parcel, yvonne);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        for (int i = 1; i <= 2; i++) {
            Letter letter = new Letter("LZ" + i, walter);
            manager.assignMailman(letter, zack);
        }
        
        // Action 1: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = manager.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal should be successful", result1);
        
        // Verifications after first removal
        assertFalse("Yvonne should be removed from area", northQuarter.getMailmen().contains(yvonne));
        assertNull("Yvonne's area should be null", yvonne.getArea());
        
        // 3 parcels moved to Xavier (now has 8 items)
        List<RegisteredMail> deliveries = manager.getDeliveriesForArea(northQuarter);
        int xavierCount = 0;
        int zackCount = 0;
        for (RegisteredMail mail : deliveries) {
            if (mail.getAssignedMailman().equals(xavier)) {
                xavierCount++;
            } else if (mail.getAssignedMailman().equals(zack)) {
                zackCount++;
            }
        }
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xavierCount);
        assertEquals("Zack should have 2 deliveries after first removal", 2, zackCount);
        
        // Action 2: Remove Xavier (specify Zack as replacement)
        boolean result2 = manager.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal should be successful", result2);
        
        // Verifications after second removal
        assertFalse("Xavier should be removed from area", northQuarter.getMailmen().contains(xavier));
        assertNull("Xavier's area should be null", xavier.getArea());
        
        // 8 letters moved to Zack (now has 10 items)
        deliveries = manager.getDeliveriesForArea(northQuarter);
        zackCount = 0;
        for (RegisteredMail mail : deliveries) {
            if (mail.getAssignedMailman().equals(zack)) {
                zackCount++;
            }
        }
        assertEquals("Zack should have 10 deliveries after second removal", 10, zackCount);
        
        // Final state: Only Zack remains with all 10 deliveries
        assertEquals("Only Zack should remain in area", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be in area", northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        GeographicalArea southEnd = new GeographicalArea("SouthEnd");
        
        Mailman paul = new Mailman("M1", "Paul");
        Mailman quinn = new Mailman("M2", "Quinn");
        
        Inhabitant rachel = new Inhabitant("I1", "Rachel");
        
        // Add mailmen to area
        manager.addMailman(paul, southEnd);
        manager.addMailman(quinn, southEnd);
        
        // Add inhabitant to area
        manager.addInhabitant(rachel, southEnd);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter("L1", rachel);
        manager.assignMailman(letter1, paul);
        
        // Action 1: Attempt remove Paul specifying Quinn
        try {
            boolean result1 = manager.removeMailman(paul, quinn);
            // Expected Output: true (normal case)
            assertTrue("First removal should be successful", result1);
        } catch (IllegalArgumentException e) {
            fail("First removal should not throw exception");
        }
        
        // Action 2: Attempt remove Quinn (last mailman)
        try {
            boolean result2 = manager.removeMailman(quinn, paul); // paul is no longer in area
            // This should not reach here due to exception
            fail("Should have thrown exception when removing last mailman");
        } catch (IllegalArgumentException e) {
            // Expected behavior - should throw exception
            assertTrue("Exception message should indicate insufficient mailmen", 
                       e.getMessage().contains("at least one mailman"));
        }
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        GeographicalArea eastHaven = new GeographicalArea("EastHaven");
        
        Mailman mario = new Mailman("M1", "Mario");
        Mailman luigi = new Mailman("M2", "Luigi");
        Mailman toad = new Mailman("M3", "Toad");
        
        Inhabitant peach = new Inhabitant("I1", "Peach");
        Inhabitant bowser = new Inhabitant("I2", "Bowser");
        
        // Add initial mailmen to area
        manager.addMailman(mario, eastHaven);
        manager.addMailman(luigi, eastHaven);
        
        // Add inhabitants to area
        manager.addInhabitant(peach, eastHaven);
        manager.addInhabitant(bowser, eastHaven);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter("L" + i, peach);
            manager.assignMailman(letter, mario);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 1; i <= 5; i++) {
            Parcel parcel = new Parcel("P" + i, bowser);
            manager.assignMailman(parcel, luigi);
        }
        
        // Procedure 1: Add duplicate "Mario" → false
        Mailman marioDuplicate = new Mailman("M1", "Mario");
        boolean addResult1 = manager.addMailman(marioDuplicate, eastHaven);
        assertFalse("Adding duplicate mailman should fail", addResult1);
        
        // Procedure 2: Remove Mario (specify Luigi) → true
        boolean removeResult1 = manager.removeMailman(mario, luigi);
        assertTrue("Removing Mario should be successful", removeResult1);
        
        // Procedure 3: Verify Luigi now has 15 deliveries
        List<RegisteredMail> deliveries = manager.getDeliveriesForArea(eastHaven);
        int luigiCount = 0;
        for (RegisteredMail mail : deliveries) {
            if (mail.getAssignedMailman().equals(luigi)) {
                luigiCount++;
            }
        }
        assertEquals("Luigi should have 15 deliveries after taking over Mario's", 15, luigiCount);
        
        // Procedure 4: Attempt remove Luigi → false (last mailman)
        try {
            manager.removeMailman(luigi, mario); // mario is no longer in area
            fail("Should have thrown exception when removing last mailman");
        } catch (IllegalArgumentException e) {
            // Expected behavior
        }
        
        // Procedure 5: Add "Toad" → true
        boolean addResult2 = manager.addMailman(toad, eastHaven);
        assertTrue("Adding Toad should be successful", addResult2);
        
        // Procedure 6: Remove Luigi (specify Toad) → true
        boolean removeResult2 = manager.removeMailman(luigi, toad);
        assertTrue("Removing Luigi should be successful", removeResult2);
        
        // Verify final state
        List<RegisteredMail> finalDeliveries = manager.getDeliveriesForArea(eastHaven);
        int toadCount = 0;
        for (RegisteredMail mail : finalDeliveries) {
            if (mail.getAssignedMailman().equals(toad)) {
                toadCount++;
            }
        }
        assertEquals("Toad should have 15 deliveries", 15, toadCount);
        assertEquals("Only Toad should remain in area", 1, eastHaven.getMailmen().size());
        assertTrue("Toad should be in area", eastHaven.getMailmen().contains(toad));
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        GeographicalArea westRidge = new GeographicalArea("WestRidge");
        
        Mailman alpha = new Mailman("M1", "Alpha");
        Mailman beta = new Mailman("M2", "Beta");
        Mailman gamma = new Mailman("M3", "Gamma");
        
        // Add mailmen to area
        manager.addMailman(alpha, westRidge);
        manager.addMailman(beta, westRidge);
        manager.addMailman(gamma, westRidge);
        
        // Add 10 inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant("I" + (i + 1), "Inhabitant" + (i + 1));
            manager.addInhabitant(inhabitants[i], westRidge);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCounter = 1;
        
        // 10 items for Alpha
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter("L" + mailCounter++, inhabitants[i % 10]);
            manager.assignMailman(letter, alpha);
        }
        
        // 10 items for Beta
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel("P" + mailCounter++, inhabitants[i % 10]);
            manager.assignMailman(parcel, beta);
        }
        
        // 10 items for Gamma
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter("L" + mailCounter++, inhabitants[i % 10]);
            manager.assignMailman(letter, gamma);
        }
        
        // Action 1: Remove Alpha (specify Beta) → true
        boolean result1 = manager.removeMailman(alpha, beta);
        assertTrue("First removal should be successful", result1);
        
        // Action 2: Remove Beta (specify Gamma) → true
        boolean result2 = manager.removeMailman(beta, gamma);
        assertTrue("Second removal should be successful", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> deliveries = manager.getDeliveriesForArea(westRidge);
        int gammaCount = 0;
        for (RegisteredMail mail : deliveries) {
            if (mail.getAssignedMailman().equals(gamma)) {
                gammaCount++;
            }
        }
        assertEquals("Gamma should have all 30 deliveries", 30, gammaCount);
        
        // Verify only Gamma remains
        assertEquals("Only Gamma should remain in area", 1, westRidge.getMailmen().size());
        assertTrue("Gamma should be in area", westRidge.getMailmen().contains(gamma));
    }
}