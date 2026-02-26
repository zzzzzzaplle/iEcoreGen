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
        // Initialize houses
        houseX = new House();
        houseY = new House();
        houseZ = new House();
        houseW = new House();
        houseV = new House();
        houseU = new House();
        
        // Initialize pets
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
        assertTrue("Removing existing pet should return true", result);
        
        // Post-Condition: HouseX has no pets
        assertEquals("House should have no pets after removal", 0, houseX.countPets());
        assertNull("Pet's house reference should be null after removal", shadow.getHouse());
    }
    
    @Test
    public void testCase2_RemoveNonExistentPet() {
        // SetUp: Create House "HouseY", add Dog "Cooper" to HouseY, create unassigned Cat "Mocha"
        houseY.addPet(cooper);
        // Mocha remains unassigned (no house)
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse("Removing non-existent pet should return false", result);
        
        // Verify Cooper is still in houseY
        assertEquals("House should still have original pet", 1, houseY.countPets());
        assertEquals("Original pet should still have house reference", houseY, cooper.getHouse());
    }
    
    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp: Create House "HouseZ" and "HouseW", add Dog "Bear" to HouseZ
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse("Removing pet from wrong house should return false", result);
        
        // Verify Bear is still in houseZ
        assertEquals("HouseZ should still have Bear", 1, houseZ.countPets());
        assertEquals("Bear should still reference houseZ", houseZ, bear.getHouse());
        assertEquals("HouseW should remain empty", 0, houseW.countPets());
    }
    
    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp: Create empty House "HouseV", create unassigned Dog "Duke"
        // HouseV is empty by default, Duke remains unassigned
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse("Removing from empty house should return false", result);
        
        // Verify Duke remains unassigned
        assertNull("Duke should remain unassigned", duke.getHouse());
        assertEquals("HouseV should remain empty", 0, houseV.countPets());
    }
    
    @Test
    public void testCase5_RemoveLastPetFromHouse() {
        // SetUp: Create House "HouseU", add Cat "Cleo" (only pet)
        houseU.addPet(cleo);
        
        // Action: Remove Cleo from HouseU
        boolean result = houseU.removePet(cleo);
        
        // Expected Output: true
        assertTrue("Removing last pet should return true", result);
        
        // Post-Condition: HouseU - no pet
        assertEquals("House should have no pets after removing last pet", 0, houseU.countPets());
        assertNull("Pet's house reference should be null after removal", cleo.getHouse());
    }
}