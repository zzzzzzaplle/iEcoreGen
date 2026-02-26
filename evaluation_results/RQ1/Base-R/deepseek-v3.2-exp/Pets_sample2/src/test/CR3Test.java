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
        // Mocha remains unassigned (no house)
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse("Should return false when removing pet that doesn't belong to the house", result);
        
        // Verify Cooper is still in houseY
        assertEquals("House should still have 1 pet", 1, houseY.countPets());
        assertEquals("Cooper should still be in houseY", houseY, cooper.getHouse());
    }
    
    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp: Create House "HouseZ" and "HouseW", add Dog "Bear" to HouseZ
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse("Should return false when removing pet from wrong house", result);
        
        // Verify Bear is still in houseZ
        assertEquals("HouseZ should still have 1 pet", 1, houseZ.countPets());
        assertEquals("Bear should still be in houseZ", houseZ, bear.getHouse());
        
        // Verify houseW is empty
        assertEquals("HouseW should have 0 pets", 0, houseW.countPets());
    }
    
    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp: Create empty House "HouseV", create unassigned Dog "Duke"
        // houseV is empty by default
        // Duke remains unassigned (no house)
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse("Should return false when removing pet from empty house", result);
        
        // Verify houseV is still empty
        assertEquals("HouseV should still have 0 pets", 0, houseV.countPets());
        assertNull("Duke should still have no house", duke.getHouse());
    }
    
    @Test
    public void testCase5_RemoveLastPetFromHouse() {
        // SetUp: Create House "HouseU", add Cat "Cleo" (only pet)
        houseU.addPet(cleo);
        
        // Action: Remove Cleo from HouseU
        boolean result = houseU.removePet(cleo);
        
        // Expected Output: true
        assertTrue("Should return true when removing last pet from house", result);
        
        // Post-Condition: HouseU - no pet
        assertEquals("House should have 0 pets after removal", 0, houseU.countPets());
        assertNull("Pet's house reference should be null after removal", cleo.getHouse());
    }
}