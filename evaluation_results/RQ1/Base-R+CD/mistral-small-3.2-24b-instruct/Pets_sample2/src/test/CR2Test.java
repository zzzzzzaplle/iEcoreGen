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
    public void testCase1_addNewPetToEmptyHouse() {
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
        assertEquals("House should contain exactly 1 pet", 1, petNames.size());
        assertTrue("House should contain Rex", petNames.contains("Rex"));
    }
    
    @Test
    public void testCase2_addPetAlreadyInTheHouse() {
        // SetUp: Create House "HomeB" and "HomeC", Add Cat "Oliver" to HomeB
        House homeB = new House();
        House homeC = new House();
        Cat oliver = new Cat();
        oliver.setName("Oliver");
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet that already belongs to another house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        List<String> petNames = homeC.retrievePetNames();
        assertTrue("HomeC should remain empty", petNames.isEmpty());
    }
    
    @Test
    public void testCase3_addSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD", Add Dog "Rocky" to HomeD
        House homeD = new House();
        Dog rocky = new Dog();
        rocky.setName("Rocky");
        homeD.addPet(rocky);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify house still contains only one instance of Rocky
        List<String> petNames = homeD.retrievePetNames();
        assertEquals("House should contain exactly 1 pet", 1, petNames.size());
        assertTrue("House should contain Rocky", petNames.contains("Rocky"));
    }
    
    @Test
    public void testCase4_addMultipleUniquePets() {
        // SetUp: Create House "HomeE", Create Dog "Bella" and Cat "Lucy" (no houses)
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
        List<String> petNames = homeE.retrievePetNames();
        assertEquals("House should contain exactly 2 pets", 2, petNames.size());
        assertTrue("House should contain Bella", petNames.contains("Bella"));
        assertTrue("House should contain Lucy", petNames.contains("Lucy"));
    }
    
    @Test
    public void testCase5_addPetWithNullName() {
        // SetUp: Create House "HomeF", Create Dog with null name
        House homeF = new House();
        Dog nullNamedDog = new Dog();
        // Name remains null as per constructor default
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify house remains empty
        List<String> petNames = homeF.retrievePetNames();
        assertTrue("House should remain empty when adding pet with null name", petNames.isEmpty());
    }
}