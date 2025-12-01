import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private MailManagementSystem system;
    
    @Before
    public void setUp() {
        system = new MailManagementSystem();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        GeographicalArea area = new GeographicalArea("CentralDistrict");
        system.addGeographicalArea(area);
        
        // Add Mailmen ["Alice", "Bob", "Charlie"]
        Mailman alice = new Mailman("Alice");
        Mailman bob = new Mailman("Bob");
        Mailman charlie = new Mailman("Charlie");
        area.addMailman(alice);
        area.addMailman(bob);
        area.addMailman(charlie);
        
        // Add Inhabitants ["David", "Eve", "Frank"]
        Inhabitant david = new Inhabitant("David");
        Inhabitant eve = new Inhabitant("Eve");
        Inhabitant frank = new Inhabitant("Frank");
        area.addInhabitant(david);
        area.addInhabitant(eve);
        area.addInhabitant(frank);
        
        // Create and assign mail items
        Letter letter1 = new Letter("L1", david);
        area.assignMailToMailman(letter1, alice);
        
        Parcel parcel1 = new Parcel("P1", eve);
        area.assignMailToMailman(parcel1, bob);
        
        Letter letter2 = new Letter("L2", frank);
        area.assignMailToMailman(letter2, charlie);
        
        Parcel parcel2 = new Parcel("P2", eve);
        area.assignMailToMailman(parcel2, alice);
        
        Letter letter3 = new Letter("L3", david);
        area.assignMailToMailman(letter3, bob);
        
        // Add mail to inhabitants
        david.addRegisteredMail(letter1);
        david.addRegisteredMail(letter3);
        eve.addRegisteredMail(parcel1);
        eve.addRegisteredMail(parcel2);
        frank.addRegisteredMail(letter2);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = area.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Alice should be successfully removed", result);
        
        // Verifications
        assertFalse("Alice should be removed from mailmen list", area.getMailmen().contains(alice));
        
        // Check Bob's assignments - should have 4 items (original 2 + 2 from Alice)
        int bobDeliveryCount = 0;
        for (RegisteredMail mail : area.getDeliveries()) {
            if (mail.getAssignedMailman().equals(bob)) {
                bobDeliveryCount++;
            }
        }
        assertEquals("Bob should have 4 deliveries after reassignment", 4, bobDeliveryCount);
        
        // Check Charlie's assignments - should have 1 item unchanged
        int charlieDeliveryCount = 0;
        for (RegisteredMail mail : area.getDeliveries()) {
            if (mail.getAssignedMailman().equals(charlie)) {
                charlieDeliveryCount++;
            }
        }
        assertEquals("Charlie should have 1 delivery unchanged", 1, charlieDeliveryCount);
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        GeographicalArea area = new GeographicalArea("NorthQuarter");
        system.addGeographicalArea(area);
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        Mailman xavier = new Mailman("Xavier");
        Mailman yvonne = new Mailman("Yvonne");
        Mailman zack = new Mailman("Zack");
        area.addMailman(xavier);
        area.addMailman(yvonne);
        area.addMailman(zack);
        
        // Add Inhabitant "Walter"
        Inhabitant walter = new Inhabitant("Walter");
        area.addInhabitant(walter);
        
        // Create and assign: 5 Letters (Xavier→Walter), 3 Parcels (Yvonne→Walter), 2 Letters (Zack→Walter)
        List<RegisteredMail> xavierMails = new ArrayList<>();
        List<RegisteredMail> yvonneMails = new ArrayList<>();
        List<RegisteredMail> zackMails = new ArrayList<>();
        
        for (int i = 1; i <= 5; i++) {
            Letter letter = new Letter("XL" + i, walter);
            area.assignMailToMailman(letter, xavier);
            walter.addRegisteredMail(letter);
            xavierMails.add(letter);
        }
        
        for (int i = 1; i <= 3; i++) {
            Parcel parcel = new Parcel("YP" + i, walter);
            area.assignMailToMailman(parcel, yvonne);
            walter.addRegisteredMail(parcel);
            yvonneMails.add(parcel);
        }
        
        for (int i = 1; i <= 2; i++) {
            Letter letter = new Letter("ZL" + i, walter);
            area.assignMailToMailman(letter, zack);
            walter.addRegisteredMail(letter);
            zackMails.add(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean firstRemoval = area.removeMailman(yvonne, xavier);
        assertTrue("First removal (Yvonne) should be successful", firstRemoval);
        
        // Verify after first removal: 3 parcels moved to Xavier (now has 8 items)
        int xavierCountAfterFirst = 0;
        for (RegisteredMail mail : area.getDeliveries()) {
            if (mail.getAssignedMailman().equals(xavier)) {
                xavierCountAfterFirst++;
            }
        }
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xavierCountAfterFirst);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean secondRemoval = area.removeMailman(xavier, zack);
        assertTrue("Second removal (Xavier) should be successful", secondRemoval);
        
        // Verify final state: Only Zack remains with all 10 deliveries
        assertEquals("Only one mailman should remain", 1, area.getMailmen().size());
        assertTrue("Zack should be the remaining mailman", area.getMailmen().contains(zack));
        
        int zackFinalCount = 0;
        for (RegisteredMail mail : area.getDeliveries()) {
            if (mail.getAssignedMailman().equals(zack)) {
                zackFinalCount++;
            }
        }
        assertEquals("Zack should have all 10 deliveries in final state", 10, zackFinalCount);
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        GeographicalArea area = new GeographicalArea("SouthEnd");
        system.addGeographicalArea(area);
        
        // Add Mailmen ["Paul", "Quinn"]
        Mailman paul = new Mailman("Paul");
        Mailman quinn = new Mailman("Quinn");
        area.addMailman(paul);
        area.addMailman(quinn);
        
        // Add Inhabitant "Rachel"
        Inhabitant rachel = new Inhabitant("Rachel");
        area.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter("L1", rachel);
        area.assignMailToMailman(letter1, paul);
        rachel.addRegisteredMail(letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean firstRemoval = area.removeMailman(paul, quinn);
        assertTrue("Removing Paul with Quinn as replacement should succeed", firstRemoval);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean secondRemoval = area.removeMailman(quinn, new Mailman("Temp"));
        assertFalse("Removing last mailman should fail", secondRemoval);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        GeographicalArea area = new GeographicalArea("EastHaven");
        system.addGeographicalArea(area);
        
        // Add Mailmen ["Mario", "Luigi"]
        Mailman mario = new Mailman("Mario");
        Mailman luigi = new Mailman("Luigi");
        area.addMailman(mario);
        area.addMailman(luigi);
        
        // Add Inhabitants ["Peach", "Bowser"]
        Inhabitant peach = new Inhabitant("Peach");
        Inhabitant bowser = new Inhabitant("Bowser");
        area.addInhabitant(peach);
        area.addInhabitant(bowser);
        
        // Create: 10 Letters (Mario→Peach), 5 Parcels (Luigi→Bowser)
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter("ML" + i, peach);
            area.assignMailToMailman(letter, mario);
            peach.addRegisteredMail(letter);
        }
        
        for (int i = 1; i <= 5; i++) {
            Parcel parcel = new Parcel("LP" + i, bowser);
            area.assignMailToMailman(parcel, luigi);
            bowser.addRegisteredMail(parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean duplicateAdd = area.addMailman(mario);
        assertFalse("Adding duplicate mailman should fail", duplicateAdd);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean firstRemoval = area.removeMailman(mario, luigi);
        assertTrue("Removing Mario with Luigi as replacement should succeed", firstRemoval);
        
        // 3. Verify Luigi now has 5 deliveries
        int luigiCount = 0;
        for (RegisteredMail mail : area.getDeliveries()) {
            if (mail.getAssignedMailman().equals(luigi)) {
                luigiCount++;
            }
        }
        assertEquals("Luigi should have 15 deliveries after reassignment", 15, luigiCount);
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean secondRemoval = area.removeMailman(luigi, new Mailman("Temp"));
        assertFalse("Removing last mailman should fail", secondRemoval);
        
        // 5. Add "Toad" → true
        Mailman toad = new Mailman("Toad");
        boolean addToad = area.addMailman(toad);
        assertTrue("Adding Toad should succeed", addToad);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean thirdRemoval = area.removeMailman(luigi, toad);
        assertTrue("Removing Luigi with Toad as replacement should succeed", thirdRemoval);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        GeographicalArea area = new GeographicalArea("WestRidge");
        system.addGeographicalArea(area);
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        Mailman alpha = new Mailman("Alpha");
        Mailman beta = new Mailman("Beta");
        Mailman gamma = new Mailman("Gamma");
        area.addMailman(alpha);
        area.addMailman(beta);
        area.addMailman(gamma);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant("Inhabitant" + i);
        area.addInhabitant(inhabitant);
            inhabitants.add(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int mailCounter = 1;
        
        // 10 for Alpha
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter("A" + mailCounter, inhabitants.get(i % 10));
            area.assignMailToMailman(letter, alpha);
            inhabitants.get(i % 10).addRegisteredMail(letter);
            mailCounter++;
        }
        
        // 10 for Beta
        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel("B" + mailCounter, inhabitants.get(i % 10));
            area.assignMailToMailman(parcel, beta);
            inhabitants.get(i % 10).addRegisteredMail(parcel);
            mailCounter++;
        }
        
        // 10 for Gamma
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter("G" + mailCounter, inhabitants.get(i % 10));
            area.assignMailToMailman(letter, gamma);
            inhabitants.get(i % 10).addRegisteredMail(letter);
            mailCounter++;
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean firstRemoval = area.removeMailman(alpha, beta);
        assertTrue("First removal (Alpha) should be successful", firstRemoval);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean secondRemoval = area.removeMailman(beta, gamma);
        assertTrue("Second removal (Beta) should be successful", secondRemoval);
        
        // Verifications:
        // Gamma ends with all 30 deliveries
        assertEquals("Only one mailman should remain", 1, area.getMailmen().size());
        assertTrue("Gamma should be the remaining mailman", area.getMailmen().contains(gamma));
        
        int gammaFinalCount = 0;
        for (RegisteredMail mail : area.getDeliveries()) {
            if (mail.getAssignedMailman().equals(gamma)) {
                gammaFinalCount++;
            }
        }
        assertEquals("Gamma should have all 30 deliveries in final state", 30, gammaFinalCount);
    }
}