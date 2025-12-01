import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Abstract base class for all pets. A pet has a name and a reference to its home {@link House}.
 */
abstract class Pet {

    /** The name of the pet. */
    private String name;

    /** The house that currently owns this pet. */
    private House house;

    /** No‑argument constructor required for the task. */
    public Pet() {
        // default constructor
    }

    /**
     * Constructs a pet with the given name.
     *
     * @param name the name of the pet; may be {@code null} or empty – validation is performed elsewhere
     */
    public Pet(String name) {
        this.name = name;
    }

    /** @return the pet's name */
    public String getName() {
        return name;
    }

    /** @param name the pet's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the house this pet belongs to, or {@code null} if it does not belong to any house */
    public House getHouse() {
        return house;
    }

    /** @param house the house to assign this pet to */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Makes the pet produce its characteristic noise.
     */
    public abstract void makeNoise();
}

/**
 * A concrete implementation of a {@link Pet} representing a dog.
 */
class Dog extends Pet {

    /** No‑argument constructor required for the task. */
    public Dog() {
        super();
    }

    /**
     * Constructs a dog with the given name.
     *
     * @param name the dog's name
     */
    public Dog(String name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * A concrete implementation of a {@link Pet} representing a cat.
 */
class Cat extends Pet {

    /** No‑argument constructor required for the task. */
    public Cat() {
        super();
    }

    /**
     * Constructs a cat with the given name.
     *
     * @param name the cat's name
     */
    public Cat(String name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}

/**
 * Represents a house that can contain any number of {@link Pet}s (dogs and cats).
 */
class House {

    /** Internal list storing all pets that belong to this house. */
    private final List<Pet> pets = new ArrayList<>();

    /** No‑argument constructor required for the task. */
    public House() {
        // default constructor
    }

    /**
     * Retrieves a list of the names of all pets (dogs and cats) living in this house.
     * <p>
     * If the house contains no pets, an empty list is returned.
     *
     * @return an unmodifiable list of pet names; never {@code null}
     */
    public List<String> getAllPetNames() {
        if (pets.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> names = new ArrayList<>(pets.size());
        for (Pet pet : pets) {
            names.add(pet.getName());
        }
        return Collections.unmodifiableList(names);
    }

    /**
     * Attempts to add the supplied {@link Pet} to this house.
     * <p>
     * The addition succeeds only if:
     * <ul>
     *   <li>The pet has a non‑null, non‑empty name.</li>
     *   <li>The pet does not already belong to any house (including this one).</li>
     * </ul>
     *
     * @param pet the pet to add; must not be {@code null}
     * @return {@code true} if the pet was added successfully; {@code false} otherwise
     */
    public boolean addPet(Pet pet) {
        Objects.requireNonNull(pet, "Pet cannot be null");

        // Validate name
        String name = pet.getName();
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        // Ensure pet is not already owned
        if (pet.getHouse() != null) {
            return false;
        }

        // Add pet to house
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes the specified {@link Pet} from its current house.
     * <p>
     * The removal succeeds only if the pet currently belongs to this house.
     *
     * @param pet the pet to remove; must not be {@code null}
     * @return {@code true} if the pet was removed successfully; {@code false} otherwise
     */
    public boolean removePet(Pet pet) {
        Objects.requireNonNull(pet, "Pet cannot be null");

        // Verify ownership
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
     * Retrieves a list of pets of the specified type that live in this house.
     * <p>
     * The {@code type} argument must be either {@code "dog"} or {@code "cat"} (case‑insensitive).
     * If no pets of the requested type are present, an empty list is returned.
     *
     * @param type the type of pet to retrieve; valid values are {@code "dog"} or {@code "cat"}
     * @return an unmodifiable list of pets of the requested type; never {@code null}
     * @throws IllegalArgumentException if {@code type} is neither {@code "dog"} nor {@code "cat"}
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        String lowered = type.trim().toLowerCase();
        if (!lowered.equals("dog") && !lowered.equals("cat")) {
            throw new IllegalArgumentException("Invalid pet type: " + type);
        }

        List<Pet> result = new ArrayList<>();
        for (Pet pet : pets) {
            if (lowered.equals("dog") && pet instanceof Dog) {
                result.add(pet);
            } else if (lowered.equals("cat") && pet instanceof Cat) {
                result.add(pet);
            }
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Counts the total number of pets residing in this house.
     *
     * @return the number of pets; {@code 0} if there are none
     */
    public int countPets() {
        return pets.size();
    }

    /**
     * Provides read‑only access to the internal list of pets.
     * <p>
     * The returned list cannot be modified; attempts to modify it will result in an {@link UnsupportedOperationException}.
     *
     * @return an unmodifiable view of the pets list
     */
    public List<Pet> getPets() {
        return Collections.unmodifiableList(pets);
    }
}