package EditCommit;

import java.sql.SQLException;

import ConnectionsToMYSQL.Execute;
import Controls.RefreshAdminTables;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import library.HourlyEmployee;

public class HourlyEmployeeCharEditCommit extends RefreshAdminTables implements EventHandler<CellEditEvent<HourlyEmployee, Character>> {

	public HourlyEmployeeCharEditCommit() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	public void handle(CellEditEvent<HourlyEmployee, Character> t) {
		
		if (t.getSource().equals(hourlyEmployeeGender)) {
			this.updateGender(t);
		}
		
	}
	
	// -----------------------------------------------------------------------------------------//
	// update gender
	private void updateGender(CellEditEvent<HourlyEmployee, Character> t) {

		// update in the tableView
		t.getTableView().getItems().get(t.getTablePosition().getRow()).setGender(t.getNewValue());

		try {
			this.updateGenderInDatabase(t.getRowValue().getId(), t.getNewValue());
		} catch (ClassNotFoundException | SQLException e) {
		}

		// refresh HOURLYEMPLOYEE table
		super.refreshTablesData(HOURLYEMPLOYEE);

	}

	// update gender
	private void updateGenderInDatabase(String id, char gender) throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE users\r\n" + "SET user_gender = '" + gender + "'\r\n" + "WHERE user_id = " + id;

		// execute
		Execute.executeQuery(query);
	}
}
