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
        // Initialize houses
        houseX = new House();
        houseY = new House();
        houseZ = new House();
        houseW = new House();
        houseV = new House();
        houseU = new House();
        
        // Initialize pets
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
        // SetUp: Create House "HouseX" and Add Cat "Shadow" to HouseX
        houseX.addPet(shadow);
        
        // Action: Remove Shadow from HouseX
        boolean result = houseX.removePet(shadow);
        
        // Expected Output: true
        assertTrue("Removing existing pet should return true", result);
        
        // Post-Condition: HouseX has no pets
        assertEquals("House should have 0 pets after removal", 0, houseX.countPets());
        assertNull("Pet's house reference should be null after removal", shadow.getHouse());
    }

    @Test
    public void testCase2_RemoveNonExistentPet() {
        // SetUp: Create House "HouseY", Add Dog "Cooper" to HouseY, Create unassigned Cat "Mocha"
        houseY.addPet(cooper);
        // mocha is unassigned (no house)
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse("Removing non-existent pet should return false", result);
        
        // Verify HouseY still has Cooper
        assertEquals("House should still have 1 pet", 1, houseY.countPets());
        assertTrue("House should still contain Cooper", houseY.getPets().contains(cooper));
    }

    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp: Create House "HouseZ" and "HouseW", Add Dog "Bear" to HouseZ
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse("Removing pet from wrong house should return false", result);
        
        // Verify Bear is still in HouseZ
        assertEquals("HouseZ should still have 1 pet", 1, houseZ.countPets());
        assertTrue("HouseZ should still contain Bear", houseZ.getPets().contains(bear));
        assertEquals("Bear should still be in HouseZ", houseZ, bear.getHouse());
    }

    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp: Create empty House "HouseV", Create unassigned Dog "Duke"
        // houseV is empty by default, duke is unassigned
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse("Removing from empty house should return false", result);
        
        // Verify HouseV remains empty
        assertEquals("House should remain empty", 0, houseV.countPets());
        assertNull("Duke should remain unassigned", duke.getHouse());
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
        assertEquals("House should have 0 pets after removal", 0, houseU.countPets());
        assertNull("Pet's house reference should be null after removal", cleo.getHouse());
    }
}