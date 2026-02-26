import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // SetUp: Create House "PetHome1"
        House petHome1 = new House();
        
        // SetUp: Add Dog "Rusty", Cat "Snowball", Dog "Zeus"
        Dog rusty = new Dog("Rusty");
        Cat snowball = new Cat("Snowball");
        Dog zeus = new Dog("Zeus");
        petHome1.addPet(rusty);
        petHome1.addPet(snowball);
        petHome1.addPet(zeus);
        
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = petHome1.getPetsOfType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, result.size());
        assertTrue(result.contains(rusty));
        assertTrue(result.contains(zeus));
        
        // Verify names match expected
        List<String> names = new ArrayList<>();
        for (Pet pet : result) {
            names.add(pet.getName());
        }
        assertTrue(names.contains("Rusty"));
        assertTrue(names.contains("Zeus"));
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // SetUp: Create House "PetHome2"
        House petHome2 = new House();
        
        // SetUp: Add Cat "Misty", Cat "Smokey"
        Cat misty = new Cat("Misty");
        Cat smokey = new Cat("Smokey");
        petHome2.addPet(misty);
        petHome2.addPet(smokey);
        
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = petHome2.getPetsOfType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, result.size());
        assertTrue(result.contains(misty));
        assertTrue(result.contains(smokey));
        
        // Verify names match expected
        List<String> names = new ArrayList<>();
        for (Pet pet : result) {
            names.add(pet.getName());
        }
        assertTrue(names.contains("Misty"));
        assertTrue(names.contains("Smokey"));
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // SetUp: Create House "PetHome3"
        House petHome3 = new House();
        
        // SetUp: Add Dog "Ace", Dog "King"
        Dog ace = new Dog("Ace");
        Dog king = new Dog("King");
        petHome3.addPet(ace);
        petHome3.addPet(king);
        
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> result = petHome3.getPetsOfType("cat");
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // SetUp: Create empty House "PetHome4"
        House petHome4 = new House();
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = petHome4.getPetsOfType("dog");
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // SetUp: Create House "PetHome5"
        House petHome5 = new House();
        
        // SetUp: Add Dog "Rover"
        Dog rover = new Dog("Rover");
        petHome5.addPet(rover);
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = petHome5.getPetsOfType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, result.size());
        assertEquals(rover, result.get(0));
        assertEquals("Rover", result.get(0).getName());
    }
}