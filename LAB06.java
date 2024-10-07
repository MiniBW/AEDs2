import java.util.Random;

public class LAB06 {
    public static void main(String[] args) {
        int[] tamanhos = {100, 1000, 10000};
        
        // Testar para cada tamanho de array (100, 1000, 10000)
        for (int tamanho : tamanhos) {
            System.out.println("\nTestando com arrays de tamanho " + tamanho + ":");
            
            // Arrays ordenados
            int[] arrayOrdenado = gerarArrayOrdenado(tamanho);
            testarQuickSorts(arrayOrdenado, "Ordenado");
            
            // Arrays quase ordenados
            int[] arrayQuaseOrdenado = gerarArrayQuaseOrdenado(tamanho);
            testarQuickSorts(arrayQuaseOrdenado, "Quase Ordenado");
            
            // Arrays aleatórios
            int[] arrayAleatorio = gerarArrayAleatorio(tamanho);
            testarQuickSorts(arrayAleatorio, "Aleatório");
        }
    }

    // Função para rodar os testes com as diferentes variações do QuickSort
    public static void testarQuickSorts(int[] arrayOriginal, String tipo) {
        System.out.println("\nArray " + tipo);

        // Clonar os arrays para garantir que todos os algoritmos usem os mesmos dados
        int[] arrayPrimeiro = arrayOriginal.clone();
        int[] arrayUltimo = arrayOriginal.clone();
        int[] arrayAleatorio = arrayOriginal.clone();
        int[] arrayMedianaTres = arrayOriginal.clone();

        // Testando QuickSort com pivô como o primeiro elemento
        long inicio = System.nanoTime();
        quickSortPivoPrimeiro(arrayPrimeiro, 0, arrayPrimeiro.length - 1);
        long fim = System.nanoTime();
        System.out.println("Tempo com Pivô Primeiro: " + (fim - inicio) + " nanosegundos");

        // Testando QuickSort com pivô como o último elemento
        inicio = System.nanoTime();
        quickSortPivoUltimo(arrayUltimo, 0, arrayUltimo.length - 1);
        fim = System.nanoTime();
        System.out.println("Tempo com Pivô Último: " + (fim - inicio) + " nanosegundos");

        // Testando QuickSort com pivô aleatório
        inicio = System.nanoTime();
        quickSortPivoAleatorio(arrayAleatorio, 0, arrayAleatorio.length - 1);
        fim = System.nanoTime();
        System.out.println("Tempo com Pivô Aleatório: " + (fim - inicio) + " nanosegundos");

        // Testando QuickSort com mediana de três elementos
        inicio = System.nanoTime();
        quickSortMedianaTres(arrayMedianaTres, 0, arrayMedianaTres.length - 1);
        fim = System.nanoTime();
        System.out.println("Tempo com Mediana de Três: " + (fim - inicio) + " nanosegundos");
    }

    // Funções de geração de arrays

    // Gera um array ordenado de 1 até n
    public static int[] gerarArrayOrdenado(int tamanho) {
        int[] array = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            array[i] = i + 1;
        }
        return array;
    }

    // Gera um array quase ordenado (somente 10% dos elementos são desordenados)
    public static int[] gerarArrayQuaseOrdenado(int tamanho) {
        int[] array = gerarArrayOrdenado(tamanho);
        Random random = new Random();
        for (int i = 0; i < tamanho / 10; i++) {
            int indice1 = random.nextInt(tamanho);
            int indice2 = random.nextInt(tamanho);
            trocar(array, indice1, indice2);  // Troca dois elementos para desordenar parcialmente
        }
        return array;
    }

    // Gera um array aleatório
    public static int[] gerarArrayAleatorio(int tamanho) {
        int[] array = new int[tamanho];
        Random random = new Random();
        for (int i = 0; i < tamanho; i++) {
            array[i] = random.nextInt(10000);  // Gera números aleatórios entre 0 e 9999
        }
        return array;
    }

    // Implementação das variações do QuickSort

    public static int particionar(int[] array, int esq, int dir, int indiceP) {
        int valorPivo = array[indiceP];
        trocar(array, indiceP, dir);  // Move o pivô para o final
        int indiceA = esq;

        for (int i = esq; i < dir; i++) {
            if (array[i] < valorPivo) {
                trocar(array, i, indiceA);
                indiceA++;
            }
        }
        trocar(array, indiceA, dir);  // Move o pivô para a posição correta
        return indiceA;
    }

    public static void trocar(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void quickSortPivoPrimeiro(int[] array, int esq, int dir) {
        if (esq < dir) {
            int indiceP = esq;
            int novoIndiceP = particionar(array, esq, dir, indiceP);
            quickSortPivoPrimeiro(array, esq, novoIndiceP - 1);
            quickSortPivoPrimeiro(array, novoIndiceP + 1, dir);
        }
    }

    public static void quickSortPivoUltimo(int[] array, int esq, int dir) {
        if (esq < dir) {
            int indiceP = dir;
            int novoIndiceP = particionar(array, esq, dir, indiceP);
            quickSortPivoUltimo(array, esq, novoIndiceP - 1);
            quickSortPivoUltimo(array, novoIndiceP + 1, dir);
        }
    }

    public static void quickSortPivoAleatorio(int[] array, int esq, int dir) {
        if (esq < dir) {
            Random random = new Random();
            int indiceP = esq + random.nextInt(dir - esq + 1);
            int novoIndiceP = particionar(array, esq, dir, indiceP);
            quickSortPivoAleatorio(array, esq, novoIndiceP - 1);
            quickSortPivoAleatorio(array, novoIndiceP + 1, dir);
        }
    }

    public static void quickSortMedianaTres(int[] array, int esq, int dir) {
        if (esq < dir) {
            int meio = esq + (dir - esq) / 2;
            int indiceP = medianaDeTres(array, esq, meio, dir);
            int novoIndiceP = particionar(array, esq, dir, indiceP);
            quickSortMedianaTres(array, esq, novoIndiceP - 1);
            quickSortMedianaTres(array, novoIndiceP + 1, dir);
        }
    }

    public static int medianaDeTres(int[] array, int esq, int meio, int dir) {
        if (array[esq] > array[meio]) trocar(array, esq, meio);
        if (array[esq] > array[dir]) trocar(array, esq, dir);
        if (array[meio] > array[dir]) trocar(array, meio, dir);
        return meio;
    }
}
