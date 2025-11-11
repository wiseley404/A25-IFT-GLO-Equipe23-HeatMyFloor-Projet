# HEATMYFLOOR

## DESCRIPTION
Application de modélisation d'un système de plancher chauffant conçue essentiellement avec Java.

## Fonctionnalités actuelles
> NB : Les dimensions actuelles sont en pixels et notre origine (0, 0) se trouve en haut à gauche.

- Créer un nouveau projet
    - Dans la barre d'outils, cliquer sur "Nouveau" pour démarrer un nouveau projet.
    - Une pièce rectangulaire par défaut y est déjà ajoutée.
    - Plusieurs projets peuvent être réalisés en même temps via plusieurs onglets.

- Renommer un onglet
    - Faites un clic droit sur le nom actuel de l'onglet.
    - Choisissez "Renommer" dans le menu ouvert à l'écran.
    - Entrez le nouveau nom, puis confirmez.

- Fermer un onglet
    - Cliquer directement sur la croix à côté du nom de l'onglet en question.
    - OU via un clic droit, puis choisir "Fermer".

- Créer une pièce rectangulaire
    - Dans la barre d'outils, cliquer sur la forme "Rectangle".
    - Une pièce rectangulaire s'ajoute avec des dimensions par défaut.
    - Redimensionnez la pièce avec les dimensions souhaitées depuis le panneau de configuration.

- Redimensionner une pièce rectangulaire
    - Depuis le panneau de configuration, entrez vos nouvelles données.
    - Cliquez sur Enter et le redimensionnement s'applique.
    - Tout redimensionnement de la pièce implique un redimensionnement et repositionnement relatifs des meubles. 

- Créer une pièce irrégulière
    - Dans la barre d'outils, cliquer sur la forme "Irrégulière".
    - Un espace de dessin s'ouvre pour placer des points.
    - Placez vos points et le polygone se dessine.
    - Faites un double clic sur votre dernier point pour fermer le polygone.

- Visualiser une pièce
    - Depuis le canvas, vous pouvez directement visualiser la pièce et ses meubles.

- Ajouter des meubles avec ou sans drain dans une pièce rectangulaire seulement
    - Dans la barre d'outils, faites un clic sur Meuble avec ou sans drain.
    - Choisissez le meuble désiré, et celui-ci s'ajoute directement au centre de la pièce avec des dimensions par défaut.
    - Les données actuelles du meuble sont directement visibles depuis le panneau.
    - Vous pourrez ensuite redimensionner, repositionner, supprimer, etc.

- Redimensionner un meuble
    - Sélectionnez le meuble en question via un clic sur le meuble.
    - Modifiez les données visibles du meuble sélectionné depuis le panneau de configuration.
    - Cliquez sur Enter et le redimensionnement s'applique.

- Déplacer un meuble
    - Sélectionnez le meuble en question via un clic sur le meuble.
    - Modifiez la position du meuble sélectionné depuis le panneau de position.
    - Un message d'erreur s'affiche et la position est réinitialisée si la position entrée n'est pas dans la pièce.

- Afficher des erreurs
    - Une erreur s'affiche si le déplacement d'un meuble dépasse les limites de la pièce.

- Supprimer un meuble
    - Faites un clic pour sélectionner le meuble à supprimer.
    - Cliquez sur la touche "Backspace" ou "Delete" du clavier pour supprimer le meuble.
