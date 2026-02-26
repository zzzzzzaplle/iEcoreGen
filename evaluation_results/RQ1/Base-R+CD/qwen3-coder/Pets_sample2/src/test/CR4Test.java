import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private House petHome1;
    private House petHome2;
    private House petHome3;
    private House petHome4;
    private House petHome5;
    
    @Before
    public void setUp() {
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
        petHome1.addPet(dog1);
        
        Cat cat1 = new Cat();
        cat1.setName("Snowball");
        petHome1.addPet(cat1);
        
        Dog dog2 = new Dog();
        dog2.setName("Zeus");
        petHome1.addPet(dog2);
        
        // Action: Retrieve pets of type "dog" from PetHome1
        List<Pet> dogs = petHome1.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, dogs.size());
        assertEquals("Rusty", dogs.get(0).getName());
        assertEquals("Zeus", dogs.get(1).getName());
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // SetUp: Create House "PetHome2" and add Cat "Misty", Cat "Smokey"
        Cat cat1 = new Cat();
        cat1.setName("Misty");
        petHome2.addPet(cat1);
        
        Cat cat2 = new Cat();
        cat2.setName("Smokey");
        petHome2.addPet(cat2);
        
        // Action: Retrieve pets of input type "cat" from PetHome2
        List<Pet> cats = petHome2.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, cats.size());
        assertEquals("Misty", cats.get(0).getName());
        assertEquals("Smokey", cats.get(1).getName());
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // SetUp: Create House "PetHome3" and add Dog "Ace", Dog "King"
        Dog dog1 = new Dog();
        dog1.setName("Ace");
        petHome3.addPet(dog1);
        
        Dog dog2 = new Dog();
        dog2.setName("King");
        petHome3.addPet(dog2);
        
        // Action: Retrieve pets of input type "cat" from PetHome3
        List<Pet> cats = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertEquals(0, cats.size());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // SetUp: Create empty House "PetHome4"
        // Action: Retrieve pets of input type "dog"
        List<Pet> dogs = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertEquals(0, dogs.size());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // SetUp: Create House "PetHome5" and add Dog "Rover"
        Dog dog1 = new Dog();
        dog1.setName("Rover");
        petHome5.addPet(dog1);
        
        // Action: Retrieve pets of input type "dog"
        List<Pet> dogs = petHome5.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, dogs.size());
        assertEquals("Rover", dogs.get(0).getName());
    }
}