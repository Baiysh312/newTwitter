import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Twitter twitter = new Twitter();
        Scanner scanner = new Scanner(System.in);
        twitter.recordingFromFile();
        int command;
        while (true) {
            System.out.println("Введите команду \n 1-регистрация\n 2-вход\n 3-добавить твит\n 4-поставить лайк\n" +
                    " 5-коментировать\n 6-подписаться\n 7-просмотр твитов\n 8-выход\n 9-покинуть твит");
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    twitter.registerUser();
                    break;
                case 2:
                    twitter.loginUser();
                    break;
                case 3:
                    twitter.createTweet();
                    break;
                case 4:
                    twitter.likeTweet();
                    break;
                case 5:
                    twitter.commentTweet();
                    break;
                case 6:
                    twitter.followUser();
                    break;
                case 7:
                    twitter.showTweets();
                    break;
                case 8:
                    twitter.logoutUser();
                    break;
                case 9:
                    System.out.println("Выход из приложения.");
                    twitter.saveUsTweet();
                    return;
                default:
                    System.out.println("Неизвестная команда.");
            }
        }
    }
}