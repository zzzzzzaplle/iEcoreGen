import java.util.ArrayList;
import java.util.List;

class Mailman {
    private String name;
    private GeographicalArea geographicalArea;

    public Mailman() {
    }

    public Mailman(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeographicalArea getGeographicalArea() {
        return geographicalArea;
    }

    public void setGeographicalArea(GeographicalArea geographicalArea) {
        this.geographicalArea = geographicalArea;
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

    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (mailmen.contains(carrier) && inhabitants.contains(addressee) && !allMails.contains(mail)) {
            mail.setCarrier(carrier);
            mail.setAddressee(addressee);
            allMails.add(mail);
            return true;
        }
        return false;
    }

    public boolean addMailman(Mailman newMailman) {
        if (!mailmen.contains(newMailman)) {
            mailmen.add(newMailman);
            newMailman.setGeographicalArea(this);
            return true;
        }
        return false;
    }

    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmen.size() <= 1 || !mailmen.contains(newMailman) || !reassignMails(mailmanToRemove, newMailman)) {
            return false;
        }
        mailmen.remove(mailmanToRemove);
        return true;
    }

    private boolean reassignMails(Mailman oldMailman, Mailman newMailman) {
        for (RegisteredMail mail : allMails) {
            if (mail.getCarrier().equals(oldMailman)) {
                if (!assignRegisteredMailDeliver(newMailman, mail.getAddressee(), mail)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (!inhabitants.contains(newInhabitant)) {
            inhabitants.add(newInhabitant);
            return true;
        }
        return false;
    }

    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitants.contains(inhabitantToRemove)) {
            for (RegisteredMail mail : allMails) {
                if (mail.getAddressee().equals(inhabitantToRemove) && mail.getCarrier() != null) {
                    mail.setCarrier(null);
                }
            }
            inhabitants.remove(inhabitantToRemove);
            return true;
        }
        return false;
    }

    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getCarrier() != null && mail.getCarrier().equals(carrier)) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }

    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getAddressee() != null && mail.getAddressee().equals(addressee)) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
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
}

class Inhabitant {
    private String name;
    private GeographicalArea geographicalArea;

    public Inhabitant() {
    }

    public Inhabitant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeographicalArea getGeographicalArea() {
        return geographicalArea;
    }

    public void setGeographicalArea(GeographicalArea geographicalArea) {
        this.geographicalArea = geographicalArea;
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