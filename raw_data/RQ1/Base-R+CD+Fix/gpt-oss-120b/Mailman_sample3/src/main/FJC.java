import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mail carrier (mailman) that can deliver registered mail.
 */
class Mailman {
    private String name;

    /** No‑argument constructor */
    public Mailman() {
    }

    /** Constructor with name */
    public Mailman(String name) {
        this.name = name;
    }

    /** @return the mailman's name */
    public String getName() {
        return name;
    }

    /** @param name the mailman's name */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on name (optional, helps collection operations) */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mailman mailman = (Mailman) o;
        return Objects.equals(name, mailman.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Represents an inhabitant that can receive registered mail.
 */
class Inhabitant {
    private String name;

    /** No‑argument constructor */
    public Inhabitant() {
    }

    /** Constructor with name */
    public Inhabitant(String name) {
        this.name = name;
    }

    /** @return the inhabitant's name */
    public String getName() {
        return name;
    }

    /** @param name the inhabitant's name */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on name (optional, helps collection operations) */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inhabitant that = (Inhabitant) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Abstract base class for all registered mail items.
 */
abstract class RegisteredMail {
    private Mailman carrier;       // may be null if not yet assigned
    private Inhabitant addressee;  // must never be null

    /** No‑argument constructor */
    public RegisteredMail() {
    }

    /** Constructor with addressee */
    public RegisteredMail(Inhabitant addressee) {
        this.addressee = addressee;
    }

    /** @return the mail carrier assigned to this item (may be null) */
    public Mailman getCarrier() {
        return carrier;
    }

    /** @param carrier the mail carrier to assign */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /** @return the addressee of this mail item */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /** @param addressee the addressee of this mail item */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Concrete class representing a registered letter.
 */
class Letter extends RegisteredMail {
    private String content;

    /** No‑argument constructor */
    public Letter() {
    }

    /** Constructor with addressee and content */
    public Letter(Inhabitant addressee, String content) {
        super(addressee);
        this.content = content;
    }

    /** @return the textual content of the letter */
    public String getContent() {
        return content;
    }

    /** @param content the textual content of the letter */
    public void setContent(String content) {
        this.content = content;
    }
}

/**
 * Concrete class representing a registered parcel.
 */
class Parcel extends RegisteredMail {
    private double weight; // kilograms

    /** No‑argument constructor */
    public Parcel() {
    }

    /** Constructor with addressee and weight */
    public Parcel(Inhabitant addressee, double weight) {
        super(addressee);
        this.weight = weight;
    }

    /** @return the weight of the parcel */
    public double getWeight() {
        return weight;
    }

    /** @param weight the weight of the parcel */
    public void setWeight(double weight) {
        this.weight = weight;
    }
}

/**
 * Represents a geographical area that groups mailmen, inhabitants,
 * and the registered mail items that belong to the area.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;

    /** No‑argument constructor initializing internal collections */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.allMails = new ArrayList<>();
    }

    /** @return list of mailmen in this area (unmodifiable) */
    public List<Mailman> getMailmen() {
        return Collections.unmodifiableList(mailmen);
    }

    /** @param mailmen list to replace the current mailmen list */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen != null ? new ArrayList<>(mailmen) : new ArrayList<>();
    }

    /** @return list of inhabitants in this area (unmodifiable) */
    public List<Inhabitant> getInhabitants() {
        return Collections.unmodifiableList(inhabitants);
    }

    /** @param inhabitants list to replace the current inhabitants list */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants != null ? new ArrayList<>(inhabitants) : new ArrayList<>();
    }

    /** @return list of all registered mails in this area (unmodifiable) */
    public List<RegisteredMail> getAllMails() {
        return Collections.unmodifiableList(allMails);
    }

    /** @param allMails list to replace the current mail list */
    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails != null ? new ArrayList<>(allMails) : new ArrayList<>();
    }

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     *
     * @param carrier   the mailman who will deliver the mail
     * @param addressee the intended recipient of the mail
     * @param mail      the registered mail item to be assigned
     * @return true if the assignment succeeds; false otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        // Preconditions: both carrier and addressee must belong to this area
        if (carrier == null || addressee == null || mail == null) {
            return false;
        }
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        // The mail must not already have a carrier assigned
        if (mail.getCarrier() != null) {
            return false;
        }
        // Set the relationships
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        // Ensure the mail is tracked in the area
        if (!allMails.contains(mail)) {
            allMails.add(mail);
        }
        return true;
    }

    /**
     * Adds a new mailman to this geographical area if not already present.
     *
     * @param newMailman the mailman to be added
     * @return true if the mailman was added; false if already present or null
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null) {
            return false;
        }
        if (mailmen.contains(newMailman)) {
            return false;
        }
        mailmen.add(newMailman);
        return true;
    }

    /**
     * Removes a mailman from the area after reassigning all of his deliveries
     * to another existing mailman. At least one mailman must remain after removal.
     *
     * @param mailmanToRemove the mailman to be removed
     * @param newMailman      the mailman that will take over the deliveries
     * @return true if removal and reassignment succeed; false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            return false;
        }
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman)) {
            return false;
        }
        // Ensure removal does not leave the area without any mailman
        if (mailmen.size() <= 1) {
            return false;
        }
        // Reassign all mails carried by mailmanToRemove
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
     * Adds a new inhabitant to the geographical area.
     *
     * @param newInhabitant the inhabitant to add
     * @return true if the inhabitant was added; false if already present or null
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null) {
            return false;
        }
        if (inhabitants.contains(newInhabitant)) {
            return false;
        }
        inhabitants.add(newInhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from the area and deletes any registered mail
     * addressed to that inhabitant which already has a carrier assigned.
     *
     * @param inhabitant the inhabitant to remove
     * @return true if removal succeeds; false if inhabitant not present or null
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null) {
            return false;
        }
        if (!inhabitants.contains(inhabitant)) {
            return false;
        }
        // Remove mails addressed to this inhabitant that have already been assigned
        Iterator<RegisteredMail> iterator = allMails.iterator();
        while (iterator.hasNext()) {
            RegisteredMail mail = iterator.next();
            if (inhabitant.equals(mail.getAddressee()) && mail.getCarrier() != null) {
                iterator.remove();
            }
        }
        // Finally remove the inhabitant
        inhabitants.remove(inhabitant);
        return true;
    }

    /**
     * Retrieves all registered mail items that are assigned to the given mailman.
     *
     * @param carrier the mailman whose deliveries are requested
     * @return a list of mail items delivered by the mailman, or null if none exist
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
     * Retrieves all registered mail items that are addressed to the given inhabitant.
     *
     * @param addressee the inhabitant whose incoming mail is requested
     * @return a list of mail items for the inhabitant, or null if none exist
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
}