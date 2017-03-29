package geneticsdisk;

import java.util.ArrayList;

public class DNA {
    public double fitness;
    public int[] genes;
//    static 
    
    public DNA(int length){
        this.genes = new int[length];
    }
    
    public void initializeGenes(){
        for(int i = 0; i<this.genes.length; i++){
            this.genes[i] = (int)(Math.random()*2);
        }
    }
    
    
    
    /*
    public void initializeGenes(){
        int r;
//        System.out.println(genes.length);
        
        ArrayList<Integer> gen = new ArrayList();
        for(int i = 0; i<this.genes.length; i++){
            do{
                r = (int)(Math.random()*GeneticsDisk.files.length);
            }while(gen.indexOf(r) != -1);
            gen.add(r);
            this.genes[i] = GeneticsDisk.files[r];
//            System.out.println("ADDED");
//            System.out.print(this.genes[i]+" ");
        }
//        System.out.println();
    }
    */
    
    
    
    /*
    public void initializeGenes(){
        for(int i = 0; i<this.genes.length; i++){
//              FOR BINARY ARRAY ONLY
//            genes[i] = (int)(Math.random()*2);
//              FOR CHARACTER ARRAY ONLY
                genes[i] = (int)(Math.random()*(126 - 32)+32);



//            System.out.print(genes[i]);
        }
//        System.out.println();
    }
    */
    
    public void calcFitness(){
        double sum = 0;
        for(int i = 0; i<this.genes.length; i++){
            if(this.genes[i] == 1)
                sum += GeneticsDisk.files[i];
        }
        this.fitness = Math.abs(sum - GeneticsDisk.diskSize)/GeneticsDisk.diskSize;
    }
    
    
    
    public double calcSum(){
        double sum = 0;
        for(int i = 0; i<this.genes.length; i++)
            if(this.genes[i] == 1)
                sum += GeneticsDisk.files[i];
        
        return sum;
    }
    
    public DNA crossover(DNA parentB){
        DNA parents[] = {this, parentB};
        int rParent = (int)(Math.random()*2);
        
        DNA child = new DNA(this.genes.length);
        int midpoint = this.genes.length/2 + 1;
        
        for(int i = 0; i < child.genes.length; i++){
            if(i < midpoint){
                child.genes[i] = parents[rParent].genes[i];
            }else{
                child.genes[i] = parents[1-rParent].genes[i];
            }
        }
        return child;
    }
    
    
    public void mutate(double mutationRate){
        for(int i = 0; i<this.genes.length; i++){
            double random = Math.random();
            if(random < GeneticsDisk.mutationRate){
                genes[i] = 1-genes[i];
            }
        }
    }
    
    public boolean checkIfNull(){
        if(this.genes == null)
            return true;
        
        return false;
    }
    
    
    @Override
    public String toString(){
        String ret = "";
        for(int i = 0; i<genes.length; i++){
            ret += genes[i]+" ";
        }
        return ret;
    }
}
