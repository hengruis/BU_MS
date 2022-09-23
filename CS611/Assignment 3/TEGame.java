// cooperate with Jiazheng Xiong
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class TEGame extends CardGame{
    private Deck TEDeck=new Deck();
    private TEPlayer[] TEPlayers;
    Scanner sc = new Scanner(System.in);
    //number of players
    int num=0;
    //the number corresponds to the banker right now
    int numBankerA=0;
    //current number of players who is not fold, stand or bust
    int Numstillplaying=0;
    //value of banker's hand
    int bankerV=0;
    boolean Natural31=false;
    boolean PlayerLost=false;

    /**
     * Initial TE game
     */

    public void initial(){
        System.out.println("Welcome to Trianta Ena. You will play against a computer dealer.");
        System.out.println("One of you will play as  Dealer. Others are players." );
        System.out.println("Enter the number of players, from 2 to 9.");
        num=sc.nextInt();
        while(num<2||num>9){
            System.out.println("Number of players should between 2 and 9. Enter again:");
            num=sc.nextInt();
        }
        Numstillplaying=num;
        //init player
        TEPlayers=new TEPlayer[num];
        System.out.println("Now it's time to initial your money!");
        System.out.println("Player plays as banker will be the last one to initial and at least 3" +
                " times of the amount of the richest player.");
        double max_money=0;
        for(int i=0;i<num-1;i++){
            TEPlayers[i]= new TEPlayer();
            System.out.println("Player "+(i+1)+" please enter your name: ");
            sc.nextLine();
            String name=sc.nextLine();
            TEPlayers[i].setPlayerName(name);
            System.out.println("Player "+(i+1)+" please enter the initial money you have: ");
            double money = sc.nextDouble();
            if(money>max_money) max_money = money;
            TEPlayers[i].setMoney(money);
        }
        TEPlayers[num-1]= new TEPlayer();
        System.out.println("Player "+num+" plays as banker.");
        System.out.println("Please enter your name: ");
        sc.nextLine();
        String name=sc.nextLine();
        TEPlayers[num-1].setPlayerName(name);
        System.out.println("Banker should have 3 times the money which is: "+3*max_money);
        System.out.println("Player "+num+" please enter the initial money you have: ");
        double money = sc.nextDouble();
        while(money<3*max_money){
            System.out.println("Initial money should be above "+3*max_money);
            System.out.println("Banker enters again: ");
            money = sc.nextDouble();
        }

        TEPlayers[num-1].setMoney(money);
        TEDeck=new Deck();
        TEDeck.initialDeck();
        TEDeck.shuffleDeck();
        numBankerA=num-1;
    }

    /**
     * main function to play TE game
     */
    public void play(){
        initial();
        round();
        while((!PlayerLost)&&Continue()){
            numBankerA=chooseBanker();
            if (numBankerA==-1) {
                return;
            }else{
                resetPara();
                round();
            }
        }

    }

    /**
     * round function for TE every round
     */
    public void round(){

        ArrangeCard();
        firstbet();
        ArrangeHiddenCard();
        checkNatural();
        while(Numstillplaying>1){
            action();
        }
        BankCheck();
        if((!Natural31)&&(bankerV!=-1)){
            PLayerCheck();
        }
        PlayerLost= ifLose();
    }

    /**
     * Arrange the first card in the round
     */
    public void ArrangeCard(){
        System.out.println("Dealer distributes cards.Players take turns to see the card.");
        TEDeck.initialDeck();
        TEDeck.shuffleDeck();
        for(int i=0;i<num;i++){
            System.out.println("Player "+TEPlayers[i].getPlayerName()+" come and see " +
                    "the card: ");
            Card CurCard=TEDeck.TEtakeCard();
            TEPlayers[i].TEHand.hit(CurCard);
            System.out.println(CurCard.toString());
            System.out.println("-------------------------");
            System.out.println();
            System.out.println("-------------------------");
        }
        System.out.println(TEPlayers[numBankerA].getPlayerName()+" is the banker. Banker's card" +
                " is" +
                " "+TEPlayers[numBankerA].showfirstcard().toString()+".");
    }

    /**
     * Ask for the bet at first
     */
    public void firstbet(){
        System.out.println("=========================");
        System.out.println("Time to bet!");
        System.out.println("=========================");
        for(int i=0;i<num;i++){
            if(i!=numBankerA) {
                System.out.println("Player " + TEPlayers[i].getPlayerName()+ ". Type 1.bet or 2" +
                        ".fold");
                int n=sc.nextInt();
                while(n<1||n>2){
                    System.out.println("Invalid input. Enter again:");
                    n=sc.nextInt();
                }
                if(n==1){
                    double money=TEPlayers[i].getMoney();
                    System.out.println("Your money in total: "+money);
                    System.out.println("Please enter your bet.");
                    double bet= sc.nextDouble();
                    while(bet<=0||bet>money){
                        System.out.println("Invalid input. Enter again:");
                        bet=sc.nextDouble();
                    }
                    TEPlayers[i].TEHand.setBet(bet);
                    TEPlayers[i].setMoney(money-bet);
                }else{
                    System.out.println("Player " + TEPlayers[i].getPlayerName()+ "choose to fold");
                    TEPlayers[i].fold();
                    Numstillplaying-=1;
                }
            }
        }
    }

    /**
     * Arrange 2 more cards for stand players and banker
     */
    public void ArrangeHiddenCard(){
        System.out.println("=========================");
        System.out.println("Stand players take turns to see 2 cards.");
        System.out.println("=========================");
        for(int i=0;i<num;i++){
            if(!TEPlayers[i].fold) {
                System.out.println("Player " + TEPlayers[i].getPlayerName()+ " get 2 cards");
                Card CurCard1=TEDeck.TEtakeCard();
                TEPlayers[i].TEHand.hit(CurCard1);
                Card CurCard2=TEDeck.TEtakeCard();
                TEPlayers[i].TEHand.hit(CurCard2);
                System.out.println(CurCard1.toString()+" and "+ CurCard2.toString());
                System.out.println("-------------------------");
                System.out.println();
                System.out.println("-------------------------");
            }
        }
    }

    /**
     * Check natural 31 for only stand players
     */
    public void checkNatural(){
        for(int i=0;i<num;i++){
            if(i!=numBankerA){
                TEPlayers[i].TEHand.calculateValue(31);
                int Val=TEPlayers[i].TEHand.getHandValues();
                if(Val==31){
                    System.out.println("************************************");
                    System.out.println("Player " +TEPlayers[i].getPlayerName()+" gets Natural 31! ");
                    System.out.println("Player " +TEPlayers[i].getPlayerName()+" wins! ");
                    double CurMoney=TEPlayers[i].getMoney();
                    double CurBet=TEPlayers[i].TEHand.getBet();
                    CurMoney=CurMoney+CurBet*2;
                    System.out.println("Player " + TEPlayers[i].getPlayerName()+ " gets money "+ CurBet);
                    TEPlayers[i].setMoney(CurMoney);
                    System.out.println("Player " + TEPlayers[i].getPlayerName()+ "'s total money: "+ CurMoney);
                    double CurBankMoney =TEPlayers[numBankerA].getMoney();
                    System.out.println("Banker " + TEPlayers[numBankerA].getPlayerName()+ " loses money "+ CurBet);
                    CurBankMoney-=CurBet;
                    TEPlayers[numBankerA].setMoney(CurBankMoney);
                    System.out.println("Banker " + TEPlayers[numBankerA].getPlayerName()+ "'s total money: "+ CurBankMoney);
                    System.out.println("************************************");
                    TEPlayers[i].fold();
                }
            }
        }
    }

    /**
     * Stand players take actions
     */
    public void action(){
        System.out.println("=========================");
        System.out.println("Stand players take actions!");
        System.out.println("=========================");
        for(int i=0;i<num;i++){
            boolean stillPlaying=!(TEPlayers[i].fold||TEPlayers[i].stand||TEPlayers[i].bust);
            if(stillPlaying&&(i!=numBankerA)) {
                System.out.println("Player " + TEPlayers[i].getPlayerName()+ "'s Cards: ");
                TEPlayers[i].printcard();
                System.out.println("Player " + TEPlayers[i].getPlayerName()+ ". Type 1.hit or 2" +
                        ".stand");
                int n=sc.nextInt();
                while(n<1||n>2){
                    System.out.println("Invalid input. Enter again:");
                    n=sc.nextInt();
                }
                if(n==1){
                    System.out.println("You choose to hit! Your card is ：");
                    Card CurCard=TEDeck.TEtakeCard();
                    System.out.println(CurCard.toString());
                    TEPlayers[i].TEHand.hit(CurCard);
                    TEPlayers[i].TEHand.calculateValue(31);
                    int CurValue=TEPlayers[i].TEHand.getHandValues();
                    if(CurValue>31){
                        System.out.println("Your hand is bust with value: "+CurValue);
                        TEPlayers[i].bust=true;
                        Numstillplaying-=1;
                    }
                }else{
                    System.out.println("Player " + TEPlayers[i].getPlayerName()+ " choose to stand");
                    TEPlayers[i].stand();
                    Numstillplaying--;
                }
            }
        }
    }

    /**
     * Check the hand of bank, hit if needed, natural 31 check for banker
     */
    public void BankCheck(){
        System.out.println("Banker's cards: ");
        TEPlayers[numBankerA].printcard();
        TEPlayers[numBankerA].TEHand.calculateValue(31);
        bankerV=TEPlayers[numBankerA].TEHand.getHandValues();
        System.out.println("--------------------");
        System.out.println("Banker's value: "+bankerV);
        if(bankerV<27){
            while(bankerV<27){
                Card CurCard=TEDeck.TEtakeCard();
                TEPlayers[numBankerA].TEHand.hit(CurCard);
                System.out.println("Hit Card is "+CurCard.toString());
                TEPlayers[numBankerA].TEHand.calculateValue(31);
                bankerV=TEPlayers[numBankerA].TEHand.getHandValues();
            }
            System.out.println("Banker's cards: ");
            TEPlayers[numBankerA].printcard();
            System.out.println("--------------------");
            System.out.println("Banker's value: "+bankerV);
            if(bankerV>31){
                System.out.println("Banker busts. Bank lost.");
                Draw();
                System.out.println("Money turned back.");
                bankerV=-1;
            }
        }else if(bankerV==31){
            System.out.println("************************************");
            System.out.println("Banker gets Natural 31! Banker wins");
            System.out.println("************************************");
            double totalBet=0;
            for (int i=0;i<num;i++){
                if(i!=numBankerA){
                    totalBet+=TEPlayers[i].TEHand.getBet();
                }
            }
            System.out.println("Banker wins money: "+totalBet);
            double money=TEPlayers[numBankerA].getMoney()+totalBet;
            TEPlayers[numBankerA].setMoney(money);
            System.out.println("Banker " + TEPlayers[numBankerA].getPlayerName()+ "'s total money: "+ money);
            Natural31=true;
        }
    }

    /**
     * When banker bust, give money back to players
     */
    public void Draw(){
        for(int i=0;i<num;i++){
            if(i!=numBankerA){
                double CurMoney=TEPlayers[i].getMoney();
                double CurBet=TEPlayers[i].TEHand.getBet();
                CurMoney=CurMoney+CurBet;
                TEPlayers[i].setMoney(CurMoney);
            }
        }
    }

    /**
     * Check players' hands
     */
    public void PLayerCheck(){
        for(int i=0;i<num;i++){
            if(TEPlayers[i].stand){
                System.out.println("Player " + TEPlayers[i].getPlayerName()+ "'s cards: ");
                TEPlayers[i].printcard();
                TEPlayers[i].TEHand.calculateValue(31);
                int playerV=TEPlayers[i].TEHand.getHandValues();
                System.out.println("--------------------");
                System.out.println("Player " + TEPlayers[i].getPlayerName()+ "'s value: "+playerV);
                if (playerV>bankerV){
                    System.out.println("Player " + TEPlayers[i].getPlayerName()+ " wins!");
                    double CurMoney=TEPlayers[i].getMoney();
                    double CurBet=TEPlayers[i].TEHand.getBet();
                    CurMoney=CurMoney+CurBet*2;
                    System.out.println("Player " + TEPlayers[i].getPlayerName()+ " gets money "+ CurBet);
                    TEPlayers[i].setMoney(CurMoney);
                    System.out.println("Player " + TEPlayers[i].getPlayerName()+ "'s total money: "+ CurMoney);
                    double CurBankMoney =TEPlayers[numBankerA].getMoney();
                    System.out.println("Banker " + TEPlayers[numBankerA].getPlayerName()+ " loses money "+ CurBet);
                    CurBankMoney-=CurBet;
                    TEPlayers[numBankerA].setMoney(CurBankMoney);
                    System.out.println("Banker " + TEPlayers[numBankerA].getPlayerName()+ "'s total money: "+ CurBankMoney);
                }else{
                    System.out.println("Banker wins against player " + TEPlayers[i].getPlayerName());
                    double CurBet=TEPlayers[i].TEHand.getBet();
                    double CurBankMoney =TEPlayers[numBankerA].getMoney();
                    System.out.println("Banker " + TEPlayers[numBankerA].getPlayerName()+ " wins money "+ CurBet);
                    CurBankMoney+=CurBet;
                    TEPlayers[numBankerA].setMoney(CurBankMoney);
                    System.out.println("Banker " + TEPlayers[numBankerA].getPlayerName()+ "'s total money: "+ CurBankMoney);
                }
            }
        }
    }

    /**
     *
     * @return if someone has 0 money return true
     */
    public boolean ifLose(){
        boolean Lose=false;
        for(int i=0;i<num;i++){
            double money=TEPlayers[i].getMoney();
            if(money<=0){
                System.out.println("Player " + TEPlayers[i].getPlayerName()+ " runs out of money");
                Lose=true;
            }
        }
        if(Lose){
            System.out.println("Game will be over.");
        }
        return Lose;
    }

    /**
     *
     * @return if wish to continue, return true
     */
    public boolean Continue(){
        System.out.println("Round is over. Would you like to cash out or continue?");
        System.out.println("Type 1.Continue  2.Cash Out");
        int Conti=sc.nextInt();
        if(Conti==1){
            return true;
        }else{
            System.out.println("You choose to cash out.");
            System.out.println("Game will be over.");
            return false;
        }
    }

    /**
     * reset parameters for next round
     */
    public void resetPara(){
        Numstillplaying=num;
        bankerV=0;
        Natural31=false;
        PlayerLost=false;
        for(int i=0;i<num;i++){
            TEPlayers[i].reset();
            TEPlayers[i].TEHand=new Hand();
        }
        TEDeck.initialDeck();
        TEDeck.shuffleDeck();
    }

    /**
     *
     * @return the number of banker next round
     */
    public int chooseBanker(){
        List<Integer> Numlist = new ArrayList<>();
        int mostMoneyNum=0;
        while(Numlist.size()<num){
            double MostMoney=0;
            for(int i=0;i<num;i++){
                double money=TEPlayers[i].getMoney();
                if((money>MostMoney)&&(!Numlist.contains(i))){
                    mostMoneyNum=i;
                    MostMoney=money;
                }
            }
            System.out.println("Player " + TEPlayers[mostMoneyNum].getPlayerName()+ " has the most money.");
            System.out.println("Would you like to become the banker?");
            System.out.println("Type 1 to Accept.");
            int Acc=sc.nextInt();
            if(Acc==1){
                return mostMoneyNum;
            }else{
                Numlist.add(mostMoneyNum);
            }
        }
        System.out.println("No one want to be the banker. Cash out.");
        System.out.println("Game will be over.");
        return -1;
    }
}
