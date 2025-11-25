package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.graphe.Graphe;
import com.heatmyfloor.domain.items.Drain;
import com.heatmyfloor.domain.items.DrainReadOnly;
import com.heatmyfloor.domain.items.MeubleAvecDrain;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.Serializable;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import com.heatmyfloor.domain.piece.PieceItemReadOnly;

/**
 *
 * @author Wily
 */
public abstract class Piece implements PieceReadOnly, Serializable {

    private List<PieceItem> itemsList;
    private List<Drain> drainList;
    private Graphe graphe;
    private List<Mur> murs;
    private double largeur;
    private double hauteur;
    private Point position;

    public Piece() {
        this.itemsList = new ArrayList<>();
        this.graphe = new Graphe();
        this.murs = new ArrayList<>();
        this.position = new Point();
        this.drainList = new ArrayList<>();
    }

    public Piece(double largeur, double hauteur, List<Mur> murs) {
        this.itemsList = new ArrayList<>();
        this.graphe = new Graphe();
        this.murs = murs;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.position = new Point();
        this.drainList = new ArrayList<>();
    }

    @Override
    public abstract boolean contientLePoint(Point position);

    @Override
    public abstract boolean contientLaForme(Shape itemRotation);

//    @Override
//    public abstract Point getCentre();
    private List<Point> sommets;

// ...
    @Override
    public Point getCentre() {
        if (sommets == null || sommets.isEmpty()) {
            return new Point(0, 0); // valeur par défaut, au cas où
        }

        double minX = sommets.get(0).getX();
        double maxX = minX;
        double minY = sommets.get(0).getY();
        double maxY = minY;

        for (Point p : sommets) {
            double x = p.getX();
            double y = p.getY();

            if (x < minX) {
                minX = x;
            }
            if (x > maxX) {
                maxX = x;
            }
            if (y < minY) {
                minY = y;
            }
            if (y > maxY) {
                maxY = y;
            }
        }

        double cx = (minX + maxX) / 2.0;
        double cy = (minY + maxY) / 2.0;

        return new Point(cx, cy);
    }

    public void ajouterItem(PieceItem item) {
        for (PieceItem i : itemsList) {
            i.setEstSelectionne(false);
        }
        if (this.contientLePoint(item.getPosition())) {
            item.setEstSelectionne(true);
            this.itemsList.add(item);
            
            if(item instanceof MeubleAvecDrain meuble){
                List<Drain> drains = meuble.getDrainList();
                for(Drain d : drains){
                    this.drainList.add(d);
                }    
            }
        }
    }

    public void changerStatutSelectionItem(Point position) {
        boolean itemTrouve = false;
        for (int i = itemsList.size() - 1; i >= 0; i--) {
            PieceItem item = itemsList.get(i);
            if (item.contientLePoint(position)) {
                for (PieceItem autre : itemsList) {
                    if (autre != item) {
                        autre.setEstSelectionne(false);
                    }
                }
                item.setEstSelectionne(true);
                itemTrouve = true;
                break;
            }
        }
        if (!itemTrouve) {
            for (PieceItem item : itemsList) {
                item.setEstSelectionne(false);
                if(item instanceof MeubleAvecDrain meuble){
                    for(Drain d : meuble.getDrainList()){
                        d.setEstSelectionne(false);
                    }
                }
            }
        }
    }
    public void changerStatutSelectionDrain(Point position) {
        Drain drain = this.trouverDrainSelectionne();
        MeubleAvecDrain meuble = drain.getMeuble();
        if (meuble != null) {
            boolean drainTrouve = false;
            for (Drain d : meuble.getDrainList()) {
                if (d.contientLePoint(position)) {
                    
                    for (Drain autre : meuble.getDrainList()) {
                        autre.setEstSelectionne(false);
                    }
                    d.setEstSelectionne(true);
                    drainTrouve = true;
                    break;
                }
            }

            if (drainTrouve) {
                
                meuble.setEstSelectionne(false);
            } else {
                
                meuble.setEstSelectionne(true);
                for (Drain autre : meuble.getDrainList()) {
                    autre.setEstSelectionne(false);
                }
            }
        }
    }



    public void changerAngleItemSelectionne(double nouvAngle) {
        PieceItem item = this.trouverItemSelectionne();

        if (item != null) {
            double ancienAngle = item.getAngle();
            item.setAngle(nouvAngle);
            if (!this.estPositionItemValide(item.getPosition())) {
                item.setAngle(ancienAngle);
                throw new IllegalArgumentException("Vous ne pouvez pas faire cette rotation aux bords de la pièce.");
            }
        }

    }

    public void pivoterItemSelectionne() {
        PieceItem item = this.trouverItemSelectionne();
        if (item != null) {
            double ancienAngle = item.getAngle();
            item.pivoter();
            if (!this.estPositionItemValide(item.getPosition())) {
                item.setAngle(ancienAngle);
                throw new IllegalArgumentException("Vous ne pouvez pas faire cette rotation aux bords de la pièce.");
            }
        }
    }

