package edu.pets.pets2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.pets.*;
import org.eclipse.emf.common.util.EList;

public class CR4Test {
    
    private PetsFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the PetsFactory using Ecore factory pattern
        factory = PetsFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_RetrieveOnlyDogsFromMixedHouse() {
        // SetUp: Create House "PetHome1" with mixed pets
        House petHome1 = factory.createHouse();
        
        // Create and add Dog "Rusty"
        Dog rusty = factory.createDog();
        rusty.setName("Rusty");
        petHome1.getLived().add(rusty);
        
        // Create and add Cat "Snowball"
        Cat snowball = factory.createCat();
        snowball.setName("Snowball");
        petHome1.getLived().add(snowball);
        
        // Create and add Dog "Zeus"
        Dog zeus = factory.createDog();
        zeus.setName("Zeus");
        petHome1.getLived().add(zeus);
        
        // Action: Retrieve pets of type "dog" from PetHome1
        EList<Pet> result = petHome1.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, result.size());
        assertEquals("Rusty", result.get(0).getName());
        assertEquals("Zeus", result.get(1).getName());
    }
    
    @Test
    public void testCase2_RetrieveCatsFromCatOnlyHouse() {
        // SetUp: Create House "PetHome2" with cats only
        House petHome2 = factory.createHouse();
        
        // Create and add Cat "Misty"
        Cat misty = factory.createCat();
        misty.setName("Misty");
        petHome2.getLived().add(misty);
        
        // Create and add Cat "Smokey"
        Cat smokey = factory.createCat();
        smokey.setName("Smokey");
        petHome2.getLived().add(smokey);
        
        // Action: Retrieve pets of input type "cat" from PetHome2
        EList<Pet> result = petHome2.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, result.size());
        assertEquals("Misty", result.get(0).getName());
        assertEquals("Smokey", result.get(1).getName());
    }
    
    @Test
    public void testCase3_RetrieveTypeWithNoMatches() {
        // SetUp: Create House "PetHome3" with dogs only
        House petHome3 = factory.createHouse();
        
        // Create and add Dog "Ace"
        Dog ace = factory.createDog();
        ace.setName("Ace");
        petHome3.getLived().add(ace);
        
        // Create and add Dog "King"
        Dog king = factory.createDog();
        king.setName("King");
        petHome3.getLived().add(king);
        
        // Action: Retrieve pets of input type "cat" from PetHome3
        EList<Pet> result = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFromEmptyHouse() {
        // SetUp: Create empty House "PetHome4"
        House petHome4 = factory.createHouse();
        
        // Action: Retrieve pets of input type "dog"
        EList<Pet> result = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CaseSensitiveTypeRetrieval() {
        // SetUp: Create House "PetHome5" with one dog
        House petHome5 = factory.createHouse();
        
        // Create and add Dog "Rover"
        Dog rover = factory.createDog();
        rover.setName("Rover");
        petHome5.getLived().add(rover);
        
        // Action: Retrieve pets of input type "dog" (lowercase)
        EList<Pet> result = petHome5.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, result.size());
        assertEquals("Rover", result.get(0).getName());
    }
}
