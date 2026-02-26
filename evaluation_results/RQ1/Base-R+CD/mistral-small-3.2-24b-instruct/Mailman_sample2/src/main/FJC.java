import java.util.ArrayList;
import java.util.List;

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
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * The mailman and the inhabitant must belong to the addressee's geographical area.
     * Ensures the mail isn't already assigned to any mailman.
     *
     * @param carrier The mailman to assign to deliver the mail
     * @param addressee The addressee of the mail
     * @param mail The mail item to be assigned
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee) || mail.getCarrier() != null) {
            return false;
        }
        mail.setCarrier(carrier);
        allMails.add(mail);
        return true;
    }

    /**
     * Adds a new mailman to the geographical area if not already present.
     *
     * @param newMailman The mailman to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addMailman(Mailman newMailman) {
        if (mailmen.contains(newMailman)) {
            return false;
        }
        mailmen.add(newMailman);
        return true;
    }

    /**
     * Removes a mailman from the geographical area.
     * Keeps at least one mailman in the area and reassigns all mail to another mailman.
     *
     * @param mailmanToRemove The mailman to remove
     * @param newMailman The mailman to take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmen.size() <= 1 || !mailmen.contains(newMailman)) {
            return false;
        }
        for (RegisteredMail mail : allMails) {
            if (mail.getCarrier().equals(mailmanToRemove)) {
                mail.setCarrier(newMailman);
            }
        }
        mailmen.remove(mailmanToRemove);
        return true;
    }

    /**
     * Adds a new inhabitant to the geographical area.
     *
     * @param newInhabitant The inhabitant to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (inhabitants.contains(newInhabitant)) {
            return false;
        }
        inhabitants.add(newInhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from the geographical area and deletes any registered mail
     * whose addressee is that inhabitant and which has already been assigned to a mailman.
     *
     * @param addedInhabitant The inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant addedInhabitant) {
        if (!inhabitants.contains(addedInhabitant)) {
            return false;
        }
        allMails.removeIf(mail -> mail.getAddressee().equals(addedInhabitant) && mail.getCarrier() != null);
        inhabitants.remove(addedInhabitant);
        return true;
    }

    /**
     * Lists all registered mail items assigned to a specified mailman.
     *
     * @param carrier The mailman whose mail items are to be listed
     * @return List of registered mail items assigned to the specified mailman, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        List<RegisteredMail> carrierMails = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getCarrier().equals(carrier)) {
                carrierMails.add(mail);
            }
        }
        return carrierMails.isEmpty() ? null : carrierMails;
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     *
     * @param addressee The inhabitant whose mail items are to be listed
     * @return List of registered mail items directed to the specified inhabitant, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        List<RegisteredMail> addresseeMails = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getAddressee().equals(addressee)) {
                addresseeMails.add(mail);
            }
        }
        return addresseeMails.isEmpty() ? null : addresseeMails;
    }

    // Getters and Setters
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

class Inhabitant {
    // Inhabitant properties and methods can be added here

    public Inhabitant() {
        // Default constructor
    }

    // Getters and Setters
    // Add any specific properties and their getters/setters here
}

abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    public RegisteredMail() {
        // Default constructor
    }

    // Getters and Setters
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

class Letter extends RegisteredMail {
    public Letter() {
        // Default constructor
    }

    // Letter-specific properties and methods can be added here
}

class Parcel extends RegisteredMail {
    public Parcel() {
        // Default constructor
    }

    // Parcel-specific properties and methods can be added here
}

class Mailman {
    // Mailman properties and methods can be added here

    public Mailman() {
        // Default constructor
    }

    // Getters and Setters
    // Add any specific properties and their getters/setters here
}