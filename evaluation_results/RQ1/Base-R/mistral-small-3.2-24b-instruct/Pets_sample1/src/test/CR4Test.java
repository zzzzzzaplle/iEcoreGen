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
        rusty = new Dog("Rusty");
        snowball = new Cat("Snowball");
        zeus = new Dog("Zeus");
        misty = new Cat("Misty");
        smokey = new Cat("Smokey");
        ace = new Dog("Ace");
        king = new Dog("King");
        rover = new Dog("Rover");
        
        // Set up petHome1: Rusty, Snowball, Zeus
        petHome1.addPet(rusty);
        petHome1.addPet(snowball);
        petHome1.addPet(zeus);
        
        // Set up petHome2: Misty, Smokey
        petHome2.addPet(misty);
        petHome2.addPet(smokey);
        
        // Set up petHome3: Ace, King
        petHome3.addPet(ace);
        petHome3.addPet(king);
        
        // petHome4 remains empty
        
        // Set up petHome5: Rover
        petHome5.addPet(rover);
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> dogs = petHome1.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, dogs.size());
        assertTrue(dogs.contains(rusty));
        assertTrue(dogs.contains(zeus));
        assertFalse(dogs.contains(snowball)); // Should not contain the cat
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> cats = petHome2.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, cats.size());
        assertTrue(cats.contains(misty));
        assertTrue(cats.contains(smokey));
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> cats = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertTrue(cats.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // Action: Retrieve pets of input type "dog" from PetHome4
        List<Pet> dogs = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertTrue(dogs.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // Action: Retrieve pets of input type "dog" from PetHome5
        List<Pet> dogs = petHome5.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, dogs.size());
        assertEquals("Rover", dogs.get(0).getName());
        assertTrue(dogs.get(0) instanceof Dog);
    }
}