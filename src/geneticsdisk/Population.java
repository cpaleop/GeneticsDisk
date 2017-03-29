package geneticsdisk;

public class Population {
    
    public DNA[] population;
    private final int GENES_SIZE = GeneticsDisk.TARGET.length;
    
    public Population(){}
    public Population(int size){
        this.population = new DNA[size];
        for(int i = 0; i<population.length; i++)
            population[i] = new DNA(GENES_SIZE);
    }
    
    // Initializing population
    public void startingPopulation(){
        for(int i = 0; i<population.length; i++){
            population[i].initializeGenes();
        }
    }
    
    // Calculating fitness of each candidate
    public void calcFitness(){
        for(int i = 0; i<population.length; i++){
            population[i].calcFitness();
        }
    }
    
    public double calcSum(){
        double sum = 0;
        for(int i = 0; i<this.population.length; i++)
            sum += this.population[i].calcSum();
        
        return sum;
    }
    
    
    public DNA[] selection(){
        
        DNA[] parents = new DNA[2];
        double min1 = 1;
        for(int i = 0; i<this.population.length; i++){
            if(this.population[i].fitness < min1){
                parents[0] = this.population[i];
                min1 = this.population[i].fitness;
            }
        }
        
        
        double min2 = 1;
        for(int i = 0; i<this.population.length; i++){
            if(this.population[i].fitness < min2 && this.population[i].fitness >= min1){
                parents[1] = this.population[i];
                min2 = this.population[i].fitness;
            }
        }
        
        return parents;
    }
    
    
    public void generate(){
        
        
        for(int i = 0; i<this.population.length; i++){
            
            
            DNA parentA = this.selection()[0];
            DNA parentB = this.selection()[1];
            
            DNA child = parentA.crossover(parentB);
            child.mutate(GeneticsDisk.mutationRate);
            
            
            this.population[i] = child;
        }
    }
    
    public double getAverageFitness(){
        double sum = 0;
        
        for(int i = 0; i<this.population.length; i++){
            sum += this.population[i].fitness;
        }
        
        return sum;
    }
    
    
    public boolean solutionExists(){
        for(int i = 0; i<this.population.length; i++){
            if(this.population[i].fitness == 0)
                return true;
        }
        return false;
    }
    
    public DNA getSolution(){
        if(this.solutionExists()){
            for(int i = 0; i<this.population.length; i++)
                if (this.population[i].fitness == 0)
                return this.population[i];
        }
        
        return null;
    }
    
    public DNA getBestFit(){
        DNA max = this.population[0];
        
        for(int i = 0; i<this.population.length; i++){
            if(this.population[i].fitness > max.fitness)
                max = this.population[i];
        }
        return max;
    }
    
    public void getFileSolution(){
            for(int j = 0; j<this.getSolution().genes.length; j++){
                if(this.getSolution().genes[j] == 1)
                    System.out.print(GeneticsDisk.files[j]+" ");
            }
    }
    
    public double getFileSolutionSum(){
        double sum = 0;
        for(int j = 0; j<this.getSolution().genes.length; j++){
            if(this.getSolution().genes[j] == 1)
                sum += GeneticsDisk.files[j];
        }
        return sum;
    }
    
    @Override
    public String toString(){
        String ret = "";
        for(int i = 0; i<this.population.length; i++){
            ret += this.population[i];
        }
        
        return ret;
    }
}
