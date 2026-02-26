import java.util.ArrayList;
import java.util.List;

/**
 * Represents a geographical area with mailmen and inhabitants.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> registeredMails;

    /**
     * Constructor for GeographicalArea.
     */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.registeredMails = new ArrayList<>();
    }

    /**
     * Gets all mailmen in the geographical area.
     * @return List of mailmen.
     */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /**
     * Sets the list of mailmen in the geographical area.
     * @param mailmen List of mailmen.
     */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /**
     * Gets all inhabitants in the geographical area.
     * @return List of inhabitants.
     */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /**
     * Sets the list of inhabitants in the geographical area.
     * @param inhabitants List of inhabitants.
     */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /**
     * Gets all registered mails in the geographical area.
     * @return List of registered mails.
     */
    public List<RegisteredMail> getRegisteredMails() {
        return registeredMails;
    }

    /**
     * Sets the list of registered mails in the geographical area.
     * @param registeredMails List of registered mails.
     */
    public void setRegisteredMails(List<RegisteredMail> registeredMails) {
        this.registeredMails = registeredMails;
    }

    /**
     * Retrieves all registered mail deliveries for the geographical area.
     * @return List of registered mail deliveries.
     */
    public List<RegisteredMail> getAllDeliveries() {
        return registeredMails;
    }

    /**
     * Adds a new inhabitant to the geographical area.
     * @param newInhabitant Inhabitant to add.
     * @return True if the inhabitant is added successfully, false otherwise.
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (!inhabitants.contains(newInhabitant)) {
            return inhabitants.add(newInhabitant);
        }
        return false;
    }

    /**
     * Removes an inhabitant from the geographical area and deletes any registered mail addressed to the inhabitant.
     * @param inhabitant Inhabitant to remove.
     * @return True if the inhabitant is removed successfully, false otherwise.
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitants.contains(inhabitant)) {
            inhabitants.remove(inhabitant);
            registeredMails.removeIf(mail -> mail.getAddressee().equals(inhabitant) && mail.getCarrier() != null);
            return true;
        }
        return false;
    }

    /**
     * Adds a mailman to the geographical area if not already present.
     * @param newMailman Mailman to add.
     * @return True if the mailman is added successfully, false otherwise.
     */
    public boolean addMailman(Mailman newMailman) {
        if (!mailmen.contains(newMailman)) {
            return mailmen.add(newMailman);
        }
        return false;
    }

    /**
     * Removes a mailman from the geographical area after reassigning their deliveries to another mailman.
     * @param mailmanToRemove Mailman to remove.
     * @param newMailman Mailman to take over deliveries.
     * @return True if the mailman is removed successfully, false otherwise.
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmen.contains(mailmanToRemove) && mailmen.contains(newMailman) && !mailmanToRemove.equals(newMailman) && mailmen.size() > 1) {
            for (RegisteredMail mail : registeredMails) {
                if (mail.getCarrier() != null && mail.getCarrier().equals(mailmanToRemove)) {
                    mail.setCarrier(newMailman);
                }
            }
            return mailmen.remove(mailmanToRemove);
        }
        return false;
    }

    /**
     * Assigns a mailman to deliver a registered mail item to an inhabitant.
     * @param carrier Mailman to assign.
     * @param addressee Inhabitant to receive the mail.
     * @param mail Registered mail item.
     * @return True if the assignment is successful, false otherwise.
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (mailmen.contains(carrier) && inhabitants.contains(addressee) && mail.getCarrier() == null) {
            mail.setCarrier(carrier);
            mail.setAddressee(addressee);
            registeredMails.add(mail);
            return true;
        }
        return false;
    }

    /**
     * Lists all registered mail items for a given mailman.
     * @param carrier Mailman to list deliveries for.
     * @return List of registered mail items.
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        List<RegisteredMail> mailItems = new ArrayList<>();
        for (RegisteredMail mail : registeredMails) {
            if (mail.getCarrier() != null && mail.getCarrier().equals(carrier)) {
                mailItems.add(mail);
            }
        }
        return mailItems;
    }
}

/**
 * Represents a mailman.
 */
class Mailman {
    /**
     * Constructor for Mailman.
     */
    public Mailman() {
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

/**
 * Represents an inhabitant.
 */
class Inhabitant {
    /**
     * Constructor for Inhabitant.
     */
    public Inhabitant() {
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

/**
 * Abstract class representing registered mail.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    /**
     * Constructor for RegisteredMail.
     */
    public RegisteredMail() {
    }

    /**
     * Gets the mailman assigned to deliver the mail.
     * @return Mailman.
     */
    public Mailman getCarrier() {
        return carrier;
    }

    /**
     * Sets the mailman assigned to deliver the mail.
     * @param carrier Mailman.
     */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /**
     * Gets the inhabitant addressed by the mail.
     * @return Inhabitant.
     */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /**
     * Sets the inhabitant addressed by the mail.
     * @param addressee Inhabitant.
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
     * Constructor for Letter.
     */
    public Letter() {
        super();
    }
}

/**
 * Represents a parcel, a type of registered mail.
 */
class Parcel extends RegisteredMail {
    /**
     * Constructor for Parcel.
     */
    public Parcel() {
        super();
    }
}