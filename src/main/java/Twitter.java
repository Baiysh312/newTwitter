import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Scanner;
@Setter
@Getter
public class Twitter {
    private HashMap<String, User> users = new HashMap<>();
    private static User loggedInUser = null;
    Scanner scanner = new Scanner(System.in);
    public void registerUser() {

        System.out.println("Введите имя пользователя:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Пользователь с таким именем уже существует.");
        } else {
            users.put(username, new User(username, password));
            System.out.println("Регистрация прошла успешно.");
        }
    }

    public void loginUser() {
        System.out.println("Введите имя пользователя:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            loggedInUser = user;
            System.out.println("Успешный вход.");
        } else {
            System.out.println("Неверное имя пользователя или пароль.");
        }
    }

    public void createTweet() {
        if (loggedInUser == null) {
            System.out.println("Вы должны войти в систему, чтобы опубликовать твит.");
            return;
        }

        System.out.println("Введите текст твита:");
        String content = scanner.nextLine();
        Tweet tweet = new Tweet(content, loggedInUser);
        loggedInUser.addTweet(tweet);
        System.out.println("Твит опубликован.");
    }

    public void likeTweet() {
        if (loggedInUser == null) {
            System.out.println("Вы должны войти в систему, чтобы ставить лайки.");
            return;
        }

        System.out.println("Введите имя пользователя, чей твит хотите лайкнуть:");
        String username = scanner.nextLine();
        User user = users.get(username);

        if (user != null && !user.getTweets().isEmpty()) {
            System.out.println("Выберите номер твита для лайка:");
            for (int i = 0; i < user.getTweets().size(); i++) {
                System.out.println(i + ": " + user.getTweets().get(i).getContent());
            }
            int tweetIndex = Integer.parseInt(scanner.nextLine());
            Tweet tweet = user.getTweets().get(tweetIndex);
            tweet.like();
            System.out.println("Твит лайкнут. Текущие лайки: " + tweet.getLikes());
        } else {
            System.out.println("Твиты не найдены.");
        }
    }

    public void commentTweet() {
        if (loggedInUser == null) {
            System.out.println("Вы должны войти в систему, чтобы комментировать твиты.");
            return;
        }

        System.out.println("Введите имя пользователя, чей твит хотите прокомментировать:");
        String username = scanner.nextLine();
        User user = users.get(username);

        if (user != null && !user.getTweets().isEmpty()) {
            System.out.println("Выберите номер твита для комментария:");
            for (int i = 0; i < user.getTweets().size(); i++) {
                System.out.println(i + ": " + user.getTweets().get(i).getContent());
            }
            int tweetIndex = Integer.parseInt(scanner.nextLine());
            Tweet tweet = user.getTweets().get(tweetIndex);
            System.out.println("Введите комментарий:");
            String comment = scanner.nextLine();
            tweet.addComment(comment);
            System.out.println("Комментарий добавлен.");
        } else {
            System.out.println("Твиты не найдены.");
        }
    }

    public void followUser() {
        if (loggedInUser == null) {
            System.out.println("Вы должны войти в систему, чтобы подписаться.");
            return;
        }

        System.out.println("Введите имя пользователя для подписки:");
        String username = scanner.nextLine();
        User user = users.get(username);

        if (user != null) {
            loggedInUser.follow(user);
            System.out.println("Теперь вы подписаны на " + user.getUsername());
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    public void showTweets() {
        if (loggedInUser == null) {
            System.out.println("Вы должны войти в систему, чтобы просматривать твиты.");
            return;
        }

        for (User user : loggedInUser.getFollowing()) {
            System.out.println("Твиты пользователя: " + user.getUsername());
            for (Tweet tweet : user.getTweets()) {
                System.out.println(tweet.getContent() + " (Лайки: " + tweet.getLikes() + ")");
                System.out.println("Комментарии:");
                for (String comment : tweet.getComments()) {
                    System.out.println("- " + comment);
                }
            }
        }
    }

    public void logoutUser() {
        if (loggedInUser != null) {
            System.out.println("Вы вышли из системы.");
            loggedInUser = null;
        } else {
            System.out.println("Вы не вошли в систему.");
        }
    }
}
