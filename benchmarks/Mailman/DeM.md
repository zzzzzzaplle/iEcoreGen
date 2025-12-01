// ==version1==
```
class Mailman 

class GeographicalArea {
    - List<Mailman> mailmen
    - List<Inhabitant> inhabitants
    - List<RegisteredMail> allMails

    + boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail)
    + boolean addMailman(Mailman newMailman)
    + boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman)
    + boolean addInhabitant(Inhabitant newInhabitant)
    + boolean removeInhabitant(Inhabitant addedInhabitant)
    + List<RegisteredMail> listRegisteredMail(Mailman carrier)
    + List<RegisteredMail> listRegisteredMailWithInhabitant( Inhabitant addressee)

}

class Inhabitant {
}

abstract class RegisteredMail {
    - Mailman carrier
    - Inhabitant addressee
}

class Letter extends RegisteredMail 

class Parcel extends RegisteredMail 

GeographicalArea --> "*" Inhabitant : inhabitants
GeographicalArea --> "*" Mailman : mailmen
GeographicalArea --> "*" RegisteredMail : allMails

RegisteredMail --> "1" Mailman : carrier
RegisteredMail --> "1" Inhabitant : addressee
```
// ==end==
