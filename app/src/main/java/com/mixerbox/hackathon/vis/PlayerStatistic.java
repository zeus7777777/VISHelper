package com.mixerbox.hackathon.vis;

import java.util.ArrayList;

/**
 * Created by zeus on 2016/8/26.
 */
public class PlayerStatistic {

    int[] result;
    ArrayList<String> foul;

    public PlayerStatistic()
    {
        result = new int[18];
        for(int i=0;i<18;i++)
        {
            result[i] = 0;
        }

        foul = new ArrayList<>();
    }
}
