# Projet RailRoad Ink - IA

## Description 

Ce module a pour but de développer une IA permettant de jouer à RailRoad Ink sans avoir besoin d'intéragir avec le plateau. 

## Pré-requis 

Vous devez avoir installé *Python* ainsi que le module *Websocket* que vous pouvez installer via la commande 
```bash
pip install websocket
```
Vous devez aussi avoir installer le module *asyncio*. 
```bash
pip install asyncio
```

## Execution 

Pour executer l'IA, vous devez vous placer dans le dépot et executer l'une des commandes suivantes :

 - Si vous souhaitez jouer avec une IA et avec un vrai joueur : 
```bash
python3 ai.py <ID>
```

 - Si vous souhaitez jouer qu'avec des IA, une IA devra executer la commande suivante avec l'argument lauch pour pouvoir démarrer la partie: 
```bash
python3 ai.py <ID> launch
```
L'IA qui intègre l'argument *launch* devra être lancée en dernière car c'est elle qui dira au réflecteur qu'il faut lancer le jeu. 


## Fonctionnement

Une fois lancée, l'IA va se connecter au réflécteur et recevoir les différents messages THROWS. Elles va calculer les meilleurs coups possibles à partir de ces tuiles et va les placer en fonction. Elle s'arretera lorsque le septième round aura été atteint. 