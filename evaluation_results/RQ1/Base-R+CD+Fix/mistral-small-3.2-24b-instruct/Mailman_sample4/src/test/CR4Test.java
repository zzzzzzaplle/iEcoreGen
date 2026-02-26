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
        
        // Create mailmen
        alice = new Mailman(); alice.setId("M001"); alice.setName("Alice");
        bob = new Mailman(); bob.setId("M002"); bob.setName("Bob");
        charlie = new Mailman(); charlie.setId("M003"); charlie.setName("Charlie");
        xavier = new Mailman(); xavier.setId("M004"); xavier.setName("Xavier");
        yvonne = new Mailman(); yvonne.setId("M005"); yvonne.setName("Yvonne");
        zack = new Mailman(); zack.setId("M006"); zack.setName("Zack");
        paul = new Mailman(); paul.setId("M007"); paul.setName("Paul");
        quinn = new Mailman(); quinn.setId("M008"); quinn.setName("Quinn");
        mario = new Mailman(); mario.setId("M009"); mario.setName("Mario");
        luigi = new Mailman(); luigi.setId("M010"); luigi.setName("Luigi");
        toad = new Mailman(); toad.setId("M011"); toad.setName("Toad");
        alpha = new Mailman(); alpha.setId("M012"); alpha.setName("Alpha");
        beta = new Mailman(); beta.setId("M013"); beta.setName("Beta");
        gamma = new Mailman(); gamma.setId("M014"); gamma.setName("Gamma");
        
        // Create inhabitants
        david = new Inhabitant(); david.setId("I001"); david.setName("David");
        eve = new Inhabitant(); eve.setId("I002"); eve.setName("Eve");
        frank = new Inhabitant(); frank.setId("I003"); frank.setName("Frank");
        walter = new Inhabitant(); walter.setId("I004"); walter.setName("Walter");
        rachel = new Inhabitant(); rachel.setId("I005"); rachel.setName("Rachel");
        peach = new Inhabitant(); peach.setId("I006"); peach.setName("Peach");
        bowser = new Inhabitant(); bowser.setId("I007"); bowser.setName("Bowser");
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        // Add Mailmen "Alice", "Bob", "Charlie"
        assertTrue(centralDistrict.addMailman(alice));
        assertTrue(centralDistrict.addMailman(bob));
        assertTrue(centralDistrict.addMailman(charlie));
        
        // Add Inhabitants "David", "Eve", "Frank"
        assertTrue(centralDistrict.addInhabitant(david));
        assertTrue(centralDistrict.addInhabitant(eve));
        assertTrue(centralDistrict.addInhabitant(frank));
        
        // Create and assign mail items
        Letter letter1 = new Letter();
        Parcel parcel1 = new Parcel();
        Letter letter2 = new Letter();
        Parcel parcel2 = new Parcel();
        Letter letter3 = new Letter();
        
        assertTrue(centralDistrict.assignRegisteredMailDeliver(alice, david, letter1));
        assertTrue(centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1));
        assertTrue(centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2));
        assertTrue(centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2));
        assertTrue(centralDistrict.assignRegisteredMailDeliver(bob, david, letter3));
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications
        // Alice removed
        assertFalse(centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobMail = centralDistrict.listRegisteredMail(bob);
        assertNotNull(bobMail);
        assertEquals(4, bobMail.size());
        assertTrue(bobMail.contains(letter1));
        assertTrue(bobMail.contains(parcel2));
        assertTrue(bobMail.contains(parcel1));
        assertTrue(bobMail.contains(letter3));
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue(bobMail.contains(parcel1));
        assertTrue(bobMail.contains(letter3));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieMail = centralDistrict.listRegisteredMail(charlie);
        assertNotNull(charlieMail);
        assertEquals(1, charlieMail.size());
        assertTrue(charlieMail.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        assertTrue(northQuarter.addMailman(xavier));
        assertTrue(northQuarter.addMailman(yvonne));
        assertTrue(northQuarter.addMailman(zack));
        
        // Add Inhabitant "Walter"
        assertTrue(northQuarter.addInhabitant(walter));
        
        // Create and assign mail items
        // 5 Letters (Xavier→Walter)
        Letter[] xavierLetters = new Letter[5];
        for (int i = 0; i < 5; i++) {
            xavierLetters[i] = new Letter();
            assertTrue(northQuarter.assignRegisteredMailDeliver(xavier, walter, xavierLetters[i]));
        }
        
        // 3 Parcels (Yvonne→Walter)
        Parcel[] yvonneParcels = new Parcel[3];
        for (int i = 0; i < 3; i++) {
            yvonneParcels[i] = new Parcel();
            assertTrue(northQuarter.assignRegisteredMailDeliver(yvonne, walter, yvonneParcels[i]));
        }
        
        // 2 Letters (Zack→Walter)
        Letter[] zackLetters = new Letter[2];
        for (int i = 0; i < 2; i++) {
            zackLetters[i] = new Letter();
            assertTrue(northQuarter.assignRegisteredMailDeliver(zack, walter, zackLetters[i]));
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zackMail = northQuarter.listRegisteredMail(zack);
        assertNotNull(zackMail);
        assertEquals(10, zackMail.size());
        
        // Verify all mail items are assigned to Zack
        for (Letter letter : xavierLetters) {
            assertTrue(zackMail.contains(letter));
        }
        for (Parcel parcel : yvonneParcels) {
            assertTrue(zackMail.contains(parcel));
        }
        for (Letter letter : zackLetters) {
            assertTrue(zackMail.contains(letter));
        }
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        // Add Mailmen ["Paul", "Quinn"]
        assertTrue(southEnd.addMailman(paul));
        assertTrue(southEnd.addMailman(quinn));
        
        // Add Inhabitant "Rachel"
        assertTrue(southEnd.addInhabitant(rachel));
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        assertTrue(southEnd.assignRegisteredMailDeliver(paul, rachel, letter1));
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue(result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is already removed, but method should fail due to last mailman constraint
        assertFalse(result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        // Add Mailmen ["Mario", "Luigi"]
        assertTrue(eastHaven.addMailman(mario));
        assertTrue(eastHaven.addMailman(luigi));
        
        // Add Inhabitants ["Peach", "Bowser"]
        assertTrue(eastHaven.addInhabitant(peach));
        assertTrue(eastHaven.addInhabitant(bowser));
        
        // Create mail items
        // 10 Letters (Mario→Peach)
        Letter[] marioLetters = new Letter[10];
        for (int i = 0; i < 10; i++) {
            marioLetters[i] = new Letter();
            assertTrue(eastHaven.assignRegisteredMailDeliver(mario, peach, marioLetters[i]));
        }
        
        // 5 Parcels (Luigi→Bowser)
        Parcel[] luigiParcels = new Parcel[5];
        for (int i = 0; i < 5; i++) {
            luigiParcels[i] = new Parcel();
            assertTrue(eastHaven.assignRegisteredMailDeliver(luigi, bowser, luigiParcels[i]));
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean result1 = eastHaven.addMailman(mario);
        assertFalse(result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        assertTrue(result2);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMail = eastHaven.listRegisteredMail(luigi);
        assertNotNull(luigiMail);
        assertEquals(15, luigiMail.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, mario); // mario is already removed
        assertFalse(result3);
        
        // 5. Add "Toad" → true
        boolean result4 = eastHaven.addMailman(toad);
        assertTrue(result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        assertTrue(result5);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        assertTrue(westRidge.addMailman(alpha));
        assertTrue(westRidge.addMailman(beta));
        assertTrue(westRidge.addMailman(gamma));
        
        // Add 10 Inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant();
            inhabitants[i].setId("I" + (100 + i));
            inhabitants[i].setName("Inhabitant" + i);
            assertTrue(westRidge.addInhabitant(inhabitants[i]));
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCount = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            assertTrue(westRidge.assignRegisteredMailDeliver(alpha, inhabitants[i], letter));
            mailCount++;
        }
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            assertTrue(westRidge.assignRegisteredMailDeliver(beta, inhabitants[i], parcel));
            mailCount++;
        }
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            assertTrue(westRidge.assignRegisteredMailDeliver(gamma, inhabitants[i], letter));
            mailCount++;
        }
        assertEquals(30, mailCount);
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue(result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue(result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaMail = westRidge.listRegisteredMail(gamma);
        assertNotNull(gammaMail);
        assertEquals(30, gammaMail.size());
    }
}