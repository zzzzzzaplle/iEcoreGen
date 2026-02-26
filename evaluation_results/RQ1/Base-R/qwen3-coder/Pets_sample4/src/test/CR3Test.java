import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_RemoveExistingPet() {
        // SetUp: Create House "HouseX" and add Cat "Shadow" to HouseX
        House houseX = new House();
        Cat shadow = new Cat();
        shadow.setName("Shadow");
        houseX.addPet(shadow);
        
        // Action: Remove Shadow from HouseX
        boolean result = houseX.removePet(shadow);
        
        // Expected Output: true
        assertTrue(result);
        
        // Post-Condition: HouseX has no pets
        assertEquals(0, houseX.getPetCount());
        assertNull(shadow.getHouse());
    }
    
    @Test
    public void testCase2_RemoveNonExistentPet() {
        // SetUp: Create House "HouseY", add Dog "Cooper" to HouseY, create unassigned Cat "Mocha"
        House houseY = new House();
        Dog cooper = new Dog();
        cooper.setName("Cooper");
        houseY.addPet(cooper);
        
        Cat mocha = new Cat();
        mocha.setName("Mocha");
        // mocha is unassigned (no house)
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verify HouseY still has Cooper
        assertEquals(1, houseY.getPetCount());
        assertEquals(cooper, houseY.getPets().get(0));
        assertNull(mocha.getHouse()); // mocha should still be unassigned
    }
    
    @Test
    public void testCase3_RemovePetFromWrongHouse() {
        // SetUp: Create House "HouseZ" and "HouseW", add Dog "Bear" to HouseZ
        House houseZ = new House();
        House houseW = new House();
        Dog bear = new Dog();
        bear.setName("Bear");
        houseZ.addPet(bear);
        
        // Action: Remove Bear from HouseW
        boolean result = houseW.removePet(bear);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verify Bear is still in HouseZ
        assertEquals(1, houseZ.getPetCount());
        assertEquals(bear, houseZ.getPets().get(0));
        assertEquals(houseZ, bear.getHouse());
        assertEquals(0, houseW.getPetCount()); // HouseW should still be empty
    }
    
    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp: Create empty House "HouseV", create unassigned Dog "Duke"
        House houseV = new House();
        Dog duke = new Dog();
        duke.setName("Duke");
        // duke is unassigned (no house)
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verify HouseV is still empty and Duke is still unassigned
        assertEquals(0, houseV.getPetCount());
        assertNull(duke.getHouse());
    }
    
    @Test
    public void testCase5_RemoveLastPetFromHouse() {
        // SetUp: Create House "HouseU", add Cat "Cleo" (only pet)
        House houseU = new House();
        Cat cleo = new Cat();
        cleo.setName("Cleo");
        houseU.addPet(cleo);
        
        // Action: Remove Cleo from HouseU
        boolean result = houseU.removePet(cleo);
        
        // Expected Output: true
        assertTrue(result);
        
        // Post-Condition: HouseU - no pet
        assertEquals(0, houseU.getPetCount());
        assertNull(cleo.getHouse());
    }
}