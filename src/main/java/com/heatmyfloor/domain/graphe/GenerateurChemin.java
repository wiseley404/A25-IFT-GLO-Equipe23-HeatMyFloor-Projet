 /* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author petit
 */
package com.heatmyfloor.domain.graphe;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.piece.Piece;
import com.heatmyfloor.domain.piece.PieceItem;
import com.heatmyfloor.domain.items.Thermostat;
import java.io.Serializable;
import java.util.*;

public class GenerateurChemin implements Serializable{
    private static final long serialVersionUID = 4523369905546364818L;
    private static final double PIXEL_PAR_POUCE = 4.5;
    private static final double LONGUEUR_MAX_SEGMENT_POUCES = 10 * 12; 
    private static final double LONGUEUR_MAX_SEGMENT = LONGUEUR_MAX_SEGMENT_POUCES * PIXEL_PAR_POUCE;
    
    private Graphe graphe;
    private double espacementMinimal; 
    
    public GenerateurChemin(Graphe graphe) {
        this.graphe = graphe;
    }

    public Chemin genererChemin(double longueurFilDesire, double distanceEntreFilsDesire, Piece piece) {
        this.espacementMinimal = distanceEntreFilsDesire;
        
        List<Intersection> intersectionsValides = graphe.getListIntersectionsValide(piece);
        
        if (intersectionsValides.isEmpty()) {
            return null;
        }
        
        // Trouver le point de départ (thermostat)
        Intersection pointDepart = trouverThermostat(intersectionsValides, piece);
        if (pointDepart == null) {
            return null;
        }

        Chemin chemin = genererCheminAvecPathfinding(intersectionsValides, pointDepart, longueurFilDesire);   
        if (chemin == null) {
            return null;
        }
        if (!validerChemin(chemin)) {
            return null;
        }
        return chemin;
    }
    

    private Intersection trouverThermostat(List<Intersection> intersections, Piece piece) {
        Thermostat thermostat = null;   
        for (PieceItem item : piece.getItemsList()) {
            if (item instanceof Thermostat t) {
                thermostat = t;
                break;
            }
        }
        if (thermostat == null) {
            return null;
        }
        
        Point posThermostat = thermostat.getPosition();
        Intersection meilleure = null;
        double distanceMin = Double.MAX_VALUE;
        
        for (Intersection inter : intersections) {
            Point posInter = inter.getCoordonees();
            double dx = posInter.getX() - posThermostat.getX();
            double dy = posInter.getY() - posThermostat.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            
            if (distance < distanceMin) {
                distanceMin = distance;
                meilleure = inter;
            }
        }
        return meilleure;
    }
    

    private Chemin genererCheminAvecPathfinding(List<Intersection> intersections,
                                                Intersection depart,
                                                double longueurDesire) {
        Chemin chemin = new Chemin();
        chemin.ajouter(depart);
        
        Intersection courant = depart;
        double longueurActuelle = 0;
        double longueurSegmentDroit = 0; 
        Set<Intersection> intersectionsVisitees = new HashSet<>();
        intersectionsVisitees.add(depart);
        
        while (longueurActuelle < longueurDesire * 0.95 && intersectionsVisitees.size() < intersections.size()) {
            List<IntersectionVoisins> voisinsValides = trouvierVoisinsValides(
                courant, intersections, intersectionsVisitees, chemin, longueurSegmentDroit
            );
            
            if (voisinsValides.isEmpty()) {
                break;
            }
            
            // Prendre le plus proche parmi les valides
            IntersectionVoisins meilleur = voisinsValides.get(0);
            Intersection prochaine = meilleur.intersection;
            double longueurSegment = meilleur.distance; 

            if (longueurSegment > LONGUEUR_MAX_SEGMENT) {
                break;
            }
            chemin.ajouter(prochaine);
            longueurActuelle += longueurSegment;
            longueurSegmentDroit += longueurSegment;
            intersectionsVisitees.add(prochaine);
            courant = prochaine;
           
            if (longueurSegmentDroit >= LONGUEUR_MAX_SEGMENT * 0.95) {
                longueurSegmentDroit = 0;
            }
        }
        
        return chemin.getParcours().size() > 1 ? chemin : null;
    }
    

    private List<IntersectionVoisins> trouvierVoisinsValides(Intersection courant,
                                                               List<Intersection> toutes,
                                                               Set<Intersection> visited,
                                                               Chemin cheminActuel,
                                                               double longueurSegmentDroit) {
        List<IntersectionVoisins> voisins = new ArrayList<>();
        
        for (Intersection inter : toutes) {
            if (visited.contains(inter)) {
                continue;
            }
            double distance = calculerDistance(courant, inter);
            if (distance < espacementMinimal) {
                continue;
            }
            if (distance > LONGUEUR_MAX_SEGMENT) {
                continue;
            }
            if (!respecteEspacementAvecPasses(inter, cheminActuel)) {
                continue;
            }

            if (longueurSegmentDroit > 0) {
                if (longueurSegmentDroit + distance > LONGUEUR_MAX_SEGMENT * 1.05) {
                    continue;
                }
            }
            voisins.add(new IntersectionVoisins(inter, distance));
        }
        voisins.sort((a, b) -> Double.compare(a.distance, b.distance));
        
        return voisins;
    }
    

