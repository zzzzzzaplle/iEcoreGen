import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private House house;
    private Pet pet;
    
    @Before
    public void setUp() {
        house = new House();
    }
    
    @Test
    public void testCase1_AddNewPetToEmptyHouse() {
        // SetUp: Create House "HomeA" and Dog "Rex" (no house assigned)
        House homeA = new House();
        Pet rex = new Dog();
        rex.setName("Rex");
        
        // Action: Add Rex to HomeA
        boolean result = homeA.addPet(rex);
        
        // Expected Output: true
        assertTrue("Adding new pet to empty house should return true", result);
        
        // Post-Condition: HomeA contains Rex
        assertEquals("HomeA should contain 1 pet", 1, homeA.getPetCount());
        assertTrue("HomeA should contain Rex", homeA.getPets().contains(rex));
        assertEquals("Rex should live in HomeA", homeA, rex.getLived());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInHouse() {
        // SetUp: Create House "HomeB" and "HomeC", Add Cat "Oliver" to HomeB
        House homeB = new House();
        House homeC = new House();
        Pet oliver = new Cat();
        oliver.setName("Oliver");
        homeB.addPet(oliver); // Oliver now belongs to HomeB
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet that already belongs to a house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        assertEquals("HomeC should remain empty", 0, homeC.getPetCount());
        assertEquals("Oliver should still live in HomeB", homeB, oliver.getLived());
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD", Add Dog "Rocky" to HomeD
        House homeD = new House();
        Pet rocky = new Dog();
        rocky.setName("Rocky");
        homeD.addPet(rocky); // First addition
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify Rocky is still in HomeD only once
        assertEquals("HomeD should contain only one pet", 1, homeD.getPetCount());
        assertEquals("Rocky should still live in HomeD", homeD, rocky.getLived());
    }
    
    @Test
    public void testCase4_AddMultipleUniquePets() {
        // SetUp: Create House "HomeE", Create Dog "Bella" and Cat "Lucy" (no houses)
        House homeE = new House();
        Pet bella = new Dog();
        bella.setName("Bella");
        Pet lucy = new Cat();
        lucy.setName("Lucy");
        
        // Action: Add Bella to HomeE → true, Add Lucy to HomeE → true
        boolean result1 = homeE.addPet(bella);
        boolean result2 = homeE.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue("Adding first unique pet should return true", result1);
        assertTrue("Adding second unique pet should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        assertEquals("HomeE should contain 2 pets", 2, homeE.getPetCount());
        assertTrue("HomeE should contain Bella", homeE.getPets().contains(bella));
        assertTrue("HomeE should contain Lucy", homeE.getPets().contains(lucy));
        assertEquals("Bella should live in HomeE", homeE, bella.getLived());
        assertEquals("Lucy should live in HomeE", homeE, lucy.getLived());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF", Create Dog with null name
        House homeF = new House();
        Pet nullNameDog = new Dog();
        // Intentionally not setting name to keep it null
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNameDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify house remains empty
        assertEquals("HomeF should remain empty", 0, homeF.getPetCount());
        assertNull("Pet should not be assigned to any house", nullNameDog.getLived());
    }
}