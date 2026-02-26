import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mailman who delivers registered mail
 */
class Mailman {
    private String id;
    private String name;
    
    /**
     * Default constructor
     */
    public Mailman() {
    }
    
    /**
     * Constructor with parameters
     * @param id the mailman's unique identifier
     * @param name the mailman's name
     */
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
 * Represents an inhabitant who receives registered mail
 */
class Inhabitant {
    private String id;
    private String name;
    
    /**
     * Default constructor
     */
    public Inhabitant() {
    }
    
    /**
     * Constructor with parameters
     * @param id the inhabitant's unique identifier
     * @param name the inhabitant's name
     */
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
 * Abstract class representing registered mail
 */
abstract class RegisteredMail {
    private String id;
    private Mailman carrier;
    private Inhabitant addressee;
    
    /**
     * Default constructor
     */
    public RegisteredMail() {
    }
    
    /**
     * Constructor with parameters
     * @param id the mail's unique identifier
     * @param addressee the recipient of the mail
     */
    public RegisteredMail(String id, Inhabitant addressee) {
        this.id = id;
        this.addressee = addressee;
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
 * Represents a letter as a type of registered mail
 */
class Letter extends RegisteredMail {
    /**
     * Default constructor
     */
    public Letter() {
    }
    
    /**
     * Constructor with parameters
     * @param id the letter's unique identifier
     * @param addressee the recipient of the letter
     */
    public Letter(String id, Inhabitant addressee) {
        super(id, addressee);
    }
}

/**
 * Represents a parcel as a type of registered mail
 */
class Parcel extends RegisteredMail {
    /**
     * Default constructor
     */
    public Parcel() {
    }
    
    /**
     * Constructor with parameters
     * @param id the parcel's unique identifier
     * @param addressee the recipient of the parcel
     */
    public Parcel(String id, Inhabitant addressee) {
        super(id, addressee);
    }
}

/**
 * Represents a geographical area with inhabitants, mailmen, and registered mail
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;
    
    /**
     * Default constructor
     */
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
     * Assigns a specific mailman to deliver a registered inhabitant's mail item
     * @param carrier the mailman to assign
     * @param addressee the inhabitant receiving the mail
     * @param mail the registered mail item to assign
     * @return true if assignment is successful, false otherwise
     * @throws IllegalArgumentException if carrier, addressee, or mail is null
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (carrier == null || addressee == null || mail == null) {
            throw new IllegalArgumentException("Carrier, addressee, and mail cannot be null");
        }
        
        // Check if mailman and inhabitant belong to the same geographical area
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        
        // Check if mail is already assigned to a mailman
        if (mail.getCarrier() != null) {
            return false;
        }
        
        // Check if mail exists in the area's mail list
        if (!allMails.contains(mail)) {
            return false;
        }
        
        // Assign the mail to the carrier
        mail.setCarrier(carrier);
        return true;
    }
    
    /**
     * Adds a new mailman to the geographical area if not already assigned
     * @param newMailman the mailman to add
     * @return true if addition is successful, false otherwise
     * @throws IllegalArgumentException if newMailman is null
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null) {
            throw new IllegalArgumentException("Mailman cannot be null");
        }
        
        if (!mailmen.contains(newMailman)) {
            mailmen.add(newMailman);
            return true;
        }
        return false;
    }
    
    /**
     * Removes a mailman from the geographical area with reassignment of deliveries
     * @param mailmanToRemove the mailman to remove
     * @param newMailman the mailman to take over deliveries
     * @return true if removal is successful, false otherwise
     * @throws IllegalArgumentException if mailmanToRemove or newMailman is null
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            throw new IllegalArgumentException("Mailman to remove and new mailman cannot be null");
        }
        
        // Check if there's at least one mailman remaining after removal
        if (mailmen.size() <= 1) {
            return false;
        }
        
        // Check if both mailmen exist in the area
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman)) {
            return false;
        }
        
        // Reassign all mail from mailmanToRemove to newMailman
        for (RegisteredMail mail : allMails) {
            if (mailmanToRemove.equals(mail.getCarrier())) {
                mail.setCarrier(newMailman);
            }
        }
        
        // Remove the mailman
        return mailmen.remove(mailmanToRemove);
    }
    
    /**
     * Adds a new inhabitant to the geographical area
     * @param newInhabitant the inhabitant to add
     * @return true if addition is successful, false otherwise
     * @throws IllegalArgumentException if newInhabitant is null
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null) {
            throw new IllegalArgumentException("Inhabitant cannot be null");
        }
        
        if (!inhabitants.contains(newInhabitant)) {
            inhabitants.add(newInhabitant);
            return true;
        }
        return false;
    }
    
    /**
     * Removes an inhabitant from the geographical area and deletes assigned mail
     * @param inhabitantToRemove the inhabitant to remove
     * @return true if removal is successful, false otherwise
     * @throws IllegalArgumentException if inhabitantToRemove is null
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitantToRemove == null) {
            throw new IllegalArgumentException("Inhabitant to remove cannot be null");
        }
        
        if (!inhabitants.contains(inhabitantToRemove)) {
            return false;
        }
        
        // Delete any registered mail whose addressee is that inhabitant and which has been assigned
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
     * Lists all registered mail items assigned to a specified mailman
     * @param carrier the mailman whose mail items to list
     * @return list of registered mail items, or null if none exist
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
     * Lists all registered mail items directed to a specified inhabitant
     * @param addressee the inhabitant whose mail items to list
     * @return list of registered mail items, or null if none exist
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
     * Adds a registered mail item to the geographical area
     * @param mail the mail item to add
     * @return true if addition is successful, false otherwise
     * @throws IllegalArgumentException if mail is null
     */
    public boolean addRegisteredMail(RegisteredMail mail) {
        if (mail == null) {
            throw new IllegalArgumentException("Mail cannot be null");
        }
        
        if (!allMails.contains(mail)) {
            allMails.add(mail);
            return true;
        }
        return false;
    }
    
    /**
     * Removes a registered mail item from the geographical area
     * @param mail the mail item to remove
     * @return true if removal is successful, false otherwise
     * @throws IllegalArgumentException if mail is null
     */
    public boolean removeRegisteredMail(RegisteredMail mail) {
        if (mail == null) {
            throw new IllegalArgumentException("Mail cannot be null");
        }
        
        return allMails.remove(mail);
    }
}