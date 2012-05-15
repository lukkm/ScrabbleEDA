package helpers;

public class Primes {

	private int[][] primes = new int[15][15];
	
	public int[][] getPrimes(){
		return this.primes;
	}
	
	public Primes(){
        this.primes = calculatePrimeNumbers();
	}
	
    public int[][] calculatePrimeNumbers() {

    	int[][] primes = new int[15][15];
//    	int i = 2000;
//        int primeNumberCounter = 0;

//        while (primeNumberCounter < 225) {
//        	++i;
//            int i1 = (int) Math.ceil(Math.sqrt(i));
//
//            boolean isPrimeNumber = false;
//
//            while (i1 > 1) {
//
//                if ((i != i1) && (i % i1 == 0)) {
//                    isPrimeNumber = false;
//                    break;
//                } else if (!isPrimeNumber) {
//                    isPrimeNumber = true;
//                }
//
//                --i1;
//            }
//
//            if (isPrimeNumber) {
//        		primes[primeNumberCounter % 15][primeNumberCounter / 15] = i;
//        		++primeNumberCounter;
//            }
//        }
        
    	for (int j = 0 ; j < 15 ; j++) {
	    	int aux = 1000 * j;
	        int primeNumberCounter = 0;
	        int i = aux;
	        while (primeNumberCounter < 15) {
	        	++i;
	            int i1 = (int) Math.ceil(Math.sqrt(i));
	
	            boolean isPrimeNumber = false;
	
	            while (i1 > 1) {
	
	                if ((i != i1) && (i % i1 == 0)) {
	                    isPrimeNumber = false;
	                    break;
	                } else if (!isPrimeNumber) {
	                    isPrimeNumber = true;
	                }
	
	                --i1;
	            }
	
	            if (isPrimeNumber) {
	        		primes[primeNumberCounter][j] = i;
	        		++primeNumberCounter;
	            }
	        }
    	}
        return primes;
    }
	
}
