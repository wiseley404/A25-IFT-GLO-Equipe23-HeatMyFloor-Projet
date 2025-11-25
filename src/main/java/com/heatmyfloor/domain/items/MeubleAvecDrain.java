/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.items;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.piece.PieceItem;


/**
 *
 * @author petit
 */
public class MeubleAvecDrain extends PieceItem {
    
    //Attributs
    
    private List<Drain> drainList;
    private final double distanceAvecFil;
    private TypeAvecDrain type;
    private Drain drain;
    
    //Constructeur
    
    public MeubleAvecDrain(double largeur, double hauteur, Point pos, TypeAvecDrain type){
        super(largeur, hauteur, pos, type.getImage());
        this.drainList = new ArrayList<>(List.of(
                new Drain(40.0,
                new Point(this.getItemForme().getCenterX()-20,this.getItemForme().getCenterY()-20)
             )    
           
            )
        );
        this.distanceAvecFil = 0.0;
        this.type = type;
    }
    
    
    //Méthodes
    
    public void ajouterDrain(Drain drain){
        for(Drain d : drainList)
        {
            d.setEstSelectionne(false);
        }
        drain.setEstSelectionne(true);
        this.drainList.add(drain);
        drain.setMeuble(this);
    }
    
    @Override
    public void translater(double facteurX, double facteurY) {
    // Déplacer le meuble (hérité de PieceItem)
    super.translater(facteurX, facteurY);

    // Déplacer les drains du meuble
        for (Drain d : drainList) {
            d.translater(facteurX, facteurY);
         }
    }
    
    @Override
        public void translater(Point delta) {
            // Déplace le meuble
            super.translater(delta);
            for (Drain d : drainList) {
                    d.translater(delta);
                }
        }
    
    public Drain trouverDrain(UUID idDrain){
        
        throw new UnsupportedOperationException("trouverDrain non implémentée");
    }
    
    public Drain trouverDrainSelectionne(){
        for(Drain drain : drainList){
            if(drain.estSelectionne()){
                return drain;   
            }
        }
        return null;
    }
    
    public void repositionnerDrainSelectionne(Point nouvPos){
      Drain d = this.trouverDrainSelectionne();
    if (d != null) {
        
        d.setPosition(nouvPos);
    }   
    }
    public void deplacerDrain(double facteurX, double facteurY){}
    
   
    public void deplacerDrainSelectionne(Point delta) {
        Drain drain = this.trouverDrainSelectionne();
        if (drain == null) return;

        double newX = drain.getPosition().getX() + delta.getX();
        double newY = drain.getPosition().getY() + delta.getY();

        // Coordonnées du meuble
        double xMin = this.getPosition().getX();
        double yMin = this.getPosition().getY();
        double xMax = xMin + this.getLargeur();
        double yMax = yMin + this.getHauteur();

        double radius = drain.getDiametre() / 2;

        // Vérifie que le drain reste au moins partiellement sur le meuble
        boolean partiellementSurMeuble =
            (newX + radius > xMin) && (newX - radius < xMax) &&
            (newY + radius > yMin) && (newY - radius < yMax);

        if (partiellementSurMeuble) {
            drain.setPosition(new Point(newX, newY));
        }
    }

   
    public boolean estPositionDrainValide(Point pos){
        throw new UnsupportedOperationException("estPositionDrainValide non implémentée");
    }
    
    public boolean estDrainPresent(UUID idDrain){
        throw new UnsupportedOperationException("estDrainPresent non implémentée");
    }
    
    public void redimensionnerDrainSelectionne(double nouvDiametre){
        Drain drain = this.trouverDrainSelectionne();
        if (drain != null) {
          //double ancien=  drain.getDiametre();
          //double facteur = nouvDiametre / ancien;
          drain.setDiametre(nouvDiametre);
          
          drain.setDiametre(nouvDiametre);
          //drain.translater(facteur, facteur);
        }
    }
    
    @Override
public void redimensionner(double facteurX, double facteurY) {
    super.redimensionner(facteurX, facteurY);

    // repositionner drain pour qu’il reste centré
    for (Drain d : drainList) {
        double nouvX = this.getItemForme().getCenterX() - d.getDiametre() / 2;
        double nouvY = this.getItemForme().getCenterY() - d.getDiametre() / 2;
        d.setPosition(new Point(nouvX, nouvY));
    }
}
    public void redimensionnerDrainSelectionne(Point delta){
    
    }
    public void redimensionnerDrain(double facteur){}
    
    public void supprimerDrainSelectionne(){
      Drain d = this.trouverDrainSelectionne();
    if (d != null) {
        drainList.remove(d);
    }}

    
    public List<Drain> getDrainList(){
        return this.drainList;
    }
    
    
    public double getDistanceAvecFil(){
        return this.distanceAvecFil;
    }
    
    
    public TypeAvecDrain getType(){
        return this.type;
    }
    
    public Drain trouverDrainSousPoint(Point pos) {
    for (Drain d : drainList) {
        if (d.contientLePoint(pos)) {
            return d;
        }
    }
    return null;
}

}
