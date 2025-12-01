import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        // SetUp
        centralDistrict = new GeographicalArea();
        centralDistrict.setName("CentralDistrict");
        
        Mailman alice = new Mailman();
        alice.setName("Alice");
        Mailman bob = new Mailman();
        bob.setName("Bob");
        Mailman charlie = new Mailman();
        charlie.setName("Charlie");
        
        Inhabitant david = new Inhabitant();
        david.setName("David");
        Inhabitant eve = new Inhabitant();
        eve.setName("Eve");
        Inhabitant frank = new Inhabitant();
        frank.setName("Frank");
        
        // Add mailmen to area
        manager.manageMailman(alice, centralDistrict, null, true);
        manager.manageMailman(bob, centralDistrict, null, true);
        manager.manageMailman(charlie, centralDistrict, null, true);
        
        // Add inhabitants to area
        manager.manageInhabitant(david, centralDistrict, true);
        manager.manageInhabitant(eve, centralDistrict, true);
        manager.manageInhabitant(frank, centralDistrict, true);
        
        // Create and assign mail items
        Letter letter1 = new Letter();
        letter1.setAddressee(david);
        letter1.assignMailman(alice);
        centralDistrict.addRegisteredMail(letter1);
        
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(eve);
        parcel1.assignMailman(bob);
        centralDistrict.addRegisteredMail(parcel1);
        
        Letter letter2 = new Letter();
        letter2.setAddressee(frank);
        letter2.assignMailman(charlie);
        centralDistrict.addRegisteredMail(letter2);
        
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(eve);
        parcel2.assignMailman(alice);
        centralDistrict.addRegisteredMail(parcel2);
        
        Letter letter3 = new Letter();
        letter3.setAddressee(david);
        letter3.assignMailman(bob);
        centralDistrict.addRegisteredMail(letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = manager.manageMailman(alice, centralDistrict, bob, false);
        
        // Expected Output: true
        assertTrue("Alice removal should succeed", result);
        
        // Verifications
        // - Alice removed
        assertFalse("Alice should be removed from mailmen", centralDistrict.getMailmen().contains(alice));
        
        // - Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsDeliveries = new ArrayList<>();
        for (RegisteredMail mail : centralDistrict.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(bob)) {
                bobsDeliveries.add(mail);
            }
        }
        assertEquals("Bob should have 4 deliveries after reassignment", 4, bobsDeliveries.size());
        
        // - Bob's original deliveries (Parcel1, Letter3) unchanged
        boolean hasParcel1 = false;
        boolean hasLetter3 = false;
        for (RegisteredMail mail : bobsDeliveries) {
            if (mail.equals(parcel1)) hasParcel1 = true;
            if (mail.equals(letter3)) hasLetter3 = true;
        }
        assertTrue("Bob should still have Parcel1", hasParcel1);
        assertTrue("Bob should still have Letter3", hasLetter3);
        
        // - Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesDeliveries = new ArrayList<>();
        for (RegisteredMail mail : centralDistrict.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(charlie)) {
            charliesDeliveries.add(mail);
        }
        assertEquals("Charlie should have 1 delivery", 1, charliesDeliveries.size());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        northQuarter = new GeographicalArea();
        northQuarter.setName("NorthQuarter");
        
        Mailman xavier = new Mailman();
        xavier.setName("Xavier");
        Mailman yvonne = new Mailman();
        yvonne.setName("Yvonne");
        Mailman zack = new Mailman();
        zack.setName("Zack");
        
        Inhabitant walter = new Inhabitant();
        walter.setName("Walter");
        
        // Add mailmen to area
        manager.manageMailman(xavier, northQuarter, null, true);
        manager.manageMailman(yvonne, northQuarter, null, true);
        manager.manageMailman(zack, northQuarter, null, true);
        
        // Add inhabitant to area
        manager.manageInhabitant(walter, northQuarter, true);
        
        // Create and assign 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.assignMailman(xavier);
            northQuarter.addRegisteredMail(letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            parcel.assignMailman(yvonne);
            northQuarter.addRegisteredMail(parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.assignMailman(zack);
            northQuarter.addRegisteredMail(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = manager.manageMailman(yvonne, northQuarter, xavier, false);
        
        // Expected Output: true
        assertTrue("First removal (Yvonne) should succeed", result1);
        
        // Verifications after first removal
        // - 3 parcels moved to Xavier (now has 8 items)
        List<RegisteredMail> xaviersDeliveries1 = new ArrayList<>();
        for (RegisteredMail mail : northQuarter.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(xavier)) {
                xaviersDeliveries1.add(mail);
            }
        }
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xaviersDeliveries1.size());
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = manager.manageMailman(xavier, northQuarter, zack, false);
        
        // Expected Output: true
        assertTrue("Second removal (Xavier) should succeed", result2);
        
        // Verifications after second removal
        // - 8 letters moved to Zack (now has 10 items)
        List<RegisteredMail> zacksDeliveries = new ArrayList<>();
        for (RegisteredMail mail : northQuarter.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(zack)) {
                zacksDeliveries.add(mail);
            }
        }
        assertEquals("Zack should have 10 deliveries after second removal", 10, zacksDeliveries.size());
        
        // - Final state: Only Zack remains with all 10 deliveries
        assertEquals("Only Zack should remain as mailman", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        southEnd = new GeographicalArea();
        southEnd.setName("SouthEnd");
        
        Mailman paul = new Mailman();
        paul.setName("Paul");
        Mailman quinn = new Mailman();
        quinn.setName("Quinn");
        
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        
        // Add mailmen to area
        manager.manageMailman(paul, southEnd, null, true);
        manager.manageMailman(quinn, southEnd, null, true);
        
        // Add inhabitant to area
        manager.manageInhabitant(rachel, southEnd, true);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        letter1.assignMailman(paul);
        southEnd.addRegisteredMail(letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = manager.manageMailman(paul, southEnd, quinn, false);
        assertTrue("Paul removal with Quinn as replacement should succeed", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = manager.manageMailman(quinn, southEnd, null, false);
        assertFalse("Quinn removal (last mailman) should fail", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        eastHaven = new GeographicalArea();
        eastHaven.setName("EastHaven");
        
        Mailman mario = new Mailman();
        mario.setName("Mario");
        Mailman luigi = new Mailman();
        luigi.setName("Luigi");
        Mailman toad = new Mailman();
        toad.setName("Toad");
        
        Inhabitant peach = new Inhabitant();
        peach.setName("Peach");
        Inhabitant bowser = new Inhabitant();
        bowser.setName("Bowser");
        
        // Add mailmen to area
        manager.manageMailman(mario, eastHaven, null, true);
        manager.manageMailman(luigi, eastHaven, null, true);
        
        // Add inhabitants to area
        manager.manageInhabitant(peach, eastHaven, true);
        manager.manageInhabitant(bowser, eastHaven, true);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            letter.assignMailman(mario);
            eastHaven.addRegisteredMail(letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            parcel.assignMailman(luigi);
            eastHaven.addRegisteredMail(parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean result1 = manager.manageMailman(mario, eastHaven, null, true);
        assertFalse("Adding duplicate Mario should fail", result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = manager.manageMailman(mario, eastHaven, luigi, false);
        assertTrue("Mario removal with Luigi as replacement should succeed", result2);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisDeliveries = new ArrayList<>();
        for (RegisteredMail mail : eastHaven.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(luigi)) {
            luigisDeliveries.add(mail);
        }
        assertEquals("Luigi should have 15 deliveries after taking over Mario's", 15, luigisDeliveries.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = manager.manageMailman(luigi, eastHaven, null, false);
        assertFalse("Luigi removal (last mailman) should fail", result3);
        
        // 5. Add "Toad" → true
        boolean result4 = manager.manageMailman(toad, eastHaven, null, true);
        assertTrue("Adding Toad should succeed", result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = manager.manageMailman(luigi, eastHaven, toad, false);
        assertTrue("Luigi removal with Toad as replacement should succeed", result5);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        westRidge = new GeographicalArea();
        westRidge.setName("WestRidge");
        
        Mailman alpha = new Mailman();
        alpha.setName("Alpha");
        Mailman beta = new Mailman();
        beta.setName("Beta");
        Mailman gamma = new Mailman();
        gamma.setName("Gamma");
        
        // Add mailmen to area
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
        int inhabitantIndex = 0;
        
        // 10 items for Alpha
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                letter.setAddressee(inhabitants.get(inhabitantIndex));
                letter.assignMailman(alpha);
                westRidge.addRegisteredMail(letter);
            } else {
                Parcel parcel = new Parcel();
                parcel.setAddressee(inhabitants.get(inhabitantIndex));
                parcel.assignMailman(alpha);
                westRidge.addRegisteredMail(parcel);
            }
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // 10 items for Beta
        for (int i = 0; i < 10; i++) {
                Letter letter = new Letter();
                letter.setAddressee(inhabitants.get(inhabitantIndex));
                letter.assignMailman(beta);
                westRidge.addRegisteredMail(letter);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // 10 items for Gamma
        for (int i = 0; i < 10; i++) {
                Parcel parcel = new Parcel();
                parcel.setAddressee(inhabitants.get(inhabitantIndex));
                parcel.assignMailman(gamma);
                westRidge.addRegisteredMail(parcel);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = manager.manageMailman(alpha, westRidge, beta, false);
        assertTrue("Alpha removal with Beta as replacement should succeed", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = manager.manageMailman(beta, westRidge, gamma, false);
        assertTrue("Beta removal with Gamma as replacement should succeed", result2);
        
        // Verifications:
        // - Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasDeliveries = new ArrayList<>();
        for (RegisteredMail mail : westRidge.getRegisteredMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(gamma)) {
            gammasDeliveries.add(mail);
        }
        assertEquals("Gamma should have all 30 deliveries at the end", 30, gammasDeliveries.size());
    }
}