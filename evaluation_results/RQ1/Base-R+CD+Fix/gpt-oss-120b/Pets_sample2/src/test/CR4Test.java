import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private House house;
    
    @Before
    public void setUp() {
        house = new House();
    }
    
    @Test
    public void testCase1_RetrieveOnlyDogsFromMixedHouse() {
        // SetUp: Create House "PetHome1" and add Dog "Rusty", Cat "Snowball", Dog "Zeus"
        House petHome1 = new House();
        Dog dog1 = new Dog();
        dog1.setName("Rusty");
        Cat cat1 = new Cat();
        cat1.setName("Snowball");
        Dog dog2 = new Dog();
        dog2.setName("Zeus");
        
        petHome1.addPet(dog1);
        petHome1.addPet(cat1);
        petHome1.addPet(dog2);
        
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = petHome1.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, result.size());
        assertEquals("Rusty", result.get(0).getName());
        assertEquals("Zeus", result.get(1).getName());
    }
    
    @Test
    public void testCase2_RetrieveCatsFromCatOnlyHouse() {
        // SetUp: Create House "PetHome2" and add Cat "Misty", Cat "Smokey"
        House petHome2 = new House();
        Cat cat1 = new Cat();
        cat1.setName("Misty");
        Cat cat2 = new Cat();
        cat2.setName("Smokey");
        
        petHome2.addPet(cat1);
        petHome2.addPet(cat2);
        
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = petHome2.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, result.size());
        assertEquals("Misty", result.get(0).getName());
        assertEquals("Smokey", result.get(1).getName());
    }
    
    @Test
    public void testCase3_RetrieveTypeWithNoMatches() {
        // SetUp: Create House "PetHome3" and add Dog "Ace", Dog "King"
        House petHome3 = new House();
        Dog dog1 = new Dog();
        dog1.setName("Ace");
        Dog dog2 = new Dog();
        dog2.setName("King");
        
        petHome3.addPet(dog1);
        petHome3.addPet(dog2);
        
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> result = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFromEmptyHouse() {
        // SetUp: Create empty House "PetHome4"
        House petHome4 = new House();
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CaseSensitiveTypeRetrieval() {
        // SetUp: Create House "PetHome5" and add Dog "Rover"
        House petHome5 = new House();
        Dog dog1 = new Dog();
        dog1.setName("Rover");
        
        petHome5.addPet(dog1);
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = petHome5.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, result.size());
        assertEquals("Rover", result.get(0).getName());
    }
}