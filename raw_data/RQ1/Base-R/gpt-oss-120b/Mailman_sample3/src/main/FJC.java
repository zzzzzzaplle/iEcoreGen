import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a geographical area. It holds the inhabitants, mailmen and all registered
 * mail items (letters and parcels) that belong to this area.
 */
 class GeographicalArea {

    private String name;
    private final List<Inhabitant> inhabitants = new ArrayList<>();
    private final List<Mailman> mailmen = new ArrayList<>();
    private final List<RegisteredMail> registeredMails = new ArrayList<>();

    /** Unparameterized constructor. */
    public GeographicalArea() {
    }

    /** Getter for the area name. */
    public String getName() {
        return name;
    }

    /** Setter for the area name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns an unmodifiable view of the inhabitants in this area. */
    public List<Inhabitant> getInhabitants() {
        return Collections.unmodifiableList(inhabitants);
    }

    /** Returns an unmodifiable view of the mailmen in this area. */
    public List<Mailman> getMailmen() {
        return Collections.unmodifiableList(mailmen);
    }

    /** Returns an unmodifiable view of all registered mail items in this area. */
    public List<RegisteredMail> getRegisteredMails() {
        return Collections.unmodifiableList(registeredMails);
    }

    /* --------------------------------------------------------------
     *  Functional requirements implementation
     * -------------------------------------------------------------- */

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     * Both the mail and the mailman must belong to this geographical area,
     * and the mail must not already be assigned.
     *
     * @param mail    the registered mail to assign
     * @param mailman the mailman who will deliver the mail
     * @return {@code true} if the assignment succeeds; {@code false} otherwise
     */
    public boolean assignMail(RegisteredMail mail, Mailman mailman) {
        if (mail == null || mailman == null) {
            return false;
        }
        if (!inhabitants.contains(mail.getAddressee())) {
            // addressee does not belong to this area
            return false;
        }
        if (!mailmen.contains(mailman)) {
            // mailman does not belong to this area
            return false;
        }
        if (mail.getAssignedMailman() != null) {
            // already assigned
            return false;
        }
        mail.setAssignedMailman(mailman);
        return true;
    }

    /**
     * Retrieves all registered mail deliveries for this geographical area.
     * Each entry contains the mail item, the responsible mailman and the addressee.
     *
     * @return a list of {@link DeliveryInfo}; empty list if no deliveries exist
     */
    public List<DeliveryInfo> getAllDeliveries() {
        List<DeliveryInfo> result = new ArrayList<>();
        for (RegisteredMail mail : registeredMails) {
            if (mail.getAssignedMailman() != null) {
                result.add(new DeliveryInfo(mail, mail.getAssignedMailman(), mail.getAddressee()));
            }
        }
        return result;
    }

    /**
     * Adds a new inhabitant to this geographical area.
     *
     * @param inhabitant the inhabitant to add
     * @return {@code true} if the inhabitant was added; {@code false} if the inhabitant
     *         already exists in the area or the argument is {@code null}
     */
    public boolean addInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null) {
            return false;
        }
        if (inhabitants.contains(inhabitant)) {
            return false;
        }
        inhabitants.add(inhabitant);
        inhabitant.setArea(this);
        return true;
    }

    /**
     * Removes an inhabitant from the area. All registered mail whose addressee is this
     * inhabitant and which has already been assigned to a mailman are deleted.
     *
     * @param inhabitant the inhabitant to remove
     * @return {@code true} if the inhabitant existed and was removed; {@code false}
     *         otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null || !inhabitants.contains(inhabitant)) {
            return false;
        }
        // Remove assigned mails that belong to this inhabitant
        registeredMails.removeIf(mail ->
                mail.getAddressee().equals(inhabitant) && mail.getAssignedMailman() != null);
        // Remove unassigned mails that belong to this inhabitant as well (optional)
        registeredMails.removeIf(mail -> mail.getAddressee().equals(inhabitant));
        inhabitants.remove(inhabitant);
        inhabitant.setArea(null);
        return true;
    }

    /**
     * Adds a mailman to this area if they are not already present.
     *
     * @param mailman the mailman to add
     * @return {@code true} if the mailman was added; {@code false} if they already exist
     *         in the area or the argument is {@code null}
     */
    public boolean addMailman(Mailman mailman) {
        if (mailman == null) {
            return false;
        }
        if (mailmen.contains(mailman)) {
            return false;
        }
        mailmen.add(mailman);
        mailman.setArea(this);
        return true;
    }

    /**
     * Removes a mailman from the area. At least one mailman must remain.
     * All deliveries currently assigned to the removed mailman are reassigned
     * to the specified replacement mailman.
     *
     * @param toRemove          the mailman to be removed
     * @param replacementMailman an existing mailman in the same area who will take over
     *                           the deliveries
     * @return {@code true} if removal and reassignment succeed; {@code false}
     *         otherwise
     */
    public boolean removeMailman(Mailman toRemove, Mailman replacementMailman) {
        if (toRemove == null || replacementMailman == null) {
            return false;
        }
        if (!mailmen.contains(toRemove) || !mailmen.contains(replacementMailman)) {
            return false;
        }
        if (mailmen.size() <= 1) {
            // need to keep at least one mailman
            return false;
        }
        // Reassign all deliveries
        for (RegisteredMail mail : registeredMails) {
            if (toRemove.equals(mail.getAssignedMailman())) {
                mail.setAssignedMailman(replacementMailman);
            }
        }
        mailmen.remove(toRemove);
        toRemove.setArea(null);
        return true;
    }

    /**
     * Lists all registered mail items (letters and parcels) addressed to a specific inhabitant.
     *
     * @param inhabitant the inhabitant whose mail should be listed
     * @return a list of mail items; {@code null} if none exist or if the inhabitant
     *         does not belong to this area
     */
    public List<RegisteredMail> getMailForInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null || !inhabitants.contains(inhabitant)) {
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

    /**
     * Adds a registered mail item (letter or parcel) to this area.
     *
     * @param mail the mail item to add
     * @return {@code true} if added; {@code false} if the mail is {@code null},
     *         its addressee does not belong to this area, or it already exists
     */
    public boolean addRegisteredMail(RegisteredMail mail) {
        if (mail == null) {
            return false;
        }
        if (!inhabitants.contains(mail.getAddressee())) {
            return false;
        }
        if (registeredMails.contains(mail)) {
            return false;
        }
        registeredMails.add(mail);
        return true;
    }
}

