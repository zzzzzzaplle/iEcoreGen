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
        
        // Setup for Test Case 1: Mixed house with dogs and cats
        Dog rusty = new Dog("Rusty");
        Cat snowball = new Cat("Snowball");
        Dog zeus = new Dog("Zeus");
        petHome1.addPet(rusty);
        petHome1.addPet(snowball);
        petHome1.addPet(zeus);
        
        // Setup for Test Case 2: Cat-only house
        Cat misty = new Cat("Misty");
        Cat smokey = new Cat("Smokey");
        petHome2.addPet(misty);
        petHome2.addPet(smokey);
        
        // Setup for Test Case 3: Dog-only house
        Dog ace = new Dog("Ace");
        Dog king = new Dog("King");
        petHome3.addPet(ace);
        petHome3.addPet(king);
        
        // Setup for Test Case 5: Single dog house
        Dog rover = new Dog("Rover");
        petHome5.addPet(rover);
    }
    
    @Test
    public void testCase1_RetrieveOnlyDogsFromMixedHouse() {
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = petHome1.getPetsByType("dog");
        
        // Verify the list contains exactly 2 dogs
        assertEquals(2, result.size());
        
        // Verify the dogs are Rusty and Zeus
        List<String> dogNames = new ArrayList<>();
        for (Pet pet : result) {
            dogNames.add(pet.getName());
        }
        assertTrue(dogNames.contains("Rusty"));
        assertTrue(dogNames.contains("Zeus"));
        assertFalse(dogNames.contains("Snowball")); // Should not contain cats
    }
    
    @Test
    public void testCase2_RetrieveCatsFromCatOnlyHouse() {
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = petHome2.getPetsByType("cat");
        
        // Verify the list contains exactly 2 cats
        assertEquals(2, result.size());
        
        // Verify the cats are Misty and Smokey
        List<String> catNames = new ArrayList<>();
        for (Pet pet : result) {
            catNames.add(pet.getName());
        }
        assertTrue(catNames.contains("Misty"));
        assertTrue(catNames.contains("Smokey"));
    }
    
    @Test
    public void testCase3_RetrieveTypeWithNoMatches() {
        // Action: Retrieve pets of input type "cat" from PetHome3 (which has only dogs)
        List<Pet> result = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFromEmptyHouse() {
        // Action: Retrieve pets of input type "dog" from empty PetHome4
        List<Pet> result = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CaseSensitiveTypeRetrieval() {
        // Action: Retrieve pets of input type "dog" from PetHome5
        List<Pet> result = petHome5.getPetsByType("dog");
        
        // Verify the list contains exactly 1 dog
        assertEquals(1, result.size());
        
        // Verify the dog is Rover
        assertEquals("Rover", result.get(0).getName());
    }
}