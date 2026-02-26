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
        
        // Add Mailmen "Alice", "Bob", "Charlie"
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add Inhabitants "David", "Eve", "Frank"
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create and assign mail items
        Letter letter1 = new Letter();
        letter1.setAddressee(david);
        centralDistrict.getAllMails().add(letter1);
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(eve);
        centralDistrict.getAllMails().add(parcel1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        
        Letter letter2 = new Letter();
        letter2.setAddressee(frank);
        centralDistrict.getAllMails().add(letter2);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(eve);
        centralDistrict.getAllMails().add(parcel2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        
        Letter letter3 = new Letter();
        letter3.setAddressee(david);
        centralDistrict.getAllMails().add(letter3);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Alice removal should succeed", result);
        
        // Verifications:
        // Alice removed
        assertFalse("Alice should be removed from mailmen list", centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsMails = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have mail assignments", bobsMails);
        assertEquals("Bob should have 4 mail items", 4, bobsMails.size());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob should still have Parcel1", bobsMails.contains(parcel1));
        assertTrue("Bob should still have Letter3", bobsMails.contains(letter3));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesMails = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have mail assignments", charliesMails);
        assertEquals("Charlie should have 1 mail item", 1, charliesMails.size());
        assertTrue("Charlie should still have Letter2", charliesMails.contains(letter2));
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
        
        // Create and assign 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            northQuarter.getAllMails().add(letter);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            northQuarter.getAllMails().add(parcel);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            northQuarter.getAllMails().add(letter);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        // Expected Output: true
        assertTrue("Yvonne removal should succeed", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        // Expected Output: true
        assertTrue("Xavier removal should succeed", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zacksMails = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have mail assignments", zacksMails);
        assertEquals("Zack should have all 10 deliveries", 10, zacksMails.size());
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
        letter1.setAddressee(rachel);
        southEnd.getAllMails().add(letter1);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("Paul removal should succeed", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in the area
        assertFalse("Quinn removal should fail (last mailman)", result2);
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
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            eastHaven.getAllMails().add(letter);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            eastHaven.getAllMails().add(parcel);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean result1 = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate Mario should fail", result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        assertTrue("Mario removal should succeed", result2);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisMails = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have mail assignments", luigisMails);
        assertEquals("Luigi should have 15 deliveries", 15, luigisMails.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // mario is no longer in the area
        assertFalse("Luigi removal should fail (last mailman)", result3);
        
        // 5. Add "Toad" → true
        boolean result4 = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should succeed", result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Luigi removal should succeed", result5);
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
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant();
            westRidge.addInhabitant(inhabitants[i]);
        }
        
        // Create 30 mail items (10 each mailman)
        int inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants[inhabitantIndex % 10]);
            westRidge.getAllMails().add(letter);
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants[inhabitantIndex % 10], letter);
            inhabitantIndex++;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(inhabitants[inhabitantIndex % 10]);
            westRidge.getAllMails().add(parcel);
            westRidge.assignRegisteredMailDeliver(beta, inhabitants[inhabitantIndex % 10], parcel);
            inhabitantIndex++;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants[inhabitantIndex % 10]);
            westRidge.getAllMails().add(letter);
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants[inhabitantIndex % 10], letter);
            inhabitantIndex++;
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("Alpha removal should succeed", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Beta removal should succeed", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasMails = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have mail assignments", gammasMails);
        assertEquals("Gamma should have all 30 deliveries", 30, gammasMails.size());
    }
}