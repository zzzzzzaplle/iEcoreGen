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
        // Initialize mailmen
        alice = new Mailman();
        bob = new Mailman();
        charlie = new Mailman();
        xavier = new Mailman();
        yvonne = new Mailman();
        zack = new Mailman();
        paul = new Mailman();
        quinn = new Mailman();
        mario = new Mailman();
        luigi = new Mailman();
        toad = new Mailman();
        alpha = new Mailman();
        beta = new Mailman();
        gamma = new Mailman();
        
        // Initialize inhabitants
        david = new Inhabitant();
        eve = new Inhabitant();
        frank = new Inhabitant();
        walter = new Inhabitant();
        rachel = new Inhabitant();
        peach = new Inhabitant();
        bowser = new Inhabitant();
        
        // Initialize geographical areas
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // Set up CentralDistrict
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(alice);
        mailmen.add(bob);
        mailmen.add(charlie);
        centralDistrict.setMailmen(mailmen);
        
        List<Inhabitant> inhabitants = new ArrayList<>();
        inhabitants.add(david);
        inhabitants.add(eve);
        inhabitants.add(frank);
        centralDistrict.setInhabitants(inhabitants);
        
        // Create mail items
        Letter letter1 = new Letter();
        letter1.setAddressee(david);
        letter1.setCarrier(alice);
        
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(eve);
        parcel1.setCarrier(bob);
        
        Letter letter2 = new Letter();
        letter2.setAddressee(frank);
        letter2.setCarrier(charlie);
        
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(eve);
        parcel2.setCarrier(alice);
        
        Letter letter3 = new Letter();
        letter3.setAddressee(david);
        letter3.setCarrier(bob);
        
        // Add mail to area
        List<RegisteredMail> mails = new ArrayList<>();
        mails.add(letter1);
        mails.add(parcel1);
        mails.add(letter2);
        mails.add(parcel2);
        mails.add(letter3);
        centralDistrict.setRegisteredMails(mails);
        
        // Action: Remove Alice specifying Bob as replacement
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Verify expected output
        assertTrue("Alice should be removed successfully", result);
        
        // Verify Alice removed
        assertFalse("Alice should be removed from mailmen list", 
                   centralDistrict.getMailmen().contains(alice));
        
        // Verify Letter1 and Parcel2 reassigned to Bob
        List<RegisteredMail> allDeliveries = centralDistrict.getAllDeliveries();
        int bobDeliveries = 0;
        int charlieDeliveries = 0;
        
        for (RegisteredMail mail : allDeliveries) {
            if (mail.getCarrier().equals(bob)) {
                bobDeliveries++;
            } else if (mail.getCarrier().equals(charlie)) {
                charlieDeliveries++;
            }
        }
        
        // Bob should have 4 items (original 2 + reassigned 2)
        assertEquals("Bob should have 4 deliveries", 4, bobDeliveries);
        
        // Charlie should have 1 item (unchanged)
        assertEquals("Charlie should have 1 delivery", 1, charlieDeliveries);
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Set up NorthQuarter
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(xavier);
        mailmen.add(yvonne);
        mailmen.add(zack);
        northQuarter.setMailmen(mailmen);
        
        List<Inhabitant> inhabitants = new ArrayList<>();
        inhabitants.add(walter);
        northQuarter.setInhabitants(inhabitants);
        
        // Create mail items
        List<RegisteredMail> mails = new ArrayList<>();
        
        // 5 Letters from Xavier to Walter
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setCarrier(xavier);
            mails.add(letter);
        }
        
        // 3 Parcels from Yvonne to Walter
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            parcel.setCarrier(yvonne);
            mails.add(parcel);
        }
        
        // 2 Letters from Zack to Walter
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setCarrier(zack);
            mails.add(letter);
        }
        
        northQuarter.setRegisteredMails(mails);
        
        // Action: Remove Yvonne specifying Xavier as replacement
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        assertTrue("First removal should succeed", result1);
        
        // Verify 3 parcels moved to Xavier (now has 8 items)
        List<RegisteredMail> deliveriesAfterFirstRemoval = northQuarter.getAllDeliveries();
        int xavierDeliveriesAfterFirst = 0;
        for (RegisteredMail mail : deliveriesAfterFirstRemoval) {
            if (mail.getCarrier().equals(xavier)) {
                xavierDeliveriesAfterFirst++;
            }
        }
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xavierDeliveriesAfterFirst);
        
        // Action: Remove Xavier specifying Zack as replacement
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        assertTrue("Second removal should succeed", result2);
        
        // Verify Zack has all 10 deliveries
        List<RegisteredMail> finalDeliveries = northQuarter.getAllDeliveries();
        int zackFinalDeliveries = 0;
        for (RegisteredMail mail : finalDeliveries) {
            if (mail.getCarrier().equals(zack)) {
                zackFinalDeliveries++;
            }
        }
        assertEquals("Zack should have all 10 deliveries", 10, zackFinalDeliveries);
        
        // Verify only Zack remains
        assertEquals("Only one mailman should remain", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Set up SouthEnd
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(paul);
        mailmen.add(quinn);
        southEnd.setMailmen(mailmen);
        
        List<Inhabitant> inhabitants = new ArrayList<>();
        inhabitants.add(rachel);
        southEnd.setInhabitants(inhabitants);
        
        // Create mail item
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        letter1.setCarrier(paul);
        
        List<RegisteredMail> mails = new ArrayList<>();
        mails.add(letter1);
        southEnd.setRegisteredMails(mails);
        
        // Action 1: Remove Paul specifying Quinn
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("Normal removal should succeed", result1);
        
        // Action 2: Attempt to remove Quinn (last mailman)
        boolean result2 = southEnd.removeMailman(quinn, paul); // Paul is no longer available, but should fail anyway
        assertFalse("Removing last mailman should fail", result2);
        
        // Verify Quinn is still there
        assertTrue("Quinn should still be in mailmen list", southEnd.getMailmen().contains(quinn));
        assertEquals("Should still have one mailman", 1, southEnd.getMailmen().size());
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Set up EastHaven
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(mario);
        mailmen.add(luigi);
        eastHaven.setMailmen(mailmen);
        
        List<Inhabitant> inhabitants = new ArrayList<>();
        inhabitants.add(peach);
        inhabitants.add(bowser);
        eastHaven.setInhabitants(inhabitants);
        
        // Create mail items
        List<RegisteredMail> mails = new ArrayList<>();
        
        // 10 Letters from Mario to Peach
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            letter.setCarrier(mario);
            mails.add(letter);
        }
        
        // 5 Parcels from Luigi to Bowser
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            parcel.setCarrier(luigi);
            mails.add(parcel);
        }
        
        eastHaven.setRegisteredMails(mails);
        
        // Action 1: Add duplicate "Mario"
        boolean result1 = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", result1);
        
        // Action 2: Remove Mario specifying Luigi
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removing Mario should succeed", result2);
        
        // Verify Luigi now has 15 deliveries (10 from Mario + 5 original)
        // Note: Specification says "5 deliveries" but logic requires 15 (Mario's 10 + Luigi's original 5)
        List<RegisteredMail> deliveriesAfterRemoval = eastHaven.getAllDeliveries();
        int luigiDeliveries = 0;
        for (RegisteredMail mail : deliveriesAfterRemoval) {
            if (mail.getCarrier().equals(luigi)) {
                luigiDeliveries++;
            }
        }
        assertEquals("Luigi should have 15 deliveries", 15, luigiDeliveries);
        
        // Action 3: Attempt to remove Luigi (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // Mario is no longer available
        assertFalse("Removing last mailman should fail", result3);
        
        // Action 4: Add "Toad"
        boolean result4 = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should succeed", result4);
        
        // Action 5: Remove Luigi specifying Toad
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removing Luigi should succeed", result5);
        
        // Verify final state
        assertEquals("Should have one mailman", 1, eastHaven.getMailmen().size());
        assertTrue("Toad should be the only mailman", eastHaven.getMailmen().contains(toad));
        
        // Verify Toad has all 15 deliveries
        List<RegisteredMail> finalDeliveries = eastHaven.getAllDeliveries();
        int toadDeliveries = 0;
        for (RegisteredMail mail : finalDeliveries) {
            if (mail.getCarrier().equals(toad)) {
                toadDeliveries++;
            }
        }
        assertEquals("Toad should have all 15 deliveries", 15, toadDeliveries);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Set up WestRidge
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(alpha);
        mailmen.add(beta);
        mailmen.add(gamma);
        westRidge.setMailmen(mailmen);
        
        // Create 10 inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            inhabitants.add(new Inhabitant());
        }
        westRidge.setInhabitants(inhabitants);
        
        // Create 30 mail items (10 each mailman)
        List<RegisteredMail> mails = new ArrayList<>();
        
        // 10 items for Alpha
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants.get(i % 10));
            letter.setCarrier(alpha);
            mails.add(letter);
        }
        
        // 10 items for Beta
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(inhabitants.get(i % 10));
            parcel.setCarrier(beta);
            mails.add(parcel);
        }
        
        // 10 items for Gamma
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants.get(i % 10));
            letter.setCarrier(gamma);
            mails.add(letter);
        }
        
        westRidge.setRegisteredMails(mails);
        
        // Action 1: Remove Alpha specifying Beta
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal should succeed", result1);
        
        // Action 2: Remove Beta specifying Gamma
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal should succeed", result2);
        
        // Verification: Gamma ends with all 30 deliveries
        List<RegisteredMail> finalDeliveries = westRidge.getAllDeliveries();
        int gammaDeliveries = 0;
        for (RegisteredMail mail : finalDeliveries) {
            if (mail.getCarrier().equals(gamma)) {
                gammaDeliveries++;
            }
        }
        assertEquals("Gamma should have all 30 deliveries", 30, gammaDeliveries);
        
        // Verify only Gamma remains
        assertEquals("Only one mailman should remain", 1, westRidge.getMailmen().size());
        assertTrue("Gamma should be the only remaining mailman", westRidge.getMailmen().contains(gamma));
    }
}