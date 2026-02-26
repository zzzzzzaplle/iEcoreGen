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
        assertTrue("Should return true when adding new pet to empty house", result);
        
        // Post-Condition: HomeA contains Rex
        List<Pet> pets = homeA.getPets();
        assertEquals("HomeA should contain exactly 1 pet", 1, pets.size());
        assertTrue("HomeA should contain Rex", pets.contains(rex));
        assertEquals("Rex should live in HomeA", homeA, rex.getLived());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInTheHouse() {
        // SetUp: Create House "HomeB" and "HomeC", add Cat "Oliver" to HomeB
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
        List<Pet> homeCPets = homeC.getPets();
        assertTrue("HomeC should remain empty", homeCPets.isEmpty());
        assertEquals("Oliver should still live in HomeB", homeB, oliver.getLived());
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD", add Dog "Rocky" to HomeD
        House homeD = new House();
        Dog rocky = new Dog();
        rocky.setName("Rocky");
        homeD.addPet(rocky); // First addition
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Should return false when adding same pet twice to same house", result);
        
        // Verify HomeD still has only one pet (Rocky)
        List<Pet> pets = homeD.getPets();
        assertEquals("HomeD should contain exactly 1 pet", 1, pets.size());
        assertTrue("HomeD should contain Rocky", pets.contains(rocky));
    }
    
    @Test
    public void testCase4_AddMultipleUniquePets() {
        // SetUp: Create House "HomeE", Dog "Bella" and Cat "Lucy" (no houses)
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
        List<Pet> pets = homeE.getPets();
        assertEquals("HomeE should contain exactly 2 pets", 2, pets.size());
        assertTrue("HomeE should contain Bella", pets.contains(bella));
        assertTrue("HomeE should contain Lucy", pets.contains(lucy));
        assertEquals("Bella should live in HomeE", homeE, bella.getLived());
        assertEquals("Lucy should live in HomeE", homeE, lucy.getLived());
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF", Dog with null name
        House homeF = new House();
        Dog nullNamedDog = new Dog();
        // Don't set name, so it remains null
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse("Should return false when adding pet with null name", result);
        
        // Verify HomeF remains empty
        List<Pet> pets = homeF.getPets();
        assertTrue("HomeF should remain empty", pets.isEmpty());
        assertNull("Dog should not have a house assigned", nullNamedDog.getLived());
    }
}