import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

public class CR1Test {
    
    private House house;
    
    @Before
    public void setUp() {
        house = new House();
    }
    
    @Test
    public void testCase1_RetrieveNamesFromHouseWithMultiplePets() {
        // SetUp: Create House "Home1", add Dog named "Buddy" and Cat named "Whiskers"
        house = new House();
        Dog dog = new Dog();
        dog.setName("Buddy");
        Cat cat = new Cat();
        cat.setName("Whiskers");
        house.addPet(dog);
        house.addPet(cat);
        
        // Action: Retrieve pet names from Home1
        List<String> result = house.retrievePetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = Arrays.asList("Buddy", "Whiskers");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_RetrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2", add Cat named "Mittens"
        house = new House();
        Cat cat = new Cat();
        cat.setName("Mittens");
        house.addPet(cat);
        
        // Action: Retrieve pet names from Home2
        List<String> result = house.retrievePetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = Arrays.asList("Mittens");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_RetrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        house = new House();
        
        // Action: Retrieve pet names from Home3
        List<String> result = house.retrievePetNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveNamesAfterPetRemoval() {
        // SetUp: Create House "Home4", add Dog "Max" and Cat "Luna", then remove "Max"
        house = new House();
        Dog dog = new Dog();
        dog.setName("Max");
        Cat cat = new Cat();
        cat.setName("Luna");
        house.addPet(dog);
        house.addPet(cat);
        house.removePet(dog);
        
        // Action: Retrieve pet names from Home4
        List<String> result = house.retrievePetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = Arrays.asList("Luna");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_RetrieveNamesFromHouseWithSameNamePets() {
        // SetUp: Create House "Home5", add Dog "Spot" and Cat "Spot"
        house = new House();
        Dog dog = new Dog();
        dog.setName("Spot");
        Cat cat = new Cat();
        cat.setName("Spot");
        house.addPet(dog);
        house.addPet(cat);
        
        // Action: Retrieve pet names from Home5
        List<String> result = house.retrievePetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = Arrays.asList("Spot", "Spot");
        assertEquals(expected, result);
    }
}