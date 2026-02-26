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
        List<Pet> pets = homeA.getPets();
        assertEquals("House should contain 1 pet after addition", 1, pets.size());
        assertEquals("House should contain Rex", "Rex", pets.get(0).getName());
        assertTrue("Pet in house should be a Dog", pets.get(0) instanceof Dog);
        assertEquals("Pet's house should be set to HomeA", homeA, rex.getHouse());
    }
    
    @Test
    public void testCase2_AddPetAlreadyInAnotherHouse() {
        // SetUp: Create House "HomeB" and "HomeC", add Cat "Oliver" to HomeB
        House homeB = new House();
        House homeC = new House();
        Cat oliver = new Cat("Oliver");
        homeB.addPet(oliver);
        
        // Action: Add Oliver to HomeC
        boolean result = homeC.addPet(oliver);
        
        // Expected Output: false
        assertFalse("Adding pet already in another house should return false", result);
        
        // Post-Condition: HomeC remains unchanged
        List<Pet> homeCPets = homeC.getPets();
        assertEquals("HomeC should remain empty", 0, homeCPets.size());
        
        // Verify Oliver is still in HomeB
        List<Pet> homeBPets = homeB.getPets();
        assertEquals("HomeB should still contain Oliver", 1, homeBPets.size());
        assertEquals("Oliver should still be in HomeB", "Oliver", homeBPets.get(0).getName());
        assertEquals("Oliver's house should still be HomeB", homeB, oliver.getHouse());
    }
    
    @Test
    public void testCase3_AddSamePetTwiceToSameHouse() {
        // SetUp: Create House "HomeD", add Dog "Rocky" to HomeD
        House homeD = new House();
        Dog rocky = new Dog("Rocky");
        homeD.addPet(rocky);
        
        // Action: Attempt to add Rocky again to HomeD
        boolean result = homeD.addPet(rocky);
        
        // Expected Output: false
        assertFalse("Adding same pet twice to same house should return false", result);
        
        // Verify house still contains only one instance of Rocky
        List<Pet> pets = homeD.getPets();
        assertEquals("House should contain only one pet", 1, pets.size());
        assertEquals("House should contain Rocky", "Rocky", pets.get(0).getName());
        assertEquals("Rocky's house should still be HomeD", homeD, rocky.getHouse());
    }
    
    @Test
    public void testCase4_AddMultipleUniquePets() {
        // SetUp: Create House "HomeE", create Dog "Bella" and Cat "Lucy" (no houses)
        House homeE = new House();
        Dog bella = new Dog("Bella");
        Cat lucy = new Cat("Lucy");
        
        // Action: Add Bella to HomeE → true, Add Lucy to HomeE → true
        boolean result1 = homeE.addPet(bella);
        boolean result2 = homeE.addPet(lucy);
        
        // Expected Output: Both operations return true
        assertTrue("Adding Bella should return true", result1);
        assertTrue("Adding Lucy should return true", result2);
        
        // Post-Condition: HomeE contains both pets
        List<Pet> pets = homeE.getPets();
        assertEquals("House should contain 2 pets", 2, pets.size());
        
        // Verify both pets are in the house with correct names and types
        boolean foundBella = false;
        boolean foundLucy = false;
        for (Pet pet : pets) {
            if (pet.getName().equals("Bella") && pet instanceof Dog) {
                foundBella = true;
                assertEquals("Bella's house should be HomeE", homeE, pet.getHouse());
            } else if (pet.getName().equals("Lucy") && pet instanceof Cat) {
                foundLucy = true;
                assertEquals("Lucy's house should be HomeE", homeE, pet.getHouse());
            }
        }
        assertTrue("House should contain Bella", foundBella);
        assertTrue("House should contain Lucy", foundLucy);
    }
    
    @Test
    public void testCase5_AddPetWithNullName() {
        // SetUp: Create House "HomeF", create Dog with null name
        House homeF = new House();
        Dog nullNamedDog = new Dog();
        nullNamedDog.setName(null);
        
        // Action: Add null-named dog to HomeF
        boolean result = homeF.addPet(nullNamedDog);
        
        // Expected Output: false
        assertFalse("Adding pet with null name should return false", result);
        
        // Verify house remains empty
        List<Pet> pets = homeF.getPets();
        assertEquals("House should remain empty", 0, pets.size());
        
        // Verify pet's house is not set
        assertNull("Pet with null name should not have house set", nullNamedDog.getHouse());
    }
}