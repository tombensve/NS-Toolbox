package se.natusoft.tools.modelish;

import org.junit.jupiter.api.Test;

public class ModelishTest {

    @Test
    public void testModelish() {

        TestModel testModel = Modelish.create( TestModel.class )
                .name("Tommy Svensson")
                .age(53)
                .address("Undisclosed").lock();

        assert testModel.name().equals( "Tommy Svensson" );
        assert testModel.age() == 53;
        assert testModel.address().equals( "Undisclosed" );

        try {
            testModel.name( "qwerty" );
            // This because shit can ALWAYS happen!
            throw new RuntimeException("This should not happen since model is locked.");
        }
        catch ( IllegalArgumentException iae ) {
            assert iae.getMessage().equals( "Object is locked!" );
        }
    }
}
