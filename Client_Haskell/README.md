
# Client Manuel

Ce projet est un client Haskell qui se connecte à un réflecteur et valide ses messages à l'aide de `MessageValidator` et
d'`Automaton`. Il est équipé de tests unitaires créés avec **HUnit** et utilise **Stack** comme outil principal de
gestion de projet.

## Prérequis

- Installer [Haskell Stack](https://docs.haskellstack.org/en/stable/README/)

## Commandes Makefile

Voici les commandes disponibles dans le `Makefile` pour la gestion du projet :

### 1. Construire le projet

Pour construire le projet, exécutez la commande suivante :

```bash
make build
```

Elle utilise **Stack** pour construire les dépendances et les modules du projet.

### 2. Lancer les tests unitaires

Pour exécuter les tests unitaires, utilisez :

```bash
make test
```

Le framework **HUnit** est utilisé pour écrire et exécuter les tests.

### 3. Nettoyer le projet

Pour nettoyer les artefacts générés, exécutez la commande :

```bash
make clean
```

Ceci supprimera les fichiers de construction générés par **Stack**.

### 4. Exécuter le client

Pour exécuter le client et se connecter au réflecteur :

```bash
make run
```

La commande démarre le programme principal en invoquant l'exécutable **Stack** avec le nom du projet : `client`.

## Commande Makefile

Le contenu du fichier `Makefile` est le suivant :

```makefile
PROJECT_NAME=client
STACK=stack

.PHONY: all build test clean run

all: build

build:
	$(STACK) build

test:
	$(STACK) test

clean:
	$(STACK) clean

run:
	$(STACK) exec $(PROJECT_NAME)
```

## Notes supplémentaires

- Le client utilise `MessageValidator` pour valider les messages et `Automaton` pour appliquer des règles basées sur les
  états.
- Ce projet repose sur l'outil **Stack** pour gérer les dépendances et simplifier la gestion du projet Haskell.