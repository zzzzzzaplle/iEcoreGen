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
    
    private Mailman alice, bob, charlie, xavier, yvonne, zack, paul, quinn, mario, luigi, toad, alpha, beta, gamma;
    private Inhabitant david, eve, frank, walter, rachel, peach, bowser;
    
    @Before
    public void setUp() {
        // Initialize mailmen
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
        
        // Initialize inhabitants
        david = new Inhabitant();
        eve = new Inhabitant();
        frank = new Inhabitant();
        walter = new Inhabitant();
        rachel = new Inhabitant();
        peach = new Inhabitant();
        bowser = new Inhabitant();
        
        // Initialize geographical areas
        centralDistrict = new GeographicalArea();
        northQuarter = new GeographicalArea();
        southEnd = new GeographicalArea();
        eastHaven = new GeographicalArea();
        westRidge = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        // Add Mailmen ["Alice", "Bob", "Charlie"]
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(alice);
        mailmen.add(bob);
        mailmen.add(charlie);
        centralDistrict.setMailmen(mailmen);
        
        // Add Inhabitants ["David", "Eve", "Frank"]
        List<Inhabitant> inhabitants = new ArrayList<>();
        inhabitants.add(david);
        inhabitants.add(eve);
        inhabitants.add(frank);
        centralDistrict.setInhabitants(inhabitants);
        
        // Create and assign mail items
        Letter letter1 = new Letter();
        letter1.setAddressee(david);
        letter1.setCarrier(alice);
        
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(eve);
        parcel1.setCarrier(bob);
        
        Letter letter2 = new Letter();
        letter2.setAddressee(frank);
        letter2.setCarrier(charlie);
        
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(eve);
        parcel2.setCarrier(alice);
        
        Letter letter3 = new Letter();
        letter3.setAddressee(david);
        letter3.setCarrier(bob);
        
        // Add deliveries to geographical area
        List<RegisteredMail> deliveries = new ArrayList<>();
        deliveries.add(letter1);
        deliveries.add(parcel1);
        deliveries.add(letter2);
        deliveries.add(parcel2);
        deliveries.add(letter3);
        centralDistrict.setDeliveries(deliveries);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Alice removal should be successful", result);
        
        // Verifications:
        // Alice removed
        assertFalse("Alice should be removed from mailmen list", 
                   centralDistrict.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        int bobDeliveries = 0;
        int charlieDeliveries = 0;
        
        for (RegisteredMail mail : centralDistrict.getDeliveries()) {
            if (mail.getCarrier().equals(bob)) {
                bobDeliveries++;
            } else if (mail.getCarrier().equals(charlie)) {
                charlieDeliveries++;
            }
        }
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged + reassigned (Letter1, Parcel2)
        assertEquals("Bob should have 4 deliveries after reassignment", 4, bobDeliveries);
        
        // Charlie's assignments unchanged (now has 1 item)
        assertEquals("Charlie should have 1 delivery unchanged", 1, charlieDeliveries);
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(xavier);
        mailmen.add(yvonne);
        mailmen.add(zack);
        northQuarter.setMailmen(mailmen);
        
        // Add Inhabitant "Walter"
        List<Inhabitant> inhabitants = new ArrayList<>();
        inhabitants.add(walter);
        northQuarter.setInhabitants(inhabitants);
        
        // Create and assign mail items
        List<RegisteredMail> deliveries = new ArrayList<>();
        
        // 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setCarrier(xavier);
            deliveries.add(letter);
        }
        
        // 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(walter);
            parcel.setCarrier(yvonne);
            deliveries.add(parcel);
        }
        
        // 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            letter.setAddressee(walter);
            letter.setCarrier(zack);
            deliveries.add(letter);
        }
        
        northQuarter.setDeliveries(deliveries);
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean firstRemoval = northQuarter.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("First removal (Yvonne) should be successful", firstRemoval);
        
        // After first removal: 3 parcels moved to Xavier (now has 8 items)
        int xavierDeliveriesAfterFirst = 0;
        for (RegisteredMail mail : northQuarter.getDeliveries()) {
            if (mail.getCarrier().equals(xavier)) {
                xavierDeliveriesAfterFirst++;
            }
        }
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xavierDeliveriesAfterFirst);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean secondRemoval = northQuarter.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Second removal (Xavier) should be successful", secondRemoval);
        
        // After second removal: 8 letters moved to Zack (now has 10 items)
        int zackDeliveries = 0;
        for (RegisteredMail mail : northQuarter.getDeliveries()) {
            if (mail.getCarrier().equals(zack)) {
                zackDeliveries++;
            }
        }
        
        // Final state: Only Zack remains with all 10 deliveries
        assertEquals("Zack should have all 10 deliveries", 10, zackDeliveries);
        assertEquals("Only Zack should remain as mailman", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", 
                  northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        // Add Mailmen ["Paul", "Quinn"]
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(paul);
        mailmen.add(quinn);
        southEnd.setMailmen(mailmen);
        
        // Add Inhabitant "Rachel"
        List<Inhabitant> inhabitants = new ArrayList<>();
        inhabitants.add(rachel);
        southEnd.setInhabitants(inhabitants);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter();
        letter1.setAddressee(rachel);
        letter1.setCarrier(paul);
        
        List<RegisteredMail> deliveries = new ArrayList<>();
        deliveries.add(letter1);
        southEnd.setDeliveries(deliveries);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean firstRemoval = southEnd.removeMailman(paul, quinn);
        assertTrue("Removing Paul with Quinn as replacement should succeed", firstRemoval);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean secondRemoval = southEnd.removeMailman(quinn, paul); // paul is no longer in the list
        assertFalse("Removing last mailman should fail", secondRemoval);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        // Add Mailmen ["Mario", "Luigi"]
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(mario);
        mailmen.add(luigi);
        eastHaven.setMailmen(mailmen);
        
        // Add Inhabitants ["Peach", "Bowser"]
        List<Inhabitant> inhabitants = new ArrayList<>();
        inhabitants.add(peach);
        inhabitants.add(bowser);
        eastHaven.setInhabitants(inhabitants);
        
        // Create mail items
        List<RegisteredMail> deliveries = new ArrayList<>();
        
        // 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            letter.setAddressee(peach);
            letter.setCarrier(mario);
            deliveries.add(letter);
        }
        
        // 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            parcel.setAddressee(bowser);
            parcel.setCarrier(luigi);
            deliveries.add(parcel);
        }
        
        eastHaven.setDeliveries(deliveries);
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean duplicateAdd = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate Mario should fail", duplicateAdd);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean marioRemoval = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removing Mario should succeed", marioRemoval);
        
        // 3. Verify Luigi now has 15 deliveries
        int luigiDeliveries = 0;
        for (RegisteredMail mail : eastHaven.getDeliveries()) {
            if (mail.getCarrier().equals(luigi)) {
                luigiDeliveries++;
            }
        }
        assertEquals("Luigi should have 15 deliveries after reassignment", 15, luigiDeliveries);
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean luigiRemovalAttempt = eastHaven.removeMailman(luigi, mario); // mario is no longer in the list
        assertFalse("Removing last mailman should fail", luigiRemovalAttempt);
        
        // 5. Add "Toad" → true
        boolean toadAdd = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should succeed", toadAdd);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean luigiRemoval = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removing Luigi with Toad as replacement should succeed", luigiRemoval);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        List<Mailman> mailmen = new ArrayList<>();
        mailmen.add(alpha);
        mailmen.add(beta);
        mailmen.add(gamma);
        westRidge.setMailmen(mailmen);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            inhabitants.add(new Inhabitant());
        }
        westRidge.setInhabitants(inhabitants);
        
        // Create 30 mail items (10 each mailman)
        List<RegisteredMail> deliveries = new ArrayList<>();
        
        // 10 items for Alpha
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                letter.setAddressee(inhabitants.get(i % inhabitants.size()));
                letter.setCarrier(alpha);
                deliveries.add(letter);
            } else {
                Parcel parcel = new Parcel();
                parcel.setAddressee(inhabitants.get(i % inhabitants.size()));
                parcel.setCarrier(alpha);
                deliveries.add(parcel);
            }
        }
        
        // 10 items for Beta
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                letter.setAddressee(inhabitants.get(i % inhabitants.size()));
                letter.setCarrier(beta);
                deliveries.add(letter);
            } else {
                Parcel parcel = new Parcel();
                parcel.setAddressee(inhabitants.get(i % inhabitants.size()));
                parcel.setCarrier(beta);
                deliveries.add(parcel);
            }
        }
        
        // 10 items for Gamma
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = new Letter();
                letter.setAddressee(inhabitants.get(i % inhabitants.size()));
                letter.setCarrier(gamma);
                deliveries.add(letter);
            } else {
                Parcel parcel = new Parcel();
                parcel.setAddressee(inhabitants.get(i % inhabitants.size()));
                parcel.setCarrier(gamma);
                deliveries.add(parcel);
            }
        }
        
        westRidge.setDeliveries(deliveries);
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean firstRemoval = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal (Alpha) should succeed", firstRemoval);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean secondRemoval = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal (Beta) should succeed", secondRemoval);
        
        // Verifications:
        // Gamma ends with all 30 deliveries
        int gammaDeliveries = 0;
        for (RegisteredMail mail : westRidge.getDeliveries()) {
            if (mail.getCarrier().equals(gamma)) {
                gammaDeliveries++;
            }
        }
        
        assertEquals("Gamma should have all 30 deliveries", 30, gammaDeliveries);
        assertEquals("Only Gamma should remain as mailman", 1, westRidge.getMailmen().size());
        assertTrue("Gamma should be the only remaining mailman", 
                  westRidge.getMailmen().contains(gamma));
    }
}