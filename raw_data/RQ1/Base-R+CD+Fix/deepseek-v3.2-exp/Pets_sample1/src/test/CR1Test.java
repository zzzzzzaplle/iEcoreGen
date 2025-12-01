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
    public void testCase1_retrieveNamesFromHouseWithMultiplePets() {
        // SetUp: Create House "Home1", Add Dog named "Buddy", Add Cat named "Whiskers"
        House home1 = new House();
        Dog dog1 = new Dog();
        dog1.setName("Buddy");
        Cat cat1 = new Cat();
        cat1.setName("Whiskers");
        
        home1.addPet(dog1);
        home1.addPet(cat1);
        
        // Action: Retrieve pet names from Home1
        List<String> result = home1.retrievePetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = Arrays.asList("Buddy", "Whiskers");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_retrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2", Add Cat named "Mittens"
        House home2 = new House();
        Cat cat = new Cat();
        cat.setName("Mittens");
        
        home2.addPet(cat);
        
        // Action: Retrieve pet names from Home2
        List<String> result = home2.retrievePetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = Arrays.asList("Mittens");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_retrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        House home3 = new House();
        
        // Action: Retrieve pet names from Home3
        List<String> result = home3.retrievePetNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveNamesAfterPetRemoval() {
        // SetUp: Create House "Home4", Add Dog "Max" and Cat "Luna", Remove "Max"
        House home4 = new House();
        Dog dog = new Dog();
        dog.setName("Max");
        Cat cat = new Cat();
        cat.setName("Luna");
        
        home4.addPet(dog);
        home4.addPet(cat);
        home4.removePet(dog);
        
        // Action: Retrieve pet names from Home4
        List<String> result = home4.retrievePetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = Arrays.asList("Luna");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_retrieveNamesFromHouseWithSameNamePets() {
        // SetUp: Create House "Home5", Add Dog "Spot" and Cat "Spot"
        House home5 = new House();
        Dog dog = new Dog();
        dog.setName("Spot");
        Cat cat = new Cat();
        cat.setName("Spot");
        
        home5.addPet(dog);
        home5.addPet(cat);
        
        // Action: Retrieve pet names from Home5
        List<String> result = home5.retrievePetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = Arrays.asList("Spot", "Spot");
        assertEquals(expected, result);
    }
}