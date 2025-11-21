package com.heatmyfloor.domain.piece;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.graphe.Graphe;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.Serializable;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
/**
 *
 * @author Wily
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
public abstract class Piece implements PieceReadOnly {

    private List<PieceItem> itemsList;
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
    }

    public Piece(double largeur, double hauteur, List<Mur> murs) {
        this.itemsList = new ArrayList<>();
        this.graphe = new Graphe();
        this.murs = murs;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.position = new Point();
    }

    @Override
    public abstract boolean contientLePoint(Point position);

    @Override
    public abstract boolean contientLaForme(Shape itemRotation);
    
    @Override
    public abstract Point getCentre();

    public void ajouterItem(PieceItem item) {
        for (PieceItem i : itemsList) {
            i.setEstSelectionne(false);
        }
        if (this.contientLePoint(item.getPosition())) {
            item.setEstSelectionne(true);
            this.itemsList.add(item);
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
            }
        }
    }
    
    public void changerAngleItemSelectionne(double nouvAngle){
        PieceItem item = this.trouverItemSelectionne();
        if(item != null){
            double ancienAngle = item.getAngle();
            item.setAngle(nouvAngle);
            if(!this.estPositionItemValide(item.getPosition())){
                item.setAngle(ancienAngle);
                throw new IllegalArgumentException("Vous ne pouvez pas faire cette rotation aux bords de la pièce.");
            }
        }
    }
    
    public void pivoterItemSelectionne(){
        PieceItem item = this.trouverItemSelectionne();
        if (item != null){
            double ancienAngle = item.getAngle();
            item.pivoter();
            if(!this.estPositionItemValide(item.getPosition())){
                item.setAngle(ancienAngle);
                throw new IllegalArgumentException("Vous ne pouvez pas faire cette rotation aux bords de la pièce.");
            }
        }
    }
    


    public void ajouterDrain(Point position) {

    }

    public void repositionnerDrainSelectionne(Point nouvPosition) {

    }

    public void deplacerDrainSelectionne(Point delta) {

    }

    public void deplacerDrain(double facteurX, double facteurY) {
    }

    public void redimensionnerDrainSelectionne(double nouvDiametre) {

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
        
        if(item != null && itemPosition != null){
            Point p1 = itemPosition;
            Point p2 = new Point(itemPosition.getX() + item.getLargeur(), itemPosition.getY() + item.getHauteur());
            
            if(item.getAngle() == 0){
                valide = contientLePoint(p1) && contientLePoint(p2);
                //Verification de la position de l'item en rotation
            } else{
                Rectangle2D formeTournee = new Rectangle2D.Double(itemPosition.getX(), itemPosition.getY(),
                                                                  item.getLargeur(), item.getHauteur());
                AffineTransform transf = new AffineTransform();
                double angleRadian = Math.toRadians(item.getAngle());
                transf.rotate(angleRadian, formeTournee.getCenterX(), formeTournee.getCenterY());
                Shape itemRotation = transf.createTransformedShape(formeTournee);
                valide = this.contientLaForme(itemRotation);
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

                //valide la position
                if (estPositionItemValide(nouvPosition)) {
                    it.setPosition(nouvPosition);
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

    @JsonIgnore
    public Graphe getGraphe() {
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }

    @JsonIgnore
    public List<Mur> getMurs() {
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
}
