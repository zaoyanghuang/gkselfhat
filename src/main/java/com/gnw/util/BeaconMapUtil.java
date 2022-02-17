package com.gnw.util;

import org.springframework.stereotype.Component;

@Component
public class BeaconMapUtil{
    public static native String pubkey(String gkName);
    public static native void init( String sName, String mapName, double[] gpsPoses, int[] gridPoses, String gkName);
    public static native float[] cali(float posX, float posY, float posZ, double gpsX, double gpsY, double gpsZ, int gpsNum, float gpsDop,
                                      int[] bsValueList, double time, boolean tagReset, String pubval, String gkName);
    public native void transinit(double[] gpsPoses, int[] gridPoses);
    public native float[] transxyz( double[] gpsPoses);
    public native double[] translatlon( int[] gridPoses);

}
