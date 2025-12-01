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
        // Create all houses needed for tests
        petHome1 = new House();
        petHome2 = new House();
        petHome3 = new House();
        petHome4 = new House();
        petHome5 = new House();
        
        // Set up PetHome1 with Dog "Rusty", Cat "Snowball", Dog "Zeus"
        Dog rusty = new Dog();
        rusty.setName("Rusty");
        Cat snowball = new Cat();
        snowball.setName("Snowball");
        Dog zeus = new Dog();
        zeus.setName("Zeus");
        
        petHome1.addPet(rusty);
        petHome1.addPet(snowball);
        petHome1.addPet(zeus);
        
        // Set up PetHome2 with Cat "Misty", Cat "Smokey"
        Cat misty = new Cat();
        misty.setName("Misty");
        Cat smokey = new Cat();
        smokey.setName("Smokey");
        
        petHome2.addPet(misty);
        petHome2.addPet(smokey);
        
        // Set up PetHome3 with Dog "Ace", Dog "King"
        Dog ace = new Dog();
        ace.setName("Ace");
        Dog king = new Dog();
        king.setName("King");
        
        petHome3.addPet(ace);
        petHome3.addPet(king);
        
        // Set up PetHome5 with Dog "Rover"
        Dog rover = new Dog();
        rover.setName("Rover");
        petHome5.addPet(rover);
    }
    
    @Test
    public void testCase1_RetrieveOnlyDogsFromMixedHouse() {
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = petHome1.getPetsByType("dog");
        
        // Verify the result contains exactly 2 dogs
        assertEquals(2, result.size());
        
        // Verify the names match expected dogs
        List<String> actualNames = new ArrayList<>();
        for (Pet pet : result) {
            actualNames.add(pet.getName());
        }
        
        assertTrue(actualNames.contains("Rusty"));
        assertTrue(actualNames.contains("Zeus"));
        assertFalse(actualNames.contains("Snowball")); // Should not contain cat
    }
    
    @Test
    public void testCase2_RetrieveCatsFromCatOnlyHouse() {
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = petHome2.getPetsByType("cat");
        
        // Verify the result contains exactly 2 cats
        assertEquals(2, result.size());
        
        // Verify the names match expected cats
        List<String> actualNames = new ArrayList<>();
        for (Pet pet : result) {
            actualNames.add(pet.getName());
        }
        
        assertTrue(actualNames.contains("Misty"));
        assertTrue(actualNames.contains("Smokey"));
    }
    
    @Test
    public void testCase3_RetrieveTypeWithNoMatches() {
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> result = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFromEmptyHouse() {
        // Action: Retrieve pets of input type "dog" from PetHome4
        List<Pet> result = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CaseSensitiveTypeRetrieval() {
        // Action: Retrieve pets of input type "dog" from PetHome5
        List<Pet> result = petHome5.getPetsByType("dog");
        
        // Verify the result contains exactly 1 dog
        assertEquals(1, result.size());
        
        // Verify the name matches expected dog
        assertEquals("Rover", result.get(0).getName());
    }
}