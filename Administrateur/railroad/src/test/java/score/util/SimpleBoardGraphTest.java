package score.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.*;
import graph.Edge;
import graph.Graph;
import util.util_for_nodes.Node;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBoardGraphTest {

    private SimpleBoardGraph simpleBoardGraph;
    private Set<Node> nodeSet1;
    private Set<Node> nodeSet2;
    private Set<Node> nodeSet3;

    @BeforeEach
    void setUp() {
        // Initialisation du graphe et des ensembles de nœuds
        simpleBoardGraph = new SimpleBoardGraph();

        // Création de trois ensembles de nœuds pour les tests
        nodeSet1 = new HashSet<>();
        nodeSet1.add(new Node());
        nodeSet1.add(new Node());

        nodeSet2 = new HashSet<>();
        nodeSet2.add(new Node());
        nodeSet2.add(new Node());

        nodeSet3 = new HashSet<>();
        nodeSet3.add(new Node());
        nodeSet3.add(new Node());
    }

    @Test
    void testAddNodeSet() {
        // Ajouter un ensemble de nœuds au graphe
        simpleBoardGraph.addNodeSet(nodeSet1);

        // Vérifier que l'ensemble de nœuds a bien été ajouté
        Set<Set<Node>> allNodeSets = simpleBoardGraph.getSetOfConnections();
        assertTrue(allNodeSets.contains(nodeSet1), "L'ensemble de nœuds devrait être présent dans le graphe");
        assertEquals(1, allNodeSets.size(), "Le graphe devrait contenir exactement 1 ensemble de nœuds");
    }

    @Test
    void testConnectNodeSets() {
        // Ajouter deux ensembles de nœuds au graphe
        simpleBoardGraph.addNodeSet(nodeSet1);
        simpleBoardGraph.addNodeSet(nodeSet2);

        // Connecter les deux ensembles de nœuds
        simpleBoardGraph.connectNodeSets(nodeSet1, nodeSet2);

        // Vérifier que la connexion existe
        assertTrue(simpleBoardGraph.containsConnection(nodeSet1, nodeSet2), "Les ensembles de nœuds devraient être connectés");
    }

    @Test
    void testContainsConnection() {
        // Ajouter deux ensembles de nœuds au graphe
        simpleBoardGraph.addNodeSet(nodeSet1);
        simpleBoardGraph.addNodeSet(nodeSet2);

        // Connecter les deux ensembles de nœuds
        simpleBoardGraph.connectNodeSets(nodeSet1, nodeSet2);

        // Vérifier que la connexion existe
        assertTrue(simpleBoardGraph.containsConnection(nodeSet1, nodeSet2), "La connexion entre nodeSet1 et nodeSet2 devrait exister");

        // Vérifier qu'une connexion inexistante n'est pas détectée
        assertFalse(simpleBoardGraph.containsConnection(nodeSet1, nodeSet3), "La connexion entre nodeSet1 et nodeSet3 ne devrait pas exister");
    }

    @Test
    void testGetSetOfConnections() {
        // Ajouter plusieurs ensembles de nœuds au graphe
        simpleBoardGraph.addNodeSet(nodeSet1);
        simpleBoardGraph.addNodeSet(nodeSet2);
        simpleBoardGraph.addNodeSet(nodeSet3);

        // Récupérer tous les ensembles de nœuds
        Set<Set<Node>> allNodeSets = simpleBoardGraph.getSetOfConnections();

        // Vérifier que tous les ensembles de nœuds sont présents
        assertEquals(3, allNodeSets.size(), "Le graphe devrait contenir 3 ensembles de nœuds");
        assertTrue(allNodeSets.contains(nodeSet1), "nodeSet1 devrait être présent dans le graphe");
        assertTrue(allNodeSets.contains(nodeSet2), "nodeSet2 devrait être présent dans le graphe");
        assertTrue(allNodeSets.contains(nodeSet3), "nodeSet3 devrait être présent dans le graphe");
    }

    @Test
    void testGetGraph() {
        // Récupérer le graphe sous-jacent
        Graph<Set<Node>, Edge<Set<Node>>> graph = simpleBoardGraph.getGraph();

        // Vérifier que le graphe est initialisé et vide
        assertNotNull(graph, "Le graphe ne devrait pas être null");
        assertTrue(graph.getVertices().isEmpty(), "Le graphe devrait être vide initialement");
    }
}