import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;

import com.Database.DBGenerator;
import com.View.TUI;

public abstract class MainActivity {
	//Main class

	public static void main(String[] args) {

		DBGenerator dbg = new DBGenerator();

		TUI tui = new TUI();

		dbg.generateDB();

		tui.startUI();

	}
}
