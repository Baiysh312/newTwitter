import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Tweet> tweets;
    private List<User> following;
    private List<User> followers;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.tweets = new ArrayList<Tweet>();
        this.following = new ArrayList<User>();
        this.followers = new ArrayList<User>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void follow(User user) {
        if (!following.contains(user)) {
            following.add(user);
            user.addFollower(this);
        }
    }

    public void addFollower(User user) {
        if (!followers.contains(user)) {
            followers.add(user);
        }
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getFollowing() {
        return following;
    }


    public String toStr(){
        StringBuilder userData= new StringBuilder(username+","+password+"\n");
        userData.append("\n");
        for (Tweet tweet:tweets) {
            userData.append("- ").append(tweet.getContent()).append("\n");
            for (String str:tweet.getComments()) {
                userData.append("_ ").append(str).append("\n");
            }
        }return userData.toString();
    }
}