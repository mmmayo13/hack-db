package hackdb;

/**
 * @Filename:       HackDB.java
 * @Author:         Matthew Mayo
 * @Modified:       2014-06-18
 * @Usage:          java hackdb/HackDB
 */


import java.io.*;


class HackDB {

    private String dbName = "";
    private Database db;	


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
	System.out.println("HackDB v0.1 up and running...");
	this.dbConsole();
    }

    
    /**
     * Accepts input from console, passes to dbInterpreter
     */
    private void dbConsole() {
	String input = "";
	while (!input.equals("quit")) {
            if (dbName.equals(null)) { System.out.print("> "); }
	    else { System.out.print(dbName + "> "); }
            input = System.console().readLine();
	    dbInterpreter(input);
	}
    }


    /**
     * Inteprets commands, performs calls to database
     */
    private void dbInterpreter(String input) {
	
	/* make-db */
	if (input.matches("(make-db)(.*)") && input.length() > 8) {
	    dbName = input.substring(8);
	    db = new Database(dbName);
	}

	/* kill-db */
	if ((input.matches("(kill-db)(.*)")) && (!dbName.equals("")) && (input.length() > 8) && (input.substring(8).equals(dbName))) {
	    dbName = "";
            db.purge();
	}

        /* add */
        if ((input.matches("(add)(.*)")) && (!dbName.equals("")) && (input.length() > 4)) {
	    String record = input.substring(4);
            db.add(record.substring(0, (record.indexOf(","))).trim(), 
                record.substring((record.indexOf(",")) + 1).trim());
	}

        /* get */
        if ((input.matches("(get)(.*)")) && (!dbName.equals("")) && (input.length() > 4)) {
            System.out.println(db.get(input.substring(4)));
	}

        /* show-all */
        if ((input.equals("show-all")) && (!dbName.equals(""))){
            db.showAll();
        }

        /* show-keys */
        if ((input.equals("show-keys")) && (!dbName.equals(""))) {
            db.showKeys();
        }

        /* remove record */
	if ((input.matches("(del)(.*)")) && (!dbName.equals("")) && (input.length() > 4)) {
	    db.del(input.substring(4));
	}

	/* count */
	if ((input.equals("count")) && (!dbName.equals(""))) {
            System.out.println(db.count());
	}

        /* min-key */
	if ((input.equals("min-key")) && (!dbName.equals(""))) {
	    System.out.println(db.minKey());
	}
        
        /* max-key */
	if ((input.equals("max-key")) && (!dbName.equals(""))){
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

	/* purge records */
	if ((input.equals("purge-all -y")) && (!dbName.equals(""))) {
	    db.purge();
	}

	/* sort */
	if ((input.equals("sort")) && (!dbName.equals(""))) {
	    db.sort();
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

        /* load */
        if ((input.matches("(load)(.*)")) && (!dbName.equals("")) && (input.length() > 5)) { db.load(input.substring(5)); }

        /* save */
        if ((input.matches("(save)(.*)")) && (!dbName.equals("")) && (input.length() > 5)) { db.save(input.substring(5)); }

    }
}

