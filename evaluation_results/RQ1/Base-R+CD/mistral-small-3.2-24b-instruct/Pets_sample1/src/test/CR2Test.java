import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    private House house;
    private Pet rex;
    private Pet oliver;
    private Pet rocky;
    private Pet bella;
    private Pet lucy;
    private Pet nullNamedDog;
    
    @Before
    public void setUp() {
        house = new House();
        rex = new Dog();
        rex.setName("Rex");
        
        oliver = new Cat();
        oliver.setName("Oliver");
        
        rocky = new Dog();
        rocky.setName("Rocky");
        
        bella = new Dog();
        bella.setName("Bella");
        
        lucy = new Cat();
        lucy.setName("Lucy");
        
        nullNamedDog = new Dog();
        // Intentionally not setting name to keep it null
    }

    @Test
    public void testCase1_AddNewPetToEmptyHouse() {
        // Setup: House "HomeA" is empty, Dog "Rex" has no house assigned
        // Action: Add Rex to house
        boolean result = house.addPet(rex);
        
        // Expected Output: true
        assertTrue("Adding new pet to empty house should return true", result);
        
        // Post-Condition: House contains Rex
        assertTrue("House should contain the added pet", house.getPets().contains(rex));
        assertEquals("House should have exactly 1 pet", 1, house.getPetCount());
        assertEquals("Rex's house should be set to this house", house, rex.getLived());
    }

    @Test
    public void testCase2_AddPetAlreadyInAnotherHouse() {
        // Setup: Create House "HomeB" and "HomeC"
        House homeB = new House();
        House homeC = new House();
        
        // Add Cat "Oliver" to HomeB
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet that already belongs to another house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        assertFalse("HomeC should not contain Oliver", homeC.getPets().contains(oliver));
        assertEquals("HomeC should remain empty", 0, homeC.getPetCount());
        assertEquals("Oliver should still belong to HomeB", homeB, oliver.getLived());
    }

    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // Setup: Create House "HomeD" and add Dog "Rocky" to it
        House homeD = new House();
        homeD.addPet(rocky);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify house still has only one pet (Rocky)
        assertEquals("House should still have only one pet", 1, homeD.getPetCount());
        assertTrue("House should still contain Rocky", homeD.getPets().contains(rocky));
    }

    @Test
    public void testCase4_AddMultipleUniquePets() {
        // Setup: Create House "HomeE", Dog "Bella" and Cat "Lucy" (no houses)
        House homeE = new House();
        
        // Action: Add Bella to HomeE → true
        boolean result1 = homeE.addPet(bella);
        
        // Action: Add Lucy to HomeE → true
        boolean result2 = homeE.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue("Adding first pet should return true", result1);
        assertTrue("Adding second pet should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        assertEquals("House should have exactly 2 pets", 2, homeE.getPetCount());
        assertTrue("House should contain Bella", homeE.getPets().contains(bella));
        assertTrue("House should contain Lucy", homeE.getPets().contains(lucy));
        assertEquals("Bella's house should be set to HomeE", homeE, bella.getLived());
        assertEquals("Lucy's house should be set to HomeE", homeE, lucy.getLived());
    }

    @Test
    public void testCase5_AddPetWithNullName() {
        // Setup: Create House "HomeF" and Dog with null name
        House homeF = new House();
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify house remains empty
        assertEquals("House should remain empty", 0, homeF.getPetCount());
        assertNull("Null-named pet should not have a house assigned", nullNamedDog.getLived());
    }
}