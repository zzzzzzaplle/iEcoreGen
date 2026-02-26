import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    private House homeA;
    private House homeB;
    private House homeC;
    private House homeD;
    private House homeE;
    private House homeF;
    private Dog rex;
    private Cat oliver;
    private Dog rocky;
    private Dog bella;
    private Cat lucy;
    private Dog nullNamedDog;

    @Before
    public void setUp() {
        // Initialize houses for test cases
        homeA = new House();
        homeB = new House();
        homeC = new House();
        homeD = new House();
        homeE = new House();
        homeF = new House();
        
        // Initialize pets for test cases
        rex = new Dog("Rex");
        oliver = new Cat("Oliver");
        rocky = new Dog("Rocky");
        bella = new Dog("Bella");
        lucy = new Cat("Lucy");
        nullNamedDog = new Dog(null);
    }

    @Test
    public void testCase1_AddNewPetToEmptyHouse() {
        // Test Case 1: "Add new pet to empty house"
        // Action: Add Rex to HomeA
        boolean result = homeA.addPet(rex);
        
        // Expected Output: true
        assertTrue("Adding new pet to empty house should return true", result);
        
        // Post-Condition: HomeA contains Rex
        List<String> petNames = homeA.getAllPetNames();
        assertEquals("HomeA should contain exactly 1 pet", 1, petNames.size());
        assertTrue("HomeA should contain Rex", petNames.contains("Rex"));
        assertEquals("Rex should belong to HomeA", homeA, rex.getHouse());
    }

    @Test
    public void testCase2_AddPetAlreadyInHouse() {
        // Test Case 2: "Add pet already in the house"
        // SetUp: Add Oliver to HomeB
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet that already belongs to another house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        List<String> homeCPetNames = homeC.getAllPetNames();
        assertTrue("HomeC should remain empty", homeCPetNames.isEmpty());
        assertEquals("Oliver should still belong to HomeB", homeB, oliver.getHouse());
    }

    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // Test Case 3: "Add same pet twice to same house"
        // SetUp: Add Rocky to HomeD
        homeD.addPet(rocky);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify HomeD still has only one pet
        assertEquals("HomeD should still have only 1 pet", 1, homeD.countPets());
    }

    @Test
    public void testCase4_AddMultipleUniquePets() {
        // Test Case 4: "Add multiple unique pets"
        // Action: Add Bella to HomeE → true
        boolean result1 = homeE.addPet(bella);
        
        // Action: Add Lucy to HomeE → true
        boolean result2 = homeE.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue("Adding first unique pet should return true", result1);
        assertTrue("Adding second unique pet should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        List<String> petNames = homeE.getAllPetNames();
        assertEquals("HomeE should contain 2 pets", 2, petNames.size());
        assertTrue("HomeE should contain Bella", petNames.contains("Bella"));
        assertTrue("HomeE should contain Lucy", petNames.contains("Lucy"));
        assertEquals("Bella should belong to HomeE", homeE, bella.getHouse());
        assertEquals("Lucy should belong to HomeE", homeE, lucy.getHouse());
    }

    @Test
    public void testCase5_AddPetWithNullName() {
        // Test Case 5: "Add pet with null name"
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify HomeF remains empty
        List<String> petNames = homeF.getAllPetNames();
        assertTrue("HomeF should remain empty", petNames.isEmpty());
    }
}