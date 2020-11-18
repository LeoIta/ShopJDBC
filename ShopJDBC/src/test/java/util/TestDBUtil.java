package util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestDBUtil {
    @Test()
    @DisplayName("connection")
    public void checkNewConnection(){
        Assertions.assertNotNull(DBUtil.newConnection());
    }
}