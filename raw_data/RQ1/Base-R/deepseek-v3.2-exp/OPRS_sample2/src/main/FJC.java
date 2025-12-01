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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Paper> getSubmittedPapers() {
        return submittedPapers;
    }
    
    public void setSubmittedPapers(List<Paper> submittedPapers) {
        this.submittedPapers = submittedPapers;
    }
    
    public List<Review> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    /**
     * Adds a paper to the user's submitted papers
     * @param paper the paper to add
     */
    public void addSubmittedPaper(Paper paper) {
        this.submittedPapers.add(paper);
    }
    
    /**
     * Adds a review to the user's reviews
     * @param review the review to add
     */
    public void addReview(Review review) {
        this.reviews.add(review);
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
    public int countSubmittedPapers() {
        return getSubmittedPapers().size();
    }
    
    /**
     * Calculates the acceptance rate of papers for this author
     * @return the acceptance rate as a double between 0.0 and 1.0, or 0.0 if no papers submitted
     */
    public double calculateAcceptanceRate() {
        List<Paper> papers = getSubmittedPapers();
        if (papers.isEmpty()) {
            return 0.0;
        }
        
        long acceptedCount = papers.stream()
            .filter(paper -> paper.getDecision() != null && paper.getDecision())
            .count();
            
        return (double) acceptedCount / papers.size();
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
     * @return the number of unsubmitted reviews, returns 0 if all reviews are submitted
     */
    public int countUnsubmittedReviews() {
        List<Review> reviews = getReviews();
        if (reviews.isEmpty()) {
            return 0;
        }
        
        long unsubmittedCount = reviews.stream()
            .filter(review -> !review.isSubmitted())
            .count();
            
        return (int) unsubmittedCount;
    }
    
    /**
     * Converts the reviewer's historical grades into a numerical average score
     * @return the average score between 0.0 and 1.0, or 0.0 if no reviews submitted
     */
    public double calculateHistoricalGradeAverage() {
        List<Review> submittedReviews = getReviews().stream()
            .filter(Review::isSubmitted)
            .toList();
            
        if (submittedReviews.isEmpty()) {
            return 0.0;
        }
        
        double totalScore = 0.0;
        for (Review review : submittedReviews) {
            if (review.getGrade() == Grade.ACCEPT) {
                totalScore += 1.0;
            }
            // Reject contributes 0.0 to the total score
        }
        
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
        super();
    }
    
    /**
     * Checks if all reviews for a paper are either exclusively Accept or exclusively Reject
     * @param paper the paper to check
     * @return true if all reviews are either all Accept or all Reject, false otherwise
     */
    public boolean areReviewsUnanimous(Paper paper) {
        List<Review> reviews = paper.getReviews();
        if (reviews.isEmpty()) {
            return false;
        }
        
        Grade firstGrade = reviews.get(0).getGrade();
        for (Review review : reviews) {
            if (review.getGrade() != firstGrade) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Makes a final decision for a paper
     * @param paper the paper to make decision for
     * @param decision the final decision (true for accept, false for reject)
     */
    public void makeFinalDecision(Paper paper, boolean decision) {
        paper.setDecision(decision);
    }
}

/**
 * Represents the possible grades for a review
 */
enum Grade {
    ACCEPT,
    REJECT
}

/**
 * Represents a paper that can be submitted and reviewed
 */
class Paper {
    private String title;
    private boolean isResearchPaper;
    private List<Review> reviews;
    private Boolean decision;
    private Author author;
    
    /**
     * Default constructor
     */
    public Paper() {
        this.reviews = new ArrayList<>();
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public boolean isResearchPaper() {
        return isResearchPaper;
    }
    
    public void setResearchPaper(boolean researchPaper) {
        isResearchPaper = researchPaper;
    }
    
    public List<Review> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    public Boolean getDecision() {
        return decision;
    }
    
    public void setDecision(Boolean decision) {
        this.decision = decision;
    }
    
    public Author getAuthor() {
        return author;
    }
    
    public void setAuthor(Author author) {
        this.author = author;
    }
    
    /**
     * Adds a review to this paper
     * @param review the review to add
     */
    public void addReview(Review review) {
        this.reviews.add(review);
    }
    
    /**
     * Checks if the paper has at least three reviews
     * @return true if the paper has at least three reviews, false otherwise
     */
    public boolean hasMinimumReviews() {
        return reviews.size() >= 3;
    }
}

/**
 * Represents a review of a paper
 */
class Review {
    private String feedback;
    private Grade grade;
    private boolean isSubmitted;
    private Reviewer reviewer;
    private Paper paper;
    
    /**
     * Default constructor
     */
    public Review() {
        this.isSubmitted = false;
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
    
    public boolean isSubmitted() {
        return isSubmitted;
    }
    
    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
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
    
    /**
     * Submits the review with the given feedback and grade
     * @param feedback the review feedback
     * @param grade the review grade
     */
    public void submitReview(String feedback, Grade grade) {
        this.feedback = feedback;
        this.grade = grade;
        this.isSubmitted = true;
    }
}