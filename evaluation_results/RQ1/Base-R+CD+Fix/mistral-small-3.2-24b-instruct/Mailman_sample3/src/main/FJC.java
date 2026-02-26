import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

/**
 * Represents a mailman who delivers registered mail in a specific geographical area.
 */
class Mailman {
    private String id;
    private String name;

    /**
     * Default constructor.
     */
    public Mailman() {
    }

    /**
     * Parameterized constructor.
     *
     * @param id the unique identifier for the mailman
     * @param name the name of the mailman
     */
    public Mailman(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mailman mailman = (Mailman) o;
        return Objects.equals(id, mailman.id) &&
               Objects.equals(name, mailman.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

/**
 * Represents a geographical area where inhabitants live and mailmen deliver mail.
 */
class GeographicalArea {
    private List<Mailman> mailmen = new ArrayList<>();
    private List<Inhabitant> inhabitants = new ArrayList<>();
    private List<RegisteredMail> allMails = new ArrayList<>();

    /**
     * Default constructor.
     */
    public GeographicalArea() {
    }

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     *
     * @param carrier the mailman to assign
     * @param addressee the addressee of the mail
     * @param mail the mail item to assign
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        if (mail.getCarrier() != null) {
            return false;
        }
        if (!allMails.contains(mail)) {
            allMails.add(mail);
        }
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
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
     * @param newMailman the mailman to take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmen.size() <= 1) {
            return false;
        }
        if (!mailmen.contains(newMailman)) {
            return false;
        }
        for (RegisteredMail mail : allMails) {
            if (mail.getCarrier().equals(mailmanToRemove)) {
                if (!assignRegisteredMailDeliver(newMailman, mail.getAddressee(), mail)) {
                    return false;
                }
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
     * @param inhabitantToRemove the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (!inhabitants.contains(inhabitantToRemove)) {
            return false;
        }
        for (RegisteredMail mail : allMails) {
            if (mail.getAddressee().equals(inhabitantToRemove) && mail.getCarrier() != null) {
                allMails.remove(mail);
            }
        }
        inhabitants.remove(inhabitantToRemove);
        return true;
    }

    /**
     * Lists all registered mail items assigned to a specified mailman.
     *
     * @param carrier the mailman whose assigned mail items are to be listed
     * @return a list of registered mail items, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getCarrier().equals(carrier)) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     *
     * @param addressee the inhabitant whose mail items are to be listed
     * @return a list of registered mail items, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getAddressee().equals(addressee)) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
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

/**
 * Represents an inhabitant in a geographical area.
 */
class Inhabitant {
    private String id;
    private String name;

    /**
     * Default constructor.
     */
    public Inhabitant() {
    }

    /**
     * Parameterized constructor.
     *
     * @param id the unique identifier for the inhabitant
     * @param name the name of the inhabitant
     */
    public Inhabitant(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inhabitant inhabitant = (Inhabitant) o;
        return Objects.equals(id, inhabitant.id) &&
               Objects.equals(name, inhabitant.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

/**
 * Abstract class representing a registered mail item.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    /**
     * Default constructor.
     */
    public RegisteredMail() {
    }

    /**
     * Parameterized constructor.
     *
     * @param carrier the mailman assigned to deliver the mail
     * @param addressee the addressee of the mail
     */
    public RegisteredMail(Mailman carrier, Inhabitant addressee) {
        this.carrier = carrier;
        this.addressee = addressee;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisteredMail that = (RegisteredMail) o;
        return Objects.equals(carrier, that.carrier) &&
               Objects.equals(addressee, that.addressee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carrier, addressee);
    }
}

/**
 * Represents a letter, a type of registered mail.
 */
class Letter extends RegisteredMail {
    /**
     * Default constructor.
     */
    public Letter() {
    }

    /**
     * Parameterized constructor.
     *
     * @param carrier the mailman assigned to deliver the letter
     * @param addressee the addressee of the letter
     */
    public Letter(Mailman carrier, Inhabitant addressee) {
        super(carrier, addressee);
    }
}

/**
 * Represents a parcel, a type of registered mail.
 */
class Parcel extends RegisteredMail {
    /**
     * Default constructor.
     */
    public Parcel() {
    }

    /**
     * Parameterized constructor.
     *
     * @param carrier the mailman assigned to deliver the parcel
     * @param addressee the addressee of the parcel
     */
    public Parcel(Mailman carrier, Inhabitant addressee) {
        super(carrier, addressee);
    }
}