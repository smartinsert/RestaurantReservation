package main.java.domain;

import main.java.types.TableStatus;
import main.java.types.TableType;

public class Table {
    private TableType tableType;
    private TableStatus tableStatus;

    public Table(TableType tableType) {
        this.tableType = tableType;
        this.tableStatus = TableStatus.AVAILABLE;
    }

    public TableType getTableType() {
        return tableType;
    }

    public TableStatus getTableStatus() {
        return tableStatus;
    }

    public void updateTableStatus(TableStatus tableStatus) {
        this.tableStatus = tableStatus;
    }
}
