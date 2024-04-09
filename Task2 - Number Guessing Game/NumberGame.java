import java.util.*;
public class NumberGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        System.out.println("Welcome to the Number Guessing Game! \nPlease enter your Name: ");
        String name = scanner.nextLine();
        int score = 0;
        System.out.println(name+", Welcome to the Number Guessing Game! \nHere's how the points are awarded for each attempt:\n1st Attempt: 100 points\n2nd Attempt: 80 points\n3rd Attempt: 60 points\n4th Attempt: 40 points\n5th Attempt: 20 points\n\nGood luck and enjoy the game!");
        boolean playAgain = true;
        while (playAgain)
        {
            boolean guess= false;
            int generatedNumber = random.nextInt(100) +1;
            System.out.println("You have 5 tries to guess the number correctly.");
            for (int i = 5,attempts=1; i >= 1 ; i--,attempts++) {
                System.out.println("Attempt : "+(attempts));
                // System.out.println("the number was: "+generatedNumber);
                System.out.print("Guess a number between 1 and 100: ");
                int guessedNumber;
                while (true) {
                    try {
                        guessedNumber = scanner.nextInt();
                        if (guessedNumber < 1 || guessedNumber > 100) {
                            System.out.print("Invalid input. Please enter a number between 1 and 100: ");
                        } else {
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.print("Invalid input. Please enter a valid number between 1 and 100: ");
                        scanner.next(); 
                    }
                }
                
                if(guessedNumber == generatedNumber) {
                    System.out.println(
                            "Congratulations "+name+" ! You have guessed the number correctly in " + attempts+ " attempts");
                    score+=i*20;
                    guess = true;
                    break;
                } 
                else if (guessedNumber < generatedNumber) {
                    System.out.println("Your guess is lower than the generated number. Try again!");
                } 
                else {
                    System.out.println("Your guess is higher than the generated number. Try again!");
                }
            }
            if(!guess) {
            System.out.println("You ran out of tries. \nThe number was "+generatedNumber);
            }
            
            System.out.println("Your current score: " + score);
            System.out.print(name + ", would you like to play again? (Enter 'play' to play again, or 'exit' to quit): ");
            String playChoice;
            while (true) {
                playChoice = scanner.next();
                if (playChoice.equalsIgnoreCase("play") || playChoice.equalsIgnoreCase("exit")) {
                    break;
                } else {
                    System.out.print("Invalid input. Please enter 'play' to play again, or 'exit' to quit: ");
                }
            }
            playAgain = playChoice.equalsIgnoreCase("play");

        }
        System.out.println("Thank you, " + name + ", for playing! Your final score is: " + score);
        scanner.close();
    }

}
