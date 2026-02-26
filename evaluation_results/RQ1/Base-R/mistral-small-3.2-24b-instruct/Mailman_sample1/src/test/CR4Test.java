import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    private MailDeliverySystem mailSystem;
    private GeographicalArea centralDistrict;
    private GeographicalArea northQuarter;
    private GeographicalArea southEnd;
    private GeographicalArea eastHaven;
    private GeographicalArea westRidge;
    
    @Before
    public void setUp() {
        mailSystem = new MailDeliverySystem();
    }
    
    @Test
    public void testCase1_ComplexMailmanRemovalWithMultipleReassignments() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        centralDistrict = new GeographicalArea("CentralDistrict");
        
        // Add Mailmen ["Alice", "Bob", "Charlie"]
        Mailman alice = new Mailman("Alice", centralDistrict);
        Mailman bob = new Mailman("Bob", centralDistrict);
        Mailman charlie = new Mailman("Charlie", centralDistrict);
        mailSystem.addMailman(alice, centralDistrict);
        mailSystem.addMailman(bob, centralDistrict);
        mailSystem.addMailman(charlie, centralDistrict);
        
        // Add Inhabitants ["David", "Eve", "Frank"]
        Inhabitant david = new Inhabitant("David", centralDistrict);
        Inhabitant eve = new Inhabitant("Eve", centralDistrict);
        Inhabitant frank = new Inhabitant("Frank", centralDistrict);
        mailSystem.addInhabitant(david, centralDistrict);
        mailSystem.addInhabitant(eve, centralDistrict);
        mailSystem.addInhabitant(frank, centralDistrict);
        
        // Create and assign mail items
        Letter letter1 = new Letter(david, null, centralDistrict);
        Parcel parcel1 = new Parcel(eve, null, centralDistrict);
        Letter letter2 = new Letter(frank, null, centralDistrict);
        Parcel parcel2 = new Parcel(eve, null, centralDistrict);
        Letter letter3 = new Letter(david, null, centralDistrict);
        
        mailSystem.assignMail(letter1, alice);
        mailSystem.assignMail(parcel1, bob);
        mailSystem.assignMail(letter2, charlie);
        mailSystem.assignMail(parcel2, alice);
        mailSystem.assignMail(letter3, bob);
        
        // Action: Remove Alice (specify Bob as replacement)
        boolean result = mailSystem.removeMailman(alice, bob, centralDistrict);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verifications
        // Alice removed
        assertFalse(centralDistrict.getMailmen().contains(alice));
        assertNull(alice.getGeographicalArea());
        
        // Letter1 and Parcel2 reassigned to Bob (now has 4 items)
        List<RegisteredMail> bobDeliveries = mailSystem.getDeliveriesForArea(centralDistrict);
        int bobCount = 0;
        for (RegisteredMail mail : bobDeliveries) {
            if (mail.getMailman().equals(bob)) {
                bobCount++;
            }
        }
        assertEquals(4, bobCount); // Original 2 (Parcel1, Letter3) + reassigned 2 (Letter1, Parcel2)
        
        // Bob's original deliveries (Parcel1, Letter3) unchanged
        assertTrue(bobDeliveries.contains(parcel1));
        assertTrue(bobDeliveries.contains(letter3));
        
        // Charlie's assignments unchanged (now has 1 item)
        List<RegisteredMail> charlieDeliveries = mailSystem.getDeliveriesForArea(centralDistrict);
        int charlieCount = 0;
        for (RegisteredMail mail : charlieDeliveries) {
            if (mail.getMailman().equals(charlie)) {
                charlieCount++;
            }
        }
        assertEquals(1, charlieCount);
        assertTrue(charlieDeliveries.contains(letter2));
    }
    
    @Test
    public void testCase2_CascadingMailmanRemovalWithDependencies() {
        // SetUp: Create GeographicalArea "NorthQuarter"
        northQuarter = new GeographicalArea("NorthQuarter");
        
        // Add Mailmen ["Xavier", "Yvonne", "Zack"]
        Mailman xavier = new Mailman("Xavier", northQuarter);
        Mailman yvonne = new Mailman("Yvonne", northQuarter);
        Mailman zack = new Mailman("Zack", northQuarter);
        mailSystem.addMailman(xavier, northQuarter);
        mailSystem.addMailman(yvonne, northQuarter);
        mailSystem.addMailman(zack, northQuarter);
        
        // Add Inhabitant "Walter"
        Inhabitant walter = new Inhabitant("Walter", northQuarter);
        mailSystem.addInhabitant(walter, northQuarter);
        
        // Create and assign mail items
        List<RegisteredMail> xavierMails = new ArrayList<>();
        List<RegisteredMail> yvonneMails = new ArrayList<>();
        List<RegisteredMail> zackMails = new ArrayList<>();
        
        // 5 Letters (Xavier→Walter)
        for (int i = 0; i < 5; i++) {
            Letter letter = new Letter(walter, null, northQuarter);
            mailSystem.assignMail(letter, xavier);
            xavierMails.add(letter);
        }
        
        // 3 Parcels (Yvonne→Walter)
        for (int i = 0; i < 3; i++) {
            Parcel parcel = new Parcel(walter, null, northQuarter);
            mailSystem.assignMail(parcel, yvonne);
            yvonneMails.add(parcel);
        }
        
        // 2 Letters (Zack→Walter)
        for (int i = 0; i < 2; i++) {
            Letter letter = new Letter(walter, null, northQuarter);
            mailSystem.assignMail(letter, zack);
            zackMails.add(letter);
        }
        
        // Action: Remove Yvonne (specify Xavier as replacement)
        boolean result1 = mailSystem.removeMailman(yvonne, xavier, northQuarter);
        
        // Expected Output: true
        assertTrue(result1);
        
        // After first removal: 3 parcels moved to Xavier (now has 8 items)
        List<RegisteredMail> deliveriesAfterFirst = mailSystem.getDeliveriesForArea(northQuarter);
        int xavierCount1 = 0;
        for (RegisteredMail mail : deliveriesAfterFirst) {
            if (mail.getMailman().equals(xavier)) {
                xavierCount1++;
            }
        }
        assertEquals(8, xavierCount1); // Original 5 + reassigned 3
        
        // Action: Remove Xavier (specify Zack as replacement)
        boolean result2 = mailSystem.removeMailman(xavier, zack, northQuarter);
        
        // Expected Output: true
        assertTrue(result2);
        
        // After second removal: 8 letters moved to Zack (now has 10 items)
        List<RegisteredMail> finalDeliveries = mailSystem.getDeliveriesForArea(northQuarter);
        int zackCount = 0;
        for (RegisteredMail mail : finalDeliveries) {
            if (mail.getMailman().equals(zack)) {
                zackCount++;
            }
        }
        assertEquals(10, zackCount); // Original 2 + reassigned 8
        
        // Final state: Only Zack remains with all 10 deliveries
        assertEquals(1, northQuarter.getMailmen().size());
        assertTrue(northQuarter.getMailmen().contains(zack));
        assertEquals(10, finalDeliveries.size());
    }
    
    @Test
    public void testCase3_FailedRemovalEdgeCases() {
        // SetUp: Create GeographicalArea "SouthEnd"
        southEnd = new GeographicalArea("SouthEnd");
        
        // Add Mailmen ["Paul", "Quinn"]
        Mailman paul = new Mailman("Paul", southEnd);
        Mailman quinn = new Mailman("Quinn", southEnd);
        mailSystem.addMailman(paul, southEnd);
        mailSystem.addMailman(quinn, southEnd);
        
        // Add Inhabitant "Rachel"
        Inhabitant rachel = new Inhabitant("Rachel", southEnd);
        mailSystem.addInhabitant(rachel, southEnd);
        
        // Create Letter1 (Paul→Rachel)
        Letter letter1 = new Letter(rachel, null, southEnd);
        mailSystem.assignMail(letter1, paul);
        
        // Action 1: Attempt remove Paul specifying Quinn → true (normal case)
        boolean result1 = mailSystem.removeMailman(paul, quinn, southEnd);
        assertTrue(result1);
        
        // Action 2: Attempt remove Quinn (last mailman) → false
        boolean result2 = mailSystem.removeMailman(quinn, null, southEnd);
        assertFalse(result2);
    }
    
    @Test
    public void testCase4_MixedSuccessfulAndFailedOperations() {
        // SetUp: Create GeographicalArea "EastHaven"
        eastHaven = new GeographicalArea("EastHaven");
        
        // Add Mailmen ["Mario", "Luigi"]
        Mailman mario = new Mailman("Mario", eastHaven);
        Mailman luigi = new Mailman("Luigi", eastHaven);
        mailSystem.addMailman(mario, eastHaven);
        mailSystem.addMailman(luigi, eastHaven);
        
        // Add Inhabitants ["Peach", "Bowser"]
        Inhabitant peach = new Inhabitant("Peach", eastHaven);
        Inhabitant bowser = new Inhabitant("Bowser", eastHaven);
        mailSystem.addInhabitant(peach, eastHaven);
        mailSystem.addInhabitant(bowser, eastHaven);
        
        // Create mail items
        List<RegisteredMail> marioMails = new ArrayList<>();
        List<RegisteredMail> luigiMails = new ArrayList<>();
        
        // 10 Letters (Mario→Peach)
        for (int i = 0; i < 10; i++) {
            Letter letter = new Letter(peach, null, eastHaven);
            mailSystem.assignMail(letter, mario);
            marioMails.add(letter);
        }
        
        // 5 Parcels (Luigi→Bowser)
        for (int i = 0; i < 5; i++) {
            Parcel parcel = new Parcel(bowser, null, eastHaven);
            mailSystem.assignMail(parcel, luigi);
            luigiMails.add(parcel);
        }
        
        // Procedure 1: Add duplicate "Mario" → false
        Mailman marioDuplicate = new Mailman("Mario", null);
        boolean result1 = mailSystem.addMailman(marioDuplicate, eastHaven);
        assertFalse(result1);
        
        // Procedure 2: Remove Mario (specify Luigi) → true
        boolean result2 = mailSystem.removeMailman(mario, luigi, eastHaven);
        assertTrue(result2);
        
        // Procedure 3: Verify Luigi now has 15 deliveries
        List<RegisteredMail> deliveriesAfterRemoval = mailSystem.getDeliveriesForArea(eastHaven);
        int luigiCount = 0;
        for (RegisteredMail mail : deliveriesAfterRemoval) {
            if (mail.getMailman().equals(luigi)) {
                luigiCount++;
            }
        }
        assertEquals(15, luigiCount); // Original 5 + reassigned 10
        
        // Procedure 4: Attempt remove Luigi → false (last mailman)
        boolean result3 = mailSystem.removeMailman(luigi, null, eastHaven);
        assertFalse(result3);
        
        // Procedure 5: Add "Toad" → true
        Mailman toad = new Mailman("Toad", null);
        boolean result4 = mailSystem.addMailman(toad, eastHaven);
        assertTrue(result4);
        
        // Procedure 6: Remove Luigi (specify Toad) → true
        boolean result5 = mailSystem.removeMailman(luigi, toad, eastHaven);
        assertTrue(result5);
    }
    
    @Test
    public void testCase5_StressTestWithMaximumReassignments() {
        // SetUp: Create GeographicalArea "WestRidge"
        westRidge = new GeographicalArea("WestRidge");
        
        // Add Mailmen ["Alpha", "Beta", "Gamma"]
        Mailman alpha = new Mailman("Alpha", westRidge);
        Mailman beta = new Mailman("Beta", westRidge);
        Mailman gamma = new Mailman("Gamma", westRidge);
        mailSystem.addMailman(alpha, westRidge);
        mailSystem.addMailman(beta, westRidge);
        mailSystem.addMailman(gamma, westRidge);
        
        // Add 10 Inhabitants
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Inhabitant inhabitant = new Inhabitant("Inhabitant" + i, westRidge);
            mailSystem.addInhabitant(inhabitant, westRidge);
            inhabitants.add(inhabitant);
        }
        
        // Create 30 mail items (10 each mailman)
        int inhabitantIndex = 0;
        for (int i = 0; i < 10; i++) {
            Letter letterAlpha = new Letter(inhabitants.get(inhabitantIndex), null, westRidge);
            mailSystem.assignMail(letterAlpha, alpha);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        for (int i = 0; i < 10; i++) {
            Parcel parcelBeta = new Parcel(inhabitants.get(inhabitantIndex), null, westRidge);
            mailSystem.assignMail(parcelBeta, beta);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        for (int i = 0; i < 10; i++) {
            Letter letterGamma = new Letter(inhabitants.get(inhabitantIndex), null, westRidge);
            mailSystem.assignMail(letterGamma, gamma);
            inhabitantIndex = (inhabitantIndex + 1) % 10;
        }
        
        // Action 1: Remove Alpha (specify Beta) → true
        boolean result1 = mailSystem.removeMailman(alpha, beta, westRidge);
        assertTrue(result1);
        
        // Action 2: Remove Beta (specify Gamma) → true
        boolean result2 = mailSystem.removeMailman(beta, gamma, westRidge);
        assertTrue(result2);
        
        // Verifications: Gamma ends with all 30 deliveries
        List<RegisteredMail> finalDeliveries = mailSystem.getDeliveriesForArea(westRidge);
        int gammaCount = 0;
        for (RegisteredMail mail : finalDeliveries) {
            if (mail.getMailman().equals(gamma)) {
                gammaCount++;
            }
        }
        assertEquals(30, gammaCount);
        assertEquals(1, westRidge.getMailmen().size());
        assertTrue(westRidge.getMailmen().contains(gamma));
    }
}