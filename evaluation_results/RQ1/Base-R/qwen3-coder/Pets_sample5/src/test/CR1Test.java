import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

public class CR1Test {
    
    @Test
    public void testCase1_retrieveNamesFromHouseWithMultiplePets() {
        // SetUp: Create House "Home1", Add Dog "Buddy" and Cat "Whiskers"
        House home1 = new House();
        Dog dog1 = new Dog();
        dog1.setName("Buddy");
        Cat cat1 = new Cat();
        cat1.setName("Whiskers");
        
        home1.addPet(dog1);
        home1.addPet(cat1);
        
        // Action: Retrieve pet names from Home1
        List<String> result = home1.getAllPetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = Arrays.asList("Buddy", "Whiskers");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_retrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2", Add Cat "Mittens"
        House home2 = new House();
        Cat cat2 = new Cat();
        cat2.setName("Mittens");
        
        home2.addPet(cat2);
        
        // Action: Retrieve pet names from Home2
        List<String> result = home2.getAllPetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = Arrays.asList("Mittens");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_retrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        House home3 = new House();
        
        // Action: Retrieve pet names from Home3
        List<String> result = home3.getAllPetNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveNamesAfterPetRemoval() {
        // SetUp: Create House "Home4", Add Dog "Max" and Cat "Luna", Remove "Max"
        House home4 = new House();
        Dog dog4 = new Dog();
        dog4.setName("Max");
        Cat cat4 = new Cat();
        cat4.setName("Luna");
        
        home4.addPet(dog4);
        home4.addPet(cat4);
        home4.removePet(dog4);
        
        // Action: Retrieve pet names from Home4
        List<String> result = home4.getAllPetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = Arrays.asList("Luna");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_retrieveNamesFromHouseWithSameNamePets() {
        // SetUp: Create House "Home5", Add Dog "Spot" and Cat "Spot"
        House home5 = new House();
        Dog dog5 = new Dog();
        dog5.setName("Spot");
        Cat cat5 = new Cat();
        cat5.setName("Spot");
        
        home5.addPet(dog5);
        home5.addPet(cat5);
        
        // Action: Retrieve pet names from Home5
        List<String> result = home5.getAllPetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = Arrays.asList("Spot", "Spot");
        assertEquals(expected, result);
    }
}