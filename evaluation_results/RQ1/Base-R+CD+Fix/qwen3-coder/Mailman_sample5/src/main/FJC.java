import java.util.*;

/**
 * Represents a mailman responsible for delivering registered mail in a geographical area.
 */
class Mailman {
    // Empty class as per design model
}

/**
 * Represents an inhabitant associated with a geographical area.
 */
class Inhabitant {
    // Empty class as per design model
}

/**
 * Abstract class representing registered mail which can be either a letter or a parcel.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    /**
     * Gets the mailman carrier for this registered mail.
     *
     * @return the carrier mailman
     */
    public Mailman getCarrier() {
        return carrier;
    }

    /**
     * Sets the mailman carrier for this registered mail.
     *
     * @param carrier the mailman to set as carrier
     */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /**
     * Gets the inhabitant addressee for this registered mail.
     *
     * @return the addressee inhabitant
     */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /**
     * Sets the inhabitant addressee for this registered mail.
     *
     * @param addressee the inhabitant to set as addressee
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
     * @param carrier    the mailman to assign for delivery
     * @param addressee  the inhabitant who is the addressee of the mail
     * @param mail       the registered mail item to be delivered
     * @return true if the assignment is successful; otherwise, false
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        // Check if mailman and inhabitant belong to this geographical area
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }

        // Check if the mail is already assigned to a mailman
        if (mail.getCarrier() != null) {
            return false;
        }

        // Assign the mailman to deliver the mail
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        return true;
    }

    /**
     * Lists all registered mail items (letters and parcels) assigned to a specified mailman.
     * Includes only mail items that specify the given mailman as the carrier.
     *
     * @param carrier the mailman whose assigned mails are to be listed
     * @return a list of registered mail items assigned to the mailman, or null if none exist
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
     * @param addressee the inhabitant whose mails are to be listed
     * @return a list of registered mail items directed to the inhabitant, or null if none exist
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
     * A mailman cannot be added if they're already assigned to this geographical area.
     *
     * @param newMailman the mailman to be added
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
     * Requirements:
     * - Must keep at least one mailman in the area
     * - Must specify a different, existing mailman to take over deliveries
     * - Must successfully reassign all mail before removal
     *
     * @param mailmanToRemove the mailman to be removed
     * @param newMailman      the mailman who will take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        // Check if the mailman to remove exists in this area
        if (!mailmen.contains(mailmanToRemove)) {
            return false;
        }

        // Check if there will be at least one mailman left after removal
        if (mailmen.size() <= 1) {
            return false;
        }

        // Check if the new mailman exists in this area and is different from the one being removed
        if (!mailmen.contains(newMailman) || mailmanToRemove.equals(newMailman)) {
            return false;
        }

        // Reassign all mails from the mailman to be removed to the new mailman
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
     * @param newInhabitant the inhabitant to be added
     * @return true if the operation is successful, false otherwise
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        return inhabitants.add(newInhabitant);
    }

    /**
     * Removes an inhabitant from this geographical area.
     * Also deletes any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman.
     *
     * @param inhabitantToRemove the inhabitant to be removed
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (!inhabitants.remove(inhabitantToRemove)) {
            return false;
        }

        // Remove all mails addressed to this inhabitant
        Iterator<RegisteredMail> iterator = allMails.iterator();
        while (iterator.hasNext()) {
            RegisteredMail mail = iterator.next();
            if (inhabitantToRemove.equals(mail.getAddressee())) {
                iterator.remove();
            }
        }

        return true;
    }
}