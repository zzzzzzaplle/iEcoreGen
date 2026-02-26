import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    private MailService mailService;
    
    @Before
    public void setUp() {
        mailService = new MailService();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        GeographicalArea area = new GeographicalArea("CD1", "CentralDistrict");
        mailService.addArea(area);
        
        // Add Mailmen ["Alice", "Bob", "Charlie"]
        Mailman alice = new Mailman("M1", "Alice", null);
        Mailman bob = new Mailman("M2", "Bob", null);
        Mailman charlie = new Mailman("M3", "Charlie", null);
        mailService.addMailman("CD1", alice);
        mailService.addMailman("CD1", bob);
        mailService.addMailman("CD1", charlie);
        
        // Add Inhabitants ["David", "Eve", "Frank"]
        Inhabitant david = new Inhabitant("I1", "David", null);
        Inhabitant eve = new Inhabitant("I2", "Eve", null);
        Inhabitant frank = new Inhabitant("I3", "Frank", null);
        mailService.addInhabitant("CD1", david);
        mailService.addInhabitant("CD1", eve);
        mailService.addInhabitant("CD1", frank);
        
        // Create and assign mail items
        Letter letter1 = new Letter("L1", david, "Subject1", "Content1");
        Parcel parcel1 = new Parcel("P1", eve, 1.5, "Description1");
        Letter letter2 = new Letter("L2", frank, "Subject2", "Content2");
        Parcel parcel2 = new Parcel("P2", eve, 2.0, "Description2");
        Letter letter3 = new Letter("L3", david, "Subject3", "Content3");
        
        mailService.registerMailItem(letter1);
        mailService.registerMailItem(parcel1);
        mailService.registerMailItem(letter2);
        mailService.registerMailItem(parcel2);
        mailService.registerMailItem(letter3);
        
        // Assign mail items to mailmen
        mailService.assignMailItemToMailman("L1", "M1"); // Alice→David
        mailService.assignMailItemToMailman("P1", "M2"); // Bob→Eve
        mailService.assignMailItemToMailman("L2", "M3"); // Charlie→Frank
        mailService.assignMailItemToMailman("P2", "M1"); // Alice→Eve
        mailService.assignMailItemToMailman("L3", "M2"); // Bob→David
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = mailService.removeMailman("CD1", "M1", "M2");
        
        // Expected Output: true
        assertTrue("Mailman removal should succeed", result);
        
        // Verifications
        // Alice removed
        assertNull("Alice should be removed", area.findMailmanById("M1"));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        MailItem letter1After = area.findMailItemById("L1");
        MailItem parcel2After = area.findMailItemById("P2");
        assertEquals("Letter1 should be reassigned to Bob", "M2", letter1After.getAssignedMailman().getId());
        assertEquals("Parcel2 should be reassigned to Bob", "M2", parcel2After.getAssignedMailman().getId());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        MailItem parcel1After = area.findMailItemById("P1");
        MailItem letter3After = area.findMailItemById("L3");
        assertEquals("Parcel1 should remain with Bob", "M2", parcel1After.getAssignedMailman().getId());
        assertEquals("Letter3 should remain with Bob", "M2", letter3After.getAssignedMailman().getId());
        
        // Count Bob's deliveries (should be 4)
        int bobDeliveries = 0;
        for (MailItem mi : area.getMailItems()) {
            if (mi.getAssignedMailman() != null && "M2".equals(mi.getAssignedMailman().getId())) {
                bobDeliveries++;
            }
        }
        assertEquals("Bob should have 4 deliveries", 4, bobDeliveries);
        
        // Charlie's assignments unchanged (now has 1 item)
        MailItem letter2After = area.findMailItemById("L2");
        assertEquals("Letter2 should remain with Charlie", "M3", letter2After.getAssignedMailman().getId());
        
        int charlieDeliveries = 0;
        for (MailItem mi : area.getMailItems()) {
            if (mi.getAssignedMailman() != null && "M3".equals(mi.getAssignedMailman().getId())) {
                charlieDeliveries++;
            }
        }
        assertEquals("Charlie should have 1 delivery", 1, charlieDeliveries);
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        GeographicalArea area = new GeographicalArea("NQ1", "NorthQuarter");
        mailService.addArea(area);
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        Mailman xavier = new Mailman("M1", "Xavier", null);
        Mailman yvonne = new Mailman("M2", "Yvonne", null);
        Mailman zack = new Mailman("M3", "Zack", null);
        mailService.addMailman("NQ1", xavier);
        mailService.addMailman("NQ1", yvonne);
        mailService.addMailman("NQ1", zack);
        
        // Add Inhabitant "Walter"
        Inhabitant walter = new Inhabitant("I1", "Walter", null);
        mailService.addInhabitant("NQ1", walter);
        
        // Create and assign mail items
        // 5 Letters (Xavier→Walter)
        for (int i = 1; i <= 5; i++) {
            Letter letter = new Letter("XL" + i, walter, "Subject" + i, "Content" + i);
            mailService.registerMailItem(letter);
            mailService.assignMailItemToMailman("XL" + i, "M1");
        }
        
        // 3 Parcels (Yvonne→Walter)
        for (int i = 1; i <= 3; i++) {
            Parcel parcel = new Parcel("YP" + i, walter, i * 0.5, "Desc" + i);
            mailService.registerMailItem(parcel);
            mailService.assignMailItemToMailman("YP" + i, "M2");
        }
        
        // 2 Letters (Zack→Walter)
        for (int i = 1; i <= 2; i++) {
            Letter letter = new Letter("ZL" + i, walter, "SubjectZ" + i, "ContentZ" + i);
            mailService.registerMailItem(letter);
            mailService.assignMailItemToMailman("ZL" + i, "M3");
        }
        
        // Action 1: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = mailService.removeMailman("NQ1", "M2", "M1");
        
        // Expected Output: true
        assertTrue("First removal should succeed", result1);
        
        // Verification after first removal: 3 parcels moved to Xavier (now has 8 items)
        int xavierDeliveriesAfterFirst = 0;
        for (MailItem mi : area.getMailItems()) {
            if (mi.getAssignedMailman() != null && "M1".equals(mi.getAssignedMailman().getId())) {
                xavierDeliveriesAfterFirst++;
            }
        }
        assertEquals("Xavier should have 8 deliveries after first removal", 8, xavierDeliveriesAfterFirst);
        
        // Action 2: Remove Xavier (specify Zack as replacement)
        boolean result2 = mailService.removeMailman("NQ1", "M1", "M3");
        
        // Expected Output: true
        assertTrue("Second removal should succeed", result2);
        
        // Verification after second removal: 8 letters moved to Zack (now has 10 items)
        int zackDeliveriesAfterSecond = 0;
        for (MailItem mi : area.getMailItems()) {
            if (mi.getAssignedMailman() != null && "M3".equals(mi.getAssignedMailman().getId())) {
                zackDeliveriesAfterSecond++;
            }
        }
        assertEquals("Zack should have 10 deliveries after second removal", 10, zackDeliveriesAfterSecond);
        
        // Final state: Only Zack remains with all 10 deliveries
        assertEquals("Only Zack should remain", 1, area.getMailmen().size());
        assertNotNull("Zack should exist", area.findMailmanById("M3"));
        assertEquals("Zack should have all 10 deliveries", 10, zackDeliveriesAfterSecond);
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        GeographicalArea area = new GeographicalArea("SE1", "SouthEnd");
        mailService.addArea(area);
        
        // Add Mailmen ["Paul", "Quinn"]
        Mailman paul = new Mailman("M1", "Paul", null);
        Mailman quinn = new Mailman("M2", "Quinn", null);
        mailService.addMailman("SE1", paul);
        mailService.addMailman("SE1", quinn);
        
        // Add Inhabitant "Rachel"
        Inhabitant rachel = new Inhabitant("I1", "Rachel", null);
        mailService.addInhabitant("SE1", rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter("L1", rachel, "Subject", "Content");
        mailService.registerMailItem(letter1);
        mailService.assignMailItemToMailman("L1", "M1");
        
        // Action 1: Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = mailService.removeMailman("SE1", "M1", "M2");
        
        // Expected Output: true
        assertTrue("First removal should succeed", result1);
        
        // Verification: Letter1 should be reassigned to Quinn
        MailItem letterAfter = area.findMailItemById("L1");
        assertEquals("Letter should be reassigned to Quinn", "M2", letterAfter.getAssignedMailman().getId());
        
        // Action 2: Attempt remove Quinn (last mailman) → false
        boolean result2 = mailService.removeMailman("SE1", "M2", "M1");
        
        // Expected Output: false
        assertFalse("Removal of last mailman should fail", result2);
        
        // Verification: Quinn should still exist
        assertNotNull("Quinn should still be present", area.findMailmanById("M2"));
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        GeographicalArea area = new GeographicalArea("EH1", "EastHaven");
        mailService.addArea(area);
        
        // Add Mailmen ["Mario", "Luigi"]
        Mailman mario = new Mailman("M1", "Mario", null);
        Mailman luigi = new Mailman("M2", "Luigi", null);
        mailService.addMailman("EH1", mario);
        mailService.addMailman("EH1", luigi);
        
        // Add Inhabitants ["Peach", "Bowser"]
        Inhabitant peach = new Inhabitant("I1", "Peach", null);
        Inhabitant bowser = new Inhabitant("I2", "Bowser", null);
        mailService.addInhabitant("EH1", peach);
        mailService.addInhabitant("EH1", bowser);
        
        // Create mail items
        // 10 Letters (Mario→Peach)
        for (int i = 1; i <= 10; i++) {
            Letter letter = new Letter("ML" + i, peach, "Subject" + i, "Content" + i);
            mailService.registerMailItem(letter);
            mailService.assignMailItemToMailman("ML" + i, "M1");
        }
        
        // 5 Parcels (Luigi→Bowser)
        for (int i = 1; i <= 5; i++) {
            Parcel parcel = new Parcel("LP" + i, bowser, i * 1.0, "Desc" + i);
            mailService.registerMailItem(parcel);
            mailService.assignMailItemToMailman("LP" + i, "M2");
        }
        
        // Procedure 1: Add duplicate "Mario" → false
        Mailman marioDuplicate = new Mailman("M1", "Mario", null);
        boolean addDuplicateResult = mailService.addMailman("EH1", marioDuplicate);
        
        // Expected Output: false
        assertFalse("Adding duplicate mailman should fail", addDuplicateResult);
        
        // Procedure 2: Remove Mario (specify Luigi) → true
        boolean removeMarioResult = mailService.removeMailman("EH1", "M1", "M2");
        
        // Expected Output: true
        assertTrue("Removing Mario should succeed", removeMarioResult);
        
        // Procedure 3: Verify Luigi now has 15 deliveries
        int luigiDeliveries = 0;
        for (MailItem mi : area.getMailItems()) {
            if (mi.getAssignedMailman() != null && "M2".equals(mi.getAssignedMailman().getId())) {
                luigiDeliveries++;
            }
        }
        assertEquals("Luigi should have 15 deliveries", 15, luigiDeliveries);
        
        // Procedure 4: Attempt remove Luigi → false (last mailman)
        boolean removeLuigiResult = mailService.removeMailman("EH1", "M2", "M1");
        
        // Expected Output: false
        assertFalse("Removing last mailman should fail", removeLuigiResult);
        
        // Procedure 5: Add "Toad" → true
        Mailman toad = new Mailman("M3", "Toad", null);
        boolean addToadResult = mailService.addMailman("EH1", toad);
        
        // Expected Output: true
        assertTrue("Adding Toad should succeed", addToadResult);
        
        // Procedure 6: Remove Luigi (specify Toad) → true
        boolean removeLuigiAgainResult = mailService.removeMailman("EH1", "M2", "M3");
        
        // Expected Output: true
        assertTrue("Removing Luigi with replacement should succeed", removeLuigiAgainResult);
        
        // Verification: Toad should have all 15 deliveries
        int toadDeliveries = 0;
        for (MailItem mi : area.getMailItems()) {
            if (mi.getAssignedMailman() != null && "M3".equals(mi.getAssignedMailman().getId())) {
                toadDeliveries++;
            }
        }
        assertEquals("Toad should have 15 deliveries", 15, toadDeliveries);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        GeographicalArea area = new GeographicalArea("WR1", "WestRidge");
        mailService.addArea(area);
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        Mailman alpha = new Mailman("M1", "Alpha", null);
        Mailman beta = new Mailman("M2", "Beta", null);
        Mailman gamma = new Mailman("M3", "Gamma", null);
        mailService.addMailman("WR1", alpha);
        mailService.addMailman("WR1", beta);
        mailService.addMailman("WR1", gamma);
        
        // Add 10 Inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 1; i <= 10; i++) {
            inhabitants[i-1] = new Inhabitant("I" + i, "Inhabitant" + i, null);
            mailService.addInhabitant("WR1", inhabitants[i-1]);
        }
        
        // Create 30 mail items (10 each mailman)
        int itemCount = 1;
        
        // 10 items for Alpha
        for (int i = 1; i <= 10; i++) {
            if (itemCount % 2 == 0) {
                Letter letter = new Letter("A" + itemCount, inhabitants[i-1], "SubjectA" + itemCount, "ContentA" + itemCount);
                mailService.registerMailItem(letter);
                mailService.assignMailItemToMailman("A" + itemCount, "M1");
            } else {
                Parcel parcel = new Parcel("A" + itemCount, inhabitants[i-1], itemCount * 0.5, "DescA" + itemCount);
                mailService.registerMailItem(parcel);
                mailService.assignMailItemToMailman("A" + itemCount, "M1");
            }
            itemCount++;
        }
        
        // 10 items for Beta
        for (int i = 1; i <= 10; i++) {
            if (itemCount % 2 == 0) {
                Letter letter = new Letter("B" + itemCount, inhabitants[i-1], "SubjectB" + itemCount, "ContentB" + itemCount);
                mailService.registerMailItem(letter);
                mailService.assignMailItemToMailman("B" + itemCount, "M2");
            } else {
                Parcel parcel = new Parcel("B" + itemCount, inhabitants[i-1], itemCount * 0.5, "DescB" + itemCount);
                mailService.registerMailItem(parcel);
                mailService.assignMailItemToMailman("B" + itemCount, "M2");
            }
            itemCount++;
        }
        
        // 10 items for Gamma
        for (int i = 1; i <= 10; i++) {
            if (itemCount % 2 == 0) {
                Letter letter = new Letter("G" + itemCount, inhabitants[i-1], "SubjectG" + itemCount, "ContentG" + itemCount);
                mailService.registerMailItem(letter);
                mailService.assignMailItemToMailman("G" + itemCount, "M3");
            } else {
                Parcel parcel = new Parcel("G" + itemCount, inhabitants[i-1], itemCount * 0.5, "DescG" + itemCount);
                mailService.registerMailItem(parcel);
                mailService.assignMailItemToMailman("G" + itemCount, "M3");
            }
            itemCount++;
        }
        
        // Action 1: Remove Alpha (specify Beta) → true
        boolean result1 = mailService.removeMailman("WR1", "M1", "M2");
        
        // Expected Output: true
        assertTrue("First removal should succeed", result1);
        
        // Action 2: Remove Beta (specify Gamma) → true
        boolean result2 = mailService.removeMailman("WR1", "M2", "M3");
        
        // Expected Output: true
        assertTrue("Second removal should succeed", result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        int gammaDeliveries = 0;
        for (MailItem mi : area.getMailItems()) {
            if (mi.getAssignedMailman() != null && "M3".equals(mi.getAssignedMailman().getId())) {
                gammaDeliveries++;
            }
        }
        assertEquals("Gamma should have all 30 deliveries", 30, gammaDeliveries);
        
        // Verify only Gamma remains
        assertEquals("Only Gamma should remain", 1, area.getMailmen().size());
        assertNotNull("Gamma should exist", area.findMailmanById("M3"));
    }
}