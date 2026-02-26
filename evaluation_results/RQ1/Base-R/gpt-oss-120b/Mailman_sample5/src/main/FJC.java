import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a geographical area. It contains the inhabitants,
 * mailmen and the registered mails that belong to this area.
 */
 class GeographicalArea {

    private String name;
    private final List<Inhabitant> inhabitants = new ArrayList<>();
    private final List<Mailman> mailmen = new ArrayList<>();
    private final List<RegisteredMail> registeredMails = new ArrayList<>();

    /** Unparameterized constructor */
    public GeographicalArea() {
    }

    /** Constructor with name */
    public GeographicalArea(String name) {
        this.name = name;
    }

    /* ---------- getters & setters ---------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public List<Inhabitant> getInhabitants() {
        return Collections.unmodifiableList(inhabitants);
    }

    public List<Mailman> getMailmen() {
        return Collections.unmodifiableList(mailmen);
    }

    public List<RegisteredMail> getRegisteredMails() {
        return Collections.unmodifiableList(registeredMails);
    }

    /* ---------- functional requirements ---------- */

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     * The mailman and the mail item must belong to the same geographical area,
     * and the mail must not have been assigned before.
     *
     * @param mail the registered mail to assign
     * @param mailman the mailman who will deliver the mail
     * @return {@code true} if the assignment is successful; {@code false} otherwise
     */
    public boolean assignMail(RegisteredMail mail, Mailman mailman) {
        if (mail == null || mailman == null) {
            return false;
        }
        // both must belong to this area
        if (!mail.getArea().equals(this) || !mailman.getArea().equals(this)) {
            return false;
        }
        // mail must not be already assigned
        if (mail.getAssignedMailman() != null) {
            return false;
        }
        mail.setAssignedMailman(mailman);
        // ensure mail is recorded in the area (if not already)
        if (!registeredMails.contains(mail)) {
            registeredMails.add(mail);
        }
        return true;
    }

    /**
     * Retrieves all registered mail deliveries for this geographical area,
     * together with the responsible mailman and the addressees.
     *
     * @return a list of {@link DeliveryRecord}; empty if no deliveries exist
     */
    public List<DeliveryRecord> retrieveDeliveries() {
        List<DeliveryRecord> result = new ArrayList<>();
        for (RegisteredMail mail : registeredMails) {
            if (mail.getAssignedMailman() != null) {
                result.add(new DeliveryRecord(mail, mail.getAssignedMailman(), mail.getAddressee()));
            }
        }
        return result;
    }

    /**
     * Adds a new inhabitant to this geographical area.
     *
     * @param inhabitant the inhabitant to add
     * @return {@code true} if the inhabitant was added; {@code false} if already present or null
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
     * All registered mails whose addressee is the removed inhabitant and which have already
     * been assigned to a mailman are deleted.
     *
     * @param inhabitant the inhabitant to remove
     * @return {@code true} if the operation succeeded; {@code false} otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null || !inhabitants.contains(inhabitant)) {
            return false;
        }
        inhabitants.remove(inhabitant);
        // Remove relevant mails
        registeredMails.removeIf(mail ->
                Objects.equals(mail.getAddressee(), inhabitant) && mail.getAssignedMailman() != null);
        return true;
    }

    /**
     * Adds a new mailman to this geographical area if they are not already present.
     *
     * @param mailman the mailman to add
     * @return {@code true} if the mailman was added; {@code false} otherwise
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
     * The removal succeeds only if at least one other mailman remains,
     * a replacement mailman (already present in the area) is supplied,
     * and all deliveries currently assigned to the removed mailman are
     * successfully reassigned to the replacement.
     *
     * @param toRemove the mailman to remove
     * @param replacement an existing mailman that will take over the deliveries
     * @return {@code true} if the removal and reassignment succeeded; {@code false} otherwise
     */
    public boolean removeMailman(Mailman toRemove, Mailman replacement) {
        if (toRemove == null || replacement == null) {
            return false;
        }
        if (!mailmen.contains(toRemove) || !mailmen.contains(replacement)) {
            return false;
        }
        // At least one mailman must stay after removal
        if (mailmen.size() <= 1) {
            return false;
        }

        // Reassign all mails assigned to toRemove
        for (RegisteredMail mail : registeredMails) {
            if (toRemove.equals(mail.getAssignedMailman())) {
                mail.setAssignedMailman(replacement);
            }
        }

        // Finally remove the mailman
        mailmen.remove(toRemove);
        return true;
    }

    /**
     * Lists all registered mail items (letters and parcels) directed to a given inhabitant.
     * Only mails that specify the given inhabitant as addressee are included.
     *
     * @param inhabitant the inhabitant whose mail items are requested
     * @return a list of {@link RegisteredMail} directed to the inhabitant,
     *         or {@code null} if none exist
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
 * Simple data holder that bundles a registered mail with its delivering mailman
 * and the addressee.
 */
class DeliveryRecord {

    private RegisteredMail mail;
    private Mailman mailman;
    private Inhabitant addressee;

    /** Unparameterized constructor */
    public DeliveryRecord() {
    }

    public DeliveryRecord(RegisteredMail mail, Mailman mailman, Inhabitant addressee) {
        this.mail = mail;
        this.mailman = mailman;
        this.addressee = addressee;
    }

    /* getters & setters */

    public RegisteredMail getMail() {
        return mail;
    }

    public void setMail(RegisteredMail mail) {
        this.mail = mail;
    }

    public Mailman getMailman() {
        return mailman;
    }

    public void setMailman(Mailman mailman) {
        this.mailman = mailman;
    }

    public Inhabitant getAddressee() {
        return addressee;
    }

    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Represents an inhabitant residing in a geographical area.
 */
class Inhabitant {

    private String name;
    private GeographicalArea area;

    /** Unparameterized constructor */
    public Inhabitant() {
    }

    public Inhabitant(String name) {
        this.name = name;
    }

    /* getters & setters */

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inhabitant that = (Inhabitant) o;
        return Objects.equals(name, that.name) && Objects.equals(area, that.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, area);
    }
}

/**
 * Represents a mailman assigned to a geographical area.
 */
class Mailman {

    private String name;
    private GeographicalArea area;

    /** Unparameterized constructor */
    public Mailman() {
    }

    public Mailman(String name) {
        this.name = name;
    }

    /* getters & setters */

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mailman mailman = (Mailman) o;
        return Objects.equals(name, mailman.name) && Objects.equals(area, mailman.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, area);
    }
}

