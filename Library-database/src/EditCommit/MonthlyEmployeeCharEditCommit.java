package EditCommit;

import java.sql.SQLException;

import ConnectionsToMYSQL.Execute;
import Controls.RefreshAdminTables;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import library.MonthlyEmployee;

public class MonthlyEmployeeCharEditCommit extends RefreshAdminTables implements EventHandler<CellEditEvent<MonthlyEmployee, Character>>{

	public MonthlyEmployeeCharEditCommit() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	public void handle(CellEditEvent<MonthlyEmployee, Character> t) {
		
		if (t.getSource().equals(monthlyEmployeeGender)) {
			this.updateGender(t);
		}
		
	}
	
	// -----------------------------------------------------------------------------------------//
	// update gender
	private void updateGender(CellEditEvent<MonthlyEmployee, Character> t) {

		// update in the tableView
		t.getTableView().getItems().get(t.getTablePosition().getRow()).setGender(t.getNewValue());

		try {
			this.updateGenderInDatabase(t.getRowValue().getId(), t.getNewValue());
		} catch (ClassNotFoundException | SQLException e) {
		}

		// refresh MONTHLYEMPLOYEE table
		super.refreshTablesData(MONTHLYEMPLOYEE);

	}

	// update gender
	private void updateGenderInDatabase(String id, char gender) throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE users\r\n" + "SET user_gender = '" + gender + "'\r\n" + "WHERE user_id = " + id;

		// execute
		Execute.executeQuery(query);
	}
}
