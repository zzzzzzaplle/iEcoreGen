import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

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
    private List<Inhabitant> westRidgeInhabitants;
    
    private Letter letter1;
    private Parcel parcel1;
    private Letter letter2;
    private Parcel parcel2;
    private Letter letter3;
    private List<RegisteredMail> xavierMails;
    private List<RegisteredMail> yvonneMails;
    private List<RegisteredMail> zackMails;
    private List<RegisteredMail> marioMails;
    private List<RegisteredMail> luigiMails;
    private List<RegisteredMail> alphaMails;
    private List<RegisteredMail> betaMails;
    private List<RegisteredMail> gammaMails;
    
    @Before
    public void setUp() {
        // Initialize all objects needed for tests
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
        
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
        
        david = new Inhabitant();
        eve = new Inhabitant();
        frank = new Inhabitant();
        walter = new Inhabitant();
        rachel = new Inhabitant();
        peach = new Inhabitant();
        bowser = new Inhabitant();
        westRidgeInhabitants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            westRidgeInhabitants.add(new Inhabitant());
        }
        
        letter1 = new Letter();
        parcel1 = new Parcel();
        letter2 = new Letter();
        parcel2 = new Parcel();
        letter3 = new Letter();
        
        xavierMails = new ArrayList<>();
        yvonneMails = new ArrayList<>();
        zackMails = new ArrayList<>();
        marioMails = new ArrayList<>();
        luigiMails = new ArrayList<>();
        alphaMails = new ArrayList<>();
        betaMails = new ArrayList<>();
        gammaMails = new ArrayList<>();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        // Add Mailmen "Alice", "Bob", "Charlie"
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add Inhabitants "David", "Eve", "Frank"
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create and assign mail items
        letter1.setAddressee(david);
        parcel1.setAddressee(eve);
        letter2.setAddressee(frank);
        parcel2.setAddressee(eve);
        letter3.setAddressee(david);
        
        centralDistrict.getAllMails().add(letter1);
        centralDistrict.getAllMails().add(parcel1);
        centralDistrict.getAllMails().add(letter2);
        centralDistrict.getAllMails().add(parcel2);
        centralDistrict.getAllMails().add(letter3);
        
        // Assign carriers
        letter1.setCarrier(alice);
        parcel1.setCarrier(bob);
        letter2.setCarrier(charlie);
        parcel2.setCarrier(alice);
        letter3.setCarrier(bob);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Removal should be successful", result);
        
        // Verifications:
        // - Alice removed
        assertFalse("Alice should be removed", centralDistrict.getMailmen().contains(alice));
        
        // - Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        assertEquals("Bob should have 4 deliveries", 4, centralDistrict.listRegisteredMail(bob).size());
        
        // - Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue("Bob should still have Parcel1", centralDistrict.listRegisteredMail(bob).contains(parcel1));
        assertTrue("Bob should still have Letter3", centralDistrict.listRegisteredMail(bob).contains(letter3));
        
        // - Charlie's assignments unchanged (now has 1 item)
        assertEquals("Charlie should have 1 delivery", 1, centralDistrict.listRegisteredMail(charlie).size());
        assertTrue("Charlie should still have Letter2", centralDistrict.listRegisteredMail(charlie).contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add Inhabitant "Walter"
        northQuarter.addInhabitant(walter);
        
        // Create and assign: 5 Letters (Xavier→Walter), 3 Parcels (Yvonne→Walter), 2 Letters (Zack→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setCarrier(xavier);
            northQuarter.getAllMails().add(letter);
            xavierMails.add(letter);
        }
        
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            parcel.setCarrier(yvonne);
            northQuarter.getAllMails().add(parcel);
            yvonneMails.add(parcel);
        }
        
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setCarrier(zack);
            northQuarter.getAllMails().add(letter);
            zackMails.add(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal should be successful", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal should be successful", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        assertEquals("Zack should have all 10 deliveries", 10, northQuarter.listRegisteredMail(zack).size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        // Add Mailmen ["Paul", "Quinn"]
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add Inhabitant "Rachel"
        southEnd.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        letter1.setCarrier(paul);
        southEnd.getAllMails().add(letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue("First removal should be successful", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is already removed, but method should handle this
        assertFalse("Removal of last mailman should fail", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        // Add Mailmen ["Mario", "Luigi"]
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add Inhabitants ["Peach", "Bowser"]
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create: 10 Letters (Mario→Peach), 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            letter.setCarrier(mario);
            eastHaven.getAllMails().add(letter);
            marioMails.add(letter);
        }
        
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            parcel.setCarrier(luigi);
            eastHaven.getAllMails().add(parcel);
            luigiMails.add(parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean addDuplicateResult = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", addDuplicateResult);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMarioResult = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removing Mario should be successful", removeMarioResult);
        
        // 3. Verify Luigi now has 15 deliveries
        assertEquals("Luigi should have 15 deliveries after reassignment", 15, eastHaven.listRegisteredMail(luigi).size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigiAttempt1 = eastHaven.removeMailman(luigi, mario); // mario is already removed
        assertFalse("Removing last mailman should fail", removeLuigiAttempt1);
        
        // 5. Add "Toad" → true
        boolean addToadResult = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should be successful", addToadResult);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeLuigiResult = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removing Luigi should be successful with Toad as replacement", removeLuigiResult);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        for (Inhabitant inhabitant : westRidgeInhabitants) {
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            RegisteredMail mail = random.nextBoolean() ? new Letter() : new Parcel();
            mail.setAddressee(westRidgeInhabitants.get(i % 10));
            mail.setCarrier(alpha);
            westRidge.getAllMails().add(mail);
            alphaMails.add(mail);
        }
        
        for (int i = 0; i < 10; i++) {
            RegisteredMail mail = random.nextBoolean() ? new Letter() : new Parcel();
            mail.setAddressee(westRidgeInhabitants.get(i % 10));
            mail.setCarrier(beta);
            westRidge.getAllMails().add(mail);
            betaMails.add(mail);
        }
        
        for (int i = 0; i < 10; i++) {
            RegisteredMail mail = random.nextBoolean() ? new Letter() : new Parcel();
            mail.setAddressee(westRidgeInhabitants.get(i % 10));
            mail.setCarrier(gamma);
            westRidge.getAllMails().add(mail);
            gammaMails.add(mail);
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal should be successful", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal should be successful", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        assertEquals("Gamma should have all 30 deliveries", 30, westRidge.listRegisteredMail(gamma).size());
    }
}