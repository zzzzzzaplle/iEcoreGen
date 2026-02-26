package edu.pets.pets5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.pets.PetsFactory;
import edu.pets.House;
import edu.pets.Cat;
import edu.pets.Dog;

public class CR3Test {
    
    private PetsFactory factory;
    
    @Before
    public void setUp() {
        factory = PetsFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_removeExistingPet() {
        // SetUp:
        // 1. Create House "HouseX"
        House houseX = factory.createHouse();
        
        // 2. Add Cat "Shadow" to HouseX
        Cat shadow = factory.createCat();
        shadow.setName("Shadow");
        houseX.addPet(shadow);
        
        // Action: Remove Shadow from HouseX
        houseX.removePet(shadow);
        
        // Expected Output: true (method is void, so we check the post-condition)
        // Post-Condition: HouseX has no pets
        assertEquals(0, houseX.getLived().size());
        assertNull(shadow.getHouse());
    }
    
    @Test
    public void testCase2_removeNonExistentPet() {
        // SetUp:
        // 1. Create House "HouseY"
        House houseY = factory.createHouse();
        
        // 2. Add Dog "Cooper" to HouseY
        Dog cooper = factory.createDog();
        cooper.setName("Cooper");
        houseY.addPet(cooper);
        
        // 3. Create unassigned Cat "Mocha"
        Cat mocha = factory.createCat();
        mocha.setName("Mocha");
        // Note: Mocha is not added to any house
        
        // Action: Remove Mocha from HouseY
        houseY.removePet(mocha);
        
        // Expected Output: false (method is void, but Mocha shouldn't be in HouseY)
        // Cooper should still be in the house
        assertEquals(1, houseY.getLived().size());
        assertTrue(houseY.getLived().contains(cooper));
        assertNull(mocha.getHouse());
    }
    
    @Test
    public void testCase3_removePetFromWrongHouse() {
        // SetUp:
        // 1. Create House "HouseZ" and "HouseW"
        House houseZ = factory.createHouse();
        House houseW = factory.createHouse();
        
        // 2. Add Dog "Bear" to HouseZ
        Dog bear = factory.createDog();
        bear.setName("Bear");
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW
        houseW.removePet(bear);
        
        // Expected Output: false (method is void, but Bear shouldn't be removed from HouseZ)
        // Bear should still be in HouseZ
        assertEquals(1, houseZ.getLived().size());
        assertTrue(houseZ.getLived().contains(bear));
        assertEquals(houseZ, bear.getHouse());
        assertEquals(0, houseW.getLived().size());
    }
    
    @Test
    public void testCase4_removeFromEmptyHouse() {
        // SetUp:
        // 1. Create empty House "HouseV"
        House houseV = factory.createHouse();
        
        // 2. Create unassigned Dog "Duke"
        Dog duke = factory.createDog();
        duke.setName("Duke");
        // Note: Duke is not added to any house
        
        // Action: Remove Duke from HouseV
        houseV.removePet(duke);
        
        // Expected Output: false (method is void, but nothing should happen)
        // HouseV should still be empty
        assertEquals(0, houseV.getLived().size());
        assertNull(duke.getHouse());
    }
    
    @Test
    public void testCase5_removeLastPetFromHouse() {
        // SetUp:
        // 1. Create House "HouseU"
        House houseU = factory.createHouse();
        
        // 2. Add Cat "Cleo" (only pet)
        Cat cleo = factory.createCat();
        cleo.setName("Cleo");
        houseU.addPet(cleo);
        
        // Action: Remove Cleo from HouseU
        houseU.removePet(cleo);
        
        // Expected Output: true (method is void, so we check the post-condition)
        // Post-Condition: HouseU - no pet
        assertEquals(0, houseU.getLived().size());
        assertNull(cleo.getHouse());
    }
}