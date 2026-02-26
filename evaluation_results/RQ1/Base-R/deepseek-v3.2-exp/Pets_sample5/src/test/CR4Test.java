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
    
    private Dog rusty;
    private Cat snowball;
    private Dog zeus;
    private Cat misty;
    private Cat smokey;
    private Dog ace;
    private Dog king;
    private Dog rover;
    
    @Before
    public void setUp() {
        // Create houses
        petHome1 = new House();
        petHome2 = new House();
        petHome3 = new House();
        petHome4 = new House();
        petHome5 = new House();
        
        // Create pets
        rusty = new Dog();
        rusty.setName("Rusty");
        
        snowball = new Cat();
        snowball.setName("Snowball");
        
        zeus = new Dog();
        zeus.setName("Zeus");
        
        misty = new Cat();
        misty.setName("Misty");
        
        smokey = new Cat();
        smokey.setName("Smokey");
        
        ace = new Dog();
        ace.setName("Ace");
        
        king = new Dog();
        king.setName("King");
        
        rover = new Dog();
        rover.setName("Rover");
        
        // Set up house 1: Rusty, Snowball, Zeus
        petHome1.addPet(rusty);
        petHome1.addPet(snowball);
        petHome1.addPet(zeus);
        
        // Set up house 2: Misty, Smokey
        petHome2.addPet(misty);
        petHome2.addPet(smokey);
        
        // Set up house 3: Ace, King
        petHome3.addPet(ace);
        petHome3.addPet(king);
        
        // House 4 remains empty
        
        // Set up house 5: Rover
        petHome5.addPet(rover);
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = petHome1.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals("Should contain 2 dogs", 2, result.size());
        assertTrue("Should contain Rusty", containsPetWithName(result, "Rusty"));
        assertTrue("Should contain Zeus", containsPetWithName(result, "Zeus"));
        
        // Verify no cats are included
        for (Pet pet : result) {
            assertTrue("All retrieved pets should be dogs", pet instanceof Dog);
        }
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = petHome2.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals("Should contain 2 cats", 2, result.size());
        assertTrue("Should contain Misty", containsPetWithName(result, "Misty"));
        assertTrue("Should contain Smokey", containsPetWithName(result, "Smokey"));
        
        // Verify all are cats
        for (Pet pet : result) {
            assertTrue("All retrieved pets should be cats", pet instanceof Cat);
        }
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> result = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertTrue("Should return empty list when no matching pets found", result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertTrue("Should return empty list when house is empty", result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = petHome5.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals("Should contain 1 dog", 1, result.size());
        assertTrue("Should contain Rover", containsPetWithName(result, "Rover"));
        assertTrue("Retrieved pet should be a dog", result.get(0) instanceof Dog);
    }
    
    // Helper method to check if a list contains a pet with specific name
    private boolean containsPetWithName(List<Pet> pets, String name) {
        for (Pet pet : pets) {
            if (pet.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}