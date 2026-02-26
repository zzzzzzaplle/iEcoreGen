import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    private House house;
    private Dog dog1;
    private Dog dog2;
    private Cat cat1;
    private Cat cat2;
    
    @Before
    public void setUp() {
        // Reset objects before each test
        house = null;
        dog1 = null;
        dog2 = null;
        cat1 = null;
        cat2 = null;
    }
    
    @Test
    public void testCase1_RetrieveOnlyDogsFromMixedHouse() {
        // SetUp: Create House "PetHome1" and add Dog "Rusty", Cat "Snowball", Dog "Zeus"
        house = new House();
        dog1 = new Dog();
        dog1.setName("Rusty");
        cat1 = new Cat();
        cat1.setName("Snowball");
        dog2 = new Dog();
        dog2.setName("Zeus");
        
        house.addPet(dog1);
        house.addPet(cat1);
        house.addPet(dog2);
        
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, result.size());
        assertTrue(result.contains(dog1));
        assertTrue(result.contains(dog2));
        assertFalse(result.contains(cat1));
        
        // Verify names
        List<String> names = new ArrayList<>();
        for (Pet pet : result) {
            names.add(pet.getName());
        }
        assertTrue(names.contains("Rusty"));
        assertTrue(names.contains("Zeus"));
    }
    
    @Test
    public void testCase2_RetrieveCatsFromCatOnlyHouse() {
        // SetUp: Create House "PetHome2" and add Cat "Misty", Cat "Smokey"
        house = new House();
        cat1 = new Cat();
        cat1.setName("Misty");
        cat2 = new Cat();
        cat2.setName("Smokey");
        
        house.addPet(cat1);
        house.addPet(cat2);
        
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = house.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, result.size());
        assertTrue(result.contains(cat1));
        assertTrue(result.contains(cat2));
        
        // Verify names
        List<String> names = new ArrayList<>();
        for (Pet pet : result) {
            names.add(pet.getName());
        }
        assertTrue(names.contains("Misty"));
        assertTrue(names.contains("Smokey"));
    }
    
    @Test
    public void testCase3_RetrieveTypeWithNoMatches() {
        // SetUp: Create House "PetHome3" and add Dog "Ace", Dog "King"
        house = new House();
        dog1 = new Dog();
        dog1.setName("Ace");
        dog2 = new Dog();
        dog2.setName("King");
        
        house.addPet(dog1);
        house.addPet(dog2);
        
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> result = house.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFromEmptyHouse() {
        // SetUp: Create empty House "PetHome4"
        house = new House();
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CaseSensitiveTypeRetrieval() {
        // SetUp: Create House "PetHome5" and add Dog "Rover"
        house = new House();
        dog1 = new Dog();
        dog1.setName("Rover");
        
        house.addPet(dog1);
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, result.size());
        assertEquals("Rover", result.get(0).getName());
        assertTrue(result.get(0) instanceof Dog);
    }
}