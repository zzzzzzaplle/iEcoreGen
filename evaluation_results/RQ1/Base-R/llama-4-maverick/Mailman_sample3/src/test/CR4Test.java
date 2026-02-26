import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private MailDeliveryManager manager;
    private GeographicalArea centralDistrict;
    private GeographicalArea northQuarter;
    private GeographicalArea southEnd;
    private GeographicalArea eastHaven;
    private GeographicalArea westRidge;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        centralDistrict = new GeographicalArea();
        centralDistrict.setName("CentralDistrict");
        manager.addGeographicalArea(centralDistrict);
        
        // Add Mailmen ["Alice", "Bob", "Charlie"]
        Mailman alice = new Mailman();
        alice.setName("Alice");
        Mailman bob = new Mailman();
        bob.setName("Bob");
        Mailman charlie = new Mailman();
        charlie.setName("Charlie");
        
        manager.manageMailman(alice, centralDistrict, null, true);
        manager.manageMailman(bob, centralDistrict, null, true);
        manager.manageMailman(charlie, centralDistrict, null, true);
        
        // Add Inhabitants ["David", "Eve", "Frank"]
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
        letter1.setMailman(alice);
        centralDistrict.addRegisteredMail(letter1);
        
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(eve);
        parcel1.setMailman(bob);
        centralDistrict.addRegisteredMail(parcel1);
        
        Letter letter2 = new Letter();
        letter2.setAddressee(frank);
        letter2.setMailman(charlie);
        centralDistrict.addRegisteredMail(letter2);
        
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(eve);
        parcel2.setMailman(alice);
        centralDistrict.addRegisteredMail(parcel2);
        
        Letter letter3 = new Letter();
        letter3.setAddressee(david);
        letter3.setMailman(bob);
        centralDistrict.addRegisteredMail(letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = manager.manageMailman(alice, centralDistrict, bob, false);
        
        // Expected Output: true
        assertTrue("Alice removal should be successful", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen list", 
                   centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsDeliveries = new ArrayList<>();
        for (RegisteredMail mail : centralDistrict.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(bob)) {
                bobsDeliveries.add(mail);
            }
        }
        assertEquals("Bob should have 4 deliveries after reassignment", 4, bobsDeliveries.size());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob should still have Parcel1", 
                  bobsDeliveries.contains(parcel1));
        assertTrue("Bob should still have Letter3", 
                  bobsDeliveries.contains(letter3));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesDeliveries = new ArrayList<>();
        for (RegisteredMail mail : centralDistrict.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(charlie)) {
                charliesDeliveries.add(mail);
            }
        }
        assertEquals("Charlie should still have 1 delivery", 1, charliesDeliveries.size());
        assertTrue("Charlie should still have Letter2", 
                  charliesDeliveries.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        northQuarter = new GeographicalArea();
        northQuarter.setName("NorthQuarter");
        manager.addGeographicalArea(northQuarter);
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        Mailman xavier = new Mailman();
        xavier.setName("Xavier");
        Mailman yvonne = new Mailman();
        yvonne.setName("Yvonne");
        Mailman zack = new Mailman();
        zack.setName("Zack");
        
        manager.manageMailman(xavier, northQuarter, null, true);
        manager.manageMailman(yvonne, northQuarter, null, true);
        manager.manageMailman(zack, northQuarter, null, true);
        
        // Add Inhabitant "Walter"
        Inhabitant walter = new Inhabitant();
        walter.setName("Walter");
        manager.manageInhabitant(walter, northQuarter, true);
        
        // Create and assign mail items
        List<Letter> xavierLetters = new ArrayList<>();
        List<Parcel> yvonneParcels = new ArrayList<>();
        List<Letter> zackLetters = new ArrayList<>();
        
        // 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setMailman(xavier);
            northQuarter.addRegisteredMail(letter);
            xavierLetters.add(letter);
        }
        
        // 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            parcel.setMailman(yvonne);
            northQuarter.addRegisteredMail(parcel);
            yvonneParcels.add(parcel);
        }
        
        // 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setMailman(zack);
            northQuarter.addRegisteredMail(letter);
            zackLetters.add(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = manager.manageMailman(yvonne, northQuarter, xavier, false);
        
        // Expected Output: true
        assertTrue("First removal (Yvonne) should be successful", result1);
        
        // Verifications after first removal
        // 3 parcels moved to Xavier (now has 8 items)
        List<RegisteredMail> xaviersDeliveriesAfterFirst = new ArrayList<>();
        for (RegisteredMail mail : northQuarter.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(xavier)) {
                xaviersDeliveriesAfterFirst.add(mail);
            }
        }
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xaviersDeliveriesAfterFirst.size());
        
        // Yvonne should be removed
        assertFalse("Yvonne should be removed from mailmen list", 
                   northQuarter.getMailmen().contains(yvonne));
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = manager.manageMailman(xavier, northQuarter, zack, false);
        
        // Expected Output: true
        assertTrue("Second removal (Xavier) should be successful", result2);
        
        // Verifications after second removal
        // 8 letters moved to Zack (now has 10 items)
        List<RegisteredMail> zacksDeliveriesAfterSecond = new ArrayList<>();
        for (RegisteredMail mail : northQuarter.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(zack)) {
                zacksDeliveriesAfterSecond.add(mail);
            }
        }
        assertEquals("Zack should have 10 deliveries after second removal", 10, zacksDeliveriesAfterSecond.size());
        
        // Final state: Only Zack remains with all 10 deliveries
        assertEquals("Only Zack should remain as mailman", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", 
                  northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        southEnd = new GeographicalArea();
        southEnd.setName("SouthEnd");
        manager.addGeographicalArea(southEnd);
        
        // Add Mailmen ["Paul", "Quinn"]
        Mailman paul = new Mailman();
        paul.setName("Paul");
        Mailman quinn = new Mailman();
        quinn.setName("Quinn");
        
        manager.manageMailman(paul, southEnd, null, true);
        manager.manageMailman(quinn, southEnd, null, true);
        
        // Add Inhabitant "Rachel"
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        manager.manageInhabitant(rachel, southEnd, true);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        letter1.setMailman(paul);
        southEnd.addRegisteredMail(letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = manager.manageMailman(paul, southEnd, quinn, false);
        assertTrue("Removing Paul with Quinn as replacement should succeed", result1);
        assertFalse("Paul should be removed", southEnd.getMailmen().contains(paul));
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = manager.manageMailman(quinn, southEnd, null, false);
        assertFalse("Removing last mailman should fail", result2);
        assertTrue("Quinn should still be in mailmen list", southEnd.getMailmen().contains(quinn));
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        eastHaven = new GeographicalArea();
        eastHaven.setName("EastHaven");
        manager.addGeographicalArea(eastHaven);
        
        // Add Mailmen ["Mario", "Luigi"]
        Mailman mario = new Mailman();
        mario.setName("Mario");
        Mailman luigi = new Mailman();
        luigi.setName("Luigi");
        
        manager.manageMailman(mario, eastHaven, null, true);
        manager.manageMailman(luigi, eastHaven, null, true);
        
        // Add Inhabitants ["Peach", "Bowser"]
        Inhabitant peach = new Inhabitant();
        peach.setName("Peach");
        Inhabitant bowser = new Inhabitant();
        bowser.setName("Bowser");
        
        manager.manageInhabitant(peach, eastHaven, true);
        manager.manageInhabitant(bowser, eastHaven, true);
        
        // Create mail items
        // 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            letter.setMailman(mario);
            eastHaven.addRegisteredMail(letter);
        }
        
        // 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            parcel.setMailman(luigi);
            eastHaven.addRegisteredMail(parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        Mailman marioDuplicate = new Mailman();
        marioDuplicate.setName("Mario");
        boolean result1 = manager.manageMailman(marioDuplicate, eastHaven, null, true);
        assertFalse("Adding duplicate Mario should fail", result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = manager.manageMailman(mario, eastHaven, luigi, false);
        assertTrue("Removing Mario should succeed", result2);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisDeliveries = new ArrayList<>();
        for (RegisteredMail mail : eastHaven.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(luigi)) {
                luigisDeliveries.add(mail);
            }
        }
        assertEquals("Luigi should have 15 deliveries after taking Mario's", 15, luigisDeliveries.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = manager.manageMailman(luigi, eastHaven, null, false);
        assertFalse("Removing last mailman should fail", result3);
        
        // 5. Add "Toad" → true
        Mailman toad = new Mailman();
        toad.setName("Toad");
        boolean result4 = manager.manageMailman(toad, eastHaven, null, true);
        assertTrue("Adding Toad should succeed", result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = manager.manageMailman(luigi, eastHaven, toad, false);
        assertTrue("Removing Luigi with Toad as replacement should succeed", result5);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        westRidge = new GeographicalArea();
        westRidge.setName("WestRidge");
        manager.addGeographicalArea(westRidge);
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
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
            letterAlpha.setAddressee(inhabitants.get(i % 10));
            letterAlpha.setMailman(alpha);
            westRidge.addRegisteredMail(letterAlpha);
            mailCount++;
            
            Parcel parcelBeta = new Parcel();
            parcelBeta.setAddressee(inhabitants.get((i + 1) % 10));
            parcelBeta.setMailman(beta);
            westRidge.addRegisteredMail(parcelBeta);
            mailCount++;
            
            Letter letterGamma = new Letter();
            letterGamma.setAddressee(inhabitants.get((i + 2) % 10));
            letterGamma.setMailman(gamma);
            westRidge.addRegisteredMail(letterGamma);
            mailCount++;
        }
        
        assertEquals("Should have created 30 mail items", 30, mailCount);
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = manager.manageMailman(alpha, westRidge, beta, false);
        assertTrue("Removing Alpha should succeed", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = manager.manageMailman(beta, westRidge, gamma, false);
        assertTrue("Removing Beta should succeed", result2);
        
        // Verifications:
        // Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasDeliveries = new ArrayList<>();
        for (RegisteredMail mail : westRidge.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(gamma)) {
                gammasDeliveries.add(mail);
            }
        }
        assertEquals("Gamma should have all 30 deliveries", 30, gammasDeliveries.size());
        
        // Only Gamma should remain
        assertEquals("Only Gamma should remain as mailman", 1, westRidge.getMailmen().size());
        assertTrue("Gamma should be the only remaining mailman", 
                  westRidge.getMailmen().contains(gamma));
    }
}