import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a house that can contain any number of pets (dogs and cats).
 */
 class House {

    /** List storing the pets that live in this house. */
    private List<Pet> pets;

    /**
     * No‑argument constructor that creates an empty house.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Returns the mutable list of pets living in this house.
     *
     * @return the list of {@link Pet} objects
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Replaces the current list of pets with the supplied list.
     *
     * @param pets the new list of pets; if {@code null} an empty list is set
     */
    public void setPets(List<Pet> pets) {
        this.pets = (pets != null) ? pets : new ArrayList<>();
    }

    /**
     * Retrieves the names of all pets (dogs and cats) living in this house.
     *
     * @return a list of pet names; never {@code null} but possibly empty
     */
    public List<String> retrievePetNames() {
        List<String> names = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getName() != null) {
                names.add(pet.getName());
            }
        }
        return names;
    }

    /**
     * Adds a pet to this house.
     *
     * <p>The method fails and returns {@code false} if:
     * <ul>
     *   <li>the pet already belongs to any house (including this one), or</li>
     *   <li>the pet does not have a valid (non‑null, non‑empty) name.</li>
     * </ul>
     *
     * @param pet the {@link Pet} to add
     * @return {@code true} if the pet was added successfully; {@code false} otherwise
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        // pet must have a name
        String name = pet.getName();
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // pet must not already belong to a house
        if (pet.getHouse() != null) {
            return false;
        }
        // add pet and set its house reference
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from its current house.
     *
     * @param pet the {@link Pet} to remove
     * @return {@code true} if the pet was removed successfully;
     *         {@code false} if the pet does not belong to this house
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            return false;
        }
        // pet must belong to this house
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
     * Retrieves all pets of a specific type that live in this house.
     *
     * @param type the type of pet to retrieve; expected values are {@code "dog"} or {@code "cat"}
     * @return a list of pets matching the requested type; never {@code null} but possibly empty
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            return Collections.emptyList();
        }
        List<Pet> result = new ArrayList<>();
        String lowered = type.trim().toLowerCase();
        for (Pet pet : pets) {
            if ("dog".equals(lowered) && pet instanceof Dog) {
                result.add(pet);
            } else if ("cat".equals(lowered) && pet instanceof Cat) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets living in this house.
     *
     * @return the total number of pets; {@code 0} if there are none
     */
    public int getPetCount() {
        return pets.size();
    }
}

/**
 * Abstract base class for all pets.
 */
abstract class Pet {

    /** The name of the pet. */
    private String name;

    /** The house this pet belongs to; {@code null} if it does not belong to any house. */
    private House house;

    /**
     * No‑argument constructor.
     */
    public Pet() {
        // name and house remain null until set
    }

    /**
     * Returns the pet's name.
     *
     * @return the name, or {@code null} if not set
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the pet's name.
     *
     * @param name the name to assign; may be {@code null}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the house this pet belongs to.
     *
     * @return the {@link House} instance, or {@code null} if the pet is homeless
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house this pet belongs to.
     *
     * @param house the {@link House} to associate with this pet; may be {@code null}
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Causes the pet to make its characteristic noise.
     */
    public abstract void makeNoise();
}

/**
 * Concrete implementation of a dog.
 */
class Dog extends Pet {

    /**
     * No‑argument constructor.
     */
    public Dog() {
        super();
    }

    /**
     * {@inheritDoc}
     *
     * The dog barks.
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Concrete implementation of a cat.
 */
class Cat extends Pet {

    /**
     * No‑argument constructor.
     */
    public Cat() {
        super();
    }

    /**
     * {@inheritDoc}
     *
     * The cat meows.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}