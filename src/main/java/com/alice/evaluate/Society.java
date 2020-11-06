/**
 * 
 */
package com.alice.evaluate;

/**
 * @author dbutnaru
 *
 */
import java.util.*;

public class Society {

    private String name;

    public static String Advisors = "advisors";
    public static String President = "president";

    private List<Inventor> members = new ArrayList<Inventor>();
    private Map<String, Inventor> officers = new HashMap<>();

    public List<Inventor> getMembers() {
        return members;
    }

    public Map<String, Inventor> getOfficers() {
        return officers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMember(String name) {
        boolean found = false;
        for (Inventor inventor : members) {
            if (inventor.getName().equals(name)) {
                found = true;
                break;
            }
        }
        return found;
    }

}
