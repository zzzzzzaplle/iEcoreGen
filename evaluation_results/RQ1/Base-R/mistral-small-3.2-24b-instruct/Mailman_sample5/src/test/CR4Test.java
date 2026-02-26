import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private MailManagementSystem system;
    
    @Before
    public void setUp() {
        system = new MailManagementSystem();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp
        GeographicalArea centralDistrict = new GeographicalArea();
        centralDistrict.setAreaId("CentralDistrict");
        system.addGeographicalArea(centralDistrict);
        
        Mailman alice = new Mailman();
        alice.setMailmanId("Alice");
        alice.setName("Alice");
        alice.setGeographicalArea(centralDistrict);
        
        Mailman bob = new Mailman();
        bob.setMailmanId("Bob");
        bob.setName("Bob");
        bob.setGeographicalArea(centralDistrict);
        
        Mailman charlie = new Mailman();
        charlie.setMailmanId("Charlie");
        charlie.setName("Charlie");
        charlie.setGeographicalArea(centralDistrict);
        
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        Inhabitant david = new Inhabitant();
        david.setInhabitantId("David");
        david.setName("David");
        david.setGeographicalArea(centralDistrict);
        
        Inhabitant eve = new Inhabitant();
        eve.setInhabitantId("Eve");
        eve.setName("Eve");
        eve.setGeographicalArea(centralDistrict);
        
        Inhabitant frank = new Inhabitant();
        frank.setInhabitantId("Frank");
        frank.setName("Frank");
        frank.setGeographicalArea(centralDistrict);
        
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        Letter letter1 = new Letter();
        letter1.setMailId("Letter1");
        letter1.setAddressee(david);
        alice.assignMail(letter1);
        
        Parcel parcel1 = new Parcel();
        parcel1.setMailId("Parcel1");
        parcel1.setAddressee(eve);
        bob.assignMail(parcel1);
        
        Letter letter2 = new Letter();
        letter2.setMailId("Letter2");
        letter2.setAddressee(frank);
        charlie.assignMail(letter2);
        
        Parcel parcel2 = new Parcel();
        parcel2.setMailId("Parcel2");
        parcel2.setAddressee(eve);
        alice.assignMail(parcel2);
        
        Letter letter3 = new Letter();
        letter3.setMailId("Letter3");
        letter3.setAddressee(david);
        bob.assignMail(letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications
        assertFalse("Alice should be removed", centralDistrict.getMailmen().contains(alice));
        assertEquals("Bob should have 4 deliveries", 4, bob.getDeliveries().size());
        assertTrue("Bob should have Letter1", bob.getDeliveries().contains(letter1));
        assertTrue("Bob should have Parcel2", bob.getDeliveries().contains(parcel2));
        assertTrue("Bob should have Parcel1", bob.getDeliveries().contains(parcel1));
        assertTrue("Bob should have Letter3", bob.getDeliveries().contains(letter3));
        assertEquals("Charlie should have 1 delivery", 1, charlie.getDeliveries().size());
        assertTrue("Charlie should have Letter2", charlie.getDeliveries().contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        GeographicalArea northQuarter = new GeographicalArea();
        northQuarter.setAreaId("NorthQuarter");
        system.addGeographicalArea(northQuarter);
        
        Mailman xavier = new Mailman();
        xavier.setMailmanId("Xavier");
        xavier.setName("Xavier");
        xavier.setGeographicalArea(northQuarter);
        
        Mailman yvonne = new Mailman();
        yvonne.setMailmanId("Yvonne");
        yvonne.setName("Yvonne");
        yvonne.setGeographicalArea(northQuarter);
        
        Mailman zack = new Mailman();
        zack.setMailmanId("Zack");
        zack.setName("Zack");
        zack.setGeographicalArea(northQuarter);
        
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        Inhabitant walter = new Inhabitant();
        walter.setInhabitantId("Walter");
        walter.setName("Walter");
        walter.setGeographicalArea(northQuarter);
        northQuarter.addInhabitant(walter);
        
        // Create 5 Letters (Xavier→Walter)
        List<Letter> xavierLetters = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Letter letter = new Letter();
            letter.setMailId("LetterX" + i);
            letter.setAddressee(walter);
            xavier.assignMail(letter);
            xavierLetters.add(letter);
        }
        
        // Create 3 Parcels (Yvonne→Walter)
        List<Parcel> yvonneParcels = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setMailId("ParcelY" + i);
            parcel.setAddressee(walter);
            yvonne.assignMail(parcel);
            yvonneParcels.add(parcel);
        }
        
        // Create 2 Letters (Zack→Walter)
        List<Letter> zackLetters = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Letter letter = new Letter();
            letter.setMailId("LetterZ" + i);
            letter.setAddressee(walter);
            zack.assignMail(letter);
            zackLetters.add(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Verifications after first removal
        assertFalse("Yvonne should be removed", northQuarter.getMailmen().contains(yvonne));
        assertEquals("Xavier should have 8 deliveries", 8, xavier.getDeliveries().size());
        for (Parcel parcel : yvonneParcels) {
            assertTrue("Xavier should have Yvonne's parcels", xavier.getDeliveries().contains(parcel));
        }
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Final verifications
        assertFalse("Xavier should be removed", northQuarter.getMailmen().contains(xavier));
        assertEquals("Zack should have 10 deliveries", 10, zack.getDeliveries().size());
        assertEquals("Only Zack should remain", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        GeographicalArea southEnd = new GeographicalArea();
        southEnd.setAreaId("SouthEnd");
        system.addGeographicalArea(southEnd);
        
        Mailman paul = new Mailman();
        paul.setMailmanId("Paul");
        paul.setName("Paul");
        paul.setGeographicalArea(southEnd);
        
        Mailman quinn = new Mailman();
        quinn.setMailmanId("Quinn");
        quinn.setName("Quinn");
        quinn.setGeographicalArea(southEnd);
        
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        Inhabitant rachel = new Inhabitant();
        rachel.setInhabitantId("Rachel");
        rachel.setName("Rachel");
        rachel.setGeographicalArea(southEnd);
        southEnd.addInhabitant(rachel);
        
        Letter letter1 = new Letter();
        letter1.setMailId("Letter1");
        letter1.setAddressee(rachel);
        paul.assignMail(letter1);
        
        // Action 1: Attempt remove Paul specifying Quinn
        boolean result1 = southEnd.removeMailman(paul, quinn);
        
        // Expected Output: true (normal case)
        assertTrue(result1);
        
        // Action 2: Attempt remove Quinn (last mailman)
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in the area
        
        // Expected Output: false
        assertFalse(result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        GeographicalArea eastHaven = new GeographicalArea();
        eastHaven.setAreaId("EastHaven");
        system.addGeographicalArea(eastHaven);
        
        Mailman mario = new Mailman();
        mario.setMailmanId("Mario");
        mario.setName("Mario");
        mario.setGeographicalArea(eastHaven);
        
        Mailman luigi = new Mailman();
        luigi.setMailmanId("Luigi");
        luigi.setName("Luigi");
        luigi.setGeographicalArea(eastHaven);
        
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        Inhabitant peach = new Inhabitant();
        peach.setInhabitantId("Peach");
        peach.setName("Peach");
        peach.setGeographicalArea(eastHaven);
        
        Inhabitant bowser = new Inhabitant();
        bowser.setInhabitantId("Bowser");
        bowser.setName("Bowser");
        bowser.setGeographicalArea(eastHaven);
        
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter();
            letter.setMailId("LetterM" + i);
            letter.setAddressee(peach);
            mario.assignMail(letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 1; i <= 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setMailId("ParcelL" + i);
            parcel.setAddressee(bowser);
            luigi.assignMail(parcel);
        }
        
        // Procedure 1: Add duplicate "Mario"
        boolean result1 = eastHaven.addMailman(mario);
        
        // Expected Output: false
        assertFalse(result1);
        
        // Procedure 2: Remove Mario (specify Luigi)
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Procedure 3: Verify Luigi now has 15 deliveries
        assertEquals("Luigi should have 15 deliveries", 15, luigi.getDeliveries().size());
        
        // Procedure 4: Attempt remove Luigi (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // mario is no longer in the area
        
        // Expected Output: false
        assertFalse(result3);
        
        // Procedure 5: Add "Toad"
        Mailman toad = new Mailman();
        toad.setMailmanId("Toad");
        toad.setName("Toad");
        toad.setGeographicalArea(eastHaven);
        boolean result4 = eastHaven.addMailman(toad);
        
        // Expected Output: true
        assertTrue(result4);
        
        // Procedure 6: Remove Luigi (specify Toad)
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        
        // Expected Output: true
        assertTrue(result5);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        GeographicalArea westRidge = new GeographicalArea();
        westRidge.setAreaId("WestRidge");
        system.addGeographicalArea(westRidge);
        
        Mailman alpha = new Mailman();
        alpha.setMailmanId("Alpha");
        alpha.setName("Alpha");
        alpha.setGeographicalArea(westRidge);
        
        Mailman beta = new Mailman();
        beta.setMailmanId("Beta");
        beta.setName("Beta");
        beta.setGeographicalArea(westRidge);
        
        Mailman gamma = new Mailman();
        gamma.setMailmanId("Gamma");
        gamma.setName("Gamma");
        gamma.setGeographicalArea(westRidge);
        
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setInhabitantId("Inhabitant" + i);
            inhabitant.setName("Inhabitant" + i);
            inhabitant.setGeographicalArea(westRidge);
            westRidge.addInhabitant(inhabitant);
            inhabitants.add(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCounter = 1;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setMailId("LetterA" + mailCounter);
            letter.setAddressee(inhabitants.get(i % 10));
            alpha.assignMail(letter);
            mailCounter++;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            parcel.setMailId("ParcelB" + mailCounter);
            parcel.setAddressee(inhabitants.get(i % 10));
            beta.assignMail(parcel);
            mailCounter++;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setMailId("LetterG" + mailCounter);
            letter.setAddressee(inhabitants.get(i % 10));
            gamma.assignMail(letter);
            mailCounter++;
        }
        
        // Action 1: Remove Alpha (specify Beta)
        boolean result1 = westRidge.removeMailman(alpha, beta);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Action 2: Remove Beta (specify Gamma)
        boolean result2 = westRidge.removeMailman(beta, gamma);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Verifications
        assertEquals("Gamma should have all 30 deliveries", 30, gamma.getDeliveries().size());
        assertEquals("Only Gamma should remain", 1, westRidge.getMailmen().size());
        assertTrue("Gamma should be the only remaining mailman", westRidge.getMailmen().contains(gamma));
    }
}