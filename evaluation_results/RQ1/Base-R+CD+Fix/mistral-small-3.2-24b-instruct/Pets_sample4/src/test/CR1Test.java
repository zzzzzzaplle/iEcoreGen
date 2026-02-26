import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    private House house;
    
    @Test
    public void testCase1_RetrieveNamesFromHouseWithMultiplePets() {
        // SetUp: Create House "Home1"
        house = new House();
        
        // SetUp: Add Dog named "Buddy" to Home1
        Dog dog1 = new Dog();
        dog1.setName("Buddy");
        house.addPet(dog1);
        
        // SetUp: Add Cat named "Whiskers" to Home1
        Cat cat1 = new Cat();
        cat1.setName("Whiskers");
        house.addPet(cat1);
        
        // Action: Retrieve pet names from Home1
        List<String> result = house.retrievePetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = new ArrayList<>();
        expected.add("Buddy");
        expected.add("Whiskers");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_RetrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2"
        house = new House();
        
        // SetUp: Add Cat named "Mittens" to Home2
        Cat cat = new Cat();
        cat.setName("Mittens");
        house.addPet(cat);
        
        // Action: Retrieve pet names from Home2
        List<String> result = house.retrievePetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = new ArrayList<>();
        expected.add("Mittens");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_RetrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        house = new House();
        
        // Action: Retrieve pet names from Home3
        List<String> result = house.retrievePetNames();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveNamesAfterPetRemoval() {
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
        List<String> result = house.retrievePetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = new ArrayList<>();
        expected.add("Luna");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_RetrieveNamesFromHouseWithSameNamePets() {
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
        List<String> result = house.retrievePetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = new ArrayList<>();
        expected.add("Spot");
        expected.add("Spot");
        assertEquals(expected, result);
    }
}