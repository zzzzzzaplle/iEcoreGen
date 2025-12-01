import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    private House home1, home2, home3, home4, home5;
    private Dog buddy, max, spotDog;
    private Cat whiskers, mittens, luna, spotCat;
    
    @Before
    public void setUp() {
        // Initialize houses
        home1 = new House();
        home2 = new House();
        home3 = new House();
        home4 = new House();
        home5 = new House();
        
        // Initialize pets
        buddy = new Dog();
        buddy.setName("Buddy");
        
        whiskers = new Cat();
        whiskers.setName("Whiskers");
        
        mittens = new Cat();
        mittens.setName("Mittens");
        
        max = new Dog();
        max.setName("Max");
        
        luna = new Cat();
        luna.setName("Luna");
        
        spotDog = new Dog();
        spotDog.setName("Spot");
        
        spotCat = new Cat();
        spotCat.setName("Spot");
    }
    
    @Test
    public void testCase1_retrieveNamesFromHouseWithMultiplePets() {
        // SetUp: Create House "Home1", Add Dog named "Buddy" to Home1, Add Cat named "Whiskers" to Home1
        home1.addPet(buddy);
        home1.addPet(whiskers);
        
        // Action: Retrieve pet names from Home1
        List<String> result = home1.retrievePetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = new ArrayList<>();
        expected.add("Buddy");
        expected.add("Whiskers");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_retrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2", Add Cat named "Mittens" to Home2
        home2.addPet(mittens);
        
        // Action: Retrieve pet names from Home2
        List<String> result = home2.retrievePetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = new ArrayList<>();
        expected.add("Mittens");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_retrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        // Action: Retrieve pet names from Home3
        List<String> result = home3.retrievePetNames();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_retrieveNamesAfterPetRemoval() {
        // SetUp: Create House "Home4", Add Dog "Max" and Cat "Luna", Remove "Max" from Home4
        home4.addPet(max);
        home4.addPet(luna);
        home4.removePet(max);
        
        // Action: Retrieve pet names from Home4
        List<String> result = home4.retrievePetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = new ArrayList<>();
        expected.add("Luna");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_retrieveNamesFromHouseWithSameNamePets() {
        // SetUp: Create House "Home5", Add Dog "Spot" and Cat "Spot"
        home5.addPet(spotDog);
        home5.addPet(spotCat);
        
        // Action: Retrieve pet names from Home5
        List<String> result = home5.retrievePetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = new ArrayList<>();
        expected.add("Spot");
        expected.add("Spot");
        assertEquals(expected, result);
    }
}