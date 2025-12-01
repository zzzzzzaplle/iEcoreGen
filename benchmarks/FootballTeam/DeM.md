// ==version1==
```
class FootballTeam {
    - firstEleven : List<Player>
    - spareTeam : List<Player>
    - players : List<Player>
    - announcements : List<Announcement>

    + calculateTotalGoalScoringAnnouncementsForFirstEleven() : int
    + findMidfielderWithHighestNumber() : Player
    + calculateAverageAgeOfSpareTeam() : double
    + calculateGoalSavingAnnouncementsByGoalkeeper() : int
    + calculateTotalAnnouncementsForForwardPlayers() : int
}

enum Position {
    FORWARD
    MIDFIELD
    DEFENSE
    GOALKEEPER
}

class Player {
    - name : String
    - age : integer
    - number : integer
    - position : Position

    + getName() : String
    + setName(name : String) : void
    + getAge() : int
    + setAge(age : int) : void
    + getNumber() : int
    + setNumber(number : int) : void
    + getPosition() : Position
    + setPosition(position : Position) : void
}

enum AnnouncementType {
    SCORE
    SAVE
}

class Announcement {
    - time : Date
    - type : AnnouncementType
    - player : Player

    + getTime() : Date
    + setTime(time : Date) : void
    + getType() : AnnouncementType
    + setType(type : AnnouncementType) : void
    + getPlayer() : Player
    + setPlayer(player : Player) : void
}

FootballTeam --> "0..11" Player : firstEleven
FootballTeam --> "*" Player : spareTeam
FootballTeam *-- "*" Player : players
FootballTeam *-- "*" Announcement : announcements

```
// ==end==
