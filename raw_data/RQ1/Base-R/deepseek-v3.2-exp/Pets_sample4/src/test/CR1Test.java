import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    private House house;
    
    @Before
    public void setUp() {
        house = new House();
    }
    
    @Test
    public void testCase1_RetrieveNamesFromHouseWithMultiplePets() {
        // SetUp: Create House "Home1"
        House home1 = new House();
        
        // Add Dog named "Buddy" to Home1
        Dog dog = new Dog();
        dog.setName("Buddy");
        home1.addPet(dog);
        
        // Add Cat named "Whiskers" to Home1
        Cat cat = new Cat();
        cat.setName("Whiskers");
        home1.addPet(cat);
        
        // Action: Retrieve pet names from Home1
        List<String> result = home1.getAllPetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = new ArrayList<>();
        expected.add("Buddy");
        expected.add("Whiskers");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_RetrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2"
        House home2 = new House();
        
        // Add Cat named "Mittens" to Home2
        Cat cat = new Cat();
        cat.setName("Mittens");
        home2.addPet(cat);
        
        // Action: Retrieve pet names from Home2
        List<String> result = home2.getAllPetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = new ArrayList<>();
        expected.add("Mittens");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_RetrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        House home3 = new House();
        
        // Action: Retrieve pet names from Home3
        List<String> result = home3.getAllPetNames();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_RetrieveNamesAfterPetRemoval() {
        // SetUp: Create House "Home4"
        House home4 = new House();
        
        // Add Dog "Max" and Cat "Luna"
        Dog dog = new Dog();
        dog.setName("Max");
        home4.addPet(dog);
        
        Cat cat = new Cat();
        cat.setName("Luna");
        home4.addPet(cat);
        
        // Remove "Max" from Home4
        home4.removePet(dog);
        
        // Action: Retrieve pet names from Home4
        List<String> result = home4.getAllPetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = new ArrayList<>();
        expected.add("Luna");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_RetrieveNamesFromHouseWithSameNamePets() {
        // SetUp: Create House "Home5"
        House home5 = new House();
        
        // Add Dog "Spot" and Cat "Spot"
        Dog dog = new Dog();
        dog.setName("Spot");
        home5.addPet(dog);
        
        Cat cat = new Cat();
        cat.setName("Spot");
        home5.addPet(cat);
        
        // Action: Retrieve pet names from Home5
        List<String> result = home5.getAllPetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = new ArrayList<>();
        expected.add("Spot");
        expected.add("Spot");
        
        assertEquals(expected, result);
    }
}