    public void ajouterDrain(Drain drain) {
        PieceItem item = this.trouverItemSelectionne();
        if(item != null && item instanceof MeubleAvecDrain meuble){
            meuble.ajouterDrain(drain);
        }
    }

    public void repositionnerDrainSelectionne(Point nouvPosition) {
        PieceItem item = this.trouverItemSelectionne();
        if (item instanceof MeubleAvecDrain meuble) {
            Drain drain = meuble.trouverDrainSelectionne();
            if (drain != null && estPositionDrainValide(nouvPosition.getX(), nouvPosition.getY())) {
                drain.setPosition(nouvPosition);
            }else{
                throw new IllegalArgumentException("Position du drain Invalide");
            }
        }
    }

    public void deplacerDrainSelectionne(Point delta) {
    PieceItem item = this.trouverItemSelectionne();
    if (!(item instanceof MeubleAvecDrain meuble)) return;

    meuble.deplacerDrainSelectionne(delta);
}


    public void deplacerDrain(double facteurX, double facteurY) {
    }

    public void redimensionnerDrainSelectionne(double nouvDiametre) {
        PieceItem item = trouverItemSelectionne();
        if (item instanceof MeubleAvecDrain meuble) {
            meuble.redimensionnerDrainSelectionne(nouvDiametre);
        }
    }

    public void redimensionnerDrainSelectionne(Point delta) {

    }

    public void redimensionnerDrain(double facteurX, double facteurY) {

    }

    public PieceItem trouverItemParId(UUID idItem) {
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }

    public boolean estPositionItemValide(Point itemPosition) {
        boolean valide = false;
        PieceItem item = this.trouverItemSelectionne();

        if (item != null && itemPosition != null) {
            Point p1 = itemPosition;
            Point p2 = new Point(itemPosition.getX() + item.getLargeur(), itemPosition.getY() + item.getHauteur());

            if (item.getAngle() == 0) {
                valide = contientLePoint(p1) && contientLePoint(p2);
                //Verification de la position de l'item en rotation
            } else {
                Rectangle2D formeTournee = new Rectangle2D.Double(itemPosition.getX(), itemPosition.getY(),
                        item.getLargeur(), item.getHauteur());
                AffineTransform transf = new AffineTransform();
                double angleRadian = Math.toRadians(item.getAngle());
                transf.rotate(angleRadian, formeTournee.getCenterX(), formeTournee.getCenterY());
                Shape itemRotation = transf.createTransformedShape(formeTournee);
                valide = this.contientLaForme(itemRotation);
            }

//            double x1 = itemPosition.getX();
//            double y1 = itemPosition.getY();
//            double x2 = x1 + item.getLargeur();
//            double y2 = y1 + item.getHauteur();
//
//            for (PieceItem autre : itemsList) {
//                if (autre == item) {
//                    continue;
//                }
//
//                double ax1 = autre.getPosition().getX();
//                double ay1 = autre.getPosition().getY();
//                double ax2 = ax1 + autre.getLargeur();
//                double ay2 = ay1 + autre.getHauteur();
//
//                boolean overlapX = x1 < ax2 && x2 > ax1;
//                boolean overlapY = y1 < ay2 && y2 > ay1;
//
//                if (overlapX && overlapY) {
//
//                    return false;
//
//                }
//
//            }
        }
        return valide;
    }

    public boolean estPositionDrainValide(double x, double y) {
        boolean valide = false;
        PieceItem item = trouverItemSelectionne();
        if (item instanceof MeubleAvecDrain meuble) {
            Drain d = meuble.trouverDrainSelectionne();
            if (d != null) {
                double posX = meuble.getPosition().getX() - d.getDiametre();
                double posY = meuble.getPosition().getY() - d.getDiametre();
                double largeur = meuble.getLargeur() + d.getDiametre();
                double hauteur = meuble.getHauteur() + d.getDiametre();
                Rectangle2D espaceDrain = new Rectangle2D.Double(posX, posY, largeur, hauteur);
                valide = espaceDrain.contains(x, y);
            }

        }
        return valide;
    }

    public boolean estItemPresent(UUID idItem) {
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }

    public boolean estVide() {
        return this.itemsList.isEmpty();
    }

    public void supprimerItemSelectionne() {
        if (itemsList.isEmpty()) {
            return;
        }

        for (var it = itemsList.iterator(); it.hasNext();) {
            PieceItem item = it.next();

            if (item.estSelectionne()) {
                it.remove();
                return;
            }
        }

    }

