// IMPORTANT: Do not include package declaration
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
    
    @Before
    public void setUp() {
        houseX = new House();
        houseY = new House();
        houseZ = new House();
        houseW = new House();
        houseV = new House();
        houseU = new House();
    }
    
    @Test
    public void testCase1_removeExistingPet() {
        // SetUp:
        // 1. Create House "HouseX"
        // 2. Add Cat "Shadow" to HouseX
        Cat shadow = new Cat();
        shadow.setName("Shadow");
        houseX.addPet(shadow);
        
        // Action: Remove Shadow from HouseX
        boolean result = houseX.removePet(shadow);
        
        // Expected Output: true
        assertTrue(result);
        
        // Post-Condition: HouseX has no pets
        assertEquals(0, houseX.getPetCount());
        assertNull(shadow.getHome());
    }
    
    @Test
    public void testCase2_removeNonExistentPet() {
        // SetUp:
        // 1. Create House "HouseY"
        // 2. Add Dog "Cooper" to HouseY
        Dog cooper = new Dog();
        cooper.setName("Cooper");
        houseY.addPet(cooper);
        
        // 3. Create unassigned Cat "Mocha"
        Cat mocha = new Cat();
        mocha.setName("Mocha");
        // Mocha is not added to any house
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verify Cooper is still in HouseY
        assertEquals(1, houseY.getPetCount());
        assertEquals(houseY, cooper.getHome());
        assertNull(mocha.getHome());
    }
    
    @Test
    public void testCase3_removePetFromWrongHouse() {
        // SetUp:
        // 1. Create House "HouseZ" and "HouseW"
        // 2. Add Dog "Bear" to HouseZ
        Dog bear = new Dog();
        bear.setName("Bear");
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verify Bear is still in HouseZ
        assertEquals(1, houseZ.getPetCount());
        assertEquals(0, houseW.getPetCount());
        assertEquals(houseZ, bear.getHome());
    }
    
    @Test
    public void testCase4_removeFromEmptyHouse() {
        // SetUp:
        // 1. Create empty House "HouseV"
        // 2. Create unassigned Dog "Duke"
        Dog duke = new Dog();
        duke.setName("Duke");
        // Duke is not added to any house
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verify HouseV is still empty
        assertEquals(0, houseV.getPetCount());
        assertNull(duke.getHome());
    }
    
    @Test
    public void testCase5_removeLastPetFromHouse() {
        // SetUp:
        // 1. Create House "HouseU"
        // 2. Add Cat "Cleo" (only pet)
        Cat cleo = new Cat();
        cleo.setName("Cleo");
        houseU.addPet(cleo);
        assertEquals(1, houseU.getPetCount());
        
        // Action: Remove Cleo from HouseU
        boolean result = houseU.removePet(cleo);
        
        // Expected Output: true
        assertTrue(result);
        
        // Post-Condition: HouseU - no pet
        assertEquals(0, houseU.getPetCount());
        assertNull(cleo.getHome());
    }
}