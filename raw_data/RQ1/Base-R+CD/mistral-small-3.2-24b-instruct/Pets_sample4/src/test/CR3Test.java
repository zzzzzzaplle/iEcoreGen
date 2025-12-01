import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

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
        shadow.setName("Shadow");
        
        cooper = new Dog();
        cooper.setName("Cooper");
        
        mocha = new Cat();
        mocha.setName("Mocha");
        
        bear = new Dog();
        bear.setName("Bear");
        
        duke = new Dog();
        duke.setName("Duke");
        
        cleo = new Cat();
        cleo.setName("Cleo");
    }

    @Test
    public void testCase1_RemoveExistingPet() {
        // SetUp: Add Cat "Shadow" to HouseX
        houseX.addPet(shadow);
        
        // Action: Remove Shadow from HouseX
        boolean result = houseX.removePet(shadow);
        
        // Expected Output: true
        assertTrue("Should return true when removing existing pet", result);
        
        // Post-Condition: HouseX has no pets
        assertEquals("House should have 0 pets after removal", 0, houseX.getPetCount());
        assertNull("Pet's lived reference should be null after removal", shadow.getLived());
    }

    @Test
    public void testCase2_RemoveNonExistentPet() {
        // SetUp: Add Dog "Cooper" to HouseY
        houseY.addPet(cooper);
        
        // Action: Remove Mocha (unassigned cat) from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse("Should return false when removing non-existent pet", result);
        
        // Verify HouseY still has Cooper
        assertEquals("House should still have 1 pet", 1, houseY.getPetCount());
        assertTrue("House should still contain Cooper", houseY.getPets().contains(cooper));
    }

    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp: Add Dog "Bear" to HouseZ
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW (different house)
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse("Should return false when removing pet from wrong house", result);
        
        // Verify Bear is still in HouseZ
        assertEquals("HouseZ should still have 1 pet", 1, houseZ.getPetCount());
        assertTrue("HouseZ should still contain Bear", houseZ.getPets().contains(bear));
        assertEquals("Bear should still live in HouseZ", houseZ, bear.getLived());
    }

    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp: HouseV is empty (no setup needed)
        
        // Action: Remove Duke (unassigned dog) from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse("Should return false when removing from empty house", result);
        
        // Verify HouseV remains empty
        assertEquals("House should remain empty", 0, houseV.getPetCount());
    }

    @Test
    public void testCase5_RemoveLastPetFromHouse() {
        // SetUp: Add Cat "Cleo" (only pet) to HouseU
        houseU.addPet(cleo);
        
        // Action: Remove Cleo from HouseU
        boolean result = houseU.removePet(cleo);
        
        // Expected Output: true
        assertTrue("Should return true when removing last pet", result);
        
        // Post-Condition: HouseU - no pet
        assertEquals("House should have 0 pets after removal", 0, houseU.getPetCount());
        assertNull("Pet's lived reference should be null after removal", cleo.getLived());
    }
}