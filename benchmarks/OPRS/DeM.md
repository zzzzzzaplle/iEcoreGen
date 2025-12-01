// ==version1==
```
class User {
    - name : String
    - roles : List<UserRole>
   
    + addRole(role : UserRole) : void

}

abstract class UserRole {
}

class Author extends UserRole {
    - papers: List<Paper>

    + submitPaper(paper: Paper) : void
    + countSubmittedPapers() : int
    + calculateAcceptanceRate(): double
    + findAuthorsWithPositiveReviews() : List<Author>
}

class Reviewer extends UserRole {
    - assignments: List<ReviewAssignment>
    + getReviewAssignments: List<ReviewAssignment>
    + calculateUnsubmittedReviews(): int
    + calculateAcceptanceProportion(): double
}

class CoChair extends UserRole {
    + makeFinalDecision(paper: Paper, decision: Grade) : int
}

User *-- "*" UserRole

enum PaperType {
    RESEARCH
    EXPERIENCE
}

enum Grade {
    UNDECIDED
    ACCEPT
    REJECT
}

class Paper {
    - title : String
    - type : PaperType
    - decision : Grade
    - reviews: List<ReviewAssignment>

    + calculateAcceptanceRate(): double
    + isAllReviewsPositive(): boolean

}

class ReviewAssignment {
    - feedback : String
    - grade : Grade
}

Author -- "*" Paper : papers
Reviewer "1" -- "*" ReviewAssignment : assignments
Paper "1" -- "3..*" ReviewAssignment : reviews
```
// ==end==