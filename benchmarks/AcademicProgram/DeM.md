// ==version1==
```
class AcademicProgram {
  - courses: List<Course>

  + sumClassroomCapacity() : int
  + calculateAverageGrade(courseCode : String) : double
  + countCoursesOnSpecialDay(day : String) : int
}

class Course {
  - name : String
  - code : String
  - startTime : Date
  - endTime : Date
  - courseDays : List<String>
  - quota : integer
  - weeklyHours : integer
  - classroom: Classroom
  - enrollments: List<Enrollment>

  + increaseQuotaBy(amount : int) : void
  + decreaseQuotaBy(amount : int) : void
  + changeWeeklyHours(newHours : int) : void
  + changeCourseDays(newDays : String[]) : void
}

AcademicProgram *-- "*" Course : courses

class Classroom {
  - id : String
  - capacity : integer
  - floor : String
  - block : String
}

class Instructor {
  - name : String
  - surname : String
  - title : String
  - specialty : String
  - courses : List<Course>
  
  + calculateTotalEnrolledStudents() : int
  + calculateRemainingQuota() : int
}

Course --> "1" Classroom : room
Instructor --> "*" Course : courses

class Student {
  + enrollInCourse(course : Course) : void
  + dropCourse(course : Course) : void
}

class Enrollment {
  - grade : double
  - student: Student
  - course: Course
}

Enrollment -- "1" Student : student
Enrollment -- "1" Course : course
```
// ==end==
