# Projet RailRoadInk - Reflecteur 

## Description  

Ce module implémente un réflécteur en Rust pour centraliser les connexions des différents modules et pouvoir centraliser les messages.
Il sera responsable de la gestion des messages et de tout le système de connexion / deconnexion associé. 

## Pré-requis  

Si vous souhaitez compiler le code vous devez vous assurer de posséder Rust sur votre machine. 
Dans le cas contraire vous pouvez directement lancer l'executable fourni dans le dépot. 

Le fichier <code>./RReflector<code> est l'executable pour les environnements Linux.
Le fichier <code>./RReflector_Win.exe<code> est l'executable pour les environnements Windows.

## Execution

Pour compiler le code, vous devez utiliser *Cargo* via la commande suivante :

```bash
make exec-release
```

Une fois la commande executée vous pour retrouver le fichier à executer dans le dossier <code>./target/release/RReflector</code>. 
Pour l'executer vous pouvez lancer la commande suivante : 

```bash
./RReflector
```
Si vous ne souhaitez pas le compiler avant, vous pouvez executer cette commande dans la racine du projet ou se trouve une copie de l'executable. 

Notez que vous pouvez modifier le port d'execution, ainsi qu'ajouter des actions qui seront maintenant acceptées par le réflécteur via la commande suivante : 

```bash
./RReflector --port <PORT> --add <ACTION>
```

Chacun de ces arguments est facultatif et peut être utilisé indépendemment de l'autre. 

## Actions 

Par défaut, le réflécteur accepte 12 messages d'actions différents : 
 - ENTERS
 - LEAVES
 - THROWS
 - ELECTS
 - PLACES
 - YIELDS
 - BLAMES
 - PLAYS
 - SCOREROUND
 - SCORES
 - GRANTS
 - AGREES

## Fonctionnement 

Lors de la connexion d'un client, le réflecteur va vérifier si le client à bien réaliser la commande *ENTERS*. 
S'il accepte la connexion, le reflécteur va envoyer au client tous les messages passés.
Ensuite pour chaque nouveau client on va vérifier si l'action qu'il réalise est bien présente dans la liste d'actions autorisées par le réflécteur. 
Et enfin le réflécteur renvoie le message à tous les autres clients. 

Si jamais l'une des conditions ci-dessus n'est pas accéptée, le réflecteur deconnectera la client et enverra le message *<id> LEAVES* à tous les clients pour signifier sa déconnexion. 