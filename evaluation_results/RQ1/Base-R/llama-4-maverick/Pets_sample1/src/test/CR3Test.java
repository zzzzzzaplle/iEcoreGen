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
        // Initialize all houses and pets needed for tests
        houseX = new House();
        houseY = new House();
        houseZ = new House();
        houseW = new House();
        houseV = new House();
        houseU = new House();
        
        shadow = new Cat("Shadow");
        cooper = new Dog("Cooper");
        mocha = new Cat("Mocha");
        bear = new Dog("Bear");
        duke = new Dog("Duke");
        cleo = new Cat("Cleo");
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
        assertEquals("House should have 0 pets after removal", 0, houseX.countPets());
        assertNull("Pet's house reference should be null after removal", shadow.getHouse());
    }

    @Test
    public void testCase2_RemoveNonExistentPet() {
        // SetUp: Create House "HouseY", add Dog "Cooper" to HouseY, create unassigned Cat "Mocha"
        houseY.addPet(cooper);
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse("Should return false when removing non-existent pet", result);
        assertEquals("House should still have 1 pet", 1, houseY.countPets());
        assertEquals("Cooper should still be in house", "Cooper", houseY.getPetNames().get(0));
    }

    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp: Create House "HouseZ" and "HouseW", add Dog "Bear" to HouseZ
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse("Should return false when removing pet from wrong house", result);
        assertEquals("HouseZ should still have Bear", 1, houseZ.countPets());
        assertEquals("HouseW should remain empty", 0, houseW.countPets());
        assertEquals("Bear should still be in HouseZ", houseZ, bear.getHouse());
    }

    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp: Create empty House "HouseV", create unassigned Dog "Duke"
        // HouseV is already empty from setup
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse("Should return false when removing pet from empty house", result);
        assertEquals("HouseV should remain empty", 0, houseV.countPets());
    }

    @Test
    public void testCase5_RemoveLastPetFromHouse() {
        // SetUp: Create House "HouseU", add Cat "Cleo" (only pet)
        houseU.addPet(cleo);
        
        // Action: Remove Cleo from HouseU
        boolean result = houseU.removePet(cleo);
        
        // Expected Output: true
        assertTrue("Should return true when removing last pet", result);
        
        // Post-Condition: HouseU - no pet
        assertEquals("House should have 0 pets after removing last pet", 0, houseU.countPets());
        assertNull("Pet's house reference should be null after removal", cleo.getHouse());
    }
}