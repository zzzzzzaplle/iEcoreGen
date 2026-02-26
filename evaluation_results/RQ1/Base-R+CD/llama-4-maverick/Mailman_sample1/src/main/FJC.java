import java.util.ArrayList;
import java.util.List;

/**
 * Represents a geographical area with associated mailmen and inhabitants.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> registeredMails;

    /**
     * Constructs a new GeographicalArea object.
     */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.registeredMails = new ArrayList<>();
    }

    /**
     * Gets all mailmen in the geographical area.
     * @return List of mailmen
     */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /**
     * Sets the mailmen in the geographical area.
     * @param mailmen List of mailmen
     */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /**
     * Gets all inhabitants in the geographical area.
     * @return List of inhabitants
     */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /**
     * Sets the inhabitants in the geographical area.
     * @param inhabitants List of inhabitants
     */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /**
     * Gets all registered mail deliveries in the geographical area.
     * @return List of registered mail deliveries
     */
    public List<RegisteredMail> getRegisteredMails() {
        return registeredMails;
    }

    /**
     * Sets the registered mail deliveries in the geographical area.
     * @param registeredMails List of registered mail deliveries
     */
    public void setRegisteredMails(List<RegisteredMail> registeredMails) {
        this.registeredMails = registeredMails;
    }

    /**
     * Retrieves all registered mail deliveries for the geographical area, 
     * along with the responsible mailman and the addressees.
     * @return List of registered mail deliveries
     */
    public List<RegisteredMail> getAllDeliveries() {
        return registeredMails;
    }

    /**
     * Adds a new inhabitant to the geographical area.
     * @param newInhabitant Inhabitant to be added
     * @return true if the inhabitant is added successfully, false otherwise
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (!inhabitants.contains(newInhabitant)) {
            return inhabitants.add(newInhabitant);
        }
        return false;
    }

    /**
     * Removes an inhabitant from the geographical area and deletes any registered mail 
     * whose addressee is that inhabitant and which has already been assigned to a mailman.
     * @param inhabitantToRemove Inhabitant to be removed
     * @return true if the inhabitant is removed successfully, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitants.contains(inhabitantToRemove)) {
            inhabitants.remove(inhabitantToRemove);
            registeredMails.removeIf(mail -> mail.getAddressee().equals(inhabitantToRemove) && mail.getCarrier() != null);
            return true;
        }
        return false;
    }

    /**
     * Adds a mailman to the geographical area if they're not already assigned.
     * @param newMailman Mailman to be added
     * @return true if the mailman is added successfully, false otherwise
     */
    public boolean addMailman(Mailman newMailman) {
        if (!mailmen.contains(newMailman)) {
            return mailmen.add(newMailman);
        }
        return false;
    }

    /**
     * Removes a mailman from the geographical area after reassigning their deliveries to another mailman.
     * @param mailmanToRemove Mailman to be removed
     * @param newMailman Mailman to take over deliveries
     * @return true if the mailman is removed successfully, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmen.contains(mailmanToRemove) && mailmen.contains(newMailman) && !mailmanToRemove.equals(newMailman)) {
            for (RegisteredMail mail : registeredMails) {
                if (mail.getCarrier().equals(mailmanToRemove)) {
                    mail.setCarrier(newMailman);
                }
            }
            return mailmen.remove(mailmanToRemove);
        }
        return false;
    }

    /**
     * Assigns a mailman to deliver a registered mail item to an inhabitant.
     * @param carrier Mailman to be assigned
     * @param addressee Inhabitant to receive the mail
     * @param mail Registered mail item to be delivered
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (mailmen.contains(carrier) && inhabitants.contains(addressee) && mail.getAddressee().equals(addressee) && mail.getCarrier() == null) {
            mail.setCarrier(carrier);
            registeredMails.add(mail);
            return true;
        }
        return false;
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     * @param inhabitant Inhabitant to check for registered mail
     * @return List of registered mail items, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Inhabitant inhabitant) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : registeredMails) {
            if (mail.getAddressee().equals(inhabitant)) {
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
    // Add properties and methods as needed

    /**
     * Constructs a new Mailman object.
     */
    public Mailman() {
    }
}

/**
 * Represents an inhabitant.
 */
class Inhabitant {
    // Add properties and methods as needed

    /**
     * Constructs a new Inhabitant object.
     */
    public Inhabitant() {
    }
}

/**
 * Represents a registered mail item.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    /**
     * Constructs a new RegisteredMail object.
     */
    public RegisteredMail() {
    }

    /**
     * Gets the mailman assigned to deliver the mail.
     * @return Mailman assigned to deliver the mail
     */
    public Mailman getCarrier() {
        return carrier;
    }

    /**
     * Sets the mailman assigned to deliver the mail.
     * @param carrier Mailman to be assigned
     */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /**
     * Gets the inhabitant to whom the mail is addressed.
     * @return Inhabitant to whom the mail is addressed
     */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /**
     * Sets the inhabitant to whom the mail is addressed.
     * @param addressee Inhabitant to whom the mail is addressed
     */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Represents a letter.
 */
class Letter extends RegisteredMail {
    // Add properties and methods as needed

    /**
     * Constructs a new Letter object.
     */
    public Letter() {
    }
}

/**
 * Represents a parcel.
 */
class Parcel extends RegisteredMail {
    // Add properties and methods as needed

    /**
     * Constructs a new Parcel object.
     */
    public Parcel() {
    }
}