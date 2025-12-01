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
        // Setup
        Mailman alice = new Mailman("1", "Alice");
        Mailman bob = new Mailman("2", "Bob");
        Mailman charlie = new Mailman("3", "Charlie");
        
        Inhabitant david = new Inhabitant("101", "David");
        Inhabitant eve = new Inhabitant("102", "Eve");
        Inhabitant frank = new Inhabitant("103", "Frank");
        
        centralDistrict.addMailman(alice);
        centralDistrict.addMailman(bob);
        centralDistrict.addMailman(charlie);
        
        centralDistrict.addInhabitant(david);
        centralDistrict.addInhabitant(eve);
        centralDistrict.addInhabitant(frank);
        
        Letter letter1 = new Letter(alice, david);
        Parcel parcel1 = new Parcel(bob, eve);
        Letter letter2 = new Letter(charlie, frank);
        Parcel parcel2 = new Parcel(alice, eve);
        Letter letter3 = new Letter(bob, david);
        
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Action: Remove Alice with Bob as replacement
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications
        assertFalse("Alice should be removed", centralDistrict.getMailmen().contains(alice));
        
        List<RegisteredMail> bobDeliveries = centralDistrict.listRegisteredMail(bob);
        assertNotNull("Bob should have deliveries", bobDeliveries);
        assertEquals("Bob should have 4 deliveries", 4, bobDeliveries.size());
        
        List<RegisteredMail> charlieDeliveries = centralDistrict.listRegisteredMail(charlie);
        assertNotNull("Charlie should have deliveries", charlieDeliveries);
        assertEquals("Charlie should have 1 delivery", 1, charlieDeliveries.size());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Setup
        Mailman xavier = new Mailman("1", "Xavier");
        Mailman yvonne = new Mailman("2", "Yvonne");
        Mailman zack = new Mailman("3", "Zack");
        
        Inhabitant walter = new Inhabitant("101", "Walter");
        
        northQuarter.addMailman(xavier);
        northQuarter.addMailman(yvonne);
        northQuarter.addMailman(zack);
        
        northQuarter.addInhabitant(walter);
        
        // Create and assign 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter(xavier, walter);
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel(yvonne, walter);
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter(zack, walter);
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
        }
        
        // Action: Remove Yvonne specifying Xavier as replacement
        boolean result1 = northQuarter.removeMailman(yvonne, xavier);
        
        // Action: Remove Xavier specifying Zack as replacement
        boolean result2 = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true, true
        assertTrue(result1);
        assertTrue(result2);
        
        // Verification: Zack remains with all 10 deliveries
        List<RegisteredMail> zackDeliveries = northQuarter.listRegisteredMail(zack);
        assertNotNull("Zack should have deliveries", zackDeliveries);
        assertEquals("Zack should have 10 deliveries", 10, zackDeliveries.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Setup
        Mailman paul = new Mailman("1", "Paul");
        Mailman quinn = new Mailman("2", "Quinn");
        
        Inhabitant rachel = new Inhabitant("101", "Rachel");
        
        southEnd.addMailman(paul);
        southEnd.addMailman(quinn);
        
        southEnd.addInhabitant(rachel);
        
        Letter letter1 = new Letter(paul, rachel);
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter1);
        
        // Action 1: Attempt remove Paul specifying Quinn
        boolean result1 = southEnd.removeMailman(paul, quinn);
        
        // Expected Output: true (normal case)
        assertTrue(result1);
        
        // Action 2: Attempt remove Quinn (last mailman)
        boolean result2 = southEnd.removeMailman(quinn, quinn);
        
        // Expected Output: false
        assertFalse(result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Setup
        Mailman mario = new Mailman("1", "Mario");
        Mailman luigi = new Mailman("2", "Luigi");
        Mailman toad = new Mailman("3", "Toad");
        
        Inhabitant peach = new Inhabitant("101", "Peach");
        Inhabitant bowser = new Inhabitant("102", "Bowser");
        
        eastHaven.addMailman(mario);
        eastHaven.addMailman(luigi);
        
        eastHaven.addInhabitant(peach);
        eastHaven.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter(mario, peach);
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Letter(luigi, bowser);
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // Procedure 1: Add duplicate "Mario"
        boolean result1 = eastHaven.addMailman(mario);
        
        // Expected Output: false
        assertFalse(result1);
        
        // Procedure 2: Remove Mario (specify Luigi)
        boolean result2 = eastHaven.removeMailman(mario, luigi);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Procedure 3: Verify Luigi now has 15 deliveries
        List<RegisteredMail> luigiDeliveries = eastHaven.listRegisteredMail(luigi);
        assertNotNull("Luigi should have deliveries", luigiDeliveries);
        assertEquals("Luigi should have 15 deliveries", 15, luigiDeliveries.size());
        
        // Procedure 4: Attempt remove Luigi (last mailman)
        boolean result3 = eastHaven.removeMailman(luigi, luigi);
        
        // Expected Output: false
        assertFalse(result3);
        
        // Procedure 5: Add "Toad"
        boolean result4 = eastHaven.addMailman(toad);
        
        // Expected Output: true
        assertTrue(result4);
        
        // Procedure 6: Remove Luigi (specify Toad)
        boolean result5 = eastHaven.removeMailman(luigi, toad);
        
        // Expected Output: true
        assertTrue(result5);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Setup
        Mailman alpha = new Mailman("1", "Alpha");
        Mailman beta = new Mailman("2", "Beta");
        Mailman gamma = new Mailman("3", "Gamma");
        
        westRidge.addMailman(alpha);
        westRidge.addMailman(beta);
        westRidge.addMailman(gamma);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant("10" + i, "Inhabitant" + i);
            inhabitants.add(inhabitant);
            westRidge.addInhabitant(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter1 = new Letter(alpha, inhabitants.get(inhabitantIndex % 10));
            westRidge.assignRegisteredMailDeliver(alpha, inhabitants.get(inhabitantIndex % 10), letter1);
            inhabitantIndex++;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel1 = new Parcel(beta, inhabitants.get(inhabitantIndex % 10));
            westRidge.assignRegisteredMailDeliver(beta, inhabitants.get(inhabitantIndex % 10), parcel1);
            inhabitantIndex++;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter2 = new Letter(gamma, inhabitants.get(inhabitantIndex % 10));
            westRidge.assignRegisteredMailDeliver(gamma, inhabitants.get(inhabitantIndex % 10), letter2);
            inhabitantIndex++;
        }
        
        // Action 1: Remove Alpha (specify Beta)
        boolean result1 = westRidge.removeMailman(alpha, beta);
        
        // Expected Output: true
        assertTrue(result1);
        
        // Action 2: Remove Beta (specify Gamma)
        boolean result2 = westRidge.removeMailman(beta, gamma);
        
        // Expected Output: true
        assertTrue(result2);
        
        // Verification: Gamma ends with all 30 deliveries
        List<RegisteredMail> gammaDeliveries = westRidge.listRegisteredMail(gamma);
        assertNotNull("Gamma should have deliveries", gammaDeliveries);
        assertEquals("Gamma should have 30 deliveries", 30, gammaDeliveries.size());
    }
}