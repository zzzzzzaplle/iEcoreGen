import java.util.ArrayList;
import java.util.List;

abstract class Pet {
    private String name;
    private House house;

    public Pet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public abstract void makeNoise();

    public boolean belongsToHouse(House house) {
        return this.house != null && this.house.equals(house);
    }
}

class Dog extends Pet {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

class Cat extends Pet {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}

class House {
    private List<Pet> pets;

    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     *
     * @return A list of pet names. Returns an empty list if there are no pets.
     */
    public List<String> getAllPetNames() {
        List<String> petNames = new ArrayList<>();
        for (Pet pet : pets) {
            petNames.add(pet.getName());
        }
        return petNames;
    }

    /**
     * Adds a pet to the house.
     *
     * @param pet The pet to be added.
     * @return true if the pet is added successfully, false if the pet already belongs to any house or does not have a name.
     */
    public boolean addPet(Pet pet) {
        if (pet == null || pet.getName() == null || pet.getName().isEmpty()) {
            return false;
        }
        if (pet.belongsToHouse(this)) {
            return false;
        }
        pet.setHouse(this);
        return pets.add(pet);
    }

    /**
     * Removes a specified pet from its current house.
     *
     * @param pet The pet to be removed.
     * @return true if the pet is removed successfully, false if the pet does not belong to this house.
     */
    public boolean removePet(Pet pet) {
        if (pet == null || !pet.belongsToHouse(this)) {
            return false;
        }
        pet.setHouse(null);
        return pets.remove(pet);
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     *
     * @param petType The type of pet to retrieve ("dog" or "cat").
     * @return A list of pets of the specified type. Returns an empty list if there are no pets of the specified type.
     */
    public List<Pet> getPetsByType(String petType) {
        List<Pet> petsByType = new ArrayList<>();
        for (Pet pet : pets) {
            if (petType.equalsIgnoreCase("dog") && pet instanceof Dog) {
                petsByType.add(pet);
            } else if (petType.equalsIgnoreCase("cat") && pet instanceof Cat) {
                petsByType.add(pet);
            }
        }
        return petsByType;
    }

    /**
     * Counts the number of pets in this house.
     *
     * @return The number of pets in the house. Returns 0 if there are no pets.
     */
    public int countPets() {
        return pets.size();
    }
}