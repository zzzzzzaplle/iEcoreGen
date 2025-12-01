package edu.postal.postal5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.postal.PostalFactory;
import edu.postal.PostalPackage;
import edu.postal.GeographicalArea;
import edu.postal.Mailman;
import edu.postal.Inhabitant;
import edu.postal.Letter;
import edu.postal.Parcel;
import edu.postal.RegisteredMail;
import org.eclipse.emf.common.util.EList;

public class CR4Test {
    
    private PostalFactory factory;
    private GeographicalArea area;
    
    @Before
    public void setUp() {
        // Initialize the factory and create test objects
        factory = PostalFactory.eINSTANCE;
        area = factory.createGeographicalArea();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp
        // Create Mailmen
        Mailman alice = factory.createMailman();
        Mailman bob = factory.createMailman();
        Mailman charlie = factory.createMailman();
        
        // Add mailmen to area
        area.addMailman(alice);
        area.addMailman(bob);
        area.addMailman(charlie);
        
        // Create Inhabitants
        Inhabitant david = factory.createInhabitant();
        Inhabitant eve = factory.createInhabitant();
        Inhabitant frank = factory.createInhabitant();
        
        // Add inhabitants to area
        area.addInhabitant(david);
        area.addInhabitant(eve);
        area.addInhabitant(frank);
        
        // Create mail items
        Letter letter1 = factory.createLetter();
        Parcel parcel1 = factory.createParcel();
        Letter letter2 = factory.createLetter();
        Parcel parcel2 = factory.createParcel();
        Letter letter3 = factory.createLetter();
        
        // Set addressees
        letter1.setAddressee(david);
        parcel1.setAddressee(eve);
        letter2.setAddressee(frank);
        parcel2.setAddressee(eve);
        letter3.setAddressee(david);
        
        // Add mail to area
        area.getAllMails().add(letter1);
        area.getAllMails().add(parcel1);
        area.getAllMails().add(letter2);
        area.getAllMails().add(parcel2);
        area.getAllMails().add(letter3);
        
        // Assign carriers
        letter1.setCarrier(alice);
        parcel1.setCarrier(bob);
        letter2.setCarrier(charlie);
        parcel2.setCarrier(alice);
        letter3.setCarrier(bob);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = area.removeMailman(alice, bob);
        
        // Expected Output: true
        assertTrue("Alice should be successfully removed", result);
        
        // Verifications
        // Alice removed
        assertFalse("Alice should be removed from mailmen", area.getMailmen().contains(alice));
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        assertEquals("Letter1 should be reassigned to Bob", bob, letter1.getCarrier());
        assertEquals("Parcel2 should be reassigned to Bob", bob, parcel2.getCarrier());
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertEquals("Parcel1 should still be assigned to Bob", bob, parcel1.getCarrier());
        assertEquals("Letter3 should still be assigned to Bob", bob, letter3.getCarrier());
        
        // Charlie's assignments unchanged (now has 1 item)
        assertEquals("Letter2 should still be assigned to Charlie", charlie, letter2.getCarrier());
        
        // Verify Bob now has 4 deliveries
        EList<RegisteredMail> bobsDeliveries = area.listRegisteredMail(bob);
        assertNotNull("Bob should have deliveries", bobsDeliveries);
        assertEquals("Bob should have 4 deliveries", 4, bobsDeliveries.size());
        
        // Verify Charlie has 1 delivery
        EList<RegisteredMail> charliesDeliveries = area.listRegisteredMail(charlie);
        assertNotNull("Charlie should have deliveries", charliesDeliveries);
        assertEquals("Charlie should have 1 delivery", 1, charliesDeliveries.size());
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp
        // Create Mailmen
        Mailman xavier = factory.createMailman();
        Mailman yvonne = factory.createMailman();
        Mailman zack = factory.createMailman();
        
        // Add mailmen to area
        area.addMailman(xavier);
        area.addMailman(yvonne);
        area.addMailman(zack);
        
        // Create Inhabitant
        Inhabitant walter = factory.createInhabitant();
        area.addInhabitant(walter);
        
        // Create and assign 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = factory.createLetter();
            letter.setAddressee(walter);
            letter.setCarrier(xavier);
            area.getAllMails().add(letter);
        }
        
