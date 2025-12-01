import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

public class CR1Test {
    
    private House house;
    
    @Before
    public void setUp() {
        // Reset house before each test
        house = null;
    }
    
    @Test
    public void testCase1_retrieveNamesFromHouseWithMultiplePets() {
        // SetUp: Create House "Home1"
        house = new House();
        
        // SetUp: Add Dog named "Buddy" to Home1
        Dog dog = new Dog();
        dog.setName("Buddy");
        house.addPet(dog);
        
        // SetUp: Add Cat named "Whiskers" to Home1
        Cat cat = new Cat();
        cat.setName("Whiskers");
        house.addPet(cat);
        
        // Action: Retrieve pet names from Home1
        List<String> result = house.getAllPetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = Arrays.asList("Buddy", "Whiskers");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_retrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2"
        house = new House();
        
        // SetUp: Add Cat named "Mittens" to Home2
        Cat cat = new Cat();
        cat.setName("Mittens");
        house.addPet(cat);
        
        // Action: Retrieve pet names from Home2
        List<String> result = house.getAllPetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = Arrays.asList("Mittens");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_retrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        house = new House();
        
        // Action: Retrieve pet names from Home3
        List<String> result = house.getAllPetNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_retrieveNamesAfterPetRemoval() {
        // SetUp: Create House "Home4"
        house = new House();
        
        // SetUp: Add Dog "Max" and Cat "Luna"
        Dog dog = new Dog();
        dog.setName("Max");
        house.addPet(dog);
        
        Cat cat = new Cat();
        cat.setName("Luna");
        house.addPet(cat);
        
        // SetUp: Remove "Max" from Home4
        house.removePet(dog);
        
        // Action: Retrieve pet names from Home4
        List<String> result = house.getAllPetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = Arrays.asList("Luna");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_retrieveNamesFromHouseWithSameNamePets() {
        // SetUp: Create House "Home5"
        house = new House();
        
        // SetUp: Add Dog "Spot" and Cat "Spot"
        Dog dog = new Dog();
        dog.setName("Spot");
        house.addPet(dog);
        
        Cat cat = new Cat();
        cat.setName("Spot");
        house.addPet(cat);
        
        // Action: Retrieve pet names from Home5
        List<String> result = house.getAllPetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = Arrays.asList("Spot", "Spot");
        assertEquals(expected, result);
    }
}