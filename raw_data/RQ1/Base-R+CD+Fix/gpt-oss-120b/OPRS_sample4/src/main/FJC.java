import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a generic user of the review system.
 */
class User {

    private String name;
    private List<UserRole> roles;

    /** Unparameterized constructor */
    public User() {
        this.roles = new ArrayList<>();
    }

    /** Parameterized constructor for convenience */
    public User(String name) {
        this();
        this.name = name;
    }

    /** Adds a role (Author, Reviewer, CoChair) to this user. */
    public void addRole(UserRole role) {
        if (role != null && !roles.contains(role)) {
            roles.add(role);
        }
    }

    // ------------------- getters & setters -------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserRole> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles == null ? new ArrayList<>() : new ArrayList<>(roles);
    }
}

/**
 * Base class for all specific user roles.
 */
abstract class UserRole {
    // No fields – serves as a common super‑type.
}

/**
 * Role representing an author. Holds the papers submitted by the author.
 */
class Author extends UserRole {

    private final List<Paper> papers;

    /** Unparameterized constructor */
    public Author() {
        this.papers = new ArrayList<>();
    }

    /**
     * Submits a paper on behalf of the author.
     *
     * @param paper the paper to submit; must not be {@code null}
     */
    public void submitPaper(Paper paper) {
        Objects.requireNonNull(paper, "paper cannot be null");
        papers.add(paper);
        paper.setAuthor(this);
    }

    /**
     * Returns the list of papers submitted by this author.
     *
     * @return an unmodifiable view of the author's papers
     */
    public List<Paper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    /**
     * Counts the total number of papers submitted by this author.
     *
     * @return number of submitted papers
     */
    public int countPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of the author's papers.
     * Only papers that already have a final decision are considered.
     *
     * @return acceptance rate in the range [0.0, 1.0]; 0.0 if the author has no decided papers
     */
    public double acceptanceRate() {
        long decided = papers.stream()
                .filter(p -> p.getFinalDecision() != null)
                .count();
        if (decided == 0) {
            return 0.0;
        }
        long accepted = papers.stream()
                .filter(p -> Boolean.TRUE.equals(p.getFinalDecision()))
                .count();
        return (double) accepted / decided;
    }
}

/**
 * Role representing a reviewer. Holds the reviews assigned to the reviewer.
 */
class Reviewer extends UserRole {

    private final List<Review> assignedReviews;

    /** Unparameterized constructor */
    public Reviewer() {
        this.assignedReviews = new ArrayList<>();
    }

    /**
     * Assigns a review to this reviewer.
     *
     * @param review the review to assign; must not be {@code null}
     */
    public void assignReview(Review review) {
        Objects.requireNonNull(review, "review cannot be null");
        assignedReviews.add(review);
    }

