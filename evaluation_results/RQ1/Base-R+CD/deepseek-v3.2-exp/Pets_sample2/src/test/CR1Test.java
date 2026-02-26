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
    public void testCase1_retrieveNamesFromHouseWithMultiplePets() {
        // SetUp: Create House "Home1", add Dog "Buddy" and Cat "Whiskers"
        House home1 = new House();
        Dog buddy = new Dog("Buddy");
        Cat whiskers = new Cat("Whiskers");
        
        home1.addPet(buddy);
        home1.addPet(whiskers);
        
        // Action: Retrieve pet names from Home1
        List<String> result = home1.retrievePetNames();
        
        // Expected Output: List containing ["Buddy", "Whiskers"]
        List<String> expected = Arrays.asList("Buddy", "Whiskers");
        assertEquals("Should return both pet names", expected, result);
    }
    
    @Test
    public void testCase2_retrieveNamesFromHouseWithSinglePet() {
        // SetUp: Create House "Home2", add Cat "Mittens"
        House home2 = new House();
        Cat mittens = new Cat("Mittens");
        
        home2.addPet(mittens);
        
        // Action: Retrieve pet names from Home2
        List<String> result = home2.retrievePetNames();
        
        // Expected Output: List containing ["Mittens"]
        List<String> expected = Arrays.asList("Mittens");
        assertEquals("Should return single pet name", expected, result);
    }
    
    @Test
    public void testCase3_retrieveNamesFromEmptyHouse() {
        // SetUp: Create House "Home3" (no pets)
        House home3 = new House();
        
        // Action: Retrieve pet names from Home3
        List<String> result = home3.retrievePet