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
        // Initialize all houses and pets needed for the test cases
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
        assertTrue("Removing an existing pet should return true", result);
        
        // Post-Condition: HouseX has no pets
        assertEquals("House should have 0 pets after removal", 0, houseX.countPets());
        assertFalse("Pet should no longer belong to the house", shadow.belongsToHouse(houseX));
    }
    
    @Test
    public void testCase2_RemoveNonExistentPet() {
        // SetUp: Create House "HouseY", add Dog "Cooper" to HouseY, create unassigned Cat "Mocha"
        houseY.addPet(cooper);
        // mocha is unassigned (not added to any house)
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse("Removing a pet that doesn't belong to the house should return false", result);
        assertEquals("House should still have 1 pet", 1, houseY.countPets());
        assertTrue("Cooper should still belong to the house", cooper.belongsToHouse(houseY));
    }
    
    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp: Create House "HouseZ" and "HouseW", add Dog "Bear" to HouseZ
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse("Removing a pet from the wrong house should return false", result);
        assertEquals("HouseZ should still have 1 pet", 1, houseZ.countPets());
        assertEquals("HouseW should have 0 pets", 0, houseW.countPets());
        assertTrue("Bear should still belong to HouseZ", bear.belongsToHouse(houseZ));
        assertFalse("Bear should not belong to HouseW", bear.belongsToHouse(houseW));
    }
    
    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp: Create empty House "HouseV", create unassigned Dog "Duke"
        // houseV is empty by default, duke is unassigned
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse("Removing a pet from an empty house should return false", result);
        assertEquals("HouseV should still have 0 pets", 0, houseV.countPets());
        assertFalse("Duke should not belong to any house", duke.belongsToHouse(houseV));
    }
    
    @Test
    public void testCase5_RemoveLastPetFromHouse() {
        // SetUp: Create House "HouseU", add Cat "Cleo" (only pet)
        houseU.addPet(cleo);
        
        // Action: Remove Cleo from HouseU
        boolean result = houseU.removePet(cleo);
        
        // Expected Output: true
        assertTrue("Removing the last pet should return true", result);
        
        // Post-Condition: HouseU - no pet
        assertEquals("House should have 0 pets after removing the last pet", 0, houseU.countPets());
        assertFalse("Pet should no longer belong to the house", cleo.belongsToHouse(houseU));
    }
}