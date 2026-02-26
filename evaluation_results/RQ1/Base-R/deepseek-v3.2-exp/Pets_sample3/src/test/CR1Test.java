import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

public class CR1Test {
    
    private House house;
    private Dog dog;
    private Cat cat;
    
    @Before
    public void setUp() {
        house = new House();
        dog = new Dog();
        cat = new Cat();
    }
    
    @Test
    public void testCase1_RetrieveNamesFromHouseWithMultiplePets() {
        // SetUp: Create House "Home1", Add Dog named "Buddy", Add Cat named "Whiskers"
        House home1 = new House();
        Dog buddy = new Dog();
        buddy.setName("Buddy");
        Cat whiskers = new Cat();
        whiskers.setName("Whiskers");
        
        home1.addPet(buddy);
        home1.addPet(whiskers);
        
        // Action: Retrieve pet names from Home1
        List<String> result = home1.getAllPetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = Arrays.asList("Buddy", "Whiskers");
        assertEquals("Should return names of all pets in house", expected, result);
    }
    
    @Test
    public void testCase2_RetrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2", Add Cat named "Mittens"
        House home2 = new House();
        Cat mittens = new Cat();
        mittens.setName("Mittens");
        
        home2.addPet(mittens);
        
        // Action: Retrieve pet names from Home2
        List<String> result = home2.getAllPetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = Arrays.asList("Mittens");
        assertEquals("Should return name of single pet in house", expected, result);
    }
    
    @Test
    public void testCase3_RetrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        House home3 = new House();
        
        // Action: Retrieve pet names from Home3
        List<String> result = home3.getAllPetNames();
        
        // Expected Output: []
        assertTrue("Should return empty list for house with no pets", result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveNamesAfterPetRemoval() {
        // SetUp: Create House "Home4", Add Dog "Max" and Cat "Luna", Remove "Max"
        House home4 = new House();
        Dog max = new Dog();
        max.setName("Max");
        Cat luna = new Cat();
        luna.setName("Luna");
        
        home4.addPet(max);
        home4.addPet(luna);
        home4.removePet(max);
        
        // Action: Retrieve pet names from Home4
        List<String> result = home4.getAllPetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = Arrays.asList("Luna");
        assertEquals("Should return names of remaining pets after removal", expected, result);
    }
    
    @Test
    public void testCase5_RetrieveNamesFromHouseWithSameNamePets() {
        // SetUp: Create House "Home5", Add Dog "Spot" and Cat "Spot"
        House home5 = new House();
        Dog spotDog = new Dog();
        spotDog.setName("Spot");
        Cat spotCat = new Cat();
        spotCat.setName("Spot");
        
        home5.addPet(spotDog);
        home5.addPet(spotCat);
        
        // Action: Retrieve pet names from Home5
        List<String> result = home5.getAllPetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = Arrays.asList("Spot", "Spot");
        assertEquals("Should return all pet names including duplicates", expected, result);
    }
}