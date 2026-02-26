import java.util.ArrayList;
import java.util.List;

class Mailman {
    private String name;
    private GeographicalArea assignedArea;

    public Mailman() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeographicalArea getAssignedArea() {
        return assignedArea;
    }

    public void setAssignedArea(GeographicalArea assignedArea) {
        this.assignedArea = assignedArea;
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
     * Assigns a specific mailman to deliver a registered mail item to an inhabitant.
     * The mailman and the inhabitant must belong to the same geographical area as the addressee,
     * and the mail must not already be assigned to any mailman.
     *
     * @param carrier the mailman to assign to the mail delivery
     * @param addressee the inhabitant who is the recipient of the mail
     * @param mail the registered mail item to be assigned
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (carrier == null || addressee == null || mail == null) {
            return false;
        }
        
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        
        if (mail.getCarrier() != null) {
            return false;
        }
        
        if (mail.getAddressee() != null && !mail.getAddressee().equals(addressee)) {
            return false;
        }
        
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        
        if (!allMails.contains(mail)) {
            allMails.add(mail);
        }
        
        return true;
    }

    /**
     * Adds a new mailman to the geographical area if they're not already assigned.
     *
     * @param newMailman the mailman to be added
     * @return true if the mailman was added successfully, false otherwise
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null) {
            return false;
        }
        
        if (mailmen.contains(newMailman)) {
            return false;
        }
        
        newMailman.setAssignedArea(this);
        mailmen.add(newMailman);
        return true;
    }

    /**
     * Removes a mailman from the geographical area.
     * Requires: keeping at least one mailman in the area, specifying a different existing mailman
     * to take over deliveries, and successfully reassigning all mail before removal.
     *
     * @param mailmanToRemove the mailman to be removed
     * @param newMailman the mailman who will take over the deliveries
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            return false;
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
        
        List<RegisteredMail> mailsToReassign = listRegisteredMail(mailmanToRemove);
        for (RegisteredMail mail : mailsToReassign) {
            mail.setCarrier(newMailman);
        }
        
        mailmen.remove(mailmanToRemove);
        mailmanToRemove.setAssignedArea(null);
        return true;
    }

    /**
     * Adds a new inhabitant to the geographical area.
     *
     * @param newInhabitant the inhabitant to be added
     * @return true if the inhabitant was added successfully, false otherwise
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null) {
            return false;
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
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitantToRemove == null) {
            return false;
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
     *
     * @param carrier the mailman whose assigned mail items are to be listed
     * @return a list of registered mail items assigned to the specified mailman, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        if (carrier == null) {
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
     * Lists all registered mail items (letters and parcels) directed to a specified inhabitant.
     *
     * @param addressee the inhabitant whose mail items are to be listed
     * @return a list of registered mail items directed to the specified inhabitant, or null if none exist
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

class Inhabitant {
    private String name;
    private GeographicalArea residentialArea;

    public Inhabitant() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeographicalArea getResidentialArea() {
        return residentialArea;
    }

    public void setResidentialArea(GeographicalArea residentialArea) {
        this.residentialArea = residentialArea;
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