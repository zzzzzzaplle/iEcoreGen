import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR1Test {
    
    private House house;
    
    @Before
    public void setUp() {
        // Reset house before each test
        house = new House();
    }
    
    @Test
    public void testCase1_retrieveNamesFromHouseWithMultiplePets() {
        // SetUp: Create House "Home1"
        House home1 = new House();
        
        // SetUp: Add Dog named "Buddy" to Home1
        Dog dog = new Dog();
        dog.setName("Buddy");
        home1.addPet(dog);
        
        // SetUp: Add Cat named "Whiskers" to Home1
        Cat cat = new Cat();
        cat.setName("Whiskers");
        home1.addPet(cat);
        
        // Action: Retrieve pet names from Home1
        List<String> result = home1.retrievePetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = Arrays.asList("Buddy", "Whiskers");
        assertEquals("Should return names of all pets in house", expected, result);
    }
    
    @Test
    public void testCase2_retrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2"
        House home2 = new House();
        
        // SetUp: Add Cat named "Mittens" to Home2
        Cat cat = new Cat();
        cat.setName("Mittens");
        home2.addPet(cat);
        
        // Action: Retrieve pet names from Home2
        List<String> result = home2.retrievePetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = Arrays.asList("Mittens");
        assertEquals("Should return name of single pet in house", expected, result);
    }
    
    @Test
    public void testCase3_retrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        House home3 = new House();
        
        // Action: Retrieve pet names from Home3
        List<String> result = home3.retrievePetNames();
        
        // Expected Output: []
        assertTrue("Should return empty list for house with no pets", result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveNamesAfterPetRemoval() {
        // SetUp: Create House "Home4"
        House home4 = new House();
        
        // SetUp: Add Dog "Max" and Cat "Luna"
        Dog dog = new Dog();
        dog.setName("Max");
        home4.addPet(dog);
        
        Cat cat = new Cat();
        cat.setName("Luna");
        home4.addPet(cat);
        
        // SetUp: Remove "Max" from Home4
        home4.removePet(dog);
        
        // Action: Retrieve pet names from Home4
        List<String> result = home4.retrievePetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = Arrays.asList("Luna");
        assertEquals("Should return names of remaining pets after removal", expected, result);
    }
    
    @Test
    public void testCase5_retrieveNamesFromHouseWithSameNamePets() {
        // SetUp: Create House "Home5"
        House home5 = new House();
        
        // SetUp: Add Dog "Spot" and Cat "Spot"
        Dog dog = new Dog();
        dog.setName("Spot");
        home5.addPet(dog);
        
        Cat cat = new Cat();
        cat.setName("Spot");
        home5.addPet(cat);
        
        // Action: Retrieve pet names from Home5
        List<String> result = home5.retrievePetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = Arrays.asList("Spot", "Spot");
        assertEquals("Should return all pet names including duplicates", expected, result);
    }
}