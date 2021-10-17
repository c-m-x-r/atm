import java.util.ArrayList;
import java.util.Scanner;
/**
 * Write a description of class ATM here.
 *
 * @author (your name)marcus chong
 * @version (a version number or a date) jan 5
 */
public class ATM
{
    //user determined  
    private double balance;
    private String ownerName;
    private boolean accountType;
    private int password;
    private double interestRate;

    //generated
    private int accountNum;
    private boolean accessGranted;

    private ArrayList<String> transactionTypeLog = new ArrayList<String>();
    private ArrayList<Double> transactionValueLog = new ArrayList<Double>(); 
    private ArrayList<Double> transactionBalanceLog = new ArrayList<Double>(); 

    private Scanner atmInput = new Scanner(System.in);
    //storage for transfers 
    private double lastTransferAmount;

    ///custom constructor used by all objects
    public ATM( double gbalance , int gpassword, String gownerName, boolean gaccountType, double ginterestRate)
    {
        balance = gbalance;
        password = gpassword;
        ownerName = gownerName;
        accountType = gaccountType;
        interestRate = ginterestRate;

        accessGranted = false;
        accountNum = (int)(Math.random()*9000+1000);
    }

    ////<METHOFSDS

    //password check
    //check password (from method parameter)
    public boolean passwordCheck(int gpassword)
    {

        if (accessGranted == true)
        {
            return true;
        }

        int i = 3; //3 password tries
        do
        {
            if (gpassword == this.password)
            {
                accessGranted = true;
                return true;
            }

            System.out.println("Incorrect Password, you have " + (i) + " tries remaining.");
            gpassword = atmInput.nextInt();
            i--;

        }
        while(i>0);
        System.out.println("Password Check Failed");
        accessGranted = false;
        return false;
    }

    //check password with no input

    //Getters here//////////////////////////////////

    public double get_balance()
    {
        if (!accessGranted)
        {
            System.out.println("Please enter your password");  
            if(!passwordCheck(atmInput.nextInt()))
            {
                System.out.println("Access denied.");
                return -1;
            }
        }
        return this.balance;
    }

    public int get_accountNum()
    {
        if (!accessGranted)
        {
            System.out.println("Please enter your password");  
            if(!passwordCheck(atmInput.nextInt()))
            {
                System.out.println("Access denied.");
                return -1;
            }
        }
        return this.accountNum;
    }

    public String get_accountType()
    {
        if (!accessGranted)
        {
            System.out.println("Please enter your password");  
            if(!passwordCheck(atmInput.nextInt()))
            {
                System.out.println("Access denied.");
                return "";
            }
        }

        if (this.accountType)
        {
            return "Checking";
        }
        return "Savings";
    }

    public double get_lastTransferAmount()
    {
        if (!accessGranted)
        {
            System.out.println("Please enter your password");  
            if(!passwordCheck(atmInput.nextInt()))
            {
                System.out.println("Access denied.");
                return 0;
            }

        }
        return this.lastTransferAmount;

    }

    //the password should not have a getter

    //All Setters
    public void set_balance( double gbalance)
    {
        if (!accessGranted)
        {
            if (!passwordCheck(atmInput.nextInt()))
            {
                System.out.println("Access denied.");
                return; 
            }
        }  
        balance = gbalance;
        System.out.print("Your balance has been changed to " + balance);
    }

    public void set_password(int gpassword)
    {
        if (!accessGranted)
        {
            if (!passwordCheck(atmInput.nextInt()))
            {
                System.out.println("Access denied.");
                return; 
            }
        } 
        //make sure password is 4 digit
        do
        {

            if (gpassword < 999 || gpassword > 10000)
            {
                System.out.println("Please enter a 4 digit integer. Try again.");
                gpassword = atmInput.nextInt();
            }
        } 
        while (password < 999 || password > 10000);

        password = gpassword;
        System.out.println("Your password has been changed.");

    }

    //////////////////////////////
    //////Generate Menus///////////
    //////////////////////////////

