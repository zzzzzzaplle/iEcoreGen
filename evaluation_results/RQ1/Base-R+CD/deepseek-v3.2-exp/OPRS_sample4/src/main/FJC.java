import java.util.ArrayList;
import java.util.List;

class User {
    private String name;
    private List<UserRole> roles;

    public User() {
        this.roles = new ArrayList<>();
    }

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

    public void addRole(UserRole role) {
        if (role != null) {
            this.roles.add(role);
        }
    }
}

abstract class UserRole {
}

class Author extends UserRole {
    private List<Paper> papers;

    public Author() {
        this.papers = new ArrayList<>();
    }

    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    /**
     * Counts the total number of papers submitted by this author.
     * 
     * @return the total count of papers submitted by this author
     */
    public int countTotalPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author.
     * The acceptance rate is defined as the ratio of accepted papers to total submitted papers.
     * 
     * @return the acceptance rate as a double between 0.0 and 1.0, or 0.0 if no papers are submitted
     */
    public double calculateAcceptanceRate() {
        if (papers.isEmpty()) {
            return 0.0;
        }
        
        long acceptedCount = papers.stream()
            .filter(paper -> paper.getDecision() != null && paper.getDecision().isAccepted())
            .count();
        
        return (double) acceptedCount / papers.size();
    }
}

class Reviewer extends UserRole {
    private List<ReviewAssignment> reviewAssignments;

    public Reviewer() {
        this.reviewAssignments = new ArrayList<>();
    }

    public List<ReviewAssignment> getReviewAssignments() {
        return reviewAssignments;
    }

    public void setReviewAssignments(List<ReviewAssignment> reviewAssignments) {
        this.reviewAssignments = reviewAssignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * 
     * @return the count of review assignments that do not have a submitted review
     */
    public int countUnsubmittedReviews() {
        return (int) reviewAssignments.stream()
            .filter(assignment -> assignment.getReview() == null)
            .count();
    }

    /**
     * Converts the reviewer's historical grades into a numerical average score.
     * Accept grades are counted as 1.0, Reject grades as 0.0.
     * 
     * @return the average score as a double between 0.0 and 1.0, or 0.0 if no reviews are submitted
     */
    public double calculateHistoricalGradeAverage() {
        List<Review> submittedReviews = reviewAssignments.stream()
            .map(ReviewAssignment::getReview)
            .filter(review -> review != null)
            .toList();
            
        if (submittedReviews.isEmpty()) {
            return 0.0;
        }
        
        double totalScore = submittedReviews.stream()
            .mapToDouble(review -> review.getGrade() == Grade.ACCEPT ? 1.0 : 0.0)
            .sum();
            
        return totalScore / submittedReviews.size();
    }
}

class CoChair extends UserRole {
}

class Paper {
    private String title;
    private PaperType type;
    private List<ReviewAssignment> reviewAssignments;
    private Decision decision;
    private Author author;

    public Paper() {
        this.reviewAssignments = new ArrayList<>();
    }

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

    public List<ReviewAssignment> getReviewAssignments() {
        return reviewAssignments;
    }

    public void setReviewAssignments(List<ReviewAssignment> reviewAssignments) {
        this.reviewAssignments = reviewAssignments;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Checks if all submitted reviews for this paper are either exclusively Accept or exclusively Reject.
     * This method should be called before the chair makes the final decision.
     * 
     * @return true if all submitted reviews have the same grade (either all Accept or all Reject), 
     *         false if there are mixed grades or no submitted reviews
     */
    public boolean areReviewsUnanimous() {
        List<Review> submittedReviews = reviewAssignments.stream()
            .map(ReviewAssignment::getReview)
            .filter(review -> review != null)
            .toList();
            
        if (submittedReviews.isEmpty()) {
            return false;
        }
        
        Grade firstGrade = submittedReviews.get(0).getGrade();
        return submittedReviews.stream()
            .allMatch(review -> review.getGrade() == firstGrade);
    }
}

class ReviewAssignment {
    private Reviewer reviewer;
    private Paper paper;
    private Review review;

    public ReviewAssignment() {
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}

class Review {
    private String feedback;
    private Grade grade;

    public Review() {
    }

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

enum Grade {
    ACCEPT,
    REJECT
}

enum PaperType {
    RESEARCH_PAPER,
    EXPERIENCE_REPORT
}

class Decision {
    private boolean accepted;

    public Decision() {
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}