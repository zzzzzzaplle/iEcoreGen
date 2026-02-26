import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private MailDeliverySystem mailDeliverySystem;
    private GeographicalArea centralDistrict;
    private GeographicalArea northQuarter;
    private GeographicalArea southEnd;
    private GeographicalArea eastHaven;
    private GeographicalArea westRidge;
    
    @Before
    public void setUp() {
        mailDeliverySystem = new MailDeliverySystem();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        centralDistrict = new GeographicalArea();
        centralDistrict.setName("CentralDistrict");
        mailDeliverySystem.addGeographicalArea(centralDistrict);
        
        // Add Mailmen ["Alice", "Bob", "Charlie"]
        Mailman alice = new Mailman();
        alice.setName("Alice");
        Mailman bob = new Mailman();
        bob.setName("Bob");
        Mailman charlie = new Mailman();
        charlie.setName("Charlie");
        
        mailDeliverySystem.addMailman(centralDistrict, alice);
        mailDeliverySystem.addMailman(centralDistrict, bob);
        mailDeliverySystem.addMailman(centralDistrict, charlie);
        
        // Add Inhabitants ["David", "Eve", "Frank"]
        Inhabitant david = new Inhabitant();
        david.setName("David");
        Inhabitant eve = new Inhabitant();
        eve.setName("Eve");
        Inhabitant frank = new Inhabitant();
        frank.setName("Frank");
        
        mailDeliverySystem.addInhabitant(centralDistrict, david);
        mailDeliverySystem.addInhabitant(centralDistrict, eve);
        mailDeliverySystem.addInhabitant(centralDistrict, frank);
        
        // Create and assign mail items
        // Letter1 (Alice→David)
        Letter letter1 = new Letter();
        letter1.setAddressee(david);
        letter1.assignMailman(alice);
        centralDistrict.addRegisteredMail(letter1);
        
        // Parcel1 (Bob→Eve)
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(eve);
        parcel1.assignMailman(bob);
        centralDistrict.addRegisteredMail(parcel1);
        
        // Letter2 (Charlie→Frank)
        Letter letter2 = new Letter();
        letter2.setAddressee(frank);
        letter2.assignMailman(charlie);
        centralDistrict.addRegisteredMail(letter2);
        
        // Parcel2 (Alice→Eve)
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(eve);
        parcel2.assignMailman(alice);
        centralDistrict.addRegisteredMail(parcel2);
        
        // Letter3 (Bob→David)
        Letter letter3 = new Letter();
        letter3.setAddressee(david);
        letter3.assignMailman(bob);
        centralDistrict.addRegisteredMail(letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = mailDeliverySystem.removeMailman(centralDistrict, alice, bob);
        
        // Expected Output: true
        assertTrue("Alice removal should succeed", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen list", 
                   centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        int bobDeliveries = 0;
        int charlieDeliveries = 0;
        for (RegisteredMail mail : centralDistrict.getRegisteredMails()) {
            if (mail.getMailman() == bob) {
                bobDeliveries++;
            } else if (mail.getMailman() == charlie) {
                charlieDeliveries++;
            }
        }
        
        assertEquals("Bob should have 4 deliveries after reassignment", 4, bobDeliveries);
        assertEquals("Charlie's assignments should remain unchanged (1 item)", 1, charlieDeliveries);
        
        // Verify specific reassignments
        boolean letter1Reassigned = false;
        boolean parcel2Reassigned = false;
        for (RegisteredMail mail : centralDistrict.getRegisteredMails()) {
            if (mail.getAddressee().equals(david) && mail instanceof Letter && mail.getMailman() == bob) {
                letter1Reassigned = true;
            }
            if (mail.getAddressee().equals(eve) && mail instanceof Parcel && mail.getMailman() == bob) {
                parcel2Reassigned = true;
            }
        }
        assertTrue("Letter1 should be reassigned to Bob", letter1Reassigned);
        assertTrue("Parcel2 should be reassigned to Bob", parcel2Reassigned);
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        northQuarter = new GeographicalArea();
        northQuarter.setName("NorthQuarter");
        mailDeliverySystem.addGeographicalArea(northQuarter);
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        Mailman xavier = new Mailman();
        xavier.setName("Xavier");
        Mailman yvonne = new Mailman();
        yvonne.setName("Yvonne");
        Mailman zack = new Mailman();
        zack.setName("Zack");
        
        mailDeliverySystem.addMailman(northQuarter, xavier);
        mailDeliverySystem.addMailman(northQuarter, yvonne);
        mailDeliverySystem.addMailman(northQuarter, zack);
        
        // Add Inhabitant "Walter"
        Inhabitant walter = new Inhabitant();
        walter.setName("Walter");
        mailDeliverySystem.addInhabitant(northQuarter, walter);
        
        // Create and assign mail items
        // 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.assignMailman(xavier);
            northQuarter.addRegisteredMail(letter);
        }
        
        // 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            parcel.assignMailman(yvonne);
            northQuarter.addRegisteredMail(parcel);
        }
        
        // 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.assignMailman(zack);
            northQuarter.addRegisteredMail(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean firstRemoval = mailDeliverySystem.removeMailman(northQuarter, yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal (Yvonne) should succeed", firstRemoval);
        
        // Verification after first removal: 3 parcels moved to Xavier (now has 8 items)
        int xavierDeliveries = 0;
        int zackDeliveries = 0;
        for (RegisteredMail mail : northQuarter.getRegisteredMails()) {
            if (mail.getMailman() == xavier) {
                xavierDeliveries++;
            } else if (mail.getMailman() == zack) {
                zackDeliveries++;
            }
        }
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xavierDeliveries);
        assertEquals("Zack should still have 2 deliveries", 2, zackDeliveries);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean secondRemoval = mailDeliverySystem.removeMailman(northQuarter, xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal (Xavier) should succeed", secondRemoval);
        
        // Verification after second removal: 8 letters moved to Zack (now has 10 items)
        int finalZackDeliveries = 0;
        for (RegisteredMail mail : northQuarter.getRegisteredMails()) {
            if (mail.getMailman() == zack) {
                finalZackDeliveries++;
            }
        }
        assertEquals("Zack should have all 10 deliveries after second removal", 10, finalZackDeliveries);
        
        // Final state: Only Zack remains with all 10 deliveries
        assertEquals("Only one mailman should remain", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the remaining mailman", northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        southEnd = new GeographicalArea();
        southEnd.setName("SouthEnd");
        mailDeliverySystem.addGeographicalArea(southEnd);
        
        // Add Mailmen ["Paul", "Quinn"]
        Mailman paul = new Mailman();
        paul.setName("Paul");
        Mailman quinn = new Mailman();
        quinn.setName("Quinn");
        
        mailDeliverySystem.addMailman(southEnd, paul);
        mailDeliverySystem.addMailman(southEnd, quinn);
        
        // Add Inhabitant "Rachel"
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        mailDeliverySystem.addInhabitant(southEnd, rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        letter1.assignMailman(paul);
        southEnd.addRegisteredMail(letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean firstRemoval = mailDeliverySystem.removeMailman(southEnd, paul, quinn);
        assertTrue("Removing Paul should succeed", firstRemoval);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean secondRemoval = mailDeliverySystem.removeMailman(southEnd, quinn, null);
        assertFalse("Removing last mailman should fail", secondRemoval);
        
        // Verify Quinn is still there
        assertTrue("Quinn should still be in mailmen list", southEnd.getMailmen().contains(quinn));
        assertEquals("Should still have one mailman", 1, southEnd.getMailmen().size());
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        eastHaven = new GeographicalArea();
        eastHaven.setName("EastHaven");
        mailDeliverySystem.addGeographicalArea(eastHaven);
        
        // Add Mailmen ["Mario", "Luigi"]
        Mailman mario = new Mailman();
        mario.setName("Mario");
        Mailman luigi = new Mailman();
        luigi.setName("Luigi");
        
        mailDeliverySystem.addMailman(eastHaven, mario);
        mailDeliverySystem.addMailman(eastHaven, luigi);
        
        // Add Inhabitants ["Peach", "Bowser"]
        Inhabitant peach = new Inhabitant();
        peach.setName("Peach");
        Inhabitant bowser = new Inhabitant();
        bowser.setName("Bowser");
        
        mailDeliverySystem.addInhabitant(eastHaven, peach);
        mailDeliverySystem.addInhabitant(eastHaven, bowser);
        
        // Create mail items
        // 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            letter.assignMailman(mario);
            eastHaven.addRegisteredMail(letter);
        }
        
        // 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            parcel.assignMailman(luigi);
            eastHaven.addRegisteredMail(parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean duplicateAdd = mailDeliverySystem.addMailman(eastHaven, mario);
        assertFalse("Adding duplicate mailman should fail", duplicateAdd);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean firstRemoval = mailDeliverySystem.removeMailman(eastHaven, mario, luigi);
        assertTrue("Removing Mario should succeed", firstRemoval);
        
        // 3. Verify Luigi now has 15 deliveries
        int luigiDeliveries = 0;
        for (RegisteredMail mail : eastHaven.getRegisteredMails()) {
            if (mail.getMailman() == luigi) {
                luigiDeliveries++;
            }
        }
        assertEquals("Luigi should have 15 deliveries after reassignment", 15, luigiDeliveries);
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean secondRemoval = mailDeliverySystem.removeMailman(eastHaven, luigi, null);
        assertFalse("Removing last mailman should fail", secondRemoval);
        
        // 5. Add "Toad" → true
        Mailman toad = new Mailman();
        toad.setName("Toad");
        boolean addToad = mailDeliverySystem.addMailman(eastHaven, toad);
        assertTrue("Adding Toad should succeed", addToad);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean thirdRemoval = mailDeliverySystem.removeMailman(eastHaven, luigi, toad);
        assertTrue("Removing Luigi should succeed", thirdRemoval);
        
        // Verify final state
        assertFalse("Luigi should be removed", eastHaven.getMailmen().contains(luigi));
        assertTrue("Toad should be present", eastHaven.getMailmen().contains(toad));
        
        int toadDeliveries = 0;
        for (RegisteredMail mail : eastHaven.getRegisteredMails()) {
            if (mail.getMailman() == toad) {
                toadDeliveries++;
            }
        }
        assertEquals("Toad should have all 15 deliveries", 15, toadDeliveries);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        westRidge = new GeographicalArea();
        westRidge.setName("WestRidge");
        mailDeliverySystem.addGeographicalArea(westRidge);
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        Mailman alpha = new Mailman();
        alpha.setName("Alpha");
        Mailman beta = new Mailman();
        beta.setName("Beta");
        Mailman gamma = new Mailman();
        gamma.setName("Gamma");
        
        mailDeliverySystem.addMailman(westRidge, alpha);
        mailDeliverySystem.addMailman(westRidge, beta);
        mailDeliverySystem.addMailman(westRidge, gamma);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setName("Inhabitant" + i);
            mailDeliverySystem.addInhabitant(westRidge, inhabitant);
            inhabitants.add(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Letter letterAlpha = new Letter();
            letterAlpha.setAddressee(inhabitants.get(inhabitantIndex % 10));
            letterAlpha.assignMailman(alpha);
            westRidge.addRegisteredMail(letterAlpha);
            
            Parcel parcelBeta = new Parcel();
            parcelBeta.setAddressee(inhabitants.get((inhabitantIndex + 1) % 10));
            parcelBeta.assignMailman(beta);
            westRidge.addRegisteredMail(parcelBeta);
            
            Letter letterGamma = new Letter();
            letterGamma.setAddressee(inhabitants.get((inhabitantIndex + 2) % 10));
            letterGamma.assignMailman(gamma);
            westRidge.addRegisteredMail(letterGamma);
            
            inhabitantIndex = (inhabitantIndex + 3) % 10;
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean firstRemoval = mailDeliverySystem.removeMailman(westRidge, alpha, beta);
        assertTrue("Removing Alpha should succeed", firstRemoval);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean secondRemoval = mailDeliverySystem.removeMailman(westRidge, beta, gamma);
        assertTrue("Removing Beta should succeed", secondRemoval);
        
        // Verifications: Gamma ends with all 30 deliveries
        int gammaDeliveries = 0;
        for (RegisteredMail mail : westRidge.getRegisteredMails()) {
            if (mail.getMailman() == gamma) {
                gammaDeliveries++;
            }
        }
        assertEquals("Gamma should have all 30 deliveries", 30, gammaDeliveries);
        
        // Verify only Gamma remains
        assertEquals("Only one mailman should remain", 1, westRidge.getMailmen().size());
        assertTrue("Gamma should be the remaining mailman", westRidge.getMailmen().contains(gamma));
    }
}