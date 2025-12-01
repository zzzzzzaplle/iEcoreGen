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
    
    private Dog dogRusty;
    private Cat catSnowball;
    private Dog dogZeus;
    private Cat catMisty;
    private Cat catSmokey;
    private Dog dogAce;
    private Dog dogKing;
    private Dog dogRover;
    
    @Before
    public void setUp() {
        // Initialize all houses
        petHome1 = new House();
        petHome2 = new House();
        petHome3 = new House();
        petHome4 = new House();
        petHome5 = new House();
        
        // Initialize all pets
        dogRusty = new Dog();
        dogRusty.setName("Rusty");
        
        catSnowball = new Cat();
        catSnowball.setName("Snowball");
        
        dogZeus = new Dog();
        dogZeus.setName("Zeus");
        
        catMisty = new Cat();
        catMisty.setName("Misty");
        
        catSmokey = new Cat();
        catSmokey.setName("Smokey");
        
        dogAce = new Dog();
        dogAce.setName("Ace");
        
        dogKing = new Dog();
        dogKing.setName("King");
        
        dogRover = new Dog();
        dogRover.setName("Rover");
        
        // Set up House 1: Mixed pets
        petHome1.addPet(dogRusty);
        petHome1.addPet(catSnowball);
        petHome1.addPet(dogZeus);
        
        // Set up House 2: Cats only
        petHome2.addPet(catMisty);
        petHome2.addPet(catSmokey);
        
        // Set up House 3: Dogs only
        petHome3.addPet(dogAce);
        petHome3.addPet(dogKing);
        
        // House 4 remains empty
        
        // Set up House 5: Single dog
        petHome5.addPet(dogRover);
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = petHome1.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals("Should return 2 dogs", 2, result.size());
        
        // Check that both dogs are present and no cats
        boolean hasRusty = false;
        boolean hasZeus = false;
        boolean hasCats = false;
        
        for (Pet pet : result) {
            if (pet.getName().equals("Rusty")) {
                hasRusty = true;
            } else if (pet.getName().equals("Zeus")) {
                hasZeus = true;
            }
            if (pet instanceof Cat) {
                hasCats = true;
            }
        }
        
        assertTrue("Should contain Rusty", hasRusty);
        assertTrue("Should contain Zeus", hasZeus);
        assertFalse("Should not contain cats", hasCats);
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = petHome2.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals("Should return 2 cats", 2, result.size());
        
        // Check that both cats are present and no dogs
        boolean hasMisty = false;
        boolean hasSmokey = false;
        boolean hasDogs = false;
        
        for (Pet pet : result) {
            if (pet.getName().equals("Misty")) {
                hasMisty = true;
            } else if (pet.getName().equals("Smokey")) {
                hasSmokey = true;
            }
            if (pet instanceof Dog) {
                hasDogs = true;
            }
        }
        
        assertTrue("Should contain Misty", hasMisty);
        assertTrue("Should contain Smokey", hasSmokey);
        assertFalse("Should not contain dogs", hasDogs);
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
        // Action: Retrieve pets of input type "dog" from PetHome5
        List<Pet> result = petHome5.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals("Should return 1 dog", 1, result.size());
        assertEquals("Should return Rover", "Rover", result.get(0).getName());
        assertTrue("Returned pet should be a Dog", result.get(0) instanceof Dog);
    }
}