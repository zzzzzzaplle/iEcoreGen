import java.util.ArrayList;
import java.util.List;

abstract class UserRole {
    // Base class for all user roles
}

class Author extends UserRole {
    private List<Paper> papers;

    public Author() {
        this.papers = new ArrayList<>();
    }

    /**
     * Submits a paper for the author.
     * @param paper The paper to be submitted.
     */
    public void submitPaper(Paper paper) {
        papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by the author.
     * @return The number of submitted papers.
     */
    public int countSubmittedPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for the author.
     * @return The acceptance rate as a double value between 0.0 and 1.0.
     */
    public double calculateAcceptanceRate() {
        if (papers.isEmpty()) {
            return 0.0;
        }
        int accepted = 0;
        for (Paper paper : papers) {
            if (paper.getDecision() == Grade.ACCEPT) {
                accepted++;
            }
        }
        return (double) accepted / papers.size();
    }

    /**
     * Finds authors with positive reviews.
     * @return A list of authors with positive reviews.
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        List<Author> authorsWithPositiveReviews = new ArrayList<>();
        for (Paper paper : papers) {
            if (paper.isAllReviewsPositive()) {
                authorsWithPositiveReviews.add(this);
                break;
            }
        }
        return authorsWithPositiveReviews;
    }

    // Getters and Setters
    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }
}

class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;

    public Reviewer() {
        this.assignments = new ArrayList<>();
    }

    /**
     * Gets the list of review assignments for the reviewer.
     * @return The list of review assignments.
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     * @return The number of unsubmitted reviews.
     */
    public int calculateUnsubmittedReviews() {
        int unsubmitted = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.UNDECIDED) {
                unsubmitted++;
            }
        }
        return unsubmitted;
    }

    /**
     * Calculates the acceptance proportion for the reviewer.
     * @return The acceptance proportion as a double value between 0.0 and 1.0.
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }
        int accepted = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.ACCEPT) {
                accepted++;
            }
        }
        return (double) accepted / assignments.size();
    }

    // Getters and Setters
    public void setReviewAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }
}

class CoChair extends UserRole {
    /**
     * Makes the final decision for a paper.
     * @param paper The paper to make a decision for.
     * @param decision The decision to make (Accept or Reject).
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
    }

    /**
     * Calculates the acceptance rate for the paper.
     * @return The acceptance rate as a double value between 0.0 and 1.0.
     */
    public double calculateAcceptanceRate() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        int accepted = 0;
        for (ReviewAssignment review : reviews) {
            if (review.getGrade() == Grade.ACCEPT) {
                accepted++;
            }
        }
        return (double) accepted / reviews.size();
    }

    /**
     * Checks if all reviews for the paper are either exclusively Accept or exclusively Reject.
     * @return True if all reviews are either Accept or Reject, false otherwise.
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }
        Grade firstGrade = reviews.get(0).getGrade();
        for (ReviewAssignment review : reviews) {
            if (review.getGrade() != firstGrade) {
                return false;
            }
        }
        return firstGrade == Grade.ACCEPT;
    }

    // Getters and Setters
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

    // Getters and Setters
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

    /**
     * Adds a role to the user.
     * @param role The role to add.
     */
    public void addRole(UserRole role) {
        roles.add(role);
    }

    // Getters and Setters
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