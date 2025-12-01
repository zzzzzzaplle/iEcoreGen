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
     * @param id the unique identifier for the mailman
     * @param name the name of the mailman
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
     * @param id the unique identifier for the inhabitant
     * @param name the name of the inhabitant
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
     * @param id the unique identifier for the registered mail
     * @param carrier the mailman assigned to deliver this mail
     * @param addressee the inhabitant who will receive this mail
     */
    public RegisteredMail(String id, Mailman carrier, Inhabitant addressee) {
        this.id = id;
        this.carrier = carrier;
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
 * Represents a letter type of registered mail
 */
class Letter extends RegisteredMail {
    private double weight;
    
    /**
     * Default constructor
     */
    public Letter() {
    }
    
    /**
     * Constructor with parameters
     * @param id the unique identifier for the letter
     * @param carrier the mailman assigned to deliver this letter
     * @param addressee the inhabitant who will receive this letter
     * @param weight the weight of the letter in grams
     */
    public Letter(String id, Mailman carrier, Inhabitant addressee, double weight) {
        super(id, carrier, addressee);
        this.weight = weight;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
}

/**
 * Represents a parcel type of registered mail
 */
class Parcel extends RegisteredMail {
    private double volume;
    
    /**
     * Default constructor
     */
    public Parcel() {
    }
    
    /**
     * Constructor with parameters
     * @param id the unique identifier for the parcel
     * @param carrier the mailman assigned to deliver this parcel
     * @param addressee the inhabitant who will receive this parcel
     * @param volume the volume of the parcel in cubic centimeters
     */
    public Parcel(String id, Mailman carrier, Inhabitant addressee, double volume) {
        super(id, carrier, addressee);
        this.volume = volume;
    }
    
    public double getVolume() {
        return volume;
    }
    
    public void setVolume(double volume) {
        this.volume = volume;
    }
}

/**
 * Represents a geographical area with mailmen, inhabitants, and registered mail
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
     * @param addressee the inhabitant who will receive the mail
     * @param mail the registered mail item to be assigned
     * @return true if the assignment is successful, false otherwise
     * @throws IllegalArgumentException if carrier, addressee, or mail is null
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (carrier == null || addressee == null || mail == null) {
            throw new IllegalArgumentException("Carrier, addressee, and mail cannot be null");
        }
        
        // Check if mailman and inhabitant belong to this geographical area
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        
        // Check if mail is already assigned to any mailman
        if (mail.getCarrier() != null) {
            return false;
        }
        
        // Assign the mail to the carrier and addressee
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        
        // Add mail to the area's mail list if not already present
        if (!allMails.contains(mail)) {
            allMails.add(mail);
        }
        
        return true;
    }
    
    /**
     * Adds a new mailman to the geographical area if they're not already assigned
     * @param newMailman the mailman to add
     * @return true if the operation is successful, false otherwise
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
     * Removes a mailman from the geographical area with proper reassignment
     * @param mailmanToRemove the mailman to remove
     * @param newMailman the mailman who will take over deliveries
     * @return true if the operation is successful, false otherwise
     * @throws IllegalArgumentException if mailmanToRemove or newMailman is null
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            throw new IllegalArgumentException("Mailman to remove and new mailman cannot be null");
        }
        
        // Check if we have at least one mailman remaining after removal
        if (mailmen.size() <= 1) {
            return false;
        }
        
        // Check if both mailmen exist in this area
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman)) {
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
     * Adds a new inhabitant to the geographical area
     * @param newInhabitant the inhabitant to add
     * @return true if the operation is successful, false otherwise
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
     * Removes an inhabitant from the geographical area and deletes their assigned mail
     * @param inhabitantToRemove the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     * @throws IllegalArgumentException if inhabitantToRemove is null
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitantToRemove == null) {
            throw new IllegalArgumentException("Inhabitant to remove cannot be null");
        }
        
        if (!inhabitants.contains(inhabitantToRemove)) {
            return false;
        }
        
        // Delete any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman
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
     * Lists all registered mail items directed to a specified inhabitant
     * @param addressee the inhabitant whose mail items to list
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
}