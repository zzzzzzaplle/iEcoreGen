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
        // SetUp: Create House "Home1", Add Dog named "Buddy" to Home1, Add Cat named "Whiskers" to Home1
        Dog buddy = new Dog();
        buddy.setName("Buddy");
        home1.addPet(buddy);
        
        Cat whiskers = new Cat();
        whiskers.setName("Whiskers");
        home1.addPet(whiskers);
        
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
        // SetUp: Create House "Home2", Add Cat named "Mittens" to Home2
        Cat mittens = new Cat();
        mittens.setName("Mittens");
        home2.addPet(mittens);
        
        // Action: Retrieve pet names from Home2
        List<String> petNames = home2.retrievePetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = new ArrayList<>();
        expected.add("Mittens");
        assertEquals(expected, petNames);
    }
    
    @Test
    public void testCase3_retrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        // Action: Retrieve pet names from Home3
        List<String> petNames = home3.retrievePetNames();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, petNames);
    }
    
    @Test
    public void testCase4_retrieveNamesAfterPetRemoval() {
        // SetUp: Create House "Home4", Add Dog "Max" and Cat "Luna", Remove "Max" from Home4
        Dog max = new Dog();
        max.setName("Max");
        home4.addPet(max);
        
        Cat luna = new Cat();
        luna.setName("Luna");
        home4.addPet(luna);
        
        home4.removePet(max);
        
        // Action: Retrieve pet names from Home4
        List<String> petNames = home4.retrievePetNames();
        
        // Expected Output: List containing ["Luna"]
        List<String> expected = new ArrayList<>();
        expected.add("Luna");
        assertEquals(expected, petNames);
    }
    
    @Test
    public void testCase5_retrieveNamesFromHouseWithSameNamePets() {
        // SetUp: Create House "Home5", Add Dog "Spot" and Cat "Spot"
        Dog spotDog = new Dog();
        spotDog.setName("Spot");
        home5.addPet(spotDog);
        
        Cat spotCat = new Cat();
        spotCat.setName("Spot");
        home5.addPet(spotCat);
        
        // Action: Retrieve pet names from Home5
        List<String> petNames = home5.retrievePetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        List<String> expected = new ArrayList<>();
        expected.add("Spot");
        expected.add("Spot");
        assertEquals(expected, petNames);
    }
}