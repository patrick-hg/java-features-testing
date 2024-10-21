package java11;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Var features examples")
class VarFeatureTest {

    @Test
    public void should_print_var_values_into_a_string() {

        var model = "3 series E46";
        var year = 2002;
        var engine = "M52b20";
        var engineCapacity = 2.2;
        var horsePower = 170;

        String text = String.format("%s %s %s %s %s", model, year, engine, engineCapacity, horsePower);

        assert "3 series E46 2002 M52b20 2.2 170".equals(text);

    }
}
