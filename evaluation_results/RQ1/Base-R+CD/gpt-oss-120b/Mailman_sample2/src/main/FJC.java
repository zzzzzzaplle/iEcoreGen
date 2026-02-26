import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mail carrier (mailman) who delivers registered mail.
 */
 class Mailman {

    private String name;
    private String id;

    /**
     * No‑argument constructor.
     */
    public Mailman() {
    }

    /**
     * Constructs a Mailman with the given name and identifier.
     *
     * @param name the mailman's name
     * @param id   a unique identifier for the mailman
     */
    public Mailman(String name, String id) {
        this.name = name;
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

    /** Getter for id. */
    public String getId() {
        return id;
    }

    /** Setter for id. */
    public void setId(String id) {
        this.id = id;
    }

    /** Equality based on unique identifier. */
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
 * Represents an inhabitant who can receive registered mail.
 */
 class Inhabitant {

    private String name;
    private String id;

    /**
     * No‑argument constructor.
     */
    public Inhabitant() {
    }

    /**
     * Constructs an Inhabitant with the given name and identifier.
     *
     * @param name the inhabitant's name
     * @param id   a unique identifier for the inhabitant
     */
    public Inhabitant(String name, String id) {
        this.name = name;
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

    /** Getter for id. */
    public String getId() {
        return id;
    }

    /** Setter for id. */
    public void setId(String id) {
        this.id = id;
    }

    /** Equality based on unique identifier. */
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
 * Abstract base class for all registered mail items.
 */
public abstract class RegisteredMail {

    private Mailman carrier;      // the mailman who delivers this item (may be null)
    private Inhabitant addressee; // the intended recipient (must not be null)

    /**
     * No‑argument constructor.
     */
    public RegisteredMail() {
    }

    /**
     * Constructs a RegisteredMail with the given addressee.
     *
     * @param addressee the inhabitant who will receive this mail
     */
    public RegisteredMail(Inhabitant addressee) {
        this.addressee = addressee;
    }

    /** Getter for carrier. */
    public Mailman getCarrier() {
        return carrier;
    }

    /** Setter for carrier. */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
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
 * Concrete class representing a registered Letter.
 */
 class Letter extends RegisteredMail {

    private String content; // optional additional data

    /**
     * No‑argument constructor.
     */
    public Letter() {
        super();
    }

    /**
     * Constructs a Letter with the given addressee and content.
     *
     * @param addressee the recipient of the letter
     * @param content   textual content of the letter
     */
    public Letter(Inhabitant addressee, String content) {
        super(addressee);
        this.content = content;
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
 * Concrete class representing a registered Parcel.
 */
 class Parcel extends RegisteredMail {

    private double weight; // optional additional data

    /**
     * No‑argument constructor.
     */
    public Parcel() {
        super();
    }

    /**
     * Constructs a Parcel with the given addressee and weight.
     *
     * @param addressee the recipient of the parcel
     * @param weight    weight of the parcel in kilograms
     */
    public Parcel(Inhabitant addressee, double weight) {
        super(addressee);
        this.weight = weight;
    }

    /** Getter for weight. */
    public double getWeight() {
        return weight;
    }

    /** Setter for weight. */
    public void setWeight(double weight) {
        this.weight = weight;
    }
}

/**
 * Represents a geographical area that groups mailmen, inhabitants and the
 * registered mail that circulates within it.
 */
 class GeographicalArea {

    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;

    /**
     * No‑argument constructor. Initializes internal collections.
     */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.allMails = new ArrayList<>();
    }

    /** Getter for mailmen list. */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /** Setter for mailmen list. */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /** Getter for inhabitants list. */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /** Setter for inhabitants list. */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /** Getter for allMails list. */
    public List<RegisteredMail> getAllMails() {
        return allMails;
    }

    /** Setter for allMails list. */
    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails;
    }

    /**
     * Assigns a specific mailman to deliver a registered mail item to a given
     * inhabitant. The mailman and the inhabitant must belong to this geographical
     * area, and the mail must not already have a carrier.
     *
     * @param carrier   the mailman who will deliver the mail
     * @param addressee the intended recipient of the mail
     * @param mail      the registered mail item to be assigned
     * @return {@code true} if the assignment succeeds; {@code false} otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier,
                                                Inhabitant addressee,
                                                RegisteredMail mail) {
        // Preconditions: carrier and addressee must be part of this area
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }

        // Mail must not already have a carrier
        if (mail.getCarrier() != null) {
            return false;
        }

        // Assign carrier and addressee
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);

        // Add the mail to the master list if it is not already present
        if (!allMails.contains(mail)) {
            allMails.add(mail);
        }

        return true;
    }

    /**
     * Adds a new mailman to this geographical area, provided that the mailman is
     * not already present.
     *
     * @param newMailman the mailman to be added
     * @return {@code true} if the mailman was added; {@code false} if the mailman
     * already exists in the area
     */
    public boolean addMailman(Mailman newMailman) {
        if (mailmen.contains(newMailman)) {
            return false;
        }
        mailmen.add(newMailman);
        return true;
    }

    /**
     * Removes a mailman from the area after reassigning all of his deliveries to
     * another existing mailman. The operation succeeds only if after removal at
     * least one mailman remains in the area.
     *
     * @param mailmanToRemove the mailman that should be removed
     * @param newMailman      the mailman who will take over the deliveries
     * @return {@code true} if removal and reassignment succeed; {@code false}
     * otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        // Both mailmen must be part of the area and must be distinct
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman)
                || mailmanToRemove.equals(newMailman)) {
            return false;
        }

        // Ensure that after removal at least one mailman remains
        if (mailmen.size() <= 1) {
            return false;
        }

        // Reassign all mails delivered by mailmanToRemove
        for (RegisteredMail mail : allMails) {
            if (mailmanToRemove.equals(mail.getCarrier())) {
                mail.setCarrier(newMailman);
            }
        }

        // Finally remove the mailman
        mailmen.remove(mailmanToRemove);
        return true;
    }

    /**
     * Adds a new inhabitant to this geographical area.
     *
     * @param newInhabitant the inhabitant to be added
     * @return {@code true} if the inhabitant was added; {@code false} if the
     * inhabitant already exists in the area
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (inhabitants.contains(newInhabitant)) {
            return false;
        }
        inhabitants.add(newInhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from the area. All registered mail that is already
     * assigned to a mailman and whose addressee is this inhabitant are deleted.
     *
     * @param inhabitant the inhabitant to be removed
     * @return {@code true} if the inhabitant existed and was removed; {@code false}
     * otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (!inhabitants.contains(inhabitant)) {
            return false;
        }

        // Remove the inhabitant from the list
        inhabitants.remove(inhabitant);

        // Remove any mail already assigned to a carrier whose addressee is this inhabitant
        List<RegisteredMail> toRemove = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (inhabitant.equals(mail.getAddressee()) && mail.getCarrier() != null) {
                toRemove.add(mail);
            }
        }
        allMails.removeAll(toRemove);
        return true;
    }

    /**
     * Retrieves all registered mail items that are assigned to the specified
     * mailman.
     *
     * @param carrier the mailman whose deliveries are to be listed
     * @return a {@link List} of {@link RegisteredMail} delivered by the given
     * mailman, or {@code null} if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (carrier.equals(mail.getCarrier())) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Retrieves all registered mail items that are addressed to the specified
     * inhabitant.
     *
     * @param addressee the inhabitant whose incoming mail is to be listed
     * @return a {@link List} of {@link RegisteredMail} for the given inhabitant,
     * or {@code null} if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (addressee.equals(mail.getAddressee())) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }
}