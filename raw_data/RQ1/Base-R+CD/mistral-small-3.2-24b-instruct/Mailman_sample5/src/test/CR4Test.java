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
        // Initialize all test objects
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
        
        // Create mailmen
        alice = new Mailman("M001", "Alice");
        bob = new Mailman("M002", "Bob");
        charlie = new Mailman("M003", "Charlie");
        xavier = new Mailman("M004", "Xavier");
        yvonne = new Mailman("M005", "Yvonne");
        zack = new Mailman("M006", "Zack");
        paul = new Mailman("M007", "Paul");
        quinn = new Mailman("M008", "Quinn");
        mario = new Mailman("M009", "Mario");
        luigi = new Mailman("M010", "Luigi");
        toad = new Mailman("M011", "Toad");
        alpha = new Mailman("M012", "Alpha");
        beta = new Mailman("M013", "Beta");
        gamma = new Mailman("M014", "Gamma");
        
        // Create inhabitants
        david = new Inhabitant("I001", "David");
        eve = new Inhabitant("I002", "Eve");
        frank = new Inhabitant("I003", "Frank");
        walter = new Inhabitant("I004", "Walter");
        rachel = new Inhabitant("I005", "Rachel");
        peach = new Inhabitant("I006", "Peach");
        bowser = new Inhabitant("I007", "Bowser");
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        // Add Mailmen "Alice", "Bob", "Charlie"
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add Inhabitants "David", "Eve", "Frank"
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create and assign mail items
        Letter letter1 = new Letter(null, david);
        Parcel parcel1 = new Parcel(null, eve);
        Letter letter2 = new Letter(null, frank);
        Parcel parcel2 = new Parcel(null, eve);
        Letter letter3 = new Letter(null, david);
        
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Removing Alice should return true", result);
        
        // Verifications:
        // Alice removed
        assertFalse("Alice should be removed from mailmen list", 
                   centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsMail = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have mail items", bobsMail);
        assertEquals("Bob should have 4 mail items after reassignment", 4, bobsMail.size());
        
        // Verify Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob should still have Parcel1", bobsMail.contains(parcel1));
        assertTrue("Bob should still have Letter3", bobsMail.contains(letter3));
        
        // Verify reassigned items
        assertTrue("Bob should have reassigned Letter1", bobsMail.contains(letter1));
        assertTrue("Bob should have reassigned Parcel2", bobsMail.contains(parcel2));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesMail = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have mail items", charliesMail);
        assertEquals("Charlie should have 1 mail item", 1, charliesMail.size());
        assertTrue("Charlie should have Letter2", charliesMail.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add Inhabitant "Walter"
        northQuarter.addInhabitant(walter);
        
        // Create and assign mail items
        Letter[] xavierLetters = new Letter[5];
        Parcel[] yvonneParcels = new Parcel[3];
        Letter[] zackLetters = new Letter[2];
        
        for (int i = 0; i < 5; i++) {
            xavierLetters[i] = new Letter(null, walter);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, xavierLetters[i]);
        }
        
        for (int i = 0; i < 3; i++) {
            yvonneParcels[i] = new Parcel(null, walter);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, yvonneParcels[i]);
        }
        
        for (int i = 0; i < 2; i++) {
            zackLetters[i] = new Letter(null, walter);
            northQuarter.assignRegisteredMailDeliver(zack, walter, zackLetters[i]);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("Removing Yvonne should return true", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Removing Xavier should return true", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zacksMail = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have mail items", zacksMail);
        assertEquals("Zack should have all 10 deliveries", 10, zacksMail.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        // Add Mailmen ["Paul", "Quinn"]
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add Inhabitant "Rachel"
        southEnd.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter(null, rachel);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("Removing Paul should return true", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in the system
        assertFalse("Removing last mailman should return false", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        // Add Mailmen ["Mario", "Luigi"]
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add Inhabitants ["Peach", "Bowser"]
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create mail items
        Letter[] marioLetters = new Letter[10];
        Parcel[] luigiParcels = new Parcel[5];
        
        for (int i = 0; i < 10; i++) {
            marioLetters[i] = new Letter(null, peach);
            eastHaven.assignRegisteredMailDeliver(mario, peach, marioLetters[i]);
        }
        
        for (int i = 0; i < 5; i++) {
            luigiParcels[i] = new Parcel(null, bowser);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, luigiParcels[i]);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean result1 = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate Mario should return false", result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removing Mario should return true", result2);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisMail = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have mail items", luigisMail);
        assertEquals("Luigi should have 15 deliveries after reassignment", 15, luigisMail.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // mario is no longer in the system
        assertFalse("Removing last mailman should return false", result3);
        
        // 5. Add "Toad" → true
        boolean result4 = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should return true", result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removing Luigi should return true", result5);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant("I" + (100 + i), "Inhabitant" + i);
            westRidge.addInhabitant(inhabitants[i]);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCounter = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                Mailman currentMailman = (j == 0) ? alpha : (j == 1) ? beta : gamma;
                if (j == 0) {
                    Letter letter = new Letter(null, inhabitants[i]);
                    westRidge.assignRegisteredMailDeliver(currentMailman, inhabitants[i], letter);
                } else {
                    Parcel parcel = new Parcel(null, inhabitants[i]);
                    westRidge.assignRegisteredMailDeliver(currentMailman, inhabitants[i], parcel);
                }
                mailCounter++;
            }
        }
        
        assertEquals("Should create exactly 30 mail items", 30, mailCounter);
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("Removing Alpha should return true", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Removing Beta should return true", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasMail = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have mail items", gammasMail);
        assertEquals("Gamma should have all 30 deliveries", 30, gammasMail.size());
    }
}