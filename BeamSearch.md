Le beam search, l'heuristique et le solitaire
==


J'ai fais deux trois recherches sur le lien qu'Antoine à filé, 
et je vais essayer de résumer ce que j'ai compris. 

Lien d'Antoine https://c.developpez.com/defis/2-Solitaire/
Un autre lien pas mal http://nico.maisonneuve.free.fr/download/solitaire.pdf

C'est la solution du vainqueur, Sylvain Togni, qui va nous intéresser.
Je copie colle ça ici :


	
    fonction beamSearch (p1:plateau, p2:plateau, taille:entier) : chemin
        début
            b1:beam
			n:entier
            b1 <- p1
			pour n décroissant de nombreDePions(p1) à nombreDePions(p2)+1 faire
			    b2:beam
				pour tous noeud dans b1 faire
					pour tous coup valide faire
						b2 <- b2 + coup
					fpour
				fpour
				échange(b1, b2)
			fpour
			si b1 contient p2
				retourne chemin(p1, p2);
			fsi
        fin
	
	fonction rechercheChemin (p1:plateau, p2:plateau) : chemin
        début
            taille : entier
            taille <- 1
            tant que !beamSearch(p1, p2, taille)
                taille <- taille*2
            fin
        fin


La notion de Beam
==


L'algorithme part du principe qu'on calcule toute les possibilités à un coup,
puis qu'un calcul heuristique en "choisi" un certain nombre parmis eux.
Il faut penser par étage en fait, ou par arbre, ici le cinquième étage
(arbre de hauteur 5) correspond à un plateau obtenable avec 5 mouvements.

Ici pas question de tout retenir toute les positions, on garde uniquement
celles qui sont "bonne" au sens heuristique du terme. C'est à dire un nombre
qu'on retient avec, je cite : les valeurs les plus faibles des sommes
des carrés d'écart types de coordonné des pions.


Idée d'implantation
==


Je précise d'avance que même si cette méthode est plus intéressante, avant
de continuer, je suis contre son utilisation à ce stade du projet. ça pourra
nous apporter quelque chose de mettre en évidence des pistes d'améliorations
très intéressantes plus tard. Eventuellement cela dit, je vais vous donner 
mon avis sur une éventuelle implantation du truc.

Une des idées serait de garder notre structure telle quelle, sauf qu'on
repense path, pour qui puisse générer une arborescence. Un algorithme
(surement dans le path) trie les différentes solution et choisi les plus
"pertinantes". On aurait plusieurs tableau et pièces, mais pas forcément 
dans un nombre démentiel.


Bref, si vous avez la moindre remarque hésitez pas. 
