package test;

import algorithm.RC6;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class RC6Test {

    @Test
    void run() {
        RC6.test();
        Assert.assertEquals(RC6.text, RC6.decryptedText);
    }
}