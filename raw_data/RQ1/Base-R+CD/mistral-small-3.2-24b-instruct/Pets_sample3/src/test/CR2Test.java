import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    private House homeA, homeB, homeC, homeD, homeE, homeF;
    private Dog rex, rocky, bella, nullNamedDog;
    private Cat oliver, lucy;
    
    @Before
    public void setUp() {
        // Create house instances
        homeA = new House();
        homeB = new House();
        homeC = new House();
        homeD = new House();
        homeE = new House();
        homeF = new House();
        
        // Create pet instances
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
        // Intentionally leaving name as null
    }
    
    @Test
    public void testCase1_AddNewPetToEmptyHouse() {
        // Test Case 1: "Add new pet to empty house"
        // Action: Add Rex to HomeA
        boolean result = homeA.addPet(rex);
        
        // Expected Output: true
        assertTrue("Adding new pet to empty house should return true", result);
        
        // Post-Condition: HomeA contains Rex
        assertTrue("HomeA should contain Rex after adding", homeA.getPets().contains(rex));
        assertEquals("HomeA should have exactly 1 pet", 1, homeA.getPetCount());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInAnotherHouse() {
        // Test Case 2: "Add pet already in the house"
        // SetUp: Add Oliver to HomeB
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet already in another house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        assertFalse("HomeC should not contain Oliver", homeC.getPets().contains(oliver));
        assertEquals("HomeC should remain empty", 0, homeC.getPetCount());
        assertTrue("HomeB should still contain Oliver", homeB.getPets().contains(oliver));
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // Test Case 3: "Add same pet twice to same house"
        // SetUp: Add Rocky to HomeD
        homeD.addPet(rocky);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify HomeD still has only one pet (Rocky)
        assertEquals("HomeD should have exactly 1 pet", 1, homeD.getPetCount());
        assertTrue("HomeD should contain Rocky", homeD.getPets().contains(rocky));
    }
    
    @Test
    public void testCase4_AddMultipleUniquePets() {
        // Test Case 4: "Add multiple unique pets"
        // Action: Add Bella to HomeE → true
        boolean result1 = homeE.addPet(bella);
        // Action: Add Lucy to HomeE → true
        boolean result2 = homeE.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue("Adding Bella should return true", result1);
        assertTrue("Adding Lucy should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        assertEquals("HomeE should have 2 pets", 2, homeE.getPetCount());
        assertTrue("HomeE should contain Bella", homeE.getPets().contains(bella));
        assertTrue("HomeE should contain Lucy", homeE.getPets().contains(lucy));
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // Test Case 5: "Add pet with null name"
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify HomeF remains empty
        assertEquals("HomeF should remain empty", 0, homeF.getPetCount());
    }
}