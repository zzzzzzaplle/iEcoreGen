// ==version1==
```
class Cinema {
    - rooms : List<Room>
    - screenings : List<Screening>
    - films : List<Film>
    
    + addRoom(Room room) : boolean
    + addFilm(Film film) : boolean
    + removeFilm(Film film, Date currentTime) : boolean
    + assignScreening(Film film, Room room, Screening screening, Date currentTime)
    + cancelScreening(Screening screening, Date currentTime)
    + checkAvailability(Room room, Date time) : boolean
}

class Room {
}

class Film {
}

class Screening {
    - time : Date
    - film : Film
    - room : Room
}

Screening --> "1" Film : film
Screening --> "1" Room : room

Cinema *-- "*" Room : rooms
Cinema *-- "*" Screening : screenings
Cinema *-- "*" Film : films 
```
// ==end==
