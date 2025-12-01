import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private House petHome1;
    private House petHome2;
    private House petHome3;
    private House petHome4;
    private House petHome5;
    
    @Before
    public void setUp() {
        // Set up test data for all test cases
        
        // Test Case 1: Mixed house with dogs and cats
        petHome1 = new House();
        petHome1.addPet(new Dog("Rusty"));
        petHome1.addPet(new Cat("Snowball"));
        petHome1.addPet(new Dog("Zeus"));
        
        // Test Case 2: Cat-only house
        petHome2 = new House();
        petHome2.addPet(new Cat("Misty"));
        petHome2.addPet(new Cat("Smokey"));
        
        // Test Case 3: Dog-only house (for retrieving cats)
        petHome3 = new House();
        petHome3.addPet(new Dog("Ace"));
        petHome3.addPet(new Dog("King"));
        
        // Test Case 4: Empty house
        petHome4 = new House();
        
        // Test Case 5: Single dog house for case sensitivity test
        petHome5 = new House();
        petHome5.addPet(new Dog("Rover"));
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = petHome1.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, result.size());
        
        // Verify that both dogs are present and in correct order
        assertEquals("Rusty", result.get(0).getName());
        assertEquals("Zeus", result.get(1).getName());
        
        // Verify that all returned pets are Dogs
        for (Pet pet : result) {
            assertTrue(pet instanceof Dog);
        }
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = petHome2.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, result.size());
        
        // Verify that both cats are present and in correct order
        assertEquals("Misty", result.get(0).getName());
        assertEquals("Smokey", result.get(1).getName());
        
        // Verify that all returned pets are Cats
        for (Pet pet : result) {
            assertTrue(pet instanceof Cat);
        }
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> result = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // Action: Retrieve pets of input type "dog" from PetHome4
        List<Pet> result = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // Action: Retrieve pets of input type "dog" (lowercase) from PetHome5
        List<Pet> result = petHome5.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, result.size());
        assertEquals("Rover", result.get(0).getName());
        assertTrue(result.get(0) instanceof Dog);
    }


}