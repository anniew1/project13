public class Player {

    private String name;
    private int money;
    private int hp;

    public Player(String name) {
        this.name = name;
        money = 500;
        hp = 1;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public int getHp() {
        return hp;
    }

    public void addMoney() {
        money += 30;
    }

    public void loseHp() {
        hp--;
    }
}
