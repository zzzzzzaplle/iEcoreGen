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
    private Dog cooper;
    private Dog bear;
    private Dog duke;
    private Cat shadow;
    private Cat mocha;
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
        cooper = new Dog();
        cooper.setName("Cooper");
        
        bear = new Dog();
        bear.setName("Bear");
        
        duke = new Dog();
        duke.setName("Duke");
        
        shadow = new Cat();
        shadow.setName("Shadow");
        
        mocha = new Cat();
        mocha.setName("Mocha");
        
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
        assertNull("Pet should not belong to any house after removal", shadow.getLived());
    }
    
    @Test
    public void testCase2_RemoveNonExistentPet() {
        // SetUp: Create House "HouseY", add Dog "Cooper" to HouseY, create unassigned Cat "Mocha"
        houseY.addPet(cooper);
        // Mocha remains unassigned (not added to any house)
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse("Removing non-existent pet should return false", result);
        
        // Verify HouseY still has Cooper
        assertEquals("House should still have 1 pet", 1, houseY.getPetCount());
        assertEquals("Cooper should still be in HouseY", houseY, cooper.getLived());
        assertNull("Mocha should not belong to any house", mocha.getLived());
    }
    
    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp: Create House "HouseZ" and "HouseW", add Dog "Bear" to HouseZ
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse("Removing pet from wrong house should return false", result);
        
        // Verify Bear still belongs to HouseZ
        assertEquals("HouseZ should still have 1 pet", 1, houseZ.getPetCount());
        assertEquals("Bear should still be in HouseZ", houseZ, bear.getLived());
        assertEquals("HouseW should have 0 pets", 0, houseW.getPetCount());
    }
    
    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp: Create empty House "HouseV", create unassigned Dog "Duke"
        // HouseV is already empty from setUp()
        // Duke remains unassigned
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse("Removing pet from empty house should return false", result);
        
        // Verify HouseV is still empty and Duke is unassigned
        assertEquals("HouseV should have 0 pets", 0, houseV.getPetCount());
        assertNull("Duke should not belong to any house", duke.getLived());
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
        assertEquals("House should have 0 pets after removing last pet", 0, houseU.getPetCount());
        assertNull("Pet should not belong to any house after removal", cleo.getLived());
    }
}