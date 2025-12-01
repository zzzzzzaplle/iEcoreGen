package edu.pets.pets3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.pets.PetsFactory;
import edu.pets.House;
import edu.pets.Dog;
import edu.pets.Cat;
import edu.pets.Pet;

import org.eclipse.emf.common.util.EList;

public class CR4Test {
    
    private PetsFactory factory;
    
    @Before
    public void setUp() {
        factory = PetsFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_retrieveOnlyDogsFromMixedHouse() {
        // SetUp:
        // 1. Create House "PetHome1"
        House petHome1 = factory.createHouse();
        
        // 2. Add Dog "Rusty", Cat "Snowball", Dog "Zeus"
        Dog rusty = factory.createDog();
        rusty.setName("Rusty");
        petHome1.addPet(rusty);
        
        Cat snowball = factory.createCat();
        snowball.setName("Snowball");
        petHome1.addPet(snowball);
        
        Dog zeus = factory.createDog();
        zeus.setName("Zeus");
        petHome1.addPet(zeus);
        
        // Action: Retrieve pets of type "dog" from PetHome1
        EList<Pet> dogs = petHome1.getPetsByType("dog");
        
        // Expected Output: List containing ["Rusty", "Zeus"]
        assertEquals(2, dogs.size());
        assertEquals("Rusty", dogs.get(0).getName());
        assertEquals("Zeus", dogs.get(1).getName());
    }
    
    @Test
    public void testCase2_retrieveCatsFromCatOnlyHouse() {
        // SetUp:
        // 1. Create House "PetHome2"
        House petHome2 = factory.createHouse();
        
        // 2. Add Cat "Misty", Cat "Smokey"
        Cat misty = factory.createCat();
        misty.setName("Misty");
        petHome2.addPet(misty);
        
        Cat smokey = factory.createCat();
        smokey.setName("Smokey");
        petHome2.addPet(smokey);
        
        // Action: Retrieve pets of input type "cat" from PetHome2
        EList<Pet> cats = petHome2.getPetsByType("cat");
        
        // Expected Output: List containing ["Misty", "Smokey"]
        assertEquals(2, cats.size());
        assertEquals("Misty", cats.get(0).getName());
        assertEquals("Smokey", cats.get(1).getName());
    }
    
    @Test
    public void testCase3_retrieveTypeWithNoMatches() {
        // SetUp:
        // 1. Create House "PetHome3"
        House petHome3 = factory.createHouse();
        
        // 2. Add Dog "Ace", Dog "King"
        Dog ace = factory.createDog();
        ace.setName("Ace");
        petHome3.addPet(ace);
        
        Dog king = factory.createDog();
        king.setName("King");
        petHome3.addPet(king);
        
        // Action: Retrieve pets of input type "cat" from PetHome3
        EList<Pet> cats = petHome3.getPetsByType("cat");
        
        // Expected Output: Empty list
        assertTrue(cats.isEmpty());
    }
    
    @Test
    public void testCase4_retrieveFromEmptyHouse() {
        // SetUp:
        // 1. Create empty House "PetHome4"
        House petHome4 = factory.createHouse();
        
        // Action: Retrieve pets of input type "dog"
        EList<Pet> dogs = petHome4.getPetsByType("dog");
        
        // Expected Output: Empty list
        assertTrue(dogs.isEmpty());
    }
    
    @Test
    public void testCase5_caseSensitiveTypeRetrieval() {
        // SetUp:
        // 1. Create House "PetHome5"
        House petHome5 = factory.createHouse();
        
        // 2. Add Dog "Rover"
        Dog rover = factory.createDog();
        rover.setName("Rover");
        petHome5.addPet(rover);
        
        // Action: Retrieve pets of input type "dog"
        EList<Pet> dogs = petHome5.getPetsByType("dog");
        
        // Expected Output: ["Rover"]
        assertEquals(1, dogs.size());
        assertEquals("Rover", dogs.get(0).getName());
    }
}