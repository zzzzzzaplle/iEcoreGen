import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    private Area area;
    private Mailman alice, bob, charlie, xavier, yvonne, zack, paul, quinn, mario, luigi, toad, alpha, beta, gamma;
    private Inhabitant david, eve, frank, walter, rachel, peach, bowser;
    
    @Before
    public void setUp() {
        area = new Area();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp
        area.setName("CentralDistrict");
        
        alice = new Mailman();
        alice.setName("Alice");
        bob = new Mailman();
        bob.setName("Bob");
        charlie = new Mailman();
        charlie.setName("Charlie");
        
        area.addMailman(alice);
        area.addMailman(bob);
        area.addMailman(charlie);
        
        david = new Inhabitant();
        david.setName("David");
        eve = new Inhabitant();
        eve.setName("Eve");
        frank = new Inhabitant();
        frank.setName("Frank");
        
        area.addInhabitant(david);
        area.addInhabitant(eve);
        area.addInhabitant(frank);
        
        Letter letter1 = new Letter();
        letter1.setAddressee(david);
        letter1.setMailman(alice);
        
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(eve);
        parcel1.setMailman(bob);
        
        Letter letter2 = new Letter();
        letter2.setAddressee(frank);
        letter2.setMailman(charlie);
        
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(eve);
        parcel2.setMailman(alice);
        
        Letter letter3 = new Letter();
        letter3.setAddressee(david);
        letter3.setMailman(bob);
        
        area.getMails().add(letter1);
        area.getMails().add(parcel1);
        area.getMails().add(letter2);
        area.getMails().add(parcel2);
        area.getMails().add(letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = area.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Removal should be successful", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen", area.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        assertEquals("Bob should have 4 deliveries", 4, countMailForMailman(bob));
        assertTrue("Letter1 should be assigned to Bob", letter1.getMailman().equals(bob));
        assertTrue("Parcel2 should be assigned to Bob", parcel2.getMailman().equals(bob));
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Parcel1 should remain with Bob", parcel1.getMailman().equals(bob));
        assertTrue("Letter3 should remain with Bob", letter3.getMailman().equals(bob));
        
        // Charlie's assignments unchanged (now has 1 item)
        assertEquals("Charlie should have 1 delivery", 1, countMailForMailman(charlie));
        assertTrue("Letter2 should remain with Charlie", letter2.getMailman().equals(charlie));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        area.setName("NorthQuarter");
        
        xavier = new Mailman();
        xavier.setName("Xavier");
        yvonne = new Mailman();
        yvonne.setName("Yvonne");
        zack = new Mailman();
        zack.setName("Zack");
        
        area.addMailman(xavier);
        area.addMailman(yvonne);
        area.addMailman(zack);
        
        walter = new Inhabitant();
        walter.setName("Walter");
        area.addInhabitant(walter);
        
        // Create 5 Letters (Xavier→Walter)
        List<Letter> xavierLetters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setMailman(xavier);
            area.getMails().add(letter);
            xavierLetters.add(letter);
        }
        
        // Create 3 Parcels (Yvonne→Walter)
        List<Parcel> yvonneParcels = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            parcel.setMailman(yvonne);
            area.getMails().add(parcel);
            yvonneParcels.add(parcel);
        }
        
        // Create 2 Letters (Zack→Walter)
        List<Letter> zackLetters = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setMailman(zack);
            area.getMails().add(letter);
            zackLetters.add(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = area.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal should be successful", result1);
        
        // Verifications after first removal
        // 3 parcels moved to Xavier (now has 8 items)
        assertEquals("Xavier should have 8 deliveries after first removal", 8, countMailForMailman(xavier));
        for (Parcel parcel : yvonneParcels) {
            assertTrue("Parcel should be reassigned to Xavier", parcel.getMailman().equals(xavier));
        }
        
        // Zack's assignments unchanged (still 2 items)
        assertEquals("Zack should have 2 deliveries", 2, countMailForMailman(zack));
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = area.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal should be successful", result2);
        
        // Verifications after second removal
        // 8 letters moved to Zack (now has 10 items)
        assertEquals("Zack should have 10 deliveries after second removal", 10, countMailForMailman(zack));
        for (Letter letter : xavierLetters) {
            assertTrue("Letter should be reassigned to Zack", letter.getMailman().equals(zack));
        }
        for (Parcel parcel : yvonneParcels) {
            assertTrue("Parcel should be reassigned to Zack", parcel.getMailman().equals(zack));
        }
        
        // Final state: Only Zack remains with all 10 deliveries
        assertEquals("Only Zack should remain as mailman", 1, area.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", area.getMailmen().contains(zack));
        assertEquals("Zack should have all 10 deliveries", 10, countMailForMailman(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        area.setName("SouthEnd");
        
        paul = new Mailman();
        paul.setName("Paul");
        quinn = new Mailman();
        quinn.setName("Quinn");
        
        area.addMailman(paul);
        area.addMailman(quinn);
        
        rachel = new Inhabitant();
        rachel.setName("Rachel");
        area.addInhabitant(rachel);
        
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        letter1.setMailman(paul);
        area.getMails().add(letter1);
        
        // Actions & Expected Outputs
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = area.removeMailman(paul, quinn);
        assertTrue("Removing Paul with Quinn as replacement should succeed", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = area.removeMailman(quinn, paul); // Paul no longer exists, but should fail anyway
        assertFalse("Removing last mailman should fail", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        area.setName("EastHaven");
        
        mario = new Mailman();
        mario.setName("Mario");
        luigi = new Mailman();
        luigi.setName("Luigi");
        
        area.addMailman(mario);
        area.addMailman(luigi);
        
        peach = new Inhabitant();
        peach.setName("Peach");
        bowser = new Inhabitant();
        bowser.setName("Bowser");
        
        area.addInhabitant(peach);
        area.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            letter.setMailman(mario);
            area.getMails().add(letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            parcel.setMailman(luigi);
            area.getMails().add(parcel);
        }
        
        // Procedure
        // 1. Add duplicate "Mario" → false
        boolean result1 = area.addMailman(mario);
        assertFalse("Adding duplicate Mario should fail", result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = area.removeMailman(mario, luigi);
        assertTrue("Removing Mario with Luigi as replacement should succeed", result2);
        
        // 3. Verify Luigi now has 5 deliveries
        assertEquals("Luigi should have 15 deliveries after reassignment", 15, countMailForMailman(luigi));
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = area.removeMailman(luigi, mario); // Mario no longer exists, but should fail anyway
        assertFalse("Removing last mailman should fail", result3);
        
        // 5. Add "Toad" → true
        toad = new Mailman();
        toad.setName("Toad");
        boolean result4 = area.addMailman(toad);
        assertTrue("Adding Toad should succeed", result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = area.removeMailman(luigi, toad);
        assertTrue("Removing Luigi with Toad as replacement should succeed", result5);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        area.setName("WestRidge");
        
        alpha = new Mailman();
        alpha.setName("Alpha");
        beta = new Mailman();
        beta.setName("Beta");
        gamma = new Mailman();
        gamma.setName("Gamma");
        
        area.addMailman(alpha);
        area.addMailman(beta);
        area.addMailman(gamma);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setName("Inhabitant" + (i + 1));
            area.addInhabitant(inhabitant);
            inhabitants.add(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants.get(inhabitantIndex % 10));
            letter.setMailman(alpha);
            area.getMails().add(letter);
            inhabitantIndex++;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(inhabitants.get(inhabitantIndex % 10));
            parcel.setMailman(beta);
            area.getMails().add(parcel);
            inhabitantIndex++;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants.get(inhabitantIndex % 10));
            letter.setMailman(gamma);
            area.getMails().add(letter);
            inhabitantIndex++;
        }
        
        // Action
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = area.removeMailman(alpha, beta);
        assertTrue("Removing Alpha with Beta as replacement should succeed", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = area.removeMailman(beta, gamma);
        assertTrue("Removing Beta with Gamma as replacement should succeed", result2);
        
        // Verifications
        // Gamma ends with all 30 deliveries
        assertEquals("Gamma should have all 30 deliveries", 30, countMailForMailman(gamma));
        assertEquals("Only Gamma should remain as mailman", 1, area.getMailmen().size());
        assertTrue("Gamma should be the only remaining mailman", area.getMailmen().contains(gamma));
    }
    
    // Helper method to count mail assigned to a specific mailman
    private int countMailForMailman(Mailman mailman) {
        int count = 0;
        for (RegisteredMail mail : area.getMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(mailman)) {
                count++;
            }
        }
        return count;
    }
}