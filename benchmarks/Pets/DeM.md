// ==version1==
```
class House {
    - pets : List<Pet>

    + retrievePetNames() : List<String>
    + addPet(pet : Pet) : boolean
    + removePet(pet : Pet) : boolean
    + getPetsByType(type : String) : List<Pet>
    + getPetCount() : int
    
}

abstract class Pet {
    - name : String
    - lived : House

    + getName() : String
    + setName(newName : String) : boolean
    + makeNoise() : void
}

class Dog extends Pet
class Cat extends Pet
House *-- "*" Pet :pets
Pet --> House : lived
```
// ==end==
