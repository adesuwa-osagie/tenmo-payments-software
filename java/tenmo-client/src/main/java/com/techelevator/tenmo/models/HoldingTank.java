package com.techelevator.tenmo.models;

public class HoldingTank {

    /*private void handleChangeEmployeeDepartmentTest() {
        printHeading("Change Employee Department");

        System.out.println("Choose an employee to transfer:");
        List<Employee> allEmployees = employeeDAO.getAllEmployees();
        Employee selectedEmployee = (Employee)menu.getChoiceFromOptions(allEmployees.toArray());

        System.out.println("Choose the new department:");
        List<Department> allDepartments = departmentDAO.getAllDepartments();
        Department selectedDepartment = (Department)menu.getChoiceFromOptions(allDepartments.toArray());

        employeeDAO.changeEmployeeDepartment(selectedEmployee.getId(), selectedDepartment.getId());


    }



    public Object getChoiceFromOptionsTest(Object[] options) {
        Object choice = null;
        while(choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    private Object getChoiceFromUserInputTest(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.valueOf(userInput);
            if(selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch(NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if(choice == null) {
            out.println("\n*** "+userInput+" is not a valid option ***\n");
        }
        return choice;
    }

    private void displayMenuOptionsTest(Object[] options) {
        out.println();
        for(int i = 0; i < options.length; i++) {
            int optionNum = i+1;
            out.println(optionNum+") "+options[i]);
        }
        out.print("\nPlease choose an option >>> ");
        out.flush();
    }*/
}