/**
 * Abstract base class for all registered mail items (letters, parcels).
 */
abstract class RegisteredMail {

    private String id;
    private Inhabitant addressee;
    private Mailman assignedMailman;          // null if not yet assigned
    private GeographicalArea area;            // the area of the addressee

    /** Unparameterized constructor */
    public RegisteredMail() {
    }

    public RegisteredMail(String id, Inhabitant addressee) {
        this.id = id;
        this.addressee = addressee;
        if (addressee != null) {
            this.area = addressee.getArea();
        }
    }

    /* getters & setters */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Inhabitant getAddressee() {
        return addressee;
    }

    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
        if (addressee != null) {
            this.area = addressee.getArea();
        }
    }

    public Mailman getAssignedMailman() {
        return assignedMailman;
    }

    public void setAssignedMailman(Mailman assignedMailman) {
        this.assignedMailman = assignedMailman;
    }

    public GeographicalArea getArea() {
        return area;
    }

    public void setArea(GeographicalArea area) {
        this.area = area;
    }
}

/**
 * Concrete class representing a registered letter.
 */
class Letter extends RegisteredMail {

    private String content;   // optional field for demonstration

    /** Unparameterized constructor */
    public Letter() {
        super();
    }

    public Letter(String id, Inhabitant addressee, String content) {
        super(id, addressee);
        this.content = content;
    }

    /* getters & setters */

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

    private double weight;   // optional weight field

    /** Unparameterized constructor */
    public Parcel() {
        super();
    }

    public Parcel(String id, Inhabitant addressee, double weight) {
        super(id, addressee);
        this.weight = weight;
    }

    /* getters & setters */

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}