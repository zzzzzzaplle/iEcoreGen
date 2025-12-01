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
        // Initialize houses for test cases
        petHome1 = new House();
        petHome2 = new House();
        petHome3 = new House();
        petHome4 = new House();
        petHome5 = new House();
        
        // Set up PetHome1 with dogs and cats
        Dog rusty = new Dog();
        rusty.setName("Rusty");
        petHome1.addPet(rusty);
        
        Cat snowball = new Cat();
        snowball.setName("Snowball");
        petHome1.addPet(snowball);
        
        Dog zeus = new Dog();
        zeus.setName("Zeus");
        petHome1.addPet(zeus);
        
        // Set up PetHome2 with cats only
        Cat misty = new Cat();
        misty.setName("Misty");
        petHome2.addPet(misty);
        
        Cat smokey = new Cat();
        smokey.setName("Smokey");
        petHome2.addPet(smokey);
        
        // Set up PetHome3 with dogs only
        Dog ace = new Dog();
        ace.setName("Ace");
        petHome3.addPet(ace);
        
        Dog king = new Dog();
        king.setName("King");
        petHome3.addPet(king);
        
        // Set up PetHome5 with one dog
        Dog rover = new Dog();
        rover.setName("Rover");
        petHome5.addPet(rover);
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = petHome1.getPetsByType("dog");
        
        // Verify the result contains exactly 2 dogs
        assertEquals(2, result.size());
        
        // Verify the dogs are "Rusty" and "Zeus"
        boolean foundRusty = false;
        boolean foundZeus = false;
        
        for (Pet pet : result) {
            if ("Rusty".equals(pet.getName())) {
                foundRusty = true;
            }
            if ("Zeus".equals(pet.getName())) {
                foundZeus = true;
            }
        }
        
        assertTrue("Should contain Rusty", foundRusty);
        assertTrue("Should contain Zeus", foundZeus);
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = petHome2.getPetsByType("cat");
        
        // Verify the result contains exactly 2 cats
        assertEquals(2, result.size());
        
        // Verify the cats are "Misty" and "Smokey"
        boolean foundMisty = false;
        boolean foundSmokey = false;
        
        for (Pet pet : result) {
            if ("Misty".equals(pet.getName())) {
                foundMisty = true;
            }
            if ("Smokey".equals(pet.getName())) {
                foundSmokey = true;
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
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // Action: Retrieve pets of input type "dog" from PetHome4
        List<Pet> result = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // Action: Retrieve pets of input type "dog" from PetHome5
        List<Pet> result = petHome5.getPetsByType("dog");
        
        // Verify the result contains exactly 1 dog
        assertEquals(1, result.size());
        
        // Verify the dog is "Rover"
        assertEquals("Rover", result.get(0).getName());
    }
}