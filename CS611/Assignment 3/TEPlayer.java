// cooperate with Jiazheng Xiong
public class TEPlayer extends Player{
    public Hand TEHand=new Hand();
    boolean bust=false;
    boolean fold=false;
    boolean stand=false;

    public void setPlayerName(String name){
        this.playerName=name;
    }
    public void setMoney(double n){
        this.money=n;
    }
    public void fold(){
        this.fold=true;
    }
    public void stand(){
        this.stand=true;
    }
    public void reset(){
        this.bust=false;
        this.fold=false;
        this.stand=false;
    }
    public Card showfirstcard(){
        return TEHand.handCards.get(0);
    }

    /**
     * print all cards for a TE player/banker
     */
    public void printcard(){
        int n= TEHand.handCards.size();
        for(int i=0;i<n;i++){
            System.out.println(TEHand.handCards.get(i).toString());
        }
    }
}
