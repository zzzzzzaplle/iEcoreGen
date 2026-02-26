import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private MailDeliveryManager manager;
    private GeographicalArea centralDistrict;
    private GeographicalArea northQuarter;
    private GeographicalArea southEnd;
    private GeographicalArea eastHaven;
    private GeographicalArea westRidge;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        centralDistrict = new GeographicalArea();
        centralDistrict.setName("CentralDistrict");
        manager.addGeographicalArea(centralDistrict);
        
        // Add Mailmen ["Alice", "Bob", "Charlie"]
        Mailman alice = new Mailman();
        alice.setName("Alice");
        manager.addMailmanToGeographicalArea(alice, centralDistrict);
        
        Mailman bob = new Mailman();
        bob.setName("Bob");
        manager.addMailmanToGeographicalArea(bob, centralDistrict);
        
        Mailman charlie = new Mailman();
        charlie.setName("Charlie");
        manager.addMailmanToGeographicalArea(charlie, centralDistrict);
        
        // Add Inhabitants ["David", "Eve", "Frank"]
        Inhabitant david = new Inhabitant();
        david.setName("David");
        manager.addInhabitantToGeographicalArea(david, centralDistrict);
        
        Inhabitant eve = new Inhabitant();
        eve.setName("Eve");
        manager.addInhabitantToGeographicalArea(eve, centralDistrict);
        
        Inhabitant frank = new Inhabitant();
        frank.setName("Frank");
        manager.addInhabitantToGeographicalArea(frank, centralDistrict);
        
        // Create and assign mail items
        // Letter1 (Alice→David)
        Letter letter1 = new Letter();
        letter1.setAddressee(david);
        letter1.assignMailman(alice);
        centralDistrict.addRegisteredMail(letter1);
        
        // Parcel1 (Bob→Eve)
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(eve);
        parcel1.assignMailman(bob);
        centralDistrict.addRegisteredMail(parcel1);
        
        // Letter2 (Charlie→Frank)
        Letter letter2 = new Letter();
        letter2.setAddressee(frank);
        letter2.assignMailman(charlie);
        centralDistrict.addRegisteredMail(letter2);
        
        // Parcel2 (Alice→Eve)
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(eve);
        parcel2.assignMailman(alice);
        centralDistrict.addRegisteredMail(parcel2);
        
        // Letter3 (Bob→David)
        Letter letter3 = new Letter();
        letter3.setAddressee(david);
        letter3.assignMailman(bob);
        centralDistrict.addRegisteredMail(letter3);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = manager.removeMailmanFromGeographicalArea(alice, bob);
        
        // Expected Output: true
        assertTrue("Alice should be successfully removed", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen list", 
                   centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        assertEquals("Bob should have 4 deliveries", 4, countDeliveriesForMailman(bob));
        assertEquals("Letter1 should be reassigned to Bob", bob, letter1.getMailman());
        assertEquals("Parcel2 should be reassigned to Bob", bob, parcel2.getMailman());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertEquals("Parcel1 should still be assigned to Bob", bob, parcel1.getMailman());
        assertEquals("Letter3 should still be assigned to Bob", bob, letter3.getMailman());
        
        // Charlie's assignments unchanged (now has 1 item)
        assertEquals("Charlie should have 1 delivery", 1, countDeliveriesForMailman(charlie));
        assertEquals("Letter2 should still be assigned to Charlie", charlie, letter2.getMailman());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        northQuarter = new GeographicalArea();
        northQuarter.setName("NorthQuarter");
        manager.addGeographicalArea(northQuarter);
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        Mailman xavier = new Mailman();
        xavier.setName("Xavier");
        manager.addMailmanToGeographicalArea(xavier, northQuarter);
        
        Mailman yvonne = new Mailman();
        yvonne.setName("Yvonne");
        manager.addMailmanToGeographicalArea(yvonne, northQuarter);
        
        Mailman zack = new Mailman();
        zack.setName("Zack");
        manager.addMailmanToGeographicalArea(zack, northQuarter);
        
        // Add Inhabitant "Walter"
        Inhabitant walter = new Inhabitant();
        walter.setName("Walter");
        manager.addInhabitantToGeographicalArea(walter, northQuarter);
        
        // Create and assign mail items
        // 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.assignMailman(xavier);
            northQuarter.addRegisteredMail(letter);
        }
        
        // 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            parcel.assignMailman(yvonne);
            northQuarter.addRegisteredMail(parcel);
        }
        
        // 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.assignMailman(zack);
            northQuarter.addRegisteredMail(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = manager.removeMailmanFromGeographicalArea(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal should be successful", result1);
        
        // Verifications after first removal
        // 3 parcels moved to Xavier (now has 8 items)
        assertEquals("Xavier should have 8 deliveries after first removal", 8, countDeliveriesForMailman(xavier));
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = manager.removeMailmanFromGeographicalArea(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal should be successful", result2);
        
        // Verifications after second removal
        // 8 letters moved to Zack (now has 10 items)
        assertEquals("Zack should have 10 deliveries after second removal", 10, countDeliveriesForMailman(zack));
        
        // Final state: Only Zack remains with all 10 deliveries
        assertEquals("Only Zack should remain", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", northQuarter.getMailmen().contains(zack));
        assertEquals("Zack should have all 10 deliveries", 10, countDeliveriesForMailman(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        southEnd = new GeographicalArea();
        southEnd.setName("SouthEnd");
        manager.addGeographicalArea(southEnd);
        
        // Add Mailmen ["Paul", "Quinn"]
        Mailman paul = new Mailman();
        paul.setName("Paul");
        manager.addMailmanToGeographicalArea(paul, southEnd);
        
        Mailman quinn = new Mailman();
        quinn.setName("Quinn");
        manager.addMailmanToGeographicalArea(quinn, southEnd);
        
        // Add Inhabitant "Rachel"
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        manager.addInhabitantToGeographicalArea(rachel, southEnd);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        letter1.assignMailman(paul);
        southEnd.addRegisteredMail(letter1);
        
        // Actions & Expected Outputs
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = manager.removeMailmanFromGeographicalArea(paul, quinn);
        assertTrue("Removing Paul should be successful", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = manager.removeMailmanFromGeographicalArea(quinn, paul); // paul is already removed
        assertFalse("Removing last mailman should fail", result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        eastHaven = new GeographicalArea();
        eastHaven.setName("EastHaven");
        manager.addGeographicalArea(eastHaven);
        
        // Add Mailmen ["Mario", "Luigi"]
        Mailman mario = new Mailman();
        mario.setName("Mario");
        manager.addMailmanToGeographicalArea(mario, eastHaven);
        
        Mailman luigi = new Mailman();
        luigi.setName("Luigi");
        manager.addMailmanToGeographicalArea(luigi, eastHaven);
        
        // Add Inhabitants ["Peach", "Bowser"]
        Inhabitant peach = new Inhabitant();
        peach.setName("Peach");
        manager.addInhabitantToGeographicalArea(peach, eastHaven);
        
        Inhabitant bowser = new Inhabitant();
        bowser.setName("Bowser");
        manager.addInhabitantToGeographicalArea(bowser, eastHaven);
        
        // Create mail items
        // 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            letter.assignMailman(mario);
            eastHaven.addRegisteredMail(letter);
        }
        
        // 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            parcel.assignMailman(luigi);
            eastHaven.addRegisteredMail(parcel);
        }
        
        // Procedure
        // 1. Add duplicate "Mario" → false
        boolean addDuplicate = manager.addMailmanToGeographicalArea(mario, eastHaven);
        assertFalse("Adding duplicate mailman should fail", addDuplicate);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMario = manager.removeMailmanFromGeographicalArea(mario, luigi);
        assertTrue("Removing Mario should be successful", removeMario);
        
        // 3. Verify Luigi now has 15 deliveries
        assertEquals("Luigi should have 15 deliveries", 15, countDeliveriesForMailman(luigi));
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigiFirstAttempt = manager.removeMailmanFromGeographicalArea(luigi, mario); // mario is removed
        assertFalse("Removing last mailman should fail", removeLuigiFirstAttempt);
        
        // 5. Add "Toad" → true
        Mailman toad = new Mailman();
        toad.setName("Toad");
        boolean addToad = manager.addMailmanToGeographicalArea(toad, eastHaven);
        assertTrue("Adding Toad should be successful", addToad);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeLuigiSecondAttempt = manager.removeMailmanFromGeographicalArea(luigi, toad);
        assertTrue("Removing Luigi should be successful", removeLuigiSecondAttempt);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        westRidge = new GeographicalArea();
        westRidge.setName("WestRidge");
        manager.addGeographicalArea(westRidge);
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        Mailman alpha = new Mailman();
        alpha.setName("Alpha");
        manager.addMailmanToGeographicalArea(alpha, westRidge);
        
        Mailman beta = new Mailman();
        beta.setName("Beta");
        manager.addMailmanToGeographicalArea(beta, westRidge);
        
        Mailman gamma = new Mailman();
        gamma.setName("Gamma");
        manager.addMailmanToGeographicalArea(gamma, westRidge);
        
        // Add 10 Inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant();
            inhabitants[i].setName("Inhabitant" + (i + 1));
            manager.addInhabitantToGeographicalArea(inhabitants[i], westRidge);
        }
        
        // Create 30 mail items (10 each mailman)
        int inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants[inhabitantIndex]);
            letter.assignMailman(alpha);
            westRidge.addRegisteredMail(letter);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(inhabitants[inhabitantIndex]);
            parcel.assignMailman(beta);
            westRidge.addRegisteredMail(parcel);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(inhabitants[inhabitantIndex]);
            letter.assignMailman(gamma);
            westRidge.addRegisteredMail(letter);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Action
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = manager.removeMailmanFromGeographicalArea(alpha, beta);
        assertTrue("First removal should be successful", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = manager.removeMailmanFromGeographicalArea(beta, gamma);
        assertTrue("Second removal should be successful", result2);
        
        // Verifications
        // Gamma ends with all 30 deliveries
        assertEquals("Gamma should have all 30 deliveries", 30, countDeliveriesForMailman(gamma));
    }
    
    // Helper method to count deliveries for a specific mailman
    private int countDeliveriesForMailman(Mailman mailman) {
        int count = 0;
        for (GeographicalArea area : manager.getGeographicalAreas()) {
            for (RegisteredMail mail : area.getRegisteredMails()) {
                if (mail.getMailman() == mailman) {
                    count++;
                }
            }
        }
        return count;
    }
}