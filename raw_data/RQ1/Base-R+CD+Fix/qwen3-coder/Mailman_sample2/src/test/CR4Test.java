import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create mail items
        Letter letter1 = new Letter();
        Parcel parcel1 = new Parcel();
        Letter letter2 = new Letter();
        Parcel parcel2 = new Parcel();
        Letter letter3 = new Letter();
        
        // Assign mail items
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Add all mails to geographical area
        List<RegisteredMail> allMails = new ArrayList<>();
        allMails.add(letter1);
        allMails.add(parcel1);
        allMails.add(letter2);
        allMails.add(parcel2);
        allMails.add(letter3);
        centralDistrict.setAllMails(allMails);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications:
        // - Alice removed
        assertFalse(centralDistrict.getMailmen().contains(alice));
        
        // - Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsMails = centralDistrict.listRegisteredMail(bob);
        assertNotNull(bobsMails);
        assertEquals(4, bobsMails.size());
        
        // - Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue(bobsMails.contains(parcel1));
        assertTrue(bobsMails.contains(letter3));
        
        // - Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesMails = centralDistrict.listRegisteredMail(charlie);
        assertNotNull(charliesMails);
        assertEquals(1, charliesMails.size());
        assertTrue(charliesMails.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        northQuarter.addInhabitant(walter);
        
        // Create mail items
        List<RegisteredMail> allMails = new ArrayList<>();
        
        // Create 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
            allMails.add(letter);
        }
        
        // Create 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
            allMails.add(parcel);
        }
        
        // Create 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
            allMails.add(letter);
        }
        
        northQuarter.setAllMails(allMails);
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zacksMails = northQuarter.listRegisteredMail(zack);
        assertNotNull(zacksMails);
        assertEquals(10, zacksMails.size());
        assertEquals(1, northQuarter.getMailmen().size());
        assertTrue(northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        southEnd.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        List<RegisteredMail> allMails = new ArrayList<>();
        allMails.add(letter1);
        southEnd.setAllMails(allMails);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue(result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is already removed, but method should fail due to being last mailman
        assertFalse(result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create mail items
        List<RegisteredMail> allMails = new ArrayList<>();
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
            allMails.add(letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
            allMails.add(parcel);
        }
        
        eastHaven.setAllMails(allMails);
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean addDuplicateResult = eastHaven.addMailman(mario);
        assertFalse(addDuplicateResult);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMarioResult = eastHaven.removeMailman(mario, luigi);
        assertTrue(removeMarioResult);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisMails = eastHaven.listRegisteredMail(luigi);
        assertNotNull(luigisMails);
        assertEquals(15, luigisMails.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigiAttempt1 = eastHaven.removeMailman(luigi, mario); // mario is already removed
        assertFalse(removeLuigiAttempt1);
        
        // 5. Add "Toad" → true
        boolean addToadResult = eastHaven.addMailman(toad);
        assertTrue(addToadResult);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeLuigiResult = eastHaven.removeMailman(luigi, toad);
        assertTrue(removeLuigiResult);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitants.add(inhabitant);
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        List<RegisteredMail> allMails = new ArrayList<>();
        int inhabitantIndex = 0;
        
        // Create 10 mail items for Alpha
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(inhabitantIndex), letter);
            allMails.add(letter);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Create 10 mail items for Beta
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(inhabitantIndex), parcel);
            allMails.add(parcel);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Create 10 mail items for Gamma
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(inhabitantIndex), letter);
            allMails.add(letter);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        westRidge.setAllMails(allMails);
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue(result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue(result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasMails = westRidge.listRegisteredMail(gamma);
        assertNotNull(gammasMails);
        assertEquals(30, gammasMails.size());
        assertEquals(1, westRidge.getMailmen().size());
        assertTrue(westRidge.getMailmen().contains(gamma));
    }
}