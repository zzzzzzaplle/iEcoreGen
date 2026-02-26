import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    private House petHome1;
    private House petHome2;
    private House petHome3;
    private House petHome4;
    private House petHome5;
    
    @Before
    public void setUp() {
        // Set up houses for test cases
        petHome1 = new House();
        petHome2 = new House();
        petHome3 = new House();
        petHome4 = new House();
        petHome5 = new House();
        
        // Set up PetHome1 with mixed pets: Dog "Rusty", Cat "Snowball", Dog "Zeus"
        petHome1.addPet(new Dog("Rusty"));
        petHome1.addPet(new Cat("Snowball"));
        petHome1.addPet(new Dog("Zeus"));
        
        // Set up PetHome2 with cats only: Cat "Misty", Cat "Smokey"
        petHome2.addPet(new Cat("Misty"));
        petHome2.addPet(new Cat("Smokey"));
        
        // Set up PetHome3 with dogs only: Dog "Ace", Dog "King"
        petHome3.addPet(new Dog("Ace"));
        petHome3.addPet(new Dog("King"));
        
        // PetHome4 remains empty (no setup needed)
        
        // Set up PetHome5 with one dog: Dog "Rover"
        petHome5.addPet(new Dog("Rover"));
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Animal> result = petHome1.getPetsByType("dog");
        
        // Verify the list contains exactly 2 dogs
        assertEquals("Should retrieve 2 dogs", 2, result.size());
        
        // Verify the dogs are "Rusty" and "Zeus"
        List<String> dogNames = new ArrayList<>();
        for (Animal dog : result) {
            dogNames.add(dog.getName());
        }
        assertTrue("Should contain Rusty", dogNames.contains("Rusty"));
        assertTrue("Should contain Zeus", dogNames.contains("Zeus"));
        assertFalse("Should not contain Snowball", dogNames.contains("Snowball"));
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Animal> result = petHome2.getPetsByType("cat");
        
        // Verify the list contains exactly 2 cats
        assertEquals("Should retrieve 2 cats", 2, result.size());
        
        // Verify the cats are "Misty" and "Smokey"
        List<String> catNames = new ArrayList<>();
        for (Animal cat : result) {
            catNames.add(cat.getName());
        }
        assertTrue("Should contain Misty", catNames.contains("Misty"));
        assertTrue("Should contain Smokey", catNames.contains("Smokey"));
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Animal> result = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Should return empty list when no cats present", result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // Action: Retrieve pets of input type "dog" from PetHome4
        List<Animal> result = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Should return empty list when house is empty", result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // Action: Retrieve pets of input type "dog" from PetHome5
        List<Animal> result = petHome5.getPetsByType("dog");
        
        // Verify the list contains exactly 1 dog
        assertEquals("Should retrieve 1 dog", 1, result.size());
        
        // Verify the dog is "Rover"
        assertEquals("Should be Rover", "Rover", result.get(0).getName());
    }
}