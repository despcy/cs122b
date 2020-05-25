package edu.uci.ics.fabflixmobile;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.fabflixmobile.pojo.User;
import edu.uci.ics.fabflixmobile.pojo.UserResp;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        String str="{\"message\":0,\"data\":{\"id\":\"961\",\"firstName\":\"a\",\"lastName\":\"a\",\"creditCard\":null,\"address\":\"a1\",\"email\":\"a@email.com\",\"password\":\"+Dg5yL93kwVHNktyWSV57YnkSJuMGTDmEHAA5G1072thImyCd8KNp9SmyWnixVTO\"}}";
//        ObjectMapper mapper = new ObjectMapper();
//        UserResp cur_user = new UserResp();
//        try {
//            cur_user = mapper.readValue(str, UserResp.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        User u = cur_user.getData();
        assertEquals(4, 2 + 2);
    }
}