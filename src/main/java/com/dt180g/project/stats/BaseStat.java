package com.dt180g.project.stats;

import com.dt180g.project.support.Constants;

/**
 * Base class for the stat hierarchy, declares common interface for all stats.
 *
 * @author Samuel Thand
 */
public abstract class BaseStat implements Constants {
        private final String statName;
        private final int baseValue;
        private int staticModifier = 0;
        private int dynamicModifier = 0;

        /**
         * Base constructor, initializes members.
         *
         * @param statName The name of this BaseStat
         * @param baseValue The base value of this BaseStat
         */
        protected BaseStat(final String statName, final int baseValue) {
                this.statName = statName;
                this.baseValue = baseValue;
        }

        /**
         * Get the name of this BaseStat.
         *
         * @return member statName
         */
        public final String getStatName() {
                return statName;
        }

        /**
         * Get the base value of this BaseStat. Can be overridden to provide a
         * decorated value.
         *
         * @return member baseValue
         */
        public int getBaseValue() {
                return baseValue;
        }

        /**
         * Get the base value + total modifier of this BaseStat.
         *
         * @return modified value of this BaseStat
         */
        public final int getModifiedValue() {
                return getBaseValue() + getTotalModifier();
        }

        /**
         * Get the total modifier for this BaseStat.
         *
         * @return total modifier for this BaseStat
         */
        public final int getTotalModifier() {
                return getStaticModifier() + dynamicModifier;
        }

        /**
         * Get the static modifier for this BaseStat.
         *
         * @return static modifier for this BaseStat
         */
        public final int getStaticModifier() {
                return staticModifier;
        }

        /**
         * Adjust the static modifier for this BaseStat.
         *
         * @param value positive or negative adjustment value
         */
        public final void adjustStaticModifier(final int value) {
                staticModifier = getStaticModifier() + value;
        }

        /**
         * Adjust the dynamic modifier for this BaseStat.
         *
         * @param value positive or negative adjustment value
         */
        public final void adjustDynamicModifier(final int value) {
                dynamicModifier += value;
        }

        /**
         * Reset the static modifier for this BaseStat.
         */
        public final void resetDynamicModifier() {
                dynamicModifier = 0;
        }

        /**
         * Get a formatted string of the stat name, modified value and
         * total modifier for this BaseStat.
         *
         * @return the stat name, modified value and total modifier
         */
        @Override
        public final String toString() {
                return String.format("%-20s %10s %s%4s",
                        ANSI_GREEN + getStatName(),
                        ANSI_CYAN + getModifiedValue(),
                        ANSI_YELLOW, "+" + getTotalModifier());
        }
}
