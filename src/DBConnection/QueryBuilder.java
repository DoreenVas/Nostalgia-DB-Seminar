package DBConnection;

class QueryBuilder {

    // the query builder
    private StringBuilder query;
    // indicates if a condition was just inserted
    private boolean conditionInserted;
    // indicates if a statement was just added
    private boolean statementAdded;

    /**
     *
     * @param fields wanted fields to get from tables
     * @param tables wanted tables to get information from
     */
    QueryBuilder(String[] fields, String[] tables) {
        // initialize builder
        this.query = new StringBuilder();
        addSelect(fields);
        addFrom(tables);
        // set condition and statement insertions as false
        this.conditionInserted = false;
        this.statementAdded = false;
    }

    /**
     *
     * @return The string representation of the query
     */
    String build() {
        return this.query.append(";").toString();
    }

    QueryBuilder addSelect(String[] fields) {
        // start select statement
        this.query.append("select ");
        // append all of the wanted fields
        for (int i = 0; i < fields.length - 1; i++) {
            this.query.append(fields[i]).append(", ");
        }
        this.query.append(fields[fields.length - 1]).append(" ");
        return this;
    }

    QueryBuilder addFrom(String[] tables) {
        // add from statement and append all wanted tables
        this.query.append("from ");
        for (int i = 0; i < tables.length - 1; i++) {
            this.query.append(tables[i]).append(", ");
        }
        this.query.append(tables[tables.length - 1]);
        return this;
    }

    /**
     *
     * @param command appends the given command to the query
     */
    private void checkCondition(String command) {
        // add command only if condition wasn't inserted
        if(!this.conditionInserted) {
            this.query.append(" ").append(command);
            // condition was just inserted
            this.conditionInserted = true;
            // indicates that a statement is needed
            this.statementAdded = false;
        }
    }

    /**
     *
     * @return the builder
     *
     * adds where to query
     */
    QueryBuilder addWhere() {
        checkCondition("where");
        return this;
    }

    /**
     *
     * @return the builder
     *
     * adds having to query
     */
    QueryBuilder addHaving() {
        checkCondition("having");
        return this;
    }

    /**
     * adds "and" between statements if needed
     */
    private void checkStatement() {
        // if a statement was already added then add a "and" between the statements
        if(this.statementAdded) {
            this.query.append(" and");
            // may add new conditional term
            this.conditionInserted = false;
        }
    }

    /**
     *
     * @param field the field to take values from
     * @param from start value
     * @param to end value
     * @param <T> the type to use for between
     * @return the builder
     */
    <T>QueryBuilder addBetweenStatements(String field, T from, T to) {
        checkStatement();
        this.query.append(" ").append(field).append(" between ").append(from).append(" and ").append(to);
        this.statementAdded = true;
        return this;
    }

    /**
     *
     * @param field the field to take values from
     * @param val the value to compare to
     * @param <T> the type to use for is
     * @return the builder
     */
    <T>QueryBuilder addIsStatements(String field, T val) {
        checkStatement();
        this.query.append(" ").append(field).append(" is ").append(val);
        this.statementAdded = true;
        return this;
    }

    /**
     *
     * @param field the field to take values from
     * @param val the value to compare to
     * @param <T> the type to use for like
     * @return the builder
     */
    <T>QueryBuilder addLikeStatements(String field, T val) {
        checkStatement();
        this.query.append(" ").append(field).append(" like ").append(val);
        this.statementAdded = true;
        return this;
    }

    /**
     *
     * @return the builder
     *
     * adds "or" to query
     */
    QueryBuilder addOr() {
        checkStatement();
        this.query.append(" or");
        this.statementAdded = true;
        return this;
    }

    /**
     *
     * @param leftStatement first statement
     * @param rightStatement second statement
     * @param <T> the type to use for between
     * @return the builder
     *
     * adds a full or statement
     */
    <T>QueryBuilder addOr(String leftStatement, String rightStatement) {
        checkStatement();
        this.query.append(" ").append(leftStatement).append(" or ").append(rightStatement);
        this.statementAdded = true;
        return this;
    }

