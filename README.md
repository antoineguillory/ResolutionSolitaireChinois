# Resolution d'un Solitaire Chinois - Projet d'études - Florent Durécu , Antoine Guillory, David Saude


## Description

Le solitaire chinois est un jeu à un seul joueur qui se joue avec 32 billes qui
sont disposées de manière à occuper toutes les cavités à exception de celle
centrale, d’un plateau à forme de croix, comme le montre la figure ci-dessous.

        • • •
        • • •
    • • • • • • •
    • • • • • • •
    • • • • • • •
        • • •
        • • •
        
Un coup consiste dans le déplacement d’une bille qui en saute une autre,
en horizontal ou en vertical mais pas en diagonal, en allant occuper la case
vide qui est située derrière. La bille sautée est retirée du plateau.
Le but de ce projet est de programmer par rebroussement, une intelligence
artificielle qui permette de résoudre le solitaire chinois où la position de
la case vide peut être choisie par l’utilisateur.

Notes de 2024 : Il est vraiment impropre de parler d'IA ici, c'était la fougue de la jeunesse. C'était simplement une résolution par heuristique, basée sur une fonction assez simple : la distance entre les pegs doit être la plus courte posssible.

Algo :
- On prend un pool de plateau n (au début, n = 1, il n'y qu'un seul trou)
- On calcule toute les possibilités avec une profondeur de 1 (si n=1, ça fait 4 max parfois moins)
- On retire les symétriques si il y en a
- On prend tout les plateaux calculés et on sélectionne les 20 meilleurs, càd ceux qui ont la distance entre les pegs la plus faible. en effet, avoir une concentration de peg, au lieu de peg isolé, renforce drastiquement la chance d'avoir un plateau résoluble.
- ces plateaux sont ajoutés au pool n+1


## Annexes

Projet réalisé en Java8 (JavaSE 1.8)
Outils logiciels utilisés : StarUML, Eclipse
Documents consultés et ou utilisés :
http://www.link.cs.cmu.edu/15859-s11/notes/peg-solitaire.pdf
https://c.developpez.com/defis/2-Solitaire/
