package com.shuben.contact.lib;


import com.shuben.contact.lib.bean.Type;

import java.util.Comparator;


public class LetterComparator implements Comparator<Type> {

    @Override
    public int compare(Type l, Type r) {
        if (l == null || r == null) {
            return 0;
        }

        String lhsSortLetters = l.getPys().substring(0, 1).toUpperCase();
        String rhsSortLetters = r.getPys().substring(0, 1).toUpperCase();
        if (lhsSortLetters == null || rhsSortLetters == null) {
            return 0;
        }
        return lhsSortLetters.compareTo(rhsSortLetters);
    }
}