        // Create and assign 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = factory.createParcel();
            parcel.setAddressee(walter);
            parcel.setCarrier(yvonne);
            area.getAllMails().add(parcel);
        }
        
        // Create and assign 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = factory.createLetter();
            letter.setAddressee(walter);
            letter.setCarrier(zack);
            area.getAllMails().add(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = area.removeMailman(yvonne, xavier);
        
        // Expected Output: true
        assertTrue("Yvonne should be successfully removed", result1);
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = area.removeMailman(xavier, zack);
        
        // Expected Output: true
        assertTrue("Xavier should be successfully removed", result2);
        
        // Verifications: Zack remains with all 10 deliveries
        EList<RegisteredMail> zacksDeliveries = area.listRegisteredMail(zack);
        assertNotNull("Zack should have deliveries", zacksDeliveries);
        assertEquals("Zack should have all 10 deliveries", 10, zacksDeliveries.size());
        
        // Verify only Zack remains
        assertEquals("Only one mailman should remain", 1, area.getMailmen().size());
        assertTrue("Zack should be the remaining mailman", area.getMailmen().contains(zack));
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp
        // Create Mailmen
        Mailman paul = factory.createMailman();
        Mailman quinn = factory.createMailman();
        
        // Add mailmen to area
        area.addMailman(paul);
        area.addMailman(quinn);
        
        // Create Inhabitant
        Inhabitant rachel = factory.createInhabitant();
        area.addInhabitant(rachel);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = factory.createLetter();
        letter1.setAddressee(rachel);
        letter1.setCarrier(paul);
        area.getAllMails().add(letter1);
        
        // Actions & Expected Outputs:
        // 1. Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = area.removeMailman(paul, quinn);
        assertTrue("Paul should be successfully removed", result1);
        
        // 2. Attempt remove Quinn (last mailman) → false
        boolean result2 = area.removeMailman(quinn, paul); // paul is no longer in area, but method should fail due to last mailman constraint
        assertFalse("Should not be able to remove last mailman", result2);
        
        // Verify Quinn still exists
        assertTrue("Quinn should still be in mailmen", area.getMailmen().contains(quinn));
        assertEquals("Only one mailman should remain", 1, area.getMailmen().size());
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp
        // Create Mailmen
        Mailman mario = factory.createMailman();
        Mailman luigi = factory.createMailman();
        
        // Add mailmen to area
        area.addMailman(mario);
        area.addMailman(luigi);
        
        // Create Inhabitants
        Inhabitant peach = factory.createInhabitant();
        Inhabitant bowser = factory.createInhabitant();
        
        // Add inhabitants to area
        area.addInhabitant(peach);
        area.addInhabitant(bowser);
        
        // Create 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = factory.createLetter();
            letter.setAddressee(peach);
            letter.setCarrier(mario);
            area.getAllMails().add(letter);
        }
        
        // Create 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = factory.createParcel();
            parcel.setAddressee(bowser);
            parcel.setCarrier(luigi);
            area.getAllMails().add(parcel);
        }
        
        // Procedure:
        // 1. Add duplicate "Mario" → false
        boolean addDuplicateResult = area.addMailman(mario);
        assertFalse("Should not be able to add duplicate mailman", addDuplicateResult);
        
        // 2. Remove Mario (specify Luigi) → true
        boolean removeMarioResult = area.removeMailman(mario, luigi);
        assertTrue("Mario should be successfully removed", removeMarioResult);
        
        // 3. Verify Luigi now has 15 deliveries
        EList<RegisteredMail> luigisDeliveries = area.listRegisteredMail(luigi);
        assertNotNull("Luigi should have deliveries", luigisDeliveries);
        assertEquals("Luigi should have 15 deliveries", 15, luigisDeliveries.size());
        
        // 4. Attempt remove Luigi → false (last mailman)
        boolean removeLuigiResult1 = area.removeMailman(luigi, mario); // mario is no longer in area
        assertFalse("Should not be able to remove last mailman", removeLuigiResult1);
        
        // 5. Add "Toad" → true
        Mailman toad = factory.createMailman();
        boolean addToadResult = area.addMailman(toad);
        assertTrue("Toad should be successfully added", addToadResult);
        
        // 6. Remove Luigi (specify Toad) → true
        boolean removeLuigiResult2 = area.removeMailman(luigi, toad);
        assertTrue("Luigi should be successfully removed", removeLuigiResult2);
        
        // Verify final state
        assertEquals("Only one mailman should remain", 1, area.getMailmen().size());
        assertTrue("Toad should be the remaining mailman", area.getMailmen().contains(toad));
        
        // Verify Toad has all 15 deliveries
        EList<RegisteredMail> toadsDeliveries = area.listRegisteredMail(toad);
        assertNotNull("Toad should have deliveries", toadsDeliveries);
        assertEquals("Toad should have all 15 deliveries", 15, toadsDeliveries.size());
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp
        // Create Mailmen
        Mailman alpha = factory.createMailman();
        Mailman beta = factory.createMailman();
        Mailman gamma = factory.createMailman();
        
        // Add mailmen to area
        area.addMailman(alpha);
        area.addMailman(beta);
        area.addMailman(gamma);
        
        // Create 10 Inhabitants
        Inhabitant[] inhabitants = new Inhabitant[10];
        for (int i = 0; i < 10; i++) {
            inhabitants[i] = factory.createInhabitant();
            area.addInhabitant(inhabitants[i]);
        }
        
        // Create 30 mail items (10 each mailman)
        int inhabitantIndex = 0;
        
        // 10 items for Alpha
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = factory.createLetter();
                letter.setAddressee(inhabitants[inhabitantIndex % 10]);
                letter.setCarrier(alpha);
                area.getAllMails().add(letter);
            } else {
                Parcel parcel = factory.createParcel();
                parcel.setAddressee(inhabitants[inhabitantIndex % 10]);
                parcel.setCarrier(alpha);
                area.getAllMails().add(parcel);
            }
            inhabitantIndex++;
        }
        
        // 10 items for Beta
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = factory.createLetter();
                letter.setAddressee(inhabitants[inhabitantIndex % 10]);
                letter.setCarrier(beta);
                area.getAllMails().add(letter);
            } else {
                Parcel parcel = factory.createParcel();
                parcel.setAddressee(inhabitants[inhabitantIndex % 10]);
                parcel.setCarrier(beta);
                area.getAllMails().add(parcel);
            }
            inhabitantIndex++;
        }
        
        // 10 items for Gamma
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Letter letter = factory.createLetter();
                letter.setAddressee(inhabitants[inhabitantIndex % 10]);
                letter.setCarrier(gamma);
                area.getAllMails().add(letter);
            } else {
                Parcel parcel = factory.createParcel();
                parcel.setAddressee(inhabitants[inhabitantIndex % 10]);
                parcel.setCarrier(gamma);
                area.getAllMails().add(parcel);
            }
            inhabitantIndex++;
        }
        
        // Action:
        // 1. Remove Alpha (specify Beta) → true
        boolean result1 = area.removeMailman(alpha, beta);
        assertTrue("Alpha should be successfully removed", result1);
        
        // 2. Remove Beta (specify Gamma) → true
        boolean result2 = area.removeMailman(beta, gamma);
        assertTrue("Beta should be successfully removed", result2);
        
        // Verifications:
        // Gamma ends with all 30 deliveries
        EList<RegisteredMail> gammasDeliveries = area.listRegisteredMail(gamma);
        assertNotNull("Gamma should have deliveries", gammasDeliveries);
        assertEquals("Gamma should have all 30 deliveries", 30, gammasDeliveries.size());
        
        // Verify only Gamma remains
        assertEquals("Only one mailman should remain", 1, area.getMailmen().size());
        assertTrue("Gamma should be the remaining mailman", area.getMailmen().contains(gamma));
        
        // Verify all mail items still exist
        assertEquals("All 30 mail items should still exist", 30, area.getAllMails().size());
    }
}