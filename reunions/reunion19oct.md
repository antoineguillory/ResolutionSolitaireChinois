# Réunion du 19/10
======
## Méthode brute : Objectif, finir cette méthode pour mi novembre

### Modèle mathématique

On considèrera une arborescence de possibilités. 
On prendra en compte les doubles sauts mais pas les n sauts car trop complexe.

#### Concept de l'algo 
Part dans un sens -> arrive au bout du chemin -> si pas de solution, revenir en arrière puis tester les autres branches

#### Remarque
Moyen de diviser par 4 les coups si symetrie dans la position initiale du pion (resp : plateau)

variable globale de liste de coups

#### objet avec cardinalité + état du pion

copier la liste de trous ? trop gros ?
Stocker des “coups” ? a voir

### IHM 

swing + Java 8
grid layout --> difficulté sera d'adapter notre solution mathématique avec l'IHM 
pattern adaptater ou map ? a voir
