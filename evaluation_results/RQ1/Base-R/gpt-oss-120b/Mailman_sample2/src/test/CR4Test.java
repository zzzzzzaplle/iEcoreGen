import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private MailDeliverySystem system;
    private GeographicalArea centralDistrict;
    private GeographicalArea northQuarter;
    private GeographicalArea southEnd;
    private GeographicalArea eastHaven;
    private GeographicalArea westRidge;
    
    @Before
    public void setUp() {
        system = new MailDeliverySystem();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        centralDistrict = new GeographicalArea("CD001", "CentralDistrict");
        system.addArea(centralDistrict);
        
        // Add Mailmen ["Alice", "Bob", "Charlie"]
        Mailman alice = new Mailman("M001", "Alice");
        Mailman bob = new Mailman("M002", "Bob");
        Mailman charlie = new Mailman("M003", "Charlie");
        system.addMailman(centralDistrict, alice);
        system.addMailman(centralDistrict, bob);
        system.addMailman(centralDistrict, charlie);
        
        // Add Inhabitants ["David", "Eve", "Frank"]
        Inhabitant david = new Inhabitant("I001", "David");
        Inhabitant eve = new Inhabitant("I002", "Eve");
        Inhabitant frank = new Inhabitant("I003", "Frank");
        system.addInhabitant(centralDistrict, david);
        system.addInhabitant(centralDistrict, eve);
        system.addInhabitant(centralDistrict, frank);
        
        // Create and assign mail items
        Letter letter1 = new Letter("L001", david, "Subject1", "Body1");
        Parcel parcel1 = new Parcel("P001", eve, 2.5, "Electronics");
        Letter letter2 = new Letter("L002", frank, "Subject2", "Body2");
        Parcel parcel2 = new Parcel("P002", eve, 1.8, "Books");
        Letter letter3 = new Letter("L003", david, "Subject3", "Body3");
        
        centralDistrict.internalAddRegisteredMail(letter1);
        centralDistrict.internalAddRegisteredMail(parcel1);
        centralDistrict.internalAddRegisteredMail(letter2);
        centralDistrict.internalAddRegisteredMail(parcel2);
        centralDistrict.internalAddRegisteredMail(letter3);
        
        // Assign mailmen to mail items
        assertTrue(system.assignMailToMailman(letter1, alice));
        assertTrue(system.assignMailToMailman(parcel1, bob));
        assertTrue(system.assignMailToMailman(letter2, charlie));
        assertTrue(system.assignMailToMailman(parcel2, alice));
        assertTrue(system.assignMailToMailman(letter3, bob));
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = system.removeMailman(centralDistrict, alice, bob);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications
        // - Alice removed
        assertFalse(centralDistrict.getMailmen().contains(alice));
        
        // - Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<DeliveryInfo> deliveries = system.getDeliveriesByArea(centralDistrict);
        int bobDeliveries = 0;
        int charlieDeliveries = 0;
        
        for (DeliveryInfo delivery : deliveries) {
            if (delivery.getMailman().equals(bob)) {
                bobDeliveries++;
            } else if (delivery.getMailman().equals(charlie)) {
                charlieDeliveries++;
            }
        }
        
        // Bob should have 4 deliveries (original 2 + 2 from Alice)
        assertEquals(4, bobDeliveries);
        
        // Charlie's assignments unchanged (now has 1 item)
        assertEquals(1, charlieDeliveries);
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        northQuarter = new GeographicalArea("NQ001", "NorthQuarter");
        system.addArea(northQuarter);
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        Mailman xavier = new Mailman("M004", "Xavier");
        Mailman yvonne = new Mailman("M005", "Yvonne");
        Mailman zack = new Mailman("M006", "Zack");
        system.addMailman(northQuarter, xavier);
        system.addMailman(northQuarter, yvonne);
        system.addMailman(northQuarter, zack);
        
        // Add Inhabitant "Walter"
        Inhabitant walter = new Inhabitant("I004", "Walter");
        system.addInhabitant(northQuarter, walter);
        
        // Create and assign: 5 Letters (Xavier→Walter), 3 Parcels (Yvonne→Walter), 2 Letters (Zack→Walter)
        for (int i = 1; i <= 5; i++) {
            Letter letter = new Letter("L10" + i, walter, "Subject" + i, "Body" + i);
        northQuarter.internalAddRegisteredMail(letter);
            assertTrue(system.assignMailToMailman(letter, xavier);
        }
        
        for (int i = 1; i <= 3; i++) {
            Parcel parcel = new Parcel("P10" + i, walter, i * 1.5, "Item" + i);
            northQuarter.internalAddRegisteredMail(parcel);
            assertTrue(system.assignMailToMailman(parcel, yvonne));
        }
        
        for (int i = 1; i <= 2; i++) {
            Letter letter = new Letter("L20" + i, walter, "Subject2" + i, "Body2" + i);
            northQuarter.internalAddRegisteredMail(letter);
            assertTrue(system.assignMailToMailman(letter, zack));
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean firstResult = system.removeMailman(northQuarter, yvonne, xavier);
        
        // Expected Output: true
        assertTrue(firstResult);
        
        // Verifications after first removal
        // - After first removal: 3 parcels moved to Xavier (now has 8 items)
        List<DeliveryInfo> afterFirstRemoval = system.getDeliveriesByArea(northQuarter);
        int xavierAfterFirst = 0;
        int zackAfterFirst = 0;
        
        for (DeliveryInfo delivery : afterFirstRemoval) {
            if (delivery.getMailman().equals(xavier)) {
                xavierAfterFirst++;
            } else if (delivery.getMailman().equals(zack)) {
                zackAfterFirst++;
            }
        }
        
        assertEquals(8, xavierAfterFirst); // 5 original + 3 from Yvonne
        assertEquals(2, zackAfterFirst); // 2 original
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean secondResult = system.removeMailman(northQuarter, xavier, zack);
        
        // Expected Output: true
        assertTrue(secondResult);
        
        // Verifications after second removal
        // - After second removal: 8 letters moved to Zack (now has 10 items)
        List<DeliveryInfo> afterSecondRemoval = system.getDeliveriesByArea(northQuarter);
        int zackAfterSecond = 0;
        
        for (DeliveryInfo delivery : afterSecondRemoval) {
            if (delivery.getMailman().equals(zack)) {
                zackAfterSecond++;
            }
        }
        
        // - Final state: Only Zack remains with all 10 deliveries
        assertEquals(2, northQuarter.getMailmen().size()); // Zack and Yvonne's replacement
        assertEquals(10, zackAfterSecond); // All 10 deliveries
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        southEnd = new GeographicalArea("SE001", "SouthEnd");
        system.addArea(southEnd);
        
        // Add Mailmen ["Paul", "Quinn"]
        Mailman paul = new Mailman("M007", "Paul");
        Mailman quinn = new Mailman("M008", "Quinn");
        system.addMailman(southEnd, paul);
        system.addMailman(southEnd, quinn);
        
        // Add Inhabitant "Rachel"
        Inhabitant rachel = new Inhabitant("I005", "Rachel");
        system.addInhabitant(southEnd, rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter("L030", rachel, "Important", "Urgent");
        southEnd.internalAddRegisteredMail(letter1);
        assertTrue(system.assignMailToMailman(letter1, paul));
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean firstResult = system.removeMailman(southEnd, paul, quinn);
        assertTrue(firstResult);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean secondResult = system.removeMailman(southEnd, quinn, paul); // paul is already removed, but this should still fail
        assertFalse(secondResult);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        eastHaven = new GeographicalArea("EH001", "EastHaven");
        system.addArea(eastHaven);
        
        // Add Mailmen ["Mario", "Luigi"]
        Mailman mario = new Mailman("M009", "Mario");
        Mailman luigi = new Mailman("M010", "Luigi");
        system.addMailman(eastHaven, mario);
        system.addMailman(eastHaven, luigi);
        
        // Add Inhabitants ["Peach", "Bowser"]
        Inhabitant peach = new Inhabitant("I006", "Peach");
        Inhabitant bowser = new Inhabitant("I007", "Bowser");
        system.addInhabitant(eastHaven, peach);
        system.addInhabitant(eastHaven, bowser);
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean duplicateAdd = system.addMailman(eastHaven, mario);
        assertFalse(duplicateAdd);
        
        // Create 10 Letters (Mario→Peach) and 5 Parcels (Luigi→Bowser)
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter("L40" + i, peach, "Letter" + i, "Content" + i);
            eastHaven.internalAddRegisteredMail(letter);
            assertTrue(system.assignMailToMailman(letter, mario));
        }
        
        for (int i = 1; i <= 5; i++) {
            Parcel parcel = new Parcel("P40" + i, bowser, i * 0.5, "Package" + i);
            eastHaven.internalAddRegisteredMail(parcel);
            assertTrue(system.assignMailToMailman(parcel, luigi));
        }
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMario = system.removeMailman(eastHaven, mario, luigi);
        assertTrue(removeMario);
        
        // 3. Verify Luigi now has 15 deliveries
        List<DeliveryInfo> deliveries = system.getDeliveriesByArea(eastHaven);
        int luigiDeliveries = 0;
        
        for (DeliveryInfo delivery : deliveries) {
            if (delivery.getMailman().equals(luigi)) {
                luigiDeliveries++;
            }
        }
        
        assertEquals(15, luigiDeliveries);
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigi = system.removeMailman(eastHaven, luigi, mario); // mario is already removed
        assertFalse(removeLuigi);
        
        // 5. Add "Toad" → true
        Mailman toad = new Mailman("M011", "Toad");
        boolean addToad = system.addMailman(eastHaven, toad);
        assertTrue(addToad);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeLuigiWithReplacement = system.removeMailman(eastHaven, luigi, toad);
        assertTrue(removeLuigiWithReplacement);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        westRidge = new GeographicalArea("WR001", "WestRidge");
        system.addArea(westRidge);
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        Mailman alpha = new Mailman("M012", "Alpha");
        Mailman beta = new Mailman("M013", "Beta");
        Mailman gamma = new Mailman("M014", "Gamma");
        system.addMailman(westRidge, alpha);
        system.addMailman(westRidge, beta);
        system.addMailman(westRidge, gamma);
        
        // Add 10 Inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = new Inhabitant("I10" + i, "Inhabitant" + i);
            system.addInhabitant(westRidge, inhabitants[i]);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCounter = 1;
        
        // 10 items for Alpha
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter("L50" + mailCounter, inhabitants[i], "Subject" + mailCounter, "Body" + mailCounter);
            westRidge.internalAddRegisteredMail(letter);
            assertTrue(system.assignMailToMailman(letter, alpha));
            mailCounter++;
        }
        
        // 10 items for Beta
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel("P50" + mailCounter, inhabitants[i], i * 0.7, "Description" + mailCounter);
            westRidge.internalAddRegisteredMail(parcel);
            assertTrue(system.assignMailToMailman(parcel, beta));
            mailCounter++;
        }
        
        // 10 items for Gamma
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter("L50" + mailCounter, inhabitants[i], "Subject" + mailCounter, "Body" + mailCounter);
            westRidge.internalAddRegisteredMail(letter);
            assertTrue(system.assignMailToMailman(letter, gamma));
            mailCounter++;
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean firstResult = system.removeMailman(westRidge, alpha, beta);
        assertTrue(firstResult);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean secondResult = system.removeMailman(westRidge, beta, gamma);
        assertTrue(secondResult);
        
        // Verifications:
        // - Gamma ends with all 30 deliveries
        List<DeliveryInfo> finalDeliveries = system.getDeliveriesByArea(westRidge);
        int gammaDeliveries = 0;
        
        for (DeliveryInfo delivery : finalDeliveries) {
            if (delivery.getMailman().equals(gamma)) {
                gammaDeliveries++;
            }
        }
        
        assertEquals(30, gammaDeliveries);
    }
}