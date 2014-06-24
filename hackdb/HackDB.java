package hackdb;

/**
 * @filename:       HackDB.java
 * @version:        0.2
 * @author:         Matthew Mayo
 * @modified:       2014-06-22
 * @usage:          java hackdb/HackDB
 */


import java.io.*;
import java.util.ArrayList;


class HackDB {

    private String dbName = "";
    private Database db;
    private ArrayList<Database> dbList = new ArrayList<Database>();


    /**
     * Main method creates new HackDB
     */
    public static void main(String[] args) throws IOException {
	HackDB hack = new HackDB();
    }


    /**
     * Default constructor passes control to dbConsole
     */
    public HackDB() throws IOException {
        // clear the screen, display message, pass control to dbConsole input loop
        for (int i = 0; i < 50; ++i) { System.out.println(); }
	System.out.println("HackDB v0.2 up and running...");
	this.dbConsole();
    }

    
    /**
     * Accepts input from console, passes to dbInterpreter
     */
    private void dbConsole() {
	String input = "";
	while (true) {
            if (dbName.equals(null)) { 
		System.out.print("> "); 
	    }
	    else { 
		System.out.print(dbName + "> "); 
	    }
            input = System.console().readLine();
	    dbInterpreter(input); 
	} 
    }


    /**
     * Inteprets commands, performs calls to database
     */
    private void dbInterpreter(String input) {
	
	/* make-db */
	if ((input.matches("(make-db)(.*)")) && (input.length() > 8) && (dbName.equals(""))) {
            if (dbList.isEmpty()) {
		dbList.add(0, new Database(input.substring(8)));
	    }
	    else {
		boolean badName = false;
		for (int i = 1; i <= dbList.size(); i++) {
		    if (dbList.get(i - 1).toString().equals(input.substring(8))) {
			System.out.println("Database name in use");
			badName = true;
		    }
		}
		if (badName == false) {
		    dbList.add(dbList.size(), new Database(input.substring(8)));
		}
	    }
	}

        if ((input.matches("(make-db)(.*)")) && (!dbName.equals(""))) { 
            System.out.println("Must unuse current database first"); 
        }

        if ((input.matches("(make-db)(.*)")) && (input.length() <= 8)) { 
            System.out.println("make-db usage: make-db <database_name>"); 
        }

        /* use-db */
        if (input.matches("(use-db)(.*)")) {
            if (input.length() > 7) {
                 if (!dbName.equals("")) { System.out.println("Already using a database"); }
                 else { 
                     for (int i = 1; i <= dbList.size(); i++) {
                         if (dbList.get(i - 1).toString().equals(input.substring(7))) {
                             dbName = input.substring(7); 
                             db = new Database(dbName);
                             db = dbList.get(i - 1); 
                         }
                     }
                 }
            }
            else { 
		System.out.println("use-db usage: use-db <database_name>"); 
	    }
        }

        /* no-db */
        if (input.equals("no-db")) {
            if (!dbName.equals("")) { 
                dbName = ""; 
            }
            else { 
                System.out.println("No database open"); 
            }
        }

        /* kill-db */
        if (input.matches("(kill-db)(.*)")) {
            if (input.length() > 8) { 
                for (int i = 1; i <= dbList.size(); i++) {
                    if (dbList.get(i - 1).toString().equals(input.substring(8))) {
                        db = dbList.remove(i - 1);
                    }
                }
            }
            else { 
		System.out.println("kill-db usage: kill-db <database_name>"); 
	    }
        }

        /* show-db */
        if (input.equals("show-db")) {
            for (int i = 1; i <= dbList.size(); i++) { 
		System.out.println(dbList.get(i - 1)); 
	    }
        }

        /* show-all */
        if ((input.equals("show-all")) && (!dbName.equals(""))) { 
	    db.showAll(); 
	}


	/* sort-all */
	if ((input.equals("sort-all")) && (!dbName.equals(""))) { 
	    db.sort(); 
	}

        /* show-keys */
        if ((input.equals("show-keys")) && (!dbName.equals(""))) { 
	    db.showKeys(); 
	}

        /* sort-keys */
        if ((input.equals("sort-keys")) && (!dbName.equals(""))) { 
	    db.showKeysSorted();
	}

        /* show-vals */
        if ((input.equals("show-vals")) && (!dbName.equals(""))) { 
	    db.showVals();
	}

        /* count */
	if ((input.equals("count")) && (!dbName.equals(""))) { 
	    System.out.println(db.count()); 
	}

        /* add */
        if ((input.matches("(add)(.*)")) && (!dbName.equals("")) && (input.length() > 4)) {
	    String record = input.substring(4);
            db.add(record.substring(0, (record.indexOf(","))).trim(), record.substring((record.indexOf(",")) + 1).trim());
	}

        /* get */
        if ((input.matches("(get)(.*)")) && (!dbName.equals("")) && (input.length() > 4)) {
            System.out.println(db.get(input.substring(4)));
	}

        /* del */
	if ((input.matches("(del)(.*)")) && (!dbName.equals("")) && (input.length() > 4)) { 
	    db.del(input.substring(4)); 
	}

        /* min-key */
	if ((input.equals("min-key")) && (!dbName.equals(""))) { 
	    System.out.println(db.minKey()); 
	}
        
        /* max-key */
	if ((input.equals("max-key")) && (!dbName.equals(""))) { 
	    System.out.println(db.maxKey()); 
	}

        /* has-key */
        if ((input.matches("(has-key)(.*)")) && (!dbName.equals("")) && (input.length() > 8)) {
	    System.out.println(db.hasKey(input.substring(8)));
	}

        /* has-val */
        if ((input.matches("(has-val)(.*)")) && (!dbName.equals("")) && (input.length() > 8)) {
	    System.out.println(db.hasValue(input.substring(8)));
	}

        /* purge-all */
        if (input.equals("purge-all")) {
            if (!dbName.equals("")) { 
		System.out.println("purge-all usage: purge-all -y"); 
	    }
            else { 
		System.out.println("No database open"); 
	    }
        }

	if (input.equals("purge-all -y")) {
            if (!dbName.equals("")) { 
		db.purge(); 
	    }
            else { 
		System.out.println("No database open"); 
	    }
        }

        /* import-csv */
        if ((input.matches("(import-csv)(.*)")) && (!dbName.equals("")) && (input.length() > 11)) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(input.substring(11)));
                String line;
                while ((line = br.readLine()) != null) {
                    db.add(line.substring(0, (line.indexOf(","))).trim(), line.substring((line.indexOf(",")) + 1).trim());
                }
                br.close();
            }
            catch (Exception e) { 
		System.out.println("Error importing csv"); 
	    }
        }

        /* import-tsv */
         if ((input.matches("(import-tsv)(.*)")) && (!dbName.equals("")) && (input.length() > 11)) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(input.substring(11)));
                String line;
                while ((line = br.readLine()) != null) {
                    db.add(line.substring(0, (line.indexOf("\t"))).trim(), line.substring((line.indexOf("\t")) + 1).trim());
                }
                br.close();
            }
            catch (Exception e) { 
		System.out.println("Error importing tsv"); 
	    }
        }

        /* load */
        if ((input.matches("(load)(.*)")) && (!dbName.equals("")) && (input.length() > 5)) { 
	    db.load(input.substring(5)); 
	}

        /* save */
        if ((input.matches("(save)(.*)")) && (!dbName.equals("")) && (input.length() > 5)) { 
	    db.save(input.substring(5)); 
	}

        /* quit */
        if (input.equals("quit")) {
	    if (!dbName.equals("")) { 
		System.out.println("Must not be using database to quit"); 
	    }
	    else { 
                System.exit(0); 
	    }
        }
    }

}
