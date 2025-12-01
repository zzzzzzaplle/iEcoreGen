import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mailman who delivers registered mail in a geographical area.
 */
class Mailman {
    // Empty class as per design model - can be extended with properties if needed
}

/**
 * Represents an inhabitant associated with a geographical area.
 */
class Inhabitant {
    // Empty class as per design model - can be extended with properties if needed
}

/**
 * Abstract class representing registered mail with a carrier and addressee.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    /**
     * Gets the mailman who carries this mail.
     *
     * @return the carrier mailman
     */
    public Mailman getCarrier() {
        return carrier;
    }

    /**
     * Sets the mailman who carries this mail.
     *
     * @param carrier the carrier mailman to set
     */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /**
     * Gets the inhabitant who is the addressee of this mail.
     *
     * @return the addressee inhabitant
     */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /**
     * Sets the inhabitant who is the addressee of this mail.
     *
     * @param addressee the addressee inhabitant to set
     */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Represents a letter, which is a type of registered mail.
 */
class Letter extends RegisteredMail {
    public Letter() {
        super();
    }
}

/**
 * Represents a parcel, which is a type of registered mail.
 */
class Parcel extends RegisteredMail {
    public Parcel() {
        super();
    }
}

/**
 * Represents a geographical area containing inhabitants, mailmen, and registered mails.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;

    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.allMails = new ArrayList<>();
    }

    /**
     * Gets the list of mailmen in this geographical area.
     *
     * @return the list of mailmen
     */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /**
     * Sets the list of mailmen in this geographical area.
     *
     * @param mailmen the list of mailmen to set
     */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /**
     * Gets the list of inhabitants in this geographical area.
     *
     * @return the list of inhabitants
     */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /**
     * Sets the list of inhabitants in this geographical area.
     *
     * @param inhabitants the list of inhabitants to set
     */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /**
     * Gets the list of all registered mails in this geographical area.
     *
     * @return the list of registered mails
     */
    public List<RegisteredMail> getAllMails() {
        return allMails;
    }

    /**
     * Sets the list of all registered mails in this geographical area.
     *
     * @param allMails the list of registered mails to set
     */
    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails;
    }

    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * The mailman and the inhabitant must belong to the addressee's geographical area.
     * Ensures the mail isn't already assigned to any mailman.
     *
     * @param carrier   the mailman to assign for delivery
     * @param addressee the inhabitant who is the addressee of the mail
     * @param mail      the registered mail item to be delivered
     * @return true if the assignment is successful; otherwise, false
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        // Check if mailman and inhabitant belong to this geographical area
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }

        // Check if mail is already assigned to a mailman
        if (mail.getCarrier() != null) {
            return false;
        }

        // Assign the carrier to the mail
        mail.setCarrier(carrier);
        return true;
    }

    /**
     * Lists all registered mail items (letters and parcels) assigned to a specified mailman.
     * Includes only mail items that specify the given mailman as the carrier.
     *
     * @param carrier the mailman for whom to list registered mails
     * @return a list of registered mail items assigned to the specified mailman, or null if none exist
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
     * Lists all registered mail items (letters and parcels) directed to a specified inhabitant.
     * Includes only mail items that specify the given inhabitant as the addressee.
     *
     * @param addressee the inhabitant for whom to list registered mails
     * @return a list of registered mail items directed to the specified inhabitant, or null if none exist
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

    /**
     * Adds a new mailman to this geographical area.
     *
     * @param newMailman the mailman to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addMailman(Mailman newMailman) {
        if (mailmen.contains(newMailman)) {
            return false;
        }
        return mailmen.add(newMailman);
    }

    /**
     * Removes a mailman from this geographical area.
     * Requires keeping at least one mailman in the area.
     * Requires specifying a different, existing mailman to take over deliveries.
     * Requires successfully reassigning all mail before removal.
     *
     * @param mailmanToRemove the mailman to remove
     * @param newMailman      the mailman who will take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        // Check if mailman to remove exists and new mailman exists and is different
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman) || mailmanToRemove.equals(newMailman)) {
            return false;
        }

        // Check if there will be at least one mailman left
        if (mailmen.size() <= 1) {
            return false;
        }

        // Reassign all mails from mailmanToRemove to newMailman
        for (RegisteredMail mail : allMails) {
            if (mailmanToRemove.equals(mail.getCarrier())) {
                mail.setCarrier(newMailman);
            }
        }

        // Remove the mailman
        return mailmen.remove(mailmanToRemove);
    }

    /**
     * Adds a new inhabitant to this geographical area.
     *
     * @param newInhabitant the inhabitant to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (inhabitants.contains(newInhabitant)) {
            return false;
        }
        return inhabitants.add(newInhabitant);
    }

    /**
     * Removes an inhabitant from this geographical area.
     * Deletes any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman.
     *
     * @param inhabitantToRemove the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (!inhabitants.contains(inhabitantToRemove)) {
            return false;
        }

        // Remove all mails associated with this inhabitant
        allMails.removeIf(mail -> inhabitantToRemove.equals(mail.getAddressee()));

        // Remove the inhabitant
        return inhabitants.remove(inhabitantToRemove);
    }
}