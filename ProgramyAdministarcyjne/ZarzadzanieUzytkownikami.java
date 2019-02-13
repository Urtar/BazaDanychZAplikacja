package ProgramyAdministarcyjne;

import BazaDanychZAplikacja.User;

import java.util.Arrays;
import java.util.Scanner;

public class ZarzadzanieUzytkownikami {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(User.loadAllUsers()));
        System.out.println("Menu:");
        System.out.println("1 - add – dodanie użytkownika");
        System.out.println("2 - edit – edycja użytkownika");
        System.out.println("3 - delete – usunięcie użytkownika");
        System.out.println("4 - quit – zakończenie programu");
        optionMenuWithQuit();
    }

    private static void optionMenuWithQuit() {
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextInt()) {
            case 1:
                newUser();
                break;
            case 2:
                Scanner scannerUpdate = new Scanner(System.in);
                System.out.println("Podaj Id użytkownika?");
                Integer idToUpdate = 0;
                while(!scanner.hasNextInt()) {
                    System.out.println("Podaj prawidłowe dane");
                    scanner.next();
                }
                idToUpdate = scanner.nextInt();
                updateUser(idToUpdate);
                break;
            case 3:
                Scanner scannerDelete = new Scanner(System.in);
                System.out.println("Podaj Id użytkownika?");
                Integer idToDelete =0;
                while (!scanner.hasNextInt()) {
                    System.out.println("Podaj prawidlowe dane");
                    scanner.next();
                }
                    idToDelete = scanner.nextInt();
                    deleteUser(idToDelete);
                break;
            case 4:
                break;

        }
    }

    private static String getString(String searchedStringValue) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj " + searchedStringValue + " :");
        return scanner.nextLine();
    }

    private static void newUser() {
        User user = new User();
        user.setEmail(getString("email"));
        user.setPassword(getString("password"));
        user.setUsername(getString("User Name"));
        user.saveToDB();
    }

    private static void updateUser(Integer id) {
        User user = User.findUserById(id);
        user.setEmail(getString("email"));
        user.setPassword(getString("password"));
        user.setUsername(getString("User Name"));
        user.updateToDB();
    }

    private static void deleteUser(Integer id) {
        User user = User.findUserById(id);
        user.delete();
    }
}
