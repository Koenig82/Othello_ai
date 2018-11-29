public class Evaluator implements OthelloEvaluator {

    @Override
    public int evaluate(OthelloPosition position) {

        int mobility = getMobilityValue(position);
        int coinValue = getCoinValue(position);

        return coinValue + mobility;

    }

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
