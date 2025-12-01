import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private House house;
    private Dog dog;
    private Cat cat;
    
    @Before
    public void setUp() {
        // Common setup that runs before each test
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
        assertTrue("Should return true when adding new pet to empty house", result);
        
        // Post-Condition: HomeA contains Rex
        assertEquals("HomeA should contain 1 pet", 1, homeA.getPetCount());
        assertTrue("HomeA should contain Rex", homeA.getPets().contains(rex));
        assertEquals("Rex should be assigned to HomeA", homeA, rex.getHouse());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInHouse() {
        // SetUp: Create House "HomeB" and "HomeC", Add Cat "Oliver" to HomeB
        House homeB = new House();
        House homeC = new House();
        Cat oliver = new Cat();
        oliver.setName("Oliver");
        homeB.addPet(oliver); // Oliver now belongs to HomeB
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Should return false when adding pet that already belongs to another house", result);
        
        // Post-Condition: HomeC remains unchanged
        assertEquals("HomeC should remain empty", 0, homeC.getPetCount());
        assertEquals("Oliver should still belong to HomeB", homeB, oliver.getHouse());
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD", Add Dog "Rocky" to HomeD
        House homeD = new House();
        Dog rocky = new Dog();
        rocky.setName("Rocky");
        homeD.addPet(rocky); // First addition
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Should return false when adding same pet twice to same house", result);
        
        // Verify HomeD still has only one pet
        assertEquals("HomeD should contain only one pet", 1, homeD.getPetCount());
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
        assertTrue("First addition should return true", result1);
        assertTrue("Second addition should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        assertEquals("HomeE should contain 2 pets", 2, homeE.getPetCount());
        assertTrue("HomeE should contain Bella", homeE.getPets().contains(bella));
        assertTrue("HomeE should contain Lucy", homeE.getPets().contains(lucy));
        assertEquals("Bella should be assigned to HomeE", homeE, bella.getHouse());
        assertEquals("Lucy should be assigned to HomeE", homeE, lucy.getHouse());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF", Create Dog with null name
        House homeF = new House();
        Dog namelessDog = new Dog();
        namelessDog.setName(null); // Explicitly set name to null
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(namelessDog);
        
        // Expected Output: false
        assertFalse("Should return false when adding pet with null name", result);
        
        // Verify HomeF remains empty
        assertEquals("HomeF should remain empty", 0, homeF.getPetCount());
        assertNull("Nameless dog should not belong to any house", namelessDog.getHouse());
    }
}