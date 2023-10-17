package utils.jobject2json;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JObject2JsonServiceTest {

    JObject2JsonService service = new JObject2JsonService();

    @Test
    void should_return_json_for_given_java_object() {
        // given
        String in = "DossierDto(idTiers='MBX4460',mode='R')";

        // when
        String out = service.jObject2Json(in);

        // then
        String expected = "{'idTiers':'MBX4460','mode':'R'}";

        Assertions.assertEquals(expected, out);


    }

}