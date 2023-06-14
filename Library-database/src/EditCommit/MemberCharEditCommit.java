package EditCommit;

import java.sql.SQLException;

import ConnectionsToMYSQL.Execute;
import Controls.RefreshAdminTables;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import library.ChickMethods;
import library.Member;

public class MemberCharEditCommit extends RefreshAdminTables implements EventHandler<CellEditEvent<Member, Character>> {

	public MemberCharEditCommit() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	public void handle(CellEditEvent<Member, Character> t) {

		if (t.getSource().equals(memberGender)) {
			this.updateGender(t);
		}
	}

	// -----------------------------------------------------------------------------------------//
	// update gender
	private void updateGender(CellEditEvent<Member, Character> t) {

		// update in the tableView
		t.getTableView().getItems().get(t.getTablePosition().getRow()).setGender(t.getNewValue());

		try {
			this.updateGenderInDatabase(t.getRowValue().getId(), t.getNewValue());
		} catch (ClassNotFoundException | SQLException e) {
		}

		// refresh member table
		super.refreshTablesData(MEMBER);

	}

	// update gender
	private void updateGenderInDatabase(String id, char gender) throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE users\r\n" + "SET user_gender = '" + gender + "'\r\n" + "WHERE user_id = " + id;

		// execute
		Execute.executeQuery(query);
	}
}
