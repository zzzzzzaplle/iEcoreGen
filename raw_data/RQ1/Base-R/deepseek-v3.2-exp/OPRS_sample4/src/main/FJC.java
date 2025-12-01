import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the review system
 */
class User {
    private String name;
    private List<Paper> submittedPapers;
    private List<Review> reviews;

    /**
     * Default constructor
     */
    public User() {
        this.submittedPapers = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    /**
     * Gets the user's name
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of submitted papers
     * @return list of submitted papers
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
     * Gets the list of reviews
     * @return list of reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews
     * @param reviews the list of reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}

/**
 * Represents an author user who can submit papers
 */
class Author extends User {
    
    /**
     * Default constructor
     */
    public Author() {
        super();
    }

    /**
     * Counts the total number of papers submitted by this author
     * @return the total number of submitted papers
     */
    public int countTotalSubmittedPapers() {
        return getSubmittedPapers().size();
    }

    /**
     * Calculates the acceptance rate of papers for this author
     * @return the acceptance rate as a double between 0.0 and 1.0, or 0.0 if no papers submitted
     */
    public double calculateAcceptanceRate() {
        List<Paper> submittedPapers = getSubmittedPapers();
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
    
    /**
     * Default constructor
     */
    public Reviewer() {
        super();
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer
     * @return the number of unsubmitted reviews, 0 if all reviews are submitted
     */
    public int countUnsubmittedReviews() {
        return (int) getReviews().stream()
                .filter(review -> !review.isSubmitted())
                .count();
    }

    /**
     * Converts the reviewer's historical grades into a numerical average score
     * @return the average score between 0.0 and 1.0, or 0.0 if no reviews
     */
    public double calculateHistoricalGradeAverage() {
        List<Review> reviews = getReviews();
        if (reviews.isEmpty()) {
            return 0.0;
        }
        
        double totalScore = reviews.stream()
                .mapToDouble(review -> review.getGrade() == Grade.ACCEPT ? 1.0 : 0.0)
                .sum();
        
        return totalScore / reviews.size();
    }
}

/**
 * Represents a co-chair user
 */
class CoChair extends User {
    
    /**
     * Default constructor
     */
    public CoChair() {
        super();
    }
}

/**
 * Represents a paper that can be submitted and reviewed
 */
class Paper {
    private String title;
    private PaperType type;
    private Decision decision;
    private List<Review> reviews;

    /**
     * Default constructor
     */
    public Paper() {
        this.reviews = new ArrayList<>();
    }

    /**
     * Gets the paper title
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the paper title
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the paper type
     * @return the paper type
     */
    public PaperType getType() {
        return type;
    }

    /**
     * Sets the paper type
     * @param type the paper type to set
     */
    public void setType(PaperType type) {
        this.type = type;
    }

    /**
     * Gets the final decision for the paper
     * @return the decision
     */
    public Decision getDecision() {
        return decision;
    }

    /**
     * Sets the final decision for the paper
     * @param decision the decision to set
     */
    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    /**
     * Gets the list of reviews for this paper
     * @return list of reviews
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
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject
     * @return true if all reviews have the same grade (either all Accept or all Reject), false otherwise
     */
    public boolean hasUnanimousReviews() {
        if (reviews.isEmpty()) {
            return false;
        }
        
        Grade firstGrade = reviews.get(0).getGrade();
        return reviews.stream()
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
        this.submitted = false;
    }

    /**
     * Gets the review feedback
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the review feedback
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the review grade
     * @return the grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Sets the review grade
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