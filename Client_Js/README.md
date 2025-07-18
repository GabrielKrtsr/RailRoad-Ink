# Projet RailRoad Ink - Interface Web

## Description 

Ce module est une interface Web interactive pour le jeu RailRoad, permettant aux joueurs de placer et manipuler des tuiles sur un plateau SVG en temps réel. 
Le système supporte le multijoueur avec synchronisation via WebSocket et offre une gestion complète des règles et du score.


## Fonctionnalités Clés

### Mécaniques de Jeu
- Distribution de 4 tuiles par tour
- Système de placement :
    - Rotation (0°, 90°, 180°, 270°)
    - Retournement horizontal (miroir)
    - Drag-drop des pièces sur le plateau.
- Gestion des tours avec chronométrage
- Validation des coups en respectant les contraintes du jeu

### Système Multijoueur
- Connexion WebSocket temps réel
- Synchronisation des plateaux
- Visualisation des actions adverses 
- Interface administrateur pour gerer les parties

### Calcul des Scores
- Réseaux connectés (Network)
- Routes les plus longues (Longest Road)
- Rails continus (Longest Rail)
- Contrôle du centre (Center Cell)
- Pénalités (Penalties) pour les connexions incompletes 

## Lancement du Serveur

### Installation
npm install

#### Démarrage 
nodemon

Ouvrir localhost sur le port 3000. 