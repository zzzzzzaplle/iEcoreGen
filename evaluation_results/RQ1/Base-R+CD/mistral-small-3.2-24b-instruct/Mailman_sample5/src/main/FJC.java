import java.util.ArrayList;
import java.util.List;

class Mailman {
    private String id;
    private String name;

    public Mailman() {
    }

    public Mailman(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Inhabitant {
    private String id;
    private String name;

    public Inhabitant() {
    }

    public Inhabitant(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    public RegisteredMail() {
    }

    public RegisteredMail(Mailman carrier, Inhabitant addressee) {
        this.carrier = carrier;
        this.addressee = addressee;
    }

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
    }

    public Letter(Mailman carrier, Inhabitant addressee) {
        super(carrier, addressee);
    }
}

class Parcel extends RegisteredMail {
    public Parcel() {
    }

    public Parcel(Mailman carrier, Inhabitant addressee) {
        super(carrier, addressee);
    }
}

class GeographicalArea {
    private List<Mailman> mailmen = new ArrayList<>();
    private List<Inhabitant> inhabitants = new ArrayList<>();
    private List<RegisteredMail> allMails = new ArrayList<>();

    public GeographicalArea() {
    }

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     *
     * @param carrier    the mailman to assign as the carrier
     * @param addressee  the addressee of the mail item
     * @param mail       the mail item to assign
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
     * Adds a new mailman to the geographical area.
     *
     * @param newMailman the mailman to add
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
     *
     * @param mailmanToRemove the mailman to remove
     * @param newMailman      the mailman to take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman) || mailmen.size() <= 1) {
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
     * @param newInhabitant the inhabitant to add
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
     * Removes an inhabitant from the geographical area.
     *
     * @param addedInhabitant the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant addedInhabitant) {
        if (!inhabitants.contains(addedInhabitant)) {
            return false;
        }
        for (RegisteredMail mail : allMails) {
            if (mail.getAddressee().equals(addedInhabitant) && mail.getCarrier() != null) {
                return false;
            }
        }
        inhabitants.remove(addedInhabitant);
        return true;
    }

    /**
     * Lists all registered mail items assigned to a specified mailman.
     *
     * @param carrier the mailman whose mail items are to be listed
     * @return a list of registered mail items assigned to the specified mailman, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        List<RegisteredMail> mailItems = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getCarrier().equals(carrier)) {
                mailItems.add(mail);
            }
        }
        return mailItems.isEmpty() ? null : mailItems;
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     *
     * @param addressee the inhabitant whose mail items are to be listed
     * @return a list of registered mail items directed to the specified inhabitant, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        List<RegisteredMail> mailItems = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getAddressee().equals(addressee)) {
                mailItems.add(mail);
            }
        }
        return mailItems.isEmpty() ? null : mailItems;
    }

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