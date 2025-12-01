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
        house = new House();
        Dog rex = new Dog();
        rex.setName("Rex");
        
        // Action: Add Rex to HomeA
        boolean result = house.addPet(rex);
        
        // Expected Output: true
        assertTrue("Adding new pet to empty house should return true", result);
        
        // Post-Condition: HomeA contains Rex
        assertTrue("House should contain Rex after addition", house.getPets().contains(rex));
        assertEquals("House should have exactly 1 pet", 1, house.countPets());
        assertEquals("Rex's house should be set to HomeA", house, rex.getHouse());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInDifferentHouse() {
        // SetUp: Create House "HomeB" and "HomeC"
        House homeB = new House();
        House homeC = new House();
        
        // Create Cat "Oliver" and add to HomeB
        Cat oliver = new Cat();
        oliver.setName("Oliver");
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet already in different house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        assertFalse("HomeC should not contain Oliver", homeC.getPets().contains(oliver));
        assertEquals("HomeC should have 0 pets", 0, homeC.countPets());
        assertEquals("Oliver should still belong to HomeB", homeB, oliver.getHouse());
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD" and add Dog "Rocky" to HomeD
        house = new House();
        Dog rocky = new Dog();
        rocky.setName("Rocky");
        house.addPet(rocky);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = house.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify house still has only one instance of Rocky
        assertEquals("House should still have only 1 pet", 1, house.countPets());
        assertEquals("Rocky should still belong to HomeD", house, rocky.getHouse());
    }
    
    @Test
    public void testCase4_AddMultipleUniquePets() {
        // SetUp: Create House "HomeE", Dog "Bella" and Cat "Lucy" (no houses)
        house = new House();
        Dog bella = new Dog();
        bella.setName("Bella");
        Cat lucy = new Cat();
        lucy.setName("Lucy");
        
        // Action: Add Bella to HomeE → true
        boolean result1 = house.addPet(bella);
        
        // Action: Add Lucy to HomeE → true
        boolean result2 = house.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue("Adding Bella should return true", result1);
        assertTrue("Adding Lucy should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        assertTrue("House should contain Bella", house.getPets().contains(bella));
        assertTrue("House should contain Lucy", house.getPets().contains(lucy));
        assertEquals("House should have exactly 2 pets", 2, house.countPets());
        assertEquals("Bella's house should be set to HomeE", house, bella.getHouse());
        assertEquals("Lucy's house should be set to HomeE", house, lucy.getHouse());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF" and Dog with null name
        house = new House();
        Dog nullNamedDog = new Dog();
        // Intentionally not setting name to null
        
        // Action: Add null-named dog to HomeF
        boolean result = house.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify house remains empty
        assertEquals("House should have 0 pets", 0, house.countPets());
        assertNull("Dog's house should remain null", nullNamedDog.getHouse());
    }
}