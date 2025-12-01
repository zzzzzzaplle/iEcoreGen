import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    private House house;
    
    @Before
    public void setUp() {
        // Reset house before each test
        house = null;
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // SetUp: Create House "PetHome1"
        house = new House();
        
        // SetUp: Add Dog "Rusty", Cat "Snowball", Dog "Zeus"
        Dog rusty = new Dog();
        rusty.setName("Rusty");
        house.addPet(rusty);
        
        Cat snowball = new Cat();
        snowball.setName("Snowball");
        house.addPet(snowball);
        
        Dog zeus = new Dog();
        zeus.setName("Zeus");
        house.addPet(zeus);
        
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, result.size());
        
        // Verify names match expected output
        List<String> names = new ArrayList<>();
        for (Pet pet : result) {
            names.add(pet.getName());
        }
        assertTrue(names.contains("Rusty"));
        assertTrue(names.contains("Zeus"));
        assertFalse(names.contains("Snowball"));
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // SetUp: Create House "PetHome2"
        house = new House();
        
        // SetUp: Add Cat "Misty", Cat "Smokey"
        Cat misty = new Cat();
        misty.setName("Misty");
        house.addPet(misty);
        
        Cat smokey = new Cat();
        smokey.setName("Smokey");
        house.addPet(smokey);
        
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = house.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, result.size());
        
        // Verify names match expected output
        List<String> names = new ArrayList<>();
        for (Pet pet : result) {
            names.add(pet.getName());
        }
        assertTrue(names.contains("Misty"));
        assertTrue(names.contains("Smokey"));
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // SetUp: Create House "PetHome3"
        house = new House();
        
        // SetUp: Add Dog "Ace", Dog "King"
        Dog ace = new Dog();
        ace.setName("Ace");
        house.addPet(ace);
        
        Dog king = new Dog();
        king.setName("King");
        house.addPet(king);
        
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> result = house.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // SetUp: Create empty House "PetHome4"
        house = new House();
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // SetUp: Create House "PetHome5"
        house = new House();
        
        // SetUp: Add Dog "Rover"
        Dog rover = new Dog();
        rover.setName("Rover");
        house.addPet(rover);
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, result.size());
        assertEquals("Rover", result.get(0).getName());
    }
}