package lk.ijse.gdse.repository;

import lk.ijse.gdse.DB.DbConnection;
import lk.ijse.gdse.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employee VALUES(?,?,?,?,?,?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, employee.getEmployeeId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getNICNo());
        pstm.setObject(4, employee.getAddress());
        pstm.setObject(5, employee.getTel());
        pstm.setObject(6, employee.getSalary());

        return pstm.executeUpdate() > 0;
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM Employee";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Employee> employeesList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String nicNo = resultSet.getString(3);
            String address = resultSet.getString(4);
            String tel = resultSet.getString(5);
            double salary = resultSet.getDouble(6);

            Employee employee = new Employee(id, name, nicNo, address, tel, salary);
            employeesList.add(employee);
        }
        return employeesList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Employee WHERE employeeId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE Employee SET name = ? , nicNo = ? , address = ? , tel = ? , salary = ? WHERE employeeId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,employee.getName());
        pstm.setObject(2,employee.getNICNo());
        pstm.setObject(3,employee.getAddress());
        pstm.setObject(4,employee.getTel());
        pstm.setObject(5,employee.getSalary());
        pstm.setObject(6,employee.getEmployeeId());

        return pstm.executeUpdate() > 0;
    }

    public static Employee searchByTel(String tel) throws SQLException {
        String sql = "SELECT*FROM Employee WHERE tel = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,tel);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String tel1 = resultSet.getString(1);
            String employeeId = resultSet.getString(2);
            String name = resultSet.getString(3);
            String address = resultSet.getString(4);
            String nicNo = resultSet.getString(5);
            double salary = resultSet.getDouble(6);


            Employee employee = new Employee(employeeId, name, address, nicNo, tel1, salary);
            return employee;
        }
        return null;
    }

    public static List<String> getTel() throws SQLException {
        String sql = "SELECT tel from Employee";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> employeeList = new ArrayList<>();

        while (resultSet.next()){
            String tel = resultSet.getString(1);
            employeeList.add(tel);
        }
        return employeeList;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT employeeId FROM Employee ORDER BY employeeId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String employeeId = resultSet.getString(1);
            return employeeId;
        }
        return null;
    }
}
