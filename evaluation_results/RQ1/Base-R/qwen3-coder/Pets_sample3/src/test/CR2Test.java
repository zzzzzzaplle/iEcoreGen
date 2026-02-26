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
        List<Pet> pets = homeA.getPets();
        assertEquals("House should contain exactly 1 pet", 1, pets.size());
        assertEquals("House should contain Rex", "Rex", pets.get(0).getName());
        assertEquals("Rex should be assigned to HomeA", homeA, rex.getHouse());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInTheHouse() {
        // SetUp: Create House "HomeB" and "HomeC", Add Cat "Oliver" to HomeB
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
        List<Pet> homeCPets = homeC.getPets();
        assertTrue("HomeC should remain empty", homeCPets.isEmpty());
        assertEquals("Oliver should still belong to HomeB", homeB, oliver.getHouse());
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD", Add Dog "Rocky" to HomeD
        House homeD = new House();
        Dog rocky = new Dog();
        rocky.setName("Rocky");
        homeD.addPet(rocky); // First addition
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify house still has only one pet
        assertEquals("House should contain exactly 1 pet", 1, homeD.countPets());
    }
    
    @Test
    public void testCase4_AddMultipleUniquePets() {
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
        assertEquals("House should contain exactly 2 pets", 2, homeE.countPets());
        List<String> petNames = homeE.getAllPetNames();
        assertTrue("House should contain Bella", petNames.contains("Bella"));
        assertTrue("House should contain Lucy", petNames.contains("Lucy"));
        assertEquals("Bella should be assigned to HomeE", homeE, bella.getHouse());
        assertEquals("Lucy should be assigned to HomeE", homeE, lucy.getHouse());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF", Create Dog with null name
        House homeF = new House();
        Dog namelessDog = new Dog();
        namelessDog.setName(null); // Explicitly set name to null
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(namelessDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify house remains empty
        assertTrue("House should remain empty", homeF.getPets().isEmpty());
        assertNull("Pet should not be assigned to any house", namelessDog.getHouse());
    }
}