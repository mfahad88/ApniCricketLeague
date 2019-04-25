package league.fantasy.psl.com.apnicricketleague.model.game;

public class PlayerBean {
    private int Id;
    private String Name;
    private String Price;
    private String Skills;
    private int isCaptain;
    private int isWCaption;



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public int getIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(int isCaptain) {
        this.isCaptain = isCaptain;
    }

    public int getIsWCaption() {
        return isWCaption;
    }

    public void setIsWCaption(int isWCaption) {
        this.isWCaption = isWCaption;
    }

    @Override
    public String toString() {
        return "PlayerBean{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Price='" + Price + '\'' +
                ", Skills='" + Skills + '\'' +
                ", isCaptain=" + isCaptain +
                ", isWCaption=" + isWCaption +
                '}';
    }
}
