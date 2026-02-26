import java.util.*;

/**
 * Represents a user in the review system.
 */
class User {
    private String name;

    public User() {
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an author in the review system.
 */
class Author extends User {
    private List<Paper> papers;

    public Author() {
        super();
        this.papers = new ArrayList<>();
    }

    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    public void addPaper(Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by this author.
     *
     * @return the total number of papers submitted
     */
    public int countPapers() {
        return this.papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author.
     * Acceptance rate = (number of accepted papers) / (total number of papers)
     *
     * @return the acceptance rate as a double between 0.0 and 1.0
     */
    public double calculateAcceptanceRate() {
        if (papers.isEmpty()) {
            return 0.0;
        }

        int acceptedCount = 0;
        for (Paper paper : papers) {
            if (paper.getDecision() == Decision.ACCEPT) {
                acceptedCount++;
            }
        }

        return (double) acceptedCount / papers.size();
    }
}

/**
 * Represents a reviewer in the review system.
 */
class Reviewer extends User {
    private List<Review> assignedReviews;

    public Reviewer() {
        super();
        this.assignedReviews = new ArrayList<>();
    }

    public List<Review> getAssignedReviews() {
        return assignedReviews;
    }

    public void setAssignedReviews(List<Review> assignedReviews) {
        this.assignedReviews = assignedReviews;
    }

    public void addReview(Review review) {
        this.assignedReviews.add(review);
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * Returns 0 if all reviews are submitted.
     *
     * @return the number of unsubmitted reviews
     */
    public int countUnsubmittedReviews() {
        int count = 0;
        for (Review review : assignedReviews) {
            if (!review.isSubmitted()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Converts this reviewer's historical grades into a numerical average score.
     * Accept = 1, Reject = 0
     *
     * @return the average score between 0.0 and 1.0
     */
    public double calculateAverageGrade() {
        if (assignedReviews.isEmpty()) {
            return 0.0;
        }

        int totalScore = 0;
        int submittedCount = 0;

        for (Review review : assignedReviews) {
            if (review.isSubmitted()) {
                if (review.getGrade() == Grade.ACCEPT) {
                    totalScore += 1;
                }
                submittedCount++;
            }
        }

        if (submittedCount == 0) {
            return 0.0;
        }

        return (double) totalScore / submittedCount;
    }
}

/**
 * Represents a co-chair in the review system.
 */
class CoChair extends User {
    public CoChair() {
        super();
    }
}

/**
 * Enumeration representing the type of paper.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE_REPORT
}

/**
 * Enumeration representing the decision for a paper.
 */
enum Decision {
    ACCEPT,
    REJECT,
    PENDING
}

/**
 * Represents a paper in the review system.
 */
class Paper {
    private String title;
    private PaperType type;
    private List<Author> authors;
    private List<Review> reviews;
    private Decision decision;

    public Paper() {
        this.title = "";
        this.type = PaperType.RESEARCH;
        this.authors = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.decision = Decision.PENDING;
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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    /**
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject.
     * Only considers submitted reviews.
     *
     * @return true if all submitted reviews have the same grade, false otherwise
     */
    public boolean areAllReviewsConsistent() {
        List<Review> submittedReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.isSubmitted()) {
                submittedReviews.add(review);
            }
        }

        if (submittedReviews.isEmpty()) {
            return false;
        }

        Grade firstGrade = submittedReviews.get(0).getGrade();
        for (Review review : submittedReviews) {
            if (review.getGrade() != firstGrade) {
                return false;
            }
        }

        return true;
    }
}

/**
 * Enumeration representing the grade of a review.
 */
enum Grade {
    ACCEPT,
    REJECT
}

/**
 * Represents a review in the review system.
 */
class Review {
    private Reviewer reviewer;
    private Paper paper;
    private String feedback;
    private Grade grade;
    private boolean submitted;

    public Review() {
        this.feedback = "";
        this.grade = Grade.REJECT;
        this.submitted = false;
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
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }
}