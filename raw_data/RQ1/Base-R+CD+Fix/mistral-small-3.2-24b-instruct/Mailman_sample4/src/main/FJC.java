import java.util.ArrayList;
import java.util.List;

class Mailman {
    private String id;
    private String name;

    public Mailman() {
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
}

class Parcel extends RegisteredMail {
    public Parcel() {
    }
}

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
     *
     * @param carrier The mailman to assign for delivery.
     * @param addressee The addressee of the mail item.
     * @param mail The mail item to be assigned.
     * @return true if the assignment is successful, false otherwise.
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee) || mail.getCarrier() != null) {
            return false;
        }
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        allMails.add(mail);
        return true;
    }

    /**
     * Adds a new mailman to the geographical area if not already present.
     *
     * @param newMailman The mailman to add.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean addMailman(Mailman newMailman) {
        if (mailmen.contains(newMailman)) {
            return false;
        }
        mailmen.add(newMailman);
        return true;
    }

    /**
     * Removes a mailman from the geographical area and reassigns their mail to another mailman.
     *
     * @param mailmanToRemove The mailman to remove.
     * @param newMailman The mailman to take over the deliveries.
     * @return true if the operation is successful, false otherwise.
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
     * @param newInhabitant The inhabitant to add.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (inhabitants.contains(newInhabitant)) {
            return false;
        }
        inhabitants.add(newInhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from the geographical area and deletes any registered mail assigned to them.
     *
     * @param addedInhabitant The inhabitant to remove.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean removeInhabitant(Inhabitant addedInhabitant) {
        if (!inhabitants.contains(addedInhabitant)) {
            return false;
        }
        for (RegisteredMail mail : allMails) {
            if (mail.getAddressee().equals(addedInhabitant) && mail.getCarrier() != null) {
                allMails.remove(mail);
            }
        }
        inhabitants.remove(addedInhabitant);
        return true;
    }

    /**
     * Lists all registered mail items assigned to a specified mailman.
     *
     * @param carrier The mailman whose mail items are to be listed.
     * @return A list of registered mail items assigned to the specified mailman, or null if none exist.
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        List<RegisteredMail> mailList = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getCarrier().equals(carrier)) {
                mailList.add(mail);
            }
        }
        return mailList.isEmpty() ? null : mailList;
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     *
     * @param addressee The inhabitant whose mail items are to be listed.
     * @return A list of registered mail items directed to the specified inhabitant, or null if none exist.
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        List<RegisteredMail> mailList = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getAddressee().equals(addressee)) {
                mailList.add(mail);
            }
        }
        return mailList.isEmpty() ? null : mailList;
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