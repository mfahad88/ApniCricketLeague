package league.fantasy.psl.com.apnicricketleague.model;

public class PrizesBean {
    private int points;
    private String amount;
    private String qty;
    private float percent;
    private String type;

    public PrizesBean(int points, String amount, String qty, float percent, String type) {
        this.points = points;
        this.amount = amount;
        this.qty = qty;
        this.percent = percent;
        this.type = type;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PrizesBean{" +
                "points=" + points +
                ", amount='" + amount + '\'' +
                ", qty='" + qty + '\'' +
                ", percent=" + percent +
                ", type='" + type + '\'' +
                '}';
    }
}
