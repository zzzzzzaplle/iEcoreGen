import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mailman who delivers registered mail
 */
class Mailman {
    private String name;
    private String id;
    
    public Mailman() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mailman mailman = (Mailman) obj;
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
    private String name;
    private String id;
    private String address;
    
    public Inhabitant() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Inhabitant inhabitant = (Inhabitant) obj;
        return Objects.equals(id, inhabitant.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Abstract base class for registered mail items
 */
abstract class RegisteredMail {
    private String trackingNumber;
    private Mailman carrier;
    private Inhabitant addressee;
    
    public RegisteredMail() {
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RegisteredMail mail = (RegisteredMail) obj;
        return Objects.equals(trackingNumber, mail.trackingNumber);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber);
    }
}

/**
 * Represents a registered letter
 */
class Letter extends RegisteredMail {
    private double weight;
    
    public Letter() {
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
}

/**
 * Represents a registered parcel
 */
class Parcel extends RegisteredMail {
    private double weight;
    private double dimensions;
    
    public Parcel() {
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public double getDimensions() {
        return dimensions;
    }
    
    public void setDimensions(double dimensions) {
        this.dimensions = dimensions;
    }
}

/**
 * Represents a geographical area with mailmen, inhabitants, and registered mail
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
     * Assigns a specific mailman to deliver a registered inhabitant's mail item
     * The mailman and the inhabitant must belong to the addressee's geographical area
     * Ensures the mail isn't already assigned to any mailman
     * 
     * @param carrier the mailman to assign the delivery to
     * @param addressee the inhabitant receiving the mail
     * @param mail the registered mail item to be assigned
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        // Check if mailman and inhabitant belong to this geographical area
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        
        // Check if mail is already in the system and not assigned
        if (!allMails.contains(mail)) {
            allMails.add(mail);
        }
        
        // Check if mail is already assigned to a mailman
        if (mail.getCarrier() != null) {
            return false;
        }
        
        // Assign the mail to the carrier and addressee
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        return true;
    }
    
    /**
     * Lists all registered mail items (letters and parcels) assigned to a specified mailman
     * Includes only mail items that specify the given mailman as the carrier
     * 
     * @param carrier the mailman to filter mail items by
     * @return list of registered mail items assigned to the mailman, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        List<RegisteredMail> result = new ArrayList<>();
        
        for (RegisteredMail mail : allMails) {
            if (mail.getCarrier() != null && mail.getCarrier().equals(carrier)) {
                result.add(mail);
            }
        }
        
        return result.isEmpty() ? null : result;
    }
    
    /**
     * Lists all registered mail items (letters and parcels) directed to a specified inhabitant
     * Includes only mail items that specify the given inhabitant as the addressee
     * 
     * @param addressee the inhabitant to filter mail items by
     * @return list of registered mail items directed to the inhabitant, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        List<RegisteredMail> result = new ArrayList<>();
        
        for (RegisteredMail mail : allMails) {
            if (mail.getAddressee() != null && mail.getAddressee().equals(addressee)) {
                result.add(mail);
            }
        }
        
        return result.isEmpty() ? null : result;
    }
    
    /**
     * Adds a new inhabitant to the geographical area
     * 
     * @param newInhabitant the inhabitant to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null || inhabitants.contains(newInhabitant)) {
            return false;
        }
        inhabitants.add(newInhabitant);
        return true;
    }
    
    /**
     * Removes an inhabitant from the geographical area
     * Deletes any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman
     * 
     * @param inhabitantToRemove the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitantToRemove == null || !inhabitants.contains(inhabitantToRemove)) {
            return false;
        }
        
        // Remove all mail items addressed to this inhabitant
        List<RegisteredMail> mailToRemove = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getAddressee() != null && mail.getAddressee().equals(inhabitantToRemove)) {
                mailToRemove.add(mail);
            }
        }
        allMails.removeAll(mailToRemove);
        
        // Remove the inhabitant
        inhabitants.remove(inhabitantToRemove);
        return true;
    }
    
    /**
     * Adds a mailman if they're not already assigned to the geographical area
     * 
     * @param newMailman the mailman to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null || mailmen.contains(newMailman)) {
            return false;
        }
        mailmen.add(newMailman);
        return true;
    }
    
    /**
     * Removes a mailman from the geographical area
     * Requires keeping at least one mailman in the area and specifying a different, existing mailman to take over deliveries
     * Reassigns all mail before removal
     * 
     * @param mailmanToRemove the mailman to remove
     * @param newMailman the mailman who will take over the deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null || 
            !mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman) ||
            mailmanToRemove.equals(newMailman)) {
            return false;
        }
        
        // Check if there will be at least one mailman left after removal
        if (mailmen.size() <= 1) {
            return false;
        }
        
        // Reassign all mail from the mailman to be removed to the new mailman
        for (RegisteredMail mail : allMails) {
            if (mail.getCarrier() != null && mail.getCarrier().equals(mailmanToRemove)) {
                mail.setCarrier(newMailman);
            }
        }
        
        // Remove the mailman
        mailmen.remove(mailmanToRemove);
        return true;
    }
}