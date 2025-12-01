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
        // Initialize all objects needed for tests
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
        
        // Create mailmen
        alice = new Mailman();
        alice.setId("M001");
        alice.setName("Alice");
        
        bob = new Mailman();
        bob.setId("M002");
        bob.setName("Bob");
        
        charlie = new Mailman();
        charlie.setId("M003");
        charlie.setName("Charlie");
        
        xavier = new Mailman();
        xavier.setId("M004");
        xavier.setName("Xavier");
        
        yvonne = new Mailman();
        yvonne.setId("M005");
        yvonne.setName("Yvonne");
        
        zack = new Mailman();
        zack.setId("M006");
        zack.setName("Zack");
        
        paul = new Mailman();
        paul.setId("M007");
        paul.setName("Paul");
        
        quinn = new Mailman();
        quinn.setId("M008");
        quinn.setName("Quinn");
        
        mario = new Mailman();
        mario.setId("M009");
        mario.setName("Mario");
        
        luigi = new Mailman();
        luigi.setId("M010");
        luigi.setName("Luigi");
        
        toad = new Mailman();
        toad.setId("M011");
        toad.setName("Toad");
        
        alpha = new Mailman();
        alpha.setId("M012");
        alpha.setName("Alpha");
        
        beta = new Mailman();
        beta.setId("M013");
        beta.setName("Beta");
        
        gamma = new Mailman();
        gamma.setId("M014");
        gamma.setName("Gamma");
        
        // Create inhabitants
        david = new Inhabitant();
        david.setId("I001");
        david.setName("David");
        david.setAddress("123 Main St");
        
        eve = new Inhabitant();
        eve.setId("I002");
        eve.setName("Eve");
        eve.setAddress("456 Oak Ave");
        
        frank = new Inhabitant();
        frank.setId("I003");
        frank.setName("Frank");
        frank.setAddress("789 Pine Rd");
        
        walter = new Inhabitant();
        walter.setId("I004");
        walter.setName("Walter");
        walter.setAddress("101 Elm St");
        
        rachel = new Inhabitant();
        rachel.setId("I005");
        rachel.setName("Rachel");
        rachel.setAddress("202 Maple Dr");
        
        peach = new Inhabitant();
        peach.setId("I006");
        peach.setName("Peach");
        peach.setAddress("303 Cherry Ln");
        
        bowser = new Inhabitant();
        bowser.setId("I007");
        bowser.setName("Bowser");
        bowser.setAddress("404 Castle Rd");
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp
        centralDistrict.setAreaCode("CD001");
        centralDistrict.setAreaName("CentralDistrict");
        
        // Add mailmen
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add inhabitants
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create mail items
        Letter letter1 = new Letter();
        letter1.setTrackingNumber("L001");
        letter1.setWeight(0.1);
        letter1.setUrgent(true);
        
        Parcel parcel1 = new Parcel();
        parcel1.setTrackingNumber("P001");
        parcel1.setWeight(2.5);
        parcel1.setDimensions(30.0);
        parcel1.setRequiresSignature(true);
        
        Letter letter2 = new Letter();
        letter2.setTrackingNumber("L002");
        letter2.setWeight(0.2);
        letter2.setUrgent(false);
        
        Parcel parcel2 = new Parcel();
        parcel2.setTrackingNumber("P002");
        parcel2.setWeight(1.8);
        parcel2.setDimensions(25.0);
        parcel2.setRequiresSignature(false);
        
        Letter letter3 = new Letter();
        letter3.setTrackingNumber("L003");
        letter3.setWeight(0.15);
        letter3.setUrgent(true);
        
        // Assign mail items
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Alice removal should be successful", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen", centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobMails = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have assigned mail", bobMails);
        assertEquals("Bob should have 4 mail items", 4, bobMails.size());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        boolean hasParcel1 = false;
        boolean hasLetter3 = false;
        boolean hasLetter1 = false;
        boolean hasParcel2 = false;
        
        for (RegisteredMail mail : bobMails) {
            if (mail.getTrackingNumber().equals("P001")) hasParcel1 = true;
            if (mail.getTrackingNumber().equals("L003")) hasLetter3 = true;
            if (mail.getTrackingNumber().equals("L001")) hasLetter1 = true;
            if (mail.getTrackingNumber().equals("P002")) hasParcel2 = true;
        }
        
        assertTrue("Bob should still have Parcel1", hasParcel1);
        assertTrue("Bob should still have Letter3", hasLetter3);
        assertTrue("Bob should have Letter1 reassigned", hasLetter1);
        assertTrue("Bob should have Parcel2 reassigned", hasParcel2);
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieMails = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have assigned mail", charlieMails);
        assertEquals("Charlie should have 1 mail item", 1, charlieMails.size());
        assertEquals("Charlie should have Letter2", "L002", charlieMails.get(0).getTrackingNumber());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        northQuarter.setAreaCode("NQ001");
        northQuarter.setAreaName("NorthQuarter");
        
        // Add mailmen
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add inhabitant
        northQuarter.addInhabitant(walter);
        
        // Create and assign 5 Letters (Xavier→Walter)
        List<Letter> xavierLetters = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Letter letter = new Letter();
            letter.setTrackingNumber("XL" + i);
            letter.setWeight(0.1 + i * 0.05);
            letter.setUrgent(i % 2 == 0);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
            xavierLetters.add(letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        List<Parcel> yvonneParcels = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setTrackingNumber("YP" + i);
            parcel.setWeight(1.0 + i * 0.5);
            parcel.setDimensions(20.0 + i * 5.0);
            parcel.setRequiresSignature(i % 2 == 1);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
            yvonneParcels.add(parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        List<Letter> zackLetters = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Letter letter = new Letter();
            letter.setTrackingNumber("ZL" + i);
            letter.setWeight(0.15 + i * 0.05);
            letter.setUrgent(true);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
            zackLetters.add(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal (Yvonne) should be successful", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal (Xavier) should be successful", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zackMails = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have assigned mail", zackMails);
        assertEquals("Zack should have all 10 deliveries", 10, zackMails.size());
        
        // Verify Yvonne and Xavier are removed
        assertFalse("Yvonne should be removed", northQuarter.getMailmen().contains(yvonne));
        assertFalse("Xavier should be removed", northQuarter.getMailmen().contains(xavier));
        
        // Verify Zack is still there
        assertTrue("Zack should remain", northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        southEnd.setAreaCode("SE001");
        southEnd.setAreaName("SouthEnd");
        
        // Add mailmen
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add inhabitant
        southEnd.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        letter1.setTrackingNumber("L001");
        letter1.setWeight(0.1);
        letter1.setUrgent(true);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Actions & Expected Outputs:
        
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("Removing Paul with Quinn as replacement should succeed", result1);
        assertFalse("Paul should be removed", southEnd.getMailmen().contains(paul));
        
        // Verify reassignment
        List<RegisteredMail> quinnMails = southEnd.listRegisteredMail(quinn);
        assertNotNull("Quinn should have assigned mail", quinnMails);
        assertEquals("Quinn should have 1 mail item", 1, quinnMails.size());
        assertEquals("Quinn should have Letter1", "L001", quinnMails.get(0).getTrackingNumber());
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, quinn); // Invalid case - same mailman
        assertFalse("Removing last mailman with same replacement should fail", result2);
        
        boolean result3 = southEnd.removeMailman(quinn, paul); // Invalid case - paul doesn't exist anymore
        assertFalse("Removing last mailman should fail", result3);
        
        // Verify Quinn is still there
        assertTrue("Quinn should remain as the only mailman", southEnd.getMailmen().contains(quinn));
        assertEquals("Should still have 1 mailman", 1, southEnd.getMailmen().size());
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        eastHaven.setAreaCode("EH001");
        eastHaven.setAreaName("EastHaven");
        
        // Add mailmen
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add inhabitants
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter();
            letter.setTrackingNumber("ML" + i);
            letter.setWeight(0.1 + i * 0.02);
            letter.setUrgent(i % 3 == 0);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 1; i <= 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setTrackingNumber("LP" + i);
            parcel.setWeight(1.5 + i * 0.3);
            parcel.setDimensions(15.0 + i * 2.0);
            parcel.setRequiresSignature(i % 2 == 1);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // Procedure:
        
        // 1. Add duplicate "Mario" → false
        Mailman marioDuplicate = new Mailman();
        marioDuplicate.setId("M009"); // Same ID as original Mario
        marioDuplicate.setName("Mario");
        boolean result1 = eastHaven.addMailman(marioDuplicate);
        assertFalse("Adding duplicate Mario should fail", result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removing Mario with Luigi as replacement should succeed", result2);
        assertFalse("Mario should be removed", eastHaven.getMailmen().contains(mario));
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMails = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have assigned mail", luigiMails);
        assertEquals("Luigi should have 15 mail items", 15, luigiMails.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // mario doesn't exist anymore
        assertFalse("Removing last mailman should fail", result3);
        
        // 5. Add "Toad" → true
        boolean result4 = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should succeed", result4);
        assertTrue("Toad should be in mailmen list", eastHaven.getMailmen().contains(toad));
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removing Luigi with Toad as replacement should succeed", result5);
        assertFalse("Luigi should be removed", eastHaven.getMailmen().contains(luigi));
        
        // Verify Toad has all 15 deliveries
        List<RegisteredMail> toadMails = eastHaven.listRegisteredMail(toad);
        assertNotNull("Toad should have assigned mail", toadMails);
        assertEquals("Toad should have all 15 mail items", 15, toadMails.size());
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        westRidge.setAreaCode("WR001");
        westRidge.setAreaName("WestRidge");
        
        // Add mailmen
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setId("IW" + i);
            inhabitant.setName("Inhabitant" + i);
            inhabitant.setAddress("Address" + i);
            westRidge.addInhabitant(inhabitant);
            inhabitants.add(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCounter = 1;
        
        // Alpha's 10 mail items
        for (int i = 0; i < 10; i++) {
            if (mailCounter % 2 == 0) {
                Letter letter = new Letter();
                letter.setTrackingNumber("MA" + mailCounter);
                letter.setWeight(0.1 + (mailCounter * 0.01));
                letter.setUrgent(mailCounter % 3 == 0);
                westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(i % 10), letter);
            } else {
                Parcel parcel = new Parcel();
                parcel.setTrackingNumber("MA" + mailCounter);
                parcel.setWeight(1.0 + (mailCounter * 0.1));
                parcel.setDimensions(20.0 + (mailCounter * 2.0));
                parcel.setRequiresSignature(mailCounter % 4 == 0);
                westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(i % 10), parcel);
            }
            mailCounter++;
        }
        
        // Beta's 10 mail items
        for (int i = 0; i < 10; i++) {
            if (mailCounter % 2 == 0) {
                Letter letter = new Letter();
                letter.setTrackingNumber("MB" + mailCounter);
                letter.setWeight(0.1 + (mailCounter * 0.01));
                letter.setUrgent(mailCounter % 3 == 0);
                westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(i % 10), letter);
            } else {
                Parcel parcel = new Parcel();
                parcel.setTrackingNumber("MB" + mailCounter);
                parcel.setWeight(1.0 + (mailCounter * 0.1));
                parcel.setDimensions(20.0 + (mailCounter * 2.0));
                parcel.setRequiresSignature(mailCounter % 4 == 0);
                westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(i % 10), parcel);
            }
            mailCounter++;
        }
        
        // Gamma's 10 mail items
        for (int i = 0; i < 10; i++) {
            if (mailCounter % 2 == 0) {
                Letter letter = new Letter();
                letter.setTrackingNumber("MG" + mailCounter);
                letter.setWeight(0.1 + (mailCounter * 0.01));
                letter.setUrgent(mailCounter % 3 == 0);
                westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(i % 10), letter);
            } else {
                Parcel parcel = new Parcel();
                parcel.setTrackingNumber("MG" + mailCounter);
                parcel.setWeight(1.0 + (mailCounter * 0.1));
                parcel.setDimensions(20.0 + (mailCounter * 2.0));
                parcel.setRequiresSignature(mailCounter % 4 == 0);
                westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(i % 10), parcel);
            }
            mailCounter++;
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("Removing Alpha with Beta as replacement should succeed", result1);
        assertFalse("Alpha should be removed", westRidge.getMailmen().contains(alpha));
        
        // Verify Beta now has 20 deliveries
        List<RegisteredMail> betaMailsAfterFirstRemoval = westRidge.listRegisteredMail(beta);
        assertNotNull("Beta should have assigned mail", betaMailsAfterFirstRemoval);
        assertEquals("Beta should have 20 mail items after first removal", 20, betaMailsAfterFirstRemoval.size());
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Removing Beta with Gamma as replacement should succeed", result2);
        assertFalse("Beta should be removed", westRidge.getMailmen().contains(beta));
        
        // Verifications:
        // Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaMails = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have assigned mail", gammaMails);
        assertEquals("Gamma should have all 30 deliveries", 30, gammaMails.size());
        
        // Verify Alpha and Beta are removed
        assertFalse("Alpha should be removed", westRidge.getMailmen().contains(alpha));
        assertFalse("Beta should be removed", westRidge.getMailmen().contains(beta));
        
        // Verify Gamma is the only remaining mailman
        assertTrue("Gamma should remain", westRidge.getMailmen().contains(gamma));
        assertEquals("Should have only 1 mailman remaining", 1, westRidge.getMailmen().size());
    }
}