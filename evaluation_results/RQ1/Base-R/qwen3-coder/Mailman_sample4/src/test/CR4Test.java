import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    private Area area;
    
    @Before
    public void setUp() {
        area = new Area();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        area.setName("CentralDistrict");
        
        // Add Mailmen ["Alice", "Bob", "Charlie"]
        Mailman alice = new Mailman();
        alice.setName("Alice");
        Mailman bob = new Mailman();
        bob.setName("Bob");
        Mailman charlie = new Mailman();
        charlie.setName("Charlie");
        
        area.addMailman(alice);
        area.addMailman(bob);
        area.addMailman(charlie);
        
        // Add Inhabitants ["David", "Eve", "Frank"]
        Inhabitant david = new Inhabitant();
        david.setName("David");
        Inhabitant eve = new Inhabitant();
        eve.setName("Eve");
        Inhabitant frank = new Inhabitant();
        frank.setName("Frank");
        
        area.addInhabitant(david);
        area.addInhabitant(eve);
        area.addInhabitant(frank);
        
        // Create and assign mail items
        // Letter1 (Alice→David)
        Letter letter1 = new Letter();
        letter1.setAddressee(david);
        area.getRegisteredMails().add(letter1);
        area.assignMailToMailman(letter1, alice, david);
        
        // Parcel1 (Bob→Eve)
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(eve);
        area.getRegisteredMails().add(parcel1);
        area.assignMailToMailman(parcel1, bob, eve);
        
        // Letter2 (Charlie→Frank)
        Letter letter2 = new Letter();
        letter2.setAddressee(frank);
        area.getRegisteredMails().add(letter2);
        area.assignMailToMailman(letter2, charlie, frank);
        
        // Parcel2 (Alice→Eve)
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(eve);
        area.getRegisteredMails().add(parcel2);
        area.assignMailToMailman(parcel2, alice, eve);
        
        // Letter3 (Bob→David)
        Letter letter3 = new Letter();
        letter3.setAddressee(david);
        area.getRegisteredMails().add(letter3);
        area.assignMailToMailman(letter3, bob, david);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = area.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Removal should be successful", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen", area.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        assertEquals("Letter1 should be reassigned to Bob", bob, letter1.getMailman());
        assertEquals("Parcel2 should be reassigned to Bob", bob, parcel2.getMailman());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertEquals("Parcel1 should still be assigned to Bob", bob, parcel1.getMailman());
        assertEquals("Letter3 should still be assigned to Bob", bob, letter3.getMailman());
        
        // Charlie's assignments unchanged (now has 1 item)
        assertEquals("Letter2 should still be assigned to Charlie", charlie, letter2.getMailman());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        area.setName("NorthQuarter");
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        Mailman xavier = new Mailman();
        xavier.setName("Xavier");
        Mailman yvonne = new Mailman();
        yvonne.setName("Yvonne");
        Mailman zack = new Mailman();
        zack.setName("Zack");
        
        area.addMailman(xavier);
        area.addMailman(yvonne);
        area.addMailman(zack);
        
        // Add Inhabitant "Walter"
        Inhabitant walter = new Inhabitant();
        walter.setName("Walter");
        area.addInhabitant(walter);
        
        // Create and assign mail items
        List<Letter> xavierLetters = new ArrayList<>();
        List<Parcel> yvonneParcels = new ArrayList<>();
        List<Letter> zackLetters = new ArrayList<>();
        
        // 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            area.getRegisteredMails().add(letter);
            area.assignMailToMailman(letter, xavier, walter);
            xavierLetters.add(letter);
        }
        
        // 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            area.getRegisteredMails().add(parcel);
            area.assignMailToMailman(parcel, yvonne, walter);
            yvonneParcels.add(parcel);
        }
        
        // 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            area.getRegisteredMails().add(letter);
            area.assignMailToMailman(letter, zack, walter);
            zackLetters.add(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = area.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal should be successful", result1);
        
        // Verifications after first removal
        // 3 parcels moved to Xavier (now has 8 items)
        for (Parcel parcel : yvonneParcels) {
            assertEquals("Parcel should be reassigned to Xavier", xavier, parcel.getMailman());
        }
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = area.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal should be successful", result2);
        
        // Verifications after second removal
        // 8 letters moved to Zack (now has 10 items)
        for (Letter letter : xavierLetters) {
            assertEquals("Letter should be reassigned to Zack", zack, letter.getMailman());
        }
        for (Parcel parcel : yvonneParcels) {
            assertEquals("Parcel should be reassigned to Zack", zack, parcel.getMailman());
        }
        
        // Final state: Only Zack remains with all 10 deliveries
        assertEquals("Only one mailman should remain", 1, area.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", area.getMailmen().contains(zack));
        
        // Verify all 10 deliveries are assigned to Zack
        for (RegisteredMail mail : area.getRegisteredMails()) {
            assertEquals("All mail should be assigned to Zack", zack, mail.getMailman());
        }
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        area.setName("SouthEnd");
        
        // Add Mailmen ["Paul", "Quinn"]
        Mailman paul = new Mailman();
        paul.setName("Paul");
        Mailman quinn = new Mailman();
        quinn.setName("Quinn");
        
        area.addMailman(paul);
        area.addMailman(quinn);
        
        // Add Inhabitant "Rachel"
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        area.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        area.getRegisteredMails().add(letter1);
        area.assignMailToMailman(letter1, paul, rachel);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = area.removeMailman(paul, quinn);
        assertTrue("First removal should be successful", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = area.removeMailman(quinn, paul); // paul is no longer in area, but method should fail before checking replacement
        assertFalse("Removal of last mailman should fail", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        area.setName("EastHaven");
        
        // Add Mailmen ["Mario", "Luigi"]
        Mailman mario = new Mailman();
        mario.setName("Mario");
        Mailman luigi = new Mailman();
        luigi.setName("Luigi");
        
        area.addMailman(mario);
        area.addMailman(luigi);
        
        // Add Inhabitants ["Peach", "Bowser"]
        Inhabitant peach = new Inhabitant();
        peach.setName("Peach");
        Inhabitant bowser = new Inhabitant();
        bowser.setName("Bowser");
        
        area.addInhabitant(peach);
        area.addInhabitant(bowser);
        
        // Create mail items
        List<Letter> marioLetters = new ArrayList<>();
        List<Parcel> luigiParcels = new ArrayList<>();
        
        // 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            area.getRegisteredMails().add(letter);
            area.assignMailToMailman(letter, mario, peach);
            marioLetters.add(letter);
        }
        
        // 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            area.getRegisteredMails().add(parcel);
            area.assignMailToMailman(parcel, luigi, bowser);
            luigiParcels.add(parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean addDuplicateResult = area.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", addDuplicateResult);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMarioResult = area.removeMailman(mario, luigi);
        assertTrue("Removing Mario should be successful", removeMarioResult);
        
        // 3. Verify Luigi now has 15 deliveries
        int luigiDeliveries = 0;
        for (RegisteredMail mail : area.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(luigi)) {
                luigiDeliveries++;
            }
        }
        assertEquals("Luigi should have 15 deliveries", 15, luigiDeliveries);
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigiResult = area.removeMailman(luigi, mario); // mario is no longer in area, but method should fail before checking replacement
        assertFalse("Removing last mailman should fail", removeLuigiResult);
        
        // 5. Add "Toad" → true
        Mailman toad = new Mailman();
        toad.setName("Toad");
        boolean addToadResult = area.addMailman(toad);
        assertTrue("Adding Toad should be successful", addToadResult);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeLuigiWithReplacementResult = area.removeMailman(luigi, toad);
        assertTrue("Removing Luigi with Toad as replacement should be successful", removeLuigiWithReplacementResult);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        area.setName("WestRidge");
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        Mailman alpha = new Mailman();
        alpha.setName("Alpha");
        Mailman beta = new Mailman();
        beta.setName("Beta");
        Mailman gamma = new Mailman();
        gamma.setName("Gamma");
        
        area.addMailman(alpha);
        area.addMailman(beta);
        area.addMailman(gamma);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setName("Inhabitant" + i);
            area.addInhabitant(inhabitant);
            inhabitants.add(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCount = 0;
        for (int i = 0; i < 10; i++) {
            // Alpha's mail
            Letter letterAlpha = new Letter();
            letterAlpha.setAddressee(inhabitants.get(i % inhabitants.size()));
            area.getRegisteredMails().add(letterAlpha);
            area.assignMailToMailman(letterAlpha, alpha, inhabitants.get(i % inhabitants.size()));
            mailCount++;
            
            // Beta's mail
            Parcel parcelBeta = new Parcel();
            parcelBeta.setAddressee(inhabitants.get((i + 1) % inhabitants.size()));
            area.getRegisteredMails().add(parcelBeta);
            area.assignMailToMailman(parcelBeta, beta, inhabitants.get((i + 1) % inhabitants.size()));
            mailCount++;
            
            // Gamma's mail
            Letter letterGamma = new Letter();
            letterGamma.setAddressee(inhabitants.get((i + 2) % inhabitants.size()));
            area.getRegisteredMails().add(letterGamma);
            area.assignMailToMailman(letterGamma, gamma, inhabitants.get((i + 2) % inhabitants.size()));
            mailCount++;
        }
        
        assertEquals("Should have 30 mail items", 30, mailCount);
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = area.removeMailman(alpha, beta);
        assertTrue("First removal should be successful", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = area.removeMailman(beta, gamma);
        assertTrue("Second removal should be successful", result2);
        
        // Verifications:
        // Gamma ends with all 30 deliveries
        assertEquals("Only Gamma should remain", 1, area.getMailmen().size());
        assertTrue("Gamma should be the only remaining mailman", area.getMailmen().contains(gamma));
        
        int gammaDeliveries = 0;
        for (RegisteredMail mail : area.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(gamma)) {
                gammaDeliveries++;
            }
        }
        assertEquals("Gamma should have all 30 deliveries", 30, gammaDeliveries);
    }
}