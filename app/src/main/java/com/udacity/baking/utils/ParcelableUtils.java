package com.udacity.baking.utils;

import android.os.Parcelable;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ParcelableUtils {

    public static <T> Parcelable[] toArray(List<T> list) {
        Parcelable[] parcelables = new Parcelable[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Parcelable parcelable = Parcels.wrap(list.get(i));
            parcelables[i] = parcelable;
        }

        return parcelables;
    }

    public static <T> List<T> fromArray(Parcelable[] parcelables) {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < parcelables.length; i++) {
            list.add((T) Parcels.unwrap(parcelables[i]));
        }

        return list;
    }
}
