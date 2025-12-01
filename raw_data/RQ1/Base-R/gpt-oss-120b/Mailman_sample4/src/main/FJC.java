import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a geographical area. Holds the inhabitants, mailmen and the
 * registered mail items that belong to this area.
 */
 class GeographicalArea {

    /** Unique identifier of the area (for simplicity). */
    private String areaId;

    /** List of inhabitants belonging to this area. */
    private List<Inhabitant> inhabitants = new ArrayList<>();

    /** List of mailmen assigned to this area. */
    private List<Mailman> mailmen = new ArrayList<>();

    /** List of all registered mail (letters and parcels) belonging to this area. */
    private List<RegisteredMail> registeredMails = new ArrayList<>();

    /** Un‑parameterised constructor required by the task. */
    public GeographicalArea() {
    }

    /* -------------------------------------------------------------
     *  Getters and Setters
     * ------------------------------------------------------------- */

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    public List<Mailman> getMailmen() {
        return mailmen;
    }

    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    public List<RegisteredMail> getRegisteredMails() {
        return registeredMails;
    }

    public void setRegisteredMails(List<RegisteredMail> registeredMails) {
        this.registeredMails = registeredMails;
    }

    /* -------------------------------------------------------------
     *  Functional requirement implementations
     * ------------------------------------------------------------- */

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     * <p>
     * The method checks that:
     * <ul>
     *   <li>Both the mailman and the addressee belong to this geographical area.</li>
     *   <li>The mail item has not already been assigned to a mailman.</li>
     * </ul>
     *
     * @param mail    the registered mail item to be assigned
     * @param mailman the mailman who will deliver the item
     * @return {@code true} if the assignment succeeded; {@code false} otherwise
     */
    public boolean assignMailToMailman(RegisteredMail mail, Mailman mailman) {
        if (mail == null || mailman == null) {
            return false;
        }
        // both must belong to this area
        if (!mailmen.contains(mailman) ||
                !inhabitants.contains(mail.getAddressee())) {
            return false;
        }
        // mail must belong to this area as well
        if (!registeredMails.contains(mail)) {
            return false;
        }
        // mail must not be already assigned
        if (mail.getAssignedMailman() != null) {
            return false;
        }
        mail.setAssignedMailman(mailman);
        return true;
    }

    /**
     * Retrieves all registered mail deliveries for this geographical area.
     * Each {@link RegisteredMail} contains a reference to the responsible
     * mailman (if already assigned) and the addressee.
     *
     * @return an immutable list of all registered mail items; empty if none exist
     */
    public List<RegisteredMail> retrieveAllDeliveries() {
        return Collections.unmodifiableList(new ArrayList<>(registeredMails));
    }

    /**
     * Adds a new inhabitant to this geographical area.
     *
     * @param inhabitant the inhabitant to add
     * @return {@code true} if the inhabitant was added; {@code false} if the inhabitant
     *         already belongs to this area or the argument is {@code null}
     */
    public boolean addInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null) {
            return false;
        }
        if (inhabitants.contains(inhabitant)) {
            return false;
        }
        inhabitant.setArea(this);
        inhabitants.add(inhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from this geographical area.
     * All registered mail that is addressed to the removed inhabitant and
     * already assigned to a mailman is deleted.
     *
     * @param inhabitant the inhabitant to remove
     * @return {@code true} if the inhabitant existed and was removed; {@code false}
     *         otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null || !inhabitants.contains(inhabitant)) {
            return false;
        }
        // Delete any registered mail addressed to this inhabitant that already
        // has an assigned mailman.
        registeredMails.removeIf(mail ->
                mail.getAddressee().equals(inhabitant) && mail.getAssignedMailman() != null);
        inhabitants.remove(inhabitant);
        inhabitant.setArea(null);
        return true;
    }

    /**
     * Adds a mailman to this geographical area if the mailman is not already
     * assigned to the area.
     *
     * @param mailman the mailman to add
     * @return {@code true} if the mailman was added; {@code false} if the mailman
     *         already belongs to this area or the argument is {@code null}
     */
    public boolean addMailman(Mailman mailman) {
        if (mailman == null) {
            return false;
        }
        if (mailmen.contains(mailman)) {
            return false;
        }
        mailman.setArea(this);
        mailmen.add(mailman);
        return true;
    }

    /**
     * Removes a mailman from this geographical area.
     * <p>
     * Conditions for successful removal:
     * <ul>
     *   <li>At least one other mailman must remain in the area after removal.</li>
     *   <li>A different existing mailman must be supplied to take over all
     *       deliveries currently assigned to the mailman being removed.</li>
     *   <li>All deliveries must be reassigned successfully before the mailman
     *       is removed.</li>
     * </ul>
     *
     * @param mailmanToRemove the mailman that should be removed
     * @param replacementMailman an existing mailman in the same area that will
     *                           receive the deliveries
     * @return {@code true} if removal and reassignment succeeded; {@code false}
     *         otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman replacementMailman) {
        if (mailmanToRemove == null || replacementMailman == null) {
            return false;
        }
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(replacementMailman)) {
            return false;
        }
        // Must keep at least one mailman after removal
        if (mailmen.size() <= 1) {
            return false;
        }
        // Reassign all deliveries
        for (RegisteredMail mail : registeredMails) {
            if (mailmanToRemove.equals(mail.getAssignedMailman())) {
                mail.setAssignedMailman(replacementMailman);
            }
        }
        // Finally remove the mailman
        mailmen.remove(mailmanToRemove);
        mailmanToRemove.setArea(null);
        return true;
    }

    /**
     * Lists all registered mail items (letters and parcels) directed to a
     * specified inhabitant. Only mail items that have the given inhabitant as
     * addressee are returned.
     *
     * @param inhabitant the inhabitant whose mail should be listed
     * @return a list of matching registered mail items, or {@code null} if none exist
     */
    public List<RegisteredMail> listMailForInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null) {
            return null;
        }
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : registeredMails) {
            if (inhabitant.equals(mail.getAddressee())) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }
}

