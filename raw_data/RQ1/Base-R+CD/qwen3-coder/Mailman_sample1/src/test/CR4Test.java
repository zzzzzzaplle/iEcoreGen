import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    private GeographicalArea area;
    private Mailman alice;
    private Mailman bob;
    private Mailman charlie;
    private Inhabitant david;
    private Inhabitant eve;
    private Inhabitant frank;
    private Letter letter1;
    private Parcel parcel1;
    private Letter letter2;
    private Parcel parcel2;
    private Letter letter3;
    
    @Before
    public void setUp() {
        area = new GeographicalArea();
        alice = new Mailman();
        bob = new Mailman();
        charlie = new Mailman();
        david = new Inhabitant();
        eve = new Inhabitant();
        frank = new Inhabitant();
        letter1 = new Letter();
        parcel1 = new Parcel();
        letter2 = new Letter();
        parcel2 = new Parcel();
        letter3 = new Letter();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // Setup
        area.addMailman(alice);
        area.addMailman(bob);
        area.addMailman(charlie);
        area.addInhabitant(david);
        area.addInhabitant(eve);
        area.addInhabitant(frank);
        
        // Create and assign mail items
        letter1.setAddressee(david);
        letter1.setCarrier(alice);
        area.getAllMails().add(letter1);
        
        parcel1.setAddressee(eve);
        parcel1.setCarrier(bob);
        area.getAllMails().add(parcel1);
        
        letter2.setAddressee(frank);
        letter2.setCarrier(charlie);
        area.getAllMails().add(letter2);
        
        parcel2.setAddressee(eve);
        parcel2.setCarrier(alice);
        area.getAllMails().add(parcel2);
        
        letter3.setAddressee(david);
        letter3.setCarrier(bob);
        area.getAllMails().add(letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = area.removeMailman(alice, bob);
        
        // Verify expected output
        assertTrue("Alice removal should succeed", result);
        
        // Verify Alice removed
        assertFalse("Alice should be removed from mailmen", area.getMailmen().contains(alice));
        
        // Verify Letter1 and Parcel2 reassigned to Bob
        assertEquals("Bob should have 4 deliveries", 4, area.listRegisteredMail(bob).size());
        assertEquals("Letter1 should be reassigned to Bob", bob, letter1.getCarrier());
        assertEquals("Parcel2 should be reassigned to Bob", bob, parcel2.getCarrier());
        
        // Verify Bob's original deliveries unchanged
        assertEquals("Parcel1 should still be assigned to Bob", bob, parcel1.getCarrier());
        assertEquals("Letter3 should still be assigned to Bob", bob, letter3.getCarrier());
        
        // Verify Charlie's assignments unchanged
        assertEquals("Charlie should have 1 delivery", 1, area.listRegisteredMail(charlie).size());
        assertEquals("Letter2 should still be assigned to Charlie", charlie, letter2.getCarrier());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Setup
        GeographicalArea northQuarter = new GeographicalArea();
        Mailman xavier = new Mailman();
        Mailman yvonne = new Mailman();
        Mailman zack = new Mailman();
        Inhabitant walter = new Inhabitant();
        
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        northQuarter.addInhabitant(walter);
        
        // Create and assign 10 mail items
        List<RegisteredMail> mails = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setCarrier(xavier);
            mails.add(letter);
        }
        
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            parcel.setCarrier(yvonne);
            mails.add(parcel);
        }
        
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setCarrier(zack);
            mails.add(letter);
        }
        
        northQuarter.setAllMails(mails);
        
        // Action 1: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Action 2: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Verify expected outputs
        assertTrue("Yvonne removal should succeed", result1);
        assertTrue("Xavier removal should succeed", result2);
        
        // Verify Zack remains with all 10 deliveries
        List<RegisteredMail> zacksDeliveries = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have deliveries", zacksDeliveries);
        assertEquals("Zack should have all 10 deliveries", 10, zacksDeliveries.size());
        
        // Verify all deliveries are assigned to Zack
        for (RegisteredMail mail : northQuarter.getAllMails()) {
            assertEquals("All mail should be assigned to Zack", zack, mail.getCarrier());
        }
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Setup
        GeographicalArea southEnd = new GeographicalArea();
        Mailman paul = new Mailman();
        Mailman quinn = new Mailman();
        Inhabitant rachel = new Inhabitant();
        
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        southEnd.addInhabitant(rachel);
        
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        letter1.setCarrier(paul);
        southEnd.getAllMails().add(letter1);
        
        // Action 1: Attempt remove Paul specifying Quinn
        boolean result1 = southEnd.removeMailman(paul, quinn);
        
        // Verify result 1
        assertTrue("Paul removal should succeed", result1);
        
        // Action 2: Attempt remove Quinn (last mailman)
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul no longer exists, but should fail due to being last mailman
        
        // Verify result 2
        assertFalse("Quinn removal should fail (last mailman)", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Setup
        GeographicalArea eastHaven = new GeographicalArea();
        Mailman mario = new Mailman();
        Mailman luigi = new Mailman();
        Inhabitant peach = new Inhabitant();
        Inhabitant bowser = new Inhabitant();
        
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 15 mail items
        List<RegisteredMail> mails = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            letter.setCarrier(mario);
            mails.add(letter);
        }
        
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            parcel.setCarrier(luigi);
            mails.add(parcel);
        }
        
        eastHaven.setAllMails(mails);
        
        // Action 1: Add duplicate "Mario"
        boolean result1 = eastHaven.addMailman(mario);
        
        // Action 2: Remove Mario (specify Luigi)
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        
        // Action 3: Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisDeliveries = eastHaven.listRegisteredMail(luigi);
        
        // Action 4: Attempt remove Luigi
        boolean result4 = eastHaven.removeMailman(luigi, mario); // mario no longer exists, but should fail due to being last mailman
        
        // Action 5: Add "Toad"
        Mailman toad = new Mailman();
        boolean result5 = eastHaven.addMailman(toad);
        
        // Action 6: Remove Luigi (specify Toad)
        boolean result6 = eastHaven.removeMailman(luigi, toad);
        
        // Verify results
        assertFalse("Adding duplicate Mario should fail", result1);
        assertTrue("Mario removal should succeed", result2);
        assertNotNull("Luigi should have deliveries", luigisDeliveries);
        assertEquals("Luigi should have 15 deliveries", 15, luigisDeliveries.size());
        assertFalse("Luigi removal should fail (last mailman)", result4);
        assertTrue("Adding Toad should succeed", result5);
        assertTrue("Luigi removal should succeed after adding Toad", result6);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Setup
        GeographicalArea westRidge = new GeographicalArea();
        Mailman alpha = new Mailman();
        Mailman beta = new Mailman();
        Mailman gamma = new Mailman();
        
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitants.add(inhabitant);
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        List<RegisteredMail> mails = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants.get(i % inhabitants.size()));
            letter.setCarrier(alpha);
            mails.add(letter);
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(inhabitants.get(i % inhabitants.size()));
            parcel.setCarrier(beta);
            mails.add(parcel);
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants.get(i % inhabitants.size()));
            letter.setCarrier(gamma);
            mails.add(letter);
        }
        
        westRidge.setAllMails(mails);
        
        // Action 1: Remove Alpha (specify Beta)
        boolean result1 = westRidge.removeMailman(alpha, beta);
        
        // Action 2: Remove Beta (specify Gamma)
        boolean result2 = westRidge.removeMailman(beta, gamma);
        
        // Verify results
        assertTrue("Alpha removal should succeed", result1);
        assertTrue("Beta removal should succeed", result2);
        
        // Verify Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasDeliveries = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have deliveries", gammasDeliveries);
        assertEquals("Gamma should have all 30 deliveries", 30, gammasDeliveries.size());
        
        // Verify no mail items are assigned to Alpha or Beta
        assertNull("Alpha should have no deliveries", westRidge.listRegisteredMail(alpha));
        assertNull("Beta should have no deliveries", westRidge.listRegisteredMail(beta));
    }
}