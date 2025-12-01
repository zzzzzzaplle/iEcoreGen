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
        shadow = new Cat();
        cooper = new Dog();
        mocha = new Cat();
        bear = new Dog();
        duke = new Dog();
        cleo = new Cat();
        
        // Set pet names
        shadow.setName("Shadow");
        cooper.setName("Cooper");
        mocha.setName("Mocha");
        bear.setName("Bear");
        duke.setName("Duke");
        cleo.setName("Cleo");
    }

    @Test
    public void testCase1_RemoveExistingPet() {
        // SetUp: Create House "HouseX" and add Cat "Shadow" to HouseX
        houseX.addPet(shadow);
        
        // Action: Remove Shadow from HouseX
        boolean result = houseX.removePet(shadow);
        
        // Expected Output: true
        assertTrue("Should return true when removing existing pet", result);
        
        // Post-Condition: HouseX has no pets
        assertEquals("House should have 0 pets after removal", 0, houseX.getPetCount());
        assertNull("Pet should not belong to any house after removal", shadow.getHouse());
    }

    @Test
    public void testCase2_RemoveNonExistentPet() {
        // SetUp: Create House "HouseY", add Dog "Cooper" to HouseY, and create unassigned Cat "Mocha"
        houseY.addPet(cooper);
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse("Should return false when removing non-existent pet", result);
        
        // Verify Cooper is still in houseY and Mocha has no house
        assertEquals("House should still have 1 pet", 1, houseY.getPetCount());
        assertTrue("Cooper should still be in house", houseY.getPets().contains(cooper));
        assertNull("Mocha should not belong to any house", mocha.getHouse());
    }

    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp: Create House "HouseZ" and "HouseW", add Dog "Bear" to HouseZ
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse("Should return false when removing pet from wrong house", result);
        
        // Verify Bear is still in houseZ and not in houseW
        assertEquals("HouseZ should still have 1 pet", 1, houseZ.getPetCount());
        assertEquals("HouseW should have 0 pets", 0, houseW.getPetCount());
        assertEquals("Bear should still belong to houseZ", houseZ, bear.getHouse());
    }

    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp: Create empty House "HouseV" and create unassigned Dog "Duke"
        // HouseV is already empty from setup, Duke is unassigned
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse("Should return false when removing from empty house", result);
        
        // Verify Duke still has no house and HouseV is still empty
        assertNull("Duke should not belong to any house", duke.getHouse());
        assertEquals("HouseV should still have 0 pets", 0, houseV.getPetCount());
    }

    @Test
    public void testCase5_RemoveLastPetFromHouse() {
        // SetUp: Create House "HouseU" and add Cat "Cleo" (only pet)
        houseU.addPet(cleo);
        
        // Action: Remove Cleo from HouseU
        boolean result = houseU.removePet(cleo);
        
        // Expected Output: true
        assertTrue("Should return true when removing last pet", result);
        
        // Post-Condition: HouseU - no pet
        assertEquals("House should have 0 pets after removing last pet", 0, houseU.getPetCount());
        assertNull("Pet should not belong to any house after removal", cleo.getHouse());
    }
}