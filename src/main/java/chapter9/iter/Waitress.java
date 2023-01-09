package main.java.chapter9.iter;

import java.util.Iterator;

public class Waitress {
    Menu dinnerMenu;

    public Waitress(Menu dinnerMenu) {
        this.dinnerMenu = dinnerMenu;
    }

    public void printMenu() {
        printMenu(dinnerMenu.createIterator());
    }

    public void printMenu(Iterator iterator) {
        while (iterator.hasNext()) {
            MenuItem menuItem = (MenuItem) iterator.next();
            System.out.println(menuItem.getName());
        }
    }
}