    public void deplacerItemSelectionne(Point nouvPosition) {

         if (nouvPosition == null) {
        return;
    }

    for (PieceItem it : itemsList) {
        if (it.estSelectionne()) {

            // Ancienne position
            Point oldPos = it.getPosition();

            // Delta du mouvement
            Point delta = new Point(
                nouvPosition.getX() - oldPos.getX(),
                nouvPosition.getY() - oldPos.getY()
            );

            // Vérifie si la position est valide
            if (estPositionItemValide(nouvPosition)) {

                // Déplace l’item principal
                it.translater(delta);

                // SI c’est un meuble avec drain → déplacer aussi le drain
                if (it instanceof MeubleAvecDrain mad) {
                    Drain drain = mad.trouverDrainSelectionne();

                    // si le drain est sélectionné automatiquement → il suit
                    if (drain != null) {
                        drain.translater(delta);
                    }
                }
            }
            break;
        }
    }
    }

    public void redimensionner(double nouvLarg, double nouvHaut) {
        double facteurX = nouvLarg / this.largeur;
        double facteurY = nouvHaut / this.hauteur;
        setLargeur(nouvLarg);
        setHauteur(nouvHaut);
        for (PieceItem item : this.itemsList) {
            item.redimensionner(facteurX, facteurY);
            item.translater(facteurX, facteurY);
        }
    }

    public void redimensionnerItemSelectionne(double nouvLarg, double nouvHauteur) {

        PieceItem itemSelectionne = trouverItemSelectionne();
        itemSelectionne.setDimension(nouvLarg, nouvHauteur);

    }

    public void genererGraphe(double distIntersections) {

    }

    public void appliquerGrapheTranslation(Point delta) {

    }

    public PieceItem trouverItemSelectionne() {
        for (PieceItem item : itemsList) {
            if (item.estSelectionne()) {
                return item;
            }
        }
        return null;
    }

    public PieceItem trouverCible(double xPouce, double yPouce) {

        Point p = new Point(xPouce, yPouce);
        for (PieceItem element : itemsList) {
            if (element.contientLePoint(p)) {

                return element;

            }
        }
        return null;
    }

    @Override
    public double getLargeur() {
        return this.largeur;
    }

    public void setLargeur(double nouvLarg) {
        this.largeur = nouvLarg;
    }

    @Override
    public double getHauteur() {
        return this.hauteur;
    }

    public void setHauteur(double nouvHaut) {
        this.hauteur = nouvHaut;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point nouvPosition) {
        this.position = nouvPosition;
    }

    public List<PieceItem> getItemsList() {
        return this.itemsList;
    }
    
    public List<Drain> getDrainList(){
        return this.drainList;
    }
    
    public void setDrainList(){
        List<PieceItem> items = this.getItemsList();
        for(PieceItem item : items){
            if(item instanceof MeubleAvecDrain meuble){
                for(Drain d : meuble.getDrainList()){
                    this.drainList.add(d);
                }
            }
        }
    }

    
    public Graphe getGraphe() {
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }

    public List<Mur> getMurs() {
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }

    @Override
    public Point getExtremiteHautGauche() {
        return position;
    }

    @Override
    public Point getExtremiteBasGauche() {
        double x = position.getX();
        double y = position.getY() + getHauteur();

        return new Point(x, y);

    }

    @Override
    public Point getExtremiteHautDroite() {
        double x = position.getX() + largeur;
        double y = position.getY();
        return new Point(x, y);
    }

    @Override
    public Point getExtremiteBasMilieu() {

        double x = position.getX() + (getLargeur() / 2.0);
        double y = position.getY() + getHauteur();
        return new Point(x, y);

    }

    @Override
    public Point getExtremiteHautMilieu() {

        double x = position.getX() + (getLargeur() / 2.0);
        double y = position.getY();
        return new Point(x, y);

    }

    @Override
    public Point getExtremiteBasDroite() {

        double x = position.getX() + getLargeur();
        double y = position.getY() + getHauteur();
        return new Point(x, y);

    }


    public Drain trouverDrainSelectionne() {
        for(Drain d : this.drainList){
            if(d.estSelectionne()){
                return d;
            }
        }
        return null;
    }

    /*public void repositionnerDrainSelectionne(Point nouvPosition,PieceItem item){
        
        Drain drain =(Drain) trouverDrainPourItem(item); // ou utiliser trouverDrainPourItem si tu as l'item
        if (drain == null) return;

            // calcul du delta
            Point delta = new Point(
                nouvPosition.getX() - drain.getPosition().getX(),
                nouvPosition.getY() - drain.getPosition().getY()
            );

            // déplacer le drain
            drain.translater(delta);
    }
     */
    public PieceItem trouverItemSurPoint(Point p) {
    List<PieceItem> items = getItemsList(); // ou itemsList
    for (int i = items.size() - 1; i >= 0; i--) { // du dessus vers le dessous
        PieceItem item = items.get(i);
        if (item.contientLePoint(p)) { // vérifie si le point est à l’intérieur du meuble
            return item;
        }
    }
    return null; // aucun item trouvé
}

}
