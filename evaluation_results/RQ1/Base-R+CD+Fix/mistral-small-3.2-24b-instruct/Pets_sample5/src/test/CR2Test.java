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
        assertTrue("Should successfully add new pet to empty house", result);
        
        // Post-Condition: HomeA contains Rex
        List<String> petNames = homeA.retrievePetNames();
        assertEquals("House should contain exactly 1 pet", 1, petNames.size());
        assertTrue("House should contain Rex", petNames.contains("Rex"));
        assertEquals("Rex should belong to HomeA", homeA, rex.getLived());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInHouse() {
        // SetUp: Create House "HomeB" and "HomeC", add Cat "Oliver" to HomeB
        House homeB = new House();
        House homeC = new House();
        Cat oliver = new Cat();
        oliver.setName("Oliver");
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Should not add pet that already belongs to another house", result);
        
        // Post-Condition: HomeC remains unchanged
        assertEquals("HomeC should remain empty", 0, homeC.getPetCount());
        assertEquals("Oliver should still belong to HomeB", homeB, oliver.getLived());
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
        assertFalse("Should not add same pet twice to the same house", result);
        
        // Verify house still contains only one instance of Rocky
        assertEquals("House should still contain only one pet", 1, homeD.getPetCount());
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
        assertTrue("First pet should be added successfully", result1);
        assertTrue("Second pet should be added successfully", result2);
        
        // Post-Condition: HomeE contains both pets
        List<String> petNames = homeE.retrievePetNames();
        assertEquals("House should contain exactly 2 pets", 2, petNames.size());
        assertTrue("House should contain Bella", petNames.contains("Bella"));
        assertTrue("House should contain Lucy", petNames.contains("Lucy"));
        assertEquals("Bella should belong to HomeE", homeE, bella.getLived());
        assertEquals("Lucy should belong to HomeE", homeE, lucy.getLived());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF" and Dog with null name
        House homeF = new House();
        Dog nullNamedDog = new Dog();
        // Intentionally not setting name to keep it null
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse("Should not add pet with null name", result);
        
        // Verify house remains empty
        assertEquals("House should remain empty", 0, homeF.getPetCount());
    }
}