import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
@Setter
@Getter
public class Twitter {
    private List<User> users = new ArrayList<>();
    private static User loggedInUser = null;
    Scanner scanner = new Scanner(System.in);

    private String fileName="C:\\Users\\Lenovo\\IdeaProjects\\Spring\\src\\main\\resources\\twitter.txt";
    public void registerUser() {

        System.out.println("Введите имя пользователя:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        if (users.stream()
                .anyMatch(user -> user.getUsername().equals(username))) {
            System.out.println("Пользователь с таким именем уже существует.");
        }
        else {
            users.add(new User(username, password));
            System.out.println("Регистрация прошла успешно.");
        }
    }

    public void loginUser() {
        System.out.println("Введите имя пользователя:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        User user = users.stream()
                .filter(us -> us.getUsername()
                        .equals(username) && us.getPassword()
                        .equals(password)).findFirst().orElse(null);
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
        User user = users.stream()
                .filter(us -> us.getUsername().equals(username))
                .findFirst()
                .orElse(null);

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
        User user = users.stream()
                .filter(us -> us.getUsername().equals(username))
                .findFirst()
                .orElse(null);
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
        User user= users.stream()
                .filter(us -> us.getUsername().equals(username))
                .findFirst()
                .orElse(null);

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

        Thread tweetThread = new Thread(() -> {
            for (User user : loggedInUser.getFollowing()) {
                System.out.println("Твиты пользователя: " + user.getUsername());
                for (Tweet tweet : user.getTweets()) {
                    System.out.println(tweet.getContent() + " (Лайки: " + tweet.getLikes() + ")");
                    System.out.println("Комментарии:");
                    for (String comment : tweet.getComments()) {
                        System.out.println("- " + comment);
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        tweetThread.start();
    }
    public void logoutUser() {
        if (loggedInUser != null) {
            System.out.println("Вы вышли из системы.");
            loggedInUser = null;
        } else {
            System.out.println("Вы не вошли в систему.");
        }
    }

    public static void saveUserWithTweetsToFile(User user, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(user.toStr());
            writer.newLine();
            System.out.println("Пользователь и твиты успешно сохранены в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении пользователя и твитов: " + e.getMessage());
        }
    }
    public void saveUsTweet(){
        for (User user : users) {
            saveUserWithTweetsToFile(user,fileName);
        }
    }
    public void recordingFromFile(){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String username;
            String password;
            User user = null;
            Tweet tweet=null;

            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("-")) {
                    username = line.split(",")[0].trim();
                    password = line.split(",")[0].trim();
                    user = new User(username,password);
                    users.add(user);
                } else if (line.startsWith("- ")) {
                    tweet= new Tweet(line,user);
                    user.getTweets().add(tweet);
                } else if (line.startsWith("_")) {
                    if (tweet!= null) {
                        tweet.addComment(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