    //confirmation menu
    public void confirmation_menu()
    {
        if (accountType)
        {
            System.out.println("Type: Savings" );
        }
        else
        {
            System.out.println("Type: Checkings");
        }
        System.out.println("Starting Balance: " + this.balance);
        System.out.println("Account Number: " + this.accountNum);

    }
    // general usage menu
    public void menu ()
    {
        int input= 0;
        if (accountType)
        {
            System.out.println("Savings " );
        }
        else
        {
            System.out.println("Checkings ");
        }
        System.out.println
        (   "Account #" + this.accountNum  + "\n"+
            "Options: \n1. Withdraw \n2. Deposit \n3. Transfer \n"+
            "4. Check Balance \n5. Check Account Number \n6. Transaction History \n"+
            "7. Change Password \n8. Switch Account \n9. Wait \n10.Quit");
    }

    //proccesses the selected number to do bank machine things with binary search 
    // some functions are used in combination with testdriver, like switching accounts and transferrinf money
    public void processSelection( int ginput, boolean secondAccountPresence)
    {
        //make sure number is between 1 and 9
        if (ginput < 0 || ginput >9)
        {
            do 
            {
                System.out.println("Invalid selection");
                ginput = atmInput.nextInt();
            }
            while (!(ginput > 0 && ginput <10));
        }
        //first operation checks password
        if (!this.accessGranted)
        {
            System.out.println("Please enter your password to proceed");
            if (!this.passwordCheck(atmInput.nextInt()))
            {
                return;
            }
        }
        //BINARY SEACH
        if (ginput  < 5)
        {//g is 1,2,3,4
            if (ginput < 3)
            {//g is 1 or 2
                if (ginput == 1)
                { //g is 1, withdraw
                    System.out.println("How much money would you like to withdraw?");
                    this.withdrawl(atmInput.nextDouble());
                }
                else
                {// g is 2, deposit
                    System.out.println("How much money would you like to deposit?");
                    this.deposit(atmInput.nextDouble());
                }
            }

            else
            {//g is 3 or 4
                if (ginput == 3 && secondAccountPresence == true) 
                {//g is 3,transdfer

                    System.out.println("How much money would you like to transfer to another account?");
                    lastTransferAmount = atmInput.nextDouble();
                    //testdriver will take this value to relay it to the other account
                    this.transfer(lastTransferAmount);
                }
                else if (ginput == 4)
                { // g is 4
                    //check balance
                    System.out.println("your current balance is $" + this.get_balance());
                    //or just use this.balance
                }
            }

        }
        else
        {
            //g is 5,6,7,8,9   
            if (ginput < 7)
            {//g is 5 or 6
                if (ginput == 5)
                {// g is 5 check account num
                    System.out.println("Your account number is " + this.get_accountNum() + ".");
                }
                else
                {// g is 6 check history
                    System.out.println("Your transaction history: ");
                    for (int i = 0 ; i < transactionTypeLog.size(); i++)
                    {
                        System.out.print("  ["+(i+1)+". " + transactionTypeLog.get(i) + ": $" + transactionValueLog.get(i) 
                            + ", Balance : " + transactionBalanceLog.get(i) +"]" );
                        if ((i+1)%3 == 0)
                        {
                            System.out.println("");
                        }
                    }
                    System.out.println();
                }
            }

            // g is 7,8 or 9 or 10
            else if (ginput < 9)
            {// g is 7 or 8

                if (ginput == 7)
                {//g is 7 change password
                    accessGranted = false;
                    System.out.println("Enter your old password");
                    passwordCheck(atmInput.nextInt());
                    System.out.println("Enter a 4 digit password that does not begin with 0");
                    set_password(atmInput.nextInt());
                    accessGranted = false;
                } 

                else if(ginput == 8 && secondAccountPresence == true)

                {//g is 8 switch acccounts
                    System.out.println("Switching accounts...");
                }
            }
            else if (ginput < 10)
            {//g is 9
                //wait for certain amount of years
                System.out.println("How many years would you like to wait?");
                int years;
                do
                {
                    years = atmInput.nextInt();
                }
                while(years < 0);
                set_balance(calculateInterest(years));
                System.out.println(" at an interest rate of" + this.interestRate + "%(compunded yearly), after " + years + " years ...");
            }
            else 
            {
                //g is 10 is quit, just returns 9 and stops the testriber from looping
                System.out.println("Goodbye!");
                return;
            }
        }
    }

