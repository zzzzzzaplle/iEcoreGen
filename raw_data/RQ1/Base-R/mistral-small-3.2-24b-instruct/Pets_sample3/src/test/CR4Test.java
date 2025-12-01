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
        // Set up test houses and pets for all test cases
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
        
        petHome4 = new House();
        
        petHome5 = new House();
        petHome5.addPet(new Dog("Rover"));
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // Test retrieving only dogs from a house with mixed pets
        List<Pet> result = petHome1.getPetsByType("dog");
        
        assertEquals("Should return exactly 2 dogs", 2, result.size());
        assertEquals("First dog should be Rusty", "Rusty", result.get(0).getName());
        assertEquals("Second dog should be Zeus", "Zeus", result.get(1).getName());
        assertTrue("All returned pets should be dogs", result.get(0) instanceof Dog);
        assertTrue("All returned pets should be dogs", result.get(1) instanceof Dog);
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // Test retrieving cats from a house with only cats
        List<Pet> result = petHome2.getPetsByType("cat");
        
        assertEquals("Should return exactly 2 cats", 2, result.size());
        assertEquals("First cat should be Misty", "Misty", result.get(0).getName());
        assertEquals("Second cat should be Smokey", "Smokey", result.get(1).getName());
        assertTrue("All returned pets should be cats", result.get(0) instanceof Cat);
        assertTrue("All returned pets should be cats", result.get(1) instanceof Cat);
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // Test retrieving cats from a house that only has dogs
        List<Pet> result = petHome3.getPetsByType("cat");
        
        assertTrue("Should return empty list when no matching pets found", result.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // Test retrieving pets from an empty house
        List<Pet> result = petHome4.getPetsByType("dog");
        
        assertTrue("Should return empty list when house has no pets", result.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // Test that type retrieval is case-insensitive ("dog" should match Dog instances)
        List<Pet> result = petHome5.getPetsByType("dog");
        
        assertEquals("Should return exactly 1 dog", 1, result.size());
        assertEquals("Dog should be Rover", "Rover", result.get(0).getName());
        assertTrue("Returned pet should be a Dog instance", result.get(0) instanceof Dog);
    }
}