import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mailman who delivers registered mail
 */
class Mailman {
    private String name;
    
    public Mailman() {
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
        return Objects.equals(name, mailman.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Represents an inhabitant who receives registered mail
 */
class Inhabitant {
    private String name;
    
    public Inhabitant() {
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
        return Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Abstract class representing registered mail
 */
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

/**
 * Represents a letter as a type of registered mail
 */
class Letter extends RegisteredMail {
    public Letter() {
    }
}

/**
 * Represents a parcel as a type of registered mail
 */
class Parcel extends RegisteredMail {
    public Parcel() {
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
     * @param carrier The mailman to assign the delivery to
     * @param addressee The inhabitant receiving the mail
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
        
        // Check if mail is already assigned to a mailman
        if (mail.getCarrier() != null) {
            return false;
        }
        
        // Check if mail exists in the system
        if (!allMails.contains(mail)) {
            return false;
        }
        
        // Assign the mail to the carrier
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        return true;
    }
    
    /**
     * Lists all registered mail items (letters and parcels) assigned to a specified mailman
     * 
     * @param carrier The mailman whose assigned mail items are to be listed
     * @return List of registered mail items assigned to the mailman, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        if (carrier == null || !mailmen.contains(carrier)) {
            return null;
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
     * Lists all registered mail items (letters and parcels) directed to a specified inhabitant
     * 
     * @param addressee The inhabitant whose mail items are to be listed
     * @return List of registered mail items directed to the inhabitant, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        if (addressee == null || !inhabitants.contains(addressee)) {
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
    
    /**
     * Adds a new inhabitant to the geographical area
     * 
     * @param newInhabitant The inhabitant to be added
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
     * @param inhabitantToRemove The inhabitant to be removed
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitantToRemove == null || !inhabitants.contains(inhabitantToRemove)) {
            return false;
        }
        
        // Remove all mail items addressed to this inhabitant that are assigned to a mailman
        List<RegisteredMail> mailToRemove = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (inhabitantToRemove.equals(mail.getAddressee()) && mail.getCarrier() != null) {
                mailToRemove.add(mail);
            }
        }
        allMails.removeAll(mailToRemove);
        
        // Remove the inhabitant
        inhabitants.remove(inhabitantToRemove);
        return true;
    }
    
    /**
     * Adds a mailman to the geographical area if they're not already assigned
     * 
     * @param newMailman The mailman to be added
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
     * Requires: keeping at least one mailman in the area; specifying a different, existing mailman to take over deliveries;
     * successfully reassigning all mail before removal
     * 
     * @param mailmanToRemove The mailman to be removed
     * @param newMailman The mailman who will take over the deliveries
     * @return true if the operation is successful, false otherwise
     * @throws IllegalArgumentException if there's only one mailman left or if new mailman is invalid
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            return false;
        }
        
        // Check if there's at least one mailman remaining after removal
        if (mailmen.size() <= 1) {
            throw new IllegalArgumentException("Cannot remove the last mailman from the area");
        }
        
        // Check if mailman to remove exists in the area
        if (!mailmen.contains(mailmanToRemove)) {
            return false;
        }
        
        // Check if new mailman exists in the area and is different from the one to remove
        if (!mailmen.contains(newMailman) || mailmanToRemove.equals(newMailman)) {
            return false;
        }
        
        // Reassign all mail from the mailman to remove to the new mailman
        for (RegisteredMail mail : allMails) {
            if (mailmanToRemove.equals(mail.getCarrier())) {
                mail.setCarrier(newMailman);
            }
        }
        
        // Remove the mailman
        mailmen.remove(mailmanToRemove);
        return true;
    }
}