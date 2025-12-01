package edu.pets.pets1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.eclipse.emf.common.util.EList;

import edu.pets.House;
import edu.pets.Pet;
import edu.pets.Dog;
import edu.pets.Cat;
import edu.pets.PetsFactory;

public class CR4Test {
    
    private House petHome1;
    private House petHome2;
    private House petHome3;
    private House petHome4;
    private House petHome5;
    
    @Before
    public void setUp() {
        // Create houses for testing
        petHome1 = PetsFactory.eINSTANCE.createHouse();
        petHome2 = PetsFactory.eINSTANCE.createHouse();
        petHome3 = PetsFactory.eINSTANCE.createHouse();
        petHome4 = PetsFactory.eINSTANCE.createHouse();
        petHome5 = PetsFactory.eINSTANCE.createHouse();
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // SetUp: Create pets and add to house
        Dog rusty = PetsFactory.eINSTANCE.createDog();
        rusty.setName("Rusty");
        
        Cat snowball = PetsFactory.eINSTANCE.createCat();
        snowball.setName("Snowball");
        
        Dog zeus = PetsFactory.eINSTANCE.createDog();
        zeus.setName("Zeus");
        
        petHome1.addPet(rusty);
        petHome1.addPet(snowball);
        petHome1.addPet(zeus);
        
        // Action: Retrieve pets of type "dog"
        EList<Pet> dogs = petHome1.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, dogs.size());
        assertEquals("Rusty", dogs.get(0).getName());
        assertEquals("Zeus", dogs.get(1).getName());
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // SetUp: Create cats and add to house
        Cat misty = PetsFactory.eINSTANCE.createCat();
        misty.setName("Misty");
        
        Cat smokey = PetsFactory.eINSTANCE.createCat();
        smokey.setName("Smokey");
        
        petHome2.addPet(misty);
        petHome2.addPet(smokey);
        
        // Action: Retrieve pets of type "cat"
        EList<Pet> cats = petHome2.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, cats.size());
        assertEquals("Misty", cats.get(0).getName());
        assertEquals("Smokey", cats.get(1).getName());
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // SetUp: Create dogs and add to house
        Dog ace = PetsFactory.eINSTANCE.createDog();
        ace.setName("Ace");
        
        Dog king = PetsFactory.eINSTANCE.createDog();
        king.setName("King");
        
        petHome3.addPet(ace);
        petHome3.addPet(king);
        
        // Action: Retrieve pets of type "cat"
        EList<Pet> cats = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertTrue(cats.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // SetUp: petHome4 is already empty
        
        // Action: Retrieve pets of type "dog"
        EList<Pet> dogs = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertTrue(dogs.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // SetUp: Create dog and add to house
        Dog rover = PetsFactory.eINSTANCE.createDog();
        rover.setName("Rover");
        
        petHome5.addPet(rover);
        
        // Action: Retrieve pets of type "dog"
        EList<Pet> dogs = petHome5.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, dogs.size());
        assertEquals("Rover", dogs.get(0).getName());
    }
}