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
    public void testCase1_RetrieveNamesFromHouseWithMultiplePets() {
        // SetUp: Create House "Home1", add Dog "Buddy" and Cat "Whiskers"
        House home1 = new House();
        Dog buddy = new Dog("Buddy");
        Cat whiskers = new Cat("Whiskers");
        home1.addPet(buddy);
        home1.addPet(whiskers);
        
        // Action: Retrieve pet names from Home1
        List<String> petNames = home1.getAllPetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = Arrays.asList("Buddy", "Whiskers");
        assertEquals("Should return both pet names", expected, petNames);
    }
    
    @Test
    public void testCase2_RetrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2", add Cat "Mittens"
        House home2 = new House();
        Cat mittens = new Cat("Mittens");
        home2.addPet(mittens);
        
        // Action: Retrieve pet names from Home2
        List<String> petNames = home2.getAllPetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = Arrays.asList("Mittens");
        assertEquals("Should return single pet name", expected, petNames);
    }
    
    @Test
    public void testCase3_RetrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        House home3 = new House();
        
        // Action: Retrieve pet names from Home3
        List<String> petNames = home3.getAllPetNames();
        
        // Expected Output: []
        assertTrue("Should return empty list", petNames.isEmpty());
        assertEquals("Should return empty list", 0, petNames.size());
    }
    
    @Test
    public void testCase4_RetrieveNamesAfterPetRemoval() {
        // SetUp: Create House "Home4", add Dog "Max" and Cat "Luna", remove "Max"
        House home4 = new House();
        Dog max = new Dog("Max");
        Cat luna = new Cat("Luna");
        home4.addPet(max);
        home4.addPet(luna);
        home4.removePet(max);
        
        // Action: Retrieve pet names from Home4
        List<String> petNames = home4.getAllPetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = Arrays.asList("Luna");
        assertEquals("Should return remaining pet name after removal", expected, petNames);
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
        List<String> petNames = home5.getAllPetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = Arrays.asList("Spot", "Spot");
        assertEquals("Should return both pets with same name", expected, petNames);
        assertEquals("Should have two pets with same name", 2, petNames.size());
        assertEquals("First pet should be Spot", "Spot", petNames.get(0));
        assertEquals("Second pet should be Spot", "Spot", petNames.get(1));
    }
}