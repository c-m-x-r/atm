import java.util.ArrayList;
import java.util.Scanner;
/**
 * Write a description of class TestDriver here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestDriver
{
    public static void main (String args[])
    {
        String ownerName;
        double balance;
        int password;
        double interestRate;

        //second account or not
        boolean secondAccountAns = false;
        //a checkings account is represented by "true". savings by "false"
        boolean accountType = true;
        //active account is either account 1 or 2, defaults to false which represents account 1
        boolean activeAccount = false;

        Scanner userInput = new Scanner(System.in);

        ////////////////////////////////////////////////////

        System.out.println( "Welcome to the Bank!\nWhat is your last name?");
        ownerName = userInput.next();
        System.out.println("What is your first name?");
        ownerName = userInput.next() + " " +ownerName;

        System.out.println("Would you like to create 2 accounts? (yes/no)");
        if (userInput.next().equalsIgnoreCase("yes"))
        {
            System.out.println("2 accounts will be made for you.");
            secondAccountAns = true;
        }
        else
        {
            System.out.println("1 account has been made for you");
        }

        System.out.println("What type of account would you like to create? (Checkings or Savings)");
        String type = userInput.next();
        if(type.equalsIgnoreCase("Checkings"))
        {
            //true= checkings
            accountType = true;
            System.out.println("A checkings account has been made for you.");
        }
        else if (type.equalsIgnoreCase("Savings"))
        {
            //false = savings
            accountType = false;
            System.out.println("A savings account has been made for you.");
        }
        else
        {
            System.out.println("Invalid account type.\nA checkings account has been made for you, by default.");
        }

        System.out.println( "How much money would you like to deposit?");
        do
        {
            balance = userInput.nextDouble();

            if (balance < 0.00)
            {
                System.out.println("Unable to have a negative balance. Please try again.");
            }
        }
        while (balance < 0.00);

        System.out.println( "What is your password for this account?\nPlease enter a 4 digit integer.");
        do
        {
            password = userInput.nextInt();
            if (password < 999 || password > 10000)
            {
                System.out.println("Please enter a 4 digit integer. Try again.");
            }
        } 
        while (password < 999 || password > 10000);

        System.out.println( "How much interest would you like to recieve? \nEnter a percentage. (Interest is compounded yearly)");
        interestRate = userInput.nextDouble();
        if (interestRate < 0)
        {
            System.out.println("Do you really want negative interest?\nSuit yourself man");
            //this does not loop, just for fun/
        }

        // create first account here//////////////
        ATM account1 = new ATM(balance,password,ownerName,accountType,interestRate);
        //////////////////////////////////

        if (secondAccountAns == true)
        {

            if (accountType)
            {
                System.out.print("For your savings account, ");
                //change the next account type to savings
                accountType = false;
            }
            else
            {
                System.out.print("For your checkings account, ");
                //change the next account type to checkings
                accountType=true;
            }

            System.out.println( "How much money would you like to deposit?");
            do
            {
                balance = userInput.nextDouble();

                if (balance < 0.00)
                {
                    System.out.print("Unable to have a negative balance. Please try again.");
                }
            }
            while (balance < 0.00);

            System.out.println( "What is your password for this account?\nPlease enter a 4 digit integer.");
            do
            {
                password = userInput.nextInt();
                if (password < 999 || password > 10000)
                {
                    System.out.println("Please enter a 4 digit integer. Try again.");
                }
            } 
            while (password < 999 || password > 10000);

            System.out.println( "How much interest would you like to recieve? \nEnter a percentage. (Interest is compounded yearly)");
            interestRate = userInput.nextDouble();
            if (interestRate < 0)
            {
                System.out.println("Do you really want negative interest?\nSuit yourself loser");
                //this does not loop, just for fun
            }
        }
        //create second account/////
        ATM account2 = new ATM(balance,password,ownerName,accountType,interestRate);
        /////////////////////

        ////// confirm accounts
        ////////////////////////
        if (!secondAccountAns)
        {
            System.out.println("Welcome " + ownerName + ", your account at X Bank have been set up.\n ");
            account1.confirmation_menu();
            activeAccount = false;
        }
        else if (secondAccountAns)
        {
            System.out.println("Welcome " + ownerName + ", your accounts at X Bank have been set up.\n ");
            System.out.println("Account 1:");
            account1.confirmation_menu();
            System.out.println();
            System.out.println("Account 2:");
            account2.confirmation_menu();

            System.out.println("\nChoose an account to begin: (1 or 2)");
            int ans = 0;

            do
            {
                ans = userInput.nextInt();
            }
            //user answers 1 or 2
            while (!(ans ==1 || ans ==2));

            if (ans ==1)
            {
                activeAccount = false;
            }
            else
            {
                activeAccount = true;
            }

        }

        //////////////////////////////////////////
        ///Destroy stored passwords and values!!!////
        ////////////////////////////////////////
        password= -1;
        balance = -1.0;
        ownerName = null;
        accountType = false;
        //secondAccountAns = false;//// 
        //we will still need this one//
        ////////////////////////////////////////

        //track active account

        //false means account 0 true means account 2,
        boolean quit = false;
        //start doing things
        //input recieves the user selection from the object, which is used to perform cross-account operations
        int input;
        while (!quit)
        {
            if (!activeAccount) //first account is selected
            {
                //////////////////////
                account1.menu();//////
                //////////////////////
                
                input = userInput.nextInt();
                
                if (input == 10)
                {
                    break; //quit without triggering password check
                }
                
                //calls methods based on input vvv
                //////////////////////////////////////////////////////////
                account1.processSelection(input,secondAccountAns);///////
                ////////////////////////////////////////////////////////
                
                
                if ((input == 3 || input == 8 ) && secondAccountAns == false)
                {
                    //user is attemping to do a 2 account function when they cannot
                    System.out.println("You do not have a second account available.");
                }
                else if (input == 3) // means money is being transferred
                {
                    account2.recieve(account1.get_lastTransferAmount());
                }
                else if (input == 8) //means account is being switched
                {

                    System.out.println("Please enter the password for your other account.");
                    //this is the other account vvv
                    account2.passwordCheck(userInput.nextInt());
                    activeAccount = true;
                }
            }
            else  //second account is slected
            {   
                account2.menu();
                input = userInput.nextInt();
                account2.processSelection(input,secondAccountAns);
                //in the second account, there is no need to check for presence of 2 accounts because it would not exist otherwise.
                if ((input == 3 || input == 8 ) && secondAccountAns == false)
                {
                    //user is attemping to do a 2 account function when they cannot
                    System.out.println("You do not have a second account available.");
                }
                else if (input == 3) // means money is being transferred
                {
                    account1.recieve(account2.get_lastTransferAmount());
                }

                else if (input == 8) // if account is being switched
                {

                    System.out.println("Please enter the password for your other account.");
                    //this is the other account vvvv
                    account1.passwordCheck(userInput.nextInt());
                    activeAccount = false;
                }
            }
            if (input != 10)
            {

                System.out.println("Input ok to continue");
                userInput.next();
            }
            else
            {
                quit = true;
            }
        }
        System.out.println("Goodbye, see you!");
    }   
}