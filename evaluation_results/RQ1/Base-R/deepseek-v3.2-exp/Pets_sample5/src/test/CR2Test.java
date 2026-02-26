import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_AddNewPetToEmptyHouse() {
        // SetUp: Create House "HomeA" and Dog "Rex" (no house assigned)
        House homeA = new House();
        Dog rex = new Dog();
        rex.setName("Rex");
        
        // Action: Add Rex to HomeA
        boolean result = homeA.addPet(rex);
        
        // Expected Output: true
        assertTrue("Adding new pet to empty house should return true", result);
        
        // Post-Condition: HomeA contains Rex
        assertTrue("HomeA should contain Rex", homeA.getPets().contains(rex));
        assertEquals("HomeA should have exactly 1 pet", 1, homeA.countPets());
        assertEquals("Rex's house should be HomeA", homeA, rex.getHouse());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInAnotherHouse() {
        // SetUp: Create House "HomeB" and "HomeC", add Cat "Oliver" to HomeB
        House homeB = new House();
        House homeC = new House();
        Cat oliver = new Cat();
        oliver.setName("Oliver");
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet already in another house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        assertFalse("HomeC should not contain Oliver", homeC.getPets().contains(oliver));
        assertEquals("HomeC should have 0 pets", 0, homeC.countPets());
        assertEquals("Oliver should still belong to HomeB", homeB, oliver.getHouse());
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD" and add Dog "Rocky" to HomeD
        House homeD = new House();
        Dog rocky = new Dog();
        rocky.setName("Rocky");
        homeD.addPet(rocky);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify pet is still in the house only once
        assertEquals("HomeD should have exactly 1 pet", 1, homeD.countPets());
        assertEquals("Rocky should still belong to HomeD", homeD, rocky.getHouse());
    }
    
    @Test
    public void testCase4_AddMultipleUniquePets() {
        // SetUp: Create House "HomeE", Dog "Bella" and Cat "Lucy" (no houses)
        House homeE = new House();
        Dog bella = new Dog();
        bella.setName("Bella");
        Cat lucy = new Cat();
        lucy.setName("Lucy");
        
        // Action: Add Bella to HomeE → true, Add Lucy to HomeE → true
        boolean result1 = homeE.addPet(bella);
        boolean result2 = homeE.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue("Adding Bella should return true", result1);
        assertTrue("Adding Lucy should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        assertTrue("HomeE should contain Bella", homeE.getPets().contains(bella));
        assertTrue("HomeE should contain Lucy", homeE.getPets().contains(lucy));
        assertEquals("HomeE should have exactly 2 pets", 2, homeE.countPets());
        assertEquals("Bella's house should be HomeE", homeE, bella.getHouse());
        assertEquals("Lucy's house should be HomeE", homeE, lucy.getHouse());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF" and Dog with null name
        House homeF = new House();
        Dog nullNamedDog = new Dog();
        nullNamedDog.setName(null); // Explicitly set name to null
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify house remains empty
        assertEquals("HomeF should have 0 pets", 0, homeF.countPets());
        assertNull("Null-named dog should not have a house assigned", nullNamedDog.getHouse());
    }
}