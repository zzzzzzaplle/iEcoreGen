import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private House house;
    private Pet pet;
    
    @Before
    public void setUp() {
        // Reset house and pet before each test
        house = null;
        pet = null;
    }
    
    @Test
    public void testCase1_AddNewPetToEmptyHouse() {
        // SetUp: Create House "HomeA" and Dog "Rex" (no house assigned)
        house = new House();
        pet = new Dog("Rex");
        
        // Action: Add Rex to HomeA
        boolean result = house.addPet(pet);
        
        // Expected Output: true
        assertTrue("Adding new pet to empty house should return true", result);
        
        // Post-Condition: HomeA contains Rex
        assertTrue("House should contain the added pet", house.getPets().contains(pet));
        assertEquals("House should have exactly 1 pet", 1, house.countPets());
        assertEquals("Pet should be assigned to the house", house, pet.getHouse());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInAnotherHouse() {
        // SetUp: Create House "HomeB" and "HomeC", Add Cat "Oliver" to HomeB
        House homeB = new House();
        House homeC = new House();
        pet = new Cat("Oliver");
        homeB.addPet(pet); // Oliver now belongs to HomeB
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(pet);
        
        // Expected Output: false
        assertFalse("Adding pet that already belongs to another house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        assertFalse("HomeC should not contain the pet", homeC.getPets().contains(pet));
        assertEquals("HomeC should have 0 pets", 0, homeC.countPets());
        assertEquals("Pet should still belong to HomeB", homeB, pet.getHouse());
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD", Add Dog "Rocky" to HomeD
        house = new House();
        pet = new Dog("Rocky");
        house.addPet(pet); // First addition
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = house.addPet(pet);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify house still has only one pet
        assertEquals("House should still have only 1 pet", 1, house.countPets());
    }
    
    @Test
    public void testCase4_AddMultipleUniquePets() {
        // SetUp: Create House "HomeE", Create Dog "Bella" and Cat "Lucy" (no houses)
        house = new House();
        Pet dog = new Dog("Bella");
        Pet cat = new Cat("Lucy");
        
        // Action: Add Bella to HomeE → true, Add Lucy to HomeE → true
        boolean result1 = house.addPet(dog);
        boolean result2 = house.addPet(cat);
        
        // Expected Output: Both operations return true
        assertTrue("Adding first unique pet should return true", result1);
        assertTrue("Adding second unique pet should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        assertEquals("House should have 2 pets", 2, house.countPets());
        assertTrue("House should contain the dog", house.getPets().contains(dog));
        assertTrue("House should contain the cat", house.getPets().contains(cat));
        
        // Verify both pets are assigned to the house
        assertEquals("Dog should be assigned to the house", house, dog.getHouse());
        assertEquals("Cat should be assigned to the house", house, cat.getHouse());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF", Create Dog with null name
        house = new House();
        pet = new Dog(); // Constructor without name sets name to null
        
        // Action: Add null-named dog to HomeF
        boolean result = house.addPet(pet);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify house remains empty
        assertEquals("House should have 0 pets", 0, house.countPets());
        assertNull("Pet should not be assigned to any house", pet.getHouse());
    }
}