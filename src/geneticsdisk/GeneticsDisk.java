package geneticsdisk;


public class GeneticsDisk {

    static String targetPhrase;
//    static double files[] = {10,39,4,191,48,1,586,18,56,6,15,3};
    static double files[] = {20,39,4,190,48,1,580,18,56,5,10,3};
//    static double files[] = {1,1};
    static double TARGET[] = files;
    static int diskSize = 800;
    static final double mutationRate = 0.01;
    
    public static void main(String[] args) {
        
        
        System.out.println("Number of files: "+files.length);
        double sumOfFiles = 0;
        for(int i = 0; i<files.length; i++){
            sumOfFiles += files[i];
        }
        System.out.println("Size of files: "+sumOfFiles);
        
        
        Population pop = new Population(files.length);
        
        pop.startingPopulation();
        pop.calcFitness();


        int i = 0;
        while(!pop.solutionExists()){
            pop.generate();
            pop.calcFitness();
            System.out.println(pop.getBestFit()+"\tFITNESS: "+pop.getBestFit().fitness+ "   SUM: "+pop.getBestFit().calcSum() + "\tI = "+i);



            
            if(pop.getSolution() != null){
                System.out.print("SOLUTION: "+pop.getSolution()+"\nFILES:    ");
                pop.getFileSolution();
                System.out.print("\tSUM: "+pop.getFileSolutionSum());
                System.out.println();
                System.out.println("GENERATION: "+(i+1));
                System.out.println("GENERATION'S AVERAGE FITNESS: "+pop.getAverageFitness());
                break;
            }
            i++;
        }
    }
    
    public static void generateTargetArray(){
        double[] arr = new double[targetPhrase.length()];
        
        for(int i = 0; i<arr.length; i++){
            arr[i] = (int)targetPhrase.charAt(i);
        }
        
        TARGET = arr;
    }
    
    
    public static String arrayToString(int[] arr){
        String ret = "";
        for(int i = 0; i<arr.length; i++){
            ret += arr[i];
        }
        return ret;
    }
}
