package geneticsdisk;

public class Population {
    
    public DNA[] population;
    public double similarity;
    private final int GENES_SIZE = GeneticsDisk.files.length;
    
    public Population(){}
    public Population(int size){
        this.population = new DNA[size];
        for(int i = 0; i<population.length; i++)
            population[i] = new DNA(GENES_SIZE);
    }
    
    // Initializing population
    public void startingPopulation(){
        
        /* CREATING A CHROMOSOME WITH ALL ZEROS */
        for(int i = 0; i<this.population[0].genes.length; i++)
            this.population[0].genes[i] = 0;
        
        /* CREATING A CHROMOSOME WITH ALL ONES */
        for(int i = 0; i<this.population[0].genes.length; i++)
            this.population[0].genes[i] = 1;
        
        for(int i = 2; i<this.population.length; i++){
            this.population[i].initializeGenes();
        }
    }
    
    // Calculating fitness of each candidate
    public void calcFitness(){
        for(int i = 0; i<population.length; i++){
            population[i].calcFitness();
        }
//        calcMutationRate();
    }
    
    // CALCULATING MUTATION RATE FOR NEXT POPULATION
    public void calcMutationRate(){
        
        DNA temp;
        for(int i = 0; i<this.population.length; i++){
            temp = this.population[i];
            int sum = 0;
            
            for(int j = i+1; j<this.population.length; j++){
                if(this.population[j].genes == this.population[i].genes){
                    sum++;
                    //System.out.println("Similarity"+((double)sum/this.population.length));
                    System.out.println("Similarity"+sum);
                }
            }
            
        }
    }
    
    // CALCULATING GENERATIONS FILE SIZE
    public double calcSum(){
        double sum = 0;
        for(int i = 0; i<this.population.length; i++)
            sum += this.population[i].calcSum();
        
        return sum;
    }
    
    /*  SELECTION - SELECTING THE BEST 2 CHROMOSOMES FOR PARENTS
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
    }*/
    
    /* ROULETTE WHEEL SELECTION */
    public DNA selection(){
        DNA ret = null;
        
        
        int sum = 0;
        int startingIndex  = 0, chromosomeWheelSize,j;
        
        for(int i = 0; i<this.population.length; i++){
            double fitness = this.population[i].fitness;
            
            if(fitness > 1){
//                System.out.println("FITNESS BEFORE: "+fitness);
                fitness = fitness * 0.5;
//                System.out.println("FITNESS AFTER: "+fitness);
            }
            sum += (int)Math.round( (fitness / this.getSumFitness() )*100);
        }
        
//        System.out.println("SUMSUM: "+sum);
        
        DNA[] wheel = new DNA[sum];
        sum = 0;
        
        for(int i = 0; i<this.population.length; i++){
            double fitness = this.population[i].fitness;
            
            if(fitness > 1 ){
//                System.out.println("FITNESS BEFORE: "+fitness);
                fitness = fitness * 0.5;
//                System.out.println("FITNESS AFTER: "+fitness);
            }
            chromosomeWheelSize = (int)Math.round( (fitness / this.getSumFitness() )*100);
            
            for(j = sum; j<sum + chromosomeWheelSize; j++){
                wheel[j] = this.population[i];
            }
            sum += chromosomeWheelSize;
        }
//        System.out.println("JKHGASKJH: "+sum);
        int r = (int)(Math.random()*sum);
        
        
        return wheel[r];
    }
    
    
    public void generate(){
        
        for(int i = 0; i<this.population.length; i++){
            
            
            DNA parentA = this.selection();
            DNA parentB;
//            do{
                parentB = this.selection();
//                System.out.println((parentA.genes == parentB.genes));
//            }while(parentA.genes == parentB.genes);
//            System.out.print("parentA: "+parentA.fitness);
//            System.out.println("\tparentB: "+parentB.fitness);
            
            DNA child = parentA.crossover(parentB);
            child.mutate(GeneticsDisk.mutationRate);
            
            
            this.population[i] = child;
        }
    }
    
    public double getSumFitness(){
        double sum =0;
        
        for(int i = 0; i<this.population.length; i++){
            sum += this.population[i].fitness;
        }
        
        return sum;
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
            if(this.population[i].fitness == 1)
                return true;
        }
        return false;
    }
    
    public DNA getSolution(){
        if(this.solutionExists()){
            for(int i = 0; i<this.population.length; i++)
                if (this.population[i].fitness == 1)
                    return this.population[i];
        }
        
        return null;
    }
    
    public DNA getBestFit(){
        DNA max = this.population[0];
        DNA ret = null;
        double ideal = 1;
        
        for(int i = 0; i<this.population.length; i++){
            if(1 - this.population[i].fitness < ideal && this.population[i].fitness <= 1){
//            if(this.population[i].fitness < max.fitness && this.population[i].fitness >= 0){
                ideal = 1 - this.population[i].fitness;
                ret = this.population[i];
            }
        }
        
        if(ret == null)
            ret = this.getBest();
        
        return ret;
    }
    
    private DNA getBest(){
        double best = Integer.MAX_VALUE;
        DNA ret = null;
        
        for(int i = 0; i<this.population.length; i++){
            if(Math.abs(1 - this.population[i].fitness) < best){
                best = Math.abs(1 - this.population[i].fitness);
                ret = this.population[i];
            }
        }
//        System.out.println("AJKHSHAJKDSHKJDAS: "+this.getFileSolutionSum(ret));
//        System.out.println(ret == null);
        return ret;
    }
    
    
    public String getFileSolution(DNA chromosome){
        String ret = "";
            for(int j = 0; j<chromosome.genes.length; j++){
                if(chromosome.genes[j] == 1)
                    ret += GeneticsDisk.files[j]+"    ";
//                    System.out.print(GeneticsDisk.files[j]+"    ");
                else
                    ret += 0+"    ";
//                    System.out.print(0+"    ");
            }
            
        return ret;
    }
    
    public double getFileSolutionSum(DNA chromosome){
        double sum = 0;
        for(int j = 0; j<chromosome.genes.length; j++){
            if(chromosome.genes[j] == 1)
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
