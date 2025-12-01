import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_AddNewPetToEmptyHouse() {
        // SetUp: Create House "HomeA" and Dog "Rex" (no house assigned)
        House homeA = new House();
        Dog rex = new Dog("Rex");
        
        // Action: Add Rex to HomeA
        boolean result = homeA.addPet(rex);
        
        // Expected Output: true
        assertTrue("Adding new pet to empty house should return true", result);
        
        // Post-Condition: HomeA contains Rex
        assertTrue("HomeA should contain Rex", homeA.getPets().contains(rex));
        assertEquals("HomeA should have exactly 1 pet", 1, homeA.getPetCount());
        assertEquals("Rex should be living in HomeA", homeA, rex.getLived());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInTheHouse() {
        // SetUp: Create House "HomeB" and "HomeC", add Cat "Oliver" to HomeB
        House homeB = new House();
        House homeC = new House();
        Cat oliver = new Cat("Oliver");
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet that already belongs to another house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        assertFalse("HomeC should not contain Oliver", homeC.getPets().contains(oliver));
        assertEquals("HomeC should have 0 pets", 0, homeC.getPetCount());
        assertEquals("Oliver should still be living in HomeB", homeB, oliver.getLived());
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD" and add Dog "Rocky" to HomeD
        House homeD = new House();
        Dog rocky = new Dog("Rocky");
        homeD.addPet(rocky);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify HomeD still has only one instance of Rocky
        assertEquals("HomeD should still have only 1 pet", 1, homeD.getPetCount());
        int rockyCount = 0;
        for (Pet pet : homeD.getPets()) {
            if (pet == rocky) {
                rockyCount++;
            }
        }
        assertEquals("Rocky should appear only once in HomeD", 1, rockyCount);
    }
    
    @Test
    public void testCase4_AddMultipleUniquePets() {
        // SetUp: Create House "HomeE", Dog "Bella" and Cat "Lucy" (no houses)
        House homeE = new House();
        Dog bella = new Dog("Bella");
        Cat lucy = new Cat("Lucy");
        
        // Action: Add Bella to HomeE → true, Add Lucy to HomeE → true
        boolean result1 = homeE.addPet(bella);
        boolean result2 = homeE.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue("Adding Bella to HomeE should return true", result1);
        assertTrue("Adding Lucy to HomeE should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        assertTrue("HomeE should contain Bella", homeE.getPets().contains(bella));
        assertTrue("HomeE should contain Lucy", homeE.getPets().contains(lucy));
        assertEquals("HomeE should have exactly 2 pets", 2, homeE.getPetCount());
        assertEquals("Bella should be living in HomeE", homeE, bella.getLived());
        assertEquals("Lucy should be living in HomeE", homeE, lucy.getLived());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF" and Dog with null name
        House homeF = new House();
        Dog nullNamedDog = new Dog();
        // Use reflection or direct field access to set name to null
        // Since setName() validates input, we need to bypass it
        nullNamedDog.setLived(null); // Ensure no house initially
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify HomeF is still empty
        assertEquals("HomeF should have 0 pets", 0, homeF.getPetCount());
        assertNull("Null-named dog should not belong to any house", nullNamedDog.getLived());
    }
}