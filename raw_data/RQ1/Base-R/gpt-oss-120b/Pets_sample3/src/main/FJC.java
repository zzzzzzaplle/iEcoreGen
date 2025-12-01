import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract base class representing a generic animal.
 */
public abstract class Animal {
    private String name;
    private House house;

    /**
     * Unparameterized constructor.
     */
    public Animal() {
    }

    /**
     * Gets the name of the animal.
     *
     * @return the animal's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the animal.
     *
     * @param name the name to assign
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the house the animal belongs to.
     *
     * @return the animal's current house, or {@code null} if none
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house the animal belongs to.
     *
     * @param house the house to assign
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Makes the animal produce its characteristic noise.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog.
 */
 class Dog extends Animal {
    /**
     * Unparameterized constructor.
     */
    public Dog() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Represents a cat.
 */
 class Cat extends Animal {
    /**
     * Unparameterized constructor.
     */
    public Cat() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}

/**
 * Represents a house that can contain any number of pets.
 */
 class House {
    private Set<Animal> pets;

    /**
     * Unparameterized constructor.
     */
    public House() {
        this.pets = new HashSet<>();
    }

    /**
     * Gets the set of pets currently living in this house.
     *
     * @return the set of pets
     */
    public Set<Animal> getPets() {
        return pets;
    }

    /**
     * Sets the set of pets for this house.
     *
     * @param pets the set of pets to assign
     */
    public void setPets(Set<Animal> pets) {
        this.pets = pets;
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     *
     * @return a list of pet names; empty if there are no pets
     */
    public List<String> getAllPetNames() {
        List<String> names = new ArrayList<>();
        for (Animal pet : pets) {
            if (pet.getName() != null) {
                names.add(pet.getName());
            }
        }
        return names;
    }

    /**
     * Adds a pet to this house.
     *
     * @param pet the pet to add
     * @return {@code true} if the pet was added successfully; {@code false} if the pet
     *         already belongs to any house (including this one) or the pet does not have a name
     */
    public boolean addPet(Animal pet) {
        if (pet == null) {
            return false;
        }
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            return false;
        }
        if (pet.getHouse() != null) {
            return false;
        }
        pet.setHouse(this);
        return pets.add(pet);
    }

    /**
     * Removes a specified pet from its current house.
     *
     * @param pet the pet to remove
     * @return {@code true} if the pet was removed successfully; {@code false} if the pet
     *         does not belong to this house
     */
    public boolean removePet(Animal pet) {
        if (pet == null) {
            return false;
        }
        if (pet.getHouse() != this) {
            return false;
        }
        boolean removed = pets.remove(pet);
        if (removed) {
            pet.setHouse(null);
        }
        return removed;
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") living in this house.
     *
     * @param type the type of pet to retrieve; expected values are "dog" or "cat"
     * @return a list of pets of the specified type; empty if there are none
     */
    public List<Animal> getPetsByType(String type) {
        List<Animal> result = new ArrayList<>();
        if (type == null) {
            return result;
        }
        String lower = type.toLowerCase();
        for (Animal pet : pets) {
            if ("dog".equals(lower) && pet instanceof Dog) {
                result.add(pet);
            } else if ("cat".equals(lower) && pet instanceof Cat) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets in this house.
     *
     * @return the number of pets; 0 if none
     */
    public int getPetCount() {
        return pets.size();
    }
}