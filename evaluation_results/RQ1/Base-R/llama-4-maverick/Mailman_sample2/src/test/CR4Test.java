import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private MailDeliveryManager manager;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp
        GeographicalArea centralDistrict = new GeographicalArea();
        centralDistrict.setName("CentralDistrict");
        
        Mailman alice = new Mailman();
        alice.setName("Alice");
        Mailman bob = new Mailman();
        bob.setName("Bob");
        Mailman charlie = new Mailman();
        charlie.setName("Charlie");
        
        manager.manageMailman(alice, centralDistrict, null, true);
        manager.manageMailman(bob, centralDistrict, null, true);
        manager.manageMailman(charlie, centralDistrict, null, true);
        
        Inhabitant david = new Inhabitant();
        david.setName("David");
        Inhabitant eve = new Inhabitant();
        eve.setName("Eve");
        Inhabitant frank = new Inhabitant();
        frank.setName("Frank");
        
        manager.manageInhabitant(david, centralDistrict, true);
        manager.manageInhabitant(eve, centralDistrict, true);
        manager.manageInhabitant(frank, centralDistrict, true);
        
        // Create and assign mail items
        Letter letter1 = new Letter();
        letter1.setAddressee(david);
        manager.assignMailman(letter1, alice);
        centralDistrict.addRegisteredMail(letter1);
        
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(eve);
        manager.assignMailman(parcel1, bob);
        centralDistrict.addRegisteredMail(parcel1);
        
        Letter letter2 = new Letter();
        letter2.setAddressee(frank);
        manager.assignMailman(letter2, charlie);
        centralDistrict.addRegisteredMail(letter2);
        
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(eve);
        manager.assignMailman(parcel2, alice);
        centralDistrict.addRegisteredMail(parcel2);
        
        Letter letter3 = new Letter();
        letter3.setAddressee(david);
        manager.assignMailman(letter3, bob);
        centralDistrict.addRegisteredMail(letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = manager.manageMailman(alice, centralDistrict, bob, false);
        
        // Expected Output: true
        assertTrue("Removing Alice should return true", result);
        
        // Verifications
        assertFalse("Alice should be removed", centralDistrict.getMailmen().contains(alice));
        
        // Check Bob's assignments (should have 4 items: original 2 + 2 from Alice)
        int bobAssignments = 0;
        for (RegisteredMail mail : centralDistrict.getRegisteredMails()) {
            if (mail.getMailman() == bob) {
                bobAssignments++;
            }
        }
        assertEquals("Bob should have 4 deliveries", 4, bobAssignments);
        
        // Check Charlie's assignments (should have 1 item)
        int charlieAssignments = 0;
        for (RegisteredMail mail : centralDistrict.getRegisteredMails()) {
            if (mail.getMailman() == charlie) {
                charlieAssignments++;
            }
        }
        assertEquals("Charlie should have 1 delivery", 1, charlieAssignments);
        
        // Verify specific reassignments
        boolean letter1Reassigned = false;
        boolean parcel2Reassigned = false;
        for (RegisteredMail mail : centralDistrict.getRegisteredMails()) {
            if (mail == letter1 && mail.getMailman() == bob) {
                letter1Reassigned = true;
            }
            if (mail == parcel2 && mail.getMailman() == bob) {
                parcel2Reassigned = true;
            }
        }
        assertTrue("Letter1 should be reassigned to Bob", letter1Reassigned);
        assertTrue("Parcel2 should be reassigned to Bob", parcel2Reassigned);
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        GeographicalArea northQuarter = new GeographicalArea();
        northQuarter.setName("NorthQuarter");
        
        Mailman xavier = new Mailman();
        xavier.setName("Xavier");
        Mailman yvonne = new Mailman();
        yvonne.setName("Yvonne");
        Mailman zack = new Mailman();
        zack.setName("Zack");
        
        manager.manageMailman(xavier, northQuarter, null, true);
        manager.manageMailman(yvonne, northQuarter, null, true);
        manager.manageMailman(zack, northQuarter, null, true);
        
        Inhabitant walter = new Inhabitant();
        walter.setName("Walter");
        manager.manageInhabitant(walter, northQuarter, true);
        
        // Create and assign 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            manager.assignMailman(letter, xavier);
            northQuarter.addRegisteredMail(letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            manager.assignMailman(parcel, yvonne);
            northQuarter.addRegisteredMail(parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            manager.assignMailman(letter, zack);
            northQuarter.addRegisteredMail(letter);
        }
        
        // Action 1: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = manager.manageMailman(yvonne, northQuarter, xavier, false);
        
        // Expected Output: true
        assertTrue("First removal (Yvonne) should return true", result1);
        
        // Verification after first removal
        assertFalse("Yvonne should be removed", northQuarter.getMailmen().contains(yvonne));
        
        int xavierAssignmentsAfterFirst = 0;
        for (RegisteredMail mail : northQuarter.getRegisteredMails()) {
            if (mail.getMailman() == xavier) {
                xavierAssignmentsAfterFirst++;
            }
        }
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xavierAssignmentsAfterFirst);
        
        // Action 2: Remove Xavier (specify Zack as replacement)
        boolean result2 = manager.manageMailman(xavier, northQuarter, zack, false);
        
        // Expected Output: true
        assertTrue("Second removal (Xavier) should return true", result2);
        
        // Final verifications
        assertFalse("Xavier should be removed", northQuarter.getMailmen().contains(xavier));
        
        int zackAssignments = 0;
        for (RegisteredMail mail : northQuarter.getRegisteredMails()) {
            if (mail.getMailman() == zack) {
                zackAssignments++;
            }
        }
        assertEquals("Zack should have all 10 deliveries", 10, zackAssignments);
        assertEquals("Only Zack should remain", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        GeographicalArea southEnd = new GeographicalArea();
        southEnd.setName("SouthEnd");
        
        Mailman paul = new Mailman();
        paul.setName("Paul");
        Mailman quinn = new Mailman();
        quinn.setName("Quinn");
        
        manager.manageMailman(paul, southEnd, null, true);
        manager.manageMailman(quinn, southEnd, null, true);
        
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        manager.manageInhabitant(rachel, southEnd, true);
        
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        manager.assignMailman(letter1, paul);
        southEnd.addRegisteredMail(letter1);
        
        // Action 1: Attempt remove Paul specifying Quinn
        boolean result1 = manager.manageMailman(paul, southEnd, quinn, false);
        
        // Expected Output: true (normal case)
        assertTrue("Removing Paul should return true", result1);
        assertFalse("Paul should be removed", southEnd.getMailmen().contains(paul));
        
        // Action 2: Attempt remove Quinn (last mailman)
        boolean result2 = manager.manageMailman(quinn, southEnd, null, false);
        
        // Expected Output: false
        assertFalse("Removing last mailman should return false", result2);
        assertTrue("Quinn should still be present", southEnd.getMailmen().contains(quinn));
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        GeographicalArea eastHaven = new GeographicalArea();
        eastHaven.setName("EastHaven");
        
        Mailman mario = new Mailman();
        mario.setName("Mario");
        Mailman luigi = new Mailman();
        luigi.setName("Luigi");
        
        manager.manageMailman(mario, eastHaven, null, true);
        manager.manageMailman(luigi, eastHaven, null, true);
        
        Inhabitant peach = new Inhabitant();
        peach.setName("Peach");
        Inhabitant bowser = new Inhabitant();
        bowser.setName("Bowser");
        
        manager.manageInhabitant(peach, eastHaven, true);
        manager.manageInhabitant(bowser, eastHaven, true);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            manager.assignMailman(letter, mario);
            eastHaven.addRegisteredMail(letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            manager.assignMailman(parcel, luigi);
            eastHaven.addRegisteredMail(parcel);
        }
        
        // Procedure 1: Add duplicate "Mario"
        boolean result1 = manager.manageMailman(mario, eastHaven, null, true);
        
        // Expected Output: false
        assertFalse("Adding duplicate Mario should return false", result1);
        
        // Procedure 2: Remove Mario (specify Luigi)
        boolean result2 = manager.manageMailman(mario, eastHaven, luigi, false);
        
        // Expected Output: true
        assertTrue("Removing Mario should return true", result2);
        assertFalse("Mario should be removed", eastHaven.getMailmen().contains(mario));
        
        // Procedure 3: Verify Luigi now has 15 deliveries
        int luigiDeliveries = 0;
        for (RegisteredMail mail : eastHaven.getRegisteredMails()) {
            if (mail.getMailman() == luigi) {
                luigiDeliveries++;
            }
        }
        assertEquals("Luigi should have 15 deliveries", 15, luigiDeliveries);
        
        // Procedure 4: Attempt remove Luigi (last mailman)
        boolean result3 = manager.manageMailman(luigi, eastHaven, null, false);
        
        // Expected Output: false
        assertFalse("Removing last mailman should return false", result3);
        assertTrue("Luigi should still be present", eastHaven.getMailmen().contains(luigi));
        
        // Procedure 5: Add "Toad"
        Mailman toad = new Mailman();
        toad.setName("Toad");
        boolean result4 = manager.manageMailman(toad, eastHaven, null, true);
        
        // Expected Output: true
        assertTrue("Adding Toad should return true", result4);
        
        // Procedure 6: Remove Luigi (specify Toad)
        boolean result5 = manager.manageMailman(luigi, eastHaven, toad, false);
        
        // Expected Output: true
        assertTrue("Removing Luigi should return true", result5);
        assertFalse("Luigi should be removed", eastHaven.getMailmen().contains(luigi));
        
        // Final verification
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
        // SetUp
        GeographicalArea westRidge = new GeographicalArea();
        westRidge.setName("WestRidge");
        
        Mailman alpha = new Mailman();
        alpha.setName("Alpha");
        Mailman beta = new Mailman();
        beta.setName("Beta");
        Mailman gamma = new Mailman();
        gamma.setName("Gamma");
        
        manager.manageMailman(alpha, westRidge, null, true);
        manager.manageMailman(beta, westRidge, null, true);
        manager.manageMailman(gamma, westRidge, null, true);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setName("Inhabitant" + i);
            manager.manageInhabitant(inhabitant, westRidge, true);
            inhabitants.add(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCount = 0;
        for (int i = 0; i < 10; i++) {
            Letter letterAlpha = new Letter();
            letterAlpha.setAddressee(inhabitants.get(i % inhabitants.size()));
            manager.assignMailman(letterAlpha, alpha);
            westRidge.addRegisteredMail(letterAlpha);
            mailCount++;
            
            Parcel parcelBeta = new Parcel();
            parcelBeta.setAddressee(inhabitants.get((i + 1) % inhabitants.size()));
            manager.assignMailman(parcelBeta, beta);
            westRidge.addRegisteredMail(parcelBeta);
            mailCount++;
            
            Letter letterGamma = new Letter();
            letterGamma.setAddressee(inhabitants.get((i + 2) % inhabitants.size()));
            manager.assignMailman(letterGamma, gamma);
            westRidge.addRegisteredMail(letterGamma);
            mailCount++;
        }
        
        // Action 1: Remove Alpha (specify Beta)
        boolean result1 = manager.manageMailman(alpha, westRidge, beta, false);
        
        // Expected Output: true
        assertTrue("Removing Alpha should return true", result1);
        assertFalse("Alpha should be removed", westRidge.getMailmen().contains(alpha));
        
        // Action 2: Remove Beta (specify Gamma)
        boolean result2 = manager.manageMailman(beta, westRidge, gamma, false);
        
        // Expected Output: true
        assertTrue("Removing Beta should return true", result2);
        assertFalse("Beta should be removed", westRidge.getMailmen().contains(beta));
        
        // Verifications
        int gammaAssignments = 0;
        for (RegisteredMail mail : westRidge.getRegisteredMails()) {
            if (mail.getMailman() == gamma) {
                gammaAssignments++;
            }
        }
        assertEquals("Gamma should have all 30 deliveries", 30, gammaAssignments);
        assertEquals("Only Gamma should remain", 1, westRidge.getMailmen().size());
        assertTrue("Gamma should be the only remaining mailman", westRidge.getMailmen().contains(gamma));
    }
}