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
        // Initialize mailmen
        alice = new Mailman("M001", "Alice");
        bob = new Mailman("M002", "Bob");
        charlie = new Mailman("M003", "Charlie");
        xavier = new Mailman("M004", "Xavier");
        yvonne = new Mailman("M005", "Yvonne");
        zack = new Mailman("M006", "Zack");
        paul = new Mailman("M007", "Paul");
        quinn = new Mailman("M008", "Quinn");
        mario = new Mailman("M009", "Mario");
        luigi = new Mailman("M010", "Luigi");
        toad = new Mailman("M011", "Toad");
        alpha = new Mailman("M012", "Alpha");
        beta = new Mailman("M013", "Beta");
        gamma = new Mailman("M014", "Gamma");
        
        // Initialize inhabitants
        david = new Inhabitant("I001", "David");
        eve = new Inhabitant("I002", "Eve");
        frank = new Inhabitant("I003", "Frank");
        walter = new Inhabitant("I004", "Walter");
        rachel = new Inhabitant("I005", "Rachel");
        peach = new Inhabitant("I006", "Peach");
        bowser = new Inhabitant("I007", "Bowser");
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        centralDistrict = new GeographicalArea();
        
        // Add Mailmen "Alice", "Bob", "Charlie"
        assertTrue(centralDistrict.addMailman(alice));
        assertTrue(centralDistrict.addMailman(bob));
        assertTrue(centralDistrict.addMailman(charlie));
        
        // Add Inhabitants "David", "Eve", "Frank"
        assertTrue(centralDistrict.addInhabitant(david));
        assertTrue(centralDistrict.addInhabitant(eve));
        assertTrue(centralDistrict.addInhabitant(frank));
        
        // Create mail items
        Letter letter1 = new Letter("L001");
        Parcel parcel1 = new Parcel("P001");
        Letter letter2 = new Letter("L002");
        Parcel parcel2 = new Parcel("P002");
        Letter letter3 = new Letter("L003");
        
        // Add mail items to area
        assertTrue(centralDistrict.addRegisteredMail(letter1));
        assertTrue(centralDistrict.addRegisteredMail(parcel1));
        assertTrue(centralDistrict.addRegisteredMail(letter2));
        assertTrue(centralDistrict.addRegisteredMail(parcel2));
        assertTrue(centralDistrict.addRegisteredMail(letter3));
        
        // Assign mail items
        assertTrue(centralDistrict.assignRegisteredMailDeliver(alice, david, letter1));
        assertTrue(centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1));
        assertTrue(centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2));
        assertTrue(centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2));
        assertTrue(centralDistrict.assignRegisteredMailDeliver(bob, david, letter3));
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications: Alice removed
        assertFalse(centralDistrict.getMailmen().contains(alice));
        assertEquals(2, centralDistrict.getMailmen().size());
        
        // Verifications: Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsMail = centralDistrict.listRegisteredMail(bob);
        assertNotNull(bobsMail);
        assertEquals(4, bobsMail.size());
        
        // Verifications: Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue(bobsMail.contains(parcel1));
        assertTrue(bobsMail.contains(letter3));
        assertTrue(bobsMail.contains(letter1));
        assertTrue(bobsMail.contains(parcel2));
        
        // Verifications: Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesMail = centralDistrict.listRegisteredMail(charlie);
        assertNotNull(charliesMail);
        assertEquals(1, charliesMail.size());
        assertTrue(charliesMail.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        northQuarter = new GeographicalArea();
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        assertTrue(northQuarter.addMailman(xavier));
        assertTrue(northQuarter.addMailman(yvonne));
        assertTrue(northQuarter.addMailman(zack));
        
        // Add Inhabitant "Walter"
        assertTrue(northQuarter.addInhabitant(walter));
        
        // Create and assign 5 Letters (Xavier→Walter)
        Letter[] xavierLetters = new Letter[5];
        for (int i = 0; i < 5; i++) {
            xavierLetters[i] = new Letter("XL" + (i+1));
            assertTrue(northQuarter.addRegisteredMail(xavierLetters[i]));
            assertTrue(northQuarter.assignRegisteredMailDeliver(xavier, walter, xavierLetters[i]));
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        Parcel[] yvonneParcels = new Parcel[3];
        for (int i = 0; i < 3; i++) {
            yvonneParcels[i] = new Parcel("YP" + (i+1));
            assertTrue(northQuarter.addRegisteredMail(yvonneParcels[i]));
            assertTrue(northQuarter.assignRegisteredMailDeliver(yvonne, walter, yvonneParcels[i]));
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        Letter[] zackLetters = new Letter[2];
        for (int i = 0; i < 2; i++) {
            zackLetters[i] = new Letter("ZL" + (i+1));
            assertTrue(northQuarter.addRegisteredMail(zackLetters[i]));
            assertTrue(northQuarter.assignRegisteredMailDeliver(zack, walter, zackLetters[i]));
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zacksMail = northQuarter.listRegisteredMail(zack);
        assertNotNull(zacksMail);
        assertEquals(10, zacksMail.size());
        
        // Verify all mail is assigned to Zack
        for (int i = 0; i < 5; i++) {
            assertTrue(zacksMail.contains(xavierLetters[i]));
        }
        for (int i = 0; i < 3; i++) {
            assertTrue(zacksMail.contains(yvonneParcels[i]));
        }
        for (int i = 0; i < 2; i++) {
            assertTrue(zacksMail.contains(zackLetters[i]));
        }
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        southEnd = new GeographicalArea();
        
        // Add Mailmen ["Paul", "Quinn"]
        assertTrue(southEnd.addMailman(paul));
        assertTrue(southEnd.addMailman(quinn));
        
        // Add Inhabitant "Rachel"
        assertTrue(southEnd.addInhabitant(rachel));
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter("L001");
        assertTrue(southEnd.addRegisteredMail(letter1));
        assertTrue(southEnd.assignRegisteredMailDeliver(paul, rachel, letter1));
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue(result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is already removed, but method will fail anyway
        assertFalse(result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        eastHaven = new GeographicalArea();
        
        // Add Mailmen ["Mario", "Luigi"]
        assertTrue(eastHaven.addMailman(mario));
        assertTrue(eastHaven.addMailman(luigi));
        
        // Add Inhabitants ["Peach", "Bowser"]
        assertTrue(eastHaven.addInhabitant(peach));
        assertTrue(eastHaven.addInhabitant(bowser));
        
        // Create 10 Letters (Mario→Peach)
        Letter[] marioLetters = new Letter[10];
        for (int i = 0; i < 10; i++) {
            marioLetters[i] = new Letter("ML" + (i+1));
            assertTrue(eastHaven.addRegisteredMail(marioLetters[i]));
            assertTrue(eastHaven.assignRegisteredMailDeliver(mario, peach, marioLetters[i]));
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        Parcel[] luigiParcels = new Parcel[5];
        for (int i = 0; i < 5; i++) {
            luigiParcels[i] = new Parcel("LP" + (i+1));
            assertTrue(eastHaven.addRegisteredMail(luigiParcels[i]));
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
        List<RegisteredMail> luigisMail = eastHaven.listRegisteredMail(luigi);
        assertNotNull(luigisMail);
        assertEquals(15, luigisMail.size());
        
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
        westRidge = new GeographicalArea();
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        assertTrue(westRidge.addMailman(alpha));
        assertTrue(westRidge.addMailman(beta));
        assertTrue(westRidge.addMailman(gamma));
        
        // Add 10 Inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant("I" + (i+10), "Inhabitant" + (i+1));
            assertTrue(westRidge.addInhabitant(inhabitants[i]));
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCount = 0;
        
        // Alpha's 10 mail items
        for (int i = 0; i < 10; i++) {
            RegisteredMail mail = (i % 2 == 0) ? new Letter("AL" + (i+1)) : new Parcel("AP" + (i+1));
            assertTrue(westRidge.addRegisteredMail(mail));
            assertTrue(westRidge.assignRegisteredMailDeliver(alpha, inhabitants[i % 10], mail));
            mailCount++;
        }
        
        // Beta's 10 mail items
        for (int i = 0; i < 10; i++) {
            RegisteredMail mail = (i % 2 == 0) ? new Letter("BL" + (i+1)) : new Parcel("BP" + (i+1));
            assertTrue(westRidge.addRegisteredMail(mail));
            assertTrue(westRidge.assignRegisteredMailDeliver(beta, inhabitants[i % 10], mail));
            mailCount++;
        }
        
        // Gamma's 10 mail items
        for (int i = 0; i < 10; i++) {
            RegisteredMail mail = (i % 2 == 0) ? new Letter("GL" + (i+1)) : new Parcel("GP" + (i+1));
            assertTrue(westRidge.addRegisteredMail(mail));
            assertTrue(westRidge.assignRegisteredMailDeliver(gamma, inhabitants[i % 10], mail));
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
        List<RegisteredMail> gammasMail = westRidge.listRegisteredMail(gamma);
        assertNotNull(gammasMail);
        assertEquals(30, gammasMail.size());
    }
}