package com.dksazxv.dskf.com.model;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class MultipleMatrixDouble {
    private final static String TAG = MultipleMatrixDouble.class.toString();

    private Matrix<Double> mMatrixA;    // Первая исходная матрица для умножения.
    private Matrix<Double> mMatrixB;    // Вторая исходная матрица для умножения.
    private Matrix<Double> mMatrixC;    // Матрица-результат произведения.
    private int mRows;                  // Количество строк первой матрицы.
    private int mCols;                  // Количество столбцов второй матрицы.

    private int mCountThreads;          // Количество используемых нитей.

    // true - производить рассчеты, без сохранения результата.
    private boolean mOnlyCompute = false;

    // Семафор для записи результатов в матрицу.
    private final Semaphore mSyncSetItem = new Semaphore(1);
    // Барьер для одновременного старта работы потоков.
    private final CountDownLatch mStartBarrier = new CountDownLatch(1);
    // Барьер для ожидания завершения работы всех потоков.
    private CountDownLatch mFinishBarrier = null;

    // Ограничение на максимальное количество потоков.
    public static final long MAX_THREADS = 100000;

    private class MultipleInThread implements Runnable {
        private int mI;         // Индекс строки рассчитываемого элемента.
        private int mJ;         // Индекс столбца рассчитываемого элемента.
        private int mA;         // Коэффициент для расчета индексов.
        private int mCount;     // Количество элементов которые нужно посчитать.

        private Thread mThread; // Поток выполнения.
        private int mThreadNum; // Номер потока.

        public MultipleInThread(int i, int j, int count, int a, int threadNum) {
            this.mA = a;
            this.mCount = count;
            this.mI = i;
            this.mJ = j;
            this.mThreadNum = threadNum;

            mThread = new Thread(this);
            mThread.start();
        }

        @Override
        public void run() {

            try {
                MultipleMatrixDouble.this.mStartBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final long startExec = System.currentTimeMillis();

            int countSolved = 0;
            Double result = 0.0;

            try {
                while (countSolved < this.mCount) {


                    result = MultipleMatrix(this.mI, this.mJ);

                    result = new BigDecimal(result).setScale(3, RoundingMode.UP).doubleValue();

                    if(!MultipleMatrixDouble.this.mOnlyCompute){
                        mSyncSetItem.acquire();     // Занимаем семафор.
                        // Заносим результат в матрицу.
                        mMatrixC.onSetItem(this.mI, this.mJ, result);
                        mSyncSetItem.release();     // Освобождаем семафор.
                    }


                    this.mJ++;
                    this.mA++;
                    if (this.mJ >= mCols) {       // Если был переход на другую строку.
                        this.mI++;              // Наращиваем индекс строки.
                        // Рассчитываем индекс столбца.
                        this.mJ = this.mA - this.mI * mCols;
                    }
                    countSolved++;       // Наращиваем количество посчитанных элементов.
                }

                // Фиксируем время окончания работы потока.
                final long endExec = System.currentTimeMillis();
//                Log.d(TAG, "Thread\t" + this.mThreadNum + "\t\t\tStart:\t" + startExec
//                        + "\t\tEnd:\t" + endExec);

                // Уменьшаем значение открытия барьера окончания работы всех потоков.
                MultipleMatrixDouble.this.mFinishBarrier.countDown();

            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(-1);
            }

        }

        // Метод умножения элементов матриц.
        // I и J - индексы рассчитываемого элемента.
        private Double MultipleMatrix(int I, int J) throws Exception {
            Double sum = 0.0;       // Результат умножения (значение элемента).

            // Цикл умножения строки на столбец.
            for (int i = 0; i < MultipleMatrixDouble.this.mMatrixA.onGetCols(); ++i)
                sum += mMatrixA.onGetItem(I, i) * mMatrixB.onGetItem(i, J);
            return sum;
        }
    }


    public MultipleMatrixDouble(Matrix<Double> matrixA, Matrix<Double> matrixB, int countThreads) throws Exception {
        // Проверка входных параметров.
        if (matrixA == null || matrixB == null)
            throw new Exception("Error t1: null resource matrix");
        if (countThreads <= 0 || countThreads > MAX_THREADS)
            throw new Exception("Error t2: countThreads error");
        if (matrixA.onGetCols() != matrixB.onGetRows())
            throw new Exception("Error t3: colsA != rowsB");
        if (countThreads > (matrixA.onGetRows() * matrixB.onGetCols()))
            throw new Exception("Error t4: countThreads error");

        // Знаносим исходные данные.
        this.mMatrixA = matrixA;
        this.mMatrixB = matrixB;
        this.mCountThreads = countThreads;
        this.mRows = this.mMatrixA.onGetRows();
        this.mCols = mMatrixB.onGetCols();

        // Выделяем память для матрицы-результата.
        this.mMatrixC = new Matrix<>(this.mRows, this.mCols);

        // Создаем барьер окончания работы всех потоков.
        this.mFinishBarrier = new CountDownLatch(this.mCountThreads);
    }

    public void onStartMultiple() throws Exception {
        int size = this.mCols * this.mRows;
        int count = size / this.mCountThreads;

        int i = 0, j = 0;

        // Коэффициент для рассчета индексов начальных элементов.
        // Используя данный коэффициент можно рассчитать индексы строки и столбца
        // следующего начального элемента (грубо говоря это количество уже
        // разделенных между потоками элементов).
        int a = 0;

        // Если каждый поток считает одинаковое количество элементов
        if (size % this.mCountThreads == 0) {
            for (int t = 0; t < this.mCountThreads; t++) {  // Цикл инициализации потоков.
                new MultipleInThread(i, j, count, a, t);    // Создаем и инициализируем поток.
                a += count;     // Наращиваем количество разделенных элементов.
                j += count;     // Определяем индекс столбца следующего нач. элемента.

                // Если индекс столбца вышел за границы, то явно был переход
                // на другую строку (или даже строки). Следовательно нужно определить
                // индексы новой строки и столбца.
                if (j >= this.mCols) {
                    // Если были переходы более чем на одну строку (учитываются все переходы).
                    if (a >= this.mCols)
                        i = a / this.mCols;    // Целая часть - индекс новой строки.
                    else  // Иначе переход только на след. строку.
                        i++;

                    j = a - i * this.mCols;    // Рассчитываем индекс нового столбца.
                }
            }
        } else { // Если нацело не делится, то всем потокам поровну, кроме последнего.
            for (int t = 0; t < this.mCountThreads - 1; ++t) {  // Цикл инициализации потоков.
                new MultipleInThread(i, j, count, a, t);        // Создаем и инициализируем поток.
                a += count;             // Наращиваем количество разделенных элементов.
                j += count;             // Определяем индекс столбца следующего нач. элемента.

                // Если индекс столбца вышел за границы, то явно был переход
                // на другую строку (или даже строки). Следовательно нужно определить
                // индексы новой строки и столбца.
                if (j >= this.mCols) {
                    // Если были переходы более чем на одну строку (учитываются все переходы).
                    if (a >= this.mCols)
                        i = a / this.mCols;     // Целая часть - индекс новой строки.
                    else    // Иначе переход только на след. строку.
                        i++;

                    j = a - i * this.mCols;     // Рассчитываем индекс нового столбца.
                }
            }
            // Последнему потоку даем оставшееся количество элементов.
            new MultipleInThread(i, j, count + (size % this.mCountThreads), a, this.mCountThreads - 1);
        }

        Log.d(TAG, "Starting " + this.mCountThreads + " threads...");

        try{
            this.mStartBarrier.countDown();     // Открываем барьер (старт всех потоков).
            this.mFinishBarrier.await();        // Ждем открытия барьера (окончания работы всех потоков).
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    // Метод для вовзрата результата умножения матриц.
    public Matrix<Double> onGetResultMatrix() {
        return this.mMatrixC;
    }

    // Метод для получения значений флага учета результата.
    public boolean onGetFlagOnlyCompute() { return this.mOnlyCompute; }

    // Установка флага учета результата.
    public void onSetFlagOnlyCompute(boolean flag){ this.mOnlyCompute = flag; }
}
