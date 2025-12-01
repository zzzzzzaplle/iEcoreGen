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
    private Inhabitant david, eve, frank;
    
    private Mailman xavier, yvonne, zack;
    private Inhabitant walter;
    
    private Mailman paul, quinn;
    private Inhabitant rachel;
    
    private Mailman mario, luigi, toad;
    private Inhabitant peach, bowser;
    
    private Mailman alpha, beta, gamma;
    
    @Before
    public void setUp() {
        // Initialize all objects needed for tests
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
        
        // Mailmen for CentralDistrict
        alice = new Mailman();
        alice.setMailmanId("M001");
        alice.setName("Alice");
        
        bob = new Mailman();
        bob.setMailmanId("M002");
        bob.setName("Bob");
        
        charlie = new Mailman();
        charlie.setMailmanId("M003");
        charlie.setName("Charlie");
        
        // Inhabitants for CentralDistrict
        david = new Inhabitant();
        david.setInhabitantId("I001");
        david.setName("David");
        
        eve = new Inhabitant();
        eve.setInhabitantId("I002");
        eve.setName("Eve");
        
        frank = new Inhabitant();
        frank.setInhabitantId("I003");
        frank.setName("Frank");
        
        // Mailmen for NorthQuarter
        xavier = new Mailman();
        xavier.setMailmanId("M004");
        xavier.setName("Xavier");
        
        yvonne = new Mailman();
        yvonne.setMailmanId("M005");
        yvonne.setName("Yvonne");
        
        zack = new Mailman();
        zack.setMailmanId("M006");
        zack.setName("Zack");
        
        // Inhabitant for NorthQuarter
        walter = new Inhabitant();
        walter.setInhabitantId("I004");
        walter.setName("Walter");
        
        // Mailmen for SouthEnd
        paul = new Mailman();
        paul.setMailmanId("M007");
        paul.setName("Paul");
        
        quinn = new Mailman();
        quinn.setMailmanId("M008");
        quinn.setName("Quinn");
        
        // Inhabitant for SouthEnd
        rachel = new Inhabitant();
        rachel.setInhabitantId("I005");
        rachel.setName("Rachel");
        
        // Mailmen for EastHaven
        mario = new Mailman();
        mario.setMailmanId("M009");
        mario.setName("Mario");
        
        luigi = new Mailman();
        luigi.setMailmanId("M010");
        luigi.setName("Luigi");
        
        toad = new Mailman();
        toad.setMailmanId("M011");
        toad.setName("Toad");
        
        // Inhabitants for EastHaven
        peach = new Inhabitant();
        peach.setInhabitantId("I006");
        peach.setName("Peach");
        
        bowser = new Inhabitant();
        bowser.setInhabitantId("I007");
        bowser.setName("Bowser");
        
        // Mailmen for WestRidge
        alpha = new Mailman();
        alpha.setMailmanId("M012");
        alpha.setName("Alpha");
        
        beta = new Mailman();
        beta.setMailmanId("M013");
        beta.setName("Beta");
        
        gamma = new Mailman();
        gamma.setMailmanId("M014");
        gamma.setName("Gamma");
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // Set up CentralDistrict
        centralDistrict.setAreaId("CentralDistrict");
        
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
        letter1.setMailId("L001");
        letter1.setAddressee(david);
        letter1.setAssignedMailman(alice);
        centralDistrict.getRegisteredMails().add(letter1);
        
        Parcel parcel1 = new Parcel();
        parcel1.setMailId("P001");
        parcel1.setAddressee(eve);
        parcel1.setAssignedMailman(bob);
        centralDistrict.getRegisteredMails().add(parcel1);
        
        Letter letter2 = new Letter();
        letter2.setMailId("L002");
        letter2.setAddressee(frank);
        letter2.setAssignedMailman(charlie);
        centralDistrict.getRegisteredMails().add(letter2);
        
        Parcel parcel2 = new Parcel();
        parcel2.setMailId("P002");
        parcel2.setAddressee(eve);
        parcel2.setAssignedMailman(alice);
        centralDistrict.getRegisteredMails().add(parcel2);
        
        Letter letter3 = new Letter();
        letter3.setMailId("L003");
        letter3.setAddressee(david);
        letter3.setAssignedMailman(bob);
        centralDistrict.getRegisteredMails().add(letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Removal of Alice should succeed", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen list", 
                   centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        assertEquals("Bob should be assigned to letter1", bob, letter1.getAssignedMailman());
        assertEquals("Bob should be assigned to parcel2", bob, parcel2.getAssignedMailman());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertEquals("Bob should still be assigned to parcel1", bob, parcel1.getAssignedMailman());
        assertEquals("Bob should still be assigned to letter3", bob, letter3.getAssignedMailman());
        
        // Charlie's assignments unchanged (now has 1 item)
        assertEquals("Charlie should still be assigned to letter2", charlie, letter2.getAssignedMailman());
        
        // Verify Bob has 4 deliveries
        int bobDeliveries = 0;
        for (RegisteredMail mail : centralDistrict.getRegisteredMails()) {
            if (bob.equals(mail.getAssignedMailman())) {
                bobDeliveries++;
            }
        }
        assertEquals("Bob should have 4 deliveries", 4, bobDeliveries);
        
        // Verify Charlie has 1 delivery
        int charlieDeliveries = 0;
        for (RegisteredMail mail : centralDistrict.getRegisteredMails()) {
            if (charlie.equals(mail.getAssignedMailman())) {
                charlieDeliveries++;
            }
        }
        assertEquals("Charlie should have 1 delivery", 1, charlieDeliveries);
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Set up NorthQuarter
        northQuarter.setAreaId("NorthQuarter");
        
        // Add mailmen
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add inhabitant
        northQuarter.addInhabitant(walter);
        
        // Create 5 Letters (Xavier→Walter)
        for (int i = 1; i <= 5; i++) {
            Letter letter = new Letter();
            letter.setMailId("XL" + i);
            letter.setAddressee(walter);
            letter.setAssignedMailman(xavier);
            northQuarter.getRegisteredMails().add(letter);
        }
        
        // Create 3 Parcels (Yvonne→Walter)
        for (int i = 1; i <= 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setMailId("YP" + i);
            parcel.setAddressee(walter);
            parcel.setAssignedMailman(yvonne);
            northQuarter.getRegisteredMails().add(parcel);
        }
        
        // Create 2 Letters (Zack→Walter)
        for (int i = 1; i <= 2; i++) {
            Letter letter = new Letter();
            letter.setMailId("ZL" + i);
            letter.setAddressee(walter);
            letter.setAssignedMailman(zack);
            northQuarter.getRegisteredMails().add(letter);
        }
        
        // Action 1: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal (Yvonne) should succeed", result1);
        
        // Verification after first removal: 3 parcels moved to Xavier (now has 8 items)
        int xavierDeliveriesAfterFirst = 0;
        for (RegisteredMail mail : northQuarter.getRegisteredMails()) {
            if (xavier.equals(mail.getAssignedMailman())) {
                xavierDeliveriesAfterFirst++;
            }
        }
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xavierDeliveriesAfterFirst);
        
        // Action 2: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal (Xavier) should succeed", result2);
        
        // Final state verification: Only Zack remains with all 10 deliveries
        assertFalse("Xavier should be removed", northQuarter.getMailmen().contains(xavier));
        assertFalse("Yvonne should be removed", northQuarter.getMailmen().contains(yvonne));
        assertTrue("Zack should remain", northQuarter.getMailmen().contains(zack));
        
        int zackDeliveries = 0;
        for (RegisteredMail mail : northQuarter.getRegisteredMails()) {
            assertEquals("All deliveries should be assigned to Zack", zack, mail.getAssignedMailman());
            zackDeliveries++;
        }
        assertEquals("Zack should have all 10 deliveries", 10, zackDeliveries);
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Set up SouthEnd
        southEnd.setAreaId("SouthEnd");
        
        // Add mailmen
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add inhabitant
        southEnd.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        letter1.setMailId("L001");
        letter1.setAddressee(rachel);
        letter1.setAssignedMailman(paul);
        southEnd.getRegisteredMails().add(letter1);
        
        // Action 1: Attempt remove Paul specifying Quinn
        boolean result1 = southEnd.removeMailman(paul, quinn);
        
        // Expected Output: true (normal case)
        assertTrue("First removal (Paul) should succeed", result1);
        assertFalse("Paul should be removed", southEnd.getMailmen().contains(paul));
        assertEquals("Letter should be reassigned to Quinn", quinn, letter1.getAssignedMailman());
        
        // Action 2: Attempt remove Quinn (last mailman)
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in area
        
        // Expected Output: false
        assertFalse("Removal of last mailman should fail", result2);
        assertTrue("Quinn should remain as last mailman", southEnd.getMailmen().contains(quinn));
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Set up EastHaven
        eastHaven.setAreaId("EastHaven");
        
        // Add mailmen
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add inhabitants
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter();
            letter.setMailId("ML" + i);
            letter.setAddressee(peach);
            letter.setAssignedMailman(mario);
            eastHaven.getRegisteredMails().add(letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 1; i <= 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setMailId("LP" + i);
            parcel.setAddressee(bowser);
            parcel.setAssignedMailman(luigi);
            eastHaven.getRegisteredMails().add(parcel);
        }
        
        // Procedure 1: Add duplicate "Mario"
        boolean addDuplicateResult = eastHaven.addMailman(mario);
        
        // Expected Output: false
        assertFalse("Adding duplicate mailman should fail", addDuplicateResult);
        
        // Procedure 2: Remove Mario (specify Luigi)
        boolean removeMarioResult = eastHaven.removeMailman(mario, luigi);
        
        // Expected Output: true
        assertTrue("Removal of Mario should succeed", removeMarioResult);
        
        // Procedure 3: Verify Luigi now has 15 deliveries
        int luigiDeliveries = 0;
        for (RegisteredMail mail : eastHaven.getRegisteredMails()) {
            assertEquals("All deliveries should be assigned to Luigi", luigi, mail.getAssignedMailman());
            luigiDeliveries++;
        }
        assertEquals("Luigi should have 15 deliveries", 15, luigiDeliveries);
        
        // Procedure 4: Attempt remove Luigi → false (last mailman)
        boolean removeLuigiResult = eastHaven.removeMailman(luigi, mario); // mario is no longer in area
        
        // Expected Output: false
        assertFalse("Removal of last mailman should fail", removeLuigiResult);
        
        // Procedure 5: Add "Toad"
        boolean addToadResult = eastHaven.addMailman(toad);
        
        // Expected Output: true
        assertTrue("Adding Toad should succeed", addToadResult);
        
        // Procedure 6: Remove Luigi (specify Toad)
        boolean removeLuigiWithReplacementResult = eastHaven.removeMailman(luigi, toad);
        
        // Expected Output: true
        assertTrue("Removal of Luigi with replacement should succeed", removeLuigiWithReplacementResult);
        
        // Final verification: Toad should have all deliveries
        int toadDeliveries = 0;
        for (RegisteredMail mail : eastHaven.getRegisteredMails()) {
            assertEquals("All deliveries should be assigned to Toad", toad, mail.getAssignedMailman());
            toadDeliveries++;
        }
        assertEquals("Toad should have 15 deliveries", 15, toadDeliveries);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Set up WestRidge
        westRidge.setAreaId("WestRidge");
        
        // Add mailmen
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant();
            inhabitants[i].setInhabitantId("IW" + (i+1));
            inhabitants[i].setName("Inhabitant" + (i+1));
            westRidge.addInhabitant(inhabitants[i]);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCounter = 1;
        
        // 10 items for Alpha
        for (int i = 0; i < 10; i++) {
            if (mailCounter % 2 == 0) {
                Letter letter = new Letter();
                letter.setMailId("MAIL" + mailCounter);
                letter.setAddressee(inhabitants[i % 10]);
                letter.setAssignedMailman(alpha);
                westRidge.getRegisteredMails().add(letter);
            } else {
                Parcel parcel = new Parcel();
                parcel.setMailId("MAIL" + mailCounter);
                parcel.setAddressee(inhabitants[i % 10]);
                parcel.setAssignedMailman(alpha);
                westRidge.getRegisteredMails().add(parcel);
            }
            mailCounter++;
        }
        
        // 10 items for Beta
        for (int i = 0; i < 10; i++) {
            if (mailCounter % 2 == 0) {
                Letter letter = new Letter();
                letter.setMailId("MAIL" + mailCounter);
                letter.setAddressee(inhabitants[i % 10]);
                letter.setAssignedMailman(beta);
                westRidge.getRegisteredMails().add(letter);
            } else {
                Parcel parcel = new Parcel();
                parcel.setMailId("MAIL" + mailCounter);
                parcel.setAddressee(inhabitants[i % 10]);
                parcel.setAssignedMailman(beta);
                westRidge.getRegisteredMails().add(parcel);
            }
            mailCounter++;
        }
        
        // 10 items for Gamma
        for (int i = 0; i < 10; i++) {
            if (mailCounter % 2 == 0) {
                Letter letter = new Letter();
                letter.setMailId("MAIL" + mailCounter);
                letter.setAddressee(inhabitants[i % 10]);
                letter.setAssignedMailman(gamma);
                westRidge.getRegisteredMails().add(letter);
            } else {
                Parcel parcel = new Parcel();
                parcel.setMailId("MAIL" + mailCounter);
                parcel.setAddressee(inhabitants[i % 10]);
                parcel.setAssignedMailman(gamma);
                westRidge.getRegisteredMails().add(parcel);
            }
            mailCounter++;
        }
        
        // Action 1: Remove Alpha (specify Beta)
        boolean result1 = westRidge.removeMailman(alpha, beta);
        
        // Expected Output: true
        assertTrue("First removal (Alpha) should succeed", result1);
        
        // Action 2: Remove Beta (specify Gamma)
        boolean result2 = westRidge.removeMailman(beta, gamma);
        
        // Expected Output: true
        assertTrue("Second removal (Beta) should succeed", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        assertFalse("Alpha should be removed", westRidge.getMailmen().contains(alpha));
        assertFalse("Beta should be removed", westRidge.getMailmen().contains(beta));
        assertTrue("Gamma should remain", westRidge.getMailmen().contains(gamma));
        
        int gammaDeliveries = 0;
        for (RegisteredMail mail : westRidge.getRegisteredMails()) {
            assertEquals("All deliveries should be assigned to Gamma", gamma, mail.getAssignedMailman());
            gammaDeliveries++;
        }
        assertEquals("Gamma should have all 30 deliveries", 30, gammaDeliveries);
    }
}