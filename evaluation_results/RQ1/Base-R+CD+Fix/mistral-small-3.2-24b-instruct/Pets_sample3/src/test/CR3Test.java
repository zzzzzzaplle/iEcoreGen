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
        // SetUp: Create House "HouseX" and add Cat "Shadow" to HouseX
        houseX.addPet(shadow);
        
        // Action: Remove Shadow from HouseX
        boolean result = houseX.removePet(shadow);
        
        // Expected Output: true
        assertTrue("Removing existing pet should return true", result);
        
        // Post-Condition: HouseX has no pets
        assertEquals("House should have 0 pets after removal", 0, houseX.getPetCount());
        assertNull("Pet's lived house should be null after removal", shadow.getLived());
    }
    
    @Test
    public void testCase2_RemoveNonExistentPet() {
        // SetUp: Create House "HouseY" and add Dog "Cooper" to HouseY
        houseY.addPet(cooper);
        // Mocha remains unassigned (not added to any house)
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse("Removing non-existent pet should return false", result);
        
        // Verify Cooper is still in houseY
        assertEquals("House should still have 1 pet", 1, houseY.getPetCount());
        assertTrue("Cooper should still be in house", houseY.getPets().contains(cooper));
    }
    
    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp: Create House "HouseZ" and "HouseW", add Dog "Bear" to HouseZ
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW (wrong house)
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse("Removing pet from wrong house should return false", result);
        
        // Verify Bear is still in houseZ and not in houseW
        assertEquals("HouseZ should still have 1 pet", 1, houseZ.getPetCount());
        assertEquals("HouseW should have 0 pets", 0, houseW.getPetCount());
        assertEquals("Bear should still be in houseZ", houseZ, bear.getLived());
    }
    
    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp: Create empty House "HouseV", Duke remains unassigned
        // HouseV is already empty from setUp
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse("Removing from empty house should return false", result);
        
        // Verify houseV remains empty
        assertEquals("House should still have 0 pets", 0, houseV.getPetCount());
    }
    
    @Test
    public void testCase5_RemoveLastPetFromHouse() {
        // SetUp: Create House "HouseU" and add Cat "Cleo" (only pet)
        houseU.addPet(cleo);
        
        // Action: Remove Cleo from HouseU
        boolean result = houseU.removePet(cleo);
        
        // Expected Output: true
        assertTrue("Removing last pet should return true", result);
        
        // Post-Condition: HouseU - no pet
        assertEquals("House should have 0 pets after removal", 0, houseU.getPetCount());
        assertNull("Pet's lived house should be null after removal", cleo.getLived());
    }
}