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
        // SetUp: Create House "PetHome1" and add Dog "Rusty", Cat "Snowball", Dog "Zeus"
        Dog dog1 = new Dog();
        dog1.setName("Rusty");
        Dog dog2 = new Dog();
        dog2.setName("Zeus");
        Cat cat = new Cat();
        cat.setName("Snowball");
        
        petHome1.addPet(dog1);
        petHome1.addPet(cat);
        petHome1.addPet(dog2);
        
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> dogs = petHome1.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, dogs.size());
        List<String> dogNames = new ArrayList<>();
        for (Pet dog : dogs) {
            dogNames.add(dog.getName());
        }
        assertTrue(dogNames.contains("Rusty"));
        assertTrue(dogNames.contains("Zeus"));
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // SetUp: Create House "PetHome2" and add Cat "Misty", Cat "Smokey"
        Cat cat1 = new Cat();
        cat1.setName("Misty");
        Cat cat2 = new Cat();
        cat2.setName("Smokey");
        
        petHome2.addPet(cat1);
        petHome2.addPet(cat2);
        
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> cats = petHome2.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, cats.size());
        List<String> catNames = new ArrayList<>();
        for (Pet cat : cats) {
            catNames.add(cat.getName());
        }
        assertTrue(catNames.contains("Misty"));
        assertTrue(catNames.contains("Smokey"));
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // SetUp: Create House "PetHome3" and add Dog "Ace", Dog "King"
        Dog dog1 = new Dog();
        dog1.setName("Ace");
        Dog dog2 = new Dog();
        dog2.setName("King");
        
        petHome3.addPet(dog1);
        petHome3.addPet(dog2);
        
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> cats = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertEquals(0, cats.size());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // SetUp: Create empty House "PetHome4"
        // (Already created in setUp method)
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> dogs = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertEquals(0, dogs.size());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // SetUp: Create House "PetHome5" and add Dog "Rover"
        Dog dog = new Dog();
        dog.setName("Rover");
        petHome5.addPet(dog);
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> dogs = petHome5.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, dogs.size());
        assertEquals("Rover", dogs.get(0).getName());
    }
}