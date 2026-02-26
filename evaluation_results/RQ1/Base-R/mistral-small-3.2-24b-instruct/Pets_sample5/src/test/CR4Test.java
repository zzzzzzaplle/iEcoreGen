import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

public class CR4Test {
    private House petHome1;
    private House petHome2;
    private House petHome3;
    private House petHome4;
    private House petHome5;
    
    @Before
    public void setUp() {
        // Set up test houses and pets before each test
        petHome1 = new House();
        petHome1.addPet(new Dog("Rusty"));
        petHome1.addPet(new Cat("Snowball"));
        petHome1.addPet(new Dog("Zeus"));
        
        petHome2 = new House();
        petHome2.addPet(new Cat("Misty"));
        petHome2.addPet(new Cat("Smokey"));
        
        petHome3 = new House();
        petHome3.addPet(new Dog("Ace"));
        petHome3.addPet(new Dog("King"));
        
        petHome4 = new House(); // Empty house
        
        petHome5 = new House();
        petHome5.addPet(new Dog("Rover"));
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // Test retrieving only dogs from a house with mixed pets
        List<Pet> result = petHome1.getPetsByType("dog");
        
        // Verify we have exactly 2 dogs
        assertEquals(2, result.size());
        
        // Verify the dogs are Rusty and Zeus
        assertEquals("Rusty", result.get(0).getName());
        assertEquals("Zeus", result.get(1).getName());
        
        // Verify all returned pets are actually Dog instances
        assertTrue(result.get(0) instanceof Dog);
        assertTrue(result.get(1) instanceof Dog);
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // Test retrieving cats from a cat-only house
        List<Pet> result = petHome2.getPetsByType("cat");
        
        // Verify we have exactly 2 cats
        assertEquals(2, result.size());
        
        // Verify the cats are Misty and Smokey
        assertEquals("Misty", result.get(0).getName());
        assertEquals("Smokey", result.get(1).getName());
        
        // Verify all returned pets are actually Cat instances
        assertTrue(result.get(0) instanceof Cat);
        assertTrue(result.get(1) instanceof Cat);
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // Test retrieving cats from a house that only has dogs
        List<Pet> result = petHome3.getPetsByType("cat");
        
        // Verify the result is an empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // Test retrieving pets from an empty house
        List<Pet> result = petHome4.getPetsByType("dog");
        
        // Verify the result is an empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // Test that type retrieval is case-sensitive (using lowercase "dog")
        List<Pet> result = petHome5.getPetsByType("dog");
        
        // Verify we have exactly 1 dog
        assertEquals(1, result.size());
        
        // Verify the dog is Rover
        assertEquals("Rover", result.get(0).getName());
        
        // Verify it's actually a Dog instance
        assertTrue(result.get(0) instanceof Dog);
    }
}