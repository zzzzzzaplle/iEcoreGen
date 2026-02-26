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
    public void testCase1_removeExistingPet() {
        // SetUp: Create House "HouseX" and add Cat "Shadow" to HouseX
        houseX.addPet(shadow);
        
        // Action: Remove Shadow from HouseX
        boolean result = houseX.removePet(shadow);
        
        // Expected Output: true
        assertTrue("Removing an existing pet should return true", result);
        
        // Post-Condition: HouseX has no pets
        assertEquals("HouseX should have no pets after removal", 0, houseX.countPets());
        assertNull("Shadow should no longer have a house reference", shadow.getHouse());
    }
    
    @Test
    public void testCase2_removeNonExistentPet() {
        // SetUp: Create House "HouseY", add Dog "Cooper" to HouseY, create unassigned Cat "Mocha"
        houseY.addPet(cooper);
        // Mocha remains unassigned (not added to any house)
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse("Removing a non-existent pet should return false", result);
        
        // Verify HouseY still has Cooper
        assertEquals("HouseY should still have 1 pet", 1, houseY.countPets());
        assertTrue("HouseY should still contain Cooper", houseY.getPets().contains(cooper));
    }
    
    @Test
    public void testCase3_removePetFromWrongHouse() {
        // SetUp: Create House "HouseZ" and "HouseW", add Dog "Bear" to HouseZ
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse("Removing a pet from wrong house should return false", result);
        
        // Verify Bear still belongs to HouseZ
        assertEquals("HouseZ should still have 1 pet", 1, houseZ.countPets());
        assertTrue("HouseZ should still contain Bear", houseZ.getPets().contains(bear));
        assertEquals("Bear should still reference HouseZ", houseZ, bear.getHouse());
    }
    
    @Test
    public void testCase4_removeFromEmptyHouse() {
        // SetUp: Create empty House "HouseV", create unassigned Dog "Duke"
        // HouseV is already empty from setup, Duke is unassigned
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse("Removing from empty house should return false", result);
        
        // Verify HouseV remains empty
        assertEquals("HouseV should still have 0 pets", 0, houseV.countPets());
        assertNull("Duke should still have no house reference", duke.getHouse());
    }
    
    @Test
    public void testCase5_removeLastPetFromHouse() {
        // SetUp: Create House "HouseU", add Cat "Cleo" (only pet)
        houseU.addPet(cleo);
        
        // Action: Remove Cleo from HouseU
        boolean result = houseU.removePet(cleo);
        
        // Expected Output: true
        assertTrue("Removing the last pet should return true", result);
        
        // Post-Condition: HouseU - no pet
        assertEquals("HouseU should have no pets after removal", 0, houseU.countPets());
        assertNull("Cleo should no longer have a house reference", cleo.getHouse());
    }
}