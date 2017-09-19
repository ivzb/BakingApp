package com.udacity.baking.utils;

public class BoundsUtils {

    public static boolean isInBounds(int position, int size) {
        return position >= 0 && position < size;
    }
}
