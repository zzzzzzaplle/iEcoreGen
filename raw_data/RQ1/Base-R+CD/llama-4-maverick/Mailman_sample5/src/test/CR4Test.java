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
    
    private Mailman alice;
    private Mailman bob;
    private Mailman charlie;
    private Mailman xavier;
    private Mailman yvonne;
    private Mailman zack;
    private Mailman paul;
    private Mailman quinn;
    private Mailman mario;
    private Mailman luigi;
    private Mailman toad;
    private Mailman alpha;
    private Mailman beta;
    private Mailman gamma;
    
    private Inhabitant david;
    private Inhabitant eve;
    private Inhabitant frank;
    private Inhabitant walter;
    private Inhabitant rachel;
    private Inhabitant peach;
    private Inhabitant bowser;
    private List<Inhabitant> westRidgeInhabitants;
    
    private Letter letter1;
    private Letter letter2;
    private Letter letter3;
    private Parcel parcel1;
    private Parcel parcel2;
    
    @Before
    public void setUp() {
        // Initialize all mailmen
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
        
        // Initialize all inhabitants
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
        
        // Initialize mail items
        letter1 = new Letter();
        letter2 = new Letter();
        letter3 = new Letter();
        parcel1 = new Parcel();
        parcel2 = new Parcel();
        
        // Initialize West Ridge inhabitants
        westRidgeInhabitants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            westRidgeInhabitants.add(new Inhabitant());
        }
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // Set up CentralDistrict with mailmen and inhabitants
        List<Mailman> centralMailmen = new ArrayList<>();
        centralMailmen.add(alice);
        centralMailmen.add(bob);
        centralMailmen.add(charlie);
        centralDistrict.setMailmen(centralMailmen);
        
        List<Inhabitant> centralInhabitants = new ArrayList<>();
        centralInhabitants.add(david);
        centralInhabitants.add(eve);
        centralInhabitants.add(frank);
        centralDistrict.setInhabitants(centralInhabitants);
        
        // Assign mail items
        centralDistrict.assignRegisteredMailDeliver(alice, david, letter1);
        centralDistrict.assignRegisteredMailDeliver(bob, eve, parcel1);
        centralDistrict.assignRegisteredMailDeliver(charlie, frank, letter2);
        centralDistrict.assignRegisteredMailDeliver(alice, eve, parcel2);
        centralDistrict.assignRegisteredMailDeliver(bob, david, letter3);
        
        // Remove Alice with Bob as replacement
        boolean result = centralDistrict.removeMailman(alice, bob);
        
        // Verify operation was successful
        assertTrue("Alice should be removed successfully", result);
        
        // Verify Alice was removed
        assertFalse("Alice should no longer be in mailmen list", 
                   centralDistrict.getMailmen().contains(alice));
        
        // Verify Bob's deliveries: should have 4 items (original 2 + 2 from Alice)
        int bobDeliveryCount = 0;
        List<RegisteredMail> allDeliveries = centralDistrict.getAllDeliveries();
        for (RegisteredMail mail : allDeliveries) {
            if (mail.getCarrier().equals(bob)) {
                bobDeliveryCount++;
            }
        }
        assertEquals("Bob should have 4 deliveries after reassignment", 4, bobDeliveryCount);
        
        // Verify Charlie's assignments unchanged (1 item)
        int charlieDeliveryCount = 0;
        for (RegisteredMail mail : allDeliveries) {
            if (mail.getCarrier().equals(charlie)) {
                charlieDeliveryCount++;
            }
        }
        assertEquals("Charlie should still have 1 delivery", 1, charlieDeliveryCount);
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // Set up NorthQuarter with mailmen and inhabitants
        List<Mailman> northMailmen = new ArrayList<>();
        northMailmen.add(xavier);
        northMailmen.add(yvonne);
        northMailmen.add(zack);
        northQuarter.setMailmen(northMailmen);
        
        List<Inhabitant> northInhabitants = new ArrayList<>();
        northInhabitants.add(walter);
        northQuarter.setInhabitants(northInhabitants);
        
        // Create and assign 5 Letters (Xavier→Walter)
        List<Letter> xavierLetters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(xavier, walter, letter);
            xavierLetters.add(letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        List<Parcel> yvonneParcels = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel();
            northQuarter.assignRegisteredMailDeliver(yvonne, walter, parcel);
            yvonneParcels.add(parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        List<Letter> zackLetters = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter();
            northQuarter.assignRegisteredMailDeliver(zack, walter, letter);
            zackLetters.add(letter);
        }
        
        // Remove Yvonne with Xavier as replacement
        boolean firstResult = northQuarter.removeMailman(yvonne, xavier);
        assertTrue("First removal (Yvonne) should be successful", firstResult);
        
        // Verify Yvonne was removed and parcels moved to Xavier (now has 8 items)
        assertFalse("Yvonne should no longer be in mailmen list", 
                   northQuarter.getMailmen().contains(yvonne));
        
        int xavierDeliveryCount = 0;
        List<RegisteredMail> allDeliveries = northQuarter.getAllDeliveries();
        for (RegisteredMail mail : allDeliveries) {
            if (mail.getCarrier().equals(xavier)) {
                xavierDeliveryCount++;
            }
        }
        assertEquals("Xavier should have 8 deliveries after first reassignment", 8, xavierDeliveryCount);
        
        // Remove Xavier with Zack as replacement
        boolean secondResult = northQuarter.removeMailman(xavier, zack);
        assertTrue("Second removal (Xavier) should be successful", secondResult);
        
        // Verify Xavier was removed and all deliveries moved to Zack (now has 10 items)
        assertFalse("Xavier should no longer be in mailmen list", 
                   northQuarter.getMailmen().contains(xavier));
        
        int zackDeliveryCount = 0;
        allDeliveries = northQuarter.getAllDeliveries();
        for (RegisteredMail mail : allDeliveries) {
            if (mail.getCarrier().equals(zack)) {
                zackDeliveryCount++;
            }
        }
        assertEquals("Zack should have all 10 deliveries after second reassignment", 10, zackDeliveryCount);
        
        // Verify final state: Only Zack remains
        assertEquals("Only Zack should remain in mailmen list", 1, northQuarter.getMailmen().size());
        assertTrue("Zack should be the only remaining mailman", 
                  northQuarter.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // Set up SouthEnd with mailmen and inhabitants
        List<Mailman> southMailmen = new ArrayList<>();
        southMailmen.add(paul);
        southMailmen.add(quinn);
        southEnd.setMailmen(southMailmen);
        
        List<Inhabitant> southInhabitants = new ArrayList<>();
        southInhabitants.add(rachel);
        southEnd.setInhabitants(southInhabitants);
        
        // Create and assign Letter1 (Paul→Rachel)
        Letter letter = new Letter();
        southEnd.assignRegisteredMailDeliver(paul, rachel, letter);
        
        // Attempt remove Paul specifying Quinn (should succeed)
        boolean firstResult = southEnd.removeMailman(paul, quinn);
        assertTrue("First removal (Paul) should be successful", firstResult);
        
        // Verify Paul was removed
        assertFalse("Paul should no longer be in mailmen list", 
                   southEnd.getMailmen().contains(paul));
        
        // Attempt remove Quinn (last mailman, should fail)
        boolean secondResult = southEnd.removeMailman(quinn, paul); // paul is no longer available, but should fail anyway
        assertFalse("Removing last mailman should fail", secondResult);
        
        // Verify Quinn is still there
        assertTrue("Quinn should still be in mailmen list", 
                  southEnd.getMailmen().contains(quinn));
        assertEquals("Should still have 1 mailman", 1, southEnd.getMailmen().size());
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // Set up EastHaven with mailmen and inhabitants
        List<Mailman> eastMailmen = new ArrayList<>();
        eastMailmen.add(mario);
        eastMailmen.add(luigi);
        eastHaven.setMailmen(eastMailmen);
        
        List<Inhabitant> eastInhabitants = new ArrayList<>();
        eastInhabitants.add(peach);
        eastInhabitants.add(bowser);
        eastHaven.setInhabitants(eastInhabitants);
        
        // Create and assign 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            eastHaven.assignRegisteredMailDeliver(mario, peach, letter);
        }
        
        // Create and assign 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel();
            eastHaven.assignRegisteredMailDeliver(luigi, bowser, parcel);
        }
        
        // 1. Add duplicate "Mario" → false
        boolean addDuplicateResult = eastHaven.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", addDuplicateResult);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMarioResult = eastHaven.removeMailman(mario, luigi);
        assertTrue("Removing Mario should succeed", removeMarioResult);
        
        // 3. Verify Luigi now has 15 deliveries (10 from Mario + 5 original)
        int luigiDeliveryCount = 0;
        List<RegisteredMail> allDeliveries = eastHaven.getAllDeliveries();
        for (RegisteredMail mail : allDeliveries) {
            if (mail.getCarrier().equals(luigi)) {
                luigiDeliveryCount++;
            }
        }
        assertEquals("Luigi should have 15 deliveries after reassignment", 15, luigiDeliveryCount);
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigiResult = eastHaven.removeMailman(luigi, mario); // mario is no longer available
        assertFalse("Removing last mailman should fail", removeLuigiResult);
        
        // 5. Add "Toad" → true
        boolean addToadResult = eastHaven.addMailman(toad);
        assertTrue("Adding Toad should succeed", addToadResult);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeLuigiAgainResult = eastHaven.removeMailman(luigi, toad);
        assertTrue("Removing Luigi with replacement should succeed", removeLuigiAgainResult);
        
        // Verify final state: Only Toad remains with all deliveries
        assertEquals("Only Toad should remain", 1, eastHaven.getMailmen().size());
        assertTrue("Toad should be the only mailman", eastHaven.getMailmen().contains(toad));
        
        int toadDeliveryCount = 0;
        allDeliveries = eastHaven.getAllDeliveries();
        for (RegisteredMail mail : allDeliveries) {
            if (mail.getCarrier().equals(toad)) {
                toadDeliveryCount++;
            }
        }
        assertEquals("Toad should have all 15 deliveries", 15, toadDeliveryCount);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // Set up WestRidge with mailmen and inhabitants
        List<Mailman> westMailmen = new ArrayList<>();
        westMailmen.add(alpha);
        westMailmen.add(beta);
        westMailmen.add(gamma);
        westRidge.setMailmen(westMailmen);
        westRidge.setInhabitants(westRidgeInhabitants);
        
        // Create and assign 30 mail items (10 each mailman)
        int inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(alpha, westRidgeInhabitants.get(inhabitantIndex), letter);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel();
            westRidge.assignRegisteredMailDeliver(beta, westRidgeInhabitants.get(inhabitantIndex), parcel);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter();
            westRidge.assignRegisteredMailDeliver(gamma, westRidgeInhabitants.get(inhabitantIndex), letter);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // 1. Remove Alpha (specify Beta) → true
        boolean firstResult = westRidge.removeMailman(alpha, beta);
        assertTrue("First removal (Alpha) should be successful", firstResult);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean secondResult = westRidge.removeMailman(beta, gamma);
        assertTrue("Second removal (Beta) should be successful", secondResult);
        
        // Verify Gamma ends with all 30 deliveries
        int gammaDeliveryCount = 0;
        List<RegisteredMail> allDeliveries = westRidge.getAllDeliveries();
        for (RegisteredMail mail : allDeliveries) {
            if (mail.getCarrier().equals(gamma)) {
                gammaDeliveryCount++;
            }
        }
        assertEquals("Gamma should have all 30 deliveries", 30, gammaDeliveryCount);
        
        // Verify only Gamma remains
        assertEquals("Only Gamma should remain", 1, westRidge.getMailmen().size());
        assertTrue("Gamma should be the only mailman", westRidge.getMailmen().contains(gamma));
    }
}