import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a house that can contain any number of {@link Pet} objects.
 */
class House {

    /** List that stores the pets living in this house. */
    private List<Pet> pets;

    /** Default constructor – creates an empty list of pets. */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves the names of all pets (dogs and cats) living in this house.
     *
     * @return a mutable {@link List} of pet names; never {@code null}. Returns an empty list if there are no pets.
     */
    public List<String> retrievePetNames() {
        List<String> names = new ArrayList<>();
        for (Pet pet : pets) {
            names.add(pet.getName());
        }
        return names;
    }

    /**
     * Adds a pet to this house.
     *
     * <p>The pet is added only if:
     * <ul>
     *   <li>the pet's name is not {@code null} or empty, and</li>
     *   <li>the pet does not already belong to any house (including this one).</li>
     * </ul>
     *
     * @param pet the {@link Pet} to be added; must not be {@code null}
     * @return {@code true} if the pet was added successfully; {@code false} otherwise
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        // name must be present
        String name = pet.getName();
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // pet must not already belong to a house
        if (pet.getLived() != null) {
            return false;
        }
        // add to the list and set the back‑reference
        boolean added = pets.add(pet);
        if (added) {
            pet.setLived(this);
        }
        return added;
    }

    /**
     * Removes the specified pet from this house.
     *
     * @param pet the {@link Pet} to be removed; must not be {@code null}
     * @return {@code true} if the pet belonged to this house and was removed; {@code false} otherwise
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            return false;
        }
        // pet must belong to this house
        if (!this.equals(pet.getLived())) {
            return false;
        }
        boolean removed = pets.remove(pet);
        if (removed) {
            pet.setLived(null);
        }
        return removed;
    }

    /**
     * Retrieves a list of pets of a specific type that live in this house.
     *
     * @param type the type of pet to filter; accepted values are {@code "dog"} or {@code "cat"} (case‑insensitive)
     * @return a mutable {@link List} of matching pets; never {@code null}. Returns an empty list if none match.
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            return Collections.emptyList();
        }
        String lowered = type.trim().toLowerCase();
        List<Pet> result = new ArrayList<>();
        for (Pet pet : pets) {
            if (("dog".equals(lowered) && pet instanceof Dog) ||
                ("cat".equals(lowered) && pet instanceof Cat)) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets currently living in this house.
     *
     * @return the number of pets; {@code 0} if there are none.
     */
    public int getPetCount() {
        return pets.size();
    }

    /** Getter for the internal pet list (mainly for testing). */
    public List<Pet> getPets() {
        return pets;
    }

    /** Setter for the internal pet list (use with care). */
    public void setPets(List<Pet> pets) {
        this.pets = pets != null ? pets : new ArrayList<>();
        // update back‑references for consistency
        for (Pet pet : this.pets) {
            pet.setLived(this);
        }
    }
}

/**
 * Abstract base class for all pets.
 */
abstract class Pet {

    /** Name of the pet. */
    private String name;

    /** The house this pet lives in; {@code null} if it has no home. */
    private House lived;

    /** Default constructor. */
    public Pet() {
        // empty
    }

    /**
     * Returns the pet's name.
     *
     * @return the name; may be {@code null}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for the pet.
     *
     * @param newName the new name; must not be {@code null} or empty
     * @return {@code true} if the name was set; {@code false} otherwise
     */
    public boolean setName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            return false;
        }
        this.name = newName;
        return true;
    }

    /**
     * Returns the house this pet lives in.
     *
     * @return the {@link House} instance or {@code null} if the pet has no home
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house this pet lives in.
     *
     * @param house the new home; may be {@code null} to indicate no home
     */
    public void setLived(House house) {
        this.lived = house;
    }

    /**
     * Makes the pet produce its characteristic noise.
     */
    public abstract void makeNoise();
}

/**
 * Concrete implementation of a dog.
 */
class Dog extends Pet {

    /** Default constructor. */
    public Dog() {
        // empty
    }

    /**
     * Constructs a dog with the given name.
     *
     * @param name the dog's name; must be non‑null and non‑empty
     */
    public Dog(String name) {
        setName(name);
    }

    @Override
    public void makeNoise() {
        System.out.println(getName() + " says: Woof!");
    }
}

/**
 * Concrete implementation of a cat.
 */
class Cat extends Pet {

    /** Default constructor. */
    public Cat() {
        // empty
    }

    /**
     * Constructs a cat with the given name.
     *
     * @param name the cat's name; must be non‑null and non‑empty
     */
    public Cat(String name) {
        setName(name);
    }

    @Override
    public void makeNoise() {
        System.out.println(getName() + " says: Meow!");
    }
}