import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    private House house1;
    private House house2;
    private House house3;
    private House house4;
    private House house5;
    private Dog dog1;
    private Dog dog2;
    private Dog dog3;
    private Cat cat1;
    private Cat cat2;
    private Cat cat3;
    
    @Before
    public void setUp() {
        // Initialize houses
        house1 = new House();
        house2 = new House();
        house3 = new House();
        house4 = new House();
        house5 = new House();
        
        // Initialize pets
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
        cat3 = new Cat();
        cat3.setName("Smokey");
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // SetUp: Create House "PetHome1" and add Dog "Rusty", Cat "Snowball", Dog "Zeus"
        house1.addPet(dog1);
        house1.addPet(cat1);
        house1.addPet(dog2);
        
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> result = house1.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, result.size());
        assertTrue(result.contains(dog1));
        assertTrue(result.contains(dog2));
        assertFalse(result.contains(cat1));
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // SetUp: Create House "PetHome2" and add Cat "Misty", Cat "Smokey"
        house2.addPet(cat2);
        house2.addPet(cat3);
        
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> result = house2.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, result.size());
        assertTrue(result.contains(cat2));
        assertTrue(result.contains(cat3));
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // SetUp: Create House "PetHome3" and add Dog "Ace", Dog "King"
        Dog ace = new Dog();
        ace.setName("Ace");
        Dog king = new Dog();
        king.setName("King");
        
        house3.addPet(ace);
        house3.addPet(king);
        
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> result = house3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // SetUp: Create empty House "PetHome4"
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // SetUp: Create House "PetHome5" and add Dog "Rover"
        house5.addPet(dog3);
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> result = house5.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, result.size());
        assertTrue(result.contains(dog3));
        assertEquals("Rover", result.get(0).getName());
    }
}