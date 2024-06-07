public class Player {

    private String name;
    private int money;
    private int hp;

    public Player(String name) {
        this.name = name;
        money = 100000;
        hp = 10;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void loseMoney(int moneyLost) {
        money -= moneyLost;
    }

    public int getHp() {
        return hp;
    }

    public void addMoney() {
        money += 30;
    }

    public Boolean loseHp() {
        hp--;
        if (hp > 0) {
            return false;
        }
        return true;
    }
}
