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
        List<Pet> pets = homeA.getPets();
        assertEquals("HomeA should contain exactly 1 pet", 1, pets.size());
        assertTrue("HomeA should contain Rex", pets.contains(rex));
        assertEquals("Rex should be living in HomeA", homeA, rex.getLived());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInTheHouse() {
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
        List<Pet> petsInHomeC = homeC.getPets();
        assertEquals("HomeC should remain empty", 0, petsInHomeC.size());
        assertEquals("Oliver should still be living in HomeB", homeB, oliver.getLived());
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
        
        // Verify Rocky is still in HomeD only once
        List<Pet> pets = homeD.getPets();
        assertEquals("HomeD should contain exactly 1 pet", 1, pets.size());
        assertTrue("HomeD should contain Rocky", pets.contains(rocky));
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
        List<Pet> pets = homeE.getPets();
        assertEquals("HomeE should contain exactly 2 pets", 2, pets.size());
        assertTrue("HomeE should contain Bella", pets.contains(bella));
        assertTrue("HomeE should contain Lucy", pets.contains(lucy));
        assertEquals("Bella should be living in HomeE", homeE, bella.getLived());
        assertEquals("Lucy should be living in HomeE", homeE, lucy.getLived());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF", Create Dog with null name
        House homeF = new House();
        Dog unnamedDog = new Dog();
        // Dog name is null by default
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(unnamedDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify HomeF remains empty
        List<Pet> pets = homeF.getPets();
        assertEquals("HomeF should remain empty", 0, pets.size());
        assertNull("Unnamed dog should not be assigned to any house", unnamedDog.getLived());
    }
}