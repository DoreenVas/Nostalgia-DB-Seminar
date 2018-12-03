package DBConnection;

public class QueryBuilder {

    private StringBuilder query;
    private boolean conditionInserted;
    private boolean statementAdded;

    public QueryBuilder(String[] fields, String[] tables) {
        this.query = new StringBuilder();
        this.query.append("select ");
        for (int i = 0; i < fields.length - 1; i++) {
            this.query.append(fields[i]).append(", ");
        }
        this.query.append(fields[fields.length - 1]).append(" ");
        this.query.append("from ");
        for (int i = 0; i < tables.length - 1; i++) {
            this.query.append(tables[i]).append(", ");
        }
        this.query.append(tables[tables.length - 1]);

        this.conditionInserted = false;
        this.statementAdded = false;
    }

    public String build() {
        return this.query.append(";").toString();
    }

    private void checkCondition(String command) {
        if(!this.conditionInserted) {
            this.query.append(" ").append(command);
            this.conditionInserted = true;
            this.statementAdded = false;
        }
    }

    public QueryBuilder addWhere() {
        checkCondition("where");
        return this;
    }

    public QueryBuilder addHaving() {
        checkCondition("having");
        return this;
    }

    private void checkStatement() {
        if(this.statementAdded) {
            this.query.append(" and");
            // may add new conditional term
            this.conditionInserted = false;
        }
    }

    public <T>QueryBuilder addBetweenStatements(String field, T from, T to) {
        checkStatement();
        this.query.append(" ").append(field).append(" between ").append(from).append(" and ").append(to);
        this.statementAdded = true;
        return this;
    }

    public <T>QueryBuilder addIsStatements(String field, T val) {
        checkStatement();
        this.query.append(" ").append(field).append(" is ").append(val);
        this.statementAdded = true;
        return this;
    }

    public <T>QueryBuilder addLikeStatements(String field, T val) {
        checkStatement();
        this.query.append(" ").append(field).append(" like ").append(val);
        this.statementAdded = true;
        return this;
    }

    public <T>QueryBuilder addOr() {
        checkStatement();
        this.query.append(" or");
        this.statementAdded = true;
        return this;
    }

    public <T>QueryBuilder addOr(String leftStatement, String rightStatement) {
        checkStatement();
        this.query.append(" ").append(leftStatement).append(" or ").append(rightStatement);
        this.statementAdded = true;
        return this;
    }

    public <T>QueryBuilder addOrderBy(String field, String ascOrDec) {
        checkStatement();
        this.query.append(" order by ").append(field).append(" ").append(ascOrDec);
        this.statementAdded = true;
        return this;
    }

    public <T>QueryBuilder addEqualStatements(String field, T value) {
        checkStatement();
        this.query.append(" ").append(field).append("=").append(value);
        this.statementAdded = true;
        return this;
    }

    public <T>QueryBuilder addNotEqualStatements(String field, T value) {
        checkStatement();
        this.query.append(" ").append(field).append("<>").append(value);
        this.statementAdded = true;
        return this;
    }

    public <T>QueryBuilder addBiggerThanStatements(String field, T value) {
        checkStatement();
        this.query.append(" ").append(field).append(">=").append(value);
        this.statementAdded = true;
        return this;
    }

    public <T>QueryBuilder addSmallerThanStatements(String field, T value) {
        checkStatement();
        this.query.append(" ").append(field).append("<=").append(value);
        this.statementAdded = true;
        return this;
    }

    private void joinAddOn(String firstTable, String secondTable, String field) {
        this.query.append(secondTable).append(" on ").append(firstTable);
        this.query.append(".").append(field).append(" = ").append(secondTable).append(".").append(field);
    }

    public QueryBuilder addInnerJoinOnEqual(String firstTable, String secondTable, String field) {
        this.query.append(" inner join ");
        joinAddOn(firstTable, secondTable, field);
        return this;
    }

    public QueryBuilder addOuterJoinOnEqual(String table, String firstTable, String secondTable, String field) {
        this.query.append(" outer join ");
        joinAddOn(firstTable, secondTable, field);
        return this;
    }

    public QueryBuilder addLeftJoinOnEqual(String table, String firstTable, String secondTable, String field) {
        this.query.append(" left join ");
        joinAddOn(firstTable, secondTable, field);
        return this;
    }

    public QueryBuilder addRightJoinOnEqual(String table, String firstTable, String secondTable, String field) {
        this.query.append(" right join ");
        joinAddOn(firstTable, secondTable, field);
        return this;
    }

    public QueryBuilder addFullJoinOnEqual(String table, String firstTable, String secondTable, String field) {
        this.query.append(" full join ");
        joinAddOn(firstTable, secondTable, field);
        return this;
    }

    public QueryBuilder addGroupBy(String field) {
        this.query.append(" group by ").append(field);
        return this;
    }
}
