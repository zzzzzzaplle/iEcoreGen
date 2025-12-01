import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mail carrier working in a geographical area.
 */
class Mailman {

    private String name;
    private String id;

    /** No‑argument constructor */
    public Mailman() {
    }

    /** Constructor with fields */
    public Mailman(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /** Getter for {@code id}. */
    public String getId() {
        return id;
    }

    /** Setter for {@code id}. */
    public void setId(String id) {
        this.id = id;
    }

    /** Getter for {@code name}. */
    public String getName() {
        return name;
    }

    /** Setter for {@code name}. */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on {@code id}. */
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
 * Represents an inhabitant of a geographical area.
 */
class Inhabitant {

    private String name;
    private String id;

    /** No‑argument constructor */
    public Inhabitant() {
    }

    /** Constructor with fields */
    public Inhabitant(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /** Getter for {@code id}. */
    public String getId() {
        return id;
    }

    /** Setter for {@code id}. */
    public void setId(String id) {
        this.id = id;
    }

    /** Getter for {@code name}. */
    public String getName() {
        return name;
    }

    /** Setter for {@code name}. */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on {@code id}. */
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
abstract class RegisteredMail {

    private Mailman carrier;          // may be null until assignment
    private Inhabitant addressee;     // must be non‑null

    /** No‑argument constructor */
    public RegisteredMail() {
    }

    /** Constructor with addressee */
    public RegisteredMail(Inhabitant addressee) {
        this.addressee = addressee;
    }

    /** Getter for {@code carrier}. */
    public Mailman getCarrier() {
        return carrier;
    }

    /** Setter for {@code carrier}. */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /** Getter for {@code addressee}. */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /** Setter for {@code addressee}. */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Concrete class representing a registered letter.
 */
class Letter extends RegisteredMail {

    /** No‑argument constructor */
    public Letter() {
        super();
    }

    /** Constructor with addressee */
    public Letter(Inhabitant addressee) {
        super(addressee);
    }
}

/**
 * Concrete class representing a registered parcel.
 */
class Parcel extends RegisteredMail {

    /** No‑argument constructor */
    public Parcel() {
        super();
    }

    /** Constructor with addressee */
    public Parcel(Inhabitant addressee) {
        super(addressee);
    }
}

/**
 * Represents a geographical area that groups mailmen, inhabitants and registered mail.
 */
class GeographicalArea {

    private List<Mailman> mailmen = new ArrayList<>();
    private List<Inhabitant> inhabitants = new ArrayList<>();
    private List<RegisteredMail> allMails = new ArrayList<>();

    /** No‑argument constructor */
    public GeographicalArea() {
    }

    /** Getter for {@code mailmen}. */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /** Setter for {@code mailmen}. */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /** Getter for {@code inhabitants}. */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /** Setter for {@code inhabitants}. */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /** Getter for {@code allMails}. */
    public List<RegisteredMail> getAllMails() {
        return allMails;
    }

    /** Setter for {@code allMails}. */
    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails;
    }

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     *
     * @param carrier   the mailman that will carry the mail
     * @param addressee the intended recipient of the mail
     * @param mail      the registered mail to be assigned
     * @return {@code true} if the assignment succeeds; {@code false} otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        // Preconditions: carrier and addressee must belong to this area,
        // and the mail must not already have a carrier.
        if (carrier == null || addressee == null || mail == null) {
            return false;
        }

        boolean carrierInArea = mailmen.contains(carrier);
        boolean addresseeInArea = inhabitants.contains(addressee);
        boolean mailUnassigned = mail.getCarrier() == null;
        boolean mailPresent = allMails.contains(mail);

        if (carrierInArea && addresseeInArea && mailUnassigned && mailPresent) {
            mail.setCarrier(carrier);
            // Ensure the addressee of the mail matches the provided addressee
            mail.setAddressee(addressee);
            return true;
        }
        return false;
    }

    /**
     * Adds a mailman to the area if they are not already present.
     *
     * @param newMailman the mailman to be added
     * @return {@code true} if the mailman was added; {@code false} if already present or null
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null) {
            return false;
        }
        if (!mailmen.contains(newMailman)) {
            mailmen.add(newMailman);
            return true;
        }
        return false;
    }

    /**
     * Removes a mailman from the area after reassigning all of his deliveries.
     *
     * @param mailmanToRemove the mailman that should be removed
     * @param newMailman      an existing mailman who will take over the deliveries
     * @return {@code true} if removal (and reassignment) succeeds; {@code false} otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            return false;
        }

        // Must keep at least one mailman after removal
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman) || mailmen.size() <= 1) {
            return false;
        }

        // Reassign every mail delivered by the mailman to be removed
        for (RegisteredMail mail : allMails) {
            if (mailmanToRemove.equals(mail.getCarrier())) {
                mail.setCarrier(newMailman);
            }
        }

        // Finally, remove the mailman
        return mailmen.remove(mailmanToRemove);
    }

    /**
     * Adds a new inhabitant to the area.
     *
     * @param newInhabitant the inhabitant to add
     * @return {@code true} if the inhabitant was added; {@code false} if already present or null
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null) {
            return false;
        }
        if (!inhabitants.contains(newInhabitant)) {
            inhabitants.add(newInhabitant);
            return true;
        }
        return false;
    }

    /**
     * Removes an inhabitant from the area and deletes any already‑assigned mail addressed to them.
     *
     * @param inhabitant the inhabitant to remove
     * @return {@code true} if the inhabitant existed and removal succeeded; {@code false} otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null) {
            return false;
        }
        if (!inhabitants.contains(inhabitant)) {
            return false;
        }

        // Remove mail that is addressed to this inhabitant and already has a carrier
        allMails.removeIf(mail -> inhabitant.equals(mail.getAddressee()) && mail.getCarrier() != null);

        // Finally, remove the inhabitant from the list
        return inhabitants.remove(inhabitant);
    }

    /**
     * Retrieves all registered mail items that are assigned to the specified mailman.
     *
     * @param carrier the mailman whose deliveries are requested
     * @return a {@link List} of {@link RegisteredMail} objects assigned to {@code carrier},
     *         or {@code null} if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        if (carrier == null) {
            return null;
        }
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (carrier.equals(mail.getCarrier())) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Retrieves all registered mail items addressed to a specific inhabitant.
     *
     * @param addressee the inhabitant whose mail is requested
     * @return a {@link List} of {@link RegisteredMail} objects addressed to {@code addressee},
     *         or {@code null} if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        if (addressee == null) {
            return null;
        }
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (addressee.equals(mail.getAddressee())) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Helper method to add a registered mail to the area.
     * This method is not part of the original specification but is useful for tests.
     *
     * @param mail the registered mail to add
     * @return {@code true} if the mail was added; {@code false} if already present or null
     */
    public boolean addRegisteredMail(RegisteredMail mail) {
        if (mail == null) {
            return false;
        }
        if (!allMails.contains(mail)) {
            allMails.add(mail);
            return true;
        }
        return false;
    }
}