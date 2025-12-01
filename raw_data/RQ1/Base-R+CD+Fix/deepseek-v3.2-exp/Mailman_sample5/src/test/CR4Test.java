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
        // Initialize geographical areas for tests
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        centralDistrict = new GeographicalArea();
        
        // Add Mailmen "Alice", "Bob", "Charlie"
        Mailman alice = new Mailman();
        alice.setId("M001");
        alice.setName("Alice");
        centralDistrict.addMailman(alice);
        
        Mailman bob = new Mailman();
        bob.setId("M002");
        bob.setName("Bob");
        centralDistrict.addMailman(bob);
        
        Mailman charlie = new Mailman();
        charlie.setId("M003");
        charlie.setName("Charlie");
        centralDistrict.addMailman(charlie);
        
        // Add Inhabitants "David", "Eve", "Frank"
        Inhabitant david = new Inhabitant();
        david.setId("I001");
        david.setName("David");
        centralDistrict.addInhabitant(david);
        
        Inhabitant eve = new Inhabitant();
        eve.setId("I002");
        eve.setName("Eve");
        centralDistrict.addInhabitant(eve);
        
        Inhabitant frank = new Inhabitant();
        frank.setId("I003");
        frank.setName("Frank");
        centralDistrict.addInhabitant(frank);
        
        // Create and assign mail items
        Letter letter1 = new Letter();
        letter1.setTrackingNumber("L001");
        letter1.setWeight(0.1);
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        
        Parcel parcel1 = new Parcel();
        parcel1.setTrackingNumber("P001");
        parcel1.setWeight(2.5);
        parcel1.setDimensions(30.0);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        
        Letter letter2 = new Letter();
        letter2.setTrackingNumber("L002");
        letter2.setWeight(0.2);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        
        Parcel parcel2 = new Parcel();
        parcel2.setTrackingNumber("P002");
        parcel2.setWeight(1.8);
        parcel2.setDimensions(25.0);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        
        Letter letter3 = new Letter();
        letter3.setTrackingNumber("L003");
        letter3.setWeight(0.15);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Alice removal should be successful", result);
        
        // Verifications:
        // - Alice removed
        assertFalse("Alice should be removed from mailmen", centralDistrict.getMailmen().contains(alice));
        
        // - Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsMail = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have mail assignments", bobsMail);
        assertEquals("Bob should have 4 mail items", 4, bobsMail.size());
        
        // - Bob's original deliveries (Parcel1, Letter3) unchanged
        boolean hasParcel1 = false;
        boolean hasLetter3 = false;
        boolean hasLetter1 = false;
        boolean hasParcel2 = false;
        
        for (RegisteredMail mail : bobsMail) {
            if (mail.getTrackingNumber().equals("P001")) hasParcel1 = true;
            if (mail.getTrackingNumber().equals("L003")) hasLetter3 = true;
            if (mail.getTrackingNumber().equals("L001")) hasLetter1 = true;
            if (mail.getTrackingNumber().equals("P002")) hasParcel2 = true;
        }
        
        assertTrue("Bob should still have Parcel1", hasParcel1);
        assertTrue("Bob should still have Letter3", hasLetter3);
        assertTrue("Bob should have reassigned Letter1", hasLetter1);
        assertTrue("Bob should have reassigned Parcel2", hasParcel2);
        
        // - Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesMail = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have mail assignments", charliesMail);
        assertEquals("Charlie should have 1 mail item", 1, charliesMail.size());
        assertEquals("Charlie should still have Letter2", "L002", charliesMail.get(0).getTrackingNumber());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        northQuarter = new GeographicalArea();
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        Mailman xavier = new Mailman();
        xavier.setId("M101");
        xavier.setName("Xavier");
        northQuarter.addMailman(xavier);
        
        Mailman yvonne = new Mailman();
        yvonne.setId("M102");
        yvonne.setName("Yvonne");
        northQuarter.addMailman(yvonne);
        
        Mailman zack = new Mailman();
        zack.setId("M103");
        zack.setName("Zack");
        northQuarter.addMailman(zack);
        
        // Add Inhabitant "Walter"
        Inhabitant walter = new Inhabitant();
        walter.setId("I101");
        walter.setName("Walter");
        northQuarter.addInhabitant(walter);
        
        // Create and assign: 5 Letters (Xavier→Walter), 3 Parcels (Yvonne→Walter), 2 Letters (Zack→Walter)
        for (int i = 1; i <= 5; i++) {
            Letter letter = new Letter();
            letter.setTrackingNumber("XL" + i);
            letter.setWeight(0.1 * i);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        for (int i = 1; i <= 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setTrackingNumber("YP" + i);
            parcel.setWeight(2.0 * i);
            parcel.setDimensions(20.0 * i);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        for (int i = 1; i <= 2; i++) {
            Letter letter = new Letter();
            letter.setTrackingNumber("ZL" + i);
            letter.setWeight(0.15 * i);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("Yvonne removal should be successful", result1);
        
        // Action: then remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Xavier removal should be successful", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zacksMail = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have mail assignments", zacksMail);
        assertEquals("Zack should have all 10 deliveries", 10, zacksMail.size());
        
        // Verify mailmen list
        assertEquals("Should have 1 mailman remaining", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the remaining mailman", northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        southEnd = new GeographicalArea();
        
        // Add Mailmen ["Paul", "Quinn"]
        Mailman paul = new Mailman();
        paul.setId("M201");
        paul.setName("Paul");
        southEnd.addMailman(paul);
        
        Mailman quinn = new Mailman();
        quinn.setId("M202");
        quinn.setName("Quinn");
        southEnd.addMailman(quinn);
        
        // Add Inhabitant "Rachel"
        Inhabitant rachel = new Inhabitant();
        rachel.setId("I201");
        rachel.setName("Rachel");
        southEnd.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        letter1.setTrackingNumber("L201");
        letter1.setWeight(0.1);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("Paul removal should be successful", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in the system
        
        // Expected Output: false
        assertFalse("Removing last mailman should fail", result2);
        
        // Verify Quinn is still in the system
        assertTrue("Quinn should still be in mailmen list", southEnd.getMailmen().contains(quinn));
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        eastHaven = new GeographicalArea();
        
        // Add Mailmen ["Mario", "Luigi"]
        Mailman mario = new Mailman();
        mario.setId("M301");
        mario.setName("Mario");
        eastHaven.addMailman(mario);
        
        Mailman luigi = new Mailman();
        luigi.setId("M302");
        luigi.setName("Luigi");
        eastHaven.addMailman(luigi);
        
        // Add Inhabitants ["Peach", "Bowser"]
        Inhabitant peach = new Inhabitant();
        peach.setId("I301");
        peach.setName("Peach");
        eastHaven.addInhabitant(peach);
        
        Inhabitant bowser = new Inhabitant();
        bowser.setId("I302");
        bowser.setName("Bowser");
        eastHaven.addInhabitant(bowser);
        
        // Create: 10 Letters (Mario→Peach), 5 Parcels (Luigi→Bowser)
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter();
            letter.setTrackingNumber("ML" + i);
            letter.setWeight(0.1 * i);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        for (int i = 1; i <= 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setTrackingNumber("LP" + i);
            parcel.setWeight(1.5 * i);
            parcel.setDimensions(15.0 * i);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        Mailman marioDuplicate = new Mailman();
        marioDuplicate.setId("M301"); // Same ID
        marioDuplicate.setName("Mario");
        boolean result1 = eastHaven.addMailman(marioDuplicate);
        assertFalse("Adding duplicate Mario should fail", result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        assertTrue("Mario removal should be successful", result2);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisMail = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have mail assignments", luigisMail);
        assertEquals("Luigi should have 15 deliveries", 15, luigisMail.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // mario is no longer in the system
        
        // Expected Output: false
        assertFalse("Removing last mailman should fail", result3);
        
        // 5. Add "Toad" → true
        Mailman toad = new Mailman();
        toad.setId("M303");
        toad.setName("Toad");
        boolean result4 = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should be successful", result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Luigi removal should be successful", result5);
        
        // Final verification
        assertEquals("Should have 1 mailman remaining", 1, eastHaven.getMailmen().size());
        assertTrue("Toad should be the remaining mailman", eastHaven.getMailmen().contains(toad));
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        westRidge = new GeographicalArea();
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        Mailman alpha = new Mailman();
        alpha.setId("M401");
        alpha.setName("Alpha");
        westRidge.addMailman(alpha);
        
        Mailman beta = new Mailman();
        beta.setId("M402");
        beta.setName("Beta");
        westRidge.addMailman(beta);
        
        Mailman gamma = new Mailman();
        gamma.setId("M403");
        gamma.setName("Gamma");
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setId("I40" + i);
            inhabitant.setName("Inhabitant" + i);
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCount = 0;
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter();
            letter.setTrackingNumber("AL" + i);
            letter.setWeight(0.1);
            westRidge.assignRegisteredMailDeliver(alpha, westRidge.getInhabitants().get(i-1), letter);
            mailCount++;
        }
        
        for (int i = 1; i <= 10; i++) {
            Parcel parcel = new Parcel();
            parcel.setTrackingNumber("BP" + i);
            parcel.setWeight(2.0);
            parcel.setDimensions(25.0);
            westRidge.assignRegisteredMailDeliver(beta, westRidge.getInhabitants().get(i-1), parcel);
            mailCount++;
        }
        
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter();
            letter.setTrackingNumber("GL" + i);
            letter.setWeight(0.15);
            westRidge.assignRegisteredMailDeliver(gamma, westRidge.getInhabitants().get(i-1), letter);
            mailCount++;
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("Alpha removal should be successful", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Beta removal should be successful", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasMail = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have mail assignments", gammasMail);
        assertEquals("Gamma should have all 30 deliveries", 30, gammasMail.size());
        
        // Verify mailmen list
        assertEquals("Should have 1 mailman remaining", 1, westRidge.getMailmen().size());
        assertTrue("Gamma should be the remaining mailman", westRidge.getMailmen().contains(gamma));
        
        // Verify total mail count
        assertEquals("Total mail count should remain 30", 30, westRidge.getAllMails().size());
    }
}