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
        // Initialize houses for testing
        petHome1 = new House();
        petHome2 = new House();
        petHome3 = new House();
        petHome4 = new House();
        petHome5 = new House();
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // SetUp: Create pets and add to house
        Dog rusty = new Dog();
        rusty.setName("Rusty");
        Cat snowball = new Cat();
        snowball.setName("Snowball");
        Dog zeus = new Dog();
        zeus.setName("Zeus");
        
        petHome1.addPet(rusty);
        petHome1.addPet(snowball);
        petHome1.addPet(zeus);
        
        // Action: Retrieve pets of type "dog"
        List<Pet> dogs = petHome1.getPetsByType("dog");
        
        // Expected Output: List containing Rusty and Zeus
        assertEquals(2, dogs.size());
        assertTrue(dogs.contains(rusty));
        assertTrue(dogs.contains(zeus));
        assertFalse(dogs.contains(snowball));
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // SetUp: Create cats and add to house
        Cat misty = new Cat();
        misty.setName("Misty");
        Cat smokey = new Cat();
        smokey.setName("Smokey");
        
        petHome2.addPet(misty);
        petHome2.addPet(smokey);
        
        // Action: Retrieve pets of type "cat"
        List<Pet> cats = petHome2.getPetsByType("cat");
        
        // Expected Output: List containing Misty and Smokey
        assertEquals(2, cats.size());
        assertTrue(cats.contains(misty));
        assertTrue(cats.contains(smokey));
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // SetUp: Create dogs and add to house
        Dog ace = new Dog();
        ace.setName("Ace");
        Dog king = new Dog();
        king.setName("King");
        
        petHome3.addPet(ace);
        petHome3.addPet(king);
        
        // Action: Retrieve pets of type "cat"
        List<Pet> cats = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertEquals(0, cats.size());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // SetUp: Empty house (already initialized in setUp)
        
        // Action: Retrieve pets of type "dog"
        List<Pet> dogs = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertEquals(0, dogs.size());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // SetUp: Create dog and add to house
        Dog rover = new Dog();
        rover.setName("Rover");
        
        petHome5.addPet(rover);
        
        // Action: Retrieve pets of type "dog"
        List<Pet> dogs = petHome5.getPetsByType("dog");
        
        // Expected Output: List containing Rover
        assertEquals(1, dogs.size());
        assertTrue(dogs.contains(rover));
    }
}