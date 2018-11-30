import java.util.LinkedList;

/**
 * This class implements the interface OthelloAlgorithm and
 * and uses the minimax algorithm whith alpha-beta pruning
 * to determine the best OthelloAction to be taken from
 * an OthelloPosition.
 *
 * @author Niklas Königsson, dv15nkn
 */

public class AlphaBeta implements OthelloAlgorithm {

    private int depth;
    long timeLimit;
    private OthelloEvaluator evaluator = new Evaluator();

    /**
     * Constructor for the algorithm
     *
     * @param depth int, how deep to search for actions
     * @param timeLimit long, The time in milliseconds when
     *                  the algorithm should terminate.
     */
    public AlphaBeta(int depth, long timeLimit) {

        this.depth = depth;
        this.timeLimit = timeLimit;
    }

    /**
     * Used to change the heuristic evaluator used
     *
     * @param evaluator OthelloEvaluator. heuristic evaluator
     */
    @Override
    public void setEvaluator(OthelloEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * This method is called from a position. it is the start
     * of the minimax algorithm and can go to one of two mirrored
     * ways depending on if the state is a maximizing or
     * minimizing node. The two paths calls minimize or maximize
     * that returns a value of the paths that can be used to
     * determine if it is the best action considering future
     * moves.
     *
     * @param position OthelloPosition, the game state
     * @return OthelloAction, The best action the algorithm
     *         the algorithm can determine.
     */
    @Override
    public OthelloAction evaluate(OthelloPosition position){

        LinkedList<OthelloAction> actions = position.getMoves();
        OthelloAction bestAction = new OthelloAction(0,0);

        if(position.toMove()){
            bestAction.setValue(Integer.MIN_VALUE);
        }else{
            bestAction.setValue(Integer.MAX_VALUE);
        }
        if(actions.isEmpty()){
            bestAction.setPassMove(true);
            return bestAction;
        }
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        if(position.toMove()){
            int value = Integer.MIN_VALUE;
            for (OthelloAction action : actions) {
                try {
                    //simulate moves from maximum node
                    OthelloPosition newMinState = position.makeMove(action);
                    value = Integer.max(value, maximum(newMinState, depth, alpha, beta));
                    if(System.currentTimeMillis() > timeLimit){
                        return new OthelloAction(9,9);
                    }
                    alpha = Integer.max(alpha, value);
                    if(value > bestAction.getValue()){
                        bestAction = action;
                        bestAction.setValue(value);
                    }
                } catch (IllegalMoveException e) {
                    e.printStackTrace();
                }
            }
            return bestAction;
        }else{

            int value = Integer.MAX_VALUE;
            for (OthelloAction action : actions) {
                try {
                    //simulate moves from minimum node
                    OthelloPosition newMinState = position.makeMove(action);
                    value = Integer.min(value, minimum(newMinState, depth, alpha, beta));
                    if(System.currentTimeMillis() > timeLimit){
                        return new OthelloAction(9,9);
                    }
                    beta = Integer.min(beta, value);
                    if(value < bestAction.getValue()){
                        bestAction = action;
                        bestAction.setValue(value);
                    }
                } catch (IllegalMoveException e) {
                    e.printStackTrace();
                }
            }
            return bestAction;
        }
    }

    /**
     * maximum is called from a maximizing node that seeks to
     * determine the maximum value of its child nodes. The
     * actions in this method will be minimizing nodes that call
     * minimize to seek the lowest value of their children nodes.
     *
     * @param position OthelloPosition, game state
     * @param depth, Integer, The current search depth
     * @param alpha, Integer, Alpha value of the parent node.
     * @param beta,  Integer, Beta value of the parent node.
     * @return Integer, The heuristic evaluation of this path
     */
    private int maximum(OthelloPosition position, int depth, int alpha, int beta){

        LinkedList<OthelloAction> actions = position.getMoves();

        if(depth == 0 || actions.isEmpty()){
            return evaluator.evaluate(position);
        }

        int value = Integer.MAX_VALUE;
        for (OthelloAction action : actions) {
            try {
                //simulate moves from minimum node
                OthelloPosition newMinState = position.makeMove(action);
                value = Integer.min(value, minimum(newMinState, depth-1, alpha, beta));
                if(System.currentTimeMillis() > timeLimit){
                    return 0;
                }
                beta = Integer.min(beta, value);
                if(alpha >= beta){
                    break;
                }
            } catch (IllegalMoveException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * minimum is called from a minimizing node that seeks to
     * determine the minimum value of its child nodes. The
     * actions in this method will be maximizing nodes that call
     * maximize to seek the highest value of their children nodes.
     *
     * @param position OthelloPosition, game state
     * @param depth, Integer, The current search depth
     * @param alpha, Integer, Alpha value of the parent node.
     * @param beta,  Integer, Beta value of the parent node.
     * @return Integer, The heuristic evaluation of this path
     */
    private int minimum(OthelloPosition position, int depth, int alpha, int beta){

        LinkedList<OthelloAction> actions = position.getMoves();

        if(depth == 0 || actions.isEmpty()){
            return evaluator.evaluate(position);
        }

        int value = Integer.MIN_VALUE;
        for (OthelloAction action : actions) {
            try {
                //simulate moves from maximum node
                OthelloPosition newMinState = position.makeMove(action);
                value = Integer.max(value, maximum(newMinState, depth-1, alpha, beta));
                if(System.currentTimeMillis() > timeLimit){
                    return 0;
                }
                alpha = Integer.max(alpha, value);
                if(alpha >= beta){
                    break;
                }
            } catch (IllegalMoveException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * Sets the search depth of the algorithm
     *
     * @param depth Integer, search depth
     */
    @Override
    public void setSearchDepth(int depth) {
        this.depth = depth;
    }
}
