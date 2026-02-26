import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mailman mailman = (Mailman) o;
        return Objects.equals(name, mailman.name) && Objects.equals(assignedArea, mailman.assignedArea);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, assignedArea);
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
     * And ensure the mail isn't already assigned to any mailman.
     *
     * @param carrier the mailman to assign to the mail
     * @param addressee the inhabitant who is the recipient of the mail
     * @param mail the registered mail item to be assigned
     * @return true if the assignment is successful; otherwise, false
     * @throws IllegalArgumentException if carrier, addressee, or mail is null
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (carrier == null || addressee == null || mail == null) {
            throw new IllegalArgumentException("Carrier, addressee, and mail must not be null");
        }
        
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        
        if (mail.getCarrier() != null) {
            return false;
        }
        
        if (!mail.getAddressee().equals(addressee)) {
            return false;
        }
        
        mail.setCarrier(carrier);
        return true;
    }

    /**
     * Adds a new mailman to the geographical area if they're not already assigned to the area.
     *
     * @param newMailman the mailman to add
     * @return true if the mailman is successfully added; otherwise, false
     * @throws IllegalArgumentException if newMailman is null
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null) {
            throw new IllegalArgumentException("New mailman must not be null");
        }
        
        if (mailmen.contains(newMailman)) {
            return false;
        }
        
        mailmen.add(newMailman);
        newMailman.setAssignedArea(this);
        return true;
    }

    /**
     * Removes a mailman from the geographical area.
     * Requires: keeping at least one mailman in the area; specifying a different, existing mailman to take over deliveries;
     * successfully reassigning all mail before removal.
     *
     * @param mailmanToRemove the mailman to remove
     * @param newMailman the mailman who will take over deliveries
     * @return true if the operation is successful, false otherwise
     * @throws IllegalArgumentException if mailmanToRemove or newMailman is null
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            throw new IllegalArgumentException("Mailman to remove and new mailman must not be null");
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
        if (mailsToReassign != null) {
            for (RegisteredMail mail : mailsToReassign) {
                if (!assignRegisteredMailDeliver(newMailman, mail.getAddressee(), mail)) {
                    return false;
                }
            }
        }
        
        mailmen.remove(mailmanToRemove);
        mailmanToRemove.setAssignedArea(null);
        return true;
    }

    /**
     * Adds a new inhabitant to the geographical area.
     *
     * @param newInhabitant the inhabitant to add
     * @return true if the inhabitant is successfully added; otherwise, false
     * @throws IllegalArgumentException if newInhabitant is null
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null) {
            throw new IllegalArgumentException("New inhabitant must not be null");
        }
        
        if (inhabitants.contains(newInhabitant)) {
            return false;
        }
        
        inhabitants.add(newInhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from the geographical area.
     * Delete any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman.
     *
     * @param inhabitantToRemove the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     * @throws IllegalArgumentException if inhabitantToRemove is null
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitantToRemove == null) {
            throw new IllegalArgumentException("Inhabitant to remove must not be null");
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
     * Include only mail items that specify the given mailman as the carrier.
     *
     * @param carrier the mailman to filter mail items by
     * @return list of registered mail items assigned to the specified mailman, or null if none exist
     * @throws IllegalArgumentException if carrier is null
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        if (carrier == null) {
            throw new IllegalArgumentException("Carrier must not be null");
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
     * Include only mail items that specify the given inhabitant as the addressee.
     *
     * @param addressee the inhabitant to filter mail items by
     * @return list of registered mail items directed to the specified inhabitant, or null if none exist
     * @throws IllegalArgumentException if addressee is null
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        if (addressee == null) {
            throw new IllegalArgumentException("Addressee must not be null");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inhabitant that = (Inhabitant) o;
        return Objects.equals(name, that.name) && Objects.equals(area, that.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, area);
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