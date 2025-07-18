package main;

import java.util.ArrayList;
import java.util.List;

import face.AbstractFace;

/**
 * The Round class represents a game round where faces are distributed to players.
 * It manages the faces that are thrown and their distribution to players.
 */
public class Round {

    /**
     * List of faces that have been thrown during this round.
     */
    private List<AbstractFace> throwsTiles;

    /**
     * Constructor for the Round class.
     * Initializes the list of thrown faces.
     */
    public Round(){
        this.throwsTiles = new ArrayList<>();
    }

    /**
     * Distributes faces to the specified players by cloning each face for each player.
     *
     * @param targets List of players to distribute faces to.
     * @throws CloneNotSupportedException If cloning a face fails.
     */
    public void distributeFaces(List<Player> targets) throws CloneNotSupportedException {
        for(Player player : targets) {
            player.resetForNextRound();
            for(AbstractFace face : this.throwsTiles) {
                player.addFaces(face.clone());
            }
        }
        if (targets.size() > 0) {
        System.out.println("\n\nFaces of players : ");
            for(AbstractFace face : targets.get(0).getFaces()) {
                System.out.println("\t - " + face.getId());
            }

        }
    }

    /**
     * Adds a face to the list of faces thrown during this round.
     *
     * @param face The face to add.
     */
    public void addFace(AbstractFace face) {
        this.throwsTiles.add(face);
    }

    /**
     * Searches for a face by its identifier in the list of thrown faces.
     *
     * @param id The identifier of the face to search for.
     * @return The corresponding face if found, otherwise null.
     */
    public AbstractFace getFaceByid(String id) {
        for(AbstractFace face : this.throwsTiles) {
            if(face.getId().contentEquals(id)) {
                return face;
            }
        }
        return null;
    }

}
