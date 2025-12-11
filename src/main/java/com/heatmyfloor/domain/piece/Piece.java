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

    public abstract void mettreAJourMurs();

    private List<Point> sommets;
    
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
        Point anciennePosition = item.getPosition();
        double ancienAngle = item.getAngle();

        if (item instanceof ElementChauffant) {
            ((ElementChauffant) item).positionnerSurMur(mur, this);
            if (!this.contientLaForme(item.getRotationForme())) {
                item.setPosition(anciennePosition);
                item.setAngle(ancienAngle);
                ((ElementChauffant) item).setMur(null);

            }
        } else if (item instanceof Thermostat) {
            ((Thermostat) item).positionnerSurMur(mur, this);
            if (!this.contientLaForme(item.getRotationForme())) {
                item.setPosition(anciennePosition);
                item.setAngle(ancienAngle);
                ((Thermostat) item).setMur(null);
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
        //double facteurX = nouvPosition.getX() - this.position.getX();
        //double facteurY = nouvPosition.getY() - this.position.getY();
        this.position = nouvPosition;
        //mettreAJourMurs();
        for (PieceItem item : itemsList) {

            if (item instanceof Thermostat th) {
                th.positionnerSurMur(th.getMur(), this);
            } else if (item instanceof ElementChauffant ec) {
                ec.positionnerSurMur(ec.getMur(), this);
            } else {
                //item.translater(new Point(facteurX, facteurY));
            }
        }
    }

    public List<PieceItem> getItemsList() {
        return this.itemsList;
    }

    public Graphe getGraphe() {
        throw new UnsupportedOperationException("Méthode non implémentée !");
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