    private boolean respecteEspacementAvecPasses(Intersection candidate, Chemin cheminActuel) {
        List<Intersection> parcours = cheminActuel.getParcours();
        Point posCand = candidate.getCoordonees();
        
        if (parcours.size() < 2) {
            return true;
        }

        for (int i = 0; i < parcours.size() - 1; i++) {
            Point p1 = parcours.get(i).getCoordonees();
            Point p2 = parcours.get(i + 1).getCoordonees();

            double distPerp = distancePointSegment(posCand, p1, p2);
            if (distPerp < espacementMinimal * 0.9) {
                return false;
            }
        }
        
        return true;
    }
    

    private double distancePointSegment(Point p, Point a, Point b) {
        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();
        
        if (dx == 0 && dy == 0) {
            return calculerDistance(new Intersection(p), new Intersection(a));
        }
        
        double t = ((p.getX() - a.getX()) * dx + (p.getY() - a.getY()) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t));
        
        double projX = a.getX() + t * dx;
        double projY = a.getY() + t * dy;
        
        return Math.sqrt((p.getX() - projX) * (p.getX() - projX) + 
                        (p.getY() - projY) * (p.getY() - projY));
    }
    

    public boolean validerChemin(Chemin chemin) {
        List<Intersection> parcours = chemin.getParcours();
        
        if (parcours.size() < 2) {
            return false;
        }

        for (int i = 0; i < parcours.size() - 1; i++) {
            double longueur = calculerDistance(parcours.get(i), parcours.get(i + 1));
            
            if (longueur > LONGUEUR_MAX_SEGMENT) {
                return false;
            }
        }
        
        if (ilYACroisement(parcours)) {
             return false;
        }
        if (!validerEspacementGlobal(parcours)) {
            return false;
        }
        
        return true;
    }
    

    private boolean validerEspacementGlobal(List<Intersection> parcours) {
        for (int i = 0; i < parcours.size(); i++) {
            for (int j = i + 3; j < parcours.size(); j++) {
                double distDirecte = calculerDistance(parcours.get(i), parcours.get(j)); 
                
                if (distDirecte < espacementMinimal * 0.5) {
                    return false;
                }
            }
        }
        return true;
    }
    

    private boolean ilYACroisement(List<Intersection> parcours) {
        for (int i = 0; i < parcours.size() - 2; i++) {
            for (int j = i + 2; j < parcours.size() - 1; j++) {
                Point p1 = parcours.get(i).getCoordonees();
                Point p2 = parcours.get(i + 1).getCoordonees();
                Point p3 = parcours.get(j).getCoordonees();
                Point p4 = parcours.get(j + 1).getCoordonees();
                
                if (segmentsCroisent(p1, p2, p3, p4)) {
                    return true;
                }
            }
        }
        return false;
    }
    

    private boolean segmentsCroisent(Point p1, Point p2, Point p3, Point p4) {
        double d1 = orientation(p1, p2, p3);
        double d2 = orientation(p1, p2, p4);
        double d3 = orientation(p3, p4, p1);
        double d4 = orientation(p3, p4, p2);
        
        return ((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
               ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0));
    }
    

    private double orientation(Point p, Point q, Point r) {
        return (q.getY() - p.getY()) * (r.getX() - q.getX()) - 
               (q.getX() - p.getX()) * (r.getY() - q.getY());
    }
    

    private double calculerDistance(Intersection inter1, Intersection inter2) {
        Point p1 = inter1.getCoordonees();
        Point p2 = inter2.getCoordonees();
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    

    public boolean modifierDirection(Chemin chemin, int indexIntersection,
                                     Intersection nouvelleIntersection, Piece piece) {
        List<Intersection> parcours = chemin.getParcours();
        
        if (indexIntersection < 0 || indexIntersection >= parcours.size()) {

            return false;
        }
        parcours.set(indexIntersection, nouvelleIntersection);
        return recalculerCheminDepuisPoint(chemin, indexIntersection, piece);
    }
    

    private boolean recalculerCheminDepuisPoint(Chemin chemin, int indexPoint, Piece piece) {
        List<Intersection> parcours = chemin.getParcours();
        List<Intersection> intersectionsDisponibles = graphe.getListIntersectionsValide(piece);
        
        if (intersectionsDisponibles.isEmpty()) {
            return false;
        }
        
        List<Intersection> nouveauParcours = new ArrayList<>();
        for (int i = 0; i <= indexPoint && i < parcours.size(); i++) {
            nouveauParcours.add(parcours.get(i));
        }
        
        Intersection courant = parcours.get(indexPoint);
        Set<Intersection> visited = new HashSet<>(nouveauParcours);
        
        while (nouveauParcours.size() < parcours.size()) {
            Intersection meilleur = null;
            double distanceMin = Double.MAX_VALUE;
            
            for (Intersection inter : intersectionsDisponibles) {
                if (!visited.contains(inter)) {
                    double dist = calculerDistance(courant, inter);
                    if (dist < distanceMin && dist <= LONGUEUR_MAX_SEGMENT) {
                        distanceMin = dist;
                        meilleur = inter;
                    }
                }
            }
            
            if (meilleur == null) {
                return false;
            }
            
            nouveauParcours.add(meilleur);
            visited.add(meilleur);
            courant = meilleur;
        }
        
        parcours.clear();
        parcours.addAll(nouveauParcours);
        
        return true;
    }
    
    private static class IntersectionVoisins {
        Intersection intersection;
        double distance;
        
        IntersectionVoisins(Intersection i, double d) {
            this.intersection = i;
            this.distance = d;
        }
    }
}
