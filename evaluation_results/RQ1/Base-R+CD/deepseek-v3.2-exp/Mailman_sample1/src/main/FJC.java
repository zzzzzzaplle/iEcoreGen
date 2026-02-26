import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mailman who delivers registered mail in a geographical area.
 */
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
 * Abstract base class representing registered mail (letters and parcels).
 */
abstract class RegisteredMail {
    private String id;
    private Mailman carrier;
    private Inhabitant addressee;
    
    public RegisteredMail() {
    }
    
    public RegisteredMail(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
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
    public Letter() {
    }
    
    public Letter(String id) {
        super(id);
    }
}

/**
 * Represents a parcel as a type of registered mail.
 */
class Parcel extends RegisteredMail {
    public Parcel() {
    }
    
    public Parcel(String id) {
        super(id);
    }
}

/**
 * Represents a geographical area containing mailmen, inhabitants, and registered mail.
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
    
    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * The mailman and the inhabitant must belong to the addressee's geographical area,
     * and the mail must not already be assigned to any mailman.
     *
     * @param carrier the mailman assigned to deliver the mail
     * @param addressee the inhabitant receiving the mail
     * @param mail the registered mail item to be delivered
     * @return true if the assignment is successful, false otherwise
     * @throws IllegalArgumentException if carrier, addressee, or mail is null
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (carrier == null || addressee == null || mail == null) {
            throw new IllegalArgumentException("Carrier, addressee, and mail cannot be null");
        }
        
        // Check if mailman and inhabitant belong to this area
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        
        // Check if mail is already assigned to a mailman
        if (mail.getCarrier() != null) {
            return false;
        }
        
        // Check if mail belongs to this area
        if (!allMails.contains(mail)) {
            return false;
        }
        
        // Assign the mail to the mailman and set the addressee
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        return true;
    }
    
    /**
     * Adds a new mailman to the geographical area if they're not already assigned.
     *
     * @param newMailman the mailman to be added
     * @return true if the mailman is successfully added, false otherwise
     * @throws IllegalArgumentException if newMailman is null
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null) {
            throw new IllegalArgumentException("Mailman cannot be null");
        }
        
        if (mailmen.contains(newMailman)) {
            return false;
        }
        
        mailmen.add(newMailman);
        return true;
    }
    
    /**
     * Removes a mailman from the geographical area, ensuring at least one mailman remains
     * and reassigning all mail to a different existing mailman.
     *
     * @param mailmanToRemove the mailman to be removed
     * @param newMailman the mailman who will take over the deliveries
     * @return true if the removal is successful, false otherwise
     * @throws IllegalArgumentException if mailmanToRemove or newMailman is null
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            throw new IllegalArgumentException("Mailman to remove and new mailman cannot be null");
        }
        
        // Check if both mailmen exist in this area
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman)) {
            return false;
        }
        
        // Ensure at least one mailman remains after removal
        if (mailmen.size() <= 1) {
            return false;
        }
        
        // Reassign all mail from the mailman to be removed to the new mailman
        for (RegisteredMail mail : allMails) {
            if (mailmanToRemove.equals(mail.getCarrier())) {
                mail.setCarrier(newMailman);
            }
        }
        
        // Remove the mailman
        return mailmen.remove(mailmanToRemove);
    }
    
    /**
     * Adds a new inhabitant to the geographical area.
     *
     * @param newInhabitant the inhabitant to be added
     * @return true if the inhabitant is successfully added, false otherwise
     * @throws IllegalArgumentException if newInhabitant is null
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null) {
            throw new IllegalArgumentException("Inhabitant cannot be null");
        }
        
        if (inhabitants.contains(newInhabitant)) {
            return false;
        }
        
        inhabitants.add(newInhabitant);
        return true;
    }
    
    /**
     * Removes an inhabitant from the geographical area and deletes any registered mail
     * assigned to that inhabitant which has already been assigned to a mailman.
     *
     * @param inhabitantToRemove the inhabitant to be removed
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
        
        // Delete any registered mail assigned to this inhabitant with a mailman
        List<RegisteredMail> mailToRemove = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (inhabitantToRemove.equals(mail.getAddressee()) && mail.getCarrier() != null) {
                mailToRemove.add(mail);
            }
        }
        allMails.removeAll(mailToRemove);
        
        // Remove the inhabitant
        return inhabitants.remove(inhabitantToRemove);
    }
    
    /**
     * Lists all registered mail items (letters and parcels) assigned to a specified mailman.
     * Includes only mail items that specify the given mailman as the carrier.
     *
     * @param carrier the mailman whose assigned mail items should be listed
     * @return list of registered mail items assigned to the mailman, or null if none exist
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
     *
     * @param addressee the inhabitant whose mail items should be listed
     * @return list of registered mail items directed to the inhabitant, or null if none exist
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
    
    /**
     * Adds a registered mail item to the geographical area.
     *
     * @param mail the registered mail item to be added
     * @return true if the mail is successfully added, false otherwise
     * @throws IllegalArgumentException if mail is null
     */
    public boolean addRegisteredMail(RegisteredMail mail) {
        if (mail == null) {
            throw new IllegalArgumentException("Mail cannot be null");
        }
        
        if (allMails.contains(mail)) {
            return false;
        }
        
        allMails.add(mail);
        return true;
    }
    
    /**
     * Removes a registered mail item from the geographical area.
     *
     * @param mail the registered mail item to be removed
     * @return true if the mail is successfully removed, false otherwise
     * @throws IllegalArgumentException if mail is null
     */
    public boolean removeRegisteredMail(RegisteredMail mail) {
        if (mail == null) {
            throw new IllegalArgumentException("Mail cannot be null");
        }
        
        return allMails.remove(mail);
    }
}