import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class GeographicalArea {
    private String areaId;
    private List<Inhabitant> inhabitants;
    private List<Mailman> mailmen;

    public GeographicalArea() {
        this.inhabitants = new ArrayList<>();
        this.mailmen = new ArrayList<>();
    }

    public GeographicalArea(String areaId) {
        this();
        this.areaId = areaId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    public List<Mailman> getMailmen() {
        return mailmen;
    }

    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    public boolean addInhabitant(Inhabitant inhabitant) {
        if (!inhabitants.contains(inhabitant)) {
            inhabitants.add(inhabitant);
            return true;
        }
        return false;
    }

    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitants.contains(inhabitant)) {
            inhabitants.remove(inhabitant);
            return true;
        }
        return false;
    }

    public boolean addMailman(Mailman mailman) {
        if (!mailmen.contains(mailman)) {
            mailmen.add(mailman);
            return true;
        }
        return false;
    }

    public boolean removeMailman(Mailman mailman, Mailman replacement) {
        if (mailmen.size() <= 1) {
            return false;
        }
        if (!mailmen.contains(replacement)) {
            return false;
        }
        if (reassignMail(mailman, replacement)) {
            mailmen.remove(mailman);
            return true;
        }
        return false;
    }

    private boolean reassignMail(Mailman oldMailman, Mailman newMailman) {
        for (Inhabitant inhabitant : inhabitants) {
            for (RegisteredMail mail : inhabitant.getRegisteredMail()) {
                if (mail.getAssignedMailman() != null && mail.getAssignedMailman().equals(oldMailman)) {
                    if (!assignMailToMailman(mail, newMailman)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean assignMailToMailman(RegisteredMail mail, Mailman mailman) {
        if (mail.getAssignedMailman() != null) {
            return false;
        }
        if (!mailmen.contains(mailman)) {
            return false;
        }
        mail.setAssignedMailman(mailman);
        return true;
    }

    public List<RegisteredMail> getDeliveries() {
        List<RegisteredMail> deliveries = new ArrayList<>();
        for (Inhabitant inhabitant : inhabitants) {
            for (RegisteredMail mail : inhabitant.getRegisteredMail()) {
                if (mail.getAssignedMailman() != null) {
                    deliveries.add(mail);
                }
            }
        }
        return deliveries;
    }
}

class Inhabitant {
    private String inhabitantId;
    private List<RegisteredMail> registeredMail;

    public Inhabitant() {
        this.registeredMail = new ArrayList<>();
    }

    public Inhabitant(String inhabitantId) {
        this();
        this.inhabitantId = inhabitantId;
    }

    public String getInhabitantId() {
        return inhabitantId;
    }

    public void setInhabitantId(String inhabitantId) {
        this.inhabitantId = inhabitantId;
    }

    public List<RegisteredMail> getRegisteredMail() {
        return registeredMail;
    }

    public void setRegisteredMail(List<RegisteredMail> registeredMail) {
        this.registeredMail = registeredMail;
    }

    public boolean addRegisteredMail(RegisteredMail mail) {
        if (!registeredMail.contains(mail)) {
            registeredMail.add(mail);
            return true;
        }
        return false;
    }

    public boolean removeRegisteredMail(RegisteredMail mail) {
        if (registeredMail.contains(mail)) {
            registeredMail.remove(mail);
            return true;
        }
        return false;
    }

    public List<RegisteredMail> getMailItems() {
        return new ArrayList<>(registeredMail);
    }
}

class Mailman {
    private String mailmanId;

    public Mailman() {
    }

    public Mailman(String mailmanId) {
        this.mailmanId = mailmanId;
    }

    public String getMailmanId() {
        return mailmanId;
    }

    public void setMailmanId(String mailmanId) {
        this.mailmanId = mailmanId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mailman mailman = (Mailman) o;
        return Objects.equals(mailmanId, mailman.mailmanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailmanId);
    }
}

abstract class RegisteredMail {
    private String mailId;
    private Inhabitant addressee;
    private Mailman assignedMailman;

    public RegisteredMail() {
    }

    public RegisteredMail(String mailId, Inhabitant addressee) {
        this.mailId = mailId;
        this.addressee = addressee;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public Inhabitant getAddressee() {
        return addressee;
    }

    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }

    public Mailman getAssignedMailman() {
        return assignedMailman;
    }

    public void setAssignedMailman(Mailman assignedMailman) {
        this.assignedMailman = assignedMailman;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisteredMail that = (RegisteredMail) o;
        return Objects.equals(mailId, that.mailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailId);
    }
}

class Letter extends RegisteredMail {
    public Letter() {
    }

    public Letter(String mailId, Inhabitant addressee) {
        super(mailId, addressee);
    }
}

class Parcel extends RegisteredMail {
    public Parcel() {
    }

    public Parcel(String mailId, Inhabitant addressee) {
        super(mailId, addressee);
    }
}

class MailManagementSystem {
    private Map<String, GeographicalArea> geographicalAreas;

    public MailManagementSystem() {
        this.geographicalAreas = new HashMap<>();
    }

    public boolean addGeographicalArea(GeographicalArea area) {
        if (!geographicalAreas.containsKey(area.getAreaId())) {
            geographicalAreas.put(area.getAreaId(), area);
            return true;
        }
        return false;
    }

    public boolean removeGeographicalArea(String areaId) {
        if (geographicalAreas.containsKey(areaId)) {
            geographicalAreas.remove(areaId);
            return true;
        }
        return false;
    }

    public boolean assignMailToMailman(String areaId, RegisteredMail mail, Mailman mailman) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return false;
        }
        return area.assignMailToMailman(mail, mailman);
    }

    public List<RegisteredMail> getDeliveriesForArea(String areaId) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return new ArrayList<>();
        }
        return area.getDeliveries();
    }

    public boolean addInhabitantToArea(String areaId, Inhabitant inhabitant) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return false;
        }
        return area.addInhabitant(inhabitant);
    }

    public boolean removeInhabitantFromArea(String areaId, Inhabitant inhabitant) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return false;
        }
        if (area.removeInhabitant(inhabitant)) {
            for (RegisteredMail mail : inhabitant.getRegisteredMail()) {
                if (mail.getAssignedMailman() != null) {
                    mail.setAssignedMailman(null);
                }
            }
            return true;
        }
        return false;
    }

    public boolean addMailmanToArea(String areaId, Mailman mailman) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return false;
        }
        return area.addMailman(mailman);
    }

    public boolean removeMailmanFromArea(String areaId, Mailman mailman, Mailman replacement) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return false;
        }
        return area.removeMailman(mailman, replacement);
    }

    public List<RegisteredMail> getMailItemsForInhabitant(Inhabitant inhabitant) {
        List<RegisteredMail> mailItems = new ArrayList<>();
        for (GeographicalArea area : geographicalAreas.values()) {
            if (area.getInhabitants().contains(inhabitant)) {
                mailItems.addAll(inhabitant.getRegisteredMail());
                break;
            }
        }
        return mailItems.isEmpty() ? null : mailItems;
    }
}