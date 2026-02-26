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
    
    @Before
    public void setUp() {
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // Set up mailmen
        Mailman alice = new Mailman("M1", "Alice");
        Mailman bob = new Mailman("M2", "Bob");
        Mailman charlie = new Mailman("M3", "Charlie");
        
        // Set up inhabitants
        Inhabitant david = new Inhabitant("I1", "David");
        Inhabitant eve = new Inhabitant("I2", "Eve");
        Inhabitant frank = new Inhabitant("I3", "Frank");
        
        // Add mailmen to area
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        // Add inhabitants to area
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        // Create mail items
        Letter letter1 = new Letter("L1", alice, david, 50.0);
        Parcel parcel1 = new Parcel("P1", bob, eve, 1000.0);
        Letter letter2 = new Letter("L2", charlie, frank, 30.0);
        Parcel parcel2 = new Parcel("P2", alice, eve, 1500.0);
        Letter letter3 = new Letter("L3", bob, david, 40.0);
        
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
        
        // Verifications:
        // Alice removed
        assertFalse(centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobMailAfter = centralDistrict.listRegisteredMail(bob);
        assertNotNull(bobMailAfter);
        assertEquals(4, bobMailAfter.size());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        boolean hasParcel1 = false;
        boolean hasLetter3 = false;
        boolean hasLetter1 = false;
        boolean hasParcel2 = false;
        
        for (RegisteredMail mail : bobMailAfter) {
            if ("P1".equals(mail.getId())) hasParcel1 = true;
            if ("L3".equals(mail.getId())) hasLetter3 = true;
            if ("L1".equals(mail.getId())) hasLetter1 = true;
            if ("P2".equals(mail.getId())) hasParcel2 = true;
        }
        
        assertTrue(hasParcel1);
        assertTrue(hasLetter3);
        assertTrue(hasLetter1);
        assertTrue(hasParcel2);
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieMailAfter = centralDistrict.listRegisteredMail(charlie);
        assertNotNull(charlieMailAfter);
        assertEquals(1, charlieMailAfter.size());
        assertEquals("L2", charlieMailAfter.get(0).getId());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Set up mailmen
        Mailman xavier = new Mailman("M1", "Xavier");
        Mailman yvonne = new Mailman("M2", "Yvonne");
        Mailman zack = new Mailman("M3", "Zack");
        
        // Set up inhabitant
        Inhabitant walter = new Inhabitant("I1", "Walter");
        
        // Add mailmen to area
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        // Add inhabitant to area
        northQuarter.addInhabitant(walter);
        
        // Create and assign 5 Letters (Xavier→Walter)
        for (int i = 1; i <= 5; i++) {
            Letter letter = new Letter("LX" + i, xavier, walter, 20.0 + i);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        for (int i = 1; i <= 3; i++) {
            Parcel parcel = new Parcel("PY" + i, yvonne, walter, 500.0 + i * 100);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        for (int i = 1; i <= 2; i++) {
            Letter letter = new Letter("LZ" + i, zack, walter, 25.0 + i);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Verify initial assignments
        List<RegisteredMail> xavierMail = northQuarter.listRegisteredMail(xavier);
        List<RegisteredMail> yvonneMail = northQuarter.listRegisteredMail(yvonne);
        List<RegisteredMail> zackMail = northQuarter.listRegisteredMail(zack);
        
        assertEquals(5, xavierMail.size());
        assertEquals(3, yvonneMail.size());
        assertEquals(2, zackMail.size());
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        // Expected Output: true
        assertTrue(result1);
        
        // Remove Xavier (specify Zack as replacement)
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        // Expected Output: true
        assertTrue(result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zackMailAfter = northQuarter.listRegisteredMail(zack);
        assertNotNull(zackMailAfter);
        assertEquals(10, zackMailAfter.size());
        
        // Verify Zack has all mail items
        int xavierItems = 0;
        int yvonneItems = 0;
        int zackItems = 0;
        
        for (RegisteredMail mail : zackMailAfter) {
            String id = mail.getId();
            if (id.startsWith("LX")) xavierItems++;
            else if (id.startsWith("PY")) yvonneItems++;
            else if (id.startsWith("LZ")) zackItems++;
        }
        
        assertEquals(5, xavierItems); // Xavier's original 5 letters
        assertEquals(3, yvonneItems); // Yvonne's original 3 parcels  
        assertEquals(2, zackItems);   // Zack's original 2 letters
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Set up mailmen
        Mailman paul = new Mailman("M1", "Paul");
        Mailman quinn = new Mailman("M2", "Quinn");
        
        // Set up inhabitant
        Inhabitant rachel = new Inhabitant("I1", "Rachel");
        
        // Add mailmen to area
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        // Add inhabitant to area
        southEnd.addInhabitant(rachel);
        
        // Create and assign Letter1 (Paul→Rachel)
        Letter letter1 = new Letter("L1", paul, rachel, 30.0);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Action 1: Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = southEnd.removeMailman(paul, quinn);
        assertTrue(result1);
        
        // Action 2: Attempt remove Quinn (last mailman) → false
        boolean result2 = southEnd.removeMailman(quinn, paul); // paul is no longer in the area
        assertFalse(result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Set up mailmen
        Mailman mario = new Mailman("M1", "Mario");
        Mailman luigi = new Mailman("M2", "Luigi");
        Mailman toad = new Mailman("M3", "Toad");
        
        // Set up inhabitants
        Inhabitant peach = new Inhabitant("I1", "Peach");
        Inhabitant bowser = new Inhabitant("I2", "Bowser");
        
        // Add initial mailmen to area
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        // Add inhabitants to area
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter("L" + i, mario, peach, 25.0 + i);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 1; i <= 5; i++) {
            Parcel parcel = new Parcel("P" + i, luigi, bowser, 1000.0 + i * 100);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // Verify initial assignments
        List<RegisteredMail> marioMail = eastHaven.listRegisteredMail(mario);
        List<RegisteredMail> luigiMail = eastHaven.listRegisteredMail(luigi);
        
        assertEquals(10, marioMail.size());
        assertEquals(5, luigiMail.size());
        
        // Procedure 1: Add duplicate "Mario" → false
        boolean addDuplicate = eastHaven.addMailman(mario);
        assertFalse(addDuplicate);
        
        // Procedure 2: Remove Mario (specify Luigi) → true
        boolean removeMario = eastHaven.removeMailman(mario, luigi);
        assertTrue(removeMario);
        
        // Procedure 3: Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiMailAfter = eastHaven.listRegisteredMail(luigi);
        assertNotNull(luigiMailAfter);
        assertEquals(15, luigiMailAfter.size());
        
        // Procedure 4: Attempt remove Luigi → false (last mailman)
        boolean removeLuigi = eastHaven.removeMailman(luigi, mario); // mario is no longer in the area
        assertFalse(removeLuigi);
        
        // Procedure 5: Add "Toad" → true
        boolean addToad = eastHaven.addMailman(toad);
        assertTrue(addToad);
        
        // Procedure 6: Remove Luigi (specify Toad) → true
        boolean removeLuigi2 = eastHaven.removeMailman(luigi, toad);
        assertTrue(removeLuigi2);
        
        // Verify Toad has all 15 deliveries
        List<RegisteredMail> toadMail = eastHaven.listRegisteredMail(toad);
        assertNotNull(toadMail);
        assertEquals(15, toadMail.size());
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Set up mailmen
        Mailman alpha = new Mailman("M1", "Alpha");
        Mailman beta = new Mailman("M2", "Beta");
        Mailman gamma = new Mailman("M3", "Gamma");
        
        // Add mailmen to area
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 inhabitants
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant("I" + i, "Inhabitant" + i);
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailId = 1;
        
        // Alpha's 10 mail items
        for (int i = 1; i <= 10; i++) {
            Inhabitant addressee = westRidge.getInhabitants().get(i - 1);
            Letter letter = new Letter("ML" + mailId++, alpha, addressee, 20.0 + i);
            westRidge.assignRegisteredMailDeliver(alpha, addressee, letter);
        }
        
        // Beta's 10 mail items
        for (int i = 1; i <= 10; i++) {
            Inhabitant addressee = westRidge.getInhabitants().get((i + 2) % 10);
            Parcel parcel = new Parcel("MP" + mailId++, beta, addressee, 500.0 + i * 50);
            westRidge.assignRegisteredMailDeliver(beta, addressee, parcel);
        }
        
        // Gamma's 10 mail items
        for (int i = 1; i <= 10; i++) {
            Inhabitant addressee = westRidge.getInhabitants().get((i + 5) % 10);
            Letter letter = new Letter("ML" + mailId++, gamma, addressee, 25.0 + i);
            westRidge.assignRegisteredMailDeliver(gamma, addressee, letter);
        }
        
        // Verify initial assignments
        List<RegisteredMail> alphaMail = westRidge.listRegisteredMail(alpha);
        List<RegisteredMail> betaMail = westRidge.listRegisteredMail(beta);
        List<RegisteredMail> gammaMail = westRidge.listRegisteredMail(gamma);
        
        assertEquals(10, alphaMail.size());
        assertEquals(10, betaMail.size());
        assertEquals(10, gammaMail.size());
        
        // Action 1: Remove Alpha (specify Beta) → true
        boolean result1 = westRidge.removeMailman(alpha, beta);
        assertTrue(result1);
        
        // Action 2: Remove Beta (specify Gamma) → true
        boolean result2 = westRidge.removeMailman(beta, gamma);
        assertTrue(result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaMailAfter = westRidge.listRegisteredMail(gamma);
        assertNotNull(gammaMailAfter);
        assertEquals(30, gammaMailAfter.size());
        
        // Verify all mail items are accounted for
        int alphaItems = 0;
        int betaItems = 0;
        int gammaItems = 0;
        
        for (RegisteredMail mail : gammaMailAfter) {
            String carrierName = mail.getCarrier().getName();
            if ("Alpha".equals(carrierName)) alphaItems++;
            else if ("Beta".equals(carrierName)) betaItems++;
            else if ("Gamma".equals(carrierName)) gammaItems++;
        }
        
        // All mail should now be assigned to Gamma
        assertEquals(0, alphaItems);
        assertEquals(0, betaItems);
        assertEquals(30, gammaItems);
    }
}