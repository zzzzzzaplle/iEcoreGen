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
        // Initialize mailmen with unique IDs
        alice = new Mailman(); alice.setId("M001"); alice.setName("Alice");
        bob = new Mailman(); bob.setId("M002"); bob.setName("Bob");
        charlie = new Mailman(); charlie.setId("M003"); charlie.setName("Charlie");
        xavier = new Mailman(); xavier.setId("M004"); xavier.setName("Xavier");
        yvonne = new Mailman(); yvonne.setId("M005"); yvonne.setName("Yvonne");
        zack = new Mailman(); zack.setId("M006"); zack.setName("Zack");
        paul = new Mailman(); paul.setId("M007"); paul.setName("Paul");
        quinn = new Mailman(); quinn.setId("M008"); quinn.setName("Quinn");
        mario = new Mailman(); mario.setId("M009"); mario.setName("Mario");
        luigi = new Mailman(); luigi.setId("M010"); luigi.setName("Luigi");
        toad = new Mailman(); toad.setId("M011"); toad.setName("Toad");
        alpha = new Mailman(); alpha.setId("M012"); alpha.setName("Alpha");
        beta = new Mailman(); beta.setId("M013"); beta.setName("Beta");
        gamma = new Mailman(); gamma.setId("M014"); gamma.setName("Gamma");
        
        // Initialize inhabitants with unique IDs
        david = new Inhabitant(); david.setId("I001"); david.setName("David");
        eve = new Inhabitant(); eve.setId("I002"); eve.setName("Eve");
        frank = new Inhabitant(); frank.setId("I003"); frank.setName("Frank");
        walter = new Inhabitant(); walter.setId("I004"); walter.setName("Walter");
        rachel = new Inhabitant(); rachel.setId("I005"); rachel.setName("Rachel");
        peach = new Inhabitant(); peach.setId("I006"); peach.setName("Peach");
        bowser = new Inhabitant(); bowser.setId("I007"); bowser.setName("Bowser");
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // Set up CentralDistrict area
        centralDistrict = new GeographicalArea();
        centralDistrict.setName("CentralDistrict");
        
        // Add mailmen
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add inhabitants
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create mail items
        Letter letter1 = new Letter(); letter1.setTrackingNumber("L001"); letter1.setAddressee(david);
        Parcel parcel1 = new Parcel(); parcel1.setTrackingNumber("P001"); parcel1.setAddressee(eve);
        Letter letter2 = new Letter(); letter2.setTrackingNumber("L002"); letter2.setAddressee(frank);
        Parcel parcel2 = new Parcel(); parcel2.setTrackingNumber("P002"); parcel2.setAddressee(eve);
        Letter letter3 = new Letter(); letter3.setTrackingNumber("L003"); letter3.setAddressee(david);
        
        // Add mail to area
        centralDistrict.addRegisteredMail(letter1);
        centralDistrict.addRegisteredMail(parcel1);
        centralDistrict.addRegisteredMail(letter2);
        centralDistrict.addRegisteredMail(parcel2);
        centralDistrict.addRegisteredMail(letter3);
        
        // Assign mail to mailmen
        centralDistrict.assignMail(letter1, alice);
        centralDistrict.assignMail(parcel1, bob);
        centralDistrict.assignMail(letter2, charlie);
        centralDistrict.assignMail(parcel2, alice);
        centralDistrict.assignMail(letter3, bob);
        
        // Remove Alice with Bob as replacement
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Verify removal was successful
        assertTrue("Removal should succeed", result);
        
        // Verify Alice is removed
        assertFalse("Alice should be removed", centralDistrict.getMailmen().contains(alice));
        
        // Verify Bob has 4 deliveries (original 2 + reassigned 2 from Alice)
        List<DeliveryInfo> deliveries = centralDistrict.getAllDeliveries();
        int bobDeliveries = 0;
        int charlieDeliveries = 0;
        
        for (DeliveryInfo delivery : deliveries) {
            if (delivery.getMailman().equals(bob)) {
                bobDeliveries++;
            } else if (delivery.getMailman().equals(charlie)) {
                charlieDeliveries++;
            }
        }
        
        assertEquals("Bob should have 4 deliveries", 4, bobDeliveries);
        assertEquals("Charlie should have 1 delivery", 1, charlieDeliveries);
        
        // Verify specific reassignments
        boolean letter1Reassigned = false;
        boolean parcel2Reassigned = false;
        
        for (DeliveryInfo delivery : deliveries) {
            if (delivery.getMail().equals(letter1) && delivery.getMailman().equals(bob)) {
                letter1Reassigned = true;
            }
            if (delivery.getMail().equals(parcel2) && delivery.getMailman().equals(bob)) {
                parcel2Reassigned = true;
            }
        }
        
        assertTrue("Letter1 should be reassigned to Bob", letter1Reassigned);
        assertTrue("Parcel2 should be reassigned to Bob", parcel2Reassigned);
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Set up NorthQuarter area
        northQuarter = new GeographicalArea();
        northQuarter.setName("NorthQuarter");
        
        // Add mailmen
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add inhabitant
        northQuarter.addInhabitant(walter);
        
        // Create and assign 5 letters to Xavier
        for (int i = 1; i <= 5; i++) {
            Letter letter = new Letter();
            letter.setTrackingNumber("LX" + i);
            letter.setAddressee(walter);
            northQuarter.addRegisteredMail(letter);
            northQuarter.assignMail(letter, xavier);
        }
        
        // Create and assign 3 parcels to Yvonne
        for (int i = 1; i <= 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setTrackingNumber("PY" + i);
            parcel.setAddressee(walter);
            northQuarter.addRegisteredMail(parcel);
            northQuarter.assignMail(parcel, yvonne);
        }
        
        // Create and assign 2 letters to Zack
        for (int i = 1; i <= 2; i++) {
            Letter letter = new Letter();
            letter.setTrackingNumber("LZ" + i);
            letter.setAddressee(walter);
            northQuarter.addRegisteredMail(letter);
            northQuarter.assignMail(letter, zack);
        }
        
        // Remove Yvonne with Xavier as replacement
        boolean firstRemoval = northQuarter.removeMailman(yvonne, xavier);
        assertTrue("First removal should succeed", firstRemoval);
        
        // Verify Yvonne is removed and Xavier has 8 deliveries (5 original + 3 from Yvonne)
        assertFalse("Yvonne should be removed", northQuarter.getMailmen().contains(yvonne));
        
        List<DeliveryInfo> deliveriesAfterFirst = northQuarter.getAllDeliveries();
        int xavierDeliveries1 = 0;
        int zackDeliveries1 = 0;
        
        for (DeliveryInfo delivery : deliveriesAfterFirst) {
            if (delivery.getMailman().equals(xavier)) {
                xavierDeliveries1++;
            } else if (delivery.getMailman().equals(zack)) {
                zackDeliveries1++;
            }
        }
        
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xavierDeliveries1);
        assertEquals("Zack should have 2 deliveries after first removal", 2, zackDeliveries1);
        
        // Remove Xavier with Zack as replacement
        boolean secondRemoval = northQuarter.removeMailman(xavier, zack);
        assertTrue("Second removal should succeed", secondRemoval);
        
        // Verify Xavier is removed and Zack has all 10 deliveries
        assertFalse("Xavier should be removed", northQuarter.getMailmen().contains(xavier));
        
        List<DeliveryInfo> finalDeliveries = northQuarter.getAllDeliveries();
        int zackDeliveries2 = 0;
        
        for (DeliveryInfo delivery : finalDeliveries) {
            if (delivery.getMailman().equals(zack)) {
                zackDeliveries2++;
            }
        }
        
        assertEquals("Zack should have all 10 deliveries", 10, zackDeliveries2);
        assertEquals("Only Zack should remain", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Set up SouthEnd area
        southEnd = new GeographicalArea();
        southEnd.setName("SouthEnd");
        
        // Add mailmen
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add inhabitant
        southEnd.addInhabitant(rachel);
        
        // Create and assign letter
        Letter letter1 = new Letter();
        letter1.setTrackingNumber("L001");
        letter1.setAddressee(rachel);
        southEnd.addRegisteredMail(letter1);
        southEnd.assignMail(letter1, paul);
        
        // First removal: Paul with Quinn as replacement (should succeed)
        boolean firstRemoval = southEnd.removeMailman(paul, quinn);
        assertTrue("First removal should succeed", firstRemoval);
        
        // Verify Paul is removed and Quinn has the delivery
        assertFalse("Paul should be removed", southEnd.getMailmen().contains(paul));
        assertEquals("Only Quinn should remain", 1, southEnd.getMailmen().size());
        
        List<DeliveryInfo> deliveries = southEnd.getAllDeliveries();
        assertEquals("Should have 1 delivery", 1, deliveries.size());
        assertEquals("Delivery should be assigned to Quinn", quinn, deliveries.get(0).getMailman());
        
        // Second removal: Attempt to remove Quinn (last mailman) - should fail
        boolean secondRemoval = southEnd.removeMailman(quinn, paul); // Paul is no longer in area, but should fail anyway
        assertFalse("Removal of last mailman should fail", secondRemoval);
        
        // Verify Quinn is still there
        assertTrue("Quinn should still be in area", southEnd.getMailmen().contains(quinn));
        assertEquals("Still only Quinn should be present", 1, southEnd.getMailmen().size());
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Set up EastHaven area
        eastHaven = new GeographicalArea();
        eastHaven.setName("EastHaven");
        
        // Add mailmen
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add inhabitants
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 letters for Mario to deliver to Peach
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter();
            letter.setTrackingNumber("LM" + i);
            letter.setAddressee(peach);
            eastHaven.addRegisteredMail(letter);
            eastHaven.assignMail(letter, mario);
        }
        
        // Create 5 parcels for Luigi to deliver to Bowser
        for (int i = 1; i <= 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setTrackingNumber("PL" + i);
            parcel.setAddressee(bowser);
            eastHaven.addRegisteredMail(parcel);
            eastHaven.assignMail(parcel, luigi);
        }
        
        // 1. Attempt to add duplicate Mario (should fail)
        boolean duplicateAdd = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", duplicateAdd);
        
        // 2. Remove Mario with Luigi as replacement (should succeed)
        boolean firstRemoval = eastHaven.removeMailman(mario, luigi);
        assertTrue("First removal should succeed", firstRemoval);
        
        // 3. Verify Luigi now has 15 deliveries (10 from Mario + 5 original)
        List<DeliveryInfo> deliveries = eastHaven.getAllDeliveries();
        int luigiDeliveries = 0;
        
        for (DeliveryInfo delivery : deliveries) {
            if (delivery.getMailman().equals(luigi)) {
                luigiDeliveries++;
            }
        }
        
        assertEquals("Luigi should have 15 deliveries", 15, luigiDeliveries);
        
        // 4. Attempt to remove Luigi (last mailman) - should fail
        boolean secondRemovalAttempt = eastHaven.removeMailman(luigi, mario); // Mario is no longer in area
        assertFalse("Removal of last mailman should fail", secondRemovalAttempt);
        
        // 5. Add Toad (should succeed)
        boolean addToad = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should succeed", addToad);
        
        // 6. Remove Luigi with Toad as replacement (should succeed)
        boolean finalRemoval = eastHaven.removeMailman(luigi, toad);
        assertTrue("Final removal should succeed", finalRemoval);
        
        // Verify final state: only Toad remains with all 15 deliveries
        assertFalse("Luigi should be removed", eastHaven.getMailmen().contains(luigi));
        assertTrue("Toad should be present", eastHaven.getMailmen().contains(toad));
        assertEquals("Only Toad should remain", 1, eastHaven.getMailmen().size());
        
        List<DeliveryInfo> finalDeliveries = eastHaven.getAllDeliveries();
        int toadDeliveries = 0;
        
        for (DeliveryInfo delivery : finalDeliveries) {
            if (delivery.getMailman().equals(toad)) {
                toadDeliveries++;
            }
        }
        
        assertEquals("Toad should have all 15 deliveries", 15, toadDeliveries);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Set up WestRidge area
        westRidge = new GeographicalArea();
        westRidge.setName("WestRidge");
        
        // Add mailmen
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant();
            inhabitants[i].setId("I" + (100 + i));
            inhabitants[i].setName("Inhabitant" + (i + 1));
            westRidge.addInhabitant(inhabitants[i]);
        }
        
        // Create 30 mail items (10 each for Alpha, Beta, Gamma)
        int mailCounter = 1;
        
        // Alpha's 10 mail items
        for (int i = 0; i < 10; i++) {
            if (mailCounter % 2 == 0) {
                Letter letter = new Letter();
                letter.setTrackingNumber("L" + mailCounter);
                letter.setAddressee(inhabitants[i % 10]);
                westRidge.addRegisteredMail(letter);
                westRidge.assignMail(letter, alpha);
            } else {
                Parcel parcel = new Parcel();
                parcel.setTrackingNumber("P" + mailCounter);
                parcel.setAddressee(inhabitants[i % 10]);
                westRidge.addRegisteredMail(parcel);
                westRidge.assignMail(parcel, alpha);
            }
            mailCounter++;
        }
        
        // Beta's 10 mail items
        for (int i = 0; i < 10; i++) {
            if (mailCounter % 2 == 0) {
                Letter letter = new Letter();
                letter.setTrackingNumber("L" + mailCounter);
                letter.setAddressee(inhabitants[i % 10]);
                westRidge.addRegisteredMail(letter);
                westRidge.assignMail(letter, beta);
            } else {
                Parcel parcel = new Parcel();
                parcel.setTrackingNumber("P" + mailCounter);
                parcel.setAddressee(inhabitants[i % 10]);
                westRidge.addRegisteredMail(parcel);
                westRidge.assignMail(parcel, beta);
            }
            mailCounter++;
        }
        
        // Gamma's 10 mail items
        for (int i = 0; i < 10; i++) {
            if (mailCounter % 2 == 0) {
                Letter letter = new Letter();
                letter.setTrackingNumber("L" + mailCounter);
                letter.setAddressee(inhabitants[i % 10]);
                westRidge.addRegisteredMail(letter);
                westRidge.assignMail(letter, gamma);
            } else {
                Parcel parcel = new Parcel();
                parcel.setTrackingNumber("P" + mailCounter);
                parcel.setAddressee(inhabitants[i % 10]);
                westRidge.addRegisteredMail(parcel);
                westRidge.assignMail(parcel, gamma);
            }
            mailCounter++;
        }
        
        // Verify initial state: 3 mailmen with 10 deliveries each
        List<DeliveryInfo> initialDeliveries = westRidge.getAllDeliveries();
        assertEquals("Should have 30 deliveries initially", 30, initialDeliveries.size());
        
        int alphaInitial = 0, betaInitial = 0, gammaInitial = 0;
        for (DeliveryInfo delivery : initialDeliveries) {
            if (delivery.getMailman().equals(alpha)) alphaInitial++;
            else if (delivery.getMailman().equals(beta)) betaInitial++;
            else if (delivery.getMailman().equals(gamma)) gammaInitial++;
        }
        
        assertEquals("Alpha should have 10 deliveries initially", 10, alphaInitial);
        assertEquals("Beta should have 10 deliveries initially", 10, betaInitial);
        assertEquals("Gamma should have 10 deliveries initially", 10, gammaInitial);
        
        // 1. Remove Alpha with Beta as replacement (should succeed)
        boolean firstRemoval = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal should succeed", firstRemoval);
        
        // 2. Remove Beta with Gamma as replacement (should succeed)
        boolean secondRemoval = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal should succeed", secondRemoval);
        
        // Verify final state: only Gamma remains with all 30 deliveries
        assertFalse("Alpha should be removed", westRidge.getMailmen().contains(alpha));
        assertFalse("Beta should be removed", westRidge.getMailmen().contains(beta));
        assertTrue("Gamma should be present", westRidge.getMailmen().contains(gamma));
        assertEquals("Only Gamma should remain", 1, westRidge.getMailmen().size());
        
        List<DeliveryInfo> finalDeliveries = westRidge.getAllDeliveries();
        int gammaFinal = 0;
        for (DeliveryInfo delivery : finalDeliveries) {
            if (delivery.getMailman().equals(gamma)) {
                gammaFinal++;
            }
        }
        
        assertEquals("Gamma should have all 30 deliveries", 30, gammaFinal);
        assertEquals("Should still have 30 total deliveries", 30, finalDeliveries.size());
    }
}