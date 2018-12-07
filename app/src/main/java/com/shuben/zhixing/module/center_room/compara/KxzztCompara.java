package com.shuben.zhixing.module.center_room.compara;

import java.util.Comparator;

public class KxzztCompara implements Comparator<Float> {

    @Override
    public int compare(Float f1, Float f2) {

        return f2.compareTo(f1);
    }
}
