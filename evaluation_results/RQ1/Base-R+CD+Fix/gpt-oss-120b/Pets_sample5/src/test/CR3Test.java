import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private House houseX;
    private House houseY;
    private House houseZ;
    private House houseW;
    private House houseV;
    private House houseU;
    
    private Cat shadow;
    private Dog cooper;
    private Cat mocha;
    private Dog bear;
    private Dog duke;
    private Cat cleo;
    
    @Before
    public void setUp() {
        // Initialize all houses
        houseX = new House();
        houseY = new House();
        houseZ = new House();
        houseW = new House();
        houseV = new House();
        houseU = new House();
        
        // Initialize all pets
        shadow = new Cat("Shadow");
        cooper = new Dog("Cooper");
        mocha = new Cat("Mocha");
        bear = new Dog("Bear");
        duke = new Dog("Duke");
        cleo = new Cat("Cleo");
    }
    
    @Test
    public void testCase1_RemoveExistingPet() {
        // SetUp: Create House "HouseX" and Add Cat "Shadow" to HouseX
        houseX.addPet(shadow);
        
        // Action: Remove Shadow from HouseX
        boolean result = houseX.removePet(shadow);
        
        // Expected Output: true
        assertTrue("Removing existing pet should return true", result);
        
        // Post-Condition: HouseX has no pets
        assertEquals("House should have 0 pets after removal", 0, houseX.getPetCount());
        assertNull("Pet should no longer have a house reference", shadow.getLived());
    }
    
    @Test
    public void testCase2_RemoveNonExistentPet() {
        // SetUp: Create House "HouseY", Add Dog "Cooper" to HouseY, Create unassigned Cat "Mocha"
        houseY.addPet(cooper);
        // mocha remains unassigned (not added to any house)
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse("Removing non-existent pet should return false", result);
        
        // Verify house state unchanged
        assertEquals("House should still have 1 pet", 1, houseY.getPetCount());
        assertTrue("Cooper should still be in the house", houseY.getPets().contains(cooper));
        assertNull("Mocha should still not have a house reference", mocha.getLived());
    }
    
    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp: Create House "HouseZ" and "HouseW", Add Dog "Bear" to HouseZ
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse("Removing pet from wrong house should return false", result);
        
        // Verify house states unchanged
        assertEquals("HouseZ should still have 1 pet", 1, houseZ.getPetCount());
        assertEquals("HouseW should still have 0 pets", 0, houseW.getPetCount());
        assertEquals("Bear should still belong to HouseZ", houseZ, bear.getLived());
    }
    
    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp: Create empty House "HouseV", Create unassigned Dog "Duke"
        // houseV is already empty, duke is unassigned
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse("Removing from empty house should return false", result);
        
        // Verify house state unchanged
        assertEquals("House should still have 0 pets", 0, houseV.getPetCount());
        assertNull("Duke should still not have a house reference", duke.getLived());
    }
    
    @Test
    public void testCase5_RemoveLastPetFromHouse() {
        // SetUp: Create House "HouseU", Add Cat "Cleo" (only pet)
        houseU.addPet(cleo);
        
        // Action: Remove Cleo from HouseU
        boolean result = houseU.removePet(cleo);
        
        // Expected Output: true
        assertTrue("Removing last pet should return true", result);
        
        // Post-Condition: HouseU - no pet
        assertEquals("House should have 0 pets after removing last pet", 0, houseU.getPetCount());
        assertNull("Pet should no longer have a house reference", cleo.getLived());
    }
}