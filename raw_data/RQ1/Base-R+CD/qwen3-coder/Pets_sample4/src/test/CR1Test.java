// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    private House home1, home2, home3, home4, home5;
    private Dog buddy, max;
    private Cat whiskers, mittens, luna, spotCat;
    
    @Before
    public void setUp() {
        // Initialize houses
        home1 = new House();
        home2 = new House();
        home3 = new House();
        home4 = new House();
        home5 = new House();
        
        // Initialize pets for test cases
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
        
        Dog spotDog = new Dog();
        spotDog.setName("Spot");
        
        spotCat = new Cat();
        spotCat.setName("Spot");
        
        // Add pets to houses as needed for test cases
        // For test case 1
        home1.addPet(buddy);
        home1.addPet(whiskers);
        
        // For test case 2
        home2.addPet(mittens);
        
        // For test case 4
        home4.addPet(max);
        home4.addPet(luna);
        home4.removePet(max);
        
        // For test case 5
        home5.addPet(spotDog);
        home5.addPet(spotCat);
    }
    
    @Test
    public void testCase1_retrieveNamesFromHouseWithMultiplePets() {
        // Action: Retrieve pet names from Home1
        List<String> petNames = home1.retrievePetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        assertEquals(2, petNames.size());
        assertTrue(petNames.contains("Buddy"));
        assertTrue(petNames.contains("Whiskers"));
    }
    
    @Test
    public void testCase2_retrieveNamesFromHouseWithSinglePet() {
        // Action: Retrieve pet names from Home2
        List<String> petNames = home2.retrievePetNames();
        
        // Expected Output: List containing ["Mittens"]
        assertEquals(1, petNames.size());
        assertTrue(petNames.contains("Mittens"));
    }
    
    @Test
    public void testCase3_retrieveNamesFromEmptyHouse() {
        // Action: Retrieve pet names from Home3
        List<String> petNames = home3.retrievePetNames();
        
        // Expected Output: []
        assertEquals(0, petNames.size());
    }
    
    @Test
    public void testCase4_retrieveNamesAfterPetRemoval() {
        // Action: Retrieve pet names from Home4
        List<String> petNames = home4.retrievePetNames();
        
        // Expected Output: List containing ["Luna"]
        assertEquals(1, petNames.size());
        assertTrue(petNames.contains("Luna"));
    }
    
    @Test
    public void testCase5_retrieveNamesFromHouseWithSameNamePets() {
        // Action: Retrieve pet names from Home5
        List<String> petNames = home5.retrievePetNames();
        
        // Expected Output: List containing ["Spot", "Spot"]
        assertEquals(2, petNames.size());
        assertEquals("Spot", petNames.get(0));
        assertEquals("Spot", petNames.get(1));
    }
}