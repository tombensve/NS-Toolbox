package se.natusoft.nvquery;

//import org.junit.Test;
import org.junit.jupiter.api.Test;
import se.natusoft.nvquery.api.DataQuery;
import se.natusoft.nvquery.data.providers.MapQueryData;
import se.natusoft.nvquery.rpn.RPNQuery;

public class RPNQueryTests {

    private final DataQuery query = new RPNQuery();

    private final MapQueryData mapQueryData = new MapQueryData(  )
            .add("name", "test data" )
            .add("version", "1.0")
            .add("int", "8" )
            .add("boolt", "true")
            .add("boolf", "false");

    //
    // Basic tests
    //

    @Test
    public void testEquals() {

        assert query.query( "name 'test_data' /=", mapQueryData );// Spaces must be '_'!
    }

    @Test
    public void testEqualsNumber() {

        assert query.query( "version 1.0 /=", mapQueryData );
    }

    @Test
    public void testNotEquals() {

        assert query.query( "int 7 /!=", mapQueryData );
        assert !query.query( "int 8 /!=", mapQueryData );
    }

    @Test
    public void testContains() {

        assert query.query( "name 'data' /()", mapQueryData );
    }

    @Test
    public void testNotContains() {

        assert query.query( "name 'Nisse' /!()", mapQueryData );
    }

    @Test
    public void testTrue() {

        assert query.query( "boolt /T", mapQueryData );
    }

    @Test
    public void testFalse() {

        assert query.query("boolf /F", mapQueryData);
    }

    @Test
    public  void testGreaterThan() {

        assert query.query( "int 5 />", mapQueryData );
    }

    @Test
    public void testLessThan() {

        assert query.query( "int 10 /<", mapQueryData );
    }

    @Test
    public  void testGreaterThanEquals() {

        assert query.query( "int 8 />=", mapQueryData );
    }

    @Test
    public void testLessThanEquals() {

        assert query.query( "int 8 /<=", mapQueryData );
    }

    //
    // Complex tests
    //
}