/**
 * Simple data holder for delivery information.
 */
class DeliveryInfo {
    private RegisteredMail mail;
    private Mailman mailman;
    private Inhabitant addressee;

    /** Unparameterized constructor. */
    public DeliveryInfo() {
    }

    /** Parameterized constructor for convenience. */
    public DeliveryInfo(RegisteredMail mail, Mailman mailman, Inhabitant addressee) {
        this.mail = mail;
        this.mailman = mailman;
        this.addressee = addressee;
    }

    /** Getter for mail. */
    public RegisteredMail getMail() {
        return mail;
    }

    /** Setter for mail. */
    public void setMail(RegisteredMail mail) {
        this.mail = mail;
    }

    /** Getter for mailman. */
    public Mailman getMailman() {
        return mailman;
    }

    /** Setter for mailman. */
    public void setMailman(Mailman mailman) {
        this.mailman = mailman;
    }

    /** Getter for addressee. */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /** Setter for addressee. */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Represents an inhabitant living in a geographical area.
 */
class Inhabitant {
    private String id;
    private String name;
    private GeographicalArea area; // back‑reference; may be null

    /** Unparameterized constructor. */
    public Inhabitant() {
    }

    /** Getter for id. */
    public String getId() {
        return id;
    }

    /** Setter for id. */
    public void setId(String id) {
        this.id = id;
    }

    /** Getter for name. */
    public String getName() {
        return name;
    }

    /** Setter for name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for the area. */
    public GeographicalArea getArea() {
        return area;
    }

    /** Setter for the area. */
    public void setArea(GeographicalArea area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inhabitant that = (Inhabitant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents a mailman who delivers registered mail within a geographical area.
 */
class Mailman {
    private String id;
    private String name;
    private GeographicalArea area; // back‑reference; may be null

    /** Unparameterized constructor. */
    public Mailman() {
    }

    /** Getter for id. */
    public String getId() {
        return id;
    }

    /** Setter for id. */
    public void setId(String id) {
        this.id = id;
    }

    /** Getter for name. */
    public String getName() {
        return name;
    }

    /** Setter for name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for the area. */
    public GeographicalArea getArea() {
        return area;
    }

    /** Setter for the area. */
    public void setArea(GeographicalArea area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mailman mailman = (Mailman) o;
        return Objects.equals(id, mailman.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Abstract base class for registered mail items (letters and parcels).
 */
abstract class RegisteredMail {
    private String trackingNumber;
    private Inhabitant addressee;
    private Mailman assignedMailman; // null if not yet assigned

    /** Unparameterized constructor. */
    public RegisteredMail() {
    }

    /** Getter for tracking number. */
    public String getTrackingNumber() {
        return trackingNumber;
    }

    /** Setter for tracking number. */
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    /** Getter for addressee. */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /** Setter for addressee. */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }

    /** Getter for the assigned mailman. */
    public Mailman getAssignedMailman() {
        return assignedMailman;
    }

    /** Setter for the assigned mailman. */
    public void setAssignedMailman(Mailman assignedMailman) {
        this.assignedMailman = assignedMailman;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegisteredMail that = (RegisteredMail) o;
        return Objects.equals(trackingNumber, that.trackingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber);
    }
}

/**
 * Concrete class representing a registered letter.
 */
class Letter extends RegisteredMail {
    private String content; // simple representation of the letter's content

    /** Unparameterized constructor. */
    public Letter() {
    }

    /** Getter for content. */
    public String getContent() {
        return content;
    }

    /** Setter for content. */
    public void setContent(String content) {
        this.content = content;
    }
}

/**
 * Concrete class representing a registered parcel.
 */
class Parcel extends RegisteredMail {
    private double weight; // in kilograms
    private String description; // brief description of the parcel

    /** Unparameterized constructor. */
    public Parcel() {
    }

    /** Getter for weight. */
    public double getWeight() {
        return weight;
    }

    /** Setter for weight. */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /** Getter for description. */
    public String getDescription() {
        return description;
    }

    /** Setter for description. */
    public void setDescription(String description) {
        this.description = description;
    }
}