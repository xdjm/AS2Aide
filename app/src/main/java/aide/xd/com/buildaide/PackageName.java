package aide.xd.com.buildaide;

import java.io.Serializable;

/**
 * @author Administrator
 */
public class PackageName implements Serializable {
    private static final long serialVersionUID = 1L;
    private String com;
    private String myCompany;
    private String myApp;
    private String name;

    public PackageName(String com, String myCompany, String myApp, String name) {
        this.com = com;
        this.myCompany = myCompany;
        this.myApp = myApp;
        this.name = name;
    }

    public String getFullName() {
        return this.myApp + "." + this.myCompany + "." + this.com + "." + this.name.toLowerCase();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDName() {
        return name;
    }

    public String getName() {
        return name.toLowerCase();
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getCom() {
        return com;
    }

    public String getMyCompany() {
        return myCompany;
    }

    public String getMyApp() {
        return myApp;
    }
}