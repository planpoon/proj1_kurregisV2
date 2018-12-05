package Model;

public class InitializeSubjectToDB {
    public static void main(String[] args) {
        DBConnector dbConnector  = new DBConnector();
        dbConnector.addSubject("01418114", "Introduction to Computer Science", 4, 1,1);
        dbConnector.addSubject("01418131", "Digital Computuer Logic", 3, 1,1);
        dbConnector.addSubject("01355112", "Foundation English II", 3, 1,1);
        dbConnector.addSubject("01420119", "Abridged Physics ", 3, 1,1);
        dbConnector.addSubject("01420115", "Laboratory in Abridge Physics I ", 1, 1,1);

        dbConnector.addSubject("01418116", "Computer Programming", 3, 2, "01418114",2);
        dbConnector.addSubject("01355113", "Foundation English III", 3, 2, "01355112",1);
        dbConnector.addSubject("01417111", "Calculus I", 3, 2,1);
        dbConnector.addSubject("01424111", "Principle of Biology", 3, 2,1);
        dbConnector.addSubject("01424112", "Laboratory for Biology", 1, 2,1);
        dbConnector.addSubject("01403111", "General Chemistry", 4, 2,1);

        dbConnector.addSubject("01418217", "Software Construction", 3, 3, "01418116",3);
        dbConnector.addSubject("01418231", "Data Structures", 3, 3, "01418116",2);
        dbConnector.addSubject("01418132", "Fundamentals of Computing", 4, 3,1);
        dbConnector.addSubject("01417112", "Calculus II", 3, 3, "01417111",2);
        dbConnector.addSubject("01355202", "Fundamental English Writing", 3, 3, "01355113",2);

        dbConnector.addSubject("01418216", "Principles of Programming Languages", 4, 4, "01418116",2);
        dbConnector.addSubject("01418221", "Fundamentals of Database Systems", 3, 4, "01418116",2);
        dbConnector.addSubject("01418232", "Algorithms Design & Analysis", 3, 4, "01418231",3);
        dbConnector.addSubject("01417322", "Introductory Linear Algebra", 3, 4, "01417112",3);
        dbConnector.addSubject("01422111", "Principles of Statistics", 3, 4,1);

        dbConnector.addSubject("01418321", "System Analysis & Design", 3, 5, "01418221",2);
        dbConnector.addSubject("01418351", "Computer Communications & Protocols", 3, 5, "01418116",3);
        dbConnector.addSubject("01418331", "Assembly Language and Computer Architecture", 4, 5, "01418116,01418131,01420243",2);

        dbConnector.addSubject("01418333", "Formal Language & Automata Theory", 3, 6, "01418132,01417271",2);
        dbConnector.addSubject("01418497", "Seminar", 1, 6, "01418216,01418221,01418221",3);
        dbConnector.addSubject("01418343", "Intellec.Pro.for Software & Digital Contents", 3, 6,1);
        dbConnector.addSubject("01418332", "Operating Systems", 4, 6, "01418331",2);

        dbConnector.addSubject("01418399", "Computer Science Project Preparation", 1, 7, "01418321",3);

        dbConnector.addSubject("01418499", "Computer Science Project", 3, 8, "01418399",3);
    }
}
