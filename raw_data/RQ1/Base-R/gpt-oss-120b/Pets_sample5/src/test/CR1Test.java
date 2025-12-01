import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

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
        Dog buddy = new Dog("Buddy");
        home1.addPet(buddy);
        
        // Add Cat named "Whiskers" to Home1
        Cat whiskers = new Cat("Whiskers");
        home1.addPet(whiskers);
        
        // Action: Retrieve pet names from Home1
        List<String> petNames = home1.getAllPetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = Arrays.asList("Buddy", "Whiskers");
        assertEquals("Should return names of all pets in house", expected, petNames);
    }
    
    @Test
    public void testCase2_RetrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2"
        House home2 = new House();
        
        // Add Cat named "Mittens" to Home2
        Cat mittens = new Cat("Mittens");
        home2.addPet(mittens);
        
        // Action: Retrieve pet names from Home2
        List<String> petNames = home2.getAllPetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = Arrays.asList("Mittens");
        assertEquals("Should return name of single pet in house", expected, petNames);
    }
    
    @Test
    public void testCase3_RetrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        House home3 = new House();
        
        // Action: Retrieve pet names from Home3
        List<String> petNames = home3.getAllPetNames();
        
        // Expected Output: []
        assertTrue("Should return empty list for house with no pets", petNames.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveNamesAfterPetRemoval() {
        // SetUp: Create House "Home4"
        House home4 = new House();
        
        // Add Dog "Max" and Cat "Luna"
        Dog max = new Dog("Max");
        Cat luna = new Cat("Luna");
        home4.addPet(max);
        home4.addPet(luna);
        
        // Remove "Max" from Home4
        home4.removePet(max);
        
        // Action: Retrieve pet names from Home4
        List<String> petNames = home4.getAllPetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = Arrays.asList("Luna");
        assertEquals("Should return names of remaining pets after removal", expected, petNames);
    }
    
    @Test
    public void testCase5_RetrieveNamesFromHouseWithSameNamePets() {
        // SetUp: Create House "Home5"
        House home5 = new House();
        
        // Add Dog "Spot" and Cat "Spot"
        Dog dogSpot = new Dog("Spot");
        Cat catSpot = new Cat("Spot");
        home5.addPet(dogSpot);
        home5.addPet(catSpot);
        
        // Action: Retrieve pet names from Home5
        List<String> petNames = home5.getAllPetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = Arrays.asList("Spot", "Spot");
        assertEquals("Should return all pet names including duplicates", expected, petNames);
    }
}