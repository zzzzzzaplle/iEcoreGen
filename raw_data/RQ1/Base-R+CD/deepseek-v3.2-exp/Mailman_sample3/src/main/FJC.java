import java.util.ArrayList;
import java.util.List;

class Mailman {
    private String name;
    private GeographicalArea area;

    public Mailman() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeographicalArea getArea() {
        return area;
    }

    public void setArea(GeographicalArea area) {
        this.area = area;
    }
}

class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;

    public GeographicalArea() {
        mailmen = new ArrayList<>();
        inhabitants = new ArrayList<>();
        allMails = new ArrayList<>();
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
     * The mailman and the inhabitant must belong to the addressee's geographical area.
     * Ensures the mail isn't already assigned to any mailman.
     *
     * @param carrier the mailman to assign for delivery
     * @param addressee the inhabitant who will receive the mail
     * @param mail the registered mail item to be assigned
     * @return true if the assignment is successful; false otherwise
     * @throws IllegalArgumentException if carrier, addressee, or mail is null
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (carrier == null || addressee == null || mail == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        
        if (mail.getCarrier() != null) {
            return false;
        }
        
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        return true;
    }

    /**
     * Adds a new mailman to the geographical area if they're not already assigned.
     *
     * @param newMailman the mailman to be added
     * @return true if the mailman is added successfully; false otherwise
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
        newMailman.setArea(this);
        return true;
    }

    /**
     * Removes a mailman from the geographical area.
     * Requires: keeping at least one mailman in the area; specifying a different, existing mailman to take over deliveries; 
     * successfully reassigning all mail before removal.
     *
     * @param mailmanToRemove the mailman to be removed
     * @param newMailman the mailman who will take over the deliveries
     * @return true if the removal is successful; false otherwise
     * @throws IllegalArgumentException if mailmanToRemove or newMailman is null
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            throw new IllegalArgumentException("Mailmen cannot be null");
        }
        
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman)) {
            return false;
        }
        
        if (mailmen.size() <= 1) {
            return false;
        }
        
        if (mailmanToRemove.equals(newMailman)) {
            return false;
        }
        
        List<RegisteredMail> mailsToReassign = listRegisteredMail(mailmanToRemove);
        for (RegisteredMail mail : mailsToReassign) {
            if (!assignRegisteredMailDeliver(newMailman, mail.getAddressee(), mail)) {
                return false;
            }
        }
        
        mailmen.remove(mailmanToRemove);
        mailmanToRemove.setArea(null);
        return true;
    }

    /**
     * Adds a new inhabitant to the geographical area.
     *
     * @param newInhabitant the inhabitant to be added
     * @return true if the inhabitant is added successfully; false otherwise
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
     * Removes an inhabitant from the geographical area.
     * Deletes any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman.
     *
     * @param inhabitantToRemove the inhabitant to be removed
     * @return true if the removal is successful; false otherwise
     * @throws IllegalArgumentException if inhabitantToRemove is null
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitantToRemove == null) {
            throw new IllegalArgumentException("Inhabitant cannot be null");
        }
        
        if (!inhabitants.contains(inhabitantToRemove)) {
            return false;
        }
        
        List<RegisteredMail> mailsToRemove = listRegisteredMailWithInhabitant(inhabitantToRemove);
        for (RegisteredMail mail : mailsToRemove) {
            if (mail.getCarrier() != null) {
                allMails.remove(mail);
            }
        }
        
        inhabitants.remove(inhabitantToRemove);
        return true;
    }

    /**
     * Lists all registered mail items (letters and parcels) assigned to a specified mailman.
     * Includes only mail items that specify the given mailman as the carrier.
     *
     * @param carrier the mailman whose assigned mail items are to be listed
     * @return list of registered mail items assigned to the mailman; null if none exist
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
     * @param addressee the inhabitant whose mail items are to be listed
     * @return list of registered mail items directed to the inhabitant; null if none exist
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

class Inhabitant {
    private String name;
    private GeographicalArea area;

    public Inhabitant() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeographicalArea getArea() {
        return area;
    }

    public void setArea(GeographicalArea area) {
        this.area = area;
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