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
        // Set up test houses and pets for each test case
        petHome1 = new House();
        Dog rusty = new Dog();
        rusty.setName("Rusty");
        Cat snowball = new Cat();
        snowball.setName("Snowball");
        Dog zeus = new Dog();
        zeus.setName("Zeus");
        petHome1.addPet(rusty);
        petHome1.addPet(snowball);
        petHome1.addPet(zeus);
        
        petHome2 = new House();
        Cat misty = new Cat();
        misty.setName("Misty");
        Cat smokey = new Cat();
        smokey.setName("Smokey");
        petHome2.addPet(misty);
        petHome2.addPet(smokey);
        
        petHome3 = new House();
        Dog ace = new Dog();
        ace.setName("Ace");
        Dog king = new Dog();
        king.setName("King");
        petHome3.addPet(ace);
        petHome3.addPet(king);
        
        petHome4 = new House(); // Empty house
        
        petHome5 = new House();
        Dog rover = new Dog();
        rover.setName("Rover");
        petHome5.addPet(rover);
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // Retrieve pets of type "dog" from PetHome1
        List<Pet> dogs = petHome1.getPetsOfType("dog");
        
        // Verify the list contains exactly 2 dogs: Rusty and Zeus
        assertEquals(2, dogs.size());
        assertEquals("Rusty", dogs.get(0).getName());
        assertEquals("Zeus", dogs.get(1).getName());
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // Retrieve pets of type "cat" from PetHome2
        List<Pet> cats = petHome2.getPetsOfType("cat");
        
        // Verify the list contains exactly 2 cats: Misty and Smokey
        assertEquals(2, cats.size());
        assertEquals("Misty", cats.get(0).getName());
        assertEquals("Smokey", cats.get(1).getName());
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // Retrieve pets of type "cat" from PetHome3 (which has only dogs)
        List<Pet> cats = petHome3.getPetsOfType("cat");
        
        // Verify the list is empty
        assertTrue(cats.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // Retrieve pets of type "dog" from empty PetHome4
        List<Pet> dogs = petHome4.getPetsOfType("dog");
        
        // Verify the list is empty
        assertTrue(dogs.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // Retrieve pets of type "dog" from PetHome5
        List<Pet> dogs = petHome5.getPetsOfType("dog");
        
        // Verify the list contains exactly 1 dog: Rover
        assertEquals(1, dogs.size());
        assertEquals("Rover", dogs.get(0).getName());
    }
}