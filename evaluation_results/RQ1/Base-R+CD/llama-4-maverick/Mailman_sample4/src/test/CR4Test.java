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
    
    private Mailman alice;
    private Mailman bob;
    private Mailman charlie;
    private Mailman xavier;
    private Mailman yvonne;
    private Mailman zack;
    private Mailman paul;
    private Mailman quinn;
    private Mailman mario;
    private Mailman luigi;
    private Mailman toad;
    private Mailman alpha;
    private Mailman beta;
    private Mailman gamma;
    
    private Inhabitant david;
    private Inhabitant eve;
    private Inhabitant frank;
    private Inhabitant walter;
    private Inhabitant rachel;
    private Inhabitant peach;
    private Inhabitant bowser;
    
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
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(alice);
        mailmen.add(bob);
        mailmen.add(charlie);
        centralDistrict.setMailmen(mailmen);
        
        // Add Inhabitants ["David", "Eve", "Frank"]
        List<Inhabitant> inhabitants = new ArrayList<>();
        inhabitants.add(david);
        inhabitants.add(eve);
        inhabitants.add(frank);
        centralDistrict.setInhabitants(inhabitants);
        
        // Create and assign mail items
        Letter letter1 = new Letter();
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        
        Parcel parcel1 = new Parcel();
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        
        Letter letter2 = new Letter();
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        
        Parcel parcel2 = new Parcel();
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        
        Letter letter3 = new Letter();
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Alice removal should succeed", result);
        
        // Verifications:
        // - Alice removed
        assertFalse("Alice should be removed from mailmen list", 
                   centralDistrict.getMailmen().contains(alice));
        
        // - Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsDeliveries = centralDistrict.listRegisteredMail(bob);
        assertEquals("Bob should have 4 deliveries after reassignment", 4, bobsDeliveries.size());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob should still have Parcel1", bobsDeliveries.contains(parcel1));
        assertTrue("Bob should still have Letter3", bobsDeliveries.contains(letter3));
        
        // Verify reassigned items
        assertTrue("Letter1 should be reassigned to Bob", bobsDeliveries.contains(letter1));
        assertTrue("Parcel2 should be reassigned to Bob", bobsDeliveries.contains(parcel2));
        
        // - Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesDeliveries = centralDistrict.listRegisteredMail(charlie);
        assertEquals("Charlie should have 1 delivery", 1, charliesDeliveries.size());
        assertTrue("Charlie should still have Letter2", charliesDeliveries.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        northQuarter = new GeographicalArea();
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(xavier);
        mailmen.add(yvonne);
        mailmen.add(zack);
        northQuarter.setMailmen(mailmen);
        
        // Add Inhabitant "Walter"
        List<Inhabitant> inhabitants = new ArrayList<>();
        inhabitants.add(walter);
        northQuarter.setInhabitants(inhabitants);
        
        // Create and assign mail items
        List<RegisteredMail> xaviersLetters = new ArrayList<>();
        List<RegisteredMail> yvonnesParcels = new ArrayList<>();
        List<RegisteredMail> zacksLetters = new ArrayList<>();
        
        // 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
            xaviersLetters.add(letter);
        }
        
        // 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
            yvonnesParcels.add(parcel);
        }
        
        // 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
            zacksLetters.add(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean firstRemoval = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal (Yvonne) should succeed", firstRemoval);
        
        // Verifications after first removal:
        // - 3 parcels moved to Xavier (now has 8 items)
        List<RegisteredMail> xaviersDeliveriesAfterFirst = northQuarter.listRegisteredMail(xavier);
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xaviersDeliveriesAfterFirst.size());
        
        // Verify all parcels were reassigned to Xavier
        for (RegisteredMail parcel : yvonnesParcels) {
            assertTrue("Parcel should be reassigned to Xavier", xaviersDeliveriesAfterFirst.contains(parcel));
        }
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean secondRemoval = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal (Xavier) should succeed", secondRemoval);
        
        // Verifications after second removal:
        // - 8 letters moved to Zack (now has 10 items)
        List<RegisteredMail> zacksDeliveriesAfterSecond = northQuarter.listRegisteredMail(zack);
        assertEquals("Zack should have 10 deliveries after second removal", 10, zacksDeliveriesAfterSecond.size());
        
        // - Final state: Only Zack remains with all 10 deliveries
        assertEquals("Only Zack should remain in mailmen list", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", northQuarter.getMailmen().contains(zack));
        
        // Verify all deliveries belong to Zack
        assertEquals("Zack should have all 10 deliveries", 10, zacksDeliveriesAfterSecond.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        southEnd = new GeographicalArea();
        
        // Add Mailmen ["Paul", "Quinn"]
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(paul);
        mailmen.add(quinn);
        southEnd.setMailmen(mailmen);
        
        // Add Inhabitant "Rachel"
        List<Inhabitant> inhabitants = new ArrayList<>();
        inhabitants.add(rachel);
        southEnd.setInhabitants(inhabitants);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean firstRemoval = southEnd.removeMailman(paul, quinn);
        assertTrue("Removing Paul with Quinn as replacement should succeed", firstRemoval);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean secondRemoval = southEnd.removeMailman(quinn, paul); // paul is no longer in the list
        assertFalse("Removing last mailman should fail", secondRemoval);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        eastHaven = new GeographicalArea();
        
        // Add Mailmen ["Mario", "Luigi"]
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(mario);
        mailmen.add(luigi);
        eastHaven.setMailmen(mailmen);
        
        // Add Inhabitants ["Peach", "Bowser"]
        List<Inhabitant> inhabitants = new ArrayList<>();
        inhabitants.add(peach);
        inhabitants.add(bowser);
        eastHaven.setInhabitants(inhabitants);
        
        // Create mail items
        List<RegisteredMail> mariosLetters = new ArrayList<>();
        List<RegisteredMail> luigisParcels = new ArrayList<>();
        
        // 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
            mariosLetters.add(letter);
        }
        
        // 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
            luigisParcels.add(parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean duplicateAdd = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate Mario should fail", duplicateAdd);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean marioRemoval = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removing Mario with Luigi as replacement should succeed", marioRemoval);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisDeliveries = eastHaven.listRegisteredMail(luigi);
        assertEquals("Luigi should have 15 deliveries after reassignment", 15, luigisDeliveries.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean luigiRemovalAttempt = eastHaven.removeMailman(luigi, mario); // mario is no longer in the list
        assertFalse("Removing last mailman should fail", luigiRemovalAttempt);
        
        // 5. Add "Toad" → true
        boolean toadAdd = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should succeed", toadAdd);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean luigiRemoval = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removing Luigi with Toad as replacement should succeed", luigiRemoval);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        westRidge = new GeographicalArea();
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(alpha);
        mailmen.add(beta);
        mailmen.add(gamma);
        westRidge.setMailmen(mailmen);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            inhabitants.add(new Inhabitant());
        }
        westRidge.setInhabitants(inhabitants);
        
        // Create 30 mail items (10 each mailman)
        List<RegisteredMail> allMails = new ArrayList<>();
        
        // Alpha's 10 mail items
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(i % 10), letter);
                allMails.add(letter);
            } else {
                Parcel parcel = new Parcel();
                westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(i % 10), parcel);
                allMails.add(parcel);
            }
        }
        
        // Beta's 10 mail items
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(i % 10), letter);
                allMails.add(letter);
            } else {
                Parcel parcel = new Parcel();
                westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(i % 10), parcel);
                allMails.add(parcel);
            }
        }
        
        // Gamma's 10 mail items
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(i % 10), letter);
                allMails.add(letter);
            } else {
                Parcel parcel = new Parcel();
                westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(i % 10), parcel);
                allMails.add(parcel);
            }
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean firstRemoval = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal (Alpha) should succeed", firstRemoval);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean secondRemoval = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal (Beta) should succeed", secondRemoval);
        
        // Verifications:
        // - Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasDeliveries = westRidge.listRegisteredMail(gamma);
        assertEquals("Gamma should have all 30 deliveries", 30, gammasDeliveries.size());
        
        // Verify all mail items are assigned to Gamma
        for (RegisteredMail mail : allMails) {
            assertEquals("All mail should be assigned to Gamma", gamma, mail.getCarrier());
        }
    }
}