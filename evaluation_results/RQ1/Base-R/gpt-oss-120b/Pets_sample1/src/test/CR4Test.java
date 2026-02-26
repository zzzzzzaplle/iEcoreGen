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
        
        // Setup PetHome1: Dog "Rusty", Cat "Snowball", Dog "Zeus"
        Dog rusty = new Dog();
        rusty.setName("Rusty");
        petHome1.addPet(rusty);
        
        Cat snowball = new Cat();
        snowball.setName("Snowball");
        petHome1.addPet(snowball);
        
        Dog zeus = new Dog();
        zeus.setName("Zeus");
        petHome1.addPet(zeus);
        
        // Setup PetHome2: Cat "Misty", Cat "Smokey"
        Cat misty = new Cat();
        misty.setName("Misty");
        petHome2.addPet(misty);
        
        Cat smokey = new Cat();
        smokey.setName("Smokey");
        petHome2.addPet(smokey);
        
        // Setup PetHome3: Dog "Ace", Dog "King"
        Dog ace = new Dog();
        ace.setName("Ace");
        petHome3.addPet(ace);
        
        Dog king = new Dog();
        king.setName("King");
        petHome3.addPet(king);
        
        // PetHome4 remains empty (no setup needed)
        
        // Setup PetHome5: Dog "Rover"
        Dog rover = new Dog();
        rover.setName("Rover");
        petHome5.addPet(rover);
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Animal> result = petHome1.getPetsByType("dog");
        
        // Verify the size of the result list
        assertEquals("Should contain exactly 2 dogs", 2, result.size());
        
        // Verify the names of the dogs in the result
        List<String> names = new ArrayList<>();
        for (Animal animal : result) {
            names.add(animal.getName());
        }
        assertTrue("Should contain Rusty", names.contains("Rusty"));
        assertTrue("Should contain Zeus", names.contains("Zeus"));
        assertFalse("Should not contain Snowball", names.contains("Snowball"));
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Animal> result = petHome2.getPetsByType("cat");
        
        // Verify the size of the result list
        assertEquals("Should contain exactly 2 cats", 2, result.size());
        
        // Verify the names of the cats in the result
        List<String> names = new ArrayList<>();
        for (Animal animal : result) {
            names.add(animal.getName());
        }
        assertTrue("Should contain Misty", names.contains("Misty"));
        assertTrue("Should contain Smokey", names.contains("Smokey"));
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Animal> result = petHome3.getPetsByType("cat");
        
        // Verify the result is an empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // Action: Retrieve pets of input type "dog" from PetHome4
        List<Animal> result = petHome4.getPetsByType("dog");
        
        // Verify the result is an empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // Action: Retrieve pets of input type "dog" from PetHome5
        List<Animal> result = petHome5.getPetsByType("dog");
        
        // Verify the size of the result list
        assertEquals("Should contain exactly 1 dog", 1, result.size());
        
        // Verify the name of the dog in the result
        assertEquals("Should contain Rover", "Rover", result.get(0).getName());
    }
}