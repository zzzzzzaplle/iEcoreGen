import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    private House house;
    
    @Before
    public void setUp() {
        house = new House();
    }
    
    @Test
    public void testCase1_AddNewPetToEmptyHouse() {
        // SetUp: Create House "HomeA" and Dog "Rex" (no house assigned)
        House homeA = new House();
        Dog rex = new Dog();
        rex.setName("Rex");
        
        // Action: Add Rex to HomeA
        boolean result = homeA.addPet(rex);
        
        // Expected Output: true
        assertTrue("Adding new pet to empty house should return true", result);
        
        // Post-Condition: HomeA contains Rex
        assertTrue("HomeA should contain Rex", homeA.getPets().contains(rex));
        assertEquals("HomeA should have exactly 1 pet", 1, homeA.getPetCount());
        assertEquals("Rex should live in HomeA", homeA, rex.getLived());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInHouse() {
        // SetUp: Create House "HomeB" and "HomeC", Add Cat "Oliver" to HomeB
        House homeB = new House();
        House homeC = new House();
        Cat oliver = new Cat();
        oliver.setName("Oliver");
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet that already belongs to another house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        assertTrue("HomeC should remain empty", homeC.getPets().isEmpty());
        assertEquals("HomeC should have 0 pets", 0, homeC.getPetCount());
        assertEquals("Oliver should still live in HomeB", homeB, oliver.getLived());
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD", Add Dog "Rocky" to HomeD
        House homeD = new House();
        Dog rocky = new Dog();
        rocky.setName("Rocky");
        homeD.addPet(rocky);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify HomeD still has only one pet
        assertEquals("HomeD should still have only 1 pet", 1, homeD.getPetCount());
        assertTrue("HomeD should still contain Rocky", homeD.getPets().contains(rocky));
    }
    
    @Test
    public void testCase4_AddMultipleUniquePets() {
        // SetUp: Create House "HomeE", Create Dog "Bella" and Cat "Lucy" (no houses)
        House homeE = new House();
        Dog bella = new Dog();
        bella.setName("Bella");
        Cat lucy = new Cat();
        lucy.setName("Lucy");
        
        // Action: Add Bella to HomeE → true, Add Lucy to HomeE → true
        boolean result1 = homeE.addPet(bella);
        boolean result2 = homeE.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue("Adding Bella should return true", result1);
        assertTrue("Adding Lucy should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        assertEquals("HomeE should have 2 pets", 2, homeE.getPetCount());
        assertTrue("HomeE should contain Bella", homeE.getPets().contains(bella));
        assertTrue("HomeE should contain Lucy", homeE.getPets().contains(lucy));
        assertEquals("Bella should live in HomeE", homeE, bella.getLived());
        assertEquals("Lucy should live in HomeE", homeE, lucy.getLived());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF", Create Dog with null name
        House homeF = new House();
        Dog nullNamedDog = new Dog();
        // Dog name remains null (not set)
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify HomeF remains empty
        assertTrue("HomeF should remain empty", homeF.getPets().isEmpty());
        assertEquals("HomeF should have 0 pets", 0, homeF.getPetCount());
        assertNull("Null-named dog should not belong to any house", nullNamedDog.getLived());
    }
}