import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mailman who delivers registered mail in a geographical area.
 */
class Mailman {
    // Empty unparameterized constructor
    public Mailman() {}
}

/**
 * Represents a geographical area containing inhabitants, mailmen, and registered mails.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;

    /**
     * Constructs a new GeographicalArea with empty lists.
     */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.allMails = new ArrayList<>();
    }

    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * The mailman and the inhabitant must belong to the addressee's geographical area.
     * The mail must not already be assigned to any mailman.
     *
     * @param carrier   the mailman to assign for delivery
     * @param addressee the inhabitant who is the recipient of the mail
     * @param mail      the registered mail item to be assigned
     * @return true if the assignment is successful; otherwise, false
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        // Check if the mailman and inhabitant belong to this geographical area
        if (!this.mailmen.contains(carrier) || !this.inhabitants.contains(addressee)) {
            return false;
        }

        // Check if the mail is already assigned to a mailman
        if (mail.getCarrier() != null) {
            return false;
        }

        // Check if the mail belongs to this geographical area (through addressee)
        if (!mail.getAddressee().equals(addressee) || !this.allMails.contains(mail)) {
            return false;
        }

        // Assign the carrier to the mail
        mail.setCarrier(carrier);
        return true;
    }

    /**
     * Adds a new mailman to this geographical area if they are not already assigned.
     *
     * @param newMailman the mailman to add
     * @return true if the mailman was added successfully; otherwise, false
     */
    public boolean addMailman(Mailman newMailman) {
        if (this.mailmen.contains(newMailman)) {
            return false;
        }
        return this.mailmen.add(newMailman);
    }

    /**
     * Removes a mailman from this geographical area, ensuring at least one mailman remains.
     * All mails assigned to the removed mailman are reassigned to a new specified mailman.
     *
     * @param mailmanToRemove the mailman to remove
     * @param newMailman      the mailman to take over deliveries
     * @return true if the operation is successful; otherwise, false
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        // Ensure we keep at least one mailman
        if (this.mailmen.size() <= 1) {
            return false;
        }

        // Check if both mailmen exist in this area
        if (!this.mailmen.contains(mailmanToRemove) || !this.mailmen.contains(newMailman)) {
            return false;
        }

        // Reassign all mails from mailmanToRemove to newMailman
        for (RegisteredMail mail : this.allMails) {
            if (mail.getCarrier() != null && mail.getCarrier().equals(mailmanToRemove)) {
                mail.setCarrier(newMailman);
            }
        }

        // Remove the mailman
        return this.mailmen.remove(mailmanToRemove);
    }

    /**
     * Adds a new inhabitant to this geographical area.
     *
     * @param newInhabitant the inhabitant to add
     * @return true if the inhabitant was added successfully; otherwise, false
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        return this.inhabitants.add(newInhabitant);
    }

    /**
     * Removes an inhabitant from this geographical area and deletes any registered mail
     * whose addressee is that inhabitant and which has already been assigned to a mailman.
     *
     * @param inhabitantToRemove the inhabitant to remove
     * @return true if the operation is successful; otherwise, false
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (!this.inhabitants.contains(inhabitantToRemove)) {
            return false;
        }

        // Remove all mails directed to this inhabitant that have been assigned to a mailman
        this.allMails.removeIf(mail -> 
            mail.getAddressee().equals(inhabitantToRemove) && mail.getCarrier() != null);

        return this.inhabitants.remove(inhabitantToRemove);
    }

    /**
     * Lists all registered mail items (letters and parcels) assigned to a specified mailman.
     * Only includes mail items that specify the given mailman as the carrier.
     *
     * @param carrier the mailman for whom to list assigned mails
     * @return a list of registered mail items assigned to the carrier, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : this.allMails) {
            if (mail.getCarrier() != null && mail.getCarrier().equals(carrier)) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Lists all registered mail items (letters and parcels) directed to a specified inhabitant.
     * Only includes mail items that specify the given inhabitant as the addressee.
     *
     * @param addressee the inhabitant for whom to list mails
     * @return a list of registered mail items directed to the addressee, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : this.allMails) {
            if (mail.getAddressee().equals(addressee)) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }

    // Getters and setters for private fields to allow testing
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    public List<RegisteredMail> getAllMails() {
        return allMails;
    }

    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails;
    }
}

/**
 * Represents an inhabitant associated with a geographical area.
 */
class Inhabitant {
    // Empty unparameterized constructor
    public Inhabitant() {}
}

/**
 * Abstract class representing registered mail with a carrier and addressee.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    // Unparameterized constructor
    public RegisteredMail() {}

    // Getters and setters
    public Mailman getCarrier() {
        return carrier;
    }

    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    public Inhabitant getAddressee() {
        return addressee;
    }

    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Represents a letter, which is a type of registered mail.
 */
class Letter extends RegisteredMail {
    // Unparameterized constructor
    public Letter() {
        super();
    }
}

/**
 * Represents a parcel, which is a type of registered mail.
 */
class Parcel extends RegisteredMail {
    // Unparameterized constructor
    public Parcel() {
        super();
    }
}