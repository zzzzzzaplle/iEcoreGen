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
        // SetUp: Create House "HouseX" and add Cat "Shadow" to HouseX
        houseX.addPet(shadow);
        
        // Action: Remove Shadow from HouseX
        boolean result = houseX.removePet(shadow);
        
        // Expected Output: true
        assertTrue("Should return true when removing existing pet", result);
        
        // Post-Condition: HouseX has no pets
        assertEquals("House should have 0 pets after removal", 0, houseX.getPetCount());
    }

    @Test
    public void testCase2_RemoveNonExistentPet() {
        // SetUp: Create House "HouseY", add Dog "Cooper" to HouseY, create unassigned Cat "Mocha"
        houseY.addPet(cooper);
        // mocha remains unassigned (not added to any house)
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse("Should return false when removing non-existent pet", result);
        
        // Verify HouseY still has Cooper
        assertEquals("House should still have 1 pet", 1, houseY.getPetCount());
        assertTrue("House should still contain Cooper", houseY.getPets().contains(cooper));
    }

    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp: Create House "HouseZ" and "HouseW", add Dog "Bear" to HouseZ
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW (different house than where it lives)
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse("Should return false when removing pet from wrong house", result);
        
        // Verify Bear is still in HouseZ
        assertEquals("HouseZ should still have 1 pet", 1, houseZ.getPetCount());
        assertTrue("HouseZ should still contain Bear", houseZ.getPets().contains(bear));
        
        // Verify HouseW is empty
        assertEquals("HouseW should have 0 pets", 0, houseW.getPetCount());
    }

    @Test