    public double calculateInterest(int gyears)
    {  
        //calculate interest compounded yearly 
        return this.balance*Math.pow((1.0 + this.interestRate/100),gyears);
    }

    ///////////////////////////////////
    //////Numbered Menu Items//////
    ////////////////////////////

    // item 1: withdrawl

    public double withdrawl (double gwithdrawl)
    {
        //password
        if (accessGranted == false)
        {
            System.out.println("Please input your password");
            if (!this.passwordCheck(atmInput.nextInt()))
            {
                return -1.0; //if password fails 3 times it will return -1 for incorrect access
            }
        }
        //check for negative withdrawl values
        do
        {
            if (gwithdrawl < 0.00 || gwithdrawl > this.balance)
            {
                System.out.println("Unable to have a negative balance. Please try again.");
                //return -2.0;  it could return -2 for negative given amount but it loops instead
                Scanner userInput = new Scanner(System.in);
                gwithdrawl = userInput.nextDouble();
            }
            //if (gwithdrawl > this.balance)
            {
                //return -3.0; // could return -3 for not enough moneybut it loops instead
            }
        }
        while (gwithdrawl < 0.00 || gwithdrawl > this.balance);

        // remove MONEY

        this.balance = this.balance - gwithdrawl;

        //log transaction
        this.transactionTypeLog.add("Withdrawl");
        this.transactionValueLog.add(gwithdrawl);
        this.transactionBalanceLog.add(this.balance);

        //end
        System.out.println("You have successfully withdrawn $" + gwithdrawl + " from your account.\n" );
        return this.balance;
    }

    // item 2: deposit
    public double deposit (double gdeposit)
    {
        //password check
        if (accessGranted == false)
        {
            System.out.println("Please input your password");
            if (!this.passwordCheck(atmInput.nextInt()))
            {
                return -1.0; // return -1 for incorrect access
            }
        }

        //check for negative deposit values
        do
        {
            if (gdeposit < 0.00)
            {
                System.out.println("Unable to have a negative balance. Please try again.");
                //return -3.0; loop instead 
                Scanner userInput = new Scanner(System.in);
                gdeposit = userInput.nextDouble();
            }

        }
        while (gdeposit < 0.00);

        // ADD MONEY

        this.balance = this.balance + gdeposit;

        //log transaction
        this.transactionTypeLog.add("Deposit");
        this.transactionValueLog.add(gdeposit);
        this.transactionBalanceLog.add(this.balance);

        //end
        System.out.println("You have successfully deposited $" + gdeposit + " into your account.\n" );
        return this.balance;
    }

    //item 3 : transfer (and recieve)
    public void transfer(double gtransfer)
    {
        //password
        if (accessGranted == false)
        {
            System.out.println("Please input your password");
            if (!this.passwordCheck(atmInput.nextInt()))
            {
                return;
            }
        }
        //check for negative deposit values
        do
        {
            if (gtransfer < 0.00 || gtransfer > this.balance)
            {
                System.out.println("Unable to have a negative balance. Please try again.");
                Scanner userInput = new Scanner(System.in);
                gtransfer = userInput.nextDouble();
            }

        }
        while (gtransfer < 0.00 || gtransfer > this.balance);

        // deduct MONEY

        this.balance = this.balance - gtransfer;

        //log transaction
        this.transactionTypeLog.add("Send");
        this.transactionValueLog.add(gtransfer);
        this.transactionBalanceLog.add(this.balance);

        System.out.println ("$"+gtransfer + " has been deducted from your account and transferred");
    }
    //when one account transfers, the testdriver tells the other to recieve
    public void recieve (double gamount)
    {
        this.balance = balance + gamount ;

        this.transactionTypeLog.add("Recieved");
        this.transactionValueLog.add(gamount);
        this.transactionBalanceLog.add(this.balance);
    }
}
