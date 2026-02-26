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
    
    private Mailman alice, bob, charlie;
    private Mailman xavier, yvonne, zack;
    private Mailman paul, quinn;
    private Mailman mario, luigi, toad;
    private Mailman alpha, beta, gamma;
    
    private Inhabitant david, eve, frank;
    private Inhabitant walter;
    private Inhabitant rachel;
    private Inhabitant peach, bowser;
    
    @Before
    public void setUp() {
        // Test Case 1 setup
        centralDistrict = new GeographicalArea("CentralDistrict");
        alice = new Mailman("Alice");
        bob = new Mailman("Bob");
        charlie = new Mailman("Charlie");
        david = new Inhabitant("David");
        eve = new Inhabitant("Eve");
        frank = new Inhabitant("Frank");
        
        // Test Case 2 setup
        northQuarter = new GeographicalArea("NorthQuarter");
        xavier = new Mailman("Xavier");
        yvonne = new Mailman("Yvonne");
        zack = new Mailman("Zack");
        walter = new Inhabitant("Walter");
        
        // Test Case 3 setup
        southEnd = new GeographicalArea("SouthEnd");
        paul = new Mailman("Paul");
        quinn = new Mailman("Quinn");
        rachel = new Inhabitant("Rachel");
        
        // Test Case 4 setup
        eastHaven = new GeographicalArea("EastHaven");
        mario = new Mailman("Mario");
        luigi = new Mailman("Luigi");
        toad = new Mailman("Toad");
        peach = new Inhabitant("Peach");
        bowser = new Inhabitant("Bowser");
        
        // Test Case 5 setup
        westRidge = new GeographicalArea("WestRidge");
        alpha = new Mailman("Alpha");
        beta = new Mailman("Beta");
        gamma = new Mailman("Gamma");
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // Setup
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create mail items
        Letter letter1 = new Letter("L1", david, "Content1");
        Parcel parcel1 = new Parcel("P1", eve, 2.5);
        Letter letter2 = new Letter("L2", frank, "Content2");
        Parcel parcel2 = new Parcel("P2", eve, 1.8);
        Letter letter3 = new Letter("L3", david, "Content3");
        
        // Assign mail items
        centralDistrict.assignMail(letter1, alice);
        centralDistrict.assignMail(parcel1, bob);
        centralDistrict.assignMail(letter2, charlie);
        centralDistrict.assignMail(parcel2, alice);
        centralDistrict.assignMail(letter3, bob);
        
        // Action: Remove Alice with Bob as replacement
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Alice removal should succeed", result);
        
        // Verifications
        assertFalse("Alice should be removed", centralDistrict.getMailmen().contains(alice));
        assertTrue("Bob should still be present", centralDistrict.getMailmen().contains(bob));
        assertTrue("Charlie should still be present", centralDistrict.getMailmen().contains(charlie));
        
        // Check reassignments
        List<DeliveryRecord> deliveries = centralDistrict.retrieveDeliveries();
        int bobDeliveries = 0;
        int charlieDeliveries = 0;
        
        for (DeliveryRecord delivery : deliveries) {
            if (delivery.getMailman().equals(bob)) {
                bobDeliveries++;
            } else if (delivery.getMailman().equals(charlie)) {
                charlieDeliveries++;
            }
        }
        
        // Bob should have 4 items (original 2 + reassigned 2 from Alice)
        assertEquals("Bob should have 4 deliveries", 4, bobDeliveries);
        // Charlie should have 1 item (unchanged)
        assertEquals("Charlie should have 1 delivery", 1, charlieDeliveries);
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Setup
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        northQuarter.addInhabitant(walter);
        
        // Create and assign 5 letters to Xavier
        for (int i = 1; i <= 5; i++) {
            Letter letter = new Letter("XL" + i, walter, "Content" + i);
            northQuarter.assignMail(letter, xavier);
        }
        
        // Create and assign 3 parcels to Yvonne
        for (int i = 1; i <= 3; i++) {
            Parcel parcel = new Parcel("YP" + i, walter, i * 1.5);
            northQuarter.assignMail(parcel, yvonne);
        }
        
        // Create and assign 2 letters to Zack
        for (int i = 1; i <= 2; i++) {
            Letter letter = new Letter("ZL" + i, walter, "Content" + i);
            northQuarter.assignMail(letter, zack);
        }
        
        // First removal: Remove Yvonne with Xavier as replacement
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        assertTrue("First removal should succeed", result1);
        assertFalse("Yvonne should be removed", northQuarter.getMailmen().contains(yvonne));
        
        // Verify Xavier now has 8 items (5 original + 3 from Yvonne)
        List<DeliveryRecord> deliveriesAfterFirst = northQuarter.retrieveDeliveries();
        int xavierDeliveries1 = 0;
        int zackDeliveries1 = 0;
        
        for (DeliveryRecord delivery : deliveriesAfterFirst) {
            if (delivery.getMailman().equals(xavier)) {
                xavierDeliveries1++;
            } else if (delivery.getMailman().equals(zack)) {
                zackDeliveries1++;
            }
        }
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xavierDeliveries1);
        assertEquals("Zack should have 2 deliveries after first removal", 2, zackDeliveries1);
        
        // Second removal: Remove Xavier with Zack as replacement
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        assertTrue("Second removal should succeed", result2);
        assertFalse("Xavier should be removed", northQuarter.getMailmen().contains(xavier));
        
        // Verify Zack now has all 10 items
        List<DeliveryRecord> finalDeliveries = northQuarter.retrieveDeliveries();
        int zackFinalDeliveries = 0;
        
        for (DeliveryRecord delivery : finalDeliveries) {
            if (delivery.getMailman().equals(zack)) {
                zackFinalDeliveries++;
            }
        }
        assertEquals("Zack should have all 10 deliveries", 10, zackFinalDeliveries);
        assertEquals("Only Zack should remain", 1, northQuarter.getMailmen().size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Setup
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        southEnd.addInhabitant(rachel);
        
        Letter letter1 = new Letter("L1", rachel, "Content");
        southEnd.assignMail(letter1, paul);
        
        // First attempt: Remove Paul specifying Quinn (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("First removal should succeed", result1);
        assertFalse("Paul should be removed", southEnd.getMailmen().contains(paul));
        
        // Second attempt: Remove Quinn (last mailman) - should fail
        boolean result2 = southEnd.removeMailman(quinn, null);
        assertFalse("Removal of last mailman should fail", result2);
        assertTrue("Quinn should still be present", southEnd.getMailmen().contains(quinn));
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Setup
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 letters for Mario→Peach
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter("ML" + i, peach, "Content" + i);
            eastHaven.assignMail(letter, mario);
        }
        
        // Create 5 parcels for Luigi→Bowser
        for (int i = 1; i <= 5; i++) {
            Parcel parcel = new Parcel("LP" + i, bowser, i * 2.0);
            eastHaven.assignMail(parcel, luigi);
        }
        
        // Step 1: Add duplicate Mario → false
        Mailman marioDuplicate = new Mailman("Mario");
        boolean addDuplicateResult = eastHaven.addMailman(marioDuplicate);
        assertFalse("Adding duplicate mailman should fail", addDuplicateResult);
        
        // Step 2: Remove Mario (specify Luigi) → true
        boolean removeMarioResult = eastHaven.removeMailman(mario, luigi);
        assertTrue("Mario removal should succeed", removeMarioResult);
        assertFalse("Mario should be removed", eastHaven.getMailmen().contains(mario));
        
        // Step 3: Verify Luigi now has 15 deliveries (5 original + 10 from Mario)
        List<DeliveryRecord> deliveriesAfterMarioRemoval = eastHaven.retrieveDeliveries();
        int luigiDeliveries = 0;
        for (DeliveryRecord delivery : deliveriesAfterMarioRemoval) {
            if (delivery.getMailman().equals(luigi)) {
                luigiDeliveries++;
            }
        }
        assertEquals("Luigi should have 15 deliveries", 15, luigiDeliveries);
        
        // Step 4: Attempt remove Luigi → false (last mailman)
        boolean removeLuigiAttempt1 = eastHaven.removeMailman(luigi, null);
        assertFalse("Removal of last mailman should fail", removeLuigiAttempt1);
        assertTrue("Luigi should still be present", eastHaven.getMailmen().contains(luigi));
        
        // Step 5: Add "Toad" → true
        boolean addToadResult = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should succeed", addToadResult);
        
        // Step 6: Remove Luigi (specify Toad) → true
        boolean removeLuigiResult = eastHaven.removeMailman(luigi, toad);
        assertTrue("Luigi removal should succeed", removeLuigiResult);
        assertFalse("Luigi should be removed", eastHaven.getMailmen().contains(luigi));
        
        // Verify Toad has all deliveries
        List<DeliveryRecord> finalDeliveries = eastHaven.retrieveDeliveries();
        int toadDeliveries = 0;
        for (DeliveryRecord delivery : finalDeliveries) {
            if (delivery.getMailman().equals(toad)) {
                toadDeliveries++;
            }
        }
        assertEquals("Toad should have all 15 deliveries", 15, toadDeliveries);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Setup
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant("Inhabitant" + i);
            westRidge.addInhabitant(inhabitants[i]);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCounter = 1;
        
        // 10 items for Alpha
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter("A" + mailCounter++, inhabitants[i], "ContentA" + i);
            westRidge.assignMail(letter, alpha);
        }
        
        // 10 items for Beta
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel("B" + mailCounter++, inhabitants[i], i * 1.5);
            westRidge.assignMail(parcel, beta);
        }
        
        // 10 items for Gamma
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter("G" + mailCounter++, inhabitants[i], "ContentG" + i);
            westRidge.assignMail(letter, gamma);
        }
        
        // Step 1: Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal should succeed", result1);
        assertFalse("Alpha should be removed", westRidge.getMailmen().contains(alpha));
        
        // Verify Beta now has 20 items (10 original + 10 from Alpha)
        List<DeliveryRecord> deliveriesAfterFirst = westRidge.retrieveDeliveries();
        int betaDeliveries1 = 0;
        int gammaDeliveries1 = 0;
        
        for (DeliveryRecord delivery : deliveriesAfterFirst) {
            if (delivery.getMailman().equals(beta)) {
                betaDeliveries1++;
            } else if (delivery.getMailman().equals(gamma)) {
                gammaDeliveries1++;
            }
        }
        assertEquals("Beta should have 20 deliveries after first removal", 20, betaDeliveries1);
        assertEquals("Gamma should have 10 deliveries after first removal", 10, gammaDeliveries1);
        
        // Step 2: Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal should succeed", result2);
        assertFalse("Beta should be removed", westRidge.getMailmen().contains(beta));
        
        // Verify Gamma ends with all 30 deliveries
        List<DeliveryRecord> finalDeliveries = westRidge.retrieveDeliveries();
        int gammaFinalDeliveries = 0;
        
        for (DeliveryRecord delivery : finalDeliveries) {
            if (delivery.getMailman().equals(gamma)) {
                gammaFinalDeliveries++;
            }
        }
        assertEquals("Gamma should have all 30 deliveries", 30, gammaFinalDeliveries);
        assertEquals("Only Gamma should remain", 1, westRidge.getMailmen().size());
    }
}