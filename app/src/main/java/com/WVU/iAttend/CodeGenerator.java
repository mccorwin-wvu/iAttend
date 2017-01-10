package com.WVU.iAttend;

/**
 * Created by Matt on 1/4/2017.
 */

import java.math.BigInteger;
import java.security.SecureRandom;

public final class CodeGenerator {

    private SecureRandom random = new SecureRandom();

    public String nextCode() {
        return new BigInteger(40, random).toString(32);

    }
}

