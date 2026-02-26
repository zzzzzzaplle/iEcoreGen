import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    private House house;
    
    @Before
    public void setUp() {
        house = new House();
    }
    
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
        List<String> petNames = homeA.retrievePetNames();
        assertEquals("HomeA should contain 1 pet", 1, petNames.size());
        assertTrue("HomeA should contain Rex", petNames.contains("Rex"));
        assertEquals("Rex should be assigned to HomeA", homeA, rex.getHouse());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInAnotherHouse() {
        // SetUp: Create House "HomeB" and "HomeC", add Cat "Oliver" to HomeB
        House homeB = new House();
        House homeC = new House();
        Cat oliver = new Cat();
        oliver.setName("Oliver");
        homeB.addPet(oliver); // Oliver now belongs to HomeB
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet that already belongs to another house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        assertEquals("HomeC should remain empty", 0, homeC.getPetCount());
        assertEquals("Oliver should still belong to HomeB", homeB, oliver.getHouse());
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD" and add Dog "Rocky" to HomeD
        House homeD = new House();
        Dog rocky = new Dog();
        rocky.setName("Rocky");
        homeD.addPet(rocky); // First addition
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify Rocky is still only in HomeD once
        assertEquals("HomeD should contain only 1 pet", 1, homeD.getPetCount());
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
        assertTrue("Adding first unique pet should return true", result1);
        assertTrue("Adding second unique pet should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        assertEquals("HomeE should contain 2 pets", 2, homeE.getPetCount());
        List<String> petNames = homeE.retrievePetNames();
        assertTrue("HomeE should contain Bella", petNames.contains("Bella"));
        assertTrue("HomeE should contain Lucy", petNames.contains("Lucy"));
        assertEquals("Bella should be assigned to HomeE", homeE, bella.getHouse());
        assertEquals("Lucy should be assigned to HomeE", homeE, lucy.getHouse());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF" and Dog with null name
        House homeF = new House();
        Dog nullNameDog = new Dog();
        nullNameDog.setName(null); // Explicitly set name to null
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNameDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify house remains empty
        assertEquals("HomeF should remain empty", 0, homeF.getPetCount());
        assertNull("Pet with null name should not be assigned to any house", nullNameDog.getHouse());
    }
}