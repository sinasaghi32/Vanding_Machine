package manager;

import datastructure.DrinkPriceTree;
import model.Drink;

/** SORT/SEARCH CHECK: 버블/선택/삽입 정렬, 선형/이진/트리 검색 제공. */
public class SortSearchManager {
    public Drink[] sortByPriceBubble(Drink[] arr) { Drink[] a = arr.clone(); for (int i=0;i<a.length-1;i++) for (int j=0;j<a.length-1-i;j++) if (a[j].getPrice()>a[j+1].getPrice()) swap(a,j,j+1); return a; }
    public Drink[] sortByStockSelection(Drink[] arr) { Drink[] a = arr.clone(); for (int i=0;i<a.length-1;i++){ int min=i; for(int j=i+1;j<a.length;j++) if(a[j].getStock()<a[min].getStock()) min=j; swap(a,i,min);} return a; }
    public Drink[] sortBySoldInsertion(Drink[] arr) { Drink[] a = arr.clone(); for (int i=1;i<a.length;i++){ Drink key=a[i]; int j=i-1; while(j>=0&&a[j].getSoldCount()>key.getSoldCount()){a[j+1]=a[j];j--;} a[j+1]=key;} return a; }
    public Drink binarySearchByPrice(Drink[] sortedByPrice, int price) { int l=0,r=sortedByPrice.length-1; while(l<=r){int m=(l+r)/2; if(sortedByPrice[m].getPrice()==price)return sortedByPrice[m]; if(sortedByPrice[m].getPrice()<price)l=m+1; else r=m-1;} return null; }
    public Drink treeSearchByPrice(Drink[] arr, int price) { DrinkPriceTree tree = new DrinkPriceTree(); for (Drink d: arr) tree.insert(d); return tree.searchByPrice(price); }
    private void swap(Drink[] a, int i, int j){ Drink t=a[i]; a[i]=a[j]; a[j]=t; }
}
