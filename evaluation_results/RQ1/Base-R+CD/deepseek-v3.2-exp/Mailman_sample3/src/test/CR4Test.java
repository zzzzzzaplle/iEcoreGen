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
        // Initialize test objects for all test cases
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
        
        // Mailmen
        alice = new Mailman(); alice.setName("Alice");
        bob = new Mailman(); bob.setName("Bob");
        charlie = new Mailman(); charlie.setName("Charlie");
        xavier = new Mailman(); xavier.setName("Xavier");
        yvonne = new Mailman(); yvonne.setName("Yvonne");
        zack = new Mailman(); zack.setName("Zack");
        paul = new Mailman(); paul.setName("Paul");
        quinn = new Mailman(); quinn.setName("Quinn");
        mario = new Mailman(); mario.setName("Mario");
        luigi = new Mailman(); luigi.setName("Luigi");
        toad = new Mailman(); toad.setName("Toad");
        alpha = new Mailman(); alpha.setName("Alpha");
        beta = new Mailman(); beta.setName("Beta");
        gamma = new Mailman(); gamma.setName("Gamma");
        
        // Inhabitants
        david = new Inhabitant(); david.setName("David");
        eve = new Inhabitant(); eve.setName("Eve");
        frank = new Inhabitant(); frank.setName("Frank");
        walter = new Inhabitant(); walter.setName("Walter");
        rachel = new Inhabitant(); rachel.setName("Rachel");
        peach = new Inhabitant(); peach.setName("Peach");
        bowser = new Inhabitant(); bowser.setName("Bowser");
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
        
        // Create mail items
        Letter letter1 = new Letter();
        Parcel parcel1 = new Parcel();
        Letter letter2 = new Letter();
        Parcel parcel2 = new Parcel();
        Letter letter3 = new Letter();
        
        centralDistrict.getAllMails().add(letter1);
        centralDistrict.getAllMails().add(parcel1);
        centralDistrict.getAllMails().add(letter2);
        centralDistrict.getAllMails().add(parcel2);
        centralDistrict.getAllMails().add(letter3);
        
        // Assign mail items
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
        assertFalse("Alice should be removed from mailmen", centralDistrict.getMailmen().contains(alice));
        assertNull("Alice's area should be set to null", alice.getArea());
        
        // Verify Bob's assignments (should have 4 items: Parcel1, Letter3, Letter1, Parcel2)
        List<RegisteredMail> bobMails = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have mail assignments", bobMails);
        assertEquals("Bob should have 4 mail items", 4, bobMails.size());
        assertTrue("Bob should have Parcel1", bobMails.contains(parcel1));
        assertTrue("Bob should have Letter3", bobMails.contains(letter3));
        assertTrue("Bob should have Letter1 (reassigned)", bobMails.contains(letter1));
        assertTrue("Bob should have Parcel2 (reassigned)", bobMails.contains(parcel2));
        
        // Verify Charlie's assignments (should have 1 item: Letter2)
        List<RegisteredMail> charlieMails = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have mail assignments", charlieMails);
        assertEquals("Charlie should have 1 mail item", 1, charlieMails.size());
        assertTrue("Charlie should have Letter2", charlieMails.contains(letter2));
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
            northQuarter.getAllMails().add(letter);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            northQuarter.getAllMails().add(parcel);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            northQuarter.getAllMails().add(letter);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        assertTrue("Yvonne should be successfully removed", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        assertTrue("Xavier should be successfully removed", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zackMails = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have mail assignments", zackMails);
        assertEquals("Zack should have all 10 mail items", 10, zackMails.size());
        
        // Verify only Zack remains as mailman
        assertEquals("Only Zack should remain as mailman", 1, northQuarter.getMailmen().size());
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
        southEnd.getAllMails().add(letter1);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("Paul should be successfully removed", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is already removed, but the method should fail due to being last mailman
        assertFalse("Quinn should not be removed (last mailman)", result2);
        
        // Verify Quinn is still in the system
        assertTrue("Quinn should still be a mailman", southEnd.getMailmen().contains(quinn));
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
            eastHaven.getAllMails().add(letter);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            eastHaven.getAllMails().add(parcel);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // 1. Add duplicate "Mario" → false
        boolean result1 = eastHaven.addMailman(mario);
        assertFalse("Should not be able to add duplicate Mario", result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        assertTrue("Mario should be successfully removed", result2);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMails = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have mail assignments", luigiMails);
        assertEquals("Luigi should have 15 mail items", 15, luigiMails.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // mario is already removed
        assertFalse("Luigi should not be removed (last mailman)", result3);
        
        // 5. Add "Toad" → true
        boolean result4 = eastHaven.addMailman(toad);
        assertTrue("Toad should be successfully added", result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Luigi should be successfully removed", result5);
        
        // Verify Toad has all 15 deliveries
        List<RegisteredMail> toadMails = eastHaven.listRegisteredMail(toad);
        assertNotNull("Toad should have mail assignments", toadMails);
        assertEquals("Toad should have 15 mail items", 15, toadMails.size());
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
            inhabitants[i].setName("Inhabitant" + (i + 1));
            westRidge.addInhabitant(inhabitants[i]);
        }
        
        // Create 30 mail items (10 each mailman)
        int inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.getAllMails().add(letter);
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants[inhabitantIndex], letter);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            westRidge.getAllMails().add(parcel);
            westRidge.assignRegisteredMailDeliver(beta, inhabitants[inhabitantIndex], parcel);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.getAllMails().add(letter);
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants[inhabitantIndex], letter);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Action: 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("Alpha should be successfully removed", result1);
        
        // Action: 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Beta should be successfully removed", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaMails = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have mail assignments", gammaMails);
        assertEquals("Gamma should have all 30 mail items", 30, gammaMails.size());
        
        // Verify only Gamma remains as mailman
        assertEquals("Only Gamma should remain as mailman", 1, westRidge.getMailmen().size());
        assertTrue("Gamma should be the remaining mailman", westRidge.getMailmen().contains(gamma));
    }
}