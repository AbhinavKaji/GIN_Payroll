module com.mycompany.gin_payroll {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    requires java.desktop;
    requires java.logging;
    requires java.base;

    opens com.mycompany.gin_payroll to javafx.fxml;
    exports com.mycompany.gin_payroll;
}
