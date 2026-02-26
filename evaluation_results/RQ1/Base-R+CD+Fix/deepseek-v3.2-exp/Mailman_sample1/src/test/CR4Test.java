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
    
    private Mailman alice, bob, charlie;
    private Mailman xavier, yvonne, zack;
    private Mailman paul, quinn;
    private Mailman mario, luigi, toad;
    private Mailman alpha, beta, gamma;
    
    private Inhabitant david, eve, frank;
    private Inhabitant walter;
    private Inhabitant rachel;
    private Inhabitant peach, bowser;
    private List<Inhabitant> westRidgeInhabitants;
    
    private Letter letter1, letter2, letter3;
    private Parcel parcel1, parcel2;
    private List<RegisteredMail> xavierLetters, yvonneParcels, zackLetters;
    private Letter paulLetter;
    private List<RegisteredMail> marioLetters, luigiParcels;
    private List<RegisteredMail> alphaMails, betaMails, gammaMails;
    
    @Before
    public void setUp() {
        // Initialize all objects needed for tests
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
        
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
        
        westRidgeInhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant();
            inhabitant.setName("Inhabitant" + i);
            westRidgeInhabitants.add(inhabitant);
        }
        
        // Initialize mail items
        letter1 = new Letter();
        letter2 = new Letter();
        letter3 = new Letter();
        parcel1 = new Parcel();
        parcel2 = new Parcel();
        
        paulLetter = new Letter();
        
        xavierLetters = new ArrayList<>();
        yvonneParcels = new ArrayList<>();
        zackLetters = new ArrayList<>();
        
        marioLetters = new ArrayList<>();
        luigiParcels = new ArrayList<>();
        
        alphaMails = new ArrayList<>();
        betaMails = new ArrayList<>();
        gammaMails = new ArrayList<>();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp CentralDistrict
        centralDistrict.setMailmen(new ArrayList<>());
        centralDistrict.setInhabitants(new ArrayList<>());
        centralDistrict.setAllMails(new ArrayList<>());
        
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        centralDistrict.getAllMails().add(letter1);
        centralDistrict.getAllMails().add(parcel1);
        centralDistrict.getAllMails().add(letter2);
        centralDistrict.getAllMails().add(parcel2);
        centralDistrict.getAllMails().add(letter3);
        
        // Assign mail items
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Verify initial assignments
        List<RegisteredMail> aliceMail = centralDistrict.listRegisteredMail(alice);
        List<RegisteredMail> bobMail = centralDistrict.listRegisteredMail(bob);
        List<RegisteredMail> charlieMail = centralDistrict.listRegisteredMail(charlie);
        
        assertEquals(2, aliceMail.size());
        assertEquals(2, bobMail.size());
        assertEquals(1, charlieMail.size());
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications
        // - Alice removed
        assertFalse(centralDistrict.getMailmen().contains(alice));
        
        // - Letter1 and Parcel2 reassigned to Bob (now has 4 items), Bob's original deliveries (Parcel1, Letter3) unchanged
        List<RegisteredMail> bobMailAfter = centralDistrict.listRegisteredMail(bob);
        assertNotNull(bobMailAfter);
        assertEquals(4, bobMailAfter.size());
        
        // - Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieMailAfter = centralDistrict.listRegisteredMail(charlie);
        assertNotNull(charlieMailAfter);
        assertEquals(1, charlieMailAfter.size());
        
        // Verify specific reassignments
        assertEquals(bob, letter1.getCarrier());
        assertEquals(bob, parcel2.getCarrier());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp NorthQuarter
        northQuarter.setMailmen(new ArrayList<>());
        northQuarter.setInhabitants(new ArrayList<>());
        northQuarter.setAllMails(new ArrayList<>());
        
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        northQuarter.addInhabitant(walter);
        
        // Create 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            northQuarter.getAllMails().add(letter);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
            xavierLetters.add(letter);
        }
        
        // Create 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            northQuarter.getAllMails().add(parcel);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
            yvonneParcels.add(parcel);
        }
        
        // Create 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            northQuarter.getAllMails().add(letter);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
            zackLetters.add(letter);
        }
        
        // Verify initial assignments
        assertEquals(5, northQuarter.listRegisteredMail(xavier).size());
        assertEquals(3, northQuarter.listRegisteredMail(yvonne).size());
        assertEquals(2, northQuarter.listRegisteredMail(zack).size());
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        assertTrue(result1);
        
        // Verify after first removal
        assertEquals(8, northQuarter.listRegisteredMail(xavier).size()); // 5 original + 3 from Yvonne
        assertEquals(2, northQuarter.listRegisteredMail(zack).size()); // unchanged
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        assertTrue(result2);
        
        // Expected Output: true, true
        assertTrue(result1);
        assertTrue(result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zackFinalMail = northQuarter.listRegisteredMail(zack);
        assertNotNull(zackFinalMail);
        assertEquals(10, zackFinalMail.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp SouthEnd
        southEnd.setMailmen(new ArrayList<>());
        southEnd.setInhabitants(new ArrayList<>());
        southEnd.setAllMails(new ArrayList<>());
        
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        southEnd.addInhabitant(rachel);
        
        southEnd.getAllMails().add(paulLetter);
        southEnd.assignRegisteredMailDeliver(paul, rachel, paulLetter);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue(result1);
        
        // Verify Paul is removed and Quinn has the mail
        assertFalse(southEnd.getMailmen().contains(paul));
        assertEquals(quinn, paulLetter.getCarrier());
        
        // 2. Attempt remove Quinn (last mailman) → false
        try {
            southEnd.removeMailman(quinn, paul); // paul is no longer in the system
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot remove the last mailman from the area", e.getMessage());
        }
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp EastHaven
        eastHaven.setMailmen(new ArrayList<>());
        eastHaven.setInhabitants(new ArrayList<>());
        eastHaven.setAllMails(new ArrayList<>());
        
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            eastHaven.getAllMails().add(letter);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
            marioLetters.add(letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            eastHaven.getAllMails().add(parcel);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
            luigiParcels.add(parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        Mailman marioDuplicate = new Mailman();
        marioDuplicate.setName("Mario");
        boolean result1 = eastHaven.addMailman(marioDuplicate);
        assertFalse(result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        assertTrue(result2);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMailAfter = eastHaven.listRegisteredMail(luigi);
        assertNotNull(luigiMailAfter);
        assertEquals(15, luigiMailAfter.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        try {
            eastHaven.removeMailman(luigi, mario); // mario is no longer in the system
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot remove the last mailman from the area", e.getMessage());
        }
        
        // 5. Add "Toad" → true
        boolean result5 = eastHaven.addMailman(toad);
        assertTrue(result5);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result6 = eastHaven.removeMailman(luigi, toad);
        assertTrue(result6);
        
        // Verify final state
        assertFalse(eastHaven.getMailmen().contains(luigi));
        assertTrue(eastHaven.getMailmen().contains(toad));
        
        List<RegisteredMail> toadFinalMail = eastHaven.listRegisteredMail(toad);
        assertNotNull(toadFinalMail);
        assertEquals(15, toadFinalMail.size());
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp WestRidge
        westRidge.setMailmen(new ArrayList<>());
        westRidge.setInhabitants(new ArrayList<>());
        westRidge.setAllMails(new ArrayList<>());
        
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        for (Inhabitant inhabitant : westRidgeInhabitants) {
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCounter = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.getAllMails().add(letter);
            westRidge.assignRegisteredMailDeliver(alpha, westRidgeInhabitants.get(mailCounter % 10), letter);
            alphaMails.add(letter);
            mailCounter++;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            westRidge.getAllMails().add(parcel);
            westRidge.assignRegisteredMailDeliver(beta, westRidgeInhabitants.get(mailCounter % 10), parcel);
            betaMails.add(parcel);
            mailCounter++;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.getAllMails().add(letter);
            westRidge.assignRegisteredMailDeliver(gamma, westRidgeInhabitants.get(mailCounter % 10), letter);
            gammaMails.add(letter);
            mailCounter++;
        }
        
        // Verify initial assignments
        assertEquals(10, westRidge.listRegisteredMail(alpha).size());
        assertEquals(10, westRidge.listRegisteredMail(beta).size());
        assertEquals(10, westRidge.listRegisteredMail(gamma).size());
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue(result1);
        
        // Verify after first removal
        assertEquals(20, westRidge.listRegisteredMail(beta).size()); // 10 original + 10 from Alpha
        assertEquals(10, westRidge.listRegisteredMail(gamma).size()); // unchanged
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue(result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaFinalMail = westRidge.listRegisteredMail(gamma);
        assertNotNull(gammaFinalMail);
        assertEquals(30, gammaFinalMail.size());
        
        // Verify all mailmen except Gamma are removed
        assertFalse(westRidge.getMailmen().contains(alpha));
        assertFalse(westRidge.getMailmen().contains(beta));
        assertTrue(westRidge.getMailmen().contains(gamma));
    }
}