/**
 * Represents an inhabitant (resident) of a geographical area.
 */
class Inhabitant {

    private String inhabitantId;
    private String name;
    private GeographicalArea area; // back‑reference to the area

    /** No‑arg constructor. */
    public Inhabitant() {
    }

    /* Getters and Setters */

    public String getInhabitantId() {
        return inhabitantId;
    }

    public void setInhabitantId(String inhabitantId) {
        this.inhabitantId = inhabitantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public GeographicalArea getArea() {
		return area;
	}

	public void setArea(GeographicalArea area) {
		this.area = area;
	}

    /* Equality based on unique identifier */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inhabitant that = (Inhabitant) o;
        return Objects.equals(inhabitantId, that.inhabitantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inhabitantId);
    }
}

/**
 * Represents a mailman (letter carrier) assigned to a geographical area.
 */
class Mailman {

    private String mailmanId;
    private String name;
    private GeographicalArea area; // back‑reference to the area

    /** No‑arg constructor. */
    public Mailman() {
    }

    /* Getters and Setters */

    public String getMailmanId() {
        return mailmanId;
    }

    public void setMailmanId(String mailmanId) {
        this.mailmanId = mailmanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public GeographicalArea getArea() {
		return area;
	}

	public void setArea(GeographicalArea area) {
		this.area = area;
	}

    /* Equality based on unique identifier */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mailman mailman = (Mailman) o;
        return Objects.equals(mailmanId, mailman.mailmanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailmanId);
    }
}

/**
 * Abstract base class for all registered mail items.
 */
abstract class RegisteredMail {

    private String mailId;
    private Inhabitant addressee;
    private Mailman assignedMailman; // null if not yet assigned

    /** No‑arg constructor. */
    public RegisteredMail() {
    }

    /* Getters and Setters */

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public Inhabitant getAddressee() {
        return addressee;
    }

    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }

    public Mailman getAssignedMailman() {
        return assignedMailman;
    }

    public void setAssignedMailman(Mailman assignedMailman) {
        this.assignedMailman = assignedMailman;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegisteredMail that = (RegisteredMail) o;
        return Objects.equals(mailId, that.mailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailId);
    }
}

/**
 * Concrete class representing a registered letter.
 */
class Letter extends RegisteredMail {

    private String content; // simple representation of the letter's text

    /** No‑arg constructor. */
    public Letter() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

/**
 * Concrete class representing a registered parcel.
 */
class Parcel extends RegisteredMail {

    private double weightKg; // weight of the parcel in kilograms
    private String description; // short description of contents

    /** No‑arg constructor. */
    public Parcel() {
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}