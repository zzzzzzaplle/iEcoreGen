import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mailman who delivers registered mail in a geographical area.
 */
class Mailman {
    private String id;
    private String name;

    /**
     * Default constructor for Mailman.
     */
    public Mailman() {
    }

    /**
     * Gets the mailman's ID.
     * @return the mailman's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the mailman's ID.
     * @param id the mailman's ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the mailman's name.
     * @return the mailman's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the mailman's name.
     * @param name the mailman's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mailman mailman = (Mailman) o;
        return Objects.equals(id, mailman.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents an inhabitant living in a geographical area.
 */
class Inhabitant {
    private String id;
    private String name;

    /**
     * Default constructor for Inhabitant.
     */
    public Inhabitant() {
    }

    /**
     * Gets the inhabitant's ID.
     * @return the inhabitant's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the inhabitant's ID.
     * @param id the inhabitant's ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the inhabitant's name.
     * @return the inhabitant's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the inhabitant's name.
     * @param name the inhabitant's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inhabitant that = (Inhabitant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Abstract class representing registered mail with a carrier and addressee.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;
    private String id;

    /**
     * Default constructor for RegisteredMail.
     */
    public RegisteredMail() {
    }

    /**
     * Gets the mail carrier.
     * @return the mail carrier
     */
    public Mailman getCarrier() {
        return carrier;
    }

    /**
     * Sets the mail carrier.
     * @param carrier the mail carrier to set
     */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /**
     * Gets the mail addressee.
     * @return the mail addressee
     */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /**
     * Sets the mail addressee.
     * @param addressee the mail addressee to set
     */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }

    /**
     * Gets the mail ID.
     * @return the mail ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the mail ID.
     * @param id the mail ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisteredMail that = (RegisteredMail) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents a letter as a type of registered mail.
 */
class Letter extends RegisteredMail {
    /**
     * Default constructor for Letter.
     */
    public Letter() {
    }
}

/**
 * Represents a parcel as a type of registered mail.
 */
class Parcel extends RegisteredMail {
    /**
     * Default constructor for Parcel.
     */
    public Parcel() {
    }
}

/**
 * Represents a geographical area containing mailmen, inhabitants, and registered mail.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;

    /**
     * Default constructor for GeographicalArea.
     */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.allMails = new ArrayList<>();
    }

    /**
     * Gets the list of mailmen in the area.
     * @return the list of mailmen
     */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /**
     * Sets the list of mailmen in the area.
     * @param mailmen the list of mailmen to set
     */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /**
     * Gets the list of inhabitants in the area.
     * @return the list of inhabitants
     */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /**
     * Sets the list of inhabitants in the area.
     * @param inhabitants the list of inhabitants to set
     */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /**
     * Gets the list of all registered mail in the area.
     * @return the list of all registered mail
     */
    public List<RegisteredMail> getAllMails() {
        return allMails;
    }

    /**
     * Sets the list of all registered mail in the area.
     * @param allMails the list of all registered mail to set
     */
    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails;
    }

    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * The mailman and the inhabitant must belong to the addressee's geographical area,
     * and the mail must not already be assigned to any mailman.
     * @param carrier the mailman to assign
     * @param addressee the inhabitant addressee
     * @param mail the registered mail to assign
     * @return true if the assignment is successful, false otherwise
     * @throws IllegalArgumentException if carrier, addressee, or mail is null
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (carrier == null || addressee == null || mail == null) {
            throw new IllegalArgumentException("Carrier, addressee, and mail cannot be null");
        }
        
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        
        if (mail.getCarrier() != null) {
            return false;
        }
        
        if (!allMails.contains(mail)) {
            return false;
        }
        
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        return true;
    }

    /**
     * Adds a new mailman to the geographical area if they're not already assigned.
     * @param newMailman the new mailman to add
     * @return true if the mailman is added successfully, false otherwise
     * @throws IllegalArgumentException if newMailman is null
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null) {
            throw new IllegalArgumentException("New mailman cannot be null");
        }
        
        if (mailmen.contains(newMailman)) {
            return false;
        }
        
        mailmen.add(newMailman);
        return true;
    }

    /**
     * Removes a mailman from the geographical area.
     * Requires: keeping at least one mailman in the area, specifying a different existing mailman to take over deliveries,
     * and successfully reassigning all mail before removal.
     * @param mailmanToRemove the mailman to remove
     * @param newMailman the mailman to take over deliveries
     * @return true if the removal is successful, false otherwise
     * @throws IllegalArgumentException if mailmanToRemove or newMailman is null
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            throw new IllegalArgumentException("Mailman to remove and new mailman cannot be null");
        }
        
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman)) {
            return false;
        }
        
        if (mailmanToRemove.equals(newMailman)) {
            return false;
        }
        
        if (mailmen.size() <= 1) {
            return false;
        }
        
        List<RegisteredMail> assignedMails = listRegisteredMail(mailmanToRemove);
        if (assignedMails != null) {
            for (RegisteredMail mail : assignedMails) {
                mail.setCarrier(newMailman);
            }
        }
        
        mailmen.remove(mailmanToRemove);
        return true;
    }

    /**
     * Adds a new inhabitant to the geographical area.
     * @param newInhabitant the new inhabitant to add
     * @return true if the inhabitant is added successfully, false otherwise
     * @throws IllegalArgumentException if newInhabitant is null
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null) {
            throw new IllegalArgumentException("New inhabitant cannot be null");
        }
        
        if (inhabitants.contains(newInhabitant)) {
            return false;
        }
        
        inhabitants.add(newInhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from the geographical area.
     * Deletes any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman.
     * @param inhabitantToRemove the inhabitant to remove
     * @return true if the removal is successful, false otherwise
     * @throws IllegalArgumentException if inhabitantToRemove is null
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitantToRemove == null) {
            throw new IllegalArgumentException("Inhabitant to remove cannot be null");
        }
        
        if (!inhabitants.contains(inhabitantToRemove)) {
            return false;
        }
        
        List<RegisteredMail> mailsToRemove = listRegisteredMailWithInhabitant(inhabitantToRemove);
        if (mailsToRemove != null) {
            for (RegisteredMail mail : mailsToRemove) {
                if (mail.getCarrier() != null) {
                    allMails.remove(mail);
                }
            }
        }
        
        inhabitants.remove(inhabitantToRemove);
        return true;
    }

    /**
     * Lists all registered mail items (letters and parcels) assigned to a specified mailman.
     * Includes only mail items that specify the given mailman as the carrier.
     * @param carrier the mailman to filter by
     * @return list of registered mail assigned to the mailman, or null if none exist
     * @throws IllegalArgumentException if carrier is null
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        if (carrier == null) {
            throw new IllegalArgumentException("Carrier cannot be null");
        }
        
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
     * @param addressee the inhabitant to filter by
     * @return list of registered mail directed to the inhabitant, or null if none exist
     * @throws IllegalArgumentException if addressee is null
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        if (addressee == null) {
            throw new IllegalArgumentException("Addressee cannot be null");
        }
        
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (addressee.equals(mail.getAddressee())) {
                result.add(mail);
            }
        }
        
        return result.isEmpty() ? null : result;
    }
}