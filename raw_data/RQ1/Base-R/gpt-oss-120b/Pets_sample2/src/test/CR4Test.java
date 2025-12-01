import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private House house;
    private Dog dog1, dog2, dog3;
    private Cat cat1, cat2;
    
    @Before
    public void setUp() {
        // Create pets for reuse in tests
        dog1 = new Dog();
        dog1.setName("Rusty");
        
        dog2 = new Dog();
        dog2.setName("Zeus");
        
        dog3 = new Dog();
        dog3.setName("Rover");
        
        cat1 = new Cat();
        cat1.setName("Snowball");
        
        cat2 = new Cat();
        cat2.setName("Misty");
    }
    
    @Test
    public void testCase1_RetrieveOnlyDogsFromMixedHouse() {
        // SetUp: Create House "PetHome1" and add Dog "Rusty", Cat "Snowball", Dog "Zeus"
        house = new House();
        house.addPet(dog1);  // Dog "Rusty"
        house.addPet(cat1);  // Cat "Snowball"
        house.addPet(dog2);  // Dog "Zeus"
        
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, result.size());
        assertTrue(result.contains(dog1));
        assertTrue(result.contains(dog2));
        assertFalse(result.contains(cat1));
    }
    
    @Test
    public void testCase2_RetrieveCatsFromCatOnlyHouse() {
        // SetUp: Create House "PetHome2" and add Cat "Misty", Cat "Smokey"
        house = new House();
        Cat smokey = new Cat();
        smokey.setName("Smokey");
        
        house.addPet(cat2);  // Cat "Misty"
        house.addPet(smokey); // Cat "Smokey"
        
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = house.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, result.size());
        assertTrue(result.contains(cat2));
        assertTrue(result.contains(smokey));
    }
    
    @Test
    public void testCase3_RetrieveTypeWithNoMatches() {
        // SetUp: Create House "PetHome3" and add Dog "Ace", Dog "King"
        house = new House();
        Dog ace = new Dog();
        ace.setName("Ace");
        Dog king = new Dog();
        king.setName("King");
        
        house.addPet(ace);  // Dog "Ace"
        house.addPet(king); // Dog "King"
        
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> result = house.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFromEmptyHouse() {
        // SetUp: Create empty House "PetHome4"
        house = new House();
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CaseSensitiveTypeRetrieval() {
        // SetUp: Create House "PetHome5" and add Dog "Rover"
        house = new House();
        house.addPet(dog3);  // Dog "Rover"
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, result.size());
        assertTrue(result.contains(dog3));
        assertEquals("Rover", result.get(0).getName());
    }
}