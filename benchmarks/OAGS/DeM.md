// ==version1==
```
enum Gender {
    MALE
    FEMALE
}

class Artist {
    - name : String
    - phoneNumber : String
    - id : String
    - email : String
    - address : String
    - gender : Gender
    - artworks : List<Artwork>
    - membership : Membership
    
    + calculateTotalPrice() : double
    + calculateRewardPoints(Date startDate, Date endDate) : int
    + countArtworksByCategory(): Map<ArtworkCategory, Integer>
    + addArtwork(artwork : Artwork) : void

}

enum ArtworkCategory {
    PAINTING
    SCULPTURE
    ARCHITECTURE
}

class Artwork {
    - title : String
    - description : String
    - category : ArtworkCategory
    - price : double
    - artist: Artist
    
}

enum MembershipType {
    INDIVIDUAL
    AGENCY
    AGENCY_AFFILIATE
}

class Membership {
    - ID : String
    - startDate : Date
    - endDate : Date
    - rewardPoint : integer
    - type : MembershipType
    
   
}

Artist --> "1" Membership : membership
Artwork "*" -- "1" Artist : author
```
// ==end==
