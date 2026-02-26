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
        // Create all houses
        petHome1 = new House();
        petHome2 = new House();
        petHome3 = new House();
        petHome4 = new House();
        petHome5 = new House();
        
        // Create pets with names
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
        
        // Set up houses with pets as per test specifications
        petHome1.addPet(rusty);
        petHome1.addPet(snowball);
        petHome1.addPet(zeus);
        
        petHome2.addPet(misty);
        petHome2.addPet(smokey);
        
        petHome3.addPet(ace);
        petHome3.addPet(king);
        
        // petHome4 remains empty as specified
        // petHome5 gets Rover added in its specific test
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = petHome1.getPetsByType("dog");
        
        // Verify the result contains only dogs
        assertEquals("Should contain 2 dogs", 2, result.size());
        
        // Check that both Rusty and Zeus are in the result
        boolean foundRusty = false;
        boolean foundZeus = false;
        for (Pet pet : result) {
            if ("Rusty".equals(pet.getName())) {
                foundRusty = true;
                assertTrue("Rusty should be a Dog", pet instanceof Dog);
            }
            if ("Zeus".equals(pet.getName())) {
                foundZeus = true;
                assertTrue("Zeus should be a Dog", pet instanceof Dog);
            }
        }
        assertTrue("Should contain Rusty", foundRusty);
        assertTrue("Should contain Zeus", foundZeus);
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = petHome2.getPetsByType("cat");
        
        // Verify the result contains only cats
        assertEquals("Should contain 2 cats", 2, result.size());
        
        // Check that both Misty and Smokey are in the result
        boolean foundMisty = false;
        boolean foundSmokey = false;
        for (Pet pet : result) {
            if ("Misty".equals(pet.getName())) {
                foundMisty = true;
                assertTrue("Misty should be a Cat", pet instanceof Cat);
            }
            if ("Smokey".equals(pet.getName())) {
                foundSmokey = true;
                assertTrue("Smokey should be a Cat", pet instanceof Cat);
            }
        }
        assertTrue("Should contain Misty", foundMisty);
        assertTrue("Should contain Smokey", foundSmokey);
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> result = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // Action: Retrieve pets of input type "dog" from PetHome4
        List<Pet> result = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // Add Rover to PetHome5
        petHome5.addPet(rover);
        
        // Action: Retrieve pets of input type "dog" from PetHome5
        List<Pet> result = petHome5.getPetsByType("dog");
        
        // Verify the result contains only Rover
        assertEquals("Should contain 1 dog", 1, result.size());
        
        // Check that Rover is in the result and is a Dog
        Pet retrievedPet = result.get(0);
        assertEquals("Should be Rover", "Rover", retrievedPet.getName());
        assertTrue("Should be a Dog", retrievedPet instanceof Dog);
    }
}