

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Main {

	public static long startTime = System.nanoTime();
	public static void main(String[] args) throws FileNotFoundException {

		BufferedReader br = null;
		FileReader fr = null;
		PrintStream fileStream = null;
		String nume = args[0]+"_out";
		fileStream = new PrintStream (nume);
		System.setOut(fileStream);
		try {
			LSQLDB db = null;
			fr = new FileReader (args[0]);
			br = new BufferedReader(fr);
			
			String instruction = br.readLine();
			String[] array = instruction.split(" ");

			if(array[0].equals("CREATEDB")) {
				db = new LSQLDB(array[1], Integer.parseInt(array[2]), Integer.parseInt(array[3]));
				for(int i = 0; i < db.noNodes; i++) {
					Node node = new Node(db.maxCapacity);
					db.nodes.add(node);
				}
			}
			Parser parser = new Parser();
			while((instruction = br.readLine()) != null) {
				readInstruction (instruction, db, parser);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readInstruction (String instruction, LSQLDB db, Parser parser) {

		String[] array = instruction.split(" ");
		if(array[0].equals("CREATE")) {
			parser.createEntity(array, db);
		} else if(array[0].equals("INSERT")) {
			parser.insertInstance(array, db);
		} else if(array[0].equals("DELETE")) {
			parser.deleteInstance(array, db);		
		} else if(array[0].equals("UPDATE")) {
			parser.updateInstance(array, db);
		} else if(array[0].equals("GET")) {
			parser.getInstance(array, db);
		} else if(array[0].equals("SNAPSHOTDB")) {
			parser.printDB(db);
		} else if(array[0].equals("CLEANUP")) {
			parser.cleanup(array, db, startTime);
		}
	}
}
