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
 * Abstract base class for registered mail items
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;
    private String trackingNumber;
    private boolean isAssigned;
    
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
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    public boolean isAssigned() {
        return isAssigned;
    }
    
    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisteredMail that = (RegisteredMail) o;
        return Objects.equals(trackingNumber, that.trackingNumber);
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
    private boolean isUrgent;
    
    public Letter() {
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public boolean isUrgent() {
        return isUrgent;
    }
    
    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }
}

/**
 * Represents a registered parcel
 */
class Parcel extends RegisteredMail {
    private double weight;
    private double dimensions;
    private boolean requiresSignature;
    
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
    
    public boolean isRequiresSignature() {
        return requiresSignature;
    }
    
    public void setRequiresSignature(boolean requiresSignature) {
        this.requiresSignature = requiresSignature;
    }
}

/**
 * Represents a geographical area with mailmen, inhabitants, and registered mail
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;
    private String areaCode;
    private String areaName;
    
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
    
    public String getAreaCode() {
        return areaCode;
    }
    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    public String getAreaName() {
        return areaName;
    }
    
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    
    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item
     * The mailman and the inhabitant must belong to the addressee's geographical area
     * Ensures the mail isn't already assigned to any mailman
     * 
     * @param carrier The mailman to assign to the mail delivery
     * @param addressee The inhabitant who will receive the mail
     * @param mail The registered mail item to be assigned
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (carrier == null || addressee == null || mail == null) {
            return false;
        }
        
        // Check if mailman and inhabitant belong to this geographical area
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        
        // Check if mail is already assigned to any mailman
        if (mail.isAssigned()) {
            return false;
        }
        
        // Assign the mail to the carrier and addressee
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        mail.setAssigned(true);
        
        // Add mail to the area's mail list if not already present
        if (!allMails.contains(mail)) {
            allMails.add(mail);
        }
        
        return true;
    }
    
    /**
     * Adds a new mailman to the geographical area if they're not already assigned
     * 
     * @param newMailman The new mailman to be added
     * @return true if the operation is successful, false otherwise
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null) {
            return false;
        }
        
        // Check if mailman already exists in the area
        if (mailmen.contains(newMailman)) {
            return false;
        }
        
        mailmen.add(newMailman);
        return true;
    }
    
    /**
     * Removes a mailman from the geographical area with reassignment of deliveries
     * Requires keeping at least one mailman in the area and specifying a different existing mailman to take over deliveries
     * 
     * @param mailmanToRemove The mailman to be removed
     * @param newMailman The mailman who will take over the deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            return false;
        }
        
        // Cannot remove if there's only one mailman in the area
        if (mailmen.size() <= 1) {
            return false;
        }
        
        // Check if both mailmen exist in the area
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman)) {
            return false;
        }
        
        // Cannot assign to the same mailman
        if (mailmanToRemove.equals(newMailman)) {
            return false;
        }
        
        // Reassign all mail from mailmanToRemove to newMailman
        for (RegisteredMail mail : allMails) {
            if (mailmanToRemove.equals(mail.getCarrier())) {
                mail.setCarrier(newMailman);
            }
        }
        
        // Remove the mailman
        mailmen.remove(mailmanToRemove);
        return true;
    }
    
    /**
     * Adds a new inhabitant to the geographical area
     * 
     * @param newInhabitant The new inhabitant to be added
     * @return true if the operation is successful, false otherwise
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null) {
            return false;
        }
        
        // Check if inhabitant already exists in the area
        if (inhabitants.contains(newInhabitant)) {
            return false;
        }
        
        inhabitants.add(newInhabitant);
        return true;
    }
    
    /**
     * Removes an inhabitant from the geographical area and deletes any registered mail assigned to that inhabitant
     * 
     * @param inhabitantToRemove The inhabitant to be removed
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitantToRemove == null) {
            return false;
        }
        
        // Check if inhabitant exists in the area
        if (!inhabitants.contains(inhabitantToRemove)) {
            return false;
        }
        
        // Delete any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman
        List<RegisteredMail> mailToRemove = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (inhabitantToRemove.equals(mail.getAddressee()) && mail.isAssigned()) {
                mailToRemove.add(mail);
            }
        }
        
        allMails.removeAll(mailToRemove);
        inhabitants.remove(inhabitantToRemove);
        return true;
    }
    
    /**
     * Lists all registered mail items (letters and parcels) assigned to a specified mailman
     * 
     * @param carrier The mailman whose assigned mail items are to be listed
     * @return List of registered mail items assigned to the mailman, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        if (carrier == null) {
            return null;
        }
        
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (carrier.equals(mail.getCarrier()) && mail.isAssigned()) {
                result.add(mail);
            }
        }
        
        return result.isEmpty() ? null : result;
    }
    
    /**
     * Lists all registered mail items (letters and parcels) directed to a specified inhabitant
     * 
     * @param addressee The inhabitant whose mail items are to be listed
     * @return List of registered mail items directed to the inhabitant, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        if (addressee == null) {
            return null;
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