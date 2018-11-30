/**
 * This is the main class of the othello algorithm. It can
 * be compiled and run with an accompanying othello.sh script.
 * This script is used as an argument in othellostart script
 * as one of the players in the othello game it starts.
 *
 * @author Niklas Königsson, dv15nkn
 */
public class Othello {

    /**
     * Main method. it takes 2 arguments. a game state and
     * a time limit.
     *
     * @param argv String[], The string vector of arguments
     */
    public static void main(String[] argv){

        if(argv.length != 2){
            System.err.println("Program should have 2 arguments. Position string and a time limit");
            return;
        }else if(argv[0].length() != 65){
            System.err.println("The position string has an invalid length");
            return;
        }

        long timeLimit = System.currentTimeMillis() + ((long) Integer.parseInt(argv[1])) * 1000 - 100;
        OthelloPosition pos = new OthelloPosition(argv[0]);

        int depth = 0;
        AlphaBeta alphaBeta = new AlphaBeta(depth, timeLimit);
        OthelloAction newAction;
        OthelloAction selectedAction = new OthelloAction(0,0,true);

        //Search for better moves with increased depth until the time limit
        while (System.currentTimeMillis() < alphaBeta.timeLimit) {
            alphaBeta.setSearchDepth(depth++);
            newAction = alphaBeta.evaluate(pos);
            if(newAction.row != 9){
                selectedAction = newAction;
            }else{
                break;
            }
        }
        selectedAction.print();
    }
}
