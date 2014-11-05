package br.uff.es2.war.model.phases;

import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.objective.FullObjective;
import br.uff.es2.war.model.objective.Objective;
import br.uff.es2.war.model.phases.GameState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * During the setup phase each player chooses his color and receives his
 * objective.
 *
 * @see Color
 * @see Objective
 *
 * @author Arthur Pitzer
 */
public class SetupPhase implements GameState<Game> {

    private Game game;

    @Override
    public GameState<Game> execute(Game game) {
        this.game = game;
        readColors();
        game.distributeTerritories();
        for (Player player : game.getPlayers()) {
            player.setGame(game);
        }
        loadObjectives();
        return new TurnChangePhase();
    }

    public void readColors() {
        Collection<Color> remaining = new ArrayList<Color>();
        remaining.addAll(Arrays.asList(game.getColors()));
        for (Player player : game.getPlayers()) {
            Color[] colors = remaining.toArray(new Color[remaining.size()]);
            Color color = player.chooseColor(colors);
            player.setColor(color);
            remaining.remove(color);
        }
    }

    private void loadObjectives() {
        List<Objective> remaining = new ArrayList<>(game.getObjectives());
        Collections.shuffle(remaining);
        Random random = new Random();
        int r;
        for (Player player : game.getPlayers()) {
            while (player.getObjective() == null || player.getObjective().wasAchieved()) {
                r = random.nextInt(remaining.size());
                setObjectiveToPlayer(r, remaining, player);
            }
        }

    }

    private void setObjectiveToPlayer(int r, List<Objective> remaining, Player player) {
        player.setObjective(remaining.get(r));
        remaining.remove(r);

        if (!player.getObjective().isPossible()) {
            player.getObjective().switchToAlternativeObjective();
        }
    }
}
