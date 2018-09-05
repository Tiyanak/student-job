package hr.firma.sp.studentskiposao.algorithm;

import java.util.Collections;
import java.util.List;

import hr.firma.sp.studentskiposao.model.AbstractData;

public class QuickSort {

    // polje po kojem se sortira - nije u knjizi, ali u implementaciji se mora znatpolje za posao  ili firmu
    public static String fieldName;

    public QuickSort(String fieldName) {
        QuickSort.fieldName = fieldName;
    }

    // kao u knjizi isto, samo je tamo Comparable[] sto oznacava polje tipova Comparable (objekti koji se mogu usporedivati)
    // ovdje nije polje [], nego Lista List<> tipova podataka AbstractData
    // znaci imamo JobData i CompanyData, to su klase modeli za aplikaciju, oni nasljeduju AbstractData koja je abstraktna klasa
    // znaci JobData i CompanyData su kao neka djeca odAbstractData odnosno oboje moraju imati metodu compareTo() koja je definirana u AbstractData
    // pogledaj folder /model
    public static void sort(List<AbstractData> a, String fieldName) {
        QuickSort.fieldName = fieldName;
        Collections.shuffle(a);
        sort(a, 0, a.size() - 1);
    }

    private static void sort(List<AbstractData> a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a,lo, hi);
        sort(a, lo, j - 1);
        sort(a, j+1, hi);
    }

    private static int partition(List<AbstractData> a, int lo, int hi) {
        int i = lo, j = hi + 1;
        AbstractData v = a.get(lo);
        while (true) {
            while (less(a.get(++i), v)) if (i == hi) break;
            while (less(v, a.get(--j))) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static boolean less(AbstractData v, AbstractData w) {
        return v.compareTo(w, QuickSort.fieldName) < 0;
    }

    private static void exch(List<AbstractData> a, int i, int j) {
        AbstractData item = a.get(i);
        a.set(i, a.get(j));
        a.set(j, item);
    }

}
