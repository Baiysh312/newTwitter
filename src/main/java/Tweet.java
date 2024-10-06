import java.util.ArrayList;
import java.util.List;

public class Tweet {
    private String content;
    private User author;
    private List<String> comments;
    private int likes;

    public Tweet(String content, User author) {
        this.content = content;
        this.author = author;
        this.comments = new ArrayList<String>();
        this.likes = 0;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public void like() {
        likes++;
    }

    public int getLikes() {
        return likes;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public List<String> getComments() {
        return comments;
    }
}