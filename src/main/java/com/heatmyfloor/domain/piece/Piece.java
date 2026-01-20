package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.graphe.Graphe;
import com.heatmyfloor.domain.items.ElementChauffant;
import com.heatmyfloor.domain.items.MeubleAvecDrain;
import com.heatmyfloor.domain.items.Thermostat;
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
public abstract class Piece implements PieceReadOnly, Serializable {

    List<PieceItem> itemsList;
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
    public abstract Shape getForme();
    
    @Override
    public abstract boolean contientLaForme(Shape itemRotation);

    public abstract void mettreAJourMurs();
    
    @Override 
    public abstract List<Point> getSommets();

    private List<Point> sommets;
    
    @Override
    public abstract TypePiece getType();

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
        }
    }

    public void positionnerSurMurItemSelectionne(Mur mur) {
        PieceItem item = this.trouverItemSelectionne();
        if(item instanceof ElementChauffant elem) {
            elem.positionnerSurMur(mur, this);
        } else if (item instanceof Thermostat thermo) {
            thermo.positionnerSurMur(mur, this);
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

    public void deplacerDrain(Point delta) {
        PieceItem item = this.trouverItemSelectionne();
        if (!(item instanceof MeubleAvecDrain meuble)) {
            return;
        }
        meuble.deplacerDrain(delta);
    }

    public void repositionnerDrain(double x, double y) {
        PieceItem item = this.trouverItemSelectionne();
        if (!(item instanceof MeubleAvecDrain meuble)) {
            return;
        }
        meuble.repositionnerDrain(new Point(x, y));
    }

    public void deplacerDrain(double facteurX, double facteurY) {
        PieceItem item = this.trouverItemSelectionne();
        if (!(item instanceof MeubleAvecDrain meuble)) {
            return;
        }
        meuble.deplacerDrain(facteurX, facteurY);
    }

    public void redimensionnerDrain(double nouvDiametre) {
        PieceItem item = trouverItemSelectionne();
        if (item instanceof MeubleAvecDrain meuble) {
            meuble.redimensionnerDrain(nouvDiametre);
        }
    }

    public void redimensionnerDrain(Point delta) {
        PieceItem item = trouverItemSelectionne();
        if (item instanceof MeubleAvecDrain meuble) {
            meuble.redimensionnerDrain(delta);
        }
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
            Point p3 = new Point(itemPosition.getX(), itemPosition.getY() + item.getHauteur());
            Point p4 = new Point(itemPosition.getX() + item.getLargeur(), itemPosition.getY());
            valide = contientLePoint(p1) || contientLePoint(p2) || contientLePoint(p3) || contientLePoint(p4);
        }

        return valide;
    }

    public boolean estPositionDrainValide(double x, double y) {
        boolean valide = false;
        PieceItem item = trouverItemSelectionne();
        if (item instanceof MeubleAvecDrain meuble) {
            valide = meuble.estPositionDrainValide(new Point(x, y));
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
                Point oldPos = it.getPosition();
                // Delta du mouvement
                Point delta = new Point(
                        nouvPosition.getX() - oldPos.getX(),
                        nouvPosition.getY() - oldPos.getY()
                );

                // Vérifie si la position est valide
                if (estPositionItemValide(nouvPosition)) {
                    it.translater(delta);
                }
                break;
            }
        }
    }
    
    public void deplacerItemSelectionneEnDrag(Point position) {
        PieceItem item = trouverItemSelectionne();
        if (item == null) return;

        Point p = position;
        if (item instanceof Thermostat thermo && thermo.getMur() != null) {
            p = thermo.getMur().projetterPositionItemSurMur(p, thermo, this);
        } else if (item instanceof ElementChauffant elem && elem.getMur() != null) {
            p = elem.getMur().projetterPositionItemSurMur(p, elem, this);
        }
        if (!estPositionItemValide(p)) {
        return;
    }

        item.setPosition(p);
    }

    public void redimensionner(double nouvLarg, double nouvHaut) {
        double facteurX = nouvLarg / this.largeur;
        double facteurY = nouvHaut / this.hauteur;
        
        setLargeur(nouvLarg);
        setHauteur(nouvHaut);
        mettreAJourMurs();
        
        Point posPiece = this.position;
       
        for (PieceItem item : this.itemsList) {
            double xRelatif = item.getPosition().getX() - posPiece.getX();
            double yRelatif = item.getPosition().getY() - posPiece.getY();
            
            double nouvXRelatif = xRelatif*facteurX;
            double nouvYRelatif = yRelatif*facteurY;
            
            double xAbsolu = nouvXRelatif + posPiece.getX();
            double yAbsolu = nouvYRelatif + posPiece.getY();
            item.setPosition(new Point(xAbsolu, yAbsolu));
            if (item instanceof Thermostat thermo && thermo.getMur() != null){
                Point nouvPosition = thermo.getMur().projetterPositionItemSurMur(thermo.getPosition(), thermo, this);
                thermo.setPosition(nouvPosition);
            }else if(item instanceof ElementChauffant elem && elem.getMur() != null){
                Point nouvPosition = elem.getMur().projetterPositionItemSurMur(elem.getPosition(), elem, this);
                elem.setPosition(nouvPosition);
            }
        }

    }

    public void redimensionnerItemSelectionne(double nouvLarg, double nouvHauteur) {

        PieceItem itemSelectionne = trouverItemSelectionne();
        itemSelectionne.setDimension(nouvLarg, nouvHauteur);

    }

    public void genererGraphe(double distIntersections, double rayonIntersections) {
        setGraphe(new Graphe(distIntersections, rayonIntersections, this));
    }

    public void appliquerGrapheTranslation(Point delta) {
        this.graphe.translater(delta);
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
    
    @Override
    public Graphe getGraphe(){
        return this.graphe;
    }
    
    public void setGraphe(Graphe graphe){
        this.graphe = graphe;
    }

    public void setHauteur(double nouvHaut) {
        this.hauteur = nouvHaut;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point nouvPosition) {
        Point anciennePos = this.position;
        this.position = nouvPosition;
        mettreAJourMurs();
        
        double deltaX = nouvPosition.getX() - anciennePos.getX();
        double deltaY = nouvPosition.getY() - anciennePos.getY();
        for (PieceItem item : itemsList) {
            item.translater(new Point(deltaX, deltaY));
            Point nouvPos = new Point(
                item.getPosition().getX() + deltaX,
                item.getPosition().getY() + deltaY
            );
            if(item instanceof Thermostat th && th.getMur() != null){
                Point projPos = th.getMur().projetterPositionItemSurMur(nouvPos, th, this);
                th.setPosition(projPos);
            } else if (item instanceof ElementChauffant elem && elem.getMur() != null){
                Point projPos = elem.getMur().projetterPositionItemSurMur(nouvPos, elem, this);
                elem.setPosition(projPos);
            }
        }
    }

    public List<PieceItem> getItemsList() {
        return this.itemsList;
    }

    public List<Mur> getMurs() {
        return this.murs;
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

    public PieceItem trouverItemSurPoint(Point p) {
        List<PieceItem> items = getItemsList();
        for (int i = items.size() - 1; i >= 0; i--) {
            PieceItem item = items.get(i);
            if (item.contientLePoint(p)) {
                return item;
            }
        }
        return null;
    }

    public void setMurs(List<Mur> murs) {
        this.murs = murs;
    }

    

}


