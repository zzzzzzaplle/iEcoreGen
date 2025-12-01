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
    
    private Mailman alice, bob, charlie;
    private Mailman xavier, yvonne, zack;
    private Mailman paul, quinn;
    private Mailman mario, luigi, toad;
    private Mailman alpha, beta, gamma;
    
    private Inhabitant david, eve, frank;
    private Inhabitant walter;
    private Inhabitant rachel;
    private Inhabitant peach, bowser;
    
    @Before
    public void setUp() {
        // Create mailmen
        alice = new Mailman("Alice");
        bob = new Mailman("Bob");
        charlie = new Mailman("Charlie");
        
        xavier = new Mailman("Xavier");
        yvonne = new Mailman("Yvonne");
        zack = new Mailman("Zack");
        
        paul = new Mailman("Paul");
        quinn = new Mailman("Quinn");
        
        mario = new Mailman("Mario");
        luigi = new Mailman("Luigi");
        toad = new Mailman("Toad");
        
        alpha = new Mailman("Alpha");
        beta = new Mailman("Beta");
        gamma = new Mailman("Gamma");
        
        // Create inhabitants
        david = new Inhabitant("David");
        eve = new Inhabitant("Eve");
        frank = new Inhabitant("Frank");
        
        walter = new Inhabitant("Walter");
        
        rachel = new Inhabitant("Rachel");
        
        peach = new Inhabitant("Peach");
        bowser = new Inhabitant("Bowser");
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // Set up CentralDistrict
        centralDistrict = new GeographicalArea();
        
        // Add mailmen
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add inhabitants
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
        
        // Verify initial assignments
        List<RegisteredMail> aliceMails = centralDistrict.listRegisteredMail(alice);
        List<RegisteredMail> bobMails = centralDistrict.listRegisteredMail(bob);
        List<RegisteredMail> charlieMails = centralDistrict.listRegisteredMail(charlie);
        
        assertEquals(2, aliceMails.size());
        assertEquals(2, bobMails.size());
        assertEquals(1, charlieMails.size());
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications
        // Alice removed
        assertFalse(centralDistrict.getMailmen().contains(alice));
        
        // Bob now has 4 items (original 2 + reassigned 2 from Alice)
        List<RegisteredMail> updatedBobMails = centralDistrict.listRegisteredMail(bob);
        assertEquals(4, updatedBobMails.size());
        
        // Charlie's assignments unchanged (1 item)
        List<RegisteredMail> updatedCharlieMails = centralDistrict.listRegisteredMail(charlie);
        assertEquals(1, updatedCharlieMails.size());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Set up NorthQuarter
        northQuarter = new GeographicalArea();
        
        // Add mailmen
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add inhabitant
        northQuarter.addInhabitant(walter);
        
        // Create and assign 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Verify initial assignments
        assertEquals(5, northQuarter.listRegisteredMail(xavier).size());
        assertEquals(3, northQuarter.listRegisteredMail(yvonne).size());
        assertEquals(2, northQuarter.listRegisteredMail(zack).size());
        
        // Action 1: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Verify after first removal
        assertFalse(northQuarter.getMailmen().contains(yvonne));
        assertEquals(8, northQuarter.listRegisteredMail(xavier).size()); // 5 original + 3 reassigned
        assertEquals(2, northQuarter.listRegisteredMail(zack).size());
        
        // Action 2: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Verifications: Zack remains with all 10 deliveries
        assertFalse(northQuarter.getMailmen().contains(xavier));
        List<RegisteredMail> zackMails = northQuarter.listRegisteredMail(zack);
        assertEquals(10, zackMails.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Set up SouthEnd
        southEnd = new GeographicalArea();
        
        // Add mailmen
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add inhabitant
        southEnd.addInhabitant(rachel);
        
        // Create and assign Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Action 1: Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue(result1);
        
        // Verify Paul is removed
        assertFalse(southEnd.getMailmen().contains(paul));
        
        // Action 2: Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // Paul is no longer in the list, but it should fail anyway
        assertFalse(result2);
        
        // Verify Quinn is still there
        assertTrue(southEnd.getMailmen().contains(quinn));
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Set up EastHaven
        eastHaven = new GeographicalArea();
        
        // Add mailmen
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add inhabitants
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // Procedure 1: Add duplicate "Mario" → false
        boolean addDuplicateResult = eastHaven.addMailman(mario);
        assertFalse(addDuplicateResult);
        
        // Procedure 2: Remove Mario (specify Luigi) → true
        boolean removeMarioResult = eastHaven.removeMailman(mario, luigi);
        assertTrue(removeMarioResult);
        
        // Procedure 3: Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMails = eastHaven.listRegisteredMail(luigi);
        assertEquals(15, luigiMails.size());
        
        // Procedure 4: Attempt remove Luigi → false (last mailman)
        boolean removeLuigiResult = eastHaven.removeMailman(luigi, mario); // mario is no longer in the list
        assertFalse(removeLuigiResult);
        
        // Procedure 5: Add "Toad" → true
        boolean addToadResult = eastHaven.addMailman(toad);
        assertTrue(addToadResult);
        
        // Procedure 6: Remove Luigi (specify Toad) → true
        boolean removeLuigiWithToadResult = eastHaven.removeMailman(luigi, toad);
        assertTrue(removeLuigiWithToadResult);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Set up WestRidge
        westRidge = new GeographicalArea();
        
        // Add mailmen
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant("Inhabitant" + (i + 1));
            westRidge.addInhabitant(inhabitants[i]);
        }
        
        // Create 30 mail items (10 each mailman)
        int inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants[inhabitantIndex % 10], letter);
            inhabitantIndex++;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            westRidge.assignRegisteredMailDeliver(beta, inhabitants[inhabitantIndex % 10], parcel);
            inhabitantIndex++;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants[inhabitantIndex % 10], letter);
            inhabitantIndex++;
        }
        
        // Verify initial assignments
        assertEquals(10, westRidge.listRegisteredMail(alpha).size());
        assertEquals(10, westRidge.listRegisteredMail(beta).size());
        assertEquals(10, westRidge.listRegisteredMail(gamma).size());
        
        // Action 1: Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue(result1);
        
        // Verify after first removal
        assertFalse(westRidge.getMailmen().contains(alpha));
        assertEquals(20, westRidge.listRegisteredMail(beta).size()); // 10 original + 10 reassigned
        assertEquals(10, westRidge.listRegisteredMail(gamma).size());
        
        // Action 2: Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue(result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        assertFalse(westRidge.getMailmen().contains(beta));
        List<RegisteredMail> gammaMails = westRidge.listRegisteredMail(gamma);
        assertEquals(30, gammaMails.size());
    }
}