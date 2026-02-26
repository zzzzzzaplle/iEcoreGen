import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the review system
 */
class User {
    private String name;

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * Gets the name of the user
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an author user who can submit papers
 */
class Author extends User {
    private List<Paper> submittedPapers;

    /**
     * Default constructor
     */
    public Author() {
        submittedPapers = new ArrayList<>();
    }

    /**
     * Gets the list of submitted papers
     * @return the list of submitted papers
     */
    public List<Paper> getSubmittedPapers() {
        return submittedPapers;
    }

    /**
     * Sets the list of submitted papers
     * @param submittedPapers the list of submitted papers to set
     */
    public void setSubmittedPapers(List<Paper> submittedPapers) {
        this.submittedPapers = submittedPapers;
    }

    /**
     * Counts the total number of papers submitted by this author
     * @return the total number of papers submitted
     */
    public int countTotalPapers() {
        return submittedPapers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author
     * @return the acceptance rate as a double between 0.0 and 1.0, 
     *         returns 0.0 if no papers submitted
     */
    public double calculateAcceptanceRate() {
        if (submittedPapers.isEmpty()) {
            return 0.0;
        }
        
        long acceptedCount = submittedPapers.stream()
                .filter(paper -> paper.getDecision() == Decision.ACCEPT)
                .count();
        
        return (double) acceptedCount / submittedPapers.size();
    }
}

/**
 * Represents a reviewer user who can review papers
 */
class Reviewer extends User {
    private List<Review> assignedReviews;

    /**
     * Default constructor
     */
    public Reviewer() {
        assignedReviews = new ArrayList<>();
    }

    /**
     * Gets the list of assigned reviews
     * @return the list of assigned reviews
     */
    public List<Review> getAssignedReviews() {
        return assignedReviews;
    }

    /**
     * Sets the list of assigned reviews
     * @param assignedReviews the list of assigned reviews to set
     */
    public void setAssignedReviews(List<Review> assignedReviews) {
        this.assignedReviews = assignedReviews;
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer
     * @return the number of unsubmitted reviews, returns 0 if all reviews are submitted
     */
    public int calculateUnsubmittedReviews() {
        return (int) assignedReviews.stream()
                .filter(review -> !review.isSubmitted())
                .count();
    }

    /**
     * Converts the reviewer's historical grades into a numerical average score
     * @return the average score between 0.0 and 1.0, returns 0.0 if no reviews submitted
     */
    public double calculateHistoricalAverage() {
        List<Review> submittedReviews = assignedReviews.stream()
                .filter(Review::isSubmitted)
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

/**
 * Represents a co-chair user who can make final decisions
 */
class CoChair extends User {
    /**
     * Default constructor
     */
    public CoChair() {
    }
}

/**
 * Represents a paper that can be submitted and reviewed
 */
class Paper {
    private String title;
    private PaperType type;
    private List<Review> reviews;
    private Decision decision;
    private Author author;

    /**
     * Default constructor
     */
    public Paper() {
        reviews = new ArrayList<>();
    }

    /**
     * Gets the title of the paper
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the paper
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the type of the paper
     * @return the paper type
     */
    public PaperType getType() {
        return type;
    }

    /**
     * Sets the type of the paper
     * @param type the paper type to set
     */
    public void setType(PaperType type) {
        this.type = type;
    }

    /**
     * Gets the list of reviews for this paper
     * @return the list of reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews for this paper
     * @param reviews the list of reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Gets the final decision for this paper
     * @return the decision
     */
    public Decision getDecision() {
        return decision;
    }

    /**
     * Sets the final decision for this paper
     * @param decision the decision to set
     */
    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    /**
     * Gets the author of this paper
     * @return the author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the author of this paper
     * @param author the author to set
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject
     * @return true if all reviews have the same grade (either all Accept or all Reject), 
     *         false otherwise or if there are no submitted reviews
     */
    public boolean hasUnanimousReviews() {
        if (reviews.isEmpty()) {
            return false;
        }
        
        List<Review> submittedReviews = reviews.stream()
                .filter(Review::isSubmitted)
                .toList();
        
        if (submittedReviews.isEmpty()) {
            return false;
        }
        
        Grade firstGrade = submittedReviews.get(0).getGrade();
        return submittedReviews.stream()
                .allMatch(review -> review.getGrade() == firstGrade);
    }
}

/**
 * Represents a review for a paper
 */
class Review {
    private String feedback;
    private Grade grade;
    private boolean submitted;
    private Reviewer reviewer;
    private Paper paper;

    /**
     * Default constructor
     */
    public Review() {
    }

    /**
     * Gets the feedback for the review
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback for the review
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the grade for the review
     * @return the grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Sets the grade for the review
     * @param grade the grade to set
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    /**
     * Checks if the review has been submitted
     * @return true if submitted, false otherwise
     */
    public boolean isSubmitted() {
        return submitted;
    }

    /**
     * Sets the submitted status of the review
     * @param submitted the submitted status to set
     */
    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    /**
     * Gets the reviewer who wrote this review
     * @return the reviewer
     */
    public Reviewer getReviewer() {
        return reviewer;
    }

    /**
     * Sets the reviewer who wrote this review
     * @param reviewer the reviewer to set
     */
    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    /**
     * Gets the paper being reviewed
     * @return the paper
     */
    public Paper getPaper() {
        return paper;
    }

    /**
     * Sets the paper being reviewed
     * @param paper the paper to set
     */
    public void setPaper(Paper paper) {
        this.paper = paper;
    }
}

/**
 * Enum representing the type of paper
 */
enum PaperType {
    RESEARCH_PAPER,
    EXPERIENCE_REPORT_PAPER
}

/**
 * Enum representing the grade in a review
 */
enum Grade {
    ACCEPT,
    REJECT
}

/**
 * Enum representing the final decision for a paper
 */
enum Decision {
    ACCEPT,
    REJECT,
    PENDING
}