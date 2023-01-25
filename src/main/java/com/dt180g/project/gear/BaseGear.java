package com.dt180g.project.gear;

import com.dt180g.project.stats.BaseStat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Base class for the gear hierarchy, declares common interface for all gear.
 *
 * @author Samuel Thand
 */
public abstract class BaseGear {
    private final String type;
    private final String gearName;
    private final List<Class<?>> classRestrictions;

    /**
     * Base constructor, initializes members.
     *
     * @param type Type of BaseGear
     * @param gearName Name of the BaseGear
     * @param classRestrictions Classes allowed to use this BaseGear
     */
    protected BaseGear(final String type, final String gearName, final String classRestrictions) {
        this.type = type;
        this.gearName = gearName;
        this.classRestrictions = new ArrayList<>();
        addClassRestrictions(classRestrictions);
    }

    /**
     * Add classes to classRestrictions from a list of strings.
     * Takes the strings from the list and adds the corresponding classes to classRestrictions.
     *
     * @param restrictions List of restrictions
     */
    private void addClassRestrictions(final String restrictions) {
        List<String> restrictionsList = new ArrayList<>(Arrays.asList(restrictions.split(",")));
        String heroesPackage = "com.dt180g.project.characters.heroes";
        restrictionsList.forEach(restriction -> {
            try {
                this.classRestrictions.add(Class.forName(String.format("%s.%s", heroesPackage, restriction)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Get the type of the BaseGear.
     *
     * @return type member
     */
    public final String getType() {
        return type;
    }

    /**
     * Get the class restrictions for this BaseGear.
     *
     * @return classRestrictions member
     */
    public final List<Class<?>> getClassRestrictions() {
        return classRestrictions;
    }

    /**
     * Check if the BaseGear can be used by the specified class.
     *
     * @param classType class to check restrictions for
     * @return The BaseGear can be used by the specified class
     */
    public final boolean checkClassRestriction(final Class<?> classType) {
        return getClassRestrictions().contains(classType);
    }

    /**
     * Get the bonus stat for this BaseGear.
     *
     * @return The bonus BaseStat for this BaseGear.
     */
    public abstract BaseStat getStat();

    /**
     * Get the name of this BaseGear.
     *
     * @return gearName member
     */
    public String toString() {
        return gearName;
    }
}
