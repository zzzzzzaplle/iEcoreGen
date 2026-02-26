// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    private House home1;
    private House home2;
    private House home3;
    private House home4;
    private House home5;
    
    @Before
    public void setUp() {
        home1 = new House();
        home2 = new House();
        home3 = new House();
        home4 = new House();
        home5 = new House();
    }
    
    @Test
    public void testCase1_retrieveNamesFromHouseWithMultiplePets() {
        // SetUp:
        // 1. Create House "Home1" (done in setUp)
        // 2. Add Dog named "Buddy" to Home1
        Dog dog = new Dog();
        dog.setName("Buddy");
        home1.addPet(dog);
        
        // 3. Add Cat named "Whiskers" to Home1
        Cat cat = new Cat();
        cat.setName("Whiskers");
        home1.addPet(cat);
        
        // Action: Retrieve pet names from Home1
        List<String> petNames = home1.retrievePetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = new ArrayList<>();
        expected.add("Buddy");
        expected.add("Whiskers");
        assertEquals(expected, petNames);
    }
    
    @Test
    public void testCase2_retrieveNamesFromHouseWithSinglePet() {
        // SetUp:
        // 1. Create House "Home2" (done in setUp)
        // 2. Add Cat named "Mittens" to Home2
        Cat cat = new Cat();
        cat.setName("Mittens");
        home2.addPet(cat);
        
        // Action: Retrieve pet names from Home2
        List<String> petNames = home2.retrievePetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = new ArrayList<>();
        expected.add("Mittens");
        assertEquals(expected, petNames);
    }
    
    @Test
    public void testCase3_retrieveNamesFromEmptyHouse() {
        // SetUp:
        // 1. Create House "Home3" (no pets) (done in setUp)
        
        // Action: Retrieve pet names from Home3
        List<String> petNames = home3.retrievePetNames();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, petNames);
    }
    
    @Test
    public void testCase4_retrieveNamesAfterPetRemoval() {
        // SetUp:
        // 1. Create House "Home4" (done in setUp)
        // 2. Add Dog "Max" and Cat "Luna"
        Dog dog = new Dog();
        dog.setName("Max");
        home4.addPet(dog);
        
        Cat cat = new Cat();
        cat.setName("Luna");
        home4.addPet(cat);
        
        // 3. Remove "Max" from Home4
        home4.removePet(dog);
        
        // Action: Retrieve pet names from Home4
        List<String> petNames = home4.retrievePetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = new ArrayList<>();
        expected.add("Luna");
        assertEquals(expected, petNames);
    }
    
    @Test
    public void testCase5_retrieveNamesFromHouseWithSameNamePets() {
        // SetUp:
        // 1. Create House "Home5" (done in setUp)
        // 2. Add Dog "Spot" and Cat "Spot"
        Dog dog = new Dog();
        dog.setName("Spot");
        home5.addPet(dog);
        
        Cat cat = new Cat();
        cat.setName("Spot");
        home5.addPet(cat);
        
        // Action: Retrieve pet names from Home5
        List<String> petNames = home5.retrievePetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = new ArrayList<>();
        expected.add("Spot");
        expected.add("Spot");
        assertEquals(expected, petNames);
    }
}