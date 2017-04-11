package geneticsdisk;


public class GeneticsDisk {

    static String targetPhrase;
//    static double files[];
    static double files[] =     {10, 39, 4, 191, 48, 1, 586, 18, 56, 6, 15, 3};
//    static double files[] =   {580, 802, 710, 1000, 60, 30, 30, 586, 7, 7, 40, 70, 15, 15, 20, 10, 11, 19};
    static int GENERATION_LIMIT = 1000;
    static int diskSize = 1000;
    static double mutationRate = 0.01;
    
    public static void main(String[] args) {
        
        /* GENERATING FILES RANDOMLY    */
//        files = generateFileArray();
        
        
        System.out.println("Number of files: "+files.length);
        double sumOfFiles = 0;
        for(int i = 0; i<files.length; i++){
            sumOfFiles += files[i];
        }
        System.out.println("Size of files: "+sumOfFiles);
        
        
        Population pop = new Population(50);
        
        pop.startingPopulation();
        pop.calcFitness();


        int i = 0,similarityCounter = 0;
        DNA bestFitGlobally = pop.getBestFit();
        
        
        while(!pop.solutionExists() && i < GENERATION_LIMIT){
            pop.generate();
            pop.calcFitness();
            
            
            System.out.println("          "+bestFitGlobally+"\tFITNESS: "+bestFitGlobally.fitness+ "   SUM: "+bestFitGlobally.calcSum() + "\tLOCAL SUM: "+pop.getBestFit().calcSum() + "\tI = "+i);
//            if(pop.getBestFit() != null)
//            System.out.println(pop.getBestFit()+"\tFITNESS: "+pop.getBestFit().fitness+ "   SUM: "+pop.getBestFit().calcSum() + "\tI = "+i);
            
            if(bestFitGlobally == pop.getBestFit()) similarityCounter++;    else similarityCounter = 0;
            
            
            if(bestFitGlobally.fitness < pop.getBestFit().fitness)
                bestFitGlobally = pop.getBestFit();

            if(similarityCounter >= 5){
                mutationRate = mutationRate*10;
                System.out.println("SIM: "+similarityCounter);
            }
            
            if(pop.getSolution() != null){
                System.out.println("POPULATION: "+pop.population.length);
                System.out.println("TOTAL FILES SIZE: "+getAllFilesSize());
                System.out.print("SOLUTION: "+pop.getSolution()+"\nFILES:    ");
                System.out.print(pop.getFileSolution(pop.getSolution()));
                System.out.print("\tSUM: "+pop.getFileSolutionSum(pop.getSolution()));
                System.out.println();
                System.out.println("GENERATION: "+(i+1));
                System.out.println("GENERATION'S AVERAGE FITNESS: "+pop.getAverageFitness());
               break;
            }
            i++;
        }
        
        if(i == GENERATION_LIMIT){
            System.out.println("POPULATION: "+pop.population.length);
            System.out.println("TOTAL FILES SIZE: "+getAllFilesSize());
            System.out.print("SOLUTION: "+bestFitGlobally+"\nFILES:    ");
            System.out.print(pop.getFileSolution(bestFitGlobally));
            System.out.print("\tSUM: "+pop.getFileSolutionSum(bestFitGlobally));
            System.out.println();
            System.out.println("GENERATION: "+(i+1));
            System.out.println("GENERATION'S AVERAGE FITNESS: "+pop.getAverageFitness());
        }
    }
    
    
    public static double getAllFilesSize(){
        double sum = 0;
        for(int i = 0; i<files.length; i++){
            sum += files[i];
        }
        
        return sum;
    
    }
    
    
    public static double[] generateFileArray(){
//        double[] temp = new double[(int)(Math.random()*20 + 10)];
        double[] temp = new double[100];
        for(int i = 0; i<temp.length; i++){
            temp[i] = Math.random()*25 + 5;
        }
        
        return temp;
    }
    
    
    public static String arrayToString(int[] arr){
        String ret = "";
        for(int i = 0; i<arr.length; i++){
            ret += arr[i];
        }
        return ret;
    }
}
