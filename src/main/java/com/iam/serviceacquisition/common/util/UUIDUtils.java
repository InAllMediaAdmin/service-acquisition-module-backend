package com.iam.serviceacquisition.common.util;

import java.util.UUID;

public class UUIDUtils {

    public static String randomUUID (){
        UUID temp = UUID.randomUUID();
        return Long.toHexString(temp.getMostSignificantBits())
                + Long.toHexString(temp.getLeastSignificantBits());
    }
}
