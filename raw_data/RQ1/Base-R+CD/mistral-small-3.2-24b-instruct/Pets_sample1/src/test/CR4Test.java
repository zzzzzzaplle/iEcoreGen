import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    private House house;
    
    @Before
    public void setUp() {
        house = new House();
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // SetUp: Create House "PetHome1" - already created in @Before
        // Add Dog "Rusty", Cat "Snowball", Dog "Zeus"
        Pet dog1 = new Dog();
        dog1.setName("Rusty");
        Pet cat = new Cat();
        cat.setName("Snowball");
        Pet dog2 = new Dog();
        dog2.setName("Zeus");
        
        house.addPet(dog1);
        house.addPet(cat);
        house.addPet(dog2);
        
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, result.size());
        assertEquals("Rusty", result.get(0).getName());
        assertEquals("Zeus", result.get(1).getName());
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // SetUp: Create House "PetHome2" - already created in @Before
        // Add Cat "Misty", Cat "Smokey"
        Pet cat1 = new Cat();
        cat1.setName("Misty");
        Pet cat2 = new Cat();
        cat2.setName("Smokey");
        
        house.addPet(cat1);
        house.addPet(cat2);
        
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = house.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, result.size());
        assertEquals("Misty", result.get(0).getName());
        assertEquals("Smokey", result.get(1).getName());
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // SetUp: Create House "PetHome3" - already created in @Before
        // Add Dog "Ace", Dog "King"
        Pet dog1 = new Dog();
        dog1.setName("Ace");
        Pet dog2 = new Dog();
        dog2.setName("King");
        
        house.addPet(dog1);
        house.addPet(dog2);
        
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> result = house.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // SetUp: Create empty House "PetHome4" - already created in @Before
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // SetUp: Create House "PetHome5" - already created in @Before
        // Add Dog "Rover"
        Pet dog = new Dog();
        dog.setName("Rover");
        
        house.addPet(dog);
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, result.size());
        assertEquals("Rover", result.get(0).getName());
    }
}