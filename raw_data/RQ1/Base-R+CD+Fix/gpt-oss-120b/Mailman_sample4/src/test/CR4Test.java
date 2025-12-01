import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private GeographicalArea area;
    private Mailman alice, bob, charlie, xavier, yvonne, zack, paul, quinn, mario, luigi, toad, alpha, beta, gamma;
    private Inhabitant david, eve, frank, walter, rachel, peach, bowser;
    
    @Before
    public void setUp() {
        area = new GeographicalArea();
        
        // Initialize mailmen
        alice = new Mailman();
        alice.setId("Alice");
        alice.setName("Alice");
        
        bob = new Mailman();
        bob.setId("Bob");
        bob.setName("Bob");
        
        charlie = new Mailman();
        charlie.setId("Charlie");
        charlie.setName("Charlie");
        
        xavier = new Mailman();
        xavier.setId("Xavier");
        xavier.setName("Xavier");
        
        yvonne = new Mailman();
        yvonne.setId("Yvonne");
        yvonne.setName("Yvonne");
        
        zack = new Mailman();
        zack.setId("Zack");
        zack.setName("Zack");
        
        paul = new Mailman();
        paul.setId("Paul");
        paul.setName("Paul");
        
        quinn = new Mailman();
        quinn.setId("Quinn");
        quinn.setName("Quinn");
        
        mario = new Mailman();
        mario.setId("Mario");
        mario.setName("Mario");
        
        luigi = new Mailman();
        luigi.setId("Luigi");
        luigi.setName("Luigi");
        
        toad = new Mailman();
        toad.setId("Toad");
        toad.setName("Toad");
        
        alpha = new Mailman();
        alpha.setId("Alpha");
        alpha.setName("Alpha");
        
        beta = new Mailman();
        beta.setId("Beta");
        beta.setName("Beta");
        
        gamma = new Mailman();
        gamma.setId("Gamma");
        gamma.setName("Gamma");
        
        // Initialize inhabitants
        david = new Inhabitant();
        david.setId("David");
        david.setName("David");
        
        eve = new Inhabitant();
        eve.setId("Eve");
        eve.setName("Eve");
        
        frank = new Inhabitant();
        frank.setId("Frank");
        frank.setName("Frank");
        
        walter = new Inhabitant();
        walter.setId("Walter");
        walter.setName("Walter");
        
        rachel = new Inhabitant();
        rachel.setId("Rachel");
        rachel.setName("Rachel");
        
        peach = new Inhabitant();
        peach.setId("Peach");
        peach.setName("Peach");
        
        bowser = new Inhabitant();
        bowser.setId("Bowser");
        bowser.setName("Bowser");
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        area.setMailmen(new java.util.ArrayList<>());
        area.setInhabitants(new java.util.ArrayList<>());
        area.setAllMails(new java.util.ArrayList<>());
        
        // Add Mailmen "Alice", "Bob", "Charlie"
        assertTrue(area.addMailman(alice));
        assertTrue(area.addMailman(bob));
        assertTrue(area.addMailman(charlie));
        
        // Add Inhabitants "David", "Eve", "Frank"
        assertTrue(area.addInhabitant(david));
        assertTrue(area.addInhabitant(eve));
        assertTrue(area.addInhabitant(frank));
        
        // Create and assign mail items
        Letter letter1 = new Letter();
        letter1.setAddressee(david);
        letter1.setCarrier(alice);
        assertTrue(area.addMail(letter1));
        
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(eve);
        parcel1.setCarrier(bob);
        assertTrue(area.addMail(parcel1));
        
        Letter letter2 = new Letter();
        letter2.setAddressee(frank);
        letter2.setCarrier(charlie);
        assertTrue(area.addMail(letter2));
        
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(eve);
        parcel2.setCarrier(alice);
        assertTrue(area.addMail(parcel2));
        
        Letter letter3 = new Letter();
        letter3.setAddressee(david);
        letter3.setCarrier(bob);
        assertTrue(area.addMail(letter3));
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = area.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications
        // Alice removed
        assertFalse(area.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobsDeliveries = area.listRegisteredMail(bob);
        assertNotNull(bobsDeliveries);
        assertEquals(4, bobsDeliveries.size());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue(bobsDeliveries.contains(parcel1));
        assertTrue(bobsDeliveries.contains(letter3));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charliesDeliveries = area.listRegisteredMail(charlie);
        assertNotNull(charliesDeliveries);
        assertEquals(1, charliesDeliveries.size());
        assertTrue(charliesDeliveries.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        area.setMailmen(new java.util.ArrayList<>());
        area.setInhabitants(new java.util.ArrayList<>());
        area.setAllMails(new java.util.ArrayList<>());
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        assertTrue(area.addMailman(xavier));
        assertTrue(area.addMailman(yvonne));
        assertTrue(area.addMailman(zack));
        
        // Add Inhabitant "Walter"
        assertTrue(area.addInhabitant(walter));
        
        // Create and assign: 5 Letters (Xavier→Walter), 3 Parcels (Yvonne→Walter), 2 Letters (Zack→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setCarrier(xavier);
            assertTrue(area.addMail(letter));
        }
        
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            parcel.setCarrier(yvonne);
            assertTrue(area.addMail(parcel));
        }
        
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setCarrier(zack);
            assertTrue(area.addMail(letter));
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = area.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = area.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Verifications: Zack remains with all 10 deliveries
        List<RegisteredMail> zacksDeliveries = area.listRegisteredMail(zack);
        assertNotNull(zacksDeliveries);
        assertEquals(10, zacksDeliveries.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        area.setMailmen(new java.util.ArrayList<>());
        area.setInhabitants(new java.util.ArrayList<>());
        area.setAllMails(new java.util.ArrayList<>());
        
        // Add Mailmen ["Paul", "Quinn"]
        assertTrue(area.addMailman(paul));
        assertTrue(area.addMailman(quinn));
        
        // Add Inhabitant "Rachel"
        assertTrue(area.addInhabitant(rachel));
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        letter1.setCarrier(paul);
        assertTrue(area.addMail(letter1));
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = area.removeMailman(paul, quinn);
        assertTrue(result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = area.removeMailman(quinn, quinn); // Invalid - same person
        assertFalse(result2);
        
        // Also test with valid replacement but last mailman case
        Mailman temp = new Mailman();
        temp.setId("Temp");
        boolean result3 = area.removeMailman(quinn, temp); // temp not in area
        assertFalse(result3);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        area.setMailmen(new java.util.ArrayList<>());
        area.setInhabitants(new java.util.ArrayList<>());
        area.setAllMails(new java.util.ArrayList<>());
        
        // Add Mailmen ["Mario", "Luigi"]
        assertTrue(area.addMailman(mario));
        assertTrue(area.addMailman(luigi));
        
        // Add Inhabitants ["Peach", "Bowser"]
        assertTrue(area.addInhabitant(peach));
        assertTrue(area.addInhabitant(bowser));
        
        // Create: 10 Letters (Mario→Peach), 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            letter.setCarrier(mario);
            assertTrue(area.addMail(letter));
        }
        
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            parcel.setCarrier(luigi);
            assertTrue(area.addMail(parcel));
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean result1 = area.addMailman(mario);
        assertFalse(result1);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean result2 = area.removeMailman(mario, luigi);
        assertTrue(result2);
        
        // 3. Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigisDeliveries = area.listRegisteredMail(luigi);
        assertNotNull(luigisDeliveries);
        assertEquals(15, luigisDeliveries.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean result3 = area.removeMailman(luigi, luigi); // Invalid - same person
        assertFalse(result3);
        
        // 5. Add "Toad" → true
        boolean result4 = area.addMailman(toad);
        assertTrue(result4);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean result5 = area.removeMailman(luigi, toad);
        assertTrue(result5);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        area.setMailmen(new java.util.ArrayList<>());
        area.setInhabitants(new java.util.ArrayList<>());
        area.setAllMails(new java.util.ArrayList<>());
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        assertTrue(area.addMailman(alpha));
        assertTrue(area.addMailman(beta));
        assertTrue(area.addMailman(gamma));
        
        // Add 10 Inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant();
            inhabitants[i].setId("Inhabitant" + i);
            inhabitants[i].setName("Inhabitant" + i);
            assertTrue(area.addInhabitant(inhabitants[i]));
        }
        
        // Create 30 mail items (10 each mailman)
        int inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants[inhabitantIndex]);
            letter.setCarrier(alpha);
            assertTrue(area.addMail(letter));
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(inhabitants[inhabitantIndex]);
            parcel.setCarrier(beta);
            assertTrue(area.addMail(parcel));
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants[inhabitantIndex]);
            letter.setCarrier(gamma);
            assertTrue(area.addMail(letter));
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = area.removeMailman(alpha, beta);
        assertTrue(result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = area.removeMailman(beta, gamma);
        assertTrue(result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammasDeliveries = area.listRegisteredMail(gamma);
        assertNotNull(gammasDeliveries);
        assertEquals(30, gammasDeliveries.size());
    }
}