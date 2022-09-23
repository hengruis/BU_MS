// cooperate with Jiazheng Xiong
public abstract class Player {
    protected String playerName;
    //protected player
    protected double money;

    public String getPlayerName() {
        return this.playerName;
    }

    public double getMoney() {
        return this.money;
    }
    public abstract void setMoney(double n);
    public abstract void setPlayerName(String name);
}
