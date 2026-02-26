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
        // SetUp:
        // 1. Create House "HouseX"
        // 2. Add Cat "Shadow" to HouseX
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
    public void testCase2_RemoveNonExistentPet() {
        // SetUp:
        // 1. Create House "HouseY"
        // 2. Add Dog "Cooper" to HouseY
        houseY.addPet(cooper);
        
        // 3. Create unassigned Cat "Mocha"
        // (mocha is already created in setUp and has no home)
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verify Cooper is still in HouseY
        assertEquals(1, houseY.getPetCount());
        assertEquals(houseY, cooper.getHome());
    }

    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp:
        // 1. Create House "HouseZ" and "HouseW"
        // 2. Add Dog "Bear" to HouseZ
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verify Bear is still in HouseZ
        assertEquals(1, houseZ.getPetCount());
        assertEquals(houseZ, bear.getHome());
        assertEquals(0, houseW.getPetCount());
    }

    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp:
        // 1. Create empty House "HouseV"
        // (houseV is already created and empty)
        
        // 2. Create unassigned Dog "Duke"
        // (duke is already created in setUp and has no home)
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verify house is still empty
        assertEquals(0, houseV.getPetCount());
        assertNull(duke.getHome());
    }

    @Test
    public void testCase5_RemoveLastPetFromHouse() {
        // SetUp:
        // 1. Create House "HouseU"
        // 2. Add Cat "Cleo" (only pet)
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