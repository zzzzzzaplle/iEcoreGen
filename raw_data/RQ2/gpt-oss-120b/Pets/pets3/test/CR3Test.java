package edu.pets.pets3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.pets.PetsFactory;
import edu.pets.PetsPackage;
import edu.pets.House;
import edu.pets.Cat;
import edu.pets.Dog;
import edu.pets.Pet;
import edu.pets.*;

public class CR3Test {
    
    private PetsFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the PetsFactory using Ecore factory pattern
        factory = PetsFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_RemoveExistingPet() {
        // SetUp: Create House "HouseX" and add Cat "Shadow" to HouseX
        House houseX = factory.createHouse();
        Cat shadow = factory.createCat();
        shadow.setName("Shadow");
        houseX.getLived().add(shadow);
        
        // Action: Remove Shadow from HouseX
        boolean result = houseX.getLived().remove(shadow);
        
        // Expected Output: true
        assertTrue("Should return true when removing existing pet", result);
        
        // Post-Condition: HouseX has no pets
        assertEquals("House should have no pets after removal", 0, houseX.getLived().size());
    }
    
    @Test
    public void testCase2_RemoveNonExistentPet() {
        // SetUp: Create House "HouseY", add Dog "Cooper" to HouseY, create unassigned Cat "Mocha"
        House houseY = factory.createHouse();
        Dog cooper = factory.createDog();
        cooper.setName("Cooper");
        houseY.getLived().add(cooper);
        
        Cat mocha = factory.createCat();
        mocha.setName("Mocha");
        // Mocha is not added to any house
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.getLived().remove(mocha);
        
        // Expected Output: false
        assertFalse("Should return false when removing non-existent pet", result);
        assertEquals("House should still have Cooper", 1, houseY.getLived().size());
    }
    
    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp: Create House "HouseZ" and "HouseW", add Dog "Bear" to HouseZ
        House houseZ = factory.createHouse();
        House houseW = factory.createHouse();
        Dog bear = factory.createDog();
        bear.setName("Bear");
        houseZ.getLived().add(bear);
        
        // Action: Remove Bear from HouseW
        boolean result = houseW.getLived().remove(bear);
        
        // Expected Output: false
        assertFalse("Should return false when removing pet from wrong house", result);
        assertEquals("HouseZ should still have Bear", 1, houseZ.getLived().size());
        assertEquals("HouseW should have no pets", 0, houseW.getLived().size());
    }
    
    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp: Create empty House "HouseV", create unassigned Dog "Duke"
        House houseV = factory.createHouse();
        Dog duke = factory.createDog();
        duke.setName("Duke");
        // Duke is not added to any house
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.getLived().remove(duke);
        
        // Expected Output: false
        assertFalse("Should return false when removing from empty house", result);
        assertEquals("HouseV should remain empty", 0, houseV.getLived().size());
    }
    
    @Test
    public void testCase5_RemoveLastPetFromHouse() {
        // SetUp: Create House "HouseU", add Cat "Cleo" (only pet)
        House houseU = factory.createHouse();
        Cat cleo = factory.createCat();
        cleo.setName("Cleo");
        houseU.getLived().add(cleo);
        
        // Action: Remove Cleo from HouseU
        boolean result = houseU.getLived().remove(cleo);
        
        // Expected Output: true
        assertTrue("Should return true when removing last pet", result);
        
        // Post-Condition: HouseU - no pet
        assertEquals("HouseU should have no pets after removal", 0, houseU.getLived().size());
    }
}
