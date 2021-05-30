package nikhilgoyal.animalshelter.Model;

public class Data
{
    private String category;
    private String totaldays;
    private String state;
    private String currentdate;

    public Data()
    {

    }

    public Data(String category, String totaldays, String state, String currentdate)
    {
        this.category = category;
        this.totaldays = totaldays;
        this.state = state;
        this.currentdate = currentdate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTotaldays() {
        return totaldays;
    }

    public void setTotaldays(String totaldays) {
        this.totaldays = totaldays;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }
}
