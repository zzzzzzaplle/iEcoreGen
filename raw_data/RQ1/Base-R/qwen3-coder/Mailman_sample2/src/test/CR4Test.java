import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

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
        // Initialize areas
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
        // SetUp: Create CentralDistrict area
        Area area = centralDistrict;
        
        // Add mailmen to area
        assertTrue(area.addMailman(alice));
        assertTrue(area.addMailman(bob));
        assertTrue(area.addMailman(charlie));
        
        // Add inhabitants to area
        david.setArea(area);
        eve.setArea(area);
        frank.setArea(area);
        assertTrue(area.addInhabitant(david));
        assertTrue(area.addInhabitant(eve));
        assertTrue(area.addInhabitant(frank));
        
        // Create mail items
        Letter letter1 = new Letter(); letter1.setAddressee(david);
        Parcel parcel1 = new Parcel(); parcel1.setAddressee(eve);
        Letter letter2 = new Letter(); letter2.setAddressee(frank);
        Parcel parcel2 = new Parcel(); parcel2.setAddressee(eve);
        Letter letter3 = new Letter(); letter3.setAddressee(david);
        
        // Add mail to area
        area.getMails().add(letter1);
        area.getMails().add(parcel1);
        area.getMails().add(letter2);
        area.getMails().add(parcel2);
        area.getMails().add(letter3);
        
        // Assign mail to mailmen
        assertTrue(area.assignMailToMailman(letter1, alice, david));
        assertTrue(area.assignMailToMailman(parcel1, bob, eve));
        assertTrue(area.assignMailToMailman(letter2, charlie, frank));
        assertTrue(area.assignMailToMailman(parcel2, alice, eve));
        assertTrue(area.assignMailToMailman(letter3, bob, david));
        
        // Action: Remove Alice with Bob as replacement
        boolean result = area.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications
        // Alice removed
        assertFalse(area.getMailmen().contains(alice));
        assertTrue(area.getMailmen().contains(bob));
        assertTrue(area.getMailmen().contains(charlie));
        
        // Letter1 and Parcel2 reassigned to Bob
        assertEquals(bob, letter1.getMailman());
        assertEquals(bob, parcel2.getMailman());
        
        // Bob's original deliveries unchanged
        assertEquals(bob, parcel1.getMailman());
        assertEquals(bob, letter3.getMailman());
        
        // Count Bob's deliveries (should be 4)
        int bobDeliveries = 0;
        for (RegisteredMail mail : area.getMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(bob)) {
                bobDeliveries++;
            }
        }
        assertEquals(4, bobDeliveries);
        
        // Charlie's assignments unchanged (1 item)
        int charlieDeliveries = 0;
        for (RegisteredMail mail : area.getMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(charlie)) {
                charlieDeliveries++;
            }
        }
        assertEquals(1, charlieDeliveries);
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create NorthQuarter area
        Area area = northQuarter;
        
        // Add mailmen to area
        assertTrue(area.addMailman(xavier));
        assertTrue(area.addMailman(yvonne));
        assertTrue(area.addMailman(zack));
        
        // Add inhabitant to area
        walter.setArea(area);
        assertTrue(area.addInhabitant(walter));
        
        // Create and assign 5 Letters (Xavier→Walter)
        List<Letter> xavierLetters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            area.getMails().add(letter);
            assertTrue(area.assignMailToMailman(letter, xavier, walter));
            xavierLetters.add(letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        List<Parcel> yvonneParcels = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            area.getMails().add(parcel);
            assertTrue(area.assignMailToMailman(parcel, yvonne, walter));
            yvonneParcels.add(parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        List<Letter> zackLetters = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            area.getMails().add(letter);
            assertTrue(area.assignMailToMailman(letter, zack, walter));
            zackLetters.add(letter);
        }
        
        // Action 1: Remove Yvonne with Xavier as replacement
        boolean result1 = area.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Verification after first removal
        assertFalse(area.getMailmen().contains(yvonne));
        assertTrue(area.getMailmen().contains(xavier));
        assertTrue(area.getMailmen().contains(zack));
        
        // 3 parcels moved to Xavier (now has 8 items)
        int xavierDeliveries1 = 0;
        for (RegisteredMail mail : area.getMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(xavier)) {
                xavierDeliveries1++;
            }
        }
        assertEquals(8, xavierDeliveries1);
        
        // Action 2: Remove Xavier with Zack as replacement
        boolean result2 = area.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Final verification
        assertFalse(area.getMailmen().contains(xavier));
        assertTrue(area.getMailmen().contains(zack));
        
        // Zack ends with all 10 deliveries
        int zackDeliveries = 0;
        for (RegisteredMail mail : area.getMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(zack)) {
                zackDeliveries++;
            }
        }
        assertEquals(10, zackDeliveries);
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create SouthEnd area
        Area area = southEnd;
        
        // Add mailmen to area
        assertTrue(area.addMailman(paul));
        assertTrue(area.addMailman(quinn));
        
        // Add inhabitant to area
        rachel.setArea(area);
        assertTrue(area.addInhabitant(rachel));
        
        // Create and assign Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        area.getMails().add(letter1);
        assertTrue(area.assignMailToMailman(letter1, paul, rachel));
        
        // Action 1: Attempt remove Paul specifying Quinn
        boolean result1 = area.removeMailman(paul, quinn);
        
        // Expected Output: true (normal case)
        assertTrue(result1);
        assertFalse(area.getMailmen().contains(paul));
        assertTrue(area.getMailmen().contains(quinn));
        assertEquals(quinn, letter1.getMailman());
        
        // Action 2: Attempt remove Quinn (last mailman)
        boolean result2 = area.removeMailman(quinn, paul); // paul is no longer in area
        
        // Expected Output: false
        assertFalse(result2);
        assertTrue(area.getMailmen().contains(quinn)); // Quinn should still be there
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create EastHaven area
        Area area = eastHaven;
        
        // Add mailmen to area
        assertTrue(area.addMailman(mario));
        assertTrue(area.addMailman(luigi));
        
        // Add inhabitants to area
        peach.setArea(area);
        bowser.setArea(area);
        assertTrue(area.addInhabitant(peach));
        assertTrue(area.addInhabitant(bowser));
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            area.getMails().add(letter);
            assertTrue(area.assignMailToMailman(letter, mario, peach));
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            area.getMails().add(parcel);
            assertTrue(area.assignMailToMailman(parcel, luigi, bowser));
        }
        
        // Procedure 1: Add duplicate "Mario"
        boolean result1 = area.addMailman(mario);
        
        // Expected Output: false
        assertFalse(result1);
        
        // Procedure 2: Remove Mario (specify Luigi)
        boolean result2 = area.removeMailman(mario, luigi);
        
        // Expected Output: true
        assertTrue(result2);
        assertFalse(area.getMailmen().contains(mario));
        
        // Procedure 3: Verify Luigi now has 15 deliveries (10 from Mario + 5 original)
        int luigiDeliveries = 0;
        for (RegisteredMail mail : area.getMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(luigi)) {
                luigiDeliveries++;
            }
        }
        assertEquals(15, luigiDeliveries);
        
        // Procedure 4: Attempt remove Luigi (last mailman)
        boolean result3 = area.removeMailman(luigi, mario); // mario is no longer in area
        
        // Expected Output: false
        assertFalse(result3);
        assertTrue(area.getMailmen().contains(luigi));
        
        // Procedure 5: Add "Toad"
        boolean result4 = area.addMailman(toad);
        
        // Expected Output: true
        assertTrue(result4);
        assertTrue(area.getMailmen().contains(toad));
        
        // Procedure 6: Remove Luigi (specify Toad)
        boolean result5 = area.removeMailman(luigi, toad);
        
        // Expected Output: true
        assertTrue(result5);
        assertFalse(area.getMailmen().contains(luigi));
        assertTrue(area.getMailmen().contains(toad));
        
        // Verify all mail now assigned to Toad
        int toadDeliveries = 0;
        for (RegisteredMail mail : area.getMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(toad)) {
                toadDeliveries++;
            }
        }
        assertEquals(15, toadDeliveries);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create WestRidge area
        Area area = westRidge;
        
        // Add mailmen to area
        assertTrue(area.addMailman(alpha));
        assertTrue(area.addMailman(beta));
        assertTrue(area.addMailman(gamma));
        
        // Add 10 inhabitants to area
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setName("Inhabitant" + i);
            inhabitant.setArea(area);
            area.addInhabitant(inhabitant);
            inhabitants.add(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCount = 0;
        for (int i = 0; i < 10; i++) {
            // Alpha's mail
            Letter letter1 = new Letter();
            letter1.setAddressee(inhabitants.get(i % 10));
            area.getMails().add(letter1);
            assertTrue(area.assignMailToMailman(letter1, alpha, inhabitants.get(i % 10)));
            mailCount++;
            
            // Beta's mail
            Parcel parcel1 = new Parcel();
            parcel1.setAddressee(inhabitants.get((i + 1) % 10));
            area.getMails().add(parcel1);
            assertTrue(area.assignMailToMailman(parcel1, beta, inhabitants.get((i + 1) % 10)));
            mailCount++;
            
            // Gamma's mail
            Letter letter2 = new Letter();
            letter2.setAddressee(inhabitants.get((i + 2) % 10));
            area.getMails().add(letter2);
            assertTrue(area.assignMailToMailman(letter2, gamma, inhabitants.get((i + 2) % 10)));
            mailCount++;
        }
        
        assertEquals(30, mailCount);
        
        // Action 1: Remove Alpha (specify Beta)
        boolean result1 = area.removeMailman(alpha, beta);
        
        // Expected Output: true
        assertTrue(result1);
        assertFalse(area.getMailmen().contains(alpha));
        
        // Action 2: Remove Beta (specify Gamma)
        boolean result2 = area.removeMailman(beta, gamma);
        
        // Expected Output: true
        assertTrue(result2);
        assertFalse(area.getMailmen().contains(beta));
        
        // Verifications: Gamma ends with all 30 deliveries
        int gammaDeliveries = 0;
        for (RegisteredMail mail : area.getMails()) {
            if (mail.getMailman() != null && mail.getMailman().equals(gamma)) {
                gammaDeliveries++;
            }
        }
        assertEquals(30, gammaDeliveries);
        
        // Only Gamma remains
        assertEquals(1, area.getMailmen().size());
        assertTrue(area.getMailmen().contains(gamma));
    }
}