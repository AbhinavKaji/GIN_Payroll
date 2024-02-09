package com.mycompany.gin_payroll;

import com.mycompany.model.Employee;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

/**
 * Custom table cell for displaying an "Edit" button in a JavaFX TableView.
 * This cell allows editing of Employee records in a TableView.
 */
public class ActionButtonTableCell extends TableCell<Employee, Void> {
    private final Button editButton;

    /**
     * Constructor for the ActionButtonTableCell.
     * Initializes the "Edit" button and sets up the click event handler.
     */
    public ActionButtonTableCell() {
        editButton = new Button("Edit");
        editButton.setMinHeight(5);

        // Set up the event handler for the "Edit" button click
        editButton.setOnAction(event -> {
            // Handle edit button click event here
            Employee employee = getTableRow().getItem();
            if (employee != null) {
                // Implement your edit logic here
                System.out.println("Edit employee: " + employee.getUserId());
            }
        });

        // Create a container for the button and set spacing
        HBox buttonContainer = new HBox(editButton);
        buttonContainer.setSpacing(5);

        // Set the button container as the graphic for the cell
        setGraphic(buttonContainer);
    }

    /**
     * Overrides the updateItem method to update the cell's content.
     * If the cell is empty, the graphic is set to null. Otherwise, it displays the "Edit" button.
     *
     * @param item  The item to be displayed in the cell.
     * @param empty Indicates whether the cell is empty.
     */
    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(editButton);
        }
    }
}
