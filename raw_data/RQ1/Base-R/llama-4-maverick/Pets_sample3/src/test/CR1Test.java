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
        // SetUp: Create House "Home1", add Dog "Buddy" and Cat "Whiskers"
        House home1 = new House();
        Dog buddy = new Dog("Buddy");
        Cat whiskers = new Cat("Whiskers");
        home1.addPet(buddy);
        home1.addPet(whiskers);
        
        // Action: Retrieve pet names from Home1
        List<String> result = home1.getPetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = Arrays.asList("Buddy", "Whiskers");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_RetrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2", add Cat "Mittens"
        House home2 = new House();
        Cat mittens = new Cat("Mittens");
        home2.addPet(mittens);
        
        // Action: Retrieve pet names from Home2
        List<String> result = home2.getPetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = Arrays.asList("Mittens");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_RetrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        House home3 = new House();
        
        // Action: Retrieve pet names from Home3
        List<String> result = home3.getPetNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveNamesAfterPetRemoval() {
        // SetUp: Create House "Home4", add Dog "Max" and Cat "Luna", then remove "Max"
        House home4 = new House();
        Dog max = new Dog("Max");
        Cat luna = new Cat("Luna");
        home4.addPet(max);
        home4.addPet(luna);
        home4.removePet(max);
        
        // Action: Retrieve pet names from Home4
        List<String> result = home4.getPetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = Arrays.asList("Luna");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_RetrieveNamesFromHouseWithSameNamePets() {
        // SetUp: Create House "Home5", add Dog "Spot" and Cat "Spot"
        House home5 = new House();
        Dog spotDog = new Dog("Spot");
        Cat spotCat = new Cat("Spot");
        home5.addPet(spotDog);
        home5.addPet(spotCat);
        
        // Action: Retrieve pet names from Home5
        List<String> result = home5.getPetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = Arrays.asList("Spot", "Spot");
        assertEquals(expected, result);
    }
}