    /**
     *
     * @param field the field to order by
     * @param ascOrDec ascending order if asc, else dec
     * @return the builder
     *
     * adds an order by statement
     */
    QueryBuilder addOrderBy(String field, String ascOrDec) {
        checkStatement();
        this.query.append(" order by ").append(field).append(" ").append(ascOrDec);
        this.statementAdded = true;
        return this;
    }

    /**
     *
     * @param field the field to check
     * @param value the value to compare to
     * @param <T> the type of the value
     * @return the builder
     *
     * adds a full equals statement
     */
    <T>QueryBuilder addEqualStatements(String field, T value) {
        checkStatement();
        this.query.append(" ").append(field).append("=").append(value);
        this.statementAdded = true;
        return this;
    }

    /**
     *
     * @param field the field to check
     * @param value the value to compare to
     * @param <T> the type of the value
     * @return the builder
     *
     * adds a full not equals statement
     */
    <T>QueryBuilder addNotEqualStatements(String field, T value) {
        checkStatement();
        this.query.append(" ").append(field).append("<>").append(value);
        this.statementAdded = true;
        return this;
    }

    /**
     *
     * @param field the field to check
     * @param value the value to compare to
     * @param <T> the type of the value
     * @return the builder
     *
     * adds a full bigger than statement
     */
    <T>QueryBuilder addBiggerThanStatements(String field, T value) {
        checkStatement();
        this.query.append(" ").append(field).append(">=").append(value);
        this.statementAdded = true;
        return this;
    }

    /**
     *
     * @param field the field to check
     * @param value the value to compare to
     * @param <T> the type of the value
     * @return the builder
     *
     * adds a full smaller than statement
     */
    <T>QueryBuilder addSmallerThanStatements(String field, T value) {
        checkStatement();
        this.query.append(" ").append(field).append("<=").append(value);
        this.statementAdded = true;
        return this;
    }

    /**
     *
     * @param firstTable first table to join
     * @param secondTable second table to join
     * @param field field to join by
     *
     * joins tables on values in field that are equal
     */
    private void joinAddOn(String firstTable, String secondTable, String field) {
        this.query.append(secondTable).append(" on ").append(firstTable);
        this.query.append(".").append(field).append(" = ").append(secondTable).append(".").append(field);
    }

    QueryBuilder addInnerJoinOnEqual(String firstTable, String secondTable, String field) {
        this.query.append(" inner join ");
        joinAddOn(firstTable, secondTable, field);
        return this;
    }

    QueryBuilder addOuterJoinOnEqual(String table, String firstTable, String secondTable, String field) {
        this.query.append(" outer join ");
        joinAddOn(firstTable, secondTable, field);
        return this;
    }

    QueryBuilder addLeftJoinOnEqual(String table, String firstTable, String secondTable, String field) {
        this.query.append(" left join ");
        joinAddOn(firstTable, secondTable, field);
        return this;
    }

    QueryBuilder addRightJoinOnEqual(String table, String firstTable, String secondTable, String field) {
        this.query.append(" right join ");
        joinAddOn(firstTable, secondTable, field);
        return this;
    }

    QueryBuilder addFullJoinOnEqual(String table, String firstTable, String secondTable, String field) {
        this.query.append(" full join ");
        joinAddOn(firstTable, secondTable, field);
        return this;
    }

    /**
     *
     * @param field field to group by
     * @return the builder
     *
     * adds a full group by statement
     */
    QueryBuilder addGroupBy(String field) {
        this.query.append(" group by ").append(field);
        return this;
    }

    String addCount(String queryStr) {
        this.query.delete(0, this.query.length());
        queryStr = queryStr.replace(";", "");
        this.query.append("select count(*) from (");
        this.query.append(queryStr).append(") as temp");
        return build();
    }
}