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
        assertTrue("Should return true when removing existing pet", result);
        
        // Post-Condition: HouseX has no pets
        assertEquals("House should have 0 pets after removal", 0, houseX.getPetCount());
        assertNull("Pet should no longer belong to any house", shadow.getLived());
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
        // Mocha is unassigned (not added to any house)
        
        // Action: Remove Mocha from HouseY
        boolean result = houseY.removePet(mocha);
        
        // Expected Output: false
        assertFalse("Should return false when removing non-existent pet", result);
        
        // Verify HouseY still has Cooper
        assertEquals("House should still have 1 pet", 1, houseY.getPetCount());
        assertEquals("Cooper should still be in the house", cooper, houseY.getPets().get(0));
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
        assertFalse("Should return false when removing pet from wrong house", result);
        
        // Verify Bear is still in HouseZ
        assertEquals("HouseZ should still have 1 pet", 1, houseZ.getPetCount());
        assertEquals("Bear should still be in HouseZ", bear, houseZ.getPets().get(0));
        assertEquals("Bear should still belong to HouseZ", houseZ, bear.getLived());
    }
    
    @Test
    public void testCase4_RemoveFromEmptyHouse() {
        // SetUp: Create empty House "HouseV", create unassigned Dog "Duke"
        House houseV = new House();
        Dog duke = new Dog();
        duke.setName("Duke");
        // Duke is unassigned (not added to any house)
        
        // Action: Remove Duke from HouseV
        boolean result = houseV.removePet(duke);
        
        // Expected Output: false
        assertFalse("Should return false when removing from empty house", result);
        
        // Verify HouseV is still empty
        assertEquals("House should still have 0 pets", 0, houseV.getPetCount());
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
        assertTrue("Should return true when removing last pet", result);
        
        // Post-Condition: HouseU - no pet
        assertEquals("House should have 0 pets after removal", 0, houseU.getPetCount());
        assertNull("Cleo should no longer belong to any house", cleo.getLived());
    }
}