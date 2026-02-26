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
        // 1. Create GeographicalArea "CentralDistrict"
        area = new GeographicalArea();
        
        // 2. Add Mailmen "Alice", "Bob", "Charlie"
        Mailman alice = new Mailman("M001", "Alice");
        Mailman bob = new Mailman("M002", "Bob");
        Mailman charlie = new Mailman("M003", "Charlie");
        area.addMailman(alice);
        area.addMailman(bob);
        area.addMailman(charlie);
        
        // 3. Add Inhabitants "David", "Eve", "Frank"
        Inhabitant david = new Inhabitant("I001", "David");
        Inhabitant eve = new Inhabitant("I002", "Eve");
        Inhabitant frank = new Inhabitant("I003", "Frank");
        area.addInhabitant(david);
        area.addInhabitant(eve);
        area.addInhabitant(frank);
        
        // 4. Create and assign mail items
        Letter letter1 = new Letter(david);
        Parcel parcel1 = new Parcel(eve);
        Letter letter2 = new Letter(frank);
        Parcel parcel2 = new Parcel(eve);
        Letter letter3 = new Letter(david);
        
        area.addRegisteredMail(letter1);
        area.addRegisteredMail(parcel1);
        area.addRegisteredMail(letter2);
        area.addRegisteredMail(parcel2);
        area.addRegisteredMail(letter3);
        
        // Assign carriers
        area.assignRegisteredMailDeliver(alice, david, letter1);
        area.assignRegisteredMailDeliver(bob, eve, parcel1);
        area.assignRegisteredMailDeliver(charlie, frank, letter2);
        area.assignRegisteredMailDeliver(alice, eve, parcel2);
        area.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = area.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Removal should succeed", result);
        
        // Verifications
        // - Alice removed
        assertFalse("Alice should be removed from mailmen list", area.getMailmen().contains(alice));
        
        // - Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsDeliveries = area.listRegisteredMail(bob);
        assertNotNull("Bob should have deliveries", bobsDeliveries);
        assertEquals("Bob should have 4 deliveries", 4, bobsDeliveries.size());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob should still have Parcel1", bobsDeliveries.contains(parcel1));
        assertTrue("Bob should still have Letter3", bobsDeliveries.contains(letter3));
        
        // Verify reassignments
        assertTrue("Letter1 should be reassigned to Bob", bobsDeliveries.contains(letter1));
        assertTrue("Parcel2 should be reassigned to Bob", bobsDeliveries.contains(parcel2));
        
        // - Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesDeliveries = area.listRegisteredMail(charlie);
        assertNotNull("Charlie should have deliveries", charliesDeliveries);
        assertEquals("Charlie should have 1 delivery", 1, charliesDeliveries.size());
        assertTrue("Charlie should still have Letter2", charliesDeliveries.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        // 1. Create GeographicalArea "NorthQuarter"
        area = new GeographicalArea();
        
        // 2. Add Mailmen ["Xavier", "Yvonne", "Zack"]
        Mailman xavier = new Mailman("M001", "Xavier");
        Mailman yvonne = new Mailman("M002", "Yvonne");
        Mailman zack = new Mailman("M003", "Zack");
        area.addMailman(xavier);
        area.addMailman(yvonne);
        area.addMailman(zack);
        
        // 3. Add Inhabitant "Walter"
        Inhabitant walter = new Inhabitant("I001", "Walter");
        area.addInhabitant(walter);
        
        // 4. Create and assign mail items
        // 5 Letters (Xavier→Walter)
        Letter[] xavierLetters = new Letter[5];
        for (int i = 0; i < 5; i++) {
            xavierLetters[i] = new Letter(walter);
            area.addRegisteredMail(xavierLetters[i]);
            area.assignRegisteredMailDeliver(xavier, walter, xavierLetters[i]);
        }
        
        // 3 Parcels (Yvonne→Walter)
        Parcel[] yvonneParcels = new Parcel[3];
        for (int i = 0; i < 3; i++) {
            yvonneParcels[i] = new Parcel(walter);
            area.addRegisteredMail(yvonneParcels[i]);
            area.assignRegisteredMailDeliver(yvonne, walter, yvonneParcels[i]);
        }
        
        // 2 Letters (Zack→Walter)
        Letter[] zackLetters = new Letter[2];
        for (int i = 0; i < 2; i++) {
            zackLetters[i] = new Letter(walter);
            area.addRegisteredMail(zackLetters[i]);
            area.assignRegisteredMailDeliver(zack, walter, zackLetters[i]);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = area.removeMailman(yvonne, xavier);
        // Expected Output: true
        assertTrue("First removal should succeed", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = area.removeMailman(xavier, zack);
        // Expected Output: true
        assertTrue("Second removal should succeed", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zacksDeliveries = area.listRegisteredMail(zack);
        assertNotNull("Zack should have deliveries", zacksDeliveries);
        assertEquals("Zack should have all 10 deliveries", 10, zacksDeliveries.size());
        
        // Verify Zack is the only remaining mailman
        assertEquals("Only Zack should remain", 1, area.getMailmen().size());
        assertTrue("Zack should be in mailmen list", area.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        // 1. Create GeographicalArea "SouthEnd"
        area = new GeographicalArea();
        
        // 2. Add Mailmen ["Paul", "Quinn"]
        Mailman paul = new Mailman("M001", "Paul");
        Mailman quinn = new Mailman("M002", "Quinn");
        area.addMailman(paul);
        area.addMailman(quinn);
        
        // 3. Add Inhabitant "Rachel"
        Inhabitant rachel = new Inhabitant("I001", "Rachel");
        area.addInhabitant(rachel);
        
        // 4. Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter(rachel);
        area.addRegisteredMail(letter1);
        area.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = area.removeMailman(paul, quinn);
        assertTrue("Removal of Paul should succeed", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = area.removeMailman(quinn, paul); // paul is no longer in list, but method should fail due to being last mailman
        assertFalse("Removal of last mailman should fail", result2);
        
        // Verify Quinn is still in the list
        assertTrue("Quinn should still be in mailmen list", area.getMailmen().contains(quinn));
        assertEquals("Only Quinn should remain", 1, area.getMailmen().size());
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        // 1. Create GeographicalArea "EastHaven"
        area = new GeographicalArea();
        
        // 2. Add Mailmen ["Mario", "Luigi"]
        Mailman mario = new Mailman("M001", "Mario");
        Mailman luigi = new Mailman("M002", "Luigi");
        area.addMailman(mario);
        area.addMailman(luigi);
        
        // 3. Add Inhabitants ["Peach", "Bowser"]
        Inhabitant peach = new Inhabitant("I001", "Peach");
        Inhabitant bowser = new Inhabitant("I002", "Bowser");
        area.addInhabitant(peach);
        area.addInhabitant(bowser);
        
        // 4. Create mail items
        // 10 Letters (Mario→Peach)
        Letter[] marioLetters = new Letter[10];
        for (int i = 0; i < 10; i++) {
            marioLetters[i] = new Letter(peach);
            area.addRegisteredMail(marioLetters[i]);
            area.assignRegisteredMailDeliver(mario, peach, marioLetters[i]);
        }
        
        // 5 Parcels (Luigi→Bowser)
        Parcel[] luigiParcels = new Parcel[5];
        for (int i = 0; i < 5; i++) {
            luigiParcels[i] = new Parcel(bowser);
            area.addRegisteredMail(luigiParcels[i]);
            area.assignRegisteredMailDeliver(luigi, bowser, luigiParcels[i]);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean addResult = area.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", addResult);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeResult1 = area.removeMailman(mario, luigi);
        assertTrue("Removal of Mario should succeed", removeResult1);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiDeliveries = area.listRegisteredMail(luigi);
        assertNotNull("Luigi should have deliveries", luigiDeliveries);
        assertEquals("Luigi should have 15 deliveries", 15, luigiDeliveries.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeResult2 = area.removeMailman(luigi, mario); // mario is no longer in list
        assertFalse("Removal of last mailman should fail", removeResult2);
        
        // 5. Add "Toad" → true
        Mailman toad = new Mailman("M003", "Toad");
        boolean addToadResult = area.addMailman(toad);
        assertTrue("Adding Toad should succeed", addToadResult);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeResult3 = area.removeMailman(luigi, toad);
        assertTrue("Removal of Luigi should succeed", removeResult3);
        
        // Verify final state
        assertEquals("Only Toad should remain", 1, area.getMailmen().size());
        assertTrue("Toad should be in mailmen list", area.getMailmen().contains(toad));
        
        List<RegisteredMail> toadDeliveries = area.listRegisteredMail(toad);
        assertNotNull("Toad should have deliveries", toadDeliveries);
        assertEquals("Toad should have 15 deliveries", 15, toadDeliveries.size());
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        // 1. Create GeographicalArea "WestRidge"
        area = new GeographicalArea();
        
        // 2. Add Mailmen ["Alpha", "Beta", "Gamma"]
        Mailman alpha = new Mailman("M001", "Alpha");
        Mailman beta = new Mailman("M002", "Beta");
        Mailman gamma = new Mailman("M003", "Gamma");
        area.addMailman(alpha);
        area.addMailman(beta);
        area.addMailman(gamma);
        
        // 3. Add 10 Inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant("I00" + (i + 1), "Inhabitant" + (i + 1));
            area.addInhabitant(inhabitants[i]);
        }
        
        // 4. Create 30 mail items (10 each mailman)
        int mailCount = 0;
        
        // Alpha's 10 mail items
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter(inhabitants[i % 10]);
            area.addRegisteredMail(letter);
            area.assignRegisteredMailDeliver(alpha, inhabitants[i % 10], letter);
            mailCount++;
        }
        
        // Beta's 10 mail items
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel(inhabitants[i % 10]);
            area.addRegisteredMail(parcel);
            area.assignRegisteredMailDeliver(beta, inhabitants[i % 10], parcel);
            mailCount++;
        }
        
        // Gamma's 10 mail items
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter(inhabitants[i % 10]);
                area.addRegisteredMail(letter);
                area.assignRegisteredMailDeliver(gamma, inhabitants[i % 10], letter);
            } else {
                Parcel parcel = new Parcel(inhabitants[i % 10]);
                area.addRegisteredMail(parcel);
                area.assignRegisteredMailDeliver(gamma, inhabitants[i % 10], parcel);
            }
            mailCount++;
        }
        
        assertEquals("Should have 30 mail items total", 30, mailCount);
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = area.removeMailman(alpha, beta);
        assertTrue("First removal should succeed", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = area.removeMailman(beta, gamma);
        assertTrue("Second removal should succeed", result2);
        
        // Verifications:
        // - Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaDeliveries = area.listRegisteredMail(gamma);
        assertNotNull("Gamma should have deliveries", gammaDeliveries);
        assertEquals("Gamma should have all 30 deliveries", 30, gammaDeliveries.size());
        
        // Verify Gamma is the only remaining mailman
        assertEquals("Only Gamma should remain", 1, area.getMailmen().size());
        assertTrue("Gamma should be in mailmen list", area.getMailmen().contains(gamma));
    }
}