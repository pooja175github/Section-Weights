package com.structure.weights;

public class DrawerItem {
	/* Commented tags are expected in future updates.
	 */




    public DrawerItem(String id, int icon, String title ) {
        this.icon1 = icon;
        this.title = title;
        this.id = id;
    }
    private String icon;
    private String title;
    private String id;
    private  int icon1;

    public int getIcon1() {
        return icon1;
    }

    public void setIcon1(int icon) {
        this.icon1 = icon;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getid() {
        return id;
    }

    public void setid(String icon) {
        this.id = icon;
    }
}
