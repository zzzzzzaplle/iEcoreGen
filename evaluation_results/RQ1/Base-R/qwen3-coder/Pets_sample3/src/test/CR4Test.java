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
        // Create houses for test cases
        petHome1 = new House();
        petHome2 = new House();
        petHome3 = new House();
        petHome4 = new House();
        petHome5 = new House();
        
        // Set up PetHome1 with mixed pets
        Dog dog1 = new Dog();
        dog1.setName("Rusty");
        Cat cat1 = new Cat();
        cat1.setName("Snowball");
        Dog dog2 = new Dog();
        dog2.setName("Zeus");
        
        petHome1.addPet(dog1);
        petHome1.addPet(cat1);
        petHome1.addPet(dog2);
        
        // Set up PetHome2 with cats only
        Cat cat2 = new Cat();
        cat2.setName("Misty");
        Cat cat3 = new Cat();
        cat3.setName("Smokey");
        
        petHome2.addPet(cat2);
        petHome2.addPet(cat3);
        
        // Set up PetHome3 with dogs only
        Dog dog3 = new Dog();
        dog3.setName("Ace");
        Dog dog4 = new Dog();
        dog4.setName("King");
        
        petHome3.addPet(dog3);
        petHome3.addPet(dog4);
        
        // PetHome4 remains empty
        
        // Set up PetHome5 with one dog
        Dog dog5 = new Dog();
        dog5.setName("Rover");
        petHome5.addPet(dog5);
    }
    
    @Test
    public void testCase1_RetrieveOnlyDogsFromMixedHouse() {
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = petHome1.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, result.size());
        
        // Verify the names of returned dogs
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
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, result.size());
        
        // Verify the names of returned cats
        List<String> catNames = new ArrayList<>();
        for (Pet pet : result) {
            catNames.add(pet.getName());
        }
        
        assertTrue(catNames.contains("Misty"));
        assertTrue(catNames.contains("Smokey"));
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
        
        // Expected Output: ["Rover"]
        assertEquals(1, result.size());
        assertEquals("Rover", result.get(0).getName());
    }
}