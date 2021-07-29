package se.natusoft.rpnquery;

public class RPNQuery {

    private QueryData queryData;

    public RPNQuery(QueryData queryData) {
        this.queryData = queryData;
    }

    public boolean booleanQuery(String query) {
        return (boolean) query(query);
    }

    public Object query(String query) {

    }

}
