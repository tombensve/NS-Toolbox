package se.natusoft.query.rpn;

import se.natusoft.query.BooleanQuery;
import se.natusoft.query.QueryData;

public class RPNQuery implements BooleanQuery {

    private QueryData queryData;

    public RPNQuery(QueryData queryData) {
        this.queryData = queryData;
    }

    public boolean query(String query) {
        //return (boolean) ...

        return false;
    }
}
