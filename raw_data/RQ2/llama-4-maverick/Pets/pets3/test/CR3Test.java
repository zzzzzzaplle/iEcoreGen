package edu.pets.pets2.test;

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
    
    /**
     * Test Case 1: "Remove existing pet"
     * SetUp:
     * 1. Create House "HouseX"
     * 2. Add Cat "Shadow" to HouseX
     * Action: Remove Shadow from HouseX
     * Expected Output: true
     * Post-Condition: HouseX has no pets
     */
    @Test
    public void testCase1_removeExistingPet() {
        // SetUp
        House houseX = factory.createHouse();
        Cat shadow = factory.createCat();
        shadow.setName("Shadow");
        
        // Add cat to house
        houseX.addPet(shadow);
        
        // Action: Remove Shadow from HouseX
        houseX.removePet(shadow);
        
        // Expected Output: Shadow should be removed
        assertEquals(0, houseX.getLived().size());
        assertNull(shadow.getHouse());
    }
    
    /**
     * Test Case 2: "Remove non-existent pet"
     * SetUp:
     * 1. Create House "HouseY"
     * 2. Add Dog "Cooper" to HouseY
     * 3. Create unassigned Cat "Mocha"
     * Action: Remove Mocha from HouseY
     * Expected Output: false
     */
    @Test
    public void testCase2_removeNonExistentPet() {
        // SetUp
        House houseY = factory.createHouse();
        Dog cooper = factory.createDog();
        cooper.setName("Cooper");
        houseY.addPet(cooper);
        
        Cat mocha = factory.createCat();
        mocha.setName("Mocha");
        // Mocha is not added to any house
        
        // Action: Try to remove Mocha from HouseY (should not affect anything)
        houseY.removePet(mocha);
        
        // Expected Output: Cooper should still be in the house
        assertEquals(1, houseY.getLived().size());
        assertEquals("Cooper", houseY.getLived().get(0).getName());
        assertNull(mocha.getHouse());
    }
    
    /**
     * Test Case 3: "Remove pet from wrong house"
     * SetUp:
     * 1. Create House "HouseZ" and "HouseW"
     * 2. Add Dog "Bear" to HouseZ
     * Action: Remove Bear from HouseW
     * Expected Output: false
     */
    @Test
    public void testCase3_removePetFromWrongHouse() {
        // SetUp
        House houseZ = factory.createHouse();
        House houseW = factory.createHouse();
        Dog bear = factory.createDog();
        bear.setName("Bear");
        houseZ.addPet(bear); // Bear is in HouseZ
        
        // Action: Try to remove Bear from HouseW (should not affect anything)
        houseW.removePet(bear);
        
        // Expected Output: Bear should still be in HouseZ
        assertEquals(1, houseZ.getLived().size());
        assertEquals("Bear", houseZ.getLived().get(0).getName());
        assertEquals(houseZ, bear.getHouse());
        assertEquals(0, houseW.getLived().size());
    }
    
    /**
     * Test Case 4: "Remove from empty house"
     * SetUp:
     * 1. Create empty House "HouseV"
     * 2. Create unassigned Dog "Duke"
     * Action: Remove Duke from HouseV
     * Expected Output: false
     */
    @Test
    public void testCase4_removeFromEmptyHouse() {
        // SetUp
        House houseV = factory.createHouse();
        Dog duke = factory.createDog();
        duke.setName("Duke");
        // HouseV is empty and Duke is unassigned
        
        // Action: Try to remove Duke from HouseV (should not affect anything)
        houseV.removePet(duke);
        
        // Expected Output: HouseV should still be empty and Duke should still be unassigned
        assertEquals(0, houseV.getLived().size());
        assertNull(duke.getHouse());
    }
    
    /**
     * Test Case 5: "Remove last pet from house"
     * SetUp:
     * 1. Create House "HouseU"
     * 2. Add Cat "Cleo" (only pet)
     * Action: Remove Cleo from HouseU
     * Expected Output: true
     * Post-Condition: HouseU - no pet
     */
    @Test
    public void testCase5_removeLastPetFromHouse() {
        // SetUp
        House houseU = factory.createHouse();
        Cat cleo = factory.createCat();
        cleo.setName("Cleo");
        houseU.addPet(cleo); // Cleo is the only pet in HouseU
        
        // Action: Remove Cleo from HouseU
        houseU.removePet(cleo);
        
        // Expected Output: HouseU should have no pets and Cleo should be unassigned
        assertEquals(0, houseU.getLived().size());
        assertNull(cleo.getHouse());
    }
}