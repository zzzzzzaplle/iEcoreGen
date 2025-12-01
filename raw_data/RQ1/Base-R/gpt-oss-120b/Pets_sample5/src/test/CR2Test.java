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
        Dog rex = new Dog("Rex");
        
        // Action: Add Rex to HomeA
        boolean result = homeA.addPet(rex);
        
        // Expected Output: true
        assertTrue("Adding new pet to empty house should return true", result);
        
        // Post-Condition: HomeA contains Rex
        List<Animal> pets = homeA.getPets();
        assertEquals("House should contain exactly 1 pet", 1, pets.size());
        assertEquals("Pet in house should be Rex", rex, pets.get(0));
        assertEquals("Rex should reference HomeA as its house", homeA, rex.getHouse());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInHouse() {
        // SetUp: Create House "HomeB" and "HomeC", Add Cat "Oliver" to HomeB
        House homeB = new House();
        House homeC = new House();
        Cat oliver = new Cat("Oliver");
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet that already belongs to another house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        List<Animal> petsC = homeC.getPets();
        assertTrue("HomeC should remain empty", petsC.isEmpty());
        assertEquals("Oliver should still reference HomeB as its house", homeB, oliver.getHouse());
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD", Add Dog "Rocky" to HomeD
        House homeD = new House();
        Dog rocky = new Dog("Rocky");
        homeD.addPet(rocky);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify house still contains only one instance of Rocky
        List<Animal> pets = homeD.getPets();
        assertEquals("House should still contain exactly 1 pet", 1, pets.size());
        assertEquals("Pet in house should still be Rocky", rocky, pets.get(0));
    }
    
    @Test
    public void testCase4_AddMultipleUniquePets() {
        // SetUp: Create House "HomeE", Create Dog "Bella" and Cat "Lucy" (no houses)
        House homeE = new House();
        Dog bella = new Dog("Bella");
        Cat lucy = new Cat("Lucy");
        
        // Action: Add Bella to HomeE → true, Add Lucy to HomeE → true
        boolean result1 = homeE.addPet(bella);
        boolean result2 = homeE.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue("First pet addition should return true", result1);
        assertTrue("Second pet addition should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        List<Animal> pets = homeE.getPets();
        assertEquals("House should contain exactly 2 pets", 2, pets.size());
        assertTrue("House should contain Bella", pets.contains(bella));
        assertTrue("House should contain Lucy", pets.contains(lucy));
        assertEquals("Bella should reference HomeE as its house", homeE, bella.getHouse());
        assertEquals("Lucy should reference HomeE as its house", homeE, lucy.getHouse());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF", Create Dog with null name
        House homeF = new House();
        Dog nullNamedDog = new Dog(null);
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify house remains empty
        List<Animal> pets = homeF.getPets();
        assertTrue("House should remain empty when adding pet with null name", pets.isEmpty());
        assertNull("Pet with null name should not have house reference set", nullNamedDog.getHouse());
    }
}