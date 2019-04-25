package league.fantasy.psl.com.apnicricketleague.model.game;

public class PlayerBean {

    private String Name;
    private String Price;
    private String Skills;

    public PlayerBean(String name, String price, String skills) {
        Name = name;
        Price = price;
        Skills = skills;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSkills() {
        return Skills;
    }

    public void setSkills(String skills) {
        Skills = skills;
    }

    @Override
    public String toString() {
        return "PlayerBean{" +
                "Name='" + Name + '\'' +
                ", Price='" + Price + '\'' +
                ", Skills='" + Skills + '\'' +
                '}';
    }
}
