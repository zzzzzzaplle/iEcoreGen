import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private House house;
    
    @Before
    public void setUp() {
        // Reset house before each test
        house = null;
    }
    
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
        List<String> petNames = homeA.getAllPetNames();
        assertEquals("HomeA should contain exactly 1 pet", 1, petNames.size());
        assertTrue("HomeA should contain Rex", petNames.contains("Rex"));
        assertTrue("Rex should belong to HomeA", rex.belongsToHouse(homeA));
    }
    
    @Test
    public void testCase2_AddPetAlreadyInTheHouse() {
        // SetUp: Create House "HomeB" and "HomeC", Add Cat "Oliver" to HomeB
        House homeB = new House();
        House homeC = new House();
        Cat oliver = new Cat("Oliver");
        homeB.addPet(oliver); // Oliver now belongs to HomeB
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet that already belongs to another house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        List<String> homeCPetNames = homeC.getAllPetNames();
        assertTrue("HomeC should remain empty", homeCPetNames.isEmpty());
        assertTrue("Oliver should still belong to HomeB", oliver.belongsToHouse(homeB));
        assertFalse("Oliver should not belong to HomeC", oliver.belongsToHouse(homeC));
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD", Add Dog "Rocky" to HomeD
        House homeD = new House();
        Dog rocky = new Dog("Rocky");
        homeD.addPet(rocky); // First addition
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify HomeD still has only one pet (Rocky)
        List<String> petNames = homeD.getAllPetNames();
        assertEquals("HomeD should contain exactly 1 pet", 1, petNames.size());
        assertTrue("HomeD should still contain Rocky", petNames.contains("Rocky"));
    }
    
    @Test
    public void testCase4_AddMultipleUniquePets() {
        // SetUp: Create House "HomeE", Create Dog "Bella" and Cat "Lucy" (no houses)
        House homeE = new House();
        Dog bella = new Dog("Bella");
        Cat lucy = new Cat("Lucy");
        
        // Action: Add Bella to HomeE → true, Add Lucy to HomeE → true
        boolean result1 = homeE.addPet(bella);
        boolean result2 = homeE.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue("Adding Bella should return true", result1);
        assertTrue("Adding Lucy should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        List<String> petNames = homeE.getAllPetNames();
        assertEquals("HomeE should contain exactly 2 pets", 2, petNames.size());
        assertTrue("HomeE should contain Bella", petNames.contains("Bella"));
        assertTrue("HomeE should contain Lucy", petNames.contains("Lucy"));
        assertTrue("Bella should belong to HomeE", bella.belongsToHouse(homeE));
        assertTrue("Lucy should belong to HomeE", lucy.belongsToHouse(homeE));
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF", Create Dog with null name
        House homeF = new House();
        Dog nullNameDog = new Dog(null);
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNameDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify HomeF remains empty
        List<String> petNames = homeF.getAllPetNames();
        assertTrue("HomeF should remain empty", petNames.isEmpty());
    }
}