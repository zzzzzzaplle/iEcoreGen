import java.util.ArrayList;
import java.util.List;

/**
 * Represents a geographical area with associated mailmen, inhabitants, and registered mail.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;

    /**
     * Unparameterized constructor to initialize a GeographicalArea object.
     */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.allMails = new ArrayList<>();
    }

    /**
     * Gets the list of mailmen in the geographical area.
     * @return the list of mailmen
     */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /**
     * Sets the list of mailmen in the geographical area.
     * @param mailmen the list of mailmen to set
     */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /**
     * Gets the list of inhabitants in the geographical area.
     * @return the list of inhabitants
     */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /**
     * Sets the list of inhabitants in the geographical area.
     * @param inhabitants the list of inhabitants to set
     */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /**
     * Gets the list of all registered mail in the geographical area.
     * @return the list of all registered mail
     */
    public List<RegisteredMail> getAllMails() {
        return allMails;
    }

    /**
     * Sets the list of all registered mail in the geographical area.
     * @param allMails the list of all registered mail to set
     */
    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails;
    }

    /**
     * Assigns a mailman to deliver a registered mail item to an inhabitant.
     * @param carrier the mailman to assign
     * @param addressee the inhabitant to receive the mail
     * @param mail the registered mail item to be delivered
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (mailmen.contains(carrier) && inhabitants.contains(addressee) && mail.getCarrier() == null) {
            mail.setCarrier(carrier);
            mail.setAddressee(addressee);
            return true;
        }
        return false;
    }

    /**
     * Adds a new mailman to the geographical area if they are not already assigned.
     * @param newMailman the mailman to add
     * @return true if the mailman is added successfully, false otherwise
     */
    public boolean addMailman(Mailman newMailman) {
        if (!mailmen.contains(newMailman)) {
            return mailmen.add(newMailman);
        }
        return false;
    }

    /**
     * Removes a mailman from the geographical area after reassigning their deliveries.
     * @param mailmanToRemove the mailman to remove
     * @param newMailman the mailman to take over deliveries
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmen.contains(mailmanToRemove) && mailmen.contains(newMailman) && mailmen.size() > 1) {
            for (RegisteredMail mail : allMails) {
                if (mail.getCarrier() == mailmanToRemove) {
                    mail.setCarrier(newMailman);
                }
            }
            return mailmen.remove(mailmanToRemove);
        }
        return false;
    }

    /**
     * Adds a new inhabitant to the geographical area.
     * @param newInhabitant the inhabitant to add
     * @return true if the inhabitant is added successfully, false otherwise
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (!inhabitants.contains(newInhabitant)) {
            return inhabitants.add(newInhabitant);
        }
        return false;
    }

    /**
     * Removes an inhabitant from the geographical area and deletes their registered mail.
     * @param inhabitantToRemove the inhabitant to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitants.contains(inhabitantToRemove)) {
            inhabitants.remove(inhabitantToRemove);
            allMails.removeIf(mail -> mail.getAddressee() == inhabitantToRemove);
            return true;
        }
        return false;
    }

    /**
     * Lists all registered mail items assigned to a specified mailman.
     * @param carrier the mailman to check
     * @return a list of registered mail items, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getCarrier() == carrier) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     * @param addressee the inhabitant to check
     * @return a list of registered mail items, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getAddressee() == addressee) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }
}

/**
 * Represents a mailman.
 */
class Mailman {
    /**
     * Unparameterized constructor to initialize a Mailman object.
     */
    public Mailman() {}
}

/**
 * Represents an inhabitant.
 */
class Inhabitant {
    /**
     * Unparameterized constructor to initialize an Inhabitant object.
     */
    public Inhabitant() {}
}

/**
 * Abstract base class for registered mail items.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    /**
     * Unparameterized constructor to initialize a RegisteredMail object.
     */
    public RegisteredMail() {}

    /**
     * Gets the mailman assigned to deliver the mail.
     * @return the mailman
     */
    public Mailman getCarrier() {
        return carrier;
    }

    /**
     * Sets the mailman assigned to deliver the mail.
     * @param carrier the mailman to set
     */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /**
     * Gets the inhabitant to whom the mail is addressed.
     * @return the inhabitant
     */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /**
     * Sets the inhabitant to whom the mail is addressed.
     * @param addressee the inhabitant to set
     */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Represents a letter, a type of registered mail.
 */
class Letter extends RegisteredMail {
    /**
     * Unparameterized constructor to initialize a Letter object.
     */
    public Letter() {}
}

/**
 * Represents a parcel, a type of registered mail.
 */
class Parcel extends RegisteredMail {
    /**
     * Unparameterized constructor to initialize a Parcel object.
     */
    public Parcel() {}
}