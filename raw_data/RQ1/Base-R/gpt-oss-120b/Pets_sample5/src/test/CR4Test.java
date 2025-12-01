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
        // Set up test houses and animals before each test
        petHome1 = new House();
        petHome2 = new House();
        petHome3 = new House();
        petHome4 = new House();
        petHome5 = new House();
        
        // Setup for PetHome1: Dog "Rusty", Cat "Snowball", Dog "Zeus"
        Dog rusty = new Dog("Rusty");
        Cat snowball = new Cat("Snowball");
        Dog zeus = new Dog("Zeus");
        petHome1.addPet(rusty);
        petHome1.addPet(snowball);
        petHome1.addPet(zeus);
        
        // Setup for PetHome2: Cat "Misty", Cat "Smokey"
        Cat misty = new Cat("Misty");
        Cat smokey = new Cat("Smokey");
        petHome2.addPet(misty);
        petHome2.addPet(smokey);
        
        // Setup for PetHome3: Dog "Ace", Dog "King"
        Dog ace = new Dog("Ace");
        Dog king = new Dog("King");
        petHome3.addPet(ace);
        petHome3.addPet(king);
        
        // Setup for PetHome5: Dog "Rover"
        Dog rover = new Dog("Rover");
        petHome5.addPet(rover);
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Animal> result = petHome1.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, result.size());
        
        // Verify both are dogs and have correct names
        boolean foundRusty = false;
        boolean foundZeus = false;
        
        for (Animal animal : result) {
            assertTrue(animal instanceof Dog);
            String name = animal.getName();
            if ("Rusty".equals(name)) {
                foundRusty = true;
            } else if ("Zeus".equals(name)) {
                foundZeus = true;
            }
        }
        
        assertTrue("Should contain Rusty", foundRusty);
        assertTrue("Should contain Zeus", foundZeus);
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Animal> result = petHome2.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, result.size());
        
        // Verify both are cats and have correct names
        boolean foundMisty = false;
        boolean foundSmokey = false;
        
        for (Animal animal : result) {
            assertTrue(animal instanceof Cat);
            String name = animal.getName();
            if ("Misty".equals(name)) {
                foundMisty = true;
            } else if ("Smokey".equals(name)) {
                foundSmokey = true;
            }
        }
        
        assertTrue("Should contain Misty", foundMisty);
        assertTrue("Should contain Smokey", foundSmokey);
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Animal> result = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // Action: Retrieve pets of input type "dog" from PetHome4
        List<Animal> result = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // Action: Retrieve pets of input type "dog" from PetHome5
        List<Animal> result = petHome5.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, result.size());
        assertEquals("Rover", result.get(0).getName());
        assertTrue(result.get(0) instanceof Dog);
    }
}