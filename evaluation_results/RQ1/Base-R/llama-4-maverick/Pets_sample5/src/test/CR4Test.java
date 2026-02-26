import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // SetUp: Create House "PetHome1" and add Dog "Rusty", Cat "Snowball", Dog "Zeus"
        House house = new House();
        Dog dog1 = new Dog();
        dog1.setName("Rusty");
        Cat cat1 = new Cat();
        cat1.setName("Snowball");
        Dog dog2 = new Dog();
        dog2.setName("Zeus");
        
        house.addPet(dog1);
        house.addPet(cat1);
        house.addPet(dog2);
        
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = house.getPetsOfType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, result.size());
        assertEquals("Rusty", result.get(0).getName());
        assertEquals("Zeus", result.get(1).getName());
        assertEquals("dog", result.get(0).getType());
        assertEquals("dog", result.get(1).getType());
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // SetUp: Create House "PetHome2" and add Cat "Misty", Cat "Smokey"
        House house = new House();
        Cat cat1 = new Cat();
        cat1.setName("Misty");
        Cat cat2 = new Cat();
        cat2.setName("Smokey");
        
        house.addPet(cat1);
        house.addPet(cat2);
        
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = house.getPetsOfType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, result.size());
        assertEquals("Misty", result.get(0).getName());
        assertEquals("Smokey", result.get(1).getName());
        assertEquals("cat", result.get(0).getType());
        assertEquals("cat", result.get(1).getType());
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // SetUp: Create House "PetHome3" and add Dog "Ace", Dog "King"
        House house = new House();
        Dog dog1 = new Dog();
        dog1.setName("Ace");
        Dog dog2 = new Dog();
        dog2.setName("King");
        
        house.addPet(dog1);
        house.addPet(dog2);
        
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> result = house.getPetsOfType("cat");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // SetUp: Create empty House "PetHome4"
        House house = new House();
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house.getPetsOfType("dog");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // SetUp: Create House "PetHome5" and add Dog "Rover"
        House house = new House();
        Dog dog1 = new Dog();
        dog1.setName("Rover");
        
        house.addPet(dog1);
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house.getPetsOfType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, result.size());
        assertEquals("Rover", result.get(0).getName());
        assertEquals("dog", result.get(0).getType());
    }
}