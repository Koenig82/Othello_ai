/**
 * This class implements OthelloEvaluator interface and
 * is used to determine utility value for agents when a
 * leaf node is reached in the alpha beta algorithm.
 * The evaluation considers how much moves are available
 * to the agents and prioritizes positioning along
 * the edges of the board
 *
 * @author Niklas Königsson, dv15nkn
 */
public class Evaluator implements OthelloEvaluator {

    /**
     * Evaluates a game state utility by determining
     * the position and mobility values.
     *
     * @param position OthelloPosition, The game state
     * @return Integer, Utility value
     */
    @Override
    public int evaluate(OthelloPosition position) {

        int mobility = getMobilityValue(position);
        int coinValue = getCoinValue(position);

        return coinValue + mobility;

    }

    /**
     * Helper method used by evaluate to determine the
     * positioning score
     *
     * @param position OthelloPosition, The game state
     * @return Integer, Utility value of the coin positions
     */
    private int getCoinValue(OthelloPosition position) {
        int whiteCoins = 0;
        int blackCoins = 0;
        for(int i = 1; i <= OthelloPosition.BOARD_SIZE; i++){
            for(int j = 1; j <= OthelloPosition.BOARD_SIZE; j++){
                if(position.board[i][j] == 'W'){
                    whiteCoins++;
                    whiteCoins = addEdgeScore(whiteCoins, i, j);
                }else if(position.board[i][j] == 'B'){
                    blackCoins++;
                    blackCoins = addEdgeScore(blackCoins, i, j);
                }
            }
        }
        return whiteCoins-blackCoins;
    }

    /**
     * Helper method for getCoinValue. adds additional utility
     * for edges and corners to the coin score.
     *
     * @param counter Integer, The current coin score
     * @param i Integer, The column value where a coin was found.
     * @param j Integer, The row value where a coin was found
     * @return Integer, The coin value with added edge bonus
     */
    private int addEdgeScore(int counter, int i, int j) {
        if((i == 1 || i == 8) && (j == 1 || j == 8)){
            counter += 50;
        }
        else if(i == 1 || i == 8){
            counter += 5;
        }
        else if(j == 1 || j == 8){
            counter += 5;
        }
        return counter;
    }

    /**
     * Helper method for evaluate. Adds together the available
     * moves for both players and returns the difference.
     *
     * @param position OthelloPosition, the game state
     * @return Integer, Mobility value
     */
    private int getMobilityValue(OthelloPosition position) {
        int whiteMobility;
        int blackMobility;
        OthelloPosition opponent = position.clone();
        if(position.toMove()){
            whiteMobility = 8 * position.getMoves().size();
            opponent.playerToMove = false;
            blackMobility = 8 * opponent.getMoves().size();
        }else{
            opponent.playerToMove = true;
            whiteMobility = 8 * opponent.getMoves().size();
            blackMobility = 8 * position.getMoves().size();
        }
        return whiteMobility-blackMobility;
    }
}
