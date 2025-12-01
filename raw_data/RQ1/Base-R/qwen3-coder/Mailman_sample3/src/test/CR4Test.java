import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private MailDeliverySystem system;
    private Area centralDistrict;
    private Area northQuarter;
    private Area southEnd;
    private Area eastHaven;
    private Area westRidge;
    
    @Before
    public void setUp() {
        system = new MailDeliverySystem();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        centralDistrict = new Area();
        centralDistrict.setName("CentralDistrict");
        system.getAreas().add(centralDistrict);
        
        // Add Mailmen ["Alice", "Bob", "Charlie"]
        Mailman alice = new Mailman();
        alice.setName("Alice");
        Mailman bob = new Mailman();
        bob.setName("Bob");
        Mailman charlie = new Mailman();
        charlie.setName("Charlie");
        
        system.addMailman(alice, centralDistrict);
        system.addMailman(bob, centralDistrict);
        system.addMailman(charlie, centralDistrict);
        
        // Add Inhabitants ["David", "Eve", "Frank"]
        Inhabitant david = new Inhabitant();
        david.setName("David");
        Inhabitant eve = new Inhabitant();
        eve.setName("Eve");
        Inhabitant frank = new Inhabitant();
        frank.setName("Frank");
        
        system.addInhabitant(david, centralDistrict);
        system.addInhabitant(eve, centralDistrict);
        system.addInhabitant(frank, centralDistrict);
        
        // Create and assign mail items
        Letter letter1 = new Letter();
        Parcel parcel1 = new Parcel();
        Letter letter2 = new Letter();
        Parcel parcel2 = new Parcel();
        Letter letter3 = new Letter();
        
        system.assignMailmanToDeliverMail(letter1, alice, david);
        system.assignMailmanToDeliverMail(parcel1, bob, eve);
        system.assignMailmanToDeliverMail(letter2, charlie, frank);
        system.assignMailmanToDeliverMail(parcel2, alice, eve);
        system.assignMailmanToDeliverMail(letter3, bob, david);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = system.removeMailman(alice, centralDistrict, bob);
        
        // Expected Output: true
        assertTrue("Alice removal should be successful", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from area mailmen", centralDistrict.getMailmen().contains(alice));
        assertFalse("Alice should be removed from mailman areas", alice.getAreas().contains(centralDistrict));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        assertEquals("Letter1 should be reassigned to Bob", bob, letter1.getDeliveringMailman());
        assertEquals("Parcel2 should be reassigned to Bob", bob, parcel2.getDeliveringMailman());
        
        // Count Bob's deliveries
        int bobDeliveryCount = 0;
        for (RegisteredMail mail : centralDistrict.getDeliveries()) {
            if (mail.getDeliveringMailman() == bob) {
                bobDeliveryCount++;
            }
        }
        assertEquals("Bob should have 4 deliveries", 4, bobDeliveryCount);
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertEquals("Parcel1 should still be assigned to Bob", bob, parcel1.getDeliveringMailman());
        assertEquals("Letter3 should still be assigned to Bob", bob, letter3.getDeliveringMailman());
        
        // Charlie's assignments unchanged (now has 1 item)
        int charlieDeliveryCount = 0;
        for (RegisteredMail mail : centralDistrict.getDeliveries()) {
            if (mail.getDeliveringMailman() == charlie) {
                charlieDeliveryCount++;
            }
        }
        assertEquals("Charlie should have 1 delivery", 1, charlieDeliveryCount);
        assertEquals("Letter2 should still be assigned to Charlie", charlie, letter2.getDeliveringMailman());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        northQuarter = new Area();
        northQuarter.setName("NorthQuarter");
        system.getAreas().add(northQuarter);
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        Mailman xavier = new Mailman();
        xavier.setName("Xavier");
        Mailman yvonne = new Mailman();
        yvonne.setName("Yvonne");
        Mailman zack = new Mailman();
        zack.setName("Zack");
        
        system.addMailman(xavier, northQuarter);
        system.addMailman(yvonne, northQuarter);
        system.addMailman(zack, northQuarter);
        
        // Add Inhabitant "Walter"
        Inhabitant walter = new Inhabitant();
        walter.setName("Walter");
        system.addInhabitant(walter, northQuarter);
        
        // Create and assign: 5 Letters (Xavier→Walter), 3 Parcels (Yvonne→Walter), 2 Letters (Zack→Walter)
        List<Letter> xavierLetters = new ArrayList<>();
        List<Parcel> yvonneParcels = new ArrayList<>();
        List<Letter> zackLetters = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            xavierLetters.add(letter);
            system.assignMailmanToDeliverMail(letter, xavier, walter);
        }
        
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            yvonneParcels.add(parcel);
            system.assignMailmanToDeliverMail(parcel, yvonne, walter);
        }
        
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            zackLetters.add(letter);
            system.assignMailmanToDeliverMail(letter, zack, walter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean firstRemoval = system.removeMailman(yvonne, northQuarter, xavier);
        
        // Expected Output: true
        assertTrue("First removal (Yvonne) should be successful", firstRemoval);
        
        // Verifications after first removal
        // 3 parcels moved to Xavier (now has 8 items)
        int xavierDeliveryCount = 0;
        for (RegisteredMail mail : northQuarter.getDeliveries()) {
            if (mail.getDeliveringMailman() == xavier) {
                xavierDeliveryCount++;
            }
        }
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xavierDeliveryCount);
        
        // Verify all Yvonne's parcels are now assigned to Xavier
        for (Parcel parcel : yvonneParcels) {
            assertEquals("Yvonne's parcels should be reassigned to Xavier", xavier, parcel.getDeliveringMailman());
        }
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean secondRemoval = system.removeMailman(xavier, northQuarter, zack);
        
        // Expected Output: true
        assertTrue("Second removal (Xavier) should be successful", secondRemoval);
        
        // Verifications after second removal
        // 8 letters moved to Zack (now has 10 items)
        int zackDeliveryCount = 0;
        for (RegisteredMail mail : northQuarter.getDeliveries()) {
            if (mail.getDeliveringMailman() == zack) {
                zackDeliveryCount++;
            }
        }
        assertEquals("Zack should have 10 deliveries after second removal", 10, zackDeliveryCount);
        
        // Final state: Only Zack remains with all 10 deliveries
        assertEquals("Only Zack should remain in area mailmen", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", northQuarter.getMailmen().contains(zack));
        assertEquals("Zack should have all 10 deliveries", 10, zackDeliveryCount);
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        southEnd = new Area();
        southEnd.setName("SouthEnd");
        system.getAreas().add(southEnd);
        
        // Add Mailmen ["Paul", "Quinn"]
        Mailman paul = new Mailman();
        paul.setName("Paul");
        Mailman quinn = new Mailman();
        quinn.setName("Quinn");
        
        system.addMailman(paul, southEnd);
        system.addMailman(quinn, southEnd);
        
        // Add Inhabitant "Rachel"
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        system.addInhabitant(rachel, southEnd);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        system.assignMailmanToDeliverMail(letter1, paul, rachel);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean firstRemoval = system.removeMailman(paul, southEnd, quinn);
        assertTrue("First removal (Paul) should be successful", firstRemoval);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean secondRemoval = system.removeMailman(quinn, southEnd, paul); // paul is no longer in area
        assertFalse("Second removal (Quinn as last mailman) should fail", secondRemoval);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        eastHaven = new Area();
        eastHaven.setName("EastHaven");
        system.getAreas().add(eastHaven);
        
        // Add Mailmen ["Mario", "Luigi"]
        Mailman mario = new Mailman();
        mario.setName("Mario");
        Mailman luigi = new Mailman();
        luigi.setName("Luigi");
        
        system.addMailman(mario, eastHaven);
        system.addMailman(luigi, eastHaven);
        
        // Add Inhabitants ["Peach", "Bowser"]
        Inhabitant peach = new Inhabitant();
        peach.setName("Peach");
        Inhabitant bowser = new Inhabitant();
        bowser.setName("Bowser");
        
        system.addInhabitant(peach, eastHaven);
        system.addInhabitant(bowser, eastHaven);
        
        // Create: 10 Letters (Mario→Peach), 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            system.assignMailmanToDeliverMail(letter, mario, peach);
        }
        
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            system.assignMailmanToDeliverMail(parcel, luigi, bowser);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean duplicateAdd = system.addMailman(mario, eastHaven);
        assertFalse("Adding duplicate Mario should fail", duplicateAdd);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean marioRemoval = system.removeMailman(mario, eastHaven, luigi);
        assertTrue("Mario removal should be successful", marioRemoval);
        
        // 3. Verify Luigi now has 15 deliveries
        int luigiDeliveryCount = 0;
        for (RegisteredMail mail : eastHaven.getDeliveries()) {
            if (mail.getDeliveringMailman() == luigi) {
                luigiDeliveryCount++;
            }
        }
        assertEquals("Luigi should have 15 deliveries after taking Mario's", 15, luigiDeliveryCount);
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean luigiRemovalAttempt = system.removeMailman(luigi, eastHaven, mario); // mario is no longer in area
        assertFalse("Removing last mailman (Luigi) should fail", luigiRemovalAttempt);
        
        // 5. Add "Toad" → true
        Mailman toad = new Mailman();
        toad.setName("Toad");
        boolean toadAdd = system.addMailman(toad, eastHaven);
        assertTrue("Adding Toad should be successful", toadAdd);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean luigiRemoval = system.removeMailman(luigi, eastHaven, toad);
        assertTrue("Luigi removal with replacement should be successful", luigiRemoval);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        westRidge = new Area();
        westRidge.setName("WestRidge");
        system.getAreas().add(westRidge);
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        Mailman alpha = new Mailman();
        alpha.setName("Alpha");
        Mailman beta = new Mailman();
        beta.setName("Beta");
        Mailman gamma = new Mailman();
        gamma.setName("Gamma");
        
        system.addMailman(alpha, westRidge);
        system.addMailman(beta, westRidge);
        system.addMailman(gamma, westRidge);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setName("Inhabitant" + i);
            inhabitants.add(inhabitant);
            system.addInhabitant(inhabitant, westRidge);
        }
        
        // Create 30 mail items (10 each mailman)
        List<RegisteredMail> alphaMails = new ArrayList<>();
        List<RegisteredMail> betaMails = new ArrayList<>();
        List<RegisteredMail> gammaMails = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            alphaMails.add(letter);
            system.assignMailmanToDeliverMail(letter, alpha, inhabitants.get(i % 10));
            
            Parcel parcel = new Parcel();
            betaMails.add(parcel);
            system.assignMailmanToDeliverMail(parcel, beta, inhabitants.get(i % 10));
            
            Letter letter2 = new Letter();
            gammaMails.add(letter2);
            system.assignMailmanToDeliverMail(letter2, gamma, inhabitants.get(i % 10));
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean firstRemoval = system.removeMailman(alpha, westRidge, beta);
        assertTrue("First removal (Alpha) should be successful", firstRemoval);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean secondRemoval = system.removeMailman(beta, westRidge, gamma);
        assertTrue("Second removal (Beta) should be successful", secondRemoval);
        
        // Verifications:
        // Gamma ends with all 30 deliveries
        int gammaDeliveryCount = 0;
        for (RegisteredMail mail : westRidge.getDeliveries()) {
            if (mail.getDeliveringMailman() == gamma) {
                gammaDeliveryCount++;
            }
        }
        assertEquals("Gamma should have all 30 deliveries", 30, gammaDeliveryCount);
        
        // Verify Gamma is the only remaining mailman
        assertEquals("Only Gamma should remain in area mailmen", 1, westRidge.getMailmen().size());
        assertTrue("Gamma should be the only remaining mailman", westRidge.getMailmen().contains(gamma));
    }
}