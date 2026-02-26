import java.util.ArrayList;
import java.util.List;

abstract class UserRole {
    // Abstract class for user roles
}

class Author extends UserRole {
    private List<Paper> papers;

    public Author() {
        this.papers = new ArrayList<>();
    }

    public void submitPaper(Paper paper) {
        papers.add(paper);
    }

    public int countSubmittedPapers() {
        return papers.size();
    }

    public double calculateAcceptanceRate() {
        if (papers.isEmpty()) {
            return 0.0;
        }
        long accepted = papers.stream().filter(p -> p.getDecision() == Grade.ACCEPT).count();
        return (double) accepted / papers.size();
    }

    public List<Author> findAuthorsWithPositiveReviews() {
        // Implementation not specified in the requirements
        return new ArrayList<>();
    }
}

class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;

    public Reviewer() {
        this.assignments = new ArrayList<>();
    }

    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }

    public void addAssignment(ReviewAssignment assignment) {
        assignments.add(assignment);
    }

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     * @return the number of unsubmitted reviews
     */
    public int calculateUnsubmittedReviews() {
        long unsubmitted = assignments.stream().filter(a -> a.getGrade() == Grade.UNDECIDED).count();
        return (int) unsubmitted;
    }

    /**
     * Calculates the acceptance proportion of the reviewer's historical grades.
     * @return the acceptance proportion as a double between 0.0 and 1.0
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }
        long accepted = assignments.stream().filter(a -> a.getGrade() == Grade.ACCEPT).count();
        return (double) accepted / assignments.size();
    }
}

class CoChair extends UserRole {
    /**
     * Makes a final decision for a paper.
     * @param paper the paper to make a decision for
     * @param decision the decision to make
     */
    public void makeFinalDecision(Paper paper, Grade decision) {
        paper.setDecision(decision);
    }
}

enum PaperType {
    RESEARCH,
    EXPERIENCE
}

enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

class Paper {
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;

    public Paper() {
        this.reviews = new ArrayList<>();
        this.decision = Grade.UNDECIDED;
    }

    /**
     * Calculates the acceptance rate of the paper.
     * @return the acceptance rate as a double between 0.0 and 1.0
     */
    public double calculateAcceptanceRate() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        long accepted = reviews.stream().filter(r -> r.getGrade() == Grade.ACCEPT).count();
        return (double) accepted / reviews.size();
    }

    /**
     * Checks if all reviews for the paper are either exclusively Accept or exclusively Reject.
     * @return true if all reviews are Accept or Reject, false otherwise
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }
        Grade firstGrade = reviews.get(0).getGrade();
        return reviews.stream().allMatch(r -> r.getGrade() == firstGrade);
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PaperType getType() {
        return type;
    }

    public void setType(PaperType type) {
        this.type = type;
    }

    public Grade getDecision() {
        return decision;
    }

    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    public List<ReviewAssignment> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews;
    }
}

class ReviewAssignment {
    private String feedback;
    private Grade grade;

    public ReviewAssignment() {
        this.grade = Grade.UNDECIDED;
    }

    // Getters and setters
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}

class User {
    private String name;
    private List<UserRole> roles;

    public User() {
        this.roles = new ArrayList<>();
    }

    public void addRole(UserRole role) {
        roles.add(role);
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}