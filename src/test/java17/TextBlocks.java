package java17;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Text blocks testing")
public class TextBlocks {


    @Test
    void work_with_text_blocks() {
        String jsonTextBlock = """
                {
                    "firstname": "Paul",
                    "lastname": "Madison",
                    "age": 31
                }
                """;

        System.out.println(jsonTextBlock);

    }
}
