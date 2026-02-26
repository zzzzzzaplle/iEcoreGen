import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    private GeographicalArea centralDistrict;
    private GeographicalArea northQuarter;
    private GeographicalArea southEnd;
    private GeographicalArea eastHaven;
    private GeographicalArea westRidge;
    
    private Mailman alice, bob, charlie, xavier, yvonne, zack, paul, quinn, mario, luigi, toad, alpha, beta, gamma;
    private Inhabitant david, eve, frank, walter, rachel, peach, bowser;
    
    @Before
    public void setUp() {
        // Initialize all objects needed for tests
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
        
        // Initialize mailmen
        alice = new Mailman();
        alice.setName("Alice");
        bob = new Mailman();
        bob.setName("Bob");
        charlie = new Mailman();
        charlie.setName("Charlie");
        xavier = new Mailman();
        xavier.setName("Xavier");
        yvonne = new Mailman();
        yvonne.setName("Yvonne");
        zack = new Mailman();
        zack.setName("Zack");
        paul = new Mailman();
        paul.setName("Paul");
        quinn = new Mailman();
        quinn.setName("Quinn");
        mario = new Mailman();
        mario.setName("Mario");
        luigi = new Mailman();
        luigi.setName("Luigi");
        toad = new Mailman();
        toad.setName("Toad");
        alpha = new Mailman();
        alpha.setName("Alpha");
        beta = new Mailman();
        beta.setName("Beta");
        gamma = new Mailman();
        gamma.setName("Gamma");
        
        // Initialize inhabitants
        david = new Inhabitant();
        david.setName("David");
        eve = new Inhabitant();
        eve.setName("Eve");
        frank = new Inhabitant();
        frank.setName("Frank");
        walter = new Inhabitant();
        walter.setName("Walter");
        rachel = new Inhabitant();
        rachel.setName("Rachel");
        peach = new Inhabitant();
        peach.setName("Peach");
        bowser = new Inhabitant();
        bowser.setName("Bowser");
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // Set up CentralDistrict with mailmen and inhabitants
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create mail items
        Letter letter1 = new Letter();
        letter1.setAddressee(david);
        centralDistrict.getAllMails().add(letter1);
        
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(eve);
        centralDistrict.getAllMails().add(parcel1);
        
        Letter letter2 = new Letter();
        letter2.setAddressee(frank);
        centralDistrict.getAllMails().add(letter2);
        
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(eve);
        centralDistrict.getAllMails().add(parcel2);
        
        Letter letter3 = new Letter();
        letter3.setAddressee(david);
        centralDistrict.getAllMails().add(letter3);
        
        // Assign mail to mailmen
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Verify initial assignments
        List<RegisteredMail> aliceMails = centralDistrict.listRegisteredMail(alice);
        List<RegisteredMail> bobMails = centralDistrict.listRegisteredMail(bob);
        List<RegisteredMail> charlieMails = centralDistrict.listRegisteredMail(charlie);
        
        assertEquals(2, aliceMails.size());
        assertEquals(2, bobMails.size());
        assertEquals(1, charlieMails.size());
        
        // Remove Alice with Bob as replacement
        boolean result = centralDistrict.removeMailman(alice, bob);
        assertTrue(result);
        
        // Verify Alice is removed
        assertFalse(centralDistrict.getMailmen().contains(alice));
        assertNull(alice.getAssignedArea());
        
        // Verify mail reassignments
        List<RegisteredMail> newBobMails = centralDistrict.listRegisteredMail(bob);
        List<RegisteredMail> newCharlieMails = centralDistrict.listRegisteredMail(charlie);
        
        // Bob should now have 4 items (original 2 + Alice's 2)
        assertEquals(4, newBobMails.size());
        // Charlie should still have 1 item
        assertEquals(1, newCharlieMails.size());
        
        // Verify Alice's original mail is now assigned to Bob
        assertTrue(newBobMails.contains(letter1));
        assertTrue(newBobMails.contains(parcel2));
        // Verify Bob's original mail is unchanged
        assertTrue(newBobMails.contains(parcel1));
        assertTrue(newBobMails.contains(letter3));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Set up NorthQuarter with mailmen and inhabitants
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        northQuarter.addInhabitant(walter);
        
        // Create and assign 5 letters from Xavier to Walter
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            northQuarter.getAllMails().add(letter);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        // Create and assign 3 parcels from Yvonne to Walter
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            northQuarter.getAllMails().add(parcel);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        // Create and assign 2 letters from Zack to Walter
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            northQuarter.getAllMails().add(letter);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Remove Yvonne with Xavier as replacement
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        assertTrue(result1);
        
        // Verify Yvonne is removed
        assertFalse(northQuarter.getMailmen().contains(yvonne));
        
        // Remove Xavier with Zack as replacement
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        assertTrue(result2);
        
        // Verify Xavier is removed
        assertFalse(northQuarter.getMailmen().contains(xavier));
        
        // Verify Zack remains with all 10 deliveries
        List<RegisteredMail> zackMails = northQuarter.listRegisteredMail(zack);
        assertEquals(10, zackMails.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Set up SouthEnd with mailmen and inhabitants
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        southEnd.addInhabitant(rachel);
        
        // Create and assign mail
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        southEnd.getAllMails().add(letter1);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Attempt to remove Paul specifying Quinn (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue(result1);
        
        // Verify Paul is removed
        assertFalse(southEnd.getMailmen().contains(paul));
        
        // Attempt to remove Quinn (last mailman) - should fail
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in area, but method should fail before checking this
        assertFalse(result2);
        
        // Verify Quinn is still present
        assertTrue(southEnd.getMailmen().contains(quinn));
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Set up EastHaven with mailmen and inhabitants
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 letters from Mario to Peach
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            eastHaven.getAllMails().add(letter);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // Create 5 parcels from Luigi to Bowser
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            eastHaven.getAllMails().add(parcel);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // 1. Add duplicate "Mario" → false
        boolean result1 = eastHaven.addMailman(mario);
        assertFalse(result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        assertTrue(result2);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMails = eastHaven.listRegisteredMail(luigi);
        assertEquals(15, luigiMails.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // mario is no longer in area, but method should fail before checking this
        assertFalse(result3);
        
        // 5. Add "Toad" → true
        boolean result4 = eastHaven.addMailman(toad);
        assertTrue(result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        assertTrue(result5);
        
        // Verify Toad has all 15 deliveries
        List<RegisteredMail> toadMails = eastHaven.listRegisteredMail(toad);
        assertEquals(15, toadMails.size());
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Set up WestRidge with mailmen
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant();
            inhabitants[i].setName("Inhabitant" + (i + 1));
            westRidge.addInhabitant(inhabitants[i]);
        }
        
        // Create 30 mail items (10 each mailman)
        int inhabitantIndex = 0;
        
        // 10 items for Alpha
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                letter.setAddressee(inhabitants[inhabitantIndex]);
                westRidge.getAllMails().add(letter);
                westRidge.assignRegisteredMailDeliver(alpha, inhabitants[inhabitantIndex], letter);
            } else {
                Parcel parcel = new Parcel();
                parcel.setAddressee(inhabitants[inhabitantIndex]);
                westRidge.getAllMails().add(parcel);
                westRidge.assignRegisteredMailDeliver(alpha, inhabitants[inhabitantIndex], parcel);
            }
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // 10 items for Beta
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                letter.setAddressee(inhabitants[inhabitantIndex]);
                westRidge.getAllMails().add(letter);
                westRidge.assignRegisteredMailDeliver(beta, inhabitants[inhabitantIndex], letter);
            } else {
                Parcel parcel = new Parcel();
                parcel.setAddressee(inhabitants[inhabitantIndex]);
                westRidge.getAllMails().add(parcel);
                westRidge.assignRegisteredMailDeliver(beta, inhabitants[inhabitantIndex], parcel);
            }
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // 10 items for Gamma
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                letter.setAddressee(inhabitants[inhabitantIndex]);
                westRidge.getAllMails().add(letter);
                westRidge.assignRegisteredMailDeliver(gamma, inhabitants[inhabitantIndex], letter);
            } else {
                Parcel parcel = new Parcel();
                parcel.setAddressee(inhabitants[inhabitantIndex]);
                westRidge.getAllMails().add(parcel);
                westRidge.assignRegisteredMailDeliver(gamma, inhabitants[inhabitantIndex], parcel);
            }
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Verify initial assignments
        assertEquals(10, westRidge.listRegisteredMail(alpha).size());
        assertEquals(10, westRidge.listRegisteredMail(beta).size());
        assertEquals(10, westRidge.listRegisteredMail(gamma).size());
        
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue(result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue(result2);
        
        // Verify Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaMails = westRidge.listRegisteredMail(gamma);
        assertEquals(30, gammaMails.size());
    }
}