    /**
     * Returns the list of reviews assigned to this reviewer.
     *
     * @return an unmodifiable view of the assigned reviews
     */
    public List<Review> getAssignedReviews() {
        return Collections.unmodifiableList(assignedReviews);
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     *
     * @return number of reviews that have not been submitted yet
     */
    public int countUnsubmittedReviews() {
        return (int) assignedReviews.stream()
                .filter(r -> !r.isSubmitted())
                .count();
    }

    /**
     * Computes the historical average score of this reviewer.
     * Accept = 1, Reject = 0.
     *
     * @return average score in the range [0.0, 1.0]; 0.0 if the reviewer has no submitted reviews
     */
    public double historicalAverageScore() {
        List<Review> submitted = new ArrayList<>();
        for (Review r : assignedReviews) {
            if (r.isSubmitted()) {
                submitted.add(r);
            }
        }
        if (submitted.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (Review r : submitted) {
            sum += (r.getGrade() == Review.Grade.ACCEPT) ? 1 : 0;
        }
        return (double) sum / submitted.size();
    }
}

/**
 * Role representing a co‑chair (program chair). Can make final decisions on papers.
 */
class CoChair extends UserRole {

    /**
     * Makes the final decision for a paper.
     *
     * @param paper   the paper to decide on; must not be {@code null}
     * @param accept  true if the paper is accepted, false if rejected
     */
    public void decidePaper(Paper paper, boolean accept) {
        Objects.requireNonNull(paper, "paper cannot be null");
        paper.setFinalDecision(accept);
    }
}

/**
 * Represents a paper submitted to the system.
 */
class Paper {

    private final String title;
    private final boolean researchPaper; // true = research, false = experience report
    private final List<Review> reviews;
    private Author author;
    private Boolean finalDecision; // null = not decided yet, true = accept, false = reject

    public Paper(String title, boolean researchPaper) {
        this.title = Objects.requireNonNull(title, "title cannot be null");
        this.researchPaper = researchPaper;
        this.reviews = new ArrayList<>();
        this.finalDecision = null;
    }

    public String getTitle() {
        return title;
    }

    public boolean isResearchPaper() {
        return researchPaper;
    }

    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    public void addReview(Review review) {
        Objects.requireNonNull(review, "review cannot be null");
        reviews.add(review);
    }

    public Author getAuthor() {
        return author;
    }

    void setAuthor(Author author) {
        this.author = author;
    }

    public Boolean getFinalDecision() {
        return finalDecision;
    }

    void setFinalDecision(Boolean decision) {
        this.finalDecision = decision;
    }

    /**
     * Checks whether all submitted reviews for this paper have the same grade
     * (all Accept or all Reject). Returns false if there are less than three
     * submitted reviews.
     *
     * @return true if uniform decision, false otherwise
     */
    public boolean allReviewsUniformDecision() {
        long submittedCount = reviews.stream().filter(Review::isSubmitted).count();
        if (submittedCount < 3) {
            return false;
        }
        // Determine the grade of the first submitted review
        Review.Grade firstGrade = null;
        for (Review r : reviews) {
            if (r.isSubmitted()) {
                firstGrade = r.getGrade();
                break;
            }
        }
        if (firstGrade == null) {
            return false;
        }
        // Verify all other submitted reviews have the same grade
        for (Review r : reviews) {
            if (r.isSubmitted() && r.getGrade() != firstGrade) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Represents a review written by a reviewer for a paper.
 */
class Review {

 enum Grade {
        ACCEPT, REJECT
    }

    private final Reviewer reviewer;
    private final Paper paper;
    private String feedback;
    private Grade grade;
    private boolean submitted;

    public Review(Reviewer reviewer, Paper paper) {
        this.reviewer = Objects.requireNonNull(reviewer, "reviewer cannot be null");
        this.paper = Objects.requireNonNull(paper, "paper cannot be null");
        this.submitted = false;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public Paper getPaper() {
        return paper;
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

    /**
     * Submits the review. Both feedback and grade must be provided before submission.
     *
     * @throws IllegalStateException if feedback or grade is missing
     */
    public void submit() {
        if (feedback == null || feedback.isBlank()) {
            throw new IllegalStateException("Feedback must be provided before submitting.");
        }
        if (grade == null) {
            throw new IllegalStateException("Grade must be provided before submitting.");
        }
        this.submitted = true;
    }
}

/**
 * Example entry point demonstrating the usage of the model.
 * This class is not required for the compilation of the library but
 * provides a simple sanity‑check when run.
 */
 class FJC {

    public static void main(String[] args) {
        // Create users
        User alice = new User("Alice");
        Author aliceAuthor = new Author();
        alice.addRole(aliceAuthor);

        User bob = new User("Bob");
        Reviewer bobReviewer = new Reviewer();
        bob.addRole(bobReviewer);

        User carol = new User("Carol");
        CoChair carolChair = new CoChair();
        carol.addRole(carolChair);

        // Alice submits a paper
        Paper paper1 = new Paper("Deep Learning Advances", true);
        aliceAuthor.submitPaper(paper1);

        // Assign three reviewers (for simplicity, same reviewer three times)
        Review rev1 = new Review(bobReviewer, paper1);
        Review rev2 = new Review(bobReviewer, paper1);
        Review rev3 = new Review(bobReviewer, paper1);
        paper1.addReview(rev1);
        paper1.addReview(rev2);
        paper1.addReview(rev3);
        bobReviewer.assignReview(rev1);
        bobReviewer.assignReview(rev2);
        bobReviewer.assignReview(rev3);

        // Bob submits reviews
        rev1.setFeedback("Good work.");
        rev1.setGrade(Review.Grade.ACCEPT);
        rev1.submit();

        rev2.setFeedback("Solid methodology.");
        rev2.setGrade(Review.Grade.ACCEPT);
        rev2.submit();

        rev3.setFeedback("Well written.");
        rev3.setGrade(Review.Grade.ACCEPT);
        rev3.submit();

        // Chair decides based on uniform reviews
        if (paper1.allReviewsUniformDecision()) {
            carolChair.decidePaper(paper1, true);
        }

        // Output some statistics
        System.out.println("Unsubmitted reviews for Bob: " + bobReviewer.countUnsubmittedReviews());
        System.out.println("Bob's historical average score: " + bobReviewer.historicalAverageScore());
        System.out.println("Alice's acceptance rate: " + aliceAuthor.acceptanceRate());
    }
}