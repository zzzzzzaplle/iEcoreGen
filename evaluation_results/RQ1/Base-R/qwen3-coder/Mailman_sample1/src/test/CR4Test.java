import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {

    private Area centralDistrict;
    private Area northQuarter;
    private Area southEnd;
    private Area eastHaven;
    private Area westRidge;
    
    private Mailman alice, bob, charlie, xavier, yvonne, zack, paul, quinn, mario, luigi, toad, alpha, beta, gamma;
    private Inhabitant david, eve, frank, walter, rachel, peach, bowser;
    
    @Before
    public void setUp() {
        // Initialize common objects that might be used across multiple tests
        centralDistrict = new Area();
        centralDistrict.setName("CentralDistrict");
        
        northQuarter = new Area();
        northQuarter.setName("NorthQuarter");
        
        southEnd = new Area();
        southEnd.setName("SouthEnd");
        
        eastHaven = new Area();
        eastHaven.setName("EastHaven");
        
        westRidge = new Area();
        westRidge.setName("WestRidge");
        
        // Initialize mailmen
        alice = new Mailman(); alice.setName("Alice");
        bob = new Mailman(); bob.setName("Bob");
        charlie = new Mailman(); charlie.setName("Charlie");
        xavier = new Mailman(); xavier.setName("Xavier");
        yvonne = new Mailman(); yvonne.setName("Yvonne");
        zack = new Mailman(); zack.setName("Zack");
        paul = new Mailman(); paul.setName("Paul");
        quinn = new Mailman(); quinn.setName("Quinn");
        mario = new Mailman(); mario.setName("Mario");
        luigi = new Mailman(); luigi.setName("Luigi");
        toad = new Mailman(); toad.setName("Toad");
        alpha = new Mailman(); alpha.setName("Alpha");
        beta = new Mailman(); beta.setName("Beta");
        gamma = new Mailman(); gamma.setName("Gamma");
        
        // Initialize inhabitants
        david = new Inhabitant(); david.setName("David");
        eve = new Inhabitant(); eve.setName("Eve");
        frank = new Inhabitant(); frank.setName("Frank");
        walter = new Inhabitant(); walter.setName("Walter");
        rachel = new Inhabitant(); rachel.setName("Rachel");
        peach = new Inhabitant(); peach.setName("Peach");
        bowser = new Inhabitant(); bowser.setName("Bowser");
    }

    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // Setup CentralDistrict area
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Setup inhabitants and assign to area
        david.setArea(centralDistrict);
        eve.setArea(centralDistrict);
        frank.setArea(centralDistrict);
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Assign mailmen to area
        alice.addAssignedArea(centralDistrict);
        bob.addAssignedArea(centralDistrict);
        charlie.addAssignedArea(centralDistrict);
        
        // Create mail items
        Letter letter1 = new Letter(); letter1.setAddressee(david); letter1.setArea(centralDistrict);
        Parcel parcel1 = new Parcel(); parcel1.setAddressee(eve); parcel1.setArea(centralDistrict);
        Letter letter2 = new Letter(); letter2.setAddressee(frank); letter2.setArea(centralDistrict);
        Parcel parcel2 = new Parcel(); parcel2.setAddressee(eve); parcel2.setArea(centralDistrict);
        Letter letter3 = new Letter(); letter3.setAddressee(david); letter3.setArea(centralDistrict);
        
        // Assign mailmen to mail items
        letter1.setAssignedMailman(alice);
        parcel1.setAssignedMailman(bob);
        letter2.setAssignedMailman(charlie);
        parcel2.setAssignedMailman(alice);
        letter3.setAssignedMailman(bob);
        
        // Add mail to area
        centralDistrict.getRegisteredMails().add(letter1);
        centralDistrict.getRegisteredMails().add(parcel1);
        centralDistrict.getRegisteredMails().add(letter2);
        centralDistrict.getRegisteredMails().add(parcel2);
        centralDistrict.getRegisteredMails().add(letter3);
        
        // Action: Remove Alice with Bob as replacement
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Verify expected output
        assertTrue("Removal should be successful", result);
        
        // Verify Alice was removed
        assertFalse("Alice should be removed from mailmen list", centralDistrict.getMailmen().contains(alice));
        assertEquals("Should have 2 mailmen remaining", 2, centralDistrict.getMailmen().size());
        
        // Verify mail reassignments
        assertEquals("Letter1 should be reassigned to Bob", bob, letter1.getAssignedMailman());
        assertEquals("Parcel2 should be reassigned to Bob", bob, parcel2.getAssignedMailman());
        assertEquals("Parcel1 should remain with Bob", bob, parcel1.getAssignedMailman());
        assertEquals("Letter3 should remain with Bob", bob, parcel1.getAssignedMailman());
        assertEquals("Letter2 should remain with Charlie", charlie, letter2.getAssignedMailman());
        
        // Count deliveries per mailman
        int bobDeliveries = 0;
        int charlieDeliveries = 0;
        for (RegisteredMail mail : centralDistrict.getAllDeliveries()) {
            if (mail.getAssignedMailman() == bob) bobDeliveries++;
            if (mail.getAssignedMailman() == charlie) charlieDeliveries++;
        }
        
        assertEquals("Bob should have 4 deliveries", 4, bobDeliveries);
        assertEquals("Charlie should have 1 delivery", 1, charlieDeliveries);
    }

    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Setup NorthQuarter area
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Setup inhabitant and assign to area
        walter.setArea(northQuarter);
        northQuarter.addInhabitant(walter);
        
        // Assign mailmen to area
        xavier.addAssignedArea(northQuarter);
        yvonne.addAssignedArea(northQuarter);
        zack.addAssignedArea(northQuarter);
        
        // Create 10 mail items
        List<RegisteredMail> mails = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setArea(northQuarter);
            letter.setAssignedMailman(xavier);
            mails.add(letter);
        }
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            parcel.setArea(northQuarter);
            parcel.setAssignedMailman(yvonne);
            mails.add(parcel);
        }
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setArea(northQuarter);
            letter.setAssignedMailman(zack);
            mails.add(letter);
        }
        
        // Add all mail to area
        northQuarter.getRegisteredMails().addAll(mails);
        
        // First removal: Remove Yvonne with Xavier as replacement
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        assertTrue("First removal should be successful", result1);
        
        // Verify first removal state
        assertFalse("Yvonne should be removed", northQuarter.getMailmen().contains(yvonne));
        assertEquals("Should have 2 mailmen remaining", 2, northQuarter.getMailmen().size());
        
        // Count deliveries after first removal
        int xavierDeliveries1 = 0;
        int zackDeliveries1 = 0;
        for (RegisteredMail mail : northQuarter.getAllDeliveries()) {
            if (mail.getAssignedMailman() == xavier) xavierDeliveries1++;
            if (mail.getAssignedMailman() == zack) zackDeliveries1++;
        }
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xavierDeliveries1);
        assertEquals("Zack should have 2 deliveries after first removal", 2, zackDeliveries1);
        
        // Second removal: Remove Xavier with Zack as replacement
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        assertTrue("Second removal should be successful", result2);
        
        // Verify final state
        assertFalse("Xavier should be removed", northQuarter.getMailmen().contains(xavier));
        assertEquals("Should have 1 mailman remaining", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", northQuarter.getMailmen().contains(zack));
        
        // Count final deliveries
        int zackDeliveries2 = 0;
        for (RegisteredMail mail : northQuarter.getAllDeliveries()) {
            if (mail.getAssignedMailman() == zack) zackDeliveries2++;
        }
        assertEquals("Zack should have all 10 deliveries", 10, zackDeliveries2);
    }

    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Setup SouthEnd area
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Setup inhabitant and assign to area
        rachel.setArea(southEnd);
        southEnd.addInhabitant(rachel);
        
        // Assign mailmen to area
        paul.addAssignedArea(southEnd);
        quinn.addAssignedArea(southEnd);
        
        // Create mail item
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        letter1.setArea(southEnd);
        letter1.setAssignedMailman(paul);
        southEnd.getRegisteredMails().add(letter1);
        
        // First removal: Remove Paul with Quinn as replacement (should succeed)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("First removal should be successful", result1);
        
        // Verify first removal
        assertFalse("Paul should be removed", southEnd.getMailmen().contains(paul));
        assertEquals("Should have 1 mailman remaining", 1, southEnd.getMailmen().size());
        assertEquals("Letter should be reassigned to Quinn", quinn, letter1.getAssignedMailman());
        
        // Second removal: Attempt to remove Quinn (last mailman - should fail)
        boolean result2 = southEnd.removeMailman(quinn, paul); // Paul no longer exists, but validation should fail first
        assertFalse("Removal of last mailman should fail", result2);
        
        // Verify Quinn is still present
        assertTrue("Quinn should still be in mailmen list", southEnd.getMailmen().contains(quinn));
        assertEquals("Should still have 1 mailman", 1, southEnd.getMailmen().size());
    }

    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Setup EastHaven area
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Setup inhabitants and assign to area
        peach.setArea(eastHaven);
        bowser.setArea(eastHaven);
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Assign mailmen to area
        mario.addAssignedArea(eastHaven);
        luigi.addAssignedArea(eastHaven);
        
        // Create mail items
        List<RegisteredMail> mariosMail = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            letter.setArea(eastHaven);
            letter.setAssignedMailman(mario);
            mariosMail.add(letter);
        }
        
        List<RegisteredMail> luigisMail = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            parcel.setArea(eastHaven);
            parcel.setAssignedMailman(luigi);
            luigisMail.add(parcel);
        }
        
        eastHaven.getRegisteredMails().addAll(mariosMail);
        eastHaven.getRegisteredMails().addAll(luigisMail);
        
        // 1. Add duplicate Mario (should fail)
        boolean addDuplicateResult = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", addDuplicateResult);
        assertEquals("Should still have 2 mailmen", 2, eastHaven.getMailmen().size());
        
        // 2. Remove Mario with Luigi as replacement (should succeed)
        boolean removeMarioResult = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removing Mario should succeed", removeMarioResult);
        assertFalse("Mario should be removed", eastHaven.getMailmen().contains(mario));
        assertEquals("Should have 1 mailman remaining", 1, eastHaven.getMailmen().size());
        
        // 3. Verify Luigi now has all 15 deliveries
        int luigiDeliveries = 0;
        for (RegisteredMail mail : eastHaven.getAllDeliveries()) {
            if (mail.getAssignedMailman() == luigi) luigiDeliveries++;
        }
        assertEquals("Luigi should have 15 deliveries", 15, luigiDeliveries);
        
        // 4. Attempt to remove Luigi (last mailman - should fail)
        boolean removeLuigiAttempt = eastHaven.removeMailman(luigi, mario); // Mario no longer exists
        assertFalse("Removing last mailman should fail", removeLuigiAttempt);
        assertTrue("Luigi should still be present", eastHaven.getMailmen().contains(luigi));
        assertEquals("Should still have 1 mailman", 1, eastHaven.getMailmen().size());
        
        // 5. Add Toad (should succeed)
        boolean addToadResult = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should succeed", addToadResult);
        assertEquals("Should have 2 mailmen", 2, eastHaven.getMailmen().size());
        
        // Assign Toad to area
        toad.addAssignedArea(eastHaven);
        
        // 6. Remove Luigi with Toad as replacement (should succeed)
        boolean removeLuigiResult = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removing Luigi with Toad as replacement should succeed", removeLuigiResult);
        assertFalse("Luigi should be removed", eastHaven.getMailmen().contains(luigi));
        assertEquals("Should have 1 mailman remaining", 1, eastHaven.getMailmen().size());
        
        // Verify Toad now has all deliveries
        int toadDeliveries = 0;
        for (RegisteredMail mail : eastHaven.getAllDeliveries()) {
            if (mail.getAssignedMailman() == toad) toadDeliveries++;
        }
        assertEquals("Toad should have 15 deliveries", 15, toadDeliveries);
    }

    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Setup WestRidge area
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Create 10 inhabitants and assign to area
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setName("Inhabitant" + i);
            inhabitant.setArea(westRidge);
            westRidge.addInhabitant(inhabitant);
            inhabitants.add(inhabitant);
        }
        
        // Assign mailmen to area
        alpha.addAssignedArea(westRidge);
        beta.addAssignedArea(westRidge);
        gamma.addAssignedArea(westRidge);
        
        // Create 30 mail items (10 per mailman)
        List<RegisteredMail> allMail = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants.get(i % 10));
            letter.setArea(westRidge);
            letter.setAssignedMailman(alpha);
            allMail.add(letter);
        }
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(inhabitants.get(i % 10));
            parcel.setArea(westRidge);
            parcel.setAssignedMailman(beta);
            allMail.add(parcel);
        }
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants.get(i % 10));
            letter.setArea(westRidge);
            letter.setAssignedMailman(gamma);
            allMail.add(letter);
        }
        
        westRidge.getRegisteredMails().addAll(allMail);
        
        // First removal: Remove Alpha with Beta as replacement
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal should be successful", result1);
        assertFalse("Alpha should be removed", westRidge.getMailmen().contains(alpha));
        assertEquals("Should have 2 mailmen remaining", 2, westRidge.getMailmen().size());
        
        // Verify Beta now has 20 deliveries
        int betaDeliveries1 = 0;
        int gammaDeliveries1 = 0;
        for (RegisteredMail mail : westRidge.getAllDeliveries()) {
            if (mail.getAssignedMailman() == beta) betaDeliveries1++;
            if (mail.getAssignedMailman() == gamma) gammaDeliveries1++;
        }
        assertEquals("Beta should have 20 deliveries after first removal", 20, betaDeliveries1);
        assertEquals("Gamma should have 10 deliveries after first removal", 10, gammaDeliveries1);
        
        // Second removal: Remove Beta with Gamma as replacement
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal should be successful", result2);
        assertFalse("Beta should be removed", westRidge.getMailmen().contains(beta));
        assertEquals("Should have 1 mailman remaining", 1, westRidge.getMailmen().size());
        
        // Verify Gamma now has all 30 deliveries
        int gammaDeliveries2 = 0;
        for (RegisteredMail mail : westRidge.getAllDeliveries()) {
            if (mail.getAssignedMailman() == gamma) gammaDeliveries2++;
        }
        assertEquals("Gamma should have all 30 deliveries", 30, gammaDeliveries2);
    }
}