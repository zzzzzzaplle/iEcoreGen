import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private House house;
    private Dog dog1;
    private Dog dog2;
    private Cat cat1;
    private Cat cat2;
    
    @Before
    public void setUp() {
        house = new House();
        dog1 = new Dog();
        dog2 = new Dog();
        cat1 = new Cat();
        cat2 = new Cat();
    }
    
    @Test
    public void testCase1_RetrieveOnlyDogsFromMixedHouse() {
        // SetUp: Create House "PetHome1" and add Dog "Rusty", Cat "Snowball", Dog "Zeus"
        dog1.setName("Rusty");
        cat1.setName("Snowball");
        dog2.setName("Zeus");
        
        house.addPet(dog1);
        house.addPet(cat1);
        house.addPet(dog2);
        
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, result.size());
        assertEquals("Rusty", result.get(0).getName());
        assertEquals("Zeus", result.get(1).getName());
    }
    
    @Test
    public void testCase2_RetrieveCatsFromCatOnlyHouse() {
        // SetUp: Create House "PetHome2" and add Cat "Misty", Cat "Smokey"
        cat1.setName("Misty");
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
    public void testCase3_RetrieveTypeWithNoMatches() {
        // SetUp: Create House "PetHome3" and add Dog "Ace", Dog "King"
        dog1.setName("Ace");
        dog2.setName("King");
        
        house.addPet(dog1);
        house.addPet(dog2);
        
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> result = house.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFromEmptyHouse() {
        // SetUp: Create empty House "PetHome4"
        // House is already empty from setup
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CaseSensitiveTypeRetrieval() {
        // SetUp: Create House "PetHome5" and add Dog "Rover"
        dog1.setName("Rover");
        house.addPet(dog1);
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, result.size());
        assertEquals("Rover", result.get(0).getName